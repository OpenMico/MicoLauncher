package org.fourthline.cling.transport.spi;

import java.net.InetAddress;

/* loaded from: classes5.dex */
public interface MulticastReceiverConfiguration {
    InetAddress getGroup();

    int getMaxDatagramBytes();

    int getPort();
}
