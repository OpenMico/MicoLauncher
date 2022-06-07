package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;

/* compiled from: SocksCommonUtils.java */
/* loaded from: classes4.dex */
final class a {
    static final /* synthetic */ boolean c = !a.class.desiredAssertionStatus();
    public static final SocksRequest a = new UnknownSocksRequest();
    public static final SocksResponse b = new UnknownSocksResponse();
    private static final char[] d = {':', ':'};

    private a() {
    }

    public static String a(int i) {
        return String.valueOf((i >> 24) & 255) + '.' + ((i >> 16) & 255) + '.' + ((i >> 8) & 255) + '.' + (i & 255);
    }

    public static String a(byte[] bArr) {
        if (c || bArr.length == 16) {
            StringBuilder sb = new StringBuilder(39);
            a(sb, bArr, 0, 8);
            return sb.toString();
        }
        throw new AssertionError();
    }

    private static void a(StringBuilder sb, byte[] bArr, int i, int i2) {
        int i3 = i2 - 1;
        while (i < i3) {
            a(sb, bArr, i);
            sb.append(':');
            i++;
        }
        a(sb, bArr, i);
    }

    private static void a(StringBuilder sb, byte[] bArr, int i) {
        StringUtil.toHexString(sb, bArr, i << 1, 2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(ByteBuf byteBuf, int i) {
        String byteBuf2 = byteBuf.toString(byteBuf.readerIndex(), i, CharsetUtil.US_ASCII);
        byteBuf.skipBytes(i);
        return byteBuf2;
    }
}
