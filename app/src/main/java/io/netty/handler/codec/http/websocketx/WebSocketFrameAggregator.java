package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageAggregator;

/* loaded from: classes4.dex */
public class WebSocketFrameAggregator extends MessageAggregator<WebSocketFrame, WebSocketFrame, ContinuationWebSocketFrame, WebSocketFrame> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isContentLengthInvalid(WebSocketFrame webSocketFrame, int i) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Object newContinueResponse(WebSocketFrame webSocketFrame, int i, ChannelPipeline channelPipeline) {
        return null;
    }

    public WebSocketFrameAggregator(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isStartMessage(WebSocketFrame webSocketFrame) throws Exception {
        return (webSocketFrame instanceof TextWebSocketFrame) || (webSocketFrame instanceof BinaryWebSocketFrame);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isContentMessage(WebSocketFrame webSocketFrame) throws Exception {
        return webSocketFrame instanceof ContinuationWebSocketFrame;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isLastContentMessage(ContinuationWebSocketFrame continuationWebSocketFrame) throws Exception {
        return isContentMessage((WebSocketFrame) continuationWebSocketFrame) && continuationWebSocketFrame.isFinalFragment();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isAggregated(WebSocketFrame webSocketFrame) throws Exception {
        if (webSocketFrame.isFinalFragment()) {
            return !isContentMessage(webSocketFrame);
        }
        return !isStartMessage(webSocketFrame) && !isContentMessage(webSocketFrame);
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
    public WebSocketFrame beginAggregation(WebSocketFrame webSocketFrame, ByteBuf byteBuf) throws Exception {
        if (webSocketFrame instanceof TextWebSocketFrame) {
            return new TextWebSocketFrame(true, webSocketFrame.rsv(), byteBuf);
        }
        if (webSocketFrame instanceof BinaryWebSocketFrame) {
            return new BinaryWebSocketFrame(true, webSocketFrame.rsv(), byteBuf);
        }
        throw new Error();
    }
}
