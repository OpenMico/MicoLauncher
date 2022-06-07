package io.netty.handler.codec.socksx.v4;

import io.netty.handler.codec.DecoderResult;
import io.netty.util.NetUtil;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultSocks4CommandResponse extends AbstractSocks4Message implements Socks4CommandResponse {
    private final Socks4CommandStatus a;
    private final String b;
    private final int c;

    public DefaultSocks4CommandResponse(Socks4CommandStatus socks4CommandStatus) {
        this(socks4CommandStatus, null, 0);
    }

    public DefaultSocks4CommandResponse(Socks4CommandStatus socks4CommandStatus, String str, int i) {
        if (socks4CommandStatus == null) {
            throw new NullPointerException("cmdStatus");
        } else if (str != null && !NetUtil.isValidIpV4Address(str)) {
            throw new IllegalArgumentException("dstAddr: " + str + " (expected: a valid IPv4 address)");
        } else if (i < 0 || i > 65535) {
            throw new IllegalArgumentException("dstPort: " + i + " (expected: 0~65535)");
        } else {
            this.a = socks4CommandStatus;
            this.b = str;
            this.c = i;
        }
    }

    @Override // io.netty.handler.codec.socksx.v4.Socks4CommandResponse
    public Socks4CommandStatus status() {
        return this.a;
    }

    @Override // io.netty.handler.codec.socksx.v4.Socks4CommandResponse
    public String dstAddr() {
        return this.b;
    }

    @Override // io.netty.handler.codec.socksx.v4.Socks4CommandResponse
    public int dstPort() {
        return this.c;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(96);
        sb.append(StringUtil.simpleClassName(this));
        DecoderResult decoderResult = decoderResult();
        if (!decoderResult.isSuccess()) {
            sb.append("(decoderResult: ");
            sb.append(decoderResult);
            sb.append(", dstAddr: ");
        } else {
            sb.append("(dstAddr: ");
        }
        sb.append(dstAddr());
        sb.append(", dstPort: ");
        sb.append(dstPort());
        sb.append(')');
        return sb.toString();
    }
}
