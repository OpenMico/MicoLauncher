package androidx.room;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.concurrent.Executor;

/* compiled from: QueryInterceptorOpenHelper.java */
/* loaded from: classes.dex */
final class h implements d, SupportSQLiteOpenHelper {
    private final SupportSQLiteOpenHelper a;
    private final RoomDatabase.QueryCallback b;
    private final Executor c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(@NonNull SupportSQLiteOpenHelper supportSQLiteOpenHelper, @NonNull RoomDatabase.QueryCallback queryCallback, @NonNull Executor executor) {
        this.a = supportSQLiteOpenHelper;
        this.b = queryCallback;
        this.c = executor;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    @Nullable
    public String getDatabaseName() {
        return this.a.getDatabaseName();
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    @RequiresApi(api = 16)
    public void setWriteAheadLoggingEnabled(boolean z) {
        this.a.setWriteAheadLoggingEnabled(z);
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public SupportSQLiteDatabase getWritableDatabase() {
        return new g(this.a.getWritableDatabase(), this.b, this.c);
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public SupportSQLiteDatabase getReadableDatabase() {
        return new g(this.a.getReadableDatabase(), this.b, this.c);
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.a.close();
    }

    @Override // androidx.room.d
    @NonNull
    public SupportSQLiteOpenHelper b() {
        return this.a;
    }
}
