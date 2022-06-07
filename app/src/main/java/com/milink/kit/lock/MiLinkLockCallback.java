package com.milink.kit.lock;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

/* loaded from: classes2.dex */
public interface MiLinkLockCallback {
    @WorkerThread
    default boolean onAcceptUnlock(@NonNull String str, @NonNull String str2, @NonNull String str3) {
        return true;
    }

    @WorkerThread
    default void onBeforeLockRevoke(@NonNull String str, @NonNull String str2) {
    }

    @WorkerThread
    void onLockGranted(@NonNull String str, @NonNull String str2);

    @WorkerThread
    void onLockRevoked(@NonNull String str, @NonNull String str2);

    @WorkerThread
    default void onRequestLockDenied(@NonNull String str, @NonNull String str2) {
    }
}
