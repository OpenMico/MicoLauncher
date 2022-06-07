package io.netty.channel.socket.nio;

import io.netty.channel.socket.InternetProtocolFamily;
import java.net.ProtocolFamily;
import java.net.StandardProtocolFamily;

/* compiled from: ProtocolFamilyConverter.java */
/* loaded from: classes4.dex */
final class b {
    public static ProtocolFamily a(InternetProtocolFamily internetProtocolFamily) {
        switch (internetProtocolFamily) {
            case IPv4:
                return StandardProtocolFamily.INET;
            case IPv6:
                return StandardProtocolFamily.INET6;
            default:
                throw new IllegalArgumentException();
        }
    }
}
