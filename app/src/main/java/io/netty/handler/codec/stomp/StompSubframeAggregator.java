package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageAggregator;

/* loaded from: classes4.dex */
public class StompSubframeAggregator extends MessageAggregator<StompSubframe, StompHeadersSubframe, StompContentSubframe, StompFrame> {
    /* JADX INFO: Access modifiers changed from: protected */
    public Object newContinueResponse(StompHeadersSubframe stompHeadersSubframe, int i, ChannelPipeline channelPipeline) {
        return null;
    }

    public StompSubframeAggregator(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isStartMessage(StompSubframe stompSubframe) throws Exception {
        return stompSubframe instanceof StompHeadersSubframe;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isContentMessage(StompSubframe stompSubframe) throws Exception {
        return stompSubframe instanceof StompContentSubframe;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isLastContentMessage(StompContentSubframe stompContentSubframe) throws Exception {
        return stompContentSubframe instanceof LastStompContentSubframe;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isAggregated(StompSubframe stompSubframe) throws Exception {
        return stompSubframe instanceof StompFrame;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isContentLengthInvalid(StompHeadersSubframe stompHeadersSubframe, int i) {
        return ((int) Math.min(2147483647L, stompHeadersSubframe.headers().getLong(StompHeaders.CONTENT_LENGTH, -1L))) > i;
    }

    @Override // io.netty.handler.codec.MessageAggregator
    protected boolean closeAfterContinueResponse(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.MessageAggregator
    protected boolean ignoreContentAfterContinueResponse(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public StompFrame beginAggregation(StompHeadersSubframe stompHeadersSubframe, ByteBuf byteBuf) throws Exception {
        DefaultStompFrame defaultStompFrame = new DefaultStompFrame(stompHeadersSubframe.command(), byteBuf);
        defaultStompFrame.headers().set(stompHeadersSubframe.headers());
        return defaultStompFrame;
    }
}
