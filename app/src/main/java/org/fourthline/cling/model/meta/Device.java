package org.fourthline.cling.model.meta;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.fourthline.cling.model.Namespace;
import org.fourthline.cling.model.Validatable;
import org.fourthline.cling.model.ValidationError;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.DeviceIdentity;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.profile.RemoteClientInfo;
import org.fourthline.cling.model.resource.Resource;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.ServiceId;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDN;

/* loaded from: classes5.dex */
public abstract class Device<DI extends DeviceIdentity, D extends Device, S extends Service> implements Validatable {
    private static final Logger log = Logger.getLogger(Device.class.getName());
    private final DeviceDetails details;
    protected final D[] embeddedDevices;
    private final Icon[] icons;
    private final DI identity;
    private D parentDevice;
    protected final S[] services;
    private final DeviceType type;
    private final UDAVersion version;

    public abstract Resource[] discoverResources(Namespace namespace);

    public abstract D findDevice(UDN udn);

    public abstract D[] getEmbeddedDevices();

    public abstract D getRoot();

    public abstract S[] getServices();

    public abstract D newInstance(UDN udn, UDAVersion uDAVersion, DeviceType deviceType, DeviceDetails deviceDetails, Icon[] iconArr, S[] sArr, List<D> list) throws ValidationException;

    public abstract S newInstance(ServiceType serviceType, ServiceId serviceId, URI uri, URI uri2, URI uri3, Action<S>[] actionArr, StateVariable<S>[] stateVariableArr) throws ValidationException;

    public abstract S[] newServiceArray(int i);

    public abstract D[] toDeviceArray(Collection<D> collection);

    public abstract S[] toServiceArray(Collection<S> collection);

    public Device(DI di) throws ValidationException {
        this(di, null, null, null, null, null);
    }

    public Device(DI di, DeviceType deviceType, DeviceDetails deviceDetails, Icon[] iconArr, S[] sArr) throws ValidationException {
        this(di, null, deviceType, deviceDetails, iconArr, sArr, null);
    }

    public Device(DI di, DeviceType deviceType, DeviceDetails deviceDetails, Icon[] iconArr, S[] sArr, D[] dArr) throws ValidationException {
        this(di, null, deviceType, deviceDetails, iconArr, sArr, dArr);
    }

    public Device(DI di, UDAVersion uDAVersion, DeviceType deviceType, DeviceDetails deviceDetails, Icon[] iconArr, S[] sArr, D[] dArr) throws ValidationException {
        boolean z;
        this.identity = di;
        this.version = uDAVersion == null ? new UDAVersion() : uDAVersion;
        this.type = deviceType;
        this.details = deviceDetails;
        ArrayList arrayList = new ArrayList();
        if (iconArr != null) {
            for (Icon icon : iconArr) {
                if (icon != null) {
                    icon.setDevice(this);
                    List<ValidationError> validate = icon.validate();
                    if (validate.isEmpty()) {
                        arrayList.add(icon);
                    } else {
                        log.warning("Discarding invalid '" + icon + "': " + validate);
                    }
                }
            }
        }
        this.icons = (Icon[]) arrayList.toArray(new Icon[arrayList.size()]);
        boolean z2 = true;
        if (sArr != null) {
            z = true;
            for (S s : sArr) {
                if (s != null) {
                    s.setDevice(this);
                    z = false;
                }
            }
        } else {
            z = true;
        }
        D[] dArr2 = null;
        this.services = (sArr == null || z) ? null : sArr;
        if (dArr != null) {
            boolean z3 = true;
            for (D d : dArr) {
                if (d != null) {
                    d.setParentDevice(this);
                    z3 = false;
                }
            }
            z2 = z3;
        }
        if (dArr != null && !z2) {
            dArr2 = dArr;
        }
        this.embeddedDevices = dArr2;
        List<ValidationError> validate2 = validate();
        if (validate2.size() > 0) {
            if (log.isLoggable(Level.FINEST)) {
                for (ValidationError validationError : validate2) {
                    log.finest(validationError.toString());
                }
            }
            throw new ValidationException("Validation of device graph failed, call getErrors() on exception", validate2);
        }
    }

    public DI getIdentity() {
        return this.identity;
    }

    public UDAVersion getVersion() {
        return this.version;
    }

