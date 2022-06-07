package androidx.room;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class RoomOpenHelper extends SupportSQLiteOpenHelper.Callback {
    @Nullable
    private DatabaseConfiguration a;
    @NonNull
    private final Delegate b;
    @NonNull
    private final String c;
    @NonNull
    private final String d;

    public RoomOpenHelper(@NonNull DatabaseConfiguration databaseConfiguration, @NonNull Delegate delegate, @NonNull String str, @NonNull String str2) {
        super(delegate.version);
        this.a = databaseConfiguration;
        this.b = delegate;
        this.c = str;
        this.d = str2;
    }

    public RoomOpenHelper(@NonNull DatabaseConfiguration databaseConfiguration, @NonNull Delegate delegate, @NonNull String str) {
        this(databaseConfiguration, delegate, "", str);
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
    public void onConfigure(SupportSQLiteDatabase supportSQLiteDatabase) {
        super.onConfigure(supportSQLiteDatabase);
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
    public void onCreate(SupportSQLiteDatabase supportSQLiteDatabase) {
        boolean e = e(supportSQLiteDatabase);
        this.b.createAllTables(supportSQLiteDatabase);
        if (!e) {
            ValidationResult onValidateSchema = this.b.onValidateSchema(supportSQLiteDatabase);
            if (!onValidateSchema.isValid) {
                throw new IllegalStateException("Pre-packaged database has an invalid schema: " + onValidateSchema.expectedFoundMsg);
            }
        }
        b(supportSQLiteDatabase);
        this.b.onCreate(supportSQLiteDatabase);
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
    public void onUpgrade(SupportSQLiteDatabase supportSQLiteDatabase, int i, int i2) {
        boolean z;
        List<Migration> findMigrationPath;
        DatabaseConfiguration databaseConfiguration = this.a;
        if (databaseConfiguration == null || (findMigrationPath = databaseConfiguration.migrationContainer.findMigrationPath(i, i2)) == null) {
            z = false;
        } else {
            this.b.onPreMigrate(supportSQLiteDatabase);
            for (Migration migration : findMigrationPath) {
                migration.migrate(supportSQLiteDatabase);
            }
            ValidationResult onValidateSchema = this.b.onValidateSchema(supportSQLiteDatabase);
            if (onValidateSchema.isValid) {
                this.b.onPostMigrate(supportSQLiteDatabase);
                b(supportSQLiteDatabase);
                z = true;
            } else {
                throw new IllegalStateException("Migration didn't properly handle: " + onValidateSchema.expectedFoundMsg);
            }
        }
        if (!z) {
            DatabaseConfiguration databaseConfiguration2 = this.a;
            if (databaseConfiguration2 == null || databaseConfiguration2.isMigrationRequired(i, i2)) {
                throw new IllegalStateException("A migration from " + i + " to " + i2 + " was required but not found. Please provide the necessary Migration path via RoomDatabase.Builder.addMigration(Migration ...) or allow for destructive migrations via one of the RoomDatabase.Builder.fallbackToDestructiveMigration* methods.");
            }
            this.b.dropAllTables(supportSQLiteDatabase);
            this.b.createAllTables(supportSQLiteDatabase);
        }
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
    public void onDowngrade(SupportSQLiteDatabase supportSQLiteDatabase, int i, int i2) {
        onUpgrade(supportSQLiteDatabase, i, i2);
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
    public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
        super.onOpen(supportSQLiteDatabase);
        a(supportSQLiteDatabase);
        this.b.onOpen(supportSQLiteDatabase);
        this.a = null;
    }

    /* JADX WARN: Finally extract failed */
    private void a(SupportSQLiteDatabase supportSQLiteDatabase) {
        if (d(supportSQLiteDatabase)) {
            String str = null;
            Cursor query = supportSQLiteDatabase.query(new SimpleSQLiteQuery(RoomMasterTable.READ_QUERY));
            try {
                if (query.moveToFirst()) {
                    str = query.getString(0);
                }
                query.close();
                if (!this.c.equals(str) && !this.d.equals(str)) {
                    throw new IllegalStateException("Room cannot verify the data integrity. Looks like you've changed schema but forgot to update the version number. You can simply fix this by increasing the version number.");
                }
            } catch (Throwable th) {
                query.close();
                throw th;
            }
        } else {
            ValidationResult onValidateSchema = this.b.onValidateSchema(supportSQLiteDatabase);
            if (onValidateSchema.isValid) {
                this.b.onPostMigrate(supportSQLiteDatabase);
                b(supportSQLiteDatabase);
                return;
            }
            throw new IllegalStateException("Pre-packaged database has an invalid schema: " + onValidateSchema.expectedFoundMsg);
        }
    }

    private void b(SupportSQLiteDatabase supportSQLiteDatabase) {
        c(supportSQLiteDatabase);
        supportSQLiteDatabase.execSQL(RoomMasterTable.createInsertQuery(this.c));
    }

    private void c(SupportSQLiteDatabase supportSQLiteDatabase) {
        supportSQLiteDatabase.execSQL(RoomMasterTable.CREATE_QUERY);
    }

    private static boolean d(SupportSQLiteDatabase supportSQLiteDatabase) {
        Cursor query = supportSQLiteDatabase.query("SELECT 1 FROM sqlite_master WHERE type = 'table' AND name='room_master_table'");
        try {
            boolean z = false;
            if (query.moveToFirst()) {
                if (query.getInt(0) != 0) {
                    z = true;
                }
            }
            return z;
        } finally {
            query.close();
        }
    }

    private static boolean e(SupportSQLiteDatabase supportSQLiteDatabase) {
        Cursor query = supportSQLiteDatabase.query("SELECT count(*) FROM sqlite_master WHERE name != 'android_metadata'");
        try {
            boolean z = false;
            if (query.moveToFirst()) {
                if (query.getInt(0) == 0) {
                    z = true;
                }
            }
            return z;
        } finally {
            query.close();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public static abstract class Delegate {
        public final int version;

        protected abstract void createAllTables(SupportSQLiteDatabase supportSQLiteDatabase);

        protected abstract void dropAllTables(SupportSQLiteDatabase supportSQLiteDatabase);

        protected abstract void onCreate(SupportSQLiteDatabase supportSQLiteDatabase);

        protected abstract void onOpen(SupportSQLiteDatabase supportSQLiteDatabase);

        protected void onPostMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
        }

        protected void onPreMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
        }

        public Delegate(int i) {
            this.version = i;
        }

        @Deprecated
        protected void validateMigration(SupportSQLiteDatabase supportSQLiteDatabase) {
            throw new UnsupportedOperationException("validateMigration is deprecated");
        }

        @NonNull
        protected ValidationResult onValidateSchema(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            validateMigration(supportSQLiteDatabase);
            return new ValidationResult(true, null);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public static class ValidationResult {
        @Nullable
        public final String expectedFoundMsg;
        public final boolean isValid;

        public ValidationResult(boolean z, @Nullable String str) {
            this.isValid = z;
            this.expectedFoundMsg = str;
        }
    }
}
