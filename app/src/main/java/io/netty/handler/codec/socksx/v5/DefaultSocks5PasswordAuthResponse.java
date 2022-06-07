package io.netty.handler.codec.socksx.v5;

import io.netty.handler.codec.DecoderResult;
import io.netty.util.internal.StringUtil;

/* loaded from: classes4.dex */
public class DefaultSocks5PasswordAuthResponse extends AbstractSocks5Message implements Socks5PasswordAuthResponse {
    private final Socks5PasswordAuthStatus a;

    public DefaultSocks5PasswordAuthResponse(Socks5PasswordAuthStatus socks5PasswordAuthStatus) {
        if (socks5PasswordAuthStatus != null) {
            this.a = socks5PasswordAuthStatus;
            return;
        }
        throw new NullPointerException("status");
    }

    @Override // io.netty.handler.codec.socksx.v5.Socks5PasswordAuthResponse
    public Socks5PasswordAuthStatus status() {
        return this.a;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(StringUtil.simpleClassName(this));
        DecoderResult decoderResult = decoderResult();
        if (!decoderResult.isSuccess()) {
            sb.append("(decoderResult: ");
            sb.append(decoderResult);
            sb.append(", status: ");
        } else {
            sb.append("(status: ");
        }
        sb.append(status());
        sb.append(')');
        return sb.toString();
    }
}
