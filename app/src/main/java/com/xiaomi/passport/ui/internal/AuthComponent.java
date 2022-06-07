package com.xiaomi.passport.ui.internal;

import android.content.ComponentName;
import android.content.Context;
import com.umeng.analytics.pro.c;
import com.xiaomi.passport.interfaces.AuthenticatorComponentNameInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PassportCore.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016J\n\u0010\b\u001a\u0004\u0018\u00010\u0006H\u0016J\b\u0010\t\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/xiaomi/passport/ui/internal/AuthComponent;", "Lcom/xiaomi/passport/interfaces/AuthenticatorComponentNameInterface;", c.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "getAddAccountActivityComponentName", "Landroid/content/ComponentName;", "getConfirmCredentialActivityComponentName", "getNotificationActivityComponentName", "getProcessScanLoginQRCodeComponentName", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class AuthComponent implements AuthenticatorComponentNameInterface {
    private final Context context;

    public AuthComponent(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        this.context = context;
    }

    @Override // com.xiaomi.passport.interfaces.AuthenticatorComponentNameInterface
    @NotNull
    public ComponentName getAddAccountActivityComponentName() {
        return new ComponentName(this.context, AddAccountActivity.class);
    }

    @Override // com.xiaomi.passport.interfaces.AuthenticatorComponentNameInterface
    @Nullable
    public ComponentName getConfirmCredentialActivityComponentName() {
        return new ComponentName(this.context, QuickLoginActivity.class);
    }

    @Override // com.xiaomi.passport.interfaces.AuthenticatorComponentNameInterface
    @Nullable
    public ComponentName getNotificationActivityComponentName() {
        return new ComponentName(this.context, NotificationActivity.class);
    }

    @Override // com.xiaomi.passport.interfaces.AuthenticatorComponentNameInterface
    @NotNull
    public ComponentName getProcessScanLoginQRCodeComponentName() {
        return new ComponentName(this.context, LoginQRCodeScanResultActivity.class);
    }
}
