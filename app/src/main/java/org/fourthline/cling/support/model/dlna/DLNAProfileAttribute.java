package org.fourthline.cling.support.model.dlna;

/* loaded from: classes5.dex */
public class DLNAProfileAttribute extends DLNAAttribute<DLNAProfiles> {
    public DLNAProfileAttribute() {
        setValue(DLNAProfiles.NONE);
    }

    public DLNAProfileAttribute(DLNAProfiles dLNAProfiles) {
        setValue(dLNAProfiles);
    }

    @Override // org.fourthline.cling.support.model.dlna.DLNAAttribute
    public void setString(String str, String str2) throws InvalidDLNAProtocolAttributeException {
        DLNAProfiles valueOf = DLNAProfiles.valueOf(str, str2);
        if (valueOf != null) {
            setValue(valueOf);
            return;
        }
        throw new InvalidDLNAProtocolAttributeException("Can't parse DLNA profile from: " + str);
    }

    @Override // org.fourthline.cling.support.model.dlna.DLNAAttribute
    public String getString() {
        return getValue().getCode();
    }
}
