package io.netty.handler.codec.http.cookie;

/* loaded from: classes4.dex */
public abstract class CookieEncoder {
    protected final boolean strict;

    /* JADX INFO: Access modifiers changed from: protected */
    public CookieEncoder(boolean z) {
        this.strict = z;
    }

    protected void validateCookie(String str, String str2) {
        if (this.strict) {
            int a = a.a(str);
            if (a < 0) {
                CharSequence c = a.c(str2);
                if (c != null) {
                    int b = a.b(c);
                    if (b >= 0) {
                        throw new IllegalArgumentException("Cookie value contains an invalid char: " + str2.charAt(b));
                    }
                    return;
                }
                throw new IllegalArgumentException("Cookie value wrapping quotes are not balanced: " + str2);
            }
            throw new IllegalArgumentException("Cookie name contains an invalid char: " + str.charAt(a));
        }
    }
}
