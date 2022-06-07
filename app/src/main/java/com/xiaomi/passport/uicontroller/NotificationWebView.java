package com.xiaomi.passport.uicontroller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.ServerTimeUtil;
import com.xiaomi.accountsdk.utils.VersionUtils;
import com.xiaomi.accountsdk.utils.WebViewCookieUtil;
import com.xiaomi.accountsdk.utils.WebViewDeviceIdUtil;
import com.xiaomi.accountsdk.utils.WebViewFidNonceUtil;
import com.xiaomi.accountsdk.utils.WebViewNativeUserAgentUtil;
import com.xiaomi.accountsdk.utils.WebViewUserSpaceIdUtil;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import com.xiaomi.passport.uicontroller.NotificationWebViewClient;
import java.util.Map;

/* loaded from: classes4.dex */
public class NotificationWebView extends WebView {
    private static final String EXTRA_NEED_REMOVE_ALL_COOKIES = "need_remove_all_cookies";
    private static final String TAG = "NotificationWebView";
    private final Map<String, String> cookies;
    private final boolean needRemoveAllCookie;
    private final NotificationWebViewClient.NotificationEndListener notificationEndListener;
    private final String notificationUrl;
    private final String passToken;
    private final ServerTimeUtil.ServerTimeAlignedListener serverTimeAlignedListener;
    private final String userId;

    /* loaded from: classes4.dex */
    public static class ExternalParams {
        public Map<String, String> cookies;
        public final boolean needRemoveAllCookies;
        public final String notificationUrl;
        public final String passToken;
        public final String userId;

        public ExternalParams(String str, boolean z) {
            this(str, z, null, null);
        }

        public ExternalParams(String str, boolean z, String str2, String str3) {
            this.notificationUrl = str;
            this.needRemoveAllCookies = z;
            this.userId = str2;
            this.passToken = str3;
        }

        public void setCookies(Map<String, String> map) {
            this.cookies = map;
        }
    }

    public static void putExtraForNotificationWebView(Intent intent, ExternalParams externalParams) {
        intent.putExtra(AccountIntent.EXTRA_NOTIFICATION_URL, externalParams.notificationUrl);
        intent.putExtra(EXTRA_NEED_REMOVE_ALL_COOKIES, externalParams.needRemoveAllCookies);
    }

    public static ExternalParams parseExtraFromIntent(Intent intent) {
        boolean booleanExtra = intent.getBooleanExtra(EXTRA_NEED_REMOVE_ALL_COOKIES, true);
        String stringExtra = intent.getStringExtra(AccountIntent.EXTRA_NOTIFICATION_URL);
        boolean booleanExtra2 = intent.getBooleanExtra(BaseConstants.EXTRA_SHOW_SKIP_LOGIN, false);
        return new ExternalParams(appendMIUIProvisionQueryParameter(stringExtra, booleanExtra2), booleanExtra, intent.getStringExtra(BaseConstants.EXTRA_USER_ID), intent.getStringExtra(BaseConstants.EXTRA_PASSTOKEN));
    }

    private static String appendMIUIProvisionQueryParameter(String str, boolean z) {
        Uri.Builder buildUpon = Uri.parse(str).buildUpon();
        buildUpon.appendQueryParameter("_device_name", Build.DEVICE);
        buildUpon.appendQueryParameter("_provision", String.valueOf(z));
        return buildUpon.build().toString();
    }

    /* loaded from: classes4.dex */
    public static class Builder {
        private Context context;
        private ExternalParams externalParams;
        private NotificationWebViewClient.NotificationEndListener notificationEndListener;

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setNotificationEndListener(NotificationWebViewClient.NotificationEndListener notificationEndListener) {
            this.notificationEndListener = notificationEndListener;
            return this;
        }

        public NotificationWebView build() {
            return new NotificationWebView(this.context, this.externalParams.notificationUrl, this.externalParams.needRemoveAllCookies, this.externalParams.userId, this.externalParams.passToken, this.externalParams.cookies, this.notificationEndListener);
        }

        public Builder setExternalParams(ExternalParams externalParams) {
            this.externalParams = externalParams;
            return this;
        }
    }

    private NotificationWebView(Context context, String str, boolean z, String str2, String str3, Map<String, String> map, NotificationWebViewClient.NotificationEndListener notificationEndListener) {
        super(context);
        this.serverTimeAlignedListener = new WebViewFidNonceUtil.ServerTimeAlignedListenerImpl(this);
        PreConditions.checkArgumentNotEmpty(str, "notificationUrl should not be empty");
        PreConditions.checkArgumentNotNull(notificationEndListener, "notificationEndListener should not be null");
        PreConditions.checkArgumentNotNull(context, "context should not be null");
        this.notificationUrl = str;
        this.needRemoveAllCookie = z;
        this.notificationEndListener = notificationEndListener;
        this.userId = str2;
        this.passToken = str3;
        this.cookies = map;
    }

    public boolean loadNotificationUrl() {
        String buildUrlWithLocaleQueryParam = XMPassportUtil.buildUrlWithLocaleQueryParam(this.notificationUrl);
        if (TextUtils.isEmpty(buildUrlWithLocaleQueryParam)) {
            AccountLog.e(TAG, "invalid notificationUrl");
            return false;
        }
        if (this.needRemoveAllCookie) {
            CookieSyncManager.createInstance(getContext());
            CookieManager.getInstance().removeAllCookie();
        }
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setAllowContentAccess(false);
        settings.setAllowFileAccess(false);
        appendPassportUserAgent(settings);
        setWebViewClient(new NotificationWebViewClient(getContext(), buildUrlWithLocaleQueryParam, this.notificationEndListener));
        new WebViewDeviceIdUtil().setupDeviceIdForAccountWeb(this);
        new WebViewFidNonceUtil().setupFidNonceForAccountWeb(this);
        new WebViewUserSpaceIdUtil().setupUserSpaceIdForAccountWeb(this);
        new WebViewNativeUserAgentUtil().setupUserAgentForAccountWeb(this);
        new WebViewCookieUtil().setupCookiesForAccountWeb(this, this.cookies);
        setPassTokenCookie(this.userId, this.passToken);
        loadUrl(buildUrlWithLocaleQueryParam);
        return true;
    }

    @Override // android.webkit.WebView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ServerTimeUtil.addServerTimeChangedListener(this.serverTimeAlignedListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        ServerTimeUtil.removeServerTimeChangedListener(this.serverTimeAlignedListener);
        if (this.needRemoveAllCookie) {
            CookieSyncManager.createInstance(getContext());
            CookieManager.getInstance().removeAllCookie();
        }
        super.onDetachedFromWindow();
    }

    static void appendPassportUserAgent(WebSettings webSettings) {
        String userAgentString = webSettings.getUserAgentString();
        if (!TextUtils.isEmpty(userAgentString)) {
            webSettings.setUserAgentString(String.format("%s PassportSDK/NotificationWebView/%s", userAgentString, VersionUtils.getVersion()));
        }
    }

    private void setPassTokenCookie(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            CookieSyncManager.createInstance(getContext());
            CookieManager instance = CookieManager.getInstance();
            setCookie(instance, BaseConstants.EXTRA_USER_ID, str);
            setCookie(instance, BaseConstants.EXTRA_PASSTOKEN, str2);
            CookieSyncManager.getInstance().sync();
        }
    }

    private void setCookie(CookieManager cookieManager, String str, String str2) {
        cookieManager.setCookie(AbstractAccountWebViewSingleCookieUtil.DOMAIN_URL_SET_COOKIE, String.format("%s=%s;", str, str2));
    }
}
