package org.conscrypt;

import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final class ApplicationProtocolSelectorAdapter {
    private static final int NO_PROTOCOL_SELECTED = -1;
    private final SSLEngine engine;
    private final ApplicationProtocolSelector selector;
    private final SSLSocket socket;

    public ApplicationProtocolSelectorAdapter(SSLEngine sSLEngine, ApplicationProtocolSelector applicationProtocolSelector) {
        this.engine = (SSLEngine) Preconditions.checkNotNull(sSLEngine, "engine");
        this.socket = null;
        this.selector = (ApplicationProtocolSelector) Preconditions.checkNotNull(applicationProtocolSelector, "selector");
    }

    public ApplicationProtocolSelectorAdapter(SSLSocket sSLSocket, ApplicationProtocolSelector applicationProtocolSelector) {
        this.engine = null;
        this.socket = (SSLSocket) Preconditions.checkNotNull(sSLSocket, "socket");
        this.selector = (ApplicationProtocolSelector) Preconditions.checkNotNull(applicationProtocolSelector, "selector");
    }

    int selectApplicationProtocol(byte[] bArr) {
        String str;
        if (bArr == null || bArr.length == 0) {
            return -1;
        }
        List<String> asList = Arrays.asList(SSLUtils.decodeProtocols(bArr));
        SSLEngine sSLEngine = this.engine;
        if (sSLEngine != null) {
            str = this.selector.selectApplicationProtocol(sSLEngine, asList);
        } else {
            str = this.selector.selectApplicationProtocol(this.socket, asList);
        }
        if (str == null || str.isEmpty()) {
            return -1;
        }
        int i = 0;
        for (String str2 : asList) {
            if (str.equals(str2)) {
                return i;
            }
            i += str2.length() + 1;
        }
        return -1;
    }
}
