package io.netty.channel.udt.nio;

import com.barchart.udt.SocketUDT;
import com.barchart.udt.TypeUDT;
import com.barchart.udt.nio.ChannelUDT;
import com.barchart.udt.nio.KindUDT;
import com.barchart.udt.nio.RendezvousChannelUDT;
import com.barchart.udt.nio.SelectorProviderUDT;
import com.barchart.udt.nio.ServerSocketChannelUDT;
import com.barchart.udt.nio.SocketChannelUDT;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFactory;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.UdtServerChannel;
import java.io.IOException;
import java.nio.channels.spi.SelectorProvider;

/* loaded from: classes4.dex */
public final class NioUdtProvider<T extends UdtChannel> implements ChannelFactory<T> {
    public static final ChannelFactory<UdtServerChannel> BYTE_ACCEPTOR = new NioUdtProvider(TypeUDT.STREAM, KindUDT.ACCEPTOR);
    public static final ChannelFactory<UdtChannel> BYTE_CONNECTOR = new NioUdtProvider(TypeUDT.STREAM, KindUDT.CONNECTOR);
    public static final SelectorProvider BYTE_PROVIDER = SelectorProviderUDT.STREAM;
    public static final ChannelFactory<UdtChannel> BYTE_RENDEZVOUS = new NioUdtProvider(TypeUDT.STREAM, KindUDT.RENDEZVOUS);
    public static final ChannelFactory<UdtServerChannel> MESSAGE_ACCEPTOR = new NioUdtProvider(TypeUDT.DATAGRAM, KindUDT.ACCEPTOR);
    public static final ChannelFactory<UdtChannel> MESSAGE_CONNECTOR = new NioUdtProvider(TypeUDT.DATAGRAM, KindUDT.CONNECTOR);
    public static final SelectorProvider MESSAGE_PROVIDER = SelectorProviderUDT.DATAGRAM;
    public static final ChannelFactory<UdtChannel> MESSAGE_RENDEZVOUS = new NioUdtProvider(TypeUDT.DATAGRAM, KindUDT.RENDEZVOUS);
    private final KindUDT a;
    private final TypeUDT b;

    public static ChannelUDT channelUDT(Channel channel) {
        if (channel instanceof NioUdtByteAcceptorChannel) {
            return ((NioUdtByteAcceptorChannel) channel).javaChannel();
        }
        if (channel instanceof NioUdtByteRendezvousChannel) {
            return ((NioUdtByteRendezvousChannel) channel).javaChannel();
        }
        if (channel instanceof NioUdtByteConnectorChannel) {
            return ((NioUdtByteConnectorChannel) channel).javaChannel();
        }
        if (channel instanceof NioUdtMessageAcceptorChannel) {
            return ((NioUdtMessageAcceptorChannel) channel).javaChannel();
        }
        if (channel instanceof NioUdtMessageRendezvousChannel) {
            return ((NioUdtMessageRendezvousChannel) channel).javaChannel();
        }
        if (channel instanceof NioUdtMessageConnectorChannel) {
            return ((NioUdtMessageConnectorChannel) channel).javaChannel();
        }
        return null;
    }

    public static ServerSocketChannelUDT a(TypeUDT typeUDT) {
        try {
            return SelectorProviderUDT.from(typeUDT).openServerSocketChannel();
        } catch (IOException e) {
            throw new ChannelException("failed to open a server socket channel", e);
        }
    }

    public static SocketChannelUDT b(TypeUDT typeUDT) {
        try {
            return SelectorProviderUDT.from(typeUDT).openSocketChannel();
        } catch (IOException e) {
            throw new ChannelException("failed to open a socket channel", e);
        }
    }

    public static RendezvousChannelUDT c(TypeUDT typeUDT) {
        try {
            return SelectorProviderUDT.from(typeUDT).openRendezvousChannel();
        } catch (IOException e) {
            throw new ChannelException("failed to open a rendezvous channel", e);
        }
    }

    public static SocketUDT socketUDT(Channel channel) {
        ChannelUDT channelUDT = channelUDT(channel);
        if (channelUDT == null) {
            return null;
        }
        return channelUDT.socketUDT();
    }

    private NioUdtProvider(TypeUDT typeUDT, KindUDT kindUDT) {
        this.b = typeUDT;
        this.a = kindUDT;
    }

    public KindUDT kind() {
        return this.a;
    }

    /* renamed from: io.netty.channel.udt.nio.NioUdtProvider$1 */
    /* loaded from: classes4.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b = new int[KindUDT.values().length];

        static {
            try {
                b[KindUDT.ACCEPTOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[KindUDT.CONNECTOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[KindUDT.RENDEZVOUS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            a = new int[TypeUDT.values().length];
            try {
                a[TypeUDT.DATAGRAM.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[TypeUDT.STREAM.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    @Override // io.netty.channel.ChannelFactory, io.netty.bootstrap.ChannelFactory
    public T newChannel() {
        switch (AnonymousClass1.b[this.a.ordinal()]) {
            case 1:
                switch (AnonymousClass1.a[this.b.ordinal()]) {
                    case 1:
                        return new NioUdtMessageAcceptorChannel();
                    case 2:
                        return new NioUdtByteAcceptorChannel();
                    default:
                        throw new IllegalStateException("wrong type=" + this.b);
                }
            case 2:
                switch (AnonymousClass1.a[this.b.ordinal()]) {
                    case 1:
                        return new NioUdtMessageConnectorChannel();
                    case 2:
                        return new NioUdtByteConnectorChannel();
                    default:
                        throw new IllegalStateException("wrong type=" + this.b);
                }
            case 3:
                switch (AnonymousClass1.a[this.b.ordinal()]) {
                    case 1:
                        return new NioUdtMessageRendezvousChannel();
                    case 2:
                        return new NioUdtByteRendezvousChannel();
                    default:
                        throw new IllegalStateException("wrong type=" + this.b);
                }
            default:
                throw new IllegalStateException("wrong kind=" + this.a);
        }
    }

    public TypeUDT type() {
        return this.b;
    }
}
