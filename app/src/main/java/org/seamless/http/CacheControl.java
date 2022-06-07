package org.seamless.http;

import com.xiaomi.mipush.sdk.Constants;
import io.netty.handler.codec.http.HttpHeaders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class CacheControl {
    private int maxAge = -1;
    private int sharedMaxAge = -1;
    private boolean noCache = false;
    private List<String> noCacheFields = new ArrayList();
    private boolean privateFlag = false;
    private List<String> privateFields = new ArrayList();
    private boolean noStore = false;
    private boolean noTransform = true;
    private boolean mustRevalidate = false;
    private boolean proxyRevalidate = false;
    private Map<String, String> cacheExtensions = new HashMap();

    public int getMaxAge() {
        return this.maxAge;
    }

    public void setMaxAge(int i) {
        this.maxAge = i;
    }

    public int getSharedMaxAge() {
        return this.sharedMaxAge;
    }

    public void setSharedMaxAge(int i) {
        this.sharedMaxAge = i;
    }

    public boolean isNoCache() {
        return this.noCache;
    }

    public void setNoCache(boolean z) {
        this.noCache = z;
    }

    public List<String> getNoCacheFields() {
        return this.noCacheFields;
    }

    public void setNoCacheFields(List<String> list) {
        this.noCacheFields = list;
    }

    public boolean isPrivateFlag() {
        return this.privateFlag;
    }

    public void setPrivateFlag(boolean z) {
        this.privateFlag = z;
    }

    public List<String> getPrivateFields() {
        return this.privateFields;
    }

    public void setPrivateFields(List<String> list) {
        this.privateFields = list;
    }

    public boolean isNoStore() {
        return this.noStore;
    }

    public void setNoStore(boolean z) {
        this.noStore = z;
    }

    public boolean isNoTransform() {
        return this.noTransform;
    }

    public void setNoTransform(boolean z) {
        this.noTransform = z;
    }

    public boolean isMustRevalidate() {
        return this.mustRevalidate;
    }

    public void setMustRevalidate(boolean z) {
        this.mustRevalidate = z;
    }

    public boolean isProxyRevalidate() {
        return this.proxyRevalidate;
    }

    public void setProxyRevalidate(boolean z) {
        this.proxyRevalidate = z;
    }

    public Map<String, String> getCacheExtensions() {
        return this.cacheExtensions;
    }

    public void setCacheExtensions(Map<String, String> map) {
        this.cacheExtensions = map;
    }

    public static CacheControl valueOf(String str) throws IllegalArgumentException {
        String str2;
        if (str == null) {
            return null;
        }
        CacheControl cacheControl = new CacheControl();
        for (String str3 : str.split(Constants.ACCEPT_TIME_SEPARATOR_SP)) {
            String[] split = str3.trim().split("=");
            String trim = split[0].trim();
            if (split.length > 1) {
                str2 = split[1].trim();
                if (str2.startsWith("\"")) {
                    str2 = str2.substring(1);
                }
                if (str2.endsWith("\"")) {
                    str2 = str2.substring(0, str2.length() - 1);
                }
            } else {
                str2 = null;
            }
            String lowerCase = trim.toLowerCase();
            if ("no-cache".equals(lowerCase)) {
                cacheControl.setNoCache(true);
                if (str2 != null && !"".equals(str2)) {
                    cacheControl.getNoCacheFields().add(str2);
                }
            } else if ("private".equals(lowerCase)) {
                cacheControl.setPrivateFlag(true);
                if (str2 != null && !"".equals(str2)) {
                    cacheControl.getPrivateFields().add(str2);
                }
            } else if (HttpHeaders.Values.NO_STORE.equals(lowerCase)) {
                cacheControl.setNoStore(true);
            } else if ("max-age".equals(lowerCase)) {
                if (str2 != null) {
                    cacheControl.setMaxAge(Integer.valueOf(str2).intValue());
                } else {
                    throw new IllegalArgumentException("CacheControl max-age header does not have a value: " + str2);
                }
            } else if (HttpHeaders.Values.S_MAXAGE.equals(lowerCase)) {
                if (str2 != null) {
                    cacheControl.setSharedMaxAge(Integer.valueOf(str2).intValue());
                } else {
                    throw new IllegalArgumentException("CacheControl s-maxage header does not have a value: " + str2);
                }
            } else if ("no-transform".equals(lowerCase)) {
                cacheControl.setNoTransform(true);
            } else if ("must-revalidate".equals(lowerCase)) {
                cacheControl.setMustRevalidate(true);
            } else if ("proxy-revalidate".equals(lowerCase)) {
                cacheControl.setProxyRevalidate(true);
            } else if (!"public".equals(lowerCase)) {
                if (str2 == null) {
                    str2 = "";
                }
                cacheControl.getCacheExtensions().put(trim, str2);
            }
        }
        return cacheControl;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!isPrivateFlag()) {
            sb.append("public");
        }
        if (isMustRevalidate()) {
            append("must-revalidate", sb);
        }
        if (isNoTransform()) {
            append("no-transform", sb);
        }
        if (isNoStore()) {
            append(HttpHeaders.Values.NO_STORE, sb);
        }
        if (isProxyRevalidate()) {
            append("proxy-revalidate", sb);
        }
        if (getSharedMaxAge() > -1) {
            StringBuilder append = append(HttpHeaders.Values.S_MAXAGE, sb);
            append.append("=");
            append.append(getSharedMaxAge());
        }
        if (getMaxAge() > -1) {
            StringBuilder append2 = append("max-age", sb);
            append2.append("=");
            append2.append(getMaxAge());
        }
        if (isNoCache()) {
            if (getNoCacheFields().size() < 1) {
                append("no-cache", sb);
            } else {
                for (String str : getNoCacheFields()) {
                    StringBuilder append3 = append("no-cache", sb);
                    append3.append("=\"");
                    append3.append(str);
                    append3.append("\"");
                }
            }
        }
        if (isPrivateFlag()) {
            if (getPrivateFields().size() < 1) {
                append("private", sb);
            } else {
                for (String str2 : getPrivateFields()) {
                    StringBuilder append4 = append("private", sb);
                    append4.append("=\"");
                    append4.append(str2);
                    append4.append("\"");
                }
            }
        }
        for (String str3 : getCacheExtensions().keySet()) {
            String str4 = getCacheExtensions().get(str3);
            append(str3, sb);
            if (str4 != null && !"".equals(str4)) {
                sb.append("=\"");
                sb.append(str4);
                sb.append("\"");
            }
        }
        return sb.toString();
    }

    private StringBuilder append(String str, StringBuilder sb) {
        if (sb.length() > 0) {
            sb.append(", ");
        }
        sb.append(str);
        return sb;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CacheControl cacheControl = (CacheControl) obj;
        return this.maxAge == cacheControl.maxAge && this.mustRevalidate == cacheControl.mustRevalidate && this.noCache == cacheControl.noCache && this.noStore == cacheControl.noStore && this.noTransform == cacheControl.noTransform && this.privateFlag == cacheControl.privateFlag && this.proxyRevalidate == cacheControl.proxyRevalidate && this.sharedMaxAge == cacheControl.sharedMaxAge && this.cacheExtensions.equals(cacheControl.cacheExtensions) && this.noCacheFields.equals(cacheControl.noCacheFields) && this.privateFields.equals(cacheControl.privateFields);
    }

    public int hashCode() {
        return (((((((((((((((((((this.maxAge * 31) + this.sharedMaxAge) * 31) + (this.noCache ? 1 : 0)) * 31) + this.noCacheFields.hashCode()) * 31) + (this.privateFlag ? 1 : 0)) * 31) + this.privateFields.hashCode()) * 31) + (this.noStore ? 1 : 0)) * 31) + (this.noTransform ? 1 : 0)) * 31) + (this.mustRevalidate ? 1 : 0)) * 31) + (this.proxyRevalidate ? 1 : 0)) * 31) + this.cacheExtensions.hashCode();
    }
}
