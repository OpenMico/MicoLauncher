package com.xiaomi.micolauncher.module.homepage.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.schema.SchemaManager;

/* loaded from: classes3.dex */
public class SimpleWebActivity extends BaseActivity {
    public static final String NAME = "actionUrl";
    WebView a;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.simple_layout);
        this.a = (WebView) findViewById(R.id.simple_webview);
        String stringExtra = getIntent().getStringExtra(NAME);
        this.a.setWebChromeClient(new WebChromeClient() { // from class: com.xiaomi.micolauncher.module.homepage.activity.SimpleWebActivity.1
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
            }

            @Override // android.webkit.WebChromeClient
            public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
                return super.onJsAlert(webView, str, str2, jsResult);
            }
        });
        this.a.setWebViewClient(new a());
        this.a.setSystemUiVisibility(4871);
        WebSettings settings = this.a.getSettings();
        settings.setCacheMode(-1);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setBlockNetworkLoads(false);
        settings.setBlockNetworkImage(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setUserAgentString("Mozilla/5.0 (Linux; U; Android 4.4.2; en-us; SCH-I535 Build/KOT49H) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this.a, true);
            settings.setMixedContentMode(2);
        }
        this.a.loadUrl(stringExtra);
        setDefaultScheduleDuration();
    }

    /* loaded from: classes3.dex */
    class a extends WebViewClient {
        a() {
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            Uri url = webResourceRequest.getUrl();
            if (!"mico".equalsIgnoreCase(url.getScheme()) || !SchemaManager.get().canHandle(url.toString())) {
                return false;
            }
            SchemaManager.handleSchema(SimpleWebActivity.this.getBaseContext(), url);
            return true;
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();
        }
    }

    public static void startSimpleWebActivity(Context context, String str) {
        Intent intent = new Intent(context, SimpleWebActivity.class);
        intent.putExtra(NAME, str);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }
}
