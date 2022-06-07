package io.netty.channel.udt.nio;

import com.barchart.udt.TypeUDT;
import com.barchart.udt.nio.SocketChannelUDT;

/* loaded from: classes4.dex */
public class NioUdtMessageRendezvousChannel extends NioUdtMessageConnectorChannel {
    public NioUdtMessageRendezvousChannel() {
        super((SocketChannelUDT) NioUdtProvider.c(TypeUDT.DATAGRAM));
    }
}
