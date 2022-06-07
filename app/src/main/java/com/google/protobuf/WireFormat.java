package com.google.protobuf;

import java.io.IOException;

/* loaded from: classes2.dex */
public final class WireFormat {
    public static final int WIRETYPE_END_GROUP = 4;
    public static final int WIRETYPE_FIXED32 = 5;
    public static final int WIRETYPE_FIXED64 = 1;
    public static final int WIRETYPE_LENGTH_DELIMITED = 2;
    public static final int WIRETYPE_START_GROUP = 3;
    public static final int WIRETYPE_VARINT = 0;
    static final int a = a(1, 3);
    static final int b = a(1, 4);
    static final int c = a(2, 0);
    static final int d = a(3, 2);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public enum a {
        LOOSE {
            @Override // com.google.protobuf.WireFormat.a
            Object a(CodedInputStream codedInputStream) throws IOException {
                return codedInputStream.readString();
            }
        },
        STRICT {
            @Override // com.google.protobuf.WireFormat.a
            Object a(CodedInputStream codedInputStream) throws IOException {
                return codedInputStream.readStringRequireUtf8();
            }
        },
        LAZY {
            @Override // com.google.protobuf.WireFormat.a
            Object a(CodedInputStream codedInputStream) throws IOException {
                return codedInputStream.readBytes();
            }
        };

        abstract Object a(CodedInputStream codedInputStream) throws IOException;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(int i, int i2) {
        return (i << 3) | i2;
    }

    public static int getTagFieldNumber(int i) {
        return i >>> 3;
    }

    public static int getTagWireType(int i) {
        return i & 7;
    }

    private WireFormat() {
    }

    /* loaded from: classes2.dex */
    public enum JavaType {
        INT(0),
        LONG(0L),
        FLOAT(Float.valueOf(0.0f)),
        DOUBLE(Double.valueOf(0.0d)),
        BOOLEAN(false),
        STRING(""),
        BYTE_STRING(ByteString.EMPTY),
        ENUM(null),
        MESSAGE(null);
        
        private final Object defaultDefault;

        JavaType(Object obj) {
            this.defaultDefault = obj;
        }
    }

    /* loaded from: classes2.dex */
    public enum FieldType {
        DOUBLE(JavaType.DOUBLE, 1),
        FLOAT(JavaType.FLOAT, 5),
        INT64(JavaType.LONG, 0),
        UINT64(JavaType.LONG, 0),
        INT32(JavaType.INT, 0),
        FIXED64(JavaType.LONG, 1),
        FIXED32(JavaType.INT, 5),
        BOOL(JavaType.BOOLEAN, 0),
        STRING(JavaType.STRING, 2) {
            @Override // com.google.protobuf.WireFormat.FieldType
            public boolean isPackable() {
                return false;
            }
        },
        GROUP(JavaType.MESSAGE, 3) {
            @Override // com.google.protobuf.WireFormat.FieldType
            public boolean isPackable() {
                return false;
            }
        },
        MESSAGE(JavaType.MESSAGE, 2) {
            @Override // com.google.protobuf.WireFormat.FieldType
            public boolean isPackable() {
                return false;
            }
        },
        BYTES(JavaType.BYTE_STRING, 2) {
            @Override // com.google.protobuf.WireFormat.FieldType
            public boolean isPackable() {
                return false;
            }
        },
        UINT32(JavaType.INT, 0),
        ENUM(JavaType.ENUM, 0),
        SFIXED32(JavaType.INT, 5),
        SFIXED64(JavaType.LONG, 1),
        SINT32(JavaType.INT, 0),
        SINT64(JavaType.LONG, 0);
        
        private final JavaType javaType;
        private final int wireType;

        public boolean isPackable() {
            return true;
        }

        FieldType(JavaType javaType, int i) {
            this.javaType = javaType;
            this.wireType = i;
        }

        public JavaType getJavaType() {
            return this.javaType;
        }

        public int getWireType() {
            return this.wireType;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object a(CodedInputStream codedInputStream, FieldType fieldType, a aVar) throws IOException {
        switch (fieldType) {
            case DOUBLE:
                return Double.valueOf(codedInputStream.readDouble());
            case FLOAT:
                return Float.valueOf(codedInputStream.readFloat());
            case INT64:
                return Long.valueOf(codedInputStream.readInt64());
            case UINT64:
                return Long.valueOf(codedInputStream.readUInt64());
            case INT32:
                return Integer.valueOf(codedInputStream.readInt32());
            case FIXED64:
                return Long.valueOf(codedInputStream.readFixed64());
            case FIXED32:
                return Integer.valueOf(codedInputStream.readFixed32());
            case BOOL:
                return Boolean.valueOf(codedInputStream.readBool());
            case BYTES:
                return codedInputStream.readBytes();
            case UINT32:
                return Integer.valueOf(codedInputStream.readUInt32());
            case SFIXED32:
                return Integer.valueOf(codedInputStream.readSFixed32());
            case SFIXED64:
                return Long.valueOf(codedInputStream.readSFixed64());
            case SINT32:
                return Integer.valueOf(codedInputStream.readSInt32());
            case SINT64:
                return Long.valueOf(codedInputStream.readSInt64());
            case STRING:
                return aVar.a(codedInputStream);
            case GROUP:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle nested groups.");
            case MESSAGE:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle embedded messages.");
            case ENUM:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle enums.");
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }
}
