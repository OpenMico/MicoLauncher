package org.fourthline.cling.support.model.dlna;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes5.dex */
public class DLNAOperationsAttribute extends DLNAAttribute<EnumSet<DLNAOperations>> {
    public DLNAOperationsAttribute() {
        setValue(EnumSet.of(DLNAOperations.NONE));
    }

    public DLNAOperationsAttribute(DLNAOperations... dLNAOperationsArr) {
        if (dLNAOperationsArr != null && dLNAOperationsArr.length > 0) {
            DLNAOperations dLNAOperations = dLNAOperationsArr[0];
            if (dLNAOperationsArr.length > 1) {
                System.arraycopy(dLNAOperationsArr, 1, dLNAOperationsArr, 0, dLNAOperationsArr.length - 1);
                setValue(EnumSet.of(dLNAOperations, dLNAOperationsArr));
                return;
            }
            setValue(EnumSet.of(dLNAOperations));
        }
    }

    @Override // org.fourthline.cling.support.model.dlna.DLNAAttribute
    public void setString(String str, String str2) throws InvalidDLNAProtocolAttributeException {
        EnumSet noneOf = EnumSet.noneOf(DLNAOperations.class);
        try {
            int parseInt = Integer.parseInt(str, 16);
            DLNAOperations[] values = DLNAOperations.values();
            for (DLNAOperations dLNAOperations : values) {
                int code = dLNAOperations.getCode() & parseInt;
                if (dLNAOperations != DLNAOperations.NONE && dLNAOperations.getCode() == code) {
                    noneOf.add(dLNAOperations);
                }
            }
        } catch (NumberFormatException unused) {
        }
        if (!noneOf.isEmpty()) {
            setValue(noneOf);
            return;
        }
        throw new InvalidDLNAProtocolAttributeException("Can't parse DLNA operations integer from: " + str);
    }

    @Override // org.fourthline.cling.support.model.dlna.DLNAAttribute
    public String getString() {
        int code = DLNAOperations.NONE.getCode();
        Iterator it = getValue().iterator();
        while (it.hasNext()) {
            code |= ((DLNAOperations) it.next()).getCode();
        }
        return String.format(Locale.ROOT, "%02x", Integer.valueOf(code));
    }
}
