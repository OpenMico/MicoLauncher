package com.xiaomi.miot.typedef.device.operable;

import com.xiaomi.miot.typedef.device.ActionInfo;
import com.xiaomi.miot.typedef.device.Device;
import com.xiaomi.miot.typedef.device.Service;
import com.xiaomi.miot.typedef.error.MiotError;
import com.xiaomi.miot.typedef.handler.OperationHandler;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.miot.typedef.urn.DeviceType;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class DeviceOperable extends Device implements OperationHandler {
    public DeviceOperable(DeviceType deviceType) {
        super(deviceType);
    }

    public List<Property> getChangedProperties() {
        ArrayList arrayList = new ArrayList();
        for (Service service : getServices()) {
            for (Property property : service.getProperties()) {
                if (property.getDefinition().isNotifiable() && property.isChanged()) {
                    arrayList.add(property);
                    property.cleanState();
                }
            }
        }
        return arrayList;
    }

    @Override // com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onGet(Property property) {
        ServiceOperable serviceOperable = (ServiceOperable) getService(property.getServiceInstanceID());
        if (serviceOperable != null) {
            return serviceOperable.onGet(property);
        }
        return MiotError.IOT_RESOURCE_NOT_EXIST;
    }

    @Override // com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onSet(Property property) {
        ServiceOperable serviceOperable = (ServiceOperable) getService(property.getServiceInstanceID());
        if (serviceOperable != null) {
            return serviceOperable.onSet(property);
        }
        return MiotError.IOT_RESOURCE_NOT_EXIST;
    }

    @Override // com.xiaomi.miot.typedef.handler.OperationHandler
    public MiotError onAction(ActionInfo actionInfo) {
        ServiceOperable serviceOperable = (ServiceOperable) getService(actionInfo.getServiceInstanceID());
        if (serviceOperable != null) {
            return serviceOperable.onAction(actionInfo);
        }
        return MiotError.IOT_RESOURCE_NOT_EXIST;
    }
}
