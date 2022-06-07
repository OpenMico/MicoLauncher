package com.google.zxing.oned.rss.expanded;

import com.google.zxing.common.BitArray;
import java.util.List;

/* compiled from: BitArrayBuilder.java */
/* loaded from: classes2.dex */
final class a {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static BitArray a(List<b> list) {
        int size = (list.size() << 1) - 1;
        if (list.get(list.size() - 1).b() == null) {
            size--;
        }
        BitArray bitArray = new BitArray(size * 12);
        int value = list.get(0).b().getValue();
        int i = 0;
        for (int i2 = 11; i2 >= 0; i2--) {
            if (((1 << i2) & value) != 0) {
                bitArray.set(i);
            }
            i++;
        }
        for (int i3 = 1; i3 < list.size(); i3++) {
            b bVar = list.get(i3);
            int value2 = bVar.a().getValue();
            for (int i4 = 11; i4 >= 0; i4--) {
                if (((1 << i4) & value2) != 0) {
                    bitArray.set(i);
                }
                i++;
            }
            if (bVar.b() != null) {
                int value3 = bVar.b().getValue();
                for (int i5 = 11; i5 >= 0; i5--) {
                    if (((1 << i5) & value3) != 0) {
                        bitArray.set(i);
                    }
                    i++;
                }
            }
        }
        return bitArray;
    }
}
