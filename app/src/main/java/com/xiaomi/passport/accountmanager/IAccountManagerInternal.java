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
import com.xiaomi.accountsdk.futureservice.MiAccountManagerFuture;
import com.xiaomi.passport.servicetoken.ServiceTokenFuture;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import com.xiaomi.passport.servicetoken.data.XmAccountVisibility;
import java.io.IOException;

/* loaded from: classes4.dex */
public interface IAccountManagerInternal extends IAccountManager {
    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    AccountManagerFuture<Bundle> addAccount(String str, String str2, String[] strArr, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler);

    boolean addAccountExplicitly(Account account, String str, Bundle bundle);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, Handler handler, boolean z);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    String blockingGetAuthToken(Account account, String str, boolean z) throws OperationCanceledException, IOException, AuthenticatorException;

    MiAccountManagerFuture<XmAccountVisibility> canAccessAccount(Context context);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    void clearPassword(Account account);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    AccountManagerFuture<Bundle> confirmCredentials(Account account, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    AccountManagerFuture<Bundle> editProperties(String str, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    Account[] getAccounts();

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    Account[] getAccountsByType(String str);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    AccountManagerFuture<Account[]> getAccountsByTypeAndFeatures(String str, String[] strArr, AccountManagerCallback<Account[]> accountManagerCallback, Handler handler);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    AccountManagerFuture<Bundle> getAuthToken(Account account, String str, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    AccountManagerFuture<Bundle> getAuthToken(Account account, String str, Bundle bundle, boolean z, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    AccountManagerFuture<Bundle> getAuthTokenByFeatures(String str, String str2, String[] strArr, Activity activity, Bundle bundle, Bundle bundle2, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    AuthenticatorDescription[] getAuthenticatorTypes();

    String getPassword(Account account);

    ServiceTokenFuture getServiceToken(Context context, String str);

    String getUserData(Account account, String str);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    AccountManagerFuture<Boolean> hasFeatures(Account account, String[] strArr, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    void invalidateAuthToken(String str, String str2);

    ServiceTokenFuture invalidateServiceToken(Context context, ServiceTokenResult serviceTokenResult);

    String peekAuthToken(Account account, String str);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    AccountManagerFuture<Boolean> removeAccount(Account account, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    void removeOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener);

    void setAuthToken(Account account, String str, String str2);

    void setPassword(Account account, String str);

    void setUserData(Account account, String str, String str2);

    @Override // com.xiaomi.passport.accountmanager.IAccountManager
    AccountManagerFuture<Bundle> updateCredentials(Account account, String str, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler);
}
