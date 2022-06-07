package org.fourthline.cling.transport.spi;

import java.net.DatagramPacket;
import java.net.InetAddress;
import org.fourthline.cling.model.message.OutgoingDatagramMessage;
import org.fourthline.cling.transport.Router;
import org.fourthline.cling.transport.spi.DatagramIOConfiguration;

/* loaded from: classes5.dex */
public interface DatagramIO<C extends DatagramIOConfiguration> extends Runnable {
    C getConfiguration();

    void init(InetAddress inetAddress, Router router, DatagramProcessor datagramProcessor) throws InitializationException;

    void send(DatagramPacket datagramPacket);

    void send(OutgoingDatagramMessage outgoingDatagramMessage);

    void stop();
}
