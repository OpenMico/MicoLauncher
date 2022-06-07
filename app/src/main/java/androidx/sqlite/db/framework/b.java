package androidx.sqlite.db.framework;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.io.File;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FrameworkSQLiteOpenHelper.java */
/* loaded from: classes.dex */
public class b implements SupportSQLiteOpenHelper {
    private final Context a;
    private final String b;
    private final SupportSQLiteOpenHelper.Callback c;
    private final boolean d;
    private final Object e = new Object();
    private a f;
    private boolean g;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(Context context, String str, SupportSQLiteOpenHelper.Callback callback, boolean z) {
        this.a = context;
        this.b = str;
        this.c = callback;
        this.d = z;
    }

    private a a() {
        a aVar;
        synchronized (this.e) {
            if (this.f == null) {
                a[] aVarArr = new a[1];
                if (Build.VERSION.SDK_INT < 23 || this.b == null || !this.d) {
                    this.f = new a(this.a, this.b, aVarArr, this.c);
                } else {
                    this.f = new a(this.a, new File(this.a.getNoBackupFilesDir(), this.b).getAbsolutePath(), aVarArr, this.c);
                }
                if (Build.VERSION.SDK_INT >= 16) {
                    this.f.setWriteAheadLoggingEnabled(this.g);
                }
            }
            aVar = this.f;
        }
        return aVar;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public String getDatabaseName() {
        return this.b;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    @RequiresApi(api = 16)
    public void setWriteAheadLoggingEnabled(boolean z) {
        synchronized (this.e) {
            if (this.f != null) {
                this.f.setWriteAheadLoggingEnabled(z);
            }
            this.g = z;
        }
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public SupportSQLiteDatabase getWritableDatabase() {
        return a().a();
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public SupportSQLiteDatabase getReadableDatabase() {
        return a().b();
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        a().close();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: FrameworkSQLiteOpenHelper.java */
    /* loaded from: classes.dex */
    public static class a extends SQLiteOpenHelper {
        final a[] a;
        final SupportSQLiteOpenHelper.Callback b;
        private boolean c;

        a(Context context, String str, final a[] aVarArr, final SupportSQLiteOpenHelper.Callback callback) {
            super(context, str, null, callback.version, new DatabaseErrorHandler() { // from class: androidx.sqlite.db.framework.b.a.1
                @Override // android.database.DatabaseErrorHandler
                public void onCorruption(SQLiteDatabase sQLiteDatabase) {
                    SupportSQLiteOpenHelper.Callback.this.onCorruption(a.a(aVarArr, sQLiteDatabase));
                }
            });
            this.b = callback;
            this.a = aVarArr;
        }

        synchronized SupportSQLiteDatabase a() {
            this.c = false;
            SQLiteDatabase writableDatabase = super.getWritableDatabase();
            if (this.c) {
                close();
                return a();
            }
            return a(writableDatabase);
        }

        synchronized SupportSQLiteDatabase b() {
            this.c = false;
            SQLiteDatabase readableDatabase = super.getReadableDatabase();
            if (this.c) {
                close();
                return b();
            }
            return a(readableDatabase);
        }

        a a(SQLiteDatabase sQLiteDatabase) {
            return a(this.a, sQLiteDatabase);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            this.b.onCreate(a(sQLiteDatabase));
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            this.c = true;
            this.b.onUpgrade(a(sQLiteDatabase), i, i2);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onConfigure(SQLiteDatabase sQLiteDatabase) {
            this.b.onConfigure(a(sQLiteDatabase));
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            this.c = true;
            this.b.onDowngrade(a(sQLiteDatabase), i, i2);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onOpen(SQLiteDatabase sQLiteDatabase) {
            if (!this.c) {
                this.b.onOpen(a(sQLiteDatabase));
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper, java.lang.AutoCloseable
        public synchronized void close() {
            super.close();
            this.a[0] = null;
        }

        static a a(a[] aVarArr, SQLiteDatabase sQLiteDatabase) {
            a aVar = aVarArr[0];
            if (aVar == null || !aVar.a(sQLiteDatabase)) {
                aVarArr[0] = new a(sQLiteDatabase);
            }
            return aVarArr[0];
        }
    }
}
