package com.franmontiel.persistentcookiejar;

import okhttp3.CookieJar;

/* loaded from: classes.dex */
public interface ClearableCookieJar extends CookieJar {
    void clear();

    void clearSession();
}
