package com.xiaomi.miot.support;

import java.util.List;

/* loaded from: classes2.dex */
public interface DoorbellPdidsCallback {
    void onFailed(String str);

    void onSuccess(List<Integer> list);
}
