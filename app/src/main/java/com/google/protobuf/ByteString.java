package com.google.protobuf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import org.slf4j.Marker;

/* loaded from: classes2.dex */
public abstract class ByteString implements Serializable, Iterable<Byte> {
    public static final ByteString EMPTY = new g(Internal.EMPTY_BYTE_ARRAY);
    private static final d a;
    private static final Comparator<ByteString> b;
    private int hash = 0;

    /* loaded from: classes2.dex */
    public interface ByteIterator extends Iterator<Byte> {
        byte nextByte();
    }

    /* loaded from: classes2.dex */
    public interface d {
        byte[] a(byte[] bArr, int i, int i2);
    }

    public static int b(byte b2) {
        return b2 & 255;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract byte a(int i);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(ByteOutput byteOutput) throws IOException;

    public abstract ByteBuffer asReadOnlyByteBuffer();

    public abstract List<ByteBuffer> asReadOnlyByteBufferList();

    public abstract byte byteAt(int i);

    public abstract void copyTo(ByteBuffer byteBuffer);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void copyToInternal(byte[] bArr, int i, int i2, int i3);

    public abstract boolean equals(Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract int getTreeDepth();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract boolean isBalanced();

    public abstract boolean isValidUtf8();

    public abstract CodedInputStream newCodedInput();

    public abstract InputStream newInput();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract int partialHash(int i, int i2, int i3);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract int partialIsValidUtf8(int i, int i2, int i3);

    public abstract int size();

    public abstract ByteString substring(int i, int i2);

    protected abstract String toStringInternal(Charset charset);

    public abstract void writeTo(OutputStream outputStream) throws IOException;

    static {
        a = b.a() ? new h() : new b();
        b = new Comparator<ByteString>() { // from class: com.google.protobuf.ByteString.2
            /* JADX WARN: Type inference failed for: r0v0, types: [com.google.protobuf.ByteString$ByteIterator] */
            /* JADX WARN: Type inference failed for: r1v0, types: [com.google.protobuf.ByteString$ByteIterator] */
            /* JADX WARN: Unknown variable types count: 2 */
            /* renamed from: a */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public int compare(com.google.protobuf.ByteString r5, com.google.protobuf.ByteString r6) {
                /*
                    r4 = this;
                    com.google.protobuf.ByteString$ByteIterator r0 = r5.iterator2()
                    com.google.protobuf.ByteString$ByteIterator r1 = r6.iterator2()
                L_0x0008:
                    boolean r2 = r0.hasNext()
                    if (r2 == 0) goto L_0x002b
                    boolean r2 = r1.hasNext()
                    if (r2 == 0) goto L_0x002b
                    byte r2 = r0.nextByte()
                    int r2 = com.google.protobuf.ByteString.a(r2)
                    byte r3 = r1.nextByte()
                    int r3 = com.google.protobuf.ByteString.a(r3)
                    int r2 = java.lang.Integer.compare(r2, r3)
                    if (r2 == 0) goto L_0x0008
                    return r2
                L_0x002b:
                    int r5 = r5.size()
                    int r6 = r6.size()
                    int r5 = java.lang.Integer.compare(r5, r6)
                    return r5
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.ByteString.AnonymousClass2.compare(com.google.protobuf.ByteString, com.google.protobuf.ByteString):int");
            }
        };
    }

    /* loaded from: classes2.dex */
    private static final class h implements d {
        private h() {
        }

        @Override // com.google.protobuf.ByteString.d
        public byte[] a(byte[] bArr, int i, int i2) {
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            return bArr2;
        }
    }

    /* loaded from: classes2.dex */
    private static final class b implements d {
        private b() {
        }

        @Override // com.google.protobuf.ByteString.d
        public byte[] a(byte[] bArr, int i, int i2) {
            return Arrays.copyOfRange(bArr, i, i2 + i);
        }
    }

    @Override // java.lang.Iterable
    /* renamed from: iterator */
    public Iterator<Byte> iterator2() {
        return new a() { // from class: com.google.protobuf.ByteString.1
            private int b = 0;
            private final int c;

            {
                ByteString.this = this;
                this.c = ByteString.this.size();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.b < this.c;
            }

            @Override // com.google.protobuf.ByteString.ByteIterator
            public byte nextByte() {
                int i = this.b;
                if (i < this.c) {
                    this.b = i + 1;
                    return ByteString.this.a(i);
                }
                throw new NoSuchElementException();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class a implements ByteIterator {
        /* renamed from: a */
        public final Byte next() {
            return Byte.valueOf(nextByte());
        }

        @Override // java.util.Iterator
        public final void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public final boolean isEmpty() {
        return size() == 0;
    }

    public static Comparator<ByteString> unsignedLexicographicalComparator() {
        return b;
    }

    public final ByteString substring(int i) {
        return substring(i, size());
    }

    public final boolean startsWith(ByteString byteString) {
        return size() >= byteString.size() && substring(0, byteString.size()).equals(byteString);
    }

    public final boolean endsWith(ByteString byteString) {
        return size() >= byteString.size() && substring(size() - byteString.size()).equals(byteString);
    }

    public static ByteString copyFrom(byte[] bArr, int i, int i2) {
        a(i, i + i2, bArr.length);
        return new g(a.a(bArr, i, i2));
    }

    public static ByteString copyFrom(byte[] bArr) {
        return copyFrom(bArr, 0, bArr.length);
    }

    public static ByteString a(ByteBuffer byteBuffer) {
        if (!byteBuffer.hasArray()) {
            return new ae(byteBuffer);
        }
        return a(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
    }

    public static ByteString a(byte[] bArr) {
        return new g(bArr);
    }

    public static ByteString a(byte[] bArr, int i, int i2) {
        return new c(bArr, i, i2);
    }

    public static ByteString copyFrom(ByteBuffer byteBuffer, int i) {
        a(0, i, byteBuffer.remaining());
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr);
        return new g(bArr);
    }

    public static ByteString copyFrom(ByteBuffer byteBuffer) {
        return copyFrom(byteBuffer, byteBuffer.remaining());
    }

    public static ByteString copyFrom(String str, String str2) throws UnsupportedEncodingException {
        return new g(str.getBytes(str2));
    }

    public static ByteString copyFrom(String str, Charset charset) {
        return new g(str.getBytes(charset));
    }

    public static ByteString copyFromUtf8(String str) {
        return new g(str.getBytes(Internal.a));
    }

    public static ByteString readFrom(InputStream inputStream) throws IOException {
        return readFrom(inputStream, 256, 8192);
    }

    public static ByteString readFrom(InputStream inputStream, int i) throws IOException {
        return readFrom(inputStream, i, i);
    }

    public static ByteString readFrom(InputStream inputStream, int i, int i2) throws IOException {
        ArrayList arrayList = new ArrayList();
        while (true) {
            ByteString a2 = a(inputStream, i);
            if (a2 == null) {
                return copyFrom(arrayList);
            }
            arrayList.add(a2);
            i = Math.min(i * 2, i2);
        }
    }

    private static ByteString a(InputStream inputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int read = inputStream.read(bArr, i2, i - i2);
            if (read == -1) {
                break;
            }
            i2 += read;
        }
        if (i2 == 0) {
            return null;
        }
        return copyFrom(bArr, 0, i2);
    }

    public final ByteString concat(ByteString byteString) {
        if (Integer.MAX_VALUE - size() >= byteString.size()) {
            return al.a(this, byteString);
        }
        throw new IllegalArgumentException("ByteString would be too long: " + size() + Marker.ANY_NON_NULL_MARKER + byteString.size());
    }

    public static ByteString copyFrom(Iterable<ByteString> iterable) {
        int i;
        if (!(iterable instanceof Collection)) {
            i = 0;
            Iterator<ByteString> it = iterable.iterator();
            while (it.hasNext()) {
                it.next();
                i++;
            }
        } else {
            i = ((Collection) iterable).size();
        }
        if (i == 0) {
            return EMPTY;
        }
        return a(iterable.iterator(), i);
    }

    private static ByteString a(Iterator<ByteString> it, int i) {
        if (i < 1) {
            throw new IllegalArgumentException(String.format("length (%s) must be >= 1", Integer.valueOf(i)));
        } else if (i == 1) {
            return it.next();
        } else {
            int i2 = i >>> 1;
            return a(it, i2).concat(a(it, i - i2));
        }
    }

    public void copyTo(byte[] bArr, int i) {
        copyTo(bArr, 0, i, size());
    }

    @Deprecated
    public final void copyTo(byte[] bArr, int i, int i2, int i3) {
        a(i, i + i3, size());
        a(i2, i2 + i3, bArr.length);
        if (i3 > 0) {
            copyToInternal(bArr, i, i2, i3);
        }
    }

    public final byte[] toByteArray() {
        int size = size();
        if (size == 0) {
            return Internal.EMPTY_BYTE_ARRAY;
        }
        byte[] bArr = new byte[size];
        copyToInternal(bArr, 0, 0, size);
        return bArr;
    }

    public final String toString(String str) throws UnsupportedEncodingException {
        try {
            return toString(Charset.forName(str));
        } catch (UnsupportedCharsetException e2) {
            UnsupportedEncodingException unsupportedEncodingException = new UnsupportedEncodingException(str);
            unsupportedEncodingException.initCause(e2);
            throw unsupportedEncodingException;
        }
    }

    public final String toString(Charset charset) {
        return size() == 0 ? "" : toStringInternal(charset);
    }

    public final String toStringUtf8() {
        return toString(Internal.a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static abstract class f extends ByteString {
        /* JADX INFO: Access modifiers changed from: package-private */
        public abstract boolean a(ByteString byteString, int i, int i2);

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.ByteString
        public final int getTreeDepth() {
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.ByteString
        public final boolean isBalanced() {
            return true;
        }

        @Override // com.google.protobuf.ByteString, java.lang.Iterable
        public /* bridge */ /* synthetic */ Iterator<Byte> iterator() {
            return ByteString.super.iterator2();
        }
    }

    public final int hashCode() {
        int i = this.hash;
        if (i == 0) {
            int size = size();
            i = partialHash(size, 0, size);
            if (i == 0) {
                i = 1;
            }
            this.hash = i;
        }
        return i;
    }

    public static Output newOutput(int i) {
        return new Output(i);
    }

    public static Output newOutput() {
        return new Output(128);
    }

    /* loaded from: classes2.dex */
    public static final class Output extends OutputStream {
        private static final byte[] a = new byte[0];
        private final int b;
        private final ArrayList<ByteString> c;
        private int d;
        private byte[] e;
        private int f;

        Output(int i) {
            if (i >= 0) {
                this.b = i;
                this.c = new ArrayList<>();
                this.e = new byte[i];
                return;
            }
            throw new IllegalArgumentException("Buffer size < 0");
        }

        @Override // java.io.OutputStream
        public synchronized void write(int i) {
            if (this.f == this.e.length) {
                a(1);
            }
            byte[] bArr = this.e;
            int i2 = this.f;
            this.f = i2 + 1;
            bArr[i2] = (byte) i;
        }

        @Override // java.io.OutputStream
        public synchronized void write(byte[] bArr, int i, int i2) {
            if (i2 <= this.e.length - this.f) {
                System.arraycopy(bArr, i, this.e, this.f, i2);
                this.f += i2;
            } else {
                int length = this.e.length - this.f;
                System.arraycopy(bArr, i, this.e, this.f, length);
                int i3 = i2 - length;
                a(i3);
                System.arraycopy(bArr, i + length, this.e, 0, i3);
                this.f = i3;
            }
        }

        public synchronized ByteString toByteString() {
            a();
            return ByteString.copyFrom(this.c);
        }

        private byte[] a(byte[] bArr, int i) {
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArr, 0, bArr2, 0, Math.min(bArr.length, i));
            return bArr2;
        }

        public void writeTo(OutputStream outputStream) throws IOException {
            ByteString[] byteStringArr;
            byte[] bArr;
            int i;
            synchronized (this) {
                byteStringArr = (ByteString[]) this.c.toArray(new ByteString[this.c.size()]);
                bArr = this.e;
                i = this.f;
            }
            for (ByteString byteString : byteStringArr) {
                byteString.writeTo(outputStream);
            }
            outputStream.write(a(bArr, i));
        }

        public synchronized int size() {
            return this.d + this.f;
        }

        public synchronized void reset() {
            this.c.clear();
            this.d = 0;
            this.f = 0;
        }

        public String toString() {
            return String.format("<ByteString.Output@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size()));
        }

        private void a(int i) {
            this.c.add(new g(this.e));
            this.d += this.e.length;
            this.e = new byte[Math.max(this.b, Math.max(i, this.d >>> 1))];
            this.f = 0;
        }

        private void a() {
            int i = this.f;
            byte[] bArr = this.e;
            if (i >= bArr.length) {
                this.c.add(new g(bArr));
                this.e = a;
            } else if (i > 0) {
                this.c.add(new g(a(bArr, i)));
            }
            this.d += this.f;
            this.f = 0;
        }
    }

    public static e b(int i) {
        return new e(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class e {
        private final CodedOutputStream a;
        private final byte[] b;

        private e(int i) {
            this.b = new byte[i];
            this.a = CodedOutputStream.newInstance(this.b);
        }

        public ByteString a() {
            this.a.checkNoSpaceLeft();
            return new g(this.b);
        }

        public CodedOutputStream b() {
            return this.a;
        }
    }

    public final int peekCachedHashCode() {
        return this.hash;
    }

    static void a(int i, int i2) {
        if (((i2 - (i + 1)) | i) >= 0) {
            return;
        }
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException("Index < 0: " + i);
        }
        throw new ArrayIndexOutOfBoundsException("Index > length: " + i + ", " + i2);
    }

    static int a(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            throw new IndexOutOfBoundsException("Beginning index: " + i + " < 0");
        } else if (i2 < i) {
            throw new IndexOutOfBoundsException("Beginning index larger than ending index: " + i + ", " + i2);
        } else {
            throw new IndexOutOfBoundsException("End index: " + i2 + " >= " + i3);
        }
    }

    public final String toString() {
        return String.format(Locale.ROOT, "<ByteString@%s size=%d contents=\"%s\">", Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size()), a());
    }

    private String a() {
        if (size() <= 50) {
            return aq.a(this);
        }
        return aq.a(substring(0, 47)) + "...";
    }

    /* loaded from: classes2.dex */
    public static class g extends f {
        private static final long serialVersionUID = 1;
        protected final byte[] bytes;

        protected int a() {
            return 0;
        }

        g(byte[] bArr) {
            if (bArr != null) {
                this.bytes = bArr;
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.google.protobuf.ByteString
        public byte byteAt(int i) {
            return this.bytes[i];
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.protobuf.ByteString
        public byte a(int i) {
            return this.bytes[i];
        }

        @Override // com.google.protobuf.ByteString
        public int size() {
            return this.bytes.length;
        }

        @Override // com.google.protobuf.ByteString
        public final ByteString substring(int i, int i2) {
            int a = a(i, i2, size());
            if (a == 0) {
                return ByteString.EMPTY;
            }
            return new c(this.bytes, a() + i, a);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.ByteString
        public void copyToInternal(byte[] bArr, int i, int i2, int i3) {
            System.arraycopy(this.bytes, i, bArr, i2, i3);
        }

        @Override // com.google.protobuf.ByteString
        public final void copyTo(ByteBuffer byteBuffer) {
            byteBuffer.put(this.bytes, a(), size());
        }

        @Override // com.google.protobuf.ByteString
        public final ByteBuffer asReadOnlyByteBuffer() {
            return ByteBuffer.wrap(this.bytes, a(), size()).asReadOnlyBuffer();
        }

        @Override // com.google.protobuf.ByteString
        public final List<ByteBuffer> asReadOnlyByteBufferList() {
            return Collections.singletonList(asReadOnlyByteBuffer());
        }

        @Override // com.google.protobuf.ByteString
        public final void writeTo(OutputStream outputStream) throws IOException {
            outputStream.write(toByteArray());
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.protobuf.ByteString
        public final void a(ByteOutput byteOutput) throws IOException {
            byteOutput.writeLazy(this.bytes, a(), size());
        }

        @Override // com.google.protobuf.ByteString
        protected final String toStringInternal(Charset charset) {
            return new String(this.bytes, a(), size(), charset);
        }

        @Override // com.google.protobuf.ByteString
        public final boolean isValidUtf8() {
            int a = a();
            return au.a(this.bytes, a, size() + a);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.ByteString
        public final int partialIsValidUtf8(int i, int i2, int i3) {
            int a = a() + i2;
            return au.a(i, this.bytes, a, i3 + a);
        }

        @Override // com.google.protobuf.ByteString
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ByteString) || size() != ((ByteString) obj).size()) {
                return false;
            }
            if (size() == 0) {
                return true;
            }
            if (!(obj instanceof g)) {
                return obj.equals(this);
            }
            g gVar = (g) obj;
            int peekCachedHashCode = peekCachedHashCode();
            int peekCachedHashCode2 = gVar.peekCachedHashCode();
            if (peekCachedHashCode == 0 || peekCachedHashCode2 == 0 || peekCachedHashCode == peekCachedHashCode2) {
                return a(gVar, 0, size());
            }
            return false;
        }

        @Override // com.google.protobuf.ByteString.f
        public final boolean a(ByteString byteString, int i, int i2) {
            if (i2 <= byteString.size()) {
                int i3 = i + i2;
                if (i3 > byteString.size()) {
                    throw new IllegalArgumentException("Ran off end of other: " + i + ", " + i2 + ", " + byteString.size());
                } else if (!(byteString instanceof g)) {
                    return byteString.substring(i, i3).equals(substring(0, i2));
                } else {
                    g gVar = (g) byteString;
                    byte[] bArr = this.bytes;
                    byte[] bArr2 = gVar.bytes;
                    int a = a() + i2;
                    int a2 = a();
                    int a3 = gVar.a() + i;
                    while (a2 < a) {
                        if (bArr[a2] != bArr2[a3]) {
                            return false;
                        }
                        a2++;
                        a3++;
                    }
                    return true;
                }
            } else {
                throw new IllegalArgumentException("Length too large: " + i2 + size());
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.ByteString
        public final int partialHash(int i, int i2, int i3) {
            return Internal.a(i, this.bytes, a() + i2, i3);
        }

        @Override // com.google.protobuf.ByteString
        public final InputStream newInput() {
            return new ByteArrayInputStream(this.bytes, a(), size());
        }

        @Override // com.google.protobuf.ByteString
        public final CodedInputStream newCodedInput() {
            return CodedInputStream.a(this.bytes, a(), size(), true);
        }
    }

    /* loaded from: classes2.dex */
    public static final class c extends g {
        private static final long serialVersionUID = 1;
        private final int bytesLength;
        private final int bytesOffset;

        c(byte[] bArr, int i, int i2) {
            super(bArr);
            a(i, i + i2, bArr.length);
            this.bytesOffset = i;
            this.bytesLength = i2;
        }

        @Override // com.google.protobuf.ByteString.g, com.google.protobuf.ByteString
        public byte byteAt(int i) {
            a(i, size());
            return this.bytes[this.bytesOffset + i];
        }

        @Override // com.google.protobuf.ByteString.g, com.google.protobuf.ByteString
        byte a(int i) {
            return this.bytes[this.bytesOffset + i];
        }

        @Override // com.google.protobuf.ByteString.g, com.google.protobuf.ByteString
        public int size() {
            return this.bytesLength;
        }

        @Override // com.google.protobuf.ByteString.g
        protected int a() {
            return this.bytesOffset;
        }

        @Override // com.google.protobuf.ByteString.g, com.google.protobuf.ByteString
        protected void copyToInternal(byte[] bArr, int i, int i2, int i3) {
            System.arraycopy(this.bytes, a() + i, bArr, i2, i3);
        }

        Object writeReplace() {
            return ByteString.a(toByteArray());
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException {
            throw new InvalidObjectException("BoundedByteStream instances are not to be serialized directly");
        }
    }
}
