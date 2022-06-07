package com.google.zxing.common.reedsolomon;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class ReedSolomonEncoder {
    private final GenericGF a;
    private final List<a> b = new ArrayList();

    public ReedSolomonEncoder(GenericGF genericGF) {
        this.a = genericGF;
        this.b.add(new a(genericGF, new int[]{1}));
    }

    private a a(int i) {
        if (i >= this.b.size()) {
            List<a> list = this.b;
            a aVar = list.get(list.size() - 1);
            for (int size = this.b.size(); size <= i; size++) {
                GenericGF genericGF = this.a;
                aVar = aVar.b(new a(genericGF, new int[]{1, genericGF.a((size - 1) + genericGF.getGeneratorBase())}));
                this.b.add(aVar);
            }
        }
        return this.b.get(i);
    }

    public void encode(int[] iArr, int i) {
        if (i != 0) {
            int length = iArr.length - i;
            if (length > 0) {
                a a = a(i);
                int[] iArr2 = new int[length];
                System.arraycopy(iArr, 0, iArr2, 0, length);
                int[] a2 = new a(this.a, iArr2).a(i, 1).c(a)[1].a();
                int length2 = i - a2.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    iArr[length + i2] = 0;
                }
                System.arraycopy(a2, 0, iArr, length + length2, a2.length);
                return;
            }
            throw new IllegalArgumentException("No data bytes provided");
        }
        throw new IllegalArgumentException("No error correction bytes");
    }
}
