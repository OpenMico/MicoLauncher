package org.fourthline.cling.model.types;

import org.seamless.util.io.Base64Coder;

/* loaded from: classes5.dex */
public class Base64Datatype extends AbstractDatatype<byte[]> {
    @Override // org.fourthline.cling.model.types.AbstractDatatype
    public Class<byte[]> getValueType() {
        return byte[].class;
    }

    @Override // org.fourthline.cling.model.types.AbstractDatatype, org.fourthline.cling.model.types.Datatype
    public byte[] valueOf(String str) throws InvalidValueException {
        if (str.equals("")) {
            return null;
        }
        try {
            return Base64Coder.decode(str);
        } catch (Exception e) {
            throw new InvalidValueException(e.getMessage(), e);
        }
    }

    public String getString(byte[] bArr) throws InvalidValueException {
        if (bArr == null) {
            return "";
        }
        try {
            return new String(Base64Coder.encode(bArr), "UTF-8");
        } catch (Exception e) {
            throw new InvalidValueException(e.getMessage(), e);
        }
    }
}
