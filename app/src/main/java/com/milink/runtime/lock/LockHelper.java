package com.milink.runtime.lock;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.Objects;

/* loaded from: classes2.dex */
public class LockHelper {
    private final String a;
    private final ActiveLockDao b;

    public LockHelper(Context context, String str) {
        this.a = str;
        this.b = LockDatabase.get(context).getActiveLockDao();
    }

    public LockInfo getByLockName(String str) {
        return this.b.getLock(this.a, str);
    }

    public LockInfo[] getByIdentifyAndTag(@NonNull String str, @NonNull String str2) {
        return this.b.getByIdentifyAndTag(this.a, (String) Objects.requireNonNull(str), (String) Objects.requireNonNull(str2));
    }

    public boolean removeByLockName(String str, String str2, String str3) {
        return this.b.removeByLockName(this.a, str, str2, str3) > 0;
    }

    public int size() {
        return this.b.size(this.a);
    }

    public LockInfo[] getAll() {
        return this.b.getByLockScope(this.a);
    }

    public void addLock(LockInfo lockInfo) {
        this.b.addLock(lockInfo);
    }
}
