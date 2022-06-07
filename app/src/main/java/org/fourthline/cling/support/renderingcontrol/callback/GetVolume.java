package org.fourthline.cling.support.renderingcontrol.callback;

import java.util.logging.Logger;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionException;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.ErrorCode;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;
import org.fourthline.cling.support.model.Channel;

/* loaded from: classes5.dex */
public abstract class GetVolume extends ActionCallback {
    private static Logger log = Logger.getLogger(GetVolume.class.getName());

    public abstract void received(ActionInvocation actionInvocation, int i);

    public GetVolume(Service service) {
        this(new UnsignedIntegerFourBytes(0L), service);
    }

    public GetVolume(UnsignedIntegerFourBytes unsignedIntegerFourBytes, Service service) {
        super(new ActionInvocation(service.getAction("GetVolume")));
        getActionInvocation().setInput("InstanceID", unsignedIntegerFourBytes);
        getActionInvocation().setInput("Channel", Channel.Master.toString());
    }

    @Override // org.fourthline.cling.controlpoint.ActionCallback
    public void success(ActionInvocation actionInvocation) {
        int i;
        boolean z = false;
        try {
            i = Integer.valueOf(actionInvocation.getOutput("CurrentVolume").getValue().toString()).intValue();
            z = true;
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.ACTION_FAILED;
            actionInvocation.setFailure(new ActionException(errorCode, "Can't parse ProtocolInfo response: " + e, e));
            failure(actionInvocation, null);
            i = 0;
        }
        if (z) {
            received(actionInvocation, i);
        }
    }
}
