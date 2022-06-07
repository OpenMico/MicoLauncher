package com.milink.runtime.lock;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
/* loaded from: classes2.dex */
public interface LockRecordDao {
    @Insert(onConflict = 1)
    void addRecord(LockRecord lockRecord);

    @Query("DELETE FROM lock_record")
    void clear();

    @Query("DELETE FROM lock_record WHERE scope = :scope AND name = :lockName AND identify = :identify AND tag = :tag")
    int delete(String str, String str2, String str3, String str4);

    @Query("SELECT * FROM lock_record WHERE scope = :scope AND name = :lockName ORDER BY req_time DESC LIMIT 1")
    LockRecord filoGet(String str, String str2);

    @Query("SELECT * FROM LOCK_RECORD WHERE scope = :scope AND name = :lockName AND identify = :identify AND tag = :tag")
    LockRecord getRecord(String str, String str2, String str3, String str4);
}
