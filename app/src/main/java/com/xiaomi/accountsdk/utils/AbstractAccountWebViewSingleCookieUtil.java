package com.xiaomi.accountsdk.utils;

import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import com.xiaomi.accountsdk.account.XMPassport;

/* loaded from: classes2.dex */
public abstract class AbstractAccountWebViewSingleCookieUtil {
    public static final String DOMAIN_URL_SET_COOKIE;

    protected abstract String getCookieName();

    protected abstract String getCookieValue();

    static {
        DOMAIN_URL_SET_COOKIE = XMPassport.USE_PREVIEW ? ".account.preview.n.xiaomi.net" : ".account.xiaomi.com";
    }

    public final void setupNonNullCookieForAccountWeb(WebView webView) {
        if (webView != null && !TextUtils.isEmpty(getCookieValue())) {
            CookieSyncManager.createInstance(webView.getContext());
            setAccountCookie(CookieManager.getInstance(), getCookieName(), getCookieValue());
            CookieSyncManager.getInstance().sync();
        }
    }

    public final void setAccountCookie(CookieManager cookieManager, String str) {
        setAccountCookie(cookieManager, getCookieName(), str);
    }

    public static void setAccountCookie(CookieManager cookieManager, String str, String str2) {
        if (cookieManager != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            cookieManager.setCookie(DOMAIN_URL_SET_COOKIE, String.format("%s=%s;", str, str2));
        }
    }
}
