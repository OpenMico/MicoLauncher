package org.eclipse.jetty.server.handler;

import org.eclipse.jetty.server.Handler;

/* loaded from: classes5.dex */
public class ProxyHandler extends ConnectHandler {
    public ProxyHandler() {
    }

    public ProxyHandler(Handler handler, String[] strArr, String[] strArr2) {
        super(handler, strArr, strArr2);
    }

    public ProxyHandler(Handler handler) {
        super(handler);
    }

    public ProxyHandler(String[] strArr, String[] strArr2) {
        super(strArr, strArr2);
    }
}
