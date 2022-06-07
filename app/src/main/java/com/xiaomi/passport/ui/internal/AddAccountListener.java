package com.xiaomi.passport.ui.internal;

import androidx.fragment.app.Fragment;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: ActivityAddAccount.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u001a\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u0005H&J\b\u0010\n\u001a\u00020\u0003H&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH&¨\u0006\u000e"}, d2 = {"Lcom/xiaomi/passport/ui/internal/AddAccountListener;", "", "goBack", "", "closeWebView", "", "gotoFragment", "fragment", "Landroidx/fragment/app/Fragment;", "addToBackStack", "loginCancelled", "loginSuccess", "accountInfo", "Lcom/xiaomi/accountsdk/account/data/AccountInfo;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public interface AddAccountListener {
    void goBack(boolean z);

    void gotoFragment(@NotNull Fragment fragment, boolean z);

    void loginCancelled();

    void loginSuccess(@NotNull AccountInfo accountInfo);

    /* compiled from: ActivityAddAccount.kt */
    @Metadata(bv = {1, 0, 2}, k = 3, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class DefaultImpls {
        public static /* synthetic */ void gotoFragment$default(AddAccountListener addAccountListener, Fragment fragment, boolean z, int i, Object obj) {
            if (obj == null) {
                if ((i & 2) != 0) {
                    z = false;
                }
                addAccountListener.gotoFragment(fragment, z);
                return;
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: gotoFragment");
        }
    }
}
