package com.xiaomi.mico.token;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/* loaded from: classes3.dex */
public class DefaultCookieJar implements CookieJar {
    private Set<Cookie> a = new CopyOnWriteArraySet();

    @Override // okhttp3.CookieJar
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        for (final Cookie cookie : list) {
            this.a.removeIf(new Predicate() { // from class: com.xiaomi.mico.token.-$$Lambda$DefaultCookieJar$XbdLTV2kRfAhROqI_InYrUHVIkM
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean a;
                    a = DefaultCookieJar.this.a(cookie, (Cookie) obj);
                    return a;
                }
            });
            this.a.add(cookie);
        }
    }

    @Override // okhttp3.CookieJar
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
        Set<Cookie> set = this.a;
        if (set == null || set.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Cookie> it = this.a.iterator();
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

    /* renamed from: cookieEquals */
    public boolean a(Cookie cookie, Cookie cookie2) {
        return cookie.name().equals(cookie2.name()) && cookie.domain().equals(cookie2.domain()) && cookie.path().equals(cookie2.path()) && cookie.expiresAt() == cookie2.expiresAt() && cookie.secure() == cookie2.secure() && cookie.httpOnly() == cookie2.httpOnly() && cookie.persistent() == cookie2.persistent() && cookie.hostOnly() == cookie2.hostOnly();
    }
}
