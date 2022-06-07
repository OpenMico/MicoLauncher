package io.netty.channel.epoll;

import io.netty.channel.Channel;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.epoll.AbstractEpollStreamChannel;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.channel.unix.DomainSocketChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public final class EpollDomainSocketChannel extends AbstractEpollStreamChannel implements DomainSocketChannel {
    private final EpollDomainSocketChannelConfig e = new EpollDomainSocketChannelConfig(this);
    private volatile DomainSocketAddress f;
    private volatile DomainSocketAddress g;

    public EpollDomainSocketChannel() {
        super(Socket.newSocketDomain(), false);
    }

    @Deprecated
    public EpollDomainSocketChannel(Channel channel, FileDescriptor fileDescriptor) {
        super(channel, new Socket(fileDescriptor.intValue()));
    }

    @Deprecated
    public EpollDomainSocketChannel(FileDescriptor fileDescriptor) {
        super(fileDescriptor);
    }

    public EpollDomainSocketChannel(Channel channel, Socket socket) {
        super(channel, socket);
    }

    public EpollDomainSocketChannel(Socket socket, boolean z) {
        super(socket, z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.epoll.AbstractEpollStreamChannel, io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.AbstractChannel
    public AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
        return new a();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.AbstractChannel
    public DomainSocketAddress localAddress0() {
        return this.f;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.AbstractChannel
    public DomainSocketAddress remoteAddress0() {
        return this.g;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        fd().bind(socketAddress);
        this.f = (DomainSocketAddress) socketAddress;
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.Channel
    public EpollDomainSocketChannelConfig config() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.epoll.AbstractEpollStreamChannel
    public boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (!super.doConnect(socketAddress, socketAddress2)) {
            return false;
        }
        this.f = (DomainSocketAddress) socketAddress2;
        this.g = (DomainSocketAddress) socketAddress;
        return true;
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public DomainSocketAddress remoteAddress() {
        return (DomainSocketAddress) super.remoteAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public DomainSocketAddress localAddress() {
        return (DomainSocketAddress) super.localAddress();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.epoll.AbstractEpollStreamChannel
    public boolean doWriteSingle(ChannelOutboundBuffer channelOutboundBuffer, int i) throws Exception {
        Object current = channelOutboundBuffer.current();
        if (!(current instanceof FileDescriptor) || Native.sendFd(fd().intValue(), ((FileDescriptor) current).intValue()) <= 0) {
            return super.doWriteSingle(channelOutboundBuffer, i);
        }
        channelOutboundBuffer.remove();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.epoll.AbstractEpollStreamChannel, io.netty.channel.AbstractChannel
    public Object filterOutboundMessage(Object obj) {
        return obj instanceof FileDescriptor ? obj : super.filterOutboundMessage(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a extends AbstractEpollStreamChannel.a {
        private a() {
            super();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // io.netty.channel.epoll.AbstractEpollStreamChannel.a, io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe
        public void a() {
            switch (EpollDomainSocketChannel.this.config().getReadMode()) {
                case BYTES:
                    super.a();
                    return;
                case FILE_DESCRIPTORS:
                    h();
                    return;
                default:
                    throw new Error();
            }
        }

        private void h() {
            if (EpollDomainSocketChannel.this.fd().isInputShutdown()) {
                clearEpollIn0();
                return;
            }
            EpollDomainSocketChannelConfig config = EpollDomainSocketChannel.this.config();
            c recvBufAllocHandle = recvBufAllocHandle();
            recvBufAllocHandle.a(EpollDomainSocketChannel.this.c(Native.EPOLLET));
            ChannelPipeline pipeline = EpollDomainSocketChannel.this.pipeline();
            recvBufAllocHandle.reset(config);
            b();
            do {
                try {
                    recvBufAllocHandle.lastBytesRead(Native.recvFd(EpollDomainSocketChannel.this.fd().intValue()));
                    switch (recvBufAllocHandle.lastBytesRead()) {
                        case -1:
                            close(voidPromise());
                            return;
                        case 0:
                            recvBufAllocHandle.readComplete();
                            pipeline.fireChannelReadComplete();
                        default:
                            recvBufAllocHandle.incMessagesRead(1);
                            this.c = false;
                            pipeline.fireChannelRead((Object) new FileDescriptor(recvBufAllocHandle.lastBytesRead()));
                            break;
                    }
                } finally {
                    a(config);
                }
            } while (recvBufAllocHandle.continueReading());
            recvBufAllocHandle.readComplete();
            pipeline.fireChannelReadComplete();
        }
    }
}
