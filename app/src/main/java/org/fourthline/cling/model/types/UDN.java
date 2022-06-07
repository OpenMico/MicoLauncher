package org.fourthline.cling.model.types;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.UUID;
import java.util.logging.Logger;
import org.fourthline.cling.model.ModelUtil;

/* loaded from: classes5.dex */
public class UDN {
    public static final String PREFIX = "uuid:";
    private static final Logger log = Logger.getLogger(UDN.class.getName());
    private String identifierString;

    public UDN(String str) {
        this.identifierString = str;
    }

    public UDN(UUID uuid) {
        this.identifierString = uuid.toString();
    }

    public boolean isUDA11Compliant() {
        try {
            UUID.fromString(this.identifierString);
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public String getIdentifierString() {
        return this.identifierString;
    }

    public static UDN valueOf(String str) {
        if (str.startsWith("uuid:")) {
            str = str.substring(5);
        }
        return new UDN(str);
    }

    public static UDN uniqueSystemIdentifier(String str) {
        StringBuilder sb = new StringBuilder();
        if (!ModelUtil.ANDROID_RUNTIME) {
            try {
                sb.append(new String(ModelUtil.getFirstNetworkInterfaceHardwareAddress(), "UTF-8"));
                try {
                    return new UDN(new UUID(new BigInteger(-1, MessageDigest.getInstance("MD5").digest(sb.toString().getBytes("UTF-8"))).longValue(), str.hashCode()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } catch (UnsupportedEncodingException e2) {
                throw new RuntimeException(e2);
            }
        } else {
            throw new RuntimeException("This method does not create a unique identifier on Android, see the Javadoc and use new UDN(UUID) instead!");
        }
    }

    public String toString() {
        return "uuid:" + getIdentifierString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UDN)) {
            return false;
        }
        return this.identifierString.equals(((UDN) obj).identifierString);
    }

    public int hashCode() {
        return this.identifierString.hashCode();
    }
}
