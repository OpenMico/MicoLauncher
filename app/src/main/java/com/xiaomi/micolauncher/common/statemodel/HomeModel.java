package com.xiaomi.micolauncher.common.statemodel;

import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class HomeModel {
    private static final HomeModel a = new HomeModel();
    private AtomicBoolean b = new AtomicBoolean(false);

    public static HomeModel getInstance() {
        return a;
    }

    public void setInitialized() {
        this.b.set(true);
    }

    public boolean isInitialized() {
        return this.b.get();
    }
}
