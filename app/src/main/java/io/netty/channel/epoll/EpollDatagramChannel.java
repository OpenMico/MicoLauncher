package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.epoll.f;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.List;
import kotlin.text.Typography;

/* loaded from: classes4.dex */
public final class EpollDatagramChannel extends AbstractEpollChannel implements DatagramChannel {
    static final /* synthetic */ boolean c = !EpollDatagramChannel.class.desiredAssertionStatus();
    private static final ChannelMetadata d = new ChannelMetadata(true);
    private static final String e = " (expected: " + StringUtil.simpleClassName((Class<?>) DatagramPacket.class) + ", " + StringUtil.simpleClassName((Class<?>) AddressedEnvelope.class) + Typography.less + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ", " + StringUtil.simpleClassName((Class<?>) InetSocketAddress.class) + ">, " + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ')';
    private volatile InetSocketAddress f;
    private volatile InetSocketAddress g;
    private volatile boolean h;
    private final EpollDatagramChannelConfig i;

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.Channel
    public /* bridge */ /* synthetic */ boolean isOpen() {
        return super.isOpen();
    }

    public EpollDatagramChannel() {
        super(Socket.newSocketDgram(), Native.EPOLLIN);
        this.i = new EpollDatagramChannelConfig(this);
    }

    @Deprecated
    public EpollDatagramChannel(FileDescriptor fileDescriptor) {
        this(new Socket(fileDescriptor.intValue()));
    }

