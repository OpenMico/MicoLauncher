package com.idb.device;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public class ResourceType {
    public static final String TYPE_P2PSTREAM = "P2pStream";

    public static final ArrayList<String> getResourceModels(String str) {
        if (((str.hashCode() == 1481743342 && str.equals(TYPE_P2PSTREAM)) ? (char) 0 : (char) 65535) != 0) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(EntityModel.MODEL_CAMERA_MIJIA_CAMERA_V3);
        arrayList.add(EntityModel.MODEL_CAMERA_CHUANGMI_CAMERA_IPC009);
        arrayList.add(EntityModel.MODEL_CAMERA_CHUANGMI_CAMERA_IPC019);
        arrayList.add(EntityModel.MODEL_CAMERA_CHUANGMI_CAMERA_IPC022);
        arrayList.add(EntityModel.MODEL_CAMERA_ISA_CAMERA_HLC6);
        return arrayList;
    }
}
