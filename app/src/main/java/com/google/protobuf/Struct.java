package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Map;

/* loaded from: classes2.dex */
public final class Struct extends GeneratedMessageLite<Struct, Builder> implements StructOrBuilder {
    public static final int FIELDS_FIELD_NUMBER = 1;
    private static final Struct b;
    private static volatile Parser<Struct> c;
    private MapFieldLite<String, Value> a = MapFieldLite.emptyMapField();

    /* loaded from: classes2.dex */
    private static final class a {
        static final MapEntryLite<String, Value> a = MapEntryLite.newDefaultInstance(WireFormat.FieldType.STRING, "", WireFormat.FieldType.MESSAGE, Value.getDefaultInstance());
    }

    private Struct() {
    }

    private MapFieldLite<String, Value> e() {
        return this.a;
    }

    private MapFieldLite<String, Value> f() {
        if (!this.a.isMutable()) {
            this.a = this.a.mutableCopy();
        }
        return this.a;
    }

    @Override // com.google.protobuf.StructOrBuilder
    public int getFieldsCount() {
        return e().size();
    }

    @Override // com.google.protobuf.StructOrBuilder
    public boolean containsFields(String str) {
        str.getClass();
        return e().containsKey(str);
    }

    @Override // com.google.protobuf.StructOrBuilder
    @Deprecated
    public Map<String, Value> getFields() {
        return getFieldsMap();
    }

    @Override // com.google.protobuf.StructOrBuilder
    public Map<String, Value> getFieldsMap() {
        return Collections.unmodifiableMap(e());
    }

    @Override // com.google.protobuf.StructOrBuilder
    public Value getFieldsOrDefault(String str, Value value) {
        str.getClass();
        MapFieldLite<String, Value> e = e();
        return e.containsKey(str) ? e.get(str) : value;
    }

    @Override // com.google.protobuf.StructOrBuilder
    public Value getFieldsOrThrow(String str) {
        str.getClass();
        MapFieldLite<String, Value> e = e();
        if (e.containsKey(str)) {
            return e.get(str);
        }
        throw new IllegalArgumentException();
    }

    public Map<String, Value> g() {
        return f();
    }

    public static Struct parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Struct) GeneratedMessageLite.parseFrom(b, byteBuffer);
    }

    public static Struct parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Struct) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
    }

    public static Struct parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Struct) GeneratedMessageLite.parseFrom(b, byteString);
    }

    public static Struct parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Struct) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
    }

    public static Struct parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Struct) GeneratedMessageLite.parseFrom(b, bArr);
    }

    public static Struct parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Struct) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
    }

    public static Struct parseFrom(InputStream inputStream) throws IOException {
        return (Struct) GeneratedMessageLite.parseFrom(b, inputStream);
    }

    public static Struct parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Struct) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
    }

    public static Struct parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Struct) parseDelimitedFrom(b, inputStream);
    }

    public static Struct parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Struct) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
    }

    public static Struct parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Struct) GeneratedMessageLite.parseFrom(b, codedInputStream);
    }

    public static Struct parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Struct) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return b.createBuilder();
    }

    public static Builder newBuilder(Struct struct) {
        return b.createBuilder(struct);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<Struct, Builder> implements StructOrBuilder {
        private Builder() {
            super(Struct.b);
        }

        @Override // com.google.protobuf.StructOrBuilder
        public int getFieldsCount() {
            return ((Struct) this.instance).getFieldsMap().size();
        }

        @Override // com.google.protobuf.StructOrBuilder
        public boolean containsFields(String str) {
            str.getClass();
            return ((Struct) this.instance).getFieldsMap().containsKey(str);
        }

        public Builder clearFields() {
            copyOnWrite();
            ((Struct) this.instance).g().clear();
            return this;
        }

        public Builder removeFields(String str) {
            str.getClass();
            copyOnWrite();
            ((Struct) this.instance).g().remove(str);
            return this;
        }

        @Override // com.google.protobuf.StructOrBuilder
        @Deprecated
        public Map<String, Value> getFields() {
            return getFieldsMap();
        }

        @Override // com.google.protobuf.StructOrBuilder
        public Map<String, Value> getFieldsMap() {
            return Collections.unmodifiableMap(((Struct) this.instance).getFieldsMap());
        }

        @Override // com.google.protobuf.StructOrBuilder
        public Value getFieldsOrDefault(String str, Value value) {
            str.getClass();
            Map<String, Value> fieldsMap = ((Struct) this.instance).getFieldsMap();
            return fieldsMap.containsKey(str) ? fieldsMap.get(str) : value;
        }

        @Override // com.google.protobuf.StructOrBuilder
        public Value getFieldsOrThrow(String str) {
            str.getClass();
            Map<String, Value> fieldsMap = ((Struct) this.instance).getFieldsMap();
            if (fieldsMap.containsKey(str)) {
                return fieldsMap.get(str);
            }
            throw new IllegalArgumentException();
        }

        public Builder putFields(String str, Value value) {
            str.getClass();
            value.getClass();
            copyOnWrite();
            ((Struct) this.instance).g().put(str, value);
            return this;
        }

        public Builder putAllFields(Map<String, Value> map) {
            copyOnWrite();
            ((Struct) this.instance).g().putAll(map);
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Struct();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u00012", new Object[]{"fields_", a.a});
            case GET_DEFAULT_INSTANCE:
                return b;
            case GET_PARSER:
                Parser<Struct> parser = c;
                if (parser == null) {
                    synchronized (Struct.class) {
                        parser = c;
                        if (parser == null) {
                            parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(b);
                            c = parser;
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
        Struct struct = new Struct();
        b = struct;
        GeneratedMessageLite.registerDefaultInstance(Struct.class, struct);
    }

    public static Struct getDefaultInstance() {
        return b;
    }

    public static Parser<Struct> parser() {
        return b.getParserForType();
    }
}
