package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.PDF417Common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BarcodeValue.java */
/* loaded from: classes2.dex */
final class b {
    private final Map<Integer, Integer> a = new HashMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i) {
        Integer num = this.a.get(Integer.valueOf(i));
        if (num == null) {
            num = 0;
        }
        this.a.put(Integer.valueOf(i), Integer.valueOf(num.intValue() + 1));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int[] a() {
        ArrayList arrayList = new ArrayList();
        int i = -1;
        for (Map.Entry<Integer, Integer> entry : this.a.entrySet()) {
            if (entry.getValue().intValue() > i) {
                i = entry.getValue().intValue();
                arrayList.clear();
                arrayList.add(entry.getKey());
            } else if (entry.getValue().intValue() == i) {
                arrayList.add(entry.getKey());
            }
        }
        return PDF417Common.toIntArray(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Integer b(int i) {
        return this.a.get(Integer.valueOf(i));
    }
}
