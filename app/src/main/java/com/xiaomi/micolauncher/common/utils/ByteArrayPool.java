package com.xiaomi.micolauncher.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes3.dex */
public class ByteArrayPool {
    protected static final Comparator<byte[]> BUF_COMPARATOR = new Comparator<byte[]>() { // from class: com.xiaomi.micolauncher.common.utils.ByteArrayPool.1
        /* renamed from: a */
        public int compare(byte[] bArr, byte[] bArr2) {
            return bArr.length - bArr2.length;
        }
    };
    private List<byte[]> a = new LinkedList();
    private List<byte[]> b = new ArrayList(64);
    private int c = 0;
    private final int d;

    public ByteArrayPool(int i) {
        this.d = i;
    }

    public synchronized byte[] getBuf(int i) {
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            byte[] bArr = this.b.get(i2);
            if (bArr.length >= i) {
                this.c -= bArr.length;
                this.b.remove(i2);
                this.a.remove(bArr);
                return bArr;
            }
        }
        return new byte[i];
    }

    public synchronized void returnBuf(byte[] bArr) {
        if (bArr != null) {
            if (bArr.length <= this.d) {
                this.a.add(bArr);
                int binarySearch = Collections.binarySearch(this.b, bArr, BUF_COMPARATOR);
                if (binarySearch < 0) {
                    binarySearch = (-binarySearch) - 1;
                }
                this.b.add(binarySearch, bArr);
                this.c += bArr.length;
                a();
            }
        }
    }

    private synchronized void a() {
        while (this.c > this.d) {
            byte[] remove = this.a.remove(0);
            this.b.remove(remove);
            this.c -= remove.length;
        }
    }
}
