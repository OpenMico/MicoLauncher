package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.umeng.analytics.pro.c;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.SignInContract;
import com.xiaomi.smarthome.setting.ServerSetting;
import java.io.IOException;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FragmentSignIn.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0018\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0017H\u0016J\u0010\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\"H\u0016J\u0012\u0010#\u001a\u00020\u00172\b\u0010$\u001a\u0004\u0018\u00010%H\u0016J\b\u0010&\u001a\u00020\u0017H\u0016J\b\u0010'\u001a\u00020\u0017H\u0016J\u0010\u0010(\u001a\u00020\u00172\u0006\u0010)\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020,H\u0016J\b\u0010-\u001a\u00020\u0017H\u0016J\u0010\u0010.\u001a\u00020\u00172\u0006\u0010/\u001a\u000200H\u0016R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u00061"}, d2 = {"Lcom/xiaomi/passport/ui/internal/SignInFragment;", "Landroidx/fragment/app/Fragment;", "Lcom/xiaomi/passport/ui/internal/SignInContract$View;", "()V", "addAccountListener", "Lcom/xiaomi/passport/ui/internal/AddAccountListener;", "getAddAccountListener", "()Lcom/xiaomi/passport/ui/internal/AddAccountListener;", "setAddAccountListener", "(Lcom/xiaomi/passport/ui/internal/AddAccountListener;)V", "mCommonErrorHandler", "Lcom/xiaomi/passport/ui/internal/CommonErrorHandler;", "getMCommonErrorHandler", "()Lcom/xiaomi/passport/ui/internal/CommonErrorHandler;", "mProgressHolder", "Lcom/xiaomi/passport/ui/internal/ProgressHolder;", "mWebAuth", "Lcom/xiaomi/passport/ui/internal/WebAuth;", "getMWebAuth", "()Lcom/xiaomi/passport/ui/internal/WebAuth;", "setMWebAuth", "(Lcom/xiaomi/passport/ui/internal/WebAuth;)V", "dismissProgress", "", "gotoBindSnsFragment", "e", "Lcom/xiaomi/passport/ui/internal/NeedBindSnsException;", "gotoFragment", "fragment", "addToBackStack", "", "loginCancelled", "loginSuccess", "accountInfo", "Lcom/xiaomi/accountsdk/account/data/AccountInfo;", "onAttach", c.R, "Landroid/content/Context;", "onDestroyView", "onDetach", "openNotificationUrl", "url", "", "showNetworkError", "Ljava/io/IOException;", "showProgress", "showUnKnowError", ServerSetting.SERVER_TK, "", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public class SignInFragment extends Fragment implements SignInContract.View {
    private HashMap _$_findViewCache;
    @Nullable
    private AddAccountListener addAccountListener;
    private final ProgressHolder mProgressHolder = new ProgressHolder();
    @NotNull
    private WebAuth mWebAuth = new WebAuth();
    @NotNull
    private final CommonErrorHandler mCommonErrorHandler = new CommonErrorHandler();

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

    @Nullable
    public final AddAccountListener getAddAccountListener() {
        return this.addAccountListener;
    }

    public final void setAddAccountListener(@Nullable AddAccountListener addAccountListener) {
        this.addAccountListener = addAccountListener;
    }

    @NotNull
    public final WebAuth getMWebAuth() {
        return this.mWebAuth;
    }

    public final void setMWebAuth(@NotNull WebAuth webAuth) {
        Intrinsics.checkParameterIsNotNull(webAuth, "<set-?>");
        this.mWebAuth = webAuth;
    }

    @NotNull
    public final CommonErrorHandler getMCommonErrorHandler() {
        return this.mCommonErrorHandler;
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(@Nullable Context context) {
        super.onAttach(context);
        if (context instanceof AddAccountListener) {
            this.addAccountListener = (AddAccountListener) context;
            return;
        }
        StringBuilder sb = new StringBuilder();
        if (context == null) {
            Intrinsics.throwNpe();
        }
        sb.append(context.toString());
        sb.append(" must implement AddAccountListener");
        throw new RuntimeException(sb.toString());
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        this.addAccountListener = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        dismissProgress();
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.xiaomi.passport.ui.internal.SignInContract.View
    public void showNetworkError(@NotNull IOException e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        if (getContext() != null) {
            CommonErrorHandler commonErrorHandler = this.mCommonErrorHandler;
            Context context = getContext();
            if (context == null) {
                Intrinsics.throwNpe();
            }
            commonErrorHandler.onIOError(e, context, (ConstraintLayout) _$_findCachedViewById(R.id.fragment_main));
        }
    }

    @Override // com.xiaomi.passport.ui.internal.SignInContract.View
    public void showUnKnowError(@NotNull Throwable tr) {
        Intrinsics.checkParameterIsNotNull(tr, "tr");
        if (getContext() != null) {
            CommonErrorHandler commonErrorHandler = this.mCommonErrorHandler;
            Context context = getContext();
            if (context == null) {
                Intrinsics.throwNpe();
            }
            commonErrorHandler.onUnKnowError(tr, context);
        }
    }

    @Override // com.xiaomi.passport.ui.internal.SignInContract.View
    public void showProgress() {
        if (getContext() != null) {
            ProgressHolder progressHolder = this.mProgressHolder;
            Context context = getContext();
            if (context == null) {
                Intrinsics.throwNpe();
            }
            progressHolder.showProgress(context);
        }
    }

    @Override // com.xiaomi.passport.ui.internal.SignInContract.View
    public void dismissProgress() {
        this.mProgressHolder.dismissProgress();
    }

    @Override // com.xiaomi.passport.ui.internal.SignInContract.View
    public void gotoBindSnsFragment(@NotNull NeedBindSnsException e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        AddAccountListener addAccountListener = this.addAccountListener;
        if (addAccountListener != null) {
            addAccountListener.gotoFragment(this.mWebAuth.getSnsBindFragment(e), true);
        }
    }

    @Override // com.xiaomi.passport.ui.internal.SignInContract.View
    public void openNotificationUrl(@NotNull String url) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        AddAccountListener addAccountListener = this.addAccountListener;
        if (addAccountListener != null) {
            addAccountListener.gotoFragment(this.mWebAuth.getNotificationFragment(url), true);
        }
    }

    @Override // com.xiaomi.passport.ui.internal.SignInContract.View
    public void loginSuccess(@NotNull AccountInfo accountInfo) {
        Intrinsics.checkParameterIsNotNull(accountInfo, "accountInfo");
        AddAccountListener addAccountListener = this.addAccountListener;
        if (addAccountListener != null) {
            addAccountListener.loginSuccess(accountInfo);
        }
    }

    @Override // com.xiaomi.passport.ui.internal.SignInContract.View
    public void loginCancelled() {
        AddAccountListener addAccountListener = this.addAccountListener;
        if (addAccountListener != null) {
            addAccountListener.loginCancelled();
        }
    }

    @Override // com.xiaomi.passport.ui.internal.SignInContract.View
    public void gotoFragment(@NotNull Fragment fragment, boolean z) {
        Intrinsics.checkParameterIsNotNull(fragment, "fragment");
        AddAccountListener addAccountListener = this.addAccountListener;
        if (addAccountListener != null) {
            addAccountListener.gotoFragment(fragment, z);
        }
    }
}
