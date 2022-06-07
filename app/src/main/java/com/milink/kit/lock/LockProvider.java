package com.milink.kit.lock;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.milink.kit.ManagerName;

@ManagerName("lock_provider")
/* loaded from: classes2.dex */
public interface LockProvider {
    public static final String AUDIO_LOCK_NAME = "urn:mi-lock:local-device:audio";
    public static final String P2P_LOCK_NAME = "urn:mi-lock:local-device:P2P";

    @Nullable
    MiLinkLock getLock(@NonNull String str, @NonNull String str2);

    @NonNull
    MiLinkLock requireLock(@NonNull Context context, @NonNull String str, @NonNull String str2, @NonNull MiLinkLockCallback miLinkLockCallback);
}
