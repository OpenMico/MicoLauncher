package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.umeng.analytics.pro.c;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.passport.snscorelib.internal.request.SNSRequest;
import com.xiaomi.passport.utils.AuthenticatorUtil;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FragmentSnsWebLogin.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u001a\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¨\u0006\u0012"}, d2 = {"Lcom/xiaomi/passport/ui/internal/SnsWebLoginFragment;", "Lcom/xiaomi/passport/ui/internal/SnsWebLoginBaseFragment;", "()V", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "storePassToken", "", c.R, "Landroid/content/Context;", "accountInfo", "Lcom/xiaomi/accountsdk/account/data/AccountInfo;", "Companion", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class SnsWebLoginFragment extends SnsWebLoginBaseFragment {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;

    @Override // com.xiaomi.passport.ui.internal.SnsWebLoginBaseFragment, com.xiaomi.passport.ui.internal.SignInFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.xiaomi.passport.ui.internal.SnsWebLoginBaseFragment, com.xiaomi.passport.ui.internal.SignInFragment
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

    @Override // com.xiaomi.passport.ui.internal.SnsWebLoginBaseFragment, com.xiaomi.passport.ui.internal.SignInFragment, androidx.fragment.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* compiled from: FragmentSnsWebLogin.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/xiaomi/passport/ui/internal/SnsWebLoginFragment$Companion;", "", "()V", "newInstance", "Lcom/xiaomi/passport/ui/internal/SnsWebLoginFragment;", "e", "Lcom/xiaomi/passport/snscorelib/internal/request/SNSRequest$RedirectToWebLoginException;", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final SnsWebLoginFragment newInstance(@NotNull SNSRequest.RedirectToWebLoginException e) {
            Intrinsics.checkParameterIsNotNull(e, "e");
            SnsWebLoginFragment snsWebLoginFragment = new SnsWebLoginFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("sns_bind_parameter", e.getSNSBindParameter());
            snsWebLoginFragment.setArguments(bundle);
            return snsWebLoginFragment;
        }
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        final Context context = getContext();
        if (context == null) {
            Intrinsics.throwNpe();
        }
        setMWebView(new PassportWebView(context) { // from class: com.xiaomi.passport.ui.internal.SnsWebLoginFragment$onCreateView$1
            @Override // com.xiaomi.passport.ui.internal.PassportWebView
            public boolean onSnsBindCancel(@Nullable AccountInfo accountInfo) {
                SnsWebLoginFragment.this.loginCancelled();
                return true;
            }

            @Override // com.xiaomi.passport.ui.internal.PassportWebView
            public boolean onSnsBindFinished(@Nullable AccountInfo accountInfo) {
                if (accountInfo != null) {
                    SnsWebLoginFragment.this.storePassToken(getContext(), accountInfo);
                    SnsWebLoginFragment.this.loginSuccess(accountInfo);
                    return true;
                }
                SnsWebLoginFragment.this.loginCancelled();
                return true;
            }

            @Override // com.xiaomi.passport.ui.internal.PassportWebView
            public void onPageFinished(@Nullable WebView webView, @Nullable String str) {
                SnsWebLoginFragment.this.dismissProgress();
            }
        });
        bind();
        return getMWebView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void storePassToken(Context context, AccountInfo accountInfo) {
        if (!TextUtils.isEmpty(accountInfo.passToken)) {
            AuthenticatorUtil.addOrUpdateAccountManager(context, accountInfo);
        }
    }
}
