package org.fourthline.cling.model.message;

import java.net.URI;
import java.net.URL;
import org.fourthline.cling.model.message.UpnpMessage;
import org.fourthline.cling.model.message.UpnpRequest;

/* loaded from: classes5.dex */
public class StreamRequestMessage extends UpnpMessage<UpnpRequest> {
    protected Connection connection;

    public StreamRequestMessage(StreamRequestMessage streamRequestMessage) {
        super(streamRequestMessage);
        this.connection = streamRequestMessage.getConnection();
    }

    public StreamRequestMessage(UpnpRequest upnpRequest) {
        super(upnpRequest);
    }

    public StreamRequestMessage(UpnpRequest.Method method, URI uri) {
        super(new UpnpRequest(method, uri));
    }

    public StreamRequestMessage(UpnpRequest.Method method, URL url) {
        super(new UpnpRequest(method, url));
    }

    public StreamRequestMessage(UpnpRequest upnpRequest, String str) {
        super(upnpRequest, UpnpMessage.BodyType.STRING, str);
    }

    public StreamRequestMessage(UpnpRequest.Method method, URI uri, String str) {
        super(new UpnpRequest(method, uri), UpnpMessage.BodyType.STRING, str);
    }

    public StreamRequestMessage(UpnpRequest.Method method, URL url, String str) {
        super(new UpnpRequest(method, url), UpnpMessage.BodyType.STRING, str);
    }

    public StreamRequestMessage(UpnpRequest upnpRequest, byte[] bArr) {
        super(upnpRequest, UpnpMessage.BodyType.BYTES, bArr);
    }

    public StreamRequestMessage(UpnpRequest.Method method, URI uri, byte[] bArr) {
        super(new UpnpRequest(method, uri), UpnpMessage.BodyType.BYTES, bArr);
    }

    public StreamRequestMessage(UpnpRequest.Method method, URL url, byte[] bArr) {
        super(new UpnpRequest(method, url), UpnpMessage.BodyType.BYTES, bArr);
    }

    public URI getUri() {
        return getOperation().getURI();
    }

    public void setUri(URI uri) {
        getOperation().setUri(uri);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return this.connection;
    }
}
