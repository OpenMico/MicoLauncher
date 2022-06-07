package org.fourthline.cling.model;

import com.fasterxml.jackson.core.JsonPointer;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes5.dex */
public class ServerClientTokens {
    public static final String UNKNOWN_PLACEHOLDER = "UNKNOWN";
    private int majorVersion;
    private int minorVersion;
    private String osName;
    private String osVersion;
    private String productName;
    private String productVersion;

    public ServerClientTokens() {
        this.majorVersion = 1;
        this.minorVersion = 0;
        this.osName = System.getProperty("os.name").replaceAll("[^a-zA-Z0-9\\.\\-_]", "");
        this.osVersion = System.getProperty("os.version").replaceAll("[^a-zA-Z0-9\\.\\-_]", "");
        this.productName = UserConstants.PRODUCT_TOKEN_NAME;
        this.productVersion = "2.0";
    }

    public ServerClientTokens(int i, int i2) {
        this.majorVersion = 1;
        this.minorVersion = 0;
        this.osName = System.getProperty("os.name").replaceAll("[^a-zA-Z0-9\\.\\-_]", "");
        this.osVersion = System.getProperty("os.version").replaceAll("[^a-zA-Z0-9\\.\\-_]", "");
        this.productName = UserConstants.PRODUCT_TOKEN_NAME;
        this.productVersion = "2.0";
        this.majorVersion = i;
        this.minorVersion = i2;
    }

    public ServerClientTokens(String str, String str2) {
        this.majorVersion = 1;
        this.minorVersion = 0;
        this.osName = System.getProperty("os.name").replaceAll("[^a-zA-Z0-9\\.\\-_]", "");
        this.osVersion = System.getProperty("os.version").replaceAll("[^a-zA-Z0-9\\.\\-_]", "");
        this.productName = UserConstants.PRODUCT_TOKEN_NAME;
        this.productVersion = "2.0";
        this.productName = str;
        this.productVersion = str2;
    }

    public ServerClientTokens(int i, int i2, String str, String str2, String str3, String str4) {
        this.majorVersion = 1;
        this.minorVersion = 0;
        this.osName = System.getProperty("os.name").replaceAll("[^a-zA-Z0-9\\.\\-_]", "");
        this.osVersion = System.getProperty("os.version").replaceAll("[^a-zA-Z0-9\\.\\-_]", "");
        this.productName = UserConstants.PRODUCT_TOKEN_NAME;
        this.productVersion = "2.0";
        this.majorVersion = i;
        this.minorVersion = i2;
        this.osName = str;
        this.osVersion = str2;
        this.productName = str3;
        this.productVersion = str4;
    }

    public int getMajorVersion() {
        return this.majorVersion;
    }

    public void setMajorVersion(int i) {
        this.majorVersion = i;
    }

    public int getMinorVersion() {
        return this.minorVersion;
    }

    public void setMinorVersion(int i) {
        this.minorVersion = i;
    }

    public String getOsName() {
        return this.osName;
    }

    public void setOsName(String str) {
        this.osName = str;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public void setOsVersion(String str) {
        this.osVersion = str;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String str) {
        this.productName = str;
    }

    public String getProductVersion() {
        return this.productVersion;
    }

    public void setProductVersion(String str) {
        this.productVersion = str;
    }

    public String toString() {
        return getOsName() + "/" + getOsVersion() + " UPnP/" + getMajorVersion() + "." + getMinorVersion() + StringUtils.SPACE + getProductName() + "/" + getProductVersion();
    }

    public String getHttpToken() {
        StringBuilder sb = new StringBuilder(256);
        sb.append(this.osName.indexOf(32) != -1 ? this.osName.replace(' ', '_') : this.osName);
        sb.append(JsonPointer.SEPARATOR);
        sb.append(this.osVersion.indexOf(32) != -1 ? this.osVersion.replace(' ', '_') : this.osVersion);
        sb.append(" UPnP/");
        sb.append(this.majorVersion);
        sb.append('.');
        sb.append(this.minorVersion);
        sb.append(' ');
        sb.append(this.productName.indexOf(32) != -1 ? this.productName.replace(' ', '_') : this.productName);
        sb.append(JsonPointer.SEPARATOR);
        sb.append(this.productVersion.indexOf(32) != -1 ? this.productVersion.replace(' ', '_') : this.productVersion);
        return sb.toString();
    }

    public String getOsToken() {
        return getOsName().replaceAll(StringUtils.SPACE, "_") + "/" + getOsVersion().replaceAll(StringUtils.SPACE, "_");
    }

    public String getProductToken() {
        return getProductName().replaceAll(StringUtils.SPACE, "_") + "/" + getProductVersion().replaceAll(StringUtils.SPACE, "_");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ServerClientTokens serverClientTokens = (ServerClientTokens) obj;
        return this.majorVersion == serverClientTokens.majorVersion && this.minorVersion == serverClientTokens.minorVersion && this.osName.equals(serverClientTokens.osName) && this.osVersion.equals(serverClientTokens.osVersion) && this.productName.equals(serverClientTokens.productName) && this.productVersion.equals(serverClientTokens.productVersion);
    }

    public int hashCode() {
        return (((((((((this.majorVersion * 31) + this.minorVersion) * 31) + this.osName.hashCode()) * 31) + this.osVersion.hashCode()) * 31) + this.productName.hashCode()) * 31) + this.productVersion.hashCode();
    }
}
