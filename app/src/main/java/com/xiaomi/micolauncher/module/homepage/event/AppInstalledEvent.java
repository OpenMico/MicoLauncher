package com.xiaomi.micolauncher.module.homepage.event;

import com.xiaomi.mico.appstore.bean.AppInfo;

/* loaded from: classes3.dex */
public class AppInstalledEvent {
    public AppInfo appInfo;

    public AppInstalledEvent(AppInfo appInfo) {
        this.appInfo = appInfo;
    }
}
