package org.fourthline.cling.registry;

import java.net.URI;
import java.util.Collection;
import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceConfiguration;
import org.fourthline.cling.model.DiscoveryOptions;
import org.fourthline.cling.model.ServiceReference;
import org.fourthline.cling.model.gena.LocalGENASubscription;
import org.fourthline.cling.model.gena.RemoteGENASubscription;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.meta.RemoteDeviceIdentity;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.resource.Resource;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDN;
import org.fourthline.cling.protocol.ProtocolFactory;

/* loaded from: classes5.dex */
public interface Registry {
    void addDevice(LocalDevice localDevice) throws RegistrationException;

    void addDevice(LocalDevice localDevice, DiscoveryOptions discoveryOptions) throws RegistrationException;

    void addDevice(RemoteDevice remoteDevice) throws RegistrationException;

    void addListener(RegistryListener registryListener);

    void addLocalSubscription(LocalGENASubscription localGENASubscription);

    void addRemoteSubscription(RemoteGENASubscription remoteGENASubscription);

    void addResource(Resource resource);

    void addResource(Resource resource, int i);

    void advertiseLocalDevices();

    UpnpServiceConfiguration getConfiguration();

    Device getDevice(UDN udn, boolean z);

    Collection<Device> getDevices();

    Collection<Device> getDevices(DeviceType deviceType);

    Collection<Device> getDevices(ServiceType serviceType);

    DiscoveryOptions getDiscoveryOptions(UDN udn);

    Collection<RegistryListener> getListeners();

    LocalDevice getLocalDevice(UDN udn, boolean z);

    Collection<LocalDevice> getLocalDevices();

    LocalGENASubscription getLocalSubscription(String str);

    ProtocolFactory getProtocolFactory();

    RemoteDevice getRemoteDevice(UDN udn, boolean z);

    Collection<RemoteDevice> getRemoteDevices();

    RemoteGENASubscription getRemoteSubscription(String str);

    <T extends Resource> T getResource(Class<T> cls, URI uri) throws IllegalArgumentException;

    Resource getResource(URI uri) throws IllegalArgumentException;

    Collection<Resource> getResources();

    <T extends Resource> Collection<T> getResources(Class<T> cls);

    Service getService(ServiceReference serviceReference);

    UpnpService getUpnpService();

    RemoteGENASubscription getWaitRemoteSubscription(String str);

    boolean isPaused();

    void notifyDiscoveryFailure(RemoteDevice remoteDevice, Exception exc);

    boolean notifyDiscoveryStart(RemoteDevice remoteDevice);

    void pause();

    void registerPendingRemoteSubscription(RemoteGENASubscription remoteGENASubscription);

    void removeAllLocalDevices();

    void removeAllRemoteDevices();

    boolean removeDevice(LocalDevice localDevice);

    boolean removeDevice(RemoteDevice remoteDevice);

    boolean removeDevice(UDN udn);

    void removeListener(RegistryListener registryListener);

    boolean removeLocalSubscription(LocalGENASubscription localGENASubscription);

    void removeRemoteSubscription(RemoteGENASubscription remoteGENASubscription);

    boolean removeResource(Resource resource);

    void resume();

    void setDiscoveryOptions(UDN udn, DiscoveryOptions discoveryOptions);

    void shutdown();

    void unregisterPendingRemoteSubscription(RemoteGENASubscription remoteGENASubscription);

    boolean update(RemoteDeviceIdentity remoteDeviceIdentity);

    boolean updateLocalSubscription(LocalGENASubscription localGENASubscription);

    void updateRemoteSubscription(RemoteGENASubscription remoteGENASubscription);
}
