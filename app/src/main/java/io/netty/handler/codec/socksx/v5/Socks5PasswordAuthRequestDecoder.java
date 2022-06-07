package io.netty.handler.codec.socksx.v5;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;
import java.util.List;

/* loaded from: classes4.dex */
public class Socks5PasswordAuthRequestDecoder extends ReplayingDecoder<a> {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public enum a {
        INIT,
        SUCCESS,
        FAILURE
    }

    public Socks5PasswordAuthRequestDecoder() {
        super(a.INIT);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            switch (state()) {
                case INIT:
                    int readerIndex = byteBuf.readerIndex();
                    byte b = byteBuf.getByte(readerIndex);
                    if (b == 1) {
                        short unsignedByte = byteBuf.getUnsignedByte(readerIndex + 1);
                        int i = readerIndex + 2;
                        short unsignedByte2 = byteBuf.getUnsignedByte(i + unsignedByte);
                        byteBuf.skipBytes(unsignedByte + unsignedByte2 + 3);
                        list.add(new DefaultSocks5PasswordAuthRequest(byteBuf.toString(i, unsignedByte, CharsetUtil.US_ASCII), byteBuf.toString(readerIndex + 3 + unsignedByte, unsignedByte2, CharsetUtil.US_ASCII)));
                        checkpoint(a.SUCCESS);
                        break;
                    } else {
                        throw new DecoderException("unsupported subnegotiation version: " + ((int) b) + " (expected: 1)");
                    }
                case SUCCESS:
                    break;
                case FAILURE:
                    byteBuf.skipBytes(actualReadableBytes());
                    return;
                default:
                    return;
            }
            int actualReadableBytes = actualReadableBytes();
            if (actualReadableBytes > 0) {
                list.add(byteBuf.readRetainedSlice(actualReadableBytes));
            }
        } catch (Exception e) {
            a(list, e);
        }
    }

    private void a(List<Object> list, Throwable th) {
        if (!(th instanceof DecoderException)) {
            th = new DecoderException(th);
        }
        checkpoint(a.FAILURE);
        DefaultSocks5PasswordAuthRequest defaultSocks5PasswordAuthRequest = new DefaultSocks5PasswordAuthRequest("", "");
        defaultSocks5PasswordAuthRequest.setDecoderResult(DecoderResult.failure(th));
        list.add(defaultSocks5PasswordAuthRequest);
    }
}
