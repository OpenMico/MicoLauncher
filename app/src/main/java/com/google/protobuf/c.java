package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.util.List;

/* compiled from: ArrayDecoders.java */
/* loaded from: classes2.dex */
public final class c {

    /* compiled from: ArrayDecoders.java */
    /* loaded from: classes2.dex */
    public static final class a {
        public int a;
        public long b;
        public Object c;
        public final ExtensionRegistryLite d;

        a() {
            this.d = ExtensionRegistryLite.getEmptyRegistry();
        }

        public a(ExtensionRegistryLite extensionRegistryLite) {
            if (extensionRegistryLite != null) {
                this.d = extensionRegistryLite;
                return;
            }
            throw new NullPointerException();
        }
    }

    public static int a(byte[] bArr, int i, a aVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return a((int) b, bArr, i2, aVar);
        }
        aVar.a = b;
        return i2;
    }

    public static int a(int i, byte[] bArr, int i2, a aVar) {
        int i3 = i & 127;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            aVar.a = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & Byte.MAX_VALUE) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            aVar.a = i5 | (b2 << 14);
            return i6;
        }
        int i7 = i5 | ((b2 & Byte.MAX_VALUE) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            aVar.a = i7 | (b3 << 21);
            return i8;
        }
        int i9 = i7 | ((b3 & Byte.MAX_VALUE) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            aVar.a = i9 | (b4 << 28);
            return i10;
        }
        int i11 = i9 | ((b4 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] < 0) {
                i10 = i12;
            } else {
                aVar.a = i11;
                return i12;
            }
        }
    }

    public static int b(byte[] bArr, int i, a aVar) {
        int i2 = i + 1;
        long j = bArr[i];
        if (j < 0) {
            return a(j, bArr, i2, aVar);
        }
        aVar.b = j;
        return i2;
    }

    static int a(long j, byte[] bArr, int i, a aVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        long j2 = (j & 127) | ((b & Byte.MAX_VALUE) << 7);
        int i3 = 7;
        while (b < 0) {
            i2++;
            byte b2 = bArr[i2];
            i3 += 7;
            j2 |= (b2 & Byte.MAX_VALUE) << i3;
            b = b2;
        }
        aVar.b = j2;
        return i2;
    }

    public static int a(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    public static long b(byte[] bArr, int i) {
        return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
    }

    public static double c(byte[] bArr, int i) {
        return Double.longBitsToDouble(b(bArr, i));
    }

    public static float d(byte[] bArr, int i) {
        return Float.intBitsToFloat(a(bArr, i));
    }

    public static int c(byte[] bArr, int i, a aVar) throws InvalidProtocolBufferException {
        int a2 = a(bArr, i, aVar);
        int i2 = aVar.a;
        if (i2 < 0) {
            throw InvalidProtocolBufferException.b();
        } else if (i2 == 0) {
            aVar.c = "";
            return a2;
        } else {
            aVar.c = new String(bArr, a2, i2, Internal.a);
            return a2 + i2;
        }
    }

    public static int d(byte[] bArr, int i, a aVar) throws InvalidProtocolBufferException {
        int a2 = a(bArr, i, aVar);
        int i2 = aVar.a;
        if (i2 < 0) {
            throw InvalidProtocolBufferException.b();
        } else if (i2 == 0) {
            aVar.c = "";
            return a2;
        } else {
            aVar.c = au.b(bArr, a2, i2);
            return a2 + i2;
        }
    }

    public static int e(byte[] bArr, int i, a aVar) throws InvalidProtocolBufferException {
        int a2 = a(bArr, i, aVar);
        int i2 = aVar.a;
        if (i2 < 0) {
            throw InvalidProtocolBufferException.b();
        } else if (i2 > bArr.length - a2) {
            throw InvalidProtocolBufferException.a();
        } else if (i2 == 0) {
            aVar.c = ByteString.EMPTY;
            return a2;
        } else {
            aVar.c = ByteString.copyFrom(bArr, a2, i2);
            return a2 + i2;
        }
    }

    public static int a(am amVar, byte[] bArr, int i, int i2, a aVar) throws IOException {
        byte b;
        int i3;
        int i4 = i + 1;
        byte b2 = bArr[i];
        if (b2 < 0) {
            i3 = a((int) b2, bArr, i4, aVar);
            b = aVar.a;
        } else {
            i3 = i4;
            b = b2;
        }
        if (b < 0 || b > i2 - i3) {
            throw InvalidProtocolBufferException.a();
        }
        Object a2 = amVar.a();
        int i5 = (b == 1 ? 1 : 0) + i3;
        amVar.a(a2, bArr, i3, i5, aVar);
        amVar.d(a2);
        aVar.c = a2;
        return i5;
    }

    public static int a(am amVar, byte[] bArr, int i, int i2, int i3, a aVar) throws IOException {
        z zVar = (z) amVar;
        Object a2 = zVar.a();
        int a3 = zVar.a((z) a2, bArr, i, i2, i3, aVar);
        zVar.d((z) a2);
        aVar.c = a2;
        return a3;
    }

    public static int a(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, a aVar) {
        o oVar = (o) protobufList;
        int a2 = a(bArr, i2, aVar);
        oVar.addInt(aVar.a);
        while (a2 < i3) {
            int a3 = a(bArr, a2, aVar);
            if (i != aVar.a) {
                break;
            }
            a2 = a(bArr, a3, aVar);
            oVar.addInt(aVar.a);
        }
        return a2;
    }

    public static int b(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, a aVar) {
        r rVar = (r) protobufList;
        int b = b(bArr, i2, aVar);
        rVar.addLong(aVar.b);
        while (b < i3) {
            int a2 = a(bArr, b, aVar);
            if (i != aVar.a) {
                break;
            }
            b = b(bArr, a2, aVar);
            rVar.addLong(aVar.b);
        }
        return b;
    }

    public static int c(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, a aVar) {
        o oVar = (o) protobufList;
        oVar.addInt(a(bArr, i2));
        int i4 = i2 + 4;
        while (i4 < i3) {
            int a2 = a(bArr, i4, aVar);
            if (i != aVar.a) {
                break;
            }
            oVar.addInt(a(bArr, a2));
            i4 = a2 + 4;
        }
        return i4;
    }

    public static int d(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, a aVar) {
        r rVar = (r) protobufList;
        rVar.addLong(b(bArr, i2));
        int i4 = i2 + 8;
        while (i4 < i3) {
            int a2 = a(bArr, i4, aVar);
            if (i != aVar.a) {
                break;
            }
            rVar.addLong(b(bArr, a2));
            i4 = a2 + 8;
        }
        return i4;
    }

    public static int e(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, a aVar) {
        m mVar = (m) protobufList;
        mVar.addFloat(d(bArr, i2));
        int i4 = i2 + 4;
        while (i4 < i3) {
            int a2 = a(bArr, i4, aVar);
            if (i != aVar.a) {
                break;
            }
            mVar.addFloat(d(bArr, a2));
            i4 = a2 + 4;
        }
        return i4;
    }

    public static int f(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, a aVar) {
        h hVar = (h) protobufList;
        hVar.addDouble(c(bArr, i2));
        int i4 = i2 + 8;
        while (i4 < i3) {
            int a2 = a(bArr, i4, aVar);
            if (i != aVar.a) {
                break;
            }
            hVar.addDouble(c(bArr, a2));
            i4 = a2 + 8;
        }
        return i4;
    }

    public static int g(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, a aVar) {
        e eVar = (e) protobufList;
        int b = b(bArr, i2, aVar);
        eVar.addBoolean(aVar.b != 0);
        while (b < i3) {
            int a2 = a(bArr, b, aVar);
            if (i != aVar.a) {
                break;
            }
            b = b(bArr, a2, aVar);
            eVar.addBoolean(aVar.b != 0);
        }
        return b;
    }

    public static int h(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, a aVar) {
        o oVar = (o) protobufList;
        int a2 = a(bArr, i2, aVar);
        oVar.addInt(CodedInputStream.decodeZigZag32(aVar.a));
        while (a2 < i3) {
            int a3 = a(bArr, a2, aVar);
            if (i != aVar.a) {
                break;
            }
            a2 = a(bArr, a3, aVar);
            oVar.addInt(CodedInputStream.decodeZigZag32(aVar.a));
        }
        return a2;
    }

    public static int i(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, a aVar) {
        r rVar = (r) protobufList;
        int b = b(bArr, i2, aVar);
        rVar.addLong(CodedInputStream.decodeZigZag64(aVar.b));
        while (b < i3) {
            int a2 = a(bArr, b, aVar);
            if (i != aVar.a) {
                break;
            }
            b = b(bArr, a2, aVar);
            rVar.addLong(CodedInputStream.decodeZigZag64(aVar.b));
        }
        return b;
    }

    public static int a(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, a aVar) throws IOException {
        o oVar = (o) protobufList;
        int a2 = a(bArr, i, aVar);
        int i2 = aVar.a + a2;
        while (a2 < i2) {
            a2 = a(bArr, a2, aVar);
            oVar.addInt(aVar.a);
        }
        if (a2 == i2) {
            return a2;
        }
        throw InvalidProtocolBufferException.a();
    }

    public static int b(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, a aVar) throws IOException {
        r rVar = (r) protobufList;
        int a2 = a(bArr, i, aVar);
        int i2 = aVar.a + a2;
        while (a2 < i2) {
            a2 = b(bArr, a2, aVar);
            rVar.addLong(aVar.b);
        }
        if (a2 == i2) {
            return a2;
        }
        throw InvalidProtocolBufferException.a();
    }

    public static int c(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, a aVar) throws IOException {
        o oVar = (o) protobufList;
        int a2 = a(bArr, i, aVar);
        int i2 = aVar.a + a2;
        while (a2 < i2) {
            oVar.addInt(a(bArr, a2));
            a2 += 4;
        }
        if (a2 == i2) {
            return a2;
        }
        throw InvalidProtocolBufferException.a();
    }

    public static int d(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, a aVar) throws IOException {
        r rVar = (r) protobufList;
        int a2 = a(bArr, i, aVar);
        int i2 = aVar.a + a2;
        while (a2 < i2) {
            rVar.addLong(b(bArr, a2));
            a2 += 8;
        }
        if (a2 == i2) {
            return a2;
        }
        throw InvalidProtocolBufferException.a();
    }

    public static int e(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, a aVar) throws IOException {
        m mVar = (m) protobufList;
        int a2 = a(bArr, i, aVar);
        int i2 = aVar.a + a2;
        while (a2 < i2) {
            mVar.addFloat(d(bArr, a2));
            a2 += 4;
        }
        if (a2 == i2) {
            return a2;
        }
        throw InvalidProtocolBufferException.a();
    }

    public static int f(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, a aVar) throws IOException {
        h hVar = (h) protobufList;
        int a2 = a(bArr, i, aVar);
        int i2 = aVar.a + a2;
        while (a2 < i2) {
            hVar.addDouble(c(bArr, a2));
            a2 += 8;
        }
        if (a2 == i2) {
            return a2;
        }
        throw InvalidProtocolBufferException.a();
    }

    public static int g(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, a aVar) throws IOException {
        e eVar = (e) protobufList;
        int a2 = a(bArr, i, aVar);
        int i2 = aVar.a + a2;
        while (a2 < i2) {
            a2 = b(bArr, a2, aVar);
            eVar.addBoolean(aVar.b != 0);
        }
        if (a2 == i2) {
            return a2;
        }
        throw InvalidProtocolBufferException.a();
    }

    public static int h(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, a aVar) throws IOException {
        o oVar = (o) protobufList;
        int a2 = a(bArr, i, aVar);
        int i2 = aVar.a + a2;
        while (a2 < i2) {
            a2 = a(bArr, a2, aVar);
            oVar.addInt(CodedInputStream.decodeZigZag32(aVar.a));
        }
        if (a2 == i2) {
            return a2;
        }
        throw InvalidProtocolBufferException.a();
    }

    public static int i(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, a aVar) throws IOException {
        r rVar = (r) protobufList;
        int a2 = a(bArr, i, aVar);
        int i2 = aVar.a + a2;
        while (a2 < i2) {
            a2 = b(bArr, a2, aVar);
            rVar.addLong(CodedInputStream.decodeZigZag64(aVar.b));
        }
        if (a2 == i2) {
            return a2;
        }
        throw InvalidProtocolBufferException.a();
    }

    public static int j(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, a aVar) throws InvalidProtocolBufferException {
        int a2 = a(bArr, i2, aVar);
        int i4 = aVar.a;
        if (i4 >= 0) {
            if (i4 == 0) {
                protobufList.add("");
            } else {
                protobufList.add(new String(bArr, a2, i4, Internal.a));
                a2 += i4;
            }
            while (a2 < i3) {
                int a3 = a(bArr, a2, aVar);
                if (i != aVar.a) {
                    break;
                }
                a2 = a(bArr, a3, aVar);
                int i5 = aVar.a;
                if (i5 < 0) {
                    throw InvalidProtocolBufferException.b();
                } else if (i5 == 0) {
                    protobufList.add("");
                } else {
                    protobufList.add(new String(bArr, a2, i5, Internal.a));
                    a2 += i5;
                }
            }
            return a2;
        }
        throw InvalidProtocolBufferException.b();
    }

    public static int k(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, a aVar) throws InvalidProtocolBufferException {
        int a2 = a(bArr, i2, aVar);
        int i4 = aVar.a;
        if (i4 >= 0) {
            if (i4 == 0) {
                protobufList.add("");
            } else {
                int i5 = a2 + i4;
                if (au.a(bArr, a2, i5)) {
                    protobufList.add(new String(bArr, a2, i4, Internal.a));
                    a2 = i5;
                } else {
                    throw InvalidProtocolBufferException.j();
                }
            }
            while (a2 < i3) {
                int a3 = a(bArr, a2, aVar);
                if (i != aVar.a) {
                    break;
                }
                a2 = a(bArr, a3, aVar);
                int i6 = aVar.a;
                if (i6 < 0) {
                    throw InvalidProtocolBufferException.b();
                } else if (i6 == 0) {
                    protobufList.add("");
                } else {
                    int i7 = a2 + i6;
                    if (au.a(bArr, a2, i7)) {
                        protobufList.add(new String(bArr, a2, i6, Internal.a));
                        a2 = i7;
                    } else {
                        throw InvalidProtocolBufferException.j();
                    }
                }
            }
            return a2;
        }
        throw InvalidProtocolBufferException.b();
    }

    public static int l(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, a aVar) throws InvalidProtocolBufferException {
        int a2 = a(bArr, i2, aVar);
        int i4 = aVar.a;
        if (i4 < 0) {
            throw InvalidProtocolBufferException.b();
        } else if (i4 <= bArr.length - a2) {
            if (i4 == 0) {
                protobufList.add(ByteString.EMPTY);
            } else {
                protobufList.add(ByteString.copyFrom(bArr, a2, i4));
                a2 += i4;
            }
            while (a2 < i3) {
                int a3 = a(bArr, a2, aVar);
                if (i != aVar.a) {
                    break;
                }
                a2 = a(bArr, a3, aVar);
                int i5 = aVar.a;
                if (i5 < 0) {
                    throw InvalidProtocolBufferException.b();
                } else if (i5 > bArr.length - a2) {
                    throw InvalidProtocolBufferException.a();
                } else if (i5 == 0) {
                    protobufList.add(ByteString.EMPTY);
                } else {
                    protobufList.add(ByteString.copyFrom(bArr, a2, i5));
                    a2 += i5;
                }
            }
            return a2;
        } else {
            throw InvalidProtocolBufferException.a();
        }
    }

    public static int a(am<?> amVar, int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, a aVar) throws IOException {
        int a2 = a(amVar, bArr, i2, i3, aVar);
        protobufList.add(aVar.c);
        while (a2 < i3) {
            int a3 = a(bArr, a2, aVar);
            if (i != aVar.a) {
                break;
            }
            a2 = a(amVar, bArr, a3, i3, aVar);
            protobufList.add(aVar.c);
        }
        return a2;
    }

    public static int b(am amVar, int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, a aVar) throws IOException {
        int i4 = (i & (-8)) | 4;
        int a2 = a(amVar, bArr, i2, i3, i4, aVar);
        protobufList.add(aVar.c);
        while (a2 < i3) {
            int a3 = a(bArr, a2, aVar);
            if (i != aVar.a) {
                break;
            }
            a2 = a(amVar, bArr, a3, i3, i4, aVar);
            protobufList.add(aVar.c);
        }
        return a2;
    }

    public static int a(int i, byte[] bArr, int i2, int i3, Object obj, MessageLite messageLite, ar<UnknownFieldSetLite, UnknownFieldSetLite> arVar, a aVar) throws IOException {
        GeneratedMessageLite.GeneratedExtension findLiteExtensionByNumber = aVar.d.findLiteExtensionByNumber(messageLite, i >>> 3);
        if (findLiteExtensionByNumber == null) {
            return a(i, bArr, i2, i3, z.c(obj), aVar);
        }
        GeneratedMessageLite.ExtendableMessage extendableMessage = (GeneratedMessageLite.ExtendableMessage) obj;
        extendableMessage.c();
        return a(i, bArr, i2, i3, extendableMessage, findLiteExtensionByNumber, arVar, aVar);
    }

    static int a(int i, byte[] bArr, int i2, int i3, GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage, GeneratedMessageLite.GeneratedExtension<?, ?> generatedExtension, ar<UnknownFieldSetLite, UnknownFieldSetLite> arVar, a aVar) throws IOException {
        int i4;
        FieldSet<GeneratedMessageLite.a> fieldSet = extendableMessage.extensions;
        int i5 = i >>> 3;
        Object obj = null;
        if (!generatedExtension.d.isRepeated() || !generatedExtension.d.isPacked()) {
            if (generatedExtension.getLiteType() != WireFormat.FieldType.ENUM) {
                switch (generatedExtension.getLiteType()) {
                    case DOUBLE:
                        obj = Double.valueOf(c(bArr, i2));
                        i4 = i2 + 8;
                        break;
                    case FLOAT:
                        obj = Float.valueOf(d(bArr, i2));
                        i4 = i2 + 4;
                        break;
                    case INT64:
                    case UINT64:
                        int b = b(bArr, i2, aVar);
                        obj = Long.valueOf(aVar.b);
                        i4 = b;
                        break;
                    case INT32:
                    case UINT32:
                        int a2 = a(bArr, i2, aVar);
                        obj = Integer.valueOf(aVar.a);
                        i4 = a2;
                        break;
                    case FIXED64:
                    case SFIXED64:
                        obj = Long.valueOf(b(bArr, i2));
                        i4 = i2 + 8;
                        break;
                    case FIXED32:
                    case SFIXED32:
                        obj = Integer.valueOf(a(bArr, i2));
                        i4 = i2 + 4;
                        break;
                    case BOOL:
                        int b2 = b(bArr, i2, aVar);
                        obj = Boolean.valueOf(aVar.b != 0);
                        i4 = b2;
                        break;
                    case SINT32:
                        int a3 = a(bArr, i2, aVar);
                        obj = Integer.valueOf(CodedInputStream.decodeZigZag32(aVar.a));
                        i4 = a3;
                        break;
                    case SINT64:
                        int b3 = b(bArr, i2, aVar);
                        obj = Long.valueOf(CodedInputStream.decodeZigZag64(aVar.b));
                        i4 = b3;
                        break;
                    case ENUM:
                        throw new IllegalStateException("Shouldn't reach here.");
                    case BYTES:
                        int e = e(bArr, i2, aVar);
                        obj = aVar.c;
                        i4 = e;
                        break;
                    case STRING:
                        int c = c(bArr, i2, aVar);
                        obj = aVar.c;
                        i4 = c;
                        break;
                    case GROUP:
                        int a4 = a(ah.a().a((Class) generatedExtension.getMessageDefaultInstance().getClass()), bArr, i2, i3, (i5 << 3) | 4, aVar);
                        obj = aVar.c;
                        i4 = a4;
                        break;
                    case MESSAGE:
                        int a5 = a(ah.a().a((Class) generatedExtension.getMessageDefaultInstance().getClass()), bArr, i2, i3, aVar);
                        obj = aVar.c;
                        i4 = a5;
                        break;
                    default:
                        i4 = i2;
                        break;
                }
            } else {
                int a6 = a(bArr, i2, aVar);
                if (generatedExtension.d.getEnumType().findValueByNumber(aVar.a) == null) {
                    UnknownFieldSetLite unknownFieldSetLite = extendableMessage.unknownFields;
                    if (unknownFieldSetLite == UnknownFieldSetLite.getDefaultInstance()) {
                        unknownFieldSetLite = UnknownFieldSetLite.a();
                        extendableMessage.unknownFields = unknownFieldSetLite;
                    }
                    ao.a(i5, aVar.a, unknownFieldSetLite, (ar<UT, UnknownFieldSetLite>) arVar);
                    return a6;
                }
                obj = Integer.valueOf(aVar.a);
                i4 = a6;
            }
            if (generatedExtension.isRepeated()) {
                fieldSet.b((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, obj);
                return i4;
            }
            switch (generatedExtension.getLiteType()) {
                case GROUP:
                case MESSAGE:
                    Object b4 = fieldSet.b((FieldSet<GeneratedMessageLite.a>) generatedExtension.d);
                    if (b4 != null) {
                        obj = Internal.a(b4, obj);
                        break;
                    }
                    break;
            }
            fieldSet.a((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, obj);
            return i4;
        }
        switch (generatedExtension.getLiteType()) {
            case DOUBLE:
                h hVar = new h();
                int f = f(bArr, i2, hVar, aVar);
                fieldSet.a((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, hVar);
                return f;
            case FLOAT:
                m mVar = new m();
                int e2 = e(bArr, i2, mVar, aVar);
                fieldSet.a((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, mVar);
                return e2;
            case INT64:
            case UINT64:
                r rVar = new r();
                int b5 = b(bArr, i2, rVar, aVar);
                fieldSet.a((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, rVar);
                return b5;
            case INT32:
            case UINT32:
                o oVar = new o();
                int a7 = a(bArr, i2, oVar, aVar);
                fieldSet.a((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, oVar);
                return a7;
            case FIXED64:
            case SFIXED64:
                r rVar2 = new r();
                int d = d(bArr, i2, rVar2, aVar);
                fieldSet.a((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, rVar2);
                return d;
            case FIXED32:
            case SFIXED32:
                o oVar2 = new o();
                int c2 = c(bArr, i2, oVar2, aVar);
                fieldSet.a((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, oVar2);
                return c2;
            case BOOL:
                e eVar = new e();
                int g = g(bArr, i2, eVar, aVar);
                fieldSet.a((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, eVar);
                return g;
            case SINT32:
                o oVar3 = new o();
                int h = h(bArr, i2, oVar3, aVar);
                fieldSet.a((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, oVar3);
                return h;
            case SINT64:
                r rVar3 = new r();
                int i6 = i(bArr, i2, rVar3, aVar);
                fieldSet.a((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, rVar3);
                return i6;
            case ENUM:
                o oVar4 = new o();
                int a8 = a(bArr, i2, oVar4, aVar);
                UnknownFieldSetLite unknownFieldSetLite2 = extendableMessage.unknownFields;
                if (unknownFieldSetLite2 == UnknownFieldSetLite.getDefaultInstance()) {
                    unknownFieldSetLite2 = null;
                }
                UnknownFieldSetLite unknownFieldSetLite3 = (UnknownFieldSetLite) ao.a(i5, (List<Integer>) oVar4, generatedExtension.d.getEnumType(), unknownFieldSetLite2, (ar<UT, UnknownFieldSetLite>) arVar);
                if (unknownFieldSetLite3 != null) {
                    extendableMessage.unknownFields = unknownFieldSetLite3;
                }
                fieldSet.a((FieldSet<GeneratedMessageLite.a>) generatedExtension.d, oVar4);
                return a8;
            default:
                throw new IllegalStateException("Type cannot be packed: " + generatedExtension.d.getLiteType());
        }
    }

    public static int a(int i, byte[] bArr, int i2, int i3, UnknownFieldSetLite unknownFieldSetLite, a aVar) throws InvalidProtocolBufferException {
        if (WireFormat.getTagFieldNumber(i) != 0) {
            int tagWireType = WireFormat.getTagWireType(i);
            if (tagWireType != 5) {
                switch (tagWireType) {
                    case 0:
                        int b = b(bArr, i2, aVar);
                        unknownFieldSetLite.a(i, Long.valueOf(aVar.b));
                        return b;
                    case 1:
                        unknownFieldSetLite.a(i, Long.valueOf(b(bArr, i2)));
                        return i2 + 8;
                    case 2:
                        int a2 = a(bArr, i2, aVar);
                        int i4 = aVar.a;
                        if (i4 < 0) {
                            throw InvalidProtocolBufferException.b();
                        } else if (i4 <= bArr.length - a2) {
                            if (i4 == 0) {
                                unknownFieldSetLite.a(i, ByteString.EMPTY);
                            } else {
                                unknownFieldSetLite.a(i, ByteString.copyFrom(bArr, a2, i4));
                            }
                            return a2 + i4;
                        } else {
                            throw InvalidProtocolBufferException.a();
                        }
                    case 3:
                        UnknownFieldSetLite a3 = UnknownFieldSetLite.a();
                        int i5 = (i & (-8)) | 4;
                        int i6 = 0;
                        while (true) {
                            if (i2 < i3) {
                                int a4 = a(bArr, i2, aVar);
                                int i7 = aVar.a;
                                if (i7 == i5) {
                                    i6 = i7;
                                    i2 = a4;
                                } else {
                                    i6 = i7;
                                    i2 = a(i7, bArr, a4, i3, a3, aVar);
                                }
                            }
                        }
                        if (i2 > i3 || i6 != i5) {
                            throw InvalidProtocolBufferException.i();
                        }
                        unknownFieldSetLite.a(i, a3);
                        return i2;
                    default:
                        throw InvalidProtocolBufferException.d();
                }
            } else {
                unknownFieldSetLite.a(i, Integer.valueOf(a(bArr, i2)));
                return i2 + 4;
            }
        } else {
            throw InvalidProtocolBufferException.d();
        }
    }

    public static int a(int i, byte[] bArr, int i2, int i3, a aVar) throws InvalidProtocolBufferException {
        if (WireFormat.getTagFieldNumber(i) != 0) {
            int tagWireType = WireFormat.getTagWireType(i);
            if (tagWireType == 5) {
                return i2 + 4;
            }
            switch (tagWireType) {
                case 0:
                    return b(bArr, i2, aVar);
                case 1:
                    return i2 + 8;
                case 2:
                    return a(bArr, i2, aVar) + aVar.a;
                case 3:
                    int i4 = (i & (-8)) | 4;
                    int i5 = 0;
                    while (i2 < i3) {
                        i2 = a(bArr, i2, aVar);
                        i5 = aVar.a;
                        if (i5 != i4) {
                            i2 = a(i5, bArr, i2, i3, aVar);
                        } else if (i2 > i3 && i5 == i4) {
                            return i2;
                        } else {
                            throw InvalidProtocolBufferException.i();
                        }
                    }
                    if (i2 > i3) {
                    }
                    throw InvalidProtocolBufferException.i();
                default:
                    throw InvalidProtocolBufferException.d();
            }
        } else {
            throw InvalidProtocolBufferException.d();
        }
    }
}
