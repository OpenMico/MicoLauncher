package org.fourthline.cling.model.message.gena;

import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.header.ContentTypeHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;

/* loaded from: classes5.dex */
public class OutgoingEventResponseMessage extends StreamResponseMessage {
    public OutgoingEventResponseMessage() {
        super(new UpnpResponse(UpnpResponse.Status.OK));
        getHeaders().add(UpnpHeader.Type.CONTENT_TYPE, new ContentTypeHeader());
    }

    public OutgoingEventResponseMessage(UpnpResponse upnpResponse) {
        super(upnpResponse);
        getHeaders().add(UpnpHeader.Type.CONTENT_TYPE, new ContentTypeHeader());
    }
}
