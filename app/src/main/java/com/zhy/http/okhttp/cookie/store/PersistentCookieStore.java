package com.zhy.http.okhttp.cookie.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.mipush.sdk.Constants;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/* loaded from: classes4.dex */
public class PersistentCookieStore implements CookieStore {
    private final HashMap<String, ConcurrentHashMap<String, Cookie>> a = new HashMap<>();
    private final SharedPreferences b;

    public PersistentCookieStore(Context context) {
        Cookie decodeCookie;
        this.b = context.getSharedPreferences("CookiePrefsFile", 0);
        for (Map.Entry<String, ?> entry : this.b.getAll().entrySet()) {
            if (((String) entry.getValue()) != null && !((String) entry.getValue()).startsWith("cookie_")) {
                String[] split = TextUtils.split((String) entry.getValue(), Constants.ACCEPT_TIME_SEPARATOR_SP);
                for (String str : split) {
                    String string = this.b.getString("cookie_" + str, null);
                    if (!(string == null || (decodeCookie = decodeCookie(string)) == null)) {
                        if (!this.a.containsKey(entry.getKey())) {
                            this.a.put(entry.getKey(), new ConcurrentHashMap<>());
                        }
                        this.a.get(entry.getKey()).put(str, decodeCookie);
                    }
                }
            }
        }
    }

    protected void add(HttpUrl httpUrl, Cookie cookie) {
        String cookieToken = getCookieToken(cookie);
        if (!cookie.persistent()) {
            if (!this.a.containsKey(httpUrl.host())) {
                this.a.put(httpUrl.host(), new ConcurrentHashMap<>());
            }
            this.a.get(httpUrl.host()).put(cookieToken, cookie);
        } else if (this.a.containsKey(httpUrl.host())) {
            this.a.get(httpUrl.host()).remove(cookieToken);
        } else {
            return;
        }
        SharedPreferences.Editor edit = this.b.edit();
        edit.putString(httpUrl.host(), TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, this.a.get(httpUrl.host()).keySet()));
        edit.putString("cookie_" + cookieToken, encodeCookie(new SerializableHttpCookie(cookie)));
        edit.apply();
    }

    protected String getCookieToken(Cookie cookie) {
        return cookie.name() + cookie.domain();
    }

    @Override // com.zhy.http.okhttp.cookie.store.CookieStore
    public void add(HttpUrl httpUrl, List<Cookie> list) {
        for (Cookie cookie : list) {
            add(httpUrl, cookie);
        }
    }

    @Override // com.zhy.http.okhttp.cookie.store.CookieStore
    public List<Cookie> get(HttpUrl httpUrl) {
        ArrayList arrayList = new ArrayList();
        if (this.a.containsKey(httpUrl.host())) {
            for (Cookie cookie : this.a.get(httpUrl.host()).values()) {
                if (a(cookie)) {
                    remove(httpUrl, cookie);
                } else {
                    arrayList.add(cookie);
                }
            }
        }
        return arrayList;
    }

    private static boolean a(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }

    @Override // com.zhy.http.okhttp.cookie.store.CookieStore
    public boolean removeAll() {
        SharedPreferences.Editor edit = this.b.edit();
        edit.clear();
        edit.apply();
        this.a.clear();
        return true;
    }

    @Override // com.zhy.http.okhttp.cookie.store.CookieStore
    public boolean remove(HttpUrl httpUrl, Cookie cookie) {
        String cookieToken = getCookieToken(cookie);
        if (!this.a.containsKey(httpUrl.host()) || !this.a.get(httpUrl.host()).containsKey(cookieToken)) {
            return false;
        }
        this.a.get(httpUrl.host()).remove(cookieToken);
        SharedPreferences.Editor edit = this.b.edit();
        SharedPreferences sharedPreferences = this.b;
        if (sharedPreferences.contains("cookie_" + cookieToken)) {
            edit.remove("cookie_" + cookieToken);
        }
        edit.putString(httpUrl.host(), TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, this.a.get(httpUrl.host()).keySet()));
        edit.apply();
        return true;
    }

    @Override // com.zhy.http.okhttp.cookie.store.CookieStore
    public List<Cookie> getCookies() {
        ArrayList arrayList = new ArrayList();
        for (String str : this.a.keySet()) {
            arrayList.addAll(this.a.get(str).values());
        }
        return arrayList;
    }

    protected String encodeCookie(SerializableHttpCookie serializableHttpCookie) {
        if (serializableHttpCookie == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(byteArrayOutputStream).writeObject(serializableHttpCookie);
            return byteArrayToHexString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            Log.d("PersistentCookieStore", "IOException in encodeCookie", e);
            return null;
        }
    }

    protected Cookie decodeCookie(String str) {
        try {
            return ((SerializableHttpCookie) new ObjectInputStream(new ByteArrayInputStream(hexStringToByteArray(str))).readObject()).getCookie();
        } catch (IOException e) {
            Log.d("PersistentCookieStore", "IOException in decodeCookie", e);
            return null;
        } catch (ClassNotFoundException e2) {
            Log.d("PersistentCookieStore", "ClassNotFoundException in decodeCookie", e2);
            return null;
        }
    }

    protected String byteArrayToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            int i = b & 255;
            if (i < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    protected byte[] hexStringToByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }
}
