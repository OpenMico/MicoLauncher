package com.xiaomi.miplay.mylibrary.mirror.glec;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.nio.ByteBuffer;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import miuix.animation.internal.AnimTask;

/* loaded from: classes4.dex */
public class BlackCheck {
    private static String n;
    private BlackEdge a;
    private BlackEdge b;
    private BlackEdge c;
    private BlackEdge d;
    private BlackEdge e;
    private BlackEdge f;
    private int g;
    private int h;
    private int i;
    private int j;
    private Context k;
    private ReentrantReadWriteLock p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int l = 0;
    private byte[] m = null;
    private boolean o = false;
    private int u = AnimTask.MAX_PAGE_SIZE;
    private int v = 200;
    private int w = 50;
    private boolean x = false;

    /* loaded from: classes4.dex */
    public static class BlackEdge {
        int a;
        int b;
        int c;
        int d;
    }

    private int a(byte b) {
        return (((b >> 4) & 15) * 16) + (b & 15);
    }

    private int a(int i, int i2, int i3, int i4) {
        float f = i;
        float f2 = i2;
        float f3 = (i3 * 1.0f) / i4;
        return (f * 1.0f) / f2 > f3 ? (int) (f - (f2 * f3)) : (int) ((f / f3) - f2);
    }

    private static String a(Context context, String str) throws IllegalArgumentException {
        try {
            Class<?> loadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
            return (String) loadClass.getMethod(BluetoothConstants.GET, String.class).invoke(loadClass, new String(str));
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception unused) {
            return "";
        }
    }

