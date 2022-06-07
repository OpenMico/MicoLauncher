package com.xiaomi.miot.host.lan.impl.codec;

import com.xiaomi.miot.typedef.error.MiotError;

/* loaded from: classes2.dex */
public interface MiotResponseHandler {
    void onFailed(MiotError miotError);

    void onSucceed(MiotResponse miotResponse);
}
