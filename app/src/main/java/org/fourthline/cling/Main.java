package org.fourthline.cling;

import java.io.PrintStream;
import org.fourthline.cling.model.message.header.STAllHeader;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

/* loaded from: classes5.dex */
public class Main {
    public static void main(String[] strArr) throws Exception {
        RegistryListener registryListener = new RegistryListener() { // from class: org.fourthline.cling.Main.1
            @Override // org.fourthline.cling.registry.RegistryListener
            public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice remoteDevice) {
                PrintStream printStream = System.out;
                printStream.println("Discovery started: " + remoteDevice.getDisplayString());
            }

            @Override // org.fourthline.cling.registry.RegistryListener
            public void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice remoteDevice, Exception exc) {
                PrintStream printStream = System.out;
                printStream.println("Discovery failed: " + remoteDevice.getDisplayString() + " => " + exc);
            }

            @Override // org.fourthline.cling.registry.RegistryListener
            public void remoteDeviceAdded(Registry registry, RemoteDevice remoteDevice) {
                PrintStream printStream = System.out;
                printStream.println("Remote device available: " + remoteDevice.getDisplayString());
            }

            @Override // org.fourthline.cling.registry.RegistryListener
            public void remoteDeviceUpdated(Registry registry, RemoteDevice remoteDevice) {
                PrintStream printStream = System.out;
                printStream.println("Remote device updated: " + remoteDevice.getDisplayString());
            }

            @Override // org.fourthline.cling.registry.RegistryListener
            public void remoteDeviceRemoved(Registry registry, RemoteDevice remoteDevice) {
                PrintStream printStream = System.out;
                printStream.println("Remote device removed: " + remoteDevice.getDisplayString());
            }

            @Override // org.fourthline.cling.registry.RegistryListener
            public void localDeviceAdded(Registry registry, LocalDevice localDevice) {
                PrintStream printStream = System.out;
                printStream.println("Local device added: " + localDevice.getDisplayString());
            }

            @Override // org.fourthline.cling.registry.RegistryListener
            public void localDeviceRemoved(Registry registry, LocalDevice localDevice) {
                PrintStream printStream = System.out;
                printStream.println("Local device removed: " + localDevice.getDisplayString());
            }

            @Override // org.fourthline.cling.registry.RegistryListener
            public void beforeShutdown(Registry registry) {
                PrintStream printStream = System.out;
                printStream.println("Before shutdown, the registry has devices: " + registry.getDevices().size());
            }

            @Override // org.fourthline.cling.registry.RegistryListener
            public void afterShutdown() {
                System.out.println("Shutdown of registry complete!");
            }
        };
        System.out.println("Starting Cling...");
        UpnpServiceImpl upnpServiceImpl = new UpnpServiceImpl(registryListener);
        System.out.println("Sending SEARCH message to all devices...");
        upnpServiceImpl.getControlPoint().search(new STAllHeader());
        System.out.println("Waiting 10 seconds before shutting down...");
        Thread.sleep(10000L);
        System.out.println("Stopping Cling...");
        upnpServiceImpl.shutdown();
    }
}
