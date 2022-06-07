package io.netty.handler.codec.http.websocketx;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.List;

/* loaded from: classes4.dex */
public class WebSocket08FrameEncoder extends MessageToMessageEncoder<WebSocketFrame> implements WebSocketFrameEncoder {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(WebSocket08FrameEncoder.class);
    private final boolean b;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List list) throws Exception {
        encode2(channelHandlerContext, webSocketFrame, (List<Object>) list);
    }

    public WebSocket08FrameEncoder(boolean z) {
        this.b = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1, types: [io.netty.buffer.ByteBuf] */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Unknown variable types count: 1 */
    /* renamed from: encode  reason: avoid collision after fix types in other method */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void encode2(io.netty.channel.ChannelHandlerContext r18, io.netty.handler.codec.http.websocketx.WebSocketFrame r19, java.util.List<java.lang.Object> r20) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 460
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http.websocketx.WebSocket08FrameEncoder.encode2(io.netty.channel.ChannelHandlerContext, io.netty.handler.codec.http.websocketx.WebSocketFrame, java.util.List):void");
    }
}
