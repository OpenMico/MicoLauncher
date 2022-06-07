package io.netty.handler.codec.socksx.v5;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import java.util.List;

/* loaded from: classes4.dex */
public class Socks5PasswordAuthResponseDecoder extends ReplayingDecoder<a> {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public enum a {
        INIT,
        SUCCESS,
        FAILURE
    }

    public Socks5PasswordAuthResponseDecoder() {
        super(a.INIT);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            switch (state()) {
                case INIT:
                    byte readByte = byteBuf.readByte();
                    if (readByte == 1) {
                        list.add(new DefaultSocks5PasswordAuthResponse(Socks5PasswordAuthStatus.valueOf(byteBuf.readByte())));
                        checkpoint(a.SUCCESS);
                        break;
                    } else {
                        throw new DecoderException("unsupported subnegotiation version: " + ((int) readByte) + " (expected: 1)");
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
        DefaultSocks5PasswordAuthResponse defaultSocks5PasswordAuthResponse = new DefaultSocks5PasswordAuthResponse(Socks5PasswordAuthStatus.FAILURE);
        defaultSocks5PasswordAuthResponse.setDecoderResult(DecoderResult.failure(th));
        list.add(defaultSocks5PasswordAuthResponse);
    }
}
