package io.netty.channel.oio;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.ThreadPerChannelEventLoop;
import io.netty.util.internal.OneTimeTask;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public abstract class AbstractOioChannel extends AbstractChannel {
    protected static final int SO_TIMEOUT = 1000;
    boolean c;
    private final Runnable d = new Runnable() { // from class: io.netty.channel.oio.AbstractOioChannel.1
        @Override // java.lang.Runnable
        public void run() {
            AbstractOioChannel.this.doRead();
        }
    };
    private final Runnable e = new Runnable() { // from class: io.netty.channel.oio.AbstractOioChannel.2
        @Override // java.lang.Runnable
        public void run() {
            AbstractOioChannel.this.c = false;
        }
    };

    protected abstract void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception;

    protected abstract void doRead();

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractOioChannel(Channel channel) {
        super(channel);
    }

    @Override // io.netty.channel.AbstractChannel
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return new a();
    }

    /* loaded from: classes4.dex */
    private final class a extends AbstractChannel.AbstractUnsafe {
        private a() {
            super();
        }

        @Override // io.netty.channel.Channel.Unsafe
        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            if (channelPromise.setUncancellable() && ensureOpen(channelPromise)) {
                try {
                    boolean isActive = AbstractOioChannel.this.isActive();
                    AbstractOioChannel.this.doConnect(socketAddress, socketAddress2);
                    safeSetSuccess(channelPromise);
                    if (!isActive && AbstractOioChannel.this.isActive()) {
                        AbstractOioChannel.this.pipeline().fireChannelActive();
                    }
                } catch (Throwable th) {
                    safeSetFailure(channelPromise, annotateConnectException(th, socketAddress));
                    closeIfClosed();
                }
            }
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof ThreadPerChannelEventLoop;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBeginRead() throws Exception {
        if (!this.c) {
            this.c = true;
            eventLoop().execute(this.d);
        }
    }

    @Deprecated
    protected boolean isReadPending() {
        return this.c;
    }

    @Deprecated
    protected void setReadPending(final boolean z) {
        if (isRegistered()) {
            EventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                this.c = z;
            } else {
                eventLoop.execute(new OneTimeTask() { // from class: io.netty.channel.oio.AbstractOioChannel.3
                    @Override // java.lang.Runnable
                    public void run() {
                        AbstractOioChannel.this.c = z;
                    }
                });
            }
        } else {
            this.c = z;
        }
    }

    protected final void clearReadPending() {
        if (isRegistered()) {
            EventLoop eventLoop = eventLoop();
            if (eventLoop.inEventLoop()) {
                this.c = false;
            } else {
                eventLoop.execute(this.e);
            }
        } else {
            this.c = false;
        }
    }
}
