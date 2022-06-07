package androidx.room;

import androidx.annotation.RestrictTo;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.concurrent.atomic.AtomicBoolean;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public abstract class SharedSQLiteStatement {
    private final AtomicBoolean a = new AtomicBoolean(false);
    private final RoomDatabase b;
    private volatile SupportSQLiteStatement c;

    protected abstract String createQuery();

    public SharedSQLiteStatement(RoomDatabase roomDatabase) {
        this.b = roomDatabase;
    }

    protected void assertNotMainThread() {
        this.b.assertNotMainThread();
    }

    private SupportSQLiteStatement a() {
        return this.b.compileStatement(createQuery());
    }

    private SupportSQLiteStatement a(boolean z) {
        if (!z) {
            return a();
        }
        if (this.c == null) {
            this.c = a();
        }
        return this.c;
    }

    public SupportSQLiteStatement acquire() {
        assertNotMainThread();
        return a(this.a.compareAndSet(false, true));
    }

    public void release(SupportSQLiteStatement supportSQLiteStatement) {
        if (supportSQLiteStatement == this.c) {
            this.a.set(false);
        }
    }
}
