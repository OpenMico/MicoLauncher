package com.xiaomi.micolauncher.overlay.client;

/* loaded from: classes3.dex */
public interface LauncherClientCallback {
    void onOverlayStateChanged(boolean z);

    void onOverlayTransitionChanged(float f);

    void onOverlayTransitionEnd(float f);

    void onOverlayTransitionStart(float f);
}
