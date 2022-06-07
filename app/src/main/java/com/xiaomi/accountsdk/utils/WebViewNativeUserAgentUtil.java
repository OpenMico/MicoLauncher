package com.xiaomi.accountsdk.utils;

import android.text.TextUtils;
import android.util.Base64;
import android.webkit.CookieManager;
import android.webkit.WebView;
import com.xiaomi.accountsdk.account.XMPassportSettings;

/* loaded from: classes2.dex */
public class WebViewNativeUserAgentUtil extends AbstractAccountWebViewSingleCookieUtil {
    @Override // com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil
    protected String getCookieName() {
        return "NativeUserAgent";
    }

    public void setupUserAgentForAccountWeb(WebView webView) {
        super.setupNonNullCookieForAccountWeb(webView);
    }

    public void setupUserAgentCookie(String str, CookieManager cookieManager) {
        if (!TextUtils.isEmpty(str)) {
            super.setAccountCookie(cookieManager, Base64.encodeToString(str.getBytes(), 2));
        }
    }

    @Override // com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil
    protected String getCookieValue() {
        String userAgent = XMPassportSettings.getUserAgent();
        if (TextUtils.isEmpty(userAgent)) {
            return null;
        }
        return Base64.encodeToString(userAgent.getBytes(), 2);
    }
}
