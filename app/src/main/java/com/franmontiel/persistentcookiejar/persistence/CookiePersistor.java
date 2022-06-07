package com.franmontiel.persistentcookiejar.persistence;

import java.util.Collection;
import java.util.List;
import okhttp3.Cookie;

/* loaded from: classes.dex */
public interface CookiePersistor {
    void clear();

    List<Cookie> loadAll();

    void removeAll(Collection<Cookie> collection);

    void saveAll(Collection<Cookie> collection);
}