    public DeviceType getType() {
        return this.type;
    }

    public DeviceDetails getDetails() {
        return this.details;
    }

    public DeviceDetails getDetails(RemoteClientInfo remoteClientInfo) {
        return getDetails();
    }

    public Icon[] getIcons() {
        return this.icons;
    }

    public boolean hasIcons() {
        return getIcons() != null && getIcons().length > 0;
    }

    public boolean hasServices() {
        return getServices() != null && getServices().length > 0;
    }

    public boolean hasEmbeddedDevices() {
        return getEmbeddedDevices() != null && getEmbeddedDevices().length > 0;
    }

    public D getParentDevice() {
        return this.parentDevice;
    }

    void setParentDevice(D d) {
        if (this.parentDevice == null) {
            this.parentDevice = d;
            return;
        }
        throw new IllegalStateException("Final value has been set already, model is immutable");
    }

    public boolean isRoot() {
        return getParentDevice() == null;
    }

    public D[] findEmbeddedDevices() {
        return toDeviceArray(findEmbeddedDevices(this));
    }

    public D[] findDevices(DeviceType deviceType) {
        return toDeviceArray(find(deviceType, (DeviceType) this));
    }

    public D[] findDevices(ServiceType serviceType) {
        return toDeviceArray(find(serviceType, (ServiceType) this));
    }

    public Icon[] findIcons() {
        ArrayList arrayList = new ArrayList();
        if (hasIcons()) {
            arrayList.addAll(Arrays.asList(getIcons()));
        }
        D[] findEmbeddedDevices = findEmbeddedDevices();
        for (D d : findEmbeddedDevices) {
            if (d.hasIcons()) {
                arrayList.addAll(Arrays.asList(d.getIcons()));
            }
        }
        return (Icon[]) arrayList.toArray(new Icon[arrayList.size()]);
    }

    public S[] findServices() {
        return toServiceArray(findServices(null, null, this));
    }

