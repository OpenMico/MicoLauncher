package com.xiaomi.miot.typedef.device.operable;

import com.xiaomi.miot.typedef.device.ActionInfo;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.property.Property;

/* loaded from: classes3.dex */
public interface Operable {
    MiotError onAction(ActionInfo actionInfo);

    MiotError onGet(Property property);

    MiotError onSet(Property property);
}
