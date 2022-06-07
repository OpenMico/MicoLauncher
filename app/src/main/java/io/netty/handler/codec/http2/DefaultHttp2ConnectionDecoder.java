package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2FrameReader;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.List;

/* loaded from: classes4.dex */
public class DefaultHttp2ConnectionDecoder implements Http2ConnectionDecoder {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(DefaultHttp2ConnectionDecoder.class);
    private Http2FrameListener b;
    private final Http2Connection c;
    private Http2LifecycleManager d;
    private final Http2ConnectionEncoder e;
    private final Http2FrameReader f;
    private Http2FrameListener g;
    private final Http2PromisedRequestVerifier h;

    public DefaultHttp2ConnectionDecoder(Http2Connection http2Connection, Http2ConnectionEncoder http2ConnectionEncoder, Http2FrameReader http2FrameReader) {
        this(http2Connection, http2ConnectionEncoder, http2FrameReader, Http2PromisedRequestVerifier.ALWAYS_VERIFY);
    }

    public DefaultHttp2ConnectionDecoder(Http2Connection http2Connection, Http2ConnectionEncoder http2ConnectionEncoder, Http2FrameReader http2FrameReader, Http2PromisedRequestVerifier http2PromisedRequestVerifier) {
        this.b = new b();
        this.c = (Http2Connection) ObjectUtil.checkNotNull(http2Connection, "connection");
        this.f = (Http2FrameReader) ObjectUtil.checkNotNull(http2FrameReader, "frameReader");
        this.e = (Http2ConnectionEncoder) ObjectUtil.checkNotNull(http2ConnectionEncoder, "encoder");
        this.h = (Http2PromisedRequestVerifier) ObjectUtil.checkNotNull(http2PromisedRequestVerifier, "requestVerifier");
        if (http2Connection.local().flowController() == null) {
            http2Connection.local().flowController(new DefaultHttp2LocalFlowController(http2Connection));
        }
        http2Connection.local().flowController().frameWriter(http2ConnectionEncoder.frameWriter());
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public void lifecycleManager(Http2LifecycleManager http2LifecycleManager) {
        this.d = (Http2LifecycleManager) ObjectUtil.checkNotNull(http2LifecycleManager, "lifecycleManager");
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public Http2Connection connection() {
        return this.c;
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public final Http2LocalFlowController flowController() {
        return this.c.local().flowController();
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public void frameListener(Http2FrameListener http2FrameListener) {
        this.g = (Http2FrameListener) ObjectUtil.checkNotNull(http2FrameListener, "listener");
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public Http2FrameListener frameListener() {
        return this.g;
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public boolean prefaceReceived() {
        return a.class == this.b.getClass();
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public void decodeFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Http2Exception {
        this.f.readFrame(channelHandlerContext, byteBuf, this.b);
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public Http2Settings localSettings() {
        Http2Settings http2Settings = new Http2Settings();
        Http2FrameReader.Configuration configuration = this.f.configuration();
        Http2HeaderTable headerTable = configuration.headerTable();
        Http2FrameSizePolicy frameSizePolicy = configuration.frameSizePolicy();
        http2Settings.initialWindowSize(flowController().initialWindowSize());
        http2Settings.maxConcurrentStreams(this.c.remote().maxActiveStreams());
        http2Settings.headerTableSize(headerTable.maxHeaderTableSize());
        http2Settings.maxFrameSize(frameSizePolicy.maxFrameSize());
        http2Settings.maxHeaderListSize(headerTable.maxHeaderListSize());
        if (!this.c.isServer()) {
            http2Settings.pushEnabled(this.c.local().allowPushTo());
        }
        return http2Settings;
    }

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder
    public void localSettings(Http2Settings http2Settings) throws Http2Exception {
        Boolean pushEnabled = http2Settings.pushEnabled();
        Http2FrameReader.Configuration configuration = this.f.configuration();
        Http2HeaderTable headerTable = configuration.headerTable();
        Http2FrameSizePolicy frameSizePolicy = configuration.frameSizePolicy();
        if (pushEnabled != null) {
            if (!this.c.isServer()) {
                this.c.local().allowPushTo(pushEnabled.booleanValue());
            } else {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Server sending SETTINGS frame with ENABLE_PUSH specified", new Object[0]);
            }
        }
        Long maxConcurrentStreams = http2Settings.maxConcurrentStreams();
        if (maxConcurrentStreams != null) {
            this.c.remote().maxActiveStreams((int) Math.min(maxConcurrentStreams.longValue(), 2147483647L));
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

    @Override // io.netty.handler.codec.http2.Http2ConnectionDecoder, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.f.close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(Http2Stream http2Stream) {
        return flowController().unconsumedBytes(http2Stream);
    }

    void a(ChannelHandlerContext channelHandlerContext, int i, long j, ByteBuf byteBuf) throws Http2Exception {
        this.g.onGoAwayRead(channelHandlerContext, i, j, byteBuf);
        this.c.goAwayReceived(i, j, byteBuf);
    }

    void a(ChannelHandlerContext channelHandlerContext, byte b2, int i, Http2Flags http2Flags, ByteBuf byteBuf) throws Http2Exception {
        this.g.onUnknownFrame(channelHandlerContext, b2, i, http2Flags, byteBuf);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a implements Http2FrameListener {
        private a() {
        }

        /* JADX WARN: Finally extract failed */
        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public int onDataRead(ChannelHandlerContext channelHandlerContext, int i, ByteBuf byteBuf, int i2, boolean z) throws Http2Exception {
            int i3;
            Http2Exception e;
            RuntimeException e2;
            Http2Stream stream = DefaultHttp2ConnectionDecoder.this.c.stream(i);
            Http2LocalFlowController flowController = DefaultHttp2ConnectionDecoder.this.flowController();
            int readableBytes = byteBuf.readableBytes() + i2;
            try {
                if (!a(channelHandlerContext, i, stream, "DATA")) {
                    Http2Exception http2Exception = null;
                    switch (stream.state()) {
                        case OPEN:
                        case HALF_CLOSED_LOCAL:
                            break;
                        case HALF_CLOSED_REMOTE:
                        case CLOSED:
                            http2Exception = Http2Exception.streamError(stream.id(), Http2Error.STREAM_CLOSED, "Stream %d in unexpected state: %s", Integer.valueOf(stream.id()), stream.state());
                            break;
                        default:
                            http2Exception = Http2Exception.streamError(stream.id(), Http2Error.PROTOCOL_ERROR, "Stream %d in unexpected state: %s", Integer.valueOf(stream.id()), stream.state());
                            break;
                    }
                    try {
                        int a = DefaultHttp2ConnectionDecoder.this.a(stream);
                        try {
                            flowController.receiveFlowControlledFrame(stream, byteBuf, i2, z);
                            i3 = DefaultHttp2ConnectionDecoder.this.a(stream);
                        } catch (Http2Exception e3) {
                            e = e3;
                            i3 = a;
                        } catch (RuntimeException e4) {
                            e2 = e4;
                            i3 = a;
                        }
                        try {
                            if (http2Exception == null) {
                                int onDataRead = DefaultHttp2ConnectionDecoder.this.g.onDataRead(channelHandlerContext, i, byteBuf, i2, z);
                                flowController.consumeBytes(stream, onDataRead);
                                if (z) {
                                    DefaultHttp2ConnectionDecoder.this.d.closeStreamRemote(stream, channelHandlerContext.newSucceededFuture());
                                }
                                return onDataRead;
                            }
                            throw http2Exception;
                        } catch (Http2Exception e5) {
                            e = e5;
                            int a2 = readableBytes - (i3 - DefaultHttp2ConnectionDecoder.this.a(stream));
                            throw e;
                        } catch (RuntimeException e6) {
                            e2 = e6;
                            int a3 = readableBytes - (i3 - DefaultHttp2ConnectionDecoder.this.a(stream));
                            throw e2;
                        }
                    } catch (Throwable th) {
                        flowController.consumeBytes(stream, readableBytes);
                        if (z) {
                            DefaultHttp2ConnectionDecoder.this.d.closeStreamRemote(stream, channelHandlerContext.newSucceededFuture());
                        }
                        throw th;
                    }
                }
            } catch (Throwable unused) {
            }
            flowController.receiveFlowControlledFrame(stream, byteBuf, i2, z);
            flowController.consumeBytes(stream, readableBytes);
            b(i);
            return readableBytes;
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, boolean z) throws Http2Exception {
            onHeadersRead(channelHandlerContext, i, http2Headers, 0, (short) 16, false, i2, z);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, short s, boolean z, int i3, boolean z2) throws Http2Exception {
            Http2Stream http2Stream;
            boolean z3;
            boolean z4;
            short s2;
            int i4;
            Http2Stream stream = DefaultHttp2ConnectionDecoder.this.c.stream(i);
            if (stream != null || DefaultHttp2ConnectionDecoder.this.c.streamMayHaveExisted(i)) {
                http2Stream = stream;
                z3 = false;
            } else {
                Http2Stream createStream = DefaultHttp2ConnectionDecoder.this.c.remote().createStream(i, z2);
                z3 = createStream.state() == Http2Stream.State.HALF_CLOSED_REMOTE;
                http2Stream = createStream;
            }
            if (!a(channelHandlerContext, i, http2Stream, "HEADERS")) {
                switch (http2Stream.state()) {
                    case OPEN:
                    case HALF_CLOSED_LOCAL:
                        i4 = i2;
                        s2 = s;
                        z4 = z;
                        break;
                    case HALF_CLOSED_REMOTE:
                        if (z3) {
                            i4 = i2;
                            s2 = s;
                            z4 = z;
                            break;
                        } else {
                            throw Http2Exception.streamError(http2Stream.id(), Http2Error.STREAM_CLOSED, "Stream %d in unexpected state: %s", Integer.valueOf(http2Stream.id()), http2Stream.state());
                        }
                    case CLOSED:
                        throw Http2Exception.streamError(http2Stream.id(), Http2Error.STREAM_CLOSED, "Stream %d in unexpected state: %s", Integer.valueOf(http2Stream.id()), http2Stream.state());
                    case RESERVED_REMOTE:
                        http2Stream.open(z2);
                        i4 = i2;
                        s2 = s;
                        z4 = z;
                        break;
                    default:
                        throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d in unexpected state: %s", Integer.valueOf(http2Stream.id()), http2Stream.state());
                }
                try {
                    http2Stream.setPriority(i4, s2, z4);
                } catch (Http2Exception.ClosedStreamCreationException unused) {
                }
                DefaultHttp2ConnectionDecoder.this.g.onHeadersRead(channelHandlerContext, i, http2Headers, i2, s, z, i3, z2);
                if (z2) {
                    DefaultHttp2ConnectionDecoder.this.d.closeStreamRemote(http2Stream, channelHandlerContext.newSucceededFuture());
                }
            }
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onPriorityRead(ChannelHandlerContext channelHandlerContext, int i, int i2, short s, boolean z) throws Http2Exception {
            Http2Stream stream = DefaultHttp2ConnectionDecoder.this.c.stream(i);
            try {
                if (stream == null) {
                    if (DefaultHttp2ConnectionDecoder.this.c.streamMayHaveExisted(i)) {
                        DefaultHttp2ConnectionDecoder.a.info("{} ignoring PRIORITY frame for stream {}. Stream doesn't exist but may  have existed", channelHandlerContext.channel(), Integer.valueOf(i));
                        return;
                    }
                    stream = DefaultHttp2ConnectionDecoder.this.c.remote().createIdleStream(i);
                } else if (a(i)) {
                    DefaultHttp2ConnectionDecoder.a.info("{} ignoring PRIORITY frame for stream {}. Stream created after GOAWAY sent. Last known stream by peer {}", channelHandlerContext.channel(), Integer.valueOf(i), Integer.valueOf(DefaultHttp2ConnectionDecoder.this.c.remote().lastStreamKnownByPeer()));
                    return;
                }
                stream.setPriority(i2, s, z);
            } catch (Http2Exception.ClosedStreamCreationException unused) {
            }
            DefaultHttp2ConnectionDecoder.this.g.onPriorityRead(channelHandlerContext, i, i2, s, z);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onRstStreamRead(ChannelHandlerContext channelHandlerContext, int i, long j) throws Http2Exception {
            Http2Stream stream = DefaultHttp2ConnectionDecoder.this.c.stream(i);
            if (stream == null) {
                b(i);
                return;
            }
            int i2 = AnonymousClass1.a[stream.state().ordinal()];
            if (i2 == 4) {
                return;
            }
            if (i2 != 6) {
                DefaultHttp2ConnectionDecoder.this.g.onRstStreamRead(channelHandlerContext, i, j);
                DefaultHttp2ConnectionDecoder.this.d.closeStream(stream, channelHandlerContext.newSucceededFuture());
                return;
            }
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "RST_STREAM received for IDLE stream %d", Integer.valueOf(i));
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onSettingsAckRead(ChannelHandlerContext channelHandlerContext) throws Http2Exception {
            Http2Settings pollSentSettings = DefaultHttp2ConnectionDecoder.this.e.pollSentSettings();
            if (pollSentSettings != null) {
                a(pollSentSettings);
            }
            DefaultHttp2ConnectionDecoder.this.g.onSettingsAckRead(channelHandlerContext);
        }

        private void a(Http2Settings http2Settings) throws Http2Exception {
            Boolean pushEnabled = http2Settings.pushEnabled();
            Http2FrameReader.Configuration configuration = DefaultHttp2ConnectionDecoder.this.f.configuration();
            Http2HeaderTable headerTable = configuration.headerTable();
            Http2FrameSizePolicy frameSizePolicy = configuration.frameSizePolicy();
            if (pushEnabled != null) {
                if (!DefaultHttp2ConnectionDecoder.this.c.isServer()) {
                    DefaultHttp2ConnectionDecoder.this.c.local().allowPushTo(pushEnabled.booleanValue());
                } else {
                    throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Server sending SETTINGS frame with ENABLE_PUSH specified", new Object[0]);
                }
            }
            Long maxConcurrentStreams = http2Settings.maxConcurrentStreams();
            if (maxConcurrentStreams != null) {
                DefaultHttp2ConnectionDecoder.this.c.remote().maxActiveStreams((int) Math.min(maxConcurrentStreams.longValue(), 2147483647L));
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
                DefaultHttp2ConnectionDecoder.this.flowController().initialWindowSize(initialWindowSize.intValue());
            }
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onSettingsRead(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.this.e.remoteSettings(http2Settings);
            DefaultHttp2ConnectionDecoder.this.e.writeSettingsAck(channelHandlerContext, channelHandlerContext.newPromise());
            DefaultHttp2ConnectionDecoder.this.g.onSettingsRead(channelHandlerContext, http2Settings);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onPingRead(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.this.e.writePing(channelHandlerContext, true, byteBuf.retainedSlice(), channelHandlerContext.newPromise());
            DefaultHttp2ConnectionDecoder.this.g.onPingRead(channelHandlerContext, byteBuf);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onPingAckRead(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.this.g.onPingAckRead(channelHandlerContext, byteBuf);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onPushPromiseRead(ChannelHandlerContext channelHandlerContext, int i, int i2, Http2Headers http2Headers, int i3) throws Http2Exception {
            Http2Stream stream = DefaultHttp2ConnectionDecoder.this.c.stream(i);
            if (!a(channelHandlerContext, i, stream, "PUSH_PROMISE")) {
                if (stream != null) {
                    switch (stream.state()) {
                        case OPEN:
                        case HALF_CLOSED_LOCAL:
                            if (!DefaultHttp2ConnectionDecoder.this.h.isAuthoritative(channelHandlerContext, http2Headers)) {
                                throw Http2Exception.streamError(i2, Http2Error.PROTOCOL_ERROR, "Promised request on stream %d for promised stream %d is not authoritative", Integer.valueOf(i), Integer.valueOf(i2));
                            } else if (!DefaultHttp2ConnectionDecoder.this.h.isCacheable(http2Headers)) {
                                throw Http2Exception.streamError(i2, Http2Error.PROTOCOL_ERROR, "Promised request on stream %d for promised stream %d is not known to be cacheable", Integer.valueOf(i), Integer.valueOf(i2));
                            } else if (DefaultHttp2ConnectionDecoder.this.h.isSafe(http2Headers)) {
                                DefaultHttp2ConnectionDecoder.this.c.remote().reservePushStream(i2, stream);
                                DefaultHttp2ConnectionDecoder.this.g.onPushPromiseRead(channelHandlerContext, i, i2, http2Headers, i3);
                                return;
                            } else {
                                throw Http2Exception.streamError(i2, Http2Error.PROTOCOL_ERROR, "Promised request on stream %d for promised stream %d is not known to be safe", Integer.valueOf(i), Integer.valueOf(i2));
                            }
                        default:
                            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d in unexpected state for receiving push promise: %s", Integer.valueOf(stream.id()), stream.state());
                    }
                } else {
                    throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d does not exist", Integer.valueOf(i));
                }
            }
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onGoAwayRead(ChannelHandlerContext channelHandlerContext, int i, long j, ByteBuf byteBuf) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.this.a(channelHandlerContext, i, j, byteBuf);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onWindowUpdateRead(ChannelHandlerContext channelHandlerContext, int i, int i2) throws Http2Exception {
            Http2Stream stream = DefaultHttp2ConnectionDecoder.this.c.stream(i);
            if (stream == null || stream.state() == Http2Stream.State.CLOSED || a(i)) {
                b(i);
                return;
            }
            DefaultHttp2ConnectionDecoder.this.e.flowController().incrementWindowSize(stream, i2);
            DefaultHttp2ConnectionDecoder.this.g.onWindowUpdateRead(channelHandlerContext, i, i2);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onUnknownFrame(ChannelHandlerContext channelHandlerContext, byte b, int i, Http2Flags http2Flags, ByteBuf byteBuf) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.this.a(channelHandlerContext, b, i, http2Flags, byteBuf);
        }

        private boolean a(ChannelHandlerContext channelHandlerContext, int i, Http2Stream http2Stream, String str) throws Http2Exception {
            String str2;
            if (http2Stream == null) {
                if (a(i)) {
                    DefaultHttp2ConnectionDecoder.a.info("{} ignoring {} frame for stream {}. Stream sent after GOAWAY sent", channelHandlerContext.channel(), str, Integer.valueOf(i));
                    return true;
                }
                throw Http2Exception.streamError(i, Http2Error.STREAM_CLOSED, "Received %s frame for an unknown stream %d", str, Integer.valueOf(i));
            } else if (!http2Stream.isResetSent() && !a(i)) {
                return false;
            } else {
                if (DefaultHttp2ConnectionDecoder.a.isInfoEnabled()) {
                    InternalLogger internalLogger = DefaultHttp2ConnectionDecoder.a;
                    Object[] objArr = new Object[3];
                    objArr[0] = channelHandlerContext.channel();
                    objArr[1] = str;
                    if (http2Stream.isResetSent()) {
                        str2 = "RST_STREAM sent.";
                    } else {
                        str2 = "Stream created after GOAWAY sent. Last known stream by peer " + DefaultHttp2ConnectionDecoder.this.c.remote().lastStreamKnownByPeer();
                    }
                    objArr[2] = str2;
                    internalLogger.info("{} ignoring {} frame for stream {} {}", objArr);
                }
                return true;
            }
        }

        private boolean a(int i) {
            Http2Connection.Endpoint<Http2RemoteFlowController> remote = DefaultHttp2ConnectionDecoder.this.c.remote();
            return DefaultHttp2ConnectionDecoder.this.c.goAwaySent() && remote.isValidStreamId(i) && i > remote.lastStreamKnownByPeer();
        }

        private void b(int i) throws Http2Exception {
            if (!DefaultHttp2ConnectionDecoder.this.c.streamMayHaveExisted(i)) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d does not exist", Integer.valueOf(i));
            }
        }
    }

    /* loaded from: classes4.dex */
    private final class b implements Http2FrameListener {
        private b() {
        }

        private void a() throws Http2Exception {
            if (!DefaultHttp2ConnectionDecoder.this.prefaceReceived()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Received non-SETTINGS as first frame.", new Object[0]);
            }
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public int onDataRead(ChannelHandlerContext channelHandlerContext, int i, ByteBuf byteBuf, int i2, boolean z) throws Http2Exception {
            a();
            return DefaultHttp2ConnectionDecoder.this.b.onDataRead(channelHandlerContext, i, byteBuf, i2, z);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, boolean z) throws Http2Exception {
            a();
            DefaultHttp2ConnectionDecoder.this.b.onHeadersRead(channelHandlerContext, i, http2Headers, i2, z);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, short s, boolean z, int i3, boolean z2) throws Http2Exception {
            a();
            DefaultHttp2ConnectionDecoder.this.b.onHeadersRead(channelHandlerContext, i, http2Headers, i2, s, z, i3, z2);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onPriorityRead(ChannelHandlerContext channelHandlerContext, int i, int i2, short s, boolean z) throws Http2Exception {
            a();
            DefaultHttp2ConnectionDecoder.this.b.onPriorityRead(channelHandlerContext, i, i2, s, z);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onRstStreamRead(ChannelHandlerContext channelHandlerContext, int i, long j) throws Http2Exception {
            a();
            DefaultHttp2ConnectionDecoder.this.b.onRstStreamRead(channelHandlerContext, i, j);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onSettingsAckRead(ChannelHandlerContext channelHandlerContext) throws Http2Exception {
            a();
            DefaultHttp2ConnectionDecoder.this.b.onSettingsAckRead(channelHandlerContext);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onSettingsRead(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings) throws Http2Exception {
            if (!DefaultHttp2ConnectionDecoder.this.prefaceReceived()) {
                DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder = DefaultHttp2ConnectionDecoder.this;
                defaultHttp2ConnectionDecoder.b = new a();
            }
            DefaultHttp2ConnectionDecoder.this.b.onSettingsRead(channelHandlerContext, http2Settings);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onPingRead(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Http2Exception {
            a();
            DefaultHttp2ConnectionDecoder.this.b.onPingRead(channelHandlerContext, byteBuf);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onPingAckRead(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Http2Exception {
            a();
            DefaultHttp2ConnectionDecoder.this.b.onPingAckRead(channelHandlerContext, byteBuf);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onPushPromiseRead(ChannelHandlerContext channelHandlerContext, int i, int i2, Http2Headers http2Headers, int i3) throws Http2Exception {
            a();
            DefaultHttp2ConnectionDecoder.this.b.onPushPromiseRead(channelHandlerContext, i, i2, http2Headers, i3);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onGoAwayRead(ChannelHandlerContext channelHandlerContext, int i, long j, ByteBuf byteBuf) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.this.a(channelHandlerContext, i, j, byteBuf);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onWindowUpdateRead(ChannelHandlerContext channelHandlerContext, int i, int i2) throws Http2Exception {
            a();
            DefaultHttp2ConnectionDecoder.this.b.onWindowUpdateRead(channelHandlerContext, i, i2);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameListener
        public void onUnknownFrame(ChannelHandlerContext channelHandlerContext, byte b, int i, Http2Flags http2Flags, ByteBuf byteBuf) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.this.a(channelHandlerContext, b, i, http2Flags, byteBuf);
        }
    }
}
