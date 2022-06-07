package org.fourthline.cling.transport;

import java.net.InetAddress;
import java.util.List;
import org.fourthline.cling.UpnpServiceConfiguration;
import org.fourthline.cling.model.NetworkAddress;
import org.fourthline.cling.model.message.IncomingDatagramMessage;
import org.fourthline.cling.model.message.OutgoingDatagramMessage;
import org.fourthline.cling.model.message.StreamRequestMessage;
import org.fourthline.cling.model.message.StreamResponseMessage;
import org.fourthline.cling.protocol.ProtocolFactory;
import org.fourthline.cling.transport.spi.InitializationException;
import org.fourthline.cling.transport.spi.UpnpStream;

/* loaded from: classes5.dex */
public interface Router {
    void broadcast(byte[] bArr) throws RouterException;

    boolean disable() throws RouterException;

    boolean enable() throws RouterException;

    List<NetworkAddress> getActiveStreamServers(InetAddress inetAddress) throws RouterException;

    UpnpServiceConfiguration getConfiguration();

    ProtocolFactory getProtocolFactory();

    void handleStartFailure(InitializationException initializationException) throws InitializationException;

    boolean isEnabled() throws RouterException;

    void received(IncomingDatagramMessage incomingDatagramMessage);

    void received(UpnpStream upnpStream);

    StreamResponseMessage send(StreamRequestMessage streamRequestMessage) throws RouterException;

    void send(OutgoingDatagramMessage outgoingDatagramMessage) throws RouterException;

    void shutdown() throws RouterException;
}
