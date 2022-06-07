package com.xiaomi.micolauncher.module.miot.mesh.uievent;

/* loaded from: classes3.dex */
public class MeshScanEvent {
    public MeshScanEnum event;

    /* loaded from: classes3.dex */
    public enum MeshScanEnum {
        FINISH,
        SCANNING,
        SCANNED_NO_DEVICE
    }

    public MeshScanEvent(MeshScanEnum meshScanEnum) {
        this.event = meshScanEnum;
    }
}
