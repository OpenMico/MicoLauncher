package com.xiaomi.miplay.mylibrary.external;

import com.xiaomi.miplay.phoneclientsdk.external.IMiPlayExternalClientCallback;

/* loaded from: classes4.dex */
public class MiPlayExternalClientInfo {
    String a;
    public IMiPlayExternalClientCallback callback;

    public MiPlayExternalClientInfo(String str, IMiPlayExternalClientCallback iMiPlayExternalClientCallback) {
        this.a = str;
        this.callback = iMiPlayExternalClientCallback;
    }
}
