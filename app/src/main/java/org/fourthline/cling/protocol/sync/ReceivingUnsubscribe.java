package org.fourthline.cling.protocol.sync;

import java.util.logging.Logger;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.model.gena.LocalGENASubscription;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.gena.IncomingUnsubscribeRequestMessage;
import org.fourthline.cling.model.resource.ServiceEventSubscriptionResource;
import org.fourthline.cling.protocol.ReceivingSync;
import org.fourthline.cling.transport.RouterException;

/* loaded from: classes5.dex */
public class ReceivingUnsubscribe extends ReceivingSync<StreamRequestMessage, StreamResponseMessage> {
    private static final Logger log = Logger.getLogger(ReceivingUnsubscribe.class.getName());

    public ReceivingUnsubscribe(UpnpService upnpService, StreamRequestMessage streamRequestMessage) {
        super(upnpService, streamRequestMessage);
    }

    @Override // org.fourthline.cling.protocol.ReceivingSync
    protected StreamResponseMessage executeSync() throws RouterException {
        ServiceEventSubscriptionResource serviceEventSubscriptionResource = (ServiceEventSubscriptionResource) getUpnpService().getRegistry().getResource(ServiceEventSubscriptionResource.class, ((StreamRequestMessage) getInputMessage()).getUri());
        if (serviceEventSubscriptionResource == null) {
            Logger logger = log;
            logger.fine("No local resource found: " + getInputMessage());
            return null;
        }
        Logger logger2 = log;
        logger2.fine("Found local event subscription matching relative request URI: " + ((StreamRequestMessage) getInputMessage()).getUri());
        IncomingUnsubscribeRequestMessage incomingUnsubscribeRequestMessage = new IncomingUnsubscribeRequestMessage((StreamRequestMessage) getInputMessage(), serviceEventSubscriptionResource.getModel());
        if (incomingUnsubscribeRequestMessage.getSubscriptionId() == null || (!incomingUnsubscribeRequestMessage.hasNotificationHeader() && !incomingUnsubscribeRequestMessage.hasCallbackHeader())) {
            LocalGENASubscription localSubscription = getUpnpService().getRegistry().getLocalSubscription(incomingUnsubscribeRequestMessage.getSubscriptionId());
            if (localSubscription == null) {
                Logger logger3 = log;
                logger3.fine("Invalid subscription ID for unsubscribe request: " + getInputMessage());
                return new StreamResponseMessage(UpnpResponse.Status.PRECONDITION_FAILED);
            }
            Logger logger4 = log;
            logger4.fine("Unregistering subscription: " + localSubscription);
            if (getUpnpService().getRegistry().removeLocalSubscription(localSubscription)) {
                localSubscription.end(null);
            } else {
                log.fine("Subscription was already removed from registry");
            }
            return new StreamResponseMessage(UpnpResponse.Status.OK);
        }
        Logger logger5 = log;
        logger5.fine("Subscription ID and NT or Callback in unsubcribe request: " + getInputMessage());
        return new StreamResponseMessage(UpnpResponse.Status.BAD_REQUEST);
    }
}
