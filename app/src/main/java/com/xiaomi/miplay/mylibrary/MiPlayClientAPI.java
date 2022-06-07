package com.xiaomi.miplay.mylibrary;

import com.xiaomi.idm.api.proto.IDMServiceProto;

/* loaded from: classes4.dex */
public interface MiPlayClientAPI {
    void VerifySameAccount(IDMServiceProto.IDMService iDMService);

    void init(MiPlayClientCallback miPlayClientCallback, String str);

    boolean isDiscovering();

    boolean isInited();

    boolean isServiceAvailable();

    void startDiscovery(int i);

    void stopDiscovery();

    void unInit(boolean z);
}
