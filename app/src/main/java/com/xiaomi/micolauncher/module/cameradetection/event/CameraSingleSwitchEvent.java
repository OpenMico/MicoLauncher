package com.xiaomi.micolauncher.module.cameradetection.event;

import com.xiaomi.micolauncher.module.camera2.action.Camera2ControlAction;

/* loaded from: classes3.dex */
public class CameraSingleSwitchEvent {
    public Camera2ControlAction.EnumAction action;
    public boolean open;

    public CameraSingleSwitchEvent(Camera2ControlAction.EnumAction enumAction, boolean z) {
        this.action = enumAction;
        this.open = z;
    }
}
