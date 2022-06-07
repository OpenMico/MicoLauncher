package androidx.room;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.CallSuper;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.util.Function;
import androidx.room.migration.Migration;
import androidx.room.util.SneakyThrow;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import com.xiaomi.idm.api.IDMServer;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes.dex */
public abstract class RoomDatabase {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final int MAX_BIND_PARAMETER_CNT = 999;
    boolean a;
    private Executor b;
    private Executor c;
    private SupportSQLiteOpenHelper d;
    private boolean f;
    @Nullable
    private a h;
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Deprecated
    protected List<Callback> mCallbacks;
    @Deprecated
    protected volatile SupportSQLiteDatabase mDatabase;
    private final ReentrantReadWriteLock g = new ReentrantReadWriteLock();
    private final ThreadLocal<Integer> i = new ThreadLocal<>();
    private final Map<String, Object> j = Collections.synchronizedMap(new HashMap());
    private final InvalidationTracker e = createInvalidationTracker();
    private final Map<Class<?>, Object> k = new HashMap();

    /* loaded from: classes.dex */
    public static abstract class Callback {
        public void onCreate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        }

        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        }

        public void onOpen(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        }
    }

    /* loaded from: classes.dex */
    public static abstract class PrepackagedDatabaseCallback {
        public void onOpenPrepackagedDatabase(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        }
    }

    /* loaded from: classes.dex */
    public interface QueryCallback {
        void onQuery(@NonNull String str, @NonNull List<Object> list);
    }

    @WorkerThread
    public abstract void clearAllTables();

    @NonNull
    protected abstract InvalidationTracker createInvalidationTracker();

    @NonNull
    protected abstract SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration databaseConfiguration);

    public Lock a() {
        return this.g.readLock();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public ThreadLocal<Integer> b() {
        return this.i;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Map<String, Object> c() {
        return this.j;
    }

    @Nullable
    public <T> T getTypeConverter(@NonNull Class<T> cls) {
        return (T) this.k.get(cls);
    }

    @CallSuper
    public void init(@NonNull DatabaseConfiguration databaseConfiguration) {
        this.d = createOpenHelper(databaseConfiguration);
        m mVar = (m) a(m.class, this.d);
        if (mVar != null) {
            mVar.a(databaseConfiguration);
        }
        b bVar = (b) a(b.class, this.d);
        if (bVar != null) {
            this.h = bVar.a();
            this.e.a(this.h);
        }
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 16) {
            if (databaseConfiguration.journalMode == JournalMode.WRITE_AHEAD_LOGGING) {
                z = true;
            }
            this.d.setWriteAheadLoggingEnabled(z);
        }
        this.mCallbacks = databaseConfiguration.callbacks;
        this.b = databaseConfiguration.queryExecutor;
        this.c = new o(databaseConfiguration.transactionExecutor);
        this.f = databaseConfiguration.allowMainThreadQueries;
        this.a = z;
        if (databaseConfiguration.multiInstanceInvalidation) {
            this.e.a(databaseConfiguration.context, databaseConfiguration.name);
        }
        Map<Class<?>, List<Class<?>>> requiredTypeConverters = getRequiredTypeConverters();
        BitSet bitSet = new BitSet();
        for (Map.Entry<Class<?>, List<Class<?>>> entry : requiredTypeConverters.entrySet()) {
            Class<?> key = entry.getKey();
            for (Class<?> cls : entry.getValue()) {
                int size = databaseConfiguration.typeConverters.size() - 1;
                while (true) {
                    if (size < 0) {
                        size = -1;
                        break;
                    } else if (cls.isAssignableFrom(databaseConfiguration.typeConverters.get(size).getClass())) {
                        bitSet.set(size);
                        break;
                    } else {
                        size--;
                    }
                }
                if (size >= 0) {
                    this.k.put(cls, databaseConfiguration.typeConverters.get(size));
                } else {
                    throw new IllegalArgumentException("A required type converter (" + cls + ") for " + key.getCanonicalName() + " is missing in the database configuration.");
                }
            }
        }
        for (int size2 = databaseConfiguration.typeConverters.size() - 1; size2 >= 0; size2--) {
            if (!bitSet.get(size2)) {
                throw new IllegalArgumentException("Unexpected type converter " + databaseConfiguration.typeConverters.get(size2) + ". Annotate TypeConverter class with @ProvidedTypeConverter annotation or remove this converter from the builder.");
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    private <T> T a(Class<T> cls, SupportSQLiteOpenHelper supportSQLiteOpenHelper) {
        if (cls.isInstance(supportSQLiteOpenHelper)) {
            return supportSQLiteOpenHelper;
        }
        if (supportSQLiteOpenHelper instanceof d) {
            return (T) a(cls, ((d) supportSQLiteOpenHelper).b());
        }
        return null;
    }

    @NonNull
    public SupportSQLiteOpenHelper getOpenHelper() {
        return this.d;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
        return Collections.emptyMap();
    }

    public boolean isOpen() {
        a aVar = this.h;
        if (aVar != null) {
            return aVar.e();
        }
        SupportSQLiteDatabase supportSQLiteDatabase = this.mDatabase;
        return supportSQLiteDatabase != null && supportSQLiteDatabase.isOpen();
    }

    public void close() {
        if (isOpen()) {
            ReentrantReadWriteLock.WriteLock writeLock = this.g.writeLock();
            writeLock.lock();
            try {
                this.e.b();
                this.d.close();
            } finally {
                writeLock.unlock();
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void assertNotMainThread() {
        if (!this.f && f()) {
            throw new IllegalStateException("Cannot access database on the main thread since it may potentially lock the UI for a long period of time.");
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void assertNotSuspendingTransaction() {
        if (!inTransaction() && this.i.get() != null) {
            throw new IllegalStateException("Cannot access database on a different coroutine context inherited from a suspending transaction.");
        }
    }

    @NonNull
    public Cursor query(@NonNull String str, @Nullable Object[] objArr) {
        return this.d.getWritableDatabase().query(new SimpleSQLiteQuery(str, objArr));
    }

    @NonNull
    public Cursor query(@NonNull SupportSQLiteQuery supportSQLiteQuery) {
        return query(supportSQLiteQuery, (CancellationSignal) null);
    }

    @NonNull
    public Cursor query(@NonNull SupportSQLiteQuery supportSQLiteQuery, @Nullable CancellationSignal cancellationSignal) {
        assertNotMainThread();
        assertNotSuspendingTransaction();
        if (cancellationSignal == null || Build.VERSION.SDK_INT < 16) {
            return this.d.getWritableDatabase().query(supportSQLiteQuery);
        }
        return this.d.getWritableDatabase().query(supportSQLiteQuery, cancellationSignal);
    }

    public SupportSQLiteStatement compileStatement(@NonNull String str) {
        assertNotMainThread();
        assertNotSuspendingTransaction();
        return this.d.getWritableDatabase().compileStatement(str);
    }

    @Deprecated
    public void beginTransaction() {
        assertNotMainThread();
        a aVar = this.h;
        if (aVar == null) {
            d();
        } else {
            aVar.a(new Function() { // from class: androidx.room.-$$Lambda$RoomDatabase$RT9FlOMMt5YDFzFDy_hvTgxdRyk
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    Object b;
                    b = RoomDatabase.this.b((SupportSQLiteDatabase) obj);
                    return b;
                }
            });
        }
    }

    public /* synthetic */ Object b(SupportSQLiteDatabase supportSQLiteDatabase) {
        d();
        return null;
    }

    private void d() {
        assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = this.d.getWritableDatabase();
        this.e.b(writableDatabase);
        if (Build.VERSION.SDK_INT < 16 || !writableDatabase.isWriteAheadLoggingEnabled()) {
            writableDatabase.beginTransaction();
        } else {
            writableDatabase.beginTransactionNonExclusive();
        }
    }

    @Deprecated
    public void endTransaction() {
        a aVar = this.h;
        if (aVar == null) {
            e();
        } else {
            aVar.a(new Function() { // from class: androidx.room.-$$Lambda$RoomDatabase$q5sgXTkHdF-eSAgw5_-_FHGBwso
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    Object a;
                    a = RoomDatabase.this.a((SupportSQLiteDatabase) obj);
                    return a;
                }
            });
        }
    }

    public /* synthetic */ Object a(SupportSQLiteDatabase supportSQLiteDatabase) {
        e();
        return null;
    }

    private void e() {
        this.d.getWritableDatabase().endTransaction();
        if (!inTransaction()) {
            this.e.refreshVersionsAsync();
        }
    }

    @NonNull
    public Executor getQueryExecutor() {
        return this.b;
    }

    @NonNull
    public Executor getTransactionExecutor() {
        return this.c;
    }

    @Deprecated
    public void setTransactionSuccessful() {
        this.d.getWritableDatabase().setTransactionSuccessful();
    }

    public void runInTransaction(@NonNull Runnable runnable) {
        beginTransaction();
        try {
            runnable.run();
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
    }

    public <V> V runInTransaction(@NonNull Callable<V> callable) {
        try {
            beginTransaction();
            try {
                V call = callable.call();
                setTransactionSuccessful();
                endTransaction();
                return call;
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                SneakyThrow.reThrow(e2);
                endTransaction();
                return null;
            }
        } catch (Throwable th) {
            endTransaction();
            throw th;
        }
    }

    protected void internalInitInvalidationTracker(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        this.e.a(supportSQLiteDatabase);
    }

    @NonNull
    public InvalidationTracker getInvalidationTracker() {
        return this.e;
    }

    public boolean inTransaction() {
        return this.d.getWritableDatabase().inTransaction();
    }

    /* loaded from: classes.dex */
    public enum JournalMode {
        AUTOMATIC,
        TRUNCATE,
        WRITE_AHEAD_LOGGING;

        @SuppressLint({"NewApi"})
        JournalMode a(Context context) {
            ActivityManager activityManager;
            if (this != AUTOMATIC) {
                return this;
            }
            if (Build.VERSION.SDK_INT < 16 || (activityManager = (ActivityManager) context.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)) == null || a(activityManager)) {
                return TRUNCATE;
            }
            return WRITE_AHEAD_LOGGING;
        }

        private static boolean a(@NonNull ActivityManager activityManager) {
            if (Build.VERSION.SDK_INT >= 19) {
                return activityManager.isLowRamDevice();
            }
            return false;
        }
    }

    /* loaded from: classes.dex */
    public static class Builder<T extends RoomDatabase> {
        private final Class<T> a;
        private final String b;
        private final Context c;
        private ArrayList<Callback> d;
        private PrepackagedDatabaseCallback e;
        private QueryCallback f;
        private Executor g;
        private List<Object> h;
        private Executor i;
        private Executor j;
        private SupportSQLiteOpenHelper.Factory k;
        private boolean l;
        private boolean n;
        private boolean p;
        private TimeUnit r;
        private Set<Integer> t;
        private Set<Integer> u;
        private String v;
        private File w;
        private Callable<InputStream> x;
        private long q = -1;
        private JournalMode m = JournalMode.AUTOMATIC;
        private boolean o = true;
        private final MigrationContainer s = new MigrationContainer();

        public Builder(@NonNull Context context, @NonNull Class<T> cls, @Nullable String str) {
            this.c = context;
            this.a = cls;
            this.b = str;
        }

        @NonNull
        public Builder<T> createFromAsset(@NonNull String str) {
            this.v = str;
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder<T> createFromAsset(@NonNull String str, @NonNull PrepackagedDatabaseCallback prepackagedDatabaseCallback) {
            this.e = prepackagedDatabaseCallback;
            this.v = str;
            return this;
        }

        @NonNull
        public Builder<T> createFromFile(@NonNull File file) {
            this.w = file;
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle", "StreamFiles"})
        public Builder<T> createFromFile(@NonNull File file, @NonNull PrepackagedDatabaseCallback prepackagedDatabaseCallback) {
            this.e = prepackagedDatabaseCallback;
            this.w = file;
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle"})
        public Builder<T> createFromInputStream(@NonNull Callable<InputStream> callable) {
            this.x = callable;
            return this;
        }

        @NonNull
        @SuppressLint({"BuilderSetStyle", "LambdaLast"})
        public Builder<T> createFromInputStream(@NonNull Callable<InputStream> callable, @NonNull PrepackagedDatabaseCallback prepackagedDatabaseCallback) {
            this.e = prepackagedDatabaseCallback;
            this.x = callable;
            return this;
        }

        @NonNull
        public Builder<T> openHelperFactory(@Nullable SupportSQLiteOpenHelper.Factory factory) {
            this.k = factory;
            return this;
        }

        @NonNull
        public Builder<T> addMigrations(@NonNull Migration... migrationArr) {
            if (this.u == null) {
                this.u = new HashSet();
            }
            for (Migration migration : migrationArr) {
                this.u.add(Integer.valueOf(migration.startVersion));
                this.u.add(Integer.valueOf(migration.endVersion));
            }
            this.s.addMigrations(migrationArr);
            return this;
        }

        @NonNull
        public Builder<T> allowMainThreadQueries() {
            this.l = true;
            return this;
        }

        @NonNull
        public Builder<T> setJournalMode(@NonNull JournalMode journalMode) {
            this.m = journalMode;
            return this;
        }

        @NonNull
        public Builder<T> setQueryExecutor(@NonNull Executor executor) {
            this.i = executor;
            return this;
        }

        @NonNull
        public Builder<T> setTransactionExecutor(@NonNull Executor executor) {
            this.j = executor;
            return this;
        }

        @NonNull
        public Builder<T> enableMultiInstanceInvalidation() {
            this.n = this.b != null;
            return this;
        }

        @NonNull
        public Builder<T> fallbackToDestructiveMigration() {
            this.o = false;
            this.p = true;
            return this;
        }

        @NonNull
        public Builder<T> fallbackToDestructiveMigrationOnDowngrade() {
            this.o = true;
            this.p = true;
            return this;
        }

        @NonNull
        public Builder<T> fallbackToDestructiveMigrationFrom(int... iArr) {
            if (this.t == null) {
                this.t = new HashSet(iArr.length);
            }
            for (int i : iArr) {
                this.t.add(Integer.valueOf(i));
            }
            return this;
        }

        @NonNull
        public Builder<T> addCallback(@NonNull Callback callback) {
            if (this.d == null) {
                this.d = new ArrayList<>();
            }
            this.d.add(callback);
            return this;
        }

        @NonNull
        public Builder<T> setQueryCallback(@NonNull QueryCallback queryCallback, @NonNull Executor executor) {
            this.f = queryCallback;
            this.g = executor;
            return this;
        }

        @NonNull
        public Builder<T> addTypeConverter(@NonNull Object obj) {
            if (this.h == null) {
                this.h = new ArrayList();
            }
            this.h.add(obj);
            return this;
        }

        @NonNull
        @ExperimentalRoomApi
        public Builder<T> setAutoCloseTimeout(@IntRange(from = 0) long j, @NonNull TimeUnit timeUnit) {
            if (j >= 0) {
                this.q = j;
                this.r = timeUnit;
                return this;
            }
            throw new IllegalArgumentException("autoCloseTimeout must be >= 0");
        }

        @NonNull
        @SuppressLint({"RestrictedApi"})
        public T build() {
            Executor executor;
            if (this.c == null) {
                throw new IllegalArgumentException("Cannot provide null context for the database.");
            } else if (this.a != null) {
                if (this.i == null && this.j == null) {
                    Executor iOThreadExecutor = ArchTaskExecutor.getIOThreadExecutor();
                    this.j = iOThreadExecutor;
                    this.i = iOThreadExecutor;
                } else {
                    Executor executor2 = this.i;
                    if (executor2 != null && this.j == null) {
                        this.j = executor2;
                    } else if (this.i == null && (executor = this.j) != null) {
                        this.i = executor;
                    }
                }
                Set<Integer> set = this.u;
                if (!(set == null || this.t == null)) {
                    for (Integer num : set) {
                        if (this.t.contains(num)) {
                            throw new IllegalArgumentException("Inconsistency detected. A Migration was supplied to addMigration(Migration... migrations) that has a start or end version equal to a start version supplied to fallbackToDestructiveMigrationFrom(int... startVersions). Start version: " + num);
                        }
                    }
                }
                c cVar = this.k;
                if (cVar == null) {
                    cVar = new FrameworkSQLiteOpenHelperFactory();
                }
                long j = this.q;
                if (j > 0) {
                    if (this.b != null) {
                        cVar = new c(cVar, new a(j, this.r, this.j));
                    } else {
                        throw new IllegalArgumentException("Cannot create auto-closing database for an in-memory database.");
                    }
                }
                if (!(this.v == null && this.w == null && this.x == null)) {
                    if (this.b != null) {
                        int i = 0;
                        int i2 = (this.v == null ? 0 : 1) + (this.w == null ? 0 : 1);
                        if (this.x != null) {
                            i = 1;
                        }
                        if (i2 + i == 1) {
                            cVar = new n(this.v, this.w, this.x, cVar);
                        } else {
                            throw new IllegalArgumentException("More than one of createFromAsset(), createFromInputStream(), and createFromFile() were called on this Builder, but the database can only be created using one of the three configurations.");
                        }
                    } else {
                        throw new IllegalArgumentException("Cannot create from asset or file for an in-memory database.");
                    }
                }
                QueryCallback queryCallback = this.f;
                i iVar = queryCallback != null ? new i(cVar, queryCallback, this.g) : cVar;
                Context context = this.c;
                DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(context, this.b, iVar, this.s, this.d, this.l, this.m.a(context), this.i, this.j, this.n, this.o, this.p, this.t, this.v, this.w, this.x, this.e, this.h);
                T t = (T) ((RoomDatabase) Room.a(this.a, "_Impl"));
                t.init(databaseConfiguration);
                return t;
            } else {
                throw new IllegalArgumentException("Must provide an abstract class that extends RoomDatabase");
            }
        }
    }

    /* loaded from: classes.dex */
    public static class MigrationContainer {
        private HashMap<Integer, TreeMap<Integer, Migration>> a = new HashMap<>();

        public void addMigrations(@NonNull Migration... migrationArr) {
            for (Migration migration : migrationArr) {
                a(migration);
            }
        }

        private void a(Migration migration) {
            int i = migration.startVersion;
            int i2 = migration.endVersion;
            TreeMap<Integer, Migration> treeMap = this.a.get(Integer.valueOf(i));
            if (treeMap == null) {
                treeMap = new TreeMap<>();
                this.a.put(Integer.valueOf(i), treeMap);
            }
            Migration migration2 = treeMap.get(Integer.valueOf(i2));
            if (migration2 != null) {
                Log.w("ROOM", "Overriding migration " + migration2 + " with " + migration);
            }
            treeMap.put(Integer.valueOf(i2), migration);
        }

        @Nullable
        public List<Migration> findMigrationPath(int i, int i2) {
            if (i == i2) {
                return Collections.emptyList();
            }
            return a(new ArrayList(), i2 > i, i, i2);
        }

        /* JADX WARN: Removed duplicated region for block: B:30:0x0016 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0017  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private java.util.List<androidx.room.migration.Migration> a(java.util.List<androidx.room.migration.Migration> r7, boolean r8, int r9, int r10) {
            /*
                r6 = this;
            L_0x0000:
                if (r8 == 0) goto L_0x0005
                if (r9 >= r10) goto L_0x005a
                goto L_0x0007
            L_0x0005:
                if (r9 <= r10) goto L_0x005a
            L_0x0007:
                java.util.HashMap<java.lang.Integer, java.util.TreeMap<java.lang.Integer, androidx.room.migration.Migration>> r0 = r6.a
                java.lang.Integer r1 = java.lang.Integer.valueOf(r9)
                java.lang.Object r0 = r0.get(r1)
                java.util.TreeMap r0 = (java.util.TreeMap) r0
                r1 = 0
                if (r0 != 0) goto L_0x0017
                return r1
            L_0x0017:
                if (r8 == 0) goto L_0x001e
                java.util.NavigableSet r2 = r0.descendingKeySet()
                goto L_0x0022
            L_0x001e:
                java.util.Set r2 = r0.keySet()
            L_0x0022:
                java.util.Iterator r2 = r2.iterator()
            L_0x0026:
                boolean r3 = r2.hasNext()
                r4 = 1
                r5 = 0
                if (r3 == 0) goto L_0x0056
                java.lang.Object r3 = r2.next()
                java.lang.Integer r3 = (java.lang.Integer) r3
                int r3 = r3.intValue()
                if (r8 == 0) goto L_0x0040
                if (r3 > r10) goto L_0x0045
                if (r3 <= r9) goto L_0x0045
                r5 = r4
                goto L_0x0045
            L_0x0040:
                if (r3 < r10) goto L_0x0045
                if (r3 >= r9) goto L_0x0045
                r5 = r4
            L_0x0045:
                if (r5 == 0) goto L_0x0026
                java.lang.Integer r9 = java.lang.Integer.valueOf(r3)
                java.lang.Object r9 = r0.get(r9)
                androidx.room.migration.Migration r9 = (androidx.room.migration.Migration) r9
                r7.add(r9)
                r9 = r3
                goto L_0x0057
            L_0x0056:
                r4 = r5
            L_0x0057:
                if (r4 != 0) goto L_0x0000
                return r1
            L_0x005a:
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.room.RoomDatabase.MigrationContainer.a(java.util.List, boolean, int, int):java.util.List");
        }
    }

    private static boolean f() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
