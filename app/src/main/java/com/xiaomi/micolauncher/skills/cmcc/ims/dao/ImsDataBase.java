package com.xiaomi.micolauncher.skills.cmcc.ims.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {ImsCallLogEntity.class}, exportSchema = false, version = 2)
/* loaded from: classes3.dex */
public abstract class ImsDataBase extends RoomDatabase {
    static final Migration b = new Migration(1, 2) { // from class: com.xiaomi.micolauncher.skills.cmcc.ims.dao.ImsDataBase.1
        @Override // androidx.room.migration.Migration
        public void migrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("ALTER TABLE ims_call_log  ADD COLUMN type TEXT");
        }
    };

    public abstract ImsDao imsDao();
}
