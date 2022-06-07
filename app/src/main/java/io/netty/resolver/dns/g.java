package io.netty.resolver.dns;

import java.net.InetSocketAddress;

/* compiled from: SequentialDnsServerAddressStream.java */
/* loaded from: classes4.dex */
final class g implements DnsServerAddressStream {
    private final InetSocketAddress[] a;
    private int b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(InetSocketAddress[] inetSocketAddressArr, int i) {
        this.a = inetSocketAddressArr;
        this.b = i;
    }

    @Override // io.netty.resolver.dns.DnsServerAddressStream
    public InetSocketAddress next() {
        int i = this.b;
        InetSocketAddress[] inetSocketAddressArr = this.a;
        InetSocketAddress inetSocketAddress = inetSocketAddressArr[i];
        int i2 = i + 1;
        if (i2 < inetSocketAddressArr.length) {
            this.b = i2;
        } else {
            this.b = 0;
        }
        return inetSocketAddress;
    }

    public String toString() {
        return a("sequential", this.b, this.a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(String str, int i, InetSocketAddress[] inetSocketAddressArr) {
        StringBuilder sb = new StringBuilder(str.length() + 2 + (inetSocketAddressArr.length * 16));
        sb.append(str);
        sb.append("(index: ");
        sb.append(i);
        sb.append(", addrs: (");
        for (InetSocketAddress inetSocketAddress : inetSocketAddressArr) {
            sb.append(inetSocketAddress);
            sb.append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("))");
        return sb.toString();
    }
}
