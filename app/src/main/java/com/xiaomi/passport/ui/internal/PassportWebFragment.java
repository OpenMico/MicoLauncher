package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.xiaomi.passport.ui.settings.utils.AccountSmsVerifyCodeReceiver;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AuthWeb.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 \u00152\u00020\u00012\u00020\u0002:\u0001\u0015B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010\f\u001a\u00020\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J(\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\b\u0001\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PassportWebFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/xiaomi/passport/ui/internal/WebViewBack;", "()V", "mProgressHolder", "Lcom/xiaomi/passport/ui/internal/ProgressHolder;", "mWebView", "Landroid/webkit/WebView;", "canGoBack", "", "goBack", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "Companion", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PassportWebFragment extends Fragment implements WebViewBack {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private final ProgressHolder mProgressHolder = new ProgressHolder();
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
        View view2 = getView();
        if (view2 == null) {
            return null;
        }
        View findViewById = view2.findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    @Override // androidx.fragment.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* compiled from: AuthWeb.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PassportWebFragment$Companion;", "", "()V", "newInstance", "Lcom/xiaomi/passport/ui/internal/PassportWebFragment;", "url", "", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final PassportWebFragment newInstance(@NotNull String url) {
            Intrinsics.checkParameterIsNotNull(url, "url");
            PassportWebFragment passportWebFragment = new PassportWebFragment();
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            passportWebFragment.setArguments(bundle);
            return passportWebFragment;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        AccountSmsVerifyCodeReceiver.tryRequestSmsPermission(getActivity());
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        final Context context = getContext();
        if (context == null) {
            Intrinsics.throwNpe();
        }
        this.mWebView = new PassportWebView(context) { // from class: com.xiaomi.passport.ui.internal.PassportWebFragment$onCreateView$1
            @Override // com.xiaomi.passport.ui.internal.PassportWebView
            public void onPageFinished(@Nullable WebView webView, @Nullable String str) {
                ProgressHolder progressHolder;
                progressHolder = PassportWebFragment.this.mProgressHolder;
                progressHolder.dismissProgress();
            }
        };
        ProgressHolder progressHolder = this.mProgressHolder;
        Context context2 = getContext();
        if (context2 == null) {
            Intrinsics.throwNpe();
        }
        progressHolder.showProgress(context2);
        WebView webView = this.mWebView;
        if (webView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        }
        Bundle arguments = getArguments();
        if (arguments == null) {
            Intrinsics.throwNpe();
        }
        webView.loadUrl((String) arguments.get("url"));
        WebView webView2 = this.mWebView;
        if (webView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mWebView");
        }
        return webView2;
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
