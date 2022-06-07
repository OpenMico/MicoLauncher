package com.google.zxing;

import androidx.core.view.ViewCompat;

/* loaded from: classes2.dex */
public final class PlanarYUVLuminanceSource extends LuminanceSource {
    private final byte[] a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;

    @Override // com.google.zxing.LuminanceSource
    public boolean isCropSupported() {
        return true;
    }

    public PlanarYUVLuminanceSource(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
        super(i5, i6);
        if (i3 + i5 > i || i4 + i6 > i2) {
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        }
        this.a = bArr;
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = i4;
        if (z) {
            a(i5, i6);
        }
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
        return new PlanarYUVLuminanceSource(this.a, this.b, this.c, this.d + i, this.e + i2, i3, i4, false);
    }

    public int[] renderThumbnail() {
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        int[] iArr = new int[width * height];
        byte[] bArr = this.a;
        int i = (this.e * this.b) + this.d;
        for (int i2 = 0; i2 < height; i2++) {
            int i3 = i2 * width;
            for (int i4 = 0; i4 < width; i4++) {
                iArr[i3 + i4] = ((bArr[(i4 << 1) + i] & 255) * 65793) | ViewCompat.MEASURED_STATE_MASK;
            }
            i += this.b << 1;
        }
        return iArr;
    }

    public int getThumbnailWidth() {
        return getWidth() / 2;
    }

    public int getThumbnailHeight() {
        return getHeight() / 2;
    }

    private void a(int i, int i2) {
        byte[] bArr = this.a;
        int i3 = (this.e * this.b) + this.d;
        int i4 = 0;
        while (i4 < i2) {
            int i5 = (i / 2) + i3;
            int i6 = (i3 + i) - 1;
            int i7 = i3;
            while (i7 < i5) {
                byte b = bArr[i7];
                bArr[i7] = bArr[i6];
                bArr[i6] = b;
                i7++;
                i6--;
            }
            i4++;
            i3 += this.b;
        }
    }
}
