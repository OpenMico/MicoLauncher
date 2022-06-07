package org.fourthline.cling.support.igd.callback;

import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionException;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.ErrorCode;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;
import org.fourthline.cling.support.model.Connection;

/* loaded from: classes5.dex */
public abstract class GetStatusInfo extends ActionCallback {
    protected abstract void success(Connection.StatusInfo statusInfo);

    public GetStatusInfo(Service service) {
        super(new ActionInvocation(service.getAction("GetStatusInfo")));
    }

    @Override // org.fourthline.cling.controlpoint.ActionCallback
    public void success(ActionInvocation actionInvocation) {
        try {
            success(new Connection.StatusInfo(Connection.Status.valueOf(actionInvocation.getOutput("NewConnectionStatus").getValue().toString()), (UnsignedIntegerFourBytes) actionInvocation.getOutput("NewUptime").getValue(), Connection.Error.valueOf(actionInvocation.getOutput("NewLastConnectionError").getValue().toString())));
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.ARGUMENT_VALUE_INVALID;
            actionInvocation.setFailure(new ActionException(errorCode, "Invalid status or last error string: " + e, e));
            failure(actionInvocation, null);
        }
    }
}
