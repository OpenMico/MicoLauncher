package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.Option;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class Field extends GeneratedMessageLite<Field, Builder> implements FieldOrBuilder {
    public static final int CARDINALITY_FIELD_NUMBER = 2;
    public static final int DEFAULT_VALUE_FIELD_NUMBER = 11;
    public static final int JSON_NAME_FIELD_NUMBER = 10;
    public static final int KIND_FIELD_NUMBER = 1;
    public static final int NAME_FIELD_NUMBER = 4;
    public static final int NUMBER_FIELD_NUMBER = 3;
    public static final int ONEOF_INDEX_FIELD_NUMBER = 7;
    public static final int OPTIONS_FIELD_NUMBER = 9;
    public static final int PACKED_FIELD_NUMBER = 8;
    public static final int TYPE_URL_FIELD_NUMBER = 6;
    private static final Field k;
    private static volatile Parser<Field> l;
    private int a;
    private int b;
    private int c;
    private int f;
    private boolean g;
    private String d = "";
    private String e = "";
    private Internal.ProtobufList<Option> h = emptyProtobufList();
    private String i = "";
    private String j = "";

    private Field() {
    }

    /* loaded from: classes2.dex */
    public enum Kind implements Internal.EnumLite {
        TYPE_UNKNOWN(0),
        TYPE_DOUBLE(1),
        TYPE_FLOAT(2),
        TYPE_INT64(3),
        TYPE_UINT64(4),
        TYPE_INT32(5),
        TYPE_FIXED64(6),
        TYPE_FIXED32(7),
        TYPE_BOOL(8),
        TYPE_STRING(9),
        TYPE_GROUP(10),
        TYPE_MESSAGE(11),
        TYPE_BYTES(12),
        TYPE_UINT32(13),
        TYPE_ENUM(14),
        TYPE_SFIXED32(15),
        TYPE_SFIXED64(16),
        TYPE_SINT32(17),
        TYPE_SINT64(18),
        UNRECOGNIZED(-1);
        
        public static final int TYPE_BOOL_VALUE = 8;
        public static final int TYPE_BYTES_VALUE = 12;
        public static final int TYPE_DOUBLE_VALUE = 1;
        public static final int TYPE_ENUM_VALUE = 14;
        public static final int TYPE_FIXED32_VALUE = 7;
        public static final int TYPE_FIXED64_VALUE = 6;
        public static final int TYPE_FLOAT_VALUE = 2;
        public static final int TYPE_GROUP_VALUE = 10;
        public static final int TYPE_INT32_VALUE = 5;
        public static final int TYPE_INT64_VALUE = 3;
        public static final int TYPE_MESSAGE_VALUE = 11;
        public static final int TYPE_SFIXED32_VALUE = 15;
        public static final int TYPE_SFIXED64_VALUE = 16;
        public static final int TYPE_SINT32_VALUE = 17;
        public static final int TYPE_SINT64_VALUE = 18;
        public static final int TYPE_STRING_VALUE = 9;
        public static final int TYPE_UINT32_VALUE = 13;
        public static final int TYPE_UINT64_VALUE = 4;
        public static final int TYPE_UNKNOWN_VALUE = 0;
        private static final Internal.EnumLiteMap<Kind> a = new Internal.EnumLiteMap<Kind>() { // from class: com.google.protobuf.Field.Kind.1
            /* renamed from: a */
            public Kind findValueByNumber(int i) {
                return Kind.forNumber(i);
            }
        };
        private final int value;

        @Override // com.google.protobuf.Internal.EnumLite
        public final int getNumber() {
            if (this != UNRECOGNIZED) {
                return this.value;
            }
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }

        @Deprecated
        public static Kind valueOf(int i) {
            return forNumber(i);
        }

        public static Kind forNumber(int i) {
            switch (i) {
                case 0:
                    return TYPE_UNKNOWN;
                case 1:
                    return TYPE_DOUBLE;
                case 2:
                    return TYPE_FLOAT;
                case 3:
                    return TYPE_INT64;
                case 4:
                    return TYPE_UINT64;
                case 5:
                    return TYPE_INT32;
                case 6:
                    return TYPE_FIXED64;
                case 7:
                    return TYPE_FIXED32;
                case 8:
                    return TYPE_BOOL;
                case 9:
                    return TYPE_STRING;
                case 10:
                    return TYPE_GROUP;
                case 11:
                    return TYPE_MESSAGE;
                case 12:
                    return TYPE_BYTES;
                case 13:
                    return TYPE_UINT32;
                case 14:
                    return TYPE_ENUM;
                case 15:
                    return TYPE_SFIXED32;
                case 16:
                    return TYPE_SFIXED64;
                case 17:
                    return TYPE_SINT32;
                case 18:
                    return TYPE_SINT64;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<Kind> internalGetValueMap() {
            return a;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return a.a;
        }

        /* loaded from: classes2.dex */
        private static final class a implements Internal.EnumVerifier {
            static final Internal.EnumVerifier a = new a();

            private a() {
            }

            @Override // com.google.protobuf.Internal.EnumVerifier
            public boolean isInRange(int i) {
                return Kind.forNumber(i) != null;
            }
        }

        Kind(int i) {
            this.value = i;
        }
    }

    /* loaded from: classes2.dex */
    public enum Cardinality implements Internal.EnumLite {
        CARDINALITY_UNKNOWN(0),
        CARDINALITY_OPTIONAL(1),
        CARDINALITY_REQUIRED(2),
        CARDINALITY_REPEATED(3),
        UNRECOGNIZED(-1);
        
        public static final int CARDINALITY_OPTIONAL_VALUE = 1;
        public static final int CARDINALITY_REPEATED_VALUE = 3;
        public static final int CARDINALITY_REQUIRED_VALUE = 2;
        public static final int CARDINALITY_UNKNOWN_VALUE = 0;
        private static final Internal.EnumLiteMap<Cardinality> a = new Internal.EnumLiteMap<Cardinality>() { // from class: com.google.protobuf.Field.Cardinality.1
            /* renamed from: a */
            public Cardinality findValueByNumber(int i) {
                return Cardinality.forNumber(i);
            }
        };
        private final int value;

        @Override // com.google.protobuf.Internal.EnumLite
        public final int getNumber() {
            if (this != UNRECOGNIZED) {
                return this.value;
            }
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }

        @Deprecated
        public static Cardinality valueOf(int i) {
            return forNumber(i);
        }

        public static Cardinality forNumber(int i) {
            switch (i) {
                case 0:
                    return CARDINALITY_UNKNOWN;
                case 1:
                    return CARDINALITY_OPTIONAL;
                case 2:
                    return CARDINALITY_REQUIRED;
                case 3:
                    return CARDINALITY_REPEATED;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<Cardinality> internalGetValueMap() {
            return a;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return a.a;
        }

        /* loaded from: classes2.dex */
        private static final class a implements Internal.EnumVerifier {
            static final Internal.EnumVerifier a = new a();

            private a() {
            }

            @Override // com.google.protobuf.Internal.EnumVerifier
            public boolean isInRange(int i) {
                return Cardinality.forNumber(i) != null;
            }
        }

        Cardinality(int i) {
            this.value = i;
        }
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public int getKindValue() {
        return this.a;
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public Kind getKind() {
        Kind forNumber = Kind.forNumber(this.a);
        return forNumber == null ? Kind.UNRECOGNIZED : forNumber;
    }

    public void b(int i) {
        this.a = i;
    }

    public void a(Kind kind) {
        this.a = kind.getNumber();
    }

    public void e() {
        this.a = 0;
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public int getCardinalityValue() {
        return this.b;
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public Cardinality getCardinality() {
        Cardinality forNumber = Cardinality.forNumber(this.b);
        return forNumber == null ? Cardinality.UNRECOGNIZED : forNumber;
    }

    public void c(int i) {
        this.b = i;
    }

    public void a(Cardinality cardinality) {
        this.b = cardinality.getNumber();
    }

    public void f() {
        this.b = 0;
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public int getNumber() {
        return this.c;
    }

    public void d(int i) {
        this.c = i;
    }

    public void g() {
        this.c = 0;
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public String getName() {
        return this.d;
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.d);
    }

    public void a(String str) {
        str.getClass();
        this.d = str;
    }

    public void h() {
        this.d = getDefaultInstance().getName();
    }

    public void a(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        this.d = byteString.toStringUtf8();
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public String getTypeUrl() {
        return this.e;
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public ByteString getTypeUrlBytes() {
        return ByteString.copyFromUtf8(this.e);
    }

    public void b(String str) {
        str.getClass();
        this.e = str;
    }

    public void i() {
        this.e = getDefaultInstance().getTypeUrl();
    }

    public void b(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        this.e = byteString.toStringUtf8();
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public int getOneofIndex() {
        return this.f;
    }

    public void e(int i) {
        this.f = i;
    }

    public void j() {
        this.f = 0;
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public boolean getPacked() {
        return this.g;
    }

    public void a(boolean z) {
        this.g = z;
    }

    public void k() {
        this.g = false;
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public List<Option> getOptionsList() {
        return this.h;
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.h;
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public int getOptionsCount() {
        return this.h.size();
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public Option getOptions(int i) {
        return this.h.get(i);
    }

    public OptionOrBuilder getOptionsOrBuilder(int i) {
        return this.h.get(i);
    }

    private void l() {
        if (!this.h.isModifiable()) {
            this.h = GeneratedMessageLite.mutableCopy(this.h);
        }
    }

    public void a(int i, Option option) {
        option.getClass();
        l();
        this.h.set(i, option);
    }

    public void a(Option option) {
        option.getClass();
        l();
        this.h.add(option);
    }

    public void b(int i, Option option) {
        option.getClass();
        l();
        this.h.add(i, option);
    }

    public void a(Iterable<? extends Option> iterable) {
        l();
        AbstractMessageLite.addAll((Iterable) iterable, (List) this.h);
    }

    public void m() {
        this.h = emptyProtobufList();
    }

    public void f(int i) {
        l();
        this.h.remove(i);
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public String getJsonName() {
        return this.i;
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public ByteString getJsonNameBytes() {
        return ByteString.copyFromUtf8(this.i);
    }

    public void c(String str) {
        str.getClass();
        this.i = str;
    }

    public void n() {
        this.i = getDefaultInstance().getJsonName();
    }

    public void c(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        this.i = byteString.toStringUtf8();
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public String getDefaultValue() {
        return this.j;
    }

    @Override // com.google.protobuf.FieldOrBuilder
    public ByteString getDefaultValueBytes() {
        return ByteString.copyFromUtf8(this.j);
    }

    public void d(String str) {
        str.getClass();
        this.j = str;
    }

    public void o() {
        this.j = getDefaultInstance().getDefaultValue();
    }

    public void d(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        this.j = byteString.toStringUtf8();
    }

    public static Field parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.parseFrom(k, byteBuffer);
    }

    public static Field parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.parseFrom(k, byteBuffer, extensionRegistryLite);
    }

    public static Field parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.parseFrom(k, byteString);
    }

    public static Field parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.parseFrom(k, byteString, extensionRegistryLite);
    }

    public static Field parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.parseFrom(k, bArr);
    }

    public static Field parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Field) GeneratedMessageLite.parseFrom(k, bArr, extensionRegistryLite);
    }

    public static Field parseFrom(InputStream inputStream) throws IOException {
        return (Field) GeneratedMessageLite.parseFrom(k, inputStream);
    }

    public static Field parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Field) GeneratedMessageLite.parseFrom(k, inputStream, extensionRegistryLite);
    }

    public static Field parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Field) parseDelimitedFrom(k, inputStream);
    }

    public static Field parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Field) parseDelimitedFrom(k, inputStream, extensionRegistryLite);
    }

    public static Field parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Field) GeneratedMessageLite.parseFrom(k, codedInputStream);
    }

    public static Field parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Field) GeneratedMessageLite.parseFrom(k, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return k.createBuilder();
    }

    public static Builder newBuilder(Field field) {
        return k.createBuilder(field);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<Field, Builder> implements FieldOrBuilder {
        private Builder() {
            super(Field.k);
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public int getKindValue() {
            return ((Field) this.instance).getKindValue();
        }

        public Builder setKindValue(int i) {
            copyOnWrite();
            ((Field) this.instance).b(i);
            return this;
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public Kind getKind() {
            return ((Field) this.instance).getKind();
        }

        public Builder setKind(Kind kind) {
            copyOnWrite();
            ((Field) this.instance).a(kind);
            return this;
        }

        public Builder clearKind() {
            copyOnWrite();
            ((Field) this.instance).e();
            return this;
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public int getCardinalityValue() {
            return ((Field) this.instance).getCardinalityValue();
        }

        public Builder setCardinalityValue(int i) {
            copyOnWrite();
            ((Field) this.instance).c(i);
            return this;
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public Cardinality getCardinality() {
            return ((Field) this.instance).getCardinality();
        }

        public Builder setCardinality(Cardinality cardinality) {
            copyOnWrite();
            ((Field) this.instance).a(cardinality);
            return this;
        }

        public Builder clearCardinality() {
            copyOnWrite();
            ((Field) this.instance).f();
            return this;
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public int getNumber() {
            return ((Field) this.instance).getNumber();
        }

        public Builder setNumber(int i) {
            copyOnWrite();
            ((Field) this.instance).d(i);
            return this;
        }

        public Builder clearNumber() {
            copyOnWrite();
            ((Field) this.instance).g();
            return this;
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public String getName() {
            return ((Field) this.instance).getName();
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public ByteString getNameBytes() {
            return ((Field) this.instance).getNameBytes();
        }

        public Builder setName(String str) {
            copyOnWrite();
            ((Field) this.instance).a(str);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((Field) this.instance).h();
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            copyOnWrite();
            ((Field) this.instance).a(byteString);
            return this;
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public String getTypeUrl() {
            return ((Field) this.instance).getTypeUrl();
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public ByteString getTypeUrlBytes() {
            return ((Field) this.instance).getTypeUrlBytes();
        }

        public Builder setTypeUrl(String str) {
            copyOnWrite();
            ((Field) this.instance).b(str);
            return this;
        }

        public Builder clearTypeUrl() {
            copyOnWrite();
            ((Field) this.instance).i();
            return this;
        }

        public Builder setTypeUrlBytes(ByteString byteString) {
            copyOnWrite();
            ((Field) this.instance).b(byteString);
            return this;
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public int getOneofIndex() {
            return ((Field) this.instance).getOneofIndex();
        }

        public Builder setOneofIndex(int i) {
            copyOnWrite();
            ((Field) this.instance).e(i);
            return this;
        }

        public Builder clearOneofIndex() {
            copyOnWrite();
            ((Field) this.instance).j();
            return this;
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public boolean getPacked() {
            return ((Field) this.instance).getPacked();
        }

        public Builder setPacked(boolean z) {
            copyOnWrite();
            ((Field) this.instance).a(z);
            return this;
        }

        public Builder clearPacked() {
            copyOnWrite();
            ((Field) this.instance).k();
            return this;
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((Field) this.instance).getOptionsList());
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public int getOptionsCount() {
            return ((Field) this.instance).getOptionsCount();
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public Option getOptions(int i) {
            return ((Field) this.instance).getOptions(i);
        }

        public Builder setOptions(int i, Option option) {
            copyOnWrite();
            ((Field) this.instance).a(i, option);
            return this;
        }

        public Builder setOptions(int i, Option.Builder builder) {
            copyOnWrite();
            ((Field) this.instance).a(i, builder.build());
            return this;
        }

        public Builder addOptions(Option option) {
            copyOnWrite();
            ((Field) this.instance).a(option);
            return this;
        }

        public Builder addOptions(int i, Option option) {
            copyOnWrite();
            ((Field) this.instance).b(i, option);
            return this;
        }

        public Builder addOptions(Option.Builder builder) {
            copyOnWrite();
            ((Field) this.instance).a(builder.build());
            return this;
        }

        public Builder addOptions(int i, Option.Builder builder) {
            copyOnWrite();
            ((Field) this.instance).b(i, builder.build());
            return this;
        }

        public Builder addAllOptions(Iterable<? extends Option> iterable) {
            copyOnWrite();
            ((Field) this.instance).a(iterable);
            return this;
        }

        public Builder clearOptions() {
            copyOnWrite();
            ((Field) this.instance).m();
            return this;
        }

        public Builder removeOptions(int i) {
            copyOnWrite();
            ((Field) this.instance).f(i);
            return this;
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public String getJsonName() {
            return ((Field) this.instance).getJsonName();
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public ByteString getJsonNameBytes() {
            return ((Field) this.instance).getJsonNameBytes();
        }

        public Builder setJsonName(String str) {
            copyOnWrite();
            ((Field) this.instance).c(str);
            return this;
        }

        public Builder clearJsonName() {
            copyOnWrite();
            ((Field) this.instance).n();
            return this;
        }

        public Builder setJsonNameBytes(ByteString byteString) {
            copyOnWrite();
            ((Field) this.instance).c(byteString);
            return this;
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public String getDefaultValue() {
            return ((Field) this.instance).getDefaultValue();
        }

        @Override // com.google.protobuf.FieldOrBuilder
        public ByteString getDefaultValueBytes() {
            return ((Field) this.instance).getDefaultValueBytes();
        }

        public Builder setDefaultValue(String str) {
            copyOnWrite();
            ((Field) this.instance).d(str);
            return this;
        }

        public Builder clearDefaultValue() {
            copyOnWrite();
            ((Field) this.instance).o();
            return this;
        }

        public Builder setDefaultValueBytes(ByteString byteString) {
            copyOnWrite();
            ((Field) this.instance).d(byteString);
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Field();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(k, "\u0000\n\u0000\u0000\u0001\u000b\n\u0000\u0001\u0000\u0001\f\u0002\f\u0003\u0004\u0004Ȉ\u0006Ȉ\u0007\u0004\b\u0007\t\u001b\nȈ\u000bȈ", new Object[]{"kind_", "cardinality_", "number_", "name_", "typeUrl_", "oneofIndex_", "packed_", "options_", Option.class, "jsonName_", "defaultValue_"});
            case GET_DEFAULT_INSTANCE:
                return k;
            case GET_PARSER:
                Parser<Field> parser = l;
                if (parser == null) {
                    synchronized (Field.class) {
                        parser = l;
                        if (parser == null) {
                            parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(k);
                            l = parser;
                        }
                    }
                }
                return parser;
            case GET_MEMOIZED_IS_INITIALIZED:
                return (byte) 1;
            case SET_MEMOIZED_IS_INITIALIZED:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    static {
        Field field = new Field();
        k = field;
        GeneratedMessageLite.registerDefaultInstance(Field.class, field);
    }

    public static Field getDefaultInstance() {
        return k;
    }

    public static Parser<Field> parser() {
        return k.getParserForType();
    }
}
