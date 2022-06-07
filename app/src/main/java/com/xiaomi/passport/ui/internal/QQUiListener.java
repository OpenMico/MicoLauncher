package com.xiaomi.passport.ui.internal;

import android.app.Activity;
import android.widget.Toast;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.idm.api.IDMServer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/* compiled from: AuthSnsProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\u0012\u0010\u000e\u001a\u00020\n2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/xiaomi/passport/ui/internal/QQUiListener;", "Lcom/tencent/tauth/IUiListener;", IDMServer.PERSIST_TYPE_ACTIVITY, "Landroid/app/Activity;", "authProvider", "Lcom/xiaomi/passport/ui/internal/SNSAuthProvider;", "(Landroid/app/Activity;Lcom/xiaomi/passport/ui/internal/SNSAuthProvider;)V", "getActivity", "()Landroid/app/Activity;", "onCancel", "", "onComplete", "result", "", "onError", "p0", "Lcom/tencent/tauth/UiError;", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class QQUiListener implements IUiListener {
    @NotNull
    private final Activity activity;
    private final SNSAuthProvider authProvider;

    public QQUiListener(@NotNull Activity activity, @NotNull SNSAuthProvider authProvider) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        Intrinsics.checkParameterIsNotNull(authProvider, "authProvider");
        this.activity = activity;
        this.authProvider = authProvider;
    }

    @NotNull
    public final Activity getActivity() {
        return this.activity;
    }

    public void onComplete(@Nullable Object obj) {
        if (obj instanceof JSONObject) {
            String accessToken = ((JSONObject) obj).getString(XiaomiOAuthConstants.EXTRA_ACCESS_TOKEN_2);
            Intrinsics.checkExpressionValueIsNotNull(accessToken, "accessToken");
            this.authProvider.storeSnsToken(this.activity, accessToken);
        }
        AuthSnsProviderKt.sendSnsBroadcast(this.activity, "ok");
    }

    public void onCancel() {
        Toast.makeText(this.activity, "onCancel", 0).show();
        AuthSnsProviderKt.sendSnsBroadcast(this.activity, SNSAuthProvider.VALUE_SNS_CANCELLED);
    }

    public void onError(@Nullable UiError uiError) {
        Toast.makeText(this.activity, "onError", 0).show();
        AuthSnsProviderKt.sendSnsBroadcast(this.activity, "error");
    }
}
