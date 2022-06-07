package io.netty.handler.codec.bytes;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class ByteArrayEncoder extends MessageToMessageEncoder<byte[]> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, byte[] bArr, List list) throws Exception {
        encode2(channelHandlerContext, bArr, (List<Object>) list);
    }

    /* renamed from: encode  reason: avoid collision after fix types in other method */
    protected void encode2(ChannelHandlerContext channelHandlerContext, byte[] bArr, List<Object> list) throws Exception {
        list.add(Unpooled.wrappedBuffer(bArr));
    }
}
