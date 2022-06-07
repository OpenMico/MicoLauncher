package org.fourthline.cling.model.message.header;

import java.net.URI;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;

/* loaded from: classes5.dex */
public class UDADeviceTypeHeader extends DeviceTypeHeader {
    public UDADeviceTypeHeader() {
    }

    public UDADeviceTypeHeader(URI uri) {
        super(uri);
    }

    public UDADeviceTypeHeader(DeviceType deviceType) {
        super(deviceType);
    }

    @Override // org.fourthline.cling.model.message.header.DeviceTypeHeader, org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        try {
            setValue(UDADeviceType.valueOf(str));
        } catch (Exception e) {
            throw new InvalidHeaderException("Invalid UDA device type header value, " + e.getMessage());
        }
    }
}
