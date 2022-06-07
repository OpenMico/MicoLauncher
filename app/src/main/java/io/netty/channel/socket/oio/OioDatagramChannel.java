package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.oio.AbstractOioMessageChannel;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.DefaultDatagramChannelConfig;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Locale;
import kotlin.text.Typography;

/* loaded from: classes4.dex */
public class OioDatagramChannel extends AbstractOioMessageChannel implements DatagramChannel {
    private static final InternalLogger d = InternalLoggerFactory.getInstance(OioDatagramChannel.class);
    private static final ChannelMetadata e = new ChannelMetadata(true);
    private static final String f = " (expected: " + StringUtil.simpleClassName((Class<?>) DatagramPacket.class) + ", " + StringUtil.simpleClassName((Class<?>) AddressedEnvelope.class) + Typography.less + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ", " + StringUtil.simpleClassName((Class<?>) SocketAddress.class) + ">, " + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ')';
    private final MulticastSocket g;
    private final DatagramChannelConfig h;
    private final java.net.DatagramPacket i;

    private static MulticastSocket b() {
        try {
            return new MulticastSocket((SocketAddress) null);
        } catch (Exception e2) {
            throw new ChannelException("failed to create a new socket", e2);
        }
    }

    public OioDatagramChannel() {
        this(b());
    }

    public OioDatagramChannel(MulticastSocket multicastSocket) {
        try {
            super(null);
            this.i = new java.net.DatagramPacket(EmptyArrays.EMPTY_BYTES, 0);
            try {
                multicastSocket.setSoTimeout(1000);
                multicastSocket.setBroadcast(false);
                this.g = multicastSocket;
                this.h = new DefaultDatagramChannelConfig(this, multicastSocket);
            } catch (SocketException e2) {
                throw new ChannelException("Failed to configure the datagram socket timeout.", e2);
            }
        } catch (Throwable th) {
            multicastSocket.close();
            throw th;
        }
    }

