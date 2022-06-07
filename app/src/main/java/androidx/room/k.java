package androidx.room;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: QueryInterceptorStatement.java */
/* loaded from: classes.dex */
final class k implements SupportSQLiteStatement {
    private final SupportSQLiteStatement a;
    private final RoomDatabase.QueryCallback b;
    private final String c;
    private final List<Object> d = new ArrayList();
    private final Executor e;

    /* JADX INFO: Access modifiers changed from: package-private */
    public k(@NonNull SupportSQLiteStatement supportSQLiteStatement, @NonNull RoomDatabase.QueryCallback queryCallback, String str, @NonNull Executor executor) {
        this.a = supportSQLiteStatement;
        this.b = queryCallback;
        this.c = str;
        this.e = executor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e() {
        this.b.onQuery(this.c, this.d);
    }

    @Override // androidx.sqlite.db.SupportSQLiteStatement
    public void execute() {
        this.e.execute(new Runnable() { // from class: androidx.room.-$$Lambda$k$oZpy3jEtMpyXihUi4_Uh_SBKfTM
            @Override // java.lang.Runnable
            public final void run() {
                k.this.e();
            }
        });
        this.a.execute();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d() {
        this.b.onQuery(this.c, this.d);
    }

    @Override // androidx.sqlite.db.SupportSQLiteStatement
    public int executeUpdateDelete() {
        this.e.execute(new Runnable() { // from class: androidx.room.-$$Lambda$k$iY9vqYfzsp-sGYu-YM4I0Z-DAu0
            @Override // java.lang.Runnable
            public final void run() {
                k.this.d();
            }
        });
        return this.a.executeUpdateDelete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c() {
        this.b.onQuery(this.c, this.d);
    }

    @Override // androidx.sqlite.db.SupportSQLiteStatement
    public long executeInsert() {
        this.e.execute(new Runnable() { // from class: androidx.room.-$$Lambda$k$KzVLhWIMDCLwsWbu-PVYPrskw_M
            @Override // java.lang.Runnable
            public final void run() {
                k.this.c();
            }
        });
        return this.a.executeInsert();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() {
        this.b.onQuery(this.c, this.d);
    }

    @Override // androidx.sqlite.db.SupportSQLiteStatement
    public long simpleQueryForLong() {
        this.e.execute(new Runnable() { // from class: androidx.room.-$$Lambda$k$SLwX0rzWYXocuHOGqo8TvWbpz7A
            @Override // java.lang.Runnable
            public final void run() {
                k.this.b();
            }
        });
        return this.a.simpleQueryForLong();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a() {
        this.b.onQuery(this.c, this.d);
    }

    @Override // androidx.sqlite.db.SupportSQLiteStatement
    public String simpleQueryForString() {
        this.e.execute(new Runnable() { // from class: androidx.room.-$$Lambda$k$lVOWkq_HQZuvU5NiqEjx6dCLeO4
            @Override // java.lang.Runnable
            public final void run() {
                k.this.a();
            }
        });
        return this.a.simpleQueryForString();
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindNull(int i) {
        a(i, this.d.toArray());
        this.a.bindNull(i);
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindLong(int i, long j) {
        a(i, Long.valueOf(j));
        this.a.bindLong(i, j);
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindDouble(int i, double d) {
        a(i, Double.valueOf(d));
        this.a.bindDouble(i, d);
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindString(int i, String str) {
        a(i, str);
        this.a.bindString(i, str);
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindBlob(int i, byte[] bArr) {
        a(i, bArr);
        this.a.bindBlob(i, bArr);
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void clearBindings() {
        this.d.clear();
        this.a.clearBindings();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.a.close();
    }

    private void a(int i, Object obj) {
        int i2 = i - 1;
        if (i2 >= this.d.size()) {
            for (int size = this.d.size(); size <= i2; size++) {
                this.d.add(null);
            }
        }
        this.d.set(i2, obj);
    }
}
