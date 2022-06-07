package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitMatrix;

/* loaded from: classes2.dex */
public final class AztecCode {
    private boolean a;
    private int b;
    private int c;
    private int d;
    private BitMatrix e;

    public boolean isCompact() {
        return this.a;
    }

    public void setCompact(boolean z) {
        this.a = z;
    }

    public int getSize() {
        return this.b;
    }

    public void setSize(int i) {
        this.b = i;
    }

    public int getLayers() {
        return this.c;
    }

    public void setLayers(int i) {
        this.c = i;
    }

    public int getCodeWords() {
        return this.d;
    }

    public void setCodeWords(int i) {
        this.d = i;
    }

    public BitMatrix getMatrix() {
        return this.e;
    }

    public void setMatrix(BitMatrix bitMatrix) {
        this.e = bitMatrix;
    }
}
