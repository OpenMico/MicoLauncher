package com.xiaomi.micolauncher.common.event;

import com.xiaomi.miot.support.ICameraStatus;

/* loaded from: classes3.dex */
public class StartMiotCameraUiEvent {
    public String encryptDid;
    public ICameraStatus iCameraStatus;

    public StartMiotCameraUiEvent(ICameraStatus iCameraStatus, String str) {
        this.encryptDid = str;
        this.iCameraStatus = iCameraStatus;
    }
}
