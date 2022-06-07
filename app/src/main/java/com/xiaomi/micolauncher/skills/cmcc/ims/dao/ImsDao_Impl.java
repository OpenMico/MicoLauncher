package com.xiaomi.micolauncher.skills.cmcc.ims.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class ImsDao_Impl implements ImsDao {
    private final RoomDatabase a;
    private final EntityInsertionAdapter<ImsCallLogEntity> b;
    private final EntityDeletionOrUpdateAdapter<ImsCallLogEntity> c;

    public ImsDao_Impl(RoomDatabase roomDatabase) {
        this.a = roomDatabase;
        this.b = new EntityInsertionAdapter<ImsCallLogEntity>(roomDatabase) { // from class: com.xiaomi.micolauncher.skills.cmcc.ims.dao.ImsDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `ims_call_log` (`id`,`num`,`name`,`cmccId`,`isInComing`,`isConnected`,`timestamp`,`callDuration`,`type`) VALUES (?,?,?,?,?,?,?,?,?)";
            }

            /* renamed from: a */
            public void bind(SupportSQLiteStatement supportSQLiteStatement, ImsCallLogEntity imsCallLogEntity) {
                supportSQLiteStatement.bindLong(1, imsCallLogEntity.id);
                if (imsCallLogEntity.num == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, imsCallLogEntity.num);
                }
                if (imsCallLogEntity.name == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, imsCallLogEntity.name);
                }
                if (imsCallLogEntity.cmccId == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, imsCallLogEntity.cmccId);
                }
                supportSQLiteStatement.bindLong(5, imsCallLogEntity.isInComing ? 1L : 0L);
                supportSQLiteStatement.bindLong(6, imsCallLogEntity.isConnected ? 1L : 0L);
                supportSQLiteStatement.bindLong(7, imsCallLogEntity.timestamp);
                supportSQLiteStatement.bindLong(8, imsCallLogEntity.callDuration);
                if (imsCallLogEntity.type == null) {
                    supportSQLiteStatement.bindNull(9);
                } else {
                    supportSQLiteStatement.bindString(9, imsCallLogEntity.type);
                }
            }
        };
        this.c = new EntityDeletionOrUpdateAdapter<ImsCallLogEntity>(roomDatabase) { // from class: com.xiaomi.micolauncher.skills.cmcc.ims.dao.ImsDao_Impl.2
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM `ims_call_log` WHERE `id` = ?";
            }

            /* renamed from: a */
            public void bind(SupportSQLiteStatement supportSQLiteStatement, ImsCallLogEntity imsCallLogEntity) {
                supportSQLiteStatement.bindLong(1, imsCallLogEntity.id);
            }
        };
    }

    @Override // com.xiaomi.micolauncher.skills.cmcc.ims.dao.ImsDao
    public void insertImsCallLog(ImsCallLogEntity imsCallLogEntity) {
        this.a.assertNotSuspendingTransaction();
        this.a.beginTransaction();
        try {
            this.b.insert((EntityInsertionAdapter<ImsCallLogEntity>) imsCallLogEntity);
            this.a.setTransactionSuccessful();
        } finally {
            this.a.endTransaction();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.cmcc.ims.dao.ImsDao
    public int deleteImsCallLog(ImsCallLogEntity imsCallLogEntity) {
        this.a.assertNotSuspendingTransaction();
        this.a.beginTransaction();
        try {
            int handle = this.c.handle(imsCallLogEntity) + 0;
            this.a.setTransactionSuccessful();
            return handle;
        } finally {
            this.a.endTransaction();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.cmcc.ims.dao.ImsDao
    public ImsCallLogEntity[] queryImsCallLogByDesc(int i) {
        boolean z;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM ims_call_log ORDER BY timestamp DESC LIMIT ?", 1);
        acquire.bindLong(1, i);
        this.a.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.a, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "num");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "name");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "cmccId");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "isInComing");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "isConnected");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "timestamp");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "callDuration");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "type");
            ImsCallLogEntity[] imsCallLogEntityArr = new ImsCallLogEntity[query.getCount()];
            int i2 = 0;
            while (query.moveToNext()) {
                ImsCallLogEntity imsCallLogEntity = new ImsCallLogEntity();
                imsCallLogEntity.id = query.getLong(columnIndexOrThrow);
                if (query.isNull(columnIndexOrThrow2)) {
                    imsCallLogEntity.num = null;
                } else {
                    imsCallLogEntity.num = query.getString(columnIndexOrThrow2);
                }
                if (query.isNull(columnIndexOrThrow3)) {
                    imsCallLogEntity.name = null;
                } else {
                    imsCallLogEntity.name = query.getString(columnIndexOrThrow3);
                }
                if (query.isNull(columnIndexOrThrow4)) {
                    imsCallLogEntity.cmccId = null;
                } else {
                    imsCallLogEntity.cmccId = query.getString(columnIndexOrThrow4);
                }
                imsCallLogEntity.isInComing = query.getInt(columnIndexOrThrow5) != 0;
                imsCallLogEntity.isConnected = query.getInt(columnIndexOrThrow6) != 0;
                imsCallLogEntity.timestamp = query.getLong(columnIndexOrThrow7);
                imsCallLogEntity.callDuration = query.getLong(columnIndexOrThrow8);
                if (query.isNull(columnIndexOrThrow9)) {
                    z = false;
                    imsCallLogEntity.type = null;
                } else {
                    z = false;
                    imsCallLogEntity.type = query.getString(columnIndexOrThrow9);
                }
                imsCallLogEntityArr[i2] = imsCallLogEntity;
                i2++;
            }
            return imsCallLogEntityArr;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.cmcc.ims.dao.ImsDao
    public ImsCallLogEntity[] queryImsCallLogByAsc(int i) {
        boolean z;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM ims_call_log ORDER BY timestamp ASC LIMIT ?", 1);
        acquire.bindLong(1, i);
        this.a.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.a, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "num");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "name");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "cmccId");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "isInComing");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, "isConnected");
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "timestamp");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "callDuration");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "type");
            ImsCallLogEntity[] imsCallLogEntityArr = new ImsCallLogEntity[query.getCount()];
            int i2 = 0;
            while (query.moveToNext()) {
                ImsCallLogEntity imsCallLogEntity = new ImsCallLogEntity();
                imsCallLogEntity.id = query.getLong(columnIndexOrThrow);
                if (query.isNull(columnIndexOrThrow2)) {
                    imsCallLogEntity.num = null;
                } else {
                    imsCallLogEntity.num = query.getString(columnIndexOrThrow2);
                }
                if (query.isNull(columnIndexOrThrow3)) {
                    imsCallLogEntity.name = null;
                } else {
                    imsCallLogEntity.name = query.getString(columnIndexOrThrow3);
                }
                if (query.isNull(columnIndexOrThrow4)) {
                    imsCallLogEntity.cmccId = null;
                } else {
                    imsCallLogEntity.cmccId = query.getString(columnIndexOrThrow4);
                }
                imsCallLogEntity.isInComing = query.getInt(columnIndexOrThrow5) != 0;
                imsCallLogEntity.isConnected = query.getInt(columnIndexOrThrow6) != 0;
                imsCallLogEntity.timestamp = query.getLong(columnIndexOrThrow7);
                imsCallLogEntity.callDuration = query.getLong(columnIndexOrThrow8);
                if (query.isNull(columnIndexOrThrow9)) {
                    z = false;
                    imsCallLogEntity.type = null;
                } else {
                    z = false;
                    imsCallLogEntity.type = query.getString(columnIndexOrThrow9);
                }
                imsCallLogEntityArr[i2] = imsCallLogEntity;
                i2++;
            }
            return imsCallLogEntityArr;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.cmcc.ims.dao.ImsDao
    public int queryImsCallLogCount() {
        int i = 0;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT COUNT(*) FROM ims_call_log", 0);
        this.a.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.a, acquire, false, null);
        try {
            if (query.moveToFirst()) {
                i = query.getInt(0);
            }
            return i;
        } finally {
            query.close();
            acquire.release();
        }
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
