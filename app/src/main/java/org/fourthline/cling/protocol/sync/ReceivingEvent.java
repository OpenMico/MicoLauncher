package org.fourthline.cling.protocol.sync;

import java.util.logging.Logger;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.model.UnsupportedDataException;
import org.fourthline.cling.model.gena.RemoteGENASubscription;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.gena.IncomingEventRequestMessage;
import org.fourthline.cling.model.message.gena.OutgoingEventResponseMessage;
import org.fourthline.cling.model.resource.ServiceEventCallbackResource;
import org.fourthline.cling.protocol.ReceivingSync;
import org.fourthline.cling.transport.RouterException;

/* loaded from: classes5.dex */
public class ReceivingEvent extends ReceivingSync<StreamRequestMessage, OutgoingEventResponseMessage> {
    private static final Logger log = Logger.getLogger(ReceivingEvent.class.getName());

    public ReceivingEvent(UpnpService upnpService, StreamRequestMessage streamRequestMessage) {
        super(upnpService, streamRequestMessage);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.fourthline.cling.protocol.ReceivingSync
    public OutgoingEventResponseMessage executeSync() throws RouterException {
        if (!((StreamRequestMessage) getInputMessage()).isContentTypeTextUDA()) {
            Logger logger = log;
            logger.warning("Received without or with invalid Content-Type: " + getInputMessage());
        }
        ServiceEventCallbackResource serviceEventCallbackResource = (ServiceEventCallbackResource) getUpnpService().getRegistry().getResource(ServiceEventCallbackResource.class, ((StreamRequestMessage) getInputMessage()).getUri());
        if (serviceEventCallbackResource == null) {
            Logger logger2 = log;
            logger2.fine("No local resource found: " + getInputMessage());
            return new OutgoingEventResponseMessage(new UpnpResponse(UpnpResponse.Status.NOT_FOUND));
        }
        final IncomingEventRequestMessage incomingEventRequestMessage = new IncomingEventRequestMessage((StreamRequestMessage) getInputMessage(), serviceEventCallbackResource.getModel());
        if (incomingEventRequestMessage.getSubscrptionId() == null) {
            Logger logger3 = log;
            logger3.fine("Subscription ID missing in event request: " + getInputMessage());
            return new OutgoingEventResponseMessage(new UpnpResponse(UpnpResponse.Status.PRECONDITION_FAILED));
        } else if (!incomingEventRequestMessage.hasValidNotificationHeaders()) {
            Logger logger4 = log;
            logger4.fine("Missing NT and/or NTS headers in event request: " + getInputMessage());
            return new OutgoingEventResponseMessage(new UpnpResponse(UpnpResponse.Status.BAD_REQUEST));
        } else if (!incomingEventRequestMessage.hasValidNotificationHeaders()) {
            Logger logger5 = log;
            logger5.fine("Invalid NT and/or NTS headers in event request: " + getInputMessage());
            return new OutgoingEventResponseMessage(new UpnpResponse(UpnpResponse.Status.PRECONDITION_FAILED));
        } else if (incomingEventRequestMessage.getSequence() == null) {
            Logger logger6 = log;
            logger6.fine("Sequence missing in event request: " + getInputMessage());
            return new OutgoingEventResponseMessage(new UpnpResponse(UpnpResponse.Status.PRECONDITION_FAILED));
        } else {
            try {
                getUpnpService().getConfiguration().getGenaEventProcessor().readBody(incomingEventRequestMessage);
                final RemoteGENASubscription waitRemoteSubscription = getUpnpService().getRegistry().getWaitRemoteSubscription(incomingEventRequestMessage.getSubscrptionId());
                if (waitRemoteSubscription == null) {
                    Logger logger7 = log;
                    logger7.severe("Invalid subscription ID, no active subscription: " + incomingEventRequestMessage);
                    return new OutgoingEventResponseMessage(new UpnpResponse(UpnpResponse.Status.PRECONDITION_FAILED));
                }
                getUpnpService().getConfiguration().getRegistryListenerExecutor().execute(new Runnable() { // from class: org.fourthline.cling.protocol.sync.ReceivingEvent.2
                    @Override // java.lang.Runnable
                    public void run() {
                        ReceivingEvent.log.fine("Calling active subscription with event state variable values");
                        waitRemoteSubscription.receive(incomingEventRequestMessage.getSequence(), incomingEventRequestMessage.getStateVariableValues());
                    }
                });
                return new OutgoingEventResponseMessage();
            } catch (UnsupportedDataException e) {
                Logger logger8 = log;
                logger8.fine("Can't read event message request body, " + e);
                final RemoteGENASubscription remoteSubscription = getUpnpService().getRegistry().getRemoteSubscription(incomingEventRequestMessage.getSubscrptionId());
                if (remoteSubscription != null) {
                    getUpnpService().getConfiguration().getRegistryListenerExecutor().execute(new Runnable() { // from class: org.fourthline.cling.protocol.sync.ReceivingEvent.1
                        @Override // java.lang.Runnable
                        public void run() {
                            remoteSubscription.invalidMessage(e);
                        }
                    });
                }
                return new OutgoingEventResponseMessage(new UpnpResponse(UpnpResponse.Status.INTERNAL_SERVER_ERROR));
            }
        }
    }
}
