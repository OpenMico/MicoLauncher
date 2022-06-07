package io.netty.handler.codec.protobuf;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class ProtobufEncoder extends MessageToMessageEncoder<MessageLiteOrBuilder> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, MessageLiteOrBuilder messageLiteOrBuilder, List list) throws Exception {
        encode2(channelHandlerContext, messageLiteOrBuilder, (List<Object>) list);
    }

    /* renamed from: encode  reason: avoid collision after fix types in other method */
    protected void encode2(ChannelHandlerContext channelHandlerContext, MessageLiteOrBuilder messageLiteOrBuilder, List<Object> list) throws Exception {
        if (messageLiteOrBuilder instanceof MessageLite) {
            list.add(Unpooled.wrappedBuffer(((MessageLite) messageLiteOrBuilder).toByteArray()));
        } else if (messageLiteOrBuilder instanceof MessageLite.Builder) {
            list.add(Unpooled.wrappedBuffer(((MessageLite.Builder) messageLiteOrBuilder).build().toByteArray()));
        }
    }
}
