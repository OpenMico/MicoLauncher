package com.xiaomi.micolauncher.module.cameradetection.event;

import com.xiaomi.micolauncher.module.camera2.event.GestureInfoEvent;

/* loaded from: classes3.dex */
public class GestureControlEvent {
    public GestureInfoEvent.GestureType gestureType;

    public GestureControlEvent(GestureInfoEvent.GestureType gestureType) {
        this.gestureType = gestureType;
    }
}
