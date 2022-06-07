package com.google.zxing.datamatrix.encoder;

import com.google.zxing.Dimension;
import java.nio.charset.StandardCharsets;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: EncoderContext.java */
/* loaded from: classes2.dex */
public final class g {
    int a;
    private final String b;
    private SymbolShapeHint c;
    private Dimension d;
    private Dimension e;
    private final StringBuilder f;
    private int g;
    private SymbolInfo h;
    private int i;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.ISO_8859_1);
        StringBuilder sb = new StringBuilder(bytes.length);
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            char c = (char) (bytes[i] & 255);
            if (c != '?' || str.charAt(i) == '?') {
                sb.append(c);
            } else {
                throw new IllegalArgumentException("Message contains characters outside ISO-8859-1 encoding.");
            }
        }
        this.b = sb.toString();
        this.c = SymbolShapeHint.FORCE_NONE;
        this.f = new StringBuilder(str.length());
        this.g = -1;
    }

    public void a(SymbolShapeHint symbolShapeHint) {
        this.c = symbolShapeHint;
    }

    public void a(Dimension dimension, Dimension dimension2) {
        this.d = dimension;
        this.e = dimension2;
    }

    public String a() {
        return this.b;
    }

    public void a(int i) {
        this.i = i;
    }

    public char b() {
        return this.b.charAt(this.a);
    }

    public StringBuilder c() {
        return this.f;
    }

    public void a(String str) {
        this.f.append(str);
    }

    public void a(char c) {
        this.f.append(c);
    }

    public int d() {
        return this.f.length();
    }

    public int e() {
        return this.g;
    }

    public void b(int i) {
        this.g = i;
    }

    public void f() {
        this.g = -1;
    }

    public boolean g() {
        return this.a < l();
    }

    private int l() {
        return this.b.length() - this.i;
    }

    public int h() {
        return l() - this.a;
    }

    public SymbolInfo i() {
        return this.h;
    }

    public void j() {
        c(d());
    }

    public void c(int i) {
        SymbolInfo symbolInfo = this.h;
        if (symbolInfo == null || i > symbolInfo.getDataCapacity()) {
            this.h = SymbolInfo.lookup(i, this.c, this.d, this.e, true);
        }
    }

    public void k() {
        this.h = null;
    }
}
