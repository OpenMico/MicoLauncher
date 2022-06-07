package com.jeremyliao.liveeventbus.core;

/* loaded from: classes2.dex */
public class ObservableConfig {
    Boolean lifecycleObserverAlwaysActive = null;
    Boolean autoClear = null;

    public ObservableConfig lifecycleObserverAlwaysActive(boolean z) {
        this.lifecycleObserverAlwaysActive = Boolean.valueOf(z);
        return this;
    }

    public ObservableConfig autoClear(boolean z) {
        this.autoClear = Boolean.valueOf(z);
        return this;
    }
}
