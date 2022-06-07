package org.fourthline.cling.support.contentdirectory.callback;

import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionException;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.ErrorCode;

/* loaded from: classes5.dex */
public abstract class GetSystemUpdateID extends ActionCallback {
    public abstract void received(ActionInvocation actionInvocation, long j);

    public GetSystemUpdateID(Service service) {
        super(new ActionInvocation(service.getAction("GetSystemUpdateID")));
    }

    @Override // org.fourthline.cling.controlpoint.ActionCallback
    public void success(ActionInvocation actionInvocation) {
        boolean z;
        long j;
        try {
            j = Long.valueOf(actionInvocation.getOutput("Id").getValue().toString()).longValue();
            z = true;
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.ACTION_FAILED;
            actionInvocation.setFailure(new ActionException(errorCode, "Can't parse GetSystemUpdateID response: " + e, e));
            failure(actionInvocation, null);
            z = false;
            j = 0;
        }
        if (z) {
            received(actionInvocation, j);
        }
    }
}
