package org.fourthline.cling.protocol.async;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.model.DiscoveryOptions;
import org.fourthline.cling.model.Location;
import org.fourthline.cling.model.NetworkAddress;
import org.fourthline.cling.model.message.IncomingDatagramMessage;
import org.fourthline.cling.model.message.UpnpRequest;
import org.fourthline.cling.model.message.discovery.IncomingSearchRequest;
import org.fourthline.cling.model.message.discovery.OutgoingSearchResponse;
import org.fourthline.cling.model.message.discovery.OutgoingSearchResponseDeviceType;
import org.fourthline.cling.model.message.discovery.OutgoingSearchResponseRootDevice;
import org.fourthline.cling.model.message.discovery.OutgoingSearchResponseServiceType;
import org.fourthline.cling.model.message.discovery.OutgoingSearchResponseUDN;
import org.fourthline.cling.model.message.header.DeviceTypeHeader;
import org.fourthline.cling.model.message.header.MXHeader;
import org.fourthline.cling.model.message.header.RootDeviceHeader;
import org.fourthline.cling.model.message.header.STAllHeader;
import org.fourthline.cling.model.message.header.ServiceTypeHeader;
import org.fourthline.cling.model.message.header.UDNHeader;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDN;
import org.fourthline.cling.protocol.ReceivingAsync;
import org.fourthline.cling.transport.RouterException;

/* loaded from: classes5.dex */
public class ReceivingSearch extends ReceivingAsync<IncomingSearchRequest> {
    protected final Random randomGenerator = new Random();
    private static final Logger log = Logger.getLogger(ReceivingSearch.class.getName());
    private static final boolean LOG_ENABLED = log.isLoggable(Level.FINE);

    protected void prepareOutgoingSearchResponse(OutgoingSearchResponse outgoingSearchResponse) {
    }

    public ReceivingSearch(UpnpService upnpService, IncomingDatagramMessage<UpnpRequest> incomingDatagramMessage) {
        super(upnpService, new IncomingSearchRequest(incomingDatagramMessage));
    }

    @Override // org.fourthline.cling.protocol.ReceivingAsync
    protected void execute() throws RouterException {
        if (getUpnpService().getRouter() == null) {
            log.fine("Router hasn't completed initialization, ignoring received search message");
        } else if (!getInputMessage().isMANSSDPDiscover()) {
            Logger logger = log;
            logger.fine("Invalid search request, no or invalid MAN ssdp:discover header: " + getInputMessage());
        } else {
            UpnpHeader searchTarget = getInputMessage().getSearchTarget();
            if (searchTarget == null) {
                Logger logger2 = log;
                logger2.fine("Invalid search request, did not contain ST header: " + getInputMessage());
                return;
            }
            List<NetworkAddress> activeStreamServers = getUpnpService().getRouter().getActiveStreamServers(getInputMessage().getLocalAddress());
            if (activeStreamServers.size() == 0) {
                log.fine("Aborting search response, no active stream servers found (network disabled?)");
                return;
            }
            for (NetworkAddress networkAddress : activeStreamServers) {
                sendResponses(searchTarget, networkAddress);
            }
        }
    }

    @Override // org.fourthline.cling.protocol.ReceivingAsync
    protected boolean waitBeforeExecution() throws InterruptedException {
        Integer mx = getInputMessage().getMX();
        if (mx == null) {
            Logger logger = log;
            logger.fine("Invalid search request, did not contain MX header: " + getInputMessage());
            return false;
        }
        if (mx.intValue() > 120 || mx.intValue() <= 0) {
            mx = MXHeader.DEFAULT_VALUE;
        }
        if (getUpnpService().getRegistry().getLocalDevices().size() <= 0) {
            return true;
        }
        int nextInt = this.randomGenerator.nextInt(mx.intValue() * 1000);
        Logger logger2 = log;
        logger2.fine("Sleeping " + nextInt + " milliseconds to avoid flooding with search responses");
        Thread.sleep((long) nextInt);
        return true;
    }

