package com.google.zxing.oned.rss.expanded.decoders;

/* compiled from: DecodedChar.java */
/* loaded from: classes2.dex */
final class m extends p {
    private final char a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public m(int i, char c) {
        super(i);
        this.a = c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public char a() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b() {
        return this.a == '$';
    }
}
