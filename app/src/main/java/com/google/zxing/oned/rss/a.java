package com.google.zxing.oned.rss;

/* compiled from: Pair.java */
/* loaded from: classes2.dex */
final class a extends DataCharacter {
    private final FinderPattern a;
    private int b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(int i, int i2, FinderPattern finderPattern) {
        super(i, i2);
        this.a = finderPattern;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FinderPattern a() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b() {
        return this.b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c() {
        this.b++;
    }
}
