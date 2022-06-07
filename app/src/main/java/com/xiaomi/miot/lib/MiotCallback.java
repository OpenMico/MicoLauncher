package com.xiaomi.miot.lib;

/* loaded from: classes2.dex */
public interface MiotCallback<RESULT> {
    void onFailure(int i, String str);

    void onSuccess(RESULT result);
}
