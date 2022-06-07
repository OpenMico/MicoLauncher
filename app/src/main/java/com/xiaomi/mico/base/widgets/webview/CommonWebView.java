package com.xiaomi.mico.base.widgets.webview;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.Stack;

/* loaded from: classes3.dex */
public class CommonWebView extends WebView {
    private static final String b = "CommonWebView";
    Stack<String> a = new Stack<>();
    private OnScrollChangeCallback c;
    private WebViewClient d;

    /* loaded from: classes3.dex */
    public interface CustomWebViewClient {
        String getFinalUrl();
    }

    /* loaded from: classes3.dex */
    public interface OnScrollChangeCallback {
        void onScrollChange(View view, int i, int i2, int i3, int i4);
    }

    public CommonWebView(Context context) {
        super(context);
    }

    public CommonWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnTouchListener(new View.OnTouchListener() { // from class: com.xiaomi.mico.base.widgets.webview.CommonWebView.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (CommonWebView.this.d == null || !(CommonWebView.this.d instanceof CustomWebViewClient)) {
                    return false;
                }
                CommonWebView.this.addHistory(((CustomWebViewClient) CommonWebView.this.d).getFinalUrl());
                return false;
            }
        });
        setLongClickable(true);
        setOnLongClickListener(new View.OnLongClickListener() { // from class: com.xiaomi.mico.base.widgets.webview.CommonWebView.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                return true;
            }
        });
        setupWebView();
    }

    protected void setupWebView() {
        WebSettings settings = getSettings();
        settings.setDatabaseEnabled(true);
        if (Build.VERSION.SDK_INT < 19) {
            settings.setDatabasePath("/data/data/" + getContext().getPackageName() + "/databases/");
        }
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setBlockNetworkLoads(false);
        settings.setBlockNetworkImage(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
            settings.setMixedContentMode(2);
        }
    }

    public void setCustomUserAgent(String str) {
        WebSettings settings = getSettings();
        settings.setUserAgentString(settings.getUserAgentString() + str);
    }

    public void setScrollChangeCallback(OnScrollChangeCallback onScrollChangeCallback) {
        this.c = onScrollChangeCallback;
    }

    @Override // android.webkit.WebView, android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        OnScrollChangeCallback onScrollChangeCallback = this.c;
        if (onScrollChangeCallback != null) {
            onScrollChangeCallback.onScrollChange(this, i, i2, i3, i4);
        }
    }

    @Override // android.webkit.WebView
    public void setWebViewClient(WebViewClient webViewClient) {
        super.setWebViewClient(webViewClient);
        this.d = webViewClient;
    }

    @Override // android.webkit.WebView
    public boolean canGoBack() {
        if (this.a.size() > 0 && this.a.peek().equals(getUrl())) {
            this.a.pop();
        }
        return this.a.size() > 0;
    }

    @Override // android.webkit.WebView
    public void goBack() {
        String pop = this.a.pop();
        loadUrl(pop);
        String str = b;
        Log.i(str, "load url " + pop);
    }

    public void addHistory(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (this.a.size() == 0 || !this.a.peek().equals(str)) {
            this.a.add(str);
            String str2 = b;
            Log.i(str2, "add url " + str);
        }
    }
}
