package com.google.zxing.datamatrix.decoder;

import com.alibaba.fastjson.asm.Opcodes;
import com.google.zxing.FormatException;
import com.xiaomi.mi_connect_sdk.BuildConfig;

/* loaded from: classes2.dex */
public final class Version {
    private static final Version[] a = b();
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    private final int f;
    private final b g;
    private final int h;

    private Version(int i, int i2, int i3, int i4, int i5, b bVar) {
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = i4;
        this.f = i5;
        this.g = bVar;
        int a2 = bVar.a();
        a[] b2 = bVar.b();
        int i6 = 0;
        for (a aVar : b2) {
            i6 += aVar.a() * (aVar.b() + a2);
        }
        this.h = i6;
    }

    public int getVersionNumber() {
        return this.b;
    }

    public int getSymbolSizeRows() {
        return this.c;
    }

    public int getSymbolSizeColumns() {
        return this.d;
    }

    public int getDataRegionSizeRows() {
        return this.e;
    }

    public int getDataRegionSizeColumns() {
        return this.f;
    }

    public int getTotalCodewords() {
        return this.h;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public b a() {
        return this.g;
    }

    public static Version getVersionForDimensions(int i, int i2) throws FormatException {
        if ((i & 1) == 0 && (i2 & 1) == 0) {
            Version[] versionArr = a;
            for (Version version : versionArr) {
                if (version.c == i && version.d == i2) {
                    return version;
                }
            }
            throw FormatException.getFormatInstance();
        }
        throw FormatException.getFormatInstance();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class b {
        private final int a;
        private final a[] b;

        private b(int i, a aVar) {
            this.a = i;
            this.b = new a[]{aVar};
        }

        private b(int i, a aVar, a aVar2) {
            this.a = i;
            this.b = new a[]{aVar, aVar2};
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public int a() {
            return this.a;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public a[] b() {
            return this.b;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class a {
        private final int a;
        private final int b;

        private a(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public int a() {
            return this.a;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public int b() {
            return this.b;
        }
    }

    public String toString() {
        return String.valueOf(this.b);
    }

    private static Version[] b() {
        return new Version[]{new Version(1, 10, 10, 8, 8, new b(5, new a(1, 3))), new Version(2, 12, 12, 10, 10, new b(7, new a(1, 5))), new Version(3, 14, 14, 12, 12, new b(10, new a(1, 8))), new Version(4, 16, 16, 14, 14, new b(12, new a(1, 12))), new Version(5, 18, 18, 16, 16, new b(14, new a(1, 18))), new Version(6, 20, 20, 18, 18, new b(18, new a(1, 22))), new Version(7, 22, 22, 20, 20, new b(20, new a(1, 30))), new Version(8, 24, 24, 22, 22, new b(24, new a(1, 36))), new Version(9, 26, 26, 24, 24, new b(28, new a(1, 44))), new Version(10, 32, 32, 14, 14, new b(36, new a(1, 62))), new Version(11, 36, 36, 16, 16, new b(42, new a(1, 86))), new Version(12, 40, 40, 18, 18, new b(48, new a(1, 114))), new Version(13, 44, 44, 20, 20, new b(56, new a(1, 144))), new Version(14, 48, 48, 22, 22, new b(68, new a(1, 174))), new Version(15, 52, 52, 24, 24, new b(42, new a(2, 102))), new Version(16, 64, 64, 14, 14, new b(56, new a(2, 140))), new Version(17, 72, 72, 16, 16, new b(36, new a(4, 92))), new Version(18, 80, 80, 18, 18, new b(48, new a(4, 114))), new Version(19, 88, 88, 20, 20, new b(56, new a(4, 144))), new Version(20, 96, 96, 22, 22, new b(68, new a(4, 174))), new Version(21, 104, 104, 24, 24, new b(56, new a(6, 136))), new Version(22, 120, 120, 18, 18, new b(68, new a(6, 175))), new Version(23, 132, 132, 20, 20, new b(62, new a(8, Opcodes.IF_ICMPGT))), new Version(24, 144, 144, 22, 22, new b(62, new a(8, BuildConfig.MiConnectVersionBuild), new a(2, 155))), new Version(25, 8, 18, 6, 16, new b(7, new a(1, 5))), new Version(26, 8, 32, 6, 14, new b(11, new a(1, 10))), new Version(27, 12, 26, 10, 24, new b(14, new a(1, 16))), new Version(28, 12, 36, 10, 16, new b(18, new a(1, 22))), new Version(29, 16, 36, 14, 16, new b(24, new a(1, 32))), new Version(30, 16, 48, 14, 22, new b(28, new a(1, 49)))};
    }
}
