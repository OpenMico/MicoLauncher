package io.netty.handler.codec.sctp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.sctp.SctpMessage;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

/* loaded from: classes4.dex */
public class SctpOutboundByteStreamHandler extends MessageToMessageEncoder<ByteBuf> {
    private final int a;
    private final int b;
    private final boolean c;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List list) throws Exception {
        encode2(channelHandlerContext, byteBuf, (List<Object>) list);
    }

    public SctpOutboundByteStreamHandler(int i, int i2) {
        this(i, i2, false);
    }

    public SctpOutboundByteStreamHandler(int i, int i2, boolean z) {
        this.a = i;
        this.b = i2;
        this.c = z;
    }

    /* renamed from: encode  reason: avoid collision after fix types in other method */
    protected void encode2(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(new SctpMessage(this.a, this.b, this.c, byteBuf.retain()));
    }
}
