package io.netty.handler.codec.http2.internal.hpack;

import io.netty.util.internal.EmptyArrays;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* compiled from: HuffmanDecoder.java */
/* loaded from: classes4.dex */
final class d {
    private static final IOException a = new IOException("HPACK - EOS Decoded");
    private static final IOException b = new IOException("HPACK - Invalid Padding");
    private final a c;

    static {
        a.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        b.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(int[] iArr, byte[] bArr) {
        if (iArr.length == 257 && iArr.length == bArr.length) {
            this.c = a(iArr, bArr);
            return;
        }
        throw new IllegalArgumentException("invalid Huffman coding");
    }

    public byte[] a(byte[] bArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        a aVar = this.c;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (byte b2 : bArr) {
            i = (i << 8) | (b2 & 255);
            i2 += 8;
            i3 += 8;
            while (i2 >= 8) {
                aVar = aVar.d[(i >>> (i2 - 8)) & 255];
                i2 -= aVar.c;
                if (aVar.a()) {
                    if (aVar.b != 256) {
                        byteArrayOutputStream.write(aVar.b);
                        aVar = this.c;
                        i3 = i2;
                    } else {
                        throw a;
                    }
                }
            }
        }
        while (i2 > 0) {
            a aVar2 = aVar.d[(i << (8 - i2)) & 255];
            if (!aVar2.a() || aVar2.c > i2) {
                break;
            } else if (aVar2.b != 256) {
                i3 = i2 - aVar2.c;
                byteArrayOutputStream.write(aVar2.b);
                aVar = this.c;
                i2 = i3;
            } else {
                throw a;
            }
        }
        int i4 = (1 << i3) - 1;
        if (i3 <= 7 && (i & i4) == i4) {
            return byteArrayOutputStream.toByteArray();
        }
        throw b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: HuffmanDecoder.java */
    /* loaded from: classes4.dex */
    public static final class a {
        static final /* synthetic */ boolean a = !d.class.desiredAssertionStatus();
        private final int b;
        private final int c;
        private final a[] d;

        private a() {
            this.b = 0;
            this.c = 8;
            this.d = new a[256];
        }

        private a(int i, int i2) {
            if (a || (i2 > 0 && i2 <= 8)) {
                this.b = i;
                this.c = i2;
                this.d = null;
                return;
            }
            throw new AssertionError();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean a() {
            return this.d == null;
        }
    }

    private static a a(int[] iArr, byte[] bArr) {
        a aVar = new a();
        for (int i = 0; i < iArr.length; i++) {
            a(aVar, i, iArr[i], bArr[i]);
        }
        return aVar;
    }

    private static void a(a aVar, int i, int i2, byte b2) {
        while (b2 > 8) {
            if (!aVar.a()) {
                b2 = (byte) (b2 - 8);
                int i3 = (i2 >>> b2) & 255;
                if (aVar.d[i3] == null) {
                    aVar.d[i3] = new a();
                }
                aVar = aVar.d[i3];
            } else {
                throw new IllegalStateException("invalid Huffman code: prefix not unique");
            }
        }
        a aVar2 = new a(i, b2);
        int i4 = 8 - b2;
        int i5 = (i2 << i4) & 255;
        int i6 = 1 << i4;
        for (int i7 = i5; i7 < i5 + i6; i7++) {
            aVar.d[i7] = aVar2;
        }
    }
}
