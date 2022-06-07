package org.fourthline.cling.model.meta;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.fourthline.cling.model.ValidationError;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.types.ServiceId;
import org.fourthline.cling.model.types.ServiceType;

/* loaded from: classes5.dex */
public class RemoteService extends Service<RemoteDevice, RemoteService> {
    private final URI controlURI;
    private final URI descriptorURI;
    private final URI eventSubscriptionURI;

    public RemoteService(ServiceType serviceType, ServiceId serviceId, URI uri, URI uri2, URI uri3) throws ValidationException {
        this(serviceType, serviceId, uri, uri2, uri3, null, null);
    }

    public RemoteService(ServiceType serviceType, ServiceId serviceId, URI uri, URI uri2, URI uri3, Action<RemoteService>[] actionArr, StateVariable<RemoteService>[] stateVariableArr) throws ValidationException {
        super(serviceType, serviceId, actionArr, stateVariableArr);
        this.descriptorURI = uri;
        this.controlURI = uri2;
        this.eventSubscriptionURI = uri3;
        List<ValidationError> validateThis = validateThis();
        if (validateThis.size() > 0) {
            throw new ValidationException("Validation of device graph failed, call getErrors() on exception", validateThis);
        }
    }

    @Override // org.fourthline.cling.model.meta.Service
    public Action getQueryStateVariableAction() {
        return new QueryStateVariableAction(this);
    }

    public URI getDescriptorURI() {
        return this.descriptorURI;
    }

    public URI getControlURI() {
        return this.controlURI;
    }

    public URI getEventSubscriptionURI() {
        return this.eventSubscriptionURI;
    }

    public List<ValidationError> validateThis() {
        ArrayList arrayList = new ArrayList();
        if (getDescriptorURI() == null) {
            arrayList.add(new ValidationError(getClass(), "descriptorURI", "Descriptor location (SCPDURL) is required"));
        }
        if (getControlURI() == null) {
            arrayList.add(new ValidationError(getClass(), "controlURI", "Control URL is required"));
        }
        if (getEventSubscriptionURI() == null) {
            arrayList.add(new ValidationError(getClass(), "eventSubscriptionURI", "Event subscription URL is required"));
        }
        return arrayList;
    }

    @Override // org.fourthline.cling.model.meta.Service
    public String toString() {
        return "(" + getClass().getSimpleName() + ") Descriptor: " + getDescriptorURI();
    }
}
