package org.eclipse.jetty.security;

import java.security.GeneralSecurityException;

/* loaded from: classes5.dex */
public class ServerAuthException extends GeneralSecurityException {
    public ServerAuthException() {
    }

    public ServerAuthException(String str) {
        super(str);
    }

    public ServerAuthException(String str, Throwable th) {
        super(str, th);
    }

    public ServerAuthException(Throwable th) {
        super(th);
    }
}
