package com.xiaomi.miot.host.lan.impl;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes2.dex */
public class MiotStore {
    public static final String COUNTRYDOMAIN = "country_domain";
    public static final String PREFS_MIOT = "miot";
    private static final String TAG = MiotHost.class.getSimpleName();
    public static final String TOKEN = "token";
    private SharedPreferences mPreferences;

    public MiotStore(Context context) {
        this.mPreferences = context.getSharedPreferences(PREFS_MIOT, 0);
    }

    public synchronized void saveCountryDomain(String str) {
        String str2 = TAG;
        MiotLogUtil.d(str2, "save countryDomain: " + str);
        SharedPreferences.Editor edit = this.mPreferences.edit();
        edit.putString(COUNTRYDOMAIN, str);
        edit.apply();
    }

    public synchronized void saveToken(String str) {
        String str2 = TAG;
        MiotLogUtil.d(str2, "save token: " + str);
        SharedPreferences.Editor edit = this.mPreferences.edit();
        edit.putString("token", str);
        edit.apply();
    }

    public synchronized String loadcountryDomain() {
        String string;
        string = this.mPreferences.getString(COUNTRYDOMAIN, null);
        String str = TAG;
        MiotLogUtil.d(str, "loadCountryDomain: " + string);
        return string;
    }

    public synchronized String loadToken() {
        String string;
        string = this.mPreferences.getString("token", null);
        String str = TAG;
        MiotLogUtil.d(str, "loadToken: " + string);
        return string;
    }

    public synchronized void deleteLocalData() {
        MiotLogUtil.d(TAG, "delete token");
        SharedPreferences.Editor edit = this.mPreferences.edit();
        edit.clear();
        edit.apply();
    }
}
