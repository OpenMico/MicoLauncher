package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;
import java.net.IDN;

/* loaded from: classes4.dex */
public final class SocksCmdResponse extends SocksResponse {
    private static final byte[] e = {0};
    private static final byte[] f = {0, 0, 0, 0};
    private static final byte[] g = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private final SocksCmdStatus a;
    private final SocksAddressType b;
    private final String c;
    private final int d;

    public SocksCmdResponse(SocksCmdStatus socksCmdStatus, SocksAddressType socksAddressType) {
        this(socksCmdStatus, socksAddressType, null, 0);
    }

    public SocksCmdResponse(SocksCmdStatus socksCmdStatus, SocksAddressType socksAddressType, String str, int i) {
        super(SocksResponseType.CMD);
        if (socksCmdStatus == null) {
            throw new NullPointerException("cmdStatus");
        } else if (socksAddressType != null) {
            if (str != null) {
                switch (socksAddressType) {
                    case IPv4:
                        if (!NetUtil.isValidIpV4Address(str)) {
                            throw new IllegalArgumentException(str + " is not a valid IPv4 address");
                        }
                        break;
                    case DOMAIN:
                        if (IDN.toASCII(str).length() > 255) {
                            throw new IllegalArgumentException(str + " IDN: " + IDN.toASCII(str) + " exceeds 255 char limit");
                        }
                        break;
                    case IPv6:
                        if (!NetUtil.isValidIpV6Address(str)) {
                            throw new IllegalArgumentException(str + " is not a valid IPv6 address");
                        }
                        break;
                }
                str = IDN.toASCII(str);
            }
            if (i < 0 || i > 65535) {
                throw new IllegalArgumentException(i + " is not in bounds 0 <= x <= 65535");
            }
            this.a = socksCmdStatus;
            this.b = socksAddressType;
            this.c = str;
            this.d = i;
        } else {
            throw new NullPointerException("addressType");
        }
    }

    public SocksCmdStatus cmdStatus() {
        return this.a;
    }

    public SocksAddressType addressType() {
        return this.b;
    }

    public String host() {
        String str = this.c;
        if (str != null) {
            return IDN.toUnicode(str);
        }
        return null;
    }

    public int port() {
        return this.d;
    }

    @Override // io.netty.handler.codec.socks.SocksMessage
    public void encodeAsByteBuf(ByteBuf byteBuf) {
        byteBuf.writeByte(protocolVersion().byteValue());
        byteBuf.writeByte(this.a.byteValue());
        byteBuf.writeByte(0);
        byteBuf.writeByte(this.b.byteValue());
        switch (this.b) {
            case IPv4:
                String str = this.c;
                byteBuf.writeBytes(str == null ? f : NetUtil.createByteArrayFromIpAddressString(str));
                byteBuf.writeShort(this.d);
                return;
            case DOMAIN:
                String str2 = this.c;
                byte[] bytes = str2 == null ? e : str2.getBytes(CharsetUtil.US_ASCII);
                byteBuf.writeByte(bytes.length);
                byteBuf.writeBytes(bytes);
                byteBuf.writeShort(this.d);
                return;
            case IPv6:
                String str3 = this.c;
                byteBuf.writeBytes(str3 == null ? g : NetUtil.createByteArrayFromIpAddressString(str3));
                byteBuf.writeShort(this.d);
                return;
            default:
                return;
        }
    }
}
