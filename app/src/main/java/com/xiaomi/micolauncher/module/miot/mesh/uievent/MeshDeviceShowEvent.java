package com.xiaomi.micolauncher.module.miot.mesh.uievent;

/* loaded from: classes3.dex */
public class MeshDeviceShowEvent {
    public MeshDeviceShowEnum event;

    /* loaded from: classes3.dex */
    public enum MeshDeviceShowEnum {
        FINISH,
        FINISH_TO_MAINACTIVITY
    }

    public MeshDeviceShowEvent(MeshDeviceShowEnum meshDeviceShowEnum) {
        this.event = meshDeviceShowEnum;
    }
}
