package org.fourthline.cling.protocol.async;

import java.util.logging.Logger;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.types.NotificationSubtype;
import org.fourthline.cling.transport.RouterException;

/* loaded from: classes5.dex */
public class SendingNotificationAlive extends SendingNotification {
    private static final Logger log = Logger.getLogger(SendingNotification.class.getName());

    public SendingNotificationAlive(UpnpService upnpService, LocalDevice localDevice) {
        super(upnpService, localDevice);
    }

    @Override // org.fourthline.cling.protocol.async.SendingNotification, org.fourthline.cling.protocol.SendingAsync
    public void execute() throws RouterException {
        Logger logger = log;
        logger.fine("Sending alive messages (" + getBulkRepeat() + " times) for: " + getDevice());
        super.execute();
    }

    @Override // org.fourthline.cling.protocol.async.SendingNotification
    protected NotificationSubtype getNotificationSubtype() {
        return NotificationSubtype.ALIVE;
    }
}
