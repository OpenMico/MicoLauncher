package com.xiaomi.miot.typedef.device.operable;

import com.xiaomi.miot.typedef.device.ActionInfo;
import com.xiaomi.miot.typedef.device.Service;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.handler.OperationHandler;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.miot.typedef.property.PropertyOperable;
import com.xiaomi.miot.typedef.urn.ServiceType;

/* loaded from: classes3.dex */
public class ServiceOperable extends Service implements OperationHandler {
    public ServiceOperable(ServiceType serviceType) {
        super(serviceType);
    }

    public MiotError onSet(Property property) {
        PropertyOperable propertyOperable = (PropertyOperable) getProperty(property.getDefinition().getType());
        if (propertyOperable != null) {
            return propertyOperable.onSet(property.getCurrentValue());
        }
        return MiotError.IOT_RESOURCE_NOT_EXIST;
    }

    public MiotError onGet(Property property) {
        PropertyOperable propertyOperable = (PropertyOperable) getProperty(property.getDefinition().getType());
        if (propertyOperable != null) {
            return propertyOperable.onGet(property.getPropertyValue());
        }
        return MiotError.IOT_RESOURCE_NOT_EXIST;
    }

    public MiotError onAction(ActionInfo actionInfo) {
        return MiotError.IOT_RESOURCE_NOT_EXIST;
    }
}
