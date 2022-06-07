package com.xiaomi.passport.accountmanager;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorDescription;
import android.accounts.AuthenticatorException;
import android.accounts.OnAccountsUpdateListener;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.futureservice.MiAccountManagerFuture;
import com.xiaomi.accountsdk.hasheddeviceidlib.HashedDeviceIdUtil;
import com.xiaomi.passport.LocalFeatures.LocalFeaturesImpl;
import com.xiaomi.passport.LocalFeatures.MiLocalFeaturesManager;
import com.xiaomi.passport.servicetoken.ServiceTokenFuture;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.servicetoken.data.XmAccountVisibility;
import java.io.IOException;

/* loaded from: classes4.dex */
public class MiAccountManager implements IAccountManagerInternal {
    public static final String ACTION_AUTHENTICATOR_INTENT = "android.accounts.AccountAuthenticator";
    public static final String AUTHENTICATOR_ATTRIBUTES_NAME = "account-authenticator";
    public static final String AUTHENTICATOR_META_DATA_NAME = "android.accounts.AccountAuthenticator";
    public static final int ERROR_CODE_BAD_ARGUMENTS = 7;
    public static final int ERROR_CODE_BAD_REQUEST = 8;
    public static final int ERROR_CODE_CANCELED = 4;
    public static final int ERROR_CODE_INVALID_RESPONSE = 5;
    public static final int ERROR_CODE_NETWORK_ERROR = 3;
    public static final int ERROR_CODE_REMOTE_EXCEPTION = 1;
    public static final int ERROR_CODE_UNSUPPORTED_OPERATION = 6;
    public static final String KEY_ACCOUNTS = "accounts";
    public static final String KEY_ACCOUNT_AUTHENTICATOR_RESPONSE = "accountAuthenticatorResponse";
    public static final String KEY_ACCOUNT_MANAGER_RESPONSE = "accountManagerResponse";
    public static final String KEY_ACCOUNT_NAME = "authAccount";
    public static final String KEY_ACCOUNT_TYPE = "accountType";
    public static final String KEY_AUTHENTICATOR_TYPES = "authenticator_types";
    public static final String KEY_AUTHTOKEN = "authtoken";
    public static final String KEY_AUTH_FAILED_MESSAGE = "authFailedMessage";
    public static final String KEY_AUTH_TOKEN_LABEL = "authTokenLabelKey";
    public static final String KEY_BOOLEAN_RESULT = "booleanResult";
    public static final String KEY_ERROR_CODE = "errorCode";
    public static final String KEY_ERROR_MESSAGE = "errorMessage";
    public static final String KEY_INTENT = "intent";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_STS_URL = "sts_url";
    public static final String KEY_USERDATA = "userdata";
    public static final String LOGIN_ACCOUNTS_CHANGED_ACTION = "account-authenticator";
    public static final String XIAOMI_ACCOUNT_TYPE = "com.xiaomi";
    private static volatile MiAccountManager sThis;
    private AccountAuthenticator mAccountAuthenticator;
    private boolean mCanUseServiceTokenUtil;
    private boolean mCanUseSystemAccount;
    private final Context mContext;
    private LocalAccountManagerAdapter mLocalAccountAdapter;
    private SystemAccountManagerAdapter mSystemAccountAdapter;
    private IAccountManagerInternal mXmsfAccountAdapter;

    /* loaded from: classes4.dex */
    public enum AccountAuthenticator {
        LOCAL,
        SYSTEM
    }

    /* loaded from: classes4.dex */
    public enum SystemAccountVisibility {
        IMPOSSIBLE,
        REQUIRE_LOCAL_APP_PERMISSION,
        CAN_USE_SERVICE_TOKEN_UTIL
    }

    private MiAccountManager(Context context) {
        this.mContext = context.getApplicationContext();
        XMPassportSettings.ensureApplicationContext((Application) this.mContext);
        XMPassportSettings.setNonNullApplicationContextContract(true);
        this.mCanUseSystemAccount = canUseSystemAccount(context);
        this.mCanUseServiceTokenUtil = canUserServiceTokenUtil(context);
        restoreAccountAuthenticator();
    }

    private void restoreAccountAuthenticator() {
        AccountAuthenticator loadAccountAuthenticator = MiAccountManagerSettingsPersistent.getInstance(this.mContext).loadAccountAuthenticator();
        if (loadAccountAuthenticator == null) {
            loadAccountAuthenticator = AccountAuthenticator.SYSTEM;
        }
        setUpAccountManagerConfig(loadAccountAuthenticator);
    }

    public boolean canUseSystem() {
        return this.mCanUseSystemAccount;
    }

