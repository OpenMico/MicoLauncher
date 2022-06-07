package com.google.protobuf;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.common.base.Ascii;
import com.xiaomi.mico.base.utils.CommonUtils;
import java.nio.ByteBuffer;
import okio.Utf8;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Utf8.java */
/* loaded from: classes2.dex */
public final class au {
    private static final b a;

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(int i) {
        if (i > -12) {
            return -1;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(int i, int i2) {
        if (i > -12 || i2 > -65) {
            return -1;
        }
        return i ^ (i2 << 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(int i, int i2, int i3) {
        if (i > -12 || i2 > -65 || i3 > -65) {
            return -1;
        }
        return (i ^ (i2 << 8)) ^ (i3 << 16);
    }

    static {
        a = (!e.a() || b.a()) ? new c() : new e();
    }

    public static boolean a(byte[] bArr) {
        return a.a(bArr, 0, bArr.length);
    }

    public static boolean a(byte[] bArr, int i, int i2) {
        return a.a(bArr, i, i2);
    }

    public static int a(int i, byte[] bArr, int i2, int i3) {
        return a.a(i, bArr, i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int d(byte[] bArr, int i, int i2) {
        byte b2 = bArr[i - 1];
        switch (i2 - i) {
            case 0:
                return b(b2);
            case 1:
                return b(b2, bArr[i]);
            case 2:
                return b(b2, bArr[i], bArr[i + 1]);
            default:
                throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(ByteBuffer byteBuffer, int i, int i2, int i3) {
        switch (i3) {
            case 0:
                return b(i);
            case 1:
                return b(i, byteBuffer.get(i2));
            case 2:
                return b(i, byteBuffer.get(i2), byteBuffer.get(i2 + 1));
            default:
                throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Utf8.java */
    /* loaded from: classes2.dex */
    public static class d extends IllegalArgumentException {
        d(int i, int i2) {
            super("Unpaired surrogate at index " + i + " of " + i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length && charSequence.charAt(i) < 128) {
            i++;
        }
        int i2 = length;
        while (true) {
            if (i < length) {
                char charAt = charSequence.charAt(i);
                if (charAt >= 2048) {
                    i2 += a(charSequence, i);
                    break;
                }
                i2 += (127 - charAt) >>> 31;
                i++;
            } else {
                break;
            }
        }
        if (i2 >= length) {
            return i2;
        }
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (i2 + 4294967296L));
    }

    private static int a(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt < 2048) {
                i2 += (127 - charAt) >>> 31;
            } else {
                i2 += 2;
                if (55296 <= charAt && charAt <= 57343) {
                    if (Character.codePointAt(charSequence, i) >= 65536) {
                        i++;
                    } else {
                        throw new d(i, length);
                    }
                }
            }
            i++;
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(CharSequence charSequence, byte[] bArr, int i, int i2) {
        return a.a(charSequence, bArr, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(ByteBuffer byteBuffer) {
        return a.a(byteBuffer, byteBuffer.position(), byteBuffer.remaining());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(int i, ByteBuffer byteBuffer, int i2, int i3) {
        return a.a(i, byteBuffer, i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(ByteBuffer byteBuffer, int i, int i2) throws InvalidProtocolBufferException {
        return a.b(byteBuffer, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String b(byte[] bArr, int i, int i2) throws InvalidProtocolBufferException {
        return a.b(bArr, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(CharSequence charSequence, ByteBuffer byteBuffer) {
        a.a(charSequence, byteBuffer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int c(ByteBuffer byteBuffer, int i, int i2) {
        int i3 = i2 - 7;
        int i4 = i;
        while (i4 < i3 && (byteBuffer.getLong(i4) & (-9187201950435737472L)) == 0) {
            i4 += 8;
        }
        return i4 - i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Utf8.java */
    /* loaded from: classes2.dex */
    public static abstract class b {
        abstract int a(int i, byte[] bArr, int i2, int i3);

        abstract int a(CharSequence charSequence, byte[] bArr, int i, int i2);

        abstract int b(int i, ByteBuffer byteBuffer, int i2, int i3);

        abstract String b(byte[] bArr, int i, int i2) throws InvalidProtocolBufferException;

        abstract void b(CharSequence charSequence, ByteBuffer byteBuffer);

        abstract String c(ByteBuffer byteBuffer, int i, int i2) throws InvalidProtocolBufferException;

        b() {
        }

        final boolean a(byte[] bArr, int i, int i2) {
            return a(0, bArr, i, i2) == 0;
        }

        final boolean a(ByteBuffer byteBuffer, int i, int i2) {
            return a(0, byteBuffer, i, i2) == 0;
        }

        final int a(int i, ByteBuffer byteBuffer, int i2, int i3) {
            if (byteBuffer.hasArray()) {
                int arrayOffset = byteBuffer.arrayOffset();
                return a(i, byteBuffer.array(), i2 + arrayOffset, arrayOffset + i3);
            } else if (byteBuffer.isDirect()) {
                return b(i, byteBuffer, i2, i3);
            } else {
                return c(i, byteBuffer, i2, i3);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0017, code lost:
            if (r8.get(r9) > (-65)) goto L_0x0019;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x0048, code lost:
            if (r8.get(r9) > (-65)) goto L_0x004a;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final int c(int r7, java.nio.ByteBuffer r8, int r9, int r10) {
            /*
                r6 = this;
                if (r7 == 0) goto L_0x008c
                if (r9 < r10) goto L_0x0005
                return r7
            L_0x0005:
                byte r0 = (byte) r7
                r1 = -32
                r2 = -1
                r3 = -65
                if (r0 >= r1) goto L_0x001a
                r7 = -62
                if (r0 < r7) goto L_0x0019
                int r7 = r9 + 1
                byte r9 = r8.get(r9)
                if (r9 <= r3) goto L_0x008d
            L_0x0019:
                return r2
            L_0x001a:
                r4 = -16
                if (r0 >= r4) goto L_0x004b
                int r7 = r7 >> 8
                int r7 = ~r7
                byte r7 = (byte) r7
                if (r7 != 0) goto L_0x0034
                int r7 = r9 + 1
                byte r9 = r8.get(r9)
                if (r7 < r10) goto L_0x0031
                int r7 = com.google.protobuf.au.a(r0, r9)
                return r7
            L_0x0031:
                r5 = r9
                r9 = r7
                r7 = r5
            L_0x0034:
                if (r7 > r3) goto L_0x004a
                r4 = -96
                if (r0 != r1) goto L_0x003c
                if (r7 < r4) goto L_0x004a
            L_0x003c:
                r1 = -19
                if (r0 != r1) goto L_0x0042
                if (r7 >= r4) goto L_0x004a
            L_0x0042:
                int r7 = r9 + 1
                byte r9 = r8.get(r9)
                if (r9 <= r3) goto L_0x008d
            L_0x004a:
                return r2
            L_0x004b:
                int r1 = r7 >> 8
                int r1 = ~r1
                byte r1 = (byte) r1
                r4 = 0
                if (r1 != 0) goto L_0x005f
                int r7 = r9 + 1
                byte r1 = r8.get(r9)
                if (r7 < r10) goto L_0x0063
                int r7 = com.google.protobuf.au.a(r0, r1)
                return r7
            L_0x005f:
                int r7 = r7 >> 16
                byte r4 = (byte) r7
                r7 = r9
            L_0x0063:
                if (r4 != 0) goto L_0x0073
                int r9 = r7 + 1
                byte r4 = r8.get(r7)
                if (r9 < r10) goto L_0x0072
                int r7 = com.google.protobuf.au.a(r0, r1, r4)
                return r7
            L_0x0072:
                r7 = r9
            L_0x0073:
                if (r1 > r3) goto L_0x008b
                int r9 = r0 << 28
                int r1 = r1 + 112
                int r9 = r9 + r1
                int r9 = r9 >> 30
                if (r9 != 0) goto L_0x008b
                if (r4 > r3) goto L_0x008b
                int r9 = r7 + 1
                byte r7 = r8.get(r7)
                if (r7 <= r3) goto L_0x0089
                goto L_0x008b
            L_0x0089:
                r7 = r9
                goto L_0x008d
            L_0x008b:
                return r2
            L_0x008c:
                r7 = r9
            L_0x008d:
                int r7 = e(r8, r7, r10)
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.au.b.c(int, java.nio.ByteBuffer, int, int):int");
        }

        private static int e(ByteBuffer byteBuffer, int i, int i2) {
            int c = i + au.c(byteBuffer, i, i2);
            while (c < i2) {
                int i3 = c + 1;
                byte b = byteBuffer.get(c);
                if (b >= 0) {
                    c = i3;
                } else if (b < -32) {
                    if (i3 >= i2) {
                        return b;
                    }
                    if (b < -62 || byteBuffer.get(i3) > -65) {
                        return -1;
                    }
                    c = i3 + 1;
                } else if (b < -16) {
                    if (i3 >= i2 - 1) {
                        return au.b(byteBuffer, b, i3, i2 - i3);
                    }
                    int i4 = i3 + 1;
                    byte b2 = byteBuffer.get(i3);
                    if (b2 > -65 || ((b == -32 && b2 < -96) || ((b == -19 && b2 >= -96) || byteBuffer.get(i4) > -65))) {
                        return -1;
                    }
                    c = i4 + 1;
                } else if (i3 >= i2 - 2) {
                    return au.b(byteBuffer, b, i3, i2 - i3);
                } else {
                    int i5 = i3 + 1;
                    byte b3 = byteBuffer.get(i3);
                    if (b3 <= -65 && (((b << 28) + (b3 + 112)) >> 30) == 0) {
                        int i6 = i5 + 1;
                        if (byteBuffer.get(i5) <= -65) {
                            c = i6 + 1;
                            if (byteBuffer.get(i6) > -65) {
                            }
                        }
                    }
                    return -1;
                }
            }
            return 0;
        }

        final String b(ByteBuffer byteBuffer, int i, int i2) throws InvalidProtocolBufferException {
            if (byteBuffer.hasArray()) {
                return b(byteBuffer.array(), byteBuffer.arrayOffset() + i, i2);
            } else if (byteBuffer.isDirect()) {
                return c(byteBuffer, i, i2);
            } else {
                return d(byteBuffer, i, i2);
            }
        }

        final String d(ByteBuffer byteBuffer, int i, int i2) throws InvalidProtocolBufferException {
            if ((i | i2 | ((byteBuffer.limit() - i) - i2)) >= 0) {
                int i3 = i + i2;
                char[] cArr = new char[i2];
                int i4 = 0;
                while (i < i3) {
                    byte b = byteBuffer.get(i);
                    if (!a.d(b)) {
                        break;
                    }
                    i++;
                    i4++;
                    a.b(b, cArr, i4);
                }
                int i5 = i4;
                while (i < i3) {
                    int i6 = i + 1;
                    byte b2 = byteBuffer.get(i);
                    if (a.d(b2)) {
                        int i7 = i5 + 1;
                        a.b(b2, cArr, i5);
                        while (i6 < i3) {
                            byte b3 = byteBuffer.get(i6);
                            if (!a.d(b3)) {
                                break;
                            }
                            i6++;
                            i7++;
                            a.b(b3, cArr, i7);
                        }
                        i = i6;
                        i5 = i7;
                    } else if (a.e(b2)) {
                        if (i6 < i3) {
                            i = i6 + 1;
                            i5++;
                            a.b(b2, byteBuffer.get(i6), cArr, i5);
                        } else {
                            throw InvalidProtocolBufferException.j();
                        }
                    } else if (a.f(b2)) {
                        if (i6 < i3 - 1) {
                            int i8 = i6 + 1;
                            i = i8 + 1;
                            i5++;
                            a.b(b2, byteBuffer.get(i6), byteBuffer.get(i8), cArr, i5);
                        } else {
                            throw InvalidProtocolBufferException.j();
                        }
                    } else if (i6 < i3 - 2) {
                        int i9 = i6 + 1;
                        byte b4 = byteBuffer.get(i6);
                        int i10 = i9 + 1;
                        i = i10 + 1;
                        a.b(b2, b4, byteBuffer.get(i9), byteBuffer.get(i10), cArr, i5);
                        i5 = i5 + 1 + 1;
                    } else {
                        throw InvalidProtocolBufferException.j();
                    }
                }
                return new String(cArr, 0, i5);
            }
            throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", Integer.valueOf(byteBuffer.limit()), Integer.valueOf(i), Integer.valueOf(i2)));
        }

        final void a(CharSequence charSequence, ByteBuffer byteBuffer) {
            if (byteBuffer.hasArray()) {
                int arrayOffset = byteBuffer.arrayOffset();
                byteBuffer.position(au.a(charSequence, byteBuffer.array(), byteBuffer.position() + arrayOffset, byteBuffer.remaining()) - arrayOffset);
            } else if (byteBuffer.isDirect()) {
                b(charSequence, byteBuffer);
            } else {
                c(charSequence, byteBuffer);
            }
        }

        final void c(CharSequence charSequence, ByteBuffer byteBuffer) {
            int length = charSequence.length();
            int position = byteBuffer.position();
            int i = 0;
            while (i < length) {
                try {
                    char charAt = charSequence.charAt(i);
                    if (charAt >= 128) {
                        break;
                    }
                    byteBuffer.put(position + i, (byte) charAt);
                    i++;
                } catch (IndexOutOfBoundsException unused) {
                    throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i) + " at index " + (byteBuffer.position() + Math.max(i, (position - byteBuffer.position()) + 1)));
                }
            }
            if (i == length) {
                byteBuffer.position(position + i);
                return;
            }
            position += i;
            while (i < length) {
                char charAt2 = charSequence.charAt(i);
                if (charAt2 < 128) {
                    byteBuffer.put(position, (byte) charAt2);
                } else if (charAt2 < 2048) {
                    int i2 = position + 1;
                    try {
                        byteBuffer.put(position, (byte) ((charAt2 >>> 6) | 192));
                        byteBuffer.put(i2, (byte) ((charAt2 & '?') | 128));
                        position = i2;
                    } catch (IndexOutOfBoundsException unused2) {
                        position = i2;
                        throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i) + " at index " + (byteBuffer.position() + Math.max(i, (position - byteBuffer.position()) + 1)));
                    }
                } else if (charAt2 < 55296 || 57343 < charAt2) {
                    int i3 = position + 1;
                    byteBuffer.put(position, (byte) ((charAt2 >>> '\f') | 224));
                    position = i3 + 1;
                    byteBuffer.put(i3, (byte) (((charAt2 >>> 6) & 63) | 128));
                    byteBuffer.put(position, (byte) ((charAt2 & '?') | 128));
                } else {
                    int i4 = i + 1;
                    if (i4 != length) {
                        try {
                            char charAt3 = charSequence.charAt(i4);
                            if (Character.isSurrogatePair(charAt2, charAt3)) {
                                int codePoint = Character.toCodePoint(charAt2, charAt3);
                                int i5 = position + 1;
                                try {
                                    byteBuffer.put(position, (byte) ((codePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                                    int i6 = i5 + 1;
                                    byteBuffer.put(i5, (byte) (((codePoint >>> 12) & 63) | 128));
                                    int i7 = i6 + 1;
                                    byteBuffer.put(i6, (byte) (((codePoint >>> 6) & 63) | 128));
                                    byteBuffer.put(i7, (byte) ((codePoint & 63) | 128));
                                    position = i7;
                                    i = i4;
                                } catch (IndexOutOfBoundsException unused3) {
                                    position = i5;
                                    i = i4;
                                    throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i) + " at index " + (byteBuffer.position() + Math.max(i, (position - byteBuffer.position()) + 1)));
                                }
                            } else {
                                i = i4;
                            }
                        } catch (IndexOutOfBoundsException unused4) {
                        }
                    }
                    throw new d(i, length);
                }
                i++;
                position++;
            }
            byteBuffer.position(position);
        }
    }

    /* compiled from: Utf8.java */
    /* loaded from: classes2.dex */
    static final class c extends b {
        c() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0015, code lost:
            if (r8[r9] > (-65)) goto L_0x0017;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x0042, code lost:
            if (r8[r9] > (-65)) goto L_0x0044;
         */
        @Override // com.google.protobuf.au.b
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        int a(int r7, byte[] r8, int r9, int r10) {
            /*
                r6 = this;
                if (r7 == 0) goto L_0x0080
                if (r9 < r10) goto L_0x0005
                return r7
            L_0x0005:
                byte r0 = (byte) r7
                r1 = -32
                r2 = -1
                r3 = -65
                if (r0 >= r1) goto L_0x0018
                r7 = -62
                if (r0 < r7) goto L_0x0017
                int r7 = r9 + 1
                byte r9 = r8[r9]
                if (r9 <= r3) goto L_0x0081
            L_0x0017:
                return r2
            L_0x0018:
                r4 = -16
                if (r0 >= r4) goto L_0x0045
                int r7 = r7 >> 8
                int r7 = ~r7
                byte r7 = (byte) r7
                if (r7 != 0) goto L_0x0030
                int r7 = r9 + 1
                byte r9 = r8[r9]
                if (r7 < r10) goto L_0x002d
                int r7 = com.google.protobuf.au.a(r0, r9)
                return r7
            L_0x002d:
                r5 = r9
                r9 = r7
                r7 = r5
            L_0x0030:
                if (r7 > r3) goto L_0x0044
                r4 = -96
                if (r0 != r1) goto L_0x0038
                if (r7 < r4) goto L_0x0044
            L_0x0038:
                r1 = -19
                if (r0 != r1) goto L_0x003e
                if (r7 >= r4) goto L_0x0044
            L_0x003e:
                int r7 = r9 + 1
                byte r9 = r8[r9]
                if (r9 <= r3) goto L_0x0081
            L_0x0044:
                return r2
            L_0x0045:
                int r1 = r7 >> 8
                int r1 = ~r1
                byte r1 = (byte) r1
                r4 = 0
                if (r1 != 0) goto L_0x0057
                int r7 = r9 + 1
                byte r1 = r8[r9]
                if (r7 < r10) goto L_0x005b
                int r7 = com.google.protobuf.au.a(r0, r1)
                return r7
            L_0x0057:
                int r7 = r7 >> 16
                byte r4 = (byte) r7
                r7 = r9
            L_0x005b:
                if (r4 != 0) goto L_0x0069
                int r9 = r7 + 1
                byte r4 = r8[r7]
                if (r9 < r10) goto L_0x0068
                int r7 = com.google.protobuf.au.a(r0, r1, r4)
                return r7
            L_0x0068:
                r7 = r9
            L_0x0069:
                if (r1 > r3) goto L_0x007f
                int r9 = r0 << 28
                int r1 = r1 + 112
                int r9 = r9 + r1
                int r9 = r9 >> 30
                if (r9 != 0) goto L_0x007f
                if (r4 > r3) goto L_0x007f
                int r9 = r7 + 1
                byte r7 = r8[r7]
                if (r7 <= r3) goto L_0x007d
                goto L_0x007f
            L_0x007d:
                r7 = r9
                goto L_0x0081
            L_0x007f:
                return r2
            L_0x0080:
                r7 = r9
            L_0x0081:
                int r7 = c(r8, r7, r10)
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.au.c.a(int, byte[], int, int):int");
        }

        @Override // com.google.protobuf.au.b
        int b(int i, ByteBuffer byteBuffer, int i2, int i3) {
            return c(i, byteBuffer, i2, i3);
        }

        @Override // com.google.protobuf.au.b
        String b(byte[] bArr, int i, int i2) throws InvalidProtocolBufferException {
            if ((i | i2 | ((bArr.length - i) - i2)) >= 0) {
                int i3 = i + i2;
                char[] cArr = new char[i2];
                int i4 = 0;
                while (i < i3) {
                    byte b = bArr[i];
                    if (!a.d(b)) {
                        break;
                    }
                    i++;
                    i4++;
                    a.b(b, cArr, i4);
                }
                int i5 = i4;
                while (i < i3) {
                    int i6 = i + 1;
                    byte b2 = bArr[i];
                    if (a.d(b2)) {
                        int i7 = i5 + 1;
                        a.b(b2, cArr, i5);
                        while (i6 < i3) {
                            byte b3 = bArr[i6];
                            if (!a.d(b3)) {
                                break;
                            }
                            i6++;
                            i7++;
                            a.b(b3, cArr, i7);
                        }
                        i = i6;
                        i5 = i7;
                    } else if (a.e(b2)) {
                        if (i6 < i3) {
                            i = i6 + 1;
                            i5++;
                            a.b(b2, bArr[i6], cArr, i5);
                        } else {
                            throw InvalidProtocolBufferException.j();
                        }
                    } else if (a.f(b2)) {
                        if (i6 < i3 - 1) {
                            int i8 = i6 + 1;
                            i = i8 + 1;
                            i5++;
                            a.b(b2, bArr[i6], bArr[i8], cArr, i5);
                        } else {
                            throw InvalidProtocolBufferException.j();
                        }
                    } else if (i6 < i3 - 2) {
                        int i9 = i6 + 1;
                        byte b4 = bArr[i6];
                        int i10 = i9 + 1;
                        i = i10 + 1;
                        a.b(b2, b4, bArr[i9], bArr[i10], cArr, i5);
                        i5 = i5 + 1 + 1;
                    } else {
                        throw InvalidProtocolBufferException.j();
                    }
                }
                return new String(cArr, 0, i5);
            }
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
        }

        @Override // com.google.protobuf.au.b
        String c(ByteBuffer byteBuffer, int i, int i2) throws InvalidProtocolBufferException {
            return d(byteBuffer, i, i2);
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x001d, code lost:
            return r10 + r0;
         */
        @Override // com.google.protobuf.au.b
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        int a(java.lang.CharSequence r8, byte[] r9, int r10, int r11) {
            /*
                Method dump skipped, instructions count: 255
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.au.c.a(java.lang.CharSequence, byte[], int, int):int");
        }

        @Override // com.google.protobuf.au.b
        void b(CharSequence charSequence, ByteBuffer byteBuffer) {
            c(charSequence, byteBuffer);
        }

        private static int c(byte[] bArr, int i, int i2) {
            while (i < i2 && bArr[i] >= 0) {
                i++;
            }
            if (i >= i2) {
                return 0;
            }
            return d(bArr, i, i2);
        }

        private static int d(byte[] bArr, int i, int i2) {
            while (i < i2) {
                int i3 = i + 1;
                byte b = bArr[i];
                if (b >= 0) {
                    i = i3;
                } else if (b < -32) {
                    if (i3 >= i2) {
                        return b;
                    }
                    if (b >= -62) {
                        i = i3 + 1;
                        if (bArr[i3] > -65) {
                        }
                    }
                    return -1;
                } else if (b < -16) {
                    if (i3 >= i2 - 1) {
                        return au.d(bArr, i3, i2);
                    }
                    int i4 = i3 + 1;
                    byte b2 = bArr[i3];
                    if (b2 <= -65 && ((b != -32 || b2 >= -96) && (b != -19 || b2 < -96))) {
                        i = i4 + 1;
                        if (bArr[i4] > -65) {
                        }
                    }
                    return -1;
                } else if (i3 >= i2 - 2) {
                    return au.d(bArr, i3, i2);
                } else {
                    int i5 = i3 + 1;
                    byte b3 = bArr[i3];
                    if (b3 <= -65 && (((b << 28) + (b3 + 112)) >> 30) == 0) {
                        int i6 = i5 + 1;
                        if (bArr[i5] <= -65) {
                            i = i6 + 1;
                            if (bArr[i6] > -65) {
                            }
                        }
                    }
                    return -1;
                }
            }
            return 0;
        }
    }

    /* compiled from: Utf8.java */
    /* loaded from: classes2.dex */
    static final class e extends b {
        e() {
        }

        static boolean a() {
            return at.a() && at.b();
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0025, code lost:
            if (com.google.protobuf.at.a(r13, r2) > (-65)) goto L_0x0027;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0056, code lost:
            if (com.google.protobuf.at.a(r13, r2) > (-65)) goto L_0x0058;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x0098, code lost:
            if (com.google.protobuf.at.a(r13, r2) > (-65)) goto L_0x009a;
         */
        @Override // com.google.protobuf.au.b
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        int a(int r12, byte[] r13, int r14, int r15) {
            /*
                Method dump skipped, instructions count: 199
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.au.e.a(int, byte[], int, int):int");
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x002f, code lost:
            if (com.google.protobuf.at.a(r2) > (-65)) goto L_0x0031;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0060, code lost:
            if (com.google.protobuf.at.a(r2) > (-65)) goto L_0x0062;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x00a2, code lost:
            if (com.google.protobuf.at.a(r2) > (-65)) goto L_0x00a4;
         */
        @Override // com.google.protobuf.au.b
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        int b(int r11, java.nio.ByteBuffer r12, int r13, int r14) {
            /*
                Method dump skipped, instructions count: 212
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.au.e.b(int, java.nio.ByteBuffer, int, int):int");
        }

        @Override // com.google.protobuf.au.b
        String b(byte[] bArr, int i, int i2) throws InvalidProtocolBufferException {
            if ((i | i2 | ((bArr.length - i) - i2)) >= 0) {
                int i3 = i + i2;
                char[] cArr = new char[i2];
                int i4 = 0;
                while (i < i3) {
                    byte a = at.a(bArr, i);
                    if (!a.d(a)) {
                        break;
                    }
                    i++;
                    i4++;
                    a.b(a, cArr, i4);
                }
                int i5 = i4;
                while (i < i3) {
                    int i6 = i + 1;
                    byte a2 = at.a(bArr, i);
                    if (a.d(a2)) {
                        int i7 = i5 + 1;
                        a.b(a2, cArr, i5);
                        while (i6 < i3) {
                            byte a3 = at.a(bArr, i6);
                            if (!a.d(a3)) {
                                break;
                            }
                            i6++;
                            i7++;
                            a.b(a3, cArr, i7);
                        }
                        i = i6;
                        i5 = i7;
                    } else if (a.e(a2)) {
                        if (i6 < i3) {
                            i = i6 + 1;
                            i5++;
                            a.b(a2, at.a(bArr, i6), cArr, i5);
                        } else {
                            throw InvalidProtocolBufferException.j();
                        }
                    } else if (a.f(a2)) {
                        if (i6 < i3 - 1) {
                            int i8 = i6 + 1;
                            i = i8 + 1;
                            i5++;
                            a.b(a2, at.a(bArr, i6), at.a(bArr, i8), cArr, i5);
                        } else {
                            throw InvalidProtocolBufferException.j();
                        }
                    } else if (i6 < i3 - 2) {
                        int i9 = i6 + 1;
                        int i10 = i9 + 1;
                        i = i10 + 1;
                        a.b(a2, at.a(bArr, i6), at.a(bArr, i9), at.a(bArr, i10), cArr, i5);
                        i5 = i5 + 1 + 1;
                    } else {
                        throw InvalidProtocolBufferException.j();
                    }
                }
                return new String(cArr, 0, i5);
            }
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
        }

        @Override // com.google.protobuf.au.b
        String c(ByteBuffer byteBuffer, int i, int i2) throws InvalidProtocolBufferException {
            if ((i | i2 | ((byteBuffer.limit() - i) - i2)) >= 0) {
                long a = at.a(byteBuffer) + i;
                long j = i2 + a;
                char[] cArr = new char[i2];
                int i3 = 0;
                while (a < j) {
                    byte a2 = at.a(a);
                    if (!a.d(a2)) {
                        break;
                    }
                    a++;
                    i3++;
                    a.b(a2, cArr, i3);
                }
                int i4 = i3;
                while (a < j) {
                    long j2 = a + 1;
                    byte a3 = at.a(a);
                    if (a.d(a3)) {
                        int i5 = i4 + 1;
                        a.b(a3, cArr, i4);
                        while (j2 < j) {
                            byte a4 = at.a(j2);
                            if (!a.d(a4)) {
                                break;
                            }
                            j2++;
                            i5++;
                            a.b(a4, cArr, i5);
                        }
                        i4 = i5;
                        a = j2;
                    } else if (a.e(a3)) {
                        if (j2 < j) {
                            a = j2 + 1;
                            i4++;
                            a.b(a3, at.a(j2), cArr, i4);
                        } else {
                            throw InvalidProtocolBufferException.j();
                        }
                    } else if (a.f(a3)) {
                        if (j2 < j - 1) {
                            long j3 = j2 + 1;
                            a = j3 + 1;
                            i4++;
                            a.b(a3, at.a(j2), at.a(j3), cArr, i4);
                        } else {
                            throw InvalidProtocolBufferException.j();
                        }
                    } else if (j2 < j - 2) {
                        long j4 = j2 + 1;
                        byte a5 = at.a(j2);
                        long j5 = j4 + 1;
                        byte a6 = at.a(j4);
                        a = j5 + 1;
                        a.b(a3, a5, a6, at.a(j5), cArr, i4);
                        i4 = i4 + 1 + 1;
                    } else {
                        throw InvalidProtocolBufferException.j();
                    }
                }
                return new String(cArr, 0, i4);
            }
            throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", Integer.valueOf(byteBuffer.limit()), Integer.valueOf(i), Integer.valueOf(i2)));
        }

        @Override // com.google.protobuf.au.b
        int a(CharSequence charSequence, byte[] bArr, int i, int i2) {
            int i3;
            char charAt;
            long j = i;
            long j2 = i2 + j;
            int length = charSequence.length();
            if (length > i2 || bArr.length - i2 < i) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(length - 1) + " at index " + (i + i2));
            }
            int i4 = 0;
            while (i4 < length && (charAt = charSequence.charAt(i4)) < 128) {
                j = 1 + j;
                at.a(bArr, j, (byte) charAt);
                i4++;
            }
            if (i4 == length) {
                return (int) j;
            }
            while (i4 < length) {
                char charAt2 = charSequence.charAt(i4);
                if (charAt2 < 128 && j < j2) {
                    j++;
                    at.a(bArr, j, (byte) charAt2);
                } else if (charAt2 < 2048 && j <= j2 - 2) {
                    long j3 = j + 1;
                    at.a(bArr, j, (byte) ((charAt2 >>> 6) | 960));
                    j = j3 + 1;
                    at.a(bArr, j3, (byte) ((charAt2 & '?') | 128));
                } else if ((charAt2 < 55296 || 57343 < charAt2) && j <= j2 - 3) {
                    long j4 = j + 1;
                    at.a(bArr, j, (byte) ((charAt2 >>> '\f') | CommonUtils.IMAGE_WIDTH_THRESHOLD));
                    long j5 = j4 + 1;
                    at.a(bArr, j4, (byte) (((charAt2 >>> 6) & 63) | 128));
                    j = j5 + 1;
                    at.a(bArr, j5, (byte) ((charAt2 & '?') | 128));
                } else if (j <= j2 - 4) {
                    int i5 = i4 + 1;
                    if (i5 != length) {
                        char charAt3 = charSequence.charAt(i5);
                        if (Character.isSurrogatePair(charAt2, charAt3)) {
                            int codePoint = Character.toCodePoint(charAt2, charAt3);
                            long j6 = j + 1;
                            at.a(bArr, j, (byte) ((codePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                            long j7 = j6 + 1;
                            at.a(bArr, j6, (byte) (((codePoint >>> 12) & 63) | 128));
                            long j8 = j7 + 1;
                            at.a(bArr, j7, (byte) (((codePoint >>> 6) & 63) | 128));
                            j = j8 + 1;
                            at.a(bArr, j8, (byte) ((codePoint & 63) | 128));
                            i4 = i5;
                        }
                    } else {
                        i5 = i4;
                    }
                    throw new d(i5 - 1, length);
                } else if (55296 > charAt2 || charAt2 > 57343 || ((i3 = i4 + 1) != length && Character.isSurrogatePair(charAt2, charSequence.charAt(i3)))) {
                    throw new ArrayIndexOutOfBoundsException("Failed writing " + charAt2 + " at index " + j);
                } else {
                    throw new d(i4, length);
                }
                i4++;
            }
            return (int) j;
        }

        @Override // com.google.protobuf.au.b
        void b(CharSequence charSequence, ByteBuffer byteBuffer) {
            char c;
            long j;
            int i;
            char charAt;
            long a = at.a(byteBuffer);
            long position = byteBuffer.position() + a;
            long limit = byteBuffer.limit() + a;
            int length = charSequence.length();
            if (length <= limit - position) {
                int i2 = 0;
                while (true) {
                    c = 128;
                    j = 1;
                    if (i2 >= length || (charAt = charSequence.charAt(i2)) >= 128) {
                        break;
                    }
                    position++;
                    at.a(position, (byte) charAt);
                    i2++;
                }
                if (i2 == length) {
                    byteBuffer.position((int) (position - a));
                    return;
                }
                while (i2 < length) {
                    char charAt2 = charSequence.charAt(i2);
                    if (charAt2 < c && position < limit) {
                        position += j;
                        at.a(position, (byte) charAt2);
                        j = j;
                        c = c;
                    } else if (charAt2 < 2048 && position <= limit - 2) {
                        long j2 = position + j;
                        at.a(position, (byte) ((charAt2 >>> 6) | 960));
                        position = j2 + j;
                        at.a(j2, (byte) ((charAt2 & '?') | 128));
                        j = j;
                        c = 128;
                    } else if ((charAt2 < 55296 || 57343 < charAt2) && position <= limit - 3) {
                        long j3 = position + j;
                        at.a(position, (byte) ((charAt2 >>> '\f') | CommonUtils.IMAGE_WIDTH_THRESHOLD));
                        long j4 = j3 + j;
                        at.a(j3, (byte) (((charAt2 >>> 6) & 63) | 128));
                        position = j4 + 1;
                        at.a(j4, (byte) ((charAt2 & '?') | 128));
                        j = 1;
                        c = 128;
                    } else if (position <= limit - 4) {
                        int i3 = i2 + 1;
                        if (i3 != length) {
                            char charAt3 = charSequence.charAt(i3);
                            if (Character.isSurrogatePair(charAt2, charAt3)) {
                                int codePoint = Character.toCodePoint(charAt2, charAt3);
                                long j5 = position + 1;
                                at.a(position, (byte) ((codePoint >>> 18) | PsExtractor.VIDEO_STREAM_MASK));
                                long j6 = j5 + 1;
                                c = 128;
                                at.a(j5, (byte) (((codePoint >>> 12) & 63) | 128));
                                long j7 = j6 + 1;
                                at.a(j6, (byte) (((codePoint >>> 6) & 63) | 128));
                                j = 1;
                                position = j7 + 1;
                                at.a(j7, (byte) ((codePoint & 63) | 128));
                                i2 = i3;
                            } else {
                                i2 = i3;
                            }
                        }
                        throw new d(i2 - 1, length);
                    } else if (55296 > charAt2 || charAt2 > 57343 || ((i = i2 + 1) != length && Character.isSurrogatePair(charAt2, charSequence.charAt(i)))) {
                        throw new ArrayIndexOutOfBoundsException("Failed writing " + charAt2 + " at index " + position);
                    } else {
                        throw new d(i2, length);
                    }
                    i2++;
                }
                byteBuffer.position((int) (position - a));
                return;
            }
            throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(length - 1) + " at index " + byteBuffer.limit());
        }

        private static int a(byte[] bArr, long j, int i) {
            if (i < 16) {
                return 0;
            }
            for (int i2 = 0; i2 < i; i2++) {
                j = 1 + j;
                if (at.a(bArr, j) < 0) {
                    return i2;
                }
            }
            return i;
        }

        private static int a(long j, int i) {
            if (i < 16) {
                return 0;
            }
            int i2 = 8 - (((int) j) & 7);
            long j2 = j;
            for (int i3 = i2; i3 > 0; i3--) {
                j2 = 1 + j2;
                if (at.a(j2) < 0) {
                    return i2 - i3;
                }
            }
            int i4 = i - i2;
            while (i4 >= 8 && (at.b(j2) & (-9187201950435737472L)) == 0) {
                j2 += 8;
                i4 -= 8;
            }
            return i - i4;
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x0039, code lost:
            return -1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private static int b(byte[] r8, long r9, int r11) {
            /*
                int r0 = a(r8, r9, r11)
                int r11 = r11 - r0
                long r0 = (long) r0
                long r9 = r9 + r0
            L_0x0007:
                r0 = 0
                r1 = r0
            L_0x0009:
                r2 = 1
                if (r11 <= 0) goto L_0x001a
                long r4 = r9 + r2
                byte r1 = com.google.protobuf.at.a(r8, r9)
                if (r1 < 0) goto L_0x0019
                int r11 = r11 + (-1)
                r9 = r4
                goto L_0x0009
            L_0x0019:
                r9 = r4
            L_0x001a:
                if (r11 != 0) goto L_0x001d
                return r0
            L_0x001d:
                int r11 = r11 + (-1)
                r0 = -32
                r4 = -65
                r5 = -1
                if (r1 >= r0) goto L_0x003a
                if (r11 != 0) goto L_0x0029
                return r1
            L_0x0029:
                int r11 = r11 + (-1)
                r0 = -62
                if (r1 < r0) goto L_0x0039
                long r2 = r2 + r9
                byte r9 = com.google.protobuf.at.a(r8, r9)
                if (r9 <= r4) goto L_0x0037
                goto L_0x0039
            L_0x0037:
                r9 = r2
                goto L_0x0007
            L_0x0039:
                return r5
            L_0x003a:
                r6 = -16
                if (r1 >= r6) goto L_0x0067
                r6 = 2
                if (r11 >= r6) goto L_0x0046
                int r8 = a(r8, r1, r9, r11)
                return r8
            L_0x0046:
                int r11 = r11 + (-2)
                long r6 = r9 + r2
                byte r9 = com.google.protobuf.at.a(r8, r9)
                if (r9 > r4) goto L_0x0066
                r10 = -96
                if (r1 != r0) goto L_0x0056
                if (r9 < r10) goto L_0x0066
            L_0x0056:
                r0 = -19
                if (r1 != r0) goto L_0x005c
                if (r9 >= r10) goto L_0x0066
            L_0x005c:
                long r2 = r2 + r6
                byte r9 = com.google.protobuf.at.a(r8, r6)
                if (r9 <= r4) goto L_0x0064
                goto L_0x0066
            L_0x0064:
                r9 = r2
                goto L_0x0007
            L_0x0066:
                return r5
            L_0x0067:
                r0 = 3
                if (r11 >= r0) goto L_0x006f
                int r8 = a(r8, r1, r9, r11)
                return r8
            L_0x006f:
                int r11 = r11 + (-3)
                long r6 = r9 + r2
                byte r9 = com.google.protobuf.at.a(r8, r9)
                if (r9 > r4) goto L_0x0095
                int r10 = r1 << 28
                int r9 = r9 + 112
                int r10 = r10 + r9
                int r9 = r10 >> 30
                if (r9 != 0) goto L_0x0095
                long r9 = r6 + r2
                byte r0 = com.google.protobuf.at.a(r8, r6)
                if (r0 > r4) goto L_0x0095
                long r2 = r2 + r9
                byte r9 = com.google.protobuf.at.a(r8, r9)
                if (r9 <= r4) goto L_0x0092
                goto L_0x0095
            L_0x0092:
                r9 = r2
                goto L_0x0007
            L_0x0095:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.au.e.b(byte[], long, int):int");
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x0039, code lost:
            return -1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private static int b(long r8, int r10) {
            /*
                int r0 = a(r8, r10)
                long r1 = (long) r0
                long r8 = r8 + r1
                int r10 = r10 - r0
            L_0x0007:
                r0 = 0
                r1 = r0
            L_0x0009:
                r2 = 1
                if (r10 <= 0) goto L_0x001a
                long r4 = r8 + r2
                byte r1 = com.google.protobuf.at.a(r8)
                if (r1 < 0) goto L_0x0019
                int r10 = r10 + (-1)
                r8 = r4
                goto L_0x0009
            L_0x0019:
                r8 = r4
            L_0x001a:
                if (r10 != 0) goto L_0x001d
                return r0
            L_0x001d:
                int r10 = r10 + (-1)
                r0 = -32
                r4 = -65
                r5 = -1
                if (r1 >= r0) goto L_0x003a
                if (r10 != 0) goto L_0x0029
                return r1
            L_0x0029:
                int r10 = r10 + (-1)
                r0 = -62
                if (r1 < r0) goto L_0x0039
                long r2 = r2 + r8
                byte r8 = com.google.protobuf.at.a(r8)
                if (r8 <= r4) goto L_0x0037
                goto L_0x0039
            L_0x0037:
                r8 = r2
                goto L_0x0007
            L_0x0039:
                return r5
            L_0x003a:
                r6 = -16
                if (r1 >= r6) goto L_0x0067
                r6 = 2
                if (r10 >= r6) goto L_0x0046
                int r8 = a(r8, r1, r10)
                return r8
            L_0x0046:
                int r10 = r10 + (-2)
                long r6 = r8 + r2
                byte r8 = com.google.protobuf.at.a(r8)
                if (r8 > r4) goto L_0x0066
                r9 = -96
                if (r1 != r0) goto L_0x0056
                if (r8 < r9) goto L_0x0066
            L_0x0056:
                r0 = -19
                if (r1 != r0) goto L_0x005c
                if (r8 >= r9) goto L_0x0066
            L_0x005c:
                long r2 = r2 + r6
                byte r8 = com.google.protobuf.at.a(r6)
                if (r8 <= r4) goto L_0x0064
                goto L_0x0066
            L_0x0064:
                r8 = r2
                goto L_0x0007
            L_0x0066:
                return r5
            L_0x0067:
                r0 = 3
                if (r10 >= r0) goto L_0x006f
                int r8 = a(r8, r1, r10)
                return r8
            L_0x006f:
                int r10 = r10 + (-3)
                long r6 = r8 + r2
                byte r8 = com.google.protobuf.at.a(r8)
                if (r8 > r4) goto L_0x0095
                int r9 = r1 << 28
                int r8 = r8 + 112
                int r9 = r9 + r8
                int r8 = r9 >> 30
                if (r8 != 0) goto L_0x0095
                long r8 = r6 + r2
                byte r0 = com.google.protobuf.at.a(r6)
                if (r0 > r4) goto L_0x0095
                long r2 = r2 + r8
                byte r8 = com.google.protobuf.at.a(r8)
                if (r8 <= r4) goto L_0x0092
                goto L_0x0095
            L_0x0092:
                r8 = r2
                goto L_0x0007
            L_0x0095:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.au.e.b(long, int):int");
        }

        private static int a(byte[] bArr, int i, long j, int i2) {
            switch (i2) {
                case 0:
                    return au.b(i);
                case 1:
                    return au.b(i, at.a(bArr, j));
                case 2:
                    return au.b(i, at.a(bArr, j), at.a(bArr, j + 1));
                default:
                    throw new AssertionError();
            }
        }

        private static int a(long j, int i, int i2) {
            switch (i2) {
                case 0:
                    return au.b(i);
                case 1:
                    return au.b(i, at.a(j));
                case 2:
                    return au.b(i, at.a(j), at.a(j + 1));
                default:
                    throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: Utf8.java */
    /* loaded from: classes2.dex */
    public static class a {
        private static char a(int i) {
            return (char) ((i >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
        }

        private static char b(int i) {
            return (char) ((i & AnalyticsListener.EVENT_DROPPED_VIDEO_FRAMES) + 56320);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean d(byte b) {
            return b >= 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean e(byte b) {
            return b < -32;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean f(byte b) {
            return b < -16;
        }

        private static boolean g(byte b) {
            return b > -65;
        }

        private static int h(byte b) {
            return b & Utf8.REPLACEMENT_BYTE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void b(byte b, char[] cArr, int i) {
            cArr[i] = (char) b;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void b(byte b, byte b2, char[] cArr, int i) throws InvalidProtocolBufferException {
            if (b < -62 || g(b2)) {
                throw InvalidProtocolBufferException.j();
            }
            cArr[i] = (char) (((b & Ascii.US) << 6) | h(b2));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void b(byte b, byte b2, byte b3, char[] cArr, int i) throws InvalidProtocolBufferException {
            if (g(b2) || ((b == -32 && b2 < -96) || ((b == -19 && b2 >= -96) || g(b3)))) {
                throw InvalidProtocolBufferException.j();
            }
            cArr[i] = (char) (((b & 15) << 12) | (h(b2) << 6) | h(b3));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void b(byte b, byte b2, byte b3, byte b4, char[] cArr, int i) throws InvalidProtocolBufferException {
            if (g(b2) || (((b << 28) + (b2 + 112)) >> 30) != 0 || g(b3) || g(b4)) {
                throw InvalidProtocolBufferException.j();
            }
            int h = ((b & 7) << 18) | (h(b2) << 12) | (h(b3) << 6) | h(b4);
            cArr[i] = a(h);
            cArr[i + 1] = b(h);
        }
    }
}
