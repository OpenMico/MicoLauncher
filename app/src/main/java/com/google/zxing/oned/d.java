package com.google.zxing.oned;

import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: UPCEANExtensionSupport.java */
/* loaded from: classes2.dex */
public final class d {
    private static final int[] a = {1, 1, 2};
    private final b b = new b();
    private final c c = new c();

    /* JADX INFO: Access modifiers changed from: package-private */
    public Result a(int i, BitArray bitArray, int i2) throws NotFoundException {
        int[] a2 = UPCEANReader.a(bitArray, i2, false, a);
        try {
            return this.c.a(i, bitArray, a2);
        } catch (ReaderException unused) {
            return this.b.a(i, bitArray, a2);
        }
    }
}
