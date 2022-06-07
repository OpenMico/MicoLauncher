package org.fourthline.cling.model.message.gena;

import java.net.URL;
import java.util.Collection;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.UpnpRequest;
import org.fourthline.cling.model.message.header.ContentTypeHeader;
import org.fourthline.cling.model.message.header.EventSequenceHeader;
import org.fourthline.cling.model.message.header.NTEventHeader;
import org.fourthline.cling.model.message.header.NTSHeader;
import org.fourthline.cling.model.message.header.SubscriptionIdHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.state.StateVariableValue;
import org.fourthline.cling.model.types.NotificationSubtype;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;

/* loaded from: classes5.dex */
public class OutgoingEventRequestMessage extends StreamRequestMessage {
    private final Collection<StateVariableValue> stateVariableValues;

    public OutgoingEventRequestMessage(GENASubscription gENASubscription, URL url, UnsignedIntegerFourBytes unsignedIntegerFourBytes, Collection<StateVariableValue> collection) {
        super(new UpnpRequest(UpnpRequest.Method.NOTIFY, url));
        getHeaders().add(UpnpHeader.Type.CONTENT_TYPE, new ContentTypeHeader());
        getHeaders().add(UpnpHeader.Type.NT, new NTEventHeader());
        getHeaders().add(UpnpHeader.Type.NTS, new NTSHeader(NotificationSubtype.PROPCHANGE));
        getHeaders().add(UpnpHeader.Type.SID, new SubscriptionIdHeader(gENASubscription.getSubscriptionId()));
        getHeaders().add(UpnpHeader.Type.SEQ, new EventSequenceHeader(unsignedIntegerFourBytes.getValue().longValue()));
        this.stateVariableValues = collection;
    }

    public OutgoingEventRequestMessage(GENASubscription gENASubscription, URL url) {
        this(gENASubscription, url, gENASubscription.getCurrentSequence(), gENASubscription.getCurrentValues().values());
    }

    public Collection<StateVariableValue> getStateVariableValues() {
        return this.stateVariableValues;
    }
}
