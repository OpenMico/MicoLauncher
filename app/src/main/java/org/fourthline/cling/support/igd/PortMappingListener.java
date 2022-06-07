package org.fourthline.cling.support.igd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDAServiceType;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.support.igd.callback.PortMappingAdd;
import org.fourthline.cling.support.igd.callback.PortMappingDelete;
import org.fourthline.cling.support.model.PortMapping;

/* loaded from: classes5.dex */
public class PortMappingListener extends DefaultRegistryListener {
    protected Map<Service, List<PortMapping>> activePortMappings;
    protected PortMapping[] portMappings;
    private static final Logger log = Logger.getLogger(PortMappingListener.class.getName());
    public static final DeviceType IGD_DEVICE_TYPE = new UDADeviceType("InternetGatewayDevice", 1);
    public static final DeviceType CONNECTION_DEVICE_TYPE = new UDADeviceType("WANConnectionDevice", 1);
    public static final ServiceType IP_SERVICE_TYPE = new UDAServiceType("WANIPConnection", 1);
    public static final ServiceType PPP_SERVICE_TYPE = new UDAServiceType("WANPPPConnection", 1);

    public PortMappingListener(PortMapping portMapping) {
        this(new PortMapping[]{portMapping});
    }

    public PortMappingListener(PortMapping[] portMappingArr) {
        this.activePortMappings = new HashMap();
        this.portMappings = portMappingArr;
    }

    @Override // org.fourthline.cling.registry.DefaultRegistryListener
    public synchronized void deviceAdded(Registry registry, Device device) {
        Service discoverConnectionService = discoverConnectionService(device);
        if (discoverConnectionService != null) {
            log.fine("Activating port mappings on: " + discoverConnectionService);
            final ArrayList arrayList = new ArrayList();
            PortMapping[] portMappingArr = this.portMappings;
            for (final PortMapping portMapping : portMappingArr) {
                new PortMappingAdd(discoverConnectionService, registry.getUpnpService().getControlPoint(), portMapping) { // from class: org.fourthline.cling.support.igd.PortMappingListener.1
                    @Override // org.fourthline.cling.controlpoint.ActionCallback
                    public void success(ActionInvocation actionInvocation) {
                        Logger logger = PortMappingListener.log;
                        logger.fine("Port mapping added: " + portMapping);
                        arrayList.add(portMapping);
                    }

                    @Override // org.fourthline.cling.controlpoint.ActionCallback
                    public void failure(ActionInvocation actionInvocation, UpnpResponse upnpResponse, String str) {
                        PortMappingListener portMappingListener = PortMappingListener.this;
                        portMappingListener.handleFailureMessage("Failed to add port mapping: " + portMapping);
                        PortMappingListener portMappingListener2 = PortMappingListener.this;
                        portMappingListener2.handleFailureMessage("Reason: " + str);
                    }
                }.run();
            }
            this.activePortMappings.put(discoverConnectionService, arrayList);
        }
    }

    @Override // org.fourthline.cling.registry.DefaultRegistryListener
    public synchronized void deviceRemoved(Registry registry, Device device) {
        Service[] findServices = device.findServices();
        for (Service service : findServices) {
            Iterator<Map.Entry<Service, List<PortMapping>>> it = this.activePortMappings.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Service, List<PortMapping>> next = it.next();
                if (next.getKey().equals(service)) {
                    if (next.getValue().size() > 0) {
                        handleFailureMessage("Device disappeared, couldn't delete port mappings: " + next.getValue().size());
                    }
                    it.remove();
                }
            }
        }
    }

    @Override // org.fourthline.cling.registry.DefaultRegistryListener, org.fourthline.cling.registry.RegistryListener
    public synchronized void beforeShutdown(Registry registry) {
        for (Map.Entry<Service, List<PortMapping>> entry : this.activePortMappings.entrySet()) {
            final Iterator<PortMapping> it = entry.getValue().iterator();
            while (it.hasNext()) {
                final PortMapping next = it.next();
                Logger logger = log;
                logger.fine("Trying to delete port mapping on IGD: " + next);
                new PortMappingDelete(entry.getKey(), registry.getUpnpService().getControlPoint(), next) { // from class: org.fourthline.cling.support.igd.PortMappingListener.2
                    @Override // org.fourthline.cling.controlpoint.ActionCallback
                    public void success(ActionInvocation actionInvocation) {
                        Logger logger2 = PortMappingListener.log;
                        logger2.fine("Port mapping deleted: " + next);
                        it.remove();
                    }

                    @Override // org.fourthline.cling.controlpoint.ActionCallback
                    public void failure(ActionInvocation actionInvocation, UpnpResponse upnpResponse, String str) {
                        PortMappingListener portMappingListener = PortMappingListener.this;
                        portMappingListener.handleFailureMessage("Failed to delete port mapping: " + next);
                        PortMappingListener portMappingListener2 = PortMappingListener.this;
                        portMappingListener2.handleFailureMessage("Reason: " + str);
                    }
                }.run();
            }
        }
    }

    protected Service discoverConnectionService(Device device) {
        if (!device.getType().equals(IGD_DEVICE_TYPE)) {
            return null;
        }
        Device[] findDevices = device.findDevices(CONNECTION_DEVICE_TYPE);
        if (findDevices.length == 0) {
            Logger logger = log;
            logger.fine("IGD doesn't support '" + CONNECTION_DEVICE_TYPE + "': " + device);
            return null;
        }
        Device device2 = findDevices[0];
        Logger logger2 = log;
        logger2.fine("Using first discovered WAN connection device: " + device2);
        Service findService = device2.findService(IP_SERVICE_TYPE);
        Service findService2 = device2.findService(PPP_SERVICE_TYPE);
        if (findService == null && findService2 == null) {
            Logger logger3 = log;
            logger3.fine("IGD doesn't support IP or PPP WAN connection service: " + device);
        }
        return findService != null ? findService : findService2;
    }

    protected void handleFailureMessage(String str) {
        log.warning(str);
    }
}
