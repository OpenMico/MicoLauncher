package androidx.room;

import androidx.sqlite.db.SupportSQLiteProgram;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: QueryInterceptorProgram.java */
/* loaded from: classes.dex */
public final class j implements SupportSQLiteProgram {
    private List<Object> a = new ArrayList();

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindNull(int i) {
        a(i, null);
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindLong(int i, long j) {
        a(i, Long.valueOf(j));
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindDouble(int i, double d) {
        a(i, Double.valueOf(d));
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindString(int i, String str) {
        a(i, str);
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void bindBlob(int i, byte[] bArr) {
        a(i, bArr);
    }

    @Override // androidx.sqlite.db.SupportSQLiteProgram
    public void clearBindings() {
        this.a.clear();
    }

    private void a(int i, Object obj) {
        int i2 = i - 1;
        if (i2 >= this.a.size()) {
            for (int size = this.a.size(); size <= i2; size++) {
                this.a.add(null);
            }
        }
        this.a.set(i2, obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<Object> a() {
        return this.a;
    }
}
