package io.netty.bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.OneTimeTask;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ServerBootstrap extends AbstractBootstrap<ServerBootstrap, ServerChannel> {
    private static final InternalLogger b = InternalLoggerFactory.getInstance(ServerBootstrap.class);
    private final Map<ChannelOption<?>, Object> c = new LinkedHashMap();
    private final Map<AttributeKey<?>, Object> d = new LinkedHashMap();
    private final ServerBootstrapConfig e = new ServerBootstrapConfig(this);
    private volatile EventLoopGroup f;
    private volatile ChannelHandler g;

    public ServerBootstrap() {
    }

    private ServerBootstrap(ServerBootstrap serverBootstrap) {
        super(serverBootstrap);
        this.f = serverBootstrap.f;
        this.g = serverBootstrap.g;
        synchronized (serverBootstrap.c) {
            this.c.putAll(serverBootstrap.c);
        }
        synchronized (serverBootstrap.d) {
            this.d.putAll(serverBootstrap.d);
        }
    }

    @Override // io.netty.bootstrap.AbstractBootstrap
    public ServerBootstrap group(EventLoopGroup eventLoopGroup) {
        return group(eventLoopGroup, eventLoopGroup);
    }

    public ServerBootstrap group(EventLoopGroup eventLoopGroup, EventLoopGroup eventLoopGroup2) {
        super.group(eventLoopGroup);
        if (eventLoopGroup2 == null) {
            throw new NullPointerException("childGroup");
        } else if (this.f == null) {
            this.f = eventLoopGroup2;
            return this;
        } else {
            throw new IllegalStateException("childGroup set already");
        }
    }

    public <T> ServerBootstrap childOption(ChannelOption<T> channelOption, T t) {
        if (channelOption != null) {
            if (t == null) {
                synchronized (this.c) {
                    this.c.remove(channelOption);
                }
            } else {
                synchronized (this.c) {
                    this.c.put(channelOption, t);
                }
            }
            return this;
        }
        throw new NullPointerException("childOption");
    }

    public <T> ServerBootstrap childAttr(AttributeKey<T> attributeKey, T t) {
        if (attributeKey != null) {
            if (t == null) {
                this.d.remove(attributeKey);
            } else {
                this.d.put(attributeKey, t);
            }
            return this;
        }
        throw new NullPointerException("childKey");
    }

    public ServerBootstrap childHandler(ChannelHandler channelHandler) {
        if (channelHandler != null) {
            this.g = channelHandler;
            return this;
        }
        throw new NullPointerException("childHandler");
    }

    @Override // io.netty.bootstrap.AbstractBootstrap
    void a(Channel channel) throws Exception {
        final Map.Entry[] entryArr;
        final Map.Entry[] entryArr2;
        Map<ChannelOption<?>, ?> b2 = b();
        synchronized (b2) {
            channel.config().setOptions(b2);
        }
        Map<AttributeKey<?>, Object> c = c();
        synchronized (c) {
            for (Map.Entry<AttributeKey<?>, Object> entry : c.entrySet()) {
                channel.attr(entry.getKey()).set(entry.getValue());
            }
        }
        ChannelPipeline pipeline = channel.pipeline();
        final EventLoopGroup eventLoopGroup = this.f;
        final ChannelHandler channelHandler = this.g;
        synchronized (this.c) {
            entryArr = (Map.Entry[]) this.c.entrySet().toArray(a(this.c.size()));
        }
        synchronized (this.d) {
            entryArr2 = (Map.Entry[]) this.d.entrySet().toArray(b(this.d.size()));
        }
        pipeline.addLast(new ChannelInitializer<Channel>() { // from class: io.netty.bootstrap.ServerBootstrap.1
            @Override // io.netty.channel.ChannelInitializer
            public void initChannel(Channel channel2) throws Exception {
                ChannelPipeline pipeline2 = channel2.pipeline();
                ChannelHandler handler = ServerBootstrap.this.e.handler();
                if (handler != null) {
                    pipeline2.addLast(handler);
                }
                pipeline2.addLast(new a(eventLoopGroup, channelHandler, entryArr, entryArr2));
            }
        });
    }

    @Override // io.netty.bootstrap.AbstractBootstrap
    public ServerBootstrap validate() {
        super.validate();
        if (this.g != null) {
            if (this.f == null) {
                b.warn("childGroup is not set. Using parentGroup instead.");
                this.f = group();
            }
            return this;
        }
        throw new IllegalStateException("childHandler not set");
    }

    private static Map.Entry<ChannelOption<?>, Object>[] a(int i) {
        return new Map.Entry[i];
    }

    private static Map.Entry<AttributeKey<?>, Object>[] b(int i) {
        return new Map.Entry[i];
    }

    /* loaded from: classes4.dex */
    public static class a extends ChannelInboundHandlerAdapter {
        private final EventLoopGroup a;
        private final ChannelHandler b;
        private final Map.Entry<ChannelOption<?>, Object>[] c;
        private final Map.Entry<AttributeKey<?>, Object>[] d;

        a(EventLoopGroup eventLoopGroup, ChannelHandler channelHandler, Map.Entry<ChannelOption<?>, Object>[] entryArr, Map.Entry<AttributeKey<?>, Object>[] entryArr2) {
            this.a = eventLoopGroup;
            this.b = channelHandler;
            this.c = entryArr;
            this.d = entryArr2;
        }

        @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
        public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) {
            final Channel channel = (Channel) obj;
            channel.pipeline().addLast(this.b);
            Map.Entry<ChannelOption<?>, Object>[] entryArr = this.c;
            for (Map.Entry<ChannelOption<?>, Object> entry : entryArr) {
                try {
                    if (!channel.config().setOption(entry.getKey(), entry.getValue())) {
                        ServerBootstrap.b.warn("Unknown channel option: " + entry);
                    }
                } catch (Throwable th) {
                    ServerBootstrap.b.warn("Failed to set a channel option: " + channel, th);
                }
            }
            Map.Entry<AttributeKey<?>, Object>[] entryArr2 = this.d;
            for (Map.Entry<AttributeKey<?>, Object> entry2 : entryArr2) {
                channel.attr(entry2.getKey()).set(entry2.getValue());
            }
            try {
                this.a.register(channel).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.bootstrap.ServerBootstrap.a.1
                    /* renamed from: a */
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (!channelFuture.isSuccess()) {
                            a.b(channel, channelFuture.cause());
                        }
                    }
                });
            } catch (Throwable th2) {
                b(channel, th2);
            }
        }

        public static void b(Channel channel, Throwable th) {
            channel.unsafe().closeForcibly();
            InternalLogger internalLogger = ServerBootstrap.b;
            internalLogger.warn("Failed to register an accepted channel: " + channel, th);
        }

        @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
        public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
            final ChannelConfig config = channelHandlerContext.channel().config();
            if (config.isAutoRead()) {
                config.setAutoRead(false);
                channelHandlerContext.channel().eventLoop().schedule((Runnable) new OneTimeTask() { // from class: io.netty.bootstrap.ServerBootstrap.a.2
                    @Override // java.lang.Runnable
                    public void run() {
                        config.setAutoRead(true);
                    }
                }, 1L, TimeUnit.SECONDS);
            }
            channelHandlerContext.fireExceptionCaught(th);
        }
    }

    @Override // io.netty.bootstrap.AbstractBootstrap
    public ServerBootstrap clone() {
        return new ServerBootstrap(this);
    }

    @Deprecated
    public EventLoopGroup childGroup() {
        return this.f;
    }

    public final ChannelHandler i() {
        return this.g;
    }

    public final Map<ChannelOption<?>, Object> j() {
        return a(this.c);
    }

    public final Map<AttributeKey<?>, Object> k() {
        return a(this.d);
    }

    @Override // io.netty.bootstrap.AbstractBootstrap
    public final AbstractBootstrapConfig<ServerBootstrap, ServerChannel> config() {
        return this.e;
    }
}
