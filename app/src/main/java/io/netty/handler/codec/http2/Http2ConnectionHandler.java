package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class Http2ConnectionHandler extends ByteToMessageDecoder implements ChannelOutboundHandler, Http2LifecycleManager {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(Http2ConnectionHandler.class);
    private final Http2ConnectionDecoder c;
    private final Http2ConnectionEncoder d;
    private final Http2Settings e;
    private ChannelFutureListener f;
    private a g;
    private long h;

    /* JADX INFO: Access modifiers changed from: protected */
    public Http2ConnectionHandler(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder, Http2Settings http2Settings) {
        this.e = (Http2Settings) ObjectUtil.checkNotNull(http2Settings, "initialSettings");
        this.c = (Http2ConnectionDecoder) ObjectUtil.checkNotNull(http2ConnectionDecoder, "decoder");
        this.d = (Http2ConnectionEncoder) ObjectUtil.checkNotNull(http2ConnectionEncoder, "encoder");
        if (http2ConnectionEncoder.connection() != http2ConnectionDecoder.connection()) {
            throw new IllegalArgumentException("Encoder and Decoder do not share the same connection object");
        }
    }

    public long gracefulShutdownTimeoutMillis() {
        return this.h;
    }

    public void gracefulShutdownTimeoutMillis(long j) {
        if (j >= 0) {
            this.h = j;
            return;
        }
        throw new IllegalArgumentException("gracefulShutdownTimeoutMillis: " + j + " (expected: >= 0)");
    }

    public Http2Connection connection() {
        return this.d.connection();
    }

    public Http2ConnectionDecoder decoder() {
        return this.c;
    }

    public Http2ConnectionEncoder encoder() {
        return this.d;
    }

    private boolean a() {
        a aVar = this.g;
        return aVar != null && aVar.a();
    }

    public void onHttpClientUpgrade() throws Http2Exception {
        if (connection().isServer()) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Client-side HTTP upgrade requested for a server", new Object[0]);
        } else if (a() || this.c.prefaceReceived()) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "HTTP upgrade must occur before HTTP/2 preface is sent or received", new Object[0]);
        } else {
            connection().local().createStream(1, true);
        }
    }

    public void onHttpServerUpgrade(Http2Settings http2Settings) throws Http2Exception {
        if (!connection().isServer()) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Server-side HTTP upgrade requested for a client", new Object[0]);
        } else if (a() || this.c.prefaceReceived()) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "HTTP upgrade must occur before HTTP/2 preface is sent or received", new Object[0]);
        } else {
            this.d.remoteSettings(http2Settings);
            connection().remote().createStream(1, true);
        }
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void flush(ChannelHandlerContext channelHandlerContext) throws Http2Exception {
        this.d.flowController().writePendingBytes();
        try {
            channelHandlerContext.flush();
        } catch (Throwable th) {
            throw new Http2Exception(Http2Error.INTERNAL_ERROR, "Error flushing", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public abstract class a {
        public void a(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        public abstract void a(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception;

        public boolean a() {
            return true;
        }

        public void b(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        private a() {
        }

        public void c(ChannelHandlerContext channelHandlerContext) throws Exception {
            Http2ConnectionHandler.this.encoder().close();
            Http2ConnectionHandler.this.decoder().close();
            Http2ConnectionHandler.this.connection().close(channelHandlerContext.voidPromise());
        }
    }

    /* loaded from: classes4.dex */
    private final class d extends a {
        private ByteBuf c;
        private boolean d;

        public d(ChannelHandlerContext channelHandlerContext) {
            super();
            this.c = Http2ConnectionHandler.b(Http2ConnectionHandler.this.d.connection());
            d(channelHandlerContext);
        }

        @Override // io.netty.handler.codec.http2.Http2ConnectionHandler.a
        public boolean a() {
            return this.d;
        }

        @Override // io.netty.handler.codec.http2.Http2ConnectionHandler.a
        public void a(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            try {
                if (channelHandlerContext.channel().isActive() && a(byteBuf) && b(byteBuf)) {
                    Http2ConnectionHandler.this.g = new c();
                    Http2ConnectionHandler.this.g.a(channelHandlerContext, byteBuf, list);
                }
            } catch (Throwable th) {
                Http2ConnectionHandler.this.onError(channelHandlerContext, th);
            }
        }

        @Override // io.netty.handler.codec.http2.Http2ConnectionHandler.a
        public void b(ChannelHandlerContext channelHandlerContext) throws Exception {
            d(channelHandlerContext);
        }

        @Override // io.netty.handler.codec.http2.Http2ConnectionHandler.a
        public void c(ChannelHandlerContext channelHandlerContext) throws Exception {
            b();
            super.c(channelHandlerContext);
        }

        @Override // io.netty.handler.codec.http2.Http2ConnectionHandler.a
        public void a(ChannelHandlerContext channelHandlerContext) throws Exception {
            b();
        }

        private void b() {
            ByteBuf byteBuf = this.c;
            if (byteBuf != null) {
                byteBuf.release();
                this.c = null;
            }
        }

        private boolean a(ByteBuf byteBuf) throws Http2Exception {
            ByteBuf byteBuf2 = this.c;
            if (byteBuf2 == null) {
                return true;
            }
            int min = Math.min(byteBuf.readableBytes(), byteBuf2.readableBytes());
            if (min != 0) {
                int readerIndex = byteBuf.readerIndex();
                ByteBuf byteBuf3 = this.c;
                if (ByteBufUtil.equals(byteBuf, readerIndex, byteBuf3, byteBuf3.readerIndex(), min)) {
                    byteBuf.skipBytes(min);
                    this.c.skipBytes(min);
                    if (this.c.isReadable()) {
                        return false;
                    }
                    this.c.release();
                    this.c = null;
                    return true;
                }
            }
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "HTTP/2 client preface string missing or corrupt. Hex dump for received bytes: %s", ByteBufUtil.hexDump(byteBuf, byteBuf.readerIndex(), Math.min(byteBuf.readableBytes(), this.c.readableBytes())));
        }

        private boolean b(ByteBuf byteBuf) throws Http2Exception {
            if (byteBuf.readableBytes() < 5) {
                return false;
            }
            short unsignedByte = byteBuf.getUnsignedByte(byteBuf.readerIndex() + 3);
            short unsignedByte2 = byteBuf.getUnsignedByte(byteBuf.readerIndex() + 4);
            if (unsignedByte == 4 && (unsignedByte2 & 1) == 0) {
                return true;
            }
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "First received frame was not SETTINGS. Hex dump for first 5 bytes: %s", ByteBufUtil.hexDump(byteBuf, byteBuf.readerIndex(), 5));
        }

        private void d(ChannelHandlerContext channelHandlerContext) {
            if (!this.d && channelHandlerContext.channel().isActive()) {
                this.d = true;
                if (!Http2ConnectionHandler.this.connection().isServer()) {
                    channelHandlerContext.write(Http2CodecUtil.connectionPrefaceBuf()).addListener((GenericFutureListener<? extends Future<? super Void>>) ChannelFutureListener.CLOSE_ON_FAILURE);
                }
                Http2ConnectionHandler.this.d.writeSettings(channelHandlerContext, Http2ConnectionHandler.this.e, channelHandlerContext.newPromise()).addListener((GenericFutureListener<? extends Future<? super Void>>) ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }
    }

    /* loaded from: classes4.dex */
    private final class c extends a {
        private c() {
            super();
        }

        @Override // io.netty.handler.codec.http2.Http2ConnectionHandler.a
        public void a(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            try {
                Http2ConnectionHandler.this.c.decodeFrame(channelHandlerContext, byteBuf, list);
            } catch (Throwable th) {
                Http2ConnectionHandler.this.onError(channelHandlerContext, th);
            }
        }
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.d.lifecycleManager(this);
        this.c.lifecycleManager(this);
        this.d.flowController().channelHandlerContext(channelHandlerContext);
        this.c.flowController().channelHandlerContext(channelHandlerContext);
        this.g = new d(channelHandlerContext);
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void handlerRemoved0(ChannelHandlerContext channelHandlerContext) throws Exception {
        a aVar = this.g;
        if (aVar != null) {
            aVar.a(channelHandlerContext);
            this.g = null;
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.g == null) {
            this.g = new d(channelHandlerContext);
        }
        this.g.b(channelHandlerContext);
        super.channelActive(channelHandlerContext);
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder, io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelInactive(channelHandlerContext);
        a aVar = this.g;
        if (aVar != null) {
            aVar.c(channelHandlerContext);
            this.g = null;
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            if (channelHandlerContext.channel().isWritable()) {
                flush(channelHandlerContext);
            }
            this.d.flowController().channelWritabilityChanged();
        } finally {
            super.channelWritabilityChanged(channelHandlerContext);
        }
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        this.g.a(channelHandlerContext, byteBuf, list);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.disconnect(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (!channelHandlerContext.channel().isActive()) {
            channelHandlerContext.close(channelPromise);
            return;
        }
        ChannelFuture write = connection().goAwaySent() ? channelHandlerContext.write(Unpooled.EMPTY_BUFFER) : a(channelHandlerContext, (Http2Exception) null);
        channelHandlerContext.flush();
        a(channelHandlerContext, write, channelPromise);
    }

    private void a(ChannelHandlerContext channelHandlerContext, ChannelFuture channelFuture, ChannelPromise channelPromise) {
        if (isGracefulShutdownComplete()) {
            channelFuture.addListener((GenericFutureListener<? extends Future<? super Void>>) new b(channelHandlerContext, channelPromise));
        } else {
            this.f = new b(channelHandlerContext, channelPromise, this.h, TimeUnit.MILLISECONDS);
        }
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.deregister(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.read();
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.write(obj, channelPromise);
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder, io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            flush(channelHandlerContext);
        } finally {
            super.channelReadComplete(channelHandlerContext);
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        if (Http2CodecUtil.getEmbeddedHttp2Exception(th) != null) {
            onError(channelHandlerContext, th);
        } else {
            super.exceptionCaught(channelHandlerContext, th);
        }
    }

    @Override // io.netty.handler.codec.http2.Http2LifecycleManager
    public void closeStreamLocal(Http2Stream http2Stream, ChannelFuture channelFuture) {
        switch (http2Stream.state()) {
            case HALF_CLOSED_LOCAL:
            case OPEN:
                http2Stream.closeLocalSide();
                return;
            default:
                closeStream(http2Stream, channelFuture);
                return;
        }
    }

    @Override // io.netty.handler.codec.http2.Http2LifecycleManager
    public void closeStreamRemote(Http2Stream http2Stream, ChannelFuture channelFuture) {
        switch (http2Stream.state()) {
            case OPEN:
            case HALF_CLOSED_REMOTE:
                http2Stream.closeRemoteSide();
                return;
            default:
                closeStream(http2Stream, channelFuture);
                return;
        }
    }

    @Override // io.netty.handler.codec.http2.Http2LifecycleManager
    public void closeStream(Http2Stream http2Stream, ChannelFuture channelFuture) {
        http2Stream.close();
        if (channelFuture.isDone()) {
            a(channelFuture);
        } else {
            channelFuture.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.http2.Http2ConnectionHandler.1
                /* renamed from: a */
                public void operationComplete(ChannelFuture channelFuture2) throws Exception {
                    Http2ConnectionHandler.this.a(channelFuture2);
                }
            });
        }
    }

    @Override // io.netty.handler.codec.http2.Http2LifecycleManager
    public void onError(ChannelHandlerContext channelHandlerContext, Throwable th) {
        Http2Exception embeddedHttp2Exception = Http2CodecUtil.getEmbeddedHttp2Exception(th);
        if (Http2Exception.isStreamError(embeddedHttp2Exception)) {
            onStreamError(channelHandlerContext, th, (Http2Exception.StreamException) embeddedHttp2Exception);
        } else if (embeddedHttp2Exception instanceof Http2Exception.CompositeStreamException) {
            Iterator<Http2Exception.StreamException> it = ((Http2Exception.CompositeStreamException) embeddedHttp2Exception).iterator();
            while (it.hasNext()) {
                onStreamError(channelHandlerContext, th, it.next());
            }
        } else {
            onConnectionError(channelHandlerContext, th, embeddedHttp2Exception);
        }
        channelHandlerContext.flush();
    }

    protected boolean isGracefulShutdownComplete() {
        return connection().numActiveStreams() == 0;
    }

    protected void onConnectionError(ChannelHandlerContext channelHandlerContext, Throwable th, Http2Exception http2Exception) {
        if (http2Exception == null) {
            http2Exception = new Http2Exception(Http2Error.INTERNAL_ERROR, th.getMessage(), th);
        }
        ChannelPromise newPromise = channelHandlerContext.newPromise();
        ChannelFuture a2 = a(channelHandlerContext, http2Exception);
        if (AnonymousClass4.b[http2Exception.shutdownHint().ordinal()] != 1) {
            a2.addListener((GenericFutureListener<? extends Future<? super Void>>) new b(channelHandlerContext, newPromise));
        } else {
            a(channelHandlerContext, a2, newPromise);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: io.netty.handler.codec.http2.Http2ConnectionHandler$4  reason: invalid class name */
    /* loaded from: classes4.dex */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] b = new int[Http2Exception.ShutdownHint.values().length];

        static {
            try {
                b[Http2Exception.ShutdownHint.GRACEFUL_SHUTDOWN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            a = new int[Http2Stream.State.values().length];
            try {
                a[Http2Stream.State.HALF_CLOSED_LOCAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[Http2Stream.State.OPEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[Http2Stream.State.HALF_CLOSED_REMOTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onStreamError(ChannelHandlerContext channelHandlerContext, Throwable th, Http2Exception.StreamException streamException) {
        resetStream(channelHandlerContext, streamException.streamId(), streamException.error().code(), channelHandlerContext.newPromise());
    }

    protected Http2FrameWriter frameWriter() {
        return encoder().frameWriter();
    }

    @Override // io.netty.handler.codec.http2.Http2LifecycleManager
    public ChannelFuture resetStream(final ChannelHandlerContext channelHandlerContext, int i, long j, ChannelPromise channelPromise) {
        ChannelFuture channelFuture;
        final Http2Stream stream = connection().stream(i);
        if (stream == null || stream.isResetSent()) {
            return channelPromise.setSuccess();
        }
        if (stream.state() == Http2Stream.State.IDLE) {
            channelFuture = channelPromise.setSuccess();
        } else {
            channelFuture = frameWriter().writeRstStream(channelHandlerContext, i, j, channelPromise);
        }
        stream.resetSent();
        if (channelFuture.isDone()) {
            a(channelHandlerContext, stream, channelFuture);
        } else {
            channelFuture.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.http2.Http2ConnectionHandler.2
                /* renamed from: a */
                public void operationComplete(ChannelFuture channelFuture2) throws Exception {
                    Http2ConnectionHandler.this.a(channelHandlerContext, stream, channelFuture2);
                }
            });
        }
        return channelFuture;
    }

    @Override // io.netty.handler.codec.http2.Http2LifecycleManager
    public ChannelFuture goAway(final ChannelHandlerContext channelHandlerContext, final int i, final long j, final ByteBuf byteBuf, ChannelPromise channelPromise) {
        try {
            Http2Connection connection = connection();
            if (connection.goAwaySent() && i > connection.remote().lastStreamKnownByPeer()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Last stream identifier must not increase between sending multiple GOAWAY frames (was '%d', is '%d').", Integer.valueOf(connection.remote().lastStreamKnownByPeer()), Integer.valueOf(i));
            }
            connection.goAwaySent(i, j, byteBuf);
            byteBuf.retain();
            ChannelFuture writeGoAway = frameWriter().writeGoAway(channelHandlerContext, i, j, byteBuf, channelPromise);
            if (writeGoAway.isDone()) {
                b(channelHandlerContext, i, j, byteBuf, writeGoAway);
            } else {
                writeGoAway.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.codec.http2.Http2ConnectionHandler.3
                    /* renamed from: a */
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        Http2ConnectionHandler.b(channelHandlerContext, i, j, byteBuf, channelFuture);
                    }
                });
            }
            return writeGoAway;
        } catch (Throwable th) {
            byteBuf.release();
            return channelPromise.setFailure(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ChannelFuture channelFuture) {
        if (this.f != null && isGracefulShutdownComplete()) {
            ChannelFutureListener channelFutureListener = this.f;
            this.f = null;
            try {
                channelFutureListener.operationComplete(channelFuture);
            } catch (Exception e) {
                throw new IllegalStateException("Close listener threw an unexpected exception", e);
            }
        }
    }

    private ChannelFuture a(ChannelHandlerContext channelHandlerContext, Http2Exception http2Exception) {
        return goAway(channelHandlerContext, connection().remote().lastStreamCreated(), (http2Exception != null ? http2Exception.error() : Http2Error.NO_ERROR).code(), Http2CodecUtil.toByteBuf(channelHandlerContext, http2Exception), channelHandlerContext.newPromise());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ChannelHandlerContext channelHandlerContext, Http2Stream http2Stream, ChannelFuture channelFuture) {
        if (channelFuture.isSuccess()) {
            closeStream(http2Stream, channelFuture);
        } else {
            onConnectionError(channelHandlerContext, channelFuture.cause(), null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ByteBuf b(Http2Connection http2Connection) {
        if (http2Connection.isServer()) {
            return Http2CodecUtil.connectionPrefaceBuf();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(ChannelHandlerContext channelHandlerContext, int i, long j, ByteBuf byteBuf, ChannelFuture channelFuture) {
        try {
            if (!channelFuture.isSuccess()) {
                if (a.isDebugEnabled()) {
                    a.debug("{} Sending GOAWAY failed: lastStreamId '{}', errorCode '{}', debugData '{}'. Forcing shutdown of the connection.", channelHandlerContext.channel(), Integer.valueOf(i), Long.valueOf(j), byteBuf.toString(CharsetUtil.UTF_8), channelFuture.cause());
                }
                channelHandlerContext.close();
            } else if (j != Http2Error.NO_ERROR.code()) {
                if (a.isDebugEnabled()) {
                    a.debug("{} Sent GOAWAY: lastStreamId '{}', errorCode '{}', debugData '{}'. Forcing shutdown of the connection.", channelHandlerContext.channel(), Integer.valueOf(i), Long.valueOf(j), byteBuf.toString(CharsetUtil.UTF_8), channelFuture.cause());
                }
                channelHandlerContext.close();
            }
        } finally {
            byteBuf.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class b implements ChannelFutureListener {
        private final ChannelHandlerContext a;
        private final ChannelPromise b;
        private final ScheduledFuture<?> c;

        b(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
            this.a = channelHandlerContext;
            this.b = channelPromise;
            this.c = null;
        }

        b(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise, long j, TimeUnit timeUnit) {
            this.a = channelHandlerContext;
            this.b = channelPromise;
            this.c = channelHandlerContext.executor().schedule((Runnable) new OneTimeTask() { // from class: io.netty.handler.codec.http2.Http2ConnectionHandler.b.1
                @Override // java.lang.Runnable
                public void run() {
                    channelHandlerContext.close(channelPromise);
                }
            }, j, timeUnit);
        }

        /* renamed from: a */
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            ScheduledFuture<?> scheduledFuture = this.c;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            this.a.close(this.b);
        }
    }
}
