package io.netty.handler.codec.compression;

import com.jcraft.jzlib.Deflater;
import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;

/* compiled from: ZlibUtil.java */
/* loaded from: classes4.dex */
final class o {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Inflater inflater, String str, int i) {
        throw b(inflater, str, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(Deflater deflater, String str, int i) {
        throw b(deflater, str, i);
    }

    static DecompressionException b(Inflater inflater, String str, int i) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" (");
        sb.append(i);
        sb.append(')');
        if (inflater.msg != null) {
            str2 = ": " + inflater.msg;
        } else {
            str2 = "";
        }
        sb.append(str2);
        return new DecompressionException(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static CompressionException b(Deflater deflater, String str, int i) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" (");
        sb.append(i);
        sb.append(')');
        if (deflater.msg != null) {
            str2 = ": " + deflater.msg;
        } else {
            str2 = "";
        }
        sb.append(str2);
        return new CompressionException(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static JZlib.WrapperType a(ZlibWrapper zlibWrapper) {
        switch (zlibWrapper) {
            case NONE:
                return JZlib.W_NONE;
            case ZLIB:
                return JZlib.W_ZLIB;
            case GZIP:
                return JZlib.W_GZIP;
            case ZLIB_OR_NONE:
                return JZlib.W_ANY;
            default:
                throw new Error();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int b(ZlibWrapper zlibWrapper) {
        switch (zlibWrapper) {
            case NONE:
                return 0;
            case ZLIB:
            case ZLIB_OR_NONE:
                return 2;
            case GZIP:
                return 10;
            default:
                throw new Error();
        }
    }
}
