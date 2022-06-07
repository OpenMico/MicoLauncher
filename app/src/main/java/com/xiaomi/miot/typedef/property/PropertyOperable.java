package com.xiaomi.miot.typedef.property;

import com.xiaomi.miot.typedef.data.DataValue;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.exception.MiotException;

/* loaded from: classes3.dex */
public abstract class PropertyOperable<T extends DataValue> extends Property {
    private PropertyGetter<T> getter;
    private PropertySetter<T> setter;

    public PropertyOperable(PropertyDefinition propertyDefinition) {
        super(propertyDefinition);
    }

    public void Setter(PropertySetter<T> propertySetter) throws MiotException {
        if (getDefinition().isSettable()) {
            this.setter = propertySetter;
            return;
        }
        throw new MiotException(MiotError.INTERNAL, "not settable");
    }

    public void Getter(PropertyGetter<T> propertyGetter) throws MiotException {
        if (getDefinition().isGettable()) {
            this.getter = propertyGetter;
            return;
        }
        throw new MiotException(MiotError.INTERNAL, "not gettable");
    }

    public MiotError onSet(T t) {
        if (!getDefinition().isSettable()) {
            return MiotError.IOT_CANNOT_WRITE;
        }
        PropertySetter<T> propertySetter = this.setter;
        if (propertySetter != null) {
            propertySetter.onSet(t);
        } else {
            super.setDataValue(t);
        }
        return MiotError.OK;
    }

    public MiotError onGet(PropertyValue propertyValue) {
        if (!getDefinition().isGettable()) {
            return MiotError.IOT_CANNOT_READ;
        }
        PropertyGetter<T> propertyGetter = this.getter;
        if (propertyGetter != null) {
            propertyValue.update(propertyGetter.onGet());
        } else {
            propertyValue.update(super.getCurrentValue());
        }
        return MiotError.OK;
    }
}
