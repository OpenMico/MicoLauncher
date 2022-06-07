package com.xiaomi.mico.account.sdk;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import androidx.annotation.NonNull;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.micolauncher.common.L;
import java.net.MalformedURLException;
import java.net.URL;

/* loaded from: classes3.dex */
public class WebSsoCookieUtils {
    public static boolean setUpCookieAccount(@NonNull Context context, @NonNull String str, @NonNull ServiceToken serviceToken) {
        try {
            URL url = new URL(str);
            return setUpCookieAccount(context, str, url.getHost(), url.getPath(), serviceToken);
        } catch (MalformedURLException e) {
            L.login.e("bad url", e);
            return false;
        }
    }

    public static boolean setUpCookieAccount(@NonNull Context context, @NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull ServiceToken serviceToken) {
        if (TextUtils.isEmpty(serviceToken.getSid())) {
            L.login.w("setCookie error: no sid");
            return false;
        } else if (TextUtils.isEmpty(serviceToken.getServiceToken())) {
            L.login.w(String.format("setCookie error: no serviceToken for sid %s", serviceToken.getSid()));
            return false;
        } else if (TextUtils.isEmpty(serviceToken.getCUserId())) {
            L.login.w("setCookie error: no cUserId");
            return false;
        } else if (TextUtils.isEmpty(serviceToken.getSlh())) {
            L.login.w(String.format("setCookie error: no %s_slh", serviceToken.getSid()));
            return false;
        } else if (TextUtils.isEmpty(serviceToken.getPh())) {
            L.login.w(String.format("setCookie error: no %s_ph", serviceToken.getSid()));
            return false;
        } else {
            CookieSyncManager.createInstance(context);
            CookieManager instance = CookieManager.getInstance();
            instance.setCookie(str, a(str2, "cUserId", serviceToken.getCUserId(), str3));
            instance.setCookie(str, a(str2, AuthorizeActivityBase.KEY_SERVICETOKEN, serviceToken.getServiceToken(), str3));
            String a = a(str2);
            instance.setCookie(str, a(a, serviceToken.getSid() + LoginManager.KEY_SLH_SUFFIX, serviceToken.getSlh(), str3));
            instance.setCookie(str, a(str2, serviceToken.getSid() + LoginManager.KEY_PH_SUFFIX, serviceToken.getPh(), str3));
            CookieSyncManager.getInstance().sync();
            return true;
        }
    }

    private static String a(String str, String str2, String str3, String str4) {
        return String.format("%s=%s; domain=%s; path=%s", str2, str3, str, str4);
    }

    private static String a(String str) {
        String[] split = str.split("\\.");
        int length = split.length;
        return length <= 2 ? str : String.format(".%s.%s", split[length - 2], split[length - 1]);
    }
}
