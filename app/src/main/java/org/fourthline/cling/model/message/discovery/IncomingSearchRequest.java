package org.fourthline.cling.model.message.discovery;

import org.fourthline.cling.model.message.IncomingDatagramMessage;
import org.fourthline.cling.model.message.UpnpRequest;
import org.fourthline.cling.model.message.header.MANHeader;
import org.fourthline.cling.model.message.header.MXHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.types.NotificationSubtype;

/* loaded from: classes5.dex */
public class IncomingSearchRequest extends IncomingDatagramMessage<UpnpRequest> {
    public IncomingSearchRequest(IncomingDatagramMessage<UpnpRequest> incomingDatagramMessage) {
        super(incomingDatagramMessage);
    }

    public UpnpHeader getSearchTarget() {
        return getHeaders().getFirstHeader(UpnpHeader.Type.ST);
    }

    public Integer getMX() {
        MXHeader mXHeader = (MXHeader) getHeaders().getFirstHeader(UpnpHeader.Type.MX, MXHeader.class);
        if (mXHeader != null) {
            return mXHeader.getValue();
        }
        return null;
    }

    public boolean isMANSSDPDiscover() {
        MANHeader mANHeader = (MANHeader) getHeaders().getFirstHeader(UpnpHeader.Type.MAN, MANHeader.class);
        return mANHeader != null && mANHeader.getValue().equals(NotificationSubtype.DISCOVER.getHeaderString());
    }
}
