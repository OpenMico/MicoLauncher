package com.xiaomi.micolauncher.module.camera2.action;

/* loaded from: classes3.dex */
public class Camera2ControlAction {
    public EnumAction action;

    /* loaded from: classes3.dex */
    public enum EnumAction {
        ACTION_IDLE,
        ACTION_DISTANCE,
        ACTION_ENTER_CHILD_MODE,
        ACTION_DISTANCE_AND_AGE_SIMULTANEOUSLY,
        ACTION_GESTURE,
        ACTION_QUITE_CHILD_MODE,
        ACTION_RELEASE_CAMERA,
        ACTION_CHILD_CONTENT_RECOMMENDATION,
        ACTION_VPM_ASR_START,
        ACTION_DESTROY_PREVIEW_ACTIVITY,
        ACTION_ALL
    }

    public Camera2ControlAction(EnumAction enumAction) {
        this.action = enumAction;
    }
}
