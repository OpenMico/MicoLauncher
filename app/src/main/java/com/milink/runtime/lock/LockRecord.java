package com.milink.runtime.lock;

import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.umeng.analytics.pro.ai;

@Entity(indices = {@Index(name = "idx_lock_record", unique = true, value = {"scope", "name", "identify", "tag"})}, tableName = "lock_record")
/* loaded from: classes2.dex */
public class LockRecord {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @NonNull
    @Embedded
    public final LockInfo lockInfo;
    @ColumnInfo(name = ai.W)
    public final long requestTime;

    public LockRecord(@NonNull LockInfo lockInfo, long j) {
        this.lockInfo = lockInfo;
        this.requestTime = j;
    }

    @Ignore
    public LockRecord(@NonNull LockInfo lockInfo) {
        this(lockInfo, SystemClock.elapsedRealtime());
    }
}
