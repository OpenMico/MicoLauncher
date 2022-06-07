package androidx.room;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.Callable;

/* compiled from: SQLiteCopyOpenHelperFactory.java */
/* loaded from: classes.dex */
class n implements SupportSQLiteOpenHelper.Factory {
    @Nullable
    private final String a;
    @Nullable
    private final File b;
    @Nullable
    private final Callable<InputStream> c;
    @NonNull
    private final SupportSQLiteOpenHelper.Factory d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public n(@Nullable String str, @Nullable File file, @Nullable Callable<InputStream> callable, @NonNull SupportSQLiteOpenHelper.Factory factory) {
        this.a = str;
        this.b = file;
        this.c = callable;
        this.d = factory;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Factory
    @NonNull
    public SupportSQLiteOpenHelper create(SupportSQLiteOpenHelper.Configuration configuration) {
        return new m(configuration.context, this.a, this.b, this.c, configuration.callback.version, this.d.create(configuration));
    }
}
