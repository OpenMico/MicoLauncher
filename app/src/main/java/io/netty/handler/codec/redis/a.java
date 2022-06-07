package io.netty.handler.codec.redis;

import io.netty.util.CharsetUtil;
import io.netty.util.internal.PlatformDependent;

/* compiled from: RedisCodecUtil.java */
/* loaded from: classes4.dex */
final class a {
    public static byte[] a(long j) {
        return Long.toString(j).getBytes(CharsetUtil.US_ASCII);
    }

    public static short a(char c, char c2) {
        return (short) (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? c | (c2 << '\b') : (c << '\b') | c2);
    }

    public static byte[] a(short s) {
        byte[] bArr = new byte[2];
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            bArr[1] = (byte) ((s >> 8) & 255);
            bArr[0] = (byte) (s & 255);
        } else {
            bArr[0] = (byte) ((s >> 8) & 255);
            bArr[1] = (byte) (s & 255);
        }
        return bArr;
    }
}
