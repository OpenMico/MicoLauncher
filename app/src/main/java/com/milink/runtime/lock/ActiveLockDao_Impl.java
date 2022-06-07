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
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class ActiveLockDao_Impl implements ActiveLockDao {
    private final RoomDatabase a;
    private final EntityInsertionAdapter<LockInfo> b;
    private final SharedSQLiteStatement c;
    private final SharedSQLiteStatement d;

    public ActiveLockDao_Impl(RoomDatabase roomDatabase) {
        this.a = roomDatabase;
        this.b = new EntityInsertionAdapter<LockInfo>(roomDatabase) { // from class: com.milink.runtime.lock.ActiveLockDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `active_lock` (`scope`,`name`,`identify`,`tag`) VALUES (?,?,?,?)";
            }

            /* renamed from: a */
            public void bind(SupportSQLiteStatement supportSQLiteStatement, LockInfo lockInfo) {
                if (lockInfo.lockScope == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, lockInfo.lockScope);
                }
                if (lockInfo.lockName == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, lockInfo.lockName);
                }
                if (lockInfo.identify == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, lockInfo.identify);
                }
                if (lockInfo.tag == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, lockInfo.tag);
                }
            }
        };
        this.c = new SharedSQLiteStatement(roomDatabase) { // from class: com.milink.runtime.lock.ActiveLockDao_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM active_lock WHERE scope = ? AND name = ? AND identify = ? AND tag = ?";
            }
        };
        this.d = new SharedSQLiteStatement(roomDatabase) { // from class: com.milink.runtime.lock.ActiveLockDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM active_lock";
            }
        };
    }

    @Override // com.milink.runtime.lock.ActiveLockDao
    public void addLock(LockInfo lockInfo) {
        this.a.assertNotSuspendingTransaction();
        this.a.beginTransaction();
        try {
            this.b.insert((EntityInsertionAdapter<LockInfo>) lockInfo);
            this.a.setTransactionSuccessful();
        } finally {
            this.a.endTransaction();
        }
    }

    @Override // com.milink.runtime.lock.ActiveLockDao
    public int removeByLockName(String str, String str2, String str3, String str4) {
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

    @Override // com.milink.runtime.lock.ActiveLockDao
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

    @Override // com.milink.runtime.lock.ActiveLockDao
    public LockInfo getLock(String str, String str2) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM active_lock WHERE scope = ? AND name = ?", 2);
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
        LockInfo lockInfo = null;
        Cursor query = DBUtil.query(this.a, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "scope");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "name");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "identify");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, MiLinkKeys.PARAM_TAG);
            if (query.moveToFirst()) {
                LockInfo lockInfo2 = new LockInfo(query.isNull(columnIndexOrThrow) ? null : query.getString(columnIndexOrThrow), query.isNull(columnIndexOrThrow2) ? null : query.getString(columnIndexOrThrow2));
                if (query.isNull(columnIndexOrThrow3)) {
                    lockInfo2.identify = null;
                } else {
                    lockInfo2.identify = query.getString(columnIndexOrThrow3);
                }
                if (query.isNull(columnIndexOrThrow4)) {
                    lockInfo2.tag = null;
                } else {
                    lockInfo2.tag = query.getString(columnIndexOrThrow4);
                }
                lockInfo = lockInfo2;
            }
            return lockInfo;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.milink.runtime.lock.ActiveLockDao
    public LockInfo[] getByIdentifyAndTag(String str, String str2, String str3) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM active_lock WHERE scope = ? AND identify = ? AND tag = ?", 3);
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
        this.a.assertNotSuspendingTransaction();
        int i = 0;
        Cursor query = DBUtil.query(this.a, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "scope");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "name");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "identify");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, MiLinkKeys.PARAM_TAG);
            LockInfo[] lockInfoArr = new LockInfo[query.getCount()];
            while (query.moveToNext()) {
                LockInfo lockInfo = new LockInfo(query.isNull(columnIndexOrThrow) ? null : query.getString(columnIndexOrThrow), query.isNull(columnIndexOrThrow2) ? null : query.getString(columnIndexOrThrow2));
                if (query.isNull(columnIndexOrThrow3)) {
                    lockInfo.identify = null;
                } else {
                    lockInfo.identify = query.getString(columnIndexOrThrow3);
                }
                if (query.isNull(columnIndexOrThrow4)) {
                    lockInfo.tag = null;
                } else {
                    lockInfo.tag = query.getString(columnIndexOrThrow4);
                }
                lockInfoArr[i] = lockInfo;
                i++;
            }
            return lockInfoArr;
        } finally {
            query.close();
            acquire.release();
        }
    }

    @Override // com.milink.runtime.lock.ActiveLockDao
    public int size(String str) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT count(*) FROM active_lock WHERE scope = ?", 1);
        if (str == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str);
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

    @Override // com.milink.runtime.lock.ActiveLockDao
    public LockInfo[] getByLockScope(String str) {
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM active_lock WHERE scope = ?", 1);
        if (str == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str);
        }
        this.a.assertNotSuspendingTransaction();
        int i = 0;
        Cursor query = DBUtil.query(this.a, acquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "scope");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "name");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "identify");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, MiLinkKeys.PARAM_TAG);
            LockInfo[] lockInfoArr = new LockInfo[query.getCount()];
            while (query.moveToNext()) {
                LockInfo lockInfo = new LockInfo(query.isNull(columnIndexOrThrow) ? null : query.getString(columnIndexOrThrow), query.isNull(columnIndexOrThrow2) ? null : query.getString(columnIndexOrThrow2));
                if (query.isNull(columnIndexOrThrow3)) {
                    lockInfo.identify = null;
                } else {
                    lockInfo.identify = query.getString(columnIndexOrThrow3);
                }
                if (query.isNull(columnIndexOrThrow4)) {
                    lockInfo.tag = null;
                } else {
                    lockInfo.tag = query.getString(columnIndexOrThrow4);
                }
                lockInfoArr[i] = lockInfo;
                i++;
            }
            return lockInfoArr;
        } finally {
            query.close();
            acquire.release();
        }
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
