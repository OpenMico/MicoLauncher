package com.zhy.http.okhttp.cookie;

import com.zhy.http.okhttp.cookie.store.CookieStore;
import com.zhy.http.okhttp.utils.Exceptions;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/* loaded from: classes4.dex */
public class CookieJarImpl implements CookieJar {
    private CookieStore a;

    public CookieJarImpl(CookieStore cookieStore) {
        if (cookieStore == null) {
            Exceptions.illegalArgument("cookieStore can not be null.", new Object[0]);
        }
        this.a = cookieStore;
    }

    @Override // okhttp3.CookieJar
    public synchronized void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        this.a.add(httpUrl, list);
    }

    @Override // okhttp3.CookieJar
    public synchronized List<Cookie> loadForRequest(HttpUrl httpUrl) {
        return this.a.get(httpUrl);
    }

    public CookieStore getCookieStore() {
        return this.a;
    }
}
