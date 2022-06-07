package com.xiaomi.passport.ui.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.umeng.analytics.pro.c;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.passport.ui.R;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AuthSnsProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\fH\u0016J*\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u0018"}, d2 = {"Lcom/xiaomi/passport/ui/internal/GoogleAuthProvider;", "Lcom/xiaomi/passport/ui/internal/SNSAuthProvider;", "()V", c.R, "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "getAppId", "", "getIconRes", "", "getRequestCode", "onActivityResult", "", IDMServer.PERSIST_TYPE_ACTIVITY, "Landroid/app/Activity;", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "startLogin", "Companion", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public class GoogleAuthProvider extends SNSAuthProvider {
    public static final Companion Companion = new Companion(null);
    private static final String GOOGLE_AUTH_TAG = "GoogleAuthProvider";
    private static final int REQUEST_CODE_GOOGLE = 32;
    @NotNull
    public Context context;

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getRequestCode() {
        return 32;
    }

    /* compiled from: AuthSnsProvider.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lcom/xiaomi/passport/ui/internal/GoogleAuthProvider$Companion;", "", "()V", "GOOGLE_AUTH_TAG", "", "REQUEST_CODE_GOOGLE", "", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public GoogleAuthProvider() {
        super(PassportUI.GOOGLE_AUTH_PROVIDER);
    }

    @NotNull
    public final Context getContext() {
        Context context = this.context;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException(c.R);
        }
        return context;
    }

    public final void setContext(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "<set-?>");
        this.context = context;
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    @NotNull
    public String getAppId(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        String string = context.getString(R.string.google_application_id);
        Intrinsics.checkExpressionValueIsNotNull(string, "context.getString(R.string.google_application_id)");
        return string;
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getIconRes() {
        return R.drawable.sns_google_logo;
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    protected void startLogin(@NotNull Activity activity) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(activity, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestServerAuthCode(getAppId(activity)).requestEmail().build());
        Intrinsics.checkExpressionValueIsNotNull(googleSignInClient, "googleSignInClient");
        activity.startActivityForResult(googleSignInClient.getSignInIntent(), 32);
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public void onActivityResult(@NotNull Activity activity, int i, int i2, @Nullable Intent intent) {
        String serverAuthCode;
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        if (i == 32) {
            try {
                GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) GoogleSignIn.getSignedInAccountFromIntent(intent).getResult(ApiException.class);
                if (googleSignInAccount != null && (serverAuthCode = googleSignInAccount.getServerAuthCode()) != null) {
                    storeSnsCode(activity, serverAuthCode);
                }
            } catch (ApiException e) {
                AccountLog.w(GOOGLE_AUTH_TAG, "Google sign in failed", e);
            }
        }
    }
}
