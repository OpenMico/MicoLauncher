package com.xiaomi.smarthome.core.server.internal;

import com.xiaomi.smarthome.core.entity.Error;

/* loaded from: classes4.dex */
public interface NetCallback<R, E extends Error> {
    void onCache(R r);

    void onFailure(E e);

    void onSuccess(R r);
}
