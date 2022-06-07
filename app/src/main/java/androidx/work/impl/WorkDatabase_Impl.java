package androidx.work.impl;

import android.os.Build;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.work.impl.model.DependencyDao;
import androidx.work.impl.model.DependencyDao_Impl;
import androidx.work.impl.model.PreferenceDao;
import androidx.work.impl.model.PreferenceDao_Impl;
import androidx.work.impl.model.RawWorkInfoDao;
import androidx.work.impl.model.RawWorkInfoDao_Impl;
import androidx.work.impl.model.SystemIdInfoDao;
import androidx.work.impl.model.SystemIdInfoDao_Impl;
import androidx.work.impl.model.WorkNameDao;
import androidx.work.impl.model.WorkNameDao_Impl;
import androidx.work.impl.model.WorkProgressDao;
import androidx.work.impl.model.WorkProgressDao_Impl;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.model.WorkSpecDao_Impl;
import androidx.work.impl.model.WorkTagDao;
import androidx.work.impl.model.WorkTagDao_Impl;
import com.milink.base.contract.MiLinkKeys;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.idm.service.iot.InputMethodService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/* loaded from: classes.dex */
public final class WorkDatabase_Impl extends WorkDatabase {
    private volatile WorkSpecDao b;
    private volatile DependencyDao c;
    private volatile WorkTagDao d;
    private volatile SystemIdInfoDao e;
    private volatile WorkNameDao f;
    private volatile WorkProgressDao g;
    private volatile PreferenceDao h;
    private volatile RawWorkInfoDao i;

