package org.fourthline.cling.model.profile;

import org.fourthline.cling.model.message.UpnpHeaders;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.message.header.UserAgentHeader;

/* loaded from: classes5.dex */
public class ClientInfo {
    protected final UpnpHeaders requestHeaders;

    public ClientInfo() {
        this(new UpnpHeaders());
    }

    public ClientInfo(UpnpHeaders upnpHeaders) {
        this.requestHeaders = upnpHeaders;
    }

    public UpnpHeaders getRequestHeaders() {
        return this.requestHeaders;
    }

    public String getRequestUserAgent() {
        return getRequestHeaders().getFirstHeaderString(UpnpHeader.Type.USER_AGENT);
    }

    public void setRequestUserAgent(String str) {
        getRequestHeaders().add(UpnpHeader.Type.USER_AGENT, new UserAgentHeader(str));
    }

    public String toString() {
        return "(" + getClass().getSimpleName() + ") User-Agent: " + getRequestUserAgent();
    }
}
