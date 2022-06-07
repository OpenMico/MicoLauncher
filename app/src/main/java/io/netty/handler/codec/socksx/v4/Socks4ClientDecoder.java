package io.netty.handler.codec.socksx.v4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.NetUtil;
import java.util.List;

/* loaded from: classes4.dex */
public class Socks4ClientDecoder extends ReplayingDecoder<a> {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public enum a {
        START,
        SUCCESS,
        FAILURE
    }

    public Socks4ClientDecoder() {
        super(a.START);
        setSingleDecode(true);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            switch (state()) {
                case START:
                    short readUnsignedByte = byteBuf.readUnsignedByte();
                    if (readUnsignedByte == 0) {
                        list.add(new DefaultSocks4CommandResponse(Socks4CommandStatus.valueOf(byteBuf.readByte()), NetUtil.intToIpAddress(byteBuf.readInt()), byteBuf.readUnsignedShort()));
                        checkpoint(a.SUCCESS);
                        break;
                    } else {
                        throw new DecoderException("unsupported reply version: " + ((int) readUnsignedByte) + " (expected: 0)");
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
        DefaultSocks4CommandResponse defaultSocks4CommandResponse = new DefaultSocks4CommandResponse(Socks4CommandStatus.REJECTED_OR_FAILED);
        defaultSocks4CommandResponse.setDecoderResult(DecoderResult.failure(th));
        list.add(defaultSocks4CommandResponse);
        checkpoint(a.FAILURE);
    }
}
