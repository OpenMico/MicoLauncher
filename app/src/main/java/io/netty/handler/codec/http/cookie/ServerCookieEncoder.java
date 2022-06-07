package io.netty.handler.codec.http.cookie;

import io.netty.handler.codec.http.HttpHeaderDateFormat;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class ServerCookieEncoder extends CookieEncoder {
    public static final ServerCookieEncoder STRICT = new ServerCookieEncoder(true);
    public static final ServerCookieEncoder LAX = new ServerCookieEncoder(false);

    private ServerCookieEncoder(boolean z) {
        super(z);
    }

    public String encode(String str, String str2) {
        return encode(new DefaultCookie(str, str2));
    }

    public String encode(Cookie cookie) {
        String name = ((Cookie) ObjectUtil.checkNotNull(cookie, "cookie")).name();
        String value = cookie.value() != null ? cookie.value() : "";
        validateCookie(name, value);
        StringBuilder a = a.a();
        if (cookie.wrap()) {
            a.b(a, name, value);
        } else {
            a.a(a, name, value);
        }
        if (cookie.maxAge() != Long.MIN_VALUE) {
            a.a(a, CookieHeaderNames.MAX_AGE, cookie.maxAge());
            a.a(a, "Expires", HttpHeaderDateFormat.get().format(new Date((cookie.maxAge() * 1000) + System.currentTimeMillis())));
        }
        if (cookie.path() != null) {
            a.a(a, CookieHeaderNames.PATH, cookie.path());
        }
        if (cookie.domain() != null) {
            a.a(a, CookieHeaderNames.DOMAIN, cookie.domain());
        }
        if (cookie.isSecure()) {
            a.a(a, CookieHeaderNames.SECURE);
        }
        if (cookie.isHttpOnly()) {
            a.a(a, CookieHeaderNames.HTTPONLY);
        }
        return a.b(a);
    }

    private List<String> a(List<String> list, Map<String, Integer> map) {
        boolean[] zArr = new boolean[list.size()];
        for (Integer num : map.values()) {
            zArr[num.intValue()] = true;
        }
        ArrayList arrayList = new ArrayList(map.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (zArr[i]) {
                arrayList.add(list.get(i));
            }
        }
        return arrayList;
    }

    public List<String> encode(Cookie... cookieArr) {
        if (((Cookie[]) ObjectUtil.checkNotNull(cookieArr, "cookies")).length == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(cookieArr.length);
        HashMap hashMap = (!this.strict || cookieArr.length <= 1) ? null : new HashMap();
        boolean z = false;
        for (int i = 0; i < cookieArr.length; i++) {
            Cookie cookie = cookieArr[i];
            arrayList.add(encode(cookie));
            if (hashMap != null) {
                z |= hashMap.put(cookie.name(), Integer.valueOf(i)) != null;
            }
        }
        return z ? a(arrayList, hashMap) : arrayList;
    }

    public List<String> encode(Collection<? extends Cookie> collection) {
        if (((Collection) ObjectUtil.checkNotNull(collection, "cookies")).isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(collection.size());
        HashMap hashMap = (!this.strict || collection.size() <= 1) ? null : new HashMap();
        boolean z = false;
        int i = 0;
        for (Cookie cookie : collection) {
            arrayList.add(encode(cookie));
            if (hashMap != null) {
                i++;
                z |= hashMap.put(cookie.name(), Integer.valueOf(i)) != null;
            }
        }
        return z ? a(arrayList, hashMap) : arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<java.lang.String> encode(java.lang.Iterable<? extends io.netty.handler.codec.http.cookie.Cookie> r9) {
        /*
            r8 = this;
            java.lang.String r0 = "cookies"
            java.lang.Object r9 = io.netty.util.internal.ObjectUtil.checkNotNull(r9, r0)
            java.lang.Iterable r9 = (java.lang.Iterable) r9
            java.util.Iterator r9 = r9.iterator()
            boolean r0 = r9.hasNext()
            if (r0 != 0) goto L_0x0017
            java.util.List r9 = java.util.Collections.emptyList()
            return r9
        L_0x0017:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.Object r1 = r9.next()
            io.netty.handler.codec.http.cookie.Cookie r1 = (io.netty.handler.codec.http.cookie.Cookie) r1
            boolean r2 = r8.strict
            if (r2 == 0) goto L_0x0032
            boolean r2 = r9.hasNext()
            if (r2 == 0) goto L_0x0032
            java.util.HashMap r2 = new java.util.HashMap
            r2.<init>()
            goto L_0x0033
        L_0x0032:
            r2 = 0
        L_0x0033:
            java.lang.String r3 = r8.encode(r1)
            r0.add(r3)
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x0051
            java.lang.String r1 = r1.name()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)
            java.lang.Object r1 = r2.put(r1, r5)
            if (r1 == 0) goto L_0x004e
            r1 = r3
            goto L_0x0052
        L_0x004e:
            r5 = r3
            r1 = r4
            goto L_0x0053
        L_0x0051:
            r1 = r4
        L_0x0052:
            r5 = r1
        L_0x0053:
            boolean r6 = r9.hasNext()
            if (r6 == 0) goto L_0x007e
            java.lang.Object r6 = r9.next()
            io.netty.handler.codec.http.cookie.Cookie r6 = (io.netty.handler.codec.http.cookie.Cookie) r6
            java.lang.String r7 = r8.encode(r6)
            r0.add(r7)
            if (r2 == 0) goto L_0x0053
            java.lang.String r6 = r6.name()
            int r7 = r5 + 1
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.Object r5 = r2.put(r6, r5)
            if (r5 == 0) goto L_0x007a
            r5 = r3
            goto L_0x007b
        L_0x007a:
            r5 = r4
        L_0x007b:
            r1 = r1 | r5
            r5 = r7
            goto L_0x0053
        L_0x007e:
            if (r1 == 0) goto L_0x0084
            java.util.List r0 = r8.a(r0, r2)
        L_0x0084:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.handler.codec.http.cookie.ServerCookieEncoder.encode(java.lang.Iterable):java.util.List");
    }
}
