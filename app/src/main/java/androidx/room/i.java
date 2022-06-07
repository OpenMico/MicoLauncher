package androidx.room;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.concurrent.Executor;

/* compiled from: QueryInterceptorOpenHelperFactory.java */
/* loaded from: classes.dex */
final class i implements SupportSQLiteOpenHelper.Factory {
    private final SupportSQLiteOpenHelper.Factory a;
    private final RoomDatabase.QueryCallback b;
    private final Executor c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(@NonNull SupportSQLiteOpenHelper.Factory factory, @NonNull RoomDatabase.QueryCallback queryCallback, @NonNull Executor executor) {
        this.a = factory;
        this.b = queryCallback;
        this.c = executor;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Factory
    @NonNull
    public SupportSQLiteOpenHelper create(@NonNull SupportSQLiteOpenHelper.Configuration configuration) {
        return new h(this.a.create(configuration), this.b, this.c);
    }
}
