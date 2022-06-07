package com.xiaomi.passport.ui.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.pro.c;
import com.xiaomi.accountsdk.account.XMPassportUserAgent;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.PassportExternal;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.settings.AccountSettingsActivity;
import com.xiaomi.passport.ui.settings.ChangePasswordActivity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PassportUI.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u001e\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020\u00042\u0006\u0010D\u001a\u00020\u0004J\u0018\u0010E\u001a\u00020B2\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020\u0004H\u0002J\u0010\u0010E\u001a\u00020B2\u0006\u0010H\u001a\u00020*H\u0002J\u0018\u0010I\u001a\u00020#2\u0006\u0010F\u001a\u00020G2\u0006\u0010J\u001a\u00020KH\u0002J\u000e\u0010L\u001a\u00020B2\u0006\u0010F\u001a\u00020GJ\u000e\u0010M\u001a\u00020N2\u0006\u0010O\u001a\u00020\u0004J\u0006\u0010P\u001a\u00020NJ\u0010\u0010Q\u001a\u0004\u0018\u00010*2\u0006\u0010R\u001a\u00020SJ\u0010\u0010Q\u001a\u0004\u0018\u00010*2\u0006\u0010O\u001a\u00020\u0004J\u0012\u0010T\u001a\u0004\u0018\u00010K2\u0006\u0010F\u001a\u00020GH\u0002J\u000e\u0010U\u001a\u00020B2\u0006\u0010F\u001a\u00020GJ\u000e\u0010V\u001a\u00020B2\u0006\u0010H\u001a\u00020\u0004J\u000e\u0010W\u001a\u00020B2\u0006\u0010X\u001a\u00020\u0004J\u000e\u0010Y\u001a\u00020B2\u0006\u0010Z\u001a\u00020\u0004J\u000e\u0010[\u001a\u00020B2\u0006\u0010\\\u001a\u00020KJ\u0014\u0010]\u001a\u00020B2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00040;J\u000e\u0010^\u001a\u00020B2\u0006\u0010F\u001a\u00020GJ\u000e\u0010_\u001a\u00020B2\u0006\u0010F\u001a\u00020GJ)\u0010`\u001a\u00020B2\b\u0010a\u001a\u0004\u0018\u00010K2\u0006\u0010F\u001a\u00020G2\b\u0010b\u001a\u0004\u0018\u00010cH\u0002¢\u0006\u0002\u0010dR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0016\u0010\u0016\u001a\n \u0017*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020\u0004X\u0080T¢\u0006\u0002\n\u0000R\u0016\u0010 \u001a\u00020\u00048\u0006X\u0087T¢\u0006\b\n\u0000\u0012\u0004\b!\u0010\u0002R\u001a\u0010\"\u001a\u00020#X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R \u0010(\u001a\b\u0012\u0004\u0012\u00020*0)X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001a\u0010/\u001a\u000200X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u001c\u00105\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R \u0010:\u001a\b\u0012\u0004\u0012\u00020\u00040;X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010,\"\u0004\b=\u0010.R\u001c\u0010>\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u00107\"\u0004\b@\u00109¨\u0006e"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PassportUI;", "", "()V", "ACTION_LOCAL_ACCOUNT_CHANGE_EMAIL", "", "ACTION_LOCAL_ACCOUNT_CHANGE_PASSWORD", "ACTION_LOCAL_ACCOUNT_CHANGE_PHONE", "CHINA_COUNTRY_CODE", "EXTRA_DEFAULT_AUTH_PROVIDER", "EXTRA_DEFAULT_PHONE_COUNTRY_CODE", "EXTRA_LOCAL_ACCOUNT_CHANGE_EMAIL_RESULT", "EXTRA_LOCAL_ACCOUNT_CHANGE_EMAIL_RESULT_CODE", "EXTRA_LOCAL_ACCOUNT_CHANGE_PASSWORD_RESULT", "EXTRA_LOCAL_ACCOUNT_CHANGE_PASSWORD_RESULT_CODE", "EXTRA_LOCAL_ACCOUNT_CHANGE_PHONE_RESULT", "EXTRA_LOCAL_ACCOUNT_CHANGE_PHONE_RESULT_CODE", "EXTRA_SNS_SIGN_IN", PassportUI.FACEBOOK_AUTH_PROVIDER, PassportUI.GOOGLE_AUTH_PROVIDER, PassportUI.ID_PSW_AUTH_PROVIDER, PassportUI.PHONE_SMS_AUTH_PROVIDER, PassportUI.QQ_AUTH_PROVIDER, "TAG", "kotlin.jvm.PlatformType", PassportUI.WECHAT_AUTH_PROVIDER, PassportUI.WEIBO_AUTH_PROVIDER, "WXAPIEventHandler", "getWXAPIEventHandler", "()Ljava/lang/Object;", "setWXAPIEventHandler", "(Ljava/lang/Object;)V", "WX_API_STATE_PASSPORT", PassportUI.ZHIFUBAO_AUTH_PROVIDER, "ZHIFUBAO_AUTH_PROVIDER$annotations", "international", "", "getInternational", "()Z", "setInternational", "(Z)V", "mProviders", "", "Lcom/xiaomi/passport/ui/internal/AuthProvider;", "getMProviders$passportui_release", "()Ljava/util/List;", "setMProviders$passportui_release", "(Ljava/util/List;)V", "passportRepo", "Lcom/xiaomi/passport/ui/internal/PassportRepo;", "getPassportRepo", "()Lcom/xiaomi/passport/ui/internal/PassportRepo;", "setPassportRepo", "(Lcom/xiaomi/passport/ui/internal/PassportRepo;)V", "privacyPolicyUrl", "getPrivacyPolicyUrl", "()Ljava/lang/String;", "setPrivacyPolicyUrl", "(Ljava/lang/String;)V", "snsInvisibleList", "", "getSnsInvisibleList", "setSnsInvisibleList", "userAgreementUrl", "getUserAgreementUrl", "setUserAgreementUrl", "addLicense", "", "readableText", "url", "addProvider", c.R, "Landroid/content/Context;", c.M, "checkActivityIntent", "intent", "Landroid/content/Intent;", "forceStartLocalAccountSettings", "getBaseAuthProvider", "Lcom/xiaomi/passport/ui/internal/BaseAuthProvider;", "providerName", "getDefaultBaseAuthProvider", "getProvider", "authCredential", "Lcom/xiaomi/passport/ui/internal/AuthCredential;", "getSystemAccountSettingsIntent", "init", "rmProvider", "setCountryChoiceButtonText", "countryName", "setCountryChoiceButtonTextColor", "countryNameColor", "setCountryChoiceIntent", "countryChoiceIntent", "setSNSInvisibleList", "startAccountSettings", "startChangePassword", "tryStartActivityIntent", "accountSettingsIntent", "requestCode", "", "(Landroid/content/Intent;Landroid/content/Context;Ljava/lang/Integer;)V", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PassportUI {
    @NotNull
    public static final String ACTION_LOCAL_ACCOUNT_CHANGE_EMAIL = "action_local_account_change_email";
    @NotNull
    public static final String ACTION_LOCAL_ACCOUNT_CHANGE_PASSWORD = "action_local_account_change_password";
    @NotNull
    public static final String ACTION_LOCAL_ACCOUNT_CHANGE_PHONE = "action_local_account_change_Phone";
    @NotNull
    public static final String CHINA_COUNTRY_CODE = "+86";
    @NotNull
    public static final String EXTRA_DEFAULT_AUTH_PROVIDER = "default_auth_provider";
    @NotNull
    public static final String EXTRA_DEFAULT_PHONE_COUNTRY_CODE = "default_phone_country_code";
    @NotNull
    public static final String EXTRA_LOCAL_ACCOUNT_CHANGE_EMAIL_RESULT = "result";
    @NotNull
    public static final String EXTRA_LOCAL_ACCOUNT_CHANGE_EMAIL_RESULT_CODE = "result_code";
    @NotNull
    public static final String EXTRA_LOCAL_ACCOUNT_CHANGE_PASSWORD_RESULT = "result";
    @NotNull
    public static final String EXTRA_LOCAL_ACCOUNT_CHANGE_PASSWORD_RESULT_CODE = "result_code";
    @NotNull
    public static final String EXTRA_LOCAL_ACCOUNT_CHANGE_PHONE_RESULT = "result";
    @NotNull
    public static final String EXTRA_LOCAL_ACCOUNT_CHANGE_PHONE_RESULT_CODE = "result_code";
    @NotNull
    public static final String EXTRA_SNS_SIGN_IN = "sns_sign_in";
    @NotNull
    public static final String FACEBOOK_AUTH_PROVIDER = "FACEBOOK_AUTH_PROVIDER";
    @NotNull
    public static final String GOOGLE_AUTH_PROVIDER = "GOOGLE_AUTH_PROVIDER";
    @NotNull
    public static final String ID_PSW_AUTH_PROVIDER = "ID_PSW_AUTH_PROVIDER";
    @NotNull
    public static final String PHONE_SMS_AUTH_PROVIDER = "PHONE_SMS_AUTH_PROVIDER";
    @NotNull
    public static final String QQ_AUTH_PROVIDER = "QQ_AUTH_PROVIDER";
    @NotNull
    public static final String WECHAT_AUTH_PROVIDER = "WECHAT_AUTH_PROVIDER";
    @NotNull
    public static final String WEIBO_AUTH_PROVIDER = "WEIBO_AUTH_PROVIDER";
    @Nullable
    private static Object WXAPIEventHandler = null;
    @NotNull
    public static final String WX_API_STATE_PASSPORT = "wx_api_passport";
    @NotNull
    public static final String ZHIFUBAO_AUTH_PROVIDER = "ZHIFUBAO_AUTH_PROVIDER";
    private static boolean international;
    @Nullable
    private static String privacyPolicyUrl;
    @Nullable
    private static String userAgreementUrl;
    public static final PassportUI INSTANCE = new PassportUI();
    private static final String TAG = PassportUI.class.getSimpleName();
    @NotNull
    private static List<AuthProvider> mProviders = new ArrayList();
    @NotNull
    private static List<String> snsInvisibleList = new ArrayList();
    @NotNull
    private static PassportRepo passportRepo = new PassportRepoImpl();

    public static /* synthetic */ void ZHIFUBAO_AUTH_PROVIDER$annotations() {
    }

    static {
        XMPassportUserAgent.addExtendedUserAgent("PassportSDK/1.8.4-SNAPSHOT");
        XMPassportUserAgent.addExtendedUserAgent("passport-ui/1.8.4-SNAPSHOT");
        mProviders.add(new IdPswAuthProvider());
        mProviders.add(new PhoneSmsAuthProvider());
    }

    private PassportUI() {
    }

    @NotNull
    public final List<AuthProvider> getMProviders$passportui_release() {
        return mProviders;
    }

    public final void setMProviders$passportui_release(@NotNull List<AuthProvider> list) {
        Intrinsics.checkParameterIsNotNull(list, "<set-?>");
        mProviders = list;
    }

    @NotNull
    public final List<String> getSnsInvisibleList() {
        return snsInvisibleList;
    }

    public final void setSnsInvisibleList(@NotNull List<String> list) {
        Intrinsics.checkParameterIsNotNull(list, "<set-?>");
        snsInvisibleList = list;
    }

    @Nullable
    public final String getUserAgreementUrl() {
        return userAgreementUrl;
    }

    public final void setUserAgreementUrl(@Nullable String str) {
        userAgreementUrl = str;
    }

    @Nullable
    public final String getPrivacyPolicyUrl() {
        return privacyPolicyUrl;
    }

    public final void setPrivacyPolicyUrl(@Nullable String str) {
        privacyPolicyUrl = str;
    }

    @NotNull
    public final PassportRepo getPassportRepo() {
        return passportRepo;
    }

    public final void setPassportRepo(@NotNull PassportRepo passportRepo2) {
        Intrinsics.checkParameterIsNotNull(passportRepo2, "<set-?>");
        passportRepo = passportRepo2;
    }

    public final boolean getInternational() {
        return international;
    }

    public final void setInternational(boolean z) {
        international = z;
    }

    public final void init(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        PassportExternal.setAuthenticatorComponentNameInterface(new AuthComponent(context));
        addProvider(context, FACEBOOK_AUTH_PROVIDER);
        addProvider(context, GOOGLE_AUTH_PROVIDER);
        addProvider(context, WEIBO_AUTH_PROVIDER);
        addProvider(context, QQ_AUTH_PROVIDER);
        addProvider(context, WECHAT_AUTH_PROVIDER);
    }

    private final void addProvider(Context context, String str) {
        boolean z = true;
        if (Intrinsics.areEqual(str, WEIBO_AUTH_PROVIDER)) {
            String string = context.getString(R.string.weibo_application_id);
            Intrinsics.checkExpressionValueIsNotNull(string, "context.getString(R.string.weibo_application_id)");
            if (string != null) {
                if (!(StringsKt.trim(string).toString().length() == 0)) {
                    addProvider(new WeiboSSOAuthProvider());
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
        }
        if (Intrinsics.areEqual(str, QQ_AUTH_PROVIDER)) {
            String string2 = context.getString(R.string.qq_application_id);
            Intrinsics.checkExpressionValueIsNotNull(string2, "context.getString(R.string.qq_application_id)");
            if (string2 != null) {
                if (!(StringsKt.trim(string2).toString().length() == 0)) {
                    addProvider(new QQSSOAuthProvider());
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
        }
        if (Intrinsics.areEqual(str, WECHAT_AUTH_PROVIDER)) {
            String string3 = context.getString(R.string.wechat_application_id);
            Intrinsics.checkExpressionValueIsNotNull(string3, "context.getString(R.string.wechat_application_id)");
            if (string3 != null) {
                if (!(StringsKt.trim(string3).toString().length() == 0)) {
                    try {
                        AccountLog.v(TAG, WXAPIFactory.class.getName());
                        addProvider(new WeChatAuthProvider());
                    } catch (NoClassDefFoundError unused) {
                        new RuntimeException("WE_CHAT provider cannot be configured without dependency. Did you forget to add 'com.tencent.mm.opensdk:wechat-sdk-android-without-mta:+' dependency?").printStackTrace();
                    }
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
        }
        if (Intrinsics.areEqual(str, FACEBOOK_AUTH_PROVIDER)) {
            String string4 = context.getString(R.string.facebook_application_id);
            Intrinsics.checkExpressionValueIsNotNull(string4, "context.getString(R.stri….facebook_application_id)");
            if (string4 != null) {
                if (!(StringsKt.trim(string4).toString().length() == 0)) {
                    try {
                        AccountLog.v(TAG, FacebookSdk.class.getName());
                        addProvider(new FacebookAuthProvider());
                    } catch (NoClassDefFoundError unused2) {
                        new RuntimeException("FaceBook provider cannot be configured without dependency. Did you forget to add com.facebook.android:facebook-login:+ dependency?").printStackTrace();
                    }
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
        }
        if (Intrinsics.areEqual(str, GOOGLE_AUTH_PROVIDER)) {
            String string5 = context.getString(R.string.google_application_id);
            Intrinsics.checkExpressionValueIsNotNull(string5, "context.getString(R.string.google_application_id)");
            if (string5 != null) {
                if (StringsKt.trim(string5).toString().length() != 0) {
                    z = false;
                }
                if (!z) {
                    try {
                        AccountLog.v(TAG, GoogleSignInClient.class.getName());
                        addProvider(new GoogleAuthProvider());
                    } catch (NoClassDefFoundError unused3) {
                        new RuntimeException("Google provider cannot be configured without dependency. Did you forget to add \"com.google.android.gms:play-services-auth:16.0.1\" dependency?").printStackTrace();
                    }
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
        }
    }

    public final void rmProvider(@NotNull String provider) {
        Intrinsics.checkParameterIsNotNull(provider, "provider");
        if (CollectionsKt.contains(mProviders, getProvider(provider))) {
            List<AuthProvider> list = mProviders;
            AuthProvider provider2 = getProvider(provider);
            if (list != null) {
                TypeIntrinsics.asMutableCollection(list).remove(provider2);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
        }
    }

    private final void addProvider(AuthProvider authProvider) {
        rmProvider(authProvider.getName());
        mProviders.add(authProvider);
    }

    @NotNull
    public final BaseAuthProvider getDefaultBaseAuthProvider() {
        if (international) {
            AuthProvider provider = getProvider(ID_PSW_AUTH_PROVIDER);
            if (provider != null) {
                return (BaseAuthProvider) provider;
            }
            throw new TypeCastException("null cannot be cast to non-null type com.xiaomi.passport.ui.internal.BaseAuthProvider");
        }
        AuthProvider provider2 = getProvider(PHONE_SMS_AUTH_PROVIDER);
        if (provider2 != null) {
            return (BaseAuthProvider) provider2;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.xiaomi.passport.ui.internal.BaseAuthProvider");
    }

    @Nullable
    public final AuthProvider getProvider(@NotNull String providerName) {
        Intrinsics.checkParameterIsNotNull(providerName, "providerName");
        ArrayList arrayList = new ArrayList();
        for (Object obj : mProviders) {
            if (Intrinsics.areEqual(((AuthProvider) obj).getName(), providerName)) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        if (it.hasNext()) {
            return (AuthProvider) it.next();
        }
        return null;
    }

    @NotNull
    public final BaseAuthProvider getBaseAuthProvider(@NotNull String providerName) {
        Intrinsics.checkParameterIsNotNull(providerName, "providerName");
        ArrayList arrayList = new ArrayList();
        for (Object obj : mProviders) {
            if (Intrinsics.areEqual(((AuthProvider) obj).getName(), providerName)) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        if (it.hasNext()) {
            AuthProvider authProvider = (AuthProvider) it.next();
            if (authProvider != null) {
                return (BaseAuthProvider) authProvider;
            }
            throw new TypeCastException("null cannot be cast to non-null type com.xiaomi.passport.ui.internal.BaseAuthProvider");
        }
        throw new RuntimeException("FaceBook provider cannot be configured ");
    }

    @Nullable
    public final AuthProvider getProvider(@NotNull AuthCredential authCredential) {
        Intrinsics.checkParameterIsNotNull(authCredential, "authCredential");
        return getProvider(authCredential.getProvider());
    }

    public final void addLicense(@NotNull String readableText, @NotNull String url) {
        Intrinsics.checkParameterIsNotNull(readableText, "readableText");
        Intrinsics.checkParameterIsNotNull(url, "url");
        UserLicenseUtils.Companion.addLicense(readableText, url);
    }

    public final void setCountryChoiceButtonText(@NotNull String countryName) {
        Intrinsics.checkParameterIsNotNull(countryName, "countryName");
        AddAccountActivity.Companion.setCountryChoiceButtonText(countryName);
    }

    public final void setCountryChoiceButtonTextColor(@NotNull String countryNameColor) {
        Intrinsics.checkParameterIsNotNull(countryNameColor, "countryNameColor");
        AddAccountActivity.Companion.setCountryChoiceButtonTextColor(countryNameColor);
    }

    public final void setCountryChoiceIntent(@NotNull Intent countryChoiceIntent) {
        Intrinsics.checkParameterIsNotNull(countryChoiceIntent, "countryChoiceIntent");
        AddAccountActivity.Companion.setCountryChoiceIntent(countryChoiceIntent);
    }

    public final void setSNSInvisibleList(@NotNull List<String> snsInvisibleList2) {
        Intrinsics.checkParameterIsNotNull(snsInvisibleList2, "snsInvisibleList");
        snsInvisibleList = snsInvisibleList2;
    }

    @Nullable
    public final Object getWXAPIEventHandler() {
        return WXAPIEventHandler;
    }

    public final void setWXAPIEventHandler(@Nullable Object obj) {
        WXAPIEventHandler = obj;
    }

    public final void startChangePassword(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        MiAccountManager miAccountManager = MiAccountManager.get(context);
        if (miAccountManager == null || miAccountManager.getXiaomiAccount() == null) {
            Toast.makeText(context, R.string.no_account, 0).show();
        } else if (miAccountManager.isUseSystem()) {
            tryStartActivityIntent(getSystemAccountSettingsIntent(context), context, null);
        } else if (miAccountManager.isUseLocal()) {
            tryStartActivityIntent(ChangePasswordActivity.newIntent(context), context, null);
        }
    }

    public final void startAccountSettings(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        MiAccountManager miAccountManager = MiAccountManager.get(context);
        if (miAccountManager == null || miAccountManager.getXiaomiAccount() == null) {
            Toast.makeText(context, R.string.no_account, 0).show();
            return;
        }
        Intent intent = null;
        if (miAccountManager.isUseSystem()) {
            intent = getSystemAccountSettingsIntent(context);
        } else if (miAccountManager.isUseLocal()) {
            intent = new Intent(context, AccountSettingsActivity.class);
        }
        tryStartActivityIntent(intent, context, null);
    }

    public final void forceStartLocalAccountSettings(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        MiAccountManager miAccountManager = MiAccountManager.get(context);
        if (miAccountManager == null || miAccountManager.getXiaomiAccount() == null) {
            Toast.makeText(context, R.string.no_account, 0).show();
        } else {
            tryStartActivityIntent(new Intent(context, AccountSettingsActivity.class), context, null);
        }
    }

    private final void tryStartActivityIntent(Intent intent, Context context, Integer num) {
        if (intent != null) {
            try {
                intent.addFlags(268435456);
                if (num == null || !(context instanceof Activity)) {
                    context.startActivity(intent);
                } else {
                    ((Activity) context).startActivityForResult(intent, num.intValue());
                }
            } catch (ActivityNotFoundException e) {
                AccountLog.e(TAG, "launch account settings failed: ", e);
            }
        }
    }

    private final Intent getSystemAccountSettingsIntent(Context context) {
        Intent intent = new Intent(Constants.ACTION_XIAOMI_ACCOUNT_USER_INFO_DETAIL);
        if (checkActivityIntent(context, intent)) {
            return intent;
        }
        Intent intent2 = new Intent(Constants.ACTION_VIEW_XIAOMI_ACCOUNT);
        if (checkActivityIntent(context, intent2)) {
            return intent2;
        }
        return null;
    }

    private final boolean checkActivityIntent(Context context, Intent intent) {
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        Intrinsics.checkExpressionValueIsNotNull(queryIntentActivities, "context.packageManager.q…tentActivities(intent, 0)");
        return !queryIntentActivities.isEmpty();
    }
}
