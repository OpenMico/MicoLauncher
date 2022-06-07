package com.fasterxml.jackson.core.sym;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.util.InternCache;
import java.util.Arrays;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class CharsToNameCanonicalizer {
    public static final int HASH_MULT = 33;
    private final CharsToNameCanonicalizer a;
    private final AtomicReference<b> b;
    private final int c;
    private final int d;
    private boolean e;
    private String[] f;
    private a[] g;
    private int h;
    private int i;
    private int j;
    private int k;
    private boolean l;
    private BitSet m;

    private static int a(int i) {
        return i - (i >> 2);
    }

    private CharsToNameCanonicalizer(int i) {
        this.a = null;
        this.c = i;
        this.e = true;
        this.d = -1;
        this.l = false;
        this.k = 0;
        this.b = new AtomicReference<>(b.a(64));
    }

    private CharsToNameCanonicalizer(CharsToNameCanonicalizer charsToNameCanonicalizer, int i, int i2, b bVar) {
        this.a = charsToNameCanonicalizer;
        this.c = i2;
        this.b = null;
        this.d = i;
        this.e = JsonFactory.Feature.CANONICALIZE_FIELD_NAMES.enabledIn(i);
        this.f = bVar.c;
        this.g = bVar.d;
        this.h = bVar.a;
        this.k = bVar.b;
        int length = this.f.length;
        this.i = a(length);
        this.j = length - 1;
        this.l = true;
    }

    public static CharsToNameCanonicalizer createRoot() {
        long currentTimeMillis = System.currentTimeMillis();
        return createRoot((((int) currentTimeMillis) + ((int) (currentTimeMillis >>> 32))) | 1);
    }

    protected static CharsToNameCanonicalizer createRoot(int i) {
        return new CharsToNameCanonicalizer(i);
    }

    public CharsToNameCanonicalizer makeChild(int i) {
        return new CharsToNameCanonicalizer(this, i, this.c, this.b.get());
    }

    public void release() {
        CharsToNameCanonicalizer charsToNameCanonicalizer;
        if (maybeDirty() && (charsToNameCanonicalizer = this.a) != null && this.e) {
            charsToNameCanonicalizer.a(new b(this));
            this.l = true;
        }
    }

    private void a(b bVar) {
        int i = bVar.a;
        b bVar2 = this.b.get();
        if (i != bVar2.a) {
            if (i > 12000) {
                bVar = b.a(64);
            }
            this.b.compareAndSet(bVar2, bVar);
        }
    }

    public int size() {
        AtomicReference<b> atomicReference = this.b;
        if (atomicReference != null) {
            return atomicReference.get().a;
        }
        return this.h;
    }

    public int bucketCount() {
        return this.f.length;
    }

    public boolean maybeDirty() {
        return !this.l;
    }

    public int hashSeed() {
        return this.c;
    }

    public int collisionCount() {
        a[] aVarArr = this.g;
        int i = 0;
        for (a aVar : aVarArr) {
            if (aVar != null) {
                i += aVar.c;
            }
        }
        return i;
    }

    public int maxCollisionLength() {
        return this.k;
    }

    public String findSymbol(char[] cArr, int i, int i2, int i3) {
        if (i2 < 1) {
            return "";
        }
        if (!this.e) {
            return new String(cArr, i, i2);
        }
        int _hashToIndex = _hashToIndex(i3);
        String str = this.f[_hashToIndex];
        if (str != null) {
            if (str.length() == i2) {
                int i4 = 0;
                while (str.charAt(i4) == cArr[i + i4]) {
                    i4++;
                    if (i4 == i2) {
                        return str;
                    }
                }
            }
            a aVar = this.g[_hashToIndex >> 1];
            if (aVar != null) {
                String a2 = aVar.a(cArr, i, i2);
                if (a2 != null) {
                    return a2;
                }
                String a3 = a(cArr, i, i2, aVar.b);
                if (a3 != null) {
                    return a3;
                }
            }
        }
        return a(cArr, i, i2, i3, _hashToIndex);
    }

    private String a(char[] cArr, int i, int i2, a aVar) {
        while (aVar != null) {
            String a2 = aVar.a(cArr, i, i2);
            if (a2 != null) {
                return a2;
            }
            aVar = aVar.b;
        }
        return null;
    }

    private String a(char[] cArr, int i, int i2, int i3, int i4) {
        if (this.l) {
            a();
            this.l = false;
        } else if (this.h >= this.i) {
            b();
            i4 = _hashToIndex(calcHash(cArr, i, i2));
        }
        String str = new String(cArr, i, i2);
        if (JsonFactory.Feature.INTERN_FIELD_NAMES.enabledIn(this.d)) {
            str = InternCache.instance.intern(str);
        }
        this.h++;
        String[] strArr = this.f;
        if (strArr[i4] == null) {
            strArr[i4] = str;
        } else {
            int i5 = i4 >> 1;
            a aVar = new a(str, this.g[i5]);
            int i6 = aVar.c;
            if (i6 > 100) {
                a(i5, aVar);
            } else {
                this.g[i5] = aVar;
                this.k = Math.max(i6, this.k);
            }
        }
        return str;
    }

    private void a(int i, a aVar) {
        BitSet bitSet = this.m;
        if (bitSet == null) {
            this.m = new BitSet();
            this.m.set(i);
        } else if (bitSet.get(i)) {
            if (JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW.enabledIn(this.d)) {
                reportTooManyCollisions(100);
            }
            this.e = false;
        } else {
            this.m.set(i);
        }
        this.f[i + i] = aVar.a;
        this.g[i] = null;
        this.h -= aVar.c;
        this.k = -1;
    }

    public int _hashToIndex(int i) {
        int i2 = i + (i >>> 15);
        int i3 = i2 ^ (i2 << 7);
        return (i3 + (i3 >>> 3)) & this.j;
    }

    public int calcHash(char[] cArr, int i, int i2) {
        int i3 = this.c;
        int i4 = i2 + i;
        while (i < i4) {
            i3 = (i3 * 33) + cArr[i];
            i++;
        }
        if (i3 == 0) {
            return 1;
        }
        return i3;
    }

    public int calcHash(String str) {
        int length = str.length();
        int i = this.c;
        for (int i2 = 0; i2 < length; i2++) {
            i = (i * 33) + str.charAt(i2);
        }
        if (i == 0) {
            return 1;
        }
        return i;
    }

    private void a() {
        String[] strArr = this.f;
        this.f = (String[]) Arrays.copyOf(strArr, strArr.length);
        a[] aVarArr = this.g;
        this.g = (a[]) Arrays.copyOf(aVarArr, aVarArr.length);
    }

    private void b() {
        String[] strArr = this.f;
        int length = strArr.length;
        int i = length + length;
        if (i > 65536) {
            this.h = 0;
            this.e = false;
            this.f = new String[64];
            this.g = new a[32];
            this.j = 63;
            this.l = false;
            return;
        }
        a[] aVarArr = this.g;
        this.f = new String[i];
        this.g = new a[i >> 1];
        this.j = i - 1;
        this.i = a(i);
        int i2 = 0;
        int i3 = 0;
        for (String str : strArr) {
            if (str != null) {
                i2++;
                int _hashToIndex = _hashToIndex(calcHash(str));
                String[] strArr2 = this.f;
                if (strArr2[_hashToIndex] == null) {
                    strArr2[_hashToIndex] = str;
                } else {
                    int i4 = _hashToIndex >> 1;
                    a aVar = new a(str, this.g[i4]);
                    this.g[i4] = aVar;
                    i3 = Math.max(i3, aVar.c);
                }
            }
        }
        int i5 = length >> 1;
        for (int i6 = 0; i6 < i5; i6++) {
            for (a aVar2 = aVarArr[i6]; aVar2 != null; aVar2 = aVar2.b) {
                i2++;
                String str2 = aVar2.a;
                int _hashToIndex2 = _hashToIndex(calcHash(str2));
                String[] strArr3 = this.f;
                if (strArr3[_hashToIndex2] == null) {
                    strArr3[_hashToIndex2] = str2;
                } else {
                    int i7 = _hashToIndex2 >> 1;
                    a aVar3 = new a(str2, this.g[i7]);
                    this.g[i7] = aVar3;
                    i3 = Math.max(i3, aVar3.c);
                }
            }
        }
        this.k = i3;
        this.m = null;
        int i8 = this.h;
        if (i2 != i8) {
            throw new IllegalStateException(String.format("Internal error on SymbolTable.rehash(): had %d entries; now have %d", Integer.valueOf(i8), Integer.valueOf(i2)));
        }
    }

    protected void reportTooManyCollisions(int i) {
        throw new IllegalStateException("Longest collision chain in symbol table (of size " + this.h + ") now exceeds maximum, " + i + " -- suspect a DoS attack based on hash collisions");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class a {
        public final String a;
        public final a b;
        public final int c;

        public a(String str, a aVar) {
            this.a = str;
            this.b = aVar;
            this.c = aVar != null ? 1 + aVar.c : 1;
        }

        public String a(char[] cArr, int i, int i2) {
            if (this.a.length() != i2) {
                return null;
            }
            int i3 = 0;
            while (this.a.charAt(i3) == cArr[i + i3]) {
                i3++;
                if (i3 >= i2) {
                    return this.a;
                }
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class b {
        final int a;
        final int b;
        final String[] c;
        final a[] d;

        public b(int i, int i2, String[] strArr, a[] aVarArr) {
            this.a = i;
            this.b = i2;
            this.c = strArr;
            this.d = aVarArr;
        }

        public b(CharsToNameCanonicalizer charsToNameCanonicalizer) {
            this.a = charsToNameCanonicalizer.h;
            this.b = charsToNameCanonicalizer.k;
            this.c = charsToNameCanonicalizer.f;
            this.d = charsToNameCanonicalizer.g;
        }

        public static b a(int i) {
            return new b(0, 0, new String[i], new a[i >> 1]);
        }
    }
}
