package com.xiaomi.passport.ui.internal;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.passport.snscorelib.SNSManager;
import com.xiaomi.passport.snscorelib.internal.entity.SNSBindParameter;
import com.xiaomi.passport.snscorelib.internal.utils.SNSCookieManager;
import java.util.HashMap;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: SnsWebLoginBaseFragment.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0016\u0018\u0000 \u000f2\u00020\u00012\u00020\u0002:\u0001\u000fB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u000bH\u0004J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000bH\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lcom/xiaomi/passport/ui/internal/SnsWebLoginBaseFragment;", "Lcom/xiaomi/passport/ui/internal/SignInFragment;", "Lcom/xiaomi/passport/ui/internal/WebViewBack;", "()V", "mWebView", "Landroid/webkit/WebView;", "getMWebView", "()Landroid/webkit/WebView;", "setMWebView", "(Landroid/webkit/WebView;)V", SchemaActivity.VALUE_REGISTER_BIND, "", "canGoBack", "", "goBack", "Companion", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public class SnsWebLoginBaseFragment extends SignInFragment implements WebViewBack {
    public static final Companion Companion = new Companion(null);
    @NotNull
    public static final String KEY_SNS_BIND_PARAMETER = "sns_bind_parameter";
    @NotNull
    public static final String SNS_TOKEN_PH = "sns_token_ph";
    @NotNull
    public static final String SNS_WEIXIN_OPENID = "sns_weixin_openId";
    private HashMap _$_findViewCache;
    @NotNull
    protected WebView mWebView;

    @Override // com.xiaomi.passport.ui.internal.SignInFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.xiaomi.passport.ui.internal.SignInFragment
    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View view2 = getView();
        if (view2 == null) {
            return null;
        }
        View findViewById = view2.findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    @Override // com.xiaomi.passport.ui.internal.SignInFragment, androidx.fragment.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* compiled from: SnsWebLoginBaseFragment.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lcom/xiaomi/passport/ui/internal/SnsWebLoginBaseFragment$Companion;", "", "()V", "KEY_SNS_BIND_PARAMETER", "", "SNS_TOKEN_PH", "SNS_WEIXIN_OPENID", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @NotNull
    protected final WebView getMWebView() {
        WebView webView = this.mWebView;
        if (webView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        }
        return webView;
    }

    protected final void setMWebView(@NotNull WebView webView) {
        Intrinsics.checkParameterIsNotNull(webView, "<set-?>");
        this.mWebView = webView;
    }

    @Override // com.xiaomi.passport.ui.internal.WebViewBack
    public boolean canGoBack() {
        WebView webView = this.mWebView;
        if (webView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        }
        return webView.canGoBack();
    }

    @Override // com.xiaomi.passport.ui.internal.WebViewBack
    public void goBack() {
        WebView webView = this.mWebView;
        if (webView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        }
        webView.goBack();
    }

    protected final void bind() {
        showProgress();
        Bundle arguments = getArguments();
        if (arguments == null) {
            Intrinsics.throwNpe();
        }
        SNSBindParameter sNSBindParameter = (SNSBindParameter) arguments.get("sns_bind_parameter");
        if (sNSBindParameter != null) {
            HashMap hashMap = new HashMap();
            String str = sNSBindParameter.sns_token_ph;
            Intrinsics.checkExpressionValueIsNotNull(str, "bindParameter.sns_token_ph");
            hashMap.put(SNS_TOKEN_PH, str);
            String str2 = sNSBindParameter.sns_weixin_openId;
            Intrinsics.checkExpressionValueIsNotNull(str2, "bindParameter.sns_weixin_openId");
            hashMap.put(SNS_WEIXIN_OPENID, str2);
            WebView webView = this.mWebView;
            if (webView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mWebView");
            }
            SNSCookieManager.setupCookiesForAccountWeb(webView, hashMap);
            WebView webView2 = this.mWebView;
            if (webView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mWebView");
            }
            webView2.loadUrl(sNSBindParameter.snsBindUrl + "&_locale=" + SNSManager.getLocaleString(Locale.getDefault()));
        }
    }
}
