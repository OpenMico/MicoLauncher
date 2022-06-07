package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

/* loaded from: classes4.dex */
public class DatagramPacketEncoder<M> extends MessageToMessageEncoder<AddressedEnvelope<M, InetSocketAddress>> {
    static final /* synthetic */ boolean a = !DatagramPacketEncoder.class.desiredAssertionStatus();
    private final MessageToMessageEncoder<? super M> b;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, Object obj, List list) throws Exception {
        encode(channelHandlerContext, (AddressedEnvelope) ((AddressedEnvelope) obj), (List<Object>) list);
    }

    public DatagramPacketEncoder(MessageToMessageEncoder<? super M> messageToMessageEncoder) {
        this.b = (MessageToMessageEncoder) ObjectUtil.checkNotNull(messageToMessageEncoder, "encoder");
    }

    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public boolean acceptOutboundMessage(Object obj) throws Exception {
        if (!super.acceptOutboundMessage(obj)) {
            return false;
        }
        AddressedEnvelope addressedEnvelope = (AddressedEnvelope) obj;
        return this.b.acceptOutboundMessage(addressedEnvelope.content()) && (addressedEnvelope.sender() instanceof InetSocketAddress) && (addressedEnvelope.recipient() instanceof InetSocketAddress);
    }

    protected void encode(ChannelHandlerContext channelHandlerContext, AddressedEnvelope<M, InetSocketAddress> addressedEnvelope, List<Object> list) throws Exception {
        if (a || list.isEmpty()) {
            this.b.encode(channelHandlerContext, addressedEnvelope.content(), list);
            if (list.size() == 1) {
                Object obj = list.get(0);
                if (obj instanceof ByteBuf) {
                    list.set(0, new DatagramPacket((ByteBuf) obj, addressedEnvelope.recipient(), addressedEnvelope.sender()));
                    return;
                }
                throw new EncoderException(StringUtil.simpleClassName(this.b) + " must produce only ByteBuf.");
            }
            throw new EncoderException(StringUtil.simpleClassName(this.b) + " must produce only one message.");
        }
        throw new AssertionError();
    }

    @Override // io.netty.channel.ChannelOutboundHandlerAdapter, io.netty.channel.ChannelOutboundHandler
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        this.b.bind(channelHandlerContext, socketAddress, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandlerAdapter, io.netty.channel.ChannelOutboundHandler
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        this.b.connect(channelHandlerContext, socketAddress, socketAddress2, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandlerAdapter, io.netty.channel.ChannelOutboundHandler
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        this.b.disconnect(channelHandlerContext, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandlerAdapter, io.netty.channel.ChannelOutboundHandler
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        this.b.close(channelHandlerContext, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandlerAdapter, io.netty.channel.ChannelOutboundHandler
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        this.b.deregister(channelHandlerContext, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandlerAdapter, io.netty.channel.ChannelOutboundHandler
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.b.read(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelOutboundHandlerAdapter, io.netty.channel.ChannelOutboundHandler
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.b.flush(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.b.handlerAdded(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.b.handlerRemoved(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        this.b.exceptionCaught(channelHandlerContext, th);
    }
}
