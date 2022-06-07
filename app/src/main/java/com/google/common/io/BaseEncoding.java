package com.google.common.io;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.IntMath;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.RoundingMode;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public abstract class BaseEncoding {
    private static final BaseEncoding a = new c("base64()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", '=');
    private static final BaseEncoding b = new c("base64Url()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", '=');
    private static final BaseEncoding c = new e("base32()", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", '=');
    private static final BaseEncoding d = new e("base32Hex()", "0123456789ABCDEFGHIJKLMNOPQRSTUV", '=');
    private static final BaseEncoding e = new b("base16()", "0123456789ABCDEF");

    abstract int a(int i);

    abstract int a(byte[] bArr, CharSequence charSequence) throws DecodingException;

    abstract void a(Appendable appendable, byte[] bArr, int i, int i2) throws IOException;

    abstract int b(int i);

    public abstract boolean canDecode(CharSequence charSequence);

    @GwtIncompatible
    public abstract InputStream decodingStream(Reader reader);

    @GwtIncompatible
    public abstract OutputStream encodingStream(Writer writer);

    public abstract BaseEncoding lowerCase();

    public abstract BaseEncoding omitPadding();

    public abstract BaseEncoding upperCase();

    public abstract BaseEncoding withPadChar(char c2);

    public abstract BaseEncoding withSeparator(String str, int i);

    BaseEncoding() {
    }

    /* loaded from: classes2.dex */
    public static final class DecodingException extends IOException {
        DecodingException(String str) {
            super(str);
        }
    }

    public String encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length);
    }

    public final String encode(byte[] bArr, int i, int i2) {
        Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
        StringBuilder sb = new StringBuilder(a(i2));
        try {
            a(sb, bArr, i, i2);
            return sb.toString();
        } catch (IOException e2) {
            throw new AssertionError(e2);
        }
    }

    @GwtIncompatible
    public final ByteSink encodingSink(final CharSink charSink) {
        Preconditions.checkNotNull(charSink);
        return new ByteSink() { // from class: com.google.common.io.BaseEncoding.1
            @Override // com.google.common.io.ByteSink
            public OutputStream openStream() throws IOException {
                return BaseEncoding.this.encodingStream(charSink.openStream());
            }
        };
    }

    private static byte[] a(byte[] bArr, int i) {
        if (i == bArr.length) {
            return bArr;
        }
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        return bArr2;
    }

    public final byte[] decode(CharSequence charSequence) {
        try {
            return a(charSequence);
        } catch (DecodingException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    final byte[] a(CharSequence charSequence) throws DecodingException {
        CharSequence b2 = b(charSequence);
        byte[] bArr = new byte[b(b2.length())];
        return a(bArr, a(bArr, b2));
    }

    @GwtIncompatible
    public final ByteSource decodingSource(final CharSource charSource) {
        Preconditions.checkNotNull(charSource);
        return new ByteSource() { // from class: com.google.common.io.BaseEncoding.2
            @Override // com.google.common.io.ByteSource
            public InputStream openStream() throws IOException {
                return BaseEncoding.this.decodingStream(charSource.openStream());
            }
        };
    }

    CharSequence b(CharSequence charSequence) {
        return (CharSequence) Preconditions.checkNotNull(charSequence);
    }

    public static BaseEncoding base64() {
        return a;
    }

    public static BaseEncoding base64Url() {
        return b;
    }

    public static BaseEncoding base32() {
        return c;
    }

    public static BaseEncoding base32Hex() {
        return d;
    }

    public static BaseEncoding base16() {
        return e;
    }

    /* loaded from: classes2.dex */
    public static final class a {
        final int a;
        final int b;
        final int c;
        final int d;
        private final String e;
        private final char[] f;
        private final byte[] g;
        private final boolean[] h;

        a(String str, char[] cArr) {
            this.e = (String) Preconditions.checkNotNull(str);
            this.f = (char[]) Preconditions.checkNotNull(cArr);
            try {
                this.b = IntMath.log2(cArr.length, RoundingMode.UNNECESSARY);
                int min = Math.min(8, Integer.lowestOneBit(this.b));
                try {
                    this.c = 8 / min;
                    this.d = this.b / min;
                    this.a = cArr.length - 1;
                    byte[] bArr = new byte[128];
                    Arrays.fill(bArr, (byte) -1);
                    for (int i = 0; i < cArr.length; i++) {
                        char c = cArr[i];
                        Preconditions.checkArgument(c < bArr.length, "Non-ASCII character: %s", c);
                        Preconditions.checkArgument(bArr[c] == -1, "Duplicate character: %s", c);
                        bArr[c] = (byte) i;
                    }
                    this.g = bArr;
                    boolean[] zArr = new boolean[this.c];
                    for (int i2 = 0; i2 < this.d; i2++) {
                        zArr[IntMath.divide(i2 * 8, this.b, RoundingMode.CEILING)] = true;
                    }
                    this.h = zArr;
                } catch (ArithmeticException e) {
                    throw new IllegalArgumentException("Illegal alphabet " + new String(cArr), e);
                }
            } catch (ArithmeticException e2) {
                throw new IllegalArgumentException("Illegal alphabet length " + cArr.length, e2);
            }
        }

        char a(int i) {
            return this.f[i];
        }

        boolean b(int i) {
            return this.h[i % this.c];
        }

        boolean a(char c) {
            return c <= 127 && this.g[c] != -1;
        }

        int b(char c) throws DecodingException {
            if (c <= 127) {
                byte b = this.g[c];
                if (b != -1) {
                    return b;
                }
                if (c <= ' ' || c == 127) {
                    throw new DecodingException("Unrecognized character: 0x" + Integer.toHexString(c));
                }
                throw new DecodingException("Unrecognized character: " + c);
            }
            throw new DecodingException("Unrecognized character: 0x" + Integer.toHexString(c));
        }

        private boolean c() {
            for (char c : this.f) {
                if (Ascii.isLowerCase(c)) {
                    return true;
                }
            }
            return false;
        }

        private boolean d() {
            for (char c : this.f) {
                if (Ascii.isUpperCase(c)) {
                    return true;
                }
            }
            return false;
        }

        a a() {
            if (!c()) {
                return this;
            }
            Preconditions.checkState(!d(), "Cannot call upperCase() on a mixed-case alphabet");
            char[] cArr = new char[this.f.length];
            int i = 0;
            while (true) {
                char[] cArr2 = this.f;
                if (i < cArr2.length) {
                    cArr[i] = Ascii.toUpperCase(cArr2[i]);
                    i++;
                } else {
                    return new a(this.e + ".upperCase()", cArr);
                }
            }
        }

        a b() {
            if (!d()) {
                return this;
            }
            Preconditions.checkState(!c(), "Cannot call lowerCase() on a mixed-case alphabet");
            char[] cArr = new char[this.f.length];
            int i = 0;
            while (true) {
                char[] cArr2 = this.f;
                if (i < cArr2.length) {
                    cArr[i] = Ascii.toLowerCase(cArr2[i]);
                    i++;
                } else {
                    return new a(this.e + ".lowerCase()", cArr);
                }
            }
        }

        public boolean c(char c) {
            byte[] bArr = this.g;
            return c < bArr.length && bArr[c] != -1;
        }

        public String toString() {
            return this.e;
        }

        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof a) {
                return Arrays.equals(this.f, ((a) obj).f);
            }
            return false;
        }

        public int hashCode() {
            return Arrays.hashCode(this.f);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class e extends BaseEncoding {
        @MonotonicNonNullDecl
        private transient BaseEncoding a;
        final a b;
        @NullableDecl
        final Character c;
        @MonotonicNonNullDecl
        private transient BaseEncoding d;

        e(String str, String str2, @NullableDecl Character ch) {
            this(new a(str, str2.toCharArray()), ch);
        }

        e(a aVar, @NullableDecl Character ch) {
            this.b = (a) Preconditions.checkNotNull(aVar);
            Preconditions.checkArgument(ch == null || !aVar.c(ch.charValue()), "Padding character %s was already in alphabet", ch);
            this.c = ch;
        }

        @Override // com.google.common.io.BaseEncoding
        int a(int i) {
            return this.b.c * IntMath.divide(i, this.b.d, RoundingMode.CEILING);
        }

        @Override // com.google.common.io.BaseEncoding
        @GwtIncompatible
        public OutputStream encodingStream(final Writer writer) {
            Preconditions.checkNotNull(writer);
            return new OutputStream() { // from class: com.google.common.io.BaseEncoding.e.1
                int a = 0;
                int b = 0;
                int c = 0;

                @Override // java.io.OutputStream
                public void write(int i) throws IOException {
                    this.a <<= 8;
                    this.a = (i & 255) | this.a;
                    this.b += 8;
                    while (this.b >= e.this.b.b) {
                        writer.write(e.this.b.a((this.a >> (this.b - e.this.b.b)) & e.this.b.a));
                        this.c++;
                        this.b -= e.this.b.b;
                    }
                }

                @Override // java.io.OutputStream, java.io.Flushable
                public void flush() throws IOException {
                    writer.flush();
                }

                @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    if (this.b > 0) {
                        writer.write(e.this.b.a((this.a << (e.this.b.b - this.b)) & e.this.b.a));
                        this.c++;
                        if (e.this.c != null) {
                            while (this.c % e.this.b.c != 0) {
                                writer.write(e.this.c.charValue());
                                this.c++;
                            }
                        }
                    }
                    writer.close();
                }
            };
        }

        @Override // com.google.common.io.BaseEncoding
        void a(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
            int i3 = 0;
            while (i3 < i2) {
                b(appendable, bArr, i + i3, Math.min(this.b.d, i2 - i3));
                i3 += this.b.d;
            }
        }

        void b(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
            int i3 = 0;
            Preconditions.checkArgument(i2 <= this.b.d);
            long j = 0;
            for (int i4 = 0; i4 < i2; i4++) {
                j = (j | (bArr[i + i4] & 255)) << 8;
            }
            int i5 = ((i2 + 1) * 8) - this.b.b;
            while (i3 < i2 * 8) {
                appendable.append(this.b.a(((int) (j >>> (i5 - i3))) & this.b.a));
                i3 += this.b.b;
            }
            if (this.c != null) {
                while (i3 < this.b.d * 8) {
                    appendable.append(this.c.charValue());
                    i3 += this.b.b;
                }
            }
        }

        @Override // com.google.common.io.BaseEncoding
        int b(int i) {
            return (int) (((this.b.b * i) + 7) / 8);
        }

        @Override // com.google.common.io.BaseEncoding
        CharSequence b(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            Character ch = this.c;
            if (ch == null) {
                return charSequence;
            }
            char charValue = ch.charValue();
            int length = charSequence.length() - 1;
            while (length >= 0 && charSequence.charAt(length) == charValue) {
                length--;
            }
            return charSequence.subSequence(0, length + 1);
        }

        @Override // com.google.common.io.BaseEncoding
        public boolean canDecode(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            CharSequence b = b(charSequence);
            if (!this.b.b(b.length())) {
                return false;
            }
            for (int i = 0; i < b.length(); i++) {
                if (!this.b.a(b.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.common.io.BaseEncoding
        int a(byte[] bArr, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(bArr);
            CharSequence b = b(charSequence);
            if (this.b.b(b.length())) {
                int i = 0;
                int i2 = 0;
                while (i < b.length()) {
                    long j = 0;
                    int i3 = 0;
                    for (int i4 = 0; i4 < this.b.c; i4++) {
                        j <<= this.b.b;
                        if (i + i4 < b.length()) {
                            i3++;
                            j |= this.b.b(b.charAt(i3 + i));
                        }
                    }
                    int i5 = (this.b.d * 8) - (i3 * this.b.b);
                    for (int i6 = (this.b.d - 1) * 8; i6 >= i5; i6 -= 8) {
                        i2++;
                        bArr[i2] = (byte) ((j >>> i6) & 255);
                    }
                    i += this.b.c;
                }
                return i2;
            }
            throw new DecodingException("Invalid input length " + b.length());
        }

        @Override // com.google.common.io.BaseEncoding
        @GwtIncompatible
        public InputStream decodingStream(final Reader reader) {
            Preconditions.checkNotNull(reader);
            return new InputStream() { // from class: com.google.common.io.BaseEncoding.e.2
                int a = 0;
                int b = 0;
                int c = 0;
                boolean d = false;

                /* JADX WARN: Code restructure failed: missing block: B:22:0x0079, code lost:
                    throw new com.google.common.io.BaseEncoding.DecodingException("Padding cannot start at index " + r4.c);
                 */
                @Override // java.io.InputStream
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public int read() throws java.io.IOException {
                    /*
                        Method dump skipped, instructions count: 214
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.common.io.BaseEncoding.e.AnonymousClass2.read():int");
                }

                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    reader.close();
                }
            };
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding omitPadding() {
            return this.c == null ? this : a(this.b, (Character) null);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withPadChar(char c) {
            Character ch;
            return (8 % this.b.b == 0 || ((ch = this.c) != null && ch.charValue() == c)) ? this : a(this.b, Character.valueOf(c));
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withSeparator(String str, int i) {
            boolean z = false;
            for (int i2 = 0; i2 < str.length(); i2++) {
                Preconditions.checkArgument(!this.b.c(str.charAt(i2)), "Separator (%s) cannot contain alphabet characters", str);
            }
            Character ch = this.c;
            if (ch != null) {
                if (str.indexOf(ch.charValue()) < 0) {
                    z = true;
                }
                Preconditions.checkArgument(z, "Separator (%s) cannot contain padding character", str);
            }
            return new d(this, str, i);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding upperCase() {
            BaseEncoding baseEncoding = this.a;
            if (baseEncoding == null) {
                a a = this.b.a();
                baseEncoding = a == this.b ? this : a(a, this.c);
                this.a = baseEncoding;
            }
            return baseEncoding;
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding lowerCase() {
            BaseEncoding baseEncoding = this.d;
            if (baseEncoding == null) {
                a b = this.b.b();
                baseEncoding = b == this.b ? this : a(b, this.c);
                this.d = baseEncoding;
            }
            return baseEncoding;
        }

        BaseEncoding a(a aVar, @NullableDecl Character ch) {
            return new e(aVar, ch);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("BaseEncoding.");
            sb.append(this.b.toString());
            if (8 % this.b.b != 0) {
                if (this.c == null) {
                    sb.append(".omitPadding()");
                } else {
                    sb.append(".withPadChar('");
                    sb.append(this.c);
                    sb.append("')");
                }
            }
            return sb.toString();
        }

        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof e)) {
                return false;
            }
            e eVar = (e) obj;
            return this.b.equals(eVar.b) && Objects.equal(this.c, eVar.c);
        }

        public int hashCode() {
            return this.b.hashCode() ^ Objects.hashCode(this.c);
        }
    }

    /* loaded from: classes2.dex */
    static final class b extends e {
        final char[] a;

        b(String str, String str2) {
            this(new a(str, str2.toCharArray()));
        }

        private b(a aVar) {
            super(aVar, null);
            this.a = new char[512];
            Preconditions.checkArgument(aVar.f.length == 16);
            for (int i = 0; i < 256; i++) {
                this.a[i] = aVar.a(i >>> 4);
                this.a[i | 256] = aVar.a(i & 15);
            }
        }

        @Override // com.google.common.io.BaseEncoding.e, com.google.common.io.BaseEncoding
        void a(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
            for (int i3 = 0; i3 < i2; i3++) {
                int i4 = bArr[i + i3] & 255;
                appendable.append(this.a[i4]);
                appendable.append(this.a[i4 | 256]);
            }
        }

        @Override // com.google.common.io.BaseEncoding.e, com.google.common.io.BaseEncoding
        int a(byte[] bArr, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(bArr);
            if (charSequence.length() % 2 != 1) {
                int i = 0;
                for (int i2 = 0; i2 < charSequence.length(); i2 += 2) {
                    i++;
                    bArr[i] = (byte) ((this.b.b(charSequence.charAt(i2)) << 4) | this.b.b(charSequence.charAt(i2 + 1)));
                }
                return i;
            }
            throw new DecodingException("Invalid input length " + charSequence.length());
        }

        @Override // com.google.common.io.BaseEncoding.e
        BaseEncoding a(a aVar, @NullableDecl Character ch) {
            return new b(aVar);
        }
    }

    /* loaded from: classes2.dex */
    static final class c extends e {
        c(String str, String str2, @NullableDecl Character ch) {
            this(new a(str, str2.toCharArray()), ch);
        }

        private c(a aVar, @NullableDecl Character ch) {
            super(aVar, ch);
            Preconditions.checkArgument(aVar.f.length == 64);
        }

        @Override // com.google.common.io.BaseEncoding.e, com.google.common.io.BaseEncoding
        void a(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
            Preconditions.checkNotNull(appendable);
            int i3 = i + i2;
            Preconditions.checkPositionIndexes(i, i3, bArr.length);
            while (i2 >= 3) {
                int i4 = i + 1;
                int i5 = i4 + 1;
                int i6 = ((bArr[i] & 255) << 16) | ((bArr[i4] & 255) << 8);
                i = i5 + 1;
                int i7 = i6 | (bArr[i5] & 255);
                appendable.append(this.b.a(i7 >>> 18));
                appendable.append(this.b.a((i7 >>> 12) & 63));
                appendable.append(this.b.a((i7 >>> 6) & 63));
                appendable.append(this.b.a(i7 & 63));
                i2 -= 3;
            }
            if (i < i3) {
                b(appendable, bArr, i, i3 - i);
            }
        }

        @Override // com.google.common.io.BaseEncoding.e, com.google.common.io.BaseEncoding
        int a(byte[] bArr, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(bArr);
            CharSequence b = b(charSequence);
            if (this.b.b(b.length())) {
                int i = 0;
                int i2 = 0;
                while (i < b.length()) {
                    int i3 = i + 1;
                    int i4 = i3 + 1;
                    int b2 = (this.b.b(b.charAt(i)) << 18) | (this.b.b(b.charAt(i3)) << 12);
                    int i5 = i2 + 1;
                    bArr[i2] = (byte) (b2 >>> 16);
                    if (i4 < b.length()) {
                        int i6 = i4 + 1;
                        int b3 = b2 | (this.b.b(b.charAt(i4)) << 6);
                        i2 = i5 + 1;
                        bArr[i5] = (byte) ((b3 >>> 8) & 255);
                        if (i6 < b.length()) {
                            i = i6 + 1;
                            i2++;
                            bArr[i2] = (byte) ((b3 | this.b.b(b.charAt(i6))) & 255);
                        } else {
                            i = i6;
                        }
                    } else {
                        i2 = i5;
                        i = i4;
                    }
                }
                return i2;
            }
            throw new DecodingException("Invalid input length " + b.length());
        }

        @Override // com.google.common.io.BaseEncoding.e
        BaseEncoding a(a aVar, @NullableDecl Character ch) {
            return new c(aVar, ch);
        }
    }

    @GwtIncompatible
    static Reader a(final Reader reader, final String str) {
        Preconditions.checkNotNull(reader);
        Preconditions.checkNotNull(str);
        return new Reader() { // from class: com.google.common.io.BaseEncoding.3
            @Override // java.io.Reader
            public int read() throws IOException {
                int read;
                do {
                    read = reader.read();
                    if (read == -1) {
                        break;
                    }
                } while (str.indexOf((char) read) >= 0);
                return read;
            }

            @Override // java.io.Reader
            public int read(char[] cArr, int i, int i2) throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                reader.close();
            }
        };
    }

    static Appendable a(final Appendable appendable, final String str, final int i) {
        Preconditions.checkNotNull(appendable);
        Preconditions.checkNotNull(str);
        Preconditions.checkArgument(i > 0);
        return new Appendable() { // from class: com.google.common.io.BaseEncoding.4
            int a;

            {
                this.a = i;
            }

            @Override // java.lang.Appendable
            public Appendable append(char c2) throws IOException {
                if (this.a == 0) {
                    appendable.append(str);
                    this.a = i;
                }
                appendable.append(c2);
                this.a--;
                return this;
            }

            @Override // java.lang.Appendable
            public Appendable append(@NullableDecl CharSequence charSequence, int i2, int i3) throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override // java.lang.Appendable
            public Appendable append(@NullableDecl CharSequence charSequence) throws IOException {
                throw new UnsupportedOperationException();
            }
        };
    }

    @GwtIncompatible
    static Writer a(final Writer writer, String str, int i) {
        final Appendable a2 = a((Appendable) writer, str, i);
        return new Writer() { // from class: com.google.common.io.BaseEncoding.5
            @Override // java.io.Writer
            public void write(int i2) throws IOException {
                a2.append((char) i2);
            }

            @Override // java.io.Writer
            public void write(char[] cArr, int i2, int i3) throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override // java.io.Writer, java.io.Flushable
            public void flush() throws IOException {
                writer.flush();
            }

            @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                writer.close();
            }
        };
    }

    /* loaded from: classes2.dex */
    static final class d extends BaseEncoding {
        private final BaseEncoding a;
        private final String b;
        private final int c;

        d(BaseEncoding baseEncoding, String str, int i) {
            this.a = (BaseEncoding) Preconditions.checkNotNull(baseEncoding);
            this.b = (String) Preconditions.checkNotNull(str);
            this.c = i;
            Preconditions.checkArgument(i > 0, "Cannot add a separator after every %s chars", i);
        }

        @Override // com.google.common.io.BaseEncoding
        CharSequence b(CharSequence charSequence) {
            return this.a.b(charSequence);
        }

        @Override // com.google.common.io.BaseEncoding
        int a(int i) {
            int a = this.a.a(i);
            return a + (this.b.length() * IntMath.divide(Math.max(0, a - 1), this.c, RoundingMode.FLOOR));
        }

        @Override // com.google.common.io.BaseEncoding
        @GwtIncompatible
        public OutputStream encodingStream(Writer writer) {
            return this.a.encodingStream(a(writer, this.b, this.c));
        }

        @Override // com.google.common.io.BaseEncoding
        void a(Appendable appendable, byte[] bArr, int i, int i2) throws IOException {
            this.a.a(a(appendable, this.b, this.c), bArr, i, i2);
        }

        @Override // com.google.common.io.BaseEncoding
        int b(int i) {
            return this.a.b(i);
        }

        @Override // com.google.common.io.BaseEncoding
        public boolean canDecode(CharSequence charSequence) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < charSequence.length(); i++) {
                char charAt = charSequence.charAt(i);
                if (this.b.indexOf(charAt) < 0) {
                    sb.append(charAt);
                }
            }
            return this.a.canDecode(sb);
        }

        @Override // com.google.common.io.BaseEncoding
        int a(byte[] bArr, CharSequence charSequence) throws DecodingException {
            StringBuilder sb = new StringBuilder(charSequence.length());
            for (int i = 0; i < charSequence.length(); i++) {
                char charAt = charSequence.charAt(i);
                if (this.b.indexOf(charAt) < 0) {
                    sb.append(charAt);
                }
            }
            return this.a.a(bArr, sb);
        }

        @Override // com.google.common.io.BaseEncoding
        @GwtIncompatible
        public InputStream decodingStream(Reader reader) {
            return this.a.decodingStream(a(reader, this.b));
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding omitPadding() {
            return this.a.omitPadding().withSeparator(this.b, this.c);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withPadChar(char c) {
            return this.a.withPadChar(c).withSeparator(this.b, this.c);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding withSeparator(String str, int i) {
            throw new UnsupportedOperationException("Already have a separator");
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding upperCase() {
            return this.a.upperCase().withSeparator(this.b, this.c);
        }

        @Override // com.google.common.io.BaseEncoding
        public BaseEncoding lowerCase() {
            return this.a.lowerCase().withSeparator(this.b, this.c);
        }

        public String toString() {
            return this.a + ".withSeparator(\"" + this.b + "\", " + this.c + ")";
        }
    }
}
