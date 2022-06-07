package org.fourthline.cling.model.message.header;

import org.fourthline.cling.model.types.NotificationSubtype;

/* loaded from: classes5.dex */
public class STAllHeader extends UpnpHeader<NotificationSubtype> {
    public STAllHeader() {
        setValue(NotificationSubtype.ALL);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        if (!str.equals(NotificationSubtype.ALL.getHeaderString())) {
            throw new InvalidHeaderException("Invalid ST header value (not " + NotificationSubtype.ALL + "): " + str);
        }
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        return getValue().getHeaderString();
    }
}
