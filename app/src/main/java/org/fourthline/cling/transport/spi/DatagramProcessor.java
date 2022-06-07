package org.fourthline.cling.transport.spi;

import java.net.DatagramPacket;
import java.net.InetAddress;
import org.fourthline.cling.model.UnsupportedDataException;
import org.fourthline.cling.model.message.IncomingDatagramMessage;
import org.fourthline.cling.model.message.OutgoingDatagramMessage;

/* loaded from: classes5.dex */
public interface DatagramProcessor {
    IncomingDatagramMessage read(InetAddress inetAddress, DatagramPacket datagramPacket) throws UnsupportedDataException;

    DatagramPacket write(OutgoingDatagramMessage outgoingDatagramMessage) throws UnsupportedDataException;
}
