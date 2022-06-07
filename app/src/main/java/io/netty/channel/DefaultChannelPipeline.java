package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.MessageSizeEstimator;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.WeakHashMap;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes4.dex */
public class DefaultChannelPipeline implements ChannelPipeline {
    private final Channel h;
    private final ChannelFuture i;
    private final g j;
    private Map<EventExecutorGroup, EventExecutor> l;
    private MessageSizeEstimator.Handle m;
    private c n;
    private boolean o;
    static final /* synthetic */ boolean d = !DefaultChannelPipeline.class.desiredAssertionStatus();
    static final InternalLogger a = InternalLoggerFactory.getInstance(DefaultChannelPipeline.class);
    private static final String e = a(a.class);
    private static final String f = a(e.class);
    private static final FastThreadLocal<Map<Class<?>, String>> g = new FastThreadLocal<Map<Class<?>, String>>() { // from class: io.netty.channel.DefaultChannelPipeline.1
        /* renamed from: a */
        public Map<Class<?>, String> initialValue() throws Exception {
            return new WeakHashMap();
        }
    };
    private final boolean k = ResourceLeakDetector.isEnabled();
    final a c = new e(this);
    final a b = new a(this);

    public DefaultChannelPipeline(Channel channel) {
        this.h = (Channel) ObjectUtil.checkNotNull(channel, "channel");
        this.i = new f(channel, null);
        this.j = new g(channel, true);
        a aVar = this.b;
        a aVar2 = this.c;
        aVar.a = aVar2;
        aVar2.b = aVar;
    }

    public final MessageSizeEstimator.Handle a() {
        if (this.m == null) {
            this.m = this.h.config().getMessageSizeEstimator().newHandle();
        }
        return this.m;
    }

    public final Object a(Object obj, a aVar) {
        return this.k ? ReferenceCountUtil.touch(obj, aVar) : obj;
    }

    private a a(EventExecutorGroup eventExecutorGroup, String str, ChannelHandler channelHandler) {
        return new c(this, a(eventExecutorGroup), str, channelHandler);
    }

    private EventExecutor a(EventExecutorGroup eventExecutorGroup) {
        if (eventExecutorGroup == null) {
            return null;
        }
        Map map = this.l;
        if (map == null) {
            map = new IdentityHashMap(4);
            this.l = map;
        }
        EventExecutor eventExecutor = (EventExecutor) map.get(eventExecutorGroup);
        if (eventExecutor != null) {
            return eventExecutor;
        }
        EventExecutor next = eventExecutorGroup.next();
        map.put(eventExecutorGroup, next);
        return next;
    }

