package com.franmontiel.persistentcookiejar;

import com.franmontiel.persistentcookiejar.cache.CookieCache;
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public class PersistentCookieJar implements ClearableCookieJar {
    private CookieCache a;
    private CookiePersistor b;

    public PersistentCookieJar(CookieCache cookieCache, CookiePersistor cookiePersistor) {
        this.a = cookieCache;
        this.b = cookiePersistor;
        this.a.addAll(cookiePersistor.loadAll());
    }

    @Override // okhttp3.CookieJar
    public synchronized void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        this.a.addAll(list);
        this.b.saveAll(a(list));
    }

    private static List<Cookie> a(List<Cookie> list) {
        ArrayList arrayList = new ArrayList();
        for (Cookie cookie : list) {
            if (cookie.persistent()) {
                arrayList.add(cookie);
            }
        }
        return arrayList;
    }

    @Override // okhttp3.CookieJar
    public synchronized List<Cookie> loadForRequest(HttpUrl httpUrl) {
        ArrayList arrayList;
        ArrayList arrayList2 = new ArrayList();
        arrayList = new ArrayList();
        Iterator<Cookie> it = this.a.iterator();
        while (it.hasNext()) {
            Cookie next = it.next();
            if (a(next)) {
                arrayList2.add(next);
                it.remove();
            } else if (next.matches(httpUrl)) {
                arrayList.add(next);
            }
        }
        this.b.removeAll(arrayList2);
        return arrayList;
    }

    private static boolean a(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }

    @Override // com.franmontiel.persistentcookiejar.ClearableCookieJar
    public synchronized void clearSession() {
        this.a.clear();
        this.a.addAll(this.b.loadAll());
    }

    @Override // com.franmontiel.persistentcookiejar.ClearableCookieJar
    public synchronized void clear() {
        this.a.clear();
        this.b.clear();
    }
}
