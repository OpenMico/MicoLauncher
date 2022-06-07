package org.eclipse.jetty.client.security;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import org.eclipse.jetty.client.HttpExchange;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.util.B64Code;

/* loaded from: classes5.dex */
public class ProxyAuthorization implements Authentication {
    private Buffer _authorization;

    public ProxyAuthorization(String str, String str2) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("Basic ");
        sb.append(B64Code.encode(str + Constants.COLON_SEPARATOR + str2, "ISO-8859-1"));
        this._authorization = new ByteArrayBuffer(sb.toString());
    }

    @Override // org.eclipse.jetty.client.security.Authentication
    public void setCredentials(HttpExchange httpExchange) throws IOException {
        httpExchange.setRequestHeader(HttpHeaders.PROXY_AUTHORIZATION_BUFFER, this._authorization);
    }
}
