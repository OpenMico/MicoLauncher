package org.fourthline.cling.support.igd.callback;

import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;

/* loaded from: classes5.dex */
public abstract class GetExternalIP extends ActionCallback {
    protected abstract void success(String str);

    public GetExternalIP(Service service) {
        super(new ActionInvocation(service.getAction("GetExternalIPAddress")));
    }

    @Override // org.fourthline.cling.controlpoint.ActionCallback
    public void success(ActionInvocation actionInvocation) {
        success((String) actionInvocation.getOutput("NewExternalIPAddress").getValue());
    }
}
