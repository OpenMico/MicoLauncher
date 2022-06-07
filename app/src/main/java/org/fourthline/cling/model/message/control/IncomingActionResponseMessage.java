package org.fourthline.cling.model.message.control;

import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.model.message.UpnpResponse;

/* loaded from: classes5.dex */
public class IncomingActionResponseMessage extends StreamResponseMessage implements ActionResponseMessage {
    @Override // org.fourthline.cling.model.message.control.ActionMessage
    public String getActionNamespace() {
        return null;
    }

    public IncomingActionResponseMessage(StreamResponseMessage streamResponseMessage) {
        super(streamResponseMessage);
    }

    public IncomingActionResponseMessage(UpnpResponse upnpResponse) {
        super(upnpResponse);
    }

    public boolean isFailedNonRecoverable() {
        int statusCode = getOperation().getStatusCode();
        return getOperation().isFailed() && statusCode != UpnpResponse.Status.METHOD_NOT_SUPPORTED.getStatusCode() && (statusCode != UpnpResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode() || !hasBody());
    }

    public boolean isFailedRecoverable() {
        return hasBody() && getOperation().getStatusCode() == UpnpResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode();
    }
}
