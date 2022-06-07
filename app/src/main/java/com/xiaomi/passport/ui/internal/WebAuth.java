package com.xiaomi.passport.ui.internal;

import androidx.fragment.app.Fragment;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import com.xiaomi.passport.snscorelib.internal.request.SNSRequest;
import com.xiaomi.passport.ui.internal.WebAuthFragment;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AuthWeb.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0006\u0010\u0007\u001a\u00020\u0004J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\t\u001a\u00020\u0004J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00062\b\u0010\f\u001a\u0004\u0018\u00010\u0006J\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0013¨\u0006\u0014"}, d2 = {"Lcom/xiaomi/passport/ui/internal/WebAuth;", "", "()V", "getAuthFragment", "Lcom/xiaomi/passport/ui/internal/SignInFragment;", "url", "", "getFindPswFragment", "getNotificationFragment", "getSignInFragment", "getSignUpFragment", "sid", "region", "getSnsBindFragment", "e", "Lcom/xiaomi/passport/ui/internal/NeedBindSnsException;", "getSnsWebLoginFragment", "Lcom/xiaomi/passport/snscorelib/internal/request/SNSRequest$RedirectToWebLoginException;", "getUserSettingFragment", "Landroidx/fragment/app/Fragment;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class WebAuth {
    @NotNull
    public final SignInFragment getSignInFragment() {
        String url = URLs.ACCOUNT_DOMAIN;
        Intrinsics.checkExpressionValueIsNotNull(url, "url");
        return getAuthFragment(url);
    }

    @NotNull
    public final SignInFragment getFindPswFragment() {
        return getAuthFragment(URLs.ACCOUNT_DOMAIN + "/pass/forgetPassword");
    }

    @NotNull
    public final SignInFragment getSignUpFragment(@NotNull String sid, @Nullable String str) {
        Intrinsics.checkParameterIsNotNull(sid, "sid");
        String str2 = URLs.ACCOUNT_DOMAIN + "/pass/register?sid=" + sid;
        if (str != null) {
            str2 = str2 + "&_uRegion=" + str;
        }
        return getAuthFragment(str2);
    }

    @NotNull
    public final SignInFragment getNotificationFragment(@NotNull String url) {
        Intrinsics.checkParameterIsNotNull(url, "url");
        return WebAuthFragment.Companion.newInstance(url);
    }

    @NotNull
    public final SignInFragment getSnsBindFragment(@NotNull NeedBindSnsException e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        return SnsBindSignInFragment.Companion.newInstance(e);
    }

    @NotNull
    public final SignInFragment getSnsWebLoginFragment(@NotNull SNSRequest.RedirectToWebLoginException e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        return SnsWebLoginFragment.Companion.newInstance(e);
    }

    @NotNull
    public final Fragment getUserSettingFragment() {
        return PassportWebFragment.Companion.newInstance(URLs.ACCOUNT_DOMAIN + "/pass/auth/security/home");
    }

    private final SignInFragment getAuthFragment(String str) {
        String urlWithLocale = XMPassportUtil.buildUrlWithLocaleQueryParam(str);
        WebAuthFragment.Companion companion = WebAuthFragment.Companion;
        Intrinsics.checkExpressionValueIsNotNull(urlWithLocale, "urlWithLocale");
        return companion.newInstance(urlWithLocale);
    }
}
