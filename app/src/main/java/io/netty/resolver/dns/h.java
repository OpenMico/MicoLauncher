package io.netty.resolver.dns;

import io.netty.util.internal.ThreadLocalRandom;
import java.net.InetSocketAddress;

/* compiled from: ShuffledDnsServerAddressStream.java */
/* loaded from: classes4.dex */
final class h implements DnsServerAddressStream {
    private final InetSocketAddress[] a;
    private int b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(InetSocketAddress[] inetSocketAddressArr) {
        this.a = (InetSocketAddress[]) inetSocketAddressArr.clone();
        a();
    }

    private void a() {
        InetSocketAddress[] inetSocketAddressArr = this.a;
        ThreadLocalRandom current = ThreadLocalRandom.current();
        for (int length = inetSocketAddressArr.length - 1; length >= 0; length--) {
            InetSocketAddress inetSocketAddress = inetSocketAddressArr[length];
            int nextInt = current.nextInt(length + 1);
            inetSocketAddressArr[length] = inetSocketAddressArr[nextInt];
            inetSocketAddressArr[nextInt] = inetSocketAddress;
        }
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
            a();
        }
        return inetSocketAddress;
    }

    public String toString() {
        return g.a("shuffled", this.b, this.a);
    }
}
