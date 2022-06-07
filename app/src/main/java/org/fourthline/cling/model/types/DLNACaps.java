package org.fourthline.cling.model.types;

import com.xiaomi.mipush.sdk.Constants;
import java.util.Arrays;
import org.fourthline.cling.model.ModelUtil;

/* loaded from: classes5.dex */
public class DLNACaps {
    final String[] caps;

    public DLNACaps(String[] strArr) {
        this.caps = strArr;
    }

    public String[] getCaps() {
        return this.caps;
    }

    public static DLNACaps valueOf(String str) throws InvalidValueException {
        if (str == null || str.length() == 0) {
            return new DLNACaps(new String[0]);
        }
        String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        String[] strArr = new String[split.length];
        for (int i = 0; i < split.length; i++) {
            strArr[i] = split[i].trim();
        }
        return new DLNACaps(strArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && Arrays.equals(this.caps, ((DLNACaps) obj).caps);
    }

    public int hashCode() {
        return Arrays.hashCode(this.caps);
    }

    public String toString() {
        return ModelUtil.toCommaSeparatedList(getCaps());
    }
}
