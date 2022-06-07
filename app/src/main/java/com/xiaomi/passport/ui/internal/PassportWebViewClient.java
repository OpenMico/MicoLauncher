package com.xiaomi.passport.ui.internal;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.xiaomi.onetrack.OneTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: webkit.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J0\u0010\u000b\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\f\u001a\u0004\u0018\u00010\n2\b\u0010\r\u001a\u0004\u0018\u00010\n2\b\u0010\u000e\u001a\u0004\u0018\u00010\nH\u0016J\u001a\u0010\u000f\u001a\u00020\u00102\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PassportWebViewClient;", "Landroid/webkit/WebViewClient;", "webView", "Lcom/xiaomi/passport/ui/internal/PassportWebView;", "(Lcom/xiaomi/passport/ui/internal/PassportWebView;)V", "onPageFinished", "", OneTrack.Event.VIEW, "Landroid/webkit/WebView;", "url", "", "onReceivedLoginRequest", "realm", "account", "args", "shouldOverrideUrlLoading", "", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public class PassportWebViewClient extends WebViewClient {
    private final PassportWebView webView;

    public PassportWebViewClient(@NotNull PassportWebView webView) {
        Intrinsics.checkParameterIsNotNull(webView, "webView");
        this.webView = webView;
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedLoginRequest(@Nullable WebView webView, @Nullable String str, @Nullable String str2, @Nullable String str3) {
        this.webView.onReceivedLoginRequest(str, str3);
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(@Nullable WebView webView, @Nullable String str) {
        this.webView.onPageFinished(webView, str);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(@Nullable WebView webView, @NotNull String url) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        return this.webView.shouldOverrideUrlLoading(webView, url);
    }
}
