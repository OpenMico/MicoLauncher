package org.fourthline.cling.support.avtransport.callback;

import java.util.logging.Logger;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;
import org.fourthline.cling.support.model.TransportInfo;

/* loaded from: classes5.dex */
public abstract class GetTransportInfo extends ActionCallback {
    private static Logger log = Logger.getLogger(GetTransportInfo.class.getName());

    public abstract void received(ActionInvocation actionInvocation, TransportInfo transportInfo);

    public GetTransportInfo(Service service) {
        this(new UnsignedIntegerFourBytes(0L), service);
    }

    public GetTransportInfo(UnsignedIntegerFourBytes unsignedIntegerFourBytes, Service service) {
        super(new ActionInvocation(service.getAction("GetTransportInfo")));
        getActionInvocation().setInput("InstanceID", unsignedIntegerFourBytes);
    }

    @Override // org.fourthline.cling.controlpoint.ActionCallback
    public void success(ActionInvocation actionInvocation) {
        received(actionInvocation, new TransportInfo(actionInvocation.getOutputMap()));
    }
}
