package org.fourthline.cling.support.model.dlna.message.header;

import org.fourthline.cling.model.message.header.InvalidHeaderException;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;

/* loaded from: classes5.dex */
public class BufferBytesHeader extends DLNAHeader<UnsignedIntegerFourBytes> {
    public BufferBytesHeader() {
        setValue(new UnsignedIntegerFourBytes(0L));
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        try {
            setValue(new UnsignedIntegerFourBytes(str));
        } catch (NumberFormatException unused) {
            throw new InvalidHeaderException("Invalid header value: " + str);
        }
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        return getValue().getValue().toString();
    }
}
