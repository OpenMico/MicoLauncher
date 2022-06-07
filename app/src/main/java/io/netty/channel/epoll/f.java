package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.unix.NativeInetAddress;
import io.netty.util.concurrent.FastThreadLocal;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NativeDatagramPacketArray.java */
/* loaded from: classes4.dex */
public final class f implements ChannelOutboundBuffer.MessageProcessor {
    private static final FastThreadLocal<f> a = new FastThreadLocal<f>() { // from class: io.netty.channel.epoll.f.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public f initialValue() throws Exception {
            return new f();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public void onRemoval(f fVar) throws Exception {
            for (a aVar : fVar.b) {
                aVar.a();
            }
        }
    };
    private final a[] b;
    private int c;

    private f() {
        this.b = new a[Native.UIO_MAX_IOV];
        int i = 0;
        while (true) {
            a[] aVarArr = this.b;
            if (i < aVarArr.length) {
                aVarArr[i] = new a();
                i++;
            } else {
                return;
            }
        }
    }

    boolean a(DatagramPacket datagramPacket) {
        if (this.c == this.b.length) {
            return false;
        }
        ByteBuf byteBuf = (ByteBuf) datagramPacket.content();
        if (byteBuf.readableBytes() == 0) {
            return true;
        }
        if (!this.b[this.c].a(byteBuf, datagramPacket.recipient())) {
            return false;
        }
        this.c++;
        return true;
    }

    @Override // io.netty.channel.ChannelOutboundBuffer.MessageProcessor
    public boolean processMessage(Object obj) throws Exception {
        return (obj instanceof DatagramPacket) && a((DatagramPacket) obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a[] b() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static f a(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        f fVar = a.get();
        fVar.c = 0;
        channelOutboundBuffer.forEachFlushedMessage(fVar);
        return fVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: NativeDatagramPacketArray.java */
    /* loaded from: classes4.dex */
    public static final class a {
        private final e a = new e();
        private long b;
        private int c;
        private byte[] d;
        private int e;
        private int f;

        a() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a() {
            this.a.d();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean a(ByteBuf byteBuf, InetSocketAddress inetSocketAddress) {
            this.a.a();
            if (!this.a.a(byteBuf)) {
                return false;
            }
            this.b = this.a.a(0);
            this.c = this.a.b();
            InetAddress address = inetSocketAddress.getAddress();
            if (address instanceof Inet6Address) {
                this.d = address.getAddress();
                this.e = ((Inet6Address) address).getScopeId();
            } else {
                this.d = NativeInetAddress.ipv4MappedIpv6Address(address.getAddress());
                this.e = 0;
            }
            this.f = inetSocketAddress.getPort();
            return true;
        }
    }
}
