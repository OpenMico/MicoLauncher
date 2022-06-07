package io.netty.channel.udt;

import io.netty.channel.ChannelOption;

/* loaded from: classes4.dex */
public final class UdtChannelOption<T> extends ChannelOption<T> {
    private static final Class<UdtChannelOption> a = UdtChannelOption.class;
    public static final ChannelOption<Integer> PROTOCOL_RECEIVE_BUFFER_SIZE = valueOf(a, "PROTOCOL_RECEIVE_BUFFER_SIZE");
    public static final ChannelOption<Integer> PROTOCOL_SEND_BUFFER_SIZE = valueOf(a, "PROTOCOL_SEND_BUFFER_SIZE");
    public static final ChannelOption<Integer> SYSTEM_RECEIVE_BUFFER_SIZE = valueOf(a, "SYSTEM_RECEIVE_BUFFER_SIZE");
    public static final ChannelOption<Integer> SYSTEM_SEND_BUFFER_SIZE = valueOf(a, "SYSTEM_SEND_BUFFER_SIZE");

    private UdtChannelOption() {
        super(null);
    }
}
