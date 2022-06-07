package org.fourthline.cling.protocol.sync;

import java.util.logging.Logger;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.model.gena.CancelReason;
import org.fourthline.cling.model.gena.RemoteGENASubscription;
import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.model.message.gena.OutgoingUnsubscribeRequestMessage;
import org.fourthline.cling.protocol.SendingSync;
import org.fourthline.cling.transport.RouterException;

/* loaded from: classes5.dex */
public class SendingUnsubscribe extends SendingSync<OutgoingUnsubscribeRequestMessage, StreamResponseMessage> {
    private static final Logger log = Logger.getLogger(SendingUnsubscribe.class.getName());
    protected final RemoteGENASubscription subscription;

    public SendingUnsubscribe(UpnpService upnpService, RemoteGENASubscription remoteGENASubscription) {
        super(upnpService, new OutgoingUnsubscribeRequestMessage(remoteGENASubscription, upnpService.getConfiguration().getEventSubscriptionHeaders(remoteGENASubscription.getService())));
        this.subscription = remoteGENASubscription;
    }

    @Override // org.fourthline.cling.protocol.SendingSync
    protected StreamResponseMessage executeSync() throws RouterException {
        Logger logger = log;
        logger.fine("Sending unsubscribe request: " + getInputMessage());
        try {
            StreamResponseMessage send = getUpnpService().getRouter().send(getInputMessage());
            onUnsubscribe(send);
            return send;
        } catch (Throwable th) {
            onUnsubscribe(null);
            throw th;
        }
    }

    protected void onUnsubscribe(final StreamResponseMessage streamResponseMessage) {
        getUpnpService().getRegistry().removeRemoteSubscription(this.subscription);
        getUpnpService().getConfiguration().getRegistryListenerExecutor().execute(new Runnable() { // from class: org.fourthline.cling.protocol.sync.SendingUnsubscribe.1
            @Override // java.lang.Runnable
            public void run() {
                StreamResponseMessage streamResponseMessage2 = streamResponseMessage;
                if (streamResponseMessage2 == null) {
                    SendingUnsubscribe.log.fine("Unsubscribe failed, no response received");
                    SendingUnsubscribe.this.subscription.end(CancelReason.UNSUBSCRIBE_FAILED, null);
                } else if (streamResponseMessage2.getOperation().isFailed()) {
                    Logger logger = SendingUnsubscribe.log;
                    logger.fine("Unsubscribe failed, response was: " + streamResponseMessage);
                    SendingUnsubscribe.this.subscription.end(CancelReason.UNSUBSCRIBE_FAILED, streamResponseMessage.getOperation());
                } else {
                    Logger logger2 = SendingUnsubscribe.log;
                    logger2.fine("Unsubscribe successful, response was: " + streamResponseMessage);
                    SendingUnsubscribe.this.subscription.end(null, streamResponseMessage.getOperation());
                }
            }
        });
    }
}
