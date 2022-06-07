package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;

/* compiled from: ReaderInputStream.java */
@GwtIncompatible
/* loaded from: classes2.dex */
final class f extends InputStream {
    private final Reader a;
    private final CharsetEncoder b;
    private final byte[] c;
    private CharBuffer d;
    private ByteBuffer e;
    private boolean f;
    private boolean g;
    private boolean h;

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(Reader reader, Charset charset, int i) {
        this(reader, charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE), i);
    }

    f(Reader reader, CharsetEncoder charsetEncoder, int i) {
        boolean z = true;
        this.c = new byte[1];
        this.a = (Reader) Preconditions.checkNotNull(reader);
        this.b = (CharsetEncoder) Preconditions.checkNotNull(charsetEncoder);
        Preconditions.checkArgument(i <= 0 ? false : z, "bufferSize must be positive: %s", i);
        charsetEncoder.reset();
        this.d = CharBuffer.allocate(i);
        this.d.flip();
        this.e = ByteBuffer.allocate(i);
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.a.close();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.c) == 1) {
            return UnsignedBytes.toInt(this.c[0]);
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x002a, code lost:
        if (r1 <= 0) goto L_0x002d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x002d, code lost:
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:?, code lost:
        return r1;
     */
    @Override // java.io.InputStream
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int read(byte[] r8, int r9, int r10) throws java.io.IOException {
        /*
            r7 = this;
            int r0 = r9 + r10
            int r1 = r8.length
            com.google.common.base.Preconditions.checkPositionIndexes(r9, r0, r1)
            r0 = 0
            if (r10 != 0) goto L_0x000a
            return r0
        L_0x000a:
            boolean r1 = r7.f
            r2 = r1
            r1 = r0
        L_0x000e:
            boolean r3 = r7.g
            if (r3 == 0) goto L_0x002f
            int r3 = r9 + r1
            int r4 = r10 - r1
            int r3 = r7.a(r8, r3, r4)
            int r1 = r1 + r3
            if (r1 == r10) goto L_0x002a
            boolean r3 = r7.h
            if (r3 == 0) goto L_0x0022
            goto L_0x002a
        L_0x0022:
            r7.g = r0
            java.nio.ByteBuffer r3 = r7.e
            r3.clear()
            goto L_0x002f
        L_0x002a:
            if (r1 <= 0) goto L_0x002d
            goto L_0x002e
        L_0x002d:
            r1 = -1
        L_0x002e:
            return r1
        L_0x002f:
            boolean r3 = r7.h
            if (r3 == 0) goto L_0x0036
            java.nio.charset.CoderResult r3 = java.nio.charset.CoderResult.UNDERFLOW
            goto L_0x004d
        L_0x0036:
            if (r2 == 0) goto L_0x0041
            java.nio.charset.CharsetEncoder r3 = r7.b
            java.nio.ByteBuffer r4 = r7.e
            java.nio.charset.CoderResult r3 = r3.flush(r4)
            goto L_0x004d
        L_0x0041:
            java.nio.charset.CharsetEncoder r3 = r7.b
            java.nio.CharBuffer r4 = r7.d
            java.nio.ByteBuffer r5 = r7.e
            boolean r6 = r7.f
            java.nio.charset.CoderResult r3 = r3.encode(r4, r5, r6)
        L_0x004d:
            boolean r4 = r3.isOverflow()
            r5 = 1
            if (r4 == 0) goto L_0x0058
            r7.a(r5)
            goto L_0x000e
        L_0x0058:
            boolean r4 = r3.isUnderflow()
            if (r4 == 0) goto L_0x0070
            if (r2 == 0) goto L_0x0066
            r7.h = r5
            r7.a(r0)
            goto L_0x000e
        L_0x0066:
            boolean r3 = r7.f
            if (r3 == 0) goto L_0x006c
            r2 = r5
            goto L_0x002f
        L_0x006c:
            r7.a()
            goto L_0x002f
        L_0x0070:
            boolean r4 = r3.isError()
            if (r4 == 0) goto L_0x002f
            r3.throwException()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.io.f.read(byte[], int, int):int");
    }

    private static CharBuffer a(CharBuffer charBuffer) {
        CharBuffer wrap = CharBuffer.wrap(Arrays.copyOf(charBuffer.array(), charBuffer.capacity() * 2));
        wrap.position(charBuffer.position());
        wrap.limit(charBuffer.limit());
        return wrap;
    }

    private void a() throws IOException {
        if (a((Buffer) this.d) == 0) {
            if (this.d.position() > 0) {
                this.d.compact().flip();
            } else {
                this.d = a(this.d);
            }
        }
        int limit = this.d.limit();
        int read = this.a.read(this.d.array(), limit, a((Buffer) this.d));
        if (read == -1) {
            this.f = true;
        } else {
            this.d.limit(limit + read);
        }
    }

    private static int a(Buffer buffer) {
        return buffer.capacity() - buffer.limit();
    }

    private void a(boolean z) {
        this.e.flip();
        if (!z || this.e.remaining() != 0) {
            this.g = true;
        } else {
            this.e = ByteBuffer.allocate(this.e.capacity() * 2);
        }
    }

    private int a(byte[] bArr, int i, int i2) {
        int min = Math.min(i2, this.e.remaining());
        this.e.get(bArr, i, min);
        return min;
    }
}
