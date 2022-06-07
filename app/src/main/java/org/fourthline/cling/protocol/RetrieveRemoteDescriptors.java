package org.fourthline.cling.protocol;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.binding.xml.DescriptorBindingException;
import org.fourthline.cling.binding.xml.DeviceDescriptorBinder;
import org.fourthline.cling.binding.xml.ServiceDescriptorBinder;
import org.fourthline.cling.model.ValidationError;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.model.message.UpnpHeaders;
import org.fourthline.cling.model.message.UpnpRequest;
import org.fourthline.cling.model.meta.Icon;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.meta.RemoteDeviceIdentity;
import org.fourthline.cling.model.meta.RemoteService;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDN;
import org.fourthline.cling.registry.RegistrationException;
import org.fourthline.cling.transport.RouterException;
import org.seamless.util.Exceptions;

/* loaded from: classes5.dex */
public class RetrieveRemoteDescriptors implements Runnable {
    protected List<UDN> errorsAlreadyLogged = new ArrayList();
    private RemoteDevice rd;
    private final UpnpService upnpService;
    private static final Logger log = Logger.getLogger(RetrieveRemoteDescriptors.class.getName());
    private static final Set<URL> activeRetrievals = new CopyOnWriteArraySet();

    public RetrieveRemoteDescriptors(UpnpService upnpService, RemoteDevice remoteDevice) {
        this.upnpService = upnpService;
        this.rd = remoteDevice;
    }

    public UpnpService getUpnpService() {
        return this.upnpService;
    }

    @Override // java.lang.Runnable
    public void run() {
        URL descriptorURL = this.rd.getIdentity().getDescriptorURL();
        if (activeRetrievals.contains(descriptorURL)) {
            Logger logger = log;
            logger.finer("Exiting early, active retrieval for URL already in progress: " + descriptorURL);
        } else if (getUpnpService().getRegistry().getRemoteDevice(this.rd.getIdentity().getUdn(), true) != null) {
            Logger logger2 = log;
            logger2.finer("Exiting early, already discovered: " + descriptorURL);
        } else {
            try {
                try {
                    activeRetrievals.add(descriptorURL);
                    describe();
                } catch (RouterException e) {
                    Logger logger3 = log;
                    Level level = Level.WARNING;
                    logger3.log(level, "Descriptor retrieval failed: " + descriptorURL, (Throwable) e);
                }
            } finally {
                activeRetrievals.remove(descriptorURL);
            }
        }
    }

    protected void describe() throws RouterException {
        if (getUpnpService().getRouter() == null) {
            log.warning("Router not yet initialized");
            return;
        }
        try {
            StreamRequestMessage streamRequestMessage = new StreamRequestMessage(UpnpRequest.Method.GET, this.rd.getIdentity().getDescriptorURL());
            UpnpHeaders descriptorRetrievalHeaders = getUpnpService().getConfiguration().getDescriptorRetrievalHeaders(this.rd.getIdentity());
            if (descriptorRetrievalHeaders != null) {
                streamRequestMessage.getHeaders().putAll(descriptorRetrievalHeaders);
            }
            Logger logger = log;
            logger.fine("Sending device descriptor retrieval message: " + streamRequestMessage);
            StreamResponseMessage send = getUpnpService().getRouter().send(streamRequestMessage);
            if (send == null) {
                Logger logger2 = log;
                logger2.warning("Device descriptor retrieval failed, no response: " + this.rd.getIdentity().getDescriptorURL());
            } else if (send.getOperation().isFailed()) {
                Logger logger3 = log;
                logger3.warning("Device descriptor retrieval failed: " + this.rd.getIdentity().getDescriptorURL() + ", " + send.getOperation().getResponseDetails());
            } else {
                if (!send.isContentTypeTextUDA()) {
                    Logger logger4 = log;
                    logger4.fine("Received device descriptor without or with invalid Content-Type: " + this.rd.getIdentity().getDescriptorURL());
                }
                String bodyString = send.getBodyString();
                if (bodyString == null || bodyString.length() == 0) {
                    Logger logger5 = log;
                    logger5.warning("Received empty device descriptor:" + this.rd.getIdentity().getDescriptorURL());
                    return;
                }
                Logger logger6 = log;
                logger6.fine("Received root device descriptor: " + send);
                describe(bodyString);
            }
        } catch (IllegalArgumentException e) {
            Logger logger7 = log;
            logger7.warning("Device descriptor retrieval failed: " + this.rd.getIdentity().getDescriptorURL() + ", possibly invalid URL: " + e);
        }
    }

