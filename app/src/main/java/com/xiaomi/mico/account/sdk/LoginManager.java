package com.xiaomi.mico.account.sdk;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.mico.settingslib.lock.LockSetting;
import com.xiaomi.micolauncher.common.L;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class LoginManager {
    public static final String KEY_C_USER_ID = "encrypted_user_id";
    public static final String KEY_HAS_PASSWORD = "has_password";
    public static final String KEY_PH_SUFFIX = "_ph";
    public static final String KEY_SLH_SUFFIX = "_slh";
    public static final String MICO_PRODUCTION_SID = "micoapi";
    public static final String MICO_SID = "micoapi";
    public static final String MICO_STAGING_SID = "mico_staging";
    public static final String WEB_LOGIN_PREFIX = "weblogin:";
    public static final String XIAOMI_ACCOUNT_TYPE = "com.xiaomi";
    private static volatile LoginManager a;
    private Context b;
    private AccountManager c;
    private HashMap<String, ServiceToken> d = new HashMap<>();
    private static final String[] e = {"com.xiaomi.mico.accounttest", "com.xiaomi.mico.provision"};
    private static final String[] f = {"com.xiaomi.mico.accounttest", LockSetting.PACKAGE_SETTING};
    public static final String[] PACKAGES_GET_ACCOUNT_WHEN_SIGN_NOT_MATCH = {"com.xiaomi.mico.accounttest"};

    private LoginManager(@NonNull Context context) {
        this.b = context.getApplicationContext();
        this.c = AccountManager.get(context);
    }

    public static void init(@NonNull Context context) {
        if (a == null) {
            synchronized (LoginManager.class) {
                if (a == null) {
                    a = new LoginManager(context);
                }
            }
        }
    }

    public static LoginManager get() {
        if (a != null) {
            return a;
        }
        throw new IllegalStateException("call LoginManager.init first");
    }

    @Nullable
    public String getUserId() {
        Account a2 = a();
        if (a2 != null) {
            return a2.name;
        }
        L.login.e("getUserId No valid account found");
        return null;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.accounts.Account, java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.StringBuilder, java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.accounts.AccountManager, java.lang.StringBuilder] */
    /* JADX WARN: Unknown variable types count: 1 */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getCUserId() {
        /*
            r4 = this;
            android.accounts.Account r0 = r4.a()
            r1 = 0
            if (r0 != 0) goto L_0x000f
            com.elvishew.xlog.Logger r0 = com.xiaomi.micolauncher.common.L.login
            java.lang.String r2 = "getCUserId No valid account found"
            r0.e(r2)
            return r1
        L_0x000f:
            android.accounts.AccountManager r2 = r4.c     // Catch: SecurityException -> 0x0018
            java.lang.String r3 = "encrypted_user_id"
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch: SecurityException -> 0x0018
            return r0
        L_0x0018:
            com.elvishew.xlog.Logger r0 = com.xiaomi.micolauncher.common.L.login
            java.lang.String r2 = "No permission to call getCUserId when signature mismatch"
            r0.e(r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mico.account.sdk.LoginManager.getCUserId():java.lang.String");
    }

    public boolean hasValidAccount() {
        return a() != null;
    }

    @Nullable
    private Account a() {
        Account[] accountsByType = this.c.getAccountsByType("com.xiaomi");
        if (accountsByType.length > 0) {
            return accountsByType[0];
        }
        return null;
    }

    public void addAccount(@NonNull Activity activity, @NonNull String str) {
        this.c.addAccount("com.xiaomi", str, null, null, activity, null, null);
    }

    public void addAccountExplicitly(@NonNull String str, @NonNull String str2, @NonNull String str3) {
        if (!Arrays.asList(e).contains(this.b.getPackageName())) {
            L.login.e("no permission to add account explicitly");
            return;
        }
        Account account = new Account(str, "com.xiaomi");
        ExtendedAuthToken build = ExtendedAuthToken.build(str2, str3);
        HashMap hashMap = new HashMap();
        for (String str4 : PACKAGES_GET_ACCOUNT_WHEN_SIGN_NOT_MATCH) {
            hashMap.put(str4, 1);
        }
        try {
            this.c.addAccountExplicitly(account, build.toPlain(), null, hashMap);
        } catch (SecurityException unused) {
            L.login.e("No permission to call addAccountExplicitly when signature mismatch");
        }
    }

    public void removeAccount(@NonNull Activity activity) {
        if (!Arrays.asList(f).contains(this.b.getPackageName())) {
            L.login.e("no permission to remove account");
            return;
        }
        Account a2 = a();
        if (a2 != null) {
            try {
                this.c.removeAccount(a2, activity, new AccountManagerCallback() { // from class: com.xiaomi.mico.account.sdk.-$$Lambda$LoginManager$LenGxKBdgzEOwR8rOGdEAwKBWXk
                    @Override // android.accounts.AccountManagerCallback
                    public final void run(AccountManagerFuture accountManagerFuture) {
                        LoginManager.this.a(accountManagerFuture);
                    }
                }, null);
            } catch (SecurityException unused) {
                L.login.e("No permission to call removeAccount when signature mismatch");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AccountManagerFuture accountManagerFuture) {
        try {
            if (((Bundle) accountManagerFuture.getResult()).getBoolean("booleanResult")) {
                this.d.clear();
                L.login.i("remove account success");
            } else {
                L.login.e("remove account failed");
            }
        } catch (AuthenticatorException | OperationCanceledException | IOException e2) {
            L.login.e("remove account failed", e2);
        }
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [android.accounts.AccountManager, java.lang.StringBuilder] */
    @Nullable
    public ServiceToken peekServiceToken(@NonNull String str) {
        Account a2 = a();
        if (a2 == null) {
            L.login.e("peekServiceToken No valid account found");
            return null;
        }
        ServiceToken serviceToken = this.d.get(str);
        if (serviceToken != null) {
            return serviceToken;
        }
        try {
            return a(a2, str, this.c.toString());
        } catch (SecurityException unused) {
            L.login.d("cannot peek service token from account manager, sign not match");
            return null;
        }
    }

    @Nullable
    public ServiceToken blockGetServiceToken(@NonNull Activity activity, @NonNull String str) {
        try {
            return a(activity, str);
        } catch (NeedUserInteractionException unused) {
            return null;
        }
    }

    @Nullable
    public ServiceToken blockGetServiceToken(@NonNull String str) throws NeedUserInteractionException {
        return a(null, str);
    }

    /* JADX WARN: Type inference failed for: r10v12, types: [void, android.accounts.AccountManagerFuture] */
    /* JADX WARN: Unknown variable types count: 1 */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.xiaomi.mico.account.sdk.ServiceToken a(@androidx.annotation.Nullable android.app.Activity r10, @androidx.annotation.NonNull java.lang.String r11) throws com.xiaomi.mico.account.sdk.LoginManager.NeedUserInteractionException {
        /*
            r9 = this;
            com.elvishew.xlog.Logger r0 = com.xiaomi.micolauncher.common.L.login
            java.lang.String r1 = "LoginManager internalBlockGetServiceToken sid=%s"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            r2[r3] = r11
            r0.d(r1, r2)
            android.os.Looper r0 = android.os.Looper.myLooper()
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            if (r0 != r1) goto L_0x001f
            com.elvishew.xlog.Logger r0 = com.xiaomi.micolauncher.common.L.login
            java.lang.String r1 = "！！！ getServiceToken is running in main thread"
            r0.w(r1)
        L_0x001f:
            android.accounts.Account r0 = r9.a()
            r1 = 0
            if (r0 != 0) goto L_0x002e
            com.elvishew.xlog.Logger r10 = com.xiaomi.micolauncher.common.L.login
            java.lang.String r11 = "LoginManager internalBlockGetServiceToken No valid account found"
            r10.w(r11)
            return r1
        L_0x002e:
            if (r10 != 0) goto L_0x0043
            android.accounts.AccountManager r2 = r9.c     // Catch: OperationCanceledException -> 0x00b1, IOException -> 0x00af, AuthenticatorException -> 0x00ad
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r3 = r0
            r4 = r11
            android.accounts.AccountManagerFuture r10 = r2.getAuthToken(r3, r4, r5, r6, r7, r8)     // Catch: OperationCanceledException -> 0x00b1, IOException -> 0x00af, AuthenticatorException -> 0x00ad
            java.lang.Object r10 = r10.getResult()     // Catch: OperationCanceledException -> 0x00b1, IOException -> 0x00af, AuthenticatorException -> 0x00ad
            android.os.Bundle r10 = (android.os.Bundle) r10     // Catch: OperationCanceledException -> 0x00b1, IOException -> 0x00af, AuthenticatorException -> 0x00ad
            goto L_0x0055
        L_0x0043:
            android.accounts.AccountManager r2 = r9.c     // Catch: OperationCanceledException -> 0x00b1, IOException -> 0x00af, AuthenticatorException -> 0x00ad
            r5 = 0
            r7 = 0
            r8 = 0
            r3 = r0
            r4 = r11
            r6 = r10
            void r10 = r2.<init>()     // Catch: OperationCanceledException -> 0x00b1, IOException -> 0x00af, AuthenticatorException -> 0x00ad
            java.lang.Object r10 = r10.getResult()     // Catch: OperationCanceledException -> 0x00b1, IOException -> 0x00af, AuthenticatorException -> 0x00ad
            android.os.Bundle r10 = (android.os.Bundle) r10     // Catch: OperationCanceledException -> 0x00b1, IOException -> 0x00af, AuthenticatorException -> 0x00ad
        L_0x0055:
            if (r10 != 0) goto L_0x005f
            com.elvishew.xlog.Logger r10 = com.xiaomi.micolauncher.common.L.login
            java.lang.String r11 = "error returned while getting auth token"
            r10.e(r11)
            return r1
        L_0x005f:
            java.lang.String r2 = "authtoken"
            boolean r2 = r10.containsKey(r2)
            if (r2 == 0) goto L_0x0072
            java.lang.String r1 = "authtoken"
            java.lang.String r10 = r10.getString(r1)
            com.xiaomi.mico.account.sdk.ServiceToken r10 = r9.a(r0, r11, r10)
            return r10
        L_0x0072:
            java.lang.String r11 = "intent"
            boolean r11 = r10.containsKey(r11)
            if (r11 != 0) goto L_0x009f
            java.lang.String r11 = "errorMessage"
            java.lang.String r10 = r10.getString(r11)
            boolean r11 = android.text.TextUtils.isEmpty(r10)
            if (r11 == 0) goto L_0x0088
            java.lang.String r10 = "UNKNOWN ERROR"
        L_0x0088:
            com.elvishew.xlog.Logger r11 = com.xiaomi.micolauncher.common.L.login
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "error returned while getting auth token: "
            r0.append(r2)
            r0.append(r10)
            java.lang.String r10 = r0.toString()
            r11.e(r10)
            return r1
        L_0x009f:
            java.lang.String r11 = "intent"
            android.os.Parcelable r10 = r10.getParcelable(r11)
            android.content.Intent r10 = (android.content.Intent) r10
            com.xiaomi.mico.account.sdk.LoginManager$NeedUserInteractionException r11 = new com.xiaomi.mico.account.sdk.LoginManager$NeedUserInteractionException
            r11.<init>(r10)
            throw r11
        L_0x00ad:
            r10 = move-exception
            goto L_0x00b2
        L_0x00af:
            r10 = move-exception
            goto L_0x00b2
        L_0x00b1:
            r10 = move-exception
        L_0x00b2:
            com.elvishew.xlog.Logger r11 = com.xiaomi.micolauncher.common.L.login
            java.lang.String r0 = "error returned while getting auth token"
            r11.e(r0, r10)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mico.account.sdk.LoginManager.a(android.app.Activity, java.lang.String):com.xiaomi.mico.account.sdk.ServiceToken");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [android.accounts.AccountManager, java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.StringBuilder, java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v4, types: [android.accounts.AccountManager, java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.lang.StringBuilder, java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v5, types: [android.accounts.AccountManager, java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r9v4, types: [java.lang.StringBuilder, java.lang.String] */
    private ServiceToken a(Account account, String str, String str2) {
        L.login.d("%s buildAndCacheServiceToken sid=%s authToken=%s", "LoginManager ", str, str2);
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        ServiceToken a2 = ServiceToken.a(str, str2);
        if (a2 == null) {
            L.login.e("error while parsing auth token");
            return null;
        }
        try {
            String str3 = str + KEY_SLH_SUFFIX;
            String str4 = str + KEY_PH_SUFFIX;
            a2.a(this.c.append(account), this.c.append(account), this.c.append(account));
        } catch (SecurityException unused) {
            L.login.d("No permission to get cUserId/slh/ph while getServiceToken when signature mismatch");
        }
        L.login.i("%s buildAndCacheServiceToken cache sid=%s serviceToken", "LoginManager ", str);
        this.d.put(str, a2);
        return a2;
    }

    @Nullable
    public String blockWebLogin(@NonNull Activity activity, @NonNull String str) {
        ServiceToken blockGetServiceToken = blockGetServiceToken(activity, "weblogin:" + str);
        if (blockGetServiceToken != null) {
            return blockGetServiceToken.getServiceToken();
        }
        L.login.e("Web login failed");
        return null;
    }

    @Nullable
    public String blockWebLogin(@NonNull String str) throws NeedUserInteractionException {
        ServiceToken blockGetServiceToken = blockGetServiceToken("weblogin:" + str);
        if (blockGetServiceToken != null) {
            return blockGetServiceToken.getServiceToken();
        }
        L.login.e("Web login failed");
        return null;
    }

    @Nullable
    public ServiceToken blockRefreshServiceToken(@NonNull Activity activity, @NonNull String str) {
        invalidateServiceToken(str);
        return blockGetServiceToken(activity, str);
    }

    @Nullable
    public ServiceToken blockRefreshServiceToken(@NonNull String str) throws NeedUserInteractionException {
        invalidateServiceToken(str);
        return blockGetServiceToken(str);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [android.accounts.AccountManager, java.lang.StringBuilder] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void invalidateServiceToken(@androidx.annotation.NonNull java.lang.String r3) {
        /*
            r2 = this;
            android.accounts.Account r0 = r2.a()
            if (r0 != 0) goto L_0x000e
            com.elvishew.xlog.Logger r3 = com.xiaomi.micolauncher.common.L.login
            java.lang.String r0 = "LoginManager invalidateServiceToken No valid account found"
            r3.e(r0)
            return
        L_0x000e:
            java.util.HashMap<java.lang.String, com.xiaomi.mico.account.sdk.ServiceToken> r0 = r2.d
            java.lang.Object r3 = r0.remove(r3)
            com.xiaomi.mico.account.sdk.ServiceToken r3 = (com.xiaomi.mico.account.sdk.ServiceToken) r3
            if (r3 != 0) goto L_0x0020
            com.elvishew.xlog.Logger r3 = com.xiaomi.micolauncher.common.L.login
            java.lang.String r0 = "LoginManager invalidateServiceToken can't found serviceToken. return"
            r3.e(r0)
            return
        L_0x0020:
            android.accounts.AccountManager r0 = r2.c
            java.lang.String r1 = "com.xiaomi"
            java.lang.String r3 = r3.getAuthToken()
            r0.append(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mico.account.sdk.LoginManager.invalidateServiceToken(java.lang.String):void");
    }

    /* loaded from: classes3.dex */
    public static class NeedUserInteractionException extends Exception {
        private static final long serialVersionUID = 4951225316343246487L;
        private Intent mIntent;

        public NeedUserInteractionException(Intent intent) {
            super("User Interaction Needed.");
            this.mIntent = intent;
        }

        public Intent getIntent() {
            return this.mIntent;
        }
    }
}
