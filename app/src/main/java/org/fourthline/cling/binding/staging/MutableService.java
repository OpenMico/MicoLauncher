package org.fourthline.cling.binding.staging;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.meta.StateVariable;
import org.fourthline.cling.model.types.ServiceId;
import org.fourthline.cling.model.types.ServiceType;

/* loaded from: classes5.dex */
public class MutableService {
    public URI controlURI;
    public URI descriptorURI;
    public URI eventSubscriptionURI;
    public ServiceId serviceId;
    public ServiceType serviceType;
    public List<MutableAction> actions = new ArrayList();
    public List<MutableStateVariable> stateVariables = new ArrayList();

    public Service build(Device device) throws ValidationException {
        return device.newInstance(this.serviceType, this.serviceId, this.descriptorURI, this.controlURI, this.eventSubscriptionURI, createActions(), createStateVariables());
    }

    public Action[] createActions() {
        Action[] actionArr = new Action[this.actions.size()];
        int i = 0;
        for (MutableAction mutableAction : this.actions) {
            i++;
            actionArr[i] = mutableAction.build();
        }
        return actionArr;
    }

    public StateVariable[] createStateVariables() {
        StateVariable[] stateVariableArr = new StateVariable[this.stateVariables.size()];
        int i = 0;
        for (MutableStateVariable mutableStateVariable : this.stateVariables) {
            i++;
            stateVariableArr[i] = mutableStateVariable.build();
        }
        return stateVariableArr;
    }
}
