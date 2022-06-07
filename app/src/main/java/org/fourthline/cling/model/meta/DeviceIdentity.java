package org.fourthline.cling.model.meta;

import java.util.ArrayList;
import java.util.List;
import org.fourthline.cling.model.Validatable;
import org.fourthline.cling.model.ValidationError;
import org.fourthline.cling.model.types.UDN;

/* loaded from: classes5.dex */
public class DeviceIdentity implements Validatable {
    private final Integer maxAgeSeconds;
    private final UDN udn;

    public DeviceIdentity(UDN udn, DeviceIdentity deviceIdentity) {
        this.udn = udn;
        this.maxAgeSeconds = deviceIdentity.getMaxAgeSeconds();
    }

    public DeviceIdentity(UDN udn) {
        this.udn = udn;
        this.maxAgeSeconds = 1800;
    }

    public DeviceIdentity(UDN udn, Integer num) {
        this.udn = udn;
        this.maxAgeSeconds = num;
    }

    public UDN getUdn() {
        return this.udn;
    }

    public Integer getMaxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.udn.equals(((DeviceIdentity) obj).udn);
    }

    public int hashCode() {
        return this.udn.hashCode();
    }

    public String toString() {
        return "(" + getClass().getSimpleName() + ") UDN: " + getUdn();
    }

    @Override // org.fourthline.cling.model.Validatable
    public List<ValidationError> validate() {
        ArrayList arrayList = new ArrayList();
        if (getUdn() == null) {
            arrayList.add(new ValidationError(getClass(), "major", "Device has no UDN"));
        }
        return arrayList;
    }
}
