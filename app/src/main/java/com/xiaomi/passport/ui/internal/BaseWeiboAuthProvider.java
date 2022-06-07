package com.xiaomi.passport.ui.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.umeng.analytics.pro.c;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.passport.ui.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AuthSnsProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0004J\b\u0010\n\u001a\u00020\bH\u0016J*\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0014¨\u0006\u0014"}, d2 = {"Lcom/xiaomi/passport/ui/internal/BaseWeiboAuthProvider;", "Lcom/xiaomi/passport/ui/internal/SNSAuthProvider;", "()V", "getAppId", "", c.R, "Landroid/content/Context;", "getIconRes", "", "getRedirectUri", "getRequestCode", "onActivityResult", "", IDMServer.PERSIST_TYPE_ACTIVITY, "Landroid/app/Activity;", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "startLogin", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public class BaseWeiboAuthProvider extends SNSAuthProvider {
    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getRequestCode() {
        return 32973;
    }

    public BaseWeiboAuthProvider() {
        super(PassportUI.WEIBO_AUTH_PROVIDER);
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getIconRes() {
        return R.drawable.sns_weibo_logo;
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    @NotNull
    public String getAppId(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        String string = context.getString(R.string.weibo_application_id);
        Intrinsics.checkExpressionValueIsNotNull(string, "context.getString(R.string.weibo_application_id)");
        return string;
    }

    @NotNull
    protected final String getRedirectUri(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        String string = context.getString(R.string.weibo_redirect_uri);
        Intrinsics.checkExpressionValueIsNotNull(string, "context.getString(R.string.weibo_redirect_uri)");
        return string;
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    protected void startLogin(@NotNull Activity activity) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        Activity activity2 = activity;
        Intent intent = new Intent(activity2, SnsAuthActivity.class);
        intent.putExtra("url", "https://api.weibo.com/oauth2/authorize?response_type=code&redirect_uri=" + getRedirectUri(activity2) + "&client_id=" + getAppId(activity2));
        activity.startActivityForResult(intent, getRequestCode());
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public void onActivityResult(@NotNull Activity activity, int i, int i2, @Nullable Intent intent) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        if (i == getRequestCode() && i2 == -1) {
            if (intent == null) {
                Intrinsics.throwNpe();
            }
            String code = intent.getStringExtra("code");
            Intrinsics.checkExpressionValueIsNotNull(code, "code");
            storeSnsCode(activity, code);
        }
    }
}
