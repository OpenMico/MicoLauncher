package org.fourthline.cling.model.meta;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.fourthline.cling.model.Namespace;
import org.fourthline.cling.model.ValidationError;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.profile.DeviceDetailsProvider;
import org.fourthline.cling.model.profile.RemoteClientInfo;
import org.fourthline.cling.model.resource.DeviceDescriptorResource;
import org.fourthline.cling.model.resource.IconResource;
import org.fourthline.cling.model.resource.Resource;
import org.fourthline.cling.model.resource.ServiceControlResource;
import org.fourthline.cling.model.resource.ServiceDescriptorResource;
import org.fourthline.cling.model.resource.ServiceEventSubscriptionResource;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.ServiceId;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDN;

/* loaded from: classes5.dex */
public class LocalDevice extends Device<DeviceIdentity, LocalDevice, LocalService> {
    private final DeviceDetailsProvider deviceDetailsProvider;

    public LocalDevice(DeviceIdentity deviceIdentity) throws ValidationException {
        super(deviceIdentity);
        this.deviceDetailsProvider = null;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetails deviceDetails, LocalService localService) throws ValidationException {
        super(deviceIdentity, deviceType, deviceDetails, null, new LocalService[]{localService});
        this.deviceDetailsProvider = null;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetailsProvider deviceDetailsProvider, LocalService localService) throws ValidationException {
        super(deviceIdentity, deviceType, null, null, new LocalService[]{localService});
        this.deviceDetailsProvider = deviceDetailsProvider;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetailsProvider deviceDetailsProvider, LocalService localService, LocalDevice localDevice) throws ValidationException {
        super(deviceIdentity, deviceType, null, null, new LocalService[]{localService}, new LocalDevice[]{localDevice});
        this.deviceDetailsProvider = deviceDetailsProvider;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetails deviceDetails, LocalService localService, LocalDevice localDevice) throws ValidationException {
        super(deviceIdentity, deviceType, deviceDetails, null, new LocalService[]{localService}, new LocalDevice[]{localDevice});
        this.deviceDetailsProvider = null;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetails deviceDetails, LocalService[] localServiceArr) throws ValidationException {
        super(deviceIdentity, deviceType, deviceDetails, null, localServiceArr);
        this.deviceDetailsProvider = null;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetails deviceDetails, LocalService[] localServiceArr, LocalDevice[] localDeviceArr) throws ValidationException {
        super(deviceIdentity, deviceType, deviceDetails, null, localServiceArr, localDeviceArr);
        this.deviceDetailsProvider = null;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetails deviceDetails, Icon icon, LocalService localService) throws ValidationException {
        super(deviceIdentity, deviceType, deviceDetails, new Icon[]{icon}, new LocalService[]{localService});
        this.deviceDetailsProvider = null;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetails deviceDetails, Icon icon, LocalService localService, LocalDevice localDevice) throws ValidationException {
        super(deviceIdentity, deviceType, deviceDetails, new Icon[]{icon}, new LocalService[]{localService}, new LocalDevice[]{localDevice});
        this.deviceDetailsProvider = null;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetails deviceDetails, Icon icon, LocalService[] localServiceArr) throws ValidationException {
        super(deviceIdentity, deviceType, deviceDetails, new Icon[]{icon}, localServiceArr);
        this.deviceDetailsProvider = null;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetailsProvider deviceDetailsProvider, Icon icon, LocalService[] localServiceArr) throws ValidationException {
        super(deviceIdentity, deviceType, null, new Icon[]{icon}, localServiceArr);
        this.deviceDetailsProvider = deviceDetailsProvider;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetails deviceDetails, Icon icon, LocalService[] localServiceArr, LocalDevice[] localDeviceArr) throws ValidationException {
        super(deviceIdentity, deviceType, deviceDetails, new Icon[]{icon}, localServiceArr, localDeviceArr);
        this.deviceDetailsProvider = null;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetails deviceDetails, Icon[] iconArr, LocalService localService) throws ValidationException {
        super(deviceIdentity, deviceType, deviceDetails, iconArr, new LocalService[]{localService});
        this.deviceDetailsProvider = null;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetails deviceDetails, Icon[] iconArr, LocalService localService, LocalDevice localDevice) throws ValidationException {
        super(deviceIdentity, deviceType, deviceDetails, iconArr, new LocalService[]{localService}, new LocalDevice[]{localDevice});
        this.deviceDetailsProvider = null;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetailsProvider deviceDetailsProvider, Icon[] iconArr, LocalService localService, LocalDevice localDevice) throws ValidationException {
        super(deviceIdentity, deviceType, null, iconArr, new LocalService[]{localService}, new LocalDevice[]{localDevice});
        this.deviceDetailsProvider = deviceDetailsProvider;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetails deviceDetails, Icon[] iconArr, LocalService[] localServiceArr) throws ValidationException {
        super(deviceIdentity, deviceType, deviceDetails, iconArr, localServiceArr);
        this.deviceDetailsProvider = null;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, DeviceType deviceType, DeviceDetails deviceDetails, Icon[] iconArr, LocalService[] localServiceArr, LocalDevice[] localDeviceArr) throws ValidationException {
        super(deviceIdentity, deviceType, deviceDetails, iconArr, localServiceArr, localDeviceArr);
        this.deviceDetailsProvider = null;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, UDAVersion uDAVersion, DeviceType deviceType, DeviceDetails deviceDetails, Icon[] iconArr, LocalService[] localServiceArr, LocalDevice[] localDeviceArr) throws ValidationException {
        super(deviceIdentity, uDAVersion, deviceType, deviceDetails, iconArr, localServiceArr, localDeviceArr);
        this.deviceDetailsProvider = null;
    }

