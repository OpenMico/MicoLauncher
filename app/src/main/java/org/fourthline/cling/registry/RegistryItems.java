package org.fourthline.cling.registry;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.resource.Resource;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDN;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public abstract class RegistryItems<D extends Device, S extends GENASubscription> {
    protected final RegistryImpl registry;
    protected final Set<RegistryItem<UDN, D>> deviceItems = new HashSet();
    protected final Set<RegistryItem<String, S>> subscriptionItems = new HashSet();

    abstract void add(D d);

    abstract void maintain();

    abstract boolean remove(D d);

    abstract void removeAll();

    abstract void shutdown();

    /* JADX INFO: Access modifiers changed from: package-private */
    public RegistryItems(RegistryImpl registryImpl) {
        this.registry = registryImpl;
    }

    Set<RegistryItem<UDN, D>> getDeviceItems() {
        return this.deviceItems;
    }

    Set<RegistryItem<String, S>> getSubscriptionItems() {
        return this.subscriptionItems;
    }

    D get(UDN udn, boolean z) {
        D d;
        for (RegistryItem<UDN, D> registryItem : this.deviceItems) {
            D item = registryItem.getItem();
            if (item.getIdentity().getUdn().equals(udn)) {
                return item;
            }
            if (!(z || (d = (D) registryItem.getItem().findDevice(udn)) == null)) {
                return d;
            }
        }
        return null;
    }

    Collection<D> get(DeviceType deviceType) {
        HashSet hashSet = new HashSet();
        for (RegistryItem<UDN, D> registryItem : this.deviceItems) {
            Device[] findDevices = registryItem.getItem().findDevices(deviceType);
            if (findDevices != null) {
                hashSet.addAll(Arrays.asList(findDevices));
            }
        }
        return hashSet;
    }

    Collection<D> get(ServiceType serviceType) {
        HashSet hashSet = new HashSet();
        for (RegistryItem<UDN, D> registryItem : this.deviceItems) {
            Device[] findDevices = registryItem.getItem().findDevices(serviceType);
            if (findDevices != null) {
                hashSet.addAll(Arrays.asList(findDevices));
            }
        }
        return hashSet;
    }

    Collection<D> get() {
        HashSet hashSet = new HashSet();
        for (RegistryItem<UDN, D> registryItem : this.deviceItems) {
            hashSet.add(registryItem.getItem());
        }
        return hashSet;
    }

    boolean contains(D d) {
        return contains(d.getIdentity().getUdn());
    }

    boolean contains(UDN udn) {
        return this.deviceItems.contains(new RegistryItem(udn));
    }

    void addSubscription(S s) {
        this.subscriptionItems.add(new RegistryItem<>(s.getSubscriptionId(), s, s.getActualDurationSeconds()));
    }

    boolean updateSubscription(S s) {
        if (!removeSubscription(s)) {
            return false;
        }
        addSubscription(s);
        return true;
    }

    boolean removeSubscription(S s) {
        return this.subscriptionItems.remove(new RegistryItem(s.getSubscriptionId()));
    }

    S getSubscription(String str) {
        for (RegistryItem<String, S> registryItem : this.subscriptionItems) {
            if (registryItem.getKey().equals(str)) {
                return registryItem.getItem();
            }
        }
        return null;
    }

    Resource[] getResources(Device device) throws RegistrationException {
        try {
            return this.registry.getConfiguration().getNamespace().getResources(device);
        } catch (ValidationException e) {
            throw new RegistrationException("Resource discover error: " + e.toString(), e);
        }
    }
}
