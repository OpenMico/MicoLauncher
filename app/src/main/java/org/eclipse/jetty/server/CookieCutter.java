package org.eclipse.jetty.server;

import javax.servlet.http.Cookie;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class CookieCutter {
    private static final Logger LOG = Log.getLogger(CookieCutter.class);
    private Cookie[] _cookies;
    int _fields;
    private Cookie[] _lastCookies;
    Object _lazyFields;

    public Cookie[] getCookies() {
        Object obj;
        Cookie[] cookieArr = this._cookies;
        if (cookieArr != null) {
            return cookieArr;
        }
        if (this._lastCookies == null || (obj = this._lazyFields) == null || this._fields != LazyList.size(obj)) {
            parseFields();
        } else {
            this._cookies = this._lastCookies;
        }
        Cookie[] cookieArr2 = this._cookies;
        this._lastCookies = cookieArr2;
        return cookieArr2;
    }

    public void setCookies(Cookie[] cookieArr) {
        this._cookies = cookieArr;
        this._lastCookies = null;
        this._lazyFields = null;
        this._fields = 0;
    }

    public void reset() {
        this._cookies = null;
        this._fields = 0;
    }

    public void addCookieField(String str) {
        if (str != null) {
            String trim = str.trim();
            if (trim.length() != 0) {
                int size = LazyList.size(this._lazyFields);
                int i = this._fields;
                if (size > i) {
                    if (!trim.equals(LazyList.get(this._lazyFields, i))) {
                        while (true) {
                            int size2 = LazyList.size(this._lazyFields);
                            int i2 = this._fields;
                            if (size2 <= i2) {
                                break;
                            }
                            this._lazyFields = LazyList.remove(this._lazyFields, i2);
                        }
                    } else {
                        this._fields++;
                        return;
                    }
                }
                this._cookies = null;
                this._lastCookies = null;
                Object obj = this._lazyFields;
                int i3 = this._fields;
                this._fields = i3 + 1;
                this._lazyFields = LazyList.add(obj, i3, trim);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0124 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void parseFields() {
        /*
            Method dump skipped, instructions count: 454
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.CookieCutter.parseFields():void");
    }
}
