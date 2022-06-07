package io.netty.handler.codec.http.cookie;

/* loaded from: classes4.dex */
public interface Cookie extends Comparable<Cookie> {
    String domain();

    boolean isHttpOnly();

    boolean isSecure();

    long maxAge();

    String name();

    String path();

    void setDomain(String str);

    void setHttpOnly(boolean z);

    void setMaxAge(long j);

    void setPath(String str);

    void setSecure(boolean z);

    void setValue(String str);

    void setWrap(boolean z);

    String value();

    boolean wrap();
}
