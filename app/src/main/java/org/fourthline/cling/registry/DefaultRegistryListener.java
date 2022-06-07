package org.fourthline.cling.registry;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;

/* loaded from: classes5.dex */
public class DefaultRegistryListener implements RegistryListener {
    @Override // org.fourthline.cling.registry.RegistryListener
    public void afterShutdown() {
    }

    @Override // org.fourthline.cling.registry.RegistryListener
    public void beforeShutdown(Registry registry) {
    }

    public void deviceAdded(Registry registry, Device device) {
    }

    public void deviceRemoved(Registry registry, Device device) {
    }

    @Override // org.fourthline.cling.registry.RegistryListener
    public void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice remoteDevice, Exception exc) {
    }

    @Override // org.fourthline.cling.registry.RegistryListener
    public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice remoteDevice) {
    }

    @Override // org.fourthline.cling.registry.RegistryListener
    public void remoteDeviceUpdated(Registry registry, RemoteDevice remoteDevice) {
    }

    @Override // org.fourthline.cling.registry.RegistryListener
    public void remoteDeviceAdded(Registry registry, RemoteDevice remoteDevice) {
        deviceAdded(registry, remoteDevice);
    }

    @Override // org.fourthline.cling.registry.RegistryListener
    public void remoteDeviceRemoved(Registry registry, RemoteDevice remoteDevice) {
        deviceRemoved(registry, remoteDevice);
    }

    @Override // org.fourthline.cling.registry.RegistryListener
    public void localDeviceAdded(Registry registry, LocalDevice localDevice) {
        deviceAdded(registry, localDevice);
    }

    @Override // org.fourthline.cling.registry.RegistryListener
    public void localDeviceRemoved(Registry registry, LocalDevice localDevice) {
        deviceRemoved(registry, localDevice);
    }
}
