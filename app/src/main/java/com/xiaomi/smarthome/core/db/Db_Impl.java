package com.xiaomi.smarthome.core.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class Db_Impl extends Db {
    private volatile RankDao b;

    @Override // androidx.room.RoomDatabase
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration databaseConfiguration) {
        return databaseConfiguration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(databaseConfiguration.context).name(databaseConfiguration.name).callback(new RoomOpenHelper(databaseConfiguration, new RoomOpenHelper.Delegate(5) { // from class: com.xiaomi.smarthome.core.db.Db_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPostMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `CategoryRankEntity` (`categoryName` TEXT NOT NULL, `orderNo` INTEGER NOT NULL, `homeId` TEXT NOT NULL, `deviceCount` INTEGER NOT NULL, PRIMARY KEY(`categoryName`, `homeId`))");
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `DeviceRankEntity` (`did` TEXT NOT NULL, `orderNo` INTEGER NOT NULL, `categoryName` TEXT NOT NULL, `homeId` TEXT NOT NULL, `isShowInFirstPage` INTEGER NOT NULL, PRIMARY KEY(`did`))");
                supportSQLiteDatabase.execSQL(RoomMasterTable.CREATE_QUERY);
                supportSQLiteDatabase.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '1b939d8ac192a6938393a340e0526495')");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `CategoryRankEntity`");
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `DeviceRankEntity`");
                if (Db_Impl.this.mCallbacks != null) {
                    int size = Db_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) Db_Impl.this.mCallbacks.get(i)).onDestructiveMigration(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected void onCreate(SupportSQLiteDatabase supportSQLiteDatabase) {
                if (Db_Impl.this.mCallbacks != null) {
                    int size = Db_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) Db_Impl.this.mCallbacks.get(i)).onCreate(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
                Db_Impl.this.mDatabase = supportSQLiteDatabase;
                Db_Impl.this.internalInitInvalidationTracker(supportSQLiteDatabase);
                if (Db_Impl.this.mCallbacks != null) {
                    int size = Db_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) Db_Impl.this.mCallbacks.get(i)).onOpen(supportSQLiteDatabase);
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
                hashMap.put("categoryName", new TableInfo.Column("categoryName", "TEXT", true, 1, null, 1));
                hashMap.put("orderNo", new TableInfo.Column("orderNo", "INTEGER", true, 0, null, 1));
                hashMap.put("homeId", new TableInfo.Column("homeId", "TEXT", true, 2, null, 1));
                hashMap.put("deviceCount", new TableInfo.Column("deviceCount", "INTEGER", true, 0, null, 1));
                TableInfo tableInfo = new TableInfo("CategoryRankEntity", hashMap, new HashSet(0), new HashSet(0));
                TableInfo read = TableInfo.read(supportSQLiteDatabase, "CategoryRankEntity");
                if (!tableInfo.equals(read)) {
                    return new RoomOpenHelper.ValidationResult(false, "CategoryRankEntity(com.xiaomi.smarthome.core.db.CategoryRankEntity).\n Expected:\n" + tableInfo + "\n Found:\n" + read);
                }
                HashMap hashMap2 = new HashMap(5);
                hashMap2.put("did", new TableInfo.Column("did", "TEXT", true, 1, null, 1));
                hashMap2.put("orderNo", new TableInfo.Column("orderNo", "INTEGER", true, 0, null, 1));
                hashMap2.put("categoryName", new TableInfo.Column("categoryName", "TEXT", true, 0, null, 1));
                hashMap2.put("homeId", new TableInfo.Column("homeId", "TEXT", true, 0, null, 1));
                hashMap2.put("isShowInFirstPage", new TableInfo.Column("isShowInFirstPage", "INTEGER", true, 0, null, 1));
                TableInfo tableInfo2 = new TableInfo("DeviceRankEntity", hashMap2, new HashSet(0), new HashSet(0));
                TableInfo read2 = TableInfo.read(supportSQLiteDatabase, "DeviceRankEntity");
                if (tableInfo2.equals(read2)) {
                    return new RoomOpenHelper.ValidationResult(true, null);
                }
                return new RoomOpenHelper.ValidationResult(false, "DeviceRankEntity(com.xiaomi.smarthome.core.db.DeviceRankEntity).\n Expected:\n" + tableInfo2 + "\n Found:\n" + read2);
            }
        }, "1b939d8ac192a6938393a340e0526495", "c15fa2f885b03e368e365e72beb2d8f5")).build());
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "CategoryRankEntity", "DeviceRankEntity");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            writableDatabase.execSQL("DELETE FROM `CategoryRankEntity`");
            writableDatabase.execSQL("DELETE FROM `DeviceRankEntity`");
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
        hashMap.put(RankDao.class, RankDao_Impl.getRequiredConverters());
        return hashMap;
    }

    @Override // com.xiaomi.smarthome.core.db.Db
    public RankDao rankDao() {
        RankDao rankDao;
        if (this.b != null) {
            return this.b;
        }
        synchronized (this) {
            if (this.b == null) {
                this.b = new RankDao_Impl(this);
            }
            rankDao = this.b;
        }
        return rankDao;
    }
}
