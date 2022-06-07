package org.fourthline.cling.transport.spi;

import java.net.NetworkInterface;
import org.fourthline.cling.transport.Router;
import org.fourthline.cling.transport.spi.MulticastReceiverConfiguration;

/* loaded from: classes5.dex */
public interface MulticastReceiver<C extends MulticastReceiverConfiguration> extends Runnable {
    C getConfiguration();

    void init(NetworkInterface networkInterface, Router router, NetworkAddressFactory networkAddressFactory, DatagramProcessor datagramProcessor) throws InitializationException;

    void stop();
}
