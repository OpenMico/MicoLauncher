package org.fourthline.cling.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;
import org.fourthline.cling.model.DiscoveryOptions;
import org.fourthline.cling.model.gena.CancelReason;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.gena.LocalGENASubscription;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.resource.Resource;
import org.fourthline.cling.model.types.UDN;
import org.fourthline.cling.protocol.async.SendingNotificationByebye;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public class LocalItems extends RegistryItems<LocalDevice, LocalGENASubscription> {
    private static Logger log = Logger.getLogger(Registry.class.getName());
    protected Map<UDN, DiscoveryOptions> discoveryOptions = new HashMap();
    protected long lastAliveIntervalTimestamp = 0;
    protected Random randomGenerator = new Random();

    /* JADX INFO: Access modifiers changed from: package-private */
    public LocalItems(RegistryImpl registryImpl) {
        super(registryImpl);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setDiscoveryOptions(UDN udn, DiscoveryOptions discoveryOptions) {
        if (discoveryOptions != null) {
            this.discoveryOptions.put(udn, discoveryOptions);
        } else {
            this.discoveryOptions.remove(udn);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public DiscoveryOptions getDiscoveryOptions(UDN udn) {
        return this.discoveryOptions.get(udn);
    }

    protected boolean isAdvertised(UDN udn) {
        return getDiscoveryOptions(udn) == null || getDiscoveryOptions(udn).isAdvertised();
    }

    protected boolean isByeByeBeforeFirstAlive(UDN udn) {
        return getDiscoveryOptions(udn) != null && getDiscoveryOptions(udn).isByeByeBeforeFirstAlive();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void add(LocalDevice localDevice) throws RegistrationException {
        add(localDevice, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void add(final LocalDevice localDevice, DiscoveryOptions discoveryOptions) throws RegistrationException {
        setDiscoveryOptions(localDevice.getIdentity().getUdn(), discoveryOptions);
        if (this.registry.getDevice(localDevice.getIdentity().getUdn(), false) != null) {
            log.fine("Ignoring addition, device already registered: " + localDevice);
            return;
        }
        log.fine("Adding local device to registry: " + localDevice);
        Resource[] resources = getResources(localDevice);
        for (Resource resource : resources) {
            if (this.registry.getResource(resource.getPathQuery()) == null) {
                this.registry.addResource(resource);
                log.fine("Registered resource: " + resource);
            } else {
                throw new RegistrationException("URI namespace conflict with already registered resource: " + resource);
            }
        }
        log.fine("Adding item to registry with expiration in seconds: " + localDevice.getIdentity().getMaxAgeSeconds());
        RegistryItem<UDN, LocalDevice> registryItem = new RegistryItem<>(localDevice.getIdentity().getUdn(), localDevice, localDevice.getIdentity().getMaxAgeSeconds().intValue());
        getDeviceItems().add(registryItem);
        log.fine("Registered local device: " + registryItem);
        if (isByeByeBeforeFirstAlive(registryItem.getKey())) {
            advertiseByebye(localDevice, true);
        }
        if (isAdvertised(registryItem.getKey())) {
            advertiseAlive(localDevice);
        }
        for (final RegistryListener registryListener : this.registry.getListeners()) {
            this.registry.getConfiguration().getRegistryListenerExecutor().execute(new Runnable() { // from class: org.fourthline.cling.registry.LocalItems.1
                @Override // java.lang.Runnable
                public void run() {
                    registryListener.localDeviceAdded(LocalItems.this.registry, localDevice);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.fourthline.cling.registry.RegistryItems
    public Collection<LocalDevice> get() {
        HashSet hashSet = new HashSet();
        for (RegistryItem<UDN, LocalDevice> registryItem : getDeviceItems()) {
            hashSet.add(registryItem.getItem());
        }
        return Collections.unmodifiableCollection(hashSet);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean remove(LocalDevice localDevice) throws RegistrationException {
        return remove(localDevice, false);
    }

    boolean remove(final LocalDevice localDevice, boolean z) throws RegistrationException {
        LocalDevice localDevice2 = get(localDevice.getIdentity().getUdn(), true);
        if (localDevice2 == null) {
            return false;
        }
        log.fine("Removing local device from registry: " + localDevice);
        setDiscoveryOptions(localDevice.getIdentity().getUdn(), null);
        getDeviceItems().remove(new RegistryItem(localDevice.getIdentity().getUdn()));
        Resource[] resources = getResources(localDevice);
        for (Resource resource : resources) {
            if (this.registry.removeResource(resource)) {
                log.fine("Unregistered resource: " + resource);
            }
        }
        Iterator<RegistryItem<String, LocalGENASubscription>> it = getSubscriptionItems().iterator();
        while (it.hasNext()) {
            final RegistryItem<String, LocalGENASubscription> next = it.next();
            if (next.getItem().getService().getDevice().getIdentity().getUdn().equals(localDevice2.getIdentity().getUdn())) {
                log.fine("Removing incoming subscription: " + next.getKey());
                it.remove();
                if (!z) {
                    this.registry.getConfiguration().getRegistryListenerExecutor().execute(new Runnable() { // from class: org.fourthline.cling.registry.LocalItems.2
                        @Override // java.lang.Runnable
                        public void run() {
                            ((LocalGENASubscription) next.getItem()).end(CancelReason.DEVICE_WAS_REMOVED);
                        }
                    });
                }
            }
        }
        if (isAdvertised(localDevice.getIdentity().getUdn())) {
            advertiseByebye(localDevice, !z);
        }
        if (!z) {
            for (final RegistryListener registryListener : this.registry.getListeners()) {
                this.registry.getConfiguration().getRegistryListenerExecutor().execute(new Runnable() { // from class: org.fourthline.cling.registry.LocalItems.3
                    @Override // java.lang.Runnable
                    public void run() {
                        registryListener.localDeviceRemoved(LocalItems.this.registry, localDevice);
                    }
                });
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.fourthline.cling.registry.RegistryItems
    public void removeAll() {
        removeAll(false);
    }

    void removeAll(boolean z) {
        for (LocalDevice localDevice : (LocalDevice[]) get().toArray(new LocalDevice[get().size()])) {
            remove(localDevice, z);
        }
    }

    public void advertiseLocalDevices() {
        for (RegistryItem registryItem : this.deviceItems) {
            if (isAdvertised((UDN) registryItem.getKey())) {
                advertiseAlive((LocalDevice) registryItem.getItem());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.fourthline.cling.registry.RegistryItems
    public void maintain() {
        if (!getDeviceItems().isEmpty()) {
            HashSet<RegistryItem> hashSet = new HashSet();
            int aliveIntervalMillis = this.registry.getConfiguration().getAliveIntervalMillis();
            if (aliveIntervalMillis > 0) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - this.lastAliveIntervalTimestamp > aliveIntervalMillis) {
                    this.lastAliveIntervalTimestamp = currentTimeMillis;
                    for (RegistryItem<UDN, LocalDevice> registryItem : getDeviceItems()) {
                        if (isAdvertised(registryItem.getKey())) {
                            Logger logger = log;
                            logger.finer("Flooding advertisement of local item: " + registryItem);
                            hashSet.add(registryItem);
                        }
                    }
                }
            } else {
                this.lastAliveIntervalTimestamp = 0L;
                for (RegistryItem<UDN, LocalDevice> registryItem2 : getDeviceItems()) {
                    if (isAdvertised(registryItem2.getKey()) && registryItem2.getExpirationDetails().hasExpired(true)) {
                        Logger logger2 = log;
                        logger2.finer("Local item has expired: " + registryItem2);
                        hashSet.add(registryItem2);
                    }
                }
            }
            for (RegistryItem registryItem3 : hashSet) {
                Logger logger3 = log;
                logger3.fine("Refreshing local device advertisement: " + registryItem3.getItem());
                advertiseAlive((LocalDevice) registryItem3.getItem());
                registryItem3.getExpirationDetails().stampLastRefresh();
            }
            HashSet<RegistryItem> hashSet2 = new HashSet();
            for (RegistryItem<String, LocalGENASubscription> registryItem4 : getSubscriptionItems()) {
                if (registryItem4.getExpirationDetails().hasExpired(false)) {
                    hashSet2.add(registryItem4);
                }
            }
            for (RegistryItem registryItem5 : hashSet2) {
                Logger logger4 = log;
                logger4.fine("Removing expired: " + registryItem5);
                removeSubscription((GENASubscription) registryItem5.getItem());
                ((LocalGENASubscription) registryItem5.getItem()).end(CancelReason.EXPIRED);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.fourthline.cling.registry.RegistryItems
    public void shutdown() {
        log.fine("Clearing all registered subscriptions to local devices during shutdown");
        getSubscriptionItems().clear();
        log.fine("Removing all local devices from registry during shutdown");
        removeAll(true);
    }

    protected void advertiseAlive(final LocalDevice localDevice) {
        this.registry.executeAsyncProtocol(new Runnable() { // from class: org.fourthline.cling.registry.LocalItems.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    LocalItems.log.finer("Sleeping some milliseconds to avoid flooding the network with ALIVE msgs");
                    Thread.sleep(LocalItems.this.randomGenerator.nextInt(100));
                } catch (InterruptedException e) {
                    Logger logger = LocalItems.log;
                    logger.severe("Background execution interrupted: " + e.getMessage());
                }
                LocalItems.this.registry.getProtocolFactory().createSendingNotificationAlive(localDevice).run();
            }
        });
    }

    protected void advertiseByebye(LocalDevice localDevice, boolean z) {
        SendingNotificationByebye createSendingNotificationByebye = this.registry.getProtocolFactory().createSendingNotificationByebye(localDevice);
        if (z) {
            this.registry.executeAsyncProtocol(createSendingNotificationByebye);
        } else {
            createSendingNotificationByebye.run();
        }
    }
}
