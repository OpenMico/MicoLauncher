package io.netty.channel.sctp;

import com.sun.nio.sctp.SctpStandardSocketOptions;
import io.netty.channel.ChannelOption;
import java.net.SocketAddress;

/* loaded from: classes4.dex */
public final class SctpChannelOption<T> extends ChannelOption<T> {
    private static final Class<SctpChannelOption> a = SctpChannelOption.class;
    public static final ChannelOption<Boolean> SCTP_DISABLE_FRAGMENTS = valueOf(a, "SCTP_DISABLE_FRAGMENTS");
    public static final ChannelOption<Boolean> SCTP_EXPLICIT_COMPLETE = valueOf(a, "SCTP_EXPLICIT_COMPLETE");
    public static final ChannelOption<Integer> SCTP_FRAGMENT_INTERLEAVE = valueOf(a, "SCTP_FRAGMENT_INTERLEAVE");
    public static final ChannelOption<SctpStandardSocketOptions.InitMaxStreams> SCTP_INIT_MAXSTREAMS = valueOf(a, "SCTP_INIT_MAXSTREAMS");
    public static final ChannelOption<Boolean> SCTP_NODELAY = valueOf(a, "SCTP_NODELAY");
    public static final ChannelOption<SocketAddress> SCTP_PRIMARY_ADDR = valueOf(a, "SCTP_PRIMARY_ADDR");
    public static final ChannelOption<SocketAddress> SCTP_SET_PEER_PRIMARY_ADDR = valueOf(a, "SCTP_SET_PEER_PRIMARY_ADDR");

    private SctpChannelOption() {
        super(null);
    }
}