    protected void sendResponses(UpnpHeader upnpHeader, NetworkAddress networkAddress) throws RouterException {
        if (upnpHeader instanceof STAllHeader) {
            sendSearchResponseAll(networkAddress);
        } else if (upnpHeader instanceof RootDeviceHeader) {
            sendSearchResponseRootDevices(networkAddress);
        } else if (upnpHeader instanceof UDNHeader) {
            sendSearchResponseUDN((UDN) upnpHeader.getValue(), networkAddress);
        } else if (upnpHeader instanceof DeviceTypeHeader) {
            sendSearchResponseDeviceType((DeviceType) upnpHeader.getValue(), networkAddress);
        } else if (upnpHeader instanceof ServiceTypeHeader) {
            sendSearchResponseServiceType((ServiceType) upnpHeader.getValue(), networkAddress);
        } else {
            Logger logger = log;
            logger.warning("Non-implemented search request target: " + upnpHeader.getClass());
        }
    }

    protected void sendSearchResponseAll(NetworkAddress networkAddress) throws RouterException {
        if (LOG_ENABLED) {
            log.fine("Responding to 'all' search with advertisement messages for all local devices");
        }
        for (LocalDevice localDevice : getUpnpService().getRegistry().getLocalDevices()) {
            if (!isAdvertisementDisabled(localDevice)) {
                if (LOG_ENABLED) {
                    log.finer("Sending root device messages: " + localDevice);
                }
                for (OutgoingSearchResponse outgoingSearchResponse : createDeviceMessages(localDevice, networkAddress)) {
                    getUpnpService().getRouter().send(outgoingSearchResponse);
                }
                if (localDevice.hasEmbeddedDevices()) {
                    LocalDevice[] findEmbeddedDevices = localDevice.findEmbeddedDevices();
                    for (LocalDevice localDevice2 : findEmbeddedDevices) {
                        if (LOG_ENABLED) {
                            log.finer("Sending embedded device messages: " + localDevice2);
                        }
                        for (OutgoingSearchResponse outgoingSearchResponse2 : createDeviceMessages(localDevice2, networkAddress)) {
                            getUpnpService().getRouter().send(outgoingSearchResponse2);
                        }
                    }
                }
                List<OutgoingSearchResponse> createServiceTypeMessages = createServiceTypeMessages(localDevice, networkAddress);
                if (createServiceTypeMessages.size() > 0) {
                    if (LOG_ENABLED) {
                        log.finer("Sending service type messages");
                    }
                    for (OutgoingSearchResponse outgoingSearchResponse3 : createServiceTypeMessages) {
                        getUpnpService().getRouter().send(outgoingSearchResponse3);
                    }
                }
            }
        }
    }

    protected List<OutgoingSearchResponse> createDeviceMessages(LocalDevice localDevice, NetworkAddress networkAddress) {
        ArrayList<OutgoingSearchResponse> arrayList = new ArrayList();
        if (localDevice.isRoot()) {
            arrayList.add(new OutgoingSearchResponseRootDevice(getInputMessage(), getDescriptorLocation(networkAddress, localDevice), localDevice));
        }
        arrayList.add(new OutgoingSearchResponseUDN(getInputMessage(), getDescriptorLocation(networkAddress, localDevice), localDevice));
        arrayList.add(new OutgoingSearchResponseDeviceType(getInputMessage(), getDescriptorLocation(networkAddress, localDevice), localDevice));
        for (OutgoingSearchResponse outgoingSearchResponse : arrayList) {
            prepareOutgoingSearchResponse(outgoingSearchResponse);
        }
        return arrayList;
    }

    protected List<OutgoingSearchResponse> createServiceTypeMessages(LocalDevice localDevice, NetworkAddress networkAddress) {
        ArrayList arrayList = new ArrayList();
        for (ServiceType serviceType : localDevice.findServiceTypes()) {
            OutgoingSearchResponseServiceType outgoingSearchResponseServiceType = new OutgoingSearchResponseServiceType(getInputMessage(), getDescriptorLocation(networkAddress, localDevice), localDevice, serviceType);
            prepareOutgoingSearchResponse(outgoingSearchResponseServiceType);
            arrayList.add(outgoingSearchResponseServiceType);
        }
        return arrayList;
    }

