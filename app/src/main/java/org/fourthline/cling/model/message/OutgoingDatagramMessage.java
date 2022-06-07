package org.fourthline.cling.model.message;

import java.net.InetAddress;
import org.fourthline.cling.model.message.UpnpMessage;
import org.fourthline.cling.model.message.UpnpOperation;

/* loaded from: classes5.dex */
public abstract class OutgoingDatagramMessage<O extends UpnpOperation> extends UpnpMessage<O> {
    private InetAddress destinationAddress;
    private int destinationPort;
    private UpnpHeaders headers = new UpnpHeaders(false);

    public OutgoingDatagramMessage(O o, InetAddress inetAddress, int i) {
        super(o);
        this.destinationAddress = inetAddress;
        this.destinationPort = i;
    }

    protected OutgoingDatagramMessage(O o, UpnpMessage.BodyType bodyType, Object obj, InetAddress inetAddress, int i) {
        super(o, bodyType, obj);
        this.destinationAddress = inetAddress;
        this.destinationPort = i;
    }

    public InetAddress getDestinationAddress() {
        return this.destinationAddress;
    }

    public int getDestinationPort() {
        return this.destinationPort;
    }

    @Override // org.fourthline.cling.model.message.UpnpMessage
    public UpnpHeaders getHeaders() {
        return this.headers;
    }
}
