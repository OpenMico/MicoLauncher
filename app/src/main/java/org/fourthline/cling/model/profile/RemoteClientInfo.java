package org.fourthline.cling.model.profile;

import java.net.InetAddress;
import org.fourthline.cling.model.message.Connection;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.UpnpHeaders;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.message.header.UserAgentHeader;
import org.seamless.http.RequestInfo;

/* loaded from: classes5.dex */
public class RemoteClientInfo extends ClientInfo {
    protected final Connection connection;
    protected final UpnpHeaders extraResponseHeaders;

    public RemoteClientInfo() {
        this(null);
    }

    public RemoteClientInfo(StreamRequestMessage streamRequestMessage) {
        this(streamRequestMessage != null ? streamRequestMessage.getConnection() : null, streamRequestMessage != null ? streamRequestMessage.getHeaders() : new UpnpHeaders());
    }

    public RemoteClientInfo(Connection connection, UpnpHeaders upnpHeaders) {
        super(upnpHeaders);
        this.extraResponseHeaders = new UpnpHeaders();
        this.connection = connection;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public boolean isRequestCancelled() {
        return !getConnection().isOpen();
    }

    public void throwIfRequestCancelled() throws InterruptedException {
        if (isRequestCancelled()) {
            throw new InterruptedException("Client's request cancelled");
        }
    }

    public InetAddress getRemoteAddress() {
        return getConnection().getRemoteAddress();
    }

    public InetAddress getLocalAddress() {
        return getConnection().getLocalAddress();
    }

    public UpnpHeaders getExtraResponseHeaders() {
        return this.extraResponseHeaders;
    }

    public void setResponseUserAgent(String str) {
        setResponseUserAgent(new UserAgentHeader(str));
    }

    public void setResponseUserAgent(UserAgentHeader userAgentHeader) {
        getExtraResponseHeaders().add(UpnpHeader.Type.USER_AGENT, userAgentHeader);
    }

    public boolean isWMPRequest() {
        return RequestInfo.isWMPRequest(getRequestUserAgent());
    }

    public boolean isXbox360Request() {
        return RequestInfo.isXbox360Request(getRequestUserAgent(), getRequestHeaders().getFirstHeaderString(UpnpHeader.Type.SERVER));
    }

    public boolean isPS3Request() {
        return RequestInfo.isPS3Request(getRequestUserAgent(), getRequestHeaders().getFirstHeaderString(UpnpHeader.Type.EXT_AV_CLIENT_INFO));
    }

    @Override // org.fourthline.cling.model.profile.ClientInfo
    public String toString() {
        return "(" + getClass().getSimpleName() + ") Remote Address: " + getRemoteAddress();
    }
}
