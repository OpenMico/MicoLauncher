package com.xiaomi.passport.ui.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AuthBaseProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0003H&J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00032\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\fH&R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u0012"}, d2 = {"Lcom/xiaomi/passport/ui/internal/BaseAuthProvider;", "Lcom/xiaomi/passport/ui/internal/AuthProvider;", "name", "", "(Ljava/lang/String;)V", "passportRepo", "Lcom/xiaomi/passport/ui/internal/PassportRepo;", "getPassportRepo", "()Lcom/xiaomi/passport/ui/internal/PassportRepo;", "setPassportRepo", "(Lcom/xiaomi/passport/ui/internal/PassportRepo;)V", "getFragment", "Lcom/xiaomi/passport/ui/internal/BaseSignInFragment;", "sid", "defaultCountryCodeWithPrefix", "setPresenter", "", "fragment", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public abstract class BaseAuthProvider extends AuthProvider {
    @NotNull
    private PassportRepo passportRepo = new PassportRepoImpl();

    @NotNull
    public abstract BaseSignInFragment getFragment(@NotNull String str);

    public abstract void setPresenter(@NotNull String str, @NotNull BaseSignInFragment baseSignInFragment);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BaseAuthProvider(@NotNull String name) {
        super(name);
        Intrinsics.checkParameterIsNotNull(name, "name");
    }

    @NotNull
    public final PassportRepo getPassportRepo() {
        return this.passportRepo;
    }

    public final void setPassportRepo(@NotNull PassportRepo passportRepo) {
        Intrinsics.checkParameterIsNotNull(passportRepo, "<set-?>");
        this.passportRepo = passportRepo;
    }

    @NotNull
    public final BaseSignInFragment getFragment(@NotNull String sid, @Nullable String str) {
        Intrinsics.checkParameterIsNotNull(sid, "sid");
        BaseSignInFragment fragment = getFragment(sid);
        fragment.setDefaultCountryCodeWithPrefix(str);
        return fragment;
    }
}
