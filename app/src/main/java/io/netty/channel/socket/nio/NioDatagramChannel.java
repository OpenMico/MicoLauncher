package io.netty.channel.socket.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.MembershipKey;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.text.Typography;

/* loaded from: classes4.dex */
public final class NioDatagramChannel extends AbstractNioMessageChannel implements DatagramChannel {
    private static final ChannelMetadata g = new ChannelMetadata(true);
    private static final SelectorProvider h = SelectorProvider.provider();
    private static final String i = " (expected: " + StringUtil.simpleClassName((Class<?>) DatagramPacket.class) + ", " + StringUtil.simpleClassName((Class<?>) AddressedEnvelope.class) + Typography.less + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ", " + StringUtil.simpleClassName((Class<?>) SocketAddress.class) + ">, " + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ')';
    private final DatagramChannelConfig j;
    private Map<InetAddress, List<MembershipKey>> k;

    @Override // io.netty.channel.nio.AbstractNioMessageChannel
    protected boolean continueOnWriteError() {
        return true;
    }

    private static java.nio.channels.DatagramChannel a(SelectorProvider selectorProvider) {
        try {
            return selectorProvider.openDatagramChannel();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }

    private static java.nio.channels.DatagramChannel a(SelectorProvider selectorProvider, InternetProtocolFamily internetProtocolFamily) {
        if (internetProtocolFamily == null) {
            return a(selectorProvider);
        }
        c();
        try {
            return selectorProvider.openDatagramChannel(b.a(internetProtocolFamily));
        } catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }

    private static void c() {
        if (PlatformDependent.javaVersion() < 7) {
            throw new UnsupportedOperationException("Only supported on java 7+.");
        }
    }

    public NioDatagramChannel() {
        this(a(h));
    }

    public NioDatagramChannel(SelectorProvider selectorProvider) {
        this(a(selectorProvider));
    }

    public NioDatagramChannel(InternetProtocolFamily internetProtocolFamily) {
        this(a(h, internetProtocolFamily));
    }

    public NioDatagramChannel(SelectorProvider selectorProvider, InternetProtocolFamily internetProtocolFamily) {
        this(a(selectorProvider, internetProtocolFamily));
    }

    public NioDatagramChannel(java.nio.channels.DatagramChannel datagramChannel) {
        super(null, datagramChannel, 1);
        this.j = new a(this, datagramChannel);
    }

    @Override // io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return g;
    }

