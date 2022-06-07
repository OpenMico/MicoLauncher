package org.fourthline.cling.model.message.control;

import org.fourthline.cling.model.Constants;
import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.header.ContentTypeHeader;
import org.fourthline.cling.model.message.header.EXTHeader;
import org.fourthline.cling.model.message.header.ServerHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.QueryStateVariableAction;

/* loaded from: classes5.dex */
public class OutgoingActionResponseMessage extends StreamResponseMessage implements ActionResponseMessage {
    private String actionNamespace;

    public OutgoingActionResponseMessage(Action action) {
        this(UpnpResponse.Status.OK, action);
    }

    public OutgoingActionResponseMessage(UpnpResponse.Status status) {
        this(status, null);
    }

    public OutgoingActionResponseMessage(UpnpResponse.Status status, Action action) {
        super(new UpnpResponse(status));
        if (action != null) {
            if (action instanceof QueryStateVariableAction) {
                this.actionNamespace = Constants.NS_UPNP_CONTROL_10;
            } else {
                this.actionNamespace = action.getService().getServiceType().toString();
            }
        }
        addHeaders();
    }

    protected void addHeaders() {
        getHeaders().add(UpnpHeader.Type.CONTENT_TYPE, new ContentTypeHeader(ContentTypeHeader.DEFAULT_CONTENT_TYPE_UTF8));
        getHeaders().add(UpnpHeader.Type.SERVER, new ServerHeader());
        getHeaders().add(UpnpHeader.Type.EXT, new EXTHeader());
    }

    @Override // org.fourthline.cling.model.message.control.ActionMessage
    public String getActionNamespace() {
        return this.actionNamespace;
    }
}
