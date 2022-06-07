package org.fourthline.cling.model.message.discovery;

import org.fourthline.cling.model.Location;
import org.fourthline.cling.model.message.header.UDNHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.types.NotificationSubtype;

/* loaded from: classes5.dex */
public class OutgoingNotificationRequestUDN extends OutgoingNotificationRequest {
    public OutgoingNotificationRequestUDN(Location location, LocalDevice localDevice, NotificationSubtype notificationSubtype) {
        super(location, localDevice, notificationSubtype);
        getHeaders().add(UpnpHeader.Type.NT, new UDNHeader(localDevice.getIdentity().getUdn()));
        getHeaders().add(UpnpHeader.Type.USN, new UDNHeader(localDevice.getIdentity().getUdn()));
    }
}
