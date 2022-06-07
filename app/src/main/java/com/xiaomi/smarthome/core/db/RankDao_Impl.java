package com.xiaomi.smarthome.core.db;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.xiaomi.smarthome.core.entity.DeviceInfoBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class RankDao_Impl extends RankDao {
    private final RoomDatabase a;
    private final EntityInsertionAdapter<DeviceRankEntity> b;
    private final EntityInsertionAdapter<CategoryRankEntity> c;
    private final SharedSQLiteStatement d;
    private final SharedSQLiteStatement e;

    public RankDao_Impl(RoomDatabase roomDatabase) {
        this.a = roomDatabase;
        this.b = new EntityInsertionAdapter<DeviceRankEntity>(roomDatabase) { // from class: com.xiaomi.smarthome.core.db.RankDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `DeviceRankEntity` (`did`,`orderNo`,`categoryName`,`homeId`,`isShowInFirstPage`) VALUES (?,?,?,?,?)";
            }

            /* renamed from: a */
            public void bind(SupportSQLiteStatement supportSQLiteStatement, DeviceRankEntity deviceRankEntity) {
                if (deviceRankEntity.getDid() == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, deviceRankEntity.getDid());
                }
                supportSQLiteStatement.bindLong(2, deviceRankEntity.getOrderNo());
                if (deviceRankEntity.getCategoryName() == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, deviceRankEntity.getCategoryName());
                }
                if (deviceRankEntity.getHomeId() == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, deviceRankEntity.getHomeId());
                }
                supportSQLiteStatement.bindLong(5, deviceRankEntity.isShowInFirstPage());
            }
        };
        this.c = new EntityInsertionAdapter<CategoryRankEntity>(roomDatabase) { // from class: com.xiaomi.smarthome.core.db.RankDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `CategoryRankEntity` (`categoryName`,`orderNo`,`homeId`,`deviceCount`) VALUES (?,?,?,?)";
            }

            /* renamed from: a */
            public void bind(SupportSQLiteStatement supportSQLiteStatement, CategoryRankEntity categoryRankEntity) {
                if (categoryRankEntity.getCategoryName() == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, categoryRankEntity.getCategoryName());
                }
                supportSQLiteStatement.bindLong(2, categoryRankEntity.getOrderNo());
                if (categoryRankEntity.getHomeId() == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, categoryRankEntity.getHomeId());
                }
                supportSQLiteStatement.bindLong(4, categoryRankEntity.getDeviceCount());
            }
        };
        this.d = new SharedSQLiteStatement(roomDatabase) { // from class: com.xiaomi.smarthome.core.db.RankDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete  from CategoryRankEntity where homeId=?";
            }
        };
        this.e = new SharedSQLiteStatement(roomDatabase) { // from class: com.xiaomi.smarthome.core.db.RankDao_Impl.4
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "delete  from DeviceRankEntity where homeId=? and categoryName=?";
            }
        };
    }

    @Override // com.xiaomi.smarthome.core.db.RankDao
    public void insertDeviceRankRecordsInner(List<DeviceRankEntity> list) {
        this.a.assertNotSuspendingTransaction();
        this.a.beginTransaction();
        try {
            this.b.insert(list);
            this.a.setTransactionSuccessful();
        } finally {
            this.a.endTransaction();
        }
    }

    @Override // com.xiaomi.smarthome.core.db.RankDao
    public void insertCategoryRankRecordsInner(List<CategoryRankEntity> list) {
        this.a.assertNotSuspendingTransaction();
        this.a.beginTransaction();
        try {
            this.c.insert(list);
            this.a.setTransactionSuccessful();
        } finally {
            this.a.endTransaction();
        }
    }

    @Override // com.xiaomi.smarthome.core.db.RankDao
    public void insertDeviceRankRecords(List<DeviceInfoBean> list, String str, String str2) {
        this.a.beginTransaction();
        try {
            super.insertDeviceRankRecords(list, str, str2);
            this.a.setTransactionSuccessful();
        } finally {
            this.a.endTransaction();
        }
    }

    @Override // com.xiaomi.smarthome.core.db.RankDao
    public void insertCategoryRankRecords(String str, List<CategoryRankEntity> list) {
        this.a.beginTransaction();
        try {
            super.insertCategoryRankRecords(str, list);
            this.a.setTransactionSuccessful();
        } finally {
            this.a.endTransaction();
        }
    }

    @Override // com.xiaomi.smarthome.core.db.RankDao
    public void deleteAllCategoryByHomeId(String str) {
        this.a.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.d.acquire();
        if (str == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str);
        }
        this.a.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.a.setTransactionSuccessful();
        } finally {
            this.a.endTransaction();
            this.d.release(acquire);
        }
    }

    @Override // com.xiaomi.smarthome.core.db.RankDao
    public void deleteDeviceRankRecordsByCategory(String str, String str2) {
        this.a.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.e.acquire();
        if (str == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str);
        }
        if (str2 == null) {
            acquire.bindNull(2);
        } else {
            acquire.bindString(2, str2);
        }
        this.a.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.a.setTransactionSuccessful();
        } finally {
            this.a.endTransaction();
            this.e.release(acquire);
        }
    }

    @Override // com.xiaomi.smarthome.core.db.RankDao
    public List<DeviceRankEntity> getDeviceRankRecordsByCategoryIdInner(String str, String str2) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select * from DeviceRankEntity where homeId=? and categoryName=?", 2);
        if (str == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str);
        }
        if (str2 == null) {
            acquire.bindNull(2);
        } else {
            acquire.bindString(2, str2);
        }
        this.a.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.a, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "did");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "orderNo");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "categoryName");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "homeId");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "isShowInFirstPage");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                arrayList.add(new DeviceRankEntity(query.isNull(columnIndexOrThrow) ? null : query.getString(columnIndexOrThrow), query.getInt(columnIndexOrThrow2), query.isNull(columnIndexOrThrow3) ? null : query.getString(columnIndexOrThrow3), query.isNull(columnIndexOrThrow4) ? null : query.getString(columnIndexOrThrow4), query.getInt(columnIndexOrThrow5)));
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaomi.smarthome.core.db.RankDao
    public int getDeviceShowCountByCategoryId(String str, String str2) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select count(*) from DeviceRankEntity where homeId=? and categoryName=? and isShowInFirstPage=1", 2);
        if (str == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str);
        }
        if (str2 == null) {
            acquire.bindNull(2);
        } else {
            acquire.bindString(2, str2);
        }
        this.a.assertNotSuspendingTransaction();
        int i = 0;
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

    @Override // com.xiaomi.smarthome.core.db.RankDao
    public List<CategoryRankEntity> getCategoryRankRecordsByCategoryIdInner(String str) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select * from CategoryRankEntity where homeId=?", 1);
        if (str == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str);
        }
        this.a.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.a, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "categoryName");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "orderNo");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "homeId");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "deviceCount");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                arrayList.add(new CategoryRankEntity(query.isNull(columnIndexOrThrow) ? null : query.getString(columnIndexOrThrow), query.getInt(columnIndexOrThrow2), query.isNull(columnIndexOrThrow3) ? null : query.getString(columnIndexOrThrow3), query.getInt(columnIndexOrThrow4)));
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.xiaomi.smarthome.core.db.RankDao
    public int getFirstPageShowCount(String str, String str2) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("select count(*) from DeviceRankEntity where homeId=? and categoryName=? and isShowInFirstPage=1", 2);
        if (str == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str);
        }
        if (str2 == null) {
            acquire.bindNull(2);
        } else {
            acquire.bindString(2, str2);
        }
        this.a.assertNotSuspendingTransaction();
        int i = 0;
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
