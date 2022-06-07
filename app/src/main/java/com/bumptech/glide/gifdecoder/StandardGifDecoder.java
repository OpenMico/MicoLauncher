package com.bumptech.glide.gifdecoder;

import android.graphics.Bitmap;
import android.util.Log;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.gifdecoder.GifDecoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes.dex */
public class StandardGifDecoder implements GifDecoder {
    private static final String a = "StandardGifDecoder";
    @ColorInt
    private int[] b;
    @ColorInt
    private final int[] c;
    private final GifDecoder.BitmapProvider d;
    private ByteBuffer e;
    private byte[] f;
    private GifHeaderParser g;
    private short[] h;
    private byte[] i;
    private byte[] j;
    private byte[] k;
    @ColorInt
    private int[] l;
    private int m;
    private GifHeader n;
    private Bitmap o;
    private boolean p;
    private int q;
    private int r;
    private int s;
    private int t;
    @Nullable
    private Boolean u;
    @NonNull
    private Bitmap.Config v;

    public StandardGifDecoder(@NonNull GifDecoder.BitmapProvider bitmapProvider, GifHeader gifHeader, ByteBuffer byteBuffer) {
        this(bitmapProvider, gifHeader, byteBuffer, 1);
    }

    public StandardGifDecoder(@NonNull GifDecoder.BitmapProvider bitmapProvider, GifHeader gifHeader, ByteBuffer byteBuffer, int i) {
        this(bitmapProvider);
        setData(gifHeader, byteBuffer, i);
    }