    public EpollDatagramChannel(Socket socket) {
        super(null, socket, Native.EPOLLIN, true);
        this.f = socket.localAddress();
        this.i = new EpollDatagramChannelConfig(this);
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return d;
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.Channel
    public boolean isActive() {
        return fd().isOpen() && ((((Boolean) this.i.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || this.active);
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public boolean isConnected() {
        return this.h;
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture joinGroup(InetAddress inetAddress) {
        return joinGroup(inetAddress, newPromise());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture joinGroup(InetAddress inetAddress, ChannelPromise channelPromise) {
        try {
            return joinGroup(inetAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), null, channelPromise);
        } catch (SocketException e2) {
            channelPromise.setFailure((Throwable) e2);
            return channelPromise;
        }
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface) {
        return joinGroup(inetSocketAddress, networkInterface, newPromise());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise) {
        return joinGroup(inetSocketAddress.getAddress(), networkInterface, null, channelPromise);
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture joinGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return joinGroup(inetAddress, networkInterface, inetAddress2, newPromise());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture joinGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (networkInterface != null) {
            channelPromise.setFailure((Throwable) new UnsupportedOperationException("Multicast not supported"));
            return channelPromise;
        } else {
            throw new NullPointerException("networkInterface");
        }
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture leaveGroup(InetAddress inetAddress) {
        return leaveGroup(inetAddress, newPromise());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture leaveGroup(InetAddress inetAddress, ChannelPromise channelPromise) {
        try {
            return leaveGroup(inetAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), null, channelPromise);
        } catch (SocketException e2) {
            channelPromise.setFailure((Throwable) e2);
            return channelPromise;
        }
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface) {
        return leaveGroup(inetSocketAddress, networkInterface, newPromise());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise) {
        return leaveGroup(inetSocketAddress.getAddress(), networkInterface, null, channelPromise);
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture leaveGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return leaveGroup(inetAddress, networkInterface, inetAddress2, newPromise());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture leaveGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (networkInterface != null) {
            channelPromise.setFailure((Throwable) new UnsupportedOperationException("Multicast not supported"));
            return channelPromise;
        } else {
            throw new NullPointerException("networkInterface");
        }
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return block(inetAddress, networkInterface, inetAddress2, newPromise());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (inetAddress2 == null) {
            throw new NullPointerException("sourceToBlock");
        } else if (networkInterface != null) {
            channelPromise.setFailure((Throwable) new UnsupportedOperationException("Multicast not supported"));
            return channelPromise;
        } else {
            throw new NullPointerException("networkInterface");
        }
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture block(InetAddress inetAddress, InetAddress inetAddress2) {
        return block(inetAddress, inetAddress2, newPromise());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture block(InetAddress inetAddress, InetAddress inetAddress2, ChannelPromise channelPromise) {
        try {
            return block(inetAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), inetAddress2, channelPromise);
        } catch (Throwable th) {
            channelPromise.setFailure(th);
            return channelPromise;
        }
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.AbstractChannel
    public AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
        return new a();
    }

    @Override // io.netty.channel.AbstractChannel
    public InetSocketAddress localAddress0() {
        return this.f;
    }

    @Override // io.netty.channel.AbstractChannel
    public InetSocketAddress remoteAddress0() {
        return this.g;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
        checkResolvable(inetSocketAddress);
        fd().bind(inetSocketAddress);
        this.f = fd().localAddress();
        this.active = true;
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        f a2;
        int a3;
        while (true) {
            Object current = channelOutboundBuffer.current();
            if (current == null) {
                b(Native.EPOLLOUT);
                return;
            }
            try {
                boolean z = false;
                if (!Native.IS_SUPPORTING_SENDMMSG || channelOutboundBuffer.size() <= 1 || (a3 = (a2 = f.a(channelOutboundBuffer)).a()) < 1) {
                    int writeSpinCount = config().getWriteSpinCount() - 1;
                    while (true) {
                        if (writeSpinCount < 0) {
                            break;
                        } else if (a(current)) {
                            z = true;
                            break;
                        } else {
                            writeSpinCount--;
                        }
                    }
                    if (z) {
                        channelOutboundBuffer.remove();
                    } else {
                        a(Native.EPOLLOUT);
                        return;
                    }
                } else {
                    f.a[] b = a2.b();
                    int i = 0;
                    while (a3 > 0) {
                        int sendmmsg = Native.sendmmsg(fd().intValue(), b, i, a3);
                        if (sendmmsg == 0) {
                            a(Native.EPOLLOUT);
                            return;
                        }
                        for (int i2 = 0; i2 < sendmmsg; i2++) {
                            channelOutboundBuffer.remove();
                        }
                        a3 -= sendmmsg;
                        i += sendmmsg;
                    }
                    continue;
                }
            } catch (IOException e2) {
                channelOutboundBuffer.remove(e2);
            }
        }
    }

    private boolean a(Object obj) throws Exception {
        InetSocketAddress inetSocketAddress;
        ByteBuf byteBuf;
        int i;
        if (obj instanceof AddressedEnvelope) {
            AddressedEnvelope addressedEnvelope = (AddressedEnvelope) obj;
            byteBuf = (ByteBuf) addressedEnvelope.content();
            inetSocketAddress = (InetSocketAddress) addressedEnvelope.recipient();
        } else {
            byteBuf = (ByteBuf) obj;
            inetSocketAddress = null;
        }
        if (byteBuf.readableBytes() == 0) {
            return true;
        }
        if (inetSocketAddress == null && (inetSocketAddress = this.g) == null) {
            throw new NotYetConnectedException();
        }
        if (byteBuf.hasMemoryAddress()) {
            i = fd().sendToAddress(byteBuf.memoryAddress(), byteBuf.readerIndex(), byteBuf.writerIndex(), inetSocketAddress.getAddress(), inetSocketAddress.getPort());
        } else if (byteBuf instanceof CompositeByteBuf) {
            e a2 = ((b) eventLoop()).a();
            a2.a(byteBuf);
            int b = a2.b();
            if (c || b != 0) {
                i = fd().sendToAddresses(a2.a(0), b, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
            } else {
                throw new AssertionError();
            }
        } else {
            ByteBuffer internalNioBuffer = byteBuf.internalNioBuffer(byteBuf.readerIndex(), byteBuf.readableBytes());
            i = fd().sendTo(internalNioBuffer, internalNioBuffer.position(), internalNioBuffer.limit(), inetSocketAddress.getAddress(), inetSocketAddress.getPort());
        }
        return i > 0;
    }

    @Override // io.netty.channel.AbstractChannel
    protected Object filterOutboundMessage(Object obj) {
        if (obj instanceof DatagramPacket) {
            DatagramPacket datagramPacket = (DatagramPacket) obj;
            ByteBuf byteBuf = (ByteBuf) datagramPacket.content();
            if (byteBuf.hasMemoryAddress()) {
                return obj;
            }
            if (byteBuf.isDirect() && (byteBuf instanceof CompositeByteBuf)) {
                CompositeByteBuf compositeByteBuf = (CompositeByteBuf) byteBuf;
                if (compositeByteBuf.isDirect() && compositeByteBuf.nioBufferCount() <= Native.IOV_MAX) {
                    return obj;
                }
            }
            return new DatagramPacket(newDirectBuffer(datagramPacket, byteBuf), datagramPacket.recipient());
        } else if (obj instanceof ByteBuf) {
            ByteBuf byteBuf2 = (ByteBuf) obj;
            if (!byteBuf2.hasMemoryAddress() && (PlatformDependent.hasUnsafe() || !byteBuf2.isDirect())) {
                if (byteBuf2 instanceof CompositeByteBuf) {
                    CompositeByteBuf compositeByteBuf2 = (CompositeByteBuf) byteBuf2;
                    if (!compositeByteBuf2.isDirect() || compositeByteBuf2.nioBufferCount() > Native.IOV_MAX) {
                        byteBuf2 = newDirectBuffer(byteBuf2);
                        if (!c && !byteBuf2.hasMemoryAddress()) {
                            throw new AssertionError();
                        }
                    }
                } else {
                    byteBuf2 = newDirectBuffer(byteBuf2);
                    if (!c && !byteBuf2.hasMemoryAddress()) {
                        throw new AssertionError();
                    }
                }
            }
            return byteBuf2;
        } else {
            if (obj instanceof AddressedEnvelope) {
                AddressedEnvelope addressedEnvelope = (AddressedEnvelope) obj;
                if ((addressedEnvelope.content() instanceof ByteBuf) && (addressedEnvelope.recipient() == null || (addressedEnvelope.recipient() instanceof InetSocketAddress))) {
                    ByteBuf byteBuf3 = (ByteBuf) addressedEnvelope.content();
                    if (byteBuf3.hasMemoryAddress()) {
                        return addressedEnvelope;
                    }
                    if (byteBuf3 instanceof CompositeByteBuf) {
                        CompositeByteBuf compositeByteBuf3 = (CompositeByteBuf) byteBuf3;
                        if (compositeByteBuf3.isDirect() && compositeByteBuf3.nioBufferCount() <= Native.IOV_MAX) {
                            return addressedEnvelope;
                        }
                    }
                    return new DefaultAddressedEnvelope(newDirectBuffer(addressedEnvelope, byteBuf3), (InetSocketAddress) addressedEnvelope.recipient());
                }
            }
            throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(obj) + e);
        }
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.Channel
    public EpollDatagramChannelConfig config() {
        return this.i;
    }

    @Override // io.netty.channel.epoll.AbstractEpollChannel, io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        this.h = false;
    }

    /* loaded from: classes4.dex */
    public final class a extends AbstractEpollChannel.AbstractEpollUnsafe {
        static final /* synthetic */ boolean h = !EpollDatagramChannel.class.desiredAssertionStatus();
        private final List<Object> j = new ArrayList();

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a() {
            super();
            EpollDatagramChannel.this = r1;
        }

        @Override // io.netty.channel.Channel.Unsafe
        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            Throwable th;
            try {
                boolean z = false;
                try {
                    boolean isActive = EpollDatagramChannel.this.isActive();
                    InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
                    if (socketAddress2 != null) {
                        EpollDatagramChannel.this.doBind((InetSocketAddress) socketAddress2);
                    }
                    AbstractEpollChannel.checkResolvable(inetSocketAddress);
                    EpollDatagramChannel.this.g = inetSocketAddress;
                    EpollDatagramChannel.this.f = EpollDatagramChannel.this.fd().localAddress();
                    try {
                        channelPromise.trySuccess();
                        if (!isActive && EpollDatagramChannel.this.isActive()) {
                            EpollDatagramChannel.this.pipeline().fireChannelActive();
                        }
                        EpollDatagramChannel.this.h = true;
                    } catch (Throwable th2) {
                        th = th2;
                        z = true;
                        if (!z) {
                            EpollDatagramChannel.this.doClose();
                        } else {
                            EpollDatagramChannel.this.h = true;
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Throwable th4) {
                channelPromise.tryFailure(th4);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x0095, code lost:
            r1.lastBytesRead(-1);
            r5.release();
         */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00e1 A[Catch: all -> 0x00cd, LOOP:1: B:32:0x00df->B:33:0x00e1, LOOP_END, TryCatch #2 {all -> 0x00cd, blocks: (B:14:0x004d, B:15:0x0051, B:17:0x005e, B:18:0x0075, B:20:0x0095, B:21:0x009d, B:22:0x00c4, B:30:0x00d4, B:31:0x00d7, B:33:0x00e1, B:34:0x00ef, B:36:0x00fc), top: B:43:0x004d }] */
        /* JADX WARN: Removed duplicated region for block: B:36:0x00fc A[Catch: all -> 0x00cd, TRY_LEAVE, TryCatch #2 {all -> 0x00cd, blocks: (B:14:0x004d, B:15:0x0051, B:17:0x005e, B:18:0x0075, B:20:0x0095, B:21:0x009d, B:22:0x00c4, B:30:0x00d4, B:31:0x00d7, B:33:0x00e1, B:34:0x00ef, B:36:0x00fc), top: B:43:0x004d }] */
        @Override // io.netty.channel.epoll.AbstractEpollChannel.AbstractEpollUnsafe
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a() {
            /*
                Method dump skipped, instructions count: 263
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.epoll.EpollDatagramChannel.a.a():void");
        }
    }
}
