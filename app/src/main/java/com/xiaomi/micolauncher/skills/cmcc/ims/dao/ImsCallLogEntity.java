package com.xiaomi.micolauncher.skills.cmcc.ims.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ims_call_log")
/* loaded from: classes3.dex */
public class ImsCallLogEntity {
    public long callDuration;
    public String cmccId;
    @PrimaryKey
    public long id;
    public boolean isConnected;
    public boolean isInComing;
    public String name;
    public String num;
    public long timestamp;
    public String type;
}
