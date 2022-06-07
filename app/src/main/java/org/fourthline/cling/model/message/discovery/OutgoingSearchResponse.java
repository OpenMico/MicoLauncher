package org.fourthline.cling.model.message.discovery;

import org.fourthline.cling.model.Constants;
import org.fourthline.cling.model.Location;
import org.fourthline.cling.model.message.IncomingDatagramMessage;
import org.fourthline.cling.model.message.OutgoingDatagramMessage;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.header.EXTHeader;
import org.fourthline.cling.model.message.header.InterfaceMacHeader;
import org.fourthline.cling.model.message.header.LocationHeader;
import org.fourthline.cling.model.message.header.MaxAgeHeader;
import org.fourthline.cling.model.message.header.ServerHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.meta.LocalDevice;

/* loaded from: classes5.dex */
public class OutgoingSearchResponse extends OutgoingDatagramMessage<UpnpResponse> {
    public OutgoingSearchResponse(IncomingDatagramMessage incomingDatagramMessage, Location location, LocalDevice localDevice) {
        super(new UpnpResponse(UpnpResponse.Status.OK), incomingDatagramMessage.getSourceAddress(), incomingDatagramMessage.getSourcePort());
        getHeaders().add(UpnpHeader.Type.MAX_AGE, new MaxAgeHeader(localDevice.getIdentity().getMaxAgeSeconds()));
        getHeaders().add(UpnpHeader.Type.LOCATION, new LocationHeader(location.getURL()));
        getHeaders().add(UpnpHeader.Type.SERVER, new ServerHeader());
        getHeaders().add(UpnpHeader.Type.EXT, new EXTHeader());
        if ("true".equals(System.getProperty(Constants.SYSTEM_PROPERTY_ANNOUNCE_MAC_ADDRESS)) && location.getNetworkAddress().getHardwareAddress() != null) {
            getHeaders().add(UpnpHeader.Type.EXT_IFACE_MAC, new InterfaceMacHeader(location.getNetworkAddress().getHardwareAddress()));
        }
    }
}