    @Override // io.netty.channel.Channel
    public DatagramChannelConfig config() {
        return this.j;
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        java.nio.channels.DatagramChannel javaChannel = javaChannel();
        return javaChannel.isOpen() && ((((Boolean) this.j.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || javaChannel.socket().isBound());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public boolean isConnected() {
        return javaChannel().isConnected();
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    public java.nio.channels.DatagramChannel javaChannel() {
        return (java.nio.channels.DatagramChannel) super.javaChannel();
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        return javaChannel().socket().getLocalSocketAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress remoteAddress0() {
        return javaChannel().socket().getRemoteSocketAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        javaChannel().socket().bind(socketAddress);
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            javaChannel().socket().bind(socketAddress2);
        }
        try {
            javaChannel().connect(socketAddress);
            return true;
        } catch (Throwable th) {
            doClose();
            throw th;
        }
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    protected void doFinishConnect() throws Exception {
        throw new Error();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        javaChannel().disconnect();
    }

    @Override // io.netty.channel.nio.AbstractNioChannel, io.netty.channel.AbstractChannel
    protected void doClose() throws Exception {
        javaChannel().close();
    }

    @Override // io.netty.channel.nio.AbstractNioMessageChannel
    protected int doReadMessages(List<Object> list) throws Exception {
        java.nio.channels.DatagramChannel javaChannel = javaChannel();
        DatagramChannelConfig config = config();
        RecvByteBufAllocator.Handle recvBufAllocHandle = unsafe().recvBufAllocHandle();
        ByteBuf allocate = recvBufAllocHandle.allocate(config.getAllocator());
        recvBufAllocHandle.attemptedBytesRead(allocate.writableBytes());
        try {
            ByteBuffer internalNioBuffer = allocate.internalNioBuffer(allocate.writerIndex(), allocate.writableBytes());
            int position = internalNioBuffer.position();
            InetSocketAddress inetSocketAddress = (InetSocketAddress) javaChannel.receive(internalNioBuffer);
            if (inetSocketAddress == null) {
                return 0;
            }
            recvBufAllocHandle.lastBytesRead(internalNioBuffer.position() - position);
            list.add(new DatagramPacket(allocate.writerIndex(allocate.writerIndex() + recvBufAllocHandle.lastBytesRead()), localAddress(), inetSocketAddress));
            return 1;
        } finally {
            allocate.release();
        }
    }

    @Override // io.netty.channel.nio.AbstractNioMessageChannel
    protected boolean doWriteMessage(Object obj, ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        SocketAddress socketAddress;
        ByteBuf byteBuf;
        int i2;
        if (obj instanceof AddressedEnvelope) {
            AddressedEnvelope addressedEnvelope = (AddressedEnvelope) obj;
            socketAddress = addressedEnvelope.recipient();
            byteBuf = (ByteBuf) addressedEnvelope.content();
        } else {
            byteBuf = (ByteBuf) obj;
            socketAddress = null;
        }
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            return true;
        }
        ByteBuffer internalNioBuffer = byteBuf.internalNioBuffer(byteBuf.readerIndex(), readableBytes);
        if (socketAddress != null) {
            i2 = javaChannel().send(internalNioBuffer, socketAddress);
        } else {
            i2 = javaChannel().write(internalNioBuffer);
        }
        return i2 > 0;
    }

    @Override // io.netty.channel.AbstractChannel
    protected Object filterOutboundMessage(Object obj) {
        if (obj instanceof DatagramPacket) {
            DatagramPacket datagramPacket = (DatagramPacket) obj;
            ByteBuf byteBuf = (ByteBuf) datagramPacket.content();
            return a(byteBuf) ? datagramPacket : new DatagramPacket(newDirectBuffer(datagramPacket, byteBuf), datagramPacket.recipient());
        } else if (obj instanceof ByteBuf) {
            ByteBuf byteBuf2 = (ByteBuf) obj;
            return a(byteBuf2) ? byteBuf2 : newDirectBuffer(byteBuf2);
        } else {
            if (obj instanceof AddressedEnvelope) {
                AddressedEnvelope addressedEnvelope = (AddressedEnvelope) obj;
                if (addressedEnvelope.content() instanceof ByteBuf) {
                    ByteBuf byteBuf3 = (ByteBuf) addressedEnvelope.content();
                    return a(byteBuf3) ? addressedEnvelope : new DefaultAddressedEnvelope(newDirectBuffer(addressedEnvelope, byteBuf3), addressedEnvelope.recipient());
                }
            }
            throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(obj) + i);
        }
    }

    private static boolean a(ByteBuf byteBuf) {
        return byteBuf.isDirect() && byteBuf.nioBufferCount() == 1;
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture joinGroup(InetAddress inetAddress) {
        return joinGroup(inetAddress, newPromise());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture joinGroup(InetAddress inetAddress, ChannelPromise channelPromise) {
        try {
            return joinGroup(inetAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), null, channelPromise);
        } catch (SocketException e) {
            channelPromise.setFailure((Throwable) e);
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
        MembershipKey membershipKey;
        c();
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (networkInterface != null) {
            try {
                if (inetAddress2 == null) {
                    membershipKey = javaChannel().join(inetAddress, networkInterface);
                } else {
                    membershipKey = javaChannel().join(inetAddress, networkInterface, inetAddress2);
                }
                synchronized (this) {
                    List<MembershipKey> list = null;
                    if (this.k == null) {
                        this.k = new HashMap();
                    } else {
                        list = this.k.get(inetAddress);
                    }
                    if (list == null) {
                        list = new ArrayList<>();
                        this.k.put(inetAddress, list);
                    }
                    list.add(membershipKey);
                }
                channelPromise.setSuccess();
            } catch (Throwable th) {
                channelPromise.setFailure(th);
            }
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
        } catch (SocketException e) {
            channelPromise.setFailure((Throwable) e);
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
        List<MembershipKey> list;
        c();
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (networkInterface != null) {
            synchronized (this) {
                if (!(this.k == null || (list = this.k.get(inetAddress)) == null)) {
                    Iterator<MembershipKey> it = list.iterator();
                    while (it.hasNext()) {
                        MembershipKey next = it.next();
                        if (networkInterface.equals(next.networkInterface()) && ((inetAddress2 == null && next.sourceAddress() == null) || (inetAddress2 != null && inetAddress2.equals(next.sourceAddress())))) {
                            next.drop();
                            it.remove();
                        }
                    }
                    if (list.isEmpty()) {
                        this.k.remove(inetAddress);
                    }
                }
            }
            channelPromise.setSuccess();
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
        c();
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        } else if (inetAddress2 == null) {
            throw new NullPointerException("sourceToBlock");
        } else if (networkInterface != null) {
            synchronized (this) {
                if (this.k != null) {
                    for (MembershipKey membershipKey : this.k.get(inetAddress)) {
                        if (networkInterface.equals(membershipKey.networkInterface())) {
                            try {
                                membershipKey.block(inetAddress2);
                            } catch (IOException e) {
                                channelPromise.setFailure((Throwable) e);
                            }
                        }
                    }
                }
            }
            channelPromise.setSuccess();
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
        } catch (SocketException e) {
            channelPromise.setFailure((Throwable) e);
            return channelPromise;
        }
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    @Deprecated
    protected void setReadPending(boolean z) {
        super.setReadPending(z);
    }

    public void b() {
        clearReadPending();
    }
}
