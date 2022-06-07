package io.netty.channel.socket;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;

/* loaded from: classes4.dex */
public enum InternetProtocolFamily {
    IPv4(Inet4Address.class),
    IPv6(Inet6Address.class);
    
    private final Class<? extends InetAddress> addressType;

    InternetProtocolFamily(Class cls) {
        this.addressType = cls;
    }

    public Class<? extends InetAddress> addressType() {
        return this.addressType;
    }
}