    public StandardGifDecoder(@NonNull GifDecoder.BitmapProvider bitmapProvider) {
        this.c = new int[256];
        this.v = Bitmap.Config.ARGB_8888;
        this.d = bitmapProvider;
        this.n = new GifHeader();
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getWidth() {
        return this.n.f;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getHeight() {
        return this.n.g;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    @NonNull
    public ByteBuffer getData() {
        return this.e;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getStatus() {
        return this.q;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void advance() {
        this.m = (this.m + 1) % this.n.c;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getDelay(int i) {
        if (i < 0 || i >= this.n.c) {
            return -1;
        }
        return this.n.e.get(i).i;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getNextDelay() {
        int i;
        if (this.n.c <= 0 || (i = this.m) < 0) {
            return 0;
        }
        return getDelay(i);
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getFrameCount() {
        return this.n.c;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getCurrentFrameIndex() {
        return this.m;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void resetFrameIndex() {
        this.m = -1;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    @Deprecated
    public int getLoopCount() {
        if (this.n.m == -1) {
            return 1;
        }
        return this.n.m;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getNetscapeLoopCount() {
        return this.n.m;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getTotalIterationCount() {
        if (this.n.m == -1) {
            return 1;
        }
        if (this.n.m == 0) {
            return 0;
        }
        return this.n.m + 1;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int getByteSize() {
        return this.e.limit() + this.k.length + (this.l.length * 4);
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    @Nullable
    public synchronized Bitmap getNextFrame() {
        if (this.n.c <= 0 || this.m < 0) {
            if (Log.isLoggable(a, 3)) {
                String str = a;
                Log.d(str, "Unable to decode frame, frameCount=" + this.n.c + ", framePointer=" + this.m);
            }
            this.q = 1;
        }
        if (!(this.q == 1 || this.q == 2)) {
            this.q = 0;
            if (this.f == null) {
                this.f = this.d.obtainByteArray(255);
            }
            a aVar = this.n.e.get(this.m);
            int i = this.m - 1;
            a aVar2 = i >= 0 ? this.n.e.get(i) : null;
            this.b = aVar.k != null ? aVar.k : this.n.a;
            if (this.b == null) {
                if (Log.isLoggable(a, 3)) {
                    String str2 = a;
                    Log.d(str2, "No valid color table found for frame #" + this.m);
                }
                this.q = 1;
                return null;
            }
            if (aVar.f) {
                System.arraycopy(this.b, 0, this.c, 0, this.b.length);
                this.b = this.c;
                this.b[aVar.h] = 0;
                if (aVar.g == 2 && this.m == 0) {
                    this.u = true;
                }
            }
            return a(aVar, aVar2);
        }
        if (Log.isLoggable(a, 3)) {
            String str3 = a;
            Log.d(str3, "Unable to decode frame, status=" + this.q);
        }
        return null;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public int read(@Nullable InputStream inputStream, int i) {
        if (inputStream != null) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i > 0 ? i + 4096 : 16384);
                byte[] bArr = new byte[16384];
                while (true) {
                    int read = inputStream.read(bArr, 0, bArr.length);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                byteArrayOutputStream.flush();
                read(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                Log.w(a, "Error reading data from stream", e);
            }
        } else {
            this.q = 2;
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e2) {
                Log.w(a, "Error closing stream", e2);
            }
        }
        return this.q;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void clear() {
        this.n = null;
        byte[] bArr = this.k;
        if (bArr != null) {
            this.d.release(bArr);
        }
        int[] iArr = this.l;
        if (iArr != null) {
            this.d.release(iArr);
        }
        Bitmap bitmap = this.o;
        if (bitmap != null) {
            this.d.release(bitmap);
        }
        this.o = null;
        this.e = null;
        this.u = null;
        byte[] bArr2 = this.f;
        if (bArr2 != null) {
            this.d.release(bArr2);
        }
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public synchronized void setData(@NonNull GifHeader gifHeader, @NonNull byte[] bArr) {
        setData(gifHeader, ByteBuffer.wrap(bArr));
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public synchronized void setData(@NonNull GifHeader gifHeader, @NonNull ByteBuffer byteBuffer) {
        setData(gifHeader, byteBuffer, 1);
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public synchronized void setData(@NonNull GifHeader gifHeader, @NonNull ByteBuffer byteBuffer, int i) {
        if (i > 0) {
            int highestOneBit = Integer.highestOneBit(i);
            this.q = 0;
            this.n = gifHeader;
            this.m = -1;
            this.e = byteBuffer.asReadOnlyBuffer();
            this.e.position(0);
            this.e.order(ByteOrder.LITTLE_ENDIAN);
            this.p = false;
            Iterator<a> it = gifHeader.e.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().g == 3) {
                        this.p = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            this.r = highestOneBit;
            this.t = gifHeader.f / highestOneBit;
            this.s = gifHeader.g / highestOneBit;
            this.k = this.d.obtainByteArray(gifHeader.f * gifHeader.g);
            this.l = this.d.obtainIntArray(this.t * this.s);
        } else {
            throw new IllegalArgumentException("Sample size must be >=0, not: " + i);
        }
    }

    @NonNull
    private GifHeaderParser a() {
        if (this.g == null) {
            this.g = new GifHeaderParser();
        }
        return this.g;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public synchronized int read(@Nullable byte[] bArr) {
        this.n = a().setData(bArr).parseHeader();
        if (bArr != null) {
            setData(this.n, bArr);
        }
        return this.q;
    }

    @Override // com.bumptech.glide.gifdecoder.GifDecoder
    public void setDefaultBitmapConfig(@NonNull Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888 || config == Bitmap.Config.RGB_565) {
            this.v = config;
            return;
        }
        throw new IllegalArgumentException("Unsupported format: " + config + ", must be one of " + Bitmap.Config.ARGB_8888 + " or " + Bitmap.Config.RGB_565);
    }

    private Bitmap a(a aVar, a aVar2) {
        Bitmap bitmap;
        int[] iArr = this.l;
        int i = 0;
        if (aVar2 == null) {
            Bitmap bitmap2 = this.o;
            if (bitmap2 != null) {
                this.d.release(bitmap2);
            }
            this.o = null;
            Arrays.fill(iArr, 0);
        }
        if (aVar2 != null && aVar2.g == 3 && this.o == null) {
            Arrays.fill(iArr, 0);
        }
        if (aVar2 != null && aVar2.g > 0) {
            if (aVar2.g == 2) {
                if (!aVar.f) {
                    i = this.n.l;
                    if (aVar.k == null || this.n.j != aVar.h) {
                    }
                }
                int i2 = aVar2.d / this.r;
                int i3 = aVar2.b / this.r;
                int i4 = aVar2.c / this.r;
                int i5 = aVar2.a / this.r;
                int i6 = this.t;
                int i7 = (i3 * i6) + i5;
                int i8 = (i2 * i6) + i7;
                while (i7 < i8) {
                    int i9 = i7 + i4;
                    for (int i10 = i7; i10 < i9; i10++) {
                        iArr[i10] = i;
                    }
                    i7 += this.t;
                }
            } else if (aVar2.g == 3 && (bitmap = this.o) != null) {
                int i11 = this.t;
                bitmap.getPixels(iArr, 0, i11, 0, 0, i11, this.s);
            }
        }
        c(aVar);
        if (aVar.e || this.r != 1) {
            b(aVar);
        } else {
            a(aVar);
        }
        if (this.p && (aVar.g == 0 || aVar.g == 1)) {
            if (this.o == null) {
                this.o = d();
            }
            Bitmap bitmap3 = this.o;
            int i12 = this.t;
            bitmap3.setPixels(iArr, 0, i12, 0, 0, i12, this.s);
        }
        Bitmap d = d();
        int i13 = this.t;
        d.setPixels(iArr, 0, i13, 0, 0, i13, this.s);
        return d;
    }

    private void a(a aVar) {
        a aVar2 = aVar;
        int[] iArr = this.l;
        int i = aVar2.d;
        int i2 = aVar2.b;
        int i3 = aVar2.c;
        int i4 = aVar2.a;
        boolean z = this.m == 0;
        int i5 = this.t;
        byte[] bArr = this.k;
        int[] iArr2 = this.b;
        int i6 = 0;
        byte b = -1;
        while (i6 < i) {
            int i7 = (i6 + i2) * i5;
            int i8 = i7 + i4;
            int i9 = i8 + i3;
            int i10 = i7 + i5;
            if (i10 < i9) {
                i9 = i10;
            }
            int i11 = aVar2.c * i6;
            for (int i12 = i8; i12 < i9; i12++) {
                byte b2 = bArr[i11];
                int i13 = b2 & 255;
                if (i13 != b) {
                    int i14 = iArr2[i13];
                    if (i14 != 0) {
                        iArr[i12] = i14;
                    } else {
                        b = b2;
                    }
                }
                i11++;
            }
            i6++;
            aVar2 = aVar;
        }
        Boolean bool = this.u;
        this.u = Boolean.valueOf((bool != null && bool.booleanValue()) || (this.u == null && z && b != -1));
    }

    private void b(a aVar) {
        int[] iArr = this.l;
        int i = aVar.b / this.r;
        int i2 = aVar.c / this.r;
        int i3 = aVar.a / this.r;
        boolean z = this.m == 0;
        int i4 = this.r;
        int i5 = this.t;
        int i6 = this.s;
        byte[] bArr = this.k;
        int[] iArr2 = this.b;
        int i7 = 8;
        int i8 = 0;
        int i9 = 1;
        Boolean bool = this.u;
        int i10 = 0;
        for (int i11 = aVar.d / this.r; i10 < i11; i11 = i11) {
            if (aVar.e) {
                if (i8 >= i11) {
                    i9++;
                    switch (i9) {
                        case 2:
                            i8 = 4;
                            break;
                        case 3:
                            i8 = 2;
                            i7 = 4;
                            break;
                        case 4:
                            i7 = 2;
                            i8 = 1;
                            break;
                    }
                }
                i8 += i7;
            } else {
                i8 = i8;
                i8 = i10;
            }
            int i12 = i8 + i;
            boolean z2 = i4 == 1;
            if (i12 < i6) {
                int i13 = i12 * i5;
                int i14 = i13 + i3;
                i = i;
                int i15 = i14 + i2;
                int i16 = i13 + i5;
                if (i16 < i15) {
                    i15 = i16;
                }
                i2 = i2;
                int i17 = i10 * i4 * aVar.c;
                if (z2) {
                    for (int i18 = i14; i18 < i15; i18++) {
                        int i19 = iArr2[bArr[i17] & 255];
                        if (i19 != 0) {
                            iArr[i18] = i19;
                        } else if (z && bool == null) {
                            bool = true;
                        }
                        i17 += i4;
                    }
                } else {
                    int i20 = ((i15 - i14) * i4) + i17;
                    int i21 = i14;
                    while (i21 < i15) {
                        int a2 = a(i17, i20, aVar.c);
                        if (a2 != 0) {
                            iArr[i21] = a2;
                        } else if (z && bool == null) {
                            bool = true;
                        }
                        i17 += i4;
                        i21++;
                        i15 = i15;
                    }
                }
            } else {
                i = i;
                i2 = i2;
            }
            i10++;
        }
        if (this.u == null) {
            this.u = Boolean.valueOf(bool == null ? false : bool.booleanValue());
        }
    }

    @ColorInt
    private int a(int i, int i2, int i3) {
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        for (int i9 = i; i9 < this.r + i; i9++) {
            byte[] bArr = this.k;
            if (i9 >= bArr.length || i9 >= i2) {
                break;
            }
            int i10 = this.b[bArr[i9] & 255];
            if (i10 != 0) {
                i4 += (i10 >> 24) & 255;
                i5 += (i10 >> 16) & 255;
                i6 += (i10 >> 8) & 255;
                i7 += i10 & 255;
                i8++;
            }
        }
        int i11 = i + i3;
        for (int i12 = i11; i12 < this.r + i11; i12++) {
            byte[] bArr2 = this.k;
            if (i12 >= bArr2.length || i12 >= i2) {
                break;
            }
            int i13 = this.b[bArr2[i12] & 255];
            if (i13 != 0) {
                i4 += (i13 >> 24) & 255;
                i5 += (i13 >> 16) & 255;
                i6 += (i13 >> 8) & 255;
                i7 += i13 & 255;
                i8++;
            }
        }
        if (i8 == 0) {
            return 0;
        }
        return ((i4 / i8) << 24) | ((i5 / i8) << 16) | ((i6 / i8) << 8) | (i7 / i8);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void c(a aVar) {
        int i;
        byte b;
        short s;
        StandardGifDecoder standardGifDecoder = this;
        if (aVar != null) {
            standardGifDecoder.e.position(aVar.j);
        }
        int i2 = aVar == null ? standardGifDecoder.n.f * standardGifDecoder.n.g : aVar.d * aVar.c;
        byte[] bArr = standardGifDecoder.k;
        if (bArr == null || bArr.length < i2) {
            standardGifDecoder.k = standardGifDecoder.d.obtainByteArray(i2);
        }
        byte[] bArr2 = standardGifDecoder.k;
        if (standardGifDecoder.h == null) {
            standardGifDecoder.h = new short[4096];
        }
        short[] sArr = standardGifDecoder.h;
        if (standardGifDecoder.i == null) {
            standardGifDecoder.i = new byte[4096];
        }
        byte[] bArr3 = standardGifDecoder.i;
        if (standardGifDecoder.j == null) {
            standardGifDecoder.j = new byte[4097];
        }
        byte[] bArr4 = standardGifDecoder.j;
        int b2 = b();
        int i3 = 1 << b2;
        int i4 = i3 + 1;
        int i5 = i3 + 2;
        int i6 = b2 + 1;
        int i7 = (1 << i6) - 1;
        int i8 = 0;
        for (int i9 = 0; i9 < i3; i9++) {
            sArr[i9] = 0;
            bArr3[i9] = (byte) i9;
        }
        byte[] bArr5 = standardGifDecoder.f;
        int i10 = -1;
        int i11 = i6;
        int i12 = i5;
        int i13 = i7;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        int i20 = 0;
        while (true) {
            if (i8 >= i2) {
                i = i18;
                b = 0;
                break;
            }
            if (i14 == 0) {
                i14 = c();
                if (i14 <= 0) {
                    standardGifDecoder.q = 3;
                    i = i18;
                    b = 0;
                    break;
                }
                i17 = 0;
            }
            i16 += (bArr5[i17] & 255) << i15;
            int i21 = i15 + 8;
            i17++;
            i14--;
            int i22 = i18;
            int i23 = i8;
            while (true) {
                if (i21 < i11) {
                    i11 = i11;
                    i19 = i19;
                    i8 = i23;
                    i18 = i22;
                    i15 = i21;
                    i12 = i12;
                    i10 = i10;
                    standardGifDecoder = this;
                    break;
                }
                int i24 = i16 & i13;
                i16 >>= i11;
                i21 -= i11;
                if (i24 == i3) {
                    i11 = i6;
                    i12 = i5;
                    i13 = i7;
                    i10 = -1;
                } else if (i24 == i4) {
                    i15 = i21;
                    i11 = i11;
                    i8 = i23;
                    i18 = i22;
                    i12 = i12;
                    i19 = i19;
                    i10 = i10;
                    break;
                } else if (i10 == -1) {
                    bArr2[i22] = bArr3[i24];
                    i22++;
                    i23++;
                    i10 = i24;
                    i19 = i10;
                    standardGifDecoder = this;
                } else {
                    i12 = i12;
                    if (i24 >= i12) {
                        i21 = i21;
                        bArr4[i20] = (byte) i19;
                        i20++;
                        s = i10;
                    } else {
                        i21 = i21;
                        s = i24;
                    }
                    while (s >= i3) {
                        bArr4[i20] = bArr3[s];
                        i20++;
                        s = sArr[s];
                    }
                    int i25 = bArr3[s] & 255;
                    byte b3 = (byte) i25;
                    bArr2[i22] = b3;
                    i22++;
                    i23++;
                    while (i20 > 0) {
                        i20--;
                        bArr2[i22] = bArr4[i20];
                        i22++;
                        i23++;
                    }
                    if (i12 < 4096) {
                        sArr[i12] = (short) i10;
                        bArr3[i12] = b3;
                        i12++;
                        if ((i12 & i13) == 0 && i12 < 4096) {
                            i11++;
                            i13 += i12;
                        }
                    }
                    i10 = i24;
                    i6 = i6;
                    i19 = i25;
                    standardGifDecoder = this;
                }
            }
        }
        Arrays.fill(bArr2, i, i2, b);
    }

    private int b() {
        return this.e.get() & 255;
    }

    private int c() {
        int b = b();
        if (b <= 0) {
            return b;
        }
        ByteBuffer byteBuffer = this.e;
        byteBuffer.get(this.f, 0, Math.min(b, byteBuffer.remaining()));
        return b;
    }

    private Bitmap d() {
        Boolean bool = this.u;
        Bitmap obtain = this.d.obtain(this.t, this.s, (bool == null || bool.booleanValue()) ? Bitmap.Config.ARGB_8888 : this.v);
        obtain.setHasAlpha(true);
        return obtain;
    }
}
