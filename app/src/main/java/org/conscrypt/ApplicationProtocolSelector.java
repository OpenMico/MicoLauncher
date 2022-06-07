package org.conscrypt;

import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;

/* loaded from: classes5.dex */
public abstract class ApplicationProtocolSelector {
    public abstract String selectApplicationProtocol(SSLEngine sSLEngine, List<String> list);

    public abstract String selectApplicationProtocol(SSLSocket sSLSocket, List<String> list);
}
