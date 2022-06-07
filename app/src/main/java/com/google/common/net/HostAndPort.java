package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.slf4j.Marker;

@Immutable
@Beta
@GwtCompatible
/* loaded from: classes2.dex */
public final class HostAndPort implements Serializable {
    private static final long serialVersionUID = 0;
    private final boolean hasBracketlessColons;
    private final String host;
    private final int port;

    private static boolean a(int i) {
        return i >= 0 && i <= 65535;
    }

    private HostAndPort(String str, int i, boolean z) {
        this.host = str;
        this.port = i;
        this.hasBracketlessColons = z;
    }

    public String getHost() {
        return this.host;
    }

    public boolean hasPort() {
        return this.port >= 0;
    }

    public int getPort() {
        Preconditions.checkState(hasPort());
        return this.port;
    }

    public int getPortOrDefault(int i) {
        return hasPort() ? this.port : i;
    }

    public static HostAndPort fromParts(String str, int i) {
        Preconditions.checkArgument(a(i), "Port out of range: %s", i);
        HostAndPort fromString = fromString(str);
        Preconditions.checkArgument(!fromString.hasPort(), "Host has a port: %s", str);
        return new HostAndPort(fromString.host, i, fromString.hasBracketlessColons);
    }

    public static HostAndPort fromHost(String str) {
        HostAndPort fromString = fromString(str);
        Preconditions.checkArgument(!fromString.hasPort(), "Host has a port: %s", str);
        return fromString;
    }

    public static HostAndPort fromString(String str) {
        String str2;
        String str3;
        Preconditions.checkNotNull(str);
        int i = -1;
        boolean z = false;
        if (str.startsWith("[")) {
            String[] a = a(str);
            str2 = a[0];
            str3 = a[1];
        } else {
            int indexOf = str.indexOf(58);
            if (indexOf >= 0) {
                int i2 = indexOf + 1;
                if (str.indexOf(58, i2) == -1) {
                    str2 = str.substring(0, indexOf);
                    str3 = str.substring(i2);
                }
            }
            if (indexOf >= 0) {
                z = true;
            }
            str3 = null;
            str2 = str;
        }
        if (!Strings.isNullOrEmpty(str3)) {
            Preconditions.checkArgument(!str3.startsWith(Marker.ANY_NON_NULL_MARKER), "Unparseable port number: %s", str);
            try {
                i = Integer.parseInt(str3);
                Preconditions.checkArgument(a(i), "Port number out of range: %s", str);
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException("Unparseable port number: " + str);
            }
        }
        return new HostAndPort(str2, i, z);
    }

    private static String[] a(String str) {
        Preconditions.checkArgument(str.charAt(0) == '[', "Bracketed host-port string must start with a bracket: %s", str);
        int indexOf = str.indexOf(58);
        int lastIndexOf = str.lastIndexOf(93);
        Preconditions.checkArgument(indexOf > -1 && lastIndexOf > indexOf, "Invalid bracketed host/port: %s", str);
        String substring = str.substring(1, lastIndexOf);
        int i = lastIndexOf + 1;
        if (i == str.length()) {
            return new String[]{substring, ""};
        }
        Preconditions.checkArgument(str.charAt(i) == ':', "Only a colon may follow a close bracket: %s", str);
        int i2 = lastIndexOf + 2;
        for (int i3 = i2; i3 < str.length(); i3++) {
            Preconditions.checkArgument(Character.isDigit(str.charAt(i3)), "Port must be numeric: %s", str);
        }
        return new String[]{substring, str.substring(i2)};
    }

    public HostAndPort withDefaultPort(int i) {
        Preconditions.checkArgument(a(i));
        return hasPort() ? this : new HostAndPort(this.host, i, this.hasBracketlessColons);
    }

    public HostAndPort requireBracketsForIPv6() {
        Preconditions.checkArgument(!this.hasBracketlessColons, "Possible bracketless IPv6 literal: %s", this.host);
        return this;
    }

    public boolean equals(@NullableDecl Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HostAndPort)) {
            return false;
        }
        HostAndPort hostAndPort = (HostAndPort) obj;
        return Objects.equal(this.host, hostAndPort.host) && this.port == hostAndPort.port && this.hasBracketlessColons == hostAndPort.hasBracketlessColons;
    }

    public int hashCode() {
        return Objects.hashCode(this.host, Integer.valueOf(this.port), Boolean.valueOf(this.hasBracketlessColons));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.host.length() + 8);
        if (this.host.indexOf(58) >= 0) {
            sb.append('[');
            sb.append(this.host);
            sb.append(']');
        } else {
            sb.append(this.host);
        }
        if (hasPort()) {
            sb.append(':');
            sb.append(this.port);
        }
        return sb.toString();
    }
}
