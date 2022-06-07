package androidx.room;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.CancellationSignal;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

/* compiled from: QueryInterceptorDatabase.java */
/* loaded from: classes.dex */
final class g implements SupportSQLiteDatabase {
    private final SupportSQLiteDatabase a;
    private final RoomDatabase.QueryCallback b;
    private final Executor c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(@NonNull SupportSQLiteDatabase supportSQLiteDatabase, @NonNull RoomDatabase.QueryCallback queryCallback, @NonNull Executor executor) {
        this.a = supportSQLiteDatabase;
        this.b = queryCallback;
        this.c = executor;
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    @NonNull
    public SupportSQLiteStatement compileStatement(@NonNull String str) {
        return new k(this.a.compileStatement(str), this.b, str, this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void f() {
        this.b.onQuery("BEGIN EXCLUSIVE TRANSACTION", Collections.emptyList());
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public void beginTransaction() {
        this.c.execute(new Runnable() { // from class: androidx.room.-$$Lambda$g$8pdm2AY8B6a4Hn0W34NvFL-_P3I
            @Override // java.lang.Runnable
            public final void run() {
                g.this.f();
            }
        });
        this.a.beginTransaction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e() {
        this.b.onQuery("BEGIN DEFERRED TRANSACTION", Collections.emptyList());
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public void beginTransactionNonExclusive() {
        this.c.execute(new Runnable() { // from class: androidx.room.-$$Lambda$g$0wEy7z5AhOnSqPkY6GdPpEZYQuE
            @Override // java.lang.Runnable
            public final void run() {
                g.this.e();
            }
        });
        this.a.beginTransactionNonExclusive();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d() {
        this.b.onQuery("BEGIN EXCLUSIVE TRANSACTION", Collections.emptyList());
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public void beginTransactionWithListener(@NonNull SQLiteTransactionListener sQLiteTransactionListener) {
        this.c.execute(new Runnable() { // from class: androidx.room.-$$Lambda$g$6L9BbDFQQ5TdzNQ39p--88ZDfUU
            @Override // java.lang.Runnable
            public final void run() {
                g.this.d();
            }
        });
        this.a.beginTransactionWithListener(sQLiteTransactionListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c() {
        this.b.onQuery("BEGIN DEFERRED TRANSACTION", Collections.emptyList());
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public void beginTransactionWithListenerNonExclusive(@NonNull SQLiteTransactionListener sQLiteTransactionListener) {
        this.c.execute(new Runnable() { // from class: androidx.room.-$$Lambda$g$yO0PVBxKf8Y0zX_wX83lKSUE388
            @Override // java.lang.Runnable
            public final void run() {
                g.this.c();
            }
        });
        this.a.beginTransactionWithListenerNonExclusive(sQLiteTransactionListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() {
        this.b.onQuery("END TRANSACTION", Collections.emptyList());
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public void endTransaction() {
        this.c.execute(new Runnable() { // from class: androidx.room.-$$Lambda$g$ofcYesE0Xlkta8pFovCnKAUsvRE
            @Override // java.lang.Runnable
            public final void run() {
                g.this.b();
            }
        });
        this.a.endTransaction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a() {
        this.b.onQuery("TRANSACTION SUCCESSFUL", Collections.emptyList());
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public void setTransactionSuccessful() {
        this.c.execute(new Runnable() { // from class: androidx.room.-$$Lambda$g$O12v78Vf9ipbGDSHGjH-8kmD_TY
            @Override // java.lang.Runnable
            public final void run() {
                g.this.a();
            }
        });
        this.a.setTransactionSuccessful();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public boolean inTransaction() {
        return this.a.inTransaction();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public boolean isDbLockedByCurrentThread() {
        return this.a.isDbLockedByCurrentThread();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public boolean yieldIfContendedSafely() {
        return this.a.yieldIfContendedSafely();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public boolean yieldIfContendedSafely(long j) {
        return this.a.yieldIfContendedSafely(j);
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public int getVersion() {
        return this.a.getVersion();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public void setVersion(int i) {
        this.a.setVersion(i);
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public long getMaximumSize() {
        return this.a.getMaximumSize();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public long setMaximumSize(long j) {
        return this.a.setMaximumSize(j);
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public long getPageSize() {
        return this.a.getPageSize();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public void setPageSize(long j) {
        this.a.setPageSize(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(String str) {
        this.b.onQuery(str, Collections.emptyList());
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    @NonNull
    public Cursor query(@NonNull final String str) {
        this.c.execute(new Runnable() { // from class: androidx.room.-$$Lambda$g$BLug-Toqd3_o7QrtS15XK4pQ1nE
            @Override // java.lang.Runnable
            public final void run() {
                g.this.b(str);
            }
        });
        return this.a.query(str);
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    @NonNull
    public Cursor query(@NonNull final String str, @NonNull Object[] objArr) {
        final ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(objArr));
        this.c.execute(new Runnable() { // from class: androidx.room.-$$Lambda$g$qzI31RCAxB8mvm3FcW5I2tZZI5s
            @Override // java.lang.Runnable
            public final void run() {
                g.this.b(str, arrayList);
            }
        });
        return this.a.query(str, objArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(String str, List list) {
        this.b.onQuery(str, list);
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    @NonNull
    public Cursor query(@NonNull final SupportSQLiteQuery supportSQLiteQuery) {
        final j jVar = new j();
        supportSQLiteQuery.bindTo(jVar);
        this.c.execute(new Runnable() { // from class: androidx.room.-$$Lambda$g$PxoADCTEUHywhPrdKDaJ0wu1rmc
            @Override // java.lang.Runnable
            public final void run() {
                g.this.b(supportSQLiteQuery, jVar);
            }
        });
        return this.a.query(supportSQLiteQuery);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(SupportSQLiteQuery supportSQLiteQuery, j jVar) {
        this.b.onQuery(supportSQLiteQuery.getSql(), jVar.a());
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    @NonNull
    public Cursor query(@NonNull final SupportSQLiteQuery supportSQLiteQuery, @NonNull CancellationSignal cancellationSignal) {
        final j jVar = new j();
        supportSQLiteQuery.bindTo(jVar);
        this.c.execute(new Runnable() { // from class: androidx.room.-$$Lambda$g$0ohyKKR7ha5YwWTeOFXNT3dtfzM
            @Override // java.lang.Runnable
            public final void run() {
                g.this.a(supportSQLiteQuery, jVar);
            }
        });
        return this.a.query(supportSQLiteQuery);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(SupportSQLiteQuery supportSQLiteQuery, j jVar) {
        this.b.onQuery(supportSQLiteQuery.getSql(), jVar.a());
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public long insert(@NonNull String str, int i, @NonNull ContentValues contentValues) throws SQLException {
        return this.a.insert(str, i, contentValues);
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public int delete(@NonNull String str, @NonNull String str2, @NonNull Object[] objArr) {
        return this.a.delete(str, str2, objArr);
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public int update(@NonNull String str, int i, @NonNull ContentValues contentValues, @NonNull String str2, @NonNull Object[] objArr) {
        return this.a.update(str, i, contentValues, str2, objArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str) {
        this.b.onQuery(str, new ArrayList(0));
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public void execSQL(@NonNull final String str) throws SQLException {
        this.c.execute(new Runnable() { // from class: androidx.room.-$$Lambda$g$o1a9qHJj6apAEbOCPvZ60-7KvHE
            @Override // java.lang.Runnable
            public final void run() {
                g.this.a(str);
            }
        });
        this.a.execSQL(str);
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public void execSQL(@NonNull final String str, @NonNull Object[] objArr) throws SQLException {
        final ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(objArr));
        this.c.execute(new Runnable() { // from class: androidx.room.-$$Lambda$g$z_i03amg9f3nT8xmzGeCzVQAGaE
            @Override // java.lang.Runnable
            public final void run() {
                g.this.a(str, arrayList);
            }
        });
        this.a.execSQL(str, arrayList.toArray());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, List list) {
        this.b.onQuery(str, list);
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public boolean isReadOnly() {
        return this.a.isReadOnly();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public boolean isOpen() {
        return this.a.isOpen();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public boolean needUpgrade(int i) {
        return this.a.needUpgrade(i);
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    @NonNull
    public String getPath() {
        return this.a.getPath();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public void setLocale(@NonNull Locale locale) {
        this.a.setLocale(locale);
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public void setMaxSqlCacheSize(int i) {
        this.a.setMaxSqlCacheSize(i);
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    @RequiresApi(api = 16)
    public void setForeignKeyConstraintsEnabled(boolean z) {
        this.a.setForeignKeyConstraintsEnabled(z);
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public boolean enableWriteAheadLogging() {
        return this.a.enableWriteAheadLogging();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    @RequiresApi(api = 16)
    public void disableWriteAheadLogging() {
        this.a.disableWriteAheadLogging();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    @RequiresApi(api = 16)
    public boolean isWriteAheadLoggingEnabled() {
        return this.a.isWriteAheadLoggingEnabled();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    @NonNull
    public List<Pair<String, String>> getAttachedDbs() {
        return this.a.getAttachedDbs();
    }

    @Override // androidx.sqlite.db.SupportSQLiteDatabase
    public boolean isDatabaseIntegrityOk() {
        return this.a.isDatabaseIntegrityOk();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.a.close();
    }
}