    public SystemAccountVisibility canUseSystemMoreDetailed() {
        if (!this.mCanUseSystemAccount) {
            return SystemAccountVisibility.IMPOSSIBLE;
        }
        if (this.mCanUseServiceTokenUtil) {
            return SystemAccountVisibility.CAN_USE_SERVICE_TOKEN_UTIL;
        }
        return SystemAccountVisibility.REQUIRE_LOCAL_APP_PERMISSION;
    }

    public boolean isUseSystem() {
        return this.mAccountAuthenticator == AccountAuthenticator.SYSTEM;
    }

    public boolean isUseLocal() {
        return this.mAccountAuthenticator == AccountAuthenticator.LOCAL;
    }

    public void setUseSystem() {
        setUpAccountManagerConfig(AccountAuthenticator.SYSTEM);
    }

    public void setUseLocal() {
        setUpAccountManagerConfig(AccountAuthenticator.LOCAL);
    }

    private void setUpAccountManagerConfig(AccountAuthenticator accountAuthenticator) {
        setUpAccountAdapter(accountAuthenticator);
    }

    private void setUpDeviceIdPolicy(AccountAuthenticator accountAuthenticator) {
        switch (accountAuthenticator) {
            case SYSTEM:
                HashedDeviceIdUtil.GlobalConfig.getInstance().setPolicy(HashedDeviceIdUtil.DeviceIdPolicy.RUNTIME_DEVICE_ID_ONLY);
                return;
            case LOCAL:
                HashedDeviceIdUtil.GlobalConfig.getInstance().setPolicy(HashedDeviceIdUtil.DeviceIdPolicy.CACHED_THEN_RUNTIME_THEN_PSEUDO);
                return;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void setUpAccountAdapter(AccountAuthenticator accountAuthenticator) {
        switch (accountAuthenticator) {
            case SYSTEM:
                if (!this.mCanUseSystemAccount) {
                    this.mAccountAuthenticator = AccountAuthenticator.LOCAL;
                    break;
                } else {
                    this.mAccountAuthenticator = AccountAuthenticator.SYSTEM;
                    break;
                }
            case LOCAL:
                this.mAccountAuthenticator = AccountAuthenticator.LOCAL;
                break;
            default:
                throw new IllegalArgumentException();
        }
        switch (this.mAccountAuthenticator) {
            case SYSTEM:
                if (this.mSystemAccountAdapter == null) {
                    this.mSystemAccountAdapter = new SystemAccountManagerAdapter(this.mContext);
                }
                this.mXmsfAccountAdapter = this.mSystemAccountAdapter;
                break;
            case LOCAL:
                if (this.mLocalAccountAdapter == null) {
                    this.mLocalAccountAdapter = new LocalAccountManagerAdapter(this.mContext);
                }
                this.mXmsfAccountAdapter = this.mLocalAccountAdapter;
                break;
            default:
                throw new IllegalArgumentException();
        }
        setUpDeviceIdPolicy(this.mAccountAuthenticator);
        MiAccountManagerSettingsPersistent.getInstance(this.mContext).saveAccountAuthenticator(this.mAccountAuthenticator);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x0000: INVOKE  (r7 I:void) = (r7v0 ?? I:java.lang.String), (r0 I:java.lang.Throwable) type: STATIC call: com.elvishew.xlog.Logger.i(java.lang.String, java.lang.Throwable):void, block:B:2:0x0000
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    private boolean canUseSystemAccount(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x0000: INVOKE  (r7 I:void) = (r7v0 ?? I:java.lang.String), (r0 I:java.lang.Throwable) type: STATIC call: com.elvishew.xlog.Logger.i(java.lang.String, java.lang.Throwable):void, block:B:2:0x0000
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r7v0 ??
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

    private boolean canUserServiceTokenUtil(Context context) {
        return context.getPackageManager().resolveService(new Intent(AccountIntent.ACTION_SERVICE_TOKEN_OP).setPackage(AccountIntent.PACKAGE_XIAOMI_ACCOUNT), 0) != null;
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    @Deprecated
    public MiAccountManagerFuture<XmAccountVisibility> canAccessAccount(Context context) {
        return this.mXmsfAccountAdapter.canAccessAccount(context);
    }

    public MiAccountManagerFuture<XmAccountVisibility> setSystemAccountVisibility(Context context) {
        return new SystemAccountManagerAdapter(context).canAccessAccount(context);
    }

    public static MiAccountManager get(Context context) {
        if (context != null) {
            if (sThis == null) {
                synchronized (MiAccountManager.class) {
                    if (sThis == null) {
                        sThis = new MiAccountManager(context);
                    }
                }
            }
            return sThis;
        }
        throw new IllegalArgumentException("context is null");
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AuthenticatorDescription[] getAuthenticatorTypes() {
        return this.mXmsfAccountAdapter.getAuthenticatorTypes();
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public Account[] getAccounts() {
        return this.mXmsfAccountAdapter.getAccounts();
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public Account[] getAccountsByType(String str) {
        return this.mXmsfAccountAdapter.getAccountsByType(str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Boolean> hasFeatures(Account account, String[] strArr, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        return this.mXmsfAccountAdapter.hasFeatures(account, strArr, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Account[]> getAccountsByTypeAndFeatures(String str, String[] strArr, AccountManagerCallback<Account[]> accountManagerCallback, Handler handler) {
        return this.mXmsfAccountAdapter.getAccountsByTypeAndFeatures(str, strArr, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Boolean> removeAccount(Account account, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        return this.mXmsfAccountAdapter.removeAccount(account, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public void invalidateAuthToken(String str, String str2) {
        this.mXmsfAccountAdapter.invalidateAuthToken(str, str2);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public void clearPassword(Account account) {
        this.mXmsfAccountAdapter.clearPassword(account);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public String blockingGetAuthToken(Account account, String str, boolean z) throws OperationCanceledException, IOException, AuthenticatorException {
        return this.mXmsfAccountAdapter.blockingGetAuthToken(account, str, z);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> getAuthToken(Account account, String str, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mXmsfAccountAdapter.getAuthToken(account, str, bundle, activity, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> getAuthToken(Account account, String str, Bundle bundle, boolean z, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mXmsfAccountAdapter.getAuthToken(account, str, bundle, z, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> addAccount(String str, String str2, String[] strArr, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mXmsfAccountAdapter.addAccount(str, str2, strArr, bundle, activity, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> confirmCredentials(Account account, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mXmsfAccountAdapter.confirmCredentials(account, bundle, activity, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> updateCredentials(Account account, String str, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mXmsfAccountAdapter.updateCredentials(account, str, bundle, activity, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> editProperties(String str, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mXmsfAccountAdapter.editProperties(str, activity, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> getAuthTokenByFeatures(String str, String str2, String[] strArr, Activity activity, Bundle bundle, Bundle bundle2, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mXmsfAccountAdapter.getAuthTokenByFeatures(str, str2, strArr, activity, bundle, bundle2, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, Handler handler, boolean z) {
        this.mXmsfAccountAdapter.addOnAccountsUpdatedListener(onAccountsUpdateListener, handler, z);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public void removeOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener) {
        this.mXmsfAccountAdapter.removeOnAccountsUpdatedListener(onAccountsUpdateListener);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public ServiceTokenFuture getServiceToken(Context context, String str) {
        return this.mXmsfAccountAdapter.getServiceToken(context, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public ServiceTokenFuture invalidateServiceToken(Context context, ServiceTokenResult serviceTokenResult) {
        return this.mXmsfAccountAdapter.invalidateServiceToken(context, serviceTokenResult);
    }

    public Account getXiaomiAccount() {
        Account[] accountsByType = this.mXmsfAccountAdapter.getAccountsByType("com.xiaomi");
        if (accountsByType.length > 0) {
            return accountsByType[0];
        }
        return null;
    }

    public void addXiaomiAccount(String str, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        addXiaomiAccount(str, null, null, activity, accountManagerCallback, handler);
    }

    public void addXiaomiAccount(String str, String[] strArr, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        for (Account account : getAccounts()) {
            if (TextUtils.equals(account.type, "com.xiaomi")) {
                return;
            }
        }
        addAccount("com.xiaomi", str, strArr, bundle, activity, accountManagerCallback, handler);
    }

    public void removeXiaomiAccount(AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        Account[] accounts = getAccounts();
        for (Account account : accounts) {
            if (TextUtils.equals(account.type, "com.xiaomi")) {
                removeAccount(account, accountManagerCallback, handler);
            }
        }
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public String getPassword(Account account) {
        return this.mXmsfAccountAdapter.getPassword(account);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public String getUserData(Account account, String str) {
        return this.mXmsfAccountAdapter.getUserData(account, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public boolean addAccountExplicitly(Account account, String str, Bundle bundle) {
        return this.mXmsfAccountAdapter.addAccountExplicitly(account, str, bundle);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public String peekAuthToken(Account account, String str) {
        return this.mXmsfAccountAdapter.peekAuthToken(account, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public void setPassword(Account account, String str) {
        this.mXmsfAccountAdapter.setPassword(account, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public void setUserData(Account account, String str, String str2) {
        this.mXmsfAccountAdapter.setUserData(account, str, str2);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public void setAuthToken(Account account, String str, String str2) {
        this.mXmsfAccountAdapter.setAuthToken(account, str, str2);
    }

    public MiLocalFeaturesManager getLocalFeatures() {
        return LocalFeaturesImpl.get(this.mContext);
    }
}