    @Override // androidx.room.RoomDatabase
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration databaseConfiguration) {
        return databaseConfiguration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(databaseConfiguration.context).name(databaseConfiguration.name).callback(new RoomOpenHelper(databaseConfiguration, new RoomOpenHelper.Delegate(11) { // from class: androidx.work.impl.WorkDatabase_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPostMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `Dependency` (`work_spec_id` TEXT NOT NULL, `prerequisite_id` TEXT NOT NULL, PRIMARY KEY(`work_spec_id`, `prerequisite_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE , FOREIGN KEY(`prerequisite_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
                supportSQLiteDatabase.execSQL("CREATE INDEX IF NOT EXISTS `index_Dependency_work_spec_id` ON `Dependency` (`work_spec_id`)");
                supportSQLiteDatabase.execSQL("CREATE INDEX IF NOT EXISTS `index_Dependency_prerequisite_id` ON `Dependency` (`prerequisite_id`)");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `WorkSpec` (`id` TEXT NOT NULL, `state` INTEGER NOT NULL, `worker_class_name` TEXT NOT NULL, `input_merger_class_name` TEXT, `input` BLOB NOT NULL, `output` BLOB NOT NULL, `initial_delay` INTEGER NOT NULL, `interval_duration` INTEGER NOT NULL, `flex_duration` INTEGER NOT NULL, `run_attempt_count` INTEGER NOT NULL, `backoff_policy` INTEGER NOT NULL, `backoff_delay_duration` INTEGER NOT NULL, `period_start_time` INTEGER NOT NULL, `minimum_retention_duration` INTEGER NOT NULL, `schedule_requested_at` INTEGER NOT NULL, `run_in_foreground` INTEGER NOT NULL, `required_network_type` INTEGER, `requires_charging` INTEGER NOT NULL, `requires_device_idle` INTEGER NOT NULL, `requires_battery_not_low` INTEGER NOT NULL, `requires_storage_not_low` INTEGER NOT NULL, `trigger_content_update_delay` INTEGER NOT NULL, `trigger_max_content_delay` INTEGER NOT NULL, `content_uri_triggers` BLOB, PRIMARY KEY(`id`))");
                supportSQLiteDatabase.execSQL("CREATE INDEX IF NOT EXISTS `index_WorkSpec_schedule_requested_at` ON `WorkSpec` (`schedule_requested_at`)");
                supportSQLiteDatabase.execSQL("CREATE INDEX IF NOT EXISTS `index_WorkSpec_period_start_time` ON `WorkSpec` (`period_start_time`)");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `WorkTag` (`tag` TEXT NOT NULL, `work_spec_id` TEXT NOT NULL, PRIMARY KEY(`tag`, `work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
                supportSQLiteDatabase.execSQL("CREATE INDEX IF NOT EXISTS `index_WorkTag_work_spec_id` ON `WorkTag` (`work_spec_id`)");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `SystemIdInfo` (`work_spec_id` TEXT NOT NULL, `system_id` INTEGER NOT NULL, PRIMARY KEY(`work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `WorkName` (`name` TEXT NOT NULL, `work_spec_id` TEXT NOT NULL, PRIMARY KEY(`name`, `work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
                supportSQLiteDatabase.execSQL("CREATE INDEX IF NOT EXISTS `index_WorkName_work_spec_id` ON `WorkName` (`work_spec_id`)");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `WorkProgress` (`work_spec_id` TEXT NOT NULL, `progress` BLOB NOT NULL, PRIMARY KEY(`work_spec_id`), FOREIGN KEY(`work_spec_id`) REFERENCES `WorkSpec`(`id`) ON UPDATE CASCADE ON DELETE CASCADE )");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `Preference` (`key` TEXT NOT NULL, `long_value` INTEGER, PRIMARY KEY(`key`))");
                supportSQLiteDatabase.execSQL(RoomMasterTable.CREATE_QUERY);
                supportSQLiteDatabase.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'cf029002fffdcadf079e8d0a1c9a70ac')");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `Dependency`");
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `WorkSpec`");
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `WorkTag`");
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `SystemIdInfo`");
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `WorkName`");
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `WorkProgress`");
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `Preference`");
                if (WorkDatabase_Impl.this.mCallbacks != null) {
                    int size = WorkDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) WorkDatabase_Impl.this.mCallbacks.get(i)).onDestructiveMigration(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected void onCreate(SupportSQLiteDatabase supportSQLiteDatabase) {
                if (WorkDatabase_Impl.this.mCallbacks != null) {
                    int size = WorkDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) WorkDatabase_Impl.this.mCallbacks.get(i)).onCreate(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
                WorkDatabase_Impl.this.mDatabase = supportSQLiteDatabase;
                supportSQLiteDatabase.execSQL("PRAGMA foreign_keys = ON");
                WorkDatabase_Impl.this.internalInitInvalidationTracker(supportSQLiteDatabase);
                if (WorkDatabase_Impl.this.mCallbacks != null) {
                    int size = WorkDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) WorkDatabase_Impl.this.mCallbacks.get(i)).onOpen(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPreMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
                DBUtil.dropFtsSyncTriggers(supportSQLiteDatabase);
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase supportSQLiteDatabase) {
                HashMap hashMap = new HashMap(2);
                hashMap.put("work_spec_id", new TableInfo.Column("work_spec_id", "TEXT", true, 1, null, 1));
                hashMap.put("prerequisite_id", new TableInfo.Column("prerequisite_id", "TEXT", true, 2, null, 1));
                HashSet hashSet = new HashSet(2);
                hashSet.add(new TableInfo.ForeignKey("WorkSpec", "CASCADE", "CASCADE", Arrays.asList("work_spec_id"), Arrays.asList("id")));
                hashSet.add(new TableInfo.ForeignKey("WorkSpec", "CASCADE", "CASCADE", Arrays.asList("prerequisite_id"), Arrays.asList("id")));
                HashSet hashSet2 = new HashSet(2);
                hashSet2.add(new TableInfo.Index("index_Dependency_work_spec_id", false, Arrays.asList("work_spec_id")));
                hashSet2.add(new TableInfo.Index("index_Dependency_prerequisite_id", false, Arrays.asList("prerequisite_id")));
                TableInfo tableInfo = new TableInfo("Dependency", hashMap, hashSet, hashSet2);
                TableInfo read = TableInfo.read(supportSQLiteDatabase, "Dependency");
                if (!tableInfo.equals(read)) {
                    return new RoomOpenHelper.ValidationResult(false, "Dependency(androidx.work.impl.model.Dependency).\n Expected:\n" + tableInfo + "\n Found:\n" + read);
                }
                HashMap hashMap2 = new HashMap(24);
                hashMap2.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, 1));
                hashMap2.put(XiaomiOAuthConstants.EXTRA_STATE_2, new TableInfo.Column(XiaomiOAuthConstants.EXTRA_STATE_2, "INTEGER", true, 0, null, 1));
                hashMap2.put("worker_class_name", new TableInfo.Column("worker_class_name", "TEXT", true, 0, null, 1));
                hashMap2.put("input_merger_class_name", new TableInfo.Column("input_merger_class_name", "TEXT", false, 0, null, 1));
                hashMap2.put(InputMethodService.InputPropertyCommand.INPUT_SERVICE_DESC, new TableInfo.Column(InputMethodService.InputPropertyCommand.INPUT_SERVICE_DESC, "BLOB", true, 0, null, 1));
                hashMap2.put("output", new TableInfo.Column("output", "BLOB", true, 0, null, 1));
                hashMap2.put("initial_delay", new TableInfo.Column("initial_delay", "INTEGER", true, 0, null, 1));
                hashMap2.put("interval_duration", new TableInfo.Column("interval_duration", "INTEGER", true, 0, null, 1));
                hashMap2.put("flex_duration", new TableInfo.Column("flex_duration", "INTEGER", true, 0, null, 1));
                hashMap2.put("run_attempt_count", new TableInfo.Column("run_attempt_count", "INTEGER", true, 0, null, 1));
                hashMap2.put("backoff_policy", new TableInfo.Column("backoff_policy", "INTEGER", true, 0, null, 1));
                hashMap2.put("backoff_delay_duration", new TableInfo.Column("backoff_delay_duration", "INTEGER", true, 0, null, 1));
                hashMap2.put("period_start_time", new TableInfo.Column("period_start_time", "INTEGER", true, 0, null, 1));
                hashMap2.put("minimum_retention_duration", new TableInfo.Column("minimum_retention_duration", "INTEGER", true, 0, null, 1));
                hashMap2.put("schedule_requested_at", new TableInfo.Column("schedule_requested_at", "INTEGER", true, 0, null, 1));
                hashMap2.put("run_in_foreground", new TableInfo.Column("run_in_foreground", "INTEGER", true, 0, null, 1));
                hashMap2.put("required_network_type", new TableInfo.Column("required_network_type", "INTEGER", false, 0, null, 1));
                hashMap2.put("requires_charging", new TableInfo.Column("requires_charging", "INTEGER", true, 0, null, 1));
                hashMap2.put("requires_device_idle", new TableInfo.Column("requires_device_idle", "INTEGER", true, 0, null, 1));
                hashMap2.put("requires_battery_not_low", new TableInfo.Column("requires_battery_not_low", "INTEGER", true, 0, null, 1));
                hashMap2.put("requires_storage_not_low", new TableInfo.Column("requires_storage_not_low", "INTEGER", true, 0, null, 1));
                hashMap2.put("trigger_content_update_delay", new TableInfo.Column("trigger_content_update_delay", "INTEGER", true, 0, null, 1));
                hashMap2.put("trigger_max_content_delay", new TableInfo.Column("trigger_max_content_delay", "INTEGER", true, 0, null, 1));
                hashMap2.put("content_uri_triggers", new TableInfo.Column("content_uri_triggers", "BLOB", false, 0, null, 1));
                HashSet hashSet3 = new HashSet(0);
                HashSet hashSet4 = new HashSet(2);
                hashSet4.add(new TableInfo.Index("index_WorkSpec_schedule_requested_at", false, Arrays.asList("schedule_requested_at")));
                hashSet4.add(new TableInfo.Index("index_WorkSpec_period_start_time", false, Arrays.asList("period_start_time")));
                TableInfo tableInfo2 = new TableInfo("WorkSpec", hashMap2, hashSet3, hashSet4);
                TableInfo read2 = TableInfo.read(supportSQLiteDatabase, "WorkSpec");
                if (!tableInfo2.equals(read2)) {
                    return new RoomOpenHelper.ValidationResult(false, "WorkSpec(androidx.work.impl.model.WorkSpec).\n Expected:\n" + tableInfo2 + "\n Found:\n" + read2);
                }
                HashMap hashMap3 = new HashMap(2);
                hashMap3.put(MiLinkKeys.PARAM_TAG, new TableInfo.Column(MiLinkKeys.PARAM_TAG, "TEXT", true, 1, null, 1));
                hashMap3.put("work_spec_id", new TableInfo.Column("work_spec_id", "TEXT", true, 2, null, 1));
                HashSet hashSet5 = new HashSet(1);
                hashSet5.add(new TableInfo.ForeignKey("WorkSpec", "CASCADE", "CASCADE", Arrays.asList("work_spec_id"), Arrays.asList("id")));
                HashSet hashSet6 = new HashSet(1);
                hashSet6.add(new TableInfo.Index("index_WorkTag_work_spec_id", false, Arrays.asList("work_spec_id")));
                TableInfo tableInfo3 = new TableInfo("WorkTag", hashMap3, hashSet5, hashSet6);
                TableInfo read3 = TableInfo.read(supportSQLiteDatabase, "WorkTag");
                if (!tableInfo3.equals(read3)) {
                    return new RoomOpenHelper.ValidationResult(false, "WorkTag(androidx.work.impl.model.WorkTag).\n Expected:\n" + tableInfo3 + "\n Found:\n" + read3);
                }
                HashMap hashMap4 = new HashMap(2);
                hashMap4.put("work_spec_id", new TableInfo.Column("work_spec_id", "TEXT", true, 1, null, 1));
                hashMap4.put("system_id", new TableInfo.Column("system_id", "INTEGER", true, 0, null, 1));
                HashSet hashSet7 = new HashSet(1);
                hashSet7.add(new TableInfo.ForeignKey("WorkSpec", "CASCADE", "CASCADE", Arrays.asList("work_spec_id"), Arrays.asList("id")));
                TableInfo tableInfo4 = new TableInfo("SystemIdInfo", hashMap4, hashSet7, new HashSet(0));
                TableInfo read4 = TableInfo.read(supportSQLiteDatabase, "SystemIdInfo");
                if (!tableInfo4.equals(read4)) {
                    return new RoomOpenHelper.ValidationResult(false, "SystemIdInfo(androidx.work.impl.model.SystemIdInfo).\n Expected:\n" + tableInfo4 + "\n Found:\n" + read4);
                }
                HashMap hashMap5 = new HashMap(2);
                hashMap5.put("name", new TableInfo.Column("name", "TEXT", true, 1, null, 1));
                hashMap5.put("work_spec_id", new TableInfo.Column("work_spec_id", "TEXT", true, 2, null, 1));
                HashSet hashSet8 = new HashSet(1);
                hashSet8.add(new TableInfo.ForeignKey("WorkSpec", "CASCADE", "CASCADE", Arrays.asList("work_spec_id"), Arrays.asList("id")));
                HashSet hashSet9 = new HashSet(1);
                hashSet9.add(new TableInfo.Index("index_WorkName_work_spec_id", false, Arrays.asList("work_spec_id")));
                TableInfo tableInfo5 = new TableInfo("WorkName", hashMap5, hashSet8, hashSet9);
                TableInfo read5 = TableInfo.read(supportSQLiteDatabase, "WorkName");
                if (!tableInfo5.equals(read5)) {
                    return new RoomOpenHelper.ValidationResult(false, "WorkName(androidx.work.impl.model.WorkName).\n Expected:\n" + tableInfo5 + "\n Found:\n" + read5);
                }
                HashMap hashMap6 = new HashMap(2);
                hashMap6.put("work_spec_id", new TableInfo.Column("work_spec_id", "TEXT", true, 1, null, 1));
                hashMap6.put("progress", new TableInfo.Column("progress", "BLOB", true, 0, null, 1));
                HashSet hashSet10 = new HashSet(1);
                hashSet10.add(new TableInfo.ForeignKey("WorkSpec", "CASCADE", "CASCADE", Arrays.asList("work_spec_id"), Arrays.asList("id")));
                TableInfo tableInfo6 = new TableInfo("WorkProgress", hashMap6, hashSet10, new HashSet(0));
                TableInfo read6 = TableInfo.read(supportSQLiteDatabase, "WorkProgress");
                if (!tableInfo6.equals(read6)) {
                    return new RoomOpenHelper.ValidationResult(false, "WorkProgress(androidx.work.impl.model.WorkProgress).\n Expected:\n" + tableInfo6 + "\n Found:\n" + read6);
                }
                HashMap hashMap7 = new HashMap(2);
                hashMap7.put("key", new TableInfo.Column("key", "TEXT", true, 1, null, 1));
                hashMap7.put("long_value", new TableInfo.Column("long_value", "INTEGER", false, 0, null, 1));
                TableInfo tableInfo7 = new TableInfo("Preference", hashMap7, new HashSet(0), new HashSet(0));
                TableInfo read7 = TableInfo.read(supportSQLiteDatabase, "Preference");
                if (tableInfo7.equals(read7)) {
                    return new RoomOpenHelper.ValidationResult(true, null);
                }
                return new RoomOpenHelper.ValidationResult(false, "Preference(androidx.work.impl.model.Preference).\n Expected:\n" + tableInfo7 + "\n Found:\n" + read7);
            }
        }, "cf029002fffdcadf079e8d0a1c9a70ac", "8aff2efc47fafe870c738f727dfcfc6e")).build());
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "Dependency", "WorkSpec", "WorkTag", "SystemIdInfo", "WorkName", "WorkProgress", "Preference");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        boolean z = Build.VERSION.SDK_INT >= 21;
        if (!z) {
            try {
                writableDatabase.execSQL("PRAGMA foreign_keys = FALSE");
            } finally {
                super.endTransaction();
                if (!z) {
                    writableDatabase.execSQL("PRAGMA foreign_keys = TRUE");
                }
                writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
                if (!writableDatabase.inTransaction()) {
                    writableDatabase.execSQL("VACUUM");
                }
            }
        }
        super.beginTransaction();
        if (z) {
            writableDatabase.execSQL("PRAGMA defer_foreign_keys = TRUE");
        }
        writableDatabase.execSQL("DELETE FROM `Dependency`");
        writableDatabase.execSQL("DELETE FROM `WorkSpec`");
        writableDatabase.execSQL("DELETE FROM `WorkTag`");
        writableDatabase.execSQL("DELETE FROM `SystemIdInfo`");
        writableDatabase.execSQL("DELETE FROM `WorkName`");
        writableDatabase.execSQL("DELETE FROM `WorkProgress`");
        writableDatabase.execSQL("DELETE FROM `Preference`");
        super.setTransactionSuccessful();
    }

    @Override // androidx.work.impl.WorkDatabase
    public WorkSpecDao workSpecDao() {
        WorkSpecDao workSpecDao;
        if (this.b != null) {
            return this.b;
        }
        synchronized (this) {
            if (this.b == null) {
                this.b = new WorkSpecDao_Impl(this);
            }
            workSpecDao = this.b;
        }
        return workSpecDao;
    }

    @Override // androidx.work.impl.WorkDatabase
    public DependencyDao dependencyDao() {
        DependencyDao dependencyDao;
        if (this.c != null) {
            return this.c;
        }
        synchronized (this) {
            if (this.c == null) {
                this.c = new DependencyDao_Impl(this);
            }
            dependencyDao = this.c;
        }
        return dependencyDao;
    }

    @Override // androidx.work.impl.WorkDatabase
    public WorkTagDao workTagDao() {
        WorkTagDao workTagDao;
        if (this.d != null) {
            return this.d;
        }
        synchronized (this) {
            if (this.d == null) {
                this.d = new WorkTagDao_Impl(this);
            }
            workTagDao = this.d;
        }
        return workTagDao;
    }

    @Override // androidx.work.impl.WorkDatabase
    public SystemIdInfoDao systemIdInfoDao() {
        SystemIdInfoDao systemIdInfoDao;
        if (this.e != null) {
            return this.e;
        }
        synchronized (this) {
            if (this.e == null) {
                this.e = new SystemIdInfoDao_Impl(this);
            }
            systemIdInfoDao = this.e;
        }
        return systemIdInfoDao;
    }

    @Override // androidx.work.impl.WorkDatabase
    public WorkNameDao workNameDao() {
        WorkNameDao workNameDao;
        if (this.f != null) {
            return this.f;
        }
        synchronized (this) {
            if (this.f == null) {
                this.f = new WorkNameDao_Impl(this);
            }
            workNameDao = this.f;
        }
        return workNameDao;
    }

    @Override // androidx.work.impl.WorkDatabase
    public WorkProgressDao workProgressDao() {
        WorkProgressDao workProgressDao;
        if (this.g != null) {
            return this.g;
        }
        synchronized (this) {
            if (this.g == null) {
                this.g = new WorkProgressDao_Impl(this);
            }
            workProgressDao = this.g;
        }
        return workProgressDao;
    }

    @Override // androidx.work.impl.WorkDatabase
    public PreferenceDao preferenceDao() {
        PreferenceDao preferenceDao;
        if (this.h != null) {
            return this.h;
        }
        synchronized (this) {
            if (this.h == null) {
                this.h = new PreferenceDao_Impl(this);
            }
            preferenceDao = this.h;
        }
        return preferenceDao;
    }

    @Override // androidx.work.impl.WorkDatabase
    public RawWorkInfoDao rawWorkInfoDao() {
        RawWorkInfoDao rawWorkInfoDao;
        if (this.i != null) {
            return this.i;
        }
        synchronized (this) {
            if (this.i == null) {
                this.i = new RawWorkInfoDao_Impl(this);
            }
            rawWorkInfoDao = this.i;
        }
        return rawWorkInfoDao;
    }
}
