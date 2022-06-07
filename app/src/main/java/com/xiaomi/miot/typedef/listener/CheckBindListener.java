package com.xiaomi.miot.typedef.listener;

import com.xiaomi.miot.typedef.error.MiotError;

/* loaded from: classes3.dex */
public interface CheckBindListener {
    void onFailed(MiotError miotError);

    void onSucceed(boolean z);
}
