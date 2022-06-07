package com.milink.runtime.lock;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.milink.base.contract.MiLinkKeys;
import com.umeng.analytics.pro.ai;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class LockRecordDao_Impl implements LockRecordDao {
    private final RoomDatabase a;
    private final EntityInsertionAdapter<LockRecord> b;
    private final SharedSQLiteStatement c;
    private final SharedSQLiteStatement d;

    public LockRecordDao_Impl(RoomDatabase roomDatabase) {
        this.a = roomDatabase;
        this.b = new EntityInsertionAdapter<LockRecord>(roomDatabase) { // from class: com.milink.runtime.lock.LockRecordDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `lock_record` (`id`,`req_time`,`scope`,`name`,`identify`,`tag`) VALUES (nullif(?, 0),?,?,?,?,?)";
            }

            /* renamed from: a */
            public void bind(SupportSQLiteStatement supportSQLiteStatement, LockRecord lockRecord) {
                supportSQLiteStatement.bindLong(1, lockRecord.id);
                supportSQLiteStatement.bindLong(2, lockRecord.requestTime);
                LockInfo lockInfo = lockRecord.lockInfo;
                if (lockInfo != null) {
                    if (lockInfo.lockScope == null) {
                        supportSQLiteStatement.bindNull(3);
                    } else {
                        supportSQLiteStatement.bindString(3, lockInfo.lockScope);
                    }
                    if (lockInfo.lockName == null) {
                        supportSQLiteStatement.bindNull(4);
                    } else {
                        supportSQLiteStatement.bindString(4, lockInfo.lockName);
                    }
                    if (lockInfo.identify == null) {
                        supportSQLiteStatement.bindNull(5);
                    } else {
                        supportSQLiteStatement.bindString(5, lockInfo.identify);
                    }
                    if (lockInfo.tag == null) {
                        supportSQLiteStatement.bindNull(6);
                    } else {
                        supportSQLiteStatement.bindString(6, lockInfo.tag);
                    }
                } else {
                    supportSQLiteStatement.bindNull(3);
                    supportSQLiteStatement.bindNull(4);
                    supportSQLiteStatement.bindNull(5);
                    supportSQLiteStatement.bindNull(6);
                }
            }
        };
        this.c = new SharedSQLiteStatement(roomDatabase) { // from class: com.milink.runtime.lock.LockRecordDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM lock_record WHERE scope = ? AND name = ? AND identify = ? AND tag = ?";
            }
        };
        this.d = new SharedSQLiteStatement(roomDatabase) { // from class: com.milink.runtime.lock.LockRecordDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM lock_record";
            }
        };
    }

    @Override // com.milink.runtime.lock.LockRecordDao
    public void addRecord(LockRecord lockRecord) {
        this.a.assertNotSuspendingTransaction();
        this.a.beginTransaction();
        try {
            this.b.insert((EntityInsertionAdapter<LockRecord>) lockRecord);
            this.a.setTransactionSuccessful();
        } finally {
            this.a.endTransaction();
        }
    }

    @Override // com.milink.runtime.lock.LockRecordDao
    public int delete(String str, String str2, String str3, String str4) {
        this.a.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.c.acquire();
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
        if (str3 == null) {
            acquire.bindNull(3);
        } else {
            acquire.bindString(3, str3);
        }
        if (str4 == null) {
            acquire.bindNull(4);
        } else {
            acquire.bindString(4, str4);
        }
        this.a.beginTransaction();
        try {
            int executeUpdateDelete = acquire.executeUpdateDelete();
            this.a.setTransactionSuccessful();
            return executeUpdateDelete;
        } finally {
            this.a.endTransaction();
            this.c.release(acquire);
        }
    }

    @Override // com.milink.runtime.lock.LockRecordDao
    public void clear() {
        this.a.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.d.acquire();
        this.a.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.a.setTransactionSuccessful();
        } finally {
            this.a.endTransaction();
            this.d.release(acquire);
        }
    }

    @Override // com.milink.runtime.lock.LockRecordDao
    public LockRecord filoGet(String str, String str2) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM lock_record WHERE scope = ? AND name = ? ORDER BY req_time DESC LIMIT 1", 2);
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
        LockRecord lockRecord = null;
        Cursor query = DBUtil.query(this.a, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, ai.W);
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "scope");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "name");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "identify");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, MiLinkKeys.PARAM_TAG);
            if (query.moveToFirst()) {
                long j = query.getLong(columnIndexOrThrow2);
                LockInfo lockInfo = new LockInfo(query.isNull(columnIndexOrThrow3) ? null : query.getString(columnIndexOrThrow3), query.isNull(columnIndexOrThrow4) ? null : query.getString(columnIndexOrThrow4));
                if (query.isNull(columnIndexOrThrow5)) {
                    lockInfo.identify = null;
                } else {
                    lockInfo.identify = query.getString(columnIndexOrThrow5);
                }
                if (query.isNull(columnIndexOrThrow6)) {
                    lockInfo.tag = null;
                } else {
                    lockInfo.tag = query.getString(columnIndexOrThrow6);
                }
                lockRecord = new LockRecord(lockInfo, j);
                lockRecord.id = query.getInt(columnIndexOrThrow);
            }
            return lockRecord;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.milink.runtime.lock.LockRecordDao
    public LockRecord getRecord(String str, String str2, String str3, String str4) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM LOCK_RECORD WHERE scope = ? AND name = ? AND identify = ? AND tag = ?", 4);
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
        if (str3 == null) {
            acquire.bindNull(3);
        } else {
            acquire.bindString(3, str3);
        }
        if (str4 == null) {
            acquire.bindNull(4);
        } else {
            acquire.bindString(4, str4);
        }
        this.a.assertNotSuspendingTransaction();
        LockRecord lockRecord = null;
        Cursor query = DBUtil.query(this.a, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, ai.W);
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "scope");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "name");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "identify");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, MiLinkKeys.PARAM_TAG);
            if (query.moveToFirst()) {
                long j = query.getLong(columnIndexOrThrow2);
                LockInfo lockInfo = new LockInfo(query.isNull(columnIndexOrThrow3) ? null : query.getString(columnIndexOrThrow3), query.isNull(columnIndexOrThrow4) ? null : query.getString(columnIndexOrThrow4));
                if (query.isNull(columnIndexOrThrow5)) {
                    lockInfo.identify = null;
                } else {
                    lockInfo.identify = query.getString(columnIndexOrThrow5);
                }
                if (query.isNull(columnIndexOrThrow6)) {
                    lockInfo.tag = null;
                } else {
                    lockInfo.tag = query.getString(columnIndexOrThrow6);
                }
                lockRecord = new LockRecord(lockInfo, j);
                lockRecord.id = query.getInt(columnIndexOrThrow);
            }
            return lockRecord;
        } finally {
            query.close();
            acquire.release();
        }
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
