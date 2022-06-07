package javax.servlet.http;

import io.netty.handler.codec.http.cookie.CookieHeaderNames;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/* loaded from: classes5.dex */
public class Cookie implements Serializable, Cloneable {
    private static final String a;
    private static ResourceBundle b = ResourceBundle.getBundle("javax.servlet.http.LocalStrings");
    private static final long serialVersionUID = -6454587001725327448L;
    private String comment;
    private String domain;
    private String name;
    private String path;
    private boolean secure;
    private String value;
    private int maxAge = -1;
    private int version = 0;
    private boolean isHttpOnly = false;

    static {
        if (Boolean.valueOf(System.getProperty("org.glassfish.web.rfc2109_cookie_names_enforced", "true")).booleanValue()) {
            a = "/()<>@,;:\\\"[]?={} \t";
        } else {
            a = ",; ";
        }
    }

    public Cookie(String str, String str2) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException(b.getString("err.cookie_name_blank"));
        } else if (!a(str) || str.equalsIgnoreCase("Comment") || str.equalsIgnoreCase("Discard") || str.equalsIgnoreCase(CookieHeaderNames.DOMAIN) || str.equalsIgnoreCase("Expires") || str.equalsIgnoreCase(CookieHeaderNames.MAX_AGE) || str.equalsIgnoreCase(CookieHeaderNames.PATH) || str.equalsIgnoreCase(CookieHeaderNames.SECURE) || str.equalsIgnoreCase("Version") || str.startsWith("$")) {
            throw new IllegalArgumentException(MessageFormat.format(b.getString("err.cookie_name_is_token"), str));
        } else {
            this.name = str;
            this.value = str2;
        }
    }

    public void setComment(String str) {
        this.comment = str;
    }

    public String getComment() {
        return this.comment;
    }

    public void setDomain(String str) {
        this.domain = str.toLowerCase(Locale.ENGLISH);
    }

    public String getDomain() {
        return this.domain;
    }

    public void setMaxAge(int i) {
        this.maxAge = i;
    }

    public int getMaxAge() {
        return this.maxAge;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setSecure(boolean z) {
        this.secure = z;
    }

    public boolean getSecure() {
        return this.secure;
    }

    public String getName() {
        return this.name;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int i) {
        this.version = i;
    }

    private boolean a(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt < ' ' || charAt >= 127 || a.indexOf(charAt) != -1) {
                return false;
            }
        }
        return true;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void setHttpOnly(boolean z) {
        this.isHttpOnly = z;
    }

    public boolean isHttpOnly() {
        return this.isHttpOnly;
    }
}
