package com.milink.runtime.lock;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.milink.base.contract.MiLinkKeys;
import com.umeng.analytics.pro.ai;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class LockDatabase_Impl extends LockDatabase {
    private volatile ActiveLockDao b;
    private volatile LockRecordDao c;

    @Override // androidx.room.RoomDatabase
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration databaseConfiguration) {
        return databaseConfiguration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(databaseConfiguration.context).name(databaseConfiguration.name).callback(new RoomOpenHelper(databaseConfiguration, new RoomOpenHelper.Delegate(1) { // from class: com.milink.runtime.lock.LockDatabase_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPostMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `active_lock` (`scope` TEXT NOT NULL, `name` TEXT NOT NULL, `identify` TEXT, `tag` TEXT, PRIMARY KEY(`scope`, `name`))");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `lock_record` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `req_time` INTEGER NOT NULL, `scope` TEXT NOT NULL, `name` TEXT NOT NULL, `identify` TEXT, `tag` TEXT)");
                supportSQLiteDatabase.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `idx_lock_record` ON `lock_record` (`scope`, `name`, `identify`, `tag`)");
                supportSQLiteDatabase.execSQL(RoomMasterTable.CREATE_QUERY);
                supportSQLiteDatabase.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '2094c05b885be5b85e267bf289f9afa1')");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `active_lock`");
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `lock_record`");
                if (LockDatabase_Impl.this.mCallbacks != null) {
                    int size = LockDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) LockDatabase_Impl.this.mCallbacks.get(i)).onDestructiveMigration(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected void onCreate(SupportSQLiteDatabase supportSQLiteDatabase) {
                if (LockDatabase_Impl.this.mCallbacks != null) {
                    int size = LockDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) LockDatabase_Impl.this.mCallbacks.get(i)).onCreate(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
                LockDatabase_Impl.this.mDatabase = supportSQLiteDatabase;
                LockDatabase_Impl.this.internalInitInvalidationTracker(supportSQLiteDatabase);
                if (LockDatabase_Impl.this.mCallbacks != null) {
                    int size = LockDatabase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) LockDatabase_Impl.this.mCallbacks.get(i)).onOpen(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPreMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
                DBUtil.dropFtsSyncTriggers(supportSQLiteDatabase);
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase supportSQLiteDatabase) {
                HashMap hashMap = new HashMap(4);
                hashMap.put("scope", new TableInfo.Column("scope", "TEXT", true, 1, null, 1));
                hashMap.put("name", new TableInfo.Column("name", "TEXT", true, 2, null, 1));
                hashMap.put("identify", new TableInfo.Column("identify", "TEXT", false, 0, null, 1));
                hashMap.put(MiLinkKeys.PARAM_TAG, new TableInfo.Column(MiLinkKeys.PARAM_TAG, "TEXT", false, 0, null, 1));
                TableInfo tableInfo = new TableInfo("active_lock", hashMap, new HashSet(0), new HashSet(0));
                TableInfo read = TableInfo.read(supportSQLiteDatabase, "active_lock");
                if (!tableInfo.equals(read)) {
                    return new RoomOpenHelper.ValidationResult(false, "active_lock(com.milink.runtime.lock.LockInfo).\n Expected:\n" + tableInfo + "\n Found:\n" + read);
                }
                HashMap hashMap2 = new HashMap(6);
                hashMap2.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
                hashMap2.put(ai.W, new TableInfo.Column(ai.W, "INTEGER", true, 0, null, 1));
                hashMap2.put("scope", new TableInfo.Column("scope", "TEXT", true, 0, null, 1));
                hashMap2.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, 1));
                hashMap2.put("identify", new TableInfo.Column("identify", "TEXT", false, 0, null, 1));
                hashMap2.put(MiLinkKeys.PARAM_TAG, new TableInfo.Column(MiLinkKeys.PARAM_TAG, "TEXT", false, 0, null, 1));
                HashSet hashSet = new HashSet(0);
                HashSet hashSet2 = new HashSet(1);
                hashSet2.add(new TableInfo.Index("idx_lock_record", true, Arrays.asList("scope", "name", "identify", MiLinkKeys.PARAM_TAG)));
                TableInfo tableInfo2 = new TableInfo("lock_record", hashMap2, hashSet, hashSet2);
                TableInfo read2 = TableInfo.read(supportSQLiteDatabase, "lock_record");
                if (tableInfo2.equals(read2)) {
                    return new RoomOpenHelper.ValidationResult(true, null);
                }
                return new RoomOpenHelper.ValidationResult(false, "lock_record(com.milink.runtime.lock.LockRecord).\n Expected:\n" + tableInfo2 + "\n Found:\n" + read2);
            }
        }, "2094c05b885be5b85e267bf289f9afa1", "5e3a93dc1fc472b572fb2b54d2b5b566")).build());
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "active_lock", "lock_record");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            writableDatabase.execSQL("DELETE FROM `active_lock`");
            writableDatabase.execSQL("DELETE FROM `lock_record`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!writableDatabase.inTransaction()) {
                writableDatabase.execSQL("VACUUM");
            }
        }
    }

    @Override // androidx.room.RoomDatabase
    protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
        HashMap hashMap = new HashMap();
        hashMap.put(ActiveLockDao.class, ActiveLockDao_Impl.getRequiredConverters());
        hashMap.put(LockRecordDao.class, LockRecordDao_Impl.getRequiredConverters());
        return hashMap;
    }

    @Override // com.milink.runtime.lock.LockDatabase
    public ActiveLockDao getActiveLockDao() {
        ActiveLockDao activeLockDao;
        if (this.b != null) {
            return this.b;
        }
        synchronized (this) {
            if (this.b == null) {
                this.b = new ActiveLockDao_Impl(this);
            }
            activeLockDao = this.b;
        }
        return activeLockDao;
    }

    @Override // com.milink.runtime.lock.LockDatabase
    public LockRecordDao getLockRecordDao() {
        LockRecordDao lockRecordDao;
        if (this.c != null) {
            return this.c;
        }
        synchronized (this) {
            if (this.c == null) {
                this.c = new LockRecordDao_Impl(this);
            }
            lockRecordDao = this.c;
        }
        return lockRecordDao;
    }
}
