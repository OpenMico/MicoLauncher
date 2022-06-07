package io.realm;

import android.content.Context;
import android.os.SystemClock;
import android.util.JsonReader;
import io.reactivex.Flowable;
import io.realm.BaseRealm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import io.realm.internal.ColumnIndices;
import io.realm.internal.ObjectServerFacade;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectStore;
import io.realm.internal.OsResults;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.OsSharedRealm;
import io.realm.internal.RealmCore;
import io.realm.internal.RealmNotifier;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Table;
import io.realm.internal.Util;
import io.realm.internal.async.RealmAsyncTaskImpl;
import io.realm.log.RealmLog;
import io.realm.m;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class Realm extends BaseRealm {
    public static final String DEFAULT_REALM_NAME = "default.realm";
    private static final Object e = new Object();
    private static RealmConfiguration f;
    private final RealmSchema g;

    /* loaded from: classes5.dex */
    public interface Transaction {

        /* loaded from: classes5.dex */
        public static class Callback {
            public void onError(Exception exc) {
            }

            public void onSuccess() {
            }
        }

        /* loaded from: classes5.dex */
        public interface OnError {
            void onError(Throwable th);
        }

        /* loaded from: classes5.dex */
        public interface OnSuccess {
            void onSuccess();
        }

        void execute(Realm realm);
    }

    /* loaded from: classes5.dex */
    public interface UnsubscribeCallback {
        void onError(String str, Throwable th);

        void onSuccess(String str);
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void beginTransaction() {
        super.beginTransaction();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void cancelTransaction() {
        super.cancelTransaction();
    }

    @Override // io.realm.BaseRealm, java.io.Closeable, java.lang.AutoCloseable
    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void commitTransaction() {
        super.commitTransaction();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void deleteAll() {
        super.deleteAll();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ RealmConfiguration getConfiguration() {
        return super.getConfiguration();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ String getPath() {
        return super.getPath();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ long getVersion() {
        return super.getVersion();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ boolean isAutoRefresh() {
        return super.isAutoRefresh();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ boolean isClosed() {
        return super.isClosed();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ boolean isFrozen() {
        return super.isFrozen();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ boolean isInTransaction() {
        return super.isInTransaction();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void refresh() {
        super.refresh();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void setAutoRefresh(boolean z) {
        super.setAutoRefresh(z);
    }

    @Override // io.realm.BaseRealm
    @Deprecated
    public /* bridge */ /* synthetic */ void stopWaitForChange() {
        super.stopWaitForChange();
    }

    @Override // io.realm.BaseRealm
    @Deprecated
    public /* bridge */ /* synthetic */ boolean waitForChange() {
        return super.waitForChange();
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void writeCopyTo(File file) {
        super.writeCopyTo(file);
    }

    @Override // io.realm.BaseRealm
    public /* bridge */ /* synthetic */ void writeEncryptedCopyTo(File file, byte[] bArr) {
        super.writeEncryptedCopyTo(file, bArr);
    }

    private Realm(m mVar, OsSharedRealm.VersionID versionID) {
        super(mVar, a(mVar.a().getSchemaMediator()), versionID);
        this.g = new g(this, new ColumnIndices(this.configuration.getSchemaMediator(), this.sharedRealm.getSchemaInfo()));
        if (this.configuration.isReadOnly()) {
            RealmProxyMediator schemaMediator = this.configuration.getSchemaMediator();
            for (Class<? extends RealmModel> cls : schemaMediator.getModelClasses()) {
                String tableNameForClass = Table.getTableNameForClass(schemaMediator.getSimpleClassName(cls));
                if (!this.sharedRealm.hasTable(tableNameForClass)) {
                    this.sharedRealm.close();
                    throw new RealmMigrationNeededException(this.configuration.getPath(), String.format(Locale.US, "Cannot open the read only Realm. '%s' is missing.", Table.getClassNameForTable(tableNameForClass)));
                }
            }
        }
    }

    private Realm(OsSharedRealm osSharedRealm) {
        super(osSharedRealm);
        this.g = new g(this, new ColumnIndices(this.configuration.getSchemaMediator(), osSharedRealm.getSchemaInfo()));
    }

    private static OsSchemaInfo a(RealmProxyMediator realmProxyMediator) {
        return new OsSchemaInfo(realmProxyMediator.getExpectedObjectSchemaInfoMap().values());
    }

    @Override // io.realm.BaseRealm
    public Flowable<Realm> asFlowable() {
        return this.configuration.getRxFactory().from(this);
    }

    @Override // io.realm.BaseRealm
    public boolean isEmpty() {
        checkIfValid();
        for (RealmObjectSchema realmObjectSchema : this.g.getAll()) {
            if (!realmObjectSchema.getClassName().startsWith("__") && realmObjectSchema.a().size() > 0) {
                return false;
            }
        }
        return true;
    }

    @Override // io.realm.BaseRealm
    public RealmSchema getSchema() {
        return this.g;
    }

    public static synchronized void init(Context context) {
        synchronized (Realm.class) {
            a(context, "");
        }
    }

    private static void a(Context context, String str) {
        if (BaseRealm.a != null) {
            return;
        }
        if (context != null) {
            a(context);
            RealmCore.loadLibrary(context);
            setDefaultConfiguration(new RealmConfiguration.Builder(context).build());
            ObjectServerFacade.getSyncFacadeIfPossible().initialize(context, str);
            if (context.getApplicationContext() != null) {
                BaseRealm.a = context.getApplicationContext();
            } else {
                BaseRealm.a = context;
            }
            OsSharedRealm.initialize(new File(context.getFilesDir(), ".realm.temp"));
            return;
        }
        throw new IllegalArgumentException("Non-null context required.");
    }

    private static void a(Context context) {
        File filesDir = context.getFilesDir();
        if (filesDir != null) {
            if (!filesDir.exists()) {
                try {
                    filesDir.mkdirs();
                } catch (SecurityException unused) {
                }
            } else {
                return;
            }
        }
        if (filesDir == null || !filesDir.exists()) {
            long[] jArr = {1, 2, 5, 10, 16};
            long j = 0;
            int i = -1;
            do {
                if (context.getFilesDir() != null && context.getFilesDir().exists()) {
                    break;
                }
                i++;
                long j2 = jArr[Math.min(i, jArr.length - 1)];
                SystemClock.sleep(j2);
                j += j2;
            } while (j <= 200);
        }
        if (context.getFilesDir() == null || !context.getFilesDir().exists()) {
            throw new IllegalStateException("Context.getFilesDir() returns " + context.getFilesDir() + " which is not an existing directory. See https://issuetracker.google.com/issues/36918154");
        }
    }

    public static Realm getDefaultInstance() {
        RealmConfiguration defaultConfiguration = getDefaultConfiguration();
        if (defaultConfiguration != null) {
            return (Realm) m.a(defaultConfiguration, Realm.class);
        }
        if (BaseRealm.a == null) {
            throw new IllegalStateException("Call `Realm.init(Context)` before calling this method.");
        }
        throw new IllegalStateException("Set default configuration by using `Realm.setDefaultConfiguration(RealmConfiguration)`.");
    }

    public static Realm getInstance(RealmConfiguration realmConfiguration) {
        if (realmConfiguration != null) {
            return (Realm) m.a(realmConfiguration, Realm.class);
        }
        throw new IllegalArgumentException("A non-null RealmConfiguration must be provided");
    }

    public static RealmAsyncTask getInstanceAsync(RealmConfiguration realmConfiguration, Callback callback) {
        if (realmConfiguration != null) {
            return m.a(realmConfiguration, callback, Realm.class);
        }
        throw new IllegalArgumentException("A non-null RealmConfiguration must be provided");
    }

    public static void setDefaultConfiguration(RealmConfiguration realmConfiguration) {
        if (realmConfiguration != null) {
            synchronized (e) {
                f = realmConfiguration;
            }
            return;
        }
        throw new IllegalArgumentException("A non-null RealmConfiguration must be provided");
    }

    @Nullable
    public static RealmConfiguration getDefaultConfiguration() {
        RealmConfiguration realmConfiguration;
        synchronized (e) {
            realmConfiguration = f;
        }
        return realmConfiguration;
    }

    public static void removeDefaultConfiguration() {
        synchronized (e) {
            f = null;
        }
    }

    public static Realm a(m mVar, OsSharedRealm.VersionID versionID) {
        return new Realm(mVar, versionID);
    }

    public static Realm a(OsSharedRealm osSharedRealm) {
        return new Realm(osSharedRealm);
    }

    public <E extends RealmModel> void createAllFromJson(Class<E> cls, JSONArray jSONArray) {
        if (!(cls == null || jSONArray == null)) {
            checkIfValid();
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, jSONArray.getJSONObject(i), false);
                } catch (JSONException e2) {
                    throw new RealmException("Could not map JSON", e2);
                }
            }
        }
    }

    public <E extends RealmModel> void createOrUpdateAllFromJson(Class<E> cls, JSONArray jSONArray) {
        if (!(cls == null || jSONArray == null)) {
            checkIfValid();
            b(cls);
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, jSONArray.getJSONObject(i), true);
                } catch (JSONException e2) {
                    throw new RealmException("Could not map JSON", e2);
                }
            }
        }
    }

    public <E extends RealmModel> void createAllFromJson(Class<E> cls, String str) {
        if (cls != null && str != null && str.length() != 0) {
            try {
                createAllFromJson(cls, new JSONArray(str));
            } catch (JSONException e2) {
                throw new RealmException("Could not create JSON array from string", e2);
            }
        }
    }

    public <E extends RealmModel> void createOrUpdateAllFromJson(Class<E> cls, String str) {
        if (cls != null && str != null && str.length() != 0) {
            checkIfValid();
            b(cls);
            try {
                createOrUpdateAllFromJson(cls, new JSONArray(str));
            } catch (JSONException e2) {
                throw new RealmException("Could not create JSON array from string", e2);
            }
        }
    }

    public <E extends RealmModel> void createAllFromJson(Class<E> cls, InputStream inputStream) throws IOException {
        if (cls != null && inputStream != null) {
            checkIfValid();
            JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            try {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    this.configuration.getSchemaMediator().createUsingJsonStream(cls, this, jsonReader);
                }
                jsonReader.endArray();
            } finally {
                jsonReader.close();
            }
        }
    }

    public <E extends RealmModel> void createOrUpdateAllFromJson(Class<E> cls, InputStream inputStream) {
        Scanner scanner;
        if (cls != null && inputStream != null) {
            try {
                checkIfValid();
                b(cls);
                scanner = null;
                try {
                    scanner = a(inputStream);
                    JSONArray jSONArray = new JSONArray(scanner.next());
                    for (int i = 0; i < jSONArray.length(); i++) {
                        this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, jSONArray.getJSONObject(i), true);
                    }
                } catch (JSONException e2) {
                    throw new RealmException("Failed to read JSON", e2);
                }
            } finally {
                if (scanner != null) {
                    scanner.close();
                }
            }
        }
    }

    @Nullable
    public <E extends RealmModel> E createObjectFromJson(Class<E> cls, JSONObject jSONObject) {
        if (cls == null || jSONObject == null) {
            return null;
        }
        checkIfValid();
        try {
            return (E) this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, jSONObject, false);
        } catch (JSONException e2) {
            throw new RealmException("Could not map JSON", e2);
        }
    }

    public <E extends RealmModel> E createOrUpdateObjectFromJson(Class<E> cls, JSONObject jSONObject) {
        if (cls == null || jSONObject == null) {
            return null;
        }
        checkIfValid();
        b(cls);
        try {
            return (E) this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, jSONObject, true);
        } catch (JSONException e2) {
            throw new RealmException("Could not map JSON", e2);
        }
    }

    @Nullable
    public <E extends RealmModel> E createObjectFromJson(Class<E> cls, String str) {
        if (cls == null || str == null || str.length() == 0) {
            return null;
        }
        try {
            return (E) createObjectFromJson(cls, new JSONObject(str));
        } catch (JSONException e2) {
            throw new RealmException("Could not create Json object from string", e2);
        }
    }

    public <E extends RealmModel> E createOrUpdateObjectFromJson(Class<E> cls, String str) {
        if (cls == null || str == null || str.length() == 0) {
            return null;
        }
        checkIfValid();
        b(cls);
        try {
            return (E) createOrUpdateObjectFromJson(cls, new JSONObject(str));
        } catch (JSONException e2) {
            throw new RealmException("Could not create Json object from string", e2);
        }
    }

    @Nullable
    public <E extends RealmModel> E createObjectFromJson(Class<E> cls, InputStream inputStream) throws IOException {
        E e2;
        Scanner scanner = null;
        if (cls == null || inputStream == null) {
            return null;
        }
        checkIfValid();
        try {
            if (OsObjectStore.getPrimaryKeyForObject(this.sharedRealm, this.configuration.getSchemaMediator().getSimpleClassName(cls)) != null) {
                try {
                    scanner = a(inputStream);
                    e2 = (E) this.configuration.getSchemaMediator().createOrUpdateUsingJsonObject(cls, this, new JSONObject(scanner.next()), false);
                } catch (JSONException e3) {
                    throw new RealmException("Failed to read JSON", e3);
                }
            } else {
                JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
                try {
                    e2 = (E) this.configuration.getSchemaMediator().createUsingJsonStream(cls, this, jsonReader);
                } finally {
                    jsonReader.close();
                }
            }
            return e2;
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    public <E extends RealmModel> E createOrUpdateObjectFromJson(Class<E> cls, InputStream inputStream) {
        Scanner scanner = null;
        if (cls == null || inputStream == null) {
            return null;
        }
        try {
            checkIfValid();
            b(cls);
            try {
                scanner = a(inputStream);
                return (E) createOrUpdateObjectFromJson(cls, new JSONObject(scanner.next()));
            } catch (JSONException e2) {
                throw new RealmException("Failed to read JSON", e2);
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private Scanner a(InputStream inputStream) {
        return new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
    }

    public <E extends RealmModel> E createObject(Class<E> cls) {
        checkIfValid();
        return (E) a((Class<RealmModel>) cls, true, Collections.emptyList());
    }

    <E extends RealmModel> E a(Class<E> cls, boolean z, List<String> list) {
        Table a = this.g.a(cls);
        if (OsObjectStore.getPrimaryKeyForObject(this.sharedRealm, this.configuration.getSchemaMediator().getSimpleClassName(cls)) == null) {
            return (E) this.configuration.getSchemaMediator().newInstance(cls, this, OsObject.create(a), this.g.c(cls), z, list);
        }
        throw new RealmException(String.format(Locale.US, "'%s' has a primary key, use 'createObject(Class<E>, Object)' instead.", a.getClassName()));
    }

    public <E extends RealmModel> E createObject(Class<E> cls, @Nullable Object obj) {
        checkIfValid();
        return (E) a((Class<RealmModel>) cls, obj, true, Collections.emptyList());
    }

    public <E extends RealmModel> E a(Class<E> cls, @Nullable Object obj, boolean z, List<String> list) {
        return (E) this.configuration.getSchemaMediator().newInstance(cls, this, OsObject.createWithPrimaryKey(this.g.a(cls), obj), this.g.c(cls), z, list);
    }

    public <E extends RealmModel> E copyToRealm(E e2, ImportFlag... importFlagArr) {
        a((Realm) e2);
        return (E) a((Realm) e2, false, (Map<RealmModel, RealmObjectProxy>) new HashMap(), Util.toSet(importFlagArr));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <E extends RealmModel> E copyToRealmOrUpdate(E e2, ImportFlag... importFlagArr) {
        a((Realm) e2);
        b((Class<? extends RealmModel>) e2.getClass());
        return (E) a((Realm) e2, true, (Map<RealmModel, RealmObjectProxy>) new HashMap(), (Set<ImportFlag>) Util.toSet(importFlagArr));
    }

    public <E extends RealmModel> List<E> copyToRealm(Iterable<E> iterable, ImportFlag... importFlagArr) {
        ArrayList arrayList;
        if (iterable == null) {
            return new ArrayList();
        }
        if (iterable instanceof Collection) {
            arrayList = new ArrayList(((Collection) iterable).size());
        } else {
            arrayList = new ArrayList();
        }
        HashMap hashMap = new HashMap();
        for (E e2 : iterable) {
            a((Realm) e2);
            arrayList.add(a((Realm) e2, false, (Map<RealmModel, RealmObjectProxy>) hashMap, Util.toSet(importFlagArr)));
        }
        return arrayList;
    }

    public void insert(Collection<? extends RealmModel> collection) {
        checkIfValidAndInTransaction();
        if (collection == null) {
            throw new IllegalArgumentException("Null objects cannot be inserted into Realm.");
        } else if (!collection.isEmpty()) {
            this.configuration.getSchemaMediator().insert(this, collection);
        }
    }

    public void insert(RealmModel realmModel) {
        checkIfValidAndInTransaction();
        if (realmModel != null) {
            this.configuration.getSchemaMediator().insert(this, realmModel, new HashMap());
            return;
        }
        throw new IllegalArgumentException("Null object cannot be inserted into Realm.");
    }

    public void insertOrUpdate(Collection<? extends RealmModel> collection) {
        checkIfValidAndInTransaction();
        if (collection == null) {
            throw new IllegalArgumentException("Null objects cannot be inserted into Realm.");
        } else if (!collection.isEmpty()) {
            this.configuration.getSchemaMediator().insertOrUpdate(this, collection);
        }
    }

    public void insertOrUpdate(RealmModel realmModel) {
        checkIfValidAndInTransaction();
        if (realmModel != null) {
            this.configuration.getSchemaMediator().insertOrUpdate(this, realmModel, new HashMap());
            return;
        }
        throw new IllegalArgumentException("Null object cannot be inserted into Realm.");
    }

    public <E extends RealmModel> List<E> copyToRealmOrUpdate(Iterable<E> iterable, ImportFlag... importFlagArr) {
        ArrayList arrayList;
        if (iterable == null) {
            return new ArrayList(0);
        }
        if (iterable instanceof Collection) {
            arrayList = new ArrayList(((Collection) iterable).size());
        } else {
            arrayList = new ArrayList();
        }
        HashMap hashMap = new HashMap();
        Set<ImportFlag> set = Util.toSet(importFlagArr);
        for (E e2 : iterable) {
            a((Realm) e2);
            arrayList.add(a((Realm) e2, true, (Map<RealmModel, RealmObjectProxy>) hashMap, set));
        }
        return arrayList;
    }

    public <E extends RealmModel> List<E> copyFromRealm(Iterable<E> iterable) {
        return copyFromRealm(iterable, Integer.MAX_VALUE);
    }

    public <E extends RealmModel> List<E> copyFromRealm(Iterable<E> iterable, int i) {
        ArrayList arrayList;
        a(i);
        if (iterable == null) {
            return new ArrayList(0);
        }
        if (iterable instanceof Collection) {
            arrayList = new ArrayList(((Collection) iterable).size());
        } else {
            arrayList = new ArrayList();
        }
        HashMap hashMap = new HashMap();
        for (E e2 : iterable) {
            b((Realm) e2);
            arrayList.add(a((Realm) e2, i, (Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>>) hashMap));
        }
        return arrayList;
    }

    public <E extends RealmModel> E copyFromRealm(E e2) {
        return (E) copyFromRealm((Realm) e2, Integer.MAX_VALUE);
    }

    public <E extends RealmModel> E copyFromRealm(E e2, int i) {
        a(i);
        b((Realm) e2);
        return (E) a((Realm) e2, i, (Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>>) new HashMap());
    }

    public <E extends RealmModel> RealmQuery<E> where(Class<E> cls) {
        checkIfValid();
        return RealmQuery.a(this, cls);
    }

    public void addChangeListener(RealmChangeListener<Realm> realmChangeListener) {
        addListener(realmChangeListener);
    }

    public void removeChangeListener(RealmChangeListener<Realm> realmChangeListener) {
        removeListener(realmChangeListener);
    }

    public void removeAllChangeListeners() {
        removeAllListeners();
    }

    public void executeTransaction(Transaction transaction) {
        if (transaction != null) {
            beginTransaction();
            try {
                transaction.execute(this);
                commitTransaction();
            } catch (Throwable th) {
                if (isInTransaction()) {
                    cancelTransaction();
                } else {
                    RealmLog.warn("Could not cancel transaction, not currently in a transaction.", new Object[0]);
                }
                throw th;
            }
        } else {
            throw new IllegalArgumentException("Transaction should not be null");
        }
    }

    public RealmAsyncTask executeTransactionAsync(Transaction transaction) {
        return executeTransactionAsync(transaction, null, null);
    }

    public RealmAsyncTask executeTransactionAsync(Transaction transaction, Transaction.OnSuccess onSuccess) {
        if (onSuccess != null) {
            return executeTransactionAsync(transaction, onSuccess, null);
        }
        throw new IllegalArgumentException("onSuccess callback can't be null");
    }

    public RealmAsyncTask executeTransactionAsync(Transaction transaction, Transaction.OnError onError) {
        if (onError != null) {
            return executeTransactionAsync(transaction, null, onError);
        }
        throw new IllegalArgumentException("onError callback can't be null");
    }

    public RealmAsyncTask executeTransactionAsync(Transaction transaction, @Nullable Transaction.OnSuccess onSuccess, @Nullable Transaction.OnError onError) {
        checkIfValid();
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction should not be null");
        } else if (!isFrozen()) {
            boolean canDeliverNotification = this.sharedRealm.capabilities.canDeliverNotification();
            if (!(onSuccess == null && onError == null)) {
                this.sharedRealm.capabilities.checkCanDeliverNotification("Callback cannot be delivered on current thread.");
            }
            return new RealmAsyncTaskImpl(b.submitTransaction(new AnonymousClass1(getConfiguration(), transaction, canDeliverNotification, onSuccess, this.sharedRealm.realmNotifier, onError)), b);
        } else {
            throw new IllegalStateException("Write transactions on a frozen Realm is not allowed.");
        }
    }

    /* renamed from: io.realm.Realm$1 */
    /* loaded from: classes5.dex */
    public class AnonymousClass1 implements Runnable {
        final /* synthetic */ RealmConfiguration a;
        final /* synthetic */ Transaction b;
        final /* synthetic */ boolean c;
        final /* synthetic */ Transaction.OnSuccess d;
        final /* synthetic */ RealmNotifier e;
        final /* synthetic */ Transaction.OnError f;

        AnonymousClass1(RealmConfiguration realmConfiguration, Transaction transaction, boolean z, Transaction.OnSuccess onSuccess, RealmNotifier realmNotifier, Transaction.OnError onError) {
            Realm.this = r1;
            this.a = realmConfiguration;
            this.b = transaction;
            this.c = z;
            this.d = onSuccess;
            this.e = realmNotifier;
            this.f = onError;
        }

        @Override // java.lang.Runnable
        public void run() {
            final OsSharedRealm.VersionID versionID;
            if (!Thread.currentThread().isInterrupted()) {
                Realm instance = Realm.getInstance(this.a);
                instance.beginTransaction();
                final Throwable th = null;
                try {
                    this.b.execute(instance);
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        if (instance.isInTransaction()) {
                            instance.cancelTransaction();
                        }
                        instance.close();
                        versionID = null;
                    } finally {
                    }
                }
                if (Thread.currentThread().isInterrupted()) {
                    try {
                        if (instance.isInTransaction()) {
                            instance.cancelTransaction();
                        }
                    } finally {
                    }
                } else {
                    instance.commitTransaction();
                    versionID = instance.sharedRealm.getVersionID();
                    try {
                        if (instance.isInTransaction()) {
                            instance.cancelTransaction();
                        }
                        if (this.c) {
                            if (versionID != null && this.d != null) {
                                this.e.post(new Runnable() { // from class: io.realm.Realm.1.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        if (Realm.this.isClosed()) {
                                            AnonymousClass1.this.d.onSuccess();
                                        } else if (Realm.this.sharedRealm.getVersionID().compareTo(versionID) < 0) {
                                            Realm.this.sharedRealm.realmNotifier.addTransactionCallback(new Runnable() { // from class: io.realm.Realm.1.1.1
                                                @Override // java.lang.Runnable
                                                public void run() {
                                                    AnonymousClass1.this.d.onSuccess();
                                                }
                                            });
                                        } else {
                                            AnonymousClass1.this.d.onSuccess();
                                        }
                                    }
                                });
                            } else if (th != null) {
                                this.e.post(new Runnable() { // from class: io.realm.Realm.1.2
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        if (AnonymousClass1.this.f != null) {
                                            AnonymousClass1.this.f.onError(th);
                                            return;
                                        }
                                        throw new RealmException("Async transaction failed", th);
                                    }
                                });
                            }
                        } else if (th != null) {
                            throw new RealmException("Async transaction failed", th);
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public void delete(Class<? extends RealmModel> cls) {
        checkIfValid();
        if (!this.sharedRealm.isPartial()) {
            this.g.a(cls).clear(this.sharedRealm.isPartial());
            return;
        }
        throw new IllegalStateException("This API is not supported by partially synchronized Realms. Either unsubscribe using 'Realm.unsubscribeAsync()' or delete the objects using a query and 'RealmResults.deleteAllFromRealm()'");
    }

    private <E extends RealmModel> E a(E e2, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        checkIfValid();
        if (isInTransaction()) {
            try {
                return (E) this.configuration.getSchemaMediator().copyOrUpdate(this, e2, z, map, set);
            } catch (IllegalStateException e3) {
                if (e3.getMessage().startsWith("Attempting to create an object of type")) {
                    throw new RealmPrimaryKeyConstraintException(e3.getMessage());
                }
                throw e3;
            }
        } else {
            throw new IllegalStateException("`copyOrUpdate` can only be called inside a write transaction.");
        }
    }

    private <E extends RealmModel> E a(E e2, int i, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        checkIfValid();
        return (E) this.configuration.getSchemaMediator().createDetachedCopy(e2, i, map);
    }

    private <E extends RealmModel> void a(E e2) {
        if (e2 == null) {
            throw new IllegalArgumentException("Null objects cannot be copied into Realm.");
        }
    }

    private void b(Class<? extends RealmModel> cls) {
        if (this.sharedRealm.getSchemaInfo().getObjectSchemaInfo(this.configuration.getSchemaMediator().getSimpleClassName(cls)).getPrimaryKeyProperty() == null) {
            throw new IllegalArgumentException("A RealmObject with no @PrimaryKey cannot be updated: " + cls.toString());
        }
    }

    private void a(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("maxDepth must be > 0. It was: " + i);
        }
    }

    private <E extends RealmModel> void b(E e2) {
        if (e2 == null) {
            throw new IllegalArgumentException("Null objects cannot be copied from Realm.");
        } else if (!RealmObject.isManaged(e2) || !RealmObject.isValid(e2)) {
            throw new IllegalArgumentException("Only valid managed objects can be copied from Realm.");
        } else if (e2 instanceof DynamicRealmObject) {
            throw new IllegalArgumentException("DynamicRealmObject cannot be copied from Realm.");
        }
    }

    public static void migrateRealm(RealmConfiguration realmConfiguration) throws FileNotFoundException {
        migrateRealm(realmConfiguration, null);
    }

    public static void migrateRealm(RealmConfiguration realmConfiguration, @Nullable RealmMigration realmMigration) throws FileNotFoundException {
        BaseRealm.migrateRealm(realmConfiguration, realmMigration);
    }

    public static boolean deleteRealm(RealmConfiguration realmConfiguration) {
        return BaseRealm.a(realmConfiguration);
    }

    public static boolean compactRealm(RealmConfiguration realmConfiguration) {
        return BaseRealm.b(realmConfiguration);
    }

    public RealmAsyncTask unsubscribeAsync(final String str, final UnsubscribeCallback unsubscribeCallback) {
        if (Util.isEmptyString(str)) {
            throw new IllegalArgumentException("Non-empty 'subscriptionName' required.");
        } else if (unsubscribeCallback != null) {
            this.sharedRealm.capabilities.checkCanDeliverNotification("This method is only available from a Looper thread.");
            if (ObjectServerFacade.getSyncFacadeIfPossible().isPartialRealm(this.configuration)) {
                return executeTransactionAsync(new Transaction() { // from class: io.realm.Realm.2
                    @Override // io.realm.Realm.Transaction
                    public void execute(Realm realm) {
                        Table table = realm.sharedRealm.getTable("class___ResultSets");
                        OsResults createFromQuery = OsResults.createFromQuery(realm.sharedRealm, table.where().equalTo(new long[]{table.getColumnKey("name")}, new long[]{0}, str));
                        long size = createFromQuery.size();
                        if (size != 0) {
                            if (size > 1) {
                                RealmLog.warn("Multiple subscriptions named '" + str + "' exists. This should not be possible. They will all be deleted", new Object[0]);
                            }
                            createFromQuery.clear();
                            return;
                        }
                        throw new IllegalArgumentException("No active subscription named '" + str + "' exists.");
                    }
                }, new Transaction.OnSuccess() { // from class: io.realm.Realm.3
                    @Override // io.realm.Realm.Transaction.OnSuccess
                    public void onSuccess() {
                        unsubscribeCallback.onSuccess(str);
                    }
                }, new Transaction.OnError() { // from class: io.realm.Realm.4
                    @Override // io.realm.Realm.Transaction.OnError
                    public void onError(Throwable th) {
                        unsubscribeCallback.onError(str, th);
                    }
                });
            }
            throw new UnsupportedOperationException("Realm is fully synchronized Realm. This method is only available when using query-based synchronization: " + this.configuration.getPath());
        } else {
            throw new IllegalArgumentException("'callback' required.");
        }
    }

    @Override // io.realm.BaseRealm
    public Realm freeze() {
        return (Realm) m.a(this.configuration, Realm.class, this.sharedRealm.getVersionID());
    }

    public Table a(Class<? extends RealmModel> cls) {
        return this.g.a(cls);
    }

    @Nullable
    public static Object getDefaultModule() {
        try {
            Constructor<?> constructor = Class.forName("io.realm.DefaultRealmModule").getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            return constructor.newInstance(new Object[0]);
        } catch (ClassNotFoundException unused) {
            return null;
        } catch (IllegalAccessException e2) {
            throw new RealmException("Could not create an instance of io.realm.DefaultRealmModule", e2);
        } catch (InstantiationException e3) {
            throw new RealmException("Could not create an instance of io.realm.DefaultRealmModule", e3);
        } catch (InvocationTargetException e4) {
            throw new RealmException("Could not create an instance of io.realm.DefaultRealmModule", e4);
        }
    }

    public static int getGlobalInstanceCount(RealmConfiguration realmConfiguration) {
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        m.a(realmConfiguration, new m.a() { // from class: io.realm.Realm.5
            @Override // io.realm.m.a
            public void a(int i) {
                atomicInteger.set(i);
            }
        });
        return atomicInteger.get();
    }

    public static int getLocalInstanceCount(RealmConfiguration realmConfiguration) {
        return m.a(realmConfiguration);
    }

    /* loaded from: classes5.dex */
    public static abstract class Callback extends BaseRealm.InstanceCallback<Realm> {
        public abstract void onSuccess(Realm realm);

        @Override // io.realm.BaseRealm.InstanceCallback
        public void onError(Throwable th) {
            super.onError(th);
        }
    }
}
