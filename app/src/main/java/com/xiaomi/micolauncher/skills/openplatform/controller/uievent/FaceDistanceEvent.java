package com.xiaomi.micolauncher.skills.openplatform.controller.uievent;

/* loaded from: classes3.dex */
public class FaceDistanceEvent {
    public EnumAction action;

    /* loaded from: classes3.dex */
    public enum EnumAction {
        CLOSE_UI,
        QUIT_ACTIVITY
    }

    public FaceDistanceEvent(EnumAction enumAction) {
        this.action = enumAction;
    }
}
