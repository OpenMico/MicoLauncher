package io.netty.handler.codec.spdy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.spdy.SpdyHttpHeaders;
import io.netty.util.ReferenceCountUtil;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/* loaded from: classes4.dex */
public class SpdyHttpResponseStreamIdHandler extends MessageToMessageCodec<Object, HttpMessage> {
    private static final Integer a = -1;
    private final Queue<Integer> b = new LinkedList();

    @Override // io.netty.handler.codec.MessageToMessageCodec
    protected /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, HttpMessage httpMessage, List list) throws Exception {
        encode2(channelHandlerContext, httpMessage, (List<Object>) list);
    }

    @Override // io.netty.handler.codec.MessageToMessageCodec
    public boolean acceptInboundMessage(Object obj) throws Exception {
        return (obj instanceof HttpMessage) || (obj instanceof SpdyRstStreamFrame);
    }

    /* renamed from: encode  reason: avoid collision after fix types in other method */
    protected void encode2(ChannelHandlerContext channelHandlerContext, HttpMessage httpMessage, List<Object> list) throws Exception {
        Integer poll = this.b.poll();
        if (!(poll == null || poll.intValue() == a.intValue() || httpMessage.headers().contains(SpdyHttpHeaders.Names.STREAM_ID))) {
            httpMessage.headers().setInt(SpdyHttpHeaders.Names.STREAM_ID, poll.intValue());
        }
        list.add(ReferenceCountUtil.retain(httpMessage));
    }

    @Override // io.netty.handler.codec.MessageToMessageCodec
    protected void decode(ChannelHandlerContext channelHandlerContext, Object obj, List<Object> list) throws Exception {
        if (obj instanceof HttpMessage) {
            HttpMessage httpMessage = (HttpMessage) obj;
            if (!httpMessage.headers().contains(SpdyHttpHeaders.Names.STREAM_ID)) {
                this.b.add(a);
            } else {
                this.b.add(httpMessage.headers().getInt(SpdyHttpHeaders.Names.STREAM_ID));
            }
        } else if (obj instanceof SpdyRstStreamFrame) {
            this.b.remove(Integer.valueOf(((SpdyRstStreamFrame) obj).streamId()));
        }
        list.add(ReferenceCountUtil.retain(obj));
    }
}
