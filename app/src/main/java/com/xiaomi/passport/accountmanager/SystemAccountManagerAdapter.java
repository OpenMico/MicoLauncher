package com.xiaomi.passport.accountmanager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OnAccountsUpdateListener;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.futureservice.MiAccountManagerFuture;
import com.xiaomi.passport.servicetoken.IServiceTokenUtil;
import com.xiaomi.passport.servicetoken.ServiceTokenFuture;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.servicetoken.data.XmAccountVisibility;
import java.io.IOException;

/* loaded from: classes4.dex */
class SystemAccountManagerAdapter implements IAccountManagerInternal {
    private final Context applicationContext;
    private AccountManager mAccountManager;
    private final IServiceTokenUtil mServiceTokenUtil;

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x0003: INVOKE  (r0 I:void) = (r5v0 ?? I:java.lang.String), (r0 I:java.lang.Throwable) type: STATIC call: com.elvishew.xlog.Logger.i(java.lang.String, java.lang.Throwable):void, block:B:2:0x0000
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    public SystemAccountManagerAdapter(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x0003: INVOKE  (r0 I:void) = (r5v0 ?? I:java.lang.String), (r0 I:java.lang.Throwable) type: STATIC call: com.elvishew.xlog.Logger.i(java.lang.String, java.lang.Throwable):void, block:B:2:0x0000
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r5v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    /* JADX WARN: Type inference failed for: r0v0, types: [android.accounts.AccountManager, java.lang.Throwable, com.elvishew.xlog.XLog$Log, java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1, types: [void, android.accounts.AuthenticatorDescription[]] */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.accounts.AuthenticatorDescription[] getAuthenticatorTypes() {
        /*
            r1 = this;
            android.accounts.AccountManager r0 = r1.mAccountManager
            void r0 = r0.wtf(r0, r0, r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.passport.accountmanager.SystemAccountManagerAdapter.getAuthenticatorTypes():android.accounts.AuthenticatorDescription[]");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.accounts.AccountManager, java.lang.Throwable, com.elvishew.xlog.XLog$Log, java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v1, types: [void, java.lang.String] */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getPassword(android.accounts.Account r2) {
        /*
            r1 = this;
            android.accounts.AccountManager r0 = r1.mAccountManager
            void r2 = r0.e(r2, r0, r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.passport.accountmanager.SystemAccountManagerAdapter.getPassword(android.accounts.Account):java.lang.String");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.accounts.AccountManager, com.elvishew.xlog.Logger] */
    /* JADX WARN: Type inference failed for: r2v1, types: [void, java.lang.String] */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public String getUserData(Account account, String str) {
        return this.mAccountManager.println(account, str);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.accounts.AccountManager, com.elvishew.xlog.Logger, java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1, types: [void, android.accounts.Account[]] */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.accounts.Account[] getAccounts() {
        /*
            r1 = this;
            android.accounts.AccountManager r0 = r1.mAccountManager
            void r0 = r0.w(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.passport.accountmanager.SystemAccountManagerAdapter.getAccounts():android.accounts.Account[]");
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.accounts.AccountManager, java.lang.Throwable, com.elvishew.xlog.Logger] */
    /* JADX WARN: Type inference failed for: r2v1, types: [void, android.accounts.Account[]] */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.accounts.Account[] getAccountsByType(java.lang.String r2) {
        /*
            r1 = this;
            android.accounts.AccountManager r0 = r1.mAccountManager
            void r2 = r0.w(r2, r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.passport.accountmanager.SystemAccountManagerAdapter.getAccountsByType(java.lang.String):android.accounts.Account[]");
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Boolean> hasFeatures(Account account, String[] strArr, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        return this.mAccountManager.hasFeatures(account, strArr, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Account[]> getAccountsByTypeAndFeatures(String str, String[] strArr, AccountManagerCallback<Account[]> accountManagerCallback, Handler handler) {
        return this.mAccountManager.getAccountsByTypeAndFeatures(str, strArr, accountManagerCallback, handler);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.accounts.AccountManager, com.elvishew.xlog.Logger$Builder] */
    /* JADX WARN: Type inference failed for: r2v1, types: [boolean, com.elvishew.xlog.Logger] */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public boolean addAccountExplicitly(Account account, String str, Bundle bundle) {
        return this.mAccountManager.build();
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Boolean> removeAccount(Account account, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        return this.mAccountManager.removeAccount(account, accountManagerCallback, handler);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.accounts.AccountManager, com.elvishew.xlog.LogConfiguration] */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public void invalidateAuthToken(String str, String str2) {
        this.mAccountManager.isLoggable(str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public String peekAuthToken(Account account, String str) {
        return this.mAccountManager.peekAuthToken(account, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public void setPassword(Account account, String str) {
        this.mAccountManager.setPassword(account, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.accounts.AccountManager, com.elvishew.xlog.Logger] */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public void clearPassword(Account account) {
        this.mAccountManager.d(account);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public void setUserData(Account account, String str, String str2) {
        this.mAccountManager.setUserData(account, str, str2);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public void setAuthToken(Account account, String str, String str2) {
        this.mAccountManager.setAuthToken(account, str, str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.accounts.AccountManager, com.elvishew.xlog.Logger] */
    /* JADX WARN: Type inference failed for: r2v1, types: [void, java.lang.String] */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public String blockingGetAuthToken(Account account, String str, boolean z) throws OperationCanceledException, IOException, AuthenticatorException {
        return this.mAccountManager.v(account, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.accounts.AccountManager, com.elvishew.xlog.Logger] */
    /* JADX WARN: Type inference failed for: r8v1, types: [void, android.accounts.AccountManagerFuture<android.os.Bundle>] */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> getAuthToken(Account account, String str, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (!shouldUseGetAuthTokenVer2(bundle, accountManagerCallback, handler) || activity != null) {
            return this.mAccountManager.e(account);
        }
        return AMFutureConverter.fromServiceTokenFuture(getServiceToken(this.applicationContext, str));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.accounts.AccountManager, com.elvishew.xlog.Logger] */
    /* JADX WARN: Type inference failed for: r8v1, types: [void, android.accounts.AccountManagerFuture<android.os.Bundle>] */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> getAuthToken(Account account, String str, Bundle bundle, boolean z, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (!shouldUseGetAuthTokenVer2(bundle, accountManagerCallback, handler) || z) {
            return this.mAccountManager.e(account, str);
        }
        return AMFutureConverter.fromServiceTokenFuture(getServiceToken(this.applicationContext, str));
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.accounts.AccountManager, com.elvishew.xlog.XLog] */
    /* JADX WARN: Type inference failed for: r9v1, types: [android.accounts.AccountManagerFuture<android.os.Bundle>, com.elvishew.xlog.Logger$Builder] */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> addAccount(String str, String str2, String[] strArr, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mAccountManager.tag(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.accounts.AccountManager, com.elvishew.xlog.Logger] */
    /* JADX WARN: Type inference failed for: r7v1, types: [void, android.accounts.AccountManagerFuture<android.os.Bundle>] */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> confirmCredentials(Account account, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mAccountManager.d(account, bundle);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> updateCredentials(Account account, String str, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mAccountManager.updateCredentials(account, str, bundle, activity, accountManagerCallback, handler);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.accounts.AccountManager, com.elvishew.xlog.Logger] */
    /* JADX WARN: Type inference failed for: r2v1, types: [void, android.accounts.AccountManagerFuture<android.os.Bundle>] */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> editProperties(String str, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mAccountManager.i(str);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [android.accounts.AccountManager, com.elvishew.xlog.XLog$Log] */
    /* JADX WARN: Type inference failed for: r1v1, types: [void, android.accounts.AccountManagerFuture<android.os.Bundle>] */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> getAuthTokenByFeatures(String str, String str2, String[] strArr, Activity activity, Bundle bundle, Bundle bundle2, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mAccountManager.e(str, str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.accounts.AccountManager, com.elvishew.xlog.Logger] */
    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, Handler handler, boolean z) {
        this.mAccountManager.v(onAccountsUpdateListener);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public void removeOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener) {
        this.mAccountManager.removeOnAccountsUpdatedListener(onAccountsUpdateListener);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public ServiceTokenFuture getServiceToken(Context context, String str) {
        return this.mServiceTokenUtil.getServiceToken(context, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public ServiceTokenFuture invalidateServiceToken(Context context, ServiceTokenResult serviceTokenResult) {
        return this.mServiceTokenUtil.invalidateServiceToken(context, serviceTokenResult);
    }

    private boolean isXiaomiAccountApp(Context context) {
        return TextUtils.equals(context.getPackageName(), AccountIntent.PACKAGE_XIAOMI_ACCOUNT);
    }

    private boolean shouldUseGetAuthTokenVer2(Bundle bundle, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return accountManagerCallback == null && handler == null && bundle != null && bundle.getBoolean(BaseConstants.EXTRA_USE_GET_AUTH_TOKEN_IMPL_VER_2, false) && bundle.keySet().size() == 1;
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public MiAccountManagerFuture<XmAccountVisibility> canAccessAccount(Context context) {
        return this.mServiceTokenUtil.canAccessAccount(context);
    }
}
