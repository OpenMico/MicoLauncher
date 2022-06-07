package io.netty.handler.proxy;

import java.net.ConnectException;

/* loaded from: classes4.dex */
public class ProxyConnectException extends ConnectException {
    private static final long serialVersionUID = 5211364632246265538L;

    public ProxyConnectException() {
    }

    public ProxyConnectException(String str) {
        super(str);
    }

    public ProxyConnectException(Throwable th) {
        initCause(th);
    }

    public ProxyConnectException(String str, Throwable th) {
        super(str);
        initCause(th);
    }
}