    public S[] findServices(ServiceType serviceType) {
        return toServiceArray(findServices(serviceType, null, this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected D find(UDN udn, D d) {
        if (!(d.getIdentity() == null || d.getIdentity().getUdn() == null || !d.getIdentity().getUdn().equals(udn))) {
            return d;
        }
        if (!d.hasEmbeddedDevices()) {
            return null;
        }
        for (Device device : d.getEmbeddedDevices()) {
            D d2 = (D) find(udn, (UDN) device);
            if (d2 != null) {
                return d2;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected Collection<D> findEmbeddedDevices(D d) {
        HashSet hashSet = new HashSet();
        if (!d.isRoot() && d.getIdentity().getUdn() != null) {
            hashSet.add(d);
        }
        if (d.hasEmbeddedDevices()) {
            for (Device device : d.getEmbeddedDevices()) {
                hashSet.addAll(findEmbeddedDevices(device));
            }
        }
        return hashSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected Collection<D> find(DeviceType deviceType, D d) {
        HashSet hashSet = new HashSet();
        if (d.getType() != null && d.getType().implementsVersion(deviceType)) {
            hashSet.add(d);
        }
        if (d.hasEmbeddedDevices()) {
            for (Device device : d.getEmbeddedDevices()) {
                hashSet.addAll(find(deviceType, (DeviceType) device));
            }
        }
        return hashSet;
    }

    protected Collection<D> find(ServiceType serviceType, D d) {
        Collection<S> findServices = findServices(serviceType, null, d);
        HashSet hashSet = new HashSet();
        for (S s : findServices) {
            hashSet.add(s.getDevice());
        }
        return hashSet;
    }

    protected Collection<S> findServices(ServiceType serviceType, ServiceId serviceId, D d) {
        HashSet hashSet = new HashSet();
        if (d.hasServices()) {
            Service[] services = d.getServices();
            for (Service service : services) {
                if (isMatch(service, serviceType, serviceId)) {
                    hashSet.add(service);
                }
            }
        }
        Collection<D> findEmbeddedDevices = findEmbeddedDevices(d);
        if (findEmbeddedDevices != null) {
            for (D d2 : findEmbeddedDevices) {
                if (d2.hasServices()) {
                    Service[] services2 = d2.getServices();
                    for (Service service2 : services2) {
                        if (isMatch(service2, serviceType, serviceId)) {
                            hashSet.add(service2);
                        }
                    }
                }
            }
        }
        return hashSet;
    }

    public S findService(ServiceId serviceId) {
        Collection<S> findServices = findServices(null, serviceId, this);
        if (findServices.size() == 1) {
            return findServices.iterator().next();
        }
        return null;
    }

    public S findService(ServiceType serviceType) {
        Collection<S> findServices = findServices(serviceType, null, this);
        if (findServices.size() > 0) {
            return findServices.iterator().next();
        }
        return null;
    }

    public ServiceType[] findServiceTypes() {
        Collection<S> findServices = findServices(null, null, this);
        HashSet hashSet = new HashSet();
        for (S s : findServices) {
            hashSet.add(s.getServiceType());
        }
        return (ServiceType[]) hashSet.toArray(new ServiceType[hashSet.size()]);
    }

    private boolean isMatch(Service service, ServiceType serviceType, ServiceId serviceId) {
        return (serviceType == null || service.getServiceType().implementsVersion(serviceType)) && (serviceId == null || service.getServiceId().equals(serviceId));
    }

    public boolean isFullyHydrated() {
        for (S s : findServices()) {
            if (s.hasStateVariables()) {
                return true;
            }
        }
        return false;
    }

    public String getDisplayString() {
        String str;
        String str2;
        String str3;
        String str4 = null;
        if (getDetails() == null || getDetails().getModelDetails() == null) {
            str = null;
        } else {
            ModelDetails modelDetails = getDetails().getModelDetails();
            if (modelDetails.getModelName() != null) {
                if (modelDetails.getModelNumber() == null || !modelDetails.getModelName().endsWith(modelDetails.getModelNumber())) {
                    str4 = modelDetails.getModelName();
                } else {
                    str4 = modelDetails.getModelName().substring(0, modelDetails.getModelName().length() - modelDetails.getModelNumber().length());
                }
            }
            if (str4 != null) {
                str = (modelDetails.getModelNumber() == null || str4.startsWith(modelDetails.getModelNumber())) ? "" : modelDetails.getModelNumber();
            } else {
                str = modelDetails.getModelNumber();
            }
        }
        StringBuilder sb = new StringBuilder();
        if (!(getDetails() == null || getDetails().getManufacturerDetails() == null)) {
            if (!(str4 == null || getDetails().getManufacturerDetails().getManufacturer() == null)) {
                if (str4.startsWith(getDetails().getManufacturerDetails().getManufacturer())) {
                    str4 = str4.substring(getDetails().getManufacturerDetails().getManufacturer().length()).trim();
                } else {
                    str4 = str4.trim();
                }
            }
            if (getDetails().getManufacturerDetails().getManufacturer() != null) {
                sb.append(getDetails().getManufacturerDetails().getManufacturer());
            }
        }
        if (str4 == null || str4.length() <= 0) {
            str2 = "";
        } else {
            str2 = StringUtils.SPACE + str4;
        }
        sb.append(str2);
        if (str == null || str.length() <= 0) {
            str3 = "";
        } else {
            str3 = StringUtils.SPACE + str.trim();
        }
        sb.append(str3);
        return sb.toString();
    }

    @Override // org.fourthline.cling.model.Validatable
    public List<ValidationError> validate() {
        ArrayList arrayList = new ArrayList();
        if (getType() != null) {
            arrayList.addAll(getVersion().validate());
            if (getIdentity() != null) {
                arrayList.addAll(getIdentity().validate());
            }
            if (getDetails() != null) {
                arrayList.addAll(getDetails().validate());
            }
            if (hasServices()) {
                S[] services = getServices();
                for (S s : services) {
                    if (s != null) {
                        arrayList.addAll(s.validate());
                    }
                }
            }
            if (hasEmbeddedDevices()) {
                D[] embeddedDevices = getEmbeddedDevices();
                for (D d : embeddedDevices) {
                    if (d != null) {
                        arrayList.addAll(d.validate());
                    }
                }
            }
        }
        return arrayList;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.identity.equals(((Device) obj).identity);
    }

    public int hashCode() {
        return this.identity.hashCode();
    }

    public String toString() {
        return "(" + getClass().getSimpleName() + ") Identity: " + getIdentity().toString() + ", Root: " + isRoot();
    }
}
