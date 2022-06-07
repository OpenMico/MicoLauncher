package com.google.zxing.oned.rss.expanded.decoders;

/* compiled from: CurrentParsingState.java */
/* loaded from: classes2.dex */
final class l {
    private int a = 0;
    private a b = a.NUMERIC;

    /* compiled from: CurrentParsingState.java */
    /* loaded from: classes2.dex */
    private enum a {
        NUMERIC,
        ALPHA,
        ISO_IEC_646
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i) {
        this.a = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(int i) {
        this.a += i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b() {
        return this.b == a.ALPHA;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean c() {
        return this.b == a.ISO_IEC_646;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void d() {
        this.b = a.NUMERIC;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void e() {
        this.b = a.ALPHA;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void f() {
        this.b = a.ISO_IEC_646;
    }
}
