package com.milink.runtime.lock;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes2.dex */
public class LockRecordHelper {
    private final LockRecordDao a;

    public LockRecordHelper(Context context) {
        this.a = LockDatabase.get(context).getLockRecordDao();
    }

    public void addLockRecord(LockInfo lockInfo) {
        this.a.addRecord(a(lockInfo));
    }

    @NonNull
    private static LockRecord a(LockInfo lockInfo) {
        return new LockRecord(lockInfo);
    }

    public boolean remove(LockInfo lockInfo) {
        return this.a.delete(lockInfo.lockScope, lockInfo.lockName, lockInfo.identify, lockInfo.tag) != 0;
    }

    @Nullable
    public LockInfo filoPop(String str, String str2) {
        LockRecord filoGet = this.a.filoGet(str, str2);
        if (filoGet != null) {
            return filoGet.lockInfo;
        }
        return null;
    }

    private LockInfo a(@NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull String str4) {
        LockRecord record = this.a.getRecord(str, str2, str3, str4);
        if (record != null) {
            return record.lockInfo;
        }
        return null;
    }

    public boolean hasLockRecord(@NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull String str4) {
        return a(str, str2, str3, str4) != null;
    }
}
