package io.netty.channel;

import io.netty.util.IntSupplier;

/* compiled from: DefaultSelectStrategy.java */
/* loaded from: classes4.dex */
final class d implements SelectStrategy {
    static final SelectStrategy a = new d();

    private d() {
    }

    @Override // io.netty.channel.SelectStrategy
    public int calculateStrategy(IntSupplier intSupplier, boolean z) throws Exception {
        if (z) {
            return intSupplier.get();
        }
        return -1;
    }
}
