package org.fourthline.cling.model.message.gena;

import org.fourthline.cling.model.gena.RemoteGENASubscription;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.UpnpHeaders;
import org.fourthline.cling.model.message.UpnpRequest;
import org.fourthline.cling.model.message.header.SubscriptionIdHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;

/* loaded from: classes5.dex */
public class OutgoingUnsubscribeRequestMessage extends StreamRequestMessage {
    public OutgoingUnsubscribeRequestMessage(RemoteGENASubscription remoteGENASubscription, UpnpHeaders upnpHeaders) {
        super(UpnpRequest.Method.UNSUBSCRIBE, remoteGENASubscription.getEventSubscriptionURL());
        getHeaders().add(UpnpHeader.Type.SID, new SubscriptionIdHeader(remoteGENASubscription.getSubscriptionId()));
        if (upnpHeaders != null) {
            getHeaders().putAll(upnpHeaders);
        }
    }
}
