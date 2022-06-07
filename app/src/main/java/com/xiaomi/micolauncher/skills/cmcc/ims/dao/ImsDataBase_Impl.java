package com.xiaomi.micolauncher.skills.cmcc.ims.dao;

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

/* loaded from: classes3.dex */
public final class ImsDataBase_Impl extends ImsDataBase {
    private volatile ImsDao c;

    @Override // androidx.room.RoomDatabase
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration databaseConfiguration) {
        return databaseConfiguration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(databaseConfiguration.context).name(databaseConfiguration.name).callback(new RoomOpenHelper(databaseConfiguration, new RoomOpenHelper.Delegate(2) { // from class: com.xiaomi.micolauncher.skills.cmcc.ims.dao.ImsDataBase_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPostMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `ims_call_log` (`id` INTEGER NOT NULL, `num` TEXT, `name` TEXT, `cmccId` TEXT, `isInComing` INTEGER NOT NULL, `isConnected` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, `callDuration` INTEGER NOT NULL, `type` TEXT, PRIMARY KEY(`id`))");
                supportSQLiteDatabase.execSQL(RoomMasterTable.CREATE_QUERY);
                supportSQLiteDatabase.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '02215ac37652065def9e91f308d2a6e8')");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
                supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `ims_call_log`");
                if (ImsDataBase_Impl.this.mCallbacks != null) {
                    int size = ImsDataBase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) ImsDataBase_Impl.this.mCallbacks.get(i)).onDestructiveMigration(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected void onCreate(SupportSQLiteDatabase supportSQLiteDatabase) {
                if (ImsDataBase_Impl.this.mCallbacks != null) {
                    int size = ImsDataBase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) ImsDataBase_Impl.this.mCallbacks.get(i)).onCreate(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
                ImsDataBase_Impl.this.mDatabase = supportSQLiteDatabase;
                ImsDataBase_Impl.this.internalInitInvalidationTracker(supportSQLiteDatabase);
                if (ImsDataBase_Impl.this.mCallbacks != null) {
                    int size = ImsDataBase_Impl.this.mCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        ((RoomDatabase.Callback) ImsDataBase_Impl.this.mCallbacks.get(i)).onOpen(supportSQLiteDatabase);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPreMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
                DBUtil.dropFtsSyncTriggers(supportSQLiteDatabase);
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase supportSQLiteDatabase) {
                HashMap hashMap = new HashMap(9);
                hashMap.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
                hashMap.put("num", new TableInfo.Column("num", "TEXT", false, 0, null, 1));
                hashMap.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, 1));
                hashMap.put("cmccId", new TableInfo.Column("cmccId", "TEXT", false, 0, null, 1));
                hashMap.put("isInComing", new TableInfo.Column("isInComing", "INTEGER", true, 0, null, 1));
                hashMap.put("isConnected", new TableInfo.Column("isConnected", "INTEGER", true, 0, null, 1));
                hashMap.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, 1));
                hashMap.put("callDuration", new TableInfo.Column("callDuration", "INTEGER", true, 0, null, 1));
                hashMap.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, 1));
                TableInfo tableInfo = new TableInfo("ims_call_log", hashMap, new HashSet(0), new HashSet(0));
                TableInfo read = TableInfo.read(supportSQLiteDatabase, "ims_call_log");
                if (tableInfo.equals(read)) {
                    return new RoomOpenHelper.ValidationResult(true, null);
                }
                return new RoomOpenHelper.ValidationResult(false, "ims_call_log(com.xiaomi.micolauncher.skills.cmcc.ims.dao.ImsCallLogEntity).\n Expected:\n" + tableInfo + "\n Found:\n" + read);
            }
        }, "02215ac37652065def9e91f308d2a6e8", "35ed3681d81a6a1a7b9d37d3d979f0db")).build());
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "ims_call_log");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            writableDatabase.execSQL("DELETE FROM `ims_call_log`");
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
        hashMap.put(ImsDao.class, ImsDao_Impl.getRequiredConverters());
        return hashMap;
    }

    @Override // com.xiaomi.micolauncher.skills.cmcc.ims.dao.ImsDataBase
    public ImsDao imsDao() {
        ImsDao imsDao;
        if (this.c != null) {
            return this.c;
        }
        synchronized (this) {
            if (this.c == null) {
                this.c = new ImsDao_Impl(this);
            }
            imsDao = this.c;
        }
        return imsDao;
    }
}
