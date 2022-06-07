package org.fourthline.cling.model.message.discovery;

import org.fourthline.cling.model.Location;
import org.fourthline.cling.model.message.IncomingDatagramMessage;
import org.fourthline.cling.model.message.header.DeviceTypeHeader;
import org.fourthline.cling.model.message.header.DeviceUSNHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.meta.LocalDevice;

/* loaded from: classes5.dex */
public class OutgoingSearchResponseDeviceType extends OutgoingSearchResponse {
    public OutgoingSearchResponseDeviceType(IncomingDatagramMessage incomingDatagramMessage, Location location, LocalDevice localDevice) {
        super(incomingDatagramMessage, location, localDevice);
        getHeaders().add(UpnpHeader.Type.ST, new DeviceTypeHeader(localDevice.getType()));
        getHeaders().add(UpnpHeader.Type.USN, new DeviceUSNHeader(localDevice.getIdentity().getUdn(), localDevice.getType()));
    }
}
