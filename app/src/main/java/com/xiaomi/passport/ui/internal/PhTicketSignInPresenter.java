package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.pro.c;
import com.xiaomi.accountsdk.account.data.ActivatorPhoneInfo;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.passport.ui.StatConstants;
import com.xiaomi.passport.ui.TrackEventManager;
import com.xiaomi.passport.ui.internal.PhTicketSignInContract;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FragmentPhTicketAuth.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B)\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0016J\u0010\u0010$\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020%H\u0016J\u0018\u0010$\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0016J\u0016\u0010&\u001a\u00020!2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0005J\u0018\u0010*\u001a\u00020\u001f2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010'\u001a\u00020(H\u0002J\u001a\u0010+\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020(2\b\u0010,\u001a\u0004\u0018\u00010-H\u0016J\u0018\u0010.\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u0005H\u0016J\u0010\u0010/\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0002R\u0016\u0010\n\u001a\u00020\u00058\u0002X\u0083D¢\u0006\b\n\u0000\u0012\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u00060"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PhTicketSignInPresenter;", "Lcom/xiaomi/passport/ui/internal/PhTicketSignInContract$Presenter;", c.R, "Landroid/content/Context;", "sid", "", OneTrack.Event.VIEW, "Lcom/xiaomi/passport/ui/internal/PhTicketSignInContract$View;", "name", "(Landroid/content/Context;Ljava/lang/String;Lcom/xiaomi/passport/ui/internal/PhTicketSignInContract$View;Ljava/lang/String;)V", "TAG", "TAG$annotations", "()V", "getContext", "()Landroid/content/Context;", "getName", "()Ljava/lang/String;", "passportRepo", "Lcom/xiaomi/passport/ui/internal/PassportRepo;", "getPassportRepo", "()Lcom/xiaomi/passport/ui/internal/PassportRepo;", "setPassportRepo", "(Lcom/xiaomi/passport/ui/internal/PassportRepo;)V", c.M, "Lcom/xiaomi/passport/ui/internal/AuthProvider;", "getProvider", "()Lcom/xiaomi/passport/ui/internal/AuthProvider;", "getSid", "getView", "()Lcom/xiaomi/passport/ui/internal/PhTicketSignInContract$View;", "chooseSignIn", "", "authCredential", "Lcom/xiaomi/passport/ui/internal/PhoneSmsAuthCredential;", "userInfo", "Lcom/xiaomi/accountsdk/account/data/RegisterUserInfo;", "chooseSignUp", "Lcom/xiaomi/passport/ui/internal/ChoosePhoneSmsAuthCredential;", "createAuthCredential", "phone", "Lcom/xiaomi/passport/ui/internal/PhoneWrapper;", "ticket", "invalidateCachePhoneNum", "sendTicket", "captchaCode", "Lcom/xiaomi/passport/ui/internal/CaptchaCode;", "signInPhoneAndTicket", "signInWithAuthCredential", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PhTicketSignInPresenter implements PhTicketSignInContract.Presenter {
    private final String TAG;
    @NotNull
    private final Context context;
    @NotNull
    private final String name;
    @NotNull
    private PassportRepo passportRepo;
    @Nullable
    private final AuthProvider provider;
    @NotNull
    private final String sid;
    @NotNull
    private final PhTicketSignInContract.View view;

    private static /* synthetic */ void TAG$annotations() {
    }

    public PhTicketSignInPresenter(@NotNull Context context, @NotNull String sid, @NotNull PhTicketSignInContract.View view, @NotNull String name) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(sid, "sid");
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(name, "name");
        this.context = context;
        this.sid = sid;
        this.view = view;
        this.name = name;
        this.TAG = "PhTicketSignIn";
        this.provider = PassportUI.INSTANCE.getProvider(this.name);
        this.passportRepo = new PassportRepoImpl();
    }

    public /* synthetic */ PhTicketSignInPresenter(Context context, String str, PhTicketSignInContract.View view, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, str, view, (i & 8) != 0 ? PassportUI.PHONE_SMS_AUTH_PROVIDER : str2);
    }

    @NotNull
    public final Context getContext() {
        return this.context;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final String getSid() {
        return this.sid;
    }

    @NotNull
    public final PhTicketSignInContract.View getView() {
        return this.view;
    }

    @Nullable
    public final AuthProvider getProvider() {
        return this.provider;
    }

    @NotNull
    public final PassportRepo getPassportRepo() {
        return this.passportRepo;
    }

    public final void setPassportRepo(@NotNull PassportRepo passportRepo) {
        Intrinsics.checkParameterIsNotNull(passportRepo, "<set-?>");
        this.passportRepo = passportRepo;
    }

    @Override // com.xiaomi.passport.ui.internal.PhTicketSignInContract.Presenter
    public void sendTicket(@NotNull PhoneWrapper phone, @Nullable CaptchaCode captchaCode) {
        Intrinsics.checkParameterIsNotNull(phone, "phone");
        TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_CLICK_REGAIN_SEND_TICKET);
        this.passportRepo.sendPhoneTicket(phone, captchaCode).get(new PhTicketSignInPresenter$sendTicket$1(this), new PhTicketSignInPresenter$sendTicket$2(this, phone));
    }

    @Override // com.xiaomi.passport.ui.internal.PhTicketSignInContract.Presenter
    public void signInPhoneAndTicket(@NotNull PhoneWrapper phone, @NotNull String ticket) {
        Intrinsics.checkParameterIsNotNull(phone, "phone");
        Intrinsics.checkParameterIsNotNull(ticket, "ticket");
        if (TextUtils.isEmpty(ticket)) {
            this.view.showInvalidTicket();
        } else {
            signInWithAuthCredential(createAuthCredential(phone, ticket));
        }
    }

    @Override // com.xiaomi.passport.ui.internal.PhTicketSignInContract.Presenter
    public void chooseSignUp(@NotNull PhoneSmsAuthCredential authCredential, @NotNull RegisterUserInfo userInfo) {
        Intrinsics.checkParameterIsNotNull(authCredential, "authCredential");
        Intrinsics.checkParameterIsNotNull(userInfo, "userInfo");
        signInWithAuthCredential(new ChoosePhoneSmsAuthCredential(authCredential, userInfo, false));
    }

    @Override // com.xiaomi.passport.ui.internal.PhTicketSignInContract.Presenter
    public void chooseSignUp(@NotNull ChoosePhoneSmsAuthCredential authCredential) {
        Intrinsics.checkParameterIsNotNull(authCredential, "authCredential");
        signInWithAuthCredential(authCredential);
    }

    @Override // com.xiaomi.passport.ui.internal.PhTicketSignInContract.Presenter
    public void chooseSignIn(@NotNull PhoneSmsAuthCredential authCredential, @NotNull RegisterUserInfo userInfo) {
        Intrinsics.checkParameterIsNotNull(authCredential, "authCredential");
        Intrinsics.checkParameterIsNotNull(userInfo, "userInfo");
        signInWithAuthCredential(new ChoosePhoneSmsAuthCredential(authCredential, userInfo, true));
    }

    private final void signInWithAuthCredential(PhoneSmsAuthCredential phoneSmsAuthCredential) {
        this.view.showProgress();
        TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_CLICK_NEXT_AFTER_GET_TICKET);
        AuthProvider authProvider = this.provider;
        if (authProvider == null) {
            Intrinsics.throwNpe();
        }
        authProvider.signInAndStoreAccount(this.context, phoneSmsAuthCredential).get(new PhTicketSignInPresenter$signInWithAuthCredential$1(this, phoneSmsAuthCredential), new PhTicketSignInPresenter$signInWithAuthCredential$2(this, phoneSmsAuthCredential));
    }

    @NotNull
    public final PhoneSmsAuthCredential createAuthCredential(@NotNull PhoneWrapper phone, @NotNull String ticket) {
        Intrinsics.checkParameterIsNotNull(phone, "phone");
        Intrinsics.checkParameterIsNotNull(ticket, "ticket");
        return new PhoneSmsAuthCredential(phone, ticket, this.sid);
    }

    public final void invalidateCachePhoneNum(Context context, PhoneWrapper phoneWrapper) {
        PassportRepo passportRepo = this.passportRepo;
        ActivatorPhoneInfo activateInfo = phoneWrapper.getActivateInfo();
        if (activateInfo == null) {
            Intrinsics.throwNpe();
        }
        passportRepo.invalidateCachePhoneNum(context, activateInfo.slotId);
    }
}
