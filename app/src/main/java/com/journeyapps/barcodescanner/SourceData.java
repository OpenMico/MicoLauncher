package com.journeyapps.barcodescanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.zxing.PlanarYUVLuminanceSource;
import java.io.ByteArrayOutputStream;

/* loaded from: classes2.dex */
public class SourceData {
    private byte[] a;
    private int b;
    private int c;
    private int d;
    private int e;
    private Rect f;

    public SourceData(byte[] bArr, int i, int i2, int i3, int i4) {
        this.a = bArr;
        this.b = i;
        this.c = i2;
        this.e = i4;
        this.d = i3;
        if (i * i2 > bArr.length) {
            throw new IllegalArgumentException("Image data does not match the resolution. " + i + "x" + i2 + " > " + bArr.length);
        }
    }

    public Rect getCropRect() {
        return this.f;
    }

    public void setCropRect(Rect rect) {
        this.f = rect;
    }

    public byte[] getData() {
        return this.a;
    }

    public int getDataWidth() {
        return this.b;
    }

    public int getDataHeight() {
        return this.c;
    }

    public boolean isRotated() {
        return this.e % Opcodes.GETFIELD != 0;
    }

    public int getImageFormat() {
        return this.d;
    }

    public PlanarYUVLuminanceSource createSource() {
        byte[] rotateCameraPreview = rotateCameraPreview(this.e, this.a, this.b, this.c);
        if (isRotated()) {
            return new PlanarYUVLuminanceSource(rotateCameraPreview, this.c, this.b, this.f.left, this.f.top, this.f.width(), this.f.height(), false);
        }
        return new PlanarYUVLuminanceSource(rotateCameraPreview, this.b, this.c, this.f.left, this.f.top, this.f.width(), this.f.height(), false);
    }

    public Bitmap getBitmap() {
        return getBitmap(1);
    }

    public Bitmap getBitmap(int i) {
        return a(this.f, i);
    }

    private Bitmap a(Rect rect, int i) {
        if (isRotated()) {
            rect = new Rect(rect.top, rect.left, rect.bottom, rect.right);
        }
        YuvImage yuvImage = new YuvImage(this.a, this.d, this.b, this.c, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(rect, 90, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = i;
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
        if (this.e == 0) {
            return decodeByteArray;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(this.e);
        return Bitmap.createBitmap(decodeByteArray, 0, 0, decodeByteArray.getWidth(), decodeByteArray.getHeight(), matrix, false);
    }

    public static byte[] rotateCameraPreview(int i, byte[] bArr, int i2, int i3) {
        if (i == 0) {
            return bArr;
        }
        if (i == 90) {
            return rotateCW(bArr, i2, i3);
        }
        if (i != 180) {
            return i != 270 ? bArr : rotateCCW(bArr, i2, i3);
        }
        return rotate180(bArr, i2, i3);
    }

    public static byte[] rotateCW(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i * i2];
        int i3 = 0;
        for (int i4 = 0; i4 < i; i4++) {
            for (int i5 = i2 - 1; i5 >= 0; i5--) {
                bArr2[i3] = bArr[(i5 * i) + i4];
                i3++;
            }
        }
        return bArr2;
    }

    public static byte[] rotate180(byte[] bArr, int i, int i2) {
        int i3 = i * i2;
        byte[] bArr2 = new byte[i3];
        int i4 = i3 - 1;
        for (int i5 = 0; i5 < i3; i5++) {
            bArr2[i4] = bArr[i5];
            i4--;
        }
        return bArr2;
    }

    public static byte[] rotateCCW(byte[] bArr, int i, int i2) {
        int i3 = i * i2;
        byte[] bArr2 = new byte[i3];
        int i4 = i3 - 1;
        for (int i5 = 0; i5 < i; i5++) {
            for (int i6 = i2 - 1; i6 >= 0; i6--) {
                bArr2[i4] = bArr[(i6 * i) + i5];
                i4--;
            }
        }
        return bArr2;
    }
}
