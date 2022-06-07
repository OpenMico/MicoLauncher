package com.xiaomi.micolauncher.module.miot.defined.property;

import com.xiaomi.mico.common.Hardware;
import com.xiaomi.miot.typedef.data.DataValue;
import com.xiaomi.miot.typedef.property.PropertyDefinition;
import com.xiaomi.miot.typedef.property.PropertyOperable;

/* loaded from: classes3.dex */
public abstract class MicoPropertyOperable<T extends DataValue> extends PropertyOperable {
    protected abstract int instanceID();

    protected abstract int siid();

    public MicoPropertyOperable(PropertyDefinition propertyDefinition) {
        super(propertyDefinition);
        if (Hardware.isSupportSpec()) {
            super.setServiceInstanceID(siid());
            super.setDeviceInstanceID(0);
            super.setInstanceID(instanceID());
        }
    }
}
