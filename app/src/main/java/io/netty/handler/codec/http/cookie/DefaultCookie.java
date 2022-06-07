package io.netty.handler.codec.http.cookie;

import com.xiaomi.onetrack.api.b;
import io.netty.util.internal.ObjectUtil;

/* loaded from: classes4.dex */
public class DefaultCookie implements Cookie {
    private final String a;
    private String b;
    private boolean c;
    private String d;
    private String e;
    private long f = Long.MIN_VALUE;
    private boolean g;
    private boolean h;

    public DefaultCookie(String str, String str2) {
        String trim = ((String) ObjectUtil.checkNotNull(str, "name")).trim();
        if (!trim.isEmpty()) {
            this.a = trim;
            setValue(str2);
            return;
        }
        throw new IllegalArgumentException("empty name");
    }

    @Override // io.netty.handler.codec.http.cookie.Cookie
    public String name() {
        return this.a;
    }

    @Override // io.netty.handler.codec.http.cookie.Cookie
    public String value() {
        return this.b;
    }

    @Override // io.netty.handler.codec.http.cookie.Cookie
    public void setValue(String str) {
        this.b = (String) ObjectUtil.checkNotNull(str, b.p);
    }

    @Override // io.netty.handler.codec.http.cookie.Cookie
    public boolean wrap() {
        return this.c;
    }

    @Override // io.netty.handler.codec.http.cookie.Cookie
    public void setWrap(boolean z) {
        this.c = z;
    }

    @Override // io.netty.handler.codec.http.cookie.Cookie
    public String domain() {
        return this.d;
    }

    @Override // io.netty.handler.codec.http.cookie.Cookie
    public void setDomain(String str) {
        this.d = a.a("domain", str);
    }

    @Override // io.netty.handler.codec.http.cookie.Cookie
    public String path() {
        return this.e;
    }

    @Override // io.netty.handler.codec.http.cookie.Cookie
    public void setPath(String str) {
        this.e = a.a("path", str);
    }

    @Override // io.netty.handler.codec.http.cookie.Cookie
    public long maxAge() {
        return this.f;
    }

    @Override // io.netty.handler.codec.http.cookie.Cookie
    public void setMaxAge(long j) {
        this.f = j;
    }

    @Override // io.netty.handler.codec.http.cookie.Cookie
    public boolean isSecure() {
        return this.g;
    }

    @Override // io.netty.handler.codec.http.cookie.Cookie
    public void setSecure(boolean z) {
        this.g = z;
    }

    @Override // io.netty.handler.codec.http.cookie.Cookie
    public boolean isHttpOnly() {
        return this.h;
    }

    @Override // io.netty.handler.codec.http.cookie.Cookie
    public void setHttpOnly(boolean z) {
        this.h = z;
    }

    public int hashCode() {
        return name().hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Cookie)) {
            return false;
        }
        Cookie cookie = (Cookie) obj;
        if (!name().equalsIgnoreCase(cookie.name())) {
            return false;
        }
        if (path() == null) {
            if (cookie.path() != null) {
                return false;
            }
        } else if (cookie.path() == null || !path().equals(cookie.path())) {
            return false;
        }
        if (domain() == null) {
            return cookie.domain() == null;
        }
        if (cookie.domain() == null) {
            return false;
        }
        return domain().equalsIgnoreCase(cookie.domain());
    }

    public int compareTo(Cookie cookie) {
        int compareToIgnoreCase = name().compareToIgnoreCase(cookie.name());
        if (compareToIgnoreCase != 0) {
            return compareToIgnoreCase;
        }
        if (path() == null) {
            if (cookie.path() != null) {
                return -1;
            }
        } else if (cookie.path() == null) {
            return 1;
        } else {
            int compareTo = path().compareTo(cookie.path());
            if (compareTo != 0) {
                return compareTo;
            }
        }
        if (domain() == null) {
            return cookie.domain() != null ? -1 : 0;
        }
        if (cookie.domain() == null) {
            return 1;
        }
        return domain().compareToIgnoreCase(cookie.domain());
    }

    @Deprecated
    protected String validateValue(String str, String str2) {
        return a.a(str, str2);
    }

    public String toString() {
        StringBuilder a = a.a();
        a.append(name());
        a.append('=');
        a.append(value());
        if (domain() != null) {
            a.append(", domain=");
            a.append(domain());
        }
        if (path() != null) {
            a.append(", path=");
            a.append(path());
        }
        if (maxAge() >= 0) {
            a.append(", maxAge=");
            a.append(maxAge());
            a.append('s');
        }
        if (isSecure()) {
            a.append(", secure");
        }
        if (isHttpOnly()) {
            a.append(", HTTPOnly");
        }
        return a.toString();
    }
}
