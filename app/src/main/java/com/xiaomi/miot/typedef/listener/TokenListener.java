package com.xiaomi.miot.typedef.listener;

import com.xiaomi.miot.typedef.error.MiotError;

/* loaded from: classes3.dex */
public interface TokenListener {
    void onFailed(MiotError miotError);

    void onSucceed(String str);
}
