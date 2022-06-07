package io.realm;

import android.content.Context;
import android.os.Looper;
import io.reactivex.Flowable;
import io.realm.Realm;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.CheckedRow;
import io.realm.internal.ColumnInfo;
import io.realm.internal.InvalidRow;
import io.realm.internal.ObjectServerFacade;
import io.realm.internal.OsObjectStore;
import io.realm.internal.OsRealmConfig;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.OsSharedRealm;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import io.realm.internal.Util;
import io.realm.internal.async.RealmThreadPoolExecutor;
import io.realm.log.RealmLog;
import io.realm.m;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public abstract class BaseRealm implements Closeable {
    static volatile Context a;
    static final RealmThreadPoolExecutor b = RealmThreadPoolExecutor.newDefaultExecutor();
    public static final a objectContext = new a();
    final boolean c;
    protected final RealmConfiguration configuration;
    final long d;
    private m e;
    private boolean f;
    private OsSharedRealm.SchemaChangedCallback g;
    public OsSharedRealm sharedRealm;

    public abstract Flowable asFlowable();

    public abstract BaseRealm freeze();

    public abstract RealmSchema getSchema();

    public abstract boolean isEmpty();

    public BaseRealm(m mVar, @Nullable OsSchemaInfo osSchemaInfo, OsSharedRealm.VersionID versionID) {
        this(mVar.a(), osSchemaInfo, versionID);
        this.e = mVar;
    }

    BaseRealm(RealmConfiguration realmConfiguration, @Nullable OsSchemaInfo osSchemaInfo, OsSharedRealm.VersionID versionID) {
        this.g = new OsSharedRealm.SchemaChangedCallback() { // from class: io.realm.BaseRealm.1
            @Override // io.realm.internal.OsSharedRealm.SchemaChangedCallback
            public void onSchemaChanged() {
                RealmSchema schema = BaseRealm.this.getSchema();
                if (schema != null) {
                    schema.b();
                }
            }
        };
        this.d = Thread.currentThread().getId();
        this.configuration = realmConfiguration;
        OsSharedRealm.InitializationCallback initializationCallback = null;
        this.e = null;
        OsSharedRealm.MigrationCallback b2 = (osSchemaInfo == null || realmConfiguration.getMigration() == null) ? null : b(realmConfiguration.getMigration());
        final Realm.Transaction a2 = realmConfiguration.a();
        this.sharedRealm = OsSharedRealm.getInstance(new OsRealmConfig.Builder(realmConfiguration).fifoFallbackDir(new File(a.getFilesDir(), ".realm.temp")).autoUpdateNotification(true).migrationCallback(b2).schemaInfo(osSchemaInfo).initializationCallback(a2 != null ? new OsSharedRealm.InitializationCallback() { // from class: io.realm.BaseRealm.2
            @Override // io.realm.internal.OsSharedRealm.InitializationCallback
            public void onInit(OsSharedRealm osSharedRealm) {
                a2.execute(Realm.a(osSharedRealm));
            }
        } : initializationCallback), versionID);
        this.c = this.sharedRealm.isFrozen();
        this.f = true;
        this.sharedRealm.registerSchemaChangedCallback(this.g);
    }

    public BaseRealm(OsSharedRealm osSharedRealm) {
        this.g = new OsSharedRealm.SchemaChangedCallback() { // from class: io.realm.BaseRealm.1
            @Override // io.realm.internal.OsSharedRealm.SchemaChangedCallback
            public void onSchemaChanged() {
                RealmSchema schema = BaseRealm.this.getSchema();
                if (schema != null) {
                    schema.b();
                }
            }
        };
        this.d = Thread.currentThread().getId();
        this.configuration = osSharedRealm.getConfiguration();
        this.e = null;
        this.sharedRealm = osSharedRealm;
        this.c = osSharedRealm.isFrozen();
        this.f = false;
    }

    public void setAutoRefresh(boolean z) {
        checkIfValid();
        this.sharedRealm.setAutoRefresh(z);
    }

    public boolean isAutoRefresh() {
        return this.sharedRealm.isAutoRefresh();
    }

    public void refresh() {
        checkIfValid();
        if (!isInTransaction()) {
            this.sharedRealm.refresh();
            return;
        }
        throw new IllegalStateException("Cannot refresh a Realm instance inside a transaction.");
    }

    public boolean isInTransaction() {
        checkIfValid();
        return this.sharedRealm.isInTransaction();
    }

    protected <T extends BaseRealm> void addListener(RealmChangeListener<T> realmChangeListener) {
        if (realmChangeListener != null) {
            checkIfValid();
            this.sharedRealm.capabilities.checkCanDeliverNotification("Listeners cannot be used on current thread.");
            if (!this.c) {
                this.sharedRealm.realmNotifier.addChangeListener(this, realmChangeListener);
                return;
            }
            throw new IllegalStateException("It is not possible to add a change listener to a frozen Realm since it never changes.");
        }
        throw new IllegalArgumentException("Listener should not be null");
    }

    protected <T extends BaseRealm> void removeListener(RealmChangeListener<T> realmChangeListener) {
        if (realmChangeListener != null) {
            if (isClosed()) {
                RealmLog.warn("Calling removeChangeListener on a closed Realm %s, make sure to close all listeners before closing the Realm.", this.configuration.getPath());
            }
            this.sharedRealm.realmNotifier.removeChangeListener(this, realmChangeListener);
            return;
        }
        throw new IllegalArgumentException("Listener should not be null");
    }

    protected void removeAllListeners() {
        if (isClosed()) {
            RealmLog.warn("Calling removeChangeListener on a closed Realm %s, make sure to close all listeners before closing the Realm.", this.configuration.getPath());
        }
        this.sharedRealm.realmNotifier.removeChangeListeners(this);
    }

    public void writeCopyTo(File file) {
        if (file != null) {
            checkIfValid();
            this.sharedRealm.writeCopy(file, null);
            return;
        }
        throw new IllegalArgumentException("The destination argument cannot be null");
    }

    public void writeEncryptedCopyTo(File file, byte[] bArr) {
        if (file != null) {
            checkIfValid();
            this.sharedRealm.writeCopy(file, bArr);
            return;
        }
        throw new IllegalArgumentException("The destination argument cannot be null");
    }

    @Deprecated
    public boolean waitForChange() {
        checkIfValid();
        if (isInTransaction()) {
            throw new IllegalStateException("Cannot wait for changes inside of a transaction.");
        } else if (Looper.myLooper() == null) {
            boolean waitForChange = this.sharedRealm.waitForChange();
            if (waitForChange) {
                this.sharedRealm.refresh();
            }
            return waitForChange;
        } else {
            throw new IllegalStateException("Cannot wait for changes inside a Looper thread. Use RealmChangeListeners instead.");
        }
    }

    @Deprecated
    public void stopWaitForChange() {
        m mVar = this.e;
        if (mVar != null) {
            mVar.a(new m.b() { // from class: io.realm.BaseRealm.3
                @Override // io.realm.m.b
                public void a() {
                    if (BaseRealm.this.sharedRealm == null || BaseRealm.this.sharedRealm.isClosed()) {
                        throw new IllegalStateException("This Realm instance has already been closed, making it unusable.");
                    }
                    BaseRealm.this.sharedRealm.stopWaitForChange();
                }
            });
            return;
        }
        throw new IllegalStateException("This Realm instance has already been closed, making it unusable.");
    }

    public void beginTransaction() {
        checkIfValid();
        this.sharedRealm.beginTransaction();
    }

    public void commitTransaction() {
        checkIfValid();
        this.sharedRealm.commitTransaction();
    }

    public void cancelTransaction() {
        checkIfValid();
        this.sharedRealm.cancelTransaction();
    }

    public boolean isFrozen() {
        OsSharedRealm osSharedRealm = this.sharedRealm;
        if (osSharedRealm != null && !osSharedRealm.isClosed()) {
            return this.c;
        }
        throw new IllegalStateException("This Realm instance has already been closed, making it unusable.");
    }

    public void checkIfValid() {
        OsSharedRealm osSharedRealm = this.sharedRealm;
        if (osSharedRealm == null || osSharedRealm.isClosed()) {
            throw new IllegalStateException("This Realm instance has already been closed, making it unusable.");
        } else if (!this.c && this.d != Thread.currentThread().getId()) {
            throw new IllegalStateException("Realm access from incorrect thread. Realm objects can only be accessed on the thread they were created.");
        }
    }

    protected void checkIfInTransaction() {
        if (!this.sharedRealm.isInTransaction()) {
            throw new IllegalStateException("Changing Realm data can only be done from inside a transaction.");
        }
    }

    protected void checkIfPartialRealm() {
        if (!(this.configuration.f() ? ObjectServerFacade.getSyncFacadeIfPossible().isPartialRealm(this.configuration) : false)) {
            throw new IllegalStateException("This method is only available on partially synchronized Realms.");
        }
    }

    public void checkIfValidAndInTransaction() {
        if (!isInTransaction()) {
            throw new IllegalStateException("Changing Realm data can only be done from inside a transaction.");
        }
    }

    public void a() {
        if (this.configuration.f()) {
            throw new IllegalArgumentException("You cannot perform changes to a schema. Please update app and restart.");
        }
    }

    public String getPath() {
        return this.configuration.getPath();
    }

    public RealmConfiguration getConfiguration() {
        return this.configuration;
    }

    public long getVersion() {
        return OsObjectStore.getSchemaVersion(this.sharedRealm);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.c || this.d == Thread.currentThread().getId()) {
            m mVar = this.e;
            if (mVar != null) {
                mVar.a(this);
            } else {
                b();
            }
        } else {
            throw new IllegalStateException("Realm access from incorrect thread. Realm instance can only be closed on the thread it was created.");
        }
    }

    public void b() {
        this.e = null;
        OsSharedRealm osSharedRealm = this.sharedRealm;
        if (osSharedRealm != null && this.f) {
            osSharedRealm.close();
            this.sharedRealm = null;
        }
    }

    public boolean isClosed() {
        if (this.c || this.d == Thread.currentThread().getId()) {
            OsSharedRealm osSharedRealm = this.sharedRealm;
            return osSharedRealm == null || osSharedRealm.isClosed();
        }
        throw new IllegalStateException("Realm access from incorrect thread. Realm objects can only be accessed on the thread they were created.");
    }

    public <E extends RealmModel> E a(@Nullable Class<E> cls, @Nullable String str, UncheckedRow uncheckedRow) {
        if (str != null) {
            return new DynamicRealmObject(this, CheckedRow.getFromRow(uncheckedRow));
        }
        return (E) this.configuration.getSchemaMediator().newInstance(cls, this, uncheckedRow, getSchema().c(cls), false, Collections.emptyList());
    }

    public <E extends RealmModel> E a(@Nullable Class<E> cls, @Nullable String str, long j) {
        boolean z = str != null;
        Table a2 = z ? getSchema().a(str) : getSchema().a(cls);
        if (z) {
            return new DynamicRealmObject(this, j != -1 ? a2.getCheckedRow(j) : InvalidRow.INSTANCE);
        }
        return (E) this.configuration.getSchemaMediator().newInstance(cls, this, j != -1 ? a2.getUncheckedRow(j) : InvalidRow.INSTANCE, getSchema().c(cls), false, Collections.emptyList());
    }

    public void deleteAll() {
        checkIfValid();
        if (!this.sharedRealm.isPartial()) {
            boolean isPartial = this.sharedRealm.isPartial();
            for (RealmObjectSchema realmObjectSchema : getSchema().getAll()) {
                getSchema().a(realmObjectSchema.getClassName()).clear(isPartial);
            }
            return;
        }
        throw new IllegalStateException("This API is not supported by partially synchronized Realms. Either unsubscribe using 'Realm.unsubscribeAsync()' or delete the objects using a query and 'RealmResults.deleteAllFromRealm()'");
    }

    public static boolean a(final RealmConfiguration realmConfiguration) {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        if (OsObjectStore.callWithLock(realmConfiguration, new Runnable() { // from class: io.realm.BaseRealm.4
            @Override // java.lang.Runnable
            public void run() {
                atomicBoolean.set(Util.deleteRealm(realmConfiguration.getPath(), realmConfiguration.getRealmDirectory(), realmConfiguration.getRealmFileName()));
            }
        })) {
            return atomicBoolean.get();
        }
        throw new IllegalStateException("It's not allowed to delete the file associated with an open Realm. Remember to close() all the instances of the Realm before deleting its file: " + realmConfiguration.getPath());
    }

    public static boolean b(RealmConfiguration realmConfiguration) {
        OsSharedRealm instance = OsSharedRealm.getInstance(realmConfiguration, OsSharedRealm.VersionID.LIVE);
        Boolean valueOf = Boolean.valueOf(instance.compact());
        instance.close();
        return valueOf.booleanValue();
    }

    public static void migrateRealm(final RealmConfiguration realmConfiguration, @Nullable final RealmMigration realmMigration) throws FileNotFoundException {
        if (realmConfiguration == null) {
            throw new IllegalArgumentException("RealmConfiguration must be provided");
        } else if (realmConfiguration.f()) {
            throw new IllegalArgumentException("Manual migrations are not supported for synced Realms");
        } else if (realmMigration == null && realmConfiguration.getMigration() == null) {
            throw new RealmMigrationNeededException(realmConfiguration.getPath(), "RealmMigration must be provided.");
        } else {
            final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            m.a(realmConfiguration, new m.a() { // from class: io.realm.BaseRealm.5
                @Override // io.realm.m.a
                public void a(int i) {
                    if (i != 0) {
                        throw new IllegalStateException("Cannot migrate a Realm file that is already open: " + realmConfiguration.getPath());
                    } else if (!new File(realmConfiguration.getPath()).exists()) {
                        atomicBoolean.set(true);
                    } else {
                        OsSchemaInfo osSchemaInfo = new OsSchemaInfo(realmConfiguration.getSchemaMediator().getExpectedObjectSchemaInfoMap().values());
                        OsSharedRealm.MigrationCallback migrationCallback = null;
                        RealmMigration realmMigration2 = realmMigration;
                        if (realmMigration2 == null) {
                            realmMigration2 = realmConfiguration.getMigration();
                        }
                        if (realmMigration2 != null) {
                            migrationCallback = BaseRealm.b(realmMigration2);
                        }
                        OsSharedRealm instance = OsSharedRealm.getInstance(new OsRealmConfig.Builder(realmConfiguration).autoUpdateNotification(false).schemaInfo(osSchemaInfo).migrationCallback(migrationCallback), OsSharedRealm.VersionID.LIVE);
                        if (instance != null) {
                            instance.close();
                        }
                    }
                }
            });
            if (atomicBoolean.get()) {
                throw new FileNotFoundException("Cannot migrate a Realm file which doesn't exist: " + realmConfiguration.getPath());
            }
        }
    }

    public static OsSharedRealm.MigrationCallback b(final RealmMigration realmMigration) {
        return new OsSharedRealm.MigrationCallback() { // from class: io.realm.BaseRealm.6
            @Override // io.realm.internal.OsSharedRealm.MigrationCallback
            public void onMigrationNeeded(OsSharedRealm osSharedRealm, long j, long j2) {
                realmMigration.migrate(DynamicRealm.a(osSharedRealm), j, j2);
            }
        };
    }

    protected void finalize() throws Throwable {
        OsSharedRealm osSharedRealm;
        if (this.f && (osSharedRealm = this.sharedRealm) != null && !osSharedRealm.isClosed()) {
            RealmLog.warn("Remember to call close() on all Realm instances. Realm %s is being finalized without being closed, this can lead to running out of native memory.", this.configuration.getPath());
            m mVar = this.e;
            if (mVar != null) {
                mVar.b();
            }
        }
        super.finalize();
    }

    public OsSharedRealm c() {
        return this.sharedRealm;
    }

    /* loaded from: classes5.dex */
    public static final class RealmObjectContext {
        private BaseRealm a;
        private Row b;
        private ColumnInfo c;
        private boolean d;
        private List<String> e;

        public void set(BaseRealm baseRealm, Row row, ColumnInfo columnInfo, boolean z, List<String> list) {
            this.a = baseRealm;
            this.b = row;
            this.c = columnInfo;
            this.d = z;
            this.e = list;
        }

        public BaseRealm a() {
            return this.a;
        }

        public Row getRow() {
            return this.b;
        }

        public ColumnInfo getColumnInfo() {
            return this.c;
        }

        public boolean getAcceptDefaultValue() {
            return this.d;
        }

        public List<String> getExcludeFields() {
            return this.e;
        }

        public void clear() {
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = false;
            this.e = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class a extends ThreadLocal<RealmObjectContext> {
        a() {
        }

        /* renamed from: a */
        public RealmObjectContext initialValue() {
            return new RealmObjectContext();
        }
    }

    /* loaded from: classes5.dex */
    public static abstract class InstanceCallback<T extends BaseRealm> {
        public abstract void onSuccess(T t);

        public void onError(Throwable th) {
            throw new RealmException("Exception happens when initializing Realm in the background thread.", th);
        }
    }
}
