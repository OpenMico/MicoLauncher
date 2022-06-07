package androidx.room;

import androidx.annotation.NonNull;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

/* compiled from: AutoClosingRoomOpenHelperFactory.java */
/* loaded from: classes.dex */
final class c implements SupportSQLiteOpenHelper.Factory {
    @NonNull
    private final SupportSQLiteOpenHelper.Factory a;
    @NonNull
    private final a b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(@NonNull SupportSQLiteOpenHelper.Factory factory, @NonNull a aVar) {
        this.a = factory;
        this.b = aVar;
    }

    @NonNull
    /* renamed from: a */
    public b create(@NonNull SupportSQLiteOpenHelper.Configuration configuration) {
        return new b(this.a.create(configuration), this.b);
    }
}
