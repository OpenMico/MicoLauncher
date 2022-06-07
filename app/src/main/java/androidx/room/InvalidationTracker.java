package androidx.room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

/* loaded from: classes.dex */
public class InvalidationTracker {
    private static final String[] i = {"UPDATE", "DELETE", "INSERT"};
    @NonNull
    final HashMap<String, Integer> a;
    final String[] b;
    @Nullable
    a c;
    final RoomDatabase d;
    AtomicBoolean e;
    volatile SupportSQLiteStatement f;
    @SuppressLint({"RestrictedApi"})
    @VisibleForTesting
    final SafeIterableMap<Observer, b> g;
    @VisibleForTesting
    Runnable h;
    @NonNull
    private Map<String, Set<String>> j;
    private volatile boolean k;
    private a l;
    private final e m;
    private f n;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public InvalidationTracker(RoomDatabase roomDatabase, String... strArr) {
        this(roomDatabase, new HashMap(), Collections.emptyMap(), strArr);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public InvalidationTracker(RoomDatabase roomDatabase, Map<String, String> map, Map<String, Set<String>> map2, String... strArr) {
        this.c = null;
        this.e = new AtomicBoolean(false);
        this.k = false;
        this.g = new SafeIterableMap<>();
        this.h = new Runnable() { // from class: androidx.room.InvalidationTracker.1
            /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
                jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x00e0: INVOKE  (r0 I:java.util.concurrent.locks.Lock) type: INTERFACE call: java.util.concurrent.locks.Lock.unlock():void, block:B:53:0x00e0
                	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
                	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
                	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
                	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
                */
            @Override // java.lang.Runnable
            public void run() {
                /*
                    Method dump skipped, instructions count: 241
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.room.InvalidationTracker.AnonymousClass1.run():void");
            }

            /* JADX WARN: Finally extract failed */
            private Set<Integer> a() {
                HashSet hashSet = new HashSet();
                Cursor query = InvalidationTracker.this.d.query(new SimpleSQLiteQuery("SELECT * FROM room_table_modification_log WHERE invalidated = 1;"));
                while (query.moveToNext()) {
                    try {
                        hashSet.add(Integer.valueOf(query.getInt(0)));
                    } catch (Throwable th) {
                        query.close();
                        throw th;
                    }
                }
                query.close();
                if (!hashSet.isEmpty()) {
                    InvalidationTracker.this.f.executeUpdateDelete();
                }
                return hashSet;
            }
        };
        this.d = roomDatabase;
        this.l = new a(strArr.length);
        this.a = new HashMap<>();
        this.j = map2;
        this.m = new e(this.d);
        int length = strArr.length;
        this.b = new String[length];
        for (int i2 = 0; i2 < length; i2++) {
            String lowerCase = strArr[i2].toLowerCase(Locale.US);
            this.a.put(lowerCase, Integer.valueOf(i2));
            String str = map.get(strArr[i2]);
            if (str != null) {
                this.b[i2] = str.toLowerCase(Locale.US);
            } else {
                this.b[i2] = lowerCase;
            }
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String lowerCase2 = entry.getValue().toLowerCase(Locale.US);
            if (this.a.containsKey(lowerCase2)) {
                String lowerCase3 = entry.getKey().toLowerCase(Locale.US);
                HashMap<String, Integer> hashMap = this.a;
                hashMap.put(lowerCase3, hashMap.get(lowerCase2));
            }
        }
    }

    public void a(a aVar) {
        this.c = aVar;
        this.c.a(new Runnable() { // from class: androidx.room.-$$Lambda$qHNedgNuw7cBjnGB2Zuw7y7n3fg
            @Override // java.lang.Runnable
            public final void run() {
                InvalidationTracker.this.a();
            }
        });
    }

    public void a(SupportSQLiteDatabase supportSQLiteDatabase) {
        synchronized (this) {
            if (this.k) {
                Log.e("ROOM", "Invalidation tracker is initialized twice :/.");
                return;
            }
            supportSQLiteDatabase.execSQL("PRAGMA temp_store = MEMORY;");
            supportSQLiteDatabase.execSQL("PRAGMA recursive_triggers='ON';");
            supportSQLiteDatabase.execSQL("CREATE TEMP TABLE room_table_modification_log(table_id INTEGER PRIMARY KEY, invalidated INTEGER NOT NULL DEFAULT 0)");
            b(supportSQLiteDatabase);
            this.f = supportSQLiteDatabase.compileStatement("UPDATE room_table_modification_log SET invalidated = 0 WHERE invalidated = 1 ");
            this.k = true;
        }
    }

    public void a() {
        synchronized (this) {
            this.k = false;
            this.l.a();
        }
    }

    public void a(Context context, String str) {
        this.n = new f(context, str, this, this.d.getQueryExecutor());
    }

    public void b() {
        f fVar = this.n;
        if (fVar != null) {
            fVar.a();
            this.n = null;
        }
    }

    private static void a(StringBuilder sb, String str, String str2) {
        sb.append("`");
        sb.append("room_table_modification_trigger_");
        sb.append(str);
        sb.append("_");
        sb.append(str2);
        sb.append("`");
    }

    private void a(SupportSQLiteDatabase supportSQLiteDatabase, int i2) {
        String str = this.b[i2];
        StringBuilder sb = new StringBuilder();
        String[] strArr = i;
        for (String str2 : strArr) {
            sb.setLength(0);
            sb.append("DROP TRIGGER IF EXISTS ");
            a(sb, str, str2);
            supportSQLiteDatabase.execSQL(sb.toString());
        }
    }

    private void b(SupportSQLiteDatabase supportSQLiteDatabase, int i2) {
        supportSQLiteDatabase.execSQL("INSERT OR IGNORE INTO room_table_modification_log VALUES(" + i2 + ", 0)");
        String str = this.b[i2];
        StringBuilder sb = new StringBuilder();
        String[] strArr = i;
        for (String str2 : strArr) {
            sb.setLength(0);
            sb.append("CREATE TEMP TRIGGER IF NOT EXISTS ");
            a(sb, str, str2);
            sb.append(" AFTER ");
            sb.append(str2);
            sb.append(" ON `");
            sb.append(str);
            sb.append("` BEGIN UPDATE ");
            sb.append("room_table_modification_log");
            sb.append(" SET ");
            sb.append("invalidated");
            sb.append(" = 1");
            sb.append(" WHERE ");
            sb.append("table_id");
            sb.append(" = ");
            sb.append(i2);
            sb.append(" AND ");
            sb.append("invalidated");
            sb.append(" = 0");
            sb.append("; END");
            supportSQLiteDatabase.execSQL(sb.toString());
        }
    }

    @SuppressLint({"RestrictedApi"})
    @WorkerThread
    public void addObserver(@NonNull Observer observer) {
        b putIfAbsent;
        String[] b2 = b(observer.c);
        int[] iArr = new int[b2.length];
        int length = b2.length;
        for (int i2 = 0; i2 < length; i2++) {
            Integer num = this.a.get(b2[i2].toLowerCase(Locale.US));
            if (num != null) {
                iArr[i2] = num.intValue();
            } else {
                throw new IllegalArgumentException("There is no table with name " + b2[i2]);
            }
        }
        b bVar = new b(observer, iArr, b2);
        synchronized (this.g) {
            putIfAbsent = this.g.putIfAbsent(observer, bVar);
        }
        if (putIfAbsent == null && this.l.a(iArr)) {
            d();
        }
    }

    private String[] a(String[] strArr) {
        String[] b2 = b(strArr);
        for (String str : b2) {
            if (!this.a.containsKey(str.toLowerCase(Locale.US))) {
                throw new IllegalArgumentException("There is no table with name " + str);
            }
        }
        return b2;
    }

    private String[] b(String[] strArr) {
        HashSet hashSet = new HashSet();
        for (String str : strArr) {
            String lowerCase = str.toLowerCase(Locale.US);
            if (this.j.containsKey(lowerCase)) {
                hashSet.addAll(this.j.get(lowerCase));
            } else {
                hashSet.add(str);
            }
        }
        return (String[]) hashSet.toArray(new String[hashSet.size()]);
    }

    private static void c(SupportSQLiteDatabase supportSQLiteDatabase) {
        if (Build.VERSION.SDK_INT < 16 || !supportSQLiteDatabase.isWriteAheadLoggingEnabled()) {
            supportSQLiteDatabase.beginTransaction();
        } else {
            supportSQLiteDatabase.beginTransactionNonExclusive();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void addWeakObserver(Observer observer) {
        addObserver(new c(this, observer));
    }

    @SuppressLint({"RestrictedApi"})
    @WorkerThread
    public void removeObserver(@NonNull Observer observer) {
        b remove;
        synchronized (this.g) {
            remove = this.g.remove(observer);
        }
        if (remove != null && this.l.b(remove.a)) {
            d();
        }
    }

    boolean c() {
        if (!this.d.isOpen()) {
            return false;
        }
        if (!this.k) {
            this.d.getOpenHelper().getWritableDatabase();
        }
        if (this.k) {
            return true;
        }
        Log.e("ROOM", "database is not initialized even though it is open");
        return false;
    }

    public void refreshVersionsAsync() {
        if (this.e.compareAndSet(false, true)) {
            a aVar = this.c;
            if (aVar != null) {
                aVar.a();
            }
            this.d.getQueryExecutor().execute(this.h);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @WorkerThread
    public void refreshVersionsSync() {
        a aVar = this.c;
        if (aVar != null) {
            aVar.a();
        }
        d();
        this.h.run();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @VisibleForTesting(otherwise = 3)
    public void notifyObserversByTableNames(String... strArr) {
        synchronized (this.g) {
            Iterator<Map.Entry<Observer, b>> it = this.g.iterator();
            while (it.hasNext()) {
                Map.Entry<Observer, b> next = it.next();
                if (!next.getKey().a()) {
                    next.getValue().a(strArr);
                }
            }
        }
    }

    public void b(SupportSQLiteDatabase supportSQLiteDatabase) {
        if (!supportSQLiteDatabase.inTransaction()) {
            while (true) {
                try {
                    Lock a2 = this.d.a();
                    a2.lock();
                    int[] b2 = this.l.b();
                    if (b2 == null) {
                        a2.unlock();
                        return;
                    }
                    int length = b2.length;
                    c(supportSQLiteDatabase);
                    for (int i2 = 0; i2 < length; i2++) {
                        switch (b2[i2]) {
                            case 1:
                                b(supportSQLiteDatabase, i2);
                                break;
                            case 2:
                                a(supportSQLiteDatabase, i2);
                                break;
                        }
                    }
                    supportSQLiteDatabase.setTransactionSuccessful();
                    supportSQLiteDatabase.endTransaction();
                    this.l.c();
                    a2.unlock();
                } catch (SQLiteException | IllegalStateException e) {
                    Log.e("ROOM", "Cannot run invalidation tracker. Is the db closed?", e);
                    return;
                }
            }
        }
    }

    void d() {
        if (this.d.isOpen()) {
            b(this.d.getOpenHelper().getWritableDatabase());
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Deprecated
    public <T> LiveData<T> createLiveData(String[] strArr, Callable<T> callable) {
        return createLiveData(strArr, false, callable);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public <T> LiveData<T> createLiveData(String[] strArr, boolean z, Callable<T> callable) {
        return this.m.a(a(strArr), z, callable);
    }

    /* loaded from: classes.dex */
    public static class b {
        final int[] a;
        final Observer b;
        private final String[] c;
        private final Set<String> d;

        b(Observer observer, int[] iArr, String[] strArr) {
            this.b = observer;
            this.a = iArr;
            this.c = strArr;
            if (iArr.length == 1) {
                HashSet hashSet = new HashSet();
                hashSet.add(this.c[0]);
                this.d = Collections.unmodifiableSet(hashSet);
                return;
            }
            this.d = null;
        }

        void a(Set<Integer> set) {
            int length = this.a.length;
            Set<String> set2 = null;
            for (int i = 0; i < length; i++) {
                if (set.contains(Integer.valueOf(this.a[i]))) {
                    if (length == 1) {
                        set2 = this.d;
                    } else {
                        if (set2 == null) {
                            set2 = new HashSet<>(length);
                        }
                        set2.add(this.c[i]);
                    }
                }
            }
            if (set2 != null) {
                this.b.onInvalidated(set2);
            }
        }

        void a(String[] strArr) {
            Set<String> set = null;
            if (this.c.length == 1) {
                int length = strArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    } else if (strArr[i].equalsIgnoreCase(this.c[0])) {
                        set = this.d;
                        break;
                    } else {
                        i++;
                    }
                }
            } else {
                HashSet hashSet = new HashSet();
                for (String str : strArr) {
                    String[] strArr2 = this.c;
                    int length2 = strArr2.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 < length2) {
                            String str2 = strArr2[i2];
                            if (str2.equalsIgnoreCase(str)) {
                                hashSet.add(str2);
                                break;
                            }
                            i2++;
                        }
                    }
                }
                if (hashSet.size() > 0) {
                    set = hashSet;
                }
            }
            if (set != null) {
                this.b.onInvalidated(set);
            }
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Observer {
        final String[] c;

        boolean a() {
            return false;
        }

        public abstract void onInvalidated(@NonNull Set<String> set);

        protected Observer(@NonNull String str, String... strArr) {
            this.c = (String[]) Arrays.copyOf(strArr, strArr.length + 1);
            this.c[strArr.length] = str;
        }

        public Observer(@NonNull String[] strArr) {
            this.c = (String[]) Arrays.copyOf(strArr, strArr.length);
        }
    }

    /* loaded from: classes.dex */
    public static class a {
        final long[] a;
        final boolean[] b;
        final int[] c;
        boolean d;
        boolean e;

        a(int i) {
            this.a = new long[i];
            this.b = new boolean[i];
            this.c = new int[i];
            Arrays.fill(this.a, 0L);
            Arrays.fill(this.b, false);
        }

        boolean a(int... iArr) {
            boolean z;
            synchronized (this) {
                z = false;
                for (int i : iArr) {
                    long j = this.a[i];
                    this.a[i] = 1 + j;
                    if (j == 0) {
                        this.d = true;
                        z = true;
                    }
                }
            }
            return z;
        }

        boolean b(int... iArr) {
            boolean z;
            synchronized (this) {
                z = false;
                for (int i : iArr) {
                    long j = this.a[i];
                    this.a[i] = j - 1;
                    if (j == 1) {
                        this.d = true;
                        z = true;
                    }
                }
            }
            return z;
        }

        void a() {
            synchronized (this) {
                Arrays.fill(this.b, false);
                this.d = true;
            }
        }

        @Nullable
        int[] b() {
            synchronized (this) {
                if (this.d && !this.e) {
                    int length = this.a.length;
                    int i = 0;
                    while (true) {
                        int i2 = 1;
                        if (i < length) {
                            boolean z = this.a[i] > 0;
                            if (z != this.b[i]) {
                                int[] iArr = this.c;
                                if (!z) {
                                    i2 = 2;
                                }
                                iArr[i] = i2;
                            } else {
                                this.c[i] = 0;
                            }
                            this.b[i] = z;
                            i++;
                        } else {
                            this.e = true;
                            this.d = false;
                            return this.c;
                        }
                    }
                }
                return null;
            }
        }

        void c() {
            synchronized (this) {
                this.e = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class c extends Observer {
        final InvalidationTracker a;
        final WeakReference<Observer> b;

        c(InvalidationTracker invalidationTracker, Observer observer) {
            super(observer.c);
            this.a = invalidationTracker;
            this.b = new WeakReference<>(observer);
        }

        @Override // androidx.room.InvalidationTracker.Observer
        public void onInvalidated(@NonNull Set<String> set) {
            Observer observer = this.b.get();
            if (observer == null) {
                this.a.removeObserver(this);
            } else {
                observer.onInvalidated(set);
            }
        }
    }
}
