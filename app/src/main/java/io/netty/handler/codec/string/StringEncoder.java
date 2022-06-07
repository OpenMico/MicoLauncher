package io.netty.handler.codec.string;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class StringEncoder extends MessageToMessageEncoder<CharSequence> {
    private final Charset a;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, CharSequence charSequence, List list) throws Exception {
        encode2(channelHandlerContext, charSequence, (List<Object>) list);
    }

    public StringEncoder() {
        this(Charset.defaultCharset());
    }

    public StringEncoder(Charset charset) {
        if (charset != null) {
            this.a = charset;
            return;
        }
        throw new NullPointerException("charset");
    }

    /* renamed from: encode  reason: avoid collision after fix types in other method */
    protected void encode2(ChannelHandlerContext channelHandlerContext, CharSequence charSequence, List<Object> list) throws Exception {
        if (charSequence.length() != 0) {
            list.add(ByteBufUtil.encodeString(channelHandlerContext.alloc(), CharBuffer.wrap(charSequence), this.a));
        }
    }
}
