package com.google.protobuf;

import com.google.protobuf.MessageLite;
import com.xiaomi.idm.service.iot.InputMethodService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class CodedInputStream {
    int a;
    int b;
    int c;
    f d;
    private boolean e;

    public static int decodeZigZag32(int i) {
        return (-(i & 1)) ^ (i >>> 1);
    }

    public static long decodeZigZag64(long j) {
        return (-(j & 1)) ^ (j >>> 1);
    }

    abstract long a() throws IOException;

    public abstract void checkLastTagWas(int i) throws InvalidProtocolBufferException;

    public abstract void enableAliasing(boolean z);

    public abstract int getBytesUntilLimit();

    public abstract int getLastTag();

    public abstract int getTotalBytesRead();

    public abstract boolean isAtEnd() throws IOException;

    public abstract void popLimit(int i);

    public abstract int pushLimit(int i) throws InvalidProtocolBufferException;

    public abstract boolean readBool() throws IOException;

    public abstract byte[] readByteArray() throws IOException;

    public abstract ByteBuffer readByteBuffer() throws IOException;

    public abstract ByteString readBytes() throws IOException;

    public abstract double readDouble() throws IOException;

    public abstract int readEnum() throws IOException;

    public abstract int readFixed32() throws IOException;

    public abstract long readFixed64() throws IOException;

    public abstract float readFloat() throws IOException;

    public abstract <T extends MessageLite> T readGroup(int i, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract void readGroup(int i, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract int readInt32() throws IOException;

    public abstract long readInt64() throws IOException;

    public abstract <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    public abstract byte readRawByte() throws IOException;

    public abstract byte[] readRawBytes(int i) throws IOException;

    public abstract int readRawLittleEndian32() throws IOException;

    public abstract long readRawLittleEndian64() throws IOException;

    public abstract int readRawVarint32() throws IOException;

    public abstract long readRawVarint64() throws IOException;

    public abstract int readSFixed32() throws IOException;

    public abstract long readSFixed64() throws IOException;

    public abstract int readSInt32() throws IOException;

    public abstract long readSInt64() throws IOException;

    public abstract String readString() throws IOException;

    public abstract String readStringRequireUtf8() throws IOException;

    public abstract int readTag() throws IOException;

    public abstract int readUInt32() throws IOException;

    public abstract long readUInt64() throws IOException;

    @Deprecated
    public abstract void readUnknownGroup(int i, MessageLite.Builder builder) throws IOException;

    public abstract void resetSizeCounter();

    public abstract boolean skipField(int i) throws IOException;

    @Deprecated
    public abstract boolean skipField(int i, CodedOutputStream codedOutputStream) throws IOException;

    public abstract void skipMessage() throws IOException;

    public abstract void skipMessage(CodedOutputStream codedOutputStream) throws IOException;

    public abstract void skipRawBytes(int i) throws IOException;

    public static CodedInputStream newInstance(InputStream inputStream) {
        return newInstance(inputStream, 4096);
    }

    public static CodedInputStream newInstance(InputStream inputStream, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("bufferSize must be > 0");
        } else if (inputStream == null) {
            return newInstance(Internal.EMPTY_BYTE_ARRAY);
        } else {
            return new c(inputStream, i);
        }
    }

    public static CodedInputStream newInstance(Iterable<ByteBuffer> iterable) {
        if (!d.b()) {
            return newInstance(new p(iterable));
        }
        return a(iterable, false);
    }

    static CodedInputStream a(Iterable<ByteBuffer> iterable, boolean z) {
        boolean z2 = false;
        int i = 0;
        for (ByteBuffer byteBuffer : iterable) {
            i += byteBuffer.remaining();
            if (byteBuffer.hasArray()) {
                z2 |= true;
            } else {
                z2 = byteBuffer.isDirect() ? z2 | true : z2 | true;
            }
        }
        if (z2) {
            return new b(iterable, i, z);
        }
        return newInstance(new p(iterable));
    }

    public static CodedInputStream newInstance(byte[] bArr) {
        return newInstance(bArr, 0, bArr.length);
    }

    public static CodedInputStream newInstance(byte[] bArr, int i, int i2) {
        return a(bArr, i, i2, false);
    }

    public static CodedInputStream a(byte[] bArr, int i, int i2, boolean z) {
        a aVar = new a(bArr, i, i2, z);
        try {
            aVar.pushLimit(i2);
            return aVar;
        } catch (InvalidProtocolBufferException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static CodedInputStream newInstance(ByteBuffer byteBuffer) {
        return a(byteBuffer, false);
    }

    public static CodedInputStream a(ByteBuffer byteBuffer, boolean z) {
        if (byteBuffer.hasArray()) {
            return a(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining(), z);
        }
        if (byteBuffer.isDirect() && d.b()) {
            return new d(byteBuffer, z);
        }
        byte[] bArr = new byte[byteBuffer.remaining()];
        byteBuffer.duplicate().get(bArr);
        return a(bArr, 0, bArr.length, true);
    }

    private CodedInputStream() {
        this.b = 100;
        this.c = Integer.MAX_VALUE;
        this.e = false;
    }

    public final int setRecursionLimit(int i) {
        if (i >= 0) {
            int i2 = this.b;
            this.b = i;
            return i2;
        }
        throw new IllegalArgumentException("Recursion limit cannot be negative: " + i);
    }

    public final int setSizeLimit(int i) {
        if (i >= 0) {
            int i2 = this.c;
            this.c = i;
            return i2;
        }
        throw new IllegalArgumentException("Size limit cannot be negative: " + i);
    }

    public static int readRawVarint32(int i, InputStream inputStream) throws IOException {
        if ((i & 128) == 0) {
            return i;
        }
        int i2 = i & 127;
        int i3 = 7;
        while (i3 < 32) {
            int read = inputStream.read();
            if (read != -1) {
                i2 |= (read & 127) << i3;
                if ((read & 128) == 0) {
                    return i2;
                }
                i3 += 7;
            } else {
                throw InvalidProtocolBufferException.a();
            }
        }
        while (i3 < 64) {
            int read2 = inputStream.read();
            if (read2 == -1) {
                throw InvalidProtocolBufferException.a();
            } else if ((read2 & 128) == 0) {
                return i2;
            } else {
                i3 += 7;
            }
        }
        throw InvalidProtocolBufferException.c();
    }

    /* loaded from: classes2.dex */
    public static final class a extends CodedInputStream {
        private final byte[] e;
        private final boolean f;
        private int g;
        private int h;
        private int i;
        private int j;
        private int k;
        private boolean l;
        private int m;

        private a(byte[] bArr, int i, int i2, boolean z) {
            super();
            this.m = Integer.MAX_VALUE;
            this.e = bArr;
            this.g = i2 + i;
            this.i = i;
            this.j = this.i;
            this.f = z;
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.k = 0;
                return 0;
            }
            this.k = readRawVarint32();
            if (WireFormat.getTagFieldNumber(this.k) != 0) {
                return this.k;
            }
            throw InvalidProtocolBufferException.d();
        }

        @Override // com.google.protobuf.CodedInputStream
        public void checkLastTagWas(int i) throws InvalidProtocolBufferException {
            if (this.k != i) {
                throw InvalidProtocolBufferException.e();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public int getLastTag() {
            return this.k;
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean skipField(int i) throws IOException {
            switch (WireFormat.getTagWireType(i)) {
                case 0:
                    b();
                    return true;
                case 1:
                    skipRawBytes(8);
                    return true;
                case 2:
                    skipRawBytes(readRawVarint32());
                    return true;
                case 3:
                    skipMessage();
                    checkLastTagWas(WireFormat.a(WireFormat.getTagFieldNumber(i), 4));
                    return true;
                case 4:
                    return false;
                case 5:
                    skipRawBytes(4);
                    return true;
                default:
                    throw InvalidProtocolBufferException.f();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean skipField(int i, CodedOutputStream codedOutputStream) throws IOException {
            switch (WireFormat.getTagWireType(i)) {
                case 0:
                    long readInt64 = readInt64();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeUInt64NoTag(readInt64);
                    return true;
                case 1:
                    long readRawLittleEndian64 = readRawLittleEndian64();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeFixed64NoTag(readRawLittleEndian64);
                    return true;
                case 2:
                    ByteString readBytes = readBytes();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeBytesNoTag(readBytes);
                    return true;
                case 3:
                    codedOutputStream.writeRawVarint32(i);
                    skipMessage(codedOutputStream);
                    int a = WireFormat.a(WireFormat.getTagFieldNumber(i), 4);
                    checkLastTagWas(a);
                    codedOutputStream.writeRawVarint32(a);
                    return true;
                case 4:
                    return false;
                case 5:
                    int readRawLittleEndian32 = readRawLittleEndian32();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeFixed32NoTag(readRawLittleEndian32);
                    return true;
                default:
                    throw InvalidProtocolBufferException.f();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public void skipMessage() throws IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag));
        }

        @Override // com.google.protobuf.CodedInputStream
        public void skipMessage(CodedOutputStream codedOutputStream) throws IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag, codedOutputStream));
        }

        @Override // com.google.protobuf.CodedInputStream
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // com.google.protobuf.CodedInputStream
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        @Override // com.google.protobuf.CodedInputStream
        public String readString() throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0) {
                int i = this.g;
                int i2 = this.i;
                if (readRawVarint32 <= i - i2) {
                    String str = new String(this.e, i2, readRawVarint32, Internal.a);
                    this.i += readRawVarint32;
                    return str;
                }
            }
            if (readRawVarint32 == 0) {
                return "";
            }
            if (readRawVarint32 < 0) {
                throw InvalidProtocolBufferException.b();
            }
            throw InvalidProtocolBufferException.a();
        }

        @Override // com.google.protobuf.CodedInputStream
        public String readStringRequireUtf8() throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0) {
                int i = this.g;
                int i2 = this.i;
                if (readRawVarint32 <= i - i2) {
                    String b = au.b(this.e, i2, readRawVarint32);
                    this.i += readRawVarint32;
                    return b;
                }
            }
            if (readRawVarint32 == 0) {
                return "";
            }
            if (readRawVarint32 <= 0) {
                throw InvalidProtocolBufferException.b();
            }
            throw InvalidProtocolBufferException.a();
        }

        @Override // com.google.protobuf.CodedInputStream
        public void readGroup(int i, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            if (this.a < this.b) {
                this.a++;
                builder.mergeFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.a(i, 4));
                this.a--;
                return;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        public <T extends MessageLite> T readGroup(int i, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            if (this.a < this.b) {
                this.a++;
                T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.a(i, 4));
                this.a--;
                return parsePartialFrom;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        @Deprecated
        public void readUnknownGroup(int i, MessageLite.Builder builder) throws IOException {
            readGroup(i, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // com.google.protobuf.CodedInputStream
        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (this.a < this.b) {
                int pushLimit = pushLimit(readRawVarint32);
                this.a++;
                builder.mergeFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.a--;
                popLimit(pushLimit);
                return;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (this.a < this.b) {
                int pushLimit = pushLimit(readRawVarint32);
                this.a++;
                T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.a--;
                popLimit(pushLimit);
                return parsePartialFrom;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        public ByteString readBytes() throws IOException {
            ByteString byteString;
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0) {
                int i = this.g;
                int i2 = this.i;
                if (readRawVarint32 <= i - i2) {
                    if (!this.f || !this.l) {
                        byteString = ByteString.copyFrom(this.e, this.i, readRawVarint32);
                    } else {
                        byteString = ByteString.a(this.e, i2, readRawVarint32);
                    }
                    this.i += readRawVarint32;
                    return byteString;
                }
            }
            if (readRawVarint32 == 0) {
                return ByteString.EMPTY;
            }
            return ByteString.a(readRawBytes(readRawVarint32));
        }

        @Override // com.google.protobuf.CodedInputStream
        public byte[] readByteArray() throws IOException {
            return readRawBytes(readRawVarint32());
        }

        @Override // com.google.protobuf.CodedInputStream
        public ByteBuffer readByteBuffer() throws IOException {
            ByteBuffer byteBuffer;
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0) {
                int i = this.g;
                int i2 = this.i;
                if (readRawVarint32 <= i - i2) {
                    if (this.f || !this.l) {
                        byte[] bArr = this.e;
                        int i3 = this.i;
                        byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(bArr, i3, i3 + readRawVarint32));
                    } else {
                        byteBuffer = ByteBuffer.wrap(this.e, i2, readRawVarint32).slice();
                    }
                    this.i += readRawVarint32;
                    return byteBuffer;
                }
            }
            if (readRawVarint32 == 0) {
                return Internal.EMPTY_BYTE_BUFFER;
            }
            if (readRawVarint32 < 0) {
                throw InvalidProtocolBufferException.b();
            }
            throw InvalidProtocolBufferException.a();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readSInt32() throws IOException {
            return decodeZigZag32(readRawVarint32());
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readSInt64() throws IOException {
            return decodeZigZag64(readRawVarint64());
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x0068, code lost:
            if (r2[r3] < 0) goto L_0x006a;
         */
        @Override // com.google.protobuf.CodedInputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int readRawVarint32() throws java.io.IOException {
            /*
                r5 = this;
                int r0 = r5.i
                int r1 = r5.g
                if (r1 != r0) goto L_0x0007
                goto L_0x006a
            L_0x0007:
                byte[] r2 = r5.e
                int r3 = r0 + 1
                byte r0 = r2[r0]
                if (r0 < 0) goto L_0x0012
                r5.i = r3
                return r0
            L_0x0012:
                int r1 = r1 - r3
                r4 = 9
                if (r1 >= r4) goto L_0x0018
                goto L_0x006a
            L_0x0018:
                int r1 = r3 + 1
                byte r3 = r2[r3]
                int r3 = r3 << 7
                r0 = r0 ^ r3
                if (r0 >= 0) goto L_0x0024
                r0 = r0 ^ (-128(0xffffffffffffff80, float:NaN))
                goto L_0x0071
            L_0x0024:
                int r3 = r1 + 1
                byte r1 = r2[r1]
                int r1 = r1 << 14
                r0 = r0 ^ r1
                if (r0 < 0) goto L_0x0031
                r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
                r1 = r3
                goto L_0x0071
            L_0x0031:
                int r1 = r3 + 1
                byte r3 = r2[r3]
                int r3 = r3 << 21
                r0 = r0 ^ r3
                if (r0 >= 0) goto L_0x003f
                r2 = -2080896(0xffffffffffe03f80, float:NaN)
                r0 = r0 ^ r2
                goto L_0x0071
            L_0x003f:
                int r3 = r1 + 1
                byte r1 = r2[r1]
                int r4 = r1 << 28
                r0 = r0 ^ r4
                r4 = 266354560(0xfe03f80, float:2.2112565E-29)
                r0 = r0 ^ r4
                if (r1 >= 0) goto L_0x0070
                int r1 = r3 + 1
                byte r3 = r2[r3]
                if (r3 >= 0) goto L_0x0071
                int r3 = r1 + 1
                byte r1 = r2[r1]
                if (r1 >= 0) goto L_0x0070
                int r1 = r3 + 1
                byte r3 = r2[r3]
                if (r3 >= 0) goto L_0x0071
                int r3 = r1 + 1
                byte r1 = r2[r1]
                if (r1 >= 0) goto L_0x0070
                int r1 = r3 + 1
                byte r2 = r2[r3]
                if (r2 >= 0) goto L_0x0071
            L_0x006a:
                long r0 = r5.a()
                int r0 = (int) r0
                return r0
            L_0x0070:
                r1 = r3
            L_0x0071:
                r5.i = r1
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.CodedInputStream.a.readRawVarint32():int");
        }

        private void b() throws IOException {
            if (this.g - this.i >= 10) {
                c();
            } else {
                d();
            }
        }

        private void c() throws IOException {
            for (int i = 0; i < 10; i++) {
                byte[] bArr = this.e;
                int i2 = this.i;
                this.i = i2 + 1;
                if (bArr[i2] >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.c();
        }

        private void d() throws IOException {
            for (int i = 0; i < 10; i++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.c();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readRawVarint64() throws IOException {
            long j;
            int i = this.i;
            int i2 = this.g;
            if (i2 != i) {
                byte[] bArr = this.e;
                int i3 = i + 1;
                byte b = bArr[i];
                if (b >= 0) {
                    this.i = i3;
                    return b;
                } else if (i2 - i3 >= 9) {
                    int i4 = i3 + 1;
                    int i5 = b ^ (bArr[i3] << 7);
                    if (i5 < 0) {
                        j = i5 ^ (-128);
                    } else {
                        int i6 = i4 + 1;
                        int i7 = i5 ^ (bArr[i4] << 14);
                        if (i7 >= 0) {
                            j = i7 ^ 16256;
                            i4 = i6;
                        } else {
                            i4 = i6 + 1;
                            int i8 = i7 ^ (bArr[i6] << 21);
                            if (i8 < 0) {
                                j = i8 ^ (-2080896);
                            } else {
                                long j2 = i8;
                                int i9 = i4 + 1;
                                long j3 = j2 ^ (bArr[i4] << 28);
                                if (j3 >= 0) {
                                    j = j3 ^ 266354560;
                                    i4 = i9;
                                } else {
                                    i4 = i9 + 1;
                                    long j4 = j3 ^ (bArr[i9] << 35);
                                    if (j4 < 0) {
                                        j = j4 ^ (-34093383808L);
                                    } else {
                                        int i10 = i4 + 1;
                                        long j5 = j4 ^ (bArr[i4] << 42);
                                        if (j5 >= 0) {
                                            j = j5 ^ 4363953127296L;
                                            i4 = i10;
                                        } else {
                                            i4 = i10 + 1;
                                            long j6 = j5 ^ (bArr[i10] << 49);
                                            if (j6 < 0) {
                                                j = j6 ^ (-558586000294016L);
                                            } else {
                                                int i11 = i4 + 1;
                                                long j7 = (j6 ^ (bArr[i4] << 56)) ^ 71499008037633920L;
                                                if (j7 < 0) {
                                                    i4 = i11 + 1;
                                                    if (bArr[i11] >= 0) {
                                                        j = j7;
                                                    }
                                                } else {
                                                    i4 = i11;
                                                    j = j7;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.i = i4;
                    return j;
                }
            }
            return a();
        }

        @Override // com.google.protobuf.CodedInputStream
        long a() throws IOException {
            long j = 0;
            for (int i = 0; i < 64; i += 7) {
                byte readRawByte = readRawByte();
                j |= (readRawByte & Byte.MAX_VALUE) << i;
                if ((readRawByte & 128) == 0) {
                    return j;
                }
            }
            throw InvalidProtocolBufferException.c();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws IOException {
            int i = this.i;
            if (this.g - i >= 4) {
                byte[] bArr = this.e;
                this.i = i + 4;
                return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
            }
            throw InvalidProtocolBufferException.a();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws IOException {
            int i = this.i;
            if (this.g - i >= 8) {
                byte[] bArr = this.e;
                this.i = i + 8;
                return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
            }
            throw InvalidProtocolBufferException.a();
        }

        @Override // com.google.protobuf.CodedInputStream
        public void enableAliasing(boolean z) {
            this.l = z;
        }

        @Override // com.google.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.j = this.i;
        }

        @Override // com.google.protobuf.CodedInputStream
        public int pushLimit(int i) throws InvalidProtocolBufferException {
            if (i >= 0) {
                int totalBytesRead = i + getTotalBytesRead();
                int i2 = this.m;
                if (totalBytesRead <= i2) {
                    this.m = totalBytesRead;
                    e();
                    return i2;
                }
                throw InvalidProtocolBufferException.a();
            }
            throw InvalidProtocolBufferException.b();
        }

        private void e() {
            this.g += this.h;
            int i = this.g;
            int i2 = i - this.j;
            int i3 = this.m;
            if (i2 > i3) {
                this.h = i2 - i3;
                this.g = i - this.h;
                return;
            }
            this.h = 0;
        }

        @Override // com.google.protobuf.CodedInputStream
        public void popLimit(int i) {
            this.m = i;
            e();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            int i = this.m;
            if (i == Integer.MAX_VALUE) {
                return -1;
            }
            return i - getTotalBytesRead();
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean isAtEnd() throws IOException {
            return this.i == this.g;
        }

        @Override // com.google.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return this.i - this.j;
        }

        @Override // com.google.protobuf.CodedInputStream
        public byte readRawByte() throws IOException {
            int i = this.i;
            if (i != this.g) {
                byte[] bArr = this.e;
                this.i = i + 1;
                return bArr[i];
            }
            throw InvalidProtocolBufferException.a();
        }

        @Override // com.google.protobuf.CodedInputStream
        public byte[] readRawBytes(int i) throws IOException {
            if (i > 0) {
                int i2 = this.g;
                int i3 = this.i;
                if (i <= i2 - i3) {
                    this.i = i + i3;
                    return Arrays.copyOfRange(this.e, i3, this.i);
                }
            }
            if (i > 0) {
                throw InvalidProtocolBufferException.a();
            } else if (i == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            } else {
                throw InvalidProtocolBufferException.b();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public void skipRawBytes(int i) throws IOException {
            if (i >= 0) {
                int i2 = this.g;
                int i3 = this.i;
                if (i <= i2 - i3) {
                    this.i = i3 + i;
                    return;
                }
            }
            if (i < 0) {
                throw InvalidProtocolBufferException.b();
            }
            throw InvalidProtocolBufferException.a();
        }
    }

    /* loaded from: classes2.dex */
    public static final class d extends CodedInputStream {
        private final ByteBuffer e;
        private final boolean f;
        private final long g;
        private long h;
        private long i;
        private long j;
        private int k;
        private int l;
        private boolean m;
        private int n;

        static boolean b() {
            return at.b();
        }

        private d(ByteBuffer byteBuffer, boolean z) {
            super();
            this.n = Integer.MAX_VALUE;
            this.e = byteBuffer;
            this.g = at.a(byteBuffer);
            this.h = this.g + byteBuffer.limit();
            this.i = this.g + byteBuffer.position();
            this.j = this.i;
            this.f = z;
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.l = 0;
                return 0;
            }
            this.l = readRawVarint32();
            if (WireFormat.getTagFieldNumber(this.l) != 0) {
                return this.l;
            }
            throw InvalidProtocolBufferException.d();
        }

        @Override // com.google.protobuf.CodedInputStream
        public void checkLastTagWas(int i) throws InvalidProtocolBufferException {
            if (this.l != i) {
                throw InvalidProtocolBufferException.e();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public int getLastTag() {
            return this.l;
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean skipField(int i) throws IOException {
            switch (WireFormat.getTagWireType(i)) {
                case 0:
                    c();
                    return true;
                case 1:
                    skipRawBytes(8);
                    return true;
                case 2:
                    skipRawBytes(readRawVarint32());
                    return true;
                case 3:
                    skipMessage();
                    checkLastTagWas(WireFormat.a(WireFormat.getTagFieldNumber(i), 4));
                    return true;
                case 4:
                    return false;
                case 5:
                    skipRawBytes(4);
                    return true;
                default:
                    throw InvalidProtocolBufferException.f();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean skipField(int i, CodedOutputStream codedOutputStream) throws IOException {
            switch (WireFormat.getTagWireType(i)) {
                case 0:
                    long readInt64 = readInt64();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeUInt64NoTag(readInt64);
                    return true;
                case 1:
                    long readRawLittleEndian64 = readRawLittleEndian64();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeFixed64NoTag(readRawLittleEndian64);
                    return true;
                case 2:
                    ByteString readBytes = readBytes();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeBytesNoTag(readBytes);
                    return true;
                case 3:
                    codedOutputStream.writeRawVarint32(i);
                    skipMessage(codedOutputStream);
                    int a = WireFormat.a(WireFormat.getTagFieldNumber(i), 4);
                    checkLastTagWas(a);
                    codedOutputStream.writeRawVarint32(a);
                    return true;
                case 4:
                    return false;
                case 5:
                    int readRawLittleEndian32 = readRawLittleEndian32();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeFixed32NoTag(readRawLittleEndian32);
                    return true;
                default:
                    throw InvalidProtocolBufferException.f();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public void skipMessage() throws IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag));
        }

        @Override // com.google.protobuf.CodedInputStream
        public void skipMessage(CodedOutputStream codedOutputStream) throws IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag, codedOutputStream));
        }

        @Override // com.google.protobuf.CodedInputStream
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // com.google.protobuf.CodedInputStream
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        @Override // com.google.protobuf.CodedInputStream
        public String readString() throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0 && readRawVarint32 <= g()) {
                byte[] bArr = new byte[readRawVarint32];
                long j = readRawVarint32;
                at.a(this.i, bArr, 0L, j);
                String str = new String(bArr, Internal.a);
                this.i += j;
                return str;
            } else if (readRawVarint32 == 0) {
                return "";
            } else {
                if (readRawVarint32 < 0) {
                    throw InvalidProtocolBufferException.b();
                }
                throw InvalidProtocolBufferException.a();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public String readStringRequireUtf8() throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0 && readRawVarint32 <= g()) {
                String a = au.a(this.e, a(this.i), readRawVarint32);
                this.i += readRawVarint32;
                return a;
            } else if (readRawVarint32 == 0) {
                return "";
            } else {
                if (readRawVarint32 <= 0) {
                    throw InvalidProtocolBufferException.b();
                }
                throw InvalidProtocolBufferException.a();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public void readGroup(int i, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            if (this.a < this.b) {
                this.a++;
                builder.mergeFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.a(i, 4));
                this.a--;
                return;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        public <T extends MessageLite> T readGroup(int i, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            if (this.a < this.b) {
                this.a++;
                T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.a(i, 4));
                this.a--;
                return parsePartialFrom;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        @Deprecated
        public void readUnknownGroup(int i, MessageLite.Builder builder) throws IOException {
            readGroup(i, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // com.google.protobuf.CodedInputStream
        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (this.a < this.b) {
                int pushLimit = pushLimit(readRawVarint32);
                this.a++;
                builder.mergeFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.a--;
                popLimit(pushLimit);
                return;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (this.a < this.b) {
                int pushLimit = pushLimit(readRawVarint32);
                this.a++;
                T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.a--;
                popLimit(pushLimit);
                return parsePartialFrom;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        public ByteString readBytes() throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 <= 0 || readRawVarint32 > g()) {
                if (readRawVarint32 == 0) {
                    return ByteString.EMPTY;
                }
                if (readRawVarint32 < 0) {
                    throw InvalidProtocolBufferException.b();
                }
                throw InvalidProtocolBufferException.a();
            } else if (!this.f || !this.m) {
                byte[] bArr = new byte[readRawVarint32];
                long j = readRawVarint32;
                at.a(this.i, bArr, 0L, j);
                this.i += j;
                return ByteString.a(bArr);
            } else {
                long j2 = this.i;
                long j3 = readRawVarint32;
                ByteBuffer a = a(j2, j2 + j3);
                this.i += j3;
                return ByteString.a(a);
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public byte[] readByteArray() throws IOException {
            return readRawBytes(readRawVarint32());
        }

        @Override // com.google.protobuf.CodedInputStream
        public ByteBuffer readByteBuffer() throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 <= 0 || readRawVarint32 > g()) {
                if (readRawVarint32 == 0) {
                    return Internal.EMPTY_BYTE_BUFFER;
                }
                if (readRawVarint32 < 0) {
                    throw InvalidProtocolBufferException.b();
                }
                throw InvalidProtocolBufferException.a();
            } else if (this.f || !this.m) {
                byte[] bArr = new byte[readRawVarint32];
                long j = readRawVarint32;
                at.a(this.i, bArr, 0L, j);
                this.i += j;
                return ByteBuffer.wrap(bArr);
            } else {
                long j2 = this.i;
                long j3 = readRawVarint32;
                ByteBuffer a = a(j2, j2 + j3);
                this.i += j3;
                return a;
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readSInt32() throws IOException {
            return decodeZigZag32(readRawVarint32());
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readSInt64() throws IOException {
            return decodeZigZag64(readRawVarint64());
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x0083, code lost:
            if (com.google.protobuf.at.a(r4) < 0) goto L_0x0085;
         */
        @Override // com.google.protobuf.CodedInputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int readRawVarint32() throws java.io.IOException {
            /*
                r10 = this;
                long r0 = r10.i
                long r2 = r10.h
                int r2 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
                if (r2 != 0) goto L_0x000a
                goto L_0x0085
            L_0x000a:
                r2 = 1
                long r4 = r0 + r2
                byte r0 = com.google.protobuf.at.a(r0)
                if (r0 < 0) goto L_0x0017
                r10.i = r4
                return r0
            L_0x0017:
                long r6 = r10.h
                long r6 = r6 - r4
                r8 = 9
                int r1 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r1 >= 0) goto L_0x0021
                goto L_0x0085
            L_0x0021:
                long r6 = r4 + r2
                byte r1 = com.google.protobuf.at.a(r4)
                int r1 = r1 << 7
                r0 = r0 ^ r1
                if (r0 >= 0) goto L_0x002f
                r0 = r0 ^ (-128(0xffffffffffffff80, float:NaN))
                goto L_0x0090
            L_0x002f:
                long r4 = r6 + r2
                byte r1 = com.google.protobuf.at.a(r6)
                int r1 = r1 << 14
                r0 = r0 ^ r1
                if (r0 < 0) goto L_0x003e
                r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
                r6 = r4
                goto L_0x0090
            L_0x003e:
                long r6 = r4 + r2
                byte r1 = com.google.protobuf.at.a(r4)
                int r1 = r1 << 21
                r0 = r0 ^ r1
                if (r0 >= 0) goto L_0x004e
                r1 = -2080896(0xffffffffffe03f80, float:NaN)
                r0 = r0 ^ r1
                goto L_0x0090
            L_0x004e:
                long r4 = r6 + r2
                byte r1 = com.google.protobuf.at.a(r6)
                int r6 = r1 << 28
                r0 = r0 ^ r6
                r6 = 266354560(0xfe03f80, float:2.2112565E-29)
                r0 = r0 ^ r6
                if (r1 >= 0) goto L_0x008f
                long r6 = r4 + r2
                byte r1 = com.google.protobuf.at.a(r4)
                if (r1 >= 0) goto L_0x0090
                long r4 = r6 + r2
                byte r1 = com.google.protobuf.at.a(r6)
                if (r1 >= 0) goto L_0x008d
                long r6 = r4 + r2
                byte r1 = com.google.protobuf.at.a(r4)
                if (r1 >= 0) goto L_0x0090
                long r4 = r6 + r2
                byte r1 = com.google.protobuf.at.a(r6)
                if (r1 >= 0) goto L_0x008b
                long r6 = r4 + r2
                byte r1 = com.google.protobuf.at.a(r4)
                if (r1 >= 0) goto L_0x0090
            L_0x0085:
                long r0 = r10.a()
                int r0 = (int) r0
                return r0
            L_0x008b:
                r6 = r4
                goto L_0x0090
            L_0x008d:
                r6 = r4
                goto L_0x0090
            L_0x008f:
                r6 = r4
            L_0x0090:
                r10.i = r6
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.CodedInputStream.d.readRawVarint32():int");
        }

        private void c() throws IOException {
            if (g() >= 10) {
                d();
            } else {
                e();
            }
        }

        private void d() throws IOException {
            for (int i = 0; i < 10; i++) {
                long j = this.i;
                this.i = 1 + j;
                if (at.a(j) >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.c();
        }

        private void e() throws IOException {
            for (int i = 0; i < 10; i++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.c();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readRawVarint64() throws IOException {
            long j;
            long j2;
            long j3 = this.i;
            if (this.h != j3) {
                long j4 = j3 + 1;
                byte a = at.a(j3);
                if (a >= 0) {
                    this.i = j4;
                    return a;
                } else if (this.h - j4 >= 9) {
                    long j5 = j4 + 1;
                    int a2 = a ^ (at.a(j4) << 7);
                    if (a2 < 0) {
                        j2 = a2 ^ (-128);
                        j = j5;
                    } else {
                        j = j5 + 1;
                        int a3 = a2 ^ (at.a(j5) << 14);
                        if (a3 >= 0) {
                            j2 = a3 ^ 16256;
                        } else {
                            long j6 = j + 1;
                            int a4 = a3 ^ (at.a(j) << 21);
                            if (a4 < 0) {
                                j2 = a4 ^ (-2080896);
                                j = j6;
                            } else {
                                j = j6 + 1;
                                long a5 = a4 ^ (at.a(j6) << 28);
                                if (a5 >= 0) {
                                    j2 = a5 ^ 266354560;
                                } else {
                                    long j7 = j + 1;
                                    long a6 = a5 ^ (at.a(j) << 35);
                                    if (a6 < 0) {
                                        j2 = a6 ^ (-34093383808L);
                                        j = j7;
                                    } else {
                                        j = j7 + 1;
                                        long a7 = a6 ^ (at.a(j7) << 42);
                                        if (a7 >= 0) {
                                            j2 = a7 ^ 4363953127296L;
                                        } else {
                                            long j8 = j + 1;
                                            long a8 = a7 ^ (at.a(j) << 49);
                                            if (a8 < 0) {
                                                j2 = a8 ^ (-558586000294016L);
                                                j = j8;
                                            } else {
                                                j = j8 + 1;
                                                j2 = (a8 ^ (at.a(j8) << 56)) ^ 71499008037633920L;
                                                if (j2 < 0) {
                                                    j = 1 + j;
                                                    if (at.a(j) >= 0) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.i = j;
                    return j2;
                }
            }
            return a();
        }

        @Override // com.google.protobuf.CodedInputStream
        long a() throws IOException {
            long j = 0;
            for (int i = 0; i < 64; i += 7) {
                byte readRawByte = readRawByte();
                j |= (readRawByte & Byte.MAX_VALUE) << i;
                if ((readRawByte & 128) == 0) {
                    return j;
                }
            }
            throw InvalidProtocolBufferException.c();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws IOException {
            long j = this.i;
            if (this.h - j >= 4) {
                this.i = 4 + j;
                return ((at.a(j + 3) & 255) << 24) | (at.a(j) & 255) | ((at.a(1 + j) & 255) << 8) | ((at.a(2 + j) & 255) << 16);
            }
            throw InvalidProtocolBufferException.a();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws IOException {
            long j = this.i;
            if (this.h - j >= 8) {
                this.i = 8 + j;
                return ((at.a(j + 7) & 255) << 56) | (at.a(j) & 255) | ((at.a(1 + j) & 255) << 8) | ((at.a(2 + j) & 255) << 16) | ((at.a(3 + j) & 255) << 24) | ((at.a(4 + j) & 255) << 32) | ((at.a(5 + j) & 255) << 40) | ((at.a(6 + j) & 255) << 48);
            }
            throw InvalidProtocolBufferException.a();
        }

        @Override // com.google.protobuf.CodedInputStream
        public void enableAliasing(boolean z) {
            this.m = z;
        }

        @Override // com.google.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.j = this.i;
        }

        @Override // com.google.protobuf.CodedInputStream
        public int pushLimit(int i) throws InvalidProtocolBufferException {
            if (i >= 0) {
                int totalBytesRead = i + getTotalBytesRead();
                int i2 = this.n;
                if (totalBytesRead <= i2) {
                    this.n = totalBytesRead;
                    f();
                    return i2;
                }
                throw InvalidProtocolBufferException.a();
            }
            throw InvalidProtocolBufferException.b();
        }

        @Override // com.google.protobuf.CodedInputStream
        public void popLimit(int i) {
            this.n = i;
            f();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            int i = this.n;
            if (i == Integer.MAX_VALUE) {
                return -1;
            }
            return i - getTotalBytesRead();
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean isAtEnd() throws IOException {
            return this.i == this.h;
        }

        @Override // com.google.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return (int) (this.i - this.j);
        }

        @Override // com.google.protobuf.CodedInputStream
        public byte readRawByte() throws IOException {
            long j = this.i;
            if (j != this.h) {
                this.i = 1 + j;
                return at.a(j);
            }
            throw InvalidProtocolBufferException.a();
        }

        @Override // com.google.protobuf.CodedInputStream
        public byte[] readRawBytes(int i) throws IOException {
            if (i >= 0 && i <= g()) {
                byte[] bArr = new byte[i];
                long j = this.i;
                long j2 = i;
                a(j, j + j2).get(bArr);
                this.i += j2;
                return bArr;
            } else if (i > 0) {
                throw InvalidProtocolBufferException.a();
            } else if (i == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            } else {
                throw InvalidProtocolBufferException.b();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public void skipRawBytes(int i) throws IOException {
            if (i >= 0 && i <= g()) {
                this.i += i;
            } else if (i < 0) {
                throw InvalidProtocolBufferException.b();
            } else {
                throw InvalidProtocolBufferException.a();
            }
        }

        private void f() {
            this.h += this.k;
            long j = this.h;
            int i = (int) (j - this.j);
            int i2 = this.n;
            if (i > i2) {
                this.k = i - i2;
                this.h = j - this.k;
                return;
            }
            this.k = 0;
        }

        private int g() {
            return (int) (this.h - this.i);
        }

        private int a(long j) {
            return (int) (j - this.g);
        }

        private ByteBuffer a(long j, long j2) throws IOException {
            int position = this.e.position();
            int limit = this.e.limit();
            try {
                try {
                    this.e.position(a(j));
                    this.e.limit(a(j2));
                    return this.e.slice();
                } catch (IllegalArgumentException unused) {
                    throw InvalidProtocolBufferException.a();
                }
            } finally {
                this.e.position(position);
                this.e.limit(limit);
            }
        }
    }

    /* loaded from: classes2.dex */
    public static final class c extends CodedInputStream {
        private final InputStream e;
        private final byte[] f;
        private int g;
        private int h;
        private int i;
        private int j;
        private int k;
        private int l;
        private a m;

        /* loaded from: classes2.dex */
        public interface a {
            void a();
        }

        @Override // com.google.protobuf.CodedInputStream
        public void enableAliasing(boolean z) {
        }

        private c(InputStream inputStream, int i) {
            super();
            this.l = Integer.MAX_VALUE;
            this.m = null;
            Internal.a(inputStream, InputMethodService.InputPropertyCommand.INPUT_SERVICE_DESC);
            this.e = inputStream;
            this.f = new byte[i];
            this.g = 0;
            this.i = 0;
            this.k = 0;
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.j = 0;
                return 0;
            }
            this.j = readRawVarint32();
            if (WireFormat.getTagFieldNumber(this.j) != 0) {
                return this.j;
            }
            throw InvalidProtocolBufferException.d();
        }

        @Override // com.google.protobuf.CodedInputStream
        public void checkLastTagWas(int i) throws InvalidProtocolBufferException {
            if (this.j != i) {
                throw InvalidProtocolBufferException.e();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public int getLastTag() {
            return this.j;
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean skipField(int i) throws IOException {
            switch (WireFormat.getTagWireType(i)) {
                case 0:
                    b();
                    return true;
                case 1:
                    skipRawBytes(8);
                    return true;
                case 2:
                    skipRawBytes(readRawVarint32());
                    return true;
                case 3:
                    skipMessage();
                    checkLastTagWas(WireFormat.a(WireFormat.getTagFieldNumber(i), 4));
                    return true;
                case 4:
                    return false;
                case 5:
                    skipRawBytes(4);
                    return true;
                default:
                    throw InvalidProtocolBufferException.f();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean skipField(int i, CodedOutputStream codedOutputStream) throws IOException {
            switch (WireFormat.getTagWireType(i)) {
                case 0:
                    long readInt64 = readInt64();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeUInt64NoTag(readInt64);
                    return true;
                case 1:
                    long readRawLittleEndian64 = readRawLittleEndian64();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeFixed64NoTag(readRawLittleEndian64);
                    return true;
                case 2:
                    ByteString readBytes = readBytes();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeBytesNoTag(readBytes);
                    return true;
                case 3:
                    codedOutputStream.writeRawVarint32(i);
                    skipMessage(codedOutputStream);
                    int a2 = WireFormat.a(WireFormat.getTagFieldNumber(i), 4);
                    checkLastTagWas(a2);
                    codedOutputStream.writeRawVarint32(a2);
                    return true;
                case 4:
                    return false;
                case 5:
                    int readRawLittleEndian32 = readRawLittleEndian32();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeFixed32NoTag(readRawLittleEndian32);
                    return true;
                default:
                    throw InvalidProtocolBufferException.f();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public void skipMessage() throws IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag));
        }

        @Override // com.google.protobuf.CodedInputStream
        public void skipMessage(CodedOutputStream codedOutputStream) throws IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag, codedOutputStream));
        }

        @Override // com.google.protobuf.CodedInputStream
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // com.google.protobuf.CodedInputStream
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        @Override // com.google.protobuf.CodedInputStream
        public String readString() throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0) {
                int i = this.g;
                int i2 = this.i;
                if (readRawVarint32 <= i - i2) {
                    String str = new String(this.f, i2, readRawVarint32, Internal.a);
                    this.i += readRawVarint32;
                    return str;
                }
            }
            if (readRawVarint32 == 0) {
                return "";
            }
            if (readRawVarint32 > this.g) {
                return new String(a(readRawVarint32, false), Internal.a);
            }
            a(readRawVarint32);
            String str2 = new String(this.f, this.i, readRawVarint32, Internal.a);
            this.i += readRawVarint32;
            return str2;
        }

        @Override // com.google.protobuf.CodedInputStream
        public String readStringRequireUtf8() throws IOException {
            byte[] bArr;
            int readRawVarint32 = readRawVarint32();
            int i = this.i;
            int i2 = 0;
            if (readRawVarint32 <= this.g - i && readRawVarint32 > 0) {
                bArr = this.f;
                this.i = i + readRawVarint32;
                i2 = i;
            } else if (readRawVarint32 == 0) {
                return "";
            } else {
                if (readRawVarint32 <= this.g) {
                    a(readRawVarint32);
                    bArr = this.f;
                    this.i = readRawVarint32 + 0;
                } else {
                    bArr = a(readRawVarint32, false);
                }
            }
            return au.b(bArr, i2, readRawVarint32);
        }

        @Override // com.google.protobuf.CodedInputStream
        public void readGroup(int i, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            if (this.a < this.b) {
                this.a++;
                builder.mergeFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.a(i, 4));
                this.a--;
                return;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        public <T extends MessageLite> T readGroup(int i, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            if (this.a < this.b) {
                this.a++;
                T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.a(i, 4));
                this.a--;
                return parsePartialFrom;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        @Deprecated
        public void readUnknownGroup(int i, MessageLite.Builder builder) throws IOException {
            readGroup(i, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // com.google.protobuf.CodedInputStream
        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (this.a < this.b) {
                int pushLimit = pushLimit(readRawVarint32);
                this.a++;
                builder.mergeFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.a--;
                popLimit(pushLimit);
                return;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (this.a < this.b) {
                int pushLimit = pushLimit(readRawVarint32);
                this.a++;
                T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.a--;
                popLimit(pushLimit);
                return parsePartialFrom;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        public ByteString readBytes() throws IOException {
            int readRawVarint32 = readRawVarint32();
            int i = this.g;
            int i2 = this.i;
            if (readRawVarint32 <= i - i2 && readRawVarint32 > 0) {
                ByteString copyFrom = ByteString.copyFrom(this.f, i2, readRawVarint32);
                this.i += readRawVarint32;
                return copyFrom;
            } else if (readRawVarint32 == 0) {
                return ByteString.EMPTY;
            } else {
                return e(readRawVarint32);
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public byte[] readByteArray() throws IOException {
            int readRawVarint32 = readRawVarint32();
            int i = this.g;
            int i2 = this.i;
            if (readRawVarint32 > i - i2 || readRawVarint32 <= 0) {
                return a(readRawVarint32, false);
            }
            byte[] copyOfRange = Arrays.copyOfRange(this.f, i2, i2 + readRawVarint32);
            this.i += readRawVarint32;
            return copyOfRange;
        }

        @Override // com.google.protobuf.CodedInputStream
        public ByteBuffer readByteBuffer() throws IOException {
            int readRawVarint32 = readRawVarint32();
            int i = this.g;
            int i2 = this.i;
            if (readRawVarint32 <= i - i2 && readRawVarint32 > 0) {
                ByteBuffer wrap = ByteBuffer.wrap(Arrays.copyOfRange(this.f, i2, i2 + readRawVarint32));
                this.i += readRawVarint32;
                return wrap;
            } else if (readRawVarint32 == 0) {
                return Internal.EMPTY_BYTE_BUFFER;
            } else {
                return ByteBuffer.wrap(a(readRawVarint32, true));
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readSInt32() throws IOException {
            return decodeZigZag32(readRawVarint32());
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readSInt64() throws IOException {
            return decodeZigZag64(readRawVarint64());
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x0068, code lost:
            if (r2[r3] < 0) goto L_0x006a;
         */
        @Override // com.google.protobuf.CodedInputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int readRawVarint32() throws java.io.IOException {
            /*
                r5 = this;
                int r0 = r5.i
                int r1 = r5.g
                if (r1 != r0) goto L_0x0007
                goto L_0x006a
            L_0x0007:
                byte[] r2 = r5.f
                int r3 = r0 + 1
                byte r0 = r2[r0]
                if (r0 < 0) goto L_0x0012
                r5.i = r3
                return r0
            L_0x0012:
                int r1 = r1 - r3
                r4 = 9
                if (r1 >= r4) goto L_0x0018
                goto L_0x006a
            L_0x0018:
                int r1 = r3 + 1
                byte r3 = r2[r3]
                int r3 = r3 << 7
                r0 = r0 ^ r3
                if (r0 >= 0) goto L_0x0024
                r0 = r0 ^ (-128(0xffffffffffffff80, float:NaN))
                goto L_0x0071
            L_0x0024:
                int r3 = r1 + 1
                byte r1 = r2[r1]
                int r1 = r1 << 14
                r0 = r0 ^ r1
                if (r0 < 0) goto L_0x0031
                r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
                r1 = r3
                goto L_0x0071
            L_0x0031:
                int r1 = r3 + 1
                byte r3 = r2[r3]
                int r3 = r3 << 21
                r0 = r0 ^ r3
                if (r0 >= 0) goto L_0x003f
                r2 = -2080896(0xffffffffffe03f80, float:NaN)
                r0 = r0 ^ r2
                goto L_0x0071
            L_0x003f:
                int r3 = r1 + 1
                byte r1 = r2[r1]
                int r4 = r1 << 28
                r0 = r0 ^ r4
                r4 = 266354560(0xfe03f80, float:2.2112565E-29)
                r0 = r0 ^ r4
                if (r1 >= 0) goto L_0x0070
                int r1 = r3 + 1
                byte r3 = r2[r3]
                if (r3 >= 0) goto L_0x0071
                int r3 = r1 + 1
                byte r1 = r2[r1]
                if (r1 >= 0) goto L_0x0070
                int r1 = r3 + 1
                byte r3 = r2[r3]
                if (r3 >= 0) goto L_0x0071
                int r3 = r1 + 1
                byte r1 = r2[r1]
                if (r1 >= 0) goto L_0x0070
                int r1 = r3 + 1
                byte r2 = r2[r3]
                if (r2 >= 0) goto L_0x0071
            L_0x006a:
                long r0 = r5.a()
                int r0 = (int) r0
                return r0
            L_0x0070:
                r1 = r3
            L_0x0071:
                r5.i = r1
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.CodedInputStream.c.readRawVarint32():int");
        }

        private void b() throws IOException {
            if (this.g - this.i >= 10) {
                c();
            } else {
                d();
            }
        }

        private void c() throws IOException {
            for (int i = 0; i < 10; i++) {
                byte[] bArr = this.f;
                int i2 = this.i;
                this.i = i2 + 1;
                if (bArr[i2] >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.c();
        }

        private void d() throws IOException {
            for (int i = 0; i < 10; i++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.c();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readRawVarint64() throws IOException {
            long j;
            int i = this.i;
            int i2 = this.g;
            if (i2 != i) {
                byte[] bArr = this.f;
                int i3 = i + 1;
                byte b = bArr[i];
                if (b >= 0) {
                    this.i = i3;
                    return b;
                } else if (i2 - i3 >= 9) {
                    int i4 = i3 + 1;
                    int i5 = b ^ (bArr[i3] << 7);
                    if (i5 < 0) {
                        j = i5 ^ (-128);
                    } else {
                        int i6 = i4 + 1;
                        int i7 = i5 ^ (bArr[i4] << 14);
                        if (i7 >= 0) {
                            j = i7 ^ 16256;
                            i4 = i6;
                        } else {
                            i4 = i6 + 1;
                            int i8 = i7 ^ (bArr[i6] << 21);
                            if (i8 < 0) {
                                j = i8 ^ (-2080896);
                            } else {
                                long j2 = i8;
                                int i9 = i4 + 1;
                                long j3 = j2 ^ (bArr[i4] << 28);
                                if (j3 >= 0) {
                                    j = j3 ^ 266354560;
                                    i4 = i9;
                                } else {
                                    i4 = i9 + 1;
                                    long j4 = j3 ^ (bArr[i9] << 35);
                                    if (j4 < 0) {
                                        j = j4 ^ (-34093383808L);
                                    } else {
                                        int i10 = i4 + 1;
                                        long j5 = j4 ^ (bArr[i4] << 42);
                                        if (j5 >= 0) {
                                            j = j5 ^ 4363953127296L;
                                            i4 = i10;
                                        } else {
                                            i4 = i10 + 1;
                                            long j6 = j5 ^ (bArr[i10] << 49);
                                            if (j6 < 0) {
                                                j = j6 ^ (-558586000294016L);
                                            } else {
                                                int i11 = i4 + 1;
                                                long j7 = (j6 ^ (bArr[i4] << 56)) ^ 71499008037633920L;
                                                if (j7 < 0) {
                                                    i4 = i11 + 1;
                                                    if (bArr[i11] >= 0) {
                                                        j = j7;
                                                    }
                                                } else {
                                                    i4 = i11;
                                                    j = j7;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.i = i4;
                    return j;
                }
            }
            return a();
        }

        @Override // com.google.protobuf.CodedInputStream
        long a() throws IOException {
            long j = 0;
            for (int i = 0; i < 64; i += 7) {
                byte readRawByte = readRawByte();
                j |= (readRawByte & Byte.MAX_VALUE) << i;
                if ((readRawByte & 128) == 0) {
                    return j;
                }
            }
            throw InvalidProtocolBufferException.c();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws IOException {
            int i = this.i;
            if (this.g - i < 4) {
                a(4);
                i = this.i;
            }
            byte[] bArr = this.f;
            this.i = i + 4;
            return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws IOException {
            int i = this.i;
            if (this.g - i < 8) {
                a(8);
                i = this.i;
            }
            byte[] bArr = this.f;
            this.i = i + 8;
            return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
        }

        @Override // com.google.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.k = -this.i;
        }

        @Override // com.google.protobuf.CodedInputStream
        public int pushLimit(int i) throws InvalidProtocolBufferException {
            if (i >= 0) {
                int i2 = i + this.k + this.i;
                int i3 = this.l;
                if (i2 <= i3) {
                    this.l = i2;
                    e();
                    return i3;
                }
                throw InvalidProtocolBufferException.a();
            }
            throw InvalidProtocolBufferException.b();
        }

        private void e() {
            this.g += this.h;
            int i = this.k;
            int i2 = this.g;
            int i3 = i + i2;
            int i4 = this.l;
            if (i3 > i4) {
                this.h = i3 - i4;
                this.g = i2 - this.h;
                return;
            }
            this.h = 0;
        }

        @Override // com.google.protobuf.CodedInputStream
        public void popLimit(int i) {
            this.l = i;
            e();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            int i = this.l;
            if (i == Integer.MAX_VALUE) {
                return -1;
            }
            return i - (this.k + this.i);
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean isAtEnd() throws IOException {
            return this.i == this.g && !b(1);
        }

        @Override // com.google.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return this.k + this.i;
        }

        private void a(int i) throws IOException {
            if (b(i)) {
                return;
            }
            if (i > (this.c - this.k) - this.i) {
                throw InvalidProtocolBufferException.h();
            }
            throw InvalidProtocolBufferException.a();
        }

        private boolean b(int i) throws IOException {
            if (this.i + i > this.g) {
                int i2 = this.c;
                int i3 = this.k;
                int i4 = this.i;
                if (i > (i2 - i3) - i4 || i3 + i4 + i > this.l) {
                    return false;
                }
                a aVar = this.m;
                if (aVar != null) {
                    aVar.a();
                }
                int i5 = this.i;
                if (i5 > 0) {
                    int i6 = this.g;
                    if (i6 > i5) {
                        byte[] bArr = this.f;
                        System.arraycopy(bArr, i5, bArr, 0, i6 - i5);
                    }
                    this.k += i5;
                    this.g -= i5;
                    this.i = 0;
                }
                InputStream inputStream = this.e;
                byte[] bArr2 = this.f;
                int i7 = this.g;
                int read = inputStream.read(bArr2, i7, Math.min(bArr2.length - i7, (this.c - this.k) - this.g));
                if (read == 0 || read < -1 || read > this.f.length) {
                    throw new IllegalStateException(this.e.getClass() + "#read(byte[]) returned invalid result: " + read + "\nThe InputStream implementation is buggy.");
                } else if (read <= 0) {
                    return false;
                } else {
                    this.g += read;
                    e();
                    if (this.g >= i) {
                        return true;
                    }
                    return b(i);
                }
            } else {
                throw new IllegalStateException("refillBuffer() called when " + i + " bytes were already available in buffer");
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public byte readRawByte() throws IOException {
            if (this.i == this.g) {
                a(1);
            }
            byte[] bArr = this.f;
            int i = this.i;
            this.i = i + 1;
            return bArr[i];
        }

        @Override // com.google.protobuf.CodedInputStream
        public byte[] readRawBytes(int i) throws IOException {
            int i2 = this.i;
            if (i > this.g - i2 || i <= 0) {
                return a(i, false);
            }
            int i3 = i + i2;
            this.i = i3;
            return Arrays.copyOfRange(this.f, i2, i3);
        }

        private byte[] a(int i, boolean z) throws IOException {
            byte[] c = c(i);
            if (c != null) {
                return z ? (byte[]) c.clone() : c;
            }
            int i2 = this.i;
            int i3 = this.g;
            int i4 = i3 - i2;
            this.k += i3;
            this.i = 0;
            this.g = 0;
            List<byte[]> d = d(i - i4);
            byte[] bArr = new byte[i];
            System.arraycopy(this.f, i2, bArr, 0, i4);
            for (byte[] bArr2 : d) {
                System.arraycopy(bArr2, 0, bArr, i4, bArr2.length);
                i4 += bArr2.length;
            }
            return bArr;
        }

        private byte[] c(int i) throws IOException {
            if (i == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            }
            if (i >= 0) {
                int i2 = this.k + this.i + i;
                if (i2 - this.c <= 0) {
                    int i3 = this.l;
                    if (i2 <= i3) {
                        int i4 = this.g - this.i;
                        int i5 = i - i4;
                        if (i5 >= 4096 && i5 > this.e.available()) {
                            return null;
                        }
                        byte[] bArr = new byte[i];
                        System.arraycopy(this.f, this.i, bArr, 0, i4);
                        this.k += this.g;
                        this.i = 0;
                        this.g = 0;
                        while (i4 < bArr.length) {
                            int read = this.e.read(bArr, i4, i - i4);
                            if (read != -1) {
                                this.k += read;
                                i4 += read;
                            } else {
                                throw InvalidProtocolBufferException.a();
                            }
                        }
                        return bArr;
                    }
                    skipRawBytes((i3 - this.k) - this.i);
                    throw InvalidProtocolBufferException.a();
                }
                throw InvalidProtocolBufferException.h();
            }
            throw InvalidProtocolBufferException.b();
        }

        private List<byte[]> d(int i) throws IOException {
            ArrayList arrayList = new ArrayList();
            while (i > 0) {
                byte[] bArr = new byte[Math.min(i, 4096)];
                int i2 = 0;
                while (i2 < bArr.length) {
                    int read = this.e.read(bArr, i2, bArr.length - i2);
                    if (read != -1) {
                        this.k += read;
                        i2 += read;
                    } else {
                        throw InvalidProtocolBufferException.a();
                    }
                }
                i -= bArr.length;
                arrayList.add(bArr);
            }
            return arrayList;
        }

        private ByteString e(int i) throws IOException {
            byte[] c = c(i);
            if (c != null) {
                return ByteString.copyFrom(c);
            }
            int i2 = this.i;
            int i3 = this.g;
            int i4 = i3 - i2;
            this.k += i3;
            this.i = 0;
            this.g = 0;
            List<byte[]> d = d(i - i4);
            byte[] bArr = new byte[i];
            System.arraycopy(this.f, i2, bArr, 0, i4);
            for (byte[] bArr2 : d) {
                System.arraycopy(bArr2, 0, bArr, i4, bArr2.length);
                i4 += bArr2.length;
            }
            return ByteString.a(bArr);
        }

        @Override // com.google.protobuf.CodedInputStream
        public void skipRawBytes(int i) throws IOException {
            int i2 = this.g;
            int i3 = this.i;
            if (i > i2 - i3 || i < 0) {
                f(i);
            } else {
                this.i = i3 + i;
            }
        }

        private void f(int i) throws IOException {
            if (i >= 0) {
                int i2 = this.k;
                int i3 = this.i;
                int i4 = i2 + i3 + i;
                int i5 = this.l;
                if (i4 <= i5) {
                    int i6 = 0;
                    if (this.m == null) {
                        this.k = i2 + i3;
                        i6 = this.g - i3;
                        this.g = 0;
                        this.i = 0;
                        while (i6 < i) {
                            try {
                                long j = i - i6;
                                long skip = this.e.skip(j);
                                int i7 = (skip > 0L ? 1 : (skip == 0L ? 0 : -1));
                                if (i7 < 0 || skip > j) {
                                    throw new IllegalStateException(this.e.getClass() + "#skip returned invalid result: " + skip + "\nThe InputStream implementation is buggy.");
                                } else if (i7 == 0) {
                                    break;
                                } else {
                                    i6 += (int) skip;
                                }
                            } finally {
                                this.k += i6;
                                e();
                            }
                        }
                    }
                    if (i6 < i) {
                        int i8 = this.g;
                        int i9 = i8 - this.i;
                        this.i = i8;
                        a(1);
                        while (true) {
                            int i10 = i - i9;
                            int i11 = this.g;
                            if (i10 > i11) {
                                i9 += i11;
                                this.i = i11;
                                a(1);
                            } else {
                                this.i = i10;
                                return;
                            }
                        }
                    }
                } else {
                    skipRawBytes((i5 - i2) - i3);
                    throw InvalidProtocolBufferException.a();
                }
            } else {
                throw InvalidProtocolBufferException.b();
            }
        }
    }

    /* loaded from: classes2.dex */
    public static final class b extends CodedInputStream {
        private Iterable<ByteBuffer> e;
        private Iterator<ByteBuffer> f;
        private ByteBuffer g;
        private boolean h;
        private boolean i;
        private int j;
        private int k;
        private int l;
        private int m;
        private int n;
        private int o;
        private long p;
        private long q;
        private long r;
        private long s;

        private b(Iterable<ByteBuffer> iterable, int i, boolean z) {
            super();
            this.l = Integer.MAX_VALUE;
            this.j = i;
            this.e = iterable;
            this.f = this.e.iterator();
            this.h = z;
            this.n = 0;
            this.o = 0;
            if (i == 0) {
                this.g = Internal.EMPTY_BYTE_BUFFER;
                this.p = 0L;
                this.q = 0L;
                this.s = 0L;
                this.r = 0L;
                return;
            }
            c();
        }

        private void b() throws InvalidProtocolBufferException {
            if (this.f.hasNext()) {
                c();
                return;
            }
            throw InvalidProtocolBufferException.a();
        }

        private void c() {
            this.g = this.f.next();
            this.n += (int) (this.p - this.q);
            this.p = this.g.position();
            this.q = this.p;
            this.s = this.g.limit();
            this.r = at.a(this.g);
            long j = this.p;
            long j2 = this.r;
            this.p = j + j2;
            this.q += j2;
            this.s += j2;
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readTag() throws IOException {
            if (isAtEnd()) {
                this.m = 0;
                return 0;
            }
            this.m = readRawVarint32();
            if (WireFormat.getTagFieldNumber(this.m) != 0) {
                return this.m;
            }
            throw InvalidProtocolBufferException.d();
        }

        @Override // com.google.protobuf.CodedInputStream
        public void checkLastTagWas(int i) throws InvalidProtocolBufferException {
            if (this.m != i) {
                throw InvalidProtocolBufferException.e();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public int getLastTag() {
            return this.m;
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean skipField(int i) throws IOException {
            switch (WireFormat.getTagWireType(i)) {
                case 0:
                    e();
                    return true;
                case 1:
                    skipRawBytes(8);
                    return true;
                case 2:
                    skipRawBytes(readRawVarint32());
                    return true;
                case 3:
                    skipMessage();
                    checkLastTagWas(WireFormat.a(WireFormat.getTagFieldNumber(i), 4));
                    return true;
                case 4:
                    return false;
                case 5:
                    skipRawBytes(4);
                    return true;
                default:
                    throw InvalidProtocolBufferException.f();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean skipField(int i, CodedOutputStream codedOutputStream) throws IOException {
            switch (WireFormat.getTagWireType(i)) {
                case 0:
                    long readInt64 = readInt64();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeUInt64NoTag(readInt64);
                    return true;
                case 1:
                    long readRawLittleEndian64 = readRawLittleEndian64();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeFixed64NoTag(readRawLittleEndian64);
                    return true;
                case 2:
                    ByteString readBytes = readBytes();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeBytesNoTag(readBytes);
                    return true;
                case 3:
                    codedOutputStream.writeRawVarint32(i);
                    skipMessage(codedOutputStream);
                    int a = WireFormat.a(WireFormat.getTagFieldNumber(i), 4);
                    checkLastTagWas(a);
                    codedOutputStream.writeRawVarint32(a);
                    return true;
                case 4:
                    return false;
                case 5:
                    int readRawLittleEndian32 = readRawLittleEndian32();
                    codedOutputStream.writeRawVarint32(i);
                    codedOutputStream.writeFixed32NoTag(readRawLittleEndian32);
                    return true;
                default:
                    throw InvalidProtocolBufferException.f();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public void skipMessage() throws IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag));
        }

        @Override // com.google.protobuf.CodedInputStream
        public void skipMessage(CodedOutputStream codedOutputStream) throws IOException {
            int readTag;
            do {
                readTag = readTag();
                if (readTag == 0) {
                    return;
                }
            } while (skipField(readTag, codedOutputStream));
        }

        @Override // com.google.protobuf.CodedInputStream
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readRawLittleEndian64());
        }

        @Override // com.google.protobuf.CodedInputStream
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readRawLittleEndian32());
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readUInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readInt64() throws IOException {
            return readRawVarint64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean readBool() throws IOException {
            return readRawVarint64() != 0;
        }

        @Override // com.google.protobuf.CodedInputStream
        public String readString() throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0) {
                long j = readRawVarint32;
                long j2 = this.s;
                long j3 = this.p;
                if (j <= j2 - j3) {
                    byte[] bArr = new byte[readRawVarint32];
                    at.a(j3, bArr, 0L, j);
                    String str = new String(bArr, Internal.a);
                    this.p += j;
                    return str;
                }
            }
            if (readRawVarint32 > 0 && readRawVarint32 <= f()) {
                byte[] bArr2 = new byte[readRawVarint32];
                a(bArr2, 0, readRawVarint32);
                return new String(bArr2, Internal.a);
            } else if (readRawVarint32 == 0) {
                return "";
            } else {
                if (readRawVarint32 < 0) {
                    throw InvalidProtocolBufferException.b();
                }
                throw InvalidProtocolBufferException.a();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public String readStringRequireUtf8() throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0) {
                long j = readRawVarint32;
                long j2 = this.s;
                long j3 = this.p;
                if (j <= j2 - j3) {
                    String a = au.a(this.g, (int) (j3 - this.q), readRawVarint32);
                    this.p += j;
                    return a;
                }
            }
            if (readRawVarint32 >= 0 && readRawVarint32 <= f()) {
                byte[] bArr = new byte[readRawVarint32];
                a(bArr, 0, readRawVarint32);
                return au.b(bArr, 0, readRawVarint32);
            } else if (readRawVarint32 == 0) {
                return "";
            } else {
                if (readRawVarint32 <= 0) {
                    throw InvalidProtocolBufferException.b();
                }
                throw InvalidProtocolBufferException.a();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public void readGroup(int i, MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            if (this.a < this.b) {
                this.a++;
                builder.mergeFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.a(i, 4));
                this.a--;
                return;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        public <T extends MessageLite> T readGroup(int i, Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            if (this.a < this.b) {
                this.a++;
                T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(WireFormat.a(i, 4));
                this.a--;
                return parsePartialFrom;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        @Deprecated
        public void readUnknownGroup(int i, MessageLite.Builder builder) throws IOException {
            readGroup(i, builder, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // com.google.protobuf.CodedInputStream
        public void readMessage(MessageLite.Builder builder, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (this.a < this.b) {
                int pushLimit = pushLimit(readRawVarint32);
                this.a++;
                builder.mergeFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.a--;
                popLimit(pushLimit);
                return;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (this.a < this.b) {
                int pushLimit = pushLimit(readRawVarint32);
                this.a++;
                T parsePartialFrom = parser.parsePartialFrom(this, extensionRegistryLite);
                checkLastTagWas(0);
                this.a--;
                popLimit(pushLimit);
                return parsePartialFrom;
            }
            throw InvalidProtocolBufferException.g();
        }

        @Override // com.google.protobuf.CodedInputStream
        public ByteString readBytes() throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0) {
                long j = readRawVarint32;
                long j2 = this.s;
                long j3 = this.p;
                if (j <= j2 - j3) {
                    if (!this.h || !this.i) {
                        byte[] bArr = new byte[readRawVarint32];
                        at.a(this.p, bArr, 0L, j);
                        this.p += j;
                        return ByteString.a(bArr);
                    }
                    int i = (int) (j3 - this.r);
                    ByteString a = ByteString.a(a(i, readRawVarint32 + i));
                    this.p += j;
                    return a;
                }
            }
            if (readRawVarint32 > 0 && readRawVarint32 <= f()) {
                byte[] bArr2 = new byte[readRawVarint32];
                a(bArr2, 0, readRawVarint32);
                return ByteString.a(bArr2);
            } else if (readRawVarint32 == 0) {
                return ByteString.EMPTY;
            } else {
                if (readRawVarint32 < 0) {
                    throw InvalidProtocolBufferException.b();
                }
                throw InvalidProtocolBufferException.a();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public byte[] readByteArray() throws IOException {
            return readRawBytes(readRawVarint32());
        }

        @Override // com.google.protobuf.CodedInputStream
        public ByteBuffer readByteBuffer() throws IOException {
            int readRawVarint32 = readRawVarint32();
            if (readRawVarint32 > 0) {
                long j = readRawVarint32;
                if (j <= g()) {
                    if (this.h || !this.i) {
                        byte[] bArr = new byte[readRawVarint32];
                        at.a(this.p, bArr, 0L, j);
                        this.p += j;
                        return ByteBuffer.wrap(bArr);
                    }
                    this.p += j;
                    long j2 = this.p;
                    long j3 = this.r;
                    return a((int) ((j2 - j3) - j), (int) (j2 - j3));
                }
            }
            if (readRawVarint32 > 0 && readRawVarint32 <= f()) {
                byte[] bArr2 = new byte[readRawVarint32];
                a(bArr2, 0, readRawVarint32);
                return ByteBuffer.wrap(bArr2);
            } else if (readRawVarint32 == 0) {
                return Internal.EMPTY_BYTE_BUFFER;
            } else {
                if (readRawVarint32 < 0) {
                    throw InvalidProtocolBufferException.b();
                }
                throw InvalidProtocolBufferException.a();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readUInt32() throws IOException {
            return readRawVarint32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readEnum() throws IOException {
            return readRawVarint32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readSFixed32() throws IOException {
            return readRawLittleEndian32();
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readSFixed64() throws IOException {
            return readRawLittleEndian64();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readSInt32() throws IOException {
            return decodeZigZag32(readRawVarint32());
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readSInt64() throws IOException {
            return decodeZigZag64(readRawVarint64());
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x0088, code lost:
            if (com.google.protobuf.at.a(r4) < 0) goto L_0x008a;
         */
        @Override // com.google.protobuf.CodedInputStream
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int readRawVarint32() throws java.io.IOException {
            /*
                r10 = this;
                long r0 = r10.p
                long r2 = r10.s
                int r2 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
                if (r2 != 0) goto L_0x000a
                goto L_0x008a
            L_0x000a:
                r2 = 1
                long r4 = r0 + r2
                byte r0 = com.google.protobuf.at.a(r0)
                if (r0 < 0) goto L_0x001a
                long r4 = r10.p
                long r4 = r4 + r2
                r10.p = r4
                return r0
            L_0x001a:
                long r6 = r10.s
                long r8 = r10.p
                long r6 = r6 - r8
                r8 = 10
                int r1 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r1 >= 0) goto L_0x0026
                goto L_0x008a
            L_0x0026:
                long r6 = r4 + r2
                byte r1 = com.google.protobuf.at.a(r4)
                int r1 = r1 << 7
                r0 = r0 ^ r1
                if (r0 >= 0) goto L_0x0034
                r0 = r0 ^ (-128(0xffffffffffffff80, float:NaN))
                goto L_0x0095
            L_0x0034:
                long r4 = r6 + r2
                byte r1 = com.google.protobuf.at.a(r6)
                int r1 = r1 << 14
                r0 = r0 ^ r1
                if (r0 < 0) goto L_0x0043
                r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
                r6 = r4
                goto L_0x0095
            L_0x0043:
                long r6 = r4 + r2
                byte r1 = com.google.protobuf.at.a(r4)
                int r1 = r1 << 21
                r0 = r0 ^ r1
                if (r0 >= 0) goto L_0x0053
                r1 = -2080896(0xffffffffffe03f80, float:NaN)
                r0 = r0 ^ r1
                goto L_0x0095
            L_0x0053:
                long r4 = r6 + r2
                byte r1 = com.google.protobuf.at.a(r6)
                int r6 = r1 << 28
                r0 = r0 ^ r6
                r6 = 266354560(0xfe03f80, float:2.2112565E-29)
                r0 = r0 ^ r6
                if (r1 >= 0) goto L_0x0094
                long r6 = r4 + r2
                byte r1 = com.google.protobuf.at.a(r4)
                if (r1 >= 0) goto L_0x0095
                long r4 = r6 + r2
                byte r1 = com.google.protobuf.at.a(r6)
                if (r1 >= 0) goto L_0x0092
                long r6 = r4 + r2
                byte r1 = com.google.protobuf.at.a(r4)
                if (r1 >= 0) goto L_0x0095
                long r4 = r6 + r2
                byte r1 = com.google.protobuf.at.a(r6)
                if (r1 >= 0) goto L_0x0090
                long r6 = r4 + r2
                byte r1 = com.google.protobuf.at.a(r4)
                if (r1 >= 0) goto L_0x0095
            L_0x008a:
                long r0 = r10.a()
                int r0 = (int) r0
                return r0
            L_0x0090:
                r6 = r4
                goto L_0x0095
            L_0x0092:
                r6 = r4
                goto L_0x0095
            L_0x0094:
                r6 = r4
            L_0x0095:
                r10.p = r6
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.CodedInputStream.b.readRawVarint32():int");
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readRawVarint64() throws IOException {
            long j;
            long j2;
            long j3 = this.p;
            if (this.s != j3) {
                long j4 = j3 + 1;
                byte a = at.a(j3);
                if (a >= 0) {
                    this.p++;
                    return a;
                } else if (this.s - this.p >= 10) {
                    long j5 = j4 + 1;
                    int a2 = a ^ (at.a(j4) << 7);
                    if (a2 < 0) {
                        j2 = a2 ^ (-128);
                        j = j5;
                    } else {
                        j = j5 + 1;
                        int a3 = a2 ^ (at.a(j5) << 14);
                        if (a3 >= 0) {
                            j2 = a3 ^ 16256;
                        } else {
                            long j6 = j + 1;
                            int a4 = a3 ^ (at.a(j) << 21);
                            if (a4 < 0) {
                                j2 = a4 ^ (-2080896);
                                j = j6;
                            } else {
                                j = j6 + 1;
                                long a5 = a4 ^ (at.a(j6) << 28);
                                if (a5 >= 0) {
                                    j2 = a5 ^ 266354560;
                                } else {
                                    long j7 = j + 1;
                                    long a6 = a5 ^ (at.a(j) << 35);
                                    if (a6 < 0) {
                                        j2 = a6 ^ (-34093383808L);
                                        j = j7;
                                    } else {
                                        j = j7 + 1;
                                        long a7 = a6 ^ (at.a(j7) << 42);
                                        if (a7 >= 0) {
                                            j2 = a7 ^ 4363953127296L;
                                        } else {
                                            long j8 = j + 1;
                                            long a8 = a7 ^ (at.a(j) << 49);
                                            if (a8 < 0) {
                                                j2 = a8 ^ (-558586000294016L);
                                                j = j8;
                                            } else {
                                                j = j8 + 1;
                                                j2 = (a8 ^ (at.a(j8) << 56)) ^ 71499008037633920L;
                                                if (j2 < 0) {
                                                    j = 1 + j;
                                                    if (at.a(j) >= 0) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.p = j;
                    return j2;
                }
            }
            return a();
        }

        @Override // com.google.protobuf.CodedInputStream
        long a() throws IOException {
            long j = 0;
            for (int i = 0; i < 64; i += 7) {
                byte readRawByte = readRawByte();
                j |= (readRawByte & Byte.MAX_VALUE) << i;
                if ((readRawByte & 128) == 0) {
                    return j;
                }
            }
            throw InvalidProtocolBufferException.c();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int readRawLittleEndian32() throws IOException {
            if (g() < 4) {
                return (readRawByte() & 255) | ((readRawByte() & 255) << 8) | ((readRawByte() & 255) << 16) | ((readRawByte() & 255) << 24);
            }
            long j = this.p;
            this.p = 4 + j;
            return ((at.a(j + 3) & 255) << 24) | (at.a(j) & 255) | ((at.a(1 + j) & 255) << 8) | ((at.a(2 + j) & 255) << 16);
        }

        @Override // com.google.protobuf.CodedInputStream
        public long readRawLittleEndian64() throws IOException {
            if (g() >= 8) {
                long j = this.p;
                this.p = 8 + j;
                return ((at.a(j + 7) & 255) << 56) | (at.a(j) & 255) | ((at.a(1 + j) & 255) << 8) | ((at.a(2 + j) & 255) << 16) | ((at.a(3 + j) & 255) << 24) | ((at.a(4 + j) & 255) << 32) | ((at.a(5 + j) & 255) << 40) | ((at.a(6 + j) & 255) << 48);
            }
            return ((readRawByte() & 255) << 56) | (readRawByte() & 255) | ((readRawByte() & 255) << 8) | ((readRawByte() & 255) << 16) | ((readRawByte() & 255) << 24) | ((readRawByte() & 255) << 32) | ((readRawByte() & 255) << 40) | ((readRawByte() & 255) << 48);
        }

        @Override // com.google.protobuf.CodedInputStream
        public void enableAliasing(boolean z) {
            this.i = z;
        }

        @Override // com.google.protobuf.CodedInputStream
        public void resetSizeCounter() {
            this.o = (int) ((this.n + this.p) - this.q);
        }

        @Override // com.google.protobuf.CodedInputStream
        public int pushLimit(int i) throws InvalidProtocolBufferException {
            if (i >= 0) {
                int totalBytesRead = i + getTotalBytesRead();
                int i2 = this.l;
                if (totalBytesRead <= i2) {
                    this.l = totalBytesRead;
                    d();
                    return i2;
                }
                throw InvalidProtocolBufferException.a();
            }
            throw InvalidProtocolBufferException.b();
        }

        private void d() {
            this.j += this.k;
            int i = this.j;
            int i2 = i - this.o;
            int i3 = this.l;
            if (i2 > i3) {
                this.k = i2 - i3;
                this.j = i - this.k;
                return;
            }
            this.k = 0;
        }

        @Override // com.google.protobuf.CodedInputStream
        public void popLimit(int i) {
            this.l = i;
            d();
        }

        @Override // com.google.protobuf.CodedInputStream
        public int getBytesUntilLimit() {
            int i = this.l;
            if (i == Integer.MAX_VALUE) {
                return -1;
            }
            return i - getTotalBytesRead();
        }

        @Override // com.google.protobuf.CodedInputStream
        public boolean isAtEnd() throws IOException {
            return (((long) this.n) + this.p) - this.q == ((long) this.j);
        }

        @Override // com.google.protobuf.CodedInputStream
        public int getTotalBytesRead() {
            return (int) (((this.n - this.o) + this.p) - this.q);
        }

        @Override // com.google.protobuf.CodedInputStream
        public byte readRawByte() throws IOException {
            if (g() == 0) {
                b();
            }
            long j = this.p;
            this.p = 1 + j;
            return at.a(j);
        }

        @Override // com.google.protobuf.CodedInputStream
        public byte[] readRawBytes(int i) throws IOException {
            if (i >= 0) {
                long j = i;
                if (j <= g()) {
                    byte[] bArr = new byte[i];
                    at.a(this.p, bArr, 0L, j);
                    this.p += j;
                    return bArr;
                }
            }
            if (i >= 0 && i <= f()) {
                byte[] bArr2 = new byte[i];
                a(bArr2, 0, i);
                return bArr2;
            } else if (i > 0) {
                throw InvalidProtocolBufferException.a();
            } else if (i == 0) {
                return Internal.EMPTY_BYTE_ARRAY;
            } else {
                throw InvalidProtocolBufferException.b();
            }
        }

        private void a(byte[] bArr, int i, int i2) throws IOException {
            if (i2 >= 0 && i2 <= f()) {
                int i3 = i2;
                while (i3 > 0) {
                    if (g() == 0) {
                        b();
                    }
                    int min = Math.min(i3, (int) g());
                    long j = min;
                    at.a(this.p, bArr, (i2 - i3) + i, j);
                    i3 -= min;
                    this.p += j;
                }
            } else if (i2 > 0) {
                throw InvalidProtocolBufferException.a();
            } else if (i2 != 0) {
                throw InvalidProtocolBufferException.b();
            }
        }

        @Override // com.google.protobuf.CodedInputStream
        public void skipRawBytes(int i) throws IOException {
            if (i >= 0 && i <= ((this.j - this.n) - this.p) + this.q) {
                while (i > 0) {
                    if (g() == 0) {
                        b();
                    }
                    int min = Math.min(i, (int) g());
                    i -= min;
                    this.p += min;
                }
            } else if (i < 0) {
                throw InvalidProtocolBufferException.b();
            } else {
                throw InvalidProtocolBufferException.a();
            }
        }

        private void e() throws IOException {
            for (int i = 0; i < 10; i++) {
                if (readRawByte() >= 0) {
                    return;
                }
            }
            throw InvalidProtocolBufferException.c();
        }

        private int f() {
            return (int) (((this.j - this.n) - this.p) + this.q);
        }

        private long g() {
            return this.s - this.p;
        }

        private ByteBuffer a(int i, int i2) throws IOException {
            int position;
            int limit;
            try {
                position = this.g.position();
                limit = this.g.limit();
                try {
                    this.g.position(i);
                    this.g.limit(i2);
                    return this.g.slice();
                } catch (IllegalArgumentException unused) {
                    throw InvalidProtocolBufferException.a();
                }
            } finally {
                this.g.position(position);
                this.g.limit(limit);
            }
        }
    }
}
