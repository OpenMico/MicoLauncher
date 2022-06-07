package com.xiaomi.accounts;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorDescription;
import android.accounts.AuthenticatorException;
import android.accounts.OnAccountsUpdateListener;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaomi.accounts.IAccountManagerResponse;
import com.xiaomi.accountsdk.utils.AccountLog;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes2.dex */
public class AccountManager {
    public static final String ACTION_AUTHENTICATOR_INTENT = "com.xiaomi.accounts.AccountAuthenticator";
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
    public static final String KEY_ANDROID_PACKAGE_NAME = "androidPackageName";
    public static final String KEY_AUTHENTICATOR_TYPES = "authenticator_types";
    public static final String KEY_AUTHTOKEN = "authtoken";
    public static final String KEY_AUTH_FAILED_MESSAGE = "authFailedMessage";
    public static final String KEY_AUTH_FAILED_TITLE = "authFailedTitle";
    public static final String KEY_AUTH_TOKEN_LABEL = "authTokenLabelKey";
    public static final String KEY_BOOLEAN_RESULT = "booleanResult";
    public static final String KEY_CALLER_PID = "callerPid";
    public static final String KEY_CALLER_UID = "callerUid";
    public static final String KEY_ERROR_CODE = "errorCode";
    public static final String KEY_ERROR_MESSAGE = "errorMessage";
    public static final String KEY_INTENT = "intent";
    public static final String KEY_NOTIFY_ON_FAILURE = "notifyOnAuthFailure";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERDATA = "userdata";
    public static final String LOGIN_ACCOUNTS_CHANGED_ACTION = "com.xiaomi.accounts.LOGIN_ACCOUNTS_CHANGED";
    private static volatile AccountManager d;
    private final Context a;
    private final Handler b;
    private AccountManagerService c;
    private final HashMap<OnAccountsUpdateListener, Handler> e = new HashMap<>();
    private final BroadcastReceiver f = new BroadcastReceiver() { // from class: com.xiaomi.accounts.AccountManager.5
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Account[] accounts = AccountManager.this.getAccounts();
            synchronized (AccountManager.this.e) {
                for (Map.Entry entry : AccountManager.this.e.entrySet()) {
                    AccountManager.this.a((Handler) entry.getValue(), (OnAccountsUpdateListener) entry.getKey(), accounts);
                }
            }
        }
    };

    private AccountManager(Context context) {
        this.a = context;
        this.b = new Handler(this.a.getMainLooper());
        this.c = new AccountManagerService(context);
    }

    public static Bundle sanitizeResult(Bundle bundle) {
        if (bundle == null || !bundle.containsKey("authtoken") || TextUtils.isEmpty(bundle.getString("authtoken"))) {
            return bundle;
        }
        Bundle bundle2 = new Bundle(bundle);
        bundle2.putString("authtoken", "<omitted for logging purposes>");
        return bundle2;
    }

    public static AccountManager get(Context context) {
        if (context != null) {
            if (d == null) {
                synchronized (AccountManager.class) {
                    if (d == null) {
                        d = new AccountManager(context.getApplicationContext());
                    }
                }
            }
            return d;
        }
        throw new IllegalArgumentException("context is null");
    }

    public String getPassword(Account account) {
        if (account != null) {
            return this.c.getPassword(account);
        }
        throw new IllegalArgumentException("account is null");
    }

    public String getUserData(Account account, String str) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        } else if (str != null) {
            return this.c.getUserData(account, str);
        } else {
            throw new IllegalArgumentException("key is null");
        }
    }

    public AuthenticatorDescription[] getAuthenticatorTypes() {
        return this.c.getAuthenticatorTypes();
    }

    public Account[] getAccounts() {
        return this.c.getAccounts((String) null);
    }

    public Account[] getAccountsByType(String str) {
        return this.c.getAccounts(str);
    }

    public void updateAppPermission(Account account, String str, int i, boolean z) {
        try {
            this.c.updateAppPermission(account, str, i, z);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public AccountManagerFuture<String> getAuthTokenLabel(final String str, final String str2, AccountManagerCallback<String> accountManagerCallback, Handler handler) {
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        } else if (str2 != null) {
            return new b<String>(handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.1
                @Override // com.xiaomi.accounts.AccountManager.BaseFutureTask
                public void a() throws RemoteException {
                    AccountManager.this.c.getAuthTokenLabel(this.d, str, str2);
                }

                /* renamed from: a */
                public String b(Bundle bundle) throws AuthenticatorException {
                    if (bundle.containsKey("authTokenLabelKey")) {
                        return bundle.getString("authTokenLabelKey");
                    }
                    throw new AuthenticatorException("no result in response");
                }
            }.c();
        } else {
            throw new IllegalArgumentException("authTokenType is null");
        }
    }

    public AccountManagerFuture<Boolean> hasFeatures(final Account account, final String[] strArr, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        } else if (strArr != null) {
            return new b<Boolean>(handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.6
                @Override // com.xiaomi.accounts.AccountManager.BaseFutureTask
                public void a() throws RemoteException {
                    AccountManager.this.c.hasFeatures(this.d, account, strArr);
                }

                /* renamed from: a */
                public Boolean b(Bundle bundle) throws AuthenticatorException {
                    if (bundle.containsKey("booleanResult")) {
                        return Boolean.valueOf(bundle.getBoolean("booleanResult"));
                    }
                    throw new AuthenticatorException("no result in response");
                }
            }.c();
        } else {
            throw new IllegalArgumentException("features is null");
        }
    }

    public AccountManagerFuture<Account[]> getAccountsByTypeAndFeatures(final String str, final String[] strArr, AccountManagerCallback<Account[]> accountManagerCallback, Handler handler) {
        if (str != null) {
            return new b<Account[]>(handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.7
                @Override // com.xiaomi.accounts.AccountManager.BaseFutureTask
                public void a() throws RemoteException {
                    AccountManager.this.c.getAccountsByFeatures(this.d, str, strArr);
                }

                /* renamed from: a */
                public Account[] b(Bundle bundle) throws AuthenticatorException {
                    if (bundle.containsKey("accounts")) {
                        Parcelable[] parcelableArray = bundle.getParcelableArray("accounts");
                        Account[] accountArr = new Account[parcelableArray.length];
                        for (int i = 0; i < parcelableArray.length; i++) {
                            accountArr[i] = (Account) parcelableArray[i];
                        }
                        return accountArr;
                    }
                    throw new AuthenticatorException("no result in response");
                }
            }.c();
        }
        throw new IllegalArgumentException("type is null");
    }

    public boolean addAccountExplicitly(Account account, String str, Bundle bundle) {
        if (account != null) {
            return this.c.addAccount(account, str, bundle);
        }
        throw new IllegalArgumentException("account is null");
    }

    public AccountManagerFuture<Boolean> removeAccount(final Account account, AccountManagerCallback<Boolean> accountManagerCallback, Handler handler) {
        if (account != null) {
            return new b<Boolean>(handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.8
                @Override // com.xiaomi.accounts.AccountManager.BaseFutureTask
                public void a() throws RemoteException {
                    AccountManager.this.c.removeAccount(this.d, account);
                }

                /* renamed from: a */
                public Boolean b(Bundle bundle) throws AuthenticatorException {
                    if (bundle.containsKey("booleanResult")) {
                        return Boolean.valueOf(bundle.getBoolean("booleanResult"));
                    }
                    throw new AuthenticatorException("no result in response");
                }
            }.c();
        }
        throw new IllegalArgumentException("account is null");
    }

    public void invalidateAuthToken(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        } else if (str2 != null) {
            this.c.invalidateAuthToken(str, str2);
        }
    }

    public String peekAuthToken(Account account, String str) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        } else if (str != null) {
            return this.c.peekAuthToken(account, str);
        } else {
            throw new IllegalArgumentException("authTokenType is null");
        }
    }

    public void setPassword(Account account, String str) {
        if (account != null) {
            this.c.setPassword(account, str);
            return;
        }
        throw new IllegalArgumentException("account is null");
    }

    public void clearPassword(Account account) {
        if (account != null) {
            this.c.clearPassword(account);
            return;
        }
        throw new IllegalArgumentException("account is null");
    }

    public void setUserData(Account account, String str, String str2) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        } else if (str != null) {
            this.c.setUserData(account, str, str2);
        } else {
            throw new IllegalArgumentException("key is null");
        }
    }

    public void setAuthToken(Account account, String str, String str2) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        } else if (str != null) {
            this.c.setAuthToken(account, str, str2);
        } else {
            throw new IllegalArgumentException("authTokenType is null");
        }
    }

    public String blockingGetAuthToken(Account account, String str, boolean z) throws OperationCanceledException, IOException, AuthenticatorException {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        } else if (str != null) {
            Bundle result = getAuthToken(account, str, z, null, null).getResult();
            if (result != null) {
                return result.getString("authtoken");
            }
            AccountLog.e("AccountManager", "blockingGetAuthToken: null was returned from getResult() for " + account + ", authTokenType " + str);
            return null;
        } else {
            throw new IllegalArgumentException("authTokenType is null");
        }
    }

    public AccountManagerFuture<Bundle> getAuthToken(final Account account, final String str, Bundle bundle, Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        } else if (str != null) {
            final Bundle bundle2 = new Bundle();
            if (bundle != null) {
                bundle2.putAll(bundle);
            }
            bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.a.getPackageName());
            return new a(activity, handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.9
                @Override // com.xiaomi.accounts.AccountManager.a
                public void a() throws RemoteException {
                    AccountManager.this.c.getAuthToken(this.g, account, str, false, true, bundle2);
                }
            }.b();
        } else {
            throw new IllegalArgumentException("authTokenType is null");
        }
    }

    @Deprecated
    public AccountManagerFuture<Bundle> getAuthToken(Account account, String str, boolean z, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        return getAuthToken(account, str, (Bundle) null, z, accountManagerCallback, handler);
    }

    public AccountManagerFuture<Bundle> getAuthToken(final Account account, final String str, Bundle bundle, final boolean z, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        } else if (str != null) {
            final Bundle bundle2 = new Bundle();
            if (bundle != null) {
                bundle2.putAll(bundle);
            }
            bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.a.getPackageName());
            return new a(null, handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.10
                @Override // com.xiaomi.accounts.AccountManager.a
                public void a() throws RemoteException {
                    AccountManager.this.c.getAuthToken(this.g, account, str, z, false, bundle2);
                }
            }.b();
        } else {
            throw new IllegalArgumentException("authTokenType is null");
        }
    }

    public AccountManagerFuture<Bundle> addAccount(final String str, final String str2, final String[] strArr, Bundle bundle, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (str != null) {
            final Bundle bundle2 = new Bundle();
            if (bundle != null) {
                bundle2.putAll(bundle);
            }
            bundle2.putString(KEY_ANDROID_PACKAGE_NAME, this.a.getPackageName());
            return new a(activity, handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.11
                @Override // com.xiaomi.accounts.AccountManager.a
                public void a() throws RemoteException {
                    AccountManager.this.c.addAcount(this.g, str, str2, strArr, activity != null, bundle2);
                }
            }.b();
        }
        throw new IllegalArgumentException("accountType is null");
    }

    public AccountManagerFuture<Bundle> confirmCredentials(final Account account, final Bundle bundle, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (account != null) {
            return new a(activity, handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.12
                @Override // com.xiaomi.accounts.AccountManager.a
                public void a() throws RemoteException {
                    AccountManager.this.c.confirmCredentials(this.g, account, bundle, activity != null);
                }
            }.b();
        }
        throw new IllegalArgumentException("account is null");
    }

    public AccountManagerFuture<Bundle> updateCredentials(final Account account, final String str, final Bundle bundle, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (account != null) {
            return new a(activity, handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.13
                @Override // com.xiaomi.accounts.AccountManager.a
                public void a() throws RemoteException {
                    AccountManager.this.c.updateCredentials(this.g, account, str, activity != null, bundle);
                }
            }.b();
        }
        throw new IllegalArgumentException("account is null");
    }

    public AccountManagerFuture<Bundle> editProperties(final String str, final Activity activity, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (str != null) {
            return new a(activity, handler, accountManagerCallback) { // from class: com.xiaomi.accounts.AccountManager.2
                @Override // com.xiaomi.accounts.AccountManager.a
                public void a() throws RemoteException {
                    AccountManager.this.c.editProperties(this.g, str, activity != null);
                }
            }.b();
        }
        throw new IllegalArgumentException("accountType is null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        Looper myLooper = Looper.myLooper();
        if (myLooper != null && myLooper == this.a.getMainLooper()) {
            IllegalStateException illegalStateException = new IllegalStateException("calling this from your main thread can lead to deadlock");
            AccountLog.e("AccountManager", "calling this from your main thread can lead to deadlock and/or ANRs", illegalStateException);
            if (this.a.getApplicationInfo().targetSdkVersion >= 8) {
                throw illegalStateException;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Handler handler, final AccountManagerCallback<Bundle> accountManagerCallback, final AccountManagerFuture<Bundle> accountManagerFuture) {
        if (handler == null) {
            handler = this.b;
        }
        handler.post(new Runnable() { // from class: com.xiaomi.accounts.AccountManager.3
            @Override // java.lang.Runnable
            public void run() {
                accountManagerCallback.run(accountManagerFuture);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Handler handler, final OnAccountsUpdateListener onAccountsUpdateListener, Account[] accountArr) {
        final Account[] accountArr2 = new Account[accountArr.length];
        System.arraycopy(accountArr, 0, accountArr2, 0, accountArr2.length);
        if (handler == null) {
            handler = this.b;
        }
        handler.post(new Runnable() { // from class: com.xiaomi.accounts.AccountManager.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    onAccountsUpdateListener.onAccountsUpdated(accountArr2);
                } catch (SQLException e) {
                    AccountLog.e("AccountManager", "Can't update accounts", e);
                }
            }
        });
    }

    /* loaded from: classes2.dex */
    private abstract class a extends FutureTask<Bundle> implements AccountManagerFuture<Bundle> {
        final IAccountManagerResponse g = new BinderC0151a();
        final Handler h;
        final AccountManagerCallback<Bundle> i;
        final WeakReference<Activity> j;

        public abstract void a() throws RemoteException;

        public a(Activity activity, Handler handler, AccountManagerCallback<Bundle> accountManagerCallback) {
            super(new Callable<Bundle>() { // from class: com.xiaomi.accounts.AccountManager.a.1
                /* renamed from: a */
                public Bundle call() throws Exception {
                    throw new IllegalStateException("this should never be called");
                }
            });
            this.h = handler;
            this.i = accountManagerCallback;
            this.j = new WeakReference<>(activity);
        }

        public final AccountManagerFuture<Bundle> b() {
            try {
                a();
            } catch (RemoteException e) {
                setException(e);
            }
            return this;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public void set(Bundle bundle) {
            if (bundle == null) {
                AccountLog.e("AccountManager", "the bundle must not be null", new Exception());
            }
            super.set(bundle);
        }

        private Bundle a(Long l, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
            if (!isDone()) {
                AccountManager.this.a();
            }
            try {
                try {
                    try {
                        if (l == null) {
                            return get();
                        }
                        return get(l.longValue(), timeUnit);
                    } finally {
                        cancel(true);
                    }
                } catch (InterruptedException | TimeoutException unused) {
                    cancel(true);
                    throw new OperationCanceledException();
                }
            } catch (CancellationException unused2) {
                throw new OperationCanceledException();
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof IOException) {
                    throw ((IOException) cause);
                } else if (cause instanceof UnsupportedOperationException) {
                    throw new AuthenticatorException(cause);
                } else if (cause instanceof AuthenticatorException) {
                    throw ((AuthenticatorException) cause);
                } else if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                } else if (cause instanceof Error) {
                    throw ((Error) cause);
                } else {
                    throw new IllegalStateException(cause);
                }
            }
        }

        /* renamed from: c */
        public Bundle getResult() throws OperationCanceledException, IOException, AuthenticatorException {
            return a((Long) null, (TimeUnit) null);
        }

        /* renamed from: a */
        public Bundle getResult(long j, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
            return a(Long.valueOf(j), timeUnit);
        }

        @Override // java.util.concurrent.FutureTask
        protected void done() {
            AccountManagerCallback<Bundle> accountManagerCallback = this.i;
            if (accountManagerCallback != null) {
                AccountManager.this.a(this.h, accountManagerCallback, this);
            }
        }

        /* renamed from: com.xiaomi.accounts.AccountManager$a$a  reason: collision with other inner class name */
        /* loaded from: classes2.dex */
        private class BinderC0151a extends IAccountManagerResponse.Stub {
            @Override // com.xiaomi.accounts.IAccountManagerResponse
            public void onRequestContinued() throws RemoteException {
            }

            private BinderC0151a() {
            }

            @Override // com.xiaomi.accounts.IAccountManagerResponse
            public void onResult(Bundle bundle) {
                Intent intent = (Intent) bundle.getParcelable("intent");
                if (intent != null && a.this.j.get() != null) {
                    a.this.j.get().startActivity(intent);
                } else if (bundle.getBoolean(com.xiaomi.onetrack.api.b.M)) {
                    try {
                        a.this.a();
                    } catch (RemoteException unused) {
                    }
                } else {
                    a.this.set(bundle);
                }
            }

            @Override // com.xiaomi.accounts.IAccountManagerResponse
            public void onError(int i, String str) {
                if (i == 4) {
                    a.this.cancel(true);
                    return;
                }
                a aVar = a.this;
                aVar.setException(AccountManager.this.a(i, str));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public abstract class BaseFutureTask<T> extends FutureTask<T> {
        public final IAccountManagerResponse d = new Response();
        final Handler e;

        public abstract void a() throws RemoteException;

        public abstract T b(Bundle bundle) throws AuthenticatorException;

        public BaseFutureTask(Handler handler) {
            super(new Callable<T>() { // from class: com.xiaomi.accounts.AccountManager.BaseFutureTask.1
                @Override // java.util.concurrent.Callable
                public T call() throws Exception {
                    throw new IllegalStateException("this should never be called");
                }
            });
            this.e = handler;
        }

        protected void a(Runnable runnable) {
            Handler handler = this.e;
            if (handler == null) {
                handler = AccountManager.this.b;
            }
            handler.post(runnable);
        }

        protected void b() {
            try {
                a();
            } catch (RemoteException e) {
                setException(e);
            }
        }

        /* loaded from: classes2.dex */
        protected class Response extends IAccountManagerResponse.Stub {
            @Override // com.xiaomi.accounts.IAccountManagerResponse
            public void onRequestContinued() throws RemoteException {
            }

            protected Response() {
            }

            @Override // com.xiaomi.accounts.IAccountManagerResponse
            public void onResult(Bundle bundle) {
                try {
                    Object b = BaseFutureTask.this.b(bundle);
                    if (b != null) {
                        BaseFutureTask.this.set(b);
                    }
                } catch (AuthenticatorException | ClassCastException unused) {
                    onError(5, "no result in response");
                }
            }

            @Override // com.xiaomi.accounts.IAccountManagerResponse
            public void onError(int i, String str) {
                if (i == 4) {
                    BaseFutureTask.this.cancel(true);
                    return;
                }
                BaseFutureTask baseFutureTask = BaseFutureTask.this;
                baseFutureTask.setException(AccountManager.this.a(i, str));
            }
        }
    }

    /* loaded from: classes2.dex */
    private abstract class b<T> extends BaseFutureTask<T> implements AccountManagerFuture<T> {
        final AccountManagerCallback<T> g;

        public b(Handler handler, AccountManagerCallback<T> accountManagerCallback) {
            super(handler);
            this.g = accountManagerCallback;
        }

        @Override // java.util.concurrent.FutureTask
        protected void done() {
            if (this.g != null) {
                a(new Runnable() { // from class: com.xiaomi.accounts.AccountManager.b.1
                    @Override // java.lang.Runnable
                    public void run() {
                        b.this.g.run(b.this);
                    }
                });
            }
        }

        public b<T> c() {
            b();
            return this;
        }

        private T a(Long l, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
            if (!isDone()) {
                AccountManager.this.a();
            }
            try {
                try {
                    try {
                        if (l == null) {
                            return (T) get();
                        }
                        return (T) get(l.longValue(), timeUnit);
                    } catch (ExecutionException e) {
                        Throwable cause = e.getCause();
                        if (cause instanceof IOException) {
                            throw ((IOException) cause);
                        } else if (cause instanceof UnsupportedOperationException) {
                            throw new AuthenticatorException(cause);
                        } else if (cause instanceof AuthenticatorException) {
                            throw ((AuthenticatorException) cause);
                        } else if (cause instanceof RuntimeException) {
                            throw ((RuntimeException) cause);
                        } else if (cause instanceof Error) {
                            throw ((Error) cause);
                        } else {
                            throw new IllegalStateException(cause);
                        }
                    }
                } catch (InterruptedException | CancellationException | TimeoutException unused) {
                    cancel(true);
                    throw new OperationCanceledException();
                }
            } finally {
                cancel(true);
            }
        }

        @Override // android.accounts.AccountManagerFuture
        public T getResult() throws OperationCanceledException, IOException, AuthenticatorException {
            return a((Long) null, (TimeUnit) null);
        }

        @Override // android.accounts.AccountManagerFuture
        public T getResult(long j, TimeUnit timeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
            return a(Long.valueOf(j), timeUnit);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Exception a(int i, String str) {
        if (i == 3) {
            return new IOException(str);
        }
        if (i == 6) {
            return new UnsupportedOperationException(str);
        }
        if (i == 5) {
            return new AuthenticatorException(str);
        }
        if (i == 7) {
            return new IllegalArgumentException(str);
        }
        return new AuthenticatorException(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class c extends a implements AccountManagerCallback<Bundle> {
        final String b;
        final String c;
        final String[] d;
        final Bundle e;
        final Bundle f;
        final AccountManagerCallback<Bundle> l;
        volatile AccountManagerFuture<Bundle> a = null;
        private volatile int n = 0;

        c(String str, String str2, String[] strArr, Activity activity, Bundle bundle, Bundle bundle2, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
            super(activity, handler, accountManagerCallback);
            if (str != null) {
                this.b = str;
                this.c = str2;
                this.d = strArr;
                this.e = bundle;
                this.f = bundle2;
                this.l = this;
                return;
            }
            throw new IllegalArgumentException("account type is null");
        }

        @Override // com.xiaomi.accounts.AccountManager.a
        public void a() throws RemoteException {
            AccountManager.this.getAccountsByTypeAndFeatures(this.b, this.d, new AccountManagerCallback<Account[]>() { // from class: com.xiaomi.accounts.AccountManager.c.1
                @Override // android.accounts.AccountManagerCallback
                public void run(AccountManagerFuture<Account[]> accountManagerFuture) {
                    try {
                        Account[] result = accountManagerFuture.getResult();
                        c.this.n = result.length;
                        try {
                            if (result.length == 0) {
                                if (c.this.j.get() != null) {
                                    c cVar = c.this;
                                    cVar.a = AccountManager.this.addAccount(c.this.b, c.this.c, c.this.d, c.this.e, (Activity) c.this.j.get(), c.this.l, c.this.h);
                                    return;
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString("authAccount", null);
                                bundle.putString("accountType", null);
                                bundle.putString("authtoken", null);
                                c.this.g.onResult(bundle);
                            } else if (result.length == 1) {
                                if (c.this.j == null) {
                                    c cVar2 = c.this;
                                    cVar2.a = AccountManager.this.getAuthToken(result[0], c.this.c, false, c.this.l, c.this.h);
                                    return;
                                }
                                c cVar3 = c.this;
                                cVar3.a = AccountManager.this.getAuthToken(result[0], c.this.c, c.this.f, (Activity) c.this.j.get(), c.this.l, c.this.h);
                            } else if (c.this.j != null) {
                                IAccountManagerResponse.Stub stub = new IAccountManagerResponse.Stub() { // from class: com.xiaomi.accounts.AccountManager.c.1.1
                                    @Override // com.xiaomi.accounts.IAccountManagerResponse
                                    public void onRequestContinued() throws RemoteException {
                                    }

                                    @Override // com.xiaomi.accounts.IAccountManagerResponse
                                    public void onResult(Bundle bundle2) throws RemoteException {
                                        Account account = new Account(bundle2.getString("authAccount"), bundle2.getString("accountType"));
                                        c.this.a = AccountManager.this.getAuthToken(account, c.this.c, c.this.f, (Activity) c.this.j.get(), c.this.l, c.this.h);
                                    }

                                    @Override // com.xiaomi.accounts.IAccountManagerResponse
                                    public void onError(int i, String str) throws RemoteException {
                                        c.this.g.onError(i, str);
                                    }
                                };
                                Intent intent = new Intent();
                                intent.setClassName("android", "android.accounts.ChooseAccountActivity");
                                intent.putExtra("accounts", result);
                                intent.putExtra("accountManagerResponse", new AccountManagerResponse(stub));
                                ((Activity) c.this.j.get()).startActivity(intent);
                            } else {
                                Bundle bundle2 = new Bundle();
                                bundle2.putString("accounts", null);
                                c.this.g.onResult(bundle2);
                            }
                        } catch (RemoteException unused) {
                        }
                    } catch (AuthenticatorException e) {
                        c.this.setException(e);
                    } catch (OperationCanceledException e2) {
                        c.this.setException(e2);
                    } catch (IOException e3) {
                        c.this.setException(e3);
                    }
                }
            }, this.h);
        }

        @Override // android.accounts.AccountManagerCallback
        public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
            try {
                Bundle result = accountManagerFuture.getResult();
                if (this.n == 0) {
                    String string = result.getString("authAccount");
                    String string2 = result.getString("accountType");
                    if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                        Account account = new Account(string, string2);
                        this.n = 1;
                        AccountManager.this.getAuthToken(account, this.c, (Bundle) null, (Activity) this.j.get(), this.l, this.h);
                        return;
                    }
                    setException(new AuthenticatorException("account not in result"));
                    return;
                }
                set(result);
            } catch (AuthenticatorException e) {
                setException(e);
            } catch (OperationCanceledException unused) {
                cancel(true);
            } catch (IOException e2) {
                setException(e2);
            }
        }
    }

    public AccountManagerFuture<Bundle> getAuthTokenByFeatures(String str, String str2, String[] strArr, Activity activity, Bundle bundle, Bundle bundle2, AccountManagerCallback<Bundle> accountManagerCallback, Handler handler) {
        if (str == null) {
            throw new IllegalArgumentException("account type is null");
        } else if (str2 != null) {
            c cVar = new c(str, str2, strArr, activity, bundle, bundle2, accountManagerCallback, handler);
            cVar.b();
            return cVar;
        } else {
            throw new IllegalArgumentException("authTokenType is null");
        }
    }

    public void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, Handler handler, boolean z) {
        if (onAccountsUpdateListener != null) {
            synchronized (this.e) {
                if (!this.e.containsKey(onAccountsUpdateListener)) {
                    boolean isEmpty = this.e.isEmpty();
                    this.e.put(onAccountsUpdateListener, handler);
                    if (isEmpty) {
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction(LOGIN_ACCOUNTS_CHANGED_ACTION);
                        intentFilter.addAction("android.intent.action.DEVICE_STORAGE_OK");
                        this.a.registerReceiver(this.f, intentFilter);
                    }
                } else {
                    throw new IllegalStateException("this listener is already added");
                }
            }
            if (z) {
                a(handler, onAccountsUpdateListener, getAccounts());
                return;
            }
            return;
        }
        throw new IllegalArgumentException("the listener is null");
    }

    public void removeOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener) {
        if (onAccountsUpdateListener != null) {
            synchronized (this.e) {
                if (!this.e.containsKey(onAccountsUpdateListener)) {
                    AccountLog.e("AccountManager", "Listener was not previously added");
                    return;
                }
                this.e.remove(onAccountsUpdateListener);
                if (this.e.isEmpty()) {
                    this.a.unregisterReceiver(this.f);
                }
                return;
            }
        }
        throw new IllegalArgumentException("listener is null");
    }
}
