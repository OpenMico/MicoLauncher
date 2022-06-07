package org.fourthline.cling.model.message.gena;

import java.util.ArrayList;
import java.util.List;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.header.EventSequenceHeader;
import org.fourthline.cling.model.message.header.NTEventHeader;
import org.fourthline.cling.model.message.header.NTSHeader;
import org.fourthline.cling.model.message.header.SubscriptionIdHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.meta.RemoteService;
import org.fourthline.cling.model.state.StateVariableValue;
import org.fourthline.cling.model.types.NotificationSubtype;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;

/* loaded from: classes5.dex */
public class IncomingEventRequestMessage extends StreamRequestMessage {
    private final RemoteService service;
    private final List<StateVariableValue> stateVariableValues = new ArrayList();

    public IncomingEventRequestMessage(StreamRequestMessage streamRequestMessage, RemoteService remoteService) {
        super(streamRequestMessage);
        this.service = remoteService;
    }

    public RemoteService getService() {
        return this.service;
    }

    public List<StateVariableValue> getStateVariableValues() {
        return this.stateVariableValues;
    }

    public String getSubscrptionId() {
        SubscriptionIdHeader subscriptionIdHeader = (SubscriptionIdHeader) getHeaders().getFirstHeader(UpnpHeader.Type.SID, SubscriptionIdHeader.class);
        if (subscriptionIdHeader != null) {
            return subscriptionIdHeader.getValue();
        }
        return null;
    }

    public UnsignedIntegerFourBytes getSequence() {
        EventSequenceHeader eventSequenceHeader = (EventSequenceHeader) getHeaders().getFirstHeader(UpnpHeader.Type.SEQ, EventSequenceHeader.class);
        if (eventSequenceHeader != null) {
            return eventSequenceHeader.getValue();
        }
        return null;
    }

    public boolean hasNotificationHeaders() {
        UpnpHeader firstHeader = getHeaders().getFirstHeader(UpnpHeader.Type.NT);
        UpnpHeader firstHeader2 = getHeaders().getFirstHeader(UpnpHeader.Type.NTS);
        return (firstHeader == null || firstHeader.getValue() == null || firstHeader2 == null || firstHeader2.getValue() == null) ? false : true;
    }

    public boolean hasValidNotificationHeaders() {
        NTEventHeader nTEventHeader = (NTEventHeader) getHeaders().getFirstHeader(UpnpHeader.Type.NT, NTEventHeader.class);
        NTSHeader nTSHeader = (NTSHeader) getHeaders().getFirstHeader(UpnpHeader.Type.NTS, NTSHeader.class);
        return (nTEventHeader == null || nTEventHeader.getValue() == null || nTSHeader == null || !nTSHeader.getValue().equals(NotificationSubtype.PROPCHANGE)) ? false : true;
    }

    @Override // org.fourthline.cling.model.message.UpnpMessage
    public String toString() {
        return super.toString() + " SEQUENCE: " + getSequence().getValue();
    }
}
