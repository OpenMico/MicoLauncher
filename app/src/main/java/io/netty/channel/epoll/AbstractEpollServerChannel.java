package io.netty.channel.epoll;

import io.netty.channel.Channel;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public abstract class AbstractEpollServerChannel extends AbstractEpollChannel implements ServerChannel {
    private static final ChannelMetadata c = new ChannelMetadata(false, 16);

    abstract Channel newChildChannel(int i, byte[] bArr, int i2, int i3) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.AbstractChannel
    public InetSocketAddress remoteAddress0() {
        return null;
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.Channel
    public /* bridge */ /* synthetic */ boolean isActive() {
        return super.isActive();
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.Channel
    public /* bridge */ /* synthetic */ boolean isOpen() {
        return super.isOpen();
    }

    @Deprecated
    protected AbstractEpollServerChannel(int i) {
        this(new Socket(i), false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Deprecated
    public AbstractEpollServerChannel(FileDescriptor fileDescriptor) {
        this(new Socket(fileDescriptor.intValue()));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Deprecated
    public AbstractEpollServerChannel(Socket socket) {
        this(socket, a(socket));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public AbstractEpollServerChannel(Socket socket, boolean z) {
        super(null, socket, Native.EPOLLIN, z);
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return c;
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.AbstractChannel
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof b;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.AbstractChannel
    public AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
        return new a();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.channel.AbstractChannel
    protected Object filterOutboundMessage(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public final class a extends AbstractEpollChannel.AbstractEpollUnsafe {
        static final /* synthetic */ boolean h = !AbstractEpollServerChannel.class.desiredAssertionStatus();
        private final byte[] j = new byte[26];

        a() {
            super();
        }

        @Override // io.netty.channel.Channel.Unsafe
        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            channelPromise.setFailure((Throwable) new UnsupportedOperationException());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe
        public void a() {
            if (!h && !AbstractEpollServerChannel.this.eventLoop().inEventLoop()) {
                throw new AssertionError();
            } else if (AbstractEpollServerChannel.this.fd().isInputShutdown()) {
                clearEpollIn0();
            } else {
                EpollChannelConfig config = AbstractEpollServerChannel.this.config();
                c recvBufAllocHandle = recvBufAllocHandle();
                recvBufAllocHandle.a(AbstractEpollServerChannel.this.c(Native.EPOLLET));
                ChannelPipeline pipeline = AbstractEpollServerChannel.this.pipeline();
                recvBufAllocHandle.reset(config);
                b();
                Throwable th = null;
                do {
                    try {
                        try {
                            recvBufAllocHandle.lastBytesRead(AbstractEpollServerChannel.this.fd().accept(this.j));
                            if (recvBufAllocHandle.lastBytesRead() == -1) {
                                break;
                            }
                            recvBufAllocHandle.incMessagesRead(1);
                            byte b = this.j[0];
                            this.c = false;
                            pipeline.fireChannelRead((Object) AbstractEpollServerChannel.this.newChildChannel(recvBufAllocHandle.lastBytesRead(), this.j, 1, b));
                        } finally {
                            a(config);
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } while (recvBufAllocHandle.continueReading());
                recvBufAllocHandle.readComplete();
                pipeline.fireChannelReadComplete();
                if (th != null) {
                    pipeline.fireExceptionCaught(th);
                }
            }
        }
    }
}
