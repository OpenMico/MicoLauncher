package com.xiaomi.miot.typedef.listener;

import com.xiaomi.miot.typedef.error.MiotError;

/* loaded from: classes3.dex */
public interface SessionListener {
    void onFailed(MiotError miotError);

    void onSucceed(String str, String str2, int i);
}
