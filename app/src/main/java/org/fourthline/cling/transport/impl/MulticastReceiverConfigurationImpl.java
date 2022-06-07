package org.fourthline.cling.transport.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.fourthline.cling.transport.spi.MulticastReceiverConfiguration;

/* loaded from: classes5.dex */
public class MulticastReceiverConfigurationImpl implements MulticastReceiverConfiguration {
    private InetAddress group;
    private int maxDatagramBytes;
    private int port;

    public MulticastReceiverConfigurationImpl(InetAddress inetAddress, int i, int i2) {
        this.group = inetAddress;
        this.port = i;
        this.maxDatagramBytes = i2;
    }

    public MulticastReceiverConfigurationImpl(InetAddress inetAddress, int i) {
        this(inetAddress, i, 640);
    }

    public MulticastReceiverConfigurationImpl(String str, int i, int i2) throws UnknownHostException {
        this(InetAddress.getByName(str), i, i2);
    }

    public MulticastReceiverConfigurationImpl(String str, int i) throws UnknownHostException {
        this(InetAddress.getByName(str), i, 640);
    }

    @Override // org.fourthline.cling.transport.spi.MulticastReceiverConfiguration
    public InetAddress getGroup() {
        return this.group;
    }

    public void setGroup(InetAddress inetAddress) {
        this.group = inetAddress;
    }

    @Override // org.fourthline.cling.transport.spi.MulticastReceiverConfiguration
    public int getPort() {
        return this.port;
    }

    public void setPort(int i) {
        this.port = i;
    }

    @Override // org.fourthline.cling.transport.spi.MulticastReceiverConfiguration
    public int getMaxDatagramBytes() {
        return this.maxDatagramBytes;
    }

    public void setMaxDatagramBytes(int i) {
        this.maxDatagramBytes = i;
    }
}