    @Override // io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return e;
    }

    @Override // io.netty.channel.Channel
    public DatagramChannelConfig config() {
        return this.h;
    }

    @Override // io.netty.channel.Channel
    public boolean isOpen() {
        return !this.g.isClosed();
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        return isOpen() && ((((Boolean) this.h.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || this.g.isBound());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public boolean isConnected() {
        return this.g.isConnected();
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        return this.g.getLocalSocketAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress remoteAddress0() {
        return this.g.getRemoteSocketAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.g.bind(socketAddress);
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    @Override // io.netty.channel.oio.AbstractOioChannel
    protected void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            this.g.bind(socketAddress2);
        }
        try {
            this.g.connect(socketAddress);
        } catch (Throwable th) {
            try {
                this.g.close();
            } catch (Throwable th2) {
                d.warn("Failed to close a socket.", th2);
            }
            throw th;
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        this.g.disconnect();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doClose() throws Exception {
        this.g.close();
    }

    @Override // io.netty.channel.oio.AbstractOioMessageChannel
    protected int doReadMessages(List<Object> list) throws Exception {
        ByteBuf heapBuffer;
        try {
            DatagramChannelConfig config = config();
            RecvByteBufAllocator.Handle recvBufAllocHandle = unsafe().recvBufAllocHandle();
            heapBuffer = config.getAllocator().heapBuffer(recvBufAllocHandle.guess());
            try {
                try {
                    this.i.setData(heapBuffer.array(), heapBuffer.arrayOffset(), heapBuffer.capacity());
                    this.g.receive(this.i);
                    recvBufAllocHandle.lastBytesRead(this.i.getLength());
                    list.add(new DatagramPacket(heapBuffer.writerIndex(recvBufAllocHandle.lastBytesRead()), localAddress(), (InetSocketAddress) this.i.getSocketAddress()));
                    return 1;
                } catch (SocketException e2) {
                    if (e2.getMessage().toLowerCase(Locale.US).contains("socket closed")) {
                        heapBuffer.release();
                        return -1;
                    }
                    throw e2;
                }
            } catch (SocketTimeoutException unused) {
                heapBuffer.release();
                return 0;
            } catch (Throwable th) {
                PlatformDependent.throwException(th);
                heapBuffer.release();
                return -1;
            }
        } catch (Throwable th2) {
            heapBuffer.release();
            throw th2;
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        SocketAddress socketAddress;
        ByteBuf byteBuf;
        while (true) {
            Object current = channelOutboundBuffer.current();
            if (current != null) {
                if (current instanceof AddressedEnvelope) {
                    AddressedEnvelope addressedEnvelope = (AddressedEnvelope) current;
                    socketAddress = addressedEnvelope.recipient();
                    byteBuf = (ByteBuf) addressedEnvelope.content();
                } else {
                    byteBuf = (ByteBuf) current;
                    socketAddress = null;
                }
                int readableBytes = byteBuf.readableBytes();
                if (socketAddress != null) {
                    this.i.setSocketAddress(socketAddress);
                }
                if (byteBuf.hasArray()) {
                    this.i.setData(byteBuf.array(), byteBuf.arrayOffset() + byteBuf.readerIndex(), readableBytes);
                } else {
                    byte[] bArr = new byte[readableBytes];
                    byteBuf.getBytes(byteBuf.readerIndex(), bArr);
                    this.i.setData(bArr);
                }
                try {
                    this.g.send(this.i);
                    channelOutboundBuffer.remove();
                } catch (IOException e2) {
                    channelOutboundBuffer.remove(e2);
                }
            } else {
                return;
            }
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected Object filterOutboundMessage(Object obj) {
        if ((obj instanceof DatagramPacket) || (obj instanceof ByteBuf)) {
            return obj;
        }
        if ((obj instanceof AddressedEnvelope) && (((AddressedEnvelope) obj).content() instanceof ByteBuf)) {
            return obj;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(obj) + f);
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture joinGroup(InetAddress inetAddress) {
        return joinGroup(inetAddress, newPromise());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture joinGroup(InetAddress inetAddress, ChannelPromise channelPromise) {
        c();
        try {
            this.g.joinGroup(inetAddress);
            channelPromise.setSuccess();
        } catch (IOException e2) {
            channelPromise.setFailure((Throwable) e2);
        }
        return channelPromise;
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface) {
        return joinGroup(inetSocketAddress, networkInterface, newPromise());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise) {
        c();
        try {
            this.g.joinGroup(inetSocketAddress, networkInterface);
            channelPromise.setSuccess();
        } catch (IOException e2) {
            channelPromise.setFailure((Throwable) e2);
        }
        return channelPromise;
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture joinGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture joinGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        channelPromise.setFailure((Throwable) new UnsupportedOperationException());
        return channelPromise;
    }

    private void c() {
        if (!isActive()) {
            throw new IllegalStateException(DatagramChannel.class.getName() + " must be bound to join a group.");
        }
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture leaveGroup(InetAddress inetAddress) {
        return leaveGroup(inetAddress, newPromise());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture leaveGroup(InetAddress inetAddress, ChannelPromise channelPromise) {
        try {
            this.g.leaveGroup(inetAddress);
            channelPromise.setSuccess();
        } catch (IOException e2) {
            channelPromise.setFailure((Throwable) e2);
        }
        return channelPromise;
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface) {
        return leaveGroup(inetSocketAddress, networkInterface, newPromise());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise) {
        try {
            this.g.leaveGroup(inetSocketAddress, networkInterface);
            channelPromise.setSuccess();
        } catch (IOException e2) {
            channelPromise.setFailure((Throwable) e2);
        }
        return channelPromise;
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture leaveGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture leaveGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        channelPromise.setFailure((Throwable) new UnsupportedOperationException());
        return channelPromise;
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        channelPromise.setFailure((Throwable) new UnsupportedOperationException());
        return channelPromise;
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture block(InetAddress inetAddress, InetAddress inetAddress2) {
        return newFailedFuture(new UnsupportedOperationException());
    }

    @Override // io.netty.channel.socket.DatagramChannel
    public ChannelFuture block(InetAddress inetAddress, InetAddress inetAddress2, ChannelPromise channelPromise) {
        channelPromise.setFailure((Throwable) new UnsupportedOperationException());
        return channelPromise;
    }
}
