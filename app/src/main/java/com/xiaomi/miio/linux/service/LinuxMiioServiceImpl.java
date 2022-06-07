package com.xiaomi.miio.linux.service;

import com.xiaomi.miio.MiioClient;
import com.xiaomi.miio.linux.ILinuxMiioService;

/* loaded from: classes3.dex */
public class LinuxMiioServiceImpl extends ILinuxMiioService.Stub {
    @Override // com.xiaomi.miio.linux.ILinuxMiioService
    public int Destroy() {
        return 0;
    }

    @Override // com.xiaomi.miio.linux.ILinuxMiioService
    public int Stop() {
        return 0;
    }

    @Override // com.xiaomi.miio.linux.ILinuxMiioService
    public int Initialize() {
        return MiioClient.getInstance().Initialize();
    }

    @Override // com.xiaomi.miio.linux.ILinuxMiioService
    public int Start(String str) {
        return MiioClient.getInstance().Start(str);
    }
}
