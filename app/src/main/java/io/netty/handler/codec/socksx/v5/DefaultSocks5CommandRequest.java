package io.netty.handler.codec.socksx.v5;

import io.netty.handler.codec.DecoderResult;
import io.netty.util.NetUtil;
import io.netty.util.internal.StringUtil;
import java.net.IDN;

/* loaded from: classes4.dex */
public final class DefaultSocks5CommandRequest extends AbstractSocks5Message implements Socks5CommandRequest {
    private final Socks5CommandType a;
    private final Socks5AddressType b;
    private final String c;
    private final int d;

    public DefaultSocks5CommandRequest(Socks5CommandType socks5CommandType, Socks5AddressType socks5AddressType, String str, int i) {
        if (socks5CommandType == null) {
            throw new NullPointerException("type");
        } else if (socks5AddressType == null) {
            throw new NullPointerException("dstAddrType");
        } else if (str != null) {
            if (socks5AddressType == Socks5AddressType.IPv4) {
                if (!NetUtil.isValidIpV4Address(str)) {
                    throw new IllegalArgumentException("dstAddr: " + str + " (expected: a valid IPv4 address)");
                }
            } else if (socks5AddressType == Socks5AddressType.DOMAIN) {
                str = IDN.toASCII(str);
                if (str.length() > 255) {
                    throw new IllegalArgumentException("dstAddr: " + str + " (expected: less than 256 chars)");
                }
            } else if (socks5AddressType == Socks5AddressType.IPv6 && !NetUtil.isValidIpV6Address(str)) {
                throw new IllegalArgumentException("dstAddr: " + str + " (expected: a valid IPv6 address");
            }
            if (i <= 0 || i >= 65536) {
                throw new IllegalArgumentException("dstPort: " + i + " (expected: 1~65535)");
            }
            this.a = socks5CommandType;
            this.b = socks5AddressType;
            this.c = str;
            this.d = i;
        } else {
            throw new NullPointerException("dstAddr");
        }
    }

    @Override // io.netty.handler.codec.socksx.v5.Socks5CommandRequest
    public Socks5CommandType type() {
        return this.a;
    }

    @Override // io.netty.handler.codec.socksx.v5.Socks5CommandRequest
    public Socks5AddressType dstAddrType() {
        return this.b;
    }

    @Override // io.netty.handler.codec.socksx.v5.Socks5CommandRequest
    public String dstAddr() {
        return this.c;
    }

    @Override // io.netty.handler.codec.socksx.v5.Socks5CommandRequest
    public int dstPort() {
        return this.d;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(StringUtil.simpleClassName(this));
        DecoderResult decoderResult = decoderResult();
        if (!decoderResult.isSuccess()) {
            sb.append("(decoderResult: ");
            sb.append(decoderResult);
            sb.append(", type: ");
        } else {
            sb.append("(type: ");
        }
        sb.append(type());
        sb.append(", dstAddrType: ");
        sb.append(dstAddrType());
        sb.append(", dstAddr: ");
        sb.append(dstAddr());
        sb.append(", dstPort: ");
        sb.append(dstPort());
        sb.append(')');
        return sb.toString();
    }
}
