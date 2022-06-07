package com.xiaomi.miot.typedef.property;

import com.xiaomi.miot.typedef.data.DataValue;

/* loaded from: classes3.dex */
public interface PropertyGetter<T extends DataValue> {
    T onGet();
}
