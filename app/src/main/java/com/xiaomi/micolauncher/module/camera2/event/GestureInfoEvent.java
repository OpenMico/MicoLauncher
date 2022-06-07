package com.xiaomi.micolauncher.module.camera2.event;

/* loaded from: classes3.dex */
public class GestureInfoEvent {
    public GestureType type;

    /* loaded from: classes3.dex */
    public enum GestureType {
        OK,
        FastBackward,
        FastForward,
        Stop,
        ThumbsUp,
        Invalid
    }

    public GestureInfoEvent(GestureType gestureType) {
        this.type = gestureType;
    }
}
