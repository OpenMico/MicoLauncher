package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@ChannelHandler.Sharable
/* loaded from: classes4.dex */
public class WebSocket00FrameEncoder extends MessageToMessageEncoder<WebSocketFrame> implements WebSocketFrameEncoder {
    private static final ByteBuf a = Unpooled.unreleasableBuffer(Unpooled.directBuffer(1, 1).writeByte(0));
    private static final ByteBuf b = Unpooled.unreleasableBuffer(Unpooled.directBuffer(1, 1).writeByte(-1));
    private static final ByteBuf c = Unpooled.unreleasableBuffer(Unpooled.directBuffer(2, 2).writeByte(-1).writeByte(0));

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToMessageEncoder
    public /* bridge */ /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List list) throws Exception {
        encode2(channelHandlerContext, webSocketFrame, (List<Object>) list);
    }

    /* renamed from: encode  reason: avoid collision after fix types in other method */
    protected void encode2(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        if (webSocketFrame instanceof TextWebSocketFrame) {
            ByteBuf content = webSocketFrame.content();
            list.add(a.duplicate());
            list.add(content.retain());
            list.add(b.duplicate());
        } else if (webSocketFrame instanceof CloseWebSocketFrame) {
            list.add(c.duplicate());
        } else {
            ByteBuf content2 = webSocketFrame.content();
            int readableBytes = content2.readableBytes();
            ByteBuf buffer = channelHandlerContext.alloc().buffer(5);
            try {
                buffer.writeByte(-128);
                int i = (readableBytes >>> 28) & 127;
                int i2 = (readableBytes >>> 14) & 127;
                int i3 = (readableBytes >>> 7) & 127;
                int i4 = readableBytes & 127;
                if (i != 0) {
                    buffer.writeByte(i | 128);
                    buffer.writeByte(i2 | 128);
                    buffer.writeByte(i3 | 128);
                    buffer.writeByte(i4);
                } else if (i2 != 0) {
                    buffer.writeByte(i2 | 128);
                    buffer.writeByte(i3 | 128);
                    buffer.writeByte(i4);
                } else if (i3 == 0) {
                    buffer.writeByte(i4);
                } else {
                    buffer.writeByte(i3 | 128);
                    buffer.writeByte(i4);
                }
                list.add(buffer);
                list.add(content2.retain());
            } catch (Throwable th) {
                buffer.release();
                throw th;
            }
        }
    }
}
