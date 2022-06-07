package com.google.protobuf;

import com.google.protobuf.ByteString;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: RopeByteString.java */
/* loaded from: classes2.dex */
public final class al extends ByteString {
    static final int[] a = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169, 63245986, 102334155, 165580141, 267914296, 433494437, 701408733, 1134903170, 1836311903, Integer.MAX_VALUE};
    private static final long serialVersionUID = 1;
    private final ByteString left;
    private final int leftLength;
    private final ByteString right;
    private final int totalLength;
    private final int treeDepth;

    private al(ByteString byteString, ByteString byteString2) {
        this.left = byteString;
        this.right = byteString2;
        this.leftLength = byteString.size();
        this.totalLength = this.leftLength + byteString2.size();
        this.treeDepth = Math.max(byteString.getTreeDepth(), byteString2.getTreeDepth()) + 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ByteString a(ByteString byteString, ByteString byteString2) {
        if (byteString2.size() == 0) {
            return byteString;
        }
        if (byteString.size() == 0) {
            return byteString2;
        }
        int size = byteString.size() + byteString2.size();
        if (size < 128) {
            return b(byteString, byteString2);
        }
        if (byteString instanceof al) {
            al alVar = (al) byteString;
            if (alVar.right.size() + byteString2.size() < 128) {
                return new al(alVar.left, b(alVar.right, byteString2));
            } else if (alVar.left.getTreeDepth() > alVar.right.getTreeDepth() && alVar.getTreeDepth() > byteString2.getTreeDepth()) {
                return new al(alVar.left, new al(alVar.right, byteString2));
            }
        }
        if (size >= c(Math.max(byteString.getTreeDepth(), byteString2.getTreeDepth()) + 1)) {
            return new al(byteString, byteString2);
        }
        return new a().a(byteString, byteString2);
    }

    private static ByteString b(ByteString byteString, ByteString byteString2) {
        int size = byteString.size();
        int size2 = byteString2.size();
        byte[] bArr = new byte[size + size2];
        byteString.copyTo(bArr, 0, 0, size);
        byteString2.copyTo(bArr, 0, size, size2);
        return ByteString.a(bArr);
    }

    static int c(int i) {
        int[] iArr = a;
        if (i >= iArr.length) {
            return Integer.MAX_VALUE;
        }
        return iArr[i];
    }

    @Override // com.google.protobuf.ByteString
    public byte byteAt(int i) {
        a(i, this.totalLength);
        return a(i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.ByteString
    public byte a(int i) {
        int i2 = this.leftLength;
        if (i < i2) {
            return this.left.a(i);
        }
        return this.right.a(i - i2);
    }

    @Override // com.google.protobuf.ByteString
    public int size() {
        return this.totalLength;
    }

    @Override // com.google.protobuf.ByteString, java.lang.Iterable
    /* renamed from: iterator */
    public Iterator<Byte> iterator2() {
        return new ByteString.a() { // from class: com.google.protobuf.al.1
            final b a;
            ByteString.ByteIterator b = b();

            {
                this.a = new b(al.this);
            }

            /* JADX WARN: Type inference failed for: r0v5, types: [com.google.protobuf.ByteString$ByteIterator] */
            private ByteString.ByteIterator b() {
                if (this.a.hasNext()) {
                    return this.a.next().iterator();
                }
                return null;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.b != null;
            }

            @Override // com.google.protobuf.ByteString.ByteIterator
            public byte nextByte() {
                ByteString.ByteIterator byteIterator = this.b;
                if (byteIterator != null) {
                    byte nextByte = byteIterator.nextByte();
                    if (!this.b.hasNext()) {
                        this.b = b();
                    }
                    return nextByte;
                }
                throw new NoSuchElementException();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.protobuf.ByteString
    public int getTreeDepth() {
        return this.treeDepth;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.protobuf.ByteString
    public boolean isBalanced() {
        return this.totalLength >= c(this.treeDepth);
    }

    @Override // com.google.protobuf.ByteString
    public ByteString substring(int i, int i2) {
        int a2 = a(i, i2, this.totalLength);
        if (a2 == 0) {
            return ByteString.EMPTY;
        }
        if (a2 == this.totalLength) {
            return this;
        }
        int i3 = this.leftLength;
        if (i2 <= i3) {
            return this.left.substring(i, i2);
        }
        if (i >= i3) {
            return this.right.substring(i - i3, i2 - i3);
        }
        return new al(this.left.substring(i), this.right.substring(0, i2 - this.leftLength));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.protobuf.ByteString
    public void copyToInternal(byte[] bArr, int i, int i2, int i3) {
        int i4 = i + i3;
        int i5 = this.leftLength;
        if (i4 <= i5) {
            this.left.copyToInternal(bArr, i, i2, i3);
        } else if (i >= i5) {
            this.right.copyToInternal(bArr, i - i5, i2, i3);
        } else {
            int i6 = i5 - i;
            this.left.copyToInternal(bArr, i, i2, i6);
            this.right.copyToInternal(bArr, 0, i2 + i6, i3 - i6);
        }
    }

    @Override // com.google.protobuf.ByteString
    public void copyTo(ByteBuffer byteBuffer) {
        this.left.copyTo(byteBuffer);
        this.right.copyTo(byteBuffer);
    }

    @Override // com.google.protobuf.ByteString
    public ByteBuffer asReadOnlyByteBuffer() {
        return ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
    }

    @Override // com.google.protobuf.ByteString
    public List<ByteBuffer> asReadOnlyByteBufferList() {
        ArrayList arrayList = new ArrayList();
        b bVar = new b(this);
        while (bVar.hasNext()) {
            arrayList.add(bVar.next().asReadOnlyByteBuffer());
        }
        return arrayList;
    }

    @Override // com.google.protobuf.ByteString
    public void writeTo(OutputStream outputStream) throws IOException {
        this.left.writeTo(outputStream);
        this.right.writeTo(outputStream);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.ByteString
    public void a(ByteOutput byteOutput) throws IOException {
        this.left.a(byteOutput);
        this.right.a(byteOutput);
    }

    @Override // com.google.protobuf.ByteString
    protected String toStringInternal(Charset charset) {
        return new String(toByteArray(), charset);
    }

    @Override // com.google.protobuf.ByteString
    public boolean isValidUtf8() {
        int partialIsValidUtf8 = this.left.partialIsValidUtf8(0, 0, this.leftLength);
        ByteString byteString = this.right;
        return byteString.partialIsValidUtf8(partialIsValidUtf8, 0, byteString.size()) == 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.protobuf.ByteString
    public int partialIsValidUtf8(int i, int i2, int i3) {
        int i4 = i2 + i3;
        int i5 = this.leftLength;
        if (i4 <= i5) {
            return this.left.partialIsValidUtf8(i, i2, i3);
        }
        if (i2 >= i5) {
            return this.right.partialIsValidUtf8(i, i2 - i5, i3);
        }
        int i6 = i5 - i2;
        return this.right.partialIsValidUtf8(this.left.partialIsValidUtf8(i, i2, i6), 0, i3 - i6);
    }

    @Override // com.google.protobuf.ByteString
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ByteString)) {
            return false;
        }
        ByteString byteString = (ByteString) obj;
        if (this.totalLength != byteString.size()) {
            return false;
        }
        if (this.totalLength == 0) {
            return true;
        }
        int peekCachedHashCode = peekCachedHashCode();
        int peekCachedHashCode2 = byteString.peekCachedHashCode();
        if (peekCachedHashCode == 0 || peekCachedHashCode2 == 0 || peekCachedHashCode == peekCachedHashCode2) {
            return a(byteString);
        }
        return false;
    }

    private boolean a(ByteString byteString) {
        boolean z;
        b bVar = new b(this);
        ByteString.f next = bVar.next();
        b bVar2 = new b(byteString);
        ByteString.f next2 = bVar2.next();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int size = next.size() - i;
            int size2 = next2.size() - i2;
            int min = Math.min(size, size2);
            if (i == 0) {
                z = next.a(next2, i2, min);
            } else {
                z = next2.a(next, i, min);
            }
            if (!z) {
                return false;
            }
            i3 += min;
            int i4 = this.totalLength;
            if (i3 < i4) {
                if (min == size) {
                    i = 0;
                    next = bVar.next();
                } else {
                    i += min;
                    next = next;
                }
                if (min == size2) {
                    next2 = bVar2.next();
                    i2 = 0;
                } else {
                    i2 += min;
                }
            } else if (i3 == i4) {
                return true;
            } else {
                throw new IllegalStateException();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.protobuf.ByteString
    public int partialHash(int i, int i2, int i3) {
        int i4 = i2 + i3;
        int i5 = this.leftLength;
        if (i4 <= i5) {
            return this.left.partialHash(i, i2, i3);
        }
        if (i2 >= i5) {
            return this.right.partialHash(i, i2 - i5, i3);
        }
        int i6 = i5 - i2;
        return this.right.partialHash(this.left.partialHash(i, i2, i6), 0, i3 - i6);
    }

    @Override // com.google.protobuf.ByteString
    public CodedInputStream newCodedInput() {
        return CodedInputStream.newInstance(new c());
    }

    @Override // com.google.protobuf.ByteString
    public InputStream newInput() {
        return new c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RopeByteString.java */
    /* loaded from: classes2.dex */
    public static class a {
        private final ArrayDeque<ByteString> a;

        private a() {
            this.a = new ArrayDeque<>();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public ByteString a(ByteString byteString, ByteString byteString2) {
            a(byteString);
            a(byteString2);
            ByteString pop = this.a.pop();
            while (!this.a.isEmpty()) {
                pop = new al(this.a.pop(), pop);
            }
            return pop;
        }

        private void a(ByteString byteString) {
            if (byteString.isBalanced()) {
                b(byteString);
            } else if (byteString instanceof al) {
                al alVar = (al) byteString;
                a(alVar.left);
                a(alVar.right);
            } else {
                throw new IllegalArgumentException("Has a new type of ByteString been created? Found " + byteString.getClass());
            }
        }

        private void b(ByteString byteString) {
            int a = a(byteString.size());
            int c = al.c(a + 1);
            if (this.a.isEmpty() || this.a.peek().size() >= c) {
                this.a.push(byteString);
                return;
            }
            int c2 = al.c(a);
            ByteString pop = this.a.pop();
            while (!this.a.isEmpty() && this.a.peek().size() < c2) {
                pop = new al(this.a.pop(), pop);
            }
            al alVar = new al(pop, byteString);
            while (!this.a.isEmpty() && this.a.peek().size() < al.c(a(alVar.size()) + 1)) {
                alVar = new al(this.a.pop(), alVar);
            }
            this.a.push(alVar);
        }

        private int a(int i) {
            int binarySearch = Arrays.binarySearch(al.a, i);
            return binarySearch < 0 ? (-(binarySearch + 1)) - 1 : binarySearch;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: RopeByteString.java */
    /* loaded from: classes2.dex */
    public static final class b implements Iterator<ByteString.f> {
        private final ArrayDeque<al> a;
        private ByteString.f b;

        private b(ByteString byteString) {
            if (byteString instanceof al) {
                al alVar = (al) byteString;
                this.a = new ArrayDeque<>(alVar.getTreeDepth());
                this.a.push(alVar);
                this.b = a(alVar.left);
                return;
            }
            this.a = null;
            this.b = (ByteString.f) byteString;
        }

        private ByteString.f a(ByteString byteString) {
            while (byteString instanceof al) {
                al alVar = (al) byteString;
                this.a.push(alVar);
                byteString = alVar.left;
            }
            return (ByteString.f) byteString;
        }

        private ByteString.f b() {
            ByteString.f a;
            do {
                ArrayDeque<al> arrayDeque = this.a;
                if (arrayDeque == null || arrayDeque.isEmpty()) {
                    return null;
                }
                a = a(this.a.pop().right);
            } while (a.isEmpty());
            return a;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.b != null;
        }

        /* renamed from: a */
        public ByteString.f next() {
            ByteString.f fVar = this.b;
            if (fVar != null) {
                this.b = b();
                return fVar;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    Object writeReplace() {
        return ByteString.a(toByteArray());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("RopeByteStream instances are not to be serialized directly");
    }

    /* compiled from: RopeByteString.java */
    /* loaded from: classes2.dex */
    private class c extends InputStream {
        private b b;
        private ByteString.f c;
        private int d;
        private int e;
        private int f;
        private int g;

        @Override // java.io.InputStream
        public boolean markSupported() {
            return true;
        }

        public c() {
            a();
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) {
            if (bArr == null) {
                throw new NullPointerException();
            } else if (i < 0 || i2 < 0 || i2 > bArr.length - i) {
                throw new IndexOutOfBoundsException();
            } else {
                int a = a(bArr, i, i2);
                if (a == 0) {
                    return -1;
                }
                return a;
            }
        }

        @Override // java.io.InputStream
        public long skip(long j) {
            if (j >= 0) {
                if (j > 2147483647L) {
                    j = 2147483647L;
                }
                return a(null, 0, (int) j);
            }
            throw new IndexOutOfBoundsException();
        }

        private int a(byte[] bArr, int i, int i2) {
            int i3 = i;
            int i4 = i2;
            while (i4 > 0) {
                b();
                if (this.c == null) {
                    break;
                }
                int min = Math.min(this.d - this.e, i4);
                if (bArr != null) {
                    this.c.copyTo(bArr, this.e, i3, min);
                    i3 += min;
                }
                this.e += min;
                i4 -= min;
            }
            return i2 - i4;
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            b();
            ByteString.f fVar = this.c;
            if (fVar == null) {
                return -1;
            }
            int i = this.e;
            this.e = i + 1;
            return fVar.byteAt(i) & 255;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            return al.this.size() - (this.f + this.e);
        }

        @Override // java.io.InputStream
        public void mark(int i) {
            this.g = this.f + this.e;
        }

        @Override // java.io.InputStream
        public synchronized void reset() {
            a();
            a(null, 0, this.g);
        }

        private void a() {
            this.b = new b(al.this);
            this.c = this.b.next();
            this.d = this.c.size();
            this.e = 0;
            this.f = 0;
        }

        private void b() {
            int i;
            if (this.c != null && this.e == (i = this.d)) {
                this.f += i;
                this.e = 0;
                if (this.b.hasNext()) {
                    this.c = this.b.next();
                    this.d = this.c.size();
                    return;
                }
                this.c = null;
                this.d = 0;
            }
        }
    }
}
