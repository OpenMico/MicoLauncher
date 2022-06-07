package org.eclipse.jetty.client;

import com.xiaomi.mipush.sdk.Constants;
import java.net.InetSocketAddress;

/* loaded from: classes5.dex */
public class Address {
    private final String host;
    private final int port;

    public static Address from(String str) {
        int indexOf = str.indexOf(58);
        int i = 0;
        if (indexOf >= 0) {
            String substring = str.substring(0, indexOf);
            i = Integer.parseInt(str.substring(indexOf + 1));
            str = substring;
        }
        return new Address(str, i);
    }

    public Address(String str, int i) {
        if (str != null) {
            this.host = str.trim();
            this.port = i;
            return;
        }
        throw new IllegalArgumentException("Host is null");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Address address = (Address) obj;
        return this.host.equals(address.host) && this.port == address.port;
    }

    public int hashCode() {
        return (this.host.hashCode() * 31) + this.port;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public InetSocketAddress toSocketAddress() {
        return new InetSocketAddress(getHost(), getPort());
    }

    public String toString() {
        return this.host + Constants.COLON_SEPARATOR + this.port;
    }
}
