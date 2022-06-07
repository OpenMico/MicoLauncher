package org.fourthline.cling.support.igd.callback;

import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.support.model.PortMapping;

/* loaded from: classes5.dex */
public abstract class PortMappingAdd extends ActionCallback {
    protected final PortMapping portMapping;

    public PortMappingAdd(Service service, PortMapping portMapping) {
        this(service, null, portMapping);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public PortMappingAdd(Service service, ControlPoint controlPoint, PortMapping portMapping) {
        super(new ActionInvocation(service.getAction("AddPortMapping")), controlPoint);
        this.portMapping = portMapping;
        getActionInvocation().setInput("NewExternalPort", portMapping.getExternalPort());
        getActionInvocation().setInput("NewProtocol", portMapping.getProtocol());
        getActionInvocation().setInput("NewInternalClient", portMapping.getInternalClient());
        getActionInvocation().setInput("NewInternalPort", portMapping.getInternalPort());
        getActionInvocation().setInput("NewLeaseDuration", portMapping.getLeaseDurationSeconds());
        getActionInvocation().setInput("NewEnabled", Boolean.valueOf(portMapping.isEnabled()));
        if (portMapping.hasRemoteHost()) {
            getActionInvocation().setInput("NewRemoteHost", portMapping.getRemoteHost());
        }
        if (portMapping.hasDescription()) {
            getActionInvocation().setInput("NewPortMappingDescription", portMapping.getDescription());
        }
    }
}
