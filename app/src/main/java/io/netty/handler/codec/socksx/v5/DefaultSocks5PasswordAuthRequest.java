package io.netty.handler.codec.socksx.v5;

import io.netty.handler.codec.DecoderResult;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultSocks5PasswordAuthRequest extends AbstractSocks5Message implements Socks5PasswordAuthRequest {
    private final String a;
    private final String b;

    public DefaultSocks5PasswordAuthRequest(String str, String str2) {
        if (str == null) {
            throw new NullPointerException("username");
        } else if (str2 == null) {
            throw new NullPointerException("password");
        } else if (str.length() > 255) {
            throw new IllegalArgumentException("username: **** (expected: less than 256 chars)");
        } else if (str2.length() <= 255) {
            this.a = str;
            this.b = str2;
        } else {
            throw new IllegalArgumentException("password: **** (expected: less than 256 chars)");
        }
    }

    @Override // io.netty.handler.codec.socksx.v5.Socks5PasswordAuthRequest
    public String username() {
        return this.a;
    }

    @Override // io.netty.handler.codec.socksx.v5.Socks5PasswordAuthRequest
    public String password() {
        return this.b;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(StringUtil.simpleClassName(this));
        DecoderResult decoderResult = decoderResult();
        if (!decoderResult.isSuccess()) {
            sb.append("(decoderResult: ");
            sb.append(decoderResult);
            sb.append(", username: ");
        } else {
            sb.append("(username: ");
        }
        sb.append(username());
        sb.append(", password: ****)");
        return sb.toString();
    }
}
