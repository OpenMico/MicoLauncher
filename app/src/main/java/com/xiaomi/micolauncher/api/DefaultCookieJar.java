package com.xiaomi.micolauncher.api;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/* loaded from: classes3.dex */
public class DefaultCookieJar implements CookieJar {
    private final Object a = new Object();
    private Set<Cookie> b = new HashSet();

    @Override // okhttp3.CookieJar
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        synchronized (this.a) {
            for (final Cookie cookie : list) {
                this.b.removeIf(new Predicate() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$DefaultCookieJar$bRQ8UQ8RYwW7QTf6w7SpAgZ6eKk
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean a;
                        a = DefaultCookieJar.this.a(cookie, (Cookie) obj);
                        return a;
                    }
                });
                this.b.add(cookie);
            }
        }
    }

    @Override // okhttp3.CookieJar
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
        synchronized (this.a) {
            if (this.b != null && !this.b.isEmpty()) {
                ArrayList arrayList = new ArrayList();
                Iterator<Cookie> it = this.b.iterator();
                while (it.hasNext()) {
                    Cookie next = it.next();
                    if (next.expiresAt() < System.currentTimeMillis()) {
                        it.remove();
                    } else if (TextUtils.isEmpty(next.value())) {
                        it.remove();
                    } else if (next.matches(httpUrl)) {
                        arrayList.add(next);
                    }
                }
                return arrayList;
            }
            return Collections.emptyList();
        }
    }

    /* renamed from: cookieEquals */
    public boolean a(Cookie cookie, Cookie cookie2) {
        return cookie.name().equals(cookie2.name()) && cookie.domain().equals(cookie2.domain()) && cookie.path().equals(cookie2.path()) && cookie.expiresAt() == cookie2.expiresAt() && cookie.secure() == cookie2.secure() && cookie.httpOnly() == cookie2.httpOnly() && cookie.persistent() == cookie2.persistent() && cookie.hostOnly() == cookie2.hostOnly();
    }
}