    public BlackCheck(int i, int i2, int i3, int i4, Context context) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.k = null;
        this.q = 0;
        this.r = 0;
        this.s = 0;
        this.t = 0;
        this.a = new BlackEdge();
        this.b = new BlackEdge();
        this.c = new BlackEdge();
        this.d = new BlackEdge();
        this.e = new BlackEdge();
        this.f = new BlackEdge();
        this.g = i;
        this.h = i2;
        this.i = i3;
        this.j = i4;
        if (this.o) {
            this.p = new ReentrantReadWriteLock();
            this.p.writeLock().lock();
            try {
                this.s = i;
                this.t = i2;
            } finally {
                this.p.writeLock().unlock();
            }
        } else {
            this.s = i;
            this.t = i2;
        }
        int a = a(this.g, this.h, this.i, this.j);
        if (a > 0) {
            this.q = a / 2;
            this.r = 0;
        } else {
            this.q = 0;
            this.r = (-a) / 2;
        }
        Log.i("MiplayQuickBlackCheck", "checkBlack:" + a + " mCheckMaxWidth:" + this.q + " mCheckMaxHeight:" + this.r);
        this.k = context;
        Context context2 = this.k;
        if (context2 != null) {
            n = a(context2, "ro.build.product");
        }
        Log.i("MiplayQuickBlackCheck", "ProductName is " + n);
    }

    private boolean a(int i, int i2, byte[] bArr, int i3, int i4) {
        byte[] bArr2 = new byte[3];
        int i5 = (i4 * i) + i3;
        if (i5 >= i * i2) {
            return false;
        }
        int i6 = i5 * 4;
        bArr2[0] = bArr[i6];
        bArr2[1] = bArr[i6 + 1];
        bArr2[2] = bArr[i6 + 2];
        return a(bArr2[0]) <= 2 && a(bArr2[1]) <= 2 && a(bArr2[2]) <= 2;
    }

    /* JADX WARN: Finally extract failed */
    public boolean CheckRGB(byte[] bArr, BlackEdge blackEdge) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        int i7 = this.g;
        int i8 = this.h;
        if (this.o) {
            this.p.readLock().lock();
            try {
                int i9 = this.s;
                int i10 = this.t;
                this.p.readLock().unlock();
                i2 = i9;
                i = i10;
            } catch (Throwable th) {
                this.p.readLock().unlock();
                throw th;
            }
        } else {
            i2 = i7;
            i = i8;
        }
        int i11 = this.q;
        int i12 = this.r;
        int i13 = i12;
        for (int i14 = 0; i14 < i12; i14++) {
            int i15 = 0;
            while (true) {
                if (i15 >= i2) {
                    z4 = false;
                    break;
                } else if (!a(i2, i, bArr, i15, i14)) {
                    z4 = true;
                    i13 = i14;
                    break;
                } else {
                    i15++;
                }
            }
            if (z4) {
                break;
            }
        }
        int i16 = i12;
        int i17 = 0;
        while (true) {
            if (i17 >= i12) {
                i3 = i16;
                break;
            }
            int i18 = 0;
            while (true) {
                if (i18 >= i2) {
                    z3 = false;
                    break;
                } else if (!a(i2, i, bArr, i18, (i - i17) - 1)) {
                    z3 = true;
                    i16 = i17;
                    break;
                } else {
                    i18++;
                }
            }
            if (z3) {
                i3 = i16;
                break;
            }
            i17++;
        }
        int i19 = i11;
        int i20 = 0;
        while (true) {
            if (i20 >= i11) {
                i4 = i19;
                break;
            }
            int i21 = 0;
            while (true) {
                if (i21 >= i - 10) {
                    z2 = false;
                    break;
                } else if (!a(i2, i, bArr, i20, i21)) {
                    z2 = true;
                    i19 = i20;
                    break;
                } else {
                    i21++;
                }
            }
            if (z2) {
                i4 = i19;
                break;
            }
            i20++;
        }
        int i22 = i11;
        int i23 = 0;
        while (true) {
            if (i23 >= i11) {
                i5 = i22;
                break;
            }
            int i24 = 0;
            while (true) {
                if (i24 >= i - 10) {
                    i6 = i23;
                    z = false;
                    break;
                }
                i6 = i23;
                if (!a(i2, i, bArr, (i2 - i23) - 1, i24)) {
                    z = true;
                    i22 = i6;
                    break;
                }
                i24++;
                i23 = i6;
            }
            if (z) {
                i5 = i22;
                break;
            }
            i23 = i6 + 1;
        }
        blackEdge.a = i4;
        blackEdge.b = i5;
        blackEdge.c = i13;
        blackEdge.d = i3;
        return true;
    }

    private boolean b(int i, int i2, byte[] bArr, int i3, int i4) {
        byte[] bArr2 = new byte[3];
        int i5 = (i4 * i) + i3;
        if (i5 >= i * i2) {
            return false;
        }
        int i6 = i5 * 4;
        bArr2[0] = bArr[i6];
        bArr2[1] = bArr[i6 + 1];
        bArr2[2] = bArr[i6 + 2];
        return a(bArr2[0]) <= 5 && a(bArr2[1]) <= 5 && a(bArr2[2]) <= 5;
    }

    private int c(int i, int i2, byte[] bArr, int i3, int i4) {
        byte[] bArr2 = new byte[3];
        int i5 = 0;
        for (int i6 = 0; i6 < i; i6++) {
            int i7 = ((i4 * i) + i6) * 4;
            bArr2[0] = bArr[i7];
            bArr2[1] = bArr[i7 + 1];
            bArr2[2] = bArr[i7 + 2];
            i5 += a(bArr2[0]) + a(bArr2[1]) + a(bArr2[2]);
        }
        return i5;
    }

    /* JADX WARN: Finally extract failed */
    public boolean CheckRGB_All(byte[] bArr, BlackEdge blackEdge) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        int i7 = this.g;
        int i8 = this.h;
        if (this.o) {
            this.p.readLock().lock();
            try {
                int i9 = this.s;
                int i10 = this.t;
                this.p.readLock().unlock();
                i2 = i9;
                i = i10;
            } catch (Throwable th) {
                this.p.readLock().unlock();
                throw th;
            }
        } else {
            i2 = i7;
            i = i8;
        }
        int i11 = this.q;
        int i12 = this.r;
        int i13 = i12;
        for (int i14 = 0; i14 < i12; i14++) {
            int i15 = 0;
            while (true) {
                if (i15 >= i2) {
                    z4 = false;
                    break;
                } else if (!b(i2, i, bArr, i15, i14)) {
                    z4 = true;
                    i13 = i14;
                    break;
                } else {
                    i15++;
                }
            }
            if (z4) {
                break;
            }
        }
        int i16 = i12;
        int i17 = 0;
        while (true) {
            if (i17 >= i12) {
                i3 = i16;
                break;
            }
            int i18 = 0;
            while (true) {
                if (i18 >= i2) {
                    z3 = false;
                    break;
                } else if (!b(i2, i, bArr, i18, (i - i17) - 1)) {
                    z3 = true;
                    i16 = i17;
                    break;
                } else {
                    i18++;
                }
            }
            if (z3) {
                i3 = i16;
                break;
            }
            i17++;
        }
        int i19 = i11;
        int i20 = 0;
        while (true) {
            if (i20 >= i11) {
                i4 = i19;
                break;
            }
            int i21 = 0;
            while (true) {
                if (i21 >= i + 0) {
                    z2 = false;
                    break;
                } else if (!b(i2, i, bArr, i20, i21)) {
                    z2 = true;
                    i19 = i20;
                    break;
                } else {
                    i21++;
                }
            }
            if (z2) {
                i4 = i19;
                break;
            }
            i20++;
        }
        int i22 = i11;
        int i23 = 0;
        while (true) {
            if (i23 >= i11) {
                i5 = i22;
                break;
            }
            int i24 = 0;
            while (true) {
                if (i24 >= i + 0) {
                    i6 = i23;
                    z = false;
                    break;
                }
                i6 = i23;
                if (!b(i2, i, bArr, (i2 - i23) - 1, i24)) {
                    z = true;
                    i22 = i6;
                    break;
                }
                i24++;
                i23 = i6;
            }
            if (z) {
                i5 = i22;
                break;
            }
            i23 = i6 + 1;
        }
        blackEdge.a = i4;
        blackEdge.b = i5;
        blackEdge.c = i13;
        blackEdge.d = i3;
        return true;
    }

    /* JADX WARN: Finally extract failed */
    public boolean CheckRGB_DelTopOrBottomEdge(byte[] bArr, BlackEdge blackEdge) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        int i7 = this.g;
        int i8 = this.h;
        if (this.o) {
            this.p.readLock().lock();
            try {
                int i9 = this.s;
                int i10 = this.t;
                this.p.readLock().unlock();
                i2 = i9;
                i = i10;
            } catch (Throwable th) {
                this.p.readLock().unlock();
                throw th;
            }
        } else {
            i2 = i7;
            i = i8;
        }
        int i11 = this.q;
        int i12 = this.r;
        int i13 = i12;
        for (int i14 = 0; i14 < i12; i14++) {
            int i15 = 0;
            while (true) {
                if (i15 >= i2) {
                    z4 = false;
                    break;
                } else if (!b(i2, i, bArr, i15, i14)) {
                    z4 = true;
                    i13 = i14;
                    break;
                } else {
                    i15++;
                }
            }
            if (z4) {
                break;
            }
        }
        int i16 = i12;
        int i17 = 0;
        while (true) {
            if (i17 >= i12) {
                i3 = i16;
                break;
            }
            int i18 = 0;
            while (true) {
                if (i18 >= i2) {
                    z3 = false;
                    break;
                } else if (!b(i2, i, bArr, i18, (i - i17) - 1)) {
                    z3 = true;
                    i16 = i17;
                    break;
                } else {
                    i18++;
                }
            }
            if (z3) {
                i3 = i16;
                break;
            }
            i17++;
        }
        int i19 = i11;
        int i20 = 0;
        while (true) {
            if (i20 >= i11) {
                i4 = i19;
                break;
            }
            int i21 = this.u + 0;
            while (true) {
                if (i21 >= i - this.u) {
                    z2 = false;
                    break;
                } else if (!b(i2, i, bArr, i20, i21)) {
                    z2 = true;
                    i19 = i20;
                    break;
                } else {
                    i21++;
                }
            }
            if (z2) {
                i4 = i19;
                break;
            }
            i20++;
        }
        int i22 = i11;
        int i23 = 0;
        while (true) {
            if (i23 >= i11) {
                i5 = i22;
                break;
            }
            int i24 = this.u + 0;
            while (true) {
                if (i24 >= i - this.u) {
                    i6 = i23;
                    z = false;
                    break;
                }
                i6 = i23;
                if (!b(i2, i, bArr, (i2 - i23) - 1, i24)) {
                    z = true;
                    i22 = i6;
                    break;
                }
                i24++;
                i23 = i6;
            }
            if (z) {
                i5 = i22;
                break;
            }
            i23 = i6 + 1;
        }
        blackEdge.a = i4;
        blackEdge.b = i5;
        blackEdge.c = i13;
        blackEdge.d = i3;
        return true;
    }

    /* JADX WARN: Finally extract failed */
    public boolean CheckRGB_TopHalfEdge(byte[] bArr, BlackEdge blackEdge) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z;
        int i9;
        boolean z2;
        boolean z3;
        boolean z4;
        int i10 = this.g;
        int i11 = this.h;
        if (this.o) {
            this.p.readLock().lock();
            try {
                int i12 = this.s;
                int i13 = this.t;
                this.p.readLock().unlock();
                i2 = i12;
                i = i13;
            } catch (Throwable th) {
                this.p.readLock().unlock();
                throw th;
            }
        } else {
            i2 = i10;
            i = i11;
        }
        int i14 = this.q;
        int i15 = this.r;
        int i16 = i15;
        for (int i17 = 0; i17 < i15; i17++) {
            int i18 = 0;
            while (true) {
                if (i18 >= i2) {
                    z4 = false;
                    break;
                } else if (!b(i2, i, bArr, i18, i17)) {
                    z4 = true;
                    i16 = i17;
                    break;
                } else {
                    i18++;
                }
            }
            if (z4) {
                break;
            }
        }
        int i19 = i15;
        int i20 = 0;
        while (true) {
            if (i20 >= i15) {
                i3 = i19;
                break;
            }
            int i21 = 0;
            while (true) {
                if (i21 >= i2) {
                    z3 = false;
                    break;
                } else if (!b(i2, i, bArr, i21, (i - i20) - 1)) {
                    z3 = true;
                    i19 = i20;
                    break;
                } else {
                    i21++;
                }
            }
            if (z3) {
                i3 = i19;
                break;
            }
            i20++;
        }
        int i22 = this.v + 0;
        int i23 = (i / 2) - this.w;
        int i24 = i14;
        int i25 = 0;
        while (true) {
            if (i25 >= i14) {
                i4 = i23;
                i5 = i24;
                break;
            }
            int i26 = i22;
            while (true) {
                if (i26 >= i23) {
                    i9 = i25;
                    i4 = i23;
                    z2 = false;
                    break;
                }
                i9 = i25;
                i4 = i23;
                if (!b(i2, i, bArr, i25, i26)) {
                    z2 = true;
                    i24 = i9;
                    break;
                }
                i26++;
                i23 = i4;
                i25 = i9;
            }
            if (z2) {
                i5 = i24;
                break;
            }
            i25 = i9 + 1;
            i23 = i4;
        }
        int i27 = i14;
        int i28 = 0;
        while (true) {
            if (i28 >= i14) {
                i6 = i5;
                i7 = i27;
                break;
            }
            int i29 = i22;
            while (true) {
                if (i29 >= i4) {
                    i8 = i28;
                    i6 = i5;
                    z = false;
                    break;
                }
                i8 = i28;
                i6 = i5;
                if (!b(i2, i, bArr, (i2 - i28) - 1, i29)) {
                    i27 = i8;
                    z = true;
                    break;
                }
                i29++;
                i5 = i6;
                i28 = i8;
            }
            if (z) {
                i7 = i27;
                break;
            }
            i28 = i8 + 1;
            i5 = i6;
        }
        blackEdge.a = i6;
        blackEdge.b = i7;
        blackEdge.c = i16;
        blackEdge.d = i3;
        return true;
    }

    /* JADX WARN: Finally extract failed */
    public boolean CheckRGB_BottomHalfEdge(byte[] bArr, BlackEdge blackEdge) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z;
        int i9;
        boolean z2;
        boolean z3;
        boolean z4;
        int i10 = this.g;
        int i11 = this.h;
        if (this.o) {
            this.p.readLock().lock();
            try {
                int i12 = this.s;
                int i13 = this.t;
                this.p.readLock().unlock();
                i2 = i12;
                i = i13;
            } catch (Throwable th) {
                this.p.readLock().unlock();
                throw th;
            }
        } else {
            i2 = i10;
            i = i11;
        }
        int i14 = this.q;
        int i15 = this.r;
        int i16 = i15;
        for (int i17 = 0; i17 < i15; i17++) {
            int i18 = 0;
            while (true) {
                if (i18 >= i2) {
                    z4 = false;
                    break;
                } else if (!b(i2, i, bArr, i18, i17)) {
                    z4 = true;
                    i16 = i17;
                    break;
                } else {
                    i18++;
                }
            }
            if (z4) {
                break;
            }
        }
        int i19 = i15;
        int i20 = 0;
        while (true) {
            if (i20 >= i15) {
                i3 = i19;
                break;
            }
            int i21 = 0;
            while (true) {
                if (i21 >= i2) {
                    z3 = false;
                    break;
                } else if (!b(i2, i, bArr, i21, (i - i20) - 1)) {
                    z3 = true;
                    i19 = i20;
                    break;
                } else {
                    i21++;
                }
            }
            if (z3) {
                i3 = i19;
                break;
            }
            i20++;
        }
        int i22 = (i / 2) + this.w;
        int i23 = i - this.v;
        int i24 = i14;
        int i25 = 0;
        while (true) {
            if (i25 >= i14) {
                i4 = i23;
                i5 = i24;
                break;
            }
            int i26 = i22;
            while (true) {
                if (i26 >= i23) {
                    i9 = i25;
                    i4 = i23;
                    z2 = false;
                    break;
                }
                i9 = i25;
                i4 = i23;
                if (!b(i2, i, bArr, i25, i26)) {
                    z2 = true;
                    i24 = i9;
                    break;
                }
                i26++;
                i23 = i4;
                i25 = i9;
            }
            if (z2) {
                i5 = i24;
                break;
            }
            i25 = i9 + 1;
            i23 = i4;
        }
        int i27 = i14;
        int i28 = 0;
        while (true) {
            if (i28 >= i14) {
                i6 = i5;
                i7 = i27;
                break;
            }
            int i29 = i22;
            while (true) {
                if (i29 >= i4) {
                    i8 = i28;
                    i6 = i5;
                    z = false;
                    break;
                }
                i8 = i28;
                i6 = i5;
                if (!b(i2, i, bArr, (i2 - i28) - 1, i29)) {
                    i27 = i8;
                    z = true;
                    break;
                }
                i29++;
                i5 = i6;
                i28 = i8;
            }
            if (z) {
                i7 = i27;
                break;
            }
            i28 = i8 + 1;
            i5 = i6;
        }
        blackEdge.a = i6;
        blackEdge.b = i7;
        blackEdge.c = i16;
        blackEdge.d = i3;
        return true;
    }

    /* JADX WARN: Finally extract failed */
    public boolean CheckRGB_OneThirdEdge(byte[] bArr, BlackEdge blackEdge) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z;
        int i9;
        boolean z2;
        boolean z3;
        boolean z4;
        int i10 = this.g;
        int i11 = this.h;
        if (this.o) {
            this.p.readLock().lock();
            try {
                int i12 = this.s;
                int i13 = this.t;
                this.p.readLock().unlock();
                i2 = i12;
                i = i13;
            } catch (Throwable th) {
                this.p.readLock().unlock();
                throw th;
            }
        } else {
            i2 = i10;
            i = i11;
        }
        int i14 = this.q;
        int i15 = this.r;
        int i16 = i15;
        for (int i17 = 0; i17 < i15; i17++) {
            int i18 = 0;
            while (true) {
                if (i18 >= i2) {
                    z4 = false;
                    break;
                } else if (!b(i2, i, bArr, i18, i17)) {
                    z4 = true;
                    i16 = i17;
                    break;
                } else {
                    i18++;
                }
            }
            if (z4) {
                break;
            }
        }
        int i19 = i15;
        int i20 = 0;
        while (true) {
            if (i20 >= i15) {
                i3 = i19;
                break;
            }
            int i21 = 0;
            while (true) {
                if (i21 >= i2) {
                    z3 = false;
                    break;
                } else if (!b(i2, i, bArr, i21, (i - i20) - 1)) {
                    z3 = true;
                    i19 = i20;
                    break;
                } else {
                    i21++;
                }
            }
            if (z3) {
                i3 = i19;
                break;
            }
            i20++;
        }
        int i22 = this.v;
        int i23 = (i - i22) - 100;
        int i24 = i - i22;
        int i25 = i14;
        int i26 = 0;
        while (true) {
            if (i26 >= i14) {
                i4 = i24;
                i5 = i25;
                break;
            }
            int i27 = i23;
            while (true) {
                if (i27 >= i24) {
                    i9 = i26;
                    i4 = i24;
                    z2 = false;
                    break;
                }
                i9 = i26;
                i4 = i24;
                if (!b(i2, i, bArr, i26, i27)) {
                    z2 = true;
                    i25 = i9;
                    break;
                }
                i27++;
                i24 = i4;
                i26 = i9;
            }
            if (z2) {
                i5 = i25;
                break;
            }
            i26 = i9 + 1;
            i24 = i4;
        }
        int i28 = i14;
        int i29 = 0;
        while (true) {
            if (i29 >= i14) {
                i6 = i5;
                i7 = i28;
                break;
            }
            int i30 = i23;
            while (true) {
                if (i30 >= i4) {
                    i8 = i29;
                    i6 = i5;
                    z = false;
                    break;
                }
                i8 = i29;
                i6 = i5;
                if (!b(i2, i, bArr, (i2 - i29) - 1, i30)) {
                    i28 = i8;
                    z = true;
                    break;
                }
                i30++;
                i5 = i6;
                i29 = i8;
            }
            if (z) {
                i7 = i28;
                break;
            }
            i29 = i8 + 1;
            i5 = i6;
        }
        blackEdge.a = i6;
        blackEdge.b = i7;
        blackEdge.c = i16;
        blackEdge.d = i3;
        return true;
    }

    /* JADX WARN: Finally extract failed */
    public boolean CheckRGB_Barrage(byte[] bArr, BlackEdge blackEdge) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        int i9 = this.g;
        int i10 = this.h;
        if (this.o) {
            this.p.readLock().lock();
            try {
                int i11 = this.s;
                int i12 = this.t;
                this.p.readLock().unlock();
                i = i11;
                i2 = i12;
            } catch (Throwable th) {
                this.p.readLock().unlock();
                throw th;
            }
        } else {
            i = i9;
            i2 = i10;
        }
        int i13 = this.q;
        int i14 = this.r;
        int i15 = (i2 * 3) / 5;
        int i16 = i14;
        int i17 = 0;
        while (true) {
            if (i17 >= i14) {
                i3 = 15000;
                i4 = i16;
                break;
            }
            i3 = 15000;
            if (c(i, i2, bArr, 0, i17) >= 15000) {
                int i18 = 0;
                while (true) {
                    if (i18 >= i) {
                        z4 = false;
                        break;
                    } else if (!b(i, i2, bArr, i18, i17)) {
                        z4 = true;
                        i16 = i17;
                        break;
                    } else {
                        i18++;
                    }
                }
                if (z4) {
                    i4 = i16;
                    break;
                }
            }
            i17++;
        }
        int i19 = i14;
        int i20 = 0;
        while (true) {
            if (i20 >= i14) {
                i5 = i19;
                break;
            }
            if (c(i, i2, bArr, 0, i20) >= i3) {
                int i21 = 0;
                while (true) {
                    if (i21 >= i) {
                        z3 = false;
                        break;
                    } else if (!b(i, i2, bArr, i21, (i2 - i20) - 1)) {
                        z3 = true;
                        i19 = i20;
                        break;
                    } else {
                        i21++;
                    }
                }
                if (z3) {
                    i5 = i19;
                    break;
                }
            }
            i20++;
        }
        int i22 = i13;
        int i23 = 0;
        while (true) {
            if (i23 >= i13) {
                i6 = i22;
                break;
            }
            int i24 = i15;
            while (true) {
                if (i24 >= i2 - this.u) {
                    z2 = false;
                    break;
                } else if (!b(i, i2, bArr, i23, i24)) {
                    z2 = true;
                    i22 = i23;
                    break;
                } else {
                    i24++;
                }
            }
            if (z2) {
                i6 = i22;
                break;
            }
            i23++;
        }
        int i25 = i13;
        int i26 = 0;
        while (true) {
            if (i26 >= i13) {
                i7 = i25;
                break;
            }
            int i27 = i15;
            while (true) {
                if (i27 >= i2 - this.u) {
                    i8 = i26;
                    z = false;
                    break;
                }
                i8 = i26;
                if (!b(i, i2, bArr, (i - i26) - 1, i27)) {
                    z = true;
                    i25 = i8;
                    break;
                }
                i27++;
                i26 = i8;
            }
            if (z) {
                i7 = i25;
                break;
            }
            i26 = i8 + 1;
        }
        blackEdge.a = i6;
        blackEdge.b = i7;
        blackEdge.c = i4;
        blackEdge.d = i5;
        return true;
    }

    public static int getStatusBarHeight(Context context) {
        String str = n;
        if (str != null && str.equals("ginkgo")) {
            return 80;
        }
        String str2 = n;
        if (str2 != null && str2.equals("lavender")) {
            return 79;
        }
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public boolean CheckBlackEdge(Bitmap bitmap, int[] iArr, int[] iArr2) {
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        ByteBuffer allocate = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(allocate);
        this.m = allocate.array();
        CheckRGB_Barrage(this.m, this.c);
        if (this.q == 0 && this.r > 0 && !(this.c.c == this.c.d && this.c.c == this.r)) {
            BlackEdge blackEdge = this.c;
            blackEdge.c = 0;
            blackEdge.d = 0;
        }
        if (this.x) {
            Log.i("MiplayQuickBlackCheck", "Bar Top:" + this.c.c + " Bottom:" + this.c.d + " Left:" + this.c.a + " Right:" + this.c.b);
        }
        iArr[0] = this.c.a;
        iArr[1] = this.c.b;
        iArr[2] = this.c.c;
        iArr[3] = this.c.d;
        if (this.c.a == this.q && this.c.b == 0) {
            iArr2[0] = 1;
            iArr2[1] = 0;
            iArr2[2] = 0;
        } else if (this.c.b == this.q && this.c.a == 0) {
            iArr2[0] = 1;
            iArr2[1] = 0;
            iArr2[2] = 0;
        } else if (this.c.a == this.q && this.c.a == this.c.b) {
            iArr2[0] = 1;
            iArr2[1] = 0;
            iArr2[2] = 0;
        } else if (this.c.a != this.q || this.c.a <= this.c.b) {
            if (this.c.b != this.q || this.c.a >= this.c.b) {
                if (this.c.a < this.q && this.c.a < this.q) {
                    CheckRGB_OneThirdEdge(this.m, this.f);
                    if (this.x) {
                        Log.i("MiplayQuickBlackCheck", "1/3 Top:" + this.f.c + " Bottom:" + this.f.d + " Left:" + this.f.a + " Right:" + this.f.b);
                    }
                    if (this.f.a == this.q && this.f.b == 0) {
                        iArr[0] = this.f.a;
                        iArr[1] = this.f.b;
                        iArr[2] = this.f.c;
                        iArr[3] = this.f.d;
                        iArr2[0] = 1;
                        iArr2[1] = 0;
                        iArr2[2] = 0;
                    } else if (this.f.b == this.q && this.f.a == 0) {
                        iArr[0] = this.f.a;
                        iArr[1] = this.f.b;
                        iArr[2] = this.f.c;
                        iArr[3] = this.f.d;
                        iArr2[0] = 1;
                        iArr2[1] = 0;
                        iArr2[2] = 0;
                    } else if (this.f.a == this.f.b && this.f.b == this.q) {
                        iArr[0] = this.f.a;
                        iArr[1] = this.f.b;
                        iArr[2] = this.f.c;
                        iArr[3] = this.f.d;
                        iArr2[0] = 1;
                        iArr2[1] = 0;
                        iArr2[2] = 0;
                    } else if (this.f.a == this.q && this.f.a > this.f.b) {
                        iArr[0] = this.f.a;
                        iArr[1] = this.f.b;
                        iArr[2] = this.f.c;
                        iArr[3] = this.f.d;
                        if (this.f.b >= this.q - 25) {
                            iArr2[0] = 1;
                            iArr2[1] = 0;
                            iArr2[2] = 0;
                        } else {
                            iArr2[0] = 1;
                            iArr2[1] = -1;
                            iArr2[2] = 0;
                        }
                    } else if (this.f.b != this.q || this.f.a >= this.f.b) {
                        iArr[0] = 0;
                        iArr[1] = 0;
                        iArr[2] = 0;
                        iArr[3] = 0;
                        iArr2[0] = 0;
                        iArr2[1] = 0;
                        iArr2[2] = 0;
                    } else {
                        iArr[0] = this.f.a;
                        iArr[1] = this.f.b;
                        iArr[2] = this.f.c;
                        iArr[3] = this.f.d;
                        if (this.f.a >= this.q - 25) {
                            iArr2[0] = 1;
                            iArr2[1] = 0;
                            iArr2[2] = 0;
                        } else {
                            iArr2[0] = 1;
                            iArr2[1] = 1;
                            iArr2[2] = 0;
                        }
                    }
                }
            } else if (this.c.a >= this.q - 25) {
                iArr2[0] = 1;
                iArr2[1] = 0;
                iArr2[2] = 0;
            } else {
                iArr2[0] = 1;
                iArr2[1] = 1;
                iArr2[2] = 0;
            }
        } else if (this.c.b >= this.q - 25) {
            iArr2[0] = 1;
            iArr2[1] = 0;
            iArr2[2] = 0;
        } else {
            iArr2[0] = 1;
            iArr2[1] = -1;
            iArr2[2] = 0;
        }
        return true;
    }
}
