package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.TooLongFrameException;
import java.util.List;

/* loaded from: classes4.dex */
public class WebSocket00FrameDecoder extends ReplayingDecoder<Void> implements WebSocketFrameDecoder {
    private final long c;
    private boolean d;

    public WebSocket00FrameDecoder() {
        this(16384);
    }

    public WebSocket00FrameDecoder(int i) {
        this.c = i;
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        WebSocketFrame webSocketFrame;
        if (this.d) {
            byteBuf.skipBytes(actualReadableBytes());
            return;
        }
        byte readByte = byteBuf.readByte();
        if ((readByte & 128) == 128) {
            webSocketFrame = a(channelHandlerContext, readByte, byteBuf);
        } else {
            webSocketFrame = a(channelHandlerContext, byteBuf);
        }
        if (webSocketFrame != null) {
            list.add(webSocketFrame);
        }
    }

    private WebSocketFrame a(ChannelHandlerContext channelHandlerContext, byte b, ByteBuf byteBuf) {
        byte readByte;
        int i = 0;
        long j = 0;
        do {
            readByte = byteBuf.readByte();
            j = (j << 7) | (readByte & Byte.MAX_VALUE);
            if (j <= this.c) {
                i++;
                if (i > 8) {
                    throw new TooLongFrameException();
                }
            } else {
                throw new TooLongFrameException();
            }
        } while ((readByte & 128) == 128);
        if (b != -1 || j != 0) {
            return new BinaryWebSocketFrame(ByteBufUtil.readBytes(channelHandlerContext.alloc(), byteBuf, (int) j));
        }
        this.d = true;
        return new CloseWebSocketFrame();
    }

    private WebSocketFrame a(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        int readerIndex = byteBuf.readerIndex();
        int actualReadableBytes = actualReadableBytes();
        int indexOf = byteBuf.indexOf(readerIndex, readerIndex + actualReadableBytes, (byte) -1);
        if (indexOf != -1) {
            int i = indexOf - readerIndex;
            if (i <= this.c) {
                ByteBuf readBytes = ByteBufUtil.readBytes(channelHandlerContext.alloc(), byteBuf, i);
                byteBuf.skipBytes(1);
                if (readBytes.indexOf(readBytes.readerIndex(), readBytes.writerIndex(), (byte) -1) < 0) {
                    return new TextWebSocketFrame(readBytes);
                }
                readBytes.release();
                throw new IllegalArgumentException("a text frame should not contain 0xFF.");
            }
            throw new TooLongFrameException();
        } else if (actualReadableBytes <= this.c) {
            return null;
        } else {
            throw new TooLongFrameException();
        }
    }
}
