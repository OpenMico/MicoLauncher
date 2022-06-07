package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;

/* compiled from: AI013103decoder.java */
/* loaded from: classes2.dex */
final class a extends f {
    @Override // com.google.zxing.oned.rss.expanded.decoders.i
    protected int a(int i) {
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(BitArray bitArray) {
        super(bitArray);
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.i
    protected void a(StringBuilder sb, int i) {
        sb.append("(3103)");
    }
}
