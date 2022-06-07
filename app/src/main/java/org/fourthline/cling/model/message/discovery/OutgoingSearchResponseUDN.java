package org.fourthline.cling.model.message.discovery;

import org.fourthline.cling.model.Location;
import org.fourthline.cling.model.message.IncomingDatagramMessage;
import org.fourthline.cling.model.message.header.UDNHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.meta.LocalDevice;

/* loaded from: classes5.dex */
public class OutgoingSearchResponseUDN extends OutgoingSearchResponse {
    public OutgoingSearchResponseUDN(IncomingDatagramMessage incomingDatagramMessage, Location location, LocalDevice localDevice) {
        super(incomingDatagramMessage, location, localDevice);
        getHeaders().add(UpnpHeader.Type.ST, new UDNHeader(localDevice.getIdentity().getUdn()));
        getHeaders().add(UpnpHeader.Type.USN, new UDNHeader(localDevice.getIdentity().getUdn()));
    }
}
