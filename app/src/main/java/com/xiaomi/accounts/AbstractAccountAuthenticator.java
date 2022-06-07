package com.xiaomi.accounts;

import android.accounts.Account;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.accounts.IAccountAuthenticator;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Arrays;
import okhttp3.HttpUrl;

/* loaded from: classes2.dex */
public abstract class AbstractAccountAuthenticator {
    private static final String TAG = "AccountAuthenticator";
    private final Context mContext;
    private a mTransport = new a();

    public abstract Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws NetworkErrorException;

    public abstract Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException;

    public abstract Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String str);

    public abstract Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) throws NetworkErrorException;

    public abstract String getAuthTokenLabel(String str);

    public abstract Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strArr) throws NetworkErrorException;

    public abstract Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) throws NetworkErrorException;

    public AbstractAccountAuthenticator(Context context) {
        this.mContext = context;
    }

    /* loaded from: classes2.dex */
    private class a extends IAccountAuthenticator.Stub {
        private a() {
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void addAccount(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException {
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("addAccount: accountType ");
                sb.append(str);
                sb.append(", authTokenType ");
                sb.append(str2);
                sb.append(", features ");
                sb.append(strArr == null ? HttpUrl.PATH_SEGMENT_ENCODE_SET_URI : Arrays.toString(strArr));
                AccountLog.v(AbstractAccountAuthenticator.TAG, sb.toString());
            }
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle addAccount = AbstractAccountAuthenticator.this.addAccount(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), str, str2, strArr, bundle);
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    addAccount.keySet();
                    AccountLog.v(AbstractAccountAuthenticator.TAG, "addAccount: result " + AccountManager.sanitizeResult(addAccount));
                }
                if (addAccount != null) {
                    iAccountAuthenticatorResponse.onResult(addAccount);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "addAccount", str, e);
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void confirmCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException {
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                AccountLog.v(AbstractAccountAuthenticator.TAG, "confirmCredentials: " + account);
            }
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle confirmCredentials = AbstractAccountAuthenticator.this.confirmCredentials(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, bundle);
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    confirmCredentials.keySet();
                    AccountLog.v(AbstractAccountAuthenticator.TAG, "confirmCredentials: result " + AccountManager.sanitizeResult(confirmCredentials));
                }
                if (confirmCredentials != null) {
                    iAccountAuthenticatorResponse.onResult(confirmCredentials);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "confirmCredentials", account.toString(), e);
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void getAuthTokenLabel(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                AccountLog.v(AbstractAccountAuthenticator.TAG, "getAuthTokenLabel: authTokenType " + str);
            }
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle bundle = new Bundle();
                bundle.putString("authTokenLabelKey", AbstractAccountAuthenticator.this.getAuthTokenLabel(str));
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    bundle.keySet();
                    AccountLog.v(AbstractAccountAuthenticator.TAG, "getAuthTokenLabel: result " + AccountManager.sanitizeResult(bundle));
                }
                iAccountAuthenticatorResponse.onResult(bundle);
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAuthTokenLabel", str, e);
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void getAuthToken(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                AccountLog.v(AbstractAccountAuthenticator.TAG, "getAuthToken: " + account + ", authTokenType " + str);
            }
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle authToken = AbstractAccountAuthenticator.this.getAuthToken(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, str, bundle);
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    authToken.keySet();
                    AccountLog.v(AbstractAccountAuthenticator.TAG, "getAuthToken: result " + AccountManager.sanitizeResult(authToken));
                }
                if (authToken != null) {
                    iAccountAuthenticatorResponse.onResult(authToken);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator abstractAccountAuthenticator = AbstractAccountAuthenticator.this;
                abstractAccountAuthenticator.handleException(iAccountAuthenticatorResponse, "getAuthToken", account.toString() + Constants.ACCEPT_TIME_SEPARATOR_SP + str, e);
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void updateCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException {
            if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                AccountLog.v(AbstractAccountAuthenticator.TAG, "updateCredentials: " + account + ", authTokenType " + str);
            }
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle updateCredentials = AbstractAccountAuthenticator.this.updateCredentials(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, str, bundle);
                if (Log.isLoggable(AbstractAccountAuthenticator.TAG, 2)) {
                    updateCredentials.keySet();
                    AccountLog.v(AbstractAccountAuthenticator.TAG, "updateCredentials: result " + AccountManager.sanitizeResult(updateCredentials));
                }
                if (updateCredentials != null) {
                    iAccountAuthenticatorResponse.onResult(updateCredentials);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator abstractAccountAuthenticator = AbstractAccountAuthenticator.this;
                abstractAccountAuthenticator.handleException(iAccountAuthenticatorResponse, "updateCredentials", account.toString() + Constants.ACCEPT_TIME_SEPARATOR_SP + str, e);
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void editProperties(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException {
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle editProperties = AbstractAccountAuthenticator.this.editProperties(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), str);
                if (editProperties != null) {
                    iAccountAuthenticatorResponse.onResult(editProperties);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "editProperties", str, e);
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void hasFeatures(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String[] strArr) throws RemoteException {
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle hasFeatures = AbstractAccountAuthenticator.this.hasFeatures(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account, strArr);
                if (hasFeatures != null) {
                    iAccountAuthenticatorResponse.onResult(hasFeatures);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "hasFeatures", account.toString(), e);
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticator
        public void getAccountRemovalAllowed(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException {
            AbstractAccountAuthenticator.this.checkBinderPermission();
            try {
                Bundle accountRemovalAllowed = AbstractAccountAuthenticator.this.getAccountRemovalAllowed(new AccountAuthenticatorResponse(iAccountAuthenticatorResponse), account);
                if (accountRemovalAllowed != null) {
                    iAccountAuthenticatorResponse.onResult(accountRemovalAllowed);
                }
            } catch (Exception e) {
                AbstractAccountAuthenticator.this.handleException(iAccountAuthenticatorResponse, "getAccountRemovalAllowed", account.toString(), e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleException(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, Exception exc) throws RemoteException {
        if (exc instanceof NetworkErrorException) {
            AccountLog.w(TAG, str + "(" + str2 + ")", exc);
            iAccountAuthenticatorResponse.onError(3, exc.getMessage());
        } else if (exc instanceof UnsupportedOperationException) {
            AccountLog.w(TAG, str + "(" + str2 + ")", exc);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" not supported");
            iAccountAuthenticatorResponse.onError(6, sb.toString());
        } else if (exc instanceof IllegalArgumentException) {
            AccountLog.w(TAG, str + "(" + str2 + ")", exc);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(" not supported");
            iAccountAuthenticatorResponse.onError(7, sb2.toString());
        } else {
            AccountLog.w(TAG, str + "(" + str2 + ")", exc);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(" failed");
            iAccountAuthenticatorResponse.onError(1, sb3.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkBinderPermission() {
        int callingUid = Binder.getCallingUid();
        if (this.mContext.getApplicationInfo().uid != callingUid && this.mContext.checkCallingOrSelfPermission("android.permission.ACCOUNT_MANAGER") != 0) {
            throw new SecurityException("caller uid " + callingUid + " lacks android.permission.ACCOUNT_MANAGER");
        }
    }

    public final IBinder getIBinder() {
        return this.mTransport.asBinder();
    }

    public Bundle getAccountRemovalAllowed(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account) throws NetworkErrorException {
        Bundle bundle = new Bundle();
        bundle.putBoolean("booleanResult", true);
        return bundle;
    }
}
