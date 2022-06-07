package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.C;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class OpusUtil {
    public static final int SAMPLE_RATE = 48000;

    private OpusUtil() {
    }

    public static int getChannelCount(byte[] bArr) {
        return bArr[9] & 255;
    }

    public static List<byte[]> buildInitializationData(byte[] bArr) {
        long b = b(a(bArr));
        long b2 = b(3840L);
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(bArr);
        arrayList.add(a(b));
        arrayList.add(a(b2));
        return arrayList;
    }

    public static int getPreSkipSamples(List<byte[]> list) {
        if (list.size() == 3) {
            return (int) c(ByteBuffer.wrap(list.get(1)).order(ByteOrder.nativeOrder()).getLong());
        }
        return a(list.get(0));
    }

    public static int getSeekPreRollSamples(List<byte[]> list) {
        if (list.size() == 3) {
            return (int) c(ByteBuffer.wrap(list.get(2)).order(ByteOrder.nativeOrder()).getLong());
        }
        return 3840;
    }

    private static int a(byte[] bArr) {
        return (bArr[10] & 255) | ((bArr[11] & 255) << 8);
    }

    private static byte[] a(long j) {
        return ByteBuffer.allocate(8).order(ByteOrder.nativeOrder()).putLong(j).array();
    }

    private static long b(long j) {
        return (j * C.NANOS_PER_SECOND) / 48000;
    }

    private static long c(long j) {
        return (j * 48000) / C.NANOS_PER_SECOND;
    }
}
