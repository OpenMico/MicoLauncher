package io.netty.handler.codec.http.cookie;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.CharBuffer;

/* loaded from: classes4.dex */
public abstract class CookieDecoder {
    private final InternalLogger a = InternalLoggerFactory.getInstance(getClass());
    private final boolean b;

    /* JADX INFO: Access modifiers changed from: protected */
    public CookieDecoder(boolean z) {
        this.b = z;
    }

    protected DefaultCookie initCookie(String str, int i, int i2, int i3, int i4) {
        int b;
        int a;
        if (i == -1 || i == i2) {
            this.a.debug("Skipping cookie with null name");
            return null;
        } else if (i3 == -1) {
            this.a.debug("Skipping cookie with null value");
            return null;
        } else {
            CharBuffer wrap = CharBuffer.wrap(str, i3, i4);
            CharSequence c = a.c(wrap);
            if (c == null) {
                this.a.debug("Skipping cookie because starting quotes are not properly balanced in '{}'", wrap);
                return null;
            }
            String substring = str.substring(i, i2);
            if (!this.b || (a = a.a(substring)) < 0) {
                boolean z = c.length() != i4 - i3;
                if (!this.b || (b = a.b(c)) < 0) {
                    DefaultCookie defaultCookie = new DefaultCookie(substring, c.toString());
                    defaultCookie.setWrap(z);
                    return defaultCookie;
                }
                if (this.a.isDebugEnabled()) {
                    this.a.debug("Skipping cookie because value '{}' contains invalid char '{}'", c, Character.valueOf(c.charAt(b)));
                }
                return null;
            }
            if (this.a.isDebugEnabled()) {
                this.a.debug("Skipping cookie because name '{}' contains invalid char '{}'", substring, Character.valueOf(substring.charAt(a)));
            }
            return null;
        }
    }
}
