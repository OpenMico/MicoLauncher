package com.xiaomi.micolauncher.module.miot.mesh;

import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class MeshPairState {
    private ArrayList<MiotMeshDeviceItem> a;
    public MeshState state;

    /* loaded from: classes3.dex */
    public enum MeshState {
        MESH_IDLE,
        MESH_REQUEST_SCAN,
        MESH_SCAN_IN_OPERATION,
        MESH_SCAN_COMPLETE,
        MESH_REQUEST_BIND,
        MESH_BIND_IN_OPERATION,
        MESH_BIND_COMPLETE,
        MESH_OPEN_MITV
    }

    public MeshPairState(MeshState meshState) {
        this.state = meshState;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ArrayList<MiotMeshDeviceItem> a() {
        return this.a;
    }

    public MeshPairState setExtDevices(ArrayList<MiotMeshDeviceItem> arrayList) {
        this.a = arrayList;
        return this;
    }
}
