package com.xiaomi.micolauncher.common.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.mico.base.utils.AndroidBug5497Workaround;
import com.xiaomi.mico.base.utils.WebViewUtils;
import com.xiaomi.mico.base.widgets.webview.CommonWebView;
import com.xiaomi.mico.base.widgets.webview.WebViewAction;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.widget.TitleBar;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class CommonWebActivity extends BaseActivity {
    public static final String COMMON_AUTH = "auth";
    public static final String COMMON_FULLSCREEN = "fullscreen";
    public static final String COMMON_WEB_TITLE = "common_web_title";
    public static final String COMMON_WEB_TITLE_COLOR = "title_color";
    public static final String COMMON_WEB_URL = "common_web_url";
    public static final String MICO = "mico";
    ProgressBar b;
    private int d;
    private boolean e;
    private String g;
    protected String mTitle;
    protected TitleBar mTitleBar;
    protected String mUrl;
    protected CommonWebView mWebView;
    Logger a = XLog.tag("MICO.webview").build();
    private List<WebViewAction> c = new ArrayList();
    private boolean f = true;

    public static Intent buildIntent(Context context, String str, String str2, boolean z) {
        Intent intent = new Intent(context, CommonWebActivity.class);
        intent.putExtra(COMMON_WEB_TITLE, str);
        intent.putExtra(COMMON_WEB_URL, str2);
        intent.putExtra(COMMON_FULLSCREEN, z);
        return intent;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.e = getIntent().getBooleanExtra(COMMON_FULLSCREEN, false);
        setContentView(R.layout.activity_common_web);
        this.b = (ProgressBar) findViewById(R.id.common_web_loading);
        this.mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        this.mWebView = (CommonWebView) findViewById(R.id.common_web_view);
        this.b = (ProgressBar) findViewById(R.id.common_web_loading);
        this.mUrl = getIntent().getStringExtra(COMMON_WEB_URL);
        if (TextUtils.isEmpty(this.mUrl)) {
            finish();
            return;
        }
        this.d = getIntent().getIntExtra(COMMON_WEB_TITLE_COLOR, -1);
        int i = this.d;
        if (i != -1) {
            this.mTitleBar.setBackgroundColor(i);
        }
        this.mTitleBar.setOnLeftIconClickListener(new TitleBar.OnLeftIconClickListener() { // from class: com.xiaomi.micolauncher.common.webview.-$$Lambda$CommonWebActivity$AK8oHobvFXkIie7KT0TMEhHb9hI
            @Override // com.xiaomi.micolauncher.common.widget.TitleBar.OnLeftIconClickListener
            public final void onLeftIconClick() {
                CommonWebActivity.this.a();
            }
        });
        setupWebView(this.mWebView);
        if (this.e) {
            this.f = false;
            this.mWebView.setScrollChangeCallback(new CommonWebView.OnScrollChangeCallback() { // from class: com.xiaomi.micolauncher.common.webview.CommonWebActivity.1
                @Override // com.xiaomi.mico.base.widgets.webview.CommonWebView.OnScrollChangeCallback
                public void onScrollChange(View view, int i2, int i3, int i4, int i5) {
                    int dip2px = DisplayUtils.dip2px(CommonWebActivity.this.getApplicationContext(), 150.0f);
                    int i6 = dip2px - i3;
                    boolean z = false;
                    int max = (int) ((1.0d - ((Math.max(i6, 0) * 1.0d) / dip2px)) * 255.0d);
                    CommonWebActivity commonWebActivity = CommonWebActivity.this;
                    if (max > 50) {
                        z = true;
                    }
                    commonWebActivity.f = z;
                    CommonWebActivity.this.mTitleBar.setTitle(CommonWebActivity.this.f ? CommonWebActivity.this.mTitle : "");
                    if (CommonWebActivity.this.d == -1) {
                        Drawable drawable = CommonWebActivity.this.getResources().getDrawable(R.drawable.bg_title_bar);
                        drawable.setAlpha(max);
                        CommonWebActivity.this.mTitleBar.setBackground(drawable);
                        return;
                    }
                    int i7 = (CommonWebActivity.this.d >> 3) & 255;
                    CommonWebActivity.this.mTitleBar.setBackgroundColor(((max / 255) * i7) << ((i7 & 16777215) + 3));
                }
            });
        } else {
            this.f = true;
        }
        this.g = getIntent().getStringExtra(COMMON_WEB_TITLE);
        setTitle(this.g);
        AndroidBug5497Workaround.assistActivity(this);
        if (TextUtils.isEmpty(getUrl())) {
            finish();
            return;
        }
        if (getIntent().getBooleanExtra(COMMON_AUTH, false)) {
            CookieManager instance = CookieManager.getInstance();
            String userId = TokenManager.getInstance().getUserId();
            String serviceToken = TokenManager.getInstance().getServiceInfo(ApiConstants.MICO_SID).getServiceToken();
            for (int i2 = 0; i2 < ApiConstants.ServiceConfigs.length; i2++) {
                ApiConstants.ServiceConfig serviceConfig = ApiConstants.ServiceConfigs[i2];
                instance.setCookie(serviceConfig.getProductionUrl(), a(BaseConstants.EXTRA_USER_ID, userId));
                instance.setCookie(serviceConfig.getProductionUrl(), a(AuthorizeActivityBase.KEY_SERVICETOKEN, serviceToken));
                instance.setCookie(serviceConfig.getProductionUrl(), a(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, SystemSetting.getDeviceID()));
            }
        }
        a(this.mWebView, getUrl());
        setDefaultScheduleDuration();
    }

    @Override // android.app.Activity
    public void setTitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            this.mTitle = charSequence.toString();
            if (this.f) {
                this.mTitleBar.setTitle(this.mTitle);
            }
        }
    }

    public String getStartUrl() {
        return this.mUrl;
    }

    protected void setupWebView(WebView webView) {
        webView.setWebViewClient(getWebViewClient());
        webView.setWebChromeClient(getWebChromeClient());
        webView.setBackgroundColor(0);
        webView.getBackground().setAlpha(0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.mWebView.onResume();
        this.mWebView.resumeTimers();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.mWebView.onPause();
        this.mWebView.pauseTimers();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        WebViewUtils.destroyWebView(this.mWebView, false, false);
        this.mWebView = null;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    /* renamed from: onBackPressed */
    public void a() {
        if (this.mWebView.canGoBack()) {
            this.mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    protected String getUrl() {
        return this.mUrl;
    }

    public void registerAction(WebViewAction webViewAction) {
        this.c.add(webViewAction);
    }

    private void a(WebView webView, String str) {
        webView.loadUrl(str);
    }

    protected WebViewClient getWebViewClient() {
        return new WebViewClient() { // from class: com.xiaomi.micolauncher.common.webview.CommonWebActivity.2
        };
    }

    private String a(String str, String str2) {
        return String.format("%s=%s; path=/", str, str2);
    }

    protected WebChromeClient getWebChromeClient() {
        return new WebChromeClient() { // from class: com.xiaomi.micolauncher.common.webview.CommonWebActivity.3
            @Override // android.webkit.WebChromeClient
            public void onReceivedTitle(WebView webView, String str) {
                super.onReceivedTitle(webView, str);
                if (TextUtils.isEmpty(CommonWebActivity.this.g) && !TextUtils.isEmpty(str)) {
                    CommonWebActivity.this.setTitle(str);
                }
            }

            @Override // android.webkit.WebChromeClient
            public Bitmap getDefaultVideoPoster() {
                return BitmapFactory.decodeResource(CommonWebActivity.this.getResources(), R.drawable.video_poster);
            }

            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView, int i) {
                CommonWebActivity.this.b.setProgress(i);
            }

            @Override // android.webkit.WebChromeClient
            public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
                CommonWebActivity.this.a.i("onJsAlert %s, %s", str, str2);
                if (CommonWebActivity.this.isDestroyed()) {
                    return true;
                }
                return super.onJsAlert(webView, str, str2, jsResult);
            }

            @Override // android.webkit.WebChromeClient
            public boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
                CommonWebActivity.this.a.i("onJsConfirm %s, %s", str, str2);
                if (CommonWebActivity.this.isDestroyed()) {
                    return true;
                }
                return super.onJsConfirm(webView, str, str2, jsResult);
            }

            @Override // android.webkit.WebChromeClient
            public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
                CommonWebActivity.this.a.i("onJsPrompt %s, %s", str, str2);
                if (CommonWebActivity.this.isDestroyed()) {
                    return true;
                }
                return super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
            }
        };
    }
}
