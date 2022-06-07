package com.xiaomi.accountsdk.utils;

import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import java.util.Map;

/* loaded from: classes2.dex */
public class WebViewCookieUtil extends AbstractAccountWebViewSingleCookieUtil {
    @Override // com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil
    protected String getCookieName() {
        return "";
    }

    @Override // com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil
    protected String getCookieValue() {
        return "";
    }

    public void setupCookiesForAccountWeb(WebView webView, Map<String, String> map) {
        if (!(map == null || map.isEmpty())) {
            CookieSyncManager.createInstance(webView.getContext());
            CookieManager instance = CookieManager.getInstance();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                setAccountCookie(instance, entry.getKey(), entry.getValue());
            }
            CookieSyncManager.getInstance().sync();
        }
    }
}
