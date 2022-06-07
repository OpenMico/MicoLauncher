package com.zlw.main.recorderlib.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes4.dex */
public class RecordUtils {
    public static long getMaxDecibels(byte[] bArr) {
        short[] shorts = ByteUtils.toShorts(bArr);
        if (shorts == null) {
            return 0L;
        }
        float f = 2.0f;
        for (float f2 : shorts) {
            if (Math.abs(f) < Math.abs(f2)) {
                f = f2;
            }
        }
        return Math.round(Math.log10(f) * 20.0d);
    }

    public static float[] byteToFloat(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        FloatBuffer allocate = FloatBuffer.allocate(bArr.length / 2);
        for (int i = 0; i < allocate.capacity(); i++) {
            allocate.put(wrap.getShort(i * 2));
        }
        return allocate.array();
    }
}
