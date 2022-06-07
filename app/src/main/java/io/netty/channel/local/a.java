package io.netty.channel.local;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentMap;

/* compiled from: LocalChannelRegistry.java */
/* loaded from: classes4.dex */
final class a {
    private static final ConcurrentMap<LocalAddress, Channel> a = PlatformDependent.newConcurrentHashMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static LocalAddress a(Channel channel, LocalAddress localAddress, SocketAddress socketAddress) {
        if (localAddress != null) {
            throw new ChannelException("already bound");
        } else if (socketAddress instanceof LocalAddress) {
            LocalAddress localAddress2 = (LocalAddress) socketAddress;
            if (LocalAddress.ANY.equals(localAddress2)) {
                localAddress2 = new LocalAddress(channel);
            }
            Channel putIfAbsent = a.putIfAbsent(localAddress2, channel);
            if (putIfAbsent == null) {
                return localAddress2;
            }
            throw new ChannelException("address already in use by: " + putIfAbsent);
        } else {
            throw new ChannelException("unsupported address type: " + StringUtil.simpleClassName(socketAddress));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Channel a(SocketAddress socketAddress) {
        return a.get(socketAddress);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(LocalAddress localAddress) {
        a.remove(localAddress);
    }
}
