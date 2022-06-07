package org.fourthline.cling.support.connectionmanager.callback;

import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.ServiceReference;
import org.fourthline.cling.model.action.ActionException;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.ErrorCode;
import org.fourthline.cling.support.model.ConnectionInfo;
import org.fourthline.cling.support.model.ProtocolInfo;

/* loaded from: classes5.dex */
public abstract class GetCurrentConnectionInfo extends ActionCallback {
    public abstract void received(ActionInvocation actionInvocation, ConnectionInfo connectionInfo);

    public GetCurrentConnectionInfo(Service service, int i) {
        this(service, null, i);
    }

    protected GetCurrentConnectionInfo(Service service, ControlPoint controlPoint, int i) {
        super(new ActionInvocation(service.getAction("GetCurrentConnectionInfo")), controlPoint);
        getActionInvocation().setInput("ConnectionID", Integer.valueOf(i));
    }

    @Override // org.fourthline.cling.controlpoint.ActionCallback
    public void success(ActionInvocation actionInvocation) {
        try {
            received(actionInvocation, new ConnectionInfo(((Integer) actionInvocation.getInput("ConnectionID").getValue()).intValue(), ((Integer) actionInvocation.getOutput("RcsID").getValue()).intValue(), ((Integer) actionInvocation.getOutput("AVTransportID").getValue()).intValue(), new ProtocolInfo(actionInvocation.getOutput("ProtocolInfo").toString()), new ServiceReference(actionInvocation.getOutput("PeerConnectionManager").toString()), ((Integer) actionInvocation.getOutput("PeerConnectionID").getValue()).intValue(), ConnectionInfo.Direction.valueOf(actionInvocation.getOutput("Direction").toString()), ConnectionInfo.Status.valueOf(actionInvocation.getOutput("Status").toString())));
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.ACTION_FAILED;
            actionInvocation.setFailure(new ActionException(errorCode, "Can't parse ConnectionInfo response: " + e, e));
            failure(actionInvocation, null);
        }
    }
}
