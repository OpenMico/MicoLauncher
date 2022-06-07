package com.google.zxing;

/* loaded from: classes2.dex */
public final class RGBLuminanceSource extends LuminanceSource {
    private final byte[] a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;

    @Override // com.google.zxing.LuminanceSource
    public boolean isCropSupported() {
        return true;
    }

    public RGBLuminanceSource(int i, int i2, int[] iArr) {
        super(i, i2);
        this.b = i;
        this.c = i2;
        this.d = 0;
        this.e = 0;
        int i3 = i * i2;
        this.a = new byte[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = iArr[i4];
            this.a[i4] = (byte) (((((i5 >> 16) & 255) + ((i5 >> 7) & 510)) + (i5 & 255)) / 4);
        }
    }

    private RGBLuminanceSource(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6) {
        super(i5, i6);
        if (i5 + i3 > i || i6 + i4 > i2) {
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        }
        this.a = bArr;
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = i4;
    }

    @Override // com.google.zxing.LuminanceSource
    public byte[] getRow(int i, byte[] bArr) {
        if (i < 0 || i >= getHeight()) {
            throw new IllegalArgumentException("Requested row is outside the image: " + i);
        }
        int width = getWidth();
        if (bArr == null || bArr.length < width) {
            bArr = new byte[width];
        }
        System.arraycopy(this.a, ((i + this.e) * this.b) + this.d, bArr, 0, width);
        return bArr;
    }

    @Override // com.google.zxing.LuminanceSource
    public byte[] getMatrix() {
        int width = getWidth();
        int height = getHeight();
        if (width == this.b && height == this.c) {
            return this.a;
        }
        int i = width * height;
        byte[] bArr = new byte[i];
        int i2 = this.e;
        int i3 = this.b;
        int i4 = (i2 * i3) + this.d;
        if (width == i3) {
            System.arraycopy(this.a, i4, bArr, 0, i);
            return bArr;
        }
        for (int i5 = 0; i5 < height; i5++) {
            System.arraycopy(this.a, i4, bArr, i5 * width, width);
            i4 += this.b;
        }
        return bArr;
    }

    @Override // com.google.zxing.LuminanceSource
    public LuminanceSource crop(int i, int i2, int i3, int i4) {
        return new RGBLuminanceSource(this.a, this.b, this.c, this.d + i, this.e + i2, i3, i4);
    }
}
