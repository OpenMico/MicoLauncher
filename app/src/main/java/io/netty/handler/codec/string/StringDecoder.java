package io.netty.handler.codec.string;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.nio.charset.Charset;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class StringDecoder extends MessageToMessageDecoder<ByteBuf> {
    private final Charset a;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageDecoder
    public /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List list) throws Exception {
        decode2(channelHandlerContext, byteBuf, (List<Object>) list);
    }

    public StringDecoder() {
        this(Charset.defaultCharset());
    }

    public StringDecoder(Charset charset) {
        if (charset != null) {
            this.a = charset;
            return;
        }
        throw new NullPointerException("charset");
    }

    /* renamed from: decode  reason: avoid collision after fix types in other method */
    protected void decode2(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(byteBuf.toString(this.a));
    }
}
