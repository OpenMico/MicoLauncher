package org.fourthline.cling.model.message.discovery;

import org.fourthline.cling.model.Constants;
import org.fourthline.cling.model.ModelUtil;
import org.fourthline.cling.model.message.OutgoingDatagramMessage;
import org.fourthline.cling.model.message.UpnpRequest;
import org.fourthline.cling.model.message.header.HostHeader;
import org.fourthline.cling.model.message.header.MANHeader;
import org.fourthline.cling.model.message.header.MXHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.types.NotificationSubtype;

/* loaded from: classes5.dex */
public class OutgoingSearchRequest extends OutgoingDatagramMessage<UpnpRequest> {
    private UpnpHeader searchTarget;

    public OutgoingSearchRequest(UpnpHeader upnpHeader, int i) {
        super(new UpnpRequest(UpnpRequest.Method.MSEARCH), ModelUtil.getInetAddressByName(Constants.IPV4_UPNP_MULTICAST_GROUP), Constants.UPNP_MULTICAST_PORT);
        this.searchTarget = upnpHeader;
        getHeaders().add(UpnpHeader.Type.MAN, new MANHeader(NotificationSubtype.DISCOVER.getHeaderString()));
        getHeaders().add(UpnpHeader.Type.MX, new MXHeader(Integer.valueOf(i)));
        getHeaders().add(UpnpHeader.Type.ST, upnpHeader);
        getHeaders().add(UpnpHeader.Type.HOST, new HostHeader());
    }

    public UpnpHeader getSearchTarget() {
        return this.searchTarget;
    }
}
