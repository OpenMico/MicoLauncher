package io.netty.channel.unix;

import java.io.File;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public final class DomainSocketAddress extends SocketAddress {
    private static final long serialVersionUID = -6934618000832236893L;
    private final String socketPath;

    public DomainSocketAddress(String str) {
        if (str != null) {
            this.socketPath = str;
            return;
        }
        throw new NullPointerException("socketPath");
    }

    public DomainSocketAddress(File file) {
        this(file.getPath());
    }

    public String path() {
        return this.socketPath;
    }

    public String toString() {
        return path();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DomainSocketAddress)) {
            return false;
        }
        return ((DomainSocketAddress) obj).socketPath.equals(this.socketPath);
    }

    public int hashCode() {
        return this.socketPath.hashCode();
    }
}
