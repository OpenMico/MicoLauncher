package io.netty.handler.codec.socksx.v4;

import com.xiaomi.account.openauth.AuthorizeActivityBase;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.socksx.SocksVersion;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;
import java.util.List;
import org.eclipse.jetty.util.StringUtil;

/* loaded from: classes4.dex */
public class Socks4ServerDecoder extends ReplayingDecoder<a> {
    private Socks4CommandType c;
    private String d;
    private int e;
    private String f;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public enum a {
        START,
        READ_USERID,
        READ_DOMAIN,
        SUCCESS,
        FAILURE
    }

    public Socks4ServerDecoder() {
        super(a.START);
        setSingleDecode(true);
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            switch (state()) {
                case START:
                    short readUnsignedByte = byteBuf.readUnsignedByte();
                    if (readUnsignedByte == SocksVersion.SOCKS4a.byteValue()) {
                        this.c = Socks4CommandType.valueOf(byteBuf.readByte());
                        this.e = byteBuf.readUnsignedShort();
                        this.d = NetUtil.intToIpAddress(byteBuf.readInt());
                        checkpoint(a.READ_USERID);
                    } else {
                        throw new DecoderException("unsupported protocol version: " + ((int) readUnsignedByte));
                    }
                case READ_USERID:
                    this.f = a(AuthorizeActivityBase.KEY_USERID, byteBuf);
                    checkpoint(a.READ_DOMAIN);
                case READ_DOMAIN:
                    if (!StringUtil.ALL_INTERFACES.equals(this.d) && this.d.startsWith("0.0.0.")) {
                        this.d = a("dstAddr", byteBuf);
                    }
                    list.add(new DefaultSocks4CommandRequest(this.c, this.d, this.e, this.f));
                    checkpoint(a.SUCCESS);
                    break;
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
        Socks4CommandType socks4CommandType = this.c;
        if (socks4CommandType == null) {
            socks4CommandType = Socks4CommandType.CONNECT;
        }
        String str = this.d;
        if (str == null) {
            str = "";
        }
        int i = this.e;
        if (i == 0) {
            i = 65535;
        }
        String str2 = this.f;
        if (str2 == null) {
            str2 = "";
        }
        DefaultSocks4CommandRequest defaultSocks4CommandRequest = new DefaultSocks4CommandRequest(socks4CommandType, str, i, str2);
        defaultSocks4CommandRequest.setDecoderResult(DecoderResult.failure(th));
        list.add(defaultSocks4CommandRequest);
        checkpoint(a.FAILURE);
    }

    private static String a(String str, ByteBuf byteBuf) {
        int bytesBefore = byteBuf.bytesBefore(256, (byte) 0);
        if (bytesBefore >= 0) {
            String byteBuf2 = byteBuf.readSlice(bytesBefore).toString(CharsetUtil.US_ASCII);
            byteBuf.skipBytes(1);
            return byteBuf2;
        }
        throw new DecoderException("field '" + str + "' longer than 255 chars");
    }
}
