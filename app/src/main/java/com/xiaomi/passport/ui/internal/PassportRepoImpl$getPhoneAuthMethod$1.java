package com.xiaomi.passport.ui.internal;

import com.xiaomi.passport.data.LoginPreference;
import com.xiaomi.passport.uicontroller.PhoneLoginController;
import java.util.concurrent.ExecutionException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: PassportCore.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/xiaomi/passport/ui/internal/PhoneAuthMethod;", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
final class PassportRepoImpl$getPhoneAuthMethod$1 extends Lambda implements Function0<PhoneAuthMethod> {
    final /* synthetic */ PhoneWrapper $phone;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PassportRepoImpl$getPhoneAuthMethod$1(PhoneWrapper phoneWrapper) {
        super(0);
        this.$phone = phoneWrapper;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function0
    @NotNull
    public final PhoneAuthMethod invoke() {
        if (this.$phone.getActivateInfo() != null) {
            return PhoneAuthMethod.SMS;
        }
        try {
            LoginPreference loginPreference = PhoneLoginController.getPhoneLoginConfigOnLine(this.$phone.getPhone(), null, null).get();
            if (loginPreference == null) {
                throw new TypeCastException("null cannot be cast to non-null type com.xiaomi.passport.data.LoginPreference");
            } else if (loginPreference.phoneLoginType == LoginPreference.PhoneLoginType.password) {
                return PhoneAuthMethod.PSW;
            } else {
                return PhoneAuthMethod.SMS;
            }
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause == null) {
                Intrinsics.throwNpe();
            }
            throw cause;
        }
    }
}
