package com.xiaomi.passport.ui.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.umeng.analytics.pro.c;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.passport.ui.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AuthSnsProvider.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u0000 %2\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001%B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0012\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0014H\u0016J*\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u00142\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u0017H\u0016J\u0012\u0010\u001f\u001a\u00020\u00172\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\u0012\u0010\"\u001a\u00020\u00172\b\u0010#\u001a\u0004\u0018\u00010\u0003H\u0016J\u0010\u0010$\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/xiaomi/passport/ui/internal/FacebookAuthProvider;", "Lcom/xiaomi/passport/ui/internal/SNSAuthProvider;", "Lcom/facebook/FacebookCallback;", "Lcom/facebook/login/LoginResult;", "()V", "EMAIL", "", "PUBLIC_PROFILE", c.R, "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "mScopes", "", "sCallbackManager", "Lcom/facebook/CallbackManager;", "getAppId", "getIconRes", "", "getRequestCode", "onActivityResult", "", IDMServer.PERSIST_TYPE_ACTIVITY, "Landroid/app/Activity;", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCancel", "onError", "error", "Lcom/facebook/FacebookException;", "onSuccess", "result", "startLogin", "Companion", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public class FacebookAuthProvider extends SNSAuthProvider implements FacebookCallback<LoginResult> {
    public static final Companion Companion = new Companion(null);
    private static final String FACEBOOK_AUTH_TAG = "FacebookAuthProvider";
    @NotNull
    public Context context;
    private CallbackManager sCallbackManager;
    private final String EMAIL = NotificationCompat.CATEGORY_EMAIL;
    private final String PUBLIC_PROFILE = "public_profile";
    private final List<String> mScopes = new ArrayList();

    public FacebookAuthProvider() {
        super(PassportUI.FACEBOOK_AUTH_PROVIDER);
    }

    /* compiled from: AuthSnsProvider.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/xiaomi/passport/ui/internal/FacebookAuthProvider$Companion;", "", "()V", "FACEBOOK_AUTH_TAG", "", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
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
        String string = context.getString(R.string.facebook_application_id);
        Intrinsics.checkExpressionValueIsNotNull(string, "context.getString(R.stri….facebook_application_id)");
        return string;
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getIconRes() {
        return R.drawable.sns_facebook_logo;
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public int getRequestCode() {
        return CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode();
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    protected void startLogin(@NotNull Activity activity) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        Context applicationContext = activity.getApplicationContext();
        Intrinsics.checkExpressionValueIsNotNull(applicationContext, "activity.applicationContext");
        this.context = applicationContext;
        this.sCallbackManager = CallbackManager.Factory.create();
        LoginManager instance = LoginManager.getInstance();
        instance.registerCallback(this.sCallbackManager, (FacebookCallback) this);
        ArrayList arrayList = new ArrayList(this.mScopes);
        if (!arrayList.contains(this.EMAIL)) {
            arrayList.add(this.EMAIL);
        }
        if (!arrayList.contains(this.PUBLIC_PROFILE)) {
            arrayList.add(this.PUBLIC_PROFILE);
        }
        instance.logInWithReadPermissions(activity, arrayList);
    }

    public void onCancel() {
        AccountLog.d(FACEBOOK_AUTH_TAG, "onCancel");
    }

    public void onError(@Nullable FacebookException facebookException) {
        Context context = this.context;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException(c.R);
        }
        Toast.makeText(context, facebookException != null ? facebookException.getMessage() : null, 0).show();
    }

    public void onSuccess(@Nullable LoginResult loginResult) {
        Context context = this.context;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException(c.R);
        }
        if (loginResult == null) {
            Intrinsics.throwNpe();
        }
        AccessToken accessToken = loginResult.getAccessToken();
        Intrinsics.checkExpressionValueIsNotNull(accessToken, "result!!.accessToken");
        String token = accessToken.getToken();
        Intrinsics.checkExpressionValueIsNotNull(token, "result!!.accessToken.token");
        storeSnsToken(context, token);
    }

    @Override // com.xiaomi.passport.ui.internal.SNSAuthProvider
    public void onActivityResult(@NotNull Activity activity, int i, int i2, @Nullable Intent intent) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        CallbackManager callbackManager = this.sCallbackManager;
        if (callbackManager != null) {
            callbackManager.onActivityResult(i, i2, intent);
        }
    }
}
