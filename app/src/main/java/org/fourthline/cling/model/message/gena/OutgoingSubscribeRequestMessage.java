package org.fourthline.cling.model.message.gena;

import java.net.URL;
import java.util.List;
import org.fourthline.cling.model.gena.RemoteGENASubscription;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.UpnpHeaders;
import org.fourthline.cling.model.message.UpnpRequest;
import org.fourthline.cling.model.message.header.CallbackHeader;
import org.fourthline.cling.model.message.header.NTEventHeader;
import org.fourthline.cling.model.message.header.TimeoutHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;

/* loaded from: classes5.dex */
public class OutgoingSubscribeRequestMessage extends StreamRequestMessage {
    public OutgoingSubscribeRequestMessage(RemoteGENASubscription remoteGENASubscription, List<URL> list, UpnpHeaders upnpHeaders) {
        super(UpnpRequest.Method.SUBSCRIBE, remoteGENASubscription.getEventSubscriptionURL());
        getHeaders().add(UpnpHeader.Type.CALLBACK, new CallbackHeader(list));
        getHeaders().add(UpnpHeader.Type.NT, new NTEventHeader());
        getHeaders().add(UpnpHeader.Type.TIMEOUT, new TimeoutHeader(remoteGENASubscription.getRequestedDurationSeconds()));
        if (upnpHeaders != null) {
            getHeaders().putAll(upnpHeaders);
        }
    }

    public boolean hasCallbackURLs() {
        return ((CallbackHeader) getHeaders().getFirstHeader(UpnpHeader.Type.CALLBACK, CallbackHeader.class)).getValue().size() > 0;
    }
}
