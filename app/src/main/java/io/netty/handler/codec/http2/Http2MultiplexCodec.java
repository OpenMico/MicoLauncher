package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http.HttpServerUpgradeHandler;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.HttpConversionUtil;
import io.netty.handler.logging.LogLevel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.OneTimeTask;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class Http2MultiplexCodec extends ChannelDuplexHandler {
    private static final Http2FrameLogger a = new Http2FrameLogger(LogLevel.INFO, Http2MultiplexCodec.class);
    private final ChannelHandler b;
    private final EventLoopGroup c;
    private final Http2ConnectionHandler d;
    private final Http2Connection.PropertyKey e;
    private final List<e> f;
    private ChannelHandlerContext g;
    private ChannelHandlerContext h;
    private volatile Runnable i;

    public Http2MultiplexCodec(boolean z, ChannelHandler channelHandler) {
        this(z, channelHandler, null);
    }

    public Http2MultiplexCodec(boolean z, ChannelHandler channelHandler, EventLoopGroup eventLoopGroup) {
        this(z, channelHandler, eventLoopGroup, new DefaultHttp2FrameWriter());
    }

    Http2MultiplexCodec(boolean z, ChannelHandler channelHandler, EventLoopGroup eventLoopGroup, Http2FrameWriter http2FrameWriter) {
        this.f = new ArrayList();
        if (channelHandler.getClass().isAnnotationPresent(ChannelHandler.Sharable.class)) {
            this.b = channelHandler;
            this.c = eventLoopGroup;
            DefaultHttp2Connection defaultHttp2Connection = new DefaultHttp2Connection(z);
            DefaultHttp2ConnectionEncoder defaultHttp2ConnectionEncoder = new DefaultHttp2ConnectionEncoder(defaultHttp2Connection, new Http2OutboundFrameLogger(http2FrameWriter, a));
            DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder = new DefaultHttp2ConnectionDecoder(defaultHttp2Connection, defaultHttp2ConnectionEncoder, new Http2InboundFrameLogger(new DefaultHttp2FrameReader(), a));
            defaultHttp2ConnectionDecoder.frameListener(new b());
            this.d = new d(defaultHttp2ConnectionDecoder, defaultHttp2ConnectionEncoder, new Http2Settings());
            this.d.connection().addListener(new a());
            this.e = this.d.connection().newKey();
            return;
        }
        throw new IllegalArgumentException("streamHandler must be Sharable");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Http2ConnectionHandler a() {
        return this.d;
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.g = channelHandlerContext;
        channelHandlerContext.pipeline().addBefore(channelHandlerContext.executor(), channelHandlerContext.name(), null, this.d);
        this.h = channelHandlerContext.pipeline().context(this.d);
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.pipeline().remove(this.d);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (!(obj instanceof HttpServerUpgradeHandler.UpgradeEvent)) {
            super.userEventTriggered(channelHandlerContext, obj);
            return;
        }
        HttpServerUpgradeHandler.UpgradeEvent upgradeEvent = (HttpServerUpgradeHandler.UpgradeEvent) obj;
        channelHandlerContext.fireUserEventTriggered((Object) upgradeEvent.retain());
        try {
            new a().onStreamActive(this.d.connection().stream(1));
            upgradeEvent.upgradeRequest().headers().setInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_ID.text(), 1);
            new InboundHttpToHttp2Adapter(this.d.connection(), this.d.decoder().frameListener()).channelRead(channelHandlerContext, upgradeEvent.upgradeRequest().retain());
        } finally {
            upgradeEvent.release();
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) {
        channelHandlerContext.fireExceptionCaught(th);
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void flush(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.flush();
    }

    void b() {
        EventExecutor executor = this.g.executor();
        if (executor.inEventLoop()) {
            flush(this.g);
            return;
        }
        Runnable runnable = this.i;
        if (runnable == null) {
            runnable = new Runnable() { // from class: io.netty.handler.codec.http2.Http2MultiplexCodec.1
                @Override // java.lang.Runnable
                public void run() {
                    Http2MultiplexCodec http2MultiplexCodec = Http2MultiplexCodec.this;
                    http2MultiplexCodec.flush(http2MultiplexCodec.g);
                }
            };
            this.i = runnable;
        }
        executor.execute(runnable);
    }

    void a(final Object obj, final boolean z) {
        final ChannelPromise newPromise = this.g.newPromise();
        EventExecutor executor = this.g.executor();
        if (executor.inEventLoop()) {
            a(obj, z, newPromise);
            return;
        }
        try {
            executor.execute(new OneTimeTask() { // from class: io.netty.handler.codec.http2.Http2MultiplexCodec.2
                @Override // java.lang.Runnable
                public void run() {
                    Http2MultiplexCodec.this.a(obj, z, newPromise);
                }
            });
        } catch (Throwable th) {
            newPromise.setFailure(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Object obj, boolean z, ChannelPromise channelPromise) {
        try {
            write(this.g, obj, channelPromise);
        } catch (Throwable th) {
            channelPromise.tryFailure(th);
        }
        if (z) {
            flush(this.g);
        }
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) {
        if (!(obj instanceof Http2Frame)) {
            channelHandlerContext.write(obj, channelPromise);
            return;
        }
        try {
            if (obj instanceof Http2StreamFrame) {
                int id = ((c) ((Http2StreamFrame) obj).stream()).g.id();
                if (obj instanceof Http2DataFrame) {
                    Http2DataFrame http2DataFrame = (Http2DataFrame) obj;
                    this.d.encoder().writeData(this.h, id, http2DataFrame.content().retain(), http2DataFrame.padding(), http2DataFrame.isEndStream(), channelPromise);
                } else if (obj instanceof Http2HeadersFrame) {
                    Http2HeadersFrame http2HeadersFrame = (Http2HeadersFrame) obj;
                    this.d.encoder().writeHeaders(this.h, id, http2HeadersFrame.headers(), http2HeadersFrame.padding(), http2HeadersFrame.isEndStream(), channelPromise);
                } else if (obj instanceof Http2ResetFrame) {
                    this.d.resetStream(this.h, id, ((Http2ResetFrame) obj).errorCode(), channelPromise);
                } else {
                    throw new UnsupportedMessageTypeException(obj, new Class[0]);
                }
            } else if (obj instanceof Http2GoAwayFrame) {
                Http2GoAwayFrame http2GoAwayFrame = (Http2GoAwayFrame) obj;
                this.d.goAway(this.h, this.d.connection().remote().lastStreamCreated() + (http2GoAwayFrame.extraStreamIds() * 2), http2GoAwayFrame.errorCode(), http2GoAwayFrame.content().retain(), channelPromise);
            } else {
                throw new UnsupportedMessageTypeException(obj, new Class[0]);
            }
        } finally {
            ReferenceCountUtil.release(obj);
        }
    }

    ChannelFuture a(ChannelHandlerContext channelHandlerContext, Http2Stream http2Stream, ChannelHandler channelHandler) {
        EventLoopGroup eventLoopGroup = this.c;
        if (eventLoopGroup == null) {
            eventLoopGroup = channelHandlerContext.channel().eventLoop();
        }
        c cVar = new c(http2Stream);
        cVar.pipeline().addLast(channelHandler);
        ChannelFuture register = eventLoopGroup.register(cVar);
        if (register.cause() != null) {
            if (cVar.isRegistered()) {
                cVar.close();
            } else {
                cVar.unsafe().closeForcibly();
            }
        }
        return register;
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
        for (int i = 0; i < this.f.size(); i++) {
            e eVar = this.f.get(i);
            eVar.b = false;
            eVar.a.d();
        }
        this.f.clear();
    }

    void a(e eVar, Object obj) {
        eVar.a.b(obj);
        if (!eVar.b) {
            this.f.add(eVar);
            eVar.b = true;
        }
    }

    /* loaded from: classes4.dex */
    final class a extends Http2ConnectionAdapter {
        a() {
        }

        @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
        public void onStreamActive(Http2Stream http2Stream) {
            if (Http2MultiplexCodec.this.g != null && http2Stream.getProperty(Http2MultiplexCodec.this.e) == null) {
                Http2MultiplexCodec http2MultiplexCodec = Http2MultiplexCodec.this;
                http2Stream.setProperty(Http2MultiplexCodec.this.e, new e((c) http2MultiplexCodec.a(http2MultiplexCodec.g, http2Stream, Http2MultiplexCodec.this.b).channel()));
            }
        }

        @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
        public void onStreamClosed(Http2Stream http2Stream) {
            final e eVar = (e) http2Stream.getProperty(Http2MultiplexCodec.this.e);
            if (eVar != null) {
                EventLoop eventLoop = eVar.a.eventLoop();
                if (eventLoop.inEventLoop()) {
                    a(eVar);
                } else {
                    eventLoop.execute(new OneTimeTask() { // from class: io.netty.handler.codec.http2.Http2MultiplexCodec.a.1
                        @Override // java.lang.Runnable
                        public void run() {
                            a.this.a(eVar);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(e eVar) {
            eVar.a.e = true;
            eVar.a.b(a.c);
        }

        @Override // io.netty.handler.codec.http2.Http2ConnectionAdapter, io.netty.handler.codec.http2.Http2Connection.Listener
        public void onGoAwayReceived(final int i, long j, ByteBuf byteBuf) {
            final DefaultHttp2GoAwayFrame defaultHttp2GoAwayFrame = new DefaultHttp2GoAwayFrame(j, byteBuf);
            try {
                Http2MultiplexCodec.this.d.connection().forEachActiveStream(new Http2StreamVisitor() { // from class: io.netty.handler.codec.http2.Http2MultiplexCodec.a.2
                    @Override // io.netty.handler.codec.http2.Http2StreamVisitor
                    public boolean visit(Http2Stream http2Stream) {
                        if (http2Stream.id() <= i || !Http2MultiplexCodec.this.d.connection().local().isValidStreamId(http2Stream.id())) {
                            return true;
                        }
                        ((e) http2Stream.getProperty(Http2MultiplexCodec.this.e)).a.pipeline().fireUserEventTriggered((Object) defaultHttp2GoAwayFrame.retainedDuplicate());
                        return true;
                    }
                });
            } catch (Throwable th) {
                EventExecutor executor = Http2MultiplexCodec.this.g.executor();
                if (executor.inEventLoop()) {
                    Http2MultiplexCodec http2MultiplexCodec = Http2MultiplexCodec.this;
                    http2MultiplexCodec.exceptionCaught(http2MultiplexCodec.g, th);
                } else {
                    executor.execute(new OneTimeTask() { // from class: io.netty.handler.codec.http2.Http2MultiplexCodec.a.3
                        @Override // java.lang.Runnable
                        public void run() {
                            Http2MultiplexCodec.this.exceptionCaught(Http2MultiplexCodec.this.g, th);
                        }
                    });
                }
            }
            Http2MultiplexCodec.this.g.fireUserEventTriggered((Object) defaultHttp2GoAwayFrame.retainedDuplicate());
        }
    }

    /* loaded from: classes4.dex */
    class d extends Http2ConnectionHandler {
        d(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder, Http2Settings http2Settings) {
            super(http2ConnectionDecoder, http2ConnectionEncoder, http2Settings);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.netty.handler.codec.http2.Http2ConnectionHandler
        public void onStreamError(ChannelHandlerContext channelHandlerContext, Throwable th, Http2Exception.StreamException streamException) {
            try {
                Http2Stream stream = Http2MultiplexCodec.this.d.connection().stream(streamException.streamId());
                if (stream != null) {
                    e eVar = (e) stream.getProperty(Http2MultiplexCodec.this.e);
                    if (eVar != null) {
                        eVar.a.pipeline().fireExceptionCaught((Throwable) streamException);
                    }
                }
            } finally {
                super.onStreamError(channelHandlerContext, th, streamException);
            }
        }
    }

    /* loaded from: classes4.dex */
    class b extends Http2FrameAdapter {
        b() {
        }

        @Override // io.netty.handler.codec.http2.Http2FrameAdapter, io.netty.handler.codec.http2.Http2FrameListener
        public void onRstStreamRead(ChannelHandlerContext channelHandlerContext, int i, long j) throws Http2Exception {
            ((e) Http2MultiplexCodec.this.d.connection().stream(i).getProperty(Http2MultiplexCodec.this.e)).a.pipeline().fireUserEventTriggered((Object) new DefaultHttp2ResetFrame(j));
        }

        @Override // io.netty.handler.codec.http2.Http2FrameAdapter, io.netty.handler.codec.http2.Http2FrameListener
        public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, short s, boolean z, int i3, boolean z2) throws Http2Exception {
            onHeadersRead(channelHandlerContext, i, http2Headers, i3, z2);
        }

        @Override // io.netty.handler.codec.http2.Http2FrameAdapter, io.netty.handler.codec.http2.Http2FrameListener
        public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int i, Http2Headers http2Headers, int i2, boolean z) throws Http2Exception {
            Http2MultiplexCodec.this.a((e) Http2MultiplexCodec.this.d.connection().stream(i).getProperty(Http2MultiplexCodec.this.e), new DefaultHttp2HeadersFrame(http2Headers, z, i2));
        }

        @Override // io.netty.handler.codec.http2.Http2FrameAdapter, io.netty.handler.codec.http2.Http2FrameListener
        public int onDataRead(ChannelHandlerContext channelHandlerContext, int i, ByteBuf byteBuf, int i2, boolean z) throws Http2Exception {
            Http2MultiplexCodec.this.a((e) Http2MultiplexCodec.this.d.connection().stream(i).getProperty(Http2MultiplexCodec.this.e), new DefaultHttp2DataFrame(byteBuf.retain(), z, i2));
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class e {
        final c a;
        boolean b;

        e(c cVar) {
            this.a = cVar;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public final class c extends a {
        boolean e;
        private final Http2Stream g;

        c(Http2Stream http2Stream) {
            super(Http2MultiplexCodec.this.g.channel());
            this.g = http2Stream;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // io.netty.handler.codec.http2.a, io.netty.channel.AbstractChannel
        public void doClose() throws Exception {
            if (!this.e) {
                Http2MultiplexCodec.this.a((Object) new DefaultHttp2ResetFrame(Http2Error.CANCEL).setStream((Object) this), true);
            }
            super.doClose();
        }

        @Override // io.netty.handler.codec.http2.a
        protected void a(Object obj) {
            if (obj instanceof Http2StreamFrame) {
                Http2StreamFrame http2StreamFrame = (Http2StreamFrame) obj;
                if (http2StreamFrame.stream() == null) {
                    http2StreamFrame.setStream(this);
                    Http2MultiplexCodec.this.a(obj, false);
                    return;
                }
                ReferenceCountUtil.release(http2StreamFrame);
                throw new IllegalArgumentException("Stream must be null on the frame");
            }
            ReferenceCountUtil.release(obj);
            throw new IllegalArgumentException("Message must be an Http2StreamFrame: " + obj);
        }

        @Override // io.netty.handler.codec.http2.a
        protected void b() {
            Http2MultiplexCodec.this.b();
        }

        @Override // io.netty.handler.codec.http2.a
        protected EventExecutor c() {
            return Http2MultiplexCodec.this.g.executor();
        }

        @Override // io.netty.handler.codec.http2.a
        protected void a(final int i) {
            EventExecutor executor = Http2MultiplexCodec.this.g.executor();
            if (executor.inEventLoop()) {
                b(i);
            } else {
                executor.execute(new OneTimeTask() { // from class: io.netty.handler.codec.http2.Http2MultiplexCodec.c.1
                    @Override // java.lang.Runnable
                    public void run() {
                        c.this.b(i);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i) {
            try {
                Http2MultiplexCodec.this.d.connection().local().flowController().consumeBytes(this.g, i);
            } catch (Throwable th) {
                Http2MultiplexCodec http2MultiplexCodec = Http2MultiplexCodec.this;
                http2MultiplexCodec.exceptionCaught(http2MultiplexCodec.g, th);
            }
        }
    }
}
