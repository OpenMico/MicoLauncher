package org.fourthline.cling.support.messagebox;

import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.support.messagebox.model.Message;

/* loaded from: classes5.dex */
public abstract class RemoveMessage extends ActionCallback {
    public RemoveMessage(Service service, Message message) {
        this(service, message.getId());
    }

    public RemoveMessage(Service service, int i) {
        super(new ActionInvocation(service.getAction("RemoveMessage")));
        getActionInvocation().setInput("MessageID", Integer.valueOf(i));
    }
}
