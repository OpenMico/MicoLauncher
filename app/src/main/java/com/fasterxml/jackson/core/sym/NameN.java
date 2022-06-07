package com.fasterxml.jackson.core.sym;

import java.util.Arrays;

/* loaded from: classes.dex */
public final class NameN extends Name {
    private final int a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    private final int[] f;

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i) {
        return false;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i, int i2) {
        return false;
    }

    @Override // com.fasterxml.jackson.core.sym.Name
    public boolean equals(int i, int i2, int i3) {
        return false;
    }

    NameN(String str, int i, int i2, int i3, int i4, int i5, int[] iArr, int i6) {
        super(str, i);
        this.a = i2;
        this.b = i3;
        this.c = i4;
        this.d = i5;
        this.f = iArr;
        this.e = i6;
    }

    public static NameN construct(String str, int i, int[] iArr, int i2) {
        if (i2 >= 4) {
            return new NameN(str, i, iArr[0], iArr[1], iArr[2], iArr[3], i2 + (-4) > 0 ? Arrays.copyOfRange(iArr, 4, i2) : null, i2);
        }
        throw new IllegalArgumentException();
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0040 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0054 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0055 A[RETURN] */
    @Override // com.fasterxml.jackson.core.sym.Name
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean equals(int[] r7, int r8) {
        /*
            r6 = this;
            int r0 = r6.e
            r1 = 0
            if (r8 == r0) goto L_0x0006
            return r1
        L_0x0006:
            r0 = r7[r1]
            int r2 = r6.a
            if (r0 == r2) goto L_0x000d
            return r1
        L_0x000d:
            r0 = 1
            r2 = r7[r0]
            int r3 = r6.b
            if (r2 == r3) goto L_0x0015
            return r1
        L_0x0015:
            r2 = 2
            r3 = r7[r2]
            int r4 = r6.c
            if (r3 == r4) goto L_0x001d
            return r1
        L_0x001d:
            r3 = 3
            r4 = r7[r3]
            int r5 = r6.d
            if (r4 == r5) goto L_0x0025
            return r1
        L_0x0025:
            switch(r8) {
                case 4: goto L_0x0055;
                case 5: goto L_0x004b;
                case 6: goto L_0x0041;
                case 7: goto L_0x0037;
                case 8: goto L_0x002d;
                default: goto L_0x0028;
            }
        L_0x0028:
            boolean r7 = r6.a(r7)
            return r7
        L_0x002d:
            r8 = 7
            r8 = r7[r8]
            int[] r4 = r6.f
            r3 = r4[r3]
            if (r8 == r3) goto L_0x0037
            return r1
        L_0x0037:
            r8 = 6
            r8 = r7[r8]
            int[] r3 = r6.f
            r2 = r3[r2]
            if (r8 == r2) goto L_0x0041
            return r1
        L_0x0041:
            r8 = 5
            r8 = r7[r8]
            int[] r2 = r6.f
            r2 = r2[r0]
            if (r8 == r2) goto L_0x004b
            return r1
        L_0x004b:
            r8 = 4
            r7 = r7[r8]
            int[] r8 = r6.f
            r8 = r8[r1]
            if (r7 == r8) goto L_0x0055
            return r1
        L_0x0055:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.sym.NameN.equals(int[], int):boolean");
    }

    private final boolean a(int[] iArr) {
        int i = this.e - 4;
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2 + 4] != this.f[i2]) {
                return false;
            }
        }
        return true;
    }
}
