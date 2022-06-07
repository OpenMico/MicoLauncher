package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.AbstractChannel;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.DefaultAttributeMap;
import io.netty.util.Recycler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ResourceLeakHint;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.RecyclableMpscLinkedQueueNode;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;

/* compiled from: AbstractChannelHandlerContext.java */
/* loaded from: classes4.dex */
public abstract class a extends DefaultAttributeMap implements ChannelHandlerContext, ResourceLeakHint {
    private static final InternalLogger d = InternalLoggerFactory.getInstance(a.class);
    volatile a a;
    volatile a b;
    final EventExecutor c;
    private final boolean e;
    private final boolean f;
    private final DefaultChannelPipeline g;
    private final String h;
    private ChannelFuture i;
    private int j = 0;
    private Runnable k;
    private Runnable l;
    private Runnable m;
    private Runnable n;

    public a(DefaultChannelPipeline defaultChannelPipeline, EventExecutor eventExecutor, String str, boolean z, boolean z2) {
        if (str != null) {
            this.g = defaultChannelPipeline;
            this.h = str;
            this.c = eventExecutor;
            this.e = z;
            this.f = z2;
            return;
        }
        throw new NullPointerException("name");
    }

    @Override // io.netty.channel.ChannelHandlerContext
    public Channel channel() {
        return this.g.channel();
    }

    @Override // io.netty.channel.ChannelHandlerContext
    public ChannelPipeline pipeline() {
        return this.g;
    }

    @Override // io.netty.channel.ChannelHandlerContext
    public ByteBufAllocator alloc() {
        return channel().config().getAllocator();
    }

    @Override // io.netty.channel.ChannelHandlerContext
    public EventExecutor executor() {
        EventExecutor eventExecutor = this.c;
        return eventExecutor == null ? channel().eventLoop() : eventExecutor;
    }

    @Override // io.netty.channel.ChannelHandlerContext
    public String name() {
        return this.h;
    }

    @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
    public ChannelHandlerContext fireChannelRegistered() {
        a(l());
        return this;
    }

    public static void a(a aVar) {
        EventExecutor executor = aVar.executor();
        if (executor.inEventLoop()) {
            aVar.c();
        } else {
            executor.execute(new OneTimeTask() { // from class: io.netty.channel.a.1
                @Override // java.lang.Runnable
                public void run() {
                    a.this.c();
                }
            });
        }
    }

    public void c() {
        if (n()) {
            try {
                ((ChannelInboundHandler) handler()).channelRegistered(this);
            } catch (Throwable th) {
                b(th);
            }
        } else {
            fireChannelRegistered();
        }
    }

    @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
    public ChannelHandlerContext fireChannelUnregistered() {
        b(l());
        return this;
    }

    public static void b(a aVar) {
        EventExecutor executor = aVar.executor();
        if (executor.inEventLoop()) {
            aVar.d();
        } else {
            executor.execute(new OneTimeTask() { // from class: io.netty.channel.a.9
                @Override // java.lang.Runnable
                public void run() {
                    a.this.d();
                }
            });
        }
    }

    public void d() {
        if (n()) {
            try {
                ((ChannelInboundHandler) handler()).channelUnregistered(this);
            } catch (Throwable th) {
                b(th);
            }
        } else {
            fireChannelUnregistered();
        }
    }

    @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
    public ChannelHandlerContext fireChannelActive() {
        c(l());
        return this;
    }

    public static void c(a aVar) {
        EventExecutor executor = aVar.executor();
        if (executor.inEventLoop()) {
            aVar.e();
        } else {
            executor.execute(new OneTimeTask() { // from class: io.netty.channel.a.10
                @Override // java.lang.Runnable
                public void run() {
                    a.this.e();
                }
            });
        }
    }

    public void e() {
        if (n()) {
            try {
                ((ChannelInboundHandler) handler()).channelActive(this);
            } catch (Throwable th) {
                b(th);
            }
        } else {
            fireChannelActive();
        }
    }

    @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
    public ChannelHandlerContext fireChannelInactive() {
        d(l());
        return this;
    }

    public static void d(a aVar) {
        EventExecutor executor = aVar.executor();
        if (executor.inEventLoop()) {
            aVar.f();
        } else {
            executor.execute(new OneTimeTask() { // from class: io.netty.channel.a.11
                @Override // java.lang.Runnable
                public void run() {
                    a.this.f();
                }
            });
        }
    }

