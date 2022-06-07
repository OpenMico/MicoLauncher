package org.fourthline.cling.support.connectionmanager.callback;

import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.ServiceReference;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.support.model.ConnectionInfo;
import org.fourthline.cling.support.model.ProtocolInfo;

/* loaded from: classes5.dex */
public abstract class PrepareForConnection extends ActionCallback {
    public abstract void received(ActionInvocation actionInvocation, int i, int i2, int i3);

    public PrepareForConnection(Service service, ProtocolInfo protocolInfo, ServiceReference serviceReference, int i, ConnectionInfo.Direction direction) {
        this(service, null, protocolInfo, serviceReference, i, direction);
    }

    public PrepareForConnection(Service service, ControlPoint controlPoint, ProtocolInfo protocolInfo, ServiceReference serviceReference, int i, ConnectionInfo.Direction direction) {
        super(new ActionInvocation(service.getAction("PrepareForConnection")), controlPoint);
        getActionInvocation().setInput("RemoteProtocolInfo", protocolInfo.toString());
        getActionInvocation().setInput("PeerConnectionManager", serviceReference.toString());
        getActionInvocation().setInput("PeerConnectionID", Integer.valueOf(i));
        getActionInvocation().setInput("Direction", direction.toString());
    }

    @Override // org.fourthline.cling.controlpoint.ActionCallback
    public void success(ActionInvocation actionInvocation) {
        received(actionInvocation, ((Integer) actionInvocation.getOutput("ConnectionID").getValue()).intValue(), ((Integer) actionInvocation.getOutput("RcsID").getValue()).intValue(), ((Integer) actionInvocation.getOutput("AVTransportID").getValue()).intValue());
    }
}
