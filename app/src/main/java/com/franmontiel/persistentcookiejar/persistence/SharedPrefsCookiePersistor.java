package com.franmontiel.persistentcookiejar.persistence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import okhttp3.Cookie;

@SuppressLint({"CommitPrefEdits"})
/* loaded from: classes.dex */
public class SharedPrefsCookiePersistor implements CookiePersistor {
    private final SharedPreferences a;

    public SharedPrefsCookiePersistor(Context context) {
        this(context.getSharedPreferences("CookiePersistence", 0));
    }

    public SharedPrefsCookiePersistor(SharedPreferences sharedPreferences) {
        this.a = sharedPreferences;
    }

    @Override // com.franmontiel.persistentcookiejar.persistence.CookiePersistor
    public List<Cookie> loadAll() {
        ArrayList arrayList = new ArrayList(this.a.getAll().size());
        for (Map.Entry<String, ?> entry : this.a.getAll().entrySet()) {
            Cookie decode = new SerializableCookie().decode((String) entry.getValue());
            if (decode != null) {
                arrayList.add(decode);
            }
        }
        return arrayList;
    }

    @Override // com.franmontiel.persistentcookiejar.persistence.CookiePersistor
    public void saveAll(Collection<Cookie> collection) {
        SharedPreferences.Editor edit = this.a.edit();
        for (Cookie cookie : collection) {
            edit.putString(a(cookie), new SerializableCookie().encode(cookie));
        }
        edit.commit();
    }

    @Override // com.franmontiel.persistentcookiejar.persistence.CookiePersistor
    public void removeAll(Collection<Cookie> collection) {
        SharedPreferences.Editor edit = this.a.edit();
        for (Cookie cookie : collection) {
            edit.remove(a(cookie));
        }
        edit.commit();
    }

    private static String a(Cookie cookie) {
        StringBuilder sb = new StringBuilder();
        sb.append(cookie.secure() ? "https" : "http");
        sb.append("://");
        sb.append(cookie.domain());
        sb.append(cookie.path());
        sb.append("|");
        sb.append(cookie.name());
        return sb.toString();
    }

    @Override // com.franmontiel.persistentcookiejar.persistence.CookiePersistor
    public void clear() {
        this.a.edit().clear().commit();
    }
}
