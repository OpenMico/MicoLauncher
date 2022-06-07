package com.xiaomi.accounts;

import android.accounts.Account;
import android.accounts.AuthenticatorDescription;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.xiaomi.accounts.AccountAuthenticatorCache;
import com.xiaomi.accounts.IAccountAuthenticator;
import com.xiaomi.accounts.IAccountAuthenticatorResponse;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.OneTrack;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes2.dex */
public class AccountManagerService {
    private final Context b;
    private final PackageManager c;
    private HandlerThread d;
    private final c e;
    private final AccountAuthenticatorCache f;
    private final LinkedHashMap<String, e> j;
    private final SparseArray<g> k;
    private static final ExecutorService a = Executors.newCachedThreadPool();
    private static final String[] h = {"type", "authtoken"};
    private static final String[] i = {"key", com.xiaomi.onetrack.api.b.p};
    private static AtomicReference<AccountManagerService> l = new AtomicReference<>();
    private static final Account[] m = new Account[0];
    private static final Intent g = new Intent(AccountManager.LOGIN_ACCOUNTS_CHANGED_ACTION);

    private void a(long j) {
    }

    private long d() {
        return 0L;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class g {
        private final int a;
        private final a b;
        private final Object c = new Object();
        private final HashMap<String, Account[]> d = new LinkedHashMap();
        private HashMap<Account, HashMap<String, String>> e = new HashMap<>();
        private HashMap<Account, HashMap<String, String>> f = new HashMap<>();

        g(Context context, int i) {
            this.a = i;
            synchronized (this.c) {
                this.b = new a(context, i);
            }
        }
    }

    public static AccountManagerService getSingleton() {
        return l.get();
    }

    public AccountManagerService(Context context) {
        this(context, context.getPackageManager(), new AccountAuthenticatorCache(context));
    }

    public AccountManagerService(Context context, PackageManager packageManager, AccountAuthenticatorCache accountAuthenticatorCache) {
        this.j = new LinkedHashMap<>();
        this.k = new SparseArray<>();
        this.b = context;
        this.c = packageManager;
        this.d = new HandlerThread("AccountManagerService");
        this.d.start();
        this.e = new c(this.d.getLooper());
        this.f = accountAuthenticatorCache;
        l.set(this);
        a(0);
    }

    private g a(int i2) {
        g gVar;
        synchronized (this.k) {
            gVar = this.k.get(i2);
            if (gVar == null) {
                gVar = new g(this.b, i2);
                this.k.append(i2, gVar);
                a(gVar);
                b(gVar);
            }
        }
        return gVar;
    }

    private void a(g gVar) {
        synchronized (gVar.c) {
            SQLiteDatabase writableDatabase = gVar.b.getWritableDatabase();
            Cursor query = writableDatabase.query("grants", new String[]{OneTrack.Param.UID}, null, null, OneTrack.Param.UID, null, null);
            while (query.moveToNext()) {
                int i2 = query.getInt(0);
                if (!(this.c.getPackagesForUid(i2) != null)) {
                    AccountLog.d("AccountManagerService", "deleting grants for UID " + i2 + " because its package is no longer installed");
                    writableDatabase.delete("grants", "uid=?", new String[]{Integer.toString(i2)});
                }
            }
            query.close();
        }
    }

    private void b(g gVar) {
        boolean z;
        Throwable th;
        synchronized (gVar.c) {
            SQLiteDatabase writableDatabase = gVar.b.getWritableDatabase();
            Cursor query = writableDatabase.query("accounts", new String[]{"_id", "type", "name"}, null, null, null, null, null);
            try {
                gVar.d.clear();
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                z = false;
                while (query.moveToNext()) {
                    try {
                        long j = query.getLong(0);
                        String string = query.getString(1);
                        String string2 = query.getString(2);
                        if (this.f.getServiceInfo(AuthenticatorDescription.newKey(string)) == null) {
                            AccountLog.d("AccountManagerService", "deleting account " + string2 + " because type " + string + " no longer has a registered authenticator");
                            StringBuilder sb = new StringBuilder();
                            sb.append("_id=");
                            sb.append(j);
                            writableDatabase.delete("accounts", sb.toString(), null);
                            try {
                                Account account = new Account(string2, string);
                                gVar.e.remove(account);
                                gVar.f.remove(account);
                                z = true;
                            } catch (Throwable th2) {
                                th = th2;
                                z = true;
                                query.close();
                                if (z) {
                                    b(gVar.a);
                                }
                                throw th;
                            }
                        } else {
                            ArrayList arrayList = (ArrayList) linkedHashMap.get(string);
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                                linkedHashMap.put(string, arrayList);
                            }
                            arrayList.add(string2);
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                }
                for (Map.Entry entry : linkedHashMap.entrySet()) {
                    String str = (String) entry.getKey();
                    ArrayList arrayList2 = (ArrayList) entry.getValue();
                    Account[] accountArr = new Account[arrayList2.size()];
                    Iterator it = arrayList2.iterator();
                    int i2 = 0;
                    while (it.hasNext()) {
                        accountArr[i2] = new Account((String) it.next(), str);
                        i2++;
                    }
                    gVar.d.put(str, accountArr);
                }
                query.close();
                if (z) {
                    b(gVar.a);
                }
            } catch (Throwable th4) {
                th = th4;
                z = false;
            }
        }
    }

    private g b() {
        return getUserAccounts(UserId.getCallingUserId());
    }

    protected g getUserAccounts(int i2) {
        g gVar;
        synchronized (this.k) {
            gVar = this.k.get(i2);
            if (gVar == null) {
                gVar = a(i2);
                this.k.append(i2, gVar);
            }
        }
        return gVar;
    }

    public String getPassword(Account account) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "getPassword: " + account + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (account != null) {
            g b2 = b();
            long d2 = d();
            try {
                return a(b2, account);
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("account is null");
        }
    }

    private String a(g gVar, Account account) {
        if (account == null) {
            return null;
        }
        synchronized (gVar.c) {
            Cursor query = gVar.b.getReadableDatabase().query("accounts", new String[]{"password"}, "name=? AND type=?", new String[]{account.name, account.type}, null, null, null);
            if (query.moveToNext()) {
                String string = query.getString(0);
                query.close();
                return string;
            }
            query.close();
            return null;
        }
    }

    public String getUserData(Account account, String str) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "getUserData: " + account + ", key " + str + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        } else if (str != null) {
            g b2 = b();
            long d2 = d();
            try {
                return readUserDataInternal(b2, account, str);
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("key is null");
        }
    }

    public AuthenticatorDescription[] getAuthenticatorTypes() {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "getAuthenticatorTypes: caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        long d2 = d();
        try {
            Collection<AccountAuthenticatorCache.ServiceInfo<AuthenticatorDescription>> allServices = this.f.getAllServices();
            AuthenticatorDescription[] authenticatorDescriptionArr = new AuthenticatorDescription[allServices.size()];
            int i2 = 0;
            for (AccountAuthenticatorCache.ServiceInfo<AuthenticatorDescription> serviceInfo : allServices) {
                authenticatorDescriptionArr[i2] = serviceInfo.type;
                i2++;
            }
            return authenticatorDescriptionArr;
        } finally {
            a(d2);
        }
    }

    public boolean addAccount(Account account, String str, Bundle bundle) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "addAccount: " + account + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (account != null) {
            g b2 = b();
            long d2 = d();
            try {
                return a(b2, account, str, bundle);
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("account is null");
        }
    }

    private boolean a(g gVar, Account account, String str, Bundle bundle) {
        if (account == null) {
            return false;
        }
        try {
            synchronized (gVar.c) {
                try {
                    SQLiteDatabase writableDatabase = gVar.b.getWritableDatabase();
                    writableDatabase.beginTransaction();
                    if (DatabaseUtils.longForQuery(writableDatabase, "select count(*) from accounts WHERE name=? AND type=?", new String[]{account.name, account.type}) > 0) {
                        AccountLog.w("AccountManagerService", "insertAccountIntoDatabase: " + account + ", skipping since the account already exists");
                        writableDatabase.endTransaction();
                        return false;
                    }
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", account.name);
                    contentValues.put("type", account.type);
                    contentValues.put("password", str);
                    long insert = writableDatabase.insert("accounts", "name", contentValues);
                    if (insert < 0) {
                        AccountLog.w("AccountManagerService", "insertAccountIntoDatabase: " + account + ", skipping the DB insert failed");
                        writableDatabase.endTransaction();
                        return false;
                    }
                    if (bundle != null) {
                        for (String str2 : bundle.keySet()) {
                            if (a(writableDatabase, insert, str2, bundle.getString(str2)) < 0) {
                                AccountLog.w("AccountManagerService", "insertAccountIntoDatabase: " + account + ", skipping since insertExtra failed for key " + str2);
                                writableDatabase.endTransaction();
                                return false;
                            }
                        }
                    }
                    writableDatabase.setTransactionSuccessful();
                    d(gVar, account);
                    writableDatabase.endTransaction();
                    b(gVar.a);
                    return true;
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private long a(SQLiteDatabase sQLiteDatabase, long j, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("key", str);
        contentValues.put("accounts_id", Long.valueOf(j));
        contentValues.put(com.xiaomi.onetrack.api.b.p, str2);
        return sQLiteDatabase.insert("extras", "key", contentValues);
    }

    public void hasFeatures(IAccountManagerResponse iAccountManagerResponse, Account account, String[] strArr) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "hasFeatures: " + account + ", response " + iAccountManagerResponse + ", features " + a(strArr) + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        } else if (account == null) {
            throw new IllegalArgumentException("account is null");
        } else if (strArr != null) {
            g b2 = b();
            long d2 = d();
            try {
                new f(b2, iAccountManagerResponse, account, strArr).f();
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("features is null");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class f extends e {
        private final String[] b;
        private final Account c;

        public f(g gVar, IAccountManagerResponse iAccountManagerResponse, Account account, String[] strArr) {
            super(gVar, iAccountManagerResponse, account.type, false, true);
            this.b = strArr;
            this.c = account;
        }

        @Override // com.xiaomi.accounts.AccountManagerService.e
        public void a() throws RemoteException {
            try {
                this.k.hasFeatures(this, this.c, this.b);
            } catch (RemoteException unused) {
                onError(1, "remote exception");
            }
        }

        @Override // com.xiaomi.accounts.AccountManagerService.e, com.xiaomi.accounts.IAccountAuthenticatorResponse
        public void onResult(Bundle bundle) {
            IAccountManagerResponse d = d();
            if (d != null) {
                try {
                    if (bundle == null) {
                        d.onError(5, "null bundle");
                        return;
                    }
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        AccountLog.v("AccountManagerService", getClass().getSimpleName() + " calling onResult() on response " + d);
                    }
                    Bundle bundle2 = new Bundle();
                    bundle2.putBoolean("booleanResult", bundle.getBoolean("booleanResult", false));
                    d.onResult(bundle2);
                } catch (RemoteException e) {
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        AccountLog.v("AccountManagerService", "failure while notifying response", e);
                    }
                }
            }
        }

        @Override // com.xiaomi.accounts.AccountManagerService.e
        protected String a(long j) {
            StringBuilder sb = new StringBuilder();
            sb.append(super.a(j));
            sb.append(", hasFeatures, ");
            sb.append(this.c);
            sb.append(", ");
            String[] strArr = this.b;
            sb.append(strArr != null ? TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, strArr) : null);
            return sb.toString();
        }
    }

    public void removeAccount(IAccountManagerResponse iAccountManagerResponse, Account account) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "removeAccount: " + account + ", response " + iAccountManagerResponse + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        } else if (account != null) {
            g b2 = b();
            long d2 = d();
            try {
                new d(b2, iAccountManagerResponse, account).f();
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("account is null");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class d extends e {
        final Account a;

        public d(g gVar, IAccountManagerResponse iAccountManagerResponse, Account account) {
            super(gVar, iAccountManagerResponse, account.type, false, true);
            this.a = account;
        }

        @Override // com.xiaomi.accounts.AccountManagerService.e
        protected String a(long j) {
            return super.a(j) + ", removeAccount, account " + this.a;
        }

        @Override // com.xiaomi.accounts.AccountManagerService.e
        public void a() throws RemoteException {
            this.k.getAccountRemovalAllowed(this, this.a);
        }

        @Override // com.xiaomi.accounts.AccountManagerService.e, com.xiaomi.accounts.IAccountAuthenticatorResponse
        public void onResult(Bundle bundle) {
            if (bundle != null && bundle.containsKey("booleanResult") && !bundle.containsKey("intent")) {
                boolean z = bundle.getBoolean("booleanResult");
                if (z) {
                    AccountManagerService.this.b(this.l, this.a);
                }
                IAccountManagerResponse d = d();
                if (d != null) {
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        AccountLog.v("AccountManagerService", getClass().getSimpleName() + " calling onResult() on response " + d);
                    }
                    Bundle bundle2 = new Bundle();
                    bundle2.putBoolean("booleanResult", z);
                    try {
                        d.onResult(bundle2);
                    } catch (RemoteException unused) {
                    }
                }
            }
            super.onResult(bundle);
        }
    }

    protected void removeAccountInternal(Account account) {
        b(b(), account);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(g gVar, Account account) {
        synchronized (gVar.c) {
            gVar.b.getWritableDatabase().delete("accounts", "name=? AND type=?", new String[]{account.name, account.type});
            c(gVar, account);
            b(gVar.a);
        }
    }

    public void invalidateAuthToken(String str, String str2) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "invalidateAuthToken: accountType " + str + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        } else if (str2 != null) {
            g b2 = b();
            long d2 = d();
            try {
                synchronized (b2.c) {
                    SQLiteDatabase writableDatabase = b2.b.getWritableDatabase();
                    writableDatabase.beginTransaction();
                    a(b2, writableDatabase, str, str2);
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                }
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("authToken is null");
        }
    }

    private void a(g gVar, SQLiteDatabase sQLiteDatabase, String str, String str2) {
        if (str2 != null && str != null) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT authtokens._id, accounts.name, authtokens.type FROM accounts JOIN authtokens ON accounts._id = accounts_id WHERE authtoken = ? AND accounts.type = ?", new String[]{str2, str});
            while (rawQuery.moveToNext()) {
                try {
                    long j = rawQuery.getLong(0);
                    String string = rawQuery.getString(1);
                    String string2 = rawQuery.getString(2);
                    sQLiteDatabase.delete("authtokens", "_id=" + j, null);
                    writeAuthTokenIntoCacheLocked(gVar, sQLiteDatabase, new Account(string, str), string2, null);
                } finally {
                    rawQuery.close();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(g gVar, Account account, String str, String str2) {
        if (account == null || str == null) {
            return false;
        }
        synchronized (gVar.c) {
            SQLiteDatabase writableDatabase = gVar.b.getWritableDatabase();
            writableDatabase.beginTransaction();
            long a2 = a(writableDatabase, account);
            if (a2 < 0) {
                writableDatabase.endTransaction();
                return false;
            }
            writableDatabase.delete("authtokens", "accounts_id=" + a2 + " AND type=?", new String[]{str});
            ContentValues contentValues = new ContentValues();
            contentValues.put("accounts_id", Long.valueOf(a2));
            contentValues.put("type", str);
            contentValues.put("authtoken", str2);
            if (writableDatabase.insert("authtokens", "authtoken", contentValues) >= 0) {
                writableDatabase.setTransactionSuccessful();
                writeAuthTokenIntoCacheLocked(gVar, writableDatabase, account, str, str2);
                writableDatabase.endTransaction();
                return true;
            }
            writableDatabase.endTransaction();
            return false;
        }
    }

    public String peekAuthToken(Account account, String str) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "peekAuthToken: " + account + ", authTokenType " + str + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        } else if (str != null) {
            g b2 = b();
            long d2 = d();
            try {
                return readAuthTokenInternal(b2, account, str);
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("authTokenType is null");
        }
    }

    public void setAuthToken(Account account, String str, String str2) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "setAuthToken: " + account + ", authTokenType " + str + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (account == null) {
            throw new IllegalArgumentException("account is null");
        } else if (str != null) {
            g b2 = b();
            long d2 = d();
            try {
                a(b2, account, str, str2);
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("authTokenType is null");
        }
    }

    public void setPassword(Account account, String str) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "setPassword: " + account + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (account != null) {
            g b2 = b();
            long d2 = d();
            try {
                a(b2, account, str);
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("account is null");
        }
    }

    private void a(g gVar, Account account, String str) {
        if (account != null) {
            synchronized (gVar.c) {
                SQLiteDatabase writableDatabase = gVar.b.getWritableDatabase();
                writableDatabase.beginTransaction();
                ContentValues contentValues = new ContentValues();
                contentValues.put("password", str);
                long a2 = a(writableDatabase, account);
                if (a2 >= 0) {
                    String[] strArr = {String.valueOf(a2)};
                    writableDatabase.update("accounts", contentValues, "_id=?", strArr);
                    writableDatabase.delete("authtokens", "accounts_id=?", strArr);
                    gVar.f.remove(account);
                    writableDatabase.setTransactionSuccessful();
                }
                writableDatabase.endTransaction();
                b(gVar.a);
            }
        }
    }

    private void b(int i2) {
        AccountLog.i("AccountManagerService", "the accounts changed, sending broadcast of " + g.getAction());
        g.setPackage(this.b.getPackageName());
        this.b.sendBroadcast(g);
    }

    public void clearPassword(Account account) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "clearPassword: " + account + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (account != null) {
            g b2 = b();
            long d2 = d();
            try {
                a(b2, account, (String) null);
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("account is null");
        }
    }

    public void setUserData(Account account, String str, String str2) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "setUserData: " + account + ", key " + str + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (str == null) {
            throw new IllegalArgumentException("key is null");
        } else if (account != null) {
            g b2 = b();
            long d2 = d();
            try {
                b(b2, account, str, str2);
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("account is null");
        }
    }

    private void b(g gVar, Account account, String str, String str2) {
        if (account != null && str != null) {
            synchronized (gVar.c) {
                SQLiteDatabase writableDatabase = gVar.b.getWritableDatabase();
                writableDatabase.beginTransaction();
                long a2 = a(writableDatabase, account);
                if (a2 < 0) {
                    writableDatabase.endTransaction();
                    return;
                }
                long a3 = a(writableDatabase, a2, str);
                if (a3 >= 0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(com.xiaomi.onetrack.api.b.p, str2);
                    if (1 != writableDatabase.update("extras", contentValues, "_id=" + a3, null)) {
                        writableDatabase.endTransaction();
                        return;
                    }
                } else if (a(writableDatabase, a2, str, str2) < 0) {
                    writableDatabase.endTransaction();
                    return;
                }
                writeUserDataIntoCacheLocked(gVar, writableDatabase, account, str, str2);
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
            }
        }
    }

    private void a(IAccountManagerResponse iAccountManagerResponse, Bundle bundle) {
        if (bundle == null) {
            AccountLog.e("AccountManagerService", "the result is unexpectedly null", new Exception());
        }
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", getClass().getSimpleName() + " calling onResult() on response " + iAccountManagerResponse);
        }
        try {
            iAccountManagerResponse.onResult(bundle);
        } catch (RemoteException e2) {
            if (Log.isLoggable("AccountManagerService", 2)) {
                AccountLog.v("AccountManagerService", "failure while notifying response", e2);
            }
        }
    }

    public void getAuthTokenLabel(IAccountManagerResponse iAccountManagerResponse, final String str, final String str2) throws RemoteException {
        if (str == null) {
            throw new IllegalArgumentException("accountType is null");
        } else if (str2 != null) {
            int c2 = c();
            d();
            if (c2 == 1000) {
                g userAccounts = getUserAccounts(UserId.getUserId(c2));
                long d2 = d();
                try {
                    new e(userAccounts, iAccountManagerResponse, str, false, false) { // from class: com.xiaomi.accounts.AccountManagerService.1
                        @Override // com.xiaomi.accounts.AccountManagerService.e
                        protected String a(long j) {
                            return super.a(j) + ", getAuthTokenLabel, " + str + ", authTokenType " + str2;
                        }

                        @Override // com.xiaomi.accounts.AccountManagerService.e
                        public void a() throws RemoteException {
                            this.k.getAuthTokenLabel(this, str2);
                        }

                        @Override // com.xiaomi.accounts.AccountManagerService.e, com.xiaomi.accounts.IAccountAuthenticatorResponse
                        public void onResult(Bundle bundle) {
                            if (bundle != null) {
                                String string = bundle.getString("authTokenLabelKey");
                                Bundle bundle2 = new Bundle();
                                bundle2.putString("authTokenLabelKey", string);
                                super.onResult(bundle2);
                                return;
                            }
                            super.onResult(bundle);
                        }
                    }.f();
                } finally {
                    a(d2);
                }
            } else {
                throw new SecurityException("can only call from system");
            }
        } else {
            throw new IllegalArgumentException("authTokenType is null");
        }
    }

    public void getAuthToken(IAccountManagerResponse iAccountManagerResponse, final Account account, final String str, final boolean z, boolean z2, Bundle bundle) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "getAuthToken: " + account + ", response " + iAccountManagerResponse + ", authTokenType " + str + ", notifyOnAuthFailure " + z + ", expectActivityLaunch " + z2 + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        } else if (account == null) {
            throw new IllegalArgumentException("account is null");
        } else if (str != null) {
            g b2 = b();
            this.f.getServiceInfo(AuthenticatorDescription.newKey(account.type));
            int callingUid = Binder.getCallingUid();
            final Bundle bundle2 = bundle == null ? new Bundle() : bundle;
            bundle2.putInt(AccountManager.KEY_CALLER_UID, callingUid);
            bundle2.putInt(AccountManager.KEY_CALLER_PID, Binder.getCallingPid());
            if (z) {
                bundle2.putBoolean(AccountManager.KEY_NOTIFY_ON_FAILURE, true);
            }
            long d2 = d();
            try {
                String readAuthTokenInternal = readAuthTokenInternal(b2, account, str);
                if (readAuthTokenInternal != null) {
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("authtoken", readAuthTokenInternal);
                    bundle3.putString("authAccount", account.name);
                    bundle3.putString("accountType", account.type);
                    a(iAccountManagerResponse, bundle3);
                    return;
                }
                new e(b2, iAccountManagerResponse, account.type, z2, false) { // from class: com.xiaomi.accounts.AccountManagerService.2
                    @Override // com.xiaomi.accounts.AccountManagerService.e
                    protected String a(long j) {
                        Bundle bundle4 = bundle2;
                        if (bundle4 != null) {
                            bundle4.keySet();
                        }
                        return super.a(j) + ", getAuthToken, " + account + ", authTokenType " + str + ", loginOptions " + bundle2 + ", notifyOnAuthFailure " + z;
                    }

                    @Override // com.xiaomi.accounts.AccountManagerService.e
                    public void a() throws RemoteException {
                        this.k.getAuthToken(this, account, str, bundle2);
                    }

                    @Override // com.xiaomi.accounts.AccountManagerService.e, com.xiaomi.accounts.IAccountAuthenticatorResponse
                    public void onResult(Bundle bundle4) {
                        String string;
                        if (!(bundle4 == null || (string = bundle4.getString("authtoken")) == null)) {
                            String string2 = bundle4.getString("authAccount");
                            String string3 = bundle4.getString("accountType");
                            if (TextUtils.isEmpty(string3) || TextUtils.isEmpty(string2)) {
                                onError(5, "the type and name should not be empty");
                                return;
                            }
                            AccountManagerService.this.a(this.l, new Account(string2, string3), str, string);
                        }
                        super.onResult(bundle4);
                    }
                }.f();
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("authTokenType is null");
        }
    }

    public void addAcount(IAccountManagerResponse iAccountManagerResponse, final String str, final String str2, final String[] strArr, boolean z, Bundle bundle) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "addAccount: accountType " + str + ", response " + iAccountManagerResponse + ", authTokenType " + str2 + ", requiredFeatures " + a(strArr) + ", expectActivityLaunch " + z + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        } else if (str != null) {
            g b2 = b();
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            final Bundle bundle2 = bundle == null ? new Bundle() : bundle;
            bundle2.putInt(AccountManager.KEY_CALLER_UID, callingUid);
            bundle2.putInt(AccountManager.KEY_CALLER_PID, callingPid);
            long d2 = d();
            try {
                new e(b2, iAccountManagerResponse, str, z, true) { // from class: com.xiaomi.accounts.AccountManagerService.3
                    @Override // com.xiaomi.accounts.AccountManagerService.e
                    public void a() throws RemoteException {
                        this.k.addAccount(this, this.g, str2, strArr, bundle2);
                    }

                    @Override // com.xiaomi.accounts.AccountManagerService.e
                    protected String a(long j) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(super.a(j));
                        sb.append(", addAccount, accountType ");
                        sb.append(str);
                        sb.append(", requiredFeatures ");
                        String[] strArr2 = strArr;
                        sb.append(strArr2 != null ? TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, strArr2) : null);
                        return sb.toString();
                    }
                }.f();
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("accountType is null");
        }
    }