    public void f() {
        if (n()) {
            try {
                ((ChannelInboundHandler) handler()).channelInactive(this);
            } catch (Throwable th) {
                b(th);
            }
        } else {
            fireChannelInactive();
        }
    }

    @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
    public ChannelHandlerContext fireExceptionCaught(Throwable th) {
        a(this.a, th);
        return this;
    }

    public static void a(a aVar, final Throwable th) {
        ObjectUtil.checkNotNull(th, "cause");
        EventExecutor executor = aVar.executor();
        if (executor.inEventLoop()) {
            aVar.a(th);
            return;
        }
        try {
            executor.execute(new OneTimeTask() { // from class: io.netty.channel.a.12
                @Override // java.lang.Runnable
                public void run() {
                    a.this.a(th);
                }
            });
        } catch (Throwable th2) {
            if (d.isWarnEnabled()) {
                d.warn("Failed to submit an exceptionCaught() event.", th2);
                d.warn("The exceptionCaught() event that was failed to submit was:", th);
            }
        }
    }

    public void a(Throwable th) {
        if (n()) {
            try {
                handler().exceptionCaught(this, th);
            } catch (Throwable unused) {
                if (d.isWarnEnabled()) {
                    d.warn("An exception was thrown by a user handler's exceptionCaught() method while handling the following exception:", th);
                }
            }
        } else {
            fireExceptionCaught(th);
        }
    }

    @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
    public ChannelHandlerContext fireUserEventTriggered(Object obj) {
        a(l(), obj);
        return this;
    }

    public static void a(a aVar, final Object obj) {
        ObjectUtil.checkNotNull(obj, "event");
        EventExecutor executor = aVar.executor();
        if (executor.inEventLoop()) {
            aVar.a(obj);
        } else {
            executor.execute(new OneTimeTask() { // from class: io.netty.channel.a.13
                @Override // java.lang.Runnable
                public void run() {
                    a.this.a(obj);
                }
            });
        }
    }

    public void a(Object obj) {
        if (n()) {
            try {
                ((ChannelInboundHandler) handler()).userEventTriggered(this, obj);
            } catch (Throwable th) {
                b(th);
            }
        } else {
            fireUserEventTriggered(obj);
        }
    }

    @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
    public ChannelHandlerContext fireChannelRead(Object obj) {
        b(l(), obj);
        return this;
    }

    public static void b(a aVar, Object obj) {
        final Object a = aVar.g.a(ObjectUtil.checkNotNull(obj, "msg"), aVar);
        EventExecutor executor = aVar.executor();
        if (executor.inEventLoop()) {
            aVar.b(a);
        } else {
            executor.execute(new OneTimeTask() { // from class: io.netty.channel.a.14
                @Override // java.lang.Runnable
                public void run() {
                    a.this.b(a);
                }
            });
        }
    }

    public void b(Object obj) {
        if (n()) {
            try {
                ((ChannelInboundHandler) handler()).channelRead(this, obj);
            } catch (Throwable th) {
                b(th);
            }
        } else {
            fireChannelRead(obj);
        }
    }

    @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
    public ChannelHandlerContext fireChannelReadComplete() {
        e(l());
        return this;
    }

    public static void e(a aVar) {
        EventExecutor executor = aVar.executor();
        if (executor.inEventLoop()) {
            aVar.g();
            return;
        }
        Runnable runnable = aVar.k;
        if (runnable == null) {
            runnable = new Runnable() { // from class: io.netty.channel.a.15
                @Override // java.lang.Runnable
                public void run() {
                    a.this.g();
                }
            };
            aVar.k = runnable;
        }
        executor.execute(runnable);
    }

    public void g() {
        if (n()) {
            try {
                ((ChannelInboundHandler) handler()).channelReadComplete(this);
            } catch (Throwable th) {
                b(th);
            }
        } else {
            fireChannelReadComplete();
        }
    }

    @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelInboundInvoker
    public ChannelHandlerContext fireChannelWritabilityChanged() {
        f(l());
        return this;
    }

    public static void f(a aVar) {
        EventExecutor executor = aVar.executor();
        if (executor.inEventLoop()) {
            aVar.h();
            return;
        }
        Runnable runnable = aVar.m;
        if (runnable == null) {
            runnable = new Runnable() { // from class: io.netty.channel.a.16
                @Override // java.lang.Runnable
                public void run() {
                    a.this.h();
                }
            };
            aVar.m = runnable;
        }
        executor.execute(runnable);
    }

