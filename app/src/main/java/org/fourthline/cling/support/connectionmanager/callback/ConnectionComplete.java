package org.fourthline.cling.support.connectionmanager.callback;

import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;

/* loaded from: classes5.dex */
public abstract class ConnectionComplete extends ActionCallback {
    public ConnectionComplete(Service service, int i) {
        this(service, null, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ConnectionComplete(Service service, ControlPoint controlPoint, int i) {
        super(new ActionInvocation(service.getAction("ConnectionComplete")), controlPoint);
        getActionInvocation().setInput("ConnectionID", Integer.valueOf(i));
    }
}
