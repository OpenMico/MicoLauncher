package io.netty.resolver.dns;

import com.umeng.analytics.pro.ai;
import io.netty.util.internal.PlatformDependent;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* compiled from: RotationalDnsServerAddresses.java */
/* loaded from: classes4.dex */
final class f extends a {
    private static final AtomicIntegerFieldUpdater<f> b;
    private volatile int c;

    static {
        AtomicIntegerFieldUpdater<f> newAtomicIntegerFieldUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(f.class, "startIdx");
        if (newAtomicIntegerFieldUpdater == null) {
            newAtomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(f.class, ai.aD);
        }
        b = newAtomicIntegerFieldUpdater;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(InetSocketAddress[] inetSocketAddressArr) {
        super("rotational", inetSocketAddressArr);
    }

    @Override // io.netty.resolver.dns.DnsServerAddresses
    public DnsServerAddressStream stream() {
        int i;
        int i2;
        do {
            i = this.c;
            i2 = i + 1;
            if (i2 >= this.a.length) {
                i2 = 0;
            }
        } while (!b.compareAndSet(this, i, i2));
        return new g(this.a, i);
    }
}
