package com.xiaomi.accountsdk.utils;

import android.webkit.CookieManager;
import android.webkit.WebView;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;

/* loaded from: classes2.dex */
public final class WebViewUserSpaceIdUtil extends AbstractAccountWebViewSingleCookieUtil {
    @Override // com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil
    protected String getCookieName() {
        return SimpleRequestForAccount.COOKIE_NAME_USER_SPACE_ID;
    }

    public void setupUserSpaceIdForAccountWeb(WebView webView) {
        super.setupNonNullCookieForAccountWeb(webView);
    }

    public void setupUserSpaceIdCookie(String str, CookieManager cookieManager) {
        super.setAccountCookie(cookieManager, str);
    }

    @Override // com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil
    protected String getCookieValue() {
        return UserSpaceIdUtil.getNullableUserSpaceIdCookie();
    }
}
