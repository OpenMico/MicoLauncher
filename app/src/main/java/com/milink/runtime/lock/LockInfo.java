package com.milink.runtime.lock;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import com.milink.base.contract.MiLinkKeys;
import java.util.Objects;

@Entity(primaryKeys = {"scope", "name"}, tableName = "active_lock")
/* loaded from: classes2.dex */
public class LockInfo {
    @ColumnInfo(name = "identify")
    public String identify;
    @NonNull
    @ColumnInfo(name = "name")
    public final String lockName;
    @NonNull
    @ColumnInfo(name = "scope")
    public final String lockScope;
    @ColumnInfo(name = MiLinkKeys.PARAM_TAG)
    public String tag;

    public LockInfo(@NonNull String str, @NonNull String str2) {
        this.lockScope = (String) Objects.requireNonNull(str);
        this.lockName = (String) Objects.requireNonNull(str2);
    }
}
