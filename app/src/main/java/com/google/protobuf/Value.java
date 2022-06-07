package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.ListValue;
import com.google.protobuf.Struct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class Value extends GeneratedMessageLite<Value, Builder> implements ValueOrBuilder {
    public static final int BOOL_VALUE_FIELD_NUMBER = 4;
    public static final int LIST_VALUE_FIELD_NUMBER = 6;
    public static final int NULL_VALUE_FIELD_NUMBER = 1;
    public static final int NUMBER_VALUE_FIELD_NUMBER = 2;
    public static final int STRING_VALUE_FIELD_NUMBER = 3;
    public static final int STRUCT_VALUE_FIELD_NUMBER = 5;
    private static final Value c;
    private static volatile Parser<Value> d;
    private int a = 0;
    private Object b;

    private Value() {
    }

    /* loaded from: classes2.dex */
    public enum KindCase {
        NULL_VALUE(1),
        NUMBER_VALUE(2),
        STRING_VALUE(3),
        BOOL_VALUE(4),
        STRUCT_VALUE(5),
        LIST_VALUE(6),
        KIND_NOT_SET(0);
        
        private final int value;

        KindCase(int i) {
            this.value = i;
        }

        @Deprecated
        public static KindCase valueOf(int i) {
            return forNumber(i);
        }

        public static KindCase forNumber(int i) {
            switch (i) {
                case 0:
                    return KIND_NOT_SET;
                case 1:
                    return NULL_VALUE;
                case 2:
                    return NUMBER_VALUE;
                case 3:
                    return STRING_VALUE;
                case 4:
                    return BOOL_VALUE;
                case 5:
                    return STRUCT_VALUE;
                case 6:
                    return LIST_VALUE;
                default:
                    return null;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public KindCase getKindCase() {
        return KindCase.forNumber(this.a);
    }

    public void e() {
        this.a = 0;
        this.b = null;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public int getNullValueValue() {
        if (this.a == 1) {
            return ((Integer) this.b).intValue();
        }
        return 0;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public NullValue getNullValue() {
        if (this.a != 1) {
            return NullValue.NULL_VALUE;
        }
        NullValue forNumber = NullValue.forNumber(((Integer) this.b).intValue());
        return forNumber == null ? NullValue.UNRECOGNIZED : forNumber;
    }

    public void b(int i) {
        this.a = 1;
        this.b = Integer.valueOf(i);
    }

    public void a(NullValue nullValue) {
        this.b = Integer.valueOf(nullValue.getNumber());
        this.a = 1;
    }

    public void f() {
        if (this.a == 1) {
            this.a = 0;
            this.b = null;
        }
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public double getNumberValue() {
        if (this.a == 2) {
            return ((Double) this.b).doubleValue();
        }
        return 0.0d;
    }

    public void a(double d2) {
        this.a = 2;
        this.b = Double.valueOf(d2);
    }

    public void g() {
        if (this.a == 2) {
            this.a = 0;
            this.b = null;
        }
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public String getStringValue() {
        if (this.a == 3) {
            return (String) this.b;
        }
        return "";
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public ByteString getStringValueBytes() {
        String str = "";
        if (this.a == 3) {
            str = (String) this.b;
        }
        return ByteString.copyFromUtf8(str);
    }

    public void a(String str) {
        str.getClass();
        this.a = 3;
        this.b = str;
    }

    public void h() {
        if (this.a == 3) {
            this.a = 0;
            this.b = null;
        }
    }

    public void a(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        this.b = byteString.toStringUtf8();
        this.a = 3;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public boolean getBoolValue() {
        if (this.a == 4) {
            return ((Boolean) this.b).booleanValue();
        }
        return false;
    }

    public void a(boolean z) {
        this.a = 4;
        this.b = Boolean.valueOf(z);
    }

    public void i() {
        if (this.a == 4) {
            this.a = 0;
            this.b = null;
        }
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public boolean hasStructValue() {
        return this.a == 5;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public Struct getStructValue() {
        if (this.a == 5) {
            return (Struct) this.b;
        }
        return Struct.getDefaultInstance();
    }

    public void a(Struct struct) {
        struct.getClass();
        this.b = struct;
        this.a = 5;
    }

    public void b(Struct struct) {
        struct.getClass();
        if (this.a != 5 || this.b == Struct.getDefaultInstance()) {
            this.b = struct;
        } else {
            this.b = Struct.newBuilder((Struct) this.b).mergeFrom((Struct.Builder) struct).buildPartial();
        }
        this.a = 5;
    }

    public void j() {
        if (this.a == 5) {
            this.a = 0;
            this.b = null;
        }
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public boolean hasListValue() {
        return this.a == 6;
    }

    @Override // com.google.protobuf.ValueOrBuilder
    public ListValue getListValue() {
        if (this.a == 6) {
            return (ListValue) this.b;
        }
        return ListValue.getDefaultInstance();
    }

    public void a(ListValue listValue) {
        listValue.getClass();
        this.b = listValue;
        this.a = 6;
    }

    public void b(ListValue listValue) {
        listValue.getClass();
        if (this.a != 6 || this.b == ListValue.getDefaultInstance()) {
            this.b = listValue;
        } else {
            this.b = ListValue.newBuilder((ListValue) this.b).mergeFrom((ListValue.Builder) listValue).buildPartial();
        }
        this.a = 6;
    }

    public void k() {
        if (this.a == 6) {
            this.a = 0;
            this.b = null;
        }
    }

    public static Value parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(c, byteBuffer);
    }

    public static Value parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
    }

    public static Value parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(c, byteString);
    }

    public static Value parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
    }

    public static Value parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(c, bArr);
    }

    public static Value parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
    }

    public static Value parseFrom(InputStream inputStream) throws IOException {
        return (Value) GeneratedMessageLite.parseFrom(c, inputStream);
    }

    public static Value parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Value) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
    }

    public static Value parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Value) parseDelimitedFrom(c, inputStream);
    }

    public static Value parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Value) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
    }

    public static Value parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Value) GeneratedMessageLite.parseFrom(c, codedInputStream);
    }

    public static Value parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Value) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return c.createBuilder();
    }

    public static Builder newBuilder(Value value) {
        return c.createBuilder(value);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<Value, Builder> implements ValueOrBuilder {
        private Builder() {
            super(Value.c);
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public KindCase getKindCase() {
            return ((Value) this.instance).getKindCase();
        }

        public Builder clearKind() {
            copyOnWrite();
            ((Value) this.instance).e();
            return this;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public int getNullValueValue() {
            return ((Value) this.instance).getNullValueValue();
        }

        public Builder setNullValueValue(int i) {
            copyOnWrite();
            ((Value) this.instance).b(i);
            return this;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public NullValue getNullValue() {
            return ((Value) this.instance).getNullValue();
        }

        public Builder setNullValue(NullValue nullValue) {
            copyOnWrite();
            ((Value) this.instance).a(nullValue);
            return this;
        }

        public Builder clearNullValue() {
            copyOnWrite();
            ((Value) this.instance).f();
            return this;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public double getNumberValue() {
            return ((Value) this.instance).getNumberValue();
        }

        public Builder setNumberValue(double d) {
            copyOnWrite();
            ((Value) this.instance).a(d);
            return this;
        }

        public Builder clearNumberValue() {
            copyOnWrite();
            ((Value) this.instance).g();
            return this;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public String getStringValue() {
            return ((Value) this.instance).getStringValue();
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public ByteString getStringValueBytes() {
            return ((Value) this.instance).getStringValueBytes();
        }

        public Builder setStringValue(String str) {
            copyOnWrite();
            ((Value) this.instance).a(str);
            return this;
        }

        public Builder clearStringValue() {
            copyOnWrite();
            ((Value) this.instance).h();
            return this;
        }

        public Builder setStringValueBytes(ByteString byteString) {
            copyOnWrite();
            ((Value) this.instance).a(byteString);
            return this;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public boolean getBoolValue() {
            return ((Value) this.instance).getBoolValue();
        }

        public Builder setBoolValue(boolean z) {
            copyOnWrite();
            ((Value) this.instance).a(z);
            return this;
        }

        public Builder clearBoolValue() {
            copyOnWrite();
            ((Value) this.instance).i();
            return this;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public boolean hasStructValue() {
            return ((Value) this.instance).hasStructValue();
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public Struct getStructValue() {
            return ((Value) this.instance).getStructValue();
        }

        public Builder setStructValue(Struct struct) {
            copyOnWrite();
            ((Value) this.instance).a(struct);
            return this;
        }

        public Builder setStructValue(Struct.Builder builder) {
            copyOnWrite();
            ((Value) this.instance).a(builder.build());
            return this;
        }

        public Builder mergeStructValue(Struct struct) {
            copyOnWrite();
            ((Value) this.instance).b(struct);
            return this;
        }

        public Builder clearStructValue() {
            copyOnWrite();
            ((Value) this.instance).j();
            return this;
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public boolean hasListValue() {
            return ((Value) this.instance).hasListValue();
        }

        @Override // com.google.protobuf.ValueOrBuilder
        public ListValue getListValue() {
            return ((Value) this.instance).getListValue();
        }

        public Builder setListValue(ListValue listValue) {
            copyOnWrite();
            ((Value) this.instance).a(listValue);
            return this;
        }

        public Builder setListValue(ListValue.Builder builder) {
            copyOnWrite();
            ((Value) this.instance).a(builder.build());
            return this;
        }

        public Builder mergeListValue(ListValue listValue) {
            copyOnWrite();
            ((Value) this.instance).b(listValue);
            return this;
        }

        public Builder clearListValue() {
            copyOnWrite();
            ((Value) this.instance).k();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Value();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(c, "\u0000\u0006\u0001\u0000\u0001\u0006\u0006\u0000\u0000\u0000\u0001?\u0000\u00023\u0000\u0003Ȼ\u0000\u0004:\u0000\u0005<\u0000\u0006<\u0000", new Object[]{"kind_", "kindCase_", Struct.class, ListValue.class});
            case GET_DEFAULT_INSTANCE:
                return c;
            case GET_PARSER:
                Parser<Value> parser = d;
                if (parser == null) {
                    synchronized (Value.class) {
                        parser = d;
                        if (parser == null) {
                            parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(c);
                            d = parser;
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
        Value value = new Value();
        c = value;
        GeneratedMessageLite.registerDefaultInstance(Value.class, value);
    }

    public static Value getDefaultInstance() {
        return c;
    }

    public static Parser<Value> parser() {
        return c.getParserForType();
    }
}
