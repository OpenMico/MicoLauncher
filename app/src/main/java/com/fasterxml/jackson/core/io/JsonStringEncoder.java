package com.fasterxml.jackson.core.io;

import com.fasterxml.jackson.core.base.GeneratorBase;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.BufferRecyclers;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;

/* loaded from: classes.dex */
public final class JsonStringEncoder {
    private static final char[] a = CharTypes.copyHexChars();
    private static final byte[] b = CharTypes.copyHexBytes();
    protected ByteArrayBuilder _bytes;
    protected final char[] _qbuf = new char[6];
    protected TextBuffer _text;

    public JsonStringEncoder() {
        char[] cArr = this._qbuf;
        cArr[0] = '\\';
        cArr[2] = '0';
        cArr[3] = '0';
    }

    @Deprecated
    public static JsonStringEncoder getInstance() {
        return BufferRecyclers.getJsonStringEncoder();
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0029, code lost:
        r1 = r1 + 1;
        r1 = r12.charAt(r1);
        r9 = r2[r1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0031, code lost:
        if (r9 >= 0) goto L_0x003a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0033, code lost:
        r1 = a(r1, r11._qbuf);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x003a, code lost:
        r1 = b(r9, r11._qbuf);
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0040, code lost:
        r9 = r7 + r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0043, code lost:
        if (r9 <= r6.length) goto L_0x005a;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0045, code lost:
        r9 = r6.length - r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0047, code lost:
        if (r9 <= 0) goto L_0x004e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0049, code lost:
        java.lang.System.arraycopy(r11._qbuf, 0, r6, r7, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x004e, code lost:
        r6 = r0.finishCurrentSegment();
        r1 = r1 - r9;
        java.lang.System.arraycopy(r11._qbuf, r9, r6, 0, r1);
        r7 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005a, code lost:
        java.lang.System.arraycopy(r11._qbuf, 0, r6, r7, r1);
        r7 = r9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public char[] quoteAsString(java.lang.String r12) {
        /*
            r11 = this;
            com.fasterxml.jackson.core.util.TextBuffer r0 = r11._text
            if (r0 != 0) goto L_0x000c
            com.fasterxml.jackson.core.util.TextBuffer r0 = new com.fasterxml.jackson.core.util.TextBuffer
            r1 = 0
            r0.<init>(r1)
            r11._text = r0
        L_0x000c:
            char[] r1 = r0.emptyAndGetCurrentSegment()
            int[] r2 = com.fasterxml.jackson.core.io.CharTypes.get7BitOutputEscapes()
            int r3 = r2.length
            int r4 = r12.length()
            r5 = 0
            r6 = r1
            r1 = r5
            r7 = r1
        L_0x001d:
            if (r1 >= r4) goto L_0x0076
        L_0x001f:
            char r8 = r12.charAt(r1)
            if (r8 >= r3) goto L_0x0062
            r9 = r2[r8]
            if (r9 == 0) goto L_0x0062
            int r8 = r1 + 1
            char r1 = r12.charAt(r1)
            r9 = r2[r1]
            if (r9 >= 0) goto L_0x003a
            char[] r9 = r11._qbuf
            int r1 = r11.a(r1, r9)
            goto L_0x0040
        L_0x003a:
            char[] r1 = r11._qbuf
            int r1 = r11.b(r9, r1)
        L_0x0040:
            int r9 = r7 + r1
            int r10 = r6.length
            if (r9 <= r10) goto L_0x005a
            int r9 = r6.length
            int r9 = r9 - r7
            if (r9 <= 0) goto L_0x004e
            char[] r10 = r11._qbuf
            java.lang.System.arraycopy(r10, r5, r6, r7, r9)
        L_0x004e:
            char[] r6 = r0.finishCurrentSegment()
            int r1 = r1 - r9
            char[] r7 = r11._qbuf
            java.lang.System.arraycopy(r7, r9, r6, r5, r1)
            r7 = r1
            goto L_0x0060
        L_0x005a:
            char[] r10 = r11._qbuf
            java.lang.System.arraycopy(r10, r5, r6, r7, r1)
            r7 = r9
        L_0x0060:
            r1 = r8
            goto L_0x001d
        L_0x0062:
            int r9 = r6.length
            if (r7 < r9) goto L_0x006a
            char[] r6 = r0.finishCurrentSegment()
            r7 = r5
        L_0x006a:
            int r9 = r7 + 1
            r6[r7] = r8
            int r1 = r1 + 1
            if (r1 < r4) goto L_0x0074
            r7 = r9
            goto L_0x0076
        L_0x0074:
            r7 = r9
            goto L_0x001f
        L_0x0076:
            r0.setCurrentLength(r7)
            char[] r12 = r0.contentsAsArray()
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.io.JsonStringEncoder.quoteAsString(java.lang.String):char[]");
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0021, code lost:
        r4 = a(r4, r7._qbuf);
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0028, code lost:
        r4 = b(r6, r7._qbuf);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x002e, code lost:
        r9.append(r7._qbuf, 0, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0017, code lost:
        r4 = r4 + 1;
        r4 = r8.charAt(r4);
        r6 = r0[r4];
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x001f, code lost:
        if (r6 >= 0) goto L_0x0028;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void quoteAsString(java.lang.CharSequence r8, java.lang.StringBuilder r9) {
        /*
            r7 = this;
            int[] r0 = com.fasterxml.jackson.core.io.CharTypes.get7BitOutputEscapes()
            int r1 = r0.length
            int r2 = r8.length()
            r3 = 0
            r4 = r3
        L_0x000b:
            if (r4 >= r2) goto L_0x003c
        L_0x000d:
            char r5 = r8.charAt(r4)
            if (r5 >= r1) goto L_0x0035
            r6 = r0[r5]
            if (r6 == 0) goto L_0x0035
            int r5 = r4 + 1
            char r4 = r8.charAt(r4)
            r6 = r0[r4]
            if (r6 >= 0) goto L_0x0028
            char[] r6 = r7._qbuf
            int r4 = r7.a(r4, r6)
            goto L_0x002e
        L_0x0028:
            char[] r4 = r7._qbuf
            int r4 = r7.b(r6, r4)
        L_0x002e:
            char[] r6 = r7._qbuf
            r9.append(r6, r3, r4)
            r4 = r5
            goto L_0x000b
        L_0x0035:
            r9.append(r5)
            int r4 = r4 + 1
            if (r4 < r2) goto L_0x000d
        L_0x003c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.io.JsonStringEncoder.quoteAsString(java.lang.CharSequence, java.lang.StringBuilder):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0042, code lost:
        if (r5 < r4.length) goto L_0x0049;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0044, code lost:
        r4 = r0.finishCurrentSegment();
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0049, code lost:
        r7 = r2 + 1;
        r2 = r11.charAt(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004f, code lost:
        if (r2 > 127) goto L_0x005d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0051, code lost:
        r5 = a(r2, r6[r2], r0, r5);
        r4 = r0.getCurrentSegment();
        r2 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x005f, code lost:
        if (r2 > 2047) goto L_0x0071;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0061, code lost:
        r5 = r5 + 1;
        r4[r5] = (byte) ((r2 >> 6) | 192);
        r2 = (r2 & '?') | 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0074, code lost:
        if (r2 < 55296) goto L_0x00d1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0079, code lost:
        if (r2 <= 57343) goto L_0x007c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x007f, code lost:
        if (r2 <= 56319) goto L_0x0084;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0081, code lost:
        a(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0084, code lost:
        if (r7 < r1) goto L_0x0089;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0086, code lost:
        a(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0089, code lost:
        r7 = r7 + 1;
        r2 = a(r2, r11.charAt(r7));
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0096, code lost:
        if (r2 <= 1114111) goto L_0x009b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0098, code lost:
        a(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x009b, code lost:
        r7 = r5 + 1;
        r4[r5] = (byte) ((r2 >> 18) | com.google.android.exoplayer2.extractor.ts.PsExtractor.VIDEO_STREAM_MASK);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a5, code lost:
        if (r7 < r4.length) goto L_0x00ac;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00a7, code lost:
        r4 = r0.finishCurrentSegment();
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ac, code lost:
        r5 = r7 + 1;
        r4[r7] = (byte) (((r2 >> 12) & 63) | 128);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b8, code lost:
        if (r5 < r4.length) goto L_0x00bf;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00ba, code lost:
        r4 = r0.finishCurrentSegment();
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00bf, code lost:
        r5 = r5 + 1;
        r4[r5] = (byte) (((r2 >> 6) & 63) | 128);
        r2 = (r2 & 63) | 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00d1, code lost:
        r6 = r5 + 1;
        r4[r5] = (byte) ((r2 >> '\f') | 224);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00db, code lost:
        if (r6 < r4.length) goto L_0x00e2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00dd, code lost:
        r4 = r0.finishCurrentSegment();
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00e2, code lost:
        r5 = r6 + 1;
        r4[r6] = (byte) (((r2 >> 6) & 63) | 128);
        r2 = (r2 & '?') | 128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00f2, code lost:
        if (r5 < r4.length) goto L_0x00f9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00f4, code lost:
        r4 = r0.finishCurrentSegment();
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00f9, code lost:
        r5 = r5 + 1;
        r4[r5] = (byte) r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public byte[] quoteAsUTF8(java.lang.String r11) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.io.JsonStringEncoder.quoteAsUTF8(java.lang.String):byte[]");
    }

    public byte[] encodeAsUTF8(String str) {
        int i;
        char c;
        ByteArrayBuilder byteArrayBuilder = this._bytes;
        if (byteArrayBuilder == null) {
            byteArrayBuilder = new ByteArrayBuilder((BufferRecycler) null);
            this._bytes = byteArrayBuilder;
        }
        int length = str.length();
        byte[] resetAndGetFirstSegment = byteArrayBuilder.resetAndGetFirstSegment();
        int length2 = resetAndGetFirstSegment.length;
        byte[] bArr = resetAndGetFirstSegment;
        int i2 = 0;
        int i3 = 0;
        loop0: while (true) {
            if (i2 >= length) {
                break;
            }
            i2++;
            char c2 = str.charAt(i2);
            while (c2 <= 127) {
                if (i3 >= length2) {
                    byte[] finishCurrentSegment = byteArrayBuilder.finishCurrentSegment();
                    i3 = 0;
                    bArr = finishCurrentSegment;
                    length2 = finishCurrentSegment.length;
                }
                int i4 = i3 + 1;
                bArr[i3] = (byte) c2;
                if (i2 >= length) {
                    i3 = i4;
                    break loop0;
                }
                i2++;
                c2 = str.charAt(i2);
                i3 = i4;
            }
            if (i3 >= length2) {
                bArr = byteArrayBuilder.finishCurrentSegment();
                length2 = bArr.length;
                i3 = 0;
            }
            if (c2 < 2048) {
                i = i3 + 1;
                bArr[i3] = (byte) ((c2 >> 6) | 192);
                c = c2;
            } else if (c2 < 55296 || c2 > 57343) {
                int i5 = i3 + 1;
                bArr[i3] = (byte) ((c2 >> '\f') | 224);
                if (i5 >= length2) {
                    bArr = byteArrayBuilder.finishCurrentSegment();
                    length2 = bArr.length;
                    i5 = 0;
                }
                i = i5 + 1;
                bArr[i5] = (byte) (((c2 >> 6) & 63) | 128);
                c = c2;
            } else {
                if (c2 > 56319) {
                    a(c2);
                }
                if (i2 >= length) {
                    a(c2);
                }
                i2++;
                int a2 = a(c2, str.charAt(i2));
                if (a2 > 1114111) {
                    a(a2);
                }
                int i6 = i3 + 1;
                bArr[i3] = (byte) ((a2 >> 18) | PsExtractor.VIDEO_STREAM_MASK);
                if (i6 >= length2) {
                    bArr = byteArrayBuilder.finishCurrentSegment();
                    length2 = bArr.length;
                    i6 = 0;
                }
                int i7 = i6 + 1;
                bArr[i6] = (byte) (((a2 >> 12) & 63) | 128);
                if (i7 >= length2) {
                    byte[] finishCurrentSegment2 = byteArrayBuilder.finishCurrentSegment();
                    i7 = 0;
                    bArr = finishCurrentSegment2;
                    length2 = finishCurrentSegment2.length;
                }
                i = i7 + 1;
                bArr[i7] = (byte) (((a2 >> 6) & 63) | 128);
                c = a2;
            }
            if (i >= length2) {
                byte[] finishCurrentSegment3 = byteArrayBuilder.finishCurrentSegment();
                i = 0;
                bArr = finishCurrentSegment3;
                length2 = finishCurrentSegment3.length;
            }
            i3 = i + 1;
            bArr[i] = (byte) (((c == 1 ? 1 : 0) & 63) | 128);
        }
        return this._bytes.completeAndCoalesce(i3);
    }

    private int a(int i, char[] cArr) {
        cArr[1] = 'u';
        char[] cArr2 = a;
        cArr[4] = cArr2[i >> 4];
        cArr[5] = cArr2[i & 15];
        return 6;
    }

    private int b(int i, char[] cArr) {
        cArr[1] = (char) i;
        return 2;
    }

    private int a(int i, int i2, ByteArrayBuilder byteArrayBuilder, int i3) {
        byteArrayBuilder.setCurrentSegmentLength(i3);
        byteArrayBuilder.append(92);
        if (i2 < 0) {
            byteArrayBuilder.append(117);
            if (i > 255) {
                int i4 = i >> 8;
                byteArrayBuilder.append(b[i4 >> 4]);
                byteArrayBuilder.append(b[i4 & 15]);
                i &= 255;
            } else {
                byteArrayBuilder.append(48);
                byteArrayBuilder.append(48);
            }
            byteArrayBuilder.append(b[i >> 4]);
            byteArrayBuilder.append(b[i & 15]);
        } else {
            byteArrayBuilder.append((byte) i2);
        }
        return byteArrayBuilder.getCurrentSegmentLength();
    }

    private static int a(int i, int i2) {
        if (i2 >= 56320 && i2 <= 57343) {
            return ((i - GeneratorBase.SURR1_FIRST) << 10) + 65536 + (i2 - 56320);
        }
        throw new IllegalArgumentException("Broken surrogate pair: first char 0x" + Integer.toHexString(i) + ", second 0x" + Integer.toHexString(i2) + "; illegal combination");
    }

    private static void a(int i) {
        throw new IllegalArgumentException(UTF8Writer.illegalSurrogateDesc(i));
    }
}
