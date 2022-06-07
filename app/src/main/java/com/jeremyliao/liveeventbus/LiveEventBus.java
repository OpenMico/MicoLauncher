package com.jeremyliao.liveeventbus;

import androidx.annotation.NonNull;
import com.jeremyliao.liveeventbus.core.Config;
import com.jeremyliao.liveeventbus.core.LiveEvent;
import com.jeremyliao.liveeventbus.core.LiveEventBusCore;
import com.jeremyliao.liveeventbus.core.Observable;
import com.jeremyliao.liveeventbus.core.ObservableConfig;

/* loaded from: classes2.dex */
public final class LiveEventBus {
    public static <T> Observable<T> get(@NonNull String str, @NonNull Class<T> cls) {
        return LiveEventBusCore.get().with(str, cls);
    }

    public static Observable<Object> get(@NonNull String str) {
        return get(str, Object.class);
    }

    public static <T extends LiveEvent> Observable<T> get(@NonNull Class<T> cls) {
        return get(cls.getName(), cls);
    }

    public static Config config() {
        return LiveEventBusCore.get().config();
    }

    public static ObservableConfig config(@NonNull String str) {
        return LiveEventBusCore.get().config(str);
    }
}
