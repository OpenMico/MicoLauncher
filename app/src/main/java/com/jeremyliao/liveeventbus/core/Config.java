package com.jeremyliao.liveeventbus.core;

import android.content.Context;
import androidx.annotation.NonNull;
import com.jeremyliao.liveeventbus.logger.Logger;
import com.jeremyliao.liveeventbus.utils.AppUtils;

/* loaded from: classes2.dex */
public class Config {
    public Config lifecycleObserverAlwaysActive(boolean z) {
        LiveEventBusCore.get().setLifecycleObserverAlwaysActive(z);
        return this;
    }

    public Config autoClear(boolean z) {
        LiveEventBusCore.get().setAutoClear(z);
        return this;
    }

    public Config setContext(Context context) {
        AppUtils.init(context);
        LiveEventBusCore.get().registerReceiver();
        return this;
    }

    public Config setLogger(@NonNull Logger logger) {
        LiveEventBusCore.get().setLogger(logger);
        return this;
    }

    public Config enableLogger(boolean z) {
        LiveEventBusCore.get().enableLogger(z);
        return this;
    }
}
