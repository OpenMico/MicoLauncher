package com.milink.runtime.lock;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
/* loaded from: classes2.dex */
public interface ActiveLockDao {
    @Insert(onConflict = 1)
    void addLock(LockInfo lockInfo);

    @Query("DELETE FROM active_lock")
    void clear();

    @Query("SELECT * FROM active_lock WHERE scope = :lockScope AND identify = :identify AND tag = :tag")
    LockInfo[] getByIdentifyAndTag(String str, String str2, String str3);

    @Query("SELECT * FROM active_lock WHERE scope = :lockScope")
    LockInfo[] getByLockScope(String str);

    @Query("SELECT * FROM active_lock WHERE scope = :lockScope AND name = :lockName")
    LockInfo getLock(String str, String str2);

    @Query("DELETE FROM active_lock WHERE scope = :lockScope AND name = :lockName AND identify = :identify AND tag = :tag")
    int removeByLockName(String str, String str2, String str3, String str4);

    @Query("SELECT count(*) FROM active_lock WHERE scope = :lockScope")
    int size(String str);
}
