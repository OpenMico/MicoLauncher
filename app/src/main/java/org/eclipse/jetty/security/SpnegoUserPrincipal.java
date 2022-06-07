package org.eclipse.jetty.security;

import java.security.Principal;
import org.eclipse.jetty.util.security.B64Code;

/* loaded from: classes5.dex */
public class SpnegoUserPrincipal implements Principal {
    private String _encodedToken;
    private final String _name;
    private byte[] _token;

    public SpnegoUserPrincipal(String str, String str2) {
        this._name = str;
        this._encodedToken = str2;
    }

    public SpnegoUserPrincipal(String str, byte[] bArr) {
        this._name = str;
        this._token = bArr;
    }

    @Override // java.security.Principal
    public String getName() {
        return this._name;
    }

    public byte[] getToken() {
        if (this._token == null) {
            this._token = B64Code.decode(this._encodedToken);
        }
        return this._token;
    }

    public String getEncodedToken() {
        if (this._encodedToken == null) {
            this._encodedToken = new String(B64Code.encode(this._token, true));
        }
        return this._encodedToken;
    }
}
