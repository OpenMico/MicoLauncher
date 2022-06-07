package org.fourthline.cling.support.renderingcontrol.callback;

import java.util.logging.Logger;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;
import org.fourthline.cling.support.model.Channel;

/* loaded from: classes5.dex */
public abstract class GetMute extends ActionCallback {
    private static Logger log = Logger.getLogger(GetMute.class.getName());

    public abstract void received(ActionInvocation actionInvocation, boolean z);

    public GetMute(Service service) {
        this(new UnsignedIntegerFourBytes(0L), service);
    }

    public GetMute(UnsignedIntegerFourBytes unsignedIntegerFourBytes, Service service) {
        super(new ActionInvocation(service.getAction("GetMute")));
        getActionInvocation().setInput("InstanceID", unsignedIntegerFourBytes);
        getActionInvocation().setInput("Channel", Channel.Master.toString());
    }

    @Override // org.fourthline.cling.controlpoint.ActionCallback
    public void success(ActionInvocation actionInvocation) {
        received(actionInvocation, ((Boolean) actionInvocation.getOutput("CurrentMute").getValue()).booleanValue());
    }
}