    protected void describe(String str) throws RouterException {
        RemoteDevice remoteDevice;
        ValidationException e;
        DescriptorBindingException e2;
        RegistrationException e3;
        try {
            remoteDevice = (RemoteDevice) getUpnpService().getConfiguration().getDeviceDescriptorBinderUDA10().describe((DeviceDescriptorBinder) this.rd, str);
            try {
                log.fine("Remote device described (without services) notifying listeners: " + remoteDevice);
                boolean notifyDiscoveryStart = getUpnpService().getRegistry().notifyDiscoveryStart(remoteDevice);
                log.fine("Hydrating described device's services: " + remoteDevice);
                RemoteDevice describeServices = describeServices(remoteDevice);
                if (describeServices == null) {
                    if (!this.errorsAlreadyLogged.contains(this.rd.getIdentity().getUdn())) {
                        this.errorsAlreadyLogged.add(this.rd.getIdentity().getUdn());
                        log.warning("Device service description failed: " + this.rd);
                    }
                    if (notifyDiscoveryStart) {
                        getUpnpService().getRegistry().notifyDiscoveryFailure(remoteDevice, new DescriptorBindingException("Device service description failed: " + this.rd));
                        return;
                    }
                    return;
                }
                log.fine("Adding fully hydrated remote device to registry: " + describeServices);
                getUpnpService().getRegistry().addDevice(describeServices);
            } catch (DescriptorBindingException e4) {
                e2 = e4;
                log.warning("Could not hydrate device or its services from descriptor: " + this.rd);
                log.warning("Cause was: " + Exceptions.unwrap(e2));
                if (!(remoteDevice == null || 0 == 0)) {
                    getUpnpService().getRegistry().notifyDiscoveryFailure(remoteDevice, e2);
                }
            } catch (ValidationException e5) {
                e = e5;
                if (!this.errorsAlreadyLogged.contains(this.rd.getIdentity().getUdn())) {
                    this.errorsAlreadyLogged.add(this.rd.getIdentity().getUdn());
                    log.warning("Could not validate device model: " + this.rd);
                    for (ValidationError validationError : e.getErrors()) {
                        log.warning(validationError.toString());
                    }
                    if (!(remoteDevice == null || 0 == 0)) {
                        getUpnpService().getRegistry().notifyDiscoveryFailure(remoteDevice, e);
                    }
                }
            } catch (RegistrationException e6) {
                e3 = e6;
                log.warning("Adding hydrated device to registry failed: " + this.rd);
                log.warning("Cause was: " + e3.toString());
                if (!(remoteDevice == null || 0 == 0)) {
                    getUpnpService().getRegistry().notifyDiscoveryFailure(remoteDevice, e3);
                }
            }
        } catch (DescriptorBindingException e7) {
            e2 = e7;
            remoteDevice = null;
        } catch (ValidationException e8) {
            e = e8;
            remoteDevice = null;
        } catch (RegistrationException e9) {
            e3 = e9;
            remoteDevice = null;
        }
    }

