package org.fourthline.cling.model.message.header;

import org.fourthline.cling.model.types.InvalidValueException;
import org.fourthline.cling.model.types.PragmaType;

/* loaded from: classes5.dex */
public class PragmaHeader extends UpnpHeader<PragmaType> {
    public PragmaHeader() {
    }

    public PragmaHeader(PragmaType pragmaType) {
        setValue(pragmaType);
    }

    public PragmaHeader(String str) {
        setString(str);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        try {
            setValue(PragmaType.valueOf(str));
        } catch (InvalidValueException e) {
            throw new InvalidHeaderException("Invalid Range Header: " + e.getMessage());
        }
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        return getValue().getString();
    }
}
