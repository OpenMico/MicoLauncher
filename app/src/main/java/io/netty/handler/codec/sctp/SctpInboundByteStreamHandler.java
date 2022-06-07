package io.netty.handler.codec.sctp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.sctp.SctpMessage;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

/* loaded from: classes4.dex */
public class SctpInboundByteStreamHandler extends MessageToMessageDecoder<SctpMessage> {
    private final int a;
    private final int b;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageDecoder
    public /* bridge */ /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, SctpMessage sctpMessage, List list) throws Exception {
        decode2(channelHandlerContext, sctpMessage, (List<Object>) list);
    }

    public SctpInboundByteStreamHandler(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    @Override // io.netty.handler.codec.MessageToMessageDecoder
    public final boolean acceptInboundMessage(Object obj) throws Exception {
        if (super.acceptInboundMessage(obj)) {
            return acceptInboundMessage((SctpMessage) obj);
        }
        return false;
    }

    protected boolean acceptInboundMessage(SctpMessage sctpMessage) {
        return sctpMessage.protocolIdentifier() == this.a && sctpMessage.streamIdentifier() == this.b;
    }

    /* renamed from: decode  reason: avoid collision after fix types in other method */
    protected void decode2(ChannelHandlerContext channelHandlerContext, SctpMessage sctpMessage, List<Object> list) throws Exception {
        if (sctpMessage.isComplete()) {
            list.add(sctpMessage.content().retain());
            return;
        }
        throw new CodecException(String.format("Received SctpMessage is not complete, please add %s in the pipeline before this handler", SctpMessageCompletionHandler.class.getSimpleName()));
    }
}
