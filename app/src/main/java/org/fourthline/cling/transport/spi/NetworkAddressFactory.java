package org.fourthline.cling.transport.spi;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Iterator;

/* loaded from: classes5.dex */
public interface NetworkAddressFactory {
    public static final String SYSTEM_PROPERTY_NET_ADDRESSES = "org.fourthline.cling.network.useAddresses";
    public static final String SYSTEM_PROPERTY_NET_IFACES = "org.fourthline.cling.network.useInterfaces";

    Short getAddressNetworkPrefixLength(InetAddress inetAddress);

    Iterator<InetAddress> getBindAddresses();

    InetAddress getBroadcastAddress(InetAddress inetAddress);

    byte[] getHardwareAddress(InetAddress inetAddress);

    InetAddress getLocalAddress(NetworkInterface networkInterface, boolean z, InetAddress inetAddress) throws IllegalStateException;

    InetAddress getMulticastGroup();

    int getMulticastPort();

    Iterator<NetworkInterface> getNetworkInterfaces();

    int getStreamListenPort();

    boolean hasUsableNetwork();

    void logInterfaceInformation();
}
