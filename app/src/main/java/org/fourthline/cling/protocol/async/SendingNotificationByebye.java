package org.fourthline.cling.protocol.async;

import java.util.logging.Logger;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.types.NotificationSubtype;
import org.fourthline.cling.transport.RouterException;

/* loaded from: classes5.dex */
public class SendingNotificationByebye extends SendingNotification {
    private static final Logger log = Logger.getLogger(SendingNotification.class.getName());

    public SendingNotificationByebye(UpnpService upnpService, LocalDevice localDevice) {
        super(upnpService, localDevice);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.fourthline.cling.protocol.async.SendingNotification, org.fourthline.cling.protocol.SendingAsync
    public void execute() throws RouterException {
        Logger logger = log;
        logger.fine("Sending byebye messages (" + getBulkRepeat() + " times) for: " + getDevice());
        super.execute();
    }

    @Override // org.fourthline.cling.protocol.async.SendingNotification
    protected NotificationSubtype getNotificationSubtype() {
        return NotificationSubtype.BYEBYE;
    }
}
