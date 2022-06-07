package com.xiaomi.passport.ui.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.xiaomi.idm.api.IDMServer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AuthSnsProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J*\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lcom/xiaomi/passport/ui/internal/WeiboSSOAuthProvider;", "Lcom/xiaomi/passport/ui/internal/BaseWeiboAuthProvider;", "()V", "mSsoHandler", "Lcom/sina/weibo/sdk/auth/sso/SsoHandler;", "onActivityResult", "", IDMServer.PERSIST_TYPE_ACTIVITY, "Landroid/app/Activity;", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "startLogin", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class WeiboSSOAuthProvider extends BaseWeiboAuthProvider {
    private SsoHandler mSsoHandler;

    @Override // com.xiaomi.passport.ui.internal.BaseWeiboAuthProvider, com.xiaomi.passport.ui.internal.SNSAuthProvider
    public void onActivityResult(@NotNull Activity activity, int i, int i2, @Nullable Intent intent) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        SsoHandler ssoHandler = this.mSsoHandler;
        if (ssoHandler == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSsoHandler");
        }
        ssoHandler.authorizeCallBack(i, i2, intent);
    }

    @Override // com.xiaomi.passport.ui.internal.BaseWeiboAuthProvider, com.xiaomi.passport.ui.internal.SNSAuthProvider
    protected void startLogin(@NotNull final Activity activity) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        Context context = activity.getApplicationContext();
        Intrinsics.checkExpressionValueIsNotNull(context, "context");
        WbSdk.install(context, new AuthInfo(context, getAppId(context), getRedirectUri(context), ""));
        this.mSsoHandler = new SsoHandler(activity);
        SsoHandler ssoHandler = this.mSsoHandler;
        if (ssoHandler == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSsoHandler");
        }
        ssoHandler.authorize(new WbAuthListener() { // from class: com.xiaomi.passport.ui.internal.WeiboSSOAuthProvider$startLogin$1
            public void onSuccess(@NotNull Oauth2AccessToken token) {
                Intrinsics.checkParameterIsNotNull(token, "token");
                if (token.isSessionValid()) {
                    String token2 = token.getToken();
                    Intrinsics.checkExpressionValueIsNotNull(token2, "token.token");
                    WeiboSSOAuthProvider.this.storeSnsToken(activity, token2);
                }
                AuthSnsProviderKt.sendSnsBroadcast(activity, "ok");
            }

            public void onFailure(@Nullable WbConnectErrorMessage wbConnectErrorMessage) {
                Activity activity2 = activity;
                StringBuilder sb = new StringBuilder();
                sb.append("onFailure: ");
                String str = null;
                sb.append(wbConnectErrorMessage != null ? wbConnectErrorMessage.getErrorMessage() : null);
                sb.append(", ");
                if (wbConnectErrorMessage != null) {
                    str = wbConnectErrorMessage.getErrorCode();
                }
                sb.append(str);
                Toast.makeText(activity2, sb.toString(), 1).show();
                AuthSnsProviderKt.sendSnsBroadcast(activity, "error");
            }

            public void cancel() {
                Toast.makeText(activity, "onCancel", 1).show();
                AuthSnsProviderKt.sendSnsBroadcast(activity, SNSAuthProvider.VALUE_SNS_CANCELLED);
            }
        });
    }
}
