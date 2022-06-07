package com.google.zxing.oned.rss.expanded.decoders;

/* compiled from: DecodedInformation.java */
/* loaded from: classes2.dex */
final class n extends p {
    private final String a;
    private final int b;
    private final boolean c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public n(int i, String str) {
        super(i);
        this.a = str;
        this.c = false;
        this.b = 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public n(int i, String str, int i2) {
        super(i);
        this.c = true;
        this.b = i2;
        this.a = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String a() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int c() {
        return this.b;
    }
}
