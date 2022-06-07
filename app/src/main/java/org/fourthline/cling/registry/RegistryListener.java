package org.fourthline.cling.registry;

import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;

/* loaded from: classes5.dex */
public interface RegistryListener {
    void afterShutdown();

    void beforeShutdown(Registry registry);

    void localDeviceAdded(Registry registry, LocalDevice localDevice);

    void localDeviceRemoved(Registry registry, LocalDevice localDevice);

    void remoteDeviceAdded(Registry registry, RemoteDevice remoteDevice);

    void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice remoteDevice, Exception exc);

    void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice remoteDevice);

    void remoteDeviceRemoved(Registry registry, RemoteDevice remoteDevice);

    void remoteDeviceUpdated(Registry registry, RemoteDevice remoteDevice);
}
