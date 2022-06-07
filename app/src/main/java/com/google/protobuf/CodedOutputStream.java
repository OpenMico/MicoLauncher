package com.google.protobuf;

import com.google.protobuf.au;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes2.dex */
public abstract class CodedOutputStream extends ByteOutput {
    public static final int DEFAULT_BUFFER_SIZE = 4096;
    @Deprecated
    public static final int LITTLE_ENDIAN_32_SIZE = 4;
    private static final Logger b = Logger.getLogger(CodedOutputStream.class.getName());
    private static final boolean c = at.a();
    g a;
    private boolean d;

    public static int a(int i) {
        if (i > 4096) {
            return 4096;
        }
        return i;
    }

    public static int computeBoolSizeNoTag(boolean z) {
        return 1;
    }

    public static int computeDoubleSizeNoTag(double d2) {
        return 8;
    }

    public static int computeFixed32SizeNoTag(int i) {
        return 4;
    }

    public static int computeFixed64SizeNoTag(long j) {
        return 8;
    }

    public static int computeFloatSizeNoTag(float f2) {
        return 4;
    }

    public static int computeSFixed32SizeNoTag(int i) {
        return 4;
    }

    public static int computeSFixed64SizeNoTag(long j) {
        return 8;
    }

    public static int computeUInt32SizeNoTag(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    public static int computeUInt64SizeNoTag(long j) {
        int i;
        if (((-128) & j) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        if (((-34359738368L) & j) != 0) {
            i = 6;
            j >>>= 28;
        } else {
            i = 2;
        }
        if (((-2097152) & j) != 0) {
            i += 2;
            j >>>= 14;
        }
        return (j & (-16384)) != 0 ? i + 1 : i;
    }

    public static int encodeZigZag32(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public static long encodeZigZag64(long j) {
        return (j >> 63) ^ (j << 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(int i, MessageLite messageLite, am amVar) throws IOException;

    abstract void a(MessageLite messageLite, am amVar) throws IOException;

    abstract void a(byte[] bArr, int i, int i2) throws IOException;

    public abstract void flush() throws IOException;

    public abstract int getTotalBytesWritten();

    public abstract int spaceLeft();

    @Override // com.google.protobuf.ByteOutput
    public abstract void write(byte b2) throws IOException;

    @Override // com.google.protobuf.ByteOutput
    public abstract void write(ByteBuffer byteBuffer) throws IOException;

    @Override // com.google.protobuf.ByteOutput
    public abstract void write(byte[] bArr, int i, int i2) throws IOException;

    public abstract void writeBool(int i, boolean z) throws IOException;

    public abstract void writeByteArray(int i, byte[] bArr) throws IOException;

    public abstract void writeByteArray(int i, byte[] bArr, int i2, int i3) throws IOException;

    public abstract void writeByteBuffer(int i, ByteBuffer byteBuffer) throws IOException;

    public abstract void writeBytes(int i, ByteString byteString) throws IOException;

    public abstract void writeBytesNoTag(ByteString byteString) throws IOException;

    public abstract void writeFixed32(int i, int i2) throws IOException;

    public abstract void writeFixed32NoTag(int i) throws IOException;

    public abstract void writeFixed64(int i, long j) throws IOException;

    public abstract void writeFixed64NoTag(long j) throws IOException;

    public abstract void writeInt32(int i, int i2) throws IOException;

    public abstract void writeInt32NoTag(int i) throws IOException;

    @Override // com.google.protobuf.ByteOutput
    public abstract void writeLazy(ByteBuffer byteBuffer) throws IOException;

    @Override // com.google.protobuf.ByteOutput
    public abstract void writeLazy(byte[] bArr, int i, int i2) throws IOException;

    public abstract void writeMessage(int i, MessageLite messageLite) throws IOException;

    public abstract void writeMessageNoTag(MessageLite messageLite) throws IOException;

    public abstract void writeMessageSetExtension(int i, MessageLite messageLite) throws IOException;

    public abstract void writeRawBytes(ByteBuffer byteBuffer) throws IOException;

    public abstract void writeRawMessageSetExtension(int i, ByteString byteString) throws IOException;

    public abstract void writeString(int i, String str) throws IOException;

    public abstract void writeStringNoTag(String str) throws IOException;

    public abstract void writeTag(int i, int i2) throws IOException;

    public abstract void writeUInt32(int i, int i2) throws IOException;

    public abstract void writeUInt32NoTag(int i) throws IOException;

    public abstract void writeUInt64(int i, long j) throws IOException;

    public abstract void writeUInt64NoTag(long j) throws IOException;

    public static CodedOutputStream newInstance(OutputStream outputStream) {
        return newInstance(outputStream, 4096);
    }

    public static CodedOutputStream newInstance(OutputStream outputStream, int i) {
        return new d(outputStream, i);
    }

    public static CodedOutputStream newInstance(byte[] bArr) {
        return newInstance(bArr, 0, bArr.length);
    }

    public static CodedOutputStream newInstance(byte[] bArr, int i, int i2) {
        return new b(bArr, i, i2);
    }

    public static CodedOutputStream newInstance(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            return new c(byteBuffer);
        }
        if (!byteBuffer.isDirect() || byteBuffer.isReadOnly()) {
            throw new IllegalArgumentException("ByteBuffer is read-only");
        } else if (f.c()) {
            return a(byteBuffer);
        } else {
            return b(byteBuffer);
        }
    }

    static CodedOutputStream a(ByteBuffer byteBuffer) {
        return new f(byteBuffer);
    }

    static CodedOutputStream b(ByteBuffer byteBuffer) {
        return new e(byteBuffer);
    }

    public void useDeterministicSerialization() {
        this.d = true;
    }

    public boolean a() {
        return this.d;
    }

    @Deprecated
    public static CodedOutputStream newInstance(ByteBuffer byteBuffer, int i) {
        return newInstance(byteBuffer);
    }

    private CodedOutputStream() {
    }

    public final void writeSInt32(int i, int i2) throws IOException {
        writeUInt32(i, encodeZigZag32(i2));
    }

    public final void writeSFixed32(int i, int i2) throws IOException {
        writeFixed32(i, i2);
    }

    public final void writeInt64(int i, long j) throws IOException {
        writeUInt64(i, j);
    }

    public final void writeSInt64(int i, long j) throws IOException {
        writeUInt64(i, encodeZigZag64(j));
    }

    public final void writeSFixed64(int i, long j) throws IOException {
        writeFixed64(i, j);
    }

    public final void writeFloat(int i, float f2) throws IOException {
        writeFixed32(i, Float.floatToRawIntBits(f2));
    }

    public final void writeDouble(int i, double d2) throws IOException {
        writeFixed64(i, Double.doubleToRawLongBits(d2));
    }

    public final void writeEnum(int i, int i2) throws IOException {
        writeInt32(i, i2);
    }

    public final void writeRawByte(byte b2) throws IOException {
        write(b2);
    }

    public final void writeRawByte(int i) throws IOException {
        write((byte) i);
    }

    public final void writeRawBytes(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    public final void writeRawBytes(byte[] bArr, int i, int i2) throws IOException {
        write(bArr, i, i2);
    }

    public final void writeRawBytes(ByteString byteString) throws IOException {
        byteString.a(this);
    }

    public final void writeSInt32NoTag(int i) throws IOException {
        writeUInt32NoTag(encodeZigZag32(i));
    }

    public final void writeSFixed32NoTag(int i) throws IOException {
        writeFixed32NoTag(i);
    }

    public final void writeInt64NoTag(long j) throws IOException {
        writeUInt64NoTag(j);
    }

    public final void writeSInt64NoTag(long j) throws IOException {
        writeUInt64NoTag(encodeZigZag64(j));
    }

    public final void writeSFixed64NoTag(long j) throws IOException {
        writeFixed64NoTag(j);
    }

    public final void writeFloatNoTag(float f2) throws IOException {
        writeFixed32NoTag(Float.floatToRawIntBits(f2));
    }

    public final void writeDoubleNoTag(double d2) throws IOException {
        writeFixed64NoTag(Double.doubleToRawLongBits(d2));
    }

    public final void writeBoolNoTag(boolean z) throws IOException {
        write(z ? (byte) 1 : (byte) 0);
    }

    public final void writeEnumNoTag(int i) throws IOException {
        writeInt32NoTag(i);
    }

    public final void writeByteArrayNoTag(byte[] bArr) throws IOException {
        a(bArr, 0, bArr.length);
    }

    public static int computeInt32Size(int i, int i2) {
        return computeTagSize(i) + computeInt32SizeNoTag(i2);
    }

    public static int computeUInt32Size(int i, int i2) {
        return computeTagSize(i) + computeUInt32SizeNoTag(i2);
    }

    public static int computeSInt32Size(int i, int i2) {
        return computeTagSize(i) + computeSInt32SizeNoTag(i2);
    }

    public static int computeFixed32Size(int i, int i2) {
        return computeTagSize(i) + computeFixed32SizeNoTag(i2);
    }

    public static int computeSFixed32Size(int i, int i2) {
        return computeTagSize(i) + computeSFixed32SizeNoTag(i2);
    }

    public static int computeInt64Size(int i, long j) {
        return computeTagSize(i) + computeInt64SizeNoTag(j);
    }

    public static int computeUInt64Size(int i, long j) {
        return computeTagSize(i) + computeUInt64SizeNoTag(j);
    }

    public static int computeSInt64Size(int i, long j) {
        return computeTagSize(i) + computeSInt64SizeNoTag(j);
    }

    public static int computeFixed64Size(int i, long j) {
        return computeTagSize(i) + computeFixed64SizeNoTag(j);
    }

    public static int computeSFixed64Size(int i, long j) {
        return computeTagSize(i) + computeSFixed64SizeNoTag(j);
    }

    public static int computeFloatSize(int i, float f2) {
        return computeTagSize(i) + computeFloatSizeNoTag(f2);
    }

    public static int computeDoubleSize(int i, double d2) {
        return computeTagSize(i) + computeDoubleSizeNoTag(d2);
    }

    public static int computeBoolSize(int i, boolean z) {
        return computeTagSize(i) + computeBoolSizeNoTag(z);
    }

    public static int computeEnumSize(int i, int i2) {
        return computeTagSize(i) + computeEnumSizeNoTag(i2);
    }

    public static int computeStringSize(int i, String str) {
        return computeTagSize(i) + computeStringSizeNoTag(str);
    }

    public static int computeBytesSize(int i, ByteString byteString) {
        return computeTagSize(i) + computeBytesSizeNoTag(byteString);
    }

    public static int computeByteArraySize(int i, byte[] bArr) {
        return computeTagSize(i) + computeByteArraySizeNoTag(bArr);
    }

    public static int computeByteBufferSize(int i, ByteBuffer byteBuffer) {
        return computeTagSize(i) + computeByteBufferSizeNoTag(byteBuffer);
    }

    public static int computeLazyFieldSize(int i, LazyFieldLite lazyFieldLite) {
        return computeTagSize(i) + computeLazyFieldSizeNoTag(lazyFieldLite);
    }

    public static int computeMessageSize(int i, MessageLite messageLite) {
        return computeTagSize(i) + computeMessageSizeNoTag(messageLite);
    }

    public static int b(int i, MessageLite messageLite, am amVar) {
        return computeTagSize(i) + b(messageLite, amVar);
    }

    public static int computeMessageSetExtensionSize(int i, MessageLite messageLite) {
        return (computeTagSize(1) * 2) + computeUInt32Size(2, i) + computeMessageSize(3, messageLite);
    }

    public static int computeRawMessageSetExtensionSize(int i, ByteString byteString) {
        return (computeTagSize(1) * 2) + computeUInt32Size(2, i) + computeBytesSize(3, byteString);
    }

    public static int computeLazyFieldMessageSetExtensionSize(int i, LazyFieldLite lazyFieldLite) {
        return (computeTagSize(1) * 2) + computeUInt32Size(2, i) + computeLazyFieldSize(3, lazyFieldLite);
    }

    public static int computeTagSize(int i) {
        return computeUInt32SizeNoTag(WireFormat.a(i, 0));
    }

    public static int computeInt32SizeNoTag(int i) {
        if (i >= 0) {
            return computeUInt32SizeNoTag(i);
        }
        return 10;
    }

    public static int computeSInt32SizeNoTag(int i) {
        return computeUInt32SizeNoTag(encodeZigZag32(i));
    }

    public static int computeInt64SizeNoTag(long j) {
        return computeUInt64SizeNoTag(j);
    }

    public static int computeSInt64SizeNoTag(long j) {
        return computeUInt64SizeNoTag(encodeZigZag64(j));
    }

    public static int computeEnumSizeNoTag(int i) {
        return computeInt32SizeNoTag(i);
    }

    public static int computeStringSizeNoTag(String str) {
        int i;
        try {
            i = au.a(str);
        } catch (au.d unused) {
            i = str.getBytes(Internal.a).length;
        }
        return b(i);
    }

    public static int computeLazyFieldSizeNoTag(LazyFieldLite lazyFieldLite) {
        return b(lazyFieldLite.getSerializedSize());
    }

    public static int computeBytesSizeNoTag(ByteString byteString) {
        return b(byteString.size());
    }

    public static int computeByteArraySizeNoTag(byte[] bArr) {
        return b(bArr.length);
    }

    public static int computeByteBufferSizeNoTag(ByteBuffer byteBuffer) {
        return b(byteBuffer.capacity());
    }

    public static int computeMessageSizeNoTag(MessageLite messageLite) {
        return b(messageLite.getSerializedSize());
    }

    public static int b(MessageLite messageLite, am amVar) {
        return b(((AbstractMessageLite) messageLite).a(amVar));
    }

    public static int b(int i) {
        return computeUInt32SizeNoTag(i) + i;
    }

    public final void checkNoSpaceLeft() {
        if (spaceLeft() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    /* loaded from: classes2.dex */
    public static class OutOfSpaceException extends IOException {
        private static final long serialVersionUID = -6947486886997889499L;

        OutOfSpaceException() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        OutOfSpaceException(String str) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.: " + str);
        }

        OutOfSpaceException(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }

        OutOfSpaceException(String str, Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.: " + str, th);
        }
    }

    final void a(String str, au.d dVar) throws IOException {
        b.log(Level.WARNING, "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", (Throwable) dVar);
        byte[] bytes = str.getBytes(Internal.a);
        try {
            writeUInt32NoTag(bytes.length);
            writeLazy(bytes, 0, bytes.length);
        } catch (OutOfSpaceException e2) {
            throw e2;
        } catch (IndexOutOfBoundsException e3) {
            throw new OutOfSpaceException(e3);
        }
    }

    @Deprecated
    public final void writeGroup(int i, MessageLite messageLite) throws IOException {
        writeTag(i, 3);
        writeGroupNoTag(messageLite);
        writeTag(i, 4);
    }

    @Deprecated
    public final void c(int i, MessageLite messageLite, am amVar) throws IOException {
        writeTag(i, 3);
        c(messageLite, amVar);
        writeTag(i, 4);
    }

    @Deprecated
    public final void writeGroupNoTag(MessageLite messageLite) throws IOException {
        messageLite.writeTo(this);
    }

    @Deprecated
    final void c(MessageLite messageLite, am amVar) throws IOException {
        amVar.a((am) messageLite, (Writer) this.a);
    }

    @Deprecated
    public static int computeGroupSize(int i, MessageLite messageLite) {
        return (computeTagSize(i) * 2) + computeGroupSizeNoTag(messageLite);
    }

    @Deprecated
    public static int d(int i, MessageLite messageLite, am amVar) {
        return (computeTagSize(i) * 2) + d(messageLite, amVar);
    }

    @Deprecated
    public static int computeGroupSizeNoTag(MessageLite messageLite) {
        return messageLite.getSerializedSize();
    }

    @Deprecated
    static int d(MessageLite messageLite, am amVar) {
        return ((AbstractMessageLite) messageLite).a(amVar);
    }

    @Deprecated
    public final void writeRawVarint32(int i) throws IOException {
        writeUInt32NoTag(i);
    }

    @Deprecated
    public final void writeRawVarint64(long j) throws IOException {
        writeUInt64NoTag(j);
    }

    @Deprecated
    public static int computeRawVarint32Size(int i) {
        return computeUInt32SizeNoTag(i);
    }

    @Deprecated
    public static int computeRawVarint64Size(long j) {
        return computeUInt64SizeNoTag(j);
    }

    @Deprecated
    public final void writeRawLittleEndian32(int i) throws IOException {
        writeFixed32NoTag(i);
    }

    @Deprecated
    public final void writeRawLittleEndian64(long j) throws IOException {
        writeFixed64NoTag(j);
    }

    /* loaded from: classes2.dex */
    public static class b extends CodedOutputStream {
        private final byte[] b;
        private final int c;
        private final int d;
        private int e;

        @Override // com.google.protobuf.CodedOutputStream
        public void flush() {
        }

        b(byte[] bArr, int i, int i2) {
            super();
            if (bArr != null) {
                int i3 = i + i2;
                if ((i | i2 | (bArr.length - i3)) >= 0) {
                    this.b = bArr;
                    this.c = i;
                    this.e = i;
                    this.d = i3;
                    return;
                }
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
            }
            throw new NullPointerException("buffer");
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeTag(int i, int i2) throws IOException {
            writeUInt32NoTag(WireFormat.a(i, i2));
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeInt32(int i, int i2) throws IOException {
            writeTag(i, 0);
            writeInt32NoTag(i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeUInt32(int i, int i2) throws IOException {
            writeTag(i, 0);
            writeUInt32NoTag(i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeFixed32(int i, int i2) throws IOException {
            writeTag(i, 5);
            writeFixed32NoTag(i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeUInt64(int i, long j) throws IOException {
            writeTag(i, 0);
            writeUInt64NoTag(j);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeFixed64(int i, long j) throws IOException {
            writeTag(i, 1);
            writeFixed64NoTag(j);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeBool(int i, boolean z) throws IOException {
            writeTag(i, 0);
            write(z ? (byte) 1 : (byte) 0);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeString(int i, String str) throws IOException {
            writeTag(i, 2);
            writeStringNoTag(str);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeBytes(int i, ByteString byteString) throws IOException {
            writeTag(i, 2);
            writeBytesNoTag(byteString);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeByteArray(int i, byte[] bArr) throws IOException {
            writeByteArray(i, bArr, 0, bArr.length);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeByteArray(int i, byte[] bArr, int i2, int i3) throws IOException {
            writeTag(i, 2);
            a(bArr, i2, i3);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeByteBuffer(int i, ByteBuffer byteBuffer) throws IOException {
            writeTag(i, 2);
            writeUInt32NoTag(byteBuffer.capacity());
            writeRawBytes(byteBuffer);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeBytesNoTag(ByteString byteString) throws IOException {
            writeUInt32NoTag(byteString.size());
            byteString.a(this);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void a(byte[] bArr, int i, int i2) throws IOException {
            writeUInt32NoTag(i2);
            write(bArr, i, i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeRawBytes(ByteBuffer byteBuffer) throws IOException {
            if (byteBuffer.hasArray()) {
                write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
                return;
            }
            ByteBuffer duplicate = byteBuffer.duplicate();
            duplicate.clear();
            write(duplicate);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeMessage(int i, MessageLite messageLite) throws IOException {
            writeTag(i, 2);
            writeMessageNoTag(messageLite);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.protobuf.CodedOutputStream
        public final void a(int i, MessageLite messageLite, am amVar) throws IOException {
            writeTag(i, 2);
            writeUInt32NoTag(((AbstractMessageLite) messageLite).a(amVar));
            amVar.a((am) messageLite, (Writer) this.a);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeMessageSetExtension(int i, MessageLite messageLite) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i);
            writeMessage(3, messageLite);
            writeTag(1, 4);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeRawMessageSetExtension(int i, ByteString byteString) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i);
            writeBytes(3, byteString);
            writeTag(1, 4);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeMessageNoTag(MessageLite messageLite) throws IOException {
            writeUInt32NoTag(messageLite.getSerializedSize());
            messageLite.writeTo(this);
        }

        @Override // com.google.protobuf.CodedOutputStream
        final void a(MessageLite messageLite, am amVar) throws IOException {
            writeUInt32NoTag(((AbstractMessageLite) messageLite).a(amVar));
            amVar.a((am) messageLite, (Writer) this.a);
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public final void write(byte b) throws IOException {
            try {
                byte[] bArr = this.b;
                int i = this.e;
                this.e = i + 1;
                bArr[i] = b;
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.e), Integer.valueOf(this.d), 1), e);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeInt32NoTag(int i) throws IOException {
            if (i >= 0) {
                writeUInt32NoTag(i);
            } else {
                writeUInt64NoTag(i);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeUInt32NoTag(int i) throws IOException {
            if (!CodedOutputStream.c || b.a() || spaceLeft() < 5) {
                while ((i & (-128)) != 0) {
                    try {
                        byte[] bArr = this.b;
                        int i2 = this.e;
                        this.e = i2 + 1;
                        bArr[i2] = (byte) ((i & 127) | 128);
                        i >>>= 7;
                    } catch (IndexOutOfBoundsException e) {
                        throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.e), Integer.valueOf(this.d), 1), e);
                    }
                }
                byte[] bArr2 = this.b;
                int i3 = this.e;
                this.e = i3 + 1;
                bArr2[i3] = (byte) i;
            } else if ((i & (-128)) == 0) {
                byte[] bArr3 = this.b;
                int i4 = this.e;
                this.e = i4 + 1;
                at.a(bArr3, i4, (byte) i);
            } else {
                byte[] bArr4 = this.b;
                int i5 = this.e;
                this.e = i5 + 1;
                at.a(bArr4, i5, (byte) (i | 128));
                int i6 = i >>> 7;
                if ((i6 & (-128)) == 0) {
                    byte[] bArr5 = this.b;
                    int i7 = this.e;
                    this.e = i7 + 1;
                    at.a(bArr5, i7, (byte) i6);
                    return;
                }
                byte[] bArr6 = this.b;
                int i8 = this.e;
                this.e = i8 + 1;
                at.a(bArr6, i8, (byte) (i6 | 128));
                int i9 = i6 >>> 7;
                if ((i9 & (-128)) == 0) {
                    byte[] bArr7 = this.b;
                    int i10 = this.e;
                    this.e = i10 + 1;
                    at.a(bArr7, i10, (byte) i9);
                    return;
                }
                byte[] bArr8 = this.b;
                int i11 = this.e;
                this.e = i11 + 1;
                at.a(bArr8, i11, (byte) (i9 | 128));
                int i12 = i9 >>> 7;
                if ((i12 & (-128)) == 0) {
                    byte[] bArr9 = this.b;
                    int i13 = this.e;
                    this.e = i13 + 1;
                    at.a(bArr9, i13, (byte) i12);
                    return;
                }
                byte[] bArr10 = this.b;
                int i14 = this.e;
                this.e = i14 + 1;
                at.a(bArr10, i14, (byte) (i12 | 128));
                byte[] bArr11 = this.b;
                int i15 = this.e;
                this.e = i15 + 1;
                at.a(bArr11, i15, (byte) (i12 >>> 7));
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeFixed32NoTag(int i) throws IOException {
            try {
                byte[] bArr = this.b;
                int i2 = this.e;
                this.e = i2 + 1;
                bArr[i2] = (byte) (i & 255);
                byte[] bArr2 = this.b;
                int i3 = this.e;
                this.e = i3 + 1;
                bArr2[i3] = (byte) ((i >> 8) & 255);
                byte[] bArr3 = this.b;
                int i4 = this.e;
                this.e = i4 + 1;
                bArr3[i4] = (byte) ((i >> 16) & 255);
                byte[] bArr4 = this.b;
                int i5 = this.e;
                this.e = i5 + 1;
                bArr4[i5] = (byte) ((i >> 24) & 255);
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.e), Integer.valueOf(this.d), 1), e);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeUInt64NoTag(long j) throws IOException {
            if (!CodedOutputStream.c || spaceLeft() < 10) {
                while ((j & (-128)) != 0) {
                    try {
                        byte[] bArr = this.b;
                        int i = this.e;
                        this.e = i + 1;
                        bArr[i] = (byte) ((((int) j) & 127) | 128);
                        j >>>= 7;
                    } catch (IndexOutOfBoundsException e) {
                        throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.e), Integer.valueOf(this.d), 1), e);
                    }
                }
                byte[] bArr2 = this.b;
                int i2 = this.e;
                this.e = i2 + 1;
                bArr2[i2] = (byte) j;
                return;
            }
            while ((j & (-128)) != 0) {
                byte[] bArr3 = this.b;
                int i3 = this.e;
                this.e = i3 + 1;
                at.a(bArr3, i3, (byte) ((((int) j) & 127) | 128));
                j >>>= 7;
            }
            byte[] bArr4 = this.b;
            int i4 = this.e;
            this.e = i4 + 1;
            at.a(bArr4, i4, (byte) j);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeFixed64NoTag(long j) throws IOException {
            try {
                byte[] bArr = this.b;
                int i = this.e;
                this.e = i + 1;
                bArr[i] = (byte) (((int) j) & 255);
                byte[] bArr2 = this.b;
                int i2 = this.e;
                this.e = i2 + 1;
                bArr2[i2] = (byte) (((int) (j >> 8)) & 255);
                byte[] bArr3 = this.b;
                int i3 = this.e;
                this.e = i3 + 1;
                bArr3[i3] = (byte) (((int) (j >> 16)) & 255);
                byte[] bArr4 = this.b;
                int i4 = this.e;
                this.e = i4 + 1;
                bArr4[i4] = (byte) (((int) (j >> 24)) & 255);
                byte[] bArr5 = this.b;
                int i5 = this.e;
                this.e = i5 + 1;
                bArr5[i5] = (byte) (((int) (j >> 32)) & 255);
                byte[] bArr6 = this.b;
                int i6 = this.e;
                this.e = i6 + 1;
                bArr6[i6] = (byte) (((int) (j >> 40)) & 255);
                byte[] bArr7 = this.b;
                int i7 = this.e;
                this.e = i7 + 1;
                bArr7[i7] = (byte) (((int) (j >> 48)) & 255);
                byte[] bArr8 = this.b;
                int i8 = this.e;
                this.e = i8 + 1;
                bArr8[i8] = (byte) (((int) (j >> 56)) & 255);
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.e), Integer.valueOf(this.d), 1), e);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public final void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                System.arraycopy(bArr, i, this.b, this.e, i2);
                this.e += i2;
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.e), Integer.valueOf(this.d), Integer.valueOf(i2)), e);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public final void writeLazy(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public final void write(ByteBuffer byteBuffer) throws IOException {
            int remaining = byteBuffer.remaining();
            try {
                byteBuffer.get(this.b, this.e, remaining);
                this.e += remaining;
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.e), Integer.valueOf(this.d), Integer.valueOf(remaining)), e);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public final void writeLazy(ByteBuffer byteBuffer) throws IOException {
            write(byteBuffer);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final void writeStringNoTag(String str) throws IOException {
            int i = this.e;
            try {
                int computeUInt32SizeNoTag = computeUInt32SizeNoTag(str.length() * 3);
                int computeUInt32SizeNoTag2 = computeUInt32SizeNoTag(str.length());
                if (computeUInt32SizeNoTag2 == computeUInt32SizeNoTag) {
                    this.e = i + computeUInt32SizeNoTag2;
                    int a = au.a(str, this.b, this.e, spaceLeft());
                    this.e = i;
                    writeUInt32NoTag((a - i) - computeUInt32SizeNoTag2);
                    this.e = a;
                } else {
                    writeUInt32NoTag(au.a(str));
                    this.e = au.a(str, this.b, this.e, spaceLeft());
                }
            } catch (au.d e) {
                this.e = i;
                a(str, e);
            } catch (IndexOutOfBoundsException e2) {
                throw new OutOfSpaceException(e2);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final int spaceLeft() {
            return this.d - this.e;
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final int getTotalBytesWritten() {
            return this.e - this.c;
        }
    }

    /* loaded from: classes2.dex */
    public static final class c extends b {
        private final ByteBuffer b;
        private int c;

        c(ByteBuffer byteBuffer) {
            super(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
            this.b = byteBuffer;
            this.c = byteBuffer.position();
        }

        @Override // com.google.protobuf.CodedOutputStream.b, com.google.protobuf.CodedOutputStream
        public void flush() {
            this.b.position(this.c + getTotalBytesWritten());
        }
    }

    /* loaded from: classes2.dex */
    public static final class e extends CodedOutputStream {
        private final ByteBuffer b;
        private final ByteBuffer c;
        private final int d;

        e(ByteBuffer byteBuffer) {
            super();
            this.b = byteBuffer;
            this.c = byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.d = byteBuffer.position();
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeTag(int i, int i2) throws IOException {
            writeUInt32NoTag(WireFormat.a(i, i2));
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeInt32(int i, int i2) throws IOException {
            writeTag(i, 0);
            writeInt32NoTag(i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeUInt32(int i, int i2) throws IOException {
            writeTag(i, 0);
            writeUInt32NoTag(i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeFixed32(int i, int i2) throws IOException {
            writeTag(i, 5);
            writeFixed32NoTag(i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeUInt64(int i, long j) throws IOException {
            writeTag(i, 0);
            writeUInt64NoTag(j);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeFixed64(int i, long j) throws IOException {
            writeTag(i, 1);
            writeFixed64NoTag(j);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeBool(int i, boolean z) throws IOException {
            writeTag(i, 0);
            write(z ? (byte) 1 : (byte) 0);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeString(int i, String str) throws IOException {
            writeTag(i, 2);
            writeStringNoTag(str);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeBytes(int i, ByteString byteString) throws IOException {
            writeTag(i, 2);
            writeBytesNoTag(byteString);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeByteArray(int i, byte[] bArr) throws IOException {
            writeByteArray(i, bArr, 0, bArr.length);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeByteArray(int i, byte[] bArr, int i2, int i3) throws IOException {
            writeTag(i, 2);
            a(bArr, i2, i3);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeByteBuffer(int i, ByteBuffer byteBuffer) throws IOException {
            writeTag(i, 2);
            writeUInt32NoTag(byteBuffer.capacity());
            writeRawBytes(byteBuffer);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeMessage(int i, MessageLite messageLite) throws IOException {
            writeTag(i, 2);
            writeMessageNoTag(messageLite);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.protobuf.CodedOutputStream
        public void a(int i, MessageLite messageLite, am amVar) throws IOException {
            writeTag(i, 2);
            a(messageLite, amVar);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeMessageSetExtension(int i, MessageLite messageLite) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i);
            writeMessage(3, messageLite);
            writeTag(1, 4);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeRawMessageSetExtension(int i, ByteString byteString) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i);
            writeBytes(3, byteString);
            writeTag(1, 4);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeMessageNoTag(MessageLite messageLite) throws IOException {
            writeUInt32NoTag(messageLite.getSerializedSize());
            messageLite.writeTo(this);
        }

        @Override // com.google.protobuf.CodedOutputStream
        void a(MessageLite messageLite, am amVar) throws IOException {
            writeUInt32NoTag(((AbstractMessageLite) messageLite).a(amVar));
            amVar.a((am) messageLite, (Writer) this.a);
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public void write(byte b) throws IOException {
            try {
                this.c.put(b);
            } catch (BufferOverflowException e) {
                throw new OutOfSpaceException(e);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeBytesNoTag(ByteString byteString) throws IOException {
            writeUInt32NoTag(byteString.size());
            byteString.a(this);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void a(byte[] bArr, int i, int i2) throws IOException {
            writeUInt32NoTag(i2);
            write(bArr, i, i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeRawBytes(ByteBuffer byteBuffer) throws IOException {
            if (byteBuffer.hasArray()) {
                write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
                return;
            }
            ByteBuffer duplicate = byteBuffer.duplicate();
            duplicate.clear();
            write(duplicate);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeInt32NoTag(int i) throws IOException {
            if (i >= 0) {
                writeUInt32NoTag(i);
            } else {
                writeUInt64NoTag(i);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeUInt32NoTag(int i) throws IOException {
            while ((i & (-128)) != 0) {
                try {
                    this.c.put((byte) ((i & 127) | 128));
                    i >>>= 7;
                } catch (BufferOverflowException e) {
                    throw new OutOfSpaceException(e);
                }
            }
            this.c.put((byte) i);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeFixed32NoTag(int i) throws IOException {
            try {
                this.c.putInt(i);
            } catch (BufferOverflowException e) {
                throw new OutOfSpaceException(e);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeUInt64NoTag(long j) throws IOException {
            while (((-128) & j) != 0) {
                try {
                    this.c.put((byte) ((((int) j) & 127) | 128));
                    j >>>= 7;
                } catch (BufferOverflowException e) {
                    throw new OutOfSpaceException(e);
                }
            }
            this.c.put((byte) j);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeFixed64NoTag(long j) throws IOException {
            try {
                this.c.putLong(j);
            } catch (BufferOverflowException e) {
                throw new OutOfSpaceException(e);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public void write(byte[] bArr, int i, int i2) throws IOException {
            try {
                this.c.put(bArr, i, i2);
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(e);
            } catch (BufferOverflowException e2) {
                throw new OutOfSpaceException(e2);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public void writeLazy(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public void write(ByteBuffer byteBuffer) throws IOException {
            try {
                this.c.put(byteBuffer);
            } catch (BufferOverflowException e) {
                throw new OutOfSpaceException(e);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public void writeLazy(ByteBuffer byteBuffer) throws IOException {
            write(byteBuffer);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeStringNoTag(String str) throws IOException {
            int position = this.c.position();
            try {
                int computeUInt32SizeNoTag = computeUInt32SizeNoTag(str.length() * 3);
                int computeUInt32SizeNoTag2 = computeUInt32SizeNoTag(str.length());
                if (computeUInt32SizeNoTag2 == computeUInt32SizeNoTag) {
                    int position2 = this.c.position() + computeUInt32SizeNoTag2;
                    this.c.position(position2);
                    a(str);
                    int position3 = this.c.position();
                    this.c.position(position);
                    writeUInt32NoTag(position3 - position2);
                    this.c.position(position3);
                } else {
                    writeUInt32NoTag(au.a(str));
                    a(str);
                }
            } catch (au.d e) {
                this.c.position(position);
                a(str, e);
            } catch (IllegalArgumentException e2) {
                throw new OutOfSpaceException(e2);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void flush() {
            this.b.position(this.c.position());
        }

        @Override // com.google.protobuf.CodedOutputStream
        public int spaceLeft() {
            return this.c.remaining();
        }

        @Override // com.google.protobuf.CodedOutputStream
        public int getTotalBytesWritten() {
            return this.c.position() - this.d;
        }

        private void a(String str) throws IOException {
            try {
                au.a(str, this.c);
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(e);
            }
        }
    }

    /* loaded from: classes2.dex */
    public static final class f extends CodedOutputStream {
        private final ByteBuffer b;
        private final ByteBuffer c;
        private final long d;
        private final long e;
        private final long f;
        private final long g;
        private long h;

        f(ByteBuffer byteBuffer) {
            super();
            this.b = byteBuffer;
            this.c = byteBuffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.d = at.a(byteBuffer);
            this.e = this.d + byteBuffer.position();
            this.f = this.d + byteBuffer.limit();
            this.g = this.f - 10;
            this.h = this.e;
        }

        static boolean c() {
            return at.b();
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeTag(int i, int i2) throws IOException {
            writeUInt32NoTag(WireFormat.a(i, i2));
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeInt32(int i, int i2) throws IOException {
            writeTag(i, 0);
            writeInt32NoTag(i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeUInt32(int i, int i2) throws IOException {
            writeTag(i, 0);
            writeUInt32NoTag(i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeFixed32(int i, int i2) throws IOException {
            writeTag(i, 5);
            writeFixed32NoTag(i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeUInt64(int i, long j) throws IOException {
            writeTag(i, 0);
            writeUInt64NoTag(j);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeFixed64(int i, long j) throws IOException {
            writeTag(i, 1);
            writeFixed64NoTag(j);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeBool(int i, boolean z) throws IOException {
            writeTag(i, 0);
            write(z ? (byte) 1 : (byte) 0);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeString(int i, String str) throws IOException {
            writeTag(i, 2);
            writeStringNoTag(str);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeBytes(int i, ByteString byteString) throws IOException {
            writeTag(i, 2);
            writeBytesNoTag(byteString);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeByteArray(int i, byte[] bArr) throws IOException {
            writeByteArray(i, bArr, 0, bArr.length);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeByteArray(int i, byte[] bArr, int i2, int i3) throws IOException {
            writeTag(i, 2);
            a(bArr, i2, i3);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeByteBuffer(int i, ByteBuffer byteBuffer) throws IOException {
            writeTag(i, 2);
            writeUInt32NoTag(byteBuffer.capacity());
            writeRawBytes(byteBuffer);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeMessage(int i, MessageLite messageLite) throws IOException {
            writeTag(i, 2);
            writeMessageNoTag(messageLite);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.protobuf.CodedOutputStream
        public void a(int i, MessageLite messageLite, am amVar) throws IOException {
            writeTag(i, 2);
            a(messageLite, amVar);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeMessageSetExtension(int i, MessageLite messageLite) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i);
            writeMessage(3, messageLite);
            writeTag(1, 4);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeRawMessageSetExtension(int i, ByteString byteString) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i);
            writeBytes(3, byteString);
            writeTag(1, 4);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeMessageNoTag(MessageLite messageLite) throws IOException {
            writeUInt32NoTag(messageLite.getSerializedSize());
            messageLite.writeTo(this);
        }

        @Override // com.google.protobuf.CodedOutputStream
        void a(MessageLite messageLite, am amVar) throws IOException {
            writeUInt32NoTag(((AbstractMessageLite) messageLite).a(amVar));
            amVar.a((am) messageLite, (Writer) this.a);
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public void write(byte b) throws IOException {
            long j = this.h;
            if (j < this.f) {
                this.h = 1 + j;
                at.a(j, b);
                return;
            }
            throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(j), Long.valueOf(this.f), 1));
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeBytesNoTag(ByteString byteString) throws IOException {
            writeUInt32NoTag(byteString.size());
            byteString.a(this);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void a(byte[] bArr, int i, int i2) throws IOException {
            writeUInt32NoTag(i2);
            write(bArr, i, i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeRawBytes(ByteBuffer byteBuffer) throws IOException {
            if (byteBuffer.hasArray()) {
                write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
                return;
            }
            ByteBuffer duplicate = byteBuffer.duplicate();
            duplicate.clear();
            write(duplicate);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeInt32NoTag(int i) throws IOException {
            if (i >= 0) {
                writeUInt32NoTag(i);
            } else {
                writeUInt64NoTag(i);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeUInt32NoTag(int i) throws IOException {
            if (this.h <= this.g) {
                while ((i & (-128)) != 0) {
                    long j = this.h;
                    this.h = j + 1;
                    at.a(j, (byte) ((i & 127) | 128));
                    i >>>= 7;
                }
                long j2 = this.h;
                this.h = 1 + j2;
                at.a(j2, (byte) i);
                return;
            }
            while (true) {
                long j3 = this.h;
                if (j3 >= this.f) {
                    throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(j3), Long.valueOf(this.f), 1));
                } else if ((i & (-128)) == 0) {
                    this.h = 1 + j3;
                    at.a(j3, (byte) i);
                    return;
                } else {
                    this.h = j3 + 1;
                    at.a(j3, (byte) ((i & 127) | 128));
                    i >>>= 7;
                }
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeFixed32NoTag(int i) throws IOException {
            this.c.putInt(b(this.h), i);
            this.h += 4;
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeUInt64NoTag(long j) throws IOException {
            if (this.h <= this.g) {
                while ((j & (-128)) != 0) {
                    long j2 = this.h;
                    this.h = j2 + 1;
                    at.a(j2, (byte) ((((int) j) & 127) | 128));
                    j >>>= 7;
                }
                long j3 = this.h;
                this.h = 1 + j3;
                at.a(j3, (byte) j);
                return;
            }
            while (true) {
                long j4 = this.h;
                if (j4 >= this.f) {
                    throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(j4), Long.valueOf(this.f), 1));
                } else if ((j & (-128)) == 0) {
                    this.h = 1 + j4;
                    at.a(j4, (byte) j);
                    return;
                } else {
                    this.h = j4 + 1;
                    at.a(j4, (byte) ((((int) j) & 127) | 128));
                    j >>>= 7;
                }
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeFixed64NoTag(long j) throws IOException {
            this.c.putLong(b(this.h), j);
            this.h += 8;
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public void write(byte[] bArr, int i, int i2) throws IOException {
            if (bArr != null && i >= 0 && i2 >= 0 && bArr.length - i2 >= i) {
                long j = i2;
                long j2 = this.h;
                if (this.f - j >= j2) {
                    at.a(bArr, i, j2, j);
                    this.h += j;
                    return;
                }
            }
            if (bArr == null) {
                throw new NullPointerException(com.xiaomi.onetrack.api.b.p);
            }
            throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", Long.valueOf(this.h), Long.valueOf(this.f), Integer.valueOf(i2)));
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public void writeLazy(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public void write(ByteBuffer byteBuffer) throws IOException {
            try {
                int remaining = byteBuffer.remaining();
                a(this.h);
                this.c.put(byteBuffer);
                this.h += remaining;
            } catch (BufferOverflowException e) {
                throw new OutOfSpaceException(e);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public void writeLazy(ByteBuffer byteBuffer) throws IOException {
            write(byteBuffer);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeStringNoTag(String str) throws IOException {
            long j = this.h;
            try {
                int computeUInt32SizeNoTag = computeUInt32SizeNoTag(str.length() * 3);
                int computeUInt32SizeNoTag2 = computeUInt32SizeNoTag(str.length());
                if (computeUInt32SizeNoTag2 == computeUInt32SizeNoTag) {
                    int b = b(this.h) + computeUInt32SizeNoTag2;
                    this.c.position(b);
                    au.a(str, this.c);
                    int position = this.c.position() - b;
                    writeUInt32NoTag(position);
                    this.h += position;
                } else {
                    int a = au.a(str);
                    writeUInt32NoTag(a);
                    a(this.h);
                    au.a(str, this.c);
                    this.h += a;
                }
            } catch (au.d e) {
                this.h = j;
                a(this.h);
                a(str, e);
            } catch (IllegalArgumentException e2) {
                throw new OutOfSpaceException(e2);
            } catch (IndexOutOfBoundsException e3) {
                throw new OutOfSpaceException(e3);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void flush() {
            this.b.position(b(this.h));
        }

        @Override // com.google.protobuf.CodedOutputStream
        public int spaceLeft() {
            return (int) (this.f - this.h);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public int getTotalBytesWritten() {
            return (int) (this.h - this.e);
        }

        private void a(long j) {
            this.c.position(b(j));
        }

        private int b(long j) {
            return (int) (j - this.d);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static abstract class a extends CodedOutputStream {
        final byte[] b;
        final int c;
        int d;
        int e;

        a(int i) {
            super();
            if (i >= 0) {
                this.b = new byte[Math.max(i, 20)];
                this.c = this.b.length;
                return;
            }
            throw new IllegalArgumentException("bufferSize must be >= 0");
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final int spaceLeft() {
            throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array or ByteBuffer.");
        }

        @Override // com.google.protobuf.CodedOutputStream
        public final int getTotalBytesWritten() {
            return this.e;
        }

        final void a(byte b) {
            byte[] bArr = this.b;
            int i = this.d;
            this.d = i + 1;
            bArr[i] = b;
            this.e++;
        }

        final void a(int i, int i2) {
            d(WireFormat.a(i, i2));
        }

        final void c(int i) {
            if (i >= 0) {
                d(i);
            } else {
                a(i);
            }
        }

        final void d(int i) {
            if (CodedOutputStream.c) {
                long j = this.d;
                while ((i & (-128)) != 0) {
                    byte[] bArr = this.b;
                    int i2 = this.d;
                    this.d = i2 + 1;
                    at.a(bArr, i2, (byte) ((i & 127) | 128));
                    i >>>= 7;
                }
                byte[] bArr2 = this.b;
                int i3 = this.d;
                this.d = i3 + 1;
                at.a(bArr2, i3, (byte) i);
                this.e += (int) (this.d - j);
                return;
            }
            while ((i & (-128)) != 0) {
                byte[] bArr3 = this.b;
                int i4 = this.d;
                this.d = i4 + 1;
                bArr3[i4] = (byte) ((i & 127) | 128);
                this.e++;
                i >>>= 7;
            }
            byte[] bArr4 = this.b;
            int i5 = this.d;
            this.d = i5 + 1;
            bArr4[i5] = (byte) i;
            this.e++;
        }

        final void a(long j) {
            if (CodedOutputStream.c) {
                long j2 = this.d;
                while ((j & (-128)) != 0) {
                    byte[] bArr = this.b;
                    int i = this.d;
                    this.d = i + 1;
                    at.a(bArr, i, (byte) ((((int) j) & 127) | 128));
                    j >>>= 7;
                }
                byte[] bArr2 = this.b;
                int i2 = this.d;
                this.d = i2 + 1;
                at.a(bArr2, i2, (byte) j);
                this.e += (int) (this.d - j2);
                return;
            }
            while ((j & (-128)) != 0) {
                byte[] bArr3 = this.b;
                int i3 = this.d;
                this.d = i3 + 1;
                bArr3[i3] = (byte) ((((int) j) & 127) | 128);
                this.e++;
                j >>>= 7;
            }
            byte[] bArr4 = this.b;
            int i4 = this.d;
            this.d = i4 + 1;
            bArr4[i4] = (byte) j;
            this.e++;
        }

        final void e(int i) {
            byte[] bArr = this.b;
            int i2 = this.d;
            this.d = i2 + 1;
            bArr[i2] = (byte) (i & 255);
            int i3 = this.d;
            this.d = i3 + 1;
            bArr[i3] = (byte) ((i >> 8) & 255);
            int i4 = this.d;
            this.d = i4 + 1;
            bArr[i4] = (byte) ((i >> 16) & 255);
            int i5 = this.d;
            this.d = i5 + 1;
            bArr[i5] = (byte) ((i >> 24) & 255);
            this.e += 4;
        }

        final void b(long j) {
            byte[] bArr = this.b;
            int i = this.d;
            this.d = i + 1;
            bArr[i] = (byte) (j & 255);
            int i2 = this.d;
            this.d = i2 + 1;
            bArr[i2] = (byte) ((j >> 8) & 255);
            int i3 = this.d;
            this.d = i3 + 1;
            bArr[i3] = (byte) ((j >> 16) & 255);
            int i4 = this.d;
            this.d = i4 + 1;
            bArr[i4] = (byte) (255 & (j >> 24));
            int i5 = this.d;
            this.d = i5 + 1;
            bArr[i5] = (byte) (((int) (j >> 32)) & 255);
            int i6 = this.d;
            this.d = i6 + 1;
            bArr[i6] = (byte) (((int) (j >> 40)) & 255);
            int i7 = this.d;
            this.d = i7 + 1;
            bArr[i7] = (byte) (((int) (j >> 48)) & 255);
            int i8 = this.d;
            this.d = i8 + 1;
            bArr[i8] = (byte) (((int) (j >> 56)) & 255);
            this.e += 8;
        }
    }

    /* loaded from: classes2.dex */
    public static final class d extends a {
        private final OutputStream f;

        d(OutputStream outputStream, int i) {
            super(i);
            if (outputStream != null) {
                this.f = outputStream;
                return;
            }
            throw new NullPointerException("out");
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeTag(int i, int i2) throws IOException {
            writeUInt32NoTag(WireFormat.a(i, i2));
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeInt32(int i, int i2) throws IOException {
            f(20);
            a(i, 0);
            c(i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeUInt32(int i, int i2) throws IOException {
            f(20);
            a(i, 0);
            d(i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeFixed32(int i, int i2) throws IOException {
            f(14);
            a(i, 5);
            e(i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeUInt64(int i, long j) throws IOException {
            f(20);
            a(i, 0);
            a(j);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeFixed64(int i, long j) throws IOException {
            f(18);
            a(i, 1);
            b(j);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeBool(int i, boolean z) throws IOException {
            f(11);
            a(i, 0);
            a(z ? (byte) 1 : (byte) 0);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeString(int i, String str) throws IOException {
            writeTag(i, 2);
            writeStringNoTag(str);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeBytes(int i, ByteString byteString) throws IOException {
            writeTag(i, 2);
            writeBytesNoTag(byteString);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeByteArray(int i, byte[] bArr) throws IOException {
            writeByteArray(i, bArr, 0, bArr.length);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeByteArray(int i, byte[] bArr, int i2, int i3) throws IOException {
            writeTag(i, 2);
            a(bArr, i2, i3);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeByteBuffer(int i, ByteBuffer byteBuffer) throws IOException {
            writeTag(i, 2);
            writeUInt32NoTag(byteBuffer.capacity());
            writeRawBytes(byteBuffer);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeBytesNoTag(ByteString byteString) throws IOException {
            writeUInt32NoTag(byteString.size());
            byteString.a(this);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void a(byte[] bArr, int i, int i2) throws IOException {
            writeUInt32NoTag(i2);
            write(bArr, i, i2);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeRawBytes(ByteBuffer byteBuffer) throws IOException {
            if (byteBuffer.hasArray()) {
                write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.capacity());
                return;
            }
            ByteBuffer duplicate = byteBuffer.duplicate();
            duplicate.clear();
            write(duplicate);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeMessage(int i, MessageLite messageLite) throws IOException {
            writeTag(i, 2);
            writeMessageNoTag(messageLite);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.protobuf.CodedOutputStream
        public void a(int i, MessageLite messageLite, am amVar) throws IOException {
            writeTag(i, 2);
            a(messageLite, amVar);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeMessageSetExtension(int i, MessageLite messageLite) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i);
            writeMessage(3, messageLite);
            writeTag(1, 4);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeRawMessageSetExtension(int i, ByteString byteString) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, i);
            writeBytes(3, byteString);
            writeTag(1, 4);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeMessageNoTag(MessageLite messageLite) throws IOException {
            writeUInt32NoTag(messageLite.getSerializedSize());
            messageLite.writeTo(this);
        }

        @Override // com.google.protobuf.CodedOutputStream
        void a(MessageLite messageLite, am amVar) throws IOException {
            writeUInt32NoTag(((AbstractMessageLite) messageLite).a(amVar));
            amVar.a((am) messageLite, (Writer) this.a);
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public void write(byte b) throws IOException {
            if (this.d == this.c) {
                c();
            }
            a(b);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeInt32NoTag(int i) throws IOException {
            if (i >= 0) {
                writeUInt32NoTag(i);
            } else {
                writeUInt64NoTag(i);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeUInt32NoTag(int i) throws IOException {
            f(5);
            d(i);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeFixed32NoTag(int i) throws IOException {
            f(4);
            e(i);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeUInt64NoTag(long j) throws IOException {
            f(10);
            a(j);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeFixed64NoTag(long j) throws IOException {
            f(8);
            b(j);
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void writeStringNoTag(String str) throws IOException {
            int i;
            try {
                int length = str.length() * 3;
                int computeUInt32SizeNoTag = computeUInt32SizeNoTag(length);
                int i2 = computeUInt32SizeNoTag + length;
                if (i2 > this.c) {
                    byte[] bArr = new byte[length];
                    int a = au.a(str, bArr, 0, length);
                    writeUInt32NoTag(a);
                    writeLazy(bArr, 0, a);
                    return;
                }
                if (i2 > this.c - this.d) {
                    c();
                }
                int computeUInt32SizeNoTag2 = computeUInt32SizeNoTag(str.length());
                int i3 = this.d;
                try {
                    if (computeUInt32SizeNoTag2 == computeUInt32SizeNoTag) {
                        this.d = i3 + computeUInt32SizeNoTag2;
                        int a2 = au.a(str, this.b, this.d, this.c - this.d);
                        this.d = i3;
                        i = (a2 - i3) - computeUInt32SizeNoTag2;
                        d(i);
                        this.d = a2;
                    } else {
                        i = au.a(str);
                        d(i);
                        this.d = au.a(str, this.b, this.d, i);
                    }
                    this.e += i;
                } catch (au.d e) {
                    this.e -= this.d - i3;
                    this.d = i3;
                    throw e;
                } catch (ArrayIndexOutOfBoundsException e2) {
                    throw new OutOfSpaceException(e2);
                }
            } catch (au.d e3) {
                a(str, e3);
            }
        }

        @Override // com.google.protobuf.CodedOutputStream
        public void flush() throws IOException {
            if (this.d > 0) {
                c();
            }
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public void write(byte[] bArr, int i, int i2) throws IOException {
            if (this.c - this.d >= i2) {
                System.arraycopy(bArr, i, this.b, this.d, i2);
                this.d += i2;
                this.e += i2;
                return;
            }
            int i3 = this.c - this.d;
            System.arraycopy(bArr, i, this.b, this.d, i3);
            int i4 = i + i3;
            int i5 = i2 - i3;
            this.d = this.c;
            this.e += i3;
            c();
            if (i5 <= this.c) {
                System.arraycopy(bArr, i4, this.b, 0, i5);
                this.d = i5;
            } else {
                this.f.write(bArr, i4, i5);
            }
            this.e += i5;
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public void writeLazy(byte[] bArr, int i, int i2) throws IOException {
            write(bArr, i, i2);
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public void write(ByteBuffer byteBuffer) throws IOException {
            int remaining = byteBuffer.remaining();
            if (this.c - this.d >= remaining) {
                byteBuffer.get(this.b, this.d, remaining);
                this.d += remaining;
                this.e += remaining;
                return;
            }
            int i = this.c - this.d;
            byteBuffer.get(this.b, this.d, i);
            int i2 = remaining - i;
            this.d = this.c;
            this.e += i;
            c();
            while (i2 > this.c) {
                byteBuffer.get(this.b, 0, this.c);
                this.f.write(this.b, 0, this.c);
                i2 -= this.c;
                this.e += this.c;
            }
            byteBuffer.get(this.b, 0, i2);
            this.d = i2;
            this.e += i2;
        }

        @Override // com.google.protobuf.CodedOutputStream, com.google.protobuf.ByteOutput
        public void writeLazy(ByteBuffer byteBuffer) throws IOException {
            write(byteBuffer);
        }

        private void f(int i) throws IOException {
            if (this.c - this.d < i) {
                c();
            }
        }

        private void c() throws IOException {
            this.f.write(this.b, 0, this.d);
            this.d = 0;
        }
    }
}
