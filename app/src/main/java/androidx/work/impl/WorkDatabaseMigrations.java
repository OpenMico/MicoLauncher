package androidx.work.impl;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.work.impl.utils.IdGenerator;
import androidx.work.impl.utils.PreferenceUtils;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class WorkDatabaseMigrations {
    public static final String INSERT_PREFERENCE = "INSERT OR REPLACE INTO `Preference` (`key`, `long_value`) VALUES (@key, @long_value)";
    @NonNull
    public static Migration MIGRATION_1_2 = new Migration(1, 2) { // from class: androidx.work.impl.WorkDatabaseMigrations.1
        @Override // androidx.room.migration.Migration
        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `SystemIdInfo` (`work_spec_id` TEXT NOT NULL, `system_id` INTEGER NOT NULL, PRIMARY KEY(`work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
            supportSQLiteDatabase.execSQL("INSERT INTO SystemIdInfo(work_spec_id, system_id) SELECT work_spec_id, alarm_id AS system_id FROM alarmInfo");
            supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS alarmInfo");
            supportSQLiteDatabase.execSQL("INSERT OR IGNORE INTO worktag(tag, work_spec_id) SELECT worker_class_name AS tag, id AS work_spec_id FROM workspec");
        }
    };
    @NonNull
    public static Migration MIGRATION_3_4 = new Migration(3, 4) { // from class: androidx.work.impl.WorkDatabaseMigrations.2
        @Override // androidx.room.migration.Migration
        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            if (Build.VERSION.SDK_INT >= 23) {
                supportSQLiteDatabase.execSQL("UPDATE workspec SET schedule_requested_at=0 WHERE state NOT IN (2, 3, 5) AND schedule_requested_at=-1 AND interval_duration<>0");
            }
        }
    };
    @NonNull
    public static Migration MIGRATION_4_5 = new Migration(4, 5) { // from class: androidx.work.impl.WorkDatabaseMigrations.3
        @Override // androidx.room.migration.Migration
        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("ALTER TABLE workspec ADD COLUMN `trigger_content_update_delay` INTEGER NOT NULL DEFAULT -1");
            supportSQLiteDatabase.execSQL("ALTER TABLE workspec ADD COLUMN `trigger_max_content_delay` INTEGER NOT NULL DEFAULT -1");
        }
    };
    @NonNull
    public static Migration MIGRATION_6_7 = new Migration(6, 7) { // from class: androidx.work.impl.WorkDatabaseMigrations.4
        @Override // androidx.room.migration.Migration
        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `WorkProgress` (`work_spec_id` TEXT NOT NULL, `progress` BLOB NOT NULL, PRIMARY KEY(`work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
        }
    };
    @NonNull
    public static Migration MIGRATION_7_8 = new Migration(7, 8) { // from class: androidx.work.impl.WorkDatabaseMigrations.5
        @Override // androidx.room.migration.Migration
        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("CREATE INDEX IF NOT EXISTS `index_WorkSpec_period_start_time` ON `workspec` (`period_start_time`)");
        }
    };
    @NonNull
    public static Migration MIGRATION_8_9 = new Migration(8, 9) { // from class: androidx.work.impl.WorkDatabaseMigrations.6
        @Override // androidx.room.migration.Migration
        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("ALTER TABLE workspec ADD COLUMN `run_in_foreground` INTEGER NOT NULL DEFAULT 0");
        }
    };
    public static final int VERSION_1 = 1;
    public static final int VERSION_10 = 10;
    public static final int VERSION_11 = 11;
    public static final int VERSION_2 = 2;
    public static final int VERSION_3 = 3;
    public static final int VERSION_4 = 4;
    public static final int VERSION_5 = 5;
    public static final int VERSION_6 = 6;
    public static final int VERSION_7 = 7;
    public static final int VERSION_8 = 8;
    public static final int VERSION_9 = 9;

    private WorkDatabaseMigrations() {
    }

    /* loaded from: classes.dex */
    public static class RescheduleMigration extends Migration {
        final Context a;

        public RescheduleMigration(@NonNull Context context, int i, int i2) {
            super(i, i2);
            this.a = context;
        }

        @Override // androidx.room.migration.Migration
        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            if (this.endVersion >= 10) {
                supportSQLiteDatabase.execSQL(WorkDatabaseMigrations.INSERT_PREFERENCE, new Object[]{PreferenceUtils.KEY_RESCHEDULE_NEEDED, 1});
            } else {
                this.a.getSharedPreferences(PreferenceUtils.PREFERENCES_FILE_NAME, 0).edit().putBoolean(PreferenceUtils.KEY_RESCHEDULE_NEEDED, true).apply();
            }
        }
    }

    /* loaded from: classes.dex */
    public static class WorkMigration9To10 extends Migration {
        final Context a;

        public WorkMigration9To10(@NonNull Context context) {
            super(9, 10);
            this.a = context;
        }

        @Override // androidx.room.migration.Migration
        public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `Preference` (`key` TEXT NOT NULL, `long_value` INTEGER, PRIMARY KEY(`key`))");
            PreferenceUtils.migrateLegacyPreferences(this.a, supportSQLiteDatabase);
            IdGenerator.migrateLegacyIdGenerator(this.a, supportSQLiteDatabase);
        }
    }
}
