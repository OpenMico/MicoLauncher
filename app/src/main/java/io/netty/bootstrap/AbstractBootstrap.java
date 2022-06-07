package io.netty.bootstrap;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ReflectiveChannelFactory;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.StringUtil;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class AbstractBootstrap<B extends AbstractBootstrap<B, C>, C extends Channel> implements Cloneable {
    volatile EventLoopGroup a;
    private volatile ChannelFactory<? extends C> b;
    private volatile SocketAddress c;
    private final Map<ChannelOption<?>, Object> d = new LinkedHashMap();
    private final Map<AttributeKey<?>, Object> e = new LinkedHashMap();
    private volatile ChannelHandler f;

    abstract void a(Channel channel) throws Exception;

    public abstract B clone();

    public abstract AbstractBootstrapConfig<B, C> config();

    public AbstractBootstrap() {
    }

    public AbstractBootstrap(AbstractBootstrap<B, C> abstractBootstrap) {
        this.a = abstractBootstrap.a;
        this.b = abstractBootstrap.b;
        this.f = abstractBootstrap.f;
        this.c = abstractBootstrap.c;
        synchronized (abstractBootstrap.d) {
            this.d.putAll(abstractBootstrap.d);
        }
        synchronized (abstractBootstrap.e) {
            this.e.putAll(abstractBootstrap.e);
        }
    }

    public B group(EventLoopGroup eventLoopGroup) {
        if (eventLoopGroup == null) {
            throw new NullPointerException("group");
        } else if (this.a == null) {
            this.a = eventLoopGroup;
            return this;
        } else {
            throw new IllegalStateException("group set already");
        }
    }

    public B channel(Class<? extends C> cls) {
        if (cls != null) {
            return channelFactory((ChannelFactory) new ReflectiveChannelFactory(cls));
        }
        throw new NullPointerException("channelClass");
    }

    @Deprecated
    public B channelFactory(ChannelFactory<? extends C> channelFactory) {
        if (channelFactory == null) {
            throw new NullPointerException("channelFactory");
        } else if (this.b == null) {
            this.b = channelFactory;
            return this;
        } else {
            throw new IllegalStateException("channelFactory set already");
        }
    }

    public B channelFactory(ChannelFactory<? extends C> channelFactory) {
        return channelFactory((ChannelFactory) channelFactory);
    }

    public B localAddress(SocketAddress socketAddress) {
        this.c = socketAddress;
        return this;
    }

    public B localAddress(int i) {
        return localAddress(new InetSocketAddress(i));
    }

    public B localAddress(String str, int i) {
        return localAddress(new InetSocketAddress(str, i));
    }

    public B localAddress(InetAddress inetAddress, int i) {
        return localAddress(new InetSocketAddress(inetAddress, i));
    }

    public <T> B option(ChannelOption<T> channelOption, T t) {
        if (channelOption != null) {
            if (t == null) {
                synchronized (this.d) {
                    this.d.remove(channelOption);
                }
            } else {
                synchronized (this.d) {
                    this.d.put(channelOption, t);
                }
            }
            return this;
        }
        throw new NullPointerException("option");
    }

    public <T> B attr(AttributeKey<T> attributeKey, T t) {
        if (attributeKey != null) {
            if (t == null) {
                synchronized (this.e) {
                    this.e.remove(attributeKey);
                }
            } else {
                synchronized (this.e) {
                    this.e.put(attributeKey, t);
                }
            }
            return this;
        }
        throw new NullPointerException("key");
    }

    public B validate() {
        if (this.a == null) {
            throw new IllegalStateException("group not set");
        } else if (this.b != null) {
            return this;
        } else {
            throw new IllegalStateException("channel or channelFactory not set");
        }
    }

    public ChannelFuture register() {
        validate();
        return a();
    }

    public ChannelFuture bind() {
        validate();
        SocketAddress socketAddress = this.c;
        if (socketAddress != null) {
            return a(socketAddress);
        }
        throw new IllegalStateException("localAddress not set");
    }

    public ChannelFuture bind(int i) {
        return bind(new InetSocketAddress(i));
    }

    public ChannelFuture bind(String str, int i) {
        return bind(new InetSocketAddress(str, i));
    }

    public ChannelFuture bind(InetAddress inetAddress, int i) {
        return bind(new InetSocketAddress(inetAddress, i));
    }

    public ChannelFuture bind(SocketAddress socketAddress) {
        validate();
        if (socketAddress != null) {
            return a(socketAddress);
        }
        throw new NullPointerException("localAddress");
    }

    private ChannelFuture a(final SocketAddress socketAddress) {
        final ChannelFuture a2 = a();
        final Channel channel = a2.channel();
        if (a2.cause() != null) {
            return a2;
        }
        if (a2.isDone()) {
            ChannelPromise newPromise = channel.newPromise();
            b(a2, channel, socketAddress, newPromise);
            return newPromise;
        }
        final a aVar = new a(channel);
        a2.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.bootstrap.AbstractBootstrap.1
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Throwable cause = channelFuture.cause();
                if (cause != null) {
                    aVar.setFailure(cause);
                    return;
                }
                aVar.a();
                AbstractBootstrap.b(a2, channel, socketAddress, aVar);
            }
        });
        return aVar;
    }

    final ChannelFuture a() {
        Channel newChannel = this.b.newChannel();
        try {
            a(newChannel);
            ChannelFuture register = group().register(newChannel);
            if (register.cause() != null) {
                if (newChannel.isRegistered()) {
                    newChannel.close();
                } else {
                    newChannel.unsafe().closeForcibly();
                }
            }
            return register;
        } catch (Throwable th) {
            newChannel.unsafe().closeForcibly();
            return new DefaultChannelPromise(newChannel, GlobalEventExecutor.INSTANCE).setFailure(th);
        }
    }

    public static void b(final ChannelFuture channelFuture, final Channel channel, final SocketAddress socketAddress, final ChannelPromise channelPromise) {
        channel.eventLoop().execute(new OneTimeTask() { // from class: io.netty.bootstrap.AbstractBootstrap.2
            @Override // java.lang.Runnable
            public void run() {
                if (channelFuture.isSuccess()) {
                    channel.bind(socketAddress, channelPromise).addListener((GenericFutureListener<? extends Future<? super Void>>) ChannelFutureListener.CLOSE_ON_FAILURE);
                } else {
                    channelPromise.setFailure(channelFuture.cause());
                }
            }
        });
    }

    public B handler(ChannelHandler channelHandler) {
        if (channelHandler != null) {
            this.f = channelHandler;
            return this;
        }
        throw new NullPointerException("handler");
    }

    @Deprecated
    public final EventLoopGroup group() {
        return this.a;
    }

    static <K, V> Map<K, V> a(Map<K, V> map) {
        synchronized (map) {
            if (map.isEmpty()) {
                return Collections.emptyMap();
            }
            return Collections.unmodifiableMap(new LinkedHashMap(map));
        }
    }

    final Map<ChannelOption<?>, Object> b() {
        return this.d;
    }

    final Map<AttributeKey<?>, Object> c() {
        return this.e;
    }

    public final SocketAddress d() {
        return this.c;
    }

    public final ChannelFactory<? extends C> e() {
        return this.b;
    }

    public final ChannelHandler f() {
        return this.f;
    }

    public final Map<ChannelOption<?>, Object> g() {
        return a(this.d);
    }

    public final Map<AttributeKey<?>, Object> h() {
        return a(this.e);
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '(' + config() + ')';
    }

    /* loaded from: classes4.dex */
    public static final class a extends DefaultChannelPromise {
        private volatile boolean a;

        public a(Channel channel) {
            super(channel);
        }

        public void a() {
            this.a = true;
        }

        @Override // io.netty.channel.DefaultChannelPromise, io.netty.util.concurrent.DefaultPromise
        public EventExecutor executor() {
            if (this.a) {
                return super.executor();
            }
            return GlobalEventExecutor.INSTANCE;
        }
    }
}
