package javax.servlet;

/* loaded from: classes5.dex */
public interface SessionCookieConfig {
    String getComment();

    String getDomain();

    int getMaxAge();

    String getName();

    String getPath();

    boolean isHttpOnly();

    boolean isSecure();

    void setComment(String str);

    void setDomain(String str);

    void setHttpOnly(boolean z);

    void setMaxAge(int i);

    void setName(String str);

    void setPath(String str);

    void setSecure(boolean z);
}
