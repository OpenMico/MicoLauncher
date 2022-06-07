package com.google.protobuf;

import com.google.protobuf.ByteString;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.InvalidMarkException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NioByteString.java */
/* loaded from: classes2.dex */
public final class ae extends ByteString.f {
    private final ByteBuffer buffer;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ae(ByteBuffer byteBuffer) {
        Internal.a(byteBuffer, "buffer");
        this.buffer = byteBuffer.slice().order(ByteOrder.nativeOrder());
    }

    private Object writeReplace() {
        return ByteString.copyFrom(this.buffer.slice());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("NioByteString instances are not to be serialized directly");
    }

    @Override // com.google.protobuf.ByteString
    public byte byteAt(int i) {
        try {
            return this.buffer.get(i);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        } catch (IndexOutOfBoundsException e2) {
            throw new ArrayIndexOutOfBoundsException(e2.getMessage());
        }
    }

    @Override // com.google.protobuf.ByteString
    public byte a(int i) {
        return byteAt(i);
    }

    @Override // com.google.protobuf.ByteString
    public int size() {
        return this.buffer.remaining();
    }

    @Override // com.google.protobuf.ByteString
    public ByteString substring(int i, int i2) {
        try {
            return new ae(b(i, i2));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        } catch (IndexOutOfBoundsException e2) {
            throw new ArrayIndexOutOfBoundsException(e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.protobuf.ByteString
    public void copyToInternal(byte[] bArr, int i, int i2, int i3) {
        ByteBuffer slice = this.buffer.slice();
        slice.position(i);
        slice.get(bArr, i2, i3);
    }

    @Override // com.google.protobuf.ByteString
    public void copyTo(ByteBuffer byteBuffer) {
        byteBuffer.put(this.buffer.slice());
    }

    @Override // com.google.protobuf.ByteString
    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(toByteArray());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.ByteString.f
    public boolean a(ByteString byteString, int i, int i2) {
        return substring(0, i2).equals(byteString.substring(i, i2 + i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.protobuf.ByteString
    public void a(ByteOutput byteOutput) throws IOException {
        byteOutput.writeLazy(this.buffer.slice());
    }

    @Override // com.google.protobuf.ByteString
    public ByteBuffer asReadOnlyByteBuffer() {
        return this.buffer.asReadOnlyBuffer();
    }

    @Override // com.google.protobuf.ByteString
    public List<ByteBuffer> asReadOnlyByteBufferList() {
        return Collections.singletonList(asReadOnlyByteBuffer());
    }

    @Override // com.google.protobuf.ByteString
    protected String toStringInternal(Charset charset) {
        int i;
        int i2;
        byte[] bArr;
        if (this.buffer.hasArray()) {
            bArr = this.buffer.array();
            i2 = this.buffer.arrayOffset() + this.buffer.position();
            i = this.buffer.remaining();
        } else {
            bArr = toByteArray();
            i2 = 0;
            i = bArr.length;
        }
        return new String(bArr, i2, i, charset);
    }

    @Override // com.google.protobuf.ByteString
    public boolean isValidUtf8() {
        return au.a(this.buffer);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.protobuf.ByteString
    public int partialIsValidUtf8(int i, int i2, int i3) {
        return au.a(i, this.buffer, i2, i3 + i2);
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
        if (size() != byteString.size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (obj instanceof ae) {
            return this.buffer.equals(((ae) obj).buffer);
        }
        if (obj instanceof al) {
            return obj.equals(this);
        }
        return this.buffer.equals(byteString.asReadOnlyByteBuffer());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.protobuf.ByteString
    public int partialHash(int i, int i2, int i3) {
        int i4 = i;
        for (int i5 = i2; i5 < i2 + i3; i5++) {
            i4 = (i4 * 31) + this.buffer.get(i5);
        }
        return i4;
    }

    @Override // com.google.protobuf.ByteString
    public InputStream newInput() {
        return new InputStream() { // from class: com.google.protobuf.ae.1
            private final ByteBuffer b;

            @Override // java.io.InputStream
            public boolean markSupported() {
                return true;
            }

            {
                this.b = ae.this.buffer.slice();
            }

            @Override // java.io.InputStream
            public void mark(int i) {
                this.b.mark();
            }

            @Override // java.io.InputStream
            public void reset() throws IOException {
                try {
                    this.b.reset();
                } catch (InvalidMarkException e) {
                    throw new IOException(e);
                }
            }

            @Override // java.io.InputStream
            public int available() throws IOException {
                return this.b.remaining();
            }

            @Override // java.io.InputStream
            public int read() throws IOException {
                if (!this.b.hasRemaining()) {
                    return -1;
                }
                return this.b.get() & 255;
            }

            @Override // java.io.InputStream
            public int read(byte[] bArr, int i, int i2) throws IOException {
                if (!this.b.hasRemaining()) {
                    return -1;
                }
                int min = Math.min(i2, this.b.remaining());
                this.b.get(bArr, i, min);
                return min;
            }
        };
    }

    @Override // com.google.protobuf.ByteString
    public CodedInputStream newCodedInput() {
        return CodedInputStream.a(this.buffer, true);
    }

    private ByteBuffer b(int i, int i2) {
        if (i < this.buffer.position() || i2 > this.buffer.limit() || i > i2) {
            throw new IllegalArgumentException(String.format("Invalid indices [%d, %d]", Integer.valueOf(i), Integer.valueOf(i2)));
        }
        ByteBuffer slice = this.buffer.slice();
        slice.position(i - this.buffer.position());
        slice.limit(i2 - this.buffer.position());
        return slice;
    }
}
