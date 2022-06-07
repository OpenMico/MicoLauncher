package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;

/* compiled from: HttpHeadersEncoder.java */
/* loaded from: classes4.dex */
final class c {
    public static void a(CharSequence charSequence, CharSequence charSequence2, ByteBuf byteBuf) throws Exception {
        int length = charSequence.length();
        int length2 = charSequence2.length();
        byteBuf.ensureWritable(length + length2 + 4);
        int writerIndex = byteBuf.writerIndex();
        a(byteBuf, writerIndex, charSequence, length);
        int i = writerIndex + length;
        int i2 = i + 1;
        byteBuf.setByte(i, 58);
        int i3 = i2 + 1;
        byteBuf.setByte(i2, 32);
        a(byteBuf, i3, charSequence2, length2);
        int i4 = i3 + length2;
        int i5 = i4 + 1;
        byteBuf.setByte(i4, 13);
        byteBuf.setByte(i5, 10);
        byteBuf.writerIndex(i5 + 1);
    }

    private static void a(ByteBuf byteBuf, int i, CharSequence charSequence, int i2) {
        if (charSequence instanceof AsciiString) {
            ByteBufUtil.copy((AsciiString) charSequence, 0, byteBuf, i, i2);
        } else {
            b(byteBuf, i, charSequence, i2);
        }
    }

    private static void b(ByteBuf byteBuf, int i, CharSequence charSequence, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            i++;
            byteBuf.setByte(i, AsciiString.c2b(charSequence.charAt(i3)));
        }
    }
}
