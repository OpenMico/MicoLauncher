package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.passport.ui.StatConstants;
import com.xiaomi.passport.ui.TrackEventManager;
import com.xiaomi.passport.ui.settings.utils.AccountSmsVerifyCodeReceiver;
import com.xiaomi.passport.utils.AuthenticatorUtil;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AuthWeb.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0000\u0018\u0000 \u001b2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001\u001bB\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J(\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\b\u0001\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\b\u0010\u0015\u001a\u00020\fH\u0016J\u001c\u0010\u0016\u001a\u00020\f2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010\u001a\u001a\u00020\fH\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/xiaomi/passport/ui/internal/WebAuthFragment;", "Lcom/xiaomi/passport/ui/internal/SignInFragment;", "Lcom/xiaomi/passport/ui/internal/WebViewBack;", "Lcom/xiaomi/passport/ui/settings/utils/AccountSmsVerifyCodeReceiver$SmsVerifyCodeMessageListener;", "()V", "mSmsReceiver", "Lcom/xiaomi/passport/ui/settings/utils/AccountSmsVerifyCodeReceiver;", "mWebView", "Landroid/webkit/WebView;", "canGoBack", "", "goBack", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onPause", "onReceived", "message", "", "verifyCode", "onResume", "Companion", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class WebAuthFragment extends SignInFragment implements WebViewBack, AccountSmsVerifyCodeReceiver.SmsVerifyCodeMessageListener {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private AccountSmsVerifyCodeReceiver mSmsReceiver;
    private WebView mWebView;

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

    /* compiled from: AuthWeb.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/xiaomi/passport/ui/internal/WebAuthFragment$Companion;", "", "()V", "newInstance", "Lcom/xiaomi/passport/ui/internal/WebAuthFragment;", "url", "", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final WebAuthFragment newInstance(@NotNull String url) {
            Intrinsics.checkParameterIsNotNull(url, "url");
            WebAuthFragment webAuthFragment = new WebAuthFragment();
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            webAuthFragment.setArguments(bundle);
            return webAuthFragment;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        this.mSmsReceiver = new AccountSmsVerifyCodeReceiver(this);
        getActivity().registerReceiver(this.mSmsReceiver, intentFilter);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.mSmsReceiver != null) {
            getActivity().unregisterReceiver(this.mSmsReceiver);
            this.mSmsReceiver = null;
        }
    }

    @Override // com.xiaomi.passport.ui.settings.utils.AccountSmsVerifyCodeReceiver.SmsVerifyCodeMessageListener
    public void onReceived(@Nullable String str, @Nullable String str2) {
        if (str2 != null) {
            WebView webView = this.mWebView;
            if (webView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mWebView");
            }
            webView.loadUrl("javascript:(function(){document.getElementsByName('ticket')[0].value='" + str2 + "';})()");
        }
    }

    /* JADX WARN: Type inference failed for: r2v8, types: [T, java.lang.String] */
    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = null;
        final Context context = getContext();
        if (context == null) {
            Intrinsics.throwNpe();
        }
        this.mWebView = new PassportWebView(context) { // from class: com.xiaomi.passport.ui.internal.WebAuthFragment$onCreateView$1
            @Override // com.xiaomi.passport.ui.internal.PassportWebView
            public void onPageFinished(@Nullable WebView webView, @Nullable String str) {
                WebAuthFragment.this.dismissProgress();
            }

            @Override // com.xiaomi.passport.ui.internal.PassportWebView
            public boolean onLoginEnd(@NotNull AccountInfo accountInfo) {
                Intrinsics.checkParameterIsNotNull(accountInfo, "accountInfo");
                T t = objectRef.element;
                if (t == 0) {
                    Intrinsics.throwUninitializedPropertyAccessException("strUrl");
                }
                if (StringsKt.contains$default((CharSequence) ((String) t), (CharSequence) MiPushClient.COMMAND_REGISTER, false, 2, (Object) null)) {
                    TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_PASSWORD_SIGN_UP_SUCCESS);
                }
                AuthenticatorUtil.addOrUpdateAccountManager(getContext(), accountInfo);
                WebAuthFragment.this.loginSuccess(accountInfo);
                return true;
            }

            @Override // com.xiaomi.passport.ui.internal.PassportWebView
            public boolean onNeedReLogin() {
                AddAccountListener addAccountListener = WebAuthFragment.this.getAddAccountListener();
                if (addAccountListener != null) {
                    addAccountListener.goBack(true);
                }
                return true;
            }
        };
        showProgress();
        Bundle arguments = getArguments();
        if (arguments == null) {
            Intrinsics.throwNpe();
        }
        Object obj = arguments.get("url");
        if (obj != null) {
            objectRef.element = (String) obj;
            WebView webView = this.mWebView;
            if (webView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mWebView");
            }
            Bundle arguments2 = getArguments();
            if (arguments2 == null) {
                Intrinsics.throwNpe();
            }
            webView.loadUrl((String) arguments2.get("url"));
            WebView webView2 = this.mWebView;
            if (webView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mWebView");
            }
            return webView2;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
    }

    @Override // com.xiaomi.passport.ui.internal.WebViewBack
    public void goBack() {
        WebView webView = this.mWebView;
        if (webView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        }
        webView.goBack();
    }

    @Override // com.xiaomi.passport.ui.internal.WebViewBack
    public boolean canGoBack() {
        WebView webView = this.mWebView;
        if (webView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        }
        return webView.canGoBack();
    }
}
