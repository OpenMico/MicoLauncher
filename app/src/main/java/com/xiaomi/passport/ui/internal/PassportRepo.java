package com.xiaomi.passport.ui.internal;

import android.accounts.Account;
import android.content.Context;
import android.graphics.Bitmap;
import com.umeng.analytics.pro.c;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.ActivatorPhoneInfo;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PassportCore.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J$\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00032\u0006\u0010\u0010\u001a\u00020\u0011H&J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\n\u001a\u00020\u000bH&J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0017H&J\u0018\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH&J\"\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u0006\u0010\u001c\u001a\u00020\u00112\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 H&J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u00032\u0006\u0010#\u001a\u00020$H&J\u0010\u0010%\u001a\u00020\"2\u0006\u0010\u001c\u001a\u00020&H&J\u0016\u0010'\u001a\b\u0012\u0004\u0012\u00020\"0\u00032\u0006\u0010#\u001a\u00020(H&J\u0010\u0010)\u001a\u00020\"2\u0006\u0010\u001c\u001a\u00020&H&¨\u0006*"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PassportRepo;", "", "getCaptchaImage", "Lcom/xiaomi/passport/ui/internal/Source;", "Lcom/xiaomi/passport/ui/internal/Captcha;", "url", "", "getLocalActivatorPhone", "", "Lcom/xiaomi/accountsdk/account/data/ActivatorPhoneInfo;", c.R, "Landroid/content/Context;", "useLocalCache", "", "getPhoneAuthMethod", "Lcom/xiaomi/passport/ui/internal/PhoneAuthMethod;", "phone", "Lcom/xiaomi/passport/ui/internal/PhoneWrapper;", "getXiaomiAccount", "Landroid/accounts/Account;", "invalidateCachePhoneNum", "", "slotId", "", "loadImage", "Landroid/graphics/Bitmap;", "queryPhoneUserInfo", "Lcom/xiaomi/accountsdk/account/data/RegisterUserInfo;", "authCredential", "Lcom/xiaomi/passport/ui/internal/PhoneSmsAuthCredential;", "sendPhoneTicket", "captchaCode", "Lcom/xiaomi/passport/ui/internal/CaptchaCode;", "signInIdAndPsw", "Lcom/xiaomi/accountsdk/account/data/AccountInfo;", "credential", "Lcom/xiaomi/passport/ui/internal/IdPswAuthCredential;", "signInWithPhone", "Lcom/xiaomi/passport/ui/internal/ChoosePhoneSmsAuthCredential;", "signInWithVStep2code", "Lcom/xiaomi/passport/ui/internal/IdPswVStep2AuthCredential;", "signUpWithPhone", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public interface PassportRepo {
    @NotNull
    Source<Captcha> getCaptchaImage(@NotNull String str);

    @NotNull
    Source<List<ActivatorPhoneInfo>> getLocalActivatorPhone(@NotNull Context context, boolean z);

    @NotNull
    Source<PhoneAuthMethod> getPhoneAuthMethod(@NotNull PhoneWrapper phoneWrapper);

    @Nullable
    Account getXiaomiAccount(@NotNull Context context);

    void invalidateCachePhoneNum(@NotNull Context context, int i);

    @NotNull
    Source<Bitmap> loadImage(@Nullable String str);

    @NotNull
    RegisterUserInfo queryPhoneUserInfo(@NotNull PhoneSmsAuthCredential phoneSmsAuthCredential);

    @NotNull
    Source<String> sendPhoneTicket(@NotNull PhoneWrapper phoneWrapper, @Nullable CaptchaCode captchaCode);

    @NotNull
    Source<AccountInfo> signInIdAndPsw(@NotNull IdPswAuthCredential idPswAuthCredential);

    @NotNull
    AccountInfo signInWithPhone(@NotNull ChoosePhoneSmsAuthCredential choosePhoneSmsAuthCredential);

    @NotNull
    Source<AccountInfo> signInWithVStep2code(@NotNull IdPswVStep2AuthCredential idPswVStep2AuthCredential);

    @NotNull
    AccountInfo signUpWithPhone(@NotNull ChoosePhoneSmsAuthCredential choosePhoneSmsAuthCredential);

    /* compiled from: PassportCore.kt */
    @Metadata(bv = {1, 0, 2}, k = 3, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class DefaultImpls {
        @NotNull
        public static /* synthetic */ Source sendPhoneTicket$default(PassportRepo passportRepo, PhoneWrapper phoneWrapper, CaptchaCode captchaCode, int i, Object obj) {
            if (obj == null) {
                if ((i & 2) != 0) {
                    captchaCode = null;
                }
                return passportRepo.sendPhoneTicket(phoneWrapper, captchaCode);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sendPhoneTicket");
        }
    }
}
