package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.util.InternCache;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class ByteQuadsCanonicalizer {
    private final ByteQuadsCanonicalizer a;
    private final AtomicReference<a> b;
    private final int c;
    private boolean d;
    private final boolean e;
    private int[] f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private String[] l;
    private int m;
    private int n;
    private transient boolean o;
    private boolean p;

    static int a(int i) {
        int i2 = i >> 2;
        if (i2 < 64) {
            return 4;
        }
        if (i2 <= 256) {
            return 5;
        }
        return i2 <= 1024 ? 6 : 7;
    }

    private ByteQuadsCanonicalizer(int i, boolean z, int i2, boolean z2) {
        this.a = null;
        this.c = i2;
        this.d = z;
        this.e = z2;
        int i3 = 16;
        if (i >= 16) {
            if (((i - 1) & i) != 0) {
                while (i3 < i) {
                    i3 += i3;
                }
            } else {
                i3 = i;
            }
        }
        this.b = new AtomicReference<>(a.a(i3));
    }

    private ByteQuadsCanonicalizer(ByteQuadsCanonicalizer byteQuadsCanonicalizer, boolean z, int i, boolean z2, a aVar) {
        this.a = byteQuadsCanonicalizer;
        this.c = i;
        this.d = z;
        this.e = z2;
        this.b = null;
        this.k = aVar.b;
        this.g = aVar.a;
        this.h = this.g << 2;
        int i2 = this.h;
        this.i = i2 + (i2 >> 1);
        this.j = aVar.c;
        this.f = aVar.d;
        this.l = aVar.e;
        this.m = aVar.f;
        this.n = aVar.g;
        this.o = false;
        this.p = true;
    }

    public static ByteQuadsCanonicalizer createRoot() {
        long currentTimeMillis = System.currentTimeMillis();
        return createRoot((((int) currentTimeMillis) + ((int) (currentTimeMillis >>> 32))) | 1);
    }

    protected static ByteQuadsCanonicalizer createRoot(int i) {
        return new ByteQuadsCanonicalizer(64, true, i, true);
    }

    public ByteQuadsCanonicalizer makeChild(int i) {
        return new ByteQuadsCanonicalizer(this, JsonFactory.Feature.INTERN_FIELD_NAMES.enabledIn(i), this.c, JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(i), this.b.get());
    }

    public void release() {
        if (this.a != null && maybeDirty()) {
            this.a.a(new a(this));
            this.p = true;
        }
    }

    private void a(a aVar) {
        int i = aVar.b;
        a aVar2 = this.b.get();
        if (i != aVar2.b) {
            if (i > 6000) {
                aVar = a.a(64);
            }
            this.b.compareAndSet(aVar2, aVar);
        }
    }

    public int size() {
        AtomicReference<a> atomicReference = this.b;
        if (atomicReference != null) {
            return atomicReference.get().b;
        }
        return this.k;
    }

    public int bucketCount() {
        return this.g;
    }

    public boolean maybeDirty() {
        return !this.p;
    }

    public int hashSeed() {
        return this.c;
    }

    public int primaryCount() {
        int i = this.h;
        int i2 = 0;
        for (int i3 = 3; i3 < i; i3 += 4) {
            if (this.f[i3] != 0) {
                i2++;
            }
        }
        return i2;
    }

    public int secondaryCount() {
        int i = this.i;
        int i2 = 0;
        for (int i3 = this.h + 3; i3 < i; i3 += 4) {
            if (this.f[i3] != 0) {
                i2++;
            }
        }
        return i2;
    }

    public int tertiaryCount() {
        int i = this.i + 3;
        int i2 = this.g + i;
        int i3 = 0;
        while (i < i2) {
            if (this.f[i] != 0) {
                i3++;
            }
            i += 4;
        }
        return i3;
    }

    public int spilloverCount() {
        return (this.m - d()) >> 2;
    }

    public int totalCount() {
        int i = this.g << 3;
        int i2 = 0;
        for (int i3 = 3; i3 < i; i3 += 4) {
            if (this.f[i3] != 0) {
                i2++;
            }
        }
        return i2;
    }

    public String toString() {
        int primaryCount = primaryCount();
        int secondaryCount = secondaryCount();
        int tertiaryCount = tertiaryCount();
        int spilloverCount = spilloverCount();
        return String.format("[%s: size=%d, hashSize=%d, %d/%d/%d/%d pri/sec/ter/spill (=%s), total:%d]", getClass().getName(), Integer.valueOf(this.k), Integer.valueOf(this.g), Integer.valueOf(primaryCount), Integer.valueOf(secondaryCount), Integer.valueOf(tertiaryCount), Integer.valueOf(spilloverCount), Integer.valueOf(primaryCount + secondaryCount + tertiaryCount + spilloverCount), Integer.valueOf(totalCount()));
    }

    public String findName(int i) {
        int b = b(calcHash(i));
        int[] iArr = this.f;
        int i2 = iArr[b + 3];
        if (i2 == 1) {
            if (iArr[b] == i) {
                return this.l[b >> 2];
            }
        } else if (i2 == 0) {
            return null;
        }
        int i3 = this.h + ((b >> 3) << 2);
        int i4 = iArr[i3 + 3];
        if (i4 == 1) {
            if (iArr[i3] == i) {
                return this.l[i3 >> 2];
            }
        } else if (i4 == 0) {
            return null;
        }
        return a(b, i);
    }

    public String findName(int i, int i2) {
        int b = b(calcHash(i, i2));
        int[] iArr = this.f;
        int i3 = iArr[b + 3];
        if (i3 == 2) {
            if (i == iArr[b] && i2 == iArr[b + 1]) {
                return this.l[b >> 2];
            }
        } else if (i3 == 0) {
            return null;
        }
        int i4 = this.h + ((b >> 3) << 2);
        int i5 = iArr[i4 + 3];
        if (i5 == 2) {
            if (i == iArr[i4] && i2 == iArr[i4 + 1]) {
                return this.l[i4 >> 2];
            }
        } else if (i5 == 0) {
            return null;
        }
        return a(b, i, i2);
    }

    public String findName(int i, int i2, int i3) {
        int b = b(calcHash(i, i2, i3));
        int[] iArr = this.f;
        int i4 = iArr[b + 3];
        if (i4 == 3) {
            if (i == iArr[b] && iArr[b + 1] == i2 && iArr[b + 2] == i3) {
                return this.l[b >> 2];
            }
        } else if (i4 == 0) {
            return null;
        }
        int i5 = this.h + ((b >> 3) << 2);
        int i6 = iArr[i5 + 3];
        if (i6 == 3) {
            if (i == iArr[i5] && iArr[i5 + 1] == i2 && iArr[i5 + 2] == i3) {
                return this.l[i5 >> 2];
            }
        } else if (i6 == 0) {
            return null;
        }
        return a(b, i, i2, i3);
    }

    public String findName(int[] iArr, int i) {
        if (i < 4) {
            switch (i) {
                case 1:
                    return findName(iArr[0]);
                case 2:
                    return findName(iArr[0], iArr[1]);
                case 3:
                    return findName(iArr[0], iArr[1], iArr[2]);
                default:
                    return "";
            }
        } else {
            int calcHash = calcHash(iArr, i);
            int b = b(calcHash);
            int[] iArr2 = this.f;
            int i2 = iArr2[b + 3];
            if (calcHash == iArr2[b] && i2 == i && a(iArr, i, iArr2[b + 1])) {
                return this.l[b >> 2];
            }
            if (i2 == 0) {
                return null;
            }
            int i3 = this.h + ((b >> 3) << 2);
            int i4 = iArr2[i3 + 3];
            if (calcHash == iArr2[i3] && i4 == i && a(iArr, i, iArr2[i3 + 1])) {
                return this.l[i3 >> 2];
            }
            return a(b, calcHash, iArr, i);
        }
    }

    private final int b(int i) {
        return (i & (this.g - 1)) << 2;
    }

    private String a(int i, int i2) {
        int i3 = this.i;
        int i4 = this.j;
        int i5 = i3 + ((i >> (i4 + 2)) << i4);
        int[] iArr = this.f;
        int i6 = (1 << i4) + i5;
        while (i5 < i6) {
            int i7 = iArr[i5 + 3];
            if (i2 == iArr[i5] && 1 == i7) {
                return this.l[i5 >> 2];
            }
            if (i7 == 0) {
                return null;
            }
            i5 += 4;
        }
        for (int d = d(); d < this.m; d += 4) {
            if (i2 == iArr[d] && 1 == iArr[d + 3]) {
                return this.l[d >> 2];
            }
        }
        return null;
    }

    private String a(int i, int i2, int i3) {
        int i4 = this.i;
        int i5 = this.j;
        int i6 = i4 + ((i >> (i5 + 2)) << i5);
        int[] iArr = this.f;
        int i7 = (1 << i5) + i6;
        while (i6 < i7) {
            int i8 = iArr[i6 + 3];
            if (i2 == iArr[i6] && i3 == iArr[i6 + 1] && 2 == i8) {
                return this.l[i6 >> 2];
            }
            if (i8 == 0) {
                return null;
            }
            i6 += 4;
        }
        for (int d = d(); d < this.m; d += 4) {
            if (i2 == iArr[d] && i3 == iArr[d + 1] && 2 == iArr[d + 3]) {
                return this.l[d >> 2];
            }
        }
        return null;
    }

    private String a(int i, int i2, int i3, int i4) {
        int i5 = this.i;
        int i6 = this.j;
        int i7 = i5 + ((i >> (i6 + 2)) << i6);
        int[] iArr = this.f;
        int i8 = (1 << i6) + i7;
        while (i7 < i8) {
            int i9 = iArr[i7 + 3];
            if (i2 == iArr[i7] && i3 == iArr[i7 + 1] && i4 == iArr[i7 + 2] && 3 == i9) {
                return this.l[i7 >> 2];
            }
            if (i9 == 0) {
                return null;
            }
            i7 += 4;
        }
        for (int d = d(); d < this.m; d += 4) {
            if (i2 == iArr[d] && i3 == iArr[d + 1] && i4 == iArr[d + 2] && 3 == iArr[d + 3]) {
                return this.l[d >> 2];
            }
        }
        return null;
    }

    private String a(int i, int i2, int[] iArr, int i3) {
        int i4 = this.i;
        int i5 = this.j;
        int i6 = i4 + ((i >> (i5 + 2)) << i5);
        int[] iArr2 = this.f;
        int i7 = (1 << i5) + i6;
        while (i6 < i7) {
            int i8 = iArr2[i6 + 3];
            if (i2 == iArr2[i6] && i3 == i8 && a(iArr, i3, iArr2[i6 + 1])) {
                return this.l[i6 >> 2];
            }
            if (i8 == 0) {
                return null;
            }
            i6 += 4;
        }
        for (int d = d(); d < this.m; d += 4) {
            if (i2 == iArr2[d] && i3 == iArr2[d + 3] && a(iArr, i3, iArr2[d + 1])) {
                return this.l[d >> 2];
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0023 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0031 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(int[] r6, int r7, int r8) {
        /*
            r5 = this;
            int[] r0 = r5.f
            r1 = 0
            r2 = 1
            switch(r7) {
                case 4: goto L_0x0042;
                case 5: goto L_0x0034;
                case 6: goto L_0x0026;
                case 7: goto L_0x0018;
                case 8: goto L_0x000c;
                default: goto L_0x0007;
            }
        L_0x0007:
            boolean r6 = r5.b(r6, r7, r8)
            return r6
        L_0x000c:
            r7 = r6[r1]
            int r3 = r8 + 1
            r8 = r0[r8]
            if (r7 == r8) goto L_0x0015
            return r1
        L_0x0015:
            r7 = r2
            r8 = r3
            goto L_0x0019
        L_0x0018:
            r7 = r1
        L_0x0019:
            int r3 = r7 + 1
            r7 = r6[r7]
            int r4 = r8 + 1
            r8 = r0[r8]
            if (r7 == r8) goto L_0x0024
            return r1
        L_0x0024:
            r8 = r4
            goto L_0x0027
        L_0x0026:
            r3 = r1
        L_0x0027:
            int r7 = r3 + 1
            r3 = r6[r3]
            int r4 = r8 + 1
            r8 = r0[r8]
            if (r3 == r8) goto L_0x0032
            return r1
        L_0x0032:
            r8 = r4
            goto L_0x0035
        L_0x0034:
            r7 = r1
        L_0x0035:
            int r3 = r7 + 1
            r7 = r6[r7]
            int r4 = r8 + 1
            r8 = r0[r8]
            if (r7 == r8) goto L_0x0040
            return r1
        L_0x0040:
            r8 = r4
            goto L_0x0043
        L_0x0042:
            r3 = r1
        L_0x0043:
            int r7 = r3 + 1
            r3 = r6[r3]
            int r4 = r8 + 1
            r8 = r0[r8]
            if (r3 == r8) goto L_0x004e
            return r1
        L_0x004e:
            int r8 = r7 + 1
            r7 = r6[r7]
            int r3 = r4 + 1
            r4 = r0[r4]
            if (r7 == r4) goto L_0x0059
            return r1
        L_0x0059:
            int r7 = r8 + 1
            r8 = r6[r8]
            int r4 = r3 + 1
            r3 = r0[r3]
            if (r8 == r3) goto L_0x0064
            return r1
        L_0x0064:
            r6 = r6[r7]
            r7 = r0[r4]
            if (r6 == r7) goto L_0x006b
            return r1
        L_0x006b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.sym.ByteQuadsCanonicalizer.a(int[], int, int):boolean");
    }

    private boolean b(int[] iArr, int i, int i2) {
        int i3 = i2;
        int i4 = 0;
        while (true) {
            int i5 = i4 + 1;
            i3++;
            if (iArr[i4] != this.f[i3]) {
                return false;
            }
            if (i5 >= i) {
                return true;
            }
            i4 = i5;
        }
    }

    public String addName(String str, int i) {
        b();
        if (this.d) {
            str = InternCache.instance.intern(str);
        }
        int c = c(calcHash(i));
        int[] iArr = this.f;
        iArr[c] = i;
        iArr[c + 3] = 1;
        this.l[c >> 2] = str;
        this.k++;
        a();
        return str;
    }

    public String addName(String str, int i, int i2) {
        b();
        if (this.d) {
            str = InternCache.instance.intern(str);
        }
        int c = c(i2 == 0 ? calcHash(i) : calcHash(i, i2));
        int[] iArr = this.f;
        iArr[c] = i;
        iArr[c + 1] = i2;
        iArr[c + 3] = 2;
        this.l[c >> 2] = str;
        this.k++;
        a();
        return str;
    }

    public String addName(String str, int i, int i2, int i3) {
        b();
        if (this.d) {
            str = InternCache.instance.intern(str);
        }
        int c = c(calcHash(i, i2, i3));
        int[] iArr = this.f;
        iArr[c] = i;
        iArr[c + 1] = i2;
        iArr[c + 2] = i3;
        iArr[c + 3] = 3;
        this.l[c >> 2] = str;
        this.k++;
        a();
        return str;
    }

    public String addName(String str, int[] iArr, int i) {
        int i2;
        b();
        if (this.d) {
            str = InternCache.instance.intern(str);
        }
        switch (i) {
            case 1:
                i2 = c(calcHash(iArr[0]));
                int[] iArr2 = this.f;
                iArr2[i2] = iArr[0];
                iArr2[i2 + 3] = 1;
                break;
            case 2:
                i2 = c(calcHash(iArr[0], iArr[1]));
                int[] iArr3 = this.f;
                iArr3[i2] = iArr[0];
                iArr3[i2 + 1] = iArr[1];
                iArr3[i2 + 3] = 2;
                break;
            case 3:
                i2 = c(calcHash(iArr[0], iArr[1], iArr[2]));
                int[] iArr4 = this.f;
                iArr4[i2] = iArr[0];
                iArr4[i2 + 1] = iArr[1];
                iArr4[i2 + 2] = iArr[2];
                iArr4[i2 + 3] = 3;
                break;
            default:
                int calcHash = calcHash(iArr, i);
                int c = c(calcHash);
                this.f[c] = calcHash;
                int a2 = a(iArr, i);
                int[] iArr5 = this.f;
                iArr5[c + 1] = a2;
                iArr5[c + 3] = i;
                i2 = c;
                break;
        }
        this.l[i2 >> 2] = str;
        this.k++;
        a();
        return str;
    }

    private void a() {
        if (this.k > (this.g >> 1)) {
            int d = (this.m - d()) >> 2;
            int i = this.k;
            if (d > ((i + 1) >> 7) || i > this.g * 0.8d) {
                this.o = true;
            }
        }
    }

    private void b() {
        if (this.p) {
            int[] iArr = this.f;
            this.f = Arrays.copyOf(iArr, iArr.length);
            String[] strArr = this.l;
            this.l = (String[]) Arrays.copyOf(strArr, strArr.length);
            this.p = false;
            a();
        }
        if (this.o) {
            c();
        }
    }

    private int c(int i) {
        int b = b(i);
        int[] iArr = this.f;
        if (iArr[b + 3] == 0) {
            return b;
        }
        int i2 = this.h + ((b >> 3) << 2);
        if (iArr[i2 + 3] == 0) {
            return i2;
        }
        int i3 = this.i;
        int i4 = this.j;
        int i5 = i3 + ((b >> (i4 + 2)) << i4);
        int i6 = (1 << i4) + i5;
        while (i5 < i6) {
            if (iArr[i5 + 3] == 0) {
                return i5;
            }
            i5 += 4;
        }
        int i7 = this.m;
        this.m = i7 + 4;
        if (this.m >= (this.g << 3)) {
            if (this.e) {
                _reportTooManyCollisions();
            }
            this.o = true;
        }
        return i7;
    }

    private int a(int[] iArr, int i) {
        int i2 = this.n;
        int i3 = i2 + i;
        int[] iArr2 = this.f;
        if (i3 > iArr2.length) {
            this.f = Arrays.copyOf(this.f, this.f.length + Math.max(i3 - iArr2.length, Math.min(4096, this.g)));
        }
        System.arraycopy(iArr, 0, this.f, i2, i);
        this.n += i;
        return i2;
    }

    public int calcHash(int i) {
        int i2 = i ^ this.c;
        int i3 = i2 + (i2 >>> 16);
        int i4 = i3 ^ (i3 << 3);
        return i4 + (i4 >>> 12);
    }

    public int calcHash(int i, int i2) {
        int i3 = i + (i >>> 15);
        int i4 = ((i3 ^ (i3 >>> 9)) + (i2 * 33)) ^ this.c;
        int i5 = i4 + (i4 >>> 16);
        int i6 = i5 ^ (i5 >>> 4);
        return i6 + (i6 << 3);
    }

    public int calcHash(int i, int i2, int i3) {
        int i4 = i ^ this.c;
        int i5 = (((i4 + (i4 >>> 9)) * 31) + i2) * 33;
        int i6 = (i5 + (i5 >>> 15)) ^ i3;
        int i7 = i6 + (i6 >>> 4);
        int i8 = i7 + (i7 >>> 15);
        return i8 ^ (i8 << 9);
    }

    public int calcHash(int[] iArr, int i) {
        if (i >= 4) {
            int i2 = iArr[0] ^ this.c;
            int i3 = i2 + (i2 >>> 9) + iArr[1];
            int i4 = ((i3 + (i3 >>> 15)) * 33) ^ iArr[2];
            int i5 = i4 + (i4 >>> 4);
            for (int i6 = 3; i6 < i; i6++) {
                int i7 = iArr[i6];
                i5 += i7 ^ (i7 >> 21);
            }
            int i8 = i5 * 65599;
            int i9 = i8 + (i8 >>> 19);
            return (i9 << 5) ^ i9;
        }
        throw new IllegalArgumentException();
    }

    private void c() {
        this.o = false;
        this.p = false;
        int[] iArr = this.f;
        String[] strArr = this.l;
        int i = this.g;
        int i2 = this.k;
        int i3 = i + i;
        int i4 = this.m;
        if (i3 > 65536) {
            a(true);
            return;
        }
        this.f = new int[iArr.length + (i << 3)];
        this.g = i3;
        this.h = i3 << 2;
        int i5 = this.h;
        this.i = i5 + (i5 >> 1);
        this.j = a(i3);
        this.l = new String[strArr.length << 1];
        a(false);
        int i6 = 0;
        int[] iArr2 = new int[16];
        for (int i7 = 0; i7 < i4; i7 += 4) {
            int i8 = iArr[i7 + 3];
            if (i8 != 0) {
                i6++;
                String str = strArr[i7 >> 2];
                switch (i8) {
                    case 1:
                        iArr2[0] = iArr[i7];
                        addName(str, iArr2, 1);
                        continue;
                    case 2:
                        iArr2[0] = iArr[i7];
                        iArr2[1] = iArr[i7 + 1];
                        addName(str, iArr2, 2);
                        continue;
                    case 3:
                        iArr2[0] = iArr[i7];
                        iArr2[1] = iArr[i7 + 1];
                        iArr2[2] = iArr[i7 + 2];
                        addName(str, iArr2, 3);
                        continue;
                    default:
                        if (i8 > iArr2.length) {
                            iArr2 = new int[i8];
                        }
                        System.arraycopy(iArr, iArr[i7 + 1], iArr2, 0, i8);
                        addName(str, iArr2, i8);
                        continue;
                }
            }
        }
        if (i6 != i2) {
            throw new IllegalStateException("Failed rehash(): old count=" + i2 + ", copyCount=" + i6);
        }
    }

    private void a(boolean z) {
        this.k = 0;
        this.m = d();
        this.n = this.g << 3;
        if (z) {
            Arrays.fill(this.f, 0);
            Arrays.fill(this.l, (Object) null);
        }
    }

    private final int d() {
        int i = this.g;
        return (i << 3) - i;
    }

    protected void _reportTooManyCollisions() {
        if (this.g > 1024) {
            throw new IllegalStateException("Spill-over slots in symbol table with " + this.k + " entries, hash area of " + this.g + " slots is now full (all " + (this.g >> 3) + " slots -- suspect a DoS attack based on hash collisions. You can disable the check via `JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW`");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class a {
        public final int a;
        public final int b;
        public final int c;
        public final int[] d;
        public final String[] e;
        public final int f;
        public final int g;

        public a(int i, int i2, int i3, int[] iArr, String[] strArr, int i4, int i5) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = iArr;
            this.e = strArr;
            this.f = i4;
            this.g = i5;
        }

        public a(ByteQuadsCanonicalizer byteQuadsCanonicalizer) {
            this.a = byteQuadsCanonicalizer.g;
            this.b = byteQuadsCanonicalizer.k;
            this.c = byteQuadsCanonicalizer.j;
            this.d = byteQuadsCanonicalizer.f;
            this.e = byteQuadsCanonicalizer.l;
            this.f = byteQuadsCanonicalizer.m;
            this.g = byteQuadsCanonicalizer.n;
        }

        public static a a(int i) {
            int i2 = i << 3;
            return new a(i, 0, ByteQuadsCanonicalizer.a(i), new int[i2], new String[i << 1], i2 - i, i2);
        }
    }
}
