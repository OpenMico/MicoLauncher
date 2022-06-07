package com.google.zxing;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;

/* loaded from: classes2.dex */
public final class BinaryBitmap {
    private final Binarizer a;
    private BitMatrix b;

    public BinaryBitmap(Binarizer binarizer) {
        if (binarizer != null) {
            this.a = binarizer;
            return;
        }
        throw new IllegalArgumentException("Binarizer must be non-null.");
    }

    public int getWidth() {
        return this.a.getWidth();
    }

    public int getHeight() {
        return this.a.getHeight();
    }

    public BitArray getBlackRow(int i, BitArray bitArray) throws NotFoundException {
        return this.a.getBlackRow(i, bitArray);
    }

    public BitMatrix getBlackMatrix() throws NotFoundException {
        if (this.b == null) {
            this.b = this.a.getBlackMatrix();
        }
        return this.b;
    }

    public boolean isCropSupported() {
        return this.a.getLuminanceSource().isCropSupported();
    }

    public BinaryBitmap crop(int i, int i2, int i3, int i4) {
        return new BinaryBitmap(this.a.createBinarizer(this.a.getLuminanceSource().crop(i, i2, i3, i4)));
    }

    public boolean isRotateSupported() {
        return this.a.getLuminanceSource().isRotateSupported();
    }

    public BinaryBitmap rotateCounterClockwise() {
        return new BinaryBitmap(this.a.createBinarizer(this.a.getLuminanceSource().rotateCounterClockwise()));
    }

    public BinaryBitmap rotateCounterClockwise45() {
        return new BinaryBitmap(this.a.createBinarizer(this.a.getLuminanceSource().rotateCounterClockwise45()));
    }

    public String toString() {
        try {
            return getBlackMatrix().toString();
        } catch (NotFoundException unused) {
            return "";
        }
    }
}
