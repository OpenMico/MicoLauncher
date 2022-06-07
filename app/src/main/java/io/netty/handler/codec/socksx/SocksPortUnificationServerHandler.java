package io.netty.handler.codec.socksx;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.socksx.v4.Socks4ServerDecoder;
import io.netty.handler.codec.socksx.v4.Socks4ServerEncoder;
import io.netty.handler.codec.socksx.v5.Socks5InitialRequestDecoder;
import io.netty.handler.codec.socksx.v5.Socks5ServerEncoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.List;

/* loaded from: classes4.dex */
public class SocksPortUnificationServerHandler extends ByteToMessageDecoder {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(SocksPortUnificationServerHandler.class);
    private final Socks5ServerEncoder c;

    public SocksPortUnificationServerHandler() {
        this(Socks5ServerEncoder.DEFAULT);
    }

    public SocksPortUnificationServerHandler(Socks5ServerEncoder socks5ServerEncoder) {
        if (socks5ServerEncoder != null) {
            this.c = socks5ServerEncoder;
            return;
        }
        throw new NullPointerException("socks5encoder");
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int readerIndex = byteBuf.readerIndex();
        if (byteBuf.writerIndex() != readerIndex) {
            ChannelPipeline pipeline = channelHandlerContext.pipeline();
            byte b = byteBuf.getByte(readerIndex);
            SocksVersion valueOf = SocksVersion.valueOf(b);
            switch (valueOf) {
                case SOCKS4a:
                    a(channelHandlerContext, valueOf);
                    pipeline.addAfter(channelHandlerContext.name(), null, Socks4ServerEncoder.INSTANCE);
                    pipeline.addAfter(channelHandlerContext.name(), null, new Socks4ServerDecoder());
                    break;
                case SOCKS5:
                    a(channelHandlerContext, valueOf);
                    pipeline.addAfter(channelHandlerContext.name(), null, this.c);
                    pipeline.addAfter(channelHandlerContext.name(), null, new Socks5InitialRequestDecoder());
                    break;
                default:
                    a(channelHandlerContext, b);
                    byteBuf.skipBytes(byteBuf.readableBytes());
                    channelHandlerContext.close();
                    return;
            }
            pipeline.remove(this);
        }
    }

    private static void a(ChannelHandlerContext channelHandlerContext, SocksVersion socksVersion) {
        a.debug("{} Protocol version: {}({})", channelHandlerContext.channel(), socksVersion);
    }

    private static void a(ChannelHandlerContext channelHandlerContext, byte b) {
        if (a.isDebugEnabled()) {
            a.debug("{} Unknown protocol version: {}", channelHandlerContext.channel(), Integer.valueOf(b & 255));
        }
    }
}
