package com.xiaomi.miot.typedef.handler;

import com.xiaomi.miot.typedef.error.MiotError;

/* loaded from: classes3.dex */
public interface CommonHandler<T> {
    void onFailed(MiotError miotError);

    void onSucceed(T t);
}
