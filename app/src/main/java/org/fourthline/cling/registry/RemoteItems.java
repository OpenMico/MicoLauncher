package org.fourthline.cling.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fourthline.cling.model.gena.CancelReason;
import org.fourthline.cling.model.gena.RemoteGENASubscription;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.meta.RemoteDeviceIdentity;
import org.fourthline.cling.model.resource.Resource;
import org.fourthline.cling.model.types.UDN;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public class RemoteItems extends RegistryItems<RemoteDevice, RemoteGENASubscription> {
    private static Logger log = Logger.getLogger(Registry.class.getName());

    void start() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RemoteItems(RegistryImpl registryImpl) {
        super(registryImpl);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void add(final RemoteDevice remoteDevice) {
        Integer num;
        if (update(remoteDevice.getIdentity())) {
            log.fine("Ignoring addition, device already registered: " + remoteDevice);
            return;
        }
        Resource[] resources = getResources(remoteDevice);
        for (Resource resource : resources) {
            log.fine("Validating remote device resource; " + resource);
            if (this.registry.getResource(resource.getPathQuery()) != null) {
                throw new RegistrationException("URI namespace conflict with already registered resource: " + resource);
            }
        }
        for (Resource resource2 : resources) {
            this.registry.addResource(resource2);
            log.fine("Added remote device resource: " + resource2);
        }
        UDN udn = remoteDevice.getIdentity().getUdn();
        if (this.registry.getConfiguration().getRemoteDeviceMaxAgeSeconds() != null) {
            num = this.registry.getConfiguration().getRemoteDeviceMaxAgeSeconds();
        } else {
            num = remoteDevice.getIdentity().getMaxAgeSeconds();
        }
        RegistryItem<UDN, RemoteDevice> registryItem = new RegistryItem<>(udn, remoteDevice, num.intValue());
        log.fine("Adding hydrated remote device to registry with " + registryItem.getExpirationDetails().getMaxAgeSeconds() + " seconds expiration: " + remoteDevice);
        getDeviceItems().add(registryItem);
        if (log.isLoggable(Level.FINEST)) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("-------------------------- START Registry Namespace -----------------------------------\n");
            for (Resource resource3 : this.registry.getResources()) {
                sb.append(resource3);
                sb.append("\n");
            }
            sb.append("-------------------------- END Registry Namespace -----------------------------------");
            log.finest(sb.toString());
        }
        log.fine("Completely hydrated remote device graph available, calling listeners: " + remoteDevice);
        for (final RegistryListener registryListener : this.registry.getListeners()) {
            this.registry.getConfiguration().getRegistryListenerExecutor().execute(new Runnable() { // from class: org.fourthline.cling.registry.RemoteItems.1
                @Override // java.lang.Runnable
                public void run() {
                    registryListener.remoteDeviceAdded(RemoteItems.this.registry, remoteDevice);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean update(RemoteDeviceIdentity remoteDeviceIdentity) {
        Integer num;
        for (LocalDevice localDevice : this.registry.getLocalDevices()) {
            if (localDevice.findDevice(remoteDeviceIdentity.getUdn()) != null) {
                log.fine("Ignoring update, a local device graph contains UDN");
                return true;
            }
        }
        RemoteDevice remoteDevice = get(remoteDeviceIdentity.getUdn(), false);
        if (remoteDevice == null) {
            return false;
        }
        if (!remoteDevice.isRoot()) {
            Logger logger = log;
            logger.fine("Updating root device of embedded: " + remoteDevice);
            remoteDevice = remoteDevice.getRoot();
        }
        UDN udn = remoteDevice.getIdentity().getUdn();
        if (this.registry.getConfiguration().getRemoteDeviceMaxAgeSeconds() != null) {
            num = this.registry.getConfiguration().getRemoteDeviceMaxAgeSeconds();
        } else {
            num = remoteDeviceIdentity.getMaxAgeSeconds();
        }
        final RegistryItem<UDN, RemoteDevice> registryItem = new RegistryItem<>(udn, remoteDevice, num.intValue());
        Logger logger2 = log;
        logger2.fine("Updating expiration of: " + remoteDevice);
        getDeviceItems().remove(registryItem);
        getDeviceItems().add(registryItem);
        Logger logger3 = log;
        logger3.fine("Remote device updated, calling listeners: " + remoteDevice);
        for (final RegistryListener registryListener : this.registry.getListeners()) {
            this.registry.getConfiguration().getRegistryListenerExecutor().execute(new Runnable() { // from class: org.fourthline.cling.registry.RemoteItems.2
                @Override // java.lang.Runnable
                public void run() {
                    registryListener.remoteDeviceUpdated(RemoteItems.this.registry, (RemoteDevice) registryItem.getItem());
                }
            });
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean remove(RemoteDevice remoteDevice) {
        return remove(remoteDevice, false);
    }

    boolean remove(RemoteDevice remoteDevice, boolean z) throws RegistrationException {
        final RemoteDevice remoteDevice2 = (RemoteDevice) get(remoteDevice.getIdentity().getUdn(), true);
        if (remoteDevice2 == null) {
            return false;
        }
        log.fine("Removing remote device from registry: " + remoteDevice);
        Resource[] resources = getResources(remoteDevice2);
        for (Resource resource : resources) {
            if (this.registry.removeResource(resource)) {
                log.fine("Unregistered resource: " + resource);
            }
        }
        Iterator it = getSubscriptionItems().iterator();
        while (it.hasNext()) {
            final RegistryItem registryItem = (RegistryItem) it.next();
            if (((RemoteGENASubscription) registryItem.getItem()).getService().getDevice().getIdentity().getUdn().equals(remoteDevice2.getIdentity().getUdn())) {
                log.fine("Removing outgoing subscription: " + ((String) registryItem.getKey()));
                it.remove();
                if (!z) {
                    this.registry.getConfiguration().getRegistryListenerExecutor().execute(new Runnable() { // from class: org.fourthline.cling.registry.RemoteItems.3
                        @Override // java.lang.Runnable
                        public void run() {
                            ((RemoteGENASubscription) registryItem.getItem()).end(CancelReason.DEVICE_WAS_REMOVED, null);
                        }
                    });
                }
            }
        }
        if (!z) {
            for (final RegistryListener registryListener : this.registry.getListeners()) {
                this.registry.getConfiguration().getRegistryListenerExecutor().execute(new Runnable() { // from class: org.fourthline.cling.registry.RemoteItems.4
                    @Override // java.lang.Runnable
                    public void run() {
                        registryListener.remoteDeviceRemoved(RemoteItems.this.registry, remoteDevice2);
                    }
                });
            }
        }
        getDeviceItems().remove(new RegistryItem(remoteDevice2.getIdentity().getUdn()));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.fourthline.cling.registry.RegistryItems
    public void removeAll() {
        removeAll(false);
    }

    void removeAll(boolean z) {
        for (RemoteDevice remoteDevice : (RemoteDevice[]) get().toArray(new RemoteDevice[get().size()])) {
            remove(remoteDevice, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.fourthline.cling.registry.RegistryItems
    public void maintain() {
        if (!getDeviceItems().isEmpty()) {
            HashMap hashMap = new HashMap();
            for (RegistryItem<UDN, RemoteDevice> registryItem : getDeviceItems()) {
                if (log.isLoggable(Level.FINEST)) {
                    Logger logger = log;
                    logger.finest("Device '" + registryItem.getItem() + "' expires in seconds: " + registryItem.getExpirationDetails().getSecondsUntilExpiration());
                }
                if (registryItem.getExpirationDetails().hasExpired(false)) {
                    hashMap.put(registryItem.getKey(), registryItem.getItem());
                }
            }
            for (RemoteDevice remoteDevice : hashMap.values()) {
                if (log.isLoggable(Level.FINE)) {
                    Logger logger2 = log;
                    logger2.fine("Removing expired: " + remoteDevice);
                }
                remove(remoteDevice);
            }
            HashSet<RemoteGENASubscription> hashSet = new HashSet();
            for (RegistryItem<String, RemoteGENASubscription> registryItem2 : getSubscriptionItems()) {
                if (registryItem2.getExpirationDetails().hasExpired(true)) {
                    hashSet.add(registryItem2.getItem());
                }
            }
            for (RemoteGENASubscription remoteGENASubscription : hashSet) {
                if (log.isLoggable(Level.FINEST)) {
                    Logger logger3 = log;
                    logger3.fine("Renewing outgoing subscription: " + remoteGENASubscription);
                }
                renewOutgoingSubscription(remoteGENASubscription);
            }
        }
    }

    public void resume() {
        log.fine("Updating remote device expiration timestamps on resume");
        ArrayList<RemoteDeviceIdentity> arrayList = new ArrayList();
        for (RegistryItem<UDN, RemoteDevice> registryItem : getDeviceItems()) {
            arrayList.add(registryItem.getItem().getIdentity());
        }
        for (RemoteDeviceIdentity remoteDeviceIdentity : arrayList) {
            update(remoteDeviceIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.fourthline.cling.registry.RegistryItems
    public void shutdown() {
        log.fine("Cancelling all outgoing subscriptions to remote devices during shutdown");
        ArrayList<RemoteGENASubscription> arrayList = new ArrayList();
        for (RegistryItem<String, RemoteGENASubscription> registryItem : getSubscriptionItems()) {
            arrayList.add(registryItem.getItem());
        }
        for (RemoteGENASubscription remoteGENASubscription : arrayList) {
            this.registry.getProtocolFactory().createSendingUnsubscribe(remoteGENASubscription).run();
        }
        log.fine("Removing all remote devices from registry during shutdown");
        removeAll(true);
    }

    protected void renewOutgoingSubscription(RemoteGENASubscription remoteGENASubscription) {
        this.registry.executeAsyncProtocol(this.registry.getProtocolFactory().createSendingRenewal(remoteGENASubscription));
    }
}
