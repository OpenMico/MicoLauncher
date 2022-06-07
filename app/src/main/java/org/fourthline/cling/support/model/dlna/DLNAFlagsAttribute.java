package org.fourthline.cling.support.model.dlna;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;

/* loaded from: classes5.dex */
public class DLNAFlagsAttribute extends DLNAAttribute<EnumSet<DLNAFlags>> {
    public DLNAFlagsAttribute() {
        setValue(EnumSet.noneOf(DLNAFlags.class));
    }

    public DLNAFlagsAttribute(DLNAFlags... dLNAFlagsArr) {
        if (dLNAFlagsArr != null && dLNAFlagsArr.length > 0) {
            DLNAFlags dLNAFlags = dLNAFlagsArr[0];
            if (dLNAFlagsArr.length > 1) {
                System.arraycopy(dLNAFlagsArr, 1, dLNAFlagsArr, 0, dLNAFlagsArr.length - 1);
                setValue(EnumSet.of(dLNAFlags, dLNAFlagsArr));
                return;
            }
            setValue(EnumSet.of(dLNAFlags));
        }
    }

    @Override // org.fourthline.cling.support.model.dlna.DLNAAttribute
    public void setString(String str, String str2) throws InvalidDLNAProtocolAttributeException {
        EnumSet noneOf = EnumSet.noneOf(DLNAFlags.class);
        try {
            int parseInt = Integer.parseInt(str.substring(0, str.length() - 24), 16);
            DLNAFlags[] values = DLNAFlags.values();
            for (DLNAFlags dLNAFlags : values) {
                if (dLNAFlags.getCode() == (dLNAFlags.getCode() & parseInt)) {
                    noneOf.add(dLNAFlags);
                }
            }
        } catch (Exception unused) {
        }
        if (!noneOf.isEmpty()) {
            setValue(noneOf);
            return;
        }
        throw new InvalidDLNAProtocolAttributeException("Can't parse DLNA flags integer from: " + str);
    }

    @Override // org.fourthline.cling.support.model.dlna.DLNAAttribute
    public String getString() {
        Iterator it = getValue().iterator();
        int i = 0;
        while (it.hasNext()) {
            i |= ((DLNAFlags) it.next()).getCode();
        }
        return String.format(Locale.ROOT, "%08x%024x", Integer.valueOf(i), 0);
    }
}
