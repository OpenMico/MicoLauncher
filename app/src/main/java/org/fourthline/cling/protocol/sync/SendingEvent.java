package org.fourthline.cling.protocol.sync;

import java.net.URL;
import java.util.logging.Logger;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.model.gena.LocalGENASubscription;
import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.model.message.gena.OutgoingEventRequestMessage;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;
import org.fourthline.cling.protocol.SendingSync;
import org.fourthline.cling.transport.RouterException;

/* loaded from: classes5.dex */
public class SendingEvent extends SendingSync<OutgoingEventRequestMessage, StreamResponseMessage> {
    private static final Logger log = Logger.getLogger(SendingEvent.class.getName());
    protected final UnsignedIntegerFourBytes currentSequence;
    protected final OutgoingEventRequestMessage[] requestMessages;
    protected final String subscriptionId;

    public SendingEvent(UpnpService upnpService, LocalGENASubscription localGENASubscription) {
        super(upnpService, null);
        this.subscriptionId = localGENASubscription.getSubscriptionId();
        this.requestMessages = new OutgoingEventRequestMessage[localGENASubscription.getCallbackURLs().size()];
        int i = 0;
        for (URL url : localGENASubscription.getCallbackURLs()) {
            this.requestMessages[i] = new OutgoingEventRequestMessage(localGENASubscription, url);
            getUpnpService().getConfiguration().getGenaEventProcessor().writeBody(this.requestMessages[i]);
            i++;
        }
        this.currentSequence = localGENASubscription.getCurrentSequence();
        localGENASubscription.incrementSequence();
    }

    @Override // org.fourthline.cling.protocol.SendingSync
    protected StreamResponseMessage executeSync() throws RouterException {
        log.fine("Sending event for subscription: " + this.subscriptionId);
        OutgoingEventRequestMessage[] outgoingEventRequestMessageArr = this.requestMessages;
        StreamResponseMessage streamResponseMessage = null;
        for (OutgoingEventRequestMessage outgoingEventRequestMessage : outgoingEventRequestMessageArr) {
            if (this.currentSequence.getValue().longValue() == 0) {
                log.fine("Sending initial event message to callback URL: " + outgoingEventRequestMessage.getUri());
            } else {
                log.fine("Sending event message '" + this.currentSequence + "' to callback URL: " + outgoingEventRequestMessage.getUri());
            }
            streamResponseMessage = getUpnpService().getRouter().send(outgoingEventRequestMessage);
            log.fine("Received event callback response: " + streamResponseMessage);
        }
        return streamResponseMessage;
    }
}
