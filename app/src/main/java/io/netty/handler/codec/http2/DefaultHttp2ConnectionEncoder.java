package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.CoalescingBufferQueue;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2FrameWriter;
import io.netty.handler.codec.http2.Http2RemoteFlowController;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayDeque;

/* loaded from: classes4.dex */
public class DefaultHttp2ConnectionEncoder implements Http2ConnectionEncoder {
    private final Http2FrameWriter a;
    private final Http2Connection b;
    private Http2LifecycleManager c;
    private final ArrayDeque<Http2Settings> d = new ArrayDeque<>(4);

    public DefaultHttp2ConnectionEncoder(Http2Connection http2Connection, Http2FrameWriter http2FrameWriter) {
        this.b = (Http2Connection) ObjectUtil.checkNotNull(http2Connection, "connection");
        this.a = (Http2FrameWriter) ObjectUtil.checkNotNull(http2FrameWriter, "frameWriter");
        if (http2Connection.remote().flowController() == null) {
            http2Connection.remote().flowController(new DefaultHttp2RemoteFlowController(http2Connection));
        }
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionEncoder
    public void lifecycleManager(Http2LifecycleManager http2LifecycleManager) {
        this.c = (Http2LifecycleManager) ObjectUtil.checkNotNull(http2LifecycleManager, "lifecycleManager");
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionEncoder
    public Http2FrameWriter frameWriter() {
        return this.a;
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionEncoder
    public Http2Connection connection() {
        return this.b;
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionEncoder
    public final Http2RemoteFlowController flowController() {
        return connection().remote().flowController();
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionEncoder
    public void remoteSettings(Http2Settings http2Settings) throws Http2Exception {
        Boolean pushEnabled = http2Settings.pushEnabled();
        Http2FrameWriter.Configuration configuration = configuration();
        Http2HeaderTable headerTable = configuration.headerTable();
        Http2FrameSizePolicy frameSizePolicy = configuration.frameSizePolicy();
        if (pushEnabled != null) {
            if (this.b.isServer() || !pushEnabled.booleanValue()) {
                this.b.remote().allowPushTo(pushEnabled.booleanValue());
            } else {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Client received a value of ENABLE_PUSH specified to other than 0", new Object[0]);
            }
        }
        Long maxConcurrentStreams = http2Settings.maxConcurrentStreams();
        if (maxConcurrentStreams != null) {
            this.b.local().maxActiveStreams((int) Math.min(maxConcurrentStreams.longValue(), 2147483647L));
        }
        Long headerTableSize = http2Settings.headerTableSize();
        if (headerTableSize != null) {
            headerTable.maxHeaderTableSize((int) Math.min(headerTableSize.longValue(), 2147483647L));
        }
        Integer maxHeaderListSize = http2Settings.maxHeaderListSize();
        if (maxHeaderListSize != null) {
            headerTable.maxHeaderListSize(maxHeaderListSize.intValue());
        }
        Integer maxFrameSize = http2Settings.maxFrameSize();
        if (maxFrameSize != null) {
            frameSizePolicy.maxFrameSize(maxFrameSize.intValue());
        }
        Integer initialWindowSize = http2Settings.initialWindowSize();
        if (initialWindowSize != null) {
            flowController().initialWindowSize(initialWindowSize.intValue());
        }
    }

    @Override // io.netty.handler.codec.http2.Http2DataWriter
    public ChannelFuture writeData(ChannelHandlerContext channelHandlerContext, int i, ByteBuf byteBuf, int i2, boolean z, ChannelPromise channelPromise) {
        try {
            Http2Stream a2 = a(i);
            switch (a2.state()) {
                case OPEN:
                case HALF_CLOSED_REMOTE:
                    flowController().addFlowControlled(a2, new a(a2, byteBuf, i2, z, channelPromise));
                    return channelPromise;
                default:
                    throw new IllegalStateException(String.format("Stream %d in unexpected state: %s", Integer.valueOf(a2.id()), a2.state()));
            }
        } catch (Throwable th) {
            byteBuf.release();
            return channelPromise.setFailure(th);
        }
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, boolean z, ChannelPromise channelPromise) {
        return writeHeaders(channelHandlerContext, i, http2Headers, 0, (short) 16, false, i2, z, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, short s, boolean z, int i3, boolean z2, final ChannelPromise channelPromise) {
        final Http2Stream http2Stream;
        try {
            Http2Stream stream = this.b.stream(i);
            if (stream == null) {
                http2Stream = this.b.local().createStream(i, z2);
            } else {
                switch (stream.state()) {
                    case OPEN:
                    case HALF_CLOSED_REMOTE:
                        break;
                    default:
                        throw new IllegalStateException(String.format("Stream %d in unexpected state: %s", Integer.valueOf(stream.id()), stream.state()));
                    case RESERVED_LOCAL:
                        stream.open(z2);
                        break;
                }
                http2Stream = stream;
            }
            Http2RemoteFlowController flowController = flowController();
            if (z2 && flowController.hasFlowControlled(http2Stream)) {
                flowController.addFlowControlled(http2Stream, new b(http2Stream, http2Headers, i2, s, z, i3, z2, channelPromise));
                return channelPromise;
            }
            ChannelFuture writeHeaders = this.a.writeHeaders(channelHandlerContext, i, http2Headers, i2, s, z, i3, z2, channelPromise);
            if (z2) {
                writeHeaders.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.http2.DefaultHttp2ConnectionEncoder.1
                    /* renamed from: a */
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        DefaultHttp2ConnectionEncoder.this.c.closeStreamLocal(http2Stream, channelPromise);
                    }
                });
            }
            return writeHeaders;
        } catch (Http2NoMoreStreamIdsException e) {
            this.c.onError(channelHandlerContext, e);
            return channelPromise.setFailure((Throwable) e);
        } catch (Throwable th) {
            return channelPromise.setFailure(th);
        }
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writePriority(ChannelHandlerContext channelHandlerContext, int i, int i2, short s, boolean z, ChannelPromise channelPromise) {
        try {
            Http2Stream stream = this.b.stream(i);
            if (stream == null) {
                stream = this.b.local().createIdleStream(i);
            }
            stream.setPriority(i2, s, z);
        } catch (Http2Exception.ClosedStreamCreationException unused) {
        } catch (Throwable th) {
            return channelPromise.setFailure(th);
        }
        return this.a.writePriority(channelHandlerContext, i, i2, s, z, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeRstStream(ChannelHandlerContext channelHandlerContext, int i, long j, ChannelPromise channelPromise) {
        return this.c.resetStream(channelHandlerContext, i, j, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeSettings(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings, ChannelPromise channelPromise) {
        this.d.add(http2Settings);
        try {
            if (http2Settings.pushEnabled() != null && this.b.isServer()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Server sending SETTINGS frame with ENABLE_PUSH specified", new Object[0]);
            }
            return this.a.writeSettings(channelHandlerContext, http2Settings, channelPromise);
        } catch (Throwable th) {
            return channelPromise.setFailure(th);
        }
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeSettingsAck(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        return this.a.writeSettingsAck(channelHandlerContext, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writePing(ChannelHandlerContext channelHandlerContext, boolean z, ByteBuf byteBuf, ChannelPromise channelPromise) {
        return this.a.writePing(channelHandlerContext, z, byteBuf, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writePushPromise(ChannelHandlerContext channelHandlerContext, int i, int i2, Http2Headers http2Headers, int i3, ChannelPromise channelPromise) {
        try {
            if (!this.b.goAwayReceived()) {
                this.b.local().reservePushStream(i2, a(i));
                return this.a.writePushPromise(channelHandlerContext, i, i2, http2Headers, i3, channelPromise);
            }
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Sending PUSH_PROMISE after GO_AWAY received.", new Object[0]);
        } catch (Throwable th) {
            return channelPromise.setFailure(th);
        }
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeGoAway(ChannelHandlerContext channelHandlerContext, int i, long j, ByteBuf byteBuf, ChannelPromise channelPromise) {
        return this.c.goAway(channelHandlerContext, i, j, byteBuf, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeWindowUpdate(ChannelHandlerContext channelHandlerContext, int i, int i2, ChannelPromise channelPromise) {
        return channelPromise.setFailure((Throwable) new UnsupportedOperationException("Use the Http2[Inbound|Outbound]FlowController objects to control window sizes"));
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionEncoder, io.netty.handler.codec.http2.Http2FrameWriter
    public ChannelFuture writeFrame(ChannelHandlerContext channelHandlerContext, byte b2, int i, Http2Flags http2Flags, ByteBuf byteBuf, ChannelPromise channelPromise) {
        return this.a.writeFrame(channelHandlerContext, b2, i, http2Flags, byteBuf, channelPromise);
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.a.close();
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionEncoder
    public Http2Settings pollSentSettings() {
        return this.d.poll();
    }

    @Override // io.netty.handler.codec.http2.Http2FrameWriter
    public Http2FrameWriter.Configuration configuration() {
        return this.a.configuration();
    }

    private Http2Stream a(int i) {
        String str;
        Http2Stream stream = this.b.stream(i);
        if (stream != null) {
            return stream;
        }
        if (this.b.streamMayHaveExisted(i)) {
            str = "Stream no longer exists: " + i;
        } else {
            str = "Stream does not exist: " + i;
        }
        throw new IllegalArgumentException(str);
    }

    /* loaded from: classes4.dex */
    private final class a extends FlowControlledBase {
        private final CoalescingBufferQueue c;
        private int d;

        public a(Http2Stream http2Stream, ByteBuf byteBuf, int i, boolean z, ChannelPromise channelPromise) {
            super(http2Stream, i, z, channelPromise);
            this.c = new CoalescingBufferQueue(channelPromise.channel());
            this.c.add(byteBuf, channelPromise);
            this.d = this.c.readableBytes();
        }

        @Override // io.netty.handler.codec.http2.Http2RemoteFlowController.FlowControlled
        public int size() {
            return this.d + this.padding;
        }

        @Override // io.netty.handler.codec.http2.Http2RemoteFlowController.FlowControlled
        public void error(ChannelHandlerContext channelHandlerContext, Throwable th) {
            this.c.releaseAndFailAll(th);
            DefaultHttp2ConnectionEncoder.this.c.onError(channelHandlerContext, th);
            this.promise.tryFailure(th);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r8v0, types: [io.netty.channel.ChannelPromise] */
        /* JADX WARN: Unknown variable types count: 1 */
        @Override // io.netty.handler.codec.http2.Http2RemoteFlowController.FlowControlled
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void write(io.netty.channel.ChannelHandlerContext r10, int r11) {
            /*
                r9 = this;
                io.netty.channel.CoalescingBufferQueue r0 = r9.c
                int r0 = r0.readableBytes()
                boolean r1 = r9.endOfStream
                if (r1 != 0) goto L_0x000f
                if (r0 == 0) goto L_0x000e
                if (r11 != 0) goto L_0x000f
            L_0x000e:
                return
            L_0x000f:
                int r0 = java.lang.Math.min(r0, r11)
                io.netty.channel.ChannelPromise r1 = r10.newPromise()
                io.netty.channel.ChannelPromise r8 = r1.addListener(r9)
                io.netty.channel.CoalescingBufferQueue r1 = r9.c
                io.netty.buffer.ByteBuf r5 = r1.remove(r0, r8)
                io.netty.channel.CoalescingBufferQueue r1 = r9.c
                int r1 = r1.readableBytes()
                r9.d = r1
                int r11 = r11 - r0
                int r0 = r9.padding
                int r6 = java.lang.Math.min(r11, r0)
                int r11 = r9.padding
                int r11 = r11 - r6
                r9.padding = r11
                io.netty.handler.codec.http2.DefaultHttp2ConnectionEncoder r11 = io.netty.handler.codec.http2.DefaultHttp2ConnectionEncoder.this
                io.netty.handler.codec.http2.Http2FrameWriter r2 = r11.frameWriter()
                io.netty.handler.codec.http2.Http2Stream r11 = r9.stream
                int r4 = r11.id()
                boolean r11 = r9.endOfStream
                if (r11 == 0) goto L_0x004d
                int r11 = r9.size()
                if (r11 != 0) goto L_0x004d
                r11 = 1
                goto L_0x004e
            L_0x004d:
                r11 = 0
            L_0x004e:
                r7 = r11
                r3 = r10
                r2.writeData(r3, r4, r5, r6, r7, r8)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http2.DefaultHttp2ConnectionEncoder.a.write(io.netty.channel.ChannelHandlerContext, int):void");
        }

        @Override // io.netty.handler.codec.http2.Http2RemoteFlowController.FlowControlled
        public boolean merge(ChannelHandlerContext channelHandlerContext, Http2RemoteFlowController.FlowControlled flowControlled) {
            if (a.class != flowControlled.getClass()) {
                return false;
            }
            a aVar = (a) flowControlled;
            if (Integer.MAX_VALUE - aVar.size() < size()) {
                return false;
            }
            aVar.c.copyTo(this.c);
            this.d = this.c.readableBytes();
            this.padding = Math.max(this.padding, aVar.padding);
            this.endOfStream = aVar.endOfStream;
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class b extends FlowControlledBase {
        private final Http2Headers c;
        private final int d;
        private final short e;
        private final boolean f;

        @Override // io.netty.handler.codec.http2.Http2RemoteFlowController.FlowControlled
        public boolean merge(ChannelHandlerContext channelHandlerContext, Http2RemoteFlowController.FlowControlled flowControlled) {
            return false;
        }

        @Override // io.netty.handler.codec.http2.Http2RemoteFlowController.FlowControlled
        public int size() {
            return 0;
        }

        public b(Http2Stream http2Stream, Http2Headers http2Headers, int i, short s, boolean z, int i2, boolean z2, ChannelPromise channelPromise) {
            super(http2Stream, i2, z2, channelPromise);
            this.c = http2Headers;
            this.d = i;
            this.e = s;
            this.f = z;
        }

        @Override // io.netty.handler.codec.http2.Http2RemoteFlowController.FlowControlled
        public void error(ChannelHandlerContext channelHandlerContext, Throwable th) {
            if (channelHandlerContext != null) {
                DefaultHttp2ConnectionEncoder.this.c.onError(channelHandlerContext, th);
            }
            this.promise.tryFailure(th);
        }

        @Override // io.netty.handler.codec.http2.Http2RemoteFlowController.FlowControlled
        public void write(ChannelHandlerContext channelHandlerContext, int i) {
            if (this.promise.isVoid()) {
                this.promise = channelHandlerContext.newPromise();
            }
            this.promise.addListener((GenericFutureListener<? extends Future<? super Void>>) this);
            DefaultHttp2ConnectionEncoder.this.a.writeHeaders(channelHandlerContext, this.stream.id(), this.c, this.d, this.e, this.f, this.padding, this.endOfStream, this.promise);
        }
    }

    /* loaded from: classes4.dex */
    public abstract class FlowControlledBase implements ChannelFutureListener, Http2RemoteFlowController.FlowControlled {
        protected boolean endOfStream;
        protected int padding;
        protected ChannelPromise promise;
        protected final Http2Stream stream;

        public FlowControlledBase(Http2Stream http2Stream, int i, boolean z, ChannelPromise channelPromise) {
            if (i >= 0) {
                this.padding = i;
                this.endOfStream = z;
                this.stream = http2Stream;
                this.promise = channelPromise;
                return;
            }
            throw new IllegalArgumentException("padding must be >= 0");
        }

        @Override // io.netty.handler.codec.http2.Http2RemoteFlowController.FlowControlled
        public void writeComplete() {
            if (this.endOfStream) {
                DefaultHttp2ConnectionEncoder.this.c.closeStreamLocal(this.stream, this.promise);
            }
        }

        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            if (!channelFuture.isSuccess()) {
                error(DefaultHttp2ConnectionEncoder.this.flowController().channelHandlerContext(), channelFuture.cause());
            }
        }
    }
}