    public LocalDevice(DeviceIdentity deviceIdentity, UDAVersion uDAVersion, DeviceType deviceType, DeviceDetailsProvider deviceDetailsProvider, Icon[] iconArr, LocalService[] localServiceArr, LocalDevice[] localDeviceArr) throws ValidationException {
        super(deviceIdentity, uDAVersion, deviceType, null, iconArr, localServiceArr, localDeviceArr);
        this.deviceDetailsProvider = deviceDetailsProvider;
    }

    public DeviceDetailsProvider getDeviceDetailsProvider() {
        return this.deviceDetailsProvider;
    }

    @Override // org.fourthline.cling.model.meta.Device
    public DeviceDetails getDetails(RemoteClientInfo remoteClientInfo) {
        if (getDeviceDetailsProvider() != null) {
            return getDeviceDetailsProvider().provide(remoteClientInfo);
        }
        return getDetails();
    }

    @Override // org.fourthline.cling.model.meta.Device
    public LocalService[] getServices() {
        return this.services != null ? (LocalService[]) this.services : new LocalService[0];
    }

    @Override // org.fourthline.cling.model.meta.Device
    public LocalDevice[] getEmbeddedDevices() {
        return this.embeddedDevices != null ? (LocalDevice[]) this.embeddedDevices : new LocalDevice[0];
    }

    public LocalDevice newInstance(UDN udn, UDAVersion uDAVersion, DeviceType deviceType, DeviceDetails deviceDetails, Icon[] iconArr, LocalService[] localServiceArr, List<LocalDevice> list) throws ValidationException {
        return new LocalDevice(new DeviceIdentity(udn, getIdentity().getMaxAgeSeconds()), uDAVersion, deviceType, deviceDetails, iconArr, localServiceArr, list.size() > 0 ? (LocalDevice[]) list.toArray(new LocalDevice[list.size()]) : null);
    }

    @Override // org.fourthline.cling.model.meta.Device
    public LocalService newInstance(ServiceType serviceType, ServiceId serviceId, URI uri, URI uri2, URI uri3, Action<LocalService>[] actionArr, StateVariable<LocalService>[] stateVariableArr) throws ValidationException {
        return new LocalService(serviceType, serviceId, actionArr, stateVariableArr);
    }

    @Override // org.fourthline.cling.model.meta.Device
    public LocalDevice[] toDeviceArray(Collection<LocalDevice> collection) {
        return (LocalDevice[]) collection.toArray(new LocalDevice[collection.size()]);
    }

    @Override // org.fourthline.cling.model.meta.Device
    public LocalService[] newServiceArray(int i) {
        return new LocalService[i];
    }

    @Override // org.fourthline.cling.model.meta.Device
    public LocalService[] toServiceArray(Collection<LocalService> collection) {
        return (LocalService[]) collection.toArray(new LocalService[collection.size()]);
    }

    @Override // org.fourthline.cling.model.meta.Device, org.fourthline.cling.model.Validatable
    public List<ValidationError> validate() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(super.validate());
        if (hasIcons()) {
            Icon[] icons = getIcons();
            for (Icon icon : icons) {
                if (icon.getUri().isAbsolute()) {
                    arrayList.add(new ValidationError(getClass(), "icons", "Local icon URI can not be absolute: " + icon.getUri()));
                }
                if (icon.getUri().toString().contains("../")) {
                    arrayList.add(new ValidationError(getClass(), "icons", "Local icon URI must not contain '../': " + icon.getUri()));
                }
                if (icon.getUri().toString().startsWith("/")) {
                    arrayList.add(new ValidationError(getClass(), "icons", "Local icon URI must not start with '/': " + icon.getUri()));
                }
            }
        }
        return arrayList;
    }

    @Override // org.fourthline.cling.model.meta.Device
    public Resource[] discoverResources(Namespace namespace) {
        ArrayList arrayList = new ArrayList();
        if (isRoot()) {
            arrayList.add(new DeviceDescriptorResource(namespace.getDescriptorPath(this), this));
        }
        LocalService[] services = getServices();
        for (LocalService localService : services) {
            arrayList.add(new ServiceDescriptorResource(namespace.getDescriptorPath(localService), localService));
            arrayList.add(new ServiceControlResource(namespace.getControlPath(localService), localService));
            arrayList.add(new ServiceEventSubscriptionResource(namespace.getEventSubscriptionPath(localService), localService));
        }
        Icon[] icons = getIcons();
        for (Icon icon : icons) {
            arrayList.add(new IconResource(namespace.prefixIfRelative(this, icon.getUri()), icon));
        }
        if (hasEmbeddedDevices()) {
            for (LocalDevice localDevice : getEmbeddedDevices()) {
                arrayList.addAll(Arrays.asList(localDevice.discoverResources(namespace)));
            }
        }
        return (Resource[]) arrayList.toArray(new Resource[arrayList.size()]);
    }

    @Override // org.fourthline.cling.model.meta.Device
    public LocalDevice getRoot() {
        if (isRoot()) {
            return this;
        }
        LocalDevice localDevice = this;
        while (localDevice.getParentDevice() != null) {
            localDevice = localDevice.getParentDevice();
        }
        return localDevice;
    }

    @Override // org.fourthline.cling.model.meta.Device
    public LocalDevice findDevice(UDN udn) {
        return find(udn, (UDN) this);
    }
}
