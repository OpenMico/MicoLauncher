package io.netty.handler.codec.socksx.v5;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.socksx.SocksVersion;
import java.util.List;
import org.eclipse.jetty.util.StringUtil;

/* loaded from: classes4.dex */
public class Socks5CommandRequestDecoder extends ReplayingDecoder<a> {
    private final Socks5AddressDecoder c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public enum a {
        INIT,
        SUCCESS,
        FAILURE
    }

    public Socks5CommandRequestDecoder() {
        this(Socks5AddressDecoder.DEFAULT);
    }

    public Socks5CommandRequestDecoder(Socks5AddressDecoder socks5AddressDecoder) {
        super(a.INIT);
        if (socks5AddressDecoder != null) {
            this.c = socks5AddressDecoder;
            return;
        }
        throw new NullPointerException("addressDecoder");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            switch (state()) {
                case INIT:
                    byte readByte = byteBuf.readByte();
                    if (readByte == SocksVersion.SOCKS5.byteValue()) {
                        Socks5CommandType valueOf = Socks5CommandType.valueOf(byteBuf.readByte());
                        byteBuf.skipBytes(1);
                        Socks5AddressType valueOf2 = Socks5AddressType.valueOf(byteBuf.readByte());
                        list.add(new DefaultSocks5CommandRequest(valueOf, valueOf2, this.c.decodeAddress(valueOf2, byteBuf), byteBuf.readUnsignedShort()));
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
        DefaultSocks5CommandRequest defaultSocks5CommandRequest = new DefaultSocks5CommandRequest(Socks5CommandType.CONNECT, Socks5AddressType.IPv4, StringUtil.ALL_INTERFACES, 1);
        defaultSocks5CommandRequest.setDecoderResult(DecoderResult.failure(th));
        list.add(defaultSocks5CommandRequest);
    }
}
