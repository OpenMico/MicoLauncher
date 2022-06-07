package com.xiaomi.smarthome.library.http.util;

import android.text.TextUtils;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import okhttp3.Response;

/* loaded from: classes4.dex */
public class CookieUtil {
    public static HttpCookie getCookie(CookieManager cookieManager, String str) {
        if (cookieManager == null || TextUtils.isEmpty(str)) {
            return null;
        }
        for (HttpCookie httpCookie : cookieManager.getCookieStore().getCookies()) {
            if (str.equals(httpCookie.getName())) {
                return httpCookie;
            }
        }
        return null;
    }

    public static HttpCookie getCookie(CookieManager cookieManager, String str, String str2) {
        if (cookieManager == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        for (HttpCookie httpCookie : cookieManager.getCookieStore().getCookies()) {
            if (str.equals(httpCookie.getName()) && str2.equals(httpCookie.getDomain())) {
                return httpCookie;
            }
        }
        return null;
    }

    public static String parseServiceTokenFromResponse(Response response) {
        for (String str : response.headers("Set-Cookie")) {
            if (!TextUtils.isEmpty(str)) {
                String[] split = str.split(";");
                for (String str2 : split) {
                    if (str2.contains(AuthorizeActivityBase.KEY_SERVICETOKEN)) {
                        int indexOf = str2.indexOf("=");
                        return indexOf == -1 ? "" : str2.substring(indexOf + 1, str2.length());
                    }
                }
                continue;
            }
        }
        return "";
    }

    public static HttpCookie getCookie(CookieManager cookieManager, String str, String str2, String str3) {
        if (cookieManager == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return null;
        }
        for (HttpCookie httpCookie : cookieManager.getCookieStore().getCookies()) {
            if (str.equals(httpCookie.getName()) && str2.equals(httpCookie.getDomain()) && str3.equals(httpCookie.getPath())) {
                return httpCookie;
            }
        }
        return null;
    }

    public static void addCookie(CookieManager cookieManager, String str, String str2, String str3, String str4, String str5) {
        if (cookieManager != null) {
            HttpCookie httpCookie = new HttpCookie(str2, str3);
            httpCookie.setDomain(str4);
            httpCookie.setPath(str5);
            try {
                cookieManager.getCookieStore().add(new URI(str), httpCookie);
            } catch (URISyntaxException unused) {
            }
        }
    }

    public static void clearAllCookie(CookieManager cookieManager) {
        if (cookieManager != null) {
            cookieManager.getCookieStore().removeAll();
        }
    }

    public static void saveCookie(CookieManager cookieManager, WebViewCookieManager webViewCookieManager) {
        if (webViewCookieManager != null) {
            for (HttpCookie httpCookie : cookieManager.getCookieStore().getCookies()) {
                webViewCookieManager.setCookie(httpCookie.getName(), httpCookie.getValue(), httpCookie.getDomain());
            }
        }
    }
}
