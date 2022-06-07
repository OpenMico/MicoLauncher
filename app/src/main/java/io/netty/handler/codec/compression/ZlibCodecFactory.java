package io.netty.handler.codec.compression;

import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/* loaded from: classes4.dex */
public final class ZlibCodecFactory {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(ZlibCodecFactory.class);
    private static final boolean b;
    private static final boolean c;
    private static final boolean d;

    static {
        boolean z = true;
        b = SystemPropertyUtil.getBoolean("io.netty.noJdkZlibDecoder", PlatformDependent.javaVersion() < 7);
        a.debug("-Dio.netty.noJdkZlibDecoder: {}", Boolean.valueOf(b));
        c = SystemPropertyUtil.getBoolean("io.netty.noJdkZlibEncoder", false);
        a.debug("-Dio.netty.noJdkZlibEncoder: {}", Boolean.valueOf(c));
        if (!b && PlatformDependent.javaVersion() < 7) {
            z = false;
        }
        d = z;
    }

    public static boolean isSupportingWindowSizeAndMemLevel() {
        return d;
    }

    public static ZlibEncoder newZlibEncoder(int i) {
        if (PlatformDependent.javaVersion() < 7 || c) {
            return new JZlibEncoder(i);
        }
        return new JdkZlibEncoder(i);
    }

    public static ZlibEncoder newZlibEncoder(ZlibWrapper zlibWrapper) {
        if (PlatformDependent.javaVersion() < 7 || c) {
            return new JZlibEncoder(zlibWrapper);
        }
        return new JdkZlibEncoder(zlibWrapper);
    }

    public static ZlibEncoder newZlibEncoder(ZlibWrapper zlibWrapper, int i) {
        if (PlatformDependent.javaVersion() < 7 || c) {
            return new JZlibEncoder(zlibWrapper, i);
        }
        return new JdkZlibEncoder(zlibWrapper, i);
    }

    public static ZlibEncoder newZlibEncoder(ZlibWrapper zlibWrapper, int i, int i2, int i3) {
        if (PlatformDependent.javaVersion() < 7 || c || i2 != 15 || i3 != 8) {
            return new JZlibEncoder(zlibWrapper, i, i2, i3);
        }
        return new JdkZlibEncoder(zlibWrapper, i);
    }

    public static ZlibEncoder newZlibEncoder(byte[] bArr) {
        if (PlatformDependent.javaVersion() < 7 || c) {
            return new JZlibEncoder(bArr);
        }
        return new JdkZlibEncoder(bArr);
    }

    public static ZlibEncoder newZlibEncoder(int i, byte[] bArr) {
        if (PlatformDependent.javaVersion() < 7 || c) {
            return new JZlibEncoder(i, bArr);
        }
        return new JdkZlibEncoder(i, bArr);
    }

    public static ZlibEncoder newZlibEncoder(int i, int i2, int i3, byte[] bArr) {
        if (PlatformDependent.javaVersion() < 7 || c || i2 != 15 || i3 != 8) {
            return new JZlibEncoder(i, i2, i3, bArr);
        }
        return new JdkZlibEncoder(i, bArr);
    }

    public static ZlibDecoder newZlibDecoder() {
        if (PlatformDependent.javaVersion() < 7 || b) {
            return new JZlibDecoder();
        }
        return new JdkZlibDecoder();
    }

    public static ZlibDecoder newZlibDecoder(ZlibWrapper zlibWrapper) {
        if (PlatformDependent.javaVersion() < 7 || b) {
            return new JZlibDecoder(zlibWrapper);
        }
        return new JdkZlibDecoder(zlibWrapper);
    }

    public static ZlibDecoder newZlibDecoder(byte[] bArr) {
        if (PlatformDependent.javaVersion() < 7 || b) {
            return new JZlibDecoder(bArr);
        }
        return new JdkZlibDecoder(bArr);
    }

    private ZlibCodecFactory() {
    }
}
