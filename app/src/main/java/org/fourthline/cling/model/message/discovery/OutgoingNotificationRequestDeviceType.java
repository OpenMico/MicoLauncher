package org.fourthline.cling.model.message.discovery;

import org.fourthline.cling.model.Location;
import org.fourthline.cling.model.message.header.DeviceTypeHeader;
import org.fourthline.cling.model.message.header.DeviceUSNHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.types.NotificationSubtype;

/* loaded from: classes5.dex */
public class OutgoingNotificationRequestDeviceType extends OutgoingNotificationRequest {
    public OutgoingNotificationRequestDeviceType(Location location, LocalDevice localDevice, NotificationSubtype notificationSubtype) {
        super(location, localDevice, notificationSubtype);
        getHeaders().add(UpnpHeader.Type.NT, new DeviceTypeHeader(localDevice.getType()));
        getHeaders().add(UpnpHeader.Type.USN, new DeviceUSNHeader(localDevice.getIdentity().getUdn(), localDevice.getType()));
    }
}
