package com.xiaomi.passport.ui.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.tencent.tauth.Tencent;
import com.umeng.analytics.pro.c;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.passport.ui.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AuthSnsProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J*\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0014¨\u0006\u0013"}, d2 = {"Lcom/xiaomi/passport/ui/internal/QQSSOAuthProvider;", "Lcom/xiaomi/passport/ui/internal/QQAuthProvider;", "()V", "getAppId", "", c.R, "Landroid/content/Context;", "getIconRes", "", "getRequestCode", "onActivityResult", "", IDMServer.PERSIST_TYPE_ACTIVITY, "Landroid/app/Activity;", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "startLogin", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class QQSSOAuthProvider extends QQAuthProvider {
    @Override // com.xiaomi.passport.ui.internal.QQAuthProvider, com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getRequestCode() {
        return 11101;
    }

    @Override // com.xiaomi.passport.ui.internal.QQAuthProvider, com.xiaomi.passport.ui.internal.SNSAuthProvider
    public void onActivityResult(@NotNull Activity activity, int i, int i2, @Nullable Intent intent) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        Tencent.onActivityResultData(i, i2, intent, new QQUiListener(activity, this));
    }

    @Override // com.xiaomi.passport.ui.internal.QQAuthProvider, com.xiaomi.passport.ui.internal.SNSAuthProvider
    protected void startLogin(@NotNull Activity activity) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        Activity activity2 = activity;
        Tencent.createInstance(getAppId(activity2), activity2).login(activity, "", new QQUiListener(activity, this));
    }

    @Override // com.xiaomi.passport.ui.internal.QQAuthProvider, com.xiaomi.passport.ui.internal.SNSAuthProvider
    @NotNull
    public String getAppId(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        String string = context.getString(R.string.qq_application_id);
        Intrinsics.checkExpressionValueIsNotNull(string, "context.getString(R.string.qq_application_id)");
        return string;
    }

    @Override // com.xiaomi.passport.ui.internal.QQAuthProvider, com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getIconRes() {
        return R.drawable.sns_qq_logo;
    }
}
