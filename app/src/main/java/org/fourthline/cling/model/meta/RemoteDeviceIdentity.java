package org.fourthline.cling.model.meta;

import java.net.InetAddress;
import java.net.URL;
import org.fourthline.cling.model.ModelUtil;
import org.fourthline.cling.model.message.discovery.IncomingNotificationRequest;
import org.fourthline.cling.model.message.discovery.IncomingSearchResponse;
import org.fourthline.cling.model.types.UDN;

/* loaded from: classes5.dex */
public class RemoteDeviceIdentity extends DeviceIdentity {
    private final URL descriptorURL;
    private final InetAddress discoveredOnLocalAddress;
    private final byte[] interfaceMacAddress;

    public RemoteDeviceIdentity(UDN udn, RemoteDeviceIdentity remoteDeviceIdentity) {
        this(udn, remoteDeviceIdentity.getMaxAgeSeconds(), remoteDeviceIdentity.getDescriptorURL(), remoteDeviceIdentity.getInterfaceMacAddress(), remoteDeviceIdentity.getDiscoveredOnLocalAddress());
    }

    public RemoteDeviceIdentity(UDN udn, Integer num, URL url, byte[] bArr, InetAddress inetAddress) {
        super(udn, num);
        this.descriptorURL = url;
        this.interfaceMacAddress = bArr;
        this.discoveredOnLocalAddress = inetAddress;
    }

    public RemoteDeviceIdentity(IncomingNotificationRequest incomingNotificationRequest) {
        this(incomingNotificationRequest.getUDN(), incomingNotificationRequest.getMaxAge(), incomingNotificationRequest.getLocationURL(), incomingNotificationRequest.getInterfaceMacHeader(), incomingNotificationRequest.getLocalAddress());
    }

    public RemoteDeviceIdentity(IncomingSearchResponse incomingSearchResponse) {
        this(incomingSearchResponse.getRootDeviceUDN(), incomingSearchResponse.getMaxAge(), incomingSearchResponse.getLocationURL(), incomingSearchResponse.getInterfaceMacHeader(), incomingSearchResponse.getLocalAddress());
    }

    public URL getDescriptorURL() {
        return this.descriptorURL;
    }

    public byte[] getInterfaceMacAddress() {
        return this.interfaceMacAddress;
    }

    public InetAddress getDiscoveredOnLocalAddress() {
        return this.discoveredOnLocalAddress;
    }

    public byte[] getWakeOnLANBytes() {
        if (getInterfaceMacAddress() == null) {
            return null;
        }
        int i = 6;
        byte[] bArr = new byte[(getInterfaceMacAddress().length * 16) + 6];
        for (int i2 = 0; i2 < 6; i2++) {
            bArr[i2] = -1;
        }
        while (i < bArr.length) {
            System.arraycopy(getInterfaceMacAddress(), 0, bArr, i, getInterfaceMacAddress().length);
            i += getInterfaceMacAddress().length;
        }
        return bArr;
    }

    @Override // org.fourthline.cling.model.meta.DeviceIdentity
    public String toString() {
        if (ModelUtil.ANDROID_RUNTIME) {
            return "(RemoteDeviceIdentity) UDN: " + getUdn() + ", Descriptor: " + getDescriptorURL();
        }
        return "(" + getClass().getSimpleName() + ") UDN: " + getUdn() + ", Descriptor: " + getDescriptorURL();
    }
}
