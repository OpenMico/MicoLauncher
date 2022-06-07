package com.xiaomi.miplay.mylibrary;

/* loaded from: classes4.dex */
public interface MiPlayClientCallback {
    void onDeviceFound(MiDevice miDevice);

    void onDeviceLost(String str);

    void onDeviceUpdate(MiDevice miDevice);

    void onInitError();

    void onInitSuccess();

    void onUnBinderIdm();

    void onVerifySameAccountFail(String str);

    void onVerifySameAccountSuccess(String str);
}
