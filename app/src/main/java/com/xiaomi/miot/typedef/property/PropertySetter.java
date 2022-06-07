package com.xiaomi.miot.typedef.property;

import com.xiaomi.miot.typedef.data.DataValue;

/* loaded from: classes3.dex */
public interface PropertySetter<T extends DataValue> {
    void onSet(T t);
}
