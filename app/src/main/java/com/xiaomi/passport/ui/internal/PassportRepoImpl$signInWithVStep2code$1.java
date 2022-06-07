package com.xiaomi.passport.ui.internal;

import android.graphics.Bitmap;
import android.util.Pair;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.Step2LoginParams;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.passport.utils.AccountHelper;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: PassportCore.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {"<anonymous>", "Lcom/xiaomi/accountsdk/account/data/AccountInfo;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
final class PassportRepoImpl$signInWithVStep2code$1 extends Lambda implements Function0<AccountInfo> {
    final /* synthetic */ IdPswVStep2AuthCredential $credential;
    final /* synthetic */ PassportRepoImpl this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PassportRepoImpl$signInWithVStep2code$1(PassportRepoImpl passportRepoImpl, IdPswVStep2AuthCredential idPswVStep2AuthCredential) {
        super(0);
        this.this$0 = passportRepoImpl;
        this.$credential = idPswVStep2AuthCredential;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function0
    public final AccountInfo invoke() {
        Pair captchaImageNullSafe;
        try {
            return AccountHelper.loginByStep2(new Step2LoginParams.Builder().setTrust(this.$credential.getTrustCurrentEnv()).setMetaLoginData(this.$credential.getMetaLoginData()).setStep1Token(this.$credential.getStep1Token()).setServiceId(this.$credential.getSid()).setUserId(this.$credential.getId()).setStep2code(this.$credential.getStep2code()).build());
        } catch (NeedCaptchaException e) {
            String url = e.getCaptchaUrl();
            PassportRepoImpl passportRepoImpl = this.this$0;
            Intrinsics.checkExpressionValueIsNotNull(url, "url");
            captchaImageNullSafe = passportRepoImpl.getCaptchaImageNullSafe(url);
            Object obj = captchaImageNullSafe.second;
            Intrinsics.checkExpressionValueIsNotNull(obj, "captcha.second");
            throw new CaptchaException(new Captcha((Bitmap) captchaImageNullSafe.first, (String) obj, url));
        }
    }
}
