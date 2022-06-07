package org.fourthline.cling.model.types;

import com.xiaomi.mipush.sdk.Constants;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class DLNADoc {
    public static final Pattern PATTERN = Pattern.compile("(.+?)[ -]([0-9].[0-9]{2})");
    private final String devClass;
    private final String version;

    /* loaded from: classes5.dex */
    public enum Version {
        V1_0("1.00"),
        V1_5("1.50");
        
        String s;

        Version(String str) {
            this.s = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.s;
        }
    }

    public DLNADoc(String str, String str2) {
        this.devClass = str;
        this.version = str2;
    }

    public DLNADoc(String str, Version version) {
        this.devClass = str;
        this.version = version.s;
    }

    public String getDevClass() {
        return this.devClass;
    }

    public String getVersion() {
        return this.version;
    }

    public static DLNADoc valueOf(String str) throws InvalidValueException {
        Matcher matcher = PATTERN.matcher(str);
        if (matcher.matches()) {
            return new DLNADoc(matcher.group(1), matcher.group(2));
        }
        throw new InvalidValueException("Can't parse DLNADoc: " + str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DLNADoc dLNADoc = (DLNADoc) obj;
        return this.devClass.equals(dLNADoc.devClass) && this.version.equals(dLNADoc.version);
    }

    public int hashCode() {
        return (this.devClass.hashCode() * 31) + this.version.hashCode();
    }

    public String toString() {
        return getDevClass() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + getVersion();
    }
}
