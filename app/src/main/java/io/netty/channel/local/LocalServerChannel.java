package io.netty.channel.local;

import io.netty.channel.AbstractServerChannel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.EventLoop;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import java.net.SocketAddress;
import java.util.ArrayDeque;
import java.util.Queue;

/* loaded from: classes4.dex */
public class LocalServerChannel extends AbstractServerChannel {
    private final ChannelConfig c = new DefaultChannelConfig(this);
    private final Queue<Object> d = new ArrayDeque();
    private final Runnable e = new Runnable() { // from class: io.netty.channel.local.LocalServerChannel.1
        @Override // java.lang.Runnable
        public void run() {
            LocalServerChannel.this.unsafe().close(LocalServerChannel.this.unsafe().voidPromise());
        }
    };
    private volatile int f;
    private volatile LocalAddress g;
    private volatile boolean h;

    @Override // io.netty.channel.Channel
    public ChannelConfig config() {
        return this.c;
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public LocalAddress localAddress() {
        return (LocalAddress) super.localAddress();
    }

    @Override // io.netty.channel.AbstractServerChannel, io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public LocalAddress remoteAddress() {
        return (LocalAddress) super.remoteAddress();
    }

    @Override // io.netty.channel.Channel
    public boolean isOpen() {
        return this.f < 2;
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        return this.f == 1;
    }

    @Override // io.netty.channel.AbstractChannel
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof SingleThreadEventLoop;
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        return this.g;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doRegister() throws Exception {
        ((SingleThreadEventExecutor) eventLoop()).addShutdownHook(this.e);
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.g = a.a(this, this.g, socketAddress);
        this.f = 1;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doClose() throws Exception {
        if (this.f <= 1) {
            if (this.g != null) {
                a.a(this.g);
                this.g = null;
            }
            this.f = 2;
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDeregister() throws Exception {
        ((SingleThreadEventExecutor) eventLoop()).removeShutdownHook(this.e);
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBeginRead() throws Exception {
        if (!this.h) {
            Queue<Object> queue = this.d;
            if (queue.isEmpty()) {
                this.h = true;
                return;
            }
            ChannelPipeline pipeline = pipeline();
            while (true) {
                Object poll = queue.poll();
                if (poll == null) {
                    pipeline.fireChannelReadComplete();
                    return;
                }
                pipeline.fireChannelRead(poll);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LocalChannel a(LocalChannel localChannel) {
        final LocalChannel localChannel2 = new LocalChannel(this, localChannel);
        if (eventLoop().inEventLoop()) {
            b(localChannel2);
        } else {
            eventLoop().execute(new Runnable() { // from class: io.netty.channel.local.LocalServerChannel.2
                @Override // java.lang.Runnable
                public void run() {
                    LocalServerChannel.this.b(localChannel2);
                }
            });
        }
        return localChannel2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(LocalChannel localChannel) {
        this.d.add(localChannel);
        if (this.h) {
            this.h = false;
            ChannelPipeline pipeline = pipeline();
            while (true) {
                Object poll = this.d.poll();
                if (poll == null) {
                    pipeline.fireChannelReadComplete();
                    return;
                }
                pipeline.fireChannelRead(poll);
            }
        }
    }
}
