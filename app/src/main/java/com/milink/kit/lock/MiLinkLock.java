package com.milink.kit.lock;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

@Keep
/* loaded from: classes2.dex */
public interface MiLinkLock {
    @NonNull
    @WorkerThread
    LockHolder getCurrentLockHolder();

    boolean isReleased();

    @WorkerThread
    void release();

    @WorkerThread
    int requestLock(long j);

    @WorkerThread
    int requestUnlock();

    void setWeakLockStatusListener(@Nullable LockStatusListener lockStatusListener);

    @NonNull
    String tag();

    @NonNull
    String uri();

    @WorkerThread
    default int requestLock() {
        return requestLock(0L);
    }

    default void cleanLockStatusListener() {
        setWeakLockStatusListener(null);
    }
}
