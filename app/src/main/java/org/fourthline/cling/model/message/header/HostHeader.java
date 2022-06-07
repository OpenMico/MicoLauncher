package org.fourthline.cling.model.message.header;

import org.fourthline.cling.model.Constants;
import org.fourthline.cling.model.types.HostPort;

/* loaded from: classes5.dex */
public class HostHeader extends UpnpHeader<HostPort> {
    int port = Constants.UPNP_MULTICAST_PORT;
    String group = Constants.IPV4_UPNP_MULTICAST_GROUP;

    public HostHeader() {
        setValue(new HostPort(this.group, this.port));
    }

    public HostHeader(int i) {
        setValue(new HostPort(this.group, i));
    }

    public HostHeader(String str, int i) {
        setValue(new HostPort(str, i));
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        if (str.contains(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR)) {
            try {
                this.port = Integer.valueOf(str.substring(str.indexOf(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR) + 1)).intValue();
                this.group = str.substring(0, str.indexOf(com.xiaomi.mipush.sdk.Constants.COLON_SEPARATOR));
                setValue(new HostPort(this.group, this.port));
            } catch (NumberFormatException e) {
                throw new InvalidHeaderException("Invalid HOST header value, can't parse port: " + str + " - " + e.getMessage());
            }
        } else {
            this.group = str;
            setValue(new HostPort(this.group, this.port));
        }
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        return getValue().toString();
    }
}
