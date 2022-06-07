package androidx.sqlite.db.framework;

import android.database.sqlite.SQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;

/* compiled from: FrameworkSQLiteStatement.java */
/* loaded from: classes.dex */
class d extends c implements SupportSQLiteStatement {
    private final SQLiteStatement a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(SQLiteStatement sQLiteStatement) {
        super(sQLiteStatement);
        this.a = sQLiteStatement;
    }

    @Override // androidx.sqlite.db.SupportSQLiteStatement
    public void execute() {
        this.a.execute();
    }

    @Override // androidx.sqlite.db.SupportSQLiteStatement
    public int executeUpdateDelete() {
        return this.a.executeUpdateDelete();
    }

    @Override // androidx.sqlite.db.SupportSQLiteStatement
    public long executeInsert() {
        return this.a.executeInsert();
    }

    @Override // androidx.sqlite.db.SupportSQLiteStatement
    public long simpleQueryForLong() {
        return this.a.simpleQueryForLong();
    }

    @Override // androidx.sqlite.db.SupportSQLiteStatement
    public String simpleQueryForString() {
        return this.a.simpleQueryForString();
    }
}
