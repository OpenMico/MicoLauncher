package org.fourthline.cling.support.connectionmanager.callback;

import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.action.ActionArgumentValue;
import org.fourthline.cling.model.action.ActionException;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.ErrorCode;
import org.fourthline.cling.support.model.ProtocolInfos;

/* loaded from: classes5.dex */
public abstract class GetProtocolInfo extends ActionCallback {
    public abstract void received(ActionInvocation actionInvocation, ProtocolInfos protocolInfos, ProtocolInfos protocolInfos2);

    public GetProtocolInfo(Service service) {
        this(service, null);
    }

    protected GetProtocolInfo(Service service, ControlPoint controlPoint) {
        super(new ActionInvocation(service.getAction("GetProtocolInfo")), controlPoint);
    }

    @Override // org.fourthline.cling.controlpoint.ActionCallback
    public void success(ActionInvocation actionInvocation) {
        ProtocolInfos protocolInfos;
        ProtocolInfos protocolInfos2;
        try {
            ActionArgumentValue output = actionInvocation.getOutput("Sink");
            ActionArgumentValue output2 = actionInvocation.getOutput("Source");
            if (output != null) {
                protocolInfos = new ProtocolInfos(output.toString());
            } else {
                protocolInfos = null;
            }
            if (output2 != null) {
                protocolInfos2 = new ProtocolInfos(output2.toString());
            } else {
                protocolInfos2 = null;
            }
            received(actionInvocation, protocolInfos, protocolInfos2);
        } catch (Exception e) {
            actionInvocation.setFailure(new ActionException(ErrorCode.ACTION_FAILED, "Can't parse ProtocolInfo response: " + e, e));
            failure(actionInvocation, null);
        }
    }
}
