package io.netty.resolver.dns;

import java.net.InetSocketAddress;

/* compiled from: SingletonDnsServerAddresses.java */
/* loaded from: classes4.dex */
final class i extends DnsServerAddresses {
    private final InetSocketAddress a;
    private final String b;
    private final DnsServerAddressStream c = new DnsServerAddressStream() { // from class: io.netty.resolver.dns.i.1
        @Override // io.netty.resolver.dns.DnsServerAddressStream
        public InetSocketAddress next() {
            return i.this.a;
        }

        public String toString() {
            return i.this.toString();
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(InetSocketAddress inetSocketAddress) {
        this.a = inetSocketAddress;
        StringBuilder sb = new StringBuilder(32);
        sb.append("singleton(");
        sb.append(inetSocketAddress);
        sb.append(')');
        this.b = sb.toString();
    }

    @Override // io.netty.resolver.dns.DnsServerAddresses
    public DnsServerAddressStream stream() {
        return this.c;
    }

    public String toString() {
        return this.b;
    }
}
