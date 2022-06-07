package org.fourthline.cling.model.message.control;

import java.net.URL;
import java.util.logging.Logger;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.action.RemoteActionInvocation;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.UpnpRequest;
import org.fourthline.cling.model.message.header.ContentTypeHeader;
import org.fourthline.cling.model.message.header.SoapActionHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.message.header.UserAgentHeader;
import org.fourthline.cling.model.meta.Action;
import org.fourthline.cling.model.meta.QueryStateVariableAction;
import org.fourthline.cling.model.types.SoapActionType;

/* loaded from: classes5.dex */
public class OutgoingActionRequestMessage extends StreamRequestMessage implements ActionRequestMessage {
    private static Logger log = Logger.getLogger(OutgoingActionRequestMessage.class.getName());
    private final String actionNamespace;

    public OutgoingActionRequestMessage(ActionInvocation actionInvocation, URL url) {
        this(actionInvocation.getAction(), new UpnpRequest(UpnpRequest.Method.POST, url));
        if (actionInvocation instanceof RemoteActionInvocation) {
            RemoteActionInvocation remoteActionInvocation = (RemoteActionInvocation) actionInvocation;
            if (remoteActionInvocation.getRemoteClientInfo() != null && remoteActionInvocation.getRemoteClientInfo().getRequestUserAgent() != null) {
                getHeaders().add(UpnpHeader.Type.USER_AGENT, new UserAgentHeader(remoteActionInvocation.getRemoteClientInfo().getRequestUserAgent()));
            }
        } else if (actionInvocation.getClientInfo() != null) {
            getHeaders().putAll(actionInvocation.getClientInfo().getRequestHeaders());
        }
    }

    public OutgoingActionRequestMessage(Action action, UpnpRequest upnpRequest) {
        super(upnpRequest);
        SoapActionHeader soapActionHeader;
        getHeaders().add(UpnpHeader.Type.CONTENT_TYPE, new ContentTypeHeader(ContentTypeHeader.DEFAULT_CONTENT_TYPE_UTF8));
        if (action instanceof QueryStateVariableAction) {
            log.fine("Adding magic control SOAP action header for state variable query action");
            soapActionHeader = new SoapActionHeader(new SoapActionType("schemas-upnp-org", SoapActionType.MAGIC_CONTROL_TYPE, null, action.getName()));
        } else {
            soapActionHeader = new SoapActionHeader(new SoapActionType(action.getService().getServiceType(), action.getName()));
        }
        this.actionNamespace = ((SoapActionType) soapActionHeader.getValue()).getTypeString();
        if (getOperation().getMethod().equals(UpnpRequest.Method.POST)) {
            getHeaders().add(UpnpHeader.Type.SOAPACTION, soapActionHeader);
            Logger logger = log;
            logger.fine("Added SOAP action header: " + soapActionHeader);
            return;
        }
        throw new IllegalArgumentException("Can't send action with request method: " + getOperation().getMethod());
    }

    @Override // org.fourthline.cling.model.message.control.ActionMessage
    public String getActionNamespace() {
        return this.actionNamespace;
    }
}