    public void confirmCredentials(IAccountManagerResponse iAccountManagerResponse, final Account account, final Bundle bundle, boolean z) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "confirmCredentials: " + account + ", response " + iAccountManagerResponse + ", expectActivityLaunch " + z + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        } else if (account != null) {
            g b2 = b();
            long d2 = d();
            try {
                new e(b2, iAccountManagerResponse, account.type, z, true) { // from class: com.xiaomi.accounts.AccountManagerService.4
                    @Override // com.xiaomi.accounts.AccountManagerService.e
                    public void a() throws RemoteException {
                        this.k.confirmCredentials(this, account, bundle);
                    }

                    @Override // com.xiaomi.accounts.AccountManagerService.e
                    protected String a(long j) {
                        return super.a(j) + ", confirmCredentials, " + account;
                    }
                }.f();
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("account is null");
        }
    }

    public void updateCredentials(IAccountManagerResponse iAccountManagerResponse, final Account account, final String str, boolean z, final Bundle bundle) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "updateCredentials: " + account + ", response " + iAccountManagerResponse + ", authTokenType " + str + ", expectActivityLaunch " + z + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        } else if (account == null) {
            throw new IllegalArgumentException("account is null");
        } else if (str != null) {
            g b2 = b();
            long d2 = d();
            try {
                new e(b2, iAccountManagerResponse, account.type, z, true) { // from class: com.xiaomi.accounts.AccountManagerService.5
                    @Override // com.xiaomi.accounts.AccountManagerService.e
                    public void a() throws RemoteException {
                        this.k.updateCredentials(this, account, str, bundle);
                    }

                    @Override // com.xiaomi.accounts.AccountManagerService.e
                    protected String a(long j) {
                        Bundle bundle2 = bundle;
                        if (bundle2 != null) {
                            bundle2.keySet();
                        }
                        return super.a(j) + ", updateCredentials, " + account + ", authTokenType " + str + ", loginOptions " + bundle;
                    }
                }.f();
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("authTokenType is null");
        }
    }

    public void editProperties(IAccountManagerResponse iAccountManagerResponse, final String str, boolean z) {
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "editProperties: accountType " + str + ", response " + iAccountManagerResponse + ", expectActivityLaunch " + z + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        } else if (str != null) {
            g b2 = b();
            long d2 = d();
            try {
                new e(b2, iAccountManagerResponse, str, z, true) { // from class: com.xiaomi.accounts.AccountManagerService.6
                    @Override // com.xiaomi.accounts.AccountManagerService.e
                    public void a() throws RemoteException {
                        this.k.editProperties(this, this.g);
                    }

                    @Override // com.xiaomi.accounts.AccountManagerService.e
                    protected String a(long j) {
                        return super.a(j) + ", editProperties, accountType " + str;
                    }
                }.f();
            } finally {
                a(d2);
            }
        } else {
            throw new IllegalArgumentException("accountType is null");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class b extends e {
        private final String[] b;
        private volatile Account[] c = null;
        private volatile ArrayList<Account> d = null;
        private volatile int e = 0;

        public b(g gVar, IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr) {
            super(gVar, iAccountManagerResponse, str, false, true);
            this.b = strArr;
        }

        @Override // com.xiaomi.accounts.AccountManagerService.e
        public void a() throws RemoteException {
            synchronized (this.l.c) {
                this.c = AccountManagerService.this.getAccountsFromCacheLocked(this.l, this.g);
            }
            this.d = new ArrayList<>(this.c.length);
            this.e = 0;
            b();
        }

        public void b() {
            if (this.e >= this.c.length) {
                c();
                return;
            }
            IAccountAuthenticator iAccountAuthenticator = this.k;
            if (iAccountAuthenticator != null) {
                try {
                    iAccountAuthenticator.hasFeatures(this, this.c[this.e], this.b);
                } catch (RemoteException unused) {
                    onError(1, "remote exception");
                }
            } else if (Log.isLoggable("AccountManagerService", 2)) {
                AccountLog.v("AccountManagerService", "checkAccount: aborting session since we are no longer connected to the authenticator, " + e());
            }
        }

        @Override // com.xiaomi.accounts.AccountManagerService.e, com.xiaomi.accounts.IAccountAuthenticatorResponse
        public void onResult(Bundle bundle) {
            this.j++;
            if (bundle == null) {
                onError(5, "null bundle");
                return;
            }
            if (bundle.getBoolean("booleanResult", false)) {
                this.d.add(this.c[this.e]);
            }
            this.e++;
            b();
        }

        public void c() {
            IAccountManagerResponse d = d();
            if (d != null) {
                try {
                    Account[] accountArr = new Account[this.d.size()];
                    for (int i = 0; i < accountArr.length; i++) {
                        accountArr[i] = this.d.get(i);
                    }
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        AccountLog.v("AccountManagerService", getClass().getSimpleName() + " calling onResult() on response " + d);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArray("accounts", accountArr);
                    d.onResult(bundle);
                } catch (RemoteException e) {
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        AccountLog.v("AccountManagerService", "failure while notifying response", e);
                    }
                }
            }
        }

        @Override // com.xiaomi.accounts.AccountManagerService.e
        protected String a(long j) {
            StringBuilder sb = new StringBuilder();
            sb.append(super.a(j));
            sb.append(", getAccountsByTypeAndFeatures, ");
            String[] strArr = this.b;
            sb.append(strArr != null ? TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, strArr) : null);
            return sb.toString();
        }
    }

    public Account[] getAccounts(int i2) {
        Account[] accountsFromCacheLocked;
        g userAccounts = getUserAccounts(i2);
        long d2 = d();
        try {
            synchronized (userAccounts.c) {
                accountsFromCacheLocked = getAccountsFromCacheLocked(userAccounts, null);
            }
            return accountsFromCacheLocked;
        } finally {
            a(d2);
        }
    }

    public Account[] getAccounts(String str) {
        Account[] accountsFromCacheLocked;
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "getAccounts: accountType " + str + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        g b2 = b();
        long d2 = d();
        try {
            synchronized (b2.c) {
                accountsFromCacheLocked = getAccountsFromCacheLocked(b2, str);
            }
            return accountsFromCacheLocked;
        } finally {
            a(d2);
        }
    }

    public void getAccountsByFeatures(IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr) {
        Account[] accountsFromCacheLocked;
        if (Log.isLoggable("AccountManagerService", 2)) {
            AccountLog.v("AccountManagerService", "getAccounts: accountType " + str + ", response " + iAccountManagerResponse + ", features " + a(strArr) + ", caller's uid " + Binder.getCallingUid() + ", pid " + Binder.getCallingPid());
        }
        if (iAccountManagerResponse == null) {
            throw new IllegalArgumentException("response is null");
        } else if (str != null) {
            g b2 = b();
            long d2 = d();
            if (strArr != null) {
                try {
                    if (strArr.length != 0) {
                        new b(b2, iAccountManagerResponse, str, strArr).f();
                        return;
                    }
                } finally {
                    a(d2);
                }
            }
            synchronized (b2.c) {
                accountsFromCacheLocked = getAccountsFromCacheLocked(b2, str);
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArray("accounts", accountsFromCacheLocked);
            a(iAccountManagerResponse, bundle);
        } else {
            throw new IllegalArgumentException("accountType is null");
        }
    }

    private long a(SQLiteDatabase sQLiteDatabase, Account account) {
        Cursor query = sQLiteDatabase.query("accounts", new String[]{"_id"}, "name=? AND type=?", new String[]{account.name, account.type}, null, null, null);
        try {
            if (query.moveToNext()) {
                return query.getLong(0);
            }
            return -1L;
        } finally {
            query.close();
        }
    }

    private long a(SQLiteDatabase sQLiteDatabase, long j, String str) {
        Cursor query = sQLiteDatabase.query("extras", new String[]{"_id"}, "accounts_id=" + j + " AND key=?", new String[]{str}, null, null, null);
        try {
            if (query.moveToNext()) {
                return query.getLong(0);
            }
            return -1L;
        } finally {
            query.close();
        }
    }

    /* loaded from: classes2.dex */
    private abstract class e extends IAccountAuthenticatorResponse.Stub implements ServiceConnection, IBinder.DeathRecipient {
        private final boolean c;
        IAccountManagerResponse f;
        final String g;
        final boolean h;
        final long i;
        protected final g l;
        public int j = 0;
        private int a = 0;
        private int b = 0;
        IAccountAuthenticator k = null;

        public abstract void a() throws RemoteException;

        public e(g gVar, IAccountManagerResponse iAccountManagerResponse, String str, boolean z, boolean z2) {
            if (iAccountManagerResponse == null) {
                throw new IllegalArgumentException("response is null");
            } else if (str != null) {
                this.l = gVar;
                this.c = z2;
                this.f = iAccountManagerResponse;
                this.g = str;
                this.h = z;
                this.i = SystemClock.elapsedRealtime();
                synchronized (AccountManagerService.this.j) {
                    AccountManagerService.this.j.put(toString(), this);
                }
                try {
                    iAccountManagerResponse.asBinder().linkToDeath(this, 0);
                } catch (RemoteException unused) {
                    this.f = null;
                    binderDied();
                }
            } else {
                throw new IllegalArgumentException("accountType is null");
            }
        }

        IAccountManagerResponse d() {
            IAccountManagerResponse iAccountManagerResponse = this.f;
            if (iAccountManagerResponse == null) {
                return null;
            }
            b();
            return iAccountManagerResponse;
        }

        private void b() {
            synchronized (AccountManagerService.this.j) {
                if (AccountManagerService.this.j.remove(toString()) != null) {
                    IAccountManagerResponse iAccountManagerResponse = this.f;
                    if (iAccountManagerResponse != null) {
                        iAccountManagerResponse.asBinder().unlinkToDeath(this, 0);
                        this.f = null;
                    }
                    g();
                    c();
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.f = null;
            b();
        }

        protected String e() {
            return a(SystemClock.elapsedRealtime());
        }

        protected String a(long j) {
            StringBuilder sb = new StringBuilder();
            sb.append("Session: expectLaunch ");
            sb.append(this.h);
            sb.append(", connected ");
            sb.append(this.k != null);
            sb.append(", stats (");
            sb.append(this.j);
            sb.append("/");
            sb.append(this.a);
            sb.append("/");
            sb.append(this.b);
            sb.append("), lifetime ");
            sb.append((j - this.i) / 1000.0d);
            return sb.toString();
        }

        void f() {
            if (Log.isLoggable("AccountManagerService", 2)) {
                AccountLog.v("AccountManagerService", "initiating bind to authenticator type " + this.g);
            }
            if (!a(this.g)) {
                AccountLog.d("AccountManagerService", "bind attempt failed for " + e());
                onError(1, "bind failure");
            }
        }

        private void c() {
            if (this.k != null) {
                this.k = null;
                AccountManagerService.this.b.unbindService(this);
            }
        }

        public void g() {
            AccountManagerService.this.e.removeMessages(3, this);
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.k = IAccountAuthenticator.Stub.asInterface(iBinder);
            AccountManagerService.a.execute(new Runnable() { // from class: com.xiaomi.accounts.AccountManagerService.e.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        e.this.a();
                    } catch (RemoteException unused) {
                        e.this.onError(1, "remote exception");
                    }
                }
            });
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            this.k = null;
            IAccountManagerResponse d = d();
            if (d != null) {
                try {
                    d.onError(1, "disconnected");
                } catch (RemoteException e) {
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        AccountLog.v("AccountManagerService", "Session.onServiceDisconnected: caught RemoteException while responding", e);
                    }
                }
            }
        }

        public void h() {
            IAccountManagerResponse d = d();
            if (d != null) {
                try {
                    d.onError(1, RtspHeaders.Values.TIMEOUT);
                } catch (RemoteException e) {
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        AccountLog.v("AccountManagerService", "Session.onTimedOut: caught RemoteException while responding", e);
                    }
                }
            }
        }

        public void onResult(Bundle bundle) {
            IAccountManagerResponse iAccountManagerResponse;
            this.j++;
            if (!this.h || bundle == null || !bundle.containsKey("intent")) {
                iAccountManagerResponse = d();
            } else {
                iAccountManagerResponse = this.f;
            }
            if (iAccountManagerResponse != null) {
                try {
                    if (bundle == null) {
                        if (Log.isLoggable("AccountManagerService", 2)) {
                            AccountLog.v("AccountManagerService", getClass().getSimpleName() + " calling onError() on response " + iAccountManagerResponse);
                        }
                        iAccountManagerResponse.onError(5, "null bundle returned");
                        return;
                    }
                    if (this.c) {
                        bundle.remove("authtoken");
                    }
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        AccountLog.v("AccountManagerService", getClass().getSimpleName() + " calling onResult() on response " + iAccountManagerResponse);
                    }
                    iAccountManagerResponse.onResult(bundle);
                } catch (RemoteException e) {
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        AccountLog.v("AccountManagerService", "failure while notifying response", e);
                    }
                }
            }
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticatorResponse
        public void onRequestContinued() {
            this.a++;
        }

        @Override // com.xiaomi.accounts.IAccountAuthenticatorResponse
        public void onError(int i, String str) {
            this.b++;
            IAccountManagerResponse d = d();
            if (d != null) {
                if (Log.isLoggable("AccountManagerService", 2)) {
                    AccountLog.v("AccountManagerService", getClass().getSimpleName() + " calling onError() on response " + d);
                }
                try {
                    d.onError(i, str);
                } catch (RemoteException e) {
                    if (Log.isLoggable("AccountManagerService", 2)) {
                        AccountLog.v("AccountManagerService", "Session.onError: caught RemoteException while responding", e);
                    }
                }
            } else if (Log.isLoggable("AccountManagerService", 2)) {
                AccountLog.v("AccountManagerService", "Session.onError: already closed");
            }
        }

        private boolean a(String str) {
            AccountAuthenticatorCache.ServiceInfo<AuthenticatorDescription> serviceInfo = AccountManagerService.this.f.getServiceInfo(AuthenticatorDescription.newKey(str));
            if (serviceInfo == null) {
                if (Log.isLoggable("AccountManagerService", 2)) {
                    AccountLog.v("AccountManagerService", "there is no authenticator for " + str + ", bailing out");
                }
                return false;
            }
            Intent intent = new Intent();
            intent.setAction(AccountManager.ACTION_AUTHENTICATOR_INTENT);
            intent.setComponent(serviceInfo.componentName);
            if (Log.isLoggable("AccountManagerService", 2)) {
                AccountLog.v("AccountManagerService", "performing bindService to " + serviceInfo.componentName);
            }
            if (AccountManagerService.this.b.bindService(intent, this, 1)) {
                return true;
            }
            if (Log.isLoggable("AccountManagerService", 2)) {
                AccountLog.v("AccountManagerService", "bindService to " + serviceInfo.componentName + " failed");
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class c extends Handler {
        c(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 3) {
                ((e) message.obj).h();
                return;
            }
            throw new IllegalStateException("unhandled message: " + message.what);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(Context context, int i2) {
        File filesDir = context.getFilesDir();
        File file = new File(filesDir, "users/" + i2);
        file.mkdirs();
        return new File(file, "accounts.db").getPath();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class a extends SQLiteOpenHelper {
        public a(Context context, int i) {
            super(context, AccountManagerService.b(context, i), (SQLiteDatabase.CursorFactory) null, 4);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("CREATE TABLE accounts ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, type TEXT NOT NULL, password TEXT, UNIQUE(name,type))");
            sQLiteDatabase.execSQL("CREATE TABLE authtokens (  _id INTEGER PRIMARY KEY AUTOINCREMENT,  accounts_id INTEGER NOT NULL, type TEXT NOT NULL,  authtoken TEXT,  UNIQUE (accounts_id,type))");
            b(sQLiteDatabase);
            sQLiteDatabase.execSQL("CREATE TABLE extras ( _id INTEGER PRIMARY KEY AUTOINCREMENT, accounts_id INTEGER, key TEXT NOT NULL, value TEXT, UNIQUE(accounts_id,key))");
            sQLiteDatabase.execSQL("CREATE TABLE meta ( key TEXT PRIMARY KEY NOT NULL, value TEXT)");
            a(sQLiteDatabase);
        }

        private void a(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(" CREATE TRIGGER accountsDelete DELETE ON accounts BEGIN   DELETE FROM authtokens     WHERE accounts_id=OLD._id ;   DELETE FROM extras     WHERE accounts_id=OLD._id ;   DELETE FROM grants     WHERE accounts_id=OLD._id ; END");
        }

        private void b(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("CREATE TABLE grants (  accounts_id INTEGER NOT NULL, auth_token_type STRING NOT NULL,  uid INTEGER NOT NULL,  UNIQUE (accounts_id,auth_token_type,uid))");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            AccountLog.e("AccountManagerService", "upgrade from version " + i + " to version " + i2);
            if (i == 1) {
                i++;
            }
            if (i == 2) {
                b(sQLiteDatabase);
                sQLiteDatabase.execSQL("DROP TRIGGER accountsDelete");
                a(sQLiteDatabase);
                i++;
            }
            if (i == 3) {
                sQLiteDatabase.execSQL("UPDATE accounts SET type = 'com.google' WHERE type == 'com.google.GAIA'");
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onOpen(SQLiteDatabase sQLiteDatabase) {
            if (Log.isLoggable("AccountManagerService", 2)) {
                AccountLog.v("AccountManagerService", "opened database accounts.db");
            }
        }
    }

    public void updateAppPermission(Account account, String str, int i2, boolean z) throws RemoteException {
        if (c() != 1000) {
            throw new SecurityException();
        } else if (z) {
            a(account, str, i2);
        } else {
            b(account, str, i2);
        }
    }

    private void a(Account account, String str, int i2) {
        if (account == null || str == null) {
            AccountLog.e("AccountManagerService", "grantAppPermission: called with invalid arguments", new Exception());
            return;
        }
        g userAccounts = getUserAccounts(UserId.getUserId(i2));
        synchronized (userAccounts.c) {
            SQLiteDatabase writableDatabase = userAccounts.b.getWritableDatabase();
            writableDatabase.beginTransaction();
            long a2 = a(writableDatabase, account);
            if (a2 >= 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("accounts_id", Long.valueOf(a2));
                contentValues.put("auth_token_type", str);
                contentValues.put(OneTrack.Param.UID, Integer.valueOf(i2));
                writableDatabase.insert("grants", "accounts_id", contentValues);
                writableDatabase.setTransactionSuccessful();
            }
            writableDatabase.endTransaction();
        }
    }

    private void b(Account account, String str, int i2) {
        if (account == null || str == null) {
            AccountLog.e("AccountManagerService", "revokeAppPermission: called with invalid arguments", new Exception());
            return;
        }
        g userAccounts = getUserAccounts(UserId.getUserId(i2));
        synchronized (userAccounts.c) {
            SQLiteDatabase writableDatabase = userAccounts.b.getWritableDatabase();
            writableDatabase.beginTransaction();
            long a2 = a(writableDatabase, account);
            if (a2 >= 0) {
                writableDatabase.delete("grants", "accounts_id=? AND auth_token_type=? AND uid=?", new String[]{String.valueOf(a2), str, String.valueOf(i2)});
                writableDatabase.setTransactionSuccessful();
            }
            writableDatabase.endTransaction();
        }
    }

    private static final String a(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        return "[" + TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, strArr) + "]";
    }

    /* JADX WARN: Type inference failed for: r5v0, types: [void] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void c(com.xiaomi.accounts.AccountManagerService.g r7, android.accounts.Account r8) {
        /*
            r6 = this;
            java.util.HashMap r0 = com.xiaomi.accounts.AccountManagerService.g.c(r7)
            java.lang.String r1 = r8.type
            java.lang.Object r0 = r0.get(r1)
            android.accounts.Account[] r0 = (android.accounts.Account[]) r0
            if (r0 == 0) goto L_0x004a
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            int r2 = r0.length
            r3 = 0
        L_0x0015:
            if (r3 >= r2) goto L_0x0025
            r4 = r0[r3]
            void r5 = r4.<init>()
            if (r5 != 0) goto L_0x0022
            r1.add(r4)
        L_0x0022:
            int r3 = r3 + 1
            goto L_0x0015
        L_0x0025:
            boolean r0 = r1.isEmpty()
            if (r0 == 0) goto L_0x0035
            java.util.HashMap r0 = com.xiaomi.accounts.AccountManagerService.g.c(r7)
            java.lang.String r1 = r8.type
            r0.remove(r1)
            goto L_0x004a
        L_0x0035:
            int r0 = r1.size()
            android.accounts.Account[] r0 = new android.accounts.Account[r0]
            java.lang.Object[] r0 = r1.toArray(r0)
            android.accounts.Account[] r0 = (android.accounts.Account[]) r0
            java.util.HashMap r1 = com.xiaomi.accounts.AccountManagerService.g.c(r7)
            java.lang.String r2 = r8.type
            r1.put(r2, r0)
        L_0x004a:
            java.util.HashMap r0 = com.xiaomi.accounts.AccountManagerService.g.d(r7)
            r0.remove(r8)
            java.util.HashMap r7 = com.xiaomi.accounts.AccountManagerService.g.e(r7)
            r7.remove(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.accounts.AccountManagerService.c(com.xiaomi.accounts.AccountManagerService$g, android.accounts.Account):void");
    }

    private void d(g gVar, Account account) {
        Account[] accountArr = (Account[]) gVar.d.get(account.type);
        int length = accountArr != null ? accountArr.length : 0;
        Account[] accountArr2 = new Account[length + 1];
        if (accountArr != null) {
            System.arraycopy(accountArr, 0, accountArr2, 0, length);
        }
        accountArr2[length] = account;
        gVar.d.put(account.type, accountArr2);
    }

    protected Account[] getAccountsFromCacheLocked(g gVar, String str) {
        b(gVar);
        if (str != null) {
            Account[] accountArr = (Account[]) gVar.d.get(str);
            if (accountArr == null) {
                return m;
            }
            return (Account[]) Arrays.copyOf(accountArr, accountArr.length);
        }
        int i2 = 0;
        for (Account[] accountArr2 : gVar.d.values()) {
            i2 += accountArr2.length;
        }
        if (i2 == 0) {
            return m;
        }
        Account[] accountArr3 = new Account[i2];
        int i3 = 0;
        for (Account[] accountArr4 : gVar.d.values()) {
            System.arraycopy(accountArr4, 0, accountArr3, i3, accountArr4.length);
            i3 += accountArr4.length;
        }
        return accountArr3;
    }

    protected void writeUserDataIntoCacheLocked(g gVar, SQLiteDatabase sQLiteDatabase, Account account, String str, String str2) {
        HashMap<String, String> hashMap = (HashMap) gVar.e.get(account);
        if (hashMap == null) {
            hashMap = readUserDataForAccountFromDatabaseLocked(sQLiteDatabase, account);
            gVar.e.put(account, hashMap);
        }
        if (str2 == null) {
            hashMap.remove(str);
        } else {
            hashMap.put(str, str2);
        }
    }

    protected void writeAuthTokenIntoCacheLocked(g gVar, SQLiteDatabase sQLiteDatabase, Account account, String str, String str2) {
        HashMap<String, String> hashMap = (HashMap) gVar.f.get(account);
        if (hashMap == null) {
            hashMap = readAuthTokensForAccountFromDatabaseLocked(sQLiteDatabase, account);
            gVar.f.put(account, hashMap);
        }
        if (str2 == null) {
            hashMap.remove(str);
        } else {
            hashMap.put(str, str2);
        }
    }

    protected String readAuthTokenInternal(g gVar, Account account, String str) {
        String str2;
        synchronized (gVar.c) {
            HashMap<String, String> hashMap = (HashMap) gVar.f.get(account);
            if (hashMap == null) {
                hashMap = readAuthTokensForAccountFromDatabaseLocked(gVar.b.getReadableDatabase(), account);
                gVar.f.put(account, hashMap);
            }
            str2 = hashMap.get(str);
        }
        return str2;
    }

    protected String readUserDataInternal(g gVar, Account account, String str) {
        String str2;
        synchronized (gVar.c) {
            HashMap<String, String> hashMap = (HashMap) gVar.e.get(account);
            if (hashMap == null || TextUtils.isEmpty(hashMap.get(str))) {
                hashMap = readUserDataForAccountFromDatabaseLocked(gVar.b.getReadableDatabase(), account);
                gVar.e.put(account, hashMap);
            }
            str2 = hashMap.get(str);
        }
        return str2;
    }

    protected HashMap<String, String> readUserDataForAccountFromDatabaseLocked(SQLiteDatabase sQLiteDatabase, Account account) {
        HashMap<String, String> hashMap = new HashMap<>();
        Cursor query = sQLiteDatabase.query("extras", i, "accounts_id=(select _id FROM accounts WHERE name=? AND type=?)", new String[]{account.name, account.type}, null, null, null);
        while (query.moveToNext()) {
            try {
                hashMap.put(query.getString(0), query.getString(1));
            } finally {
                query.close();
            }
        }
        return hashMap;
    }

    protected HashMap<String, String> readAuthTokensForAccountFromDatabaseLocked(SQLiteDatabase sQLiteDatabase, Account account) {
        HashMap<String, String> hashMap = new HashMap<>();
        Cursor query = sQLiteDatabase.query("authtokens", h, "accounts_id=(select _id FROM accounts WHERE name=? AND type=?)", new String[]{account.name, account.type}, null, null, null);
        while (query.moveToNext()) {
            try {
                hashMap.put(query.getString(0), query.getString(1));
            } finally {
                query.close();
            }
        }
        return hashMap;
    }

    private int c() {
        return this.b.getApplicationInfo().uid;
    }
}
