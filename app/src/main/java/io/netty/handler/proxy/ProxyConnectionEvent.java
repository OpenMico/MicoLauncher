package io.netty.handler.proxy;

import io.netty.util.internal.StringUtil;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public final class ProxyConnectionEvent {
    private final String a;
    private final String b;
    private final SocketAddress c;
    private final SocketAddress d;
    private String e;

    public ProxyConnectionEvent(String str, String str2, SocketAddress socketAddress, SocketAddress socketAddress2) {
        if (str == null) {
            throw new NullPointerException("protocol");
        } else if (str2 == null) {
            throw new NullPointerException("authScheme");
        } else if (socketAddress == null) {
            throw new NullPointerException("proxyAddress");
        } else if (socketAddress2 != null) {
            this.a = str;
            this.b = str2;
            this.c = socketAddress;
            this.d = socketAddress2;
        } else {
            throw new NullPointerException("destinationAddress");
        }
    }

    public String protocol() {
        return this.a;
    }

    public String authScheme() {
        return this.b;
    }

    public <T extends SocketAddress> T proxyAddress() {
        return (T) this.c;
    }

    public <T extends SocketAddress> T destinationAddress() {
        return (T) this.d;
    }

    public String toString() {
        String str = this.e;
        if (str != null) {
            return str;
        }
        StringBuilder sb = new StringBuilder(128);
        sb.append(StringUtil.simpleClassName(this));
        sb.append('(');
        sb.append(this.a);
        sb.append(", ");
        sb.append(this.b);
        sb.append(", ");
        sb.append(this.c);
        sb.append(" => ");
        sb.append(this.d);
        sb.append(')');
        String sb2 = sb.toString();
        this.e = sb2;
        return sb2;
    }
}
