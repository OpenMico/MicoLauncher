package com.xiaomi.micolauncher.skills.cmcc.ims.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
/* loaded from: classes3.dex */
public interface ImsDao {
    @Delete
    int deleteImsCallLog(ImsCallLogEntity imsCallLogEntity);

    @Insert(onConflict = 1)
    void insertImsCallLog(ImsCallLogEntity imsCallLogEntity);

    @Query("SELECT * FROM ims_call_log ORDER BY timestamp ASC LIMIT :limit")
    ImsCallLogEntity[] queryImsCallLogByAsc(int i);

    @Query("SELECT * FROM ims_call_log ORDER BY timestamp DESC LIMIT :limit")
    ImsCallLogEntity[] queryImsCallLogByDesc(int i);

    @Query("SELECT COUNT(*) FROM ims_call_log")
    int queryImsCallLogCount();
}
