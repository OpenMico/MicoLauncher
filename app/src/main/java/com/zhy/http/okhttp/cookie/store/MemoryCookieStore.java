package com.zhy.http.okhttp.cookie.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/* loaded from: classes4.dex */
public class MemoryCookieStore implements CookieStore {
    private final HashMap<String, List<Cookie>> a = new HashMap<>();

    @Override // com.zhy.http.okhttp.cookie.store.CookieStore
    public void add(HttpUrl httpUrl, List<Cookie> list) {
        List<Cookie> list2 = this.a.get(httpUrl.host());
        if (list2 != null) {
            Iterator<Cookie> it = list2.iterator();
            for (Cookie cookie : list) {
                String name = cookie.name();
                while (name != null && it.hasNext()) {
                    String name2 = it.next().name();
                    if (name2 != null && name.equals(name2)) {
                        it.remove();
                    }
                }
            }
            list2.addAll(list);
            return;
        }
        this.a.put(httpUrl.host(), list);
    }

    @Override // com.zhy.http.okhttp.cookie.store.CookieStore
    public List<Cookie> get(HttpUrl httpUrl) {
        List<Cookie> list = this.a.get(httpUrl.host());
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        this.a.put(httpUrl.host(), arrayList);
        return arrayList;
    }

    @Override // com.zhy.http.okhttp.cookie.store.CookieStore
    public boolean removeAll() {
        this.a.clear();
        return true;
    }

    @Override // com.zhy.http.okhttp.cookie.store.CookieStore
    public List<Cookie> getCookies() {
        ArrayList arrayList = new ArrayList();
        for (String str : this.a.keySet()) {
            arrayList.addAll(this.a.get(str));
        }
        return arrayList;
    }

    @Override // com.zhy.http.okhttp.cookie.store.CookieStore
    public boolean remove(HttpUrl httpUrl, Cookie cookie) {
        List<Cookie> list = this.a.get(httpUrl.host());
        if (cookie != null) {
            return list.remove(cookie);
        }
        return false;
    }
}
