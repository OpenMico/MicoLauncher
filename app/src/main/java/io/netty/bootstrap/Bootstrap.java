package io.netty.bootstrap;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.resolver.AddressResolver;
import io.netty.resolver.AddressResolverGroup;
import io.netty.resolver.DefaultAddressResolverGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;

/* loaded from: classes4.dex */
public class Bootstrap extends AbstractBootstrap<Bootstrap, Channel> {
    private static final InternalLogger b = InternalLoggerFactory.getInstance(Bootstrap.class);
    private static final AddressResolverGroup<?> c = DefaultAddressResolverGroup.INSTANCE;
    private final BootstrapConfig d;
    private volatile AddressResolverGroup<SocketAddress> e;
    private volatile SocketAddress f;

    public Bootstrap() {
        this.d = new BootstrapConfig(this);
        this.e = c;
    }

    private Bootstrap(Bootstrap bootstrap) {
        super(bootstrap);
        this.d = new BootstrapConfig(this);
        this.e = c;
        this.e = bootstrap.e;
        this.f = bootstrap.f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Bootstrap resolver(AddressResolverGroup<?> addressResolverGroup) {
        if (addressResolverGroup != 0) {
            this.e = addressResolverGroup;
            return this;
        }
        throw new NullPointerException("resolver");
    }

    public Bootstrap remoteAddress(SocketAddress socketAddress) {
        this.f = socketAddress;
        return this;
    }

    public Bootstrap remoteAddress(String str, int i) {
        this.f = InetSocketAddress.createUnresolved(str, i);
        return this;
    }

    public Bootstrap remoteAddress(InetAddress inetAddress, int i) {
        this.f = new InetSocketAddress(inetAddress, i);
        return this;
    }

    public ChannelFuture connect() {
        validate();
        SocketAddress socketAddress = this.f;
        if (socketAddress != null) {
            return a(socketAddress, this.d.localAddress());
        }
        throw new IllegalStateException("remoteAddress not set");
    }

    public ChannelFuture connect(String str, int i) {
        return connect(InetSocketAddress.createUnresolved(str, i));
    }

    public ChannelFuture connect(InetAddress inetAddress, int i) {
        return connect(new InetSocketAddress(inetAddress, i));
    }

    public ChannelFuture connect(SocketAddress socketAddress) {
        if (socketAddress != null) {
            validate();
            return a(socketAddress, this.d.localAddress());
        }
        throw new NullPointerException("remoteAddress");
    }

    public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
        if (socketAddress != null) {
            validate();
            return a(socketAddress, socketAddress2);
        }
        throw new NullPointerException("remoteAddress");
    }

    private ChannelFuture a(final SocketAddress socketAddress, final SocketAddress socketAddress2) {
        ChannelFuture a = a();
        final Channel channel = a.channel();
        if (a.isDone()) {
            return !a.isSuccess() ? a : a(channel, socketAddress, socketAddress2, channel.newPromise());
        }
        final AbstractBootstrap.a aVar = new AbstractBootstrap.a(channel);
        a.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.bootstrap.Bootstrap.1
            /* renamed from: a */
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Throwable cause = channelFuture.cause();
                if (cause != null) {
                    aVar.setFailure(cause);
                    return;
                }
                aVar.a();
                Bootstrap.this.a(channel, socketAddress, socketAddress2, aVar);
            }
        });
        return aVar;
    }

    public ChannelFuture a(final Channel channel, SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
        AddressResolver<SocketAddress> resolver;
        try {
            resolver = this.e.getResolver(channel.eventLoop());
        } catch (Throwable th) {
            channelPromise.tryFailure(th);
        }
        if (resolver.isSupported(socketAddress) && !resolver.isResolved(socketAddress)) {
            Future<SocketAddress> resolve = resolver.resolve(socketAddress);
            if (resolve.isDone()) {
                Throwable cause = resolve.cause();
                if (cause != null) {
                    channel.close();
                    channelPromise.setFailure(cause);
                } else {
                    b(resolve.getNow(), socketAddress2, channelPromise);
                }
                return channelPromise;
            }
            resolve.addListener(new FutureListener<SocketAddress>() { // from class: io.netty.bootstrap.Bootstrap.2
                @Override // io.netty.util.concurrent.GenericFutureListener
                public void operationComplete(Future<SocketAddress> future) throws Exception {
                    if (future.cause() != null) {
                        channel.close();
                        channelPromise.setFailure(future.cause());
                        return;
                    }
                    Bootstrap.b(future.getNow(), socketAddress2, channelPromise);
                }
            });
            return channelPromise;
        }
        b(socketAddress, socketAddress2, channelPromise);
        return channelPromise;
    }

    public static void b(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) {
        final Channel channel = channelPromise.channel();
        channel.eventLoop().execute(new OneTimeTask() { // from class: io.netty.bootstrap.Bootstrap.3
            @Override // java.lang.Runnable
            public void run() {
                SocketAddress socketAddress3 = socketAddress2;
                if (socketAddress3 == null) {
                    channel.connect(socketAddress, channelPromise);
                } else {
                    channel.connect(socketAddress, socketAddress3, channelPromise);
                }
                channelPromise.addListener((GenericFutureListener<? extends Future<? super Void>>) ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        });
    }

    @Override // io.netty.bootstrap.AbstractBootstrap
    void a(Channel channel) throws Exception {
        channel.pipeline().addLast(this.d.handler());
        Map<ChannelOption<?>, Object> b2 = b();
        synchronized (b2) {
            for (Map.Entry<ChannelOption<?>, Object> entry : b2.entrySet()) {
                if (!channel.config().setOption(entry.getKey(), entry.getValue())) {
                    InternalLogger internalLogger = b;
                    internalLogger.warn("Unknown channel option: " + entry);
                }
            }
        }
        Map<AttributeKey<?>, Object> c2 = c();
        synchronized (c2) {
            for (Map.Entry<AttributeKey<?>, Object> entry2 : c2.entrySet()) {
                channel.attr(entry2.getKey()).set(entry2.getValue());
            }
        }
    }

    @Override // io.netty.bootstrap.AbstractBootstrap
    public Bootstrap validate() {
        super.validate();
        if (this.d.handler() != null) {
            return this;
        }
        throw new IllegalStateException("handler not set");
    }

    @Override // io.netty.bootstrap.AbstractBootstrap
    public Bootstrap clone() {
        return new Bootstrap(this);
    }

    public Bootstrap clone(EventLoopGroup eventLoopGroup) {
        Bootstrap bootstrap = new Bootstrap(this);
        bootstrap.a = eventLoopGroup;
        return bootstrap;
    }

    @Override // io.netty.bootstrap.AbstractBootstrap
    public final AbstractBootstrapConfig<Bootstrap, Channel> config() {
        return this.d;
    }

    public final SocketAddress i() {
        return this.f;
    }

    public final AddressResolverGroup<?> j() {
        return this.e;
    }
}
