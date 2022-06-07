package org.fourthline.cling.model.meta;

import java.util.ArrayList;
import java.util.List;
import org.fourthline.cling.model.Validatable;
import org.fourthline.cling.model.ValidationError;

/* loaded from: classes5.dex */
public class UDAVersion implements Validatable {
    private int major;
    private int minor;

    public UDAVersion() {
        this.major = 1;
        this.minor = 0;
    }

    public UDAVersion(int i, int i2) {
        this.major = 1;
        this.minor = 0;
        this.major = i;
        this.minor = i2;
    }

    public int getMajor() {
        return this.major;
    }

    public int getMinor() {
        return this.minor;
    }

    @Override // org.fourthline.cling.model.Validatable
    public List<ValidationError> validate() {
        ArrayList arrayList = new ArrayList();
        if (getMajor() != 1) {
            arrayList.add(new ValidationError(getClass(), "major", "UDA major spec version must be 1"));
        }
        if (getMajor() < 0) {
            arrayList.add(new ValidationError(getClass(), "minor", "UDA minor spec version must be equal or greater 0"));
        }
        return arrayList;
    }
}
