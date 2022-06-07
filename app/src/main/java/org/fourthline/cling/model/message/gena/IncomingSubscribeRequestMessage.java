package org.fourthline.cling.model.message.gena;

import java.net.URL;
import java.util.List;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.header.CallbackHeader;
import org.fourthline.cling.model.message.header.NTEventHeader;
import org.fourthline.cling.model.message.header.SubscriptionIdHeader;
import org.fourthline.cling.model.message.header.TimeoutHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.meta.LocalService;

/* loaded from: classes5.dex */
public class IncomingSubscribeRequestMessage extends StreamRequestMessage {
    private final LocalService service;

    public IncomingSubscribeRequestMessage(StreamRequestMessage streamRequestMessage, LocalService localService) {
        super(streamRequestMessage);
        this.service = localService;
    }

    public LocalService getService() {
        return this.service;
    }

    public List<URL> getCallbackURLs() {
        CallbackHeader callbackHeader = (CallbackHeader) getHeaders().getFirstHeader(UpnpHeader.Type.CALLBACK, CallbackHeader.class);
        if (callbackHeader != null) {
            return callbackHeader.getValue();
        }
        return null;
    }

    public boolean hasNotificationHeader() {
        return getHeaders().getFirstHeader(UpnpHeader.Type.NT, NTEventHeader.class) != null;
    }

    public Integer getRequestedTimeoutSeconds() {
        TimeoutHeader timeoutHeader = (TimeoutHeader) getHeaders().getFirstHeader(UpnpHeader.Type.TIMEOUT, TimeoutHeader.class);
        if (timeoutHeader != null) {
            return timeoutHeader.getValue();
        }
        return null;
    }

    public String getSubscriptionId() {
        SubscriptionIdHeader subscriptionIdHeader = (SubscriptionIdHeader) getHeaders().getFirstHeader(UpnpHeader.Type.SID, SubscriptionIdHeader.class);
        if (subscriptionIdHeader != null) {
            return subscriptionIdHeader.getValue();
        }
        return null;
    }
}
