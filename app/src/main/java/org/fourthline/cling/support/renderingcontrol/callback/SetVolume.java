package org.fourthline.cling.support.renderingcontrol.callback;

import java.util.logging.Logger;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;
import org.fourthline.cling.model.types.UnsignedIntegerTwoBytes;
import org.fourthline.cling.support.model.Channel;

/* loaded from: classes5.dex */
public abstract class SetVolume extends ActionCallback {
    private static Logger log = Logger.getLogger(SetVolume.class.getName());

    public SetVolume(Service service, long j) {
        this(new UnsignedIntegerFourBytes(0L), service, j);
    }

    public SetVolume(UnsignedIntegerFourBytes unsignedIntegerFourBytes, Service service, long j) {
        super(new ActionInvocation(service.getAction("SetVolume")));
        getActionInvocation().setInput("InstanceID", unsignedIntegerFourBytes);
        getActionInvocation().setInput("Channel", Channel.Master.toString());
        getActionInvocation().setInput("DesiredVolume", new UnsignedIntegerTwoBytes(j));
    }

    @Override // org.fourthline.cling.controlpoint.ActionCallback
    public void success(ActionInvocation actionInvocation) {
        log.fine("Executed successfully");
    }
}
