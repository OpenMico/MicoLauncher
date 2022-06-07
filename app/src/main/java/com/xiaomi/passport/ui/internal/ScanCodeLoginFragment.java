package com.xiaomi.passport.ui.internal;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.utils.AbstractAccountWebViewSingleCookieUtil;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.FidNonce;
import com.xiaomi.accountsdk.utils.ServerTimeUtil;
import com.xiaomi.accountsdk.utils.UserSpaceIdUtil;
import com.xiaomi.accountsdk.utils.WebViewDeviceIdUtil;
import com.xiaomi.accountsdk.utils.WebViewFidNonceUtil;
import com.xiaomi.accountsdk.utils.WebViewNativeUserAgentUtil;
import com.xiaomi.accountsdk.utils.WebViewUserSpaceIdUtil;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import com.xiaomi.passport.utils.AccountHelper;
import com.xiaomi.passport.utils.AuthenticatorUtil;

/* loaded from: classes4.dex */
public class ScanCodeLoginFragment extends Fragment {
    private static final int REQUEST_CODE_ADD_ACCOUNT = 1;
    private static final String TAG = "ScanCodeLoginFragment";
    private String mRequestUrl;
    private ServerTimeUtil.ServerTimeAlignedListener mServerTimeAlignedListener;
    private WebView mWebView;
    final WebViewClient mWebViewClient = new WebViewClient() { // from class: com.xiaomi.passport.ui.internal.ScanCodeLoginFragment.1
        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            if (str.contains("#closewebview")) {
                boolean isScanResultSuccess = ScanCodeLoginFragment.this.isScanResultSuccess(str);
                AccountLog.i(ScanCodeLoginFragment.TAG, "onPageFinished " + isScanResultSuccess);
                ScanCodeLoginFragment.this.finishActivityAndHandleResponse(isScanResultSuccess);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            if (str.contains("#closewebview")) {
                boolean isScanResultSuccess = ScanCodeLoginFragment.this.isScanResultSuccess(str);
                AccountLog.i(ScanCodeLoginFragment.TAG, "onPageStarted " + isScanResultSuccess);
                ScanCodeLoginFragment.this.finishActivityAndHandleResponse(isScanResultSuccess);
            }
        }
    };
    final WebChromeClient mWebChromeClient = new WebChromeClient() { // from class: com.xiaomi.passport.ui.internal.ScanCodeLoginFragment.2
        @Override // android.webkit.WebChromeClient
        public void onCloseWindow(WebView webView) {
            super.onCloseWindow(webView);
            boolean isScanResultSuccess = ScanCodeLoginFragment.this.isScanResultSuccess(webView.getUrl());
            AccountLog.i(ScanCodeLoginFragment.TAG, "onCloseWindow " + isScanResultSuccess);
            ScanCodeLoginFragment.this.finishActivityAndHandleResponse(isScanResultSuccess);
        }
    };

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mRequestUrl = getActivity().getIntent().getDataString();
        if (!AccountHelper.isMiAccountLoginQRCodeScanResult(this.mRequestUrl)) {
            AccountLog.w(TAG, "illegal account login url");
            finishActivityAndHandleResponse(false);
        } else if (AuthenticatorUtil.getXiaomiAccount(getActivity().getApplicationContext()) == null) {
            startAddAccountIntent();
            getActivity().overridePendingTransition(0, 0);
        }
    }

    @Override // android.app.Fragment
    @Nullable
    @SuppressLint({"SetJavaScriptEnabled"})
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View createView = createView();
        WebSettings settings = this.mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(false);
        settings.setAllowFileAccess(false);
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        this.mWebView.setWebViewClient(this.mWebViewClient);
        this.mWebView.setWebChromeClient(this.mWebChromeClient);
        Account xiaomiAccount = AuthenticatorUtil.getXiaomiAccount(getActivity().getApplicationContext());
        if (xiaomiAccount != null) {
            loginByWebView(xiaomiAccount);
        }
        this.mServerTimeAlignedListener = new WebViewFidNonceUtil.ServerTimeAlignedListenerImpl(this.mWebView);
        ServerTimeUtil.addServerTimeChangedListener(this.mServerTimeAlignedListener);
        return createView;
    }

    private void loginByWebView(Account account) {
        if (account != null) {
            String passToken = AuthenticatorUtil.getPassToken(getActivity().getApplicationContext(), account);
            CookieSyncManager.createInstance(getActivity());
            CookieManager instance = CookieManager.getInstance();
            instance.removeAllCookie();
            instance.setCookie(AbstractAccountWebViewSingleCookieUtil.DOMAIN_URL_SET_COOKIE, getCookie(BaseConstants.EXTRA_USER_ID, account.name));
            instance.setCookie(AbstractAccountWebViewSingleCookieUtil.DOMAIN_URL_SET_COOKIE, getCookie(BaseConstants.EXTRA_PASSTOKEN, passToken));
            String hashedDeviceId = AccountHelper.getHashedDeviceId();
            if (!TextUtils.isEmpty(hashedDeviceId)) {
                new WebViewDeviceIdUtil().setDeviceIdCookie(hashedDeviceId, instance);
            }
            FidNonce build = new FidNonce.Builder().build(FidNonce.Type.WEB_VIEW);
            if (build != null) {
                new WebViewFidNonceUtil().setFidNonceCookie(build, instance);
            }
            String nullableUserSpaceIdCookie = UserSpaceIdUtil.getNullableUserSpaceIdCookie();
            if (!TextUtils.isEmpty(nullableUserSpaceIdCookie)) {
                new WebViewUserSpaceIdUtil().setupUserSpaceIdCookie(nullableUserSpaceIdCookie, instance);
            }
            String userAgent = XMPassportSettings.getUserAgent();
            if (!TextUtils.isEmpty(userAgent)) {
                new WebViewNativeUserAgentUtil().setupUserAgentCookie(userAgent, instance);
            }
            CookieSyncManager.getInstance().sync();
            this.mWebView.loadUrl(XMPassportUtil.buildUrlWithLocaleQueryParam(getActivity().getIntent().getDataString()));
        }
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        ServerTimeUtil.ServerTimeAlignedListener serverTimeAlignedListener = this.mServerTimeAlignedListener;
        if (serverTimeAlignedListener != null) {
            ServerTimeUtil.removeServerTimeChangedListener(serverTimeAlignedListener);
            this.mServerTimeAlignedListener = null;
        }
        super.onDestroy();
    }

    private static String getCookie(String str, String str2) {
        return str + "=" + str2 + "; domain = account.xiaomi.com; path=/";
    }

    private View createView() {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        linearLayout.setOrientation(1);
        this.mWebView = new WebView(getActivity());
        linearLayout.addView(this.mWebView, new ViewGroup.LayoutParams(-1, -1));
        return linearLayout;
    }

    public boolean onBackPressed() {
        if (this.mWebView.canGoBack()) {
            this.mWebView.goBack();
            return false;
        }
        finishActivityAndHandleResponse(false);
        return true;
    }

    @Override // android.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1) {
            if (i2 == -1) {
                loginByWebView(AuthenticatorUtil.getXiaomiAccount(getActivity().getApplicationContext()));
            } else {
                finishActivityAndHandleResponse(false);
            }
        }
    }

    private void startAddAccountIntent() {
        Intent newAddAccountIntent = AuthenticatorUtil.newAddAccountIntent(getActivity(), null, new Bundle(), null);
        newAddAccountIntent.setPackage(getActivity().getPackageName());
        startActivityForResult(newAddAccountIntent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishActivityAndHandleResponse(boolean z) {
        Activity activity = getActivity();
        if (activity != null && !activity.isFinishing()) {
            handleResponse(z);
            activity.finish();
        }
    }

    private void handleResponse(boolean z) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("booleanResult", z);
            AuthenticatorUtil.handleAccountAuthenticatorResponse(arguments.getParcelable("accountAuthenticatorResponse"), bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isScanResultSuccess(String str) {
        String cookie = CookieManager.getInstance().getCookie(str);
        if (cookie == null) {
            return false;
        }
        String[] split = cookie.split(";");
        for (String str2 : split) {
            if (!TextUtils.isEmpty(str2) && str2.contains("scanInfo")) {
                AccountLog.i(TAG, "cookie scan result: " + str2);
                String[] split2 = str2.split("=");
                if (split2[0].trim().equals("scanInfo")) {
                    return String.valueOf(0).equals(split2[1].trim());
                }
            }
        }
        return false;
    }

    public void checkScanCodeSuccess() {
        handleResponse(isScanResultSuccess(this.mRequestUrl));
    }
}
