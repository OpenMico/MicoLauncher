package com.xiaomi.passport.ui.internal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.xiaomi.passport.ui.R;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ActivitySnsAuth.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0012\u0010\r\u001a\u00020\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u0010"}, d2 = {"Lcom/xiaomi/passport/ui/internal/SnsAuthActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "mProgressHolder", "Lcom/xiaomi/passport/ui/internal/ProgressHolder;", "mWebView", "Landroid/webkit/WebView;", "getMWebView", "()Landroid/webkit/WebView;", "setMWebView", "(Landroid/webkit/WebView;)V", "onBackPressed", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class SnsAuthActivity extends AppCompatActivity {
    private HashMap _$_findViewCache;
    private final ProgressHolder mProgressHolder = new ProgressHolder();
    @NotNull
    public WebView mWebView;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    @NotNull
    public final WebView getMWebView() {
        WebView webView = this.mWebView;
        if (webView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        }
        return webView;
    }

    public final void setMWebView(@NotNull WebView webView) {
        Intrinsics.checkParameterIsNotNull(webView, "<set-?>");
        this.mWebView = webView;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.passport_webview_layout);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null) {
            Intrinsics.throwNpe();
        }
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        ActionBar supportActionBar2 = getSupportActionBar();
        if (supportActionBar2 == null) {
            Intrinsics.throwNpe();
        }
        supportActionBar2.setDisplayShowHomeEnabled(true);
        ActionBar supportActionBar3 = getSupportActionBar();
        if (supportActionBar3 == null) {
            Intrinsics.throwNpe();
        }
        supportActionBar3.setDisplayShowTitleEnabled(false);
        TextView close_btn = (TextView) _$_findCachedViewById(R.id.close_btn);
        Intrinsics.checkExpressionValueIsNotNull(close_btn, "close_btn");
        close_btn.setVisibility(0);
        ((TextView) _$_findCachedViewById(R.id.close_btn)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.SnsAuthActivity$onCreate$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SnsAuthActivity.this.finish();
            }
        });
        SnsAuthActivity snsAuthActivity = this;
        this.mWebView = new PassportWebView(snsAuthActivity) { // from class: com.xiaomi.passport.ui.internal.SnsAuthActivity$onCreate$2
            @Override // com.xiaomi.passport.ui.internal.PassportWebView
            public boolean shouldOverrideUrlLoading(@Nullable WebView webView, @NotNull String url) {
                Intrinsics.checkParameterIsNotNull(url, "url");
                String queryParameter = Uri.parse(url).getQueryParameter("code");
                if (queryParameter == null) {
                    return super.shouldOverrideUrlLoading(webView, url);
                }
                Intent intent = new Intent();
                intent.putExtra("code", queryParameter);
                SnsAuthActivity.this.setResult(-1, intent);
                SnsAuthActivity.this.finish();
                return true;
            }

            @Override // com.xiaomi.passport.ui.internal.PassportWebView
            public void onPageFinished(@Nullable WebView webView, @Nullable String str) {
                ProgressHolder progressHolder;
                progressHolder = SnsAuthActivity.this.mProgressHolder;
                progressHolder.dismissProgress();
            }
        };
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
        WebView webView = this.mWebView;
        if (webView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        }
        webView.setLayoutParams(layoutParams);
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R.id.webview_container);
        WebView webView2 = this.mWebView;
        if (webView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        }
        relativeLayout.addView(webView2);
        this.mProgressHolder.showProgress(snsAuthActivity);
        WebView webView3 = this.mWebView;
        if (webView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        }
        webView3.loadUrl(getIntent().getStringExtra("url"));
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        WebView webView = this.mWebView;
        if (webView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        }
        if (webView.canGoBack()) {
            WebView webView2 = this.mWebView;
            if (webView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mWebView");
            }
            webView2.goBack();
            return;
        }
        super.onBackPressed();
    }
}
