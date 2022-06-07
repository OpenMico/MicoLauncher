package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.AsciiString;
import java.util.Map;

/* loaded from: classes4.dex */
public final class AsciiHeadersEncoder {
    private final ByteBuf a;
    private final SeparatorType b;
    private final NewlineType c;

    /* loaded from: classes4.dex */
    public enum NewlineType {
        LF,
        CRLF
    }

    /* loaded from: classes4.dex */
    public enum SeparatorType {
        COLON,
        COLON_SPACE
    }

    private static int a(char c) {
        if (c < 256) {
            return (byte) c;
        }
        return 63;
    }

    public AsciiHeadersEncoder(ByteBuf byteBuf) {
        this(byteBuf, SeparatorType.COLON_SPACE, NewlineType.CRLF);
    }

    public AsciiHeadersEncoder(ByteBuf byteBuf, SeparatorType separatorType, NewlineType newlineType) {
        if (byteBuf == null) {
            throw new NullPointerException("buf");
        } else if (separatorType == null) {
            throw new NullPointerException("separatorType");
        } else if (newlineType != null) {
            this.a = byteBuf;
            this.b = separatorType;
            this.c = newlineType;
        } else {
            throw new NullPointerException("newlineType");
        }
    }

    public void encode(Map.Entry<CharSequence, CharSequence> entry) {
        int i;
        int i2;
        CharSequence key = entry.getKey();
        CharSequence value = entry.getValue();
        ByteBuf byteBuf = this.a;
        int length = key.length();
        int length2 = value.length();
        int writerIndex = byteBuf.writerIndex();
        byteBuf.ensureWritable(length + length2 + 4);
        a(byteBuf, writerIndex, key, length);
        int i3 = writerIndex + length;
        switch (this.b) {
            case COLON:
                i = i3 + 1;
                byteBuf.setByte(i3, 58);
                break;
            case COLON_SPACE:
                int i4 = i3 + 1;
                byteBuf.setByte(i3, 58);
                i = i4 + 1;
                byteBuf.setByte(i4, 32);
                break;
            default:
                throw new Error();
        }
        a(byteBuf, i, value, length2);
        int i5 = i + length2;
        switch (this.c) {
            case LF:
                i2 = i5 + 1;
                byteBuf.setByte(i5, 10);
                break;
            case CRLF:
                int i6 = i5 + 1;
                byteBuf.setByte(i5, 13);
                i2 = i6 + 1;
                byteBuf.setByte(i6, 10);
                break;
            default:
                throw new Error();
        }
        byteBuf.writerIndex(i2);
    }

    private static void a(ByteBuf byteBuf, int i, CharSequence charSequence, int i2) {
        if (charSequence instanceof AsciiString) {
            a(byteBuf, i, (AsciiString) charSequence, i2);
        } else {
            b(byteBuf, i, charSequence, i2);
        }
    }

    private static void a(ByteBuf byteBuf, int i, AsciiString asciiString, int i2) {
        ByteBufUtil.copy(asciiString, 0, byteBuf, i, i2);
    }

    private static void b(ByteBuf byteBuf, int i, CharSequence charSequence, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            i++;
            byteBuf.setByte(i, a(charSequence.charAt(i3)));
        }
    }
}
