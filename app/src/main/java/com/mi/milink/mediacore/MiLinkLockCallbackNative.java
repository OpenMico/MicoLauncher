package com.mi.milink.mediacore;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

@Keep
/* loaded from: classes2.dex */
class MiLinkLockCallbackNative {
    public static final int EVENT_BEFORE_LOCK_REVOKE = 3;
    public static final int EVENT_LOCK_GRANTED = 2;
    public static final int EVENT_LOCK_REVOKED = 4;
    public static final int EVENT_REQUEST_LOCK_DENIED = 1;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static native boolean onAcceptUnlock(@NonNull String str, @NonNull String str2, @NonNull String str3);

    /* JADX INFO: Access modifiers changed from: package-private */
    public static native void onLockEvent(int i, @NonNull String str, @NonNull String str2);

    static {
        System.loadLibrary("mirror_sink_server");
    }

    private MiLinkLockCallbackNative() {
    }
}
