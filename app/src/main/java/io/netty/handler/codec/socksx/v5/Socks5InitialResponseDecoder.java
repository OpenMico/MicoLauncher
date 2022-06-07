package io.netty.handler.codec.socksx.v5;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.socksx.SocksVersion;
import java.util.List;

/* loaded from: classes4.dex */
public class Socks5InitialResponseDecoder extends ReplayingDecoder<a> {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public enum a {
        INIT,
        SUCCESS,
        FAILURE
    }

    public Socks5InitialResponseDecoder() {
        super(a.INIT);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            switch (state()) {
                case INIT:
                    byte readByte = byteBuf.readByte();
                    if (readByte == SocksVersion.SOCKS5.byteValue()) {
                        list.add(new DefaultSocks5InitialResponse(Socks5AuthMethod.valueOf(byteBuf.readByte())));
                        checkpoint(a.SUCCESS);
                        break;
                    } else {
                        throw new DecoderException("unsupported version: " + ((int) readByte) + " (expected: " + ((int) SocksVersion.SOCKS5.byteValue()) + ')');
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
        DefaultSocks5InitialResponse defaultSocks5InitialResponse = new DefaultSocks5InitialResponse(Socks5AuthMethod.UNACCEPTED);
        defaultSocks5InitialResponse.setDecoderResult(DecoderResult.failure(th));
        list.add(defaultSocks5InitialResponse);
    }
}
