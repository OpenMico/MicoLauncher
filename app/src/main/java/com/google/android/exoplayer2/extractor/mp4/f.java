package com.google.android.exoplayer2.extractor.mp4;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import java.io.IOException;

/* compiled from: Sniffer.java */
/* loaded from: classes2.dex */
final class f {
    private static final int[] a = {1769172845, 1769172786, 1769172787, 1769172788, 1769172789, 1769172790, 1769172793, 1635148593, 1752589105, 1751479857, 1635135537, 1836069937, 1836069938, 862401121, 862401122, 862417462, 862417718, 862414134, 862414646, 1295275552, 1295270176, 1714714144, 1801741417, 1295275600, 1903435808, 1297305174, 1684175153, 1769172332, 1885955686};

    public static boolean a(ExtractorInput extractorInput) throws IOException {
        return a(extractorInput, true, false);
    }

    public static boolean a(ExtractorInput extractorInput, boolean z) throws IOException {
        return a(extractorInput, false, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x0100, code lost:
        r20 = r8;
        r9 = true;
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0108, code lost:
        r0 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x010a, code lost:
        if (r10 == false) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x010e, code lost:
        if (r24 != r0) goto L_?;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0112, code lost:
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:?, code lost:
        return r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:?, code lost:
        return r20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean a(com.google.android.exoplayer2.extractor.ExtractorInput r23, boolean r24, boolean r25) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 275
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.f.a(com.google.android.exoplayer2.extractor.ExtractorInput, boolean, boolean):boolean");
    }

    private static boolean a(int i, boolean z) {
        if ((i >>> 8) == 3368816) {
            return true;
        }
        if (i == 1751476579 && z) {
            return true;
        }
        for (int i2 : a) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }
}
