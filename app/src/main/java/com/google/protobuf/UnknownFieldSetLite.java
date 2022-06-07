package com.google.protobuf;

import com.google.protobuf.Writer;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class UnknownFieldSetLite {
    private static final UnknownFieldSetLite a = new UnknownFieldSetLite(0, new int[0], new Object[0], false);
    private int b;
    private int[] c;
    private Object[] d;
    private int e;
    private boolean f;

    public static UnknownFieldSetLite getDefaultInstance() {
        return a;
    }

    public static UnknownFieldSetLite a() {
        return new UnknownFieldSetLite();
    }

    public static UnknownFieldSetLite a(UnknownFieldSetLite unknownFieldSetLite, UnknownFieldSetLite unknownFieldSetLite2) {
        int i = unknownFieldSetLite.b + unknownFieldSetLite2.b;
        int[] copyOf = Arrays.copyOf(unknownFieldSetLite.c, i);
        System.arraycopy(unknownFieldSetLite2.c, 0, copyOf, unknownFieldSetLite.b, unknownFieldSetLite2.b);
        Object[] copyOf2 = Arrays.copyOf(unknownFieldSetLite.d, i);
        System.arraycopy(unknownFieldSetLite2.d, 0, copyOf2, unknownFieldSetLite.b, unknownFieldSetLite2.b);
        return new UnknownFieldSetLite(i, copyOf, copyOf2, true);
    }

    private UnknownFieldSetLite() {
        this(0, new int[8], new Object[8], true);
    }

    private UnknownFieldSetLite(int i, int[] iArr, Object[] objArr, boolean z) {
        this.e = -1;
        this.b = i;
        this.c = iArr;
        this.d = objArr;
        this.f = z;
    }

    public void makeImmutable() {
        this.f = false;
    }

    void b() {
        if (!this.f) {
            throw new UnsupportedOperationException();
        }
    }

    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        for (int i = 0; i < this.b; i++) {
            int i2 = this.c[i];
            int tagFieldNumber = WireFormat.getTagFieldNumber(i2);
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType != 5) {
                switch (tagWireType) {
                    case 0:
                        codedOutputStream.writeUInt64(tagFieldNumber, ((Long) this.d[i]).longValue());
                        continue;
                    case 1:
                        codedOutputStream.writeFixed64(tagFieldNumber, ((Long) this.d[i]).longValue());
                        continue;
                    case 2:
                        codedOutputStream.writeBytes(tagFieldNumber, (ByteString) this.d[i]);
                        continue;
                    case 3:
                        codedOutputStream.writeTag(tagFieldNumber, 3);
                        ((UnknownFieldSetLite) this.d[i]).writeTo(codedOutputStream);
                        codedOutputStream.writeTag(tagFieldNumber, 4);
                        continue;
                    default:
                        throw InvalidProtocolBufferException.f();
                }
            } else {
                codedOutputStream.writeFixed32(tagFieldNumber, ((Integer) this.d[i]).intValue());
            }
        }
    }

    public void writeAsMessageSetTo(CodedOutputStream codedOutputStream) throws IOException {
        for (int i = 0; i < this.b; i++) {
            codedOutputStream.writeRawMessageSetExtension(WireFormat.getTagFieldNumber(this.c[i]), (ByteString) this.d[i]);
        }
    }

    public void a(Writer writer) throws IOException {
        if (writer.a() == Writer.FieldOrder.DESCENDING) {
            for (int i = this.b - 1; i >= 0; i--) {
                writer.a(WireFormat.getTagFieldNumber(this.c[i]), this.d[i]);
            }
            return;
        }
        for (int i2 = 0; i2 < this.b; i2++) {
            writer.a(WireFormat.getTagFieldNumber(this.c[i2]), this.d[i2]);
        }
    }

    public void writeTo(Writer writer) throws IOException {
        if (this.b != 0) {
            if (writer.a() == Writer.FieldOrder.ASCENDING) {
                for (int i = 0; i < this.b; i++) {
                    a(this.c[i], this.d[i], writer);
                }
                return;
            }
            for (int i2 = this.b - 1; i2 >= 0; i2--) {
                a(this.c[i2], this.d[i2], writer);
            }
        }
    }

    private static void a(int i, Object obj, Writer writer) throws IOException {
        int tagFieldNumber = WireFormat.getTagFieldNumber(i);
        int tagWireType = WireFormat.getTagWireType(i);
        if (tagWireType != 5) {
            switch (tagWireType) {
                case 0:
                    writer.a(tagFieldNumber, ((Long) obj).longValue());
                    return;
                case 1:
                    writer.d(tagFieldNumber, ((Long) obj).longValue());
                    return;
                case 2:
                    writer.a(tagFieldNumber, (ByteString) obj);
                    return;
                case 3:
                    if (writer.a() == Writer.FieldOrder.ASCENDING) {
                        writer.a(tagFieldNumber);
                        ((UnknownFieldSetLite) obj).writeTo(writer);
                        writer.b(tagFieldNumber);
                        return;
                    }
                    writer.b(tagFieldNumber);
                    ((UnknownFieldSetLite) obj).writeTo(writer);
                    writer.a(tagFieldNumber);
                    return;
                default:
                    throw new RuntimeException(InvalidProtocolBufferException.f());
            }
        } else {
            writer.d(tagFieldNumber, ((Integer) obj).intValue());
        }
    }

    public int getSerializedSizeAsMessageSet() {
        int i = this.e;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.b; i3++) {
            i2 += CodedOutputStream.computeRawMessageSetExtensionSize(WireFormat.getTagFieldNumber(this.c[i3]), (ByteString) this.d[i3]);
        }
        this.e = i2;
        return i2;
    }

    public int getSerializedSize() {
        int i;
        int i2 = this.e;
        if (i2 != -1) {
            return i2;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.b; i4++) {
            int i5 = this.c[i4];
            int tagFieldNumber = WireFormat.getTagFieldNumber(i5);
            int tagWireType = WireFormat.getTagWireType(i5);
            if (tagWireType != 5) {
                switch (tagWireType) {
                    case 0:
                        i = CodedOutputStream.computeUInt64Size(tagFieldNumber, ((Long) this.d[i4]).longValue());
                        continue;
                    case 1:
                        i = CodedOutputStream.computeFixed64Size(tagFieldNumber, ((Long) this.d[i4]).longValue());
                        continue;
                    case 2:
                        i = CodedOutputStream.computeBytesSize(tagFieldNumber, (ByteString) this.d[i4]);
                        continue;
                    case 3:
                        i = (CodedOutputStream.computeTagSize(tagFieldNumber) * 2) + ((UnknownFieldSetLite) this.d[i4]).getSerializedSize();
                        continue;
                    default:
                        throw new IllegalStateException(InvalidProtocolBufferException.f());
                }
            } else {
                i = CodedOutputStream.computeFixed32Size(tagFieldNumber, ((Integer) this.d[i4]).intValue());
            }
            i3 += i;
        }
        this.e = i3;
        return i3;
    }

    private static boolean a(int[] iArr, int[] iArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2] != iArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(Object[] objArr, Object[] objArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (!objArr[i2].equals(objArr2[i2])) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UnknownFieldSetLite)) {
            return false;
        }
        UnknownFieldSetLite unknownFieldSetLite = (UnknownFieldSetLite) obj;
        int i = this.b;
        return i == unknownFieldSetLite.b && a(this.c, unknownFieldSetLite.c, i) && a(this.d, unknownFieldSetLite.d, this.b);
    }

    private static int a(int[] iArr, int i) {
        int i2 = 17;
        for (int i3 = 0; i3 < i; i3++) {
            i2 = (i2 * 31) + iArr[i3];
        }
        return i2;
    }

    private static int a(Object[] objArr, int i) {
        int i2 = 17;
        for (int i3 = 0; i3 < i; i3++) {
            i2 = (i2 * 31) + objArr[i3].hashCode();
        }
        return i2;
    }

    public int hashCode() {
        int i = this.b;
        return ((((527 + i) * 31) + a(this.c, i)) * 31) + a(this.d, this.b);
    }

    public final void a(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.b; i2++) {
            y.a(sb, i, String.valueOf(WireFormat.getTagFieldNumber(this.c[i2])), this.d[i2]);
        }
    }

    public void a(int i, Object obj) {
        b();
        c();
        int[] iArr = this.c;
        int i2 = this.b;
        iArr[i2] = i;
        this.d[i2] = obj;
        this.b = i2 + 1;
    }

    private void c() {
        int i = this.b;
        if (i == this.c.length) {
            int i2 = this.b + (i < 4 ? 8 : i >> 1);
            this.c = Arrays.copyOf(this.c, i2);
            this.d = Arrays.copyOf(this.d, i2);
        }
    }

    public boolean a(int i, CodedInputStream codedInputStream) throws IOException {
        b();
        int tagFieldNumber = WireFormat.getTagFieldNumber(i);
        switch (WireFormat.getTagWireType(i)) {
            case 0:
                a(i, Long.valueOf(codedInputStream.readInt64()));
                return true;
            case 1:
                a(i, Long.valueOf(codedInputStream.readFixed64()));
                return true;
            case 2:
                a(i, codedInputStream.readBytes());
                return true;
            case 3:
                UnknownFieldSetLite unknownFieldSetLite = new UnknownFieldSetLite();
                unknownFieldSetLite.a(codedInputStream);
                codedInputStream.checkLastTagWas(WireFormat.a(tagFieldNumber, 4));
                a(i, unknownFieldSetLite);
                return true;
            case 4:
                return false;
            case 5:
                a(i, Integer.valueOf(codedInputStream.readFixed32()));
                return true;
            default:
                throw InvalidProtocolBufferException.f();
        }
    }

    public UnknownFieldSetLite a(int i, int i2) {
        b();
        if (i != 0) {
            a(WireFormat.a(i, 0), Long.valueOf(i2));
            return this;
        }
        throw new IllegalArgumentException("Zero is not a valid field number.");
    }

    public UnknownFieldSetLite a(int i, ByteString byteString) {
        b();
        if (i != 0) {
            a(WireFormat.a(i, 2), (Object) byteString);
            return this;
        }
        throw new IllegalArgumentException("Zero is not a valid field number.");
    }

    private UnknownFieldSetLite a(CodedInputStream codedInputStream) throws IOException {
        int readTag;
        do {
            readTag = codedInputStream.readTag();
            if (readTag == 0) {
                break;
            }
        } while (a(readTag, codedInputStream));
        return this;
    }
}
