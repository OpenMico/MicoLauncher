package io.netty.handler.codec.socksx.v4;

import com.xiaomi.accountsdk.account.data.BaseConstants;
import io.netty.handler.codec.DecoderResult;
import io.netty.util.internal.StringUtil;
import java.net.IDN;

/* loaded from: classes4.dex */
public class DefaultSocks4CommandRequest extends AbstractSocks4Message implements Socks4CommandRequest {
    private final Socks4CommandType a;
    private final String b;
    private final int c;
    private final String d;

    public DefaultSocks4CommandRequest(Socks4CommandType socks4CommandType, String str, int i) {
        this(socks4CommandType, str, i, "");
    }

    public DefaultSocks4CommandRequest(Socks4CommandType socks4CommandType, String str, int i, String str2) {
        if (socks4CommandType == null) {
            throw new NullPointerException("type");
        } else if (str == null) {
            throw new NullPointerException("dstAddr");
        } else if (i <= 0 || i >= 65536) {
            throw new IllegalArgumentException("dstPort: " + i + " (expected: 1~65535)");
        } else if (str2 != null) {
            this.d = str2;
            this.a = socks4CommandType;
            this.b = IDN.toASCII(str);
            this.c = i;
        } else {
            throw new NullPointerException(BaseConstants.EXTRA_USER_ID);
        }
    }

    @Override // io.netty.handler.codec.socksx.v4.Socks4CommandRequest
    public Socks4CommandType type() {
        return this.a;
    }

    @Override // io.netty.handler.codec.socksx.v4.Socks4CommandRequest
    public String dstAddr() {
        return this.b;
    }

    @Override // io.netty.handler.codec.socksx.v4.Socks4CommandRequest
    public int dstPort() {
        return this.c;
    }

    @Override // io.netty.handler.codec.socksx.v4.Socks4CommandRequest
    public String userId() {
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
        sb.append(", dstAddr: ");
        sb.append(dstAddr());
        sb.append(", dstPort: ");
        sb.append(dstPort());
        sb.append(", userId: ");
        sb.append(userId());
        sb.append(')');
        return sb.toString();
    }
}
