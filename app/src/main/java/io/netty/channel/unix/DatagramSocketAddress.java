package io.netty.channel.unix;

import java.net.InetSocketAddress;

/* loaded from: classes4.dex */
public final class DatagramSocketAddress extends InetSocketAddress {
    private static final long serialVersionUID = 3094819287843178401L;
    private final int receivedAmount;

    public int receivedAmount() {
        return this.receivedAmount;
    }
}
