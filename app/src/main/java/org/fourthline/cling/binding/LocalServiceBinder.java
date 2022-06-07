package org.fourthline.cling.binding;

import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.model.types.ServiceId;
import org.fourthline.cling.model.types.ServiceType;

/* loaded from: classes5.dex */
public interface LocalServiceBinder {
    LocalService read(Class<?> cls) throws LocalServiceBindingException;

    LocalService read(Class<?> cls, ServiceId serviceId, ServiceType serviceType, boolean z, Class[] clsArr) throws LocalServiceBindingException;
}
