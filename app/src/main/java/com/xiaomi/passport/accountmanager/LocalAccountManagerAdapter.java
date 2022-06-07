package com.xiaomi.passport.accountmanager;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorDescription;
import android.accounts.AuthenticatorException;
import android.accounts.OnAccountsUpdateListener;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.xiaomi.accounts.AccountManager;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.futureservice.MiAccountManagerFuture;
import com.xiaomi.passport.servicetoken.IServiceTokenUtil;
import com.xiaomi.passport.servicetoken.ServiceTokenFuture;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.servicetoken.data.XmAccountVisibility;
import com.xiaomi.passport.utils.AuthenticatorUtil;
import java.io.IOException;

/* loaded from: classes4.dex */
class LocalAccountManagerAdapter implements IAccountManagerInternal {
    private AccountManager mAccountManager;
    private final IServiceTokenUtil mServiceTokenUtil = new LocalAccountManagerServiceTokenUtil();

    public LocalAccountManagerAdapter(Context context) {
        this.mAccountManager = AccountManager.get(context);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AuthenticatorDescription[] getAuthenticatorTypes() {
        return this.mAccountManager.getAuthenticatorTypes();
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public String getPassword(Account account) {
        return this.mAccountManager.getPassword(account);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public String getUserData(Account account, String str) {
        return this.mAccountManager.getUserData(account, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public Account[] getAccounts() {
        return this.mAccountManager.getAccounts();
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public Account[] getAccountsByType(String str) {
        return this.mAccountManager.getAccountsByType(str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Boolean> hasFeatures(Account account, String[] strArr, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        return this.mAccountManager.hasFeatures(account, strArr, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Account[]> getAccountsByTypeAndFeatures(String str, String[] strArr, AccountManagerCallback<Account[]> accountManagerCallback, Handler handler) {
        return this.mAccountManager.getAccountsByTypeAndFeatures(str, strArr, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public boolean addAccountExplicitly(Account account, String str, Bundle bundle) {
        return this.mAccountManager.addAccountExplicitly(account, str, bundle);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Boolean> removeAccount(Account account, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        return this.mAccountManager.removeAccount(account, wrapRemoveAccountCallback(accountManagerCallback), handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public void invalidateAuthToken(String str, String str2) {
        this.mAccountManager.invalidateAuthToken(str, str2);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public String peekAuthToken(Account account, String str) {
        return this.mAccountManager.peekAuthToken(account, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public void setPassword(Account account, String str) {
        this.mAccountManager.setPassword(account, str);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public void clearPassword(Account account) {
        this.mAccountManager.clearPassword(account);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public void setUserData(Account account, String str, String str2) {
        this.mAccountManager.setUserData(account, str, str2);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public void setAuthToken(Account account, String str, String str2) {
        this.mAccountManager.setAuthToken(account, str, str2);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public String blockingGetAuthToken(Account account, String str, boolean z) throws OperationCanceledException, IOException, AuthenticatorException {
        return this.mAccountManager.blockingGetAuthToken(account, str, z);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> getAuthToken(Account account, String str, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mAccountManager.getAuthToken(account, str, bundle, activity, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> getAuthToken(Account account, String str, Bundle bundle, boolean z, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mAccountManager.getAuthToken(account, str, bundle, z, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> addAccount(String str, String str2, String[] strArr, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mAccountManager.addAccount(str, str2, strArr, bundle, activity, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> confirmCredentials(Account account, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mAccountManager.confirmCredentials(account, bundle, activity, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> updateCredentials(Account account, String str, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mAccountManager.updateCredentials(account, str, bundle, activity, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> editProperties(String str, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mAccountManager.editProperties(str, activity, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public AccountManagerFuture<Bundle> getAuthTokenByFeatures(String str, String str2, String[] strArr, Activity activity, Bundle bundle, Bundle bundle2, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return this.mAccountManager.getAuthTokenByFeatures(str, str2, strArr, activity, bundle, bundle2, accountManagerCallback, handler);
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal, com.xiaomi.passport.accountmanager.IAccountManager
    public void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, Handler handler, boolean z) {
        this.mAccountManager.addOnAccountsUpdatedListener(onAccountsUpdateListener, handler, z);
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

    private AccountManagerCallback<Boolean> wrapRemoveAccountCallback(final AccountManagerCallback<Boolean> accountManagerCallback) {
        return new AccountManagerCallback<Boolean>() { // from class: com.xiaomi.passport.accountmanager.LocalAccountManagerAdapter.1
            @Override // android.accounts.AccountManagerCallback
            public void run(AccountManagerFuture<Boolean> accountManagerFuture) {
                AccountManagerCallback accountManagerCallback2 = accountManagerCallback;
                if (accountManagerCallback2 != null) {
                    accountManagerCallback2.run(accountManagerFuture);
                }
                AuthenticatorUtil.clearAllXiaomiAccountCookies(XMPassportSettings.getApplicationContext());
            }
        };
    }

    @Override // com.xiaomi.passport.accountmanager.IAccountManagerInternal
    public MiAccountManagerFuture<XmAccountVisibility> canAccessAccount(Context context) {
        return this.mServiceTokenUtil.canAccessAccount(context);
    }
}
