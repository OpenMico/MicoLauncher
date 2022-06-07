package com.xiaomi.passport.ui.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.NotificationAuthResult;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.NotificationActivity;
import com.xiaomi.passport.ui.internal.NotificationAuthTask;
import com.xiaomi.passport.utils.AccountHelper;
import com.xiaomi.passport.utils.AuthenticatorUtil;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ActivityNotification.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0012\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0014J\b\u0010\f\u001a\u00020\bH\u0002J\u0012\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\b\u0010\u0011\u001a\u00020\bH\u0014J\u0018\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/xiaomi/passport/ui/internal/NotificationActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "mWebView", "Landroid/webkit/WebView;", "getResources", "Landroid/content/res/Resources;", "onBackPressed", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onNeedReLogin", "onOptionsItemSelected", "", "item", "Landroid/view/MenuItem;", "onPause", "setAddAccountResultAndFinish", "resultCode", "", "accountInfo", "Lcom/xiaomi/accountsdk/account/data/AccountInfo;", "NotificationAuthTaskCallback", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class NotificationActivity extends AppCompatActivity {
    private HashMap _$_findViewCache;
    private WebView mWebView;

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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        overridePendingTransition(0, 0);
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
        ((TextView) _$_findCachedViewById(R.id.close_btn)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.NotificationActivity$onCreate$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NotificationActivity.this.onNeedReLogin();
            }
        });
        String stringExtra = getIntent().getStringExtra(AccountIntent.EXTRA_NOTIFICATION_URL);
        final NotificationActivity notificationActivity = this;
        this.mWebView = new PassportWebView(notificationActivity) { // from class: com.xiaomi.passport.ui.internal.NotificationActivity$onCreate$2
            @Override // com.xiaomi.passport.ui.internal.PassportWebView
            public void onPageFinished(@Nullable WebView webView, @Nullable String str) {
            }

            @Override // com.xiaomi.passport.ui.internal.PassportWebView
            public boolean onLoginEnd(@NotNull AccountInfo accountInfo) {
                Intrinsics.checkParameterIsNotNull(accountInfo, "accountInfo");
                AuthenticatorUtil.addOrUpdateAccountManager(NotificationActivity.this, accountInfo);
                NotificationActivity.this.setResult(-1);
                NotificationActivity.this.setAddAccountResultAndFinish(-1, accountInfo);
                return true;
            }

            @Override // com.xiaomi.passport.ui.internal.PassportWebView
            public boolean onNeedReLogin() {
                onNeedReLogin();
                return true;
            }

            @Override // com.xiaomi.passport.ui.internal.PassportWebView
            public boolean onAuthEnd(@NotNull String url) {
                Intrinsics.checkParameterIsNotNull(url, "url");
                new NotificationAuthTask(url, new NotificationActivity.NotificationAuthTaskCallback(NotificationActivity.this)).execute(new Void[0]);
                return true;
            }
        };
        WebView webView = this.mWebView;
        if (webView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        }
        webView.loadUrl(stringExtra);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        WebView webView2 = this.mWebView;
        if (webView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        }
        webView2.setLayoutParams(layoutParams);
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R.id.webview_container);
        WebView webView3 = this.mWebView;
        if (webView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        }
        relativeLayout.addView(webView3);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }

    /* compiled from: ActivityNotification.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\f"}, d2 = {"Lcom/xiaomi/passport/ui/internal/NotificationActivity$NotificationAuthTaskCallback;", "Lcom/xiaomi/passport/ui/internal/NotificationAuthTask$NotificationAuthUICallback;", IDMServer.PERSIST_TYPE_ACTIVITY, "Landroid/app/Activity;", "(Landroid/app/Activity;)V", "getActivity", "()Landroid/app/Activity;", "setActivity", "call", "", "result", "Lcom/xiaomi/accountsdk/account/data/NotificationAuthResult;", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class NotificationAuthTaskCallback implements NotificationAuthTask.NotificationAuthUICallback {
        @NotNull
        private Activity activity;

        public NotificationAuthTaskCallback(@NotNull Activity activity) {
            Intrinsics.checkParameterIsNotNull(activity, "activity");
            this.activity = activity;
        }

        @NotNull
        public final Activity getActivity() {
            return this.activity;
        }

        public final void setActivity(@NotNull Activity activity) {
            Intrinsics.checkParameterIsNotNull(activity, "<set-?>");
            this.activity = activity;
        }

        @Override // com.xiaomi.passport.ui.internal.NotificationAuthTask.NotificationAuthUICallback
        public void call(@Nullable NotificationAuthResult notificationAuthResult) {
            if (!this.activity.isFinishing()) {
                Intent intent = new Intent();
                if (notificationAuthResult != null) {
                    intent.putExtra("notification_auth_end", notificationAuthResult);
                    this.activity.setResult(-1, intent);
                } else {
                    this.activity.setResult(0, intent);
                }
                this.activity.finish();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setAddAccountResultAndFinish(int i, AccountInfo accountInfo) {
        Parcelable parcelableExtra = getIntent().getParcelableExtra("accountAuthenticatorResponse");
        Intrinsics.checkExpressionValueIsNotNull(parcelableExtra, "intent.getParcelableExtr…T_AUTHENTICATOR_RESPONSE)");
        AuthenticatorUtil.handleAccountAuthenticatorResponse(parcelableExtra, AccountHelper.getAccountAuthenticatorResponseResult(i, accountInfo, getIntent().getBooleanExtra("need_retry_on_authenticator_response_result", false)));
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onNeedReLogin() {
        setResult(0);
        finish();
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(@Nullable MenuItem menuItem) {
        if (menuItem == null || menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
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

    @Override // androidx.appcompat.app.AppCompatActivity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    @NotNull
    public Resources getResources() {
        if (Build.VERSION.SDK_INT >= 24) {
            Context applicationContext = getApplicationContext();
            Intrinsics.checkExpressionValueIsNotNull(applicationContext, "applicationContext");
            Resources resources = applicationContext.getResources();
            Intrinsics.checkExpressionValueIsNotNull(resources, "applicationContext.resources");
            return resources;
        }
        Resources resources2 = super.getResources();
        Intrinsics.checkExpressionValueIsNotNull(resources2, "super.getResources()");
        return resources2;
    }
}