    protected RemoteDevice describeServices(RemoteDevice remoteDevice) throws RouterException, DescriptorBindingException, ValidationException {
        RemoteDevice describeServices;
        ArrayList arrayList = new ArrayList();
        if (remoteDevice.hasServices()) {
            for (RemoteService remoteService : filterExclusiveServices(remoteDevice.getServices())) {
                RemoteService describeService = describeService(remoteService);
                if (describeService != null) {
                    arrayList.add(describeService);
                } else {
                    log.warning("Skipping invalid service '" + remoteService + "' of: " + remoteDevice);
                }
            }
        }
        List<RemoteDevice> arrayList2 = new ArrayList<>();
        if (remoteDevice.hasEmbeddedDevices()) {
            RemoteDevice[] embeddedDevices = remoteDevice.getEmbeddedDevices();
            for (RemoteDevice remoteDevice2 : embeddedDevices) {
                if (!(remoteDevice2 == null || (describeServices = describeServices(remoteDevice2)) == null)) {
                    arrayList2.add(describeServices);
                }
            }
        }
        Icon[] iconArr = new Icon[remoteDevice.getIcons().length];
        for (int i = 0; i < remoteDevice.getIcons().length; i++) {
            iconArr[i] = remoteDevice.getIcons()[i].deepCopy();
        }
        return remoteDevice.newInstance(((RemoteDeviceIdentity) remoteDevice.getIdentity()).getUdn(), remoteDevice.getVersion(), remoteDevice.getType(), remoteDevice.getDetails(), iconArr, remoteDevice.toServiceArray((Collection<RemoteService>) arrayList), arrayList2);
    }

    protected RemoteService describeService(RemoteService remoteService) throws RouterException, DescriptorBindingException, ValidationException {
        try {
            URL normalizeURI = remoteService.getDevice().normalizeURI(remoteService.getDescriptorURI());
            StreamRequestMessage streamRequestMessage = new StreamRequestMessage(UpnpRequest.Method.GET, normalizeURI);
            UpnpHeaders descriptorRetrievalHeaders = getUpnpService().getConfiguration().getDescriptorRetrievalHeaders(remoteService.getDevice().getIdentity());
            if (descriptorRetrievalHeaders != null) {
                streamRequestMessage.getHeaders().putAll(descriptorRetrievalHeaders);
            }
            Logger logger = log;
            logger.fine("Sending service descriptor retrieval message: " + streamRequestMessage);
            StreamResponseMessage send = getUpnpService().getRouter().send(streamRequestMessage);
            if (send == null) {
                Logger logger2 = log;
                logger2.warning("Could not retrieve service descriptor, no response: " + remoteService);
                return null;
            } else if (send.getOperation().isFailed()) {
                Logger logger3 = log;
                logger3.warning("Service descriptor retrieval failed: " + normalizeURI + ", " + send.getOperation().getResponseDetails());
                return null;
            } else {
                if (!send.isContentTypeTextUDA()) {
                    Logger logger4 = log;
                    logger4.fine("Received service descriptor without or with invalid Content-Type: " + normalizeURI);
                }
                String bodyString = send.getBodyString();
                if (bodyString == null || bodyString.length() == 0) {
                    Logger logger5 = log;
                    logger5.warning("Received empty service descriptor:" + normalizeURI);
                    return null;
                }
                Logger logger6 = log;
                logger6.fine("Received service descriptor, hydrating service model: " + send);
                return (RemoteService) getUpnpService().getConfiguration().getServiceDescriptorBinderUDA10().describe((ServiceDescriptorBinder) remoteService, bodyString);
            }
        } catch (IllegalArgumentException unused) {
            Logger logger7 = log;
            logger7.warning("Could not normalize service descriptor URL: " + remoteService.getDescriptorURI());
            return null;
        }
    }

    protected List<RemoteService> filterExclusiveServices(RemoteService[] remoteServiceArr) {
        ServiceType[] exclusiveServiceTypes = getUpnpService().getConfiguration().getExclusiveServiceTypes();
        if (exclusiveServiceTypes == null || exclusiveServiceTypes.length == 0) {
            return Arrays.asList(remoteServiceArr);
        }
        ArrayList arrayList = new ArrayList();
        for (RemoteService remoteService : remoteServiceArr) {
            for (ServiceType serviceType : exclusiveServiceTypes) {
                if (remoteService.getServiceType().implementsVersion(serviceType)) {
                    log.fine("Including exclusive service: " + remoteService);
                    arrayList.add(remoteService);
                } else {
                    log.fine("Excluding unwanted service: " + serviceType);
                }
            }
        }
        return arrayList;
    }
}
