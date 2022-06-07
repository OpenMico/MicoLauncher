package org.fourthline.cling.model.message.header;

import java.net.URI;
import org.fourthline.cling.model.types.ServiceType;

/* loaded from: classes5.dex */
public class ServiceTypeHeader extends UpnpHeader<ServiceType> {
    public ServiceTypeHeader() {
    }

    public ServiceTypeHeader(URI uri) {
        setString(uri.toString());
    }

    public ServiceTypeHeader(ServiceType serviceType) {
        setValue(serviceType);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        try {
            setValue(ServiceType.valueOf(str));
        } catch (RuntimeException e) {
            throw new InvalidHeaderException("Invalid service type header value, " + e.getMessage());
        }
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        return getValue().toString();
    }
}
