package com.milink.runtime.lock;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.milink.base.utils.AndroidContextUtil;

@Database(entities = {LockInfo.class, LockRecord.class}, version = 1)
/* loaded from: classes2.dex */
public abstract class LockDatabase extends RoomDatabase {
    private static LockDatabase b;

    public abstract ActiveLockDao getActiveLockDao();

    public abstract LockRecordDao getLockRecordDao();

    public static LockDatabase get(Context context) {
        synchronized (LockDatabase.class) {
            if (b == null) {
                b = (LockDatabase) Room.databaseBuilder(AndroidContextUtil.getSafeContext(context), LockDatabase.class, "mi_lock.db").build();
            }
        }
        return b;
    }
}
