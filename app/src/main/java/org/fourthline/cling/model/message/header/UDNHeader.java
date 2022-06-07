package org.fourthline.cling.model.message.header;

import org.fourthline.cling.model.types.UDN;

/* loaded from: classes5.dex */
public class UDNHeader extends UpnpHeader<UDN> {
    public UDNHeader() {
    }

    public UDNHeader(UDN udn) {
        setValue(udn);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        if (!str.startsWith("uuid:")) {
            throw new InvalidHeaderException("Invalid UDA header value, must start with 'uuid:': " + str);
        } else if (!str.contains("::urn")) {
            setValue(new UDN(str.substring(5)));
        } else {
            throw new InvalidHeaderException("Invalid UDA header value, must not contain '::urn': " + str);
        }
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        return getValue().toString();
    }
}