    @Override // io.netty.channel.ChannelPipeline
    public final Channel channel() {
        return this.h;
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelPipeline addFirst(String str, ChannelHandler channelHandler) {
        return addFirst(null, str, channelHandler);
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelPipeline addFirst(EventExecutorGroup eventExecutorGroup, String str, ChannelHandler channelHandler) {
        synchronized (this) {
            b(channelHandler);
            final a a2 = a(eventExecutorGroup, a(str, channelHandler), channelHandler);
            b(a2);
            if (!this.o) {
                b(a2, true);
                return this;
            }
            EventExecutor executor = a2.executor();
            if (!executor.inEventLoop()) {
                executor.execute(new OneTimeTask() { // from class: io.netty.channel.DefaultChannelPipeline.2
                    @Override // java.lang.Runnable
                    public void run() {
                        DefaultChannelPipeline.this.f(a2);
                    }
                });
                return this;
            }
            f(a2);
            return this;
        }
    }

    private void b(a aVar) {
        a aVar2 = this.b.a;
        a aVar3 = this.b;
        aVar.b = aVar3;
        aVar.a = aVar2;
        aVar3.a = aVar;
        aVar2.b = aVar;
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelPipeline addLast(String str, ChannelHandler channelHandler) {
        return addLast(null, str, channelHandler);
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelPipeline addLast(EventExecutorGroup eventExecutorGroup, String str, ChannelHandler channelHandler) {
        synchronized (this) {
            b(channelHandler);
            final a a2 = a(eventExecutorGroup, a(str, channelHandler), channelHandler);
            c(a2);
            if (!this.o) {
                b(a2, true);
                return this;
            }
            EventExecutor executor = a2.executor();
            if (!executor.inEventLoop()) {
                executor.execute(new OneTimeTask() { // from class: io.netty.channel.DefaultChannelPipeline.3
                    @Override // java.lang.Runnable
                    public void run() {
                        DefaultChannelPipeline.this.f(a2);
                    }
                });
                return this;
            }
            f(a2);
            return this;
        }
    }

    private void c(a aVar) {
        a aVar2 = this.c.b;
        aVar.b = aVar2;
        a aVar3 = this.c;
        aVar.a = aVar3;
        aVar2.a = aVar;
        aVar3.b = aVar;
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelPipeline addBefore(String str, String str2, ChannelHandler channelHandler) {
        return addBefore(null, str, str2, channelHandler);
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelPipeline addBefore(EventExecutorGroup eventExecutorGroup, String str, String str2, ChannelHandler channelHandler) {
        synchronized (this) {
            b(channelHandler);
            String a2 = a(str2, channelHandler);
            a c2 = c(str);
            final a a3 = a(eventExecutorGroup, a2, channelHandler);
            a(c2, a3);
            if (!this.o) {
                b(a3, true);
                return this;
            }
            EventExecutor executor = a3.executor();
            if (!executor.inEventLoop()) {
                executor.execute(new OneTimeTask() { // from class: io.netty.channel.DefaultChannelPipeline.4
                    @Override // java.lang.Runnable
                    public void run() {
                        DefaultChannelPipeline.this.f(a3);
                    }
                });
                return this;
            }
            f(a3);
            return this;
        }
    }

    private static void a(a aVar, a aVar2) {
        aVar2.b = aVar.b;
        aVar2.a = aVar;
        aVar.b.a = aVar2;
        aVar.b = aVar2;
    }

    private String a(String str, ChannelHandler channelHandler) {
        if (str == null) {
            return a(channelHandler);
        }
        a(str);
        return str;
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelPipeline addAfter(String str, String str2, ChannelHandler channelHandler) {
        return addAfter(null, str, str2, channelHandler);
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelPipeline addAfter(EventExecutorGroup eventExecutorGroup, String str, String str2, ChannelHandler channelHandler) {
        synchronized (this) {
            b(channelHandler);
            String a2 = a(str2, channelHandler);
            a c2 = c(str);
            final a a3 = a(eventExecutorGroup, a2, channelHandler);
            b(c2, a3);
            if (!this.o) {
                b(a3, true);
                return this;
            }
            EventExecutor executor = a3.executor();
            if (!executor.inEventLoop()) {
                executor.execute(new OneTimeTask() { // from class: io.netty.channel.DefaultChannelPipeline.5
                    @Override // java.lang.Runnable
                    public void run() {
                        DefaultChannelPipeline.this.f(a3);
                    }
                });
                return this;
            }
            f(a3);
            return this;
        }
    }

    private static void b(a aVar, a aVar2) {
        aVar2.b = aVar;
        aVar2.a = aVar.a;
        aVar.a.b = aVar2;
        aVar.a = aVar2;
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelPipeline addFirst(ChannelHandler... channelHandlerArr) {
        return addFirst((EventExecutorGroup) null, channelHandlerArr);
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelPipeline addFirst(EventExecutorGroup eventExecutorGroup, ChannelHandler... channelHandlerArr) {
        if (channelHandlerArr == null) {
            throw new NullPointerException("handlers");
        } else if (channelHandlerArr.length == 0 || channelHandlerArr[0] == null) {
            return this;
        } else {
            int i = 1;
            while (i < channelHandlerArr.length && channelHandlerArr[i] != null) {
                i++;
            }
            for (int i2 = i - 1; i2 >= 0; i2--) {
                addFirst(eventExecutorGroup, null, channelHandlerArr[i2]);
            }
            return this;
        }
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelPipeline addLast(ChannelHandler... channelHandlerArr) {
        return addLast((EventExecutorGroup) null, channelHandlerArr);
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelPipeline addLast(EventExecutorGroup eventExecutorGroup, ChannelHandler... channelHandlerArr) {
        if (channelHandlerArr != null) {
            for (ChannelHandler channelHandler : channelHandlerArr) {
                if (channelHandler == null) {
                    break;
                }
                addLast(eventExecutorGroup, null, channelHandler);
            }
            return this;
        }
        throw new NullPointerException("handlers");
    }

    private String a(ChannelHandler channelHandler) {
        Map<Class<?>, String> map = g.get();
        Class<?> cls = channelHandler.getClass();
        String str = map.get(cls);
        if (str == null) {
            str = a(cls);
            map.put(cls, str);
        }
        if (b(str) != null) {
            int i = 1;
            String substring = str.substring(0, str.length() - 1);
            while (true) {
                str = substring + i;
                if (b(str) == null) {
                    break;
                }
                i++;
            }
        }
        return str;
    }

    private static String a(Class<?> cls) {
        return StringUtil.simpleClassName(cls) + "#0";
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelPipeline remove(ChannelHandler channelHandler) {
        d(c(channelHandler));
        return this;
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelHandler remove(String str) {
        return d(c(str)).handler();
    }

    @Override // io.netty.channel.ChannelPipeline
    public final <T extends ChannelHandler> T remove(Class<T> cls) {
        return (T) d(b(cls)).handler();
    }

    private a d(final a aVar) {
        if (d || !(aVar == this.b || aVar == this.c)) {
            synchronized (this) {
                e(aVar);
                if (!this.o) {
                    b(aVar, false);
                    return aVar;
                }
                EventExecutor executor = aVar.executor();
                if (!executor.inEventLoop()) {
                    executor.execute(new OneTimeTask() { // from class: io.netty.channel.DefaultChannelPipeline.6
                        @Override // java.lang.Runnable
                        public void run() {
                            DefaultChannelPipeline.this.g(aVar);
                        }
                    });
                    return aVar;
                }
                g(aVar);
                return aVar;
            }
        }
        throw new AssertionError();
    }

    public static void e(a aVar) {
        a aVar2 = aVar.b;
        a aVar3 = aVar.a;
        aVar2.a = aVar3;
        aVar3.b = aVar2;
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelHandler removeFirst() {
        if (this.b.a != this.c) {
            return d(this.b.a).handler();
        }
        throw new NoSuchElementException();
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelHandler removeLast() {
        a aVar = this.b.a;
        a aVar2 = this.c;
        if (aVar != aVar2) {
            return d(aVar2.b).handler();
        }
        throw new NoSuchElementException();
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelPipeline replace(ChannelHandler channelHandler, String str, ChannelHandler channelHandler2) {
        a(c(channelHandler), str, channelHandler2);
        return this;
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelHandler replace(String str, String str2, ChannelHandler channelHandler) {
        return a(c(str), str2, channelHandler);
    }

    @Override // io.netty.channel.ChannelPipeline
    public final <T extends ChannelHandler> T replace(Class<T> cls, String str, ChannelHandler channelHandler) {
        return (T) a(b(cls), str, channelHandler);
    }

    private ChannelHandler a(final a aVar, String str, ChannelHandler channelHandler) {
        if (d || !(aVar == this.b || aVar == this.c)) {
            synchronized (this) {
                b(channelHandler);
                if (str == null) {
                    str = a(channelHandler);
                } else if (!aVar.name().equals(str)) {
                    a(str);
                }
                final a a2 = a(aVar.c, str, channelHandler);
                c(aVar, a2);
                if (!this.o) {
                    b(a2, true);
                    b(aVar, false);
                    return aVar.handler();
                }
                EventExecutor executor = aVar.executor();
                if (!executor.inEventLoop()) {
                    executor.execute(new OneTimeTask() { // from class: io.netty.channel.DefaultChannelPipeline.7
                        @Override // java.lang.Runnable
                        public void run() {
                            DefaultChannelPipeline.this.f(a2);
                            DefaultChannelPipeline.this.g(aVar);
                        }
                    });
                    return aVar.handler();
                }
                f(a2);
                g(aVar);
                return aVar.handler();
            }
        }
        throw new AssertionError();
    }

    private static void c(a aVar, a aVar2) {
        a aVar3 = aVar.b;
        a aVar4 = aVar.a;
        aVar2.b = aVar3;
        aVar2.a = aVar4;
        aVar3.a = aVar2;
        aVar4.b = aVar2;
        aVar.b = aVar2;
        aVar.a = aVar2;
    }

    private static void b(ChannelHandler channelHandler) {
        if (channelHandler instanceof ChannelHandlerAdapter) {
            ChannelHandlerAdapter channelHandlerAdapter = (ChannelHandlerAdapter) channelHandler;
            if (channelHandlerAdapter.isSharable() || !channelHandlerAdapter.added) {
                channelHandlerAdapter.added = true;
                return;
            }
            throw new ChannelPipelineException(channelHandlerAdapter.getClass().getName() + " is not a @Sharable handler, so can't be added or removed multiple times.");
        }
    }

    public void f(a aVar) {
        try {
            aVar.handler().handlerAdded(aVar);
            aVar.b();
        } catch (Throwable th) {
            boolean z = false;
            try {
                e(aVar);
                aVar.handler().handlerRemoved(aVar);
                aVar.a();
                z = true;
            } catch (Throwable th2) {
                if (a.isWarnEnabled()) {
                    InternalLogger internalLogger = a;
                    internalLogger.warn("Failed to remove a handler: " + aVar.name(), th2);
                }
            }
            if (z) {
                fireExceptionCaught((Throwable) new ChannelPipelineException(aVar.handler().getClass().getName() + ".handlerAdded() has thrown an exception; removed.", th));
                return;
            }
            fireExceptionCaught((Throwable) new ChannelPipelineException(aVar.handler().getClass().getName() + ".handlerAdded() has thrown an exception; also failed to remove.", th));
        }
    }

    public void g(a aVar) {
        try {
            aVar.handler().handlerRemoved(aVar);
            aVar.a();
        } catch (Throwable th) {
            fireExceptionCaught((Throwable) new ChannelPipelineException(aVar.handler().getClass().getName() + ".handlerRemoved() has thrown an exception.", th));
        }
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelHandler first() {
        ChannelHandlerContext firstContext = firstContext();
        if (firstContext == null) {
            return null;
        }
        return firstContext.handler();
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelHandlerContext firstContext() {
        if (this.b.a == this.c) {
            return null;
        }
        return this.b.a;
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelHandler last() {
        a aVar = this.c.b;
        if (aVar == this.b) {
            return null;
        }
        return aVar.handler();
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelHandlerContext lastContext() {
        a aVar = this.c.b;
        if (aVar == this.b) {
            return null;
        }
        return aVar;
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelHandler get(String str) {
        ChannelHandlerContext context = context(str);
        if (context == null) {
            return null;
        }
        return context.handler();
    }

    @Override // io.netty.channel.ChannelPipeline
    public final <T extends ChannelHandler> T get(Class<T> cls) {
        ChannelHandlerContext context = context(cls);
        if (context == null) {
            return null;
        }
        return (T) context.handler();
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelHandlerContext context(String str) {
        if (str != null) {
            return b(str);
        }
        throw new NullPointerException("name");
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelHandlerContext context(ChannelHandler channelHandler) {
        if (channelHandler != null) {
            for (a aVar = this.b.a; aVar != null; aVar = aVar.a) {
                if (aVar.handler() == channelHandler) {
                    return aVar;
                }
            }
            return null;
        }
        throw new NullPointerException("handler");
    }

    @Override // io.netty.channel.ChannelPipeline
    public final ChannelHandlerContext context(Class<? extends ChannelHandler> cls) {
        if (cls != null) {
            for (a aVar = this.b.a; aVar != null; aVar = aVar.a) {
                if (cls.isAssignableFrom(aVar.handler().getClass())) {
                    return aVar;
                }
            }
            return null;
        }
        throw new NullPointerException("handlerType");
    }

    @Override // io.netty.channel.ChannelPipeline
    public final List<String> names() {
        ArrayList arrayList = new ArrayList();
        for (a aVar = this.b.a; aVar != null; aVar = aVar.a) {
            arrayList.add(aVar.name());
        }
        return arrayList;
    }

    @Override // io.netty.channel.ChannelPipeline
    public final Map<String, ChannelHandler> toMap() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (a aVar = this.b.a; aVar != this.c; aVar = aVar.a) {
            linkedHashMap.put(aVar.name(), aVar.handler());
        }
        return linkedHashMap;
    }

    @Override // java.lang.Iterable
    public final Iterator<Map.Entry<String, ChannelHandler>> iterator() {
        return toMap().entrySet().iterator();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this));
        sb.append('{');
        a aVar = this.b.a;
        while (aVar != this.c) {
            sb.append('(');
            sb.append(aVar.name());
            sb.append(" = ");
            sb.append(aVar.handler().getClass().getName());
            sb.append(')');
            aVar = aVar.a;
            if (aVar == this.c) {
                break;
            }
            sb.append(", ");
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // io.netty.channel.ChannelPipeline, io.netty.channel.ChannelInboundInvoker
    public final ChannelPipeline fireChannelRegistered() {
        a.a(this.b);
        return this;
    }

    @Override // io.netty.channel.ChannelPipeline, io.netty.channel.ChannelInboundInvoker
    public final ChannelPipeline fireChannelUnregistered() {
        a.b(this.b);
        return this;
    }

    public synchronized void d() {
        a(this.b.a, false);
    }

    public void a(final a aVar, boolean z) {
        Thread currentThread = Thread.currentThread();
        a aVar2 = this.c;
        while (aVar != aVar2) {
            EventExecutor executor = aVar.executor();
            if (z || executor.inEventLoop(currentThread)) {
                aVar = aVar.a;
                z = false;
            } else {
                executor.execute(new OneTimeTask() { // from class: io.netty.channel.DefaultChannelPipeline.8
                    @Override // java.lang.Runnable
                    public void run() {
                        DefaultChannelPipeline.this.a(aVar, true);
                    }
                });
                return;
            }
        }
        a(currentThread, aVar2.b, z);
    }

    public void a(Thread thread, final a aVar, boolean z) {
        a aVar2 = this.b;
        while (aVar != aVar2) {
            EventExecutor executor = aVar.executor();
            if (z || executor.inEventLoop(thread)) {
                synchronized (this) {
                    e(aVar);
                }
                g(aVar);
                aVar = aVar.b;
                z = false;
            } else {
                executor.execute(new OneTimeTask() { // from class: io.netty.channel.DefaultChannelPipeline.9
                    @Override // java.lang.Runnable
                    public void run() {
                        DefaultChannelPipeline.this.a(Thread.currentThread(), aVar, true);
                    }
                });
                return;
            }
        }
    }

    @Override // io.netty.channel.ChannelPipeline, io.netty.channel.ChannelInboundInvoker
    public final ChannelPipeline fireChannelActive() {
        a.c(this.b);
        return this;
    }

    @Override // io.netty.channel.ChannelPipeline, io.netty.channel.ChannelInboundInvoker
    public final ChannelPipeline fireChannelInactive() {
        a.d(this.b);
        return this;
    }

    @Override // io.netty.channel.ChannelPipeline, io.netty.channel.ChannelInboundInvoker
    public final ChannelPipeline fireExceptionCaught(Throwable th) {
        a.a(this.b, th);
        return this;
    }

    @Override // io.netty.channel.ChannelPipeline, io.netty.channel.ChannelInboundInvoker
    public final ChannelPipeline fireUserEventTriggered(Object obj) {
        a.a(this.b, obj);
        return this;
    }

    @Override // io.netty.channel.ChannelPipeline, io.netty.channel.ChannelInboundInvoker
    public final ChannelPipeline fireChannelRead(Object obj) {
        a.b(this.b, obj);
        return this;
    }

    @Override // io.netty.channel.ChannelPipeline, io.netty.channel.ChannelInboundInvoker
    public final ChannelPipeline fireChannelReadComplete() {
        a.e(this.b);
        return this;
    }

    @Override // io.netty.channel.ChannelPipeline, io.netty.channel.ChannelInboundInvoker
    public final ChannelPipeline fireChannelWritabilityChanged() {
        a.f(this.b);
        return this;
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture bind(SocketAddress socketAddress) {
        return this.c.bind(socketAddress);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture connect(SocketAddress socketAddress) {
        return this.c.connect(socketAddress);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
        return this.c.connect(socketAddress, socketAddress2);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture disconnect() {
        return this.c.disconnect();
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture close() {
        return this.c.close();
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture deregister() {
        return this.c.deregister();
    }

    @Override // io.netty.channel.ChannelPipeline, io.netty.channel.ChannelOutboundInvoker
    public final ChannelPipeline flush() {
        this.c.flush();
        return this;
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
        return this.c.bind(socketAddress, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
        return this.c.connect(socketAddress, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        return this.c.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture disconnect(ChannelPromise channelPromise) {
        return this.c.disconnect(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture close(ChannelPromise channelPromise) {
        return this.c.close(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture deregister(ChannelPromise channelPromise) {
        return this.c.deregister(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelPipeline read() {
        this.c.read();
        return this;
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture write(Object obj) {
        return this.c.write(obj);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture write(Object obj, ChannelPromise channelPromise) {
        return this.c.write(obj, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture writeAndFlush(Object obj, ChannelPromise channelPromise) {
        return this.c.writeAndFlush(obj, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture writeAndFlush(Object obj) {
        return this.c.writeAndFlush(obj);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelPromise newPromise() {
        return new DefaultChannelPromise(this.h);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelProgressivePromise newProgressivePromise() {
        return new DefaultChannelProgressivePromise(this.h);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture newSucceededFuture() {
        return this.i;
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelFuture newFailedFuture(Throwable th) {
        return new e(this.h, null, th);
    }

    @Override // io.netty.channel.ChannelOutboundInvoker
    public final ChannelPromise voidPromise() {
        return this.j;
    }

    private void a(String str) {
        if (b(str) != null) {
            throw new IllegalArgumentException("Duplicate handler name: " + str);
        }
    }

    private a b(String str) {
        for (a aVar = this.b.a; aVar != this.c; aVar = aVar.a) {
            if (aVar.name().equals(str)) {
                return aVar;
            }
        }
        return null;
    }

    private a c(String str) {
        a aVar = (a) context(str);
        if (aVar != null) {
            return aVar;
        }
        throw new NoSuchElementException(str);
    }

    private a c(ChannelHandler channelHandler) {
        a aVar = (a) context(channelHandler);
        if (aVar != null) {
            return aVar;
        }
        throw new NoSuchElementException(channelHandler.getClass().getName());
    }

    private a b(Class<? extends ChannelHandler> cls) {
        a aVar = (a) context(cls);
        if (aVar != null) {
            return aVar;
        }
        throw new NoSuchElementException(cls.getName());
    }

    public void e() {
        c cVar;
        synchronized (this) {
            if (!d && this.o) {
                throw new AssertionError();
            }
            this.o = true;
            this.n = null;
        }
        for (cVar = this.n; cVar != null; cVar = cVar.c) {
            cVar.a();
        }
    }

    private void b(a aVar, boolean z) {
        if (d || !this.o) {
            c bVar = z ? new b(aVar) : new d(aVar);
            c cVar = this.n;
            if (cVar == null) {
                this.n = bVar;
                return;
            }
            while (cVar.c != null) {
                cVar = cVar.c;
            }
            cVar.c = bVar;
            return;
        }
        throw new AssertionError();
    }

    protected void onUnhandledInboundException(Throwable th) {
        try {
            a.warn("An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.", th);
        } finally {
            ReferenceCountUtil.release(th);
        }
    }

    protected void onUnhandledInboundMessage(Object obj) {
        try {
            a.debug("Discarded inbound message {} that reached at the tail of the pipeline. Please check your pipeline configuration.", obj);
        } finally {
            ReferenceCountUtil.release(obj);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public final class e extends a implements ChannelInboundHandler {
        @Override // io.netty.channel.ChannelInboundHandler
        public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        @Override // io.netty.channel.ChannelHandlerContext
        public ChannelHandler handler() {
            return this;
        }

        @Override // io.netty.channel.ChannelHandler
        public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        @Override // io.netty.channel.ChannelHandler
        public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        e(DefaultChannelPipeline defaultChannelPipeline) {
            super(defaultChannelPipeline, null, DefaultChannelPipeline.f, true, false);
            DefaultChannelPipeline.this = r7;
            b();
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
            ReferenceCountUtil.release(obj);
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
            DefaultChannelPipeline.this.onUnhandledInboundException(th);
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
            DefaultChannelPipeline.this.onUnhandledInboundMessage(obj);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public final class a extends a implements ChannelInboundHandler, ChannelOutboundHandler {
        private final Channel.Unsafe e;
        private boolean f = true;

        @Override // io.netty.channel.ChannelHandlerContext
        public ChannelHandler handler() {
            return this;
        }

        @Override // io.netty.channel.ChannelHandler
        public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        @Override // io.netty.channel.ChannelHandler
        public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(DefaultChannelPipeline defaultChannelPipeline) {
            super(defaultChannelPipeline, null, DefaultChannelPipeline.e, false, true);
            DefaultChannelPipeline.this = r7;
            this.e = defaultChannelPipeline.channel().unsafe();
            b();
        }

        @Override // io.netty.channel.ChannelOutboundHandler
        public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
            this.e.bind(socketAddress, channelPromise);
        }

        @Override // io.netty.channel.ChannelOutboundHandler
        public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
            this.e.connect(socketAddress, socketAddress2, channelPromise);
        }

        @Override // io.netty.channel.ChannelOutboundHandler
        public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
            this.e.disconnect(channelPromise);
        }

        @Override // io.netty.channel.ChannelOutboundHandler
        public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
            this.e.close(channelPromise);
        }

        @Override // io.netty.channel.ChannelOutboundHandler
        public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
            this.e.deregister(channelPromise);
        }

        @Override // io.netty.channel.ChannelOutboundHandler
        public void read(ChannelHandlerContext channelHandlerContext) {
            this.e.beginRead();
        }

        @Override // io.netty.channel.ChannelOutboundHandler
        public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
            this.e.write(obj, channelPromise);
        }

        @Override // io.netty.channel.ChannelOutboundHandler
        public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
            this.e.flush();
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
            channelHandlerContext.fireExceptionCaught(th);
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
            if (this.f) {
                this.f = false;
                DefaultChannelPipeline.this.e();
            }
            channelHandlerContext.fireChannelRegistered();
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
            channelHandlerContext.fireChannelUnregistered();
            if (!DefaultChannelPipeline.this.h.isOpen()) {
                DefaultChannelPipeline.this.d();
            }
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
            channelHandlerContext.fireChannelActive();
            c();
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
            channelHandlerContext.fireChannelInactive();
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
            channelHandlerContext.fireChannelRead(obj);
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
            channelHandlerContext.fireChannelReadComplete();
            c();
        }

        private void c() {
            if (DefaultChannelPipeline.this.h.config().isAutoRead()) {
                DefaultChannelPipeline.this.h.read();
            }
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
            channelHandlerContext.fireUserEventTriggered(obj);
        }

        @Override // io.netty.channel.ChannelInboundHandler
        public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
            channelHandlerContext.fireChannelWritabilityChanged();
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class c extends OneTimeTask {
        final a b;
        c c;

        abstract void a();

        c(a aVar) {
            this.b = aVar;
        }
    }

    /* loaded from: classes4.dex */
    public final class b extends c {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b(a aVar) {
            super(aVar);
            DefaultChannelPipeline.this = r1;
        }

        @Override // java.lang.Runnable
        public void run() {
            DefaultChannelPipeline.this.f(this.b);
        }

        @Override // io.netty.channel.DefaultChannelPipeline.c
        void a() {
            EventExecutor executor = this.b.executor();
            if (executor.inEventLoop()) {
                DefaultChannelPipeline.this.f(this.b);
                return;
            }
            try {
                executor.execute(this);
            } catch (RejectedExecutionException e) {
                if (DefaultChannelPipeline.a.isWarnEnabled()) {
                    DefaultChannelPipeline.a.warn("Can't invoke handlerAdded() as the EventExecutor {} rejected it, removing handler {}.", executor, this.b.name(), e);
                }
                DefaultChannelPipeline.e(this.b);
                this.b.a();
            }
        }
    }

    /* loaded from: classes4.dex */
    public final class d extends c {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        d(a aVar) {
            super(aVar);
            DefaultChannelPipeline.this = r1;
        }

        @Override // java.lang.Runnable
        public void run() {
            DefaultChannelPipeline.this.g(this.b);
        }

        @Override // io.netty.channel.DefaultChannelPipeline.c
        void a() {
            EventExecutor executor = this.b.executor();
            if (executor.inEventLoop()) {
                DefaultChannelPipeline.this.g(this.b);
                return;
            }
            try {
                executor.execute(this);
            } catch (RejectedExecutionException e) {
                if (DefaultChannelPipeline.a.isWarnEnabled()) {
                    DefaultChannelPipeline.a.warn("Can't invoke handlerRemoved() as the EventExecutor {} rejected it, removing handler {}.", executor, this.b.name(), e);
                }
                this.b.a();
            }
        }
    }
}
