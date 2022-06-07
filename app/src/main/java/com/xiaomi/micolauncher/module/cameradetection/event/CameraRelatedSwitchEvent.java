package com.xiaomi.micolauncher.module.cameradetection.event;

/* loaded from: classes3.dex */
public class CameraRelatedSwitchEvent {
    public event status;

    /* loaded from: classes3.dex */
    public enum event {
        OPEN,
        CLOSE,
        CLOSE_WITH_RELEASE_CAMERA,
        DESTROY
    }

    public CameraRelatedSwitchEvent(event eventVar) {
        this.status = eventVar;
    }
}
