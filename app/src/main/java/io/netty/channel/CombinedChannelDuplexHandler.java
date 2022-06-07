package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public class CombinedChannelDuplexHandler<I extends ChannelInboundHandler, O extends ChannelOutboundHandler> extends ChannelDuplexHandler {
    static final /* synthetic */ boolean a = !CombinedChannelDuplexHandler.class.desiredAssertionStatus();
    private static final InternalLogger b = InternalLoggerFactory.getInstance(CombinedChannelDuplexHandler.class);
    private a c;
    private a d;
    private volatile boolean e;
    private I f;
    private O g;

    /* JADX INFO: Access modifiers changed from: protected */
    public CombinedChannelDuplexHandler() {
    }

    public CombinedChannelDuplexHandler(I i, O o) {
        init(i, o);
    }

    protected final void init(I i, O o) {
        a(i, o);
        this.f = i;
        this.g = o;
    }

    private void a(I i, O o) {
        if (this.f != null) {
            throw new IllegalStateException("init() can not be invoked if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with non-default constructor.");
        } else if (i == null) {
            throw new NullPointerException("inboundHandler");
        } else if (o == null) {
            throw new NullPointerException("outboundHandler");
        } else if (i instanceof ChannelOutboundHandler) {
            throw new IllegalArgumentException("inboundHandler must not implement " + ChannelOutboundHandler.class.getSimpleName() + " to get combined.");
        } else if (o instanceof ChannelInboundHandler) {
            throw new IllegalArgumentException("outboundHandler must not implement " + ChannelInboundHandler.class.getSimpleName() + " to get combined.");
        }
    }

    protected final I inboundHandler() {
        return this.f;
    }

    protected final O outboundHandler() {
        return this.g;
    }

    private void b() {
        if (!this.e) {
            throw new IllegalStateException("handler not added to pipeline yet");
        }
    }

    public final void removeInboundHandler() {
        b();
        this.c.a();
    }

    public final void removeOutboundHandler() {
        b();
        this.d.a();
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.f != null) {
            this.d = new a(channelHandlerContext, this.g);
            this.c = new a(channelHandlerContext, this.f) { // from class: io.netty.channel.CombinedChannelDuplexHandler.1
                @Override // io.netty.channel.CombinedChannelDuplexHandler.a, io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
                public ChannelHandlerContext fireExceptionCaught(Throwable th) {
                    if (!CombinedChannelDuplexHandler.this.d.b) {
                        try {
                            CombinedChannelDuplexHandler.this.g.exceptionCaught(CombinedChannelDuplexHandler.this.d, th);
                        } catch (Throwable th2) {
                            if (CombinedChannelDuplexHandler.b.isWarnEnabled()) {
                                CombinedChannelDuplexHandler.b.warn("An exception was thrown by a user handler's exceptionCaught() method while handling the following exception:", th2);
                            }
                        }
                    } else {
                        super.fireExceptionCaught(th);
                    }
                    return this;
                }
            };
            this.e = true;
            try {
                this.f.handlerAdded(this.c);
            } finally {
                this.g.handlerAdded(this.d);
            }
        } else {
            throw new IllegalStateException("init() must be invoked before being added to a " + ChannelPipeline.class.getSimpleName() + " if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with the default constructor.");
        }
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            this.c.a();
        } finally {
            this.d.a();
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!a && channelHandlerContext != this.c.a) {
            throw new AssertionError();
        } else if (!this.c.b) {
            this.f.channelRegistered(this.c);
        } else {
            this.c.fireChannelRegistered();
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!a && channelHandlerContext != this.c.a) {
            throw new AssertionError();
        } else if (!this.c.b) {
            this.f.channelUnregistered(this.c);
        } else {
            this.c.fireChannelUnregistered();
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!a && channelHandlerContext != this.c.a) {
            throw new AssertionError();
        } else if (!this.c.b) {
            this.f.channelActive(this.c);
        } else {
            this.c.fireChannelActive();
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!a && channelHandlerContext != this.c.a) {
            throw new AssertionError();
        } else if (!this.c.b) {
            this.f.channelInactive(this.c);
        } else {
            this.c.fireChannelInactive();
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        if (!a && channelHandlerContext != this.c.a) {
            throw new AssertionError();
        } else if (!this.c.b) {
            this.f.exceptionCaught(this.c, th);
        } else {
            this.c.fireExceptionCaught(th);
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (!a && channelHandlerContext != this.c.a) {
            throw new AssertionError();
        } else if (!this.c.b) {
            this.f.userEventTriggered(this.c, obj);
        } else {
            this.c.fireUserEventTriggered(obj);
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (!a && channelHandlerContext != this.c.a) {
            throw new AssertionError();
        } else if (!this.c.b) {
            this.f.channelRead(this.c, obj);
        } else {
            this.c.fireChannelRead(obj);
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!a && channelHandlerContext != this.c.a) {
            throw new AssertionError();
        } else if (!this.c.b) {
            this.f.channelReadComplete(this.c);
        } else {
            this.c.fireChannelReadComplete();
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!a && channelHandlerContext != this.c.a) {
            throw new AssertionError();
        } else if (!this.c.b) {
            this.f.channelWritabilityChanged(this.c);
        } else {
            this.c.fireChannelWritabilityChanged();
        }
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        if (!a && channelHandlerContext != this.d.a) {
            throw new AssertionError();
        } else if (!this.d.b) {
            this.g.bind(this.d, socketAddress, channelPromise);
        } else {
            this.d.bind(socketAddress, channelPromise);
        }
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        if (!a && channelHandlerContext != this.d.a) {
            throw new AssertionError();
        } else if (!this.d.b) {
            this.g.connect(this.d, socketAddress, socketAddress2, channelPromise);
        } else {
            this.d.connect(socketAddress2, channelPromise);
        }
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (!a && channelHandlerContext != this.d.a) {
            throw new AssertionError();
        } else if (!this.d.b) {
            this.g.disconnect(this.d, channelPromise);
        } else {
            this.d.disconnect(channelPromise);
        }
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (!a && channelHandlerContext != this.d.a) {
            throw new AssertionError();
        } else if (!this.d.b) {
            this.g.close(this.d, channelPromise);
        } else {
            this.d.close(channelPromise);
        }
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (!a && channelHandlerContext != this.d.a) {
            throw new AssertionError();
        } else if (!this.d.b) {
            this.g.deregister(this.d, channelPromise);
        } else {
            this.d.deregister(channelPromise);
        }
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!a && channelHandlerContext != this.d.a) {
            throw new AssertionError();
        } else if (!this.d.b) {
            this.g.read(this.d);
        } else {
            this.d.read();
        }
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        if (!a && channelHandlerContext != this.d.a) {
            throw new AssertionError();
        } else if (!this.d.b) {
            this.g.write(this.d, obj, channelPromise);
        } else {
            this.d.write(obj, channelPromise);
        }
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!a && channelHandlerContext != this.d.a) {
            throw new AssertionError();
        } else if (!this.d.b) {
            this.g.flush(this.d);
        } else {
            this.d.flush();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class a implements ChannelHandlerContext {
        private final ChannelHandlerContext a;
        boolean b;
        private final ChannelHandler c;

        a(ChannelHandlerContext channelHandlerContext, ChannelHandler channelHandler) {
            this.a = channelHandlerContext;
            this.c = channelHandler;
        }

        @Override // io.netty.channel.ChannelHandlerContext
        public Channel channel() {
            return this.a.channel();
        }

        @Override // io.netty.channel.ChannelHandlerContext
        public EventExecutor executor() {
            return this.a.executor();
        }

        @Override // io.netty.channel.ChannelHandlerContext
        public String name() {
            return this.a.name();
        }

        @Override // io.netty.channel.ChannelHandlerContext
        public ChannelHandler handler() {
            return this.a.handler();
        }

        @Override // io.netty.channel.ChannelHandlerContext
        public boolean isRemoved() {
            return this.b || this.a.isRemoved();
        }

        @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
        public ChannelHandlerContext fireChannelRegistered() {
            this.a.fireChannelRegistered();
            return this;
        }

        @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
        public ChannelHandlerContext fireChannelUnregistered() {
            this.a.fireChannelUnregistered();
            return this;
        }

        @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
        public ChannelHandlerContext fireChannelActive() {
            this.a.fireChannelActive();
            return this;
        }

        @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
        public ChannelHandlerContext fireChannelInactive() {
            this.a.fireChannelInactive();
            return this;
        }

        @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
        public ChannelHandlerContext fireExceptionCaught(Throwable th) {
            this.a.fireExceptionCaught(th);
            return this;
        }

        @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
        public ChannelHandlerContext fireUserEventTriggered(Object obj) {
            this.a.fireUserEventTriggered(obj);
            return this;
        }

        @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
        public ChannelHandlerContext fireChannelRead(Object obj) {
            this.a.fireChannelRead(obj);
            return this;
        }

        @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
        public ChannelHandlerContext fireChannelReadComplete() {
            this.a.fireChannelReadComplete();
            return this;
        }

        @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
        public ChannelHandlerContext fireChannelWritabilityChanged() {
            this.a.fireChannelWritabilityChanged();
            return this;
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture bind(SocketAddress socketAddress) {
            return this.a.bind(socketAddress);
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture connect(SocketAddress socketAddress) {
            return this.a.connect(socketAddress);
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
            return this.a.connect(socketAddress, socketAddress2);
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture disconnect() {
            return this.a.disconnect();
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture close() {
            return this.a.close();
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture deregister() {
            return this.a.deregister();
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
            return this.a.bind(socketAddress, channelPromise);
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
            return this.a.connect(socketAddress, channelPromise);
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            return this.a.connect(socketAddress, socketAddress2, channelPromise);
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture disconnect(ChannelPromise channelPromise) {
            return this.a.disconnect(channelPromise);
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture close(ChannelPromise channelPromise) {
            return this.a.close(channelPromise);
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture deregister(ChannelPromise channelPromise) {
            return this.a.deregister(channelPromise);
        }

        @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelOutboundInvoker
        public ChannelHandlerContext read() {
            this.a.read();
            return this;
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture write(Object obj) {
            return this.a.write(obj);
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture write(Object obj, ChannelPromise channelPromise) {
            return this.a.write(obj, channelPromise);
        }

        @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelOutboundInvoker
        public ChannelHandlerContext flush() {
            this.a.flush();
            return this;
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture writeAndFlush(Object obj, ChannelPromise channelPromise) {
            return this.a.writeAndFlush(obj, channelPromise);
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture writeAndFlush(Object obj) {
            return this.a.writeAndFlush(obj);
        }

        @Override // io.netty.channel.ChannelHandlerContext
        public ChannelPipeline pipeline() {
            return this.a.pipeline();
        }

        @Override // io.netty.channel.ChannelHandlerContext
        public ByteBufAllocator alloc() {
            return this.a.alloc();
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelPromise newPromise() {
            return this.a.newPromise();
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelProgressivePromise newProgressivePromise() {
            return this.a.newProgressivePromise();
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture newSucceededFuture() {
            return this.a.newSucceededFuture();
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelFuture newFailedFuture(Throwable th) {
            return this.a.newFailedFuture(th);
        }

        @Override // io.netty.channel.ChannelOutboundInvoker
        public ChannelPromise voidPromise() {
            return this.a.voidPromise();
        }

        @Override // io.netty.channel.ChannelHandlerContext, io.netty.util.AttributeMap
        public <T> Attribute<T> attr(AttributeKey<T> attributeKey) {
            return this.a.attr(attributeKey);
        }

        @Override // io.netty.channel.ChannelHandlerContext, io.netty.util.AttributeMap
        public <T> boolean hasAttr(AttributeKey<T> attributeKey) {
            return this.a.hasAttr(attributeKey);
        }

        final void a() {
            EventExecutor executor = executor();
            if (executor.inEventLoop()) {
                b();
            } else {
                executor.execute(new OneTimeTask() { // from class: io.netty.channel.CombinedChannelDuplexHandler.a.1
                    @Override // java.lang.Runnable
                    public void run() {
                        a.this.b();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b() {
            if (!this.b) {
                this.b = true;
                try {
                    this.c.handlerRemoved(this);
                } catch (Throwable th) {
                    fireExceptionCaught((Throwable) new ChannelPipelineException(this.c.getClass().getName() + ".handlerRemoved() has thrown an exception.", th));
                }
            }
        }
    }
}
