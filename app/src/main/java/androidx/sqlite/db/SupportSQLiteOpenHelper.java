package androidx.sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
public interface SupportSQLiteOpenHelper extends Closeable {

    /* loaded from: classes.dex */
    public interface Factory {
        @NonNull
        SupportSQLiteOpenHelper create(@NonNull Configuration configuration);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close();

    @Nullable
    String getDatabaseName();

    SupportSQLiteDatabase getReadableDatabase();

    SupportSQLiteDatabase getWritableDatabase();

    @RequiresApi(api = 16)
    void setWriteAheadLoggingEnabled(boolean z);

    /* loaded from: classes.dex */
    public static abstract class Callback {
        public final int version;

        public void onConfigure(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        }

        public abstract void onCreate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase);

        public void onOpen(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
        }

        public abstract void onUpgrade(@NonNull SupportSQLiteDatabase supportSQLiteDatabase, int i, int i2);

        public Callback(int i) {
            this.version = i;
        }

        public void onDowngrade(@NonNull SupportSQLiteDatabase supportSQLiteDatabase, int i, int i2) {
            throw new SQLiteException("Can't downgrade database from version " + i + " to " + i2);
        }

        public void onCorruption(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            Log.e("SupportSQLite", "Corruption reported by sqlite on database: " + supportSQLiteDatabase.getPath());
            if (!supportSQLiteDatabase.isOpen()) {
                a(supportSQLiteDatabase.getPath());
                return;
            }
            List<Pair<String, String>> list = null;
            try {
                try {
                    list = supportSQLiteDatabase.getAttachedDbs();
                } catch (SQLiteException unused) {
                }
                try {
                    supportSQLiteDatabase.close();
                } catch (IOException unused2) {
                }
            } finally {
                if (list != null) {
                    for (Pair<String, String> next : list) {
                        a((String) next.second);
                    }
                } else {
                    a(supportSQLiteDatabase.getPath());
                }
            }
        }

        private void a(String str) {
            if (!str.equalsIgnoreCase(":memory:") && str.trim().length() != 0) {
                Log.w("SupportSQLite", "deleting the database file: " + str);
                try {
                    if (Build.VERSION.SDK_INT >= 16) {
                        SQLiteDatabase.deleteDatabase(new File(str));
                    } else {
                        try {
                            if (!new File(str).delete()) {
                                Log.e("SupportSQLite", "Could not delete the database file " + str);
                            }
                        } catch (Exception e) {
                            Log.e("SupportSQLite", "error while deleting corrupted database file", e);
                        }
                    }
                } catch (Exception e2) {
                    Log.w("SupportSQLite", "delete failed: ", e2);
                }
            }
        }
    }

    /* loaded from: classes.dex */
    public static class Configuration {
        @NonNull
        public final Callback callback;
        @NonNull
        public final Context context;
        @Nullable
        public final String name;
        public final boolean useNoBackupDirectory;

        Configuration(@NonNull Context context, @Nullable String str, @NonNull Callback callback, boolean z) {
            this.context = context;
            this.name = str;
            this.callback = callback;
            this.useNoBackupDirectory = z;
        }

        @NonNull
        public static Builder builder(@NonNull Context context) {
            return new Builder(context);
        }

        /* loaded from: classes.dex */
        public static class Builder {
            Context a;
            String b;
            Callback c;
            boolean d;

            @NonNull
            public Configuration build() {
                if (this.c == null) {
                    throw new IllegalArgumentException("Must set a callback to create the configuration.");
                } else if (this.a == null) {
                    throw new IllegalArgumentException("Must set a non-null context to create the configuration.");
                } else if (!this.d || !TextUtils.isEmpty(this.b)) {
                    return new Configuration(this.a, this.b, this.c, this.d);
                } else {
                    throw new IllegalArgumentException("Must set a non-null database name to a configuration that uses the no backup directory.");
                }
            }

            Builder(@NonNull Context context) {
                this.a = context;
            }

            @NonNull
            public Builder name(@Nullable String str) {
                this.b = str;
                return this;
            }

            @NonNull
            public Builder callback(@NonNull Callback callback) {
                this.c = callback;
                return this;
            }

            @NonNull
            public Builder noBackupDirectory(boolean z) {
                this.d = z;
                return this;
            }
        }
    }
}
