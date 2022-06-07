package com.xiaomi.passport.ui.internal;

import android.content.Context;
import com.umeng.analytics.pro.c;
import com.xiaomi.accountsdk.account.data.ActivatorPhoneInfo;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.StatConstants;
import com.xiaomi.passport.ui.TrackEventManager;
import com.xiaomi.passport.ui.internal.PhAuthContract;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FragmentGetPhAuthMethod.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B)\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\u0016\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0005J\u0012\u0010#\u001a\u00020$2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\u0018\u0010%\u001a\u00020$2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010 \u001a\u00020!H\u0002J\u001a\u0010&\u001a\u00020$2\u0006\u0010 \u001a\u00020!2\b\u0010'\u001a\u0004\u0018\u00010(H\u0016R\u0016\u0010\n\u001a\u00020\u00058\u0002X\u0083D¢\u0006\b\n\u0000\u0012\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006)"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PhAuthPresenter;", "Lcom/xiaomi/passport/ui/internal/PhAuthContract$Presenter;", c.R, "Landroid/content/Context;", "sid", "", OneTrack.Event.VIEW, "Lcom/xiaomi/passport/ui/internal/PhAuthContract$View;", "name", "(Landroid/content/Context;Ljava/lang/String;Lcom/xiaomi/passport/ui/internal/PhAuthContract$View;Ljava/lang/String;)V", "TAG", "TAG$annotations", "()V", "getContext", "()Landroid/content/Context;", "getName", "()Ljava/lang/String;", "passportRepo", "Lcom/xiaomi/passport/ui/internal/PassportRepo;", "getPassportRepo", "()Lcom/xiaomi/passport/ui/internal/PassportRepo;", "setPassportRepo", "(Lcom/xiaomi/passport/ui/internal/PassportRepo;)V", c.M, "Lcom/xiaomi/passport/ui/internal/AuthProvider;", "getProvider", "()Lcom/xiaomi/passport/ui/internal/AuthProvider;", "getSid", "getView", "()Lcom/xiaomi/passport/ui/internal/PhAuthContract$View;", "createAuthCredential", "Lcom/xiaomi/passport/ui/internal/PhoneSmsAuthCredential;", "phone", "Lcom/xiaomi/passport/ui/internal/PhoneWrapper;", "ticket", "getPhoneAuthMethod", "", "invalidateCathePhoneNum", "sendTicket", "captchaCode", "Lcom/xiaomi/passport/ui/internal/CaptchaCode;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PhAuthPresenter implements PhAuthContract.Presenter {
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
    private final PhAuthContract.View view;

    private static /* synthetic */ void TAG$annotations() {
    }

    public PhAuthPresenter(@NotNull Context context, @NotNull String sid, @NotNull PhAuthContract.View view, @NotNull String name) {
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

    public /* synthetic */ PhAuthPresenter(Context context, String str, PhAuthContract.View view, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
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
    public final PhAuthContract.View getView() {
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

    @Override // com.xiaomi.passport.ui.internal.PhAuthContract.Presenter
    public void getPhoneAuthMethod(@Nullable PhoneWrapper phoneWrapper) {
        if (phoneWrapper == null) {
            this.view.showPhoneNumError(R.string.passport_error_phone_error);
            return;
        }
        this.view.showProgress();
        TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_GET_PHONE_AUTHMETHOD);
        this.passportRepo.getPhoneAuthMethod(phoneWrapper).get(new PhAuthPresenter$getPhoneAuthMethod$1(this, phoneWrapper), new PhAuthPresenter$getPhoneAuthMethod$2(this));
    }

    @Override // com.xiaomi.passport.ui.internal.PhAuthContract.Presenter
    public void sendTicket(@NotNull PhoneWrapper phone, @Nullable CaptchaCode captchaCode) {
        Intrinsics.checkParameterIsNotNull(phone, "phone");
        this.view.showProgress();
        TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_SEND_TICKET);
        this.passportRepo.sendPhoneTicket(phone, captchaCode).get(new PhAuthPresenter$sendTicket$1(this, phone), new PhAuthPresenter$sendTicket$2(this, phone));
    }

    @NotNull
    public final PhoneSmsAuthCredential createAuthCredential(@NotNull PhoneWrapper phone, @NotNull String ticket) {
        Intrinsics.checkParameterIsNotNull(phone, "phone");
        Intrinsics.checkParameterIsNotNull(ticket, "ticket");
        return new PhoneSmsAuthCredential(phone, ticket, this.sid);
    }

    public final void invalidateCathePhoneNum(Context context, PhoneWrapper phoneWrapper) {
        PassportRepo passportRepo = this.passportRepo;
        ActivatorPhoneInfo activateInfo = phoneWrapper.getActivateInfo();
        if (activateInfo == null) {
            Intrinsics.throwNpe();
        }
        passportRepo.invalidateCachePhoneNum(context, activateInfo.slotId);
    }
}
