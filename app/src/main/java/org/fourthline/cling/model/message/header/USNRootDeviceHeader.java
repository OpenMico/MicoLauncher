package org.fourthline.cling.model.message.header;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import org.fourthline.cling.model.types.UDN;

/* loaded from: classes5.dex */
public class USNRootDeviceHeader extends UpnpHeader<UDN> {
    public static final String ROOT_DEVICE_SUFFIX = "::upnp:rootdevice";

    public USNRootDeviceHeader() {
    }

    public USNRootDeviceHeader(UDN udn) {
        setValue(udn);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        if (!str.startsWith("uuid:") || !str.endsWith(ROOT_DEVICE_SUFFIX)) {
            throw new InvalidHeaderException("Invalid root device USN header value, must start with 'uuid:' and end with '::upnp:rootdevice' but is '" + str + LrcRow.SINGLE_QUOTE);
        }
        setValue(new UDN(str.substring(5, str.length() - 17)));
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        return getValue().toString() + ROOT_DEVICE_SUFFIX;
    }
}
