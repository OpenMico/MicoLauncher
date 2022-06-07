package org.fourthline.cling.model.message;

import java.net.InetAddress;
import org.fourthline.cling.model.message.UpnpOperation;

/* loaded from: classes5.dex */
public class IncomingDatagramMessage<O extends UpnpOperation> extends UpnpMessage<O> {
    private InetAddress localAddress;
    private InetAddress sourceAddress;
    private int sourcePort;

    public IncomingDatagramMessage(O o, InetAddress inetAddress, int i, InetAddress inetAddress2) {
        super(o);
        this.sourceAddress = inetAddress;
        this.sourcePort = i;
        this.localAddress = inetAddress2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public IncomingDatagramMessage(IncomingDatagramMessage<O> incomingDatagramMessage) {
        super(incomingDatagramMessage);
        this.sourceAddress = incomingDatagramMessage.getSourceAddress();
        this.sourcePort = incomingDatagramMessage.getSourcePort();
        this.localAddress = incomingDatagramMessage.getLocalAddress();
    }

    public InetAddress getSourceAddress() {
        return this.sourceAddress;
    }

    public int getSourcePort() {
        return this.sourcePort;
    }

    public InetAddress getLocalAddress() {
        return this.localAddress;
    }
}
