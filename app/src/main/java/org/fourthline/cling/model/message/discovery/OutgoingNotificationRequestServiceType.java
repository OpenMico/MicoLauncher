package org.fourthline.cling.model.message.discovery;

import org.fourthline.cling.model.Location;
import org.fourthline.cling.model.message.header.ServiceTypeHeader;
import org.fourthline.cling.model.message.header.ServiceUSNHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.types.NotificationSubtype;
import org.fourthline.cling.model.types.ServiceType;

/* loaded from: classes5.dex */
public class OutgoingNotificationRequestServiceType extends OutgoingNotificationRequest {
    public OutgoingNotificationRequestServiceType(Location location, LocalDevice localDevice, NotificationSubtype notificationSubtype, ServiceType serviceType) {
        super(location, localDevice, notificationSubtype);
        getHeaders().add(UpnpHeader.Type.NT, new ServiceTypeHeader(serviceType));
        getHeaders().add(UpnpHeader.Type.USN, new ServiceUSNHeader(localDevice.getIdentity().getUdn(), serviceType));
    }
}
