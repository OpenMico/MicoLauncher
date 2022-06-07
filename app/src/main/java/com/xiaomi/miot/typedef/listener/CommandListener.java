package com.xiaomi.miot.typedef.listener;

import com.xiaomi.miot.typedef.error.MiotError;

/* loaded from: classes3.dex */
public interface CommandListener {
    void onFailed(MiotError miotError);

    void onSucceed(String str, String str2);
}
