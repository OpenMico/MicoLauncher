package org.fourthline.cling.model.types;

import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes5.dex */
public class HostPort {
    private String host;
    private int port;

    public HostPort() {
    }

    public HostPort(String str, int i) {
        this.host = str;
        this.port = i;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String str) {
        this.host = str;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int i) {
        this.port = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HostPort hostPort = (HostPort) obj;
        return this.port == hostPort.port && this.host.equals(hostPort.host);
    }

    public int hashCode() {
        return (this.host.hashCode() * 31) + this.port;
    }

    public String toString() {
        return this.host + Constants.COLON_SEPARATOR + this.port;
    }
}
