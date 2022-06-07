package org.fourthline.cling.protocol.async;

import java.util.logging.Logger;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.model.ValidationError;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.message.IncomingDatagramMessage;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.discovery.IncomingSearchResponse;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.meta.RemoteDeviceIdentity;
import org.fourthline.cling.model.types.UDN;
import org.fourthline.cling.protocol.ReceivingAsync;
import org.fourthline.cling.protocol.RetrieveRemoteDescriptors;
import org.fourthline.cling.transport.RouterException;

/* loaded from: classes5.dex */
public class ReceivingSearchResponse extends ReceivingAsync<IncomingSearchResponse> {
    private static final Logger log = Logger.getLogger(ReceivingSearchResponse.class.getName());

    public ReceivingSearchResponse(UpnpService upnpService, IncomingDatagramMessage<UpnpResponse> incomingDatagramMessage) {
        super(upnpService, new IncomingSearchResponse(incomingDatagramMessage));
    }

    @Override // org.fourthline.cling.protocol.ReceivingAsync
    protected void execute() throws RouterException {
        if (!getInputMessage().isSearchResponseMessage()) {
            Logger logger = log;
            logger.fine("Ignoring invalid search response message: " + getInputMessage());
            return;
        }
        UDN rootDeviceUDN = getInputMessage().getRootDeviceUDN();
        if (rootDeviceUDN == null) {
            Logger logger2 = log;
            logger2.fine("Ignoring search response message without UDN: " + getInputMessage());
            return;
        }
        RemoteDeviceIdentity remoteDeviceIdentity = new RemoteDeviceIdentity(getInputMessage());
        Logger logger3 = log;
        logger3.fine("Received device search response: " + remoteDeviceIdentity);
        if (getUpnpService().getRegistry().update(remoteDeviceIdentity)) {
            Logger logger4 = log;
            logger4.fine("Remote device was already known: " + rootDeviceUDN);
            return;
        }
        try {
            RemoteDevice remoteDevice = new RemoteDevice(remoteDeviceIdentity);
            if (remoteDeviceIdentity.getDescriptorURL() == null) {
                Logger logger5 = log;
                logger5.finer("Ignoring message without location URL header: " + getInputMessage());
            } else if (remoteDeviceIdentity.getMaxAgeSeconds() == null) {
                Logger logger6 = log;
                logger6.finer("Ignoring message without max-age header: " + getInputMessage());
            } else {
                getUpnpService().getConfiguration().getAsyncProtocolExecutor().execute(new RetrieveRemoteDescriptors(getUpnpService(), remoteDevice));
            }
        } catch (ValidationException e) {
            Logger logger7 = log;
            logger7.warning("Validation errors of device during discovery: " + remoteDeviceIdentity);
            for (ValidationError validationError : e.getErrors()) {
                log.warning(validationError.toString());
            }
        }
    }
}
