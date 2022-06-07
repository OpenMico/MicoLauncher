package org.fourthline.cling.model.message.discovery;

import org.fourthline.cling.model.Location;
import org.fourthline.cling.model.message.IncomingDatagramMessage;
import org.fourthline.cling.model.message.header.RootDeviceHeader;
import org.fourthline.cling.model.message.header.USNRootDeviceHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.meta.LocalDevice;

/* loaded from: classes5.dex */
public class OutgoingSearchResponseRootDevice extends OutgoingSearchResponse {
    public OutgoingSearchResponseRootDevice(IncomingDatagramMessage incomingDatagramMessage, Location location, LocalDevice localDevice) {
        super(incomingDatagramMessage, location, localDevice);
        getHeaders().add(UpnpHeader.Type.ST, new RootDeviceHeader());
        getHeaders().add(UpnpHeader.Type.USN, new USNRootDeviceHeader(localDevice.getIdentity().getUdn()));
    }
}