    public void h() {
        if (n()) {
            try {
                ((ChannelInboundHandler) handler()).channelWritabilityChanged(this);
            } catch (Throwable th) {
                b(th);
            }
        } else {
            fireChannelWritabilityChanged();
        }
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture bind(SocketAddress socketAddress) {
        return bind(socketAddress, newPromise());
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture connect(SocketAddress socketAddress) {
        return connect(socketAddress, newPromise());
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
        return connect(socketAddress, socketAddress2, newPromise());
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture disconnect() {
        return disconnect(newPromise());
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture close() {
        return close(newPromise());
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture deregister() {
        return deregister(newPromise());
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture bind(final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        if (socketAddress == null) {
            throw new NullPointerException("localAddress");
        } else if (!a(channelPromise, false)) {
            return channelPromise;
        } else {
            final a m = m();
            EventExecutor executor = m.executor();
            if (executor.inEventLoop()) {
                m.a(socketAddress, channelPromise);
            } else {
                a(executor, new OneTimeTask() { // from class: io.netty.channel.a.2
                    @Override // java.lang.Runnable
                    public void run() {
                        m.a(socketAddress, channelPromise);
                    }
                }, channelPromise, (Object) null);
            }
            return channelPromise;
        }
    }

    public void a(SocketAddress socketAddress, ChannelPromise channelPromise) {
        if (n()) {
            try {
                ((ChannelOutboundHandler) handler()).bind(this, socketAddress, channelPromise);
            } catch (Throwable th) {
                a(th, channelPromise);
            }
        } else {
            bind(socketAddress, channelPromise);
        }
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
        return connect(socketAddress, null, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture connect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
        if (socketAddress == null) {
            throw new NullPointerException("remoteAddress");
        } else if (!a(channelPromise, false)) {
            return channelPromise;
        } else {
            final a m = m();
            EventExecutor executor = m.executor();
            if (executor.inEventLoop()) {
                m.a(socketAddress, socketAddress2, channelPromise);
            } else {
                a(executor, new OneTimeTask() { // from class: io.netty.channel.a.3
                    @Override // java.lang.Runnable
                    public void run() {
                        m.a(socketAddress, socketAddress2, channelPromise);
                    }
                }, channelPromise, (Object) null);
            }
            return channelPromise;
        }
    }

    public void a(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        if (n()) {
            try {
                ((ChannelOutboundHandler) handler()).connect(this, socketAddress, socketAddress2, channelPromise);
            } catch (Throwable th) {
                a(th, channelPromise);
            }
        } else {
            connect(socketAddress, socketAddress2, channelPromise);
        }
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture disconnect(final ChannelPromise channelPromise) {
        if (!a(channelPromise, false)) {
            return channelPromise;
        }
        final a m = m();
        EventExecutor executor = m.executor();
        if (!executor.inEventLoop()) {
            a(executor, new OneTimeTask() { // from class: io.netty.channel.a.4
                @Override // java.lang.Runnable
                public void run() {
                    if (!a.this.channel().metadata().hasDisconnect()) {
                        m.b(channelPromise);
                    } else {
                        m.a(channelPromise);
                    }
                }
            }, channelPromise, (Object) null);
        } else if (!channel().metadata().hasDisconnect()) {
            m.b(channelPromise);
        } else {
            m.a(channelPromise);
        }
        return channelPromise;
    }

    public void a(ChannelPromise channelPromise) {
        if (n()) {
            try {
                ((ChannelOutboundHandler) handler()).disconnect(this, channelPromise);
            } catch (Throwable th) {
                a(th, channelPromise);
            }
        } else {
            disconnect(channelPromise);
        }
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture close(final ChannelPromise channelPromise) {
        if (!a(channelPromise, false)) {
            return channelPromise;
        }
        final a m = m();
        EventExecutor executor = m.executor();
        if (executor.inEventLoop()) {
            m.b(channelPromise);
        } else {
            a(executor, new OneTimeTask() { // from class: io.netty.channel.a.5
                @Override // java.lang.Runnable
                public void run() {
                    m.b(channelPromise);
                }
            }, channelPromise, (Object) null);
        }
        return channelPromise;
    }

    public void b(ChannelPromise channelPromise) {
        if (n()) {
            try {
                ((ChannelOutboundHandler) handler()).close(this, channelPromise);
            } catch (Throwable th) {
                a(th, channelPromise);
            }
        } else {
            close(channelPromise);
        }
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture deregister(final ChannelPromise channelPromise) {
        if (!a(channelPromise, false)) {
            return channelPromise;
        }
        final a m = m();
        EventExecutor executor = m.executor();
        if (executor.inEventLoop()) {
            m.c(channelPromise);
        } else {
            a(executor, new OneTimeTask() { // from class: io.netty.channel.a.6
                @Override // java.lang.Runnable
                public void run() {
                    m.c(channelPromise);
                }
            }, channelPromise, (Object) null);
        }
        return channelPromise;
    }

    public void c(ChannelPromise channelPromise) {
        if (n()) {
            try {
                ((ChannelOutboundHandler) handler()).deregister(this, channelPromise);
            } catch (Throwable th) {
                a(th, channelPromise);
            }
        } else {
            deregister(channelPromise);
        }
    }

    @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelOutboundInvoker
    public ChannelHandlerContext read() {
        final a m = m();
        EventExecutor executor = m.executor();
        if (executor.inEventLoop()) {
            m.i();
        } else {
            Runnable runnable = m.l;
            if (runnable == null) {
                runnable = new Runnable() { // from class: io.netty.channel.a.7
                    @Override // java.lang.Runnable
                    public void run() {
                        m.i();
                    }
                };
                m.l = runnable;
            }
            executor.execute(runnable);
        }
        return this;
    }

    public void i() {
        if (n()) {
            try {
                ((ChannelOutboundHandler) handler()).read(this);
            } catch (Throwable th) {
                b(th);
            }
        } else {
            read();
        }
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture write(Object obj) {
        return write(obj, newPromise());
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture write(Object obj, ChannelPromise channelPromise) {
        if (obj != null) {
            try {
                if (!a(channelPromise, true)) {
                    ReferenceCountUtil.release(obj);
                    return channelPromise;
                }
                a(obj, false, channelPromise);
                return channelPromise;
            } catch (RuntimeException e) {
                ReferenceCountUtil.release(obj);
                throw e;
            }
        } else {
            throw new NullPointerException("msg");
        }
    }

    public void a(Object obj, ChannelPromise channelPromise) {
        if (n()) {
            b(obj, channelPromise);
        } else {
            write(obj, channelPromise);
        }
    }

    private void b(Object obj, ChannelPromise channelPromise) {
        try {
            ((ChannelOutboundHandler) handler()).write(this, obj, channelPromise);
        } catch (Throwable th) {
            a(th, channelPromise);
        }
    }

    @Override // io.netty.channel.ChannelHandlerContext, io.netty.channel.ChannelOutboundInvoker
    public ChannelHandlerContext flush() {
        final a m = m();
        EventExecutor executor = m.executor();
        if (executor.inEventLoop()) {
            m.j();
        } else {
            Runnable runnable = m.n;
            if (runnable == null) {
                runnable = new Runnable() { // from class: io.netty.channel.a.8
                    @Override // java.lang.Runnable
                    public void run() {
                        m.j();
                    }
                };
                m.n = runnable;
            }
            a(executor, runnable, channel().voidPromise(), (Object) null);
        }
        return this;
    }

    public void j() {
        if (n()) {
            k();
        } else {
            flush();
        }
    }

    private void k() {
        try {
            ((ChannelOutboundHandler) handler()).flush(this);
        } catch (Throwable th) {
            b(th);
        }
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture writeAndFlush(Object obj, ChannelPromise channelPromise) {
        if (obj == null) {
            throw new NullPointerException("msg");
        } else if (!a(channelPromise, true)) {
            ReferenceCountUtil.release(obj);
            return channelPromise;
        } else {
            a(obj, true, channelPromise);
            return channelPromise;
        }
    }

    private void c(Object obj, ChannelPromise channelPromise) {
        if (n()) {
            b(obj, channelPromise);
            k();
            return;
        }
        writeAndFlush(obj, channelPromise);
    }

    private void a(Object obj, boolean z, ChannelPromise channelPromise) {
        Runnable runnable;
        a m = m();
        Object a = this.g.a(obj, m);
        EventExecutor executor = m.executor();
        if (!executor.inEventLoop()) {
            if (z) {
                runnable = b.c(m, a, channelPromise);
            } else {
                runnable = c.c(m, a, channelPromise);
            }
            a(executor, runnable, channelPromise, a);
        } else if (z) {
            m.c(a, channelPromise);
        } else {
            m.a(a, channelPromise);
        }
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture writeAndFlush(Object obj) {
        return writeAndFlush(obj, newPromise());
    }

    private static void a(Throwable th, ChannelPromise channelPromise) {
        if (!channelPromise.tryFailure(th) && !(channelPromise instanceof g) && d.isWarnEnabled()) {
            d.warn("Failed to fail the promise because it's done already: {}", channelPromise, th);
        }
    }

    private void b(Throwable th) {
        if (!c(th)) {
            a(th);
        } else if (d.isWarnEnabled()) {
            d.warn("An exception was thrown by a user handler while handling an exceptionCaught event", th);
        }
    }

    private static boolean c(Throwable th) {
        do {
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    if (stackTraceElement == null) {
                        break;
                    } else if ("exceptionCaught".equals(stackTraceElement.getMethodName())) {
                        return true;
                    }
                }
            }
            th = th.getCause();
        } while (th != null);
        return false;
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelPromise newPromise() {
        return new DefaultChannelPromise(channel(), executor());
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelProgressivePromise newProgressivePromise() {
        return new DefaultChannelProgressivePromise(channel(), executor());
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture newSucceededFuture() {
        ChannelFuture channelFuture = this.i;
        if (channelFuture != null) {
            return channelFuture;
        }
        f fVar = new f(channel(), executor());
        this.i = fVar;
        return fVar;
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelFuture newFailedFuture(Throwable th) {
        return new e(channel(), executor(), th);
    }

    private boolean a(ChannelPromise channelPromise, boolean z) {
        if (channelPromise == null) {
            throw new NullPointerException("promise");
        } else if (channelPromise.isDone()) {
            if (channelPromise.isCancelled()) {
                return false;
            }
            throw new IllegalArgumentException("promise already done: " + channelPromise);
        } else if (channelPromise.channel() != channel()) {
            throw new IllegalArgumentException(String.format("promise.channel does not match: %s (expected: %s)", channelPromise.channel(), channel()));
        } else if (channelPromise.getClass() == DefaultChannelPromise.class) {
            return true;
        } else {
            if (!z && (channelPromise instanceof g)) {
                throw new IllegalArgumentException(StringUtil.simpleClassName((Class<?>) g.class) + " not allowed for this operation");
            } else if (!(channelPromise instanceof AbstractChannel.a)) {
                return true;
            } else {
                throw new IllegalArgumentException(StringUtil.simpleClassName((Class<?>) AbstractChannel.a.class) + " not allowed in a pipeline");
            }
        }
    }

    private a l() {
        a aVar = this;
        do {
            aVar = aVar.a;
        } while (!aVar.e);
        return aVar;
    }

    private a m() {
        a aVar = this;
        do {
            aVar = aVar.b;
        } while (!aVar.f);
        return aVar;
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public ChannelPromise voidPromise() {
        return channel().voidPromise();
    }

    public final void a() {
        this.j = 2;
    }

    public final void b() {
        this.j = 1;
    }

    private boolean n() {
        return this.j == 1;
    }

    @Override // io.netty.channel.ChannelHandlerContext
    public boolean isRemoved() {
        return this.j == 2;
    }

    @Override // io.netty.util.DefaultAttributeMap, io.netty.util.AttributeMap
    public <T> Attribute<T> attr(AttributeKey<T> attributeKey) {
        return channel().attr(attributeKey);
    }

    @Override // io.netty.util.DefaultAttributeMap, io.netty.util.AttributeMap
    public <T> boolean hasAttr(AttributeKey<T> attributeKey) {
        return channel().hasAttr(attributeKey);
    }

    private static void a(EventExecutor eventExecutor, Runnable runnable, ChannelPromise channelPromise, Object obj) {
        try {
            eventExecutor.execute(runnable);
        } catch (Throwable th) {
            try {
                channelPromise.setFailure(th);
                if (obj != null) {
                    ReferenceCountUtil.release(obj);
                }
            } catch (Throwable th2) {
                if (obj != null) {
                    ReferenceCountUtil.release(obj);
                }
                throw th2;
            }
        }
    }

    @Override // io.netty.util.ResourceLeakHint
    public String toHintString() {
        return '\'' + this.h + "' will handle the message from this point.";
    }

    public String toString() {
        return StringUtil.simpleClassName((Class<?>) ChannelHandlerContext.class) + '(' + this.h + ", " + channel() + ')';
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AbstractChannelHandlerContext.java */
    /* renamed from: io.netty.channel.a$a */
    /* loaded from: classes4.dex */
    public static abstract class AbstractRunnableC0201a extends RecyclableMpscLinkedQueueNode<Runnable> implements Runnable {
        private static final boolean a = SystemPropertyUtil.getBoolean("io.netty.transport.estimateSizeOnSubmit", true);
        private static final int b = SystemPropertyUtil.getInt("io.netty.transport.writeTaskSizeOverhead", 48);
        private a c;
        private Object d;
        private ChannelPromise e;
        private int f;

        /* renamed from: a */
        public Runnable value() {
            return this;
        }

        private AbstractRunnableC0201a(Recycler.Handle<? extends AbstractRunnableC0201a> handle) {
            super(handle);
        }

        protected static void a(AbstractRunnableC0201a aVar, a aVar2, Object obj, ChannelPromise channelPromise) {
            aVar.c = aVar2;
            aVar.d = obj;
            aVar.e = channelPromise;
            if (a) {
                ChannelOutboundBuffer outboundBuffer = aVar2.channel().unsafe().outboundBuffer();
                if (outboundBuffer != null) {
                    aVar.f = aVar2.g.a().size(obj) + b;
                    outboundBuffer.a(aVar.f);
                    return;
                }
                aVar.f = 0;
                return;
            }
            aVar.f = 0;
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [io.netty.channel.ChannelPromise, io.netty.channel.a, java.lang.Object] */
        /* JADX WARN: Unknown variable types count: 1 */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                r4 = this;
                r0 = 0
                io.netty.channel.a r1 = r4.c     // Catch: all -> 0x002b
                io.netty.channel.Channel r1 = r1.channel()     // Catch: all -> 0x002b
                io.netty.channel.Channel$Unsafe r1 = r1.unsafe()     // Catch: all -> 0x002b
                io.netty.channel.ChannelOutboundBuffer r1 = r1.outboundBuffer()     // Catch: all -> 0x002b
                boolean r2 = io.netty.channel.a.AbstractRunnableC0201a.a     // Catch: all -> 0x002b
                if (r2 == 0) goto L_0x001b
                if (r1 == 0) goto L_0x001b
                int r2 = r4.f     // Catch: all -> 0x002b
                long r2 = (long) r2     // Catch: all -> 0x002b
                r1.b(r2)     // Catch: all -> 0x002b
            L_0x001b:
                io.netty.channel.a r1 = r4.c     // Catch: all -> 0x002b
                java.lang.Object r2 = r4.d     // Catch: all -> 0x002b
                io.netty.channel.ChannelPromise r3 = r4.e     // Catch: all -> 0x002b
                r4.a(r1, r2, r3)     // Catch: all -> 0x002b
                r4.c = r0
                r4.d = r0
                r4.e = r0
                return
            L_0x002b:
                r1 = move-exception
                r4.c = r0
                r4.d = r0
                r4.e = r0
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.a.AbstractRunnableC0201a.run():void");
        }

        protected void a(a aVar, Object obj, ChannelPromise channelPromise) {
            aVar.a(obj, channelPromise);
        }
    }

    /* compiled from: AbstractChannelHandlerContext.java */
    /* loaded from: classes4.dex */
    public static final class c extends AbstractRunnableC0201a implements SingleThreadEventLoop.a {
        private static final Recycler<c> a = new Recycler<c>() { // from class: io.netty.channel.a.c.1
            /* renamed from: a */
            public c newObject(Recycler.Handle<c> handle) {
                return new c(handle);
            }
        };

        public static c c(a aVar, Object obj, ChannelPromise channelPromise) {
            c cVar = a.get();
            a(cVar, aVar, obj, channelPromise);
            return cVar;
        }

        private c(Recycler.Handle<c> handle) {
            super(handle);
        }
    }

    /* compiled from: AbstractChannelHandlerContext.java */
    /* loaded from: classes4.dex */
    public static final class b extends AbstractRunnableC0201a {
        private static final Recycler<b> a = new Recycler<b>() { // from class: io.netty.channel.a.b.1
            /* renamed from: a */
            public b newObject(Recycler.Handle<b> handle) {
                return new b(handle);
            }
        };

        public static b c(a aVar, Object obj, ChannelPromise channelPromise) {
            b bVar = a.get();
            a(bVar, aVar, obj, channelPromise);
            return bVar;
        }

        private b(Recycler.Handle<b> handle) {
            super(handle);
        }

        @Override // io.netty.channel.a.AbstractRunnableC0201a
        public void a(a aVar, Object obj, ChannelPromise channelPromise) {
            super.a(aVar, obj, channelPromise);
            aVar.j();
        }
    }
}
