package com.xiaomi.passport.snscorelib.internal.utils;

import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil;
import java.util.Map;

/* loaded from: classes4.dex */
public class SNSCookieManager {
    public static void setupCookiesForAccountWeb(WebView webView, Map<String, String> map) {
        if (!(map == null || map.isEmpty())) {
            CookieSyncManager.createInstance(webView.getContext());
            CookieManager instance = CookieManager.getInstance();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                setAccountCookie(instance, entry.getKey(), entry.getValue());
            }
            CookieSyncManager.getInstance().sync();
        }
    }

    private static void setAccountCookie(CookieManager cookieManager, String str, String str2) {
        if (cookieManager != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            cookieManager.setCookie(AbstractAccountWebViewSingleCookieUtil.DOMAIN_URL_SET_COOKIE, str.equals(BaseConstants.EXTRA_PASSTOKEN) ? String.format("%s=%s;HttpOnly;", str, str2) : String.format("%s=%s;Secure;", str, str2));
        }
    }

    public static String getSnsBindCookie(String str) {
        return extractFromCookieString(str, "sns-bind-step");
    }

    public static String extractFromCookieString(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        String[] split = str.split(";");
        for (String str3 : split) {
            if (str3.contains(str2) && str3.split("=")[0].trim().equals(str2)) {
                return str3.substring(str3.indexOf("=") + 1);
            }
        }
        return null;
    }
}
