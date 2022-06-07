package io.netty.resolver.dns;

import java.net.InetSocketAddress;

/* compiled from: DefaultDnsServerAddresses.java */
/* loaded from: classes4.dex */
abstract class a extends DnsServerAddresses {
    protected final InetSocketAddress[] a;
    private final String b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(String str, InetSocketAddress[] inetSocketAddressArr) {
        this.a = inetSocketAddressArr;
        StringBuilder sb = new StringBuilder(str.length() + 2 + (inetSocketAddressArr.length * 16));
        sb.append(str);
        sb.append('(');
        for (InetSocketAddress inetSocketAddress : inetSocketAddressArr) {
            sb.append(inetSocketAddress);
            sb.append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append(')');
        this.b = sb.toString();
    }

    public String toString() {
        return this.b;
    }
}
