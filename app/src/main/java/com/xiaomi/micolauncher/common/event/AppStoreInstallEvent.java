package com.xiaomi.micolauncher.common.event;

import com.xiaomi.mico.appstore.bean.AppInfo;

/* loaded from: classes3.dex */
public class AppStoreInstallEvent {
    AppInfo a;
    long b;

    public AppStoreInstallEvent(AppInfo appInfo, long j) {
        this.a = appInfo;
        this.b = j;
    }

    public AppInfo getAppInfo() {
        return this.a;
    }

    public long getDownloadId() {
        return this.b;
    }
}
