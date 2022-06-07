package org.fourthline.cling.model.message.header;

/* loaded from: classes5.dex */
public class EXTHeader extends UpnpHeader<String> {
    public static final String DEFAULT_VALUE = "";

    public EXTHeader() {
        setValue("");
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        if (str != null && str.length() > 0) {
            throw new InvalidHeaderException("Invalid EXT header, it has no value: " + str);
        }
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        return getValue();
    }
}
