package io.netty.channel.epoll;

import io.netty.channel.ChannelOption;
import io.netty.channel.unix.DomainSocketReadMode;
import java.net.InetAddress;
import java.util.Map;

/* loaded from: classes4.dex */
public final class EpollChannelOption<T> extends ChannelOption<T> {
    private static final Class<EpollChannelOption> a = EpollChannelOption.class;
    public static final ChannelOption<Boolean> TCP_CORK = ChannelOption.valueOf(a, "TCP_CORK");
    public static final ChannelOption<Boolean> SO_REUSEPORT = ChannelOption.valueOf(a, "SO_REUSEPORT");
    public static final ChannelOption<Long> TCP_NOTSENT_LOWAT = ChannelOption.valueOf(a, "TCP_NOTSENT_LOWAT");
    public static final ChannelOption<Integer> TCP_KEEPIDLE = ChannelOption.valueOf(a, "TCP_KEEPIDLE");
    public static final ChannelOption<Integer> TCP_KEEPINTVL = ChannelOption.valueOf(a, "TCP_KEEPINTVL");
    public static final ChannelOption<Integer> TCP_KEEPCNT = ChannelOption.valueOf(a, "TCP_KEEPCNT");
    public static final ChannelOption<Integer> TCP_USER_TIMEOUT = valueOf(a, "TCP_USER_TIMEOUT");
    public static final ChannelOption<Boolean> IP_FREEBIND = ChannelOption.valueOf("IP_FREEBIND");
    public static final ChannelOption<Integer> TCP_FASTOPEN = valueOf(a, "TCP_FASTOPEN");
    public static final ChannelOption<Integer> TCP_DEFER_ACCEPT = ChannelOption.valueOf(a, "TCP_DEFER_ACCEPT");
    public static final ChannelOption<Boolean> TCP_QUICKACK = ChannelOption.valueOf(a, "TCP_QUICKACK");
    public static final ChannelOption<DomainSocketReadMode> DOMAIN_SOCKET_READ_MODE = ChannelOption.valueOf(a, "DOMAIN_SOCKET_READ_MODE");
    public static final ChannelOption<EpollMode> EPOLL_MODE = ChannelOption.valueOf(a, "EPOLL_MODE");
    public static final ChannelOption<Map<InetAddress, byte[]>> TCP_MD5SIG = valueOf("TCP_MD5SIG");

    private EpollChannelOption() {
        super(null);
    }
}
