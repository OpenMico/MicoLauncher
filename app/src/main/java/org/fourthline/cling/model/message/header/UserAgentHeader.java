package org.fourthline.cling.model.message.header;

/* loaded from: classes5.dex */
public class UserAgentHeader extends UpnpHeader<String> {
    public UserAgentHeader() {
    }

    public UserAgentHeader(String str) {
        setValue(str);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        setValue(str);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        return getValue();
    }
}
