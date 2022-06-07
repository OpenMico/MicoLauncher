package io.netty.handler.codec.string;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ObjectUtil;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class LineEncoder extends MessageToMessageEncoder<CharSequence> {
    private final Charset a;
    private final byte[] b;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, CharSequence charSequence, List list) throws Exception {
        encode2(channelHandlerContext, charSequence, (List<Object>) list);
    }

    public LineEncoder() {
        this(LineSeparator.DEFAULT, CharsetUtil.UTF_8);
    }

    public LineEncoder(LineSeparator lineSeparator) {
        this(lineSeparator, CharsetUtil.UTF_8);
    }

    public LineEncoder(Charset charset) {
        this(LineSeparator.DEFAULT, charset);
    }

    public LineEncoder(LineSeparator lineSeparator, Charset charset) {
        this.a = (Charset) ObjectUtil.checkNotNull(charset, "charset");
        this.b = ((LineSeparator) ObjectUtil.checkNotNull(lineSeparator, "lineSeparator")).value().getBytes(charset);
    }

    /* renamed from: encode  reason: avoid collision after fix types in other method */
    protected void encode2(ChannelHandlerContext channelHandlerContext, CharSequence charSequence, List<Object> list) throws Exception {
        ByteBuf encodeString = ByteBufUtil.encodeString(channelHandlerContext.alloc(), CharBuffer.wrap(charSequence), this.a, this.b.length);
        encodeString.writeBytes(this.b);
        list.add(encodeString);
    }
}
