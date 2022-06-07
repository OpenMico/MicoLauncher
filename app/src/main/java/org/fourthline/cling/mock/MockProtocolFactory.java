package org.fourthline.cling.mock;

import java.net.URL;
import javax.enterprise.inject.Alternative;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.gena.LocalGENASubscription;
import org.fourthline.cling.model.gena.RemoteGENASubscription;
import org.fourthline.cling.model.message.IncomingDatagramMessage;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.protocol.ProtocolCreationException;
import org.fourthline.cling.protocol.ProtocolFactory;
import org.fourthline.cling.protocol.ReceivingAsync;
import org.fourthline.cling.protocol.ReceivingSync;
import org.fourthline.cling.protocol.async.SendingNotificationAlive;
import org.fourthline.cling.protocol.async.SendingNotificationByebye;
import org.fourthline.cling.protocol.async.SendingSearch;
import org.fourthline.cling.protocol.sync.SendingAction;
import org.fourthline.cling.protocol.sync.SendingEvent;
import org.fourthline.cling.protocol.sync.SendingRenewal;
import org.fourthline.cling.protocol.sync.SendingSubscribe;
import org.fourthline.cling.protocol.sync.SendingUnsubscribe;

@Alternative
/* loaded from: classes5.dex */
public class MockProtocolFactory implements ProtocolFactory {
    @Override // org.fourthline.cling.protocol.ProtocolFactory
    public ReceivingAsync createReceivingAsync(IncomingDatagramMessage incomingDatagramMessage) throws ProtocolCreationException {
        return null;
    }

    @Override // org.fourthline.cling.protocol.ProtocolFactory
    public ReceivingSync createReceivingSync(StreamRequestMessage streamRequestMessage) throws ProtocolCreationException {
        return null;
    }

    @Override // org.fourthline.cling.protocol.ProtocolFactory
    public SendingAction createSendingAction(ActionInvocation actionInvocation, URL url) {
        return null;
    }

    @Override // org.fourthline.cling.protocol.ProtocolFactory
    public SendingEvent createSendingEvent(LocalGENASubscription localGENASubscription) {
        return null;
    }

    @Override // org.fourthline.cling.protocol.ProtocolFactory
    public SendingNotificationAlive createSendingNotificationAlive(LocalDevice localDevice) {
        return null;
    }

    @Override // org.fourthline.cling.protocol.ProtocolFactory
    public SendingNotificationByebye createSendingNotificationByebye(LocalDevice localDevice) {
        return null;
    }

    @Override // org.fourthline.cling.protocol.ProtocolFactory
    public SendingRenewal createSendingRenewal(RemoteGENASubscription remoteGENASubscription) {
        return null;
    }

    @Override // org.fourthline.cling.protocol.ProtocolFactory
    public SendingSearch createSendingSearch(UpnpHeader upnpHeader, int i) {
        return null;
    }

    @Override // org.fourthline.cling.protocol.ProtocolFactory
    public SendingSubscribe createSendingSubscribe(RemoteGENASubscription remoteGENASubscription) {
        return null;
    }

    @Override // org.fourthline.cling.protocol.ProtocolFactory
    public SendingUnsubscribe createSendingUnsubscribe(RemoteGENASubscription remoteGENASubscription) {
        return null;
    }

    @Override // org.fourthline.cling.protocol.ProtocolFactory
    public UpnpService getUpnpService() {
        return null;
    }
}
