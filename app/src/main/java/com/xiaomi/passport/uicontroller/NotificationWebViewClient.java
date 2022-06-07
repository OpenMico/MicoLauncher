package com.xiaomi.passport.uicontroller;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.xiaomi.accountsdk.utils.XMPassportUtil;

/* loaded from: classes4.dex */
public class NotificationWebViewClient extends WebViewClient {
    private static final String AUTH_END = "auth-end";
    private static final String LOGIN_END = "login-end";
    private static final String NEED_RELOGIN = "need-relogin";
    private static final String PASS_INFO = "passInfo";
    private CookieManager mCookieManager = CookieManager.getInstance();
    private NotificationEndListener mNotificationEndListener;
    private String mNotificationUrl;

    /* loaded from: classes4.dex */
    public interface NotificationEndListener {
        void onAuthEnd(String str);

        void onLoginEnd(String str, String str2);

        void onNeedReLogin();
    }

    public NotificationWebViewClient(Context context, String str, NotificationEndListener notificationEndListener) {
        this.mNotificationUrl = str;
        CookieSyncManager.createInstance(context);
        this.mNotificationEndListener = notificationEndListener;
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (this.mNotificationEndListener == null) {
            return true;
        }
        String cookie = this.mCookieManager.getCookie(this.mNotificationUrl);
        if (!TextUtils.isEmpty(cookie) && cookie.contains(PASS_INFO)) {
            if (cookie.contains(NEED_RELOGIN)) {
                this.mNotificationEndListener.onNeedReLogin();
                return true;
            } else if (cookie.contains(LOGIN_END)) {
                String extractPasstokenFromNotificationLoginEndCookie = XMPassportUtil.extractPasstokenFromNotificationLoginEndCookie(cookie);
                this.mNotificationEndListener.onLoginEnd(XMPassportUtil.extractUserIdFromNotificationLoginEndCookie(cookie), extractPasstokenFromNotificationLoginEndCookie);
                return true;
            } else if (cookie.contains(AUTH_END)) {
                this.mNotificationEndListener.onAuthEnd(str);
                return true;
            }
        }
        return super.shouldOverrideUrlLoading(webView, str);
    }
}
