package org.fourthline.cling.protocol;

import java.net.URL;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.gena.LocalGENASubscription;
import org.fourthline.cling.model.gena.RemoteGENASubscription;
import org.fourthline.cling.model.message.IncomingDatagramMessage;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.protocol.async.SendingNotificationAlive;
import org.fourthline.cling.protocol.async.SendingNotificationByebye;
import org.fourthline.cling.protocol.async.SendingSearch;
import org.fourthline.cling.protocol.sync.SendingAction;
import org.fourthline.cling.protocol.sync.SendingEvent;
import org.fourthline.cling.protocol.sync.SendingRenewal;
import org.fourthline.cling.protocol.sync.SendingSubscribe;
import org.fourthline.cling.protocol.sync.SendingUnsubscribe;

/* loaded from: classes5.dex */
public interface ProtocolFactory {
    ReceivingAsync createReceivingAsync(IncomingDatagramMessage incomingDatagramMessage) throws ProtocolCreationException;

    ReceivingSync createReceivingSync(StreamRequestMessage streamRequestMessage) throws ProtocolCreationException;

    SendingAction createSendingAction(ActionInvocation actionInvocation, URL url);

    SendingEvent createSendingEvent(LocalGENASubscription localGENASubscription);

    SendingNotificationAlive createSendingNotificationAlive(LocalDevice localDevice);

    SendingNotificationByebye createSendingNotificationByebye(LocalDevice localDevice);

    SendingRenewal createSendingRenewal(RemoteGENASubscription remoteGENASubscription);

    SendingSearch createSendingSearch(UpnpHeader upnpHeader, int i);

    SendingSubscribe createSendingSubscribe(RemoteGENASubscription remoteGENASubscription) throws ProtocolCreationException;

    SendingUnsubscribe createSendingUnsubscribe(RemoteGENASubscription remoteGENASubscription);

    UpnpService getUpnpService();
}