    protected void sendSearchResponseRootDevices(NetworkAddress networkAddress) throws RouterException {
        log.fine("Responding to root device search with advertisement messages for all local root devices");
        for (LocalDevice localDevice : getUpnpService().getRegistry().getLocalDevices()) {
            if (!isAdvertisementDisabled(localDevice)) {
                OutgoingSearchResponseRootDevice outgoingSearchResponseRootDevice = new OutgoingSearchResponseRootDevice(getInputMessage(), getDescriptorLocation(networkAddress, localDevice), localDevice);
                prepareOutgoingSearchResponse(outgoingSearchResponseRootDevice);
                getUpnpService().getRouter().send(outgoingSearchResponseRootDevice);
            }
        }
    }

    protected void sendSearchResponseUDN(UDN udn, NetworkAddress networkAddress) throws RouterException {
        Device device = getUpnpService().getRegistry().getDevice(udn, false);
        if (device != null && (device instanceof LocalDevice)) {
            LocalDevice localDevice = (LocalDevice) device;
            if (!isAdvertisementDisabled(localDevice)) {
                Logger logger = log;
                logger.fine("Responding to UDN device search: " + udn);
                OutgoingSearchResponseUDN outgoingSearchResponseUDN = new OutgoingSearchResponseUDN(getInputMessage(), getDescriptorLocation(networkAddress, localDevice), localDevice);
                prepareOutgoingSearchResponse(outgoingSearchResponseUDN);
                getUpnpService().getRouter().send(outgoingSearchResponseUDN);
            }
        }
    }

    protected void sendSearchResponseDeviceType(DeviceType deviceType, NetworkAddress networkAddress) throws RouterException {
        Logger logger = log;
        logger.fine("Responding to device type search: " + deviceType);
        for (Device device : getUpnpService().getRegistry().getDevices(deviceType)) {
            if (device instanceof LocalDevice) {
                LocalDevice localDevice = (LocalDevice) device;
                if (!isAdvertisementDisabled(localDevice)) {
                    Logger logger2 = log;
                    logger2.finer("Sending matching device type search result for: " + device);
                    OutgoingSearchResponseDeviceType outgoingSearchResponseDeviceType = new OutgoingSearchResponseDeviceType(getInputMessage(), getDescriptorLocation(networkAddress, localDevice), localDevice);
                    prepareOutgoingSearchResponse(outgoingSearchResponseDeviceType);
                    getUpnpService().getRouter().send(outgoingSearchResponseDeviceType);
                }
            }
        }
    }

    protected void sendSearchResponseServiceType(ServiceType serviceType, NetworkAddress networkAddress) throws RouterException {
        Logger logger = log;
        logger.fine("Responding to service type search: " + serviceType);
        for (Device device : getUpnpService().getRegistry().getDevices(serviceType)) {
            if (device instanceof LocalDevice) {
                LocalDevice localDevice = (LocalDevice) device;
                if (!isAdvertisementDisabled(localDevice)) {
                    Logger logger2 = log;
                    logger2.finer("Sending matching service type search result: " + device);
                    OutgoingSearchResponseServiceType outgoingSearchResponseServiceType = new OutgoingSearchResponseServiceType(getInputMessage(), getDescriptorLocation(networkAddress, localDevice), localDevice, serviceType);
                    prepareOutgoingSearchResponse(outgoingSearchResponseServiceType);
                    getUpnpService().getRouter().send(outgoingSearchResponseServiceType);
                }
            }
        }
    }

    protected Location getDescriptorLocation(NetworkAddress networkAddress, LocalDevice localDevice) {
        return new Location(networkAddress, getUpnpService().getConfiguration().getNamespace().getDescriptorPathString(localDevice));
    }

    protected boolean isAdvertisementDisabled(LocalDevice localDevice) {
        DiscoveryOptions discoveryOptions = getUpnpService().getRegistry().getDiscoveryOptions(localDevice.getIdentity().getUdn());
        return discoveryOptions != null && !discoveryOptions.isAdvertised();
    }
}
