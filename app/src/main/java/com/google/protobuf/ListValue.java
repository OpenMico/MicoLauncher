package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.Value;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class ListValue extends GeneratedMessageLite<ListValue, Builder> implements ListValueOrBuilder {
    public static final int VALUES_FIELD_NUMBER = 1;
    private static final ListValue b;
    private static volatile Parser<ListValue> c;
    private Internal.ProtobufList<Value> a = emptyProtobufList();

    private ListValue() {
    }

    @Override // com.google.protobuf.ListValueOrBuilder
    public List<Value> getValuesList() {
        return this.a;
    }

    public List<? extends ValueOrBuilder> getValuesOrBuilderList() {
        return this.a;
    }

    @Override // com.google.protobuf.ListValueOrBuilder
    public int getValuesCount() {
        return this.a.size();
    }

    @Override // com.google.protobuf.ListValueOrBuilder
    public Value getValues(int i) {
        return this.a.get(i);
    }

    public ValueOrBuilder getValuesOrBuilder(int i) {
        return this.a.get(i);
    }

    private void e() {
        if (!this.a.isModifiable()) {
            this.a = GeneratedMessageLite.mutableCopy(this.a);
        }
    }

    public void a(int i, Value value) {
        value.getClass();
        e();
        this.a.set(i, value);
    }

    public void a(Value value) {
        value.getClass();
        e();
        this.a.add(value);
    }

    public void b(int i, Value value) {
        value.getClass();
        e();
        this.a.add(i, value);
    }

    public void a(Iterable<? extends Value> iterable) {
        e();
        AbstractMessageLite.addAll((Iterable) iterable, (List) this.a);
    }

    public void f() {
        this.a = emptyProtobufList();
    }

    public void b(int i) {
        e();
        this.a.remove(i);
    }

    public static ListValue parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (ListValue) GeneratedMessageLite.parseFrom(b, byteBuffer);
    }

    public static ListValue parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (ListValue) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
    }

    public static ListValue parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (ListValue) GeneratedMessageLite.parseFrom(b, byteString);
    }

    public static ListValue parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (ListValue) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
    }

    public static ListValue parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (ListValue) GeneratedMessageLite.parseFrom(b, bArr);
    }

    public static ListValue parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (ListValue) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
    }

    public static ListValue parseFrom(InputStream inputStream) throws IOException {
        return (ListValue) GeneratedMessageLite.parseFrom(b, inputStream);
    }

    public static ListValue parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (ListValue) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
    }

    public static ListValue parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (ListValue) parseDelimitedFrom(b, inputStream);
    }

    public static ListValue parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (ListValue) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
    }

    public static ListValue parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (ListValue) GeneratedMessageLite.parseFrom(b, codedInputStream);
    }

    public static ListValue parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (ListValue) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return b.createBuilder();
    }

    public static Builder newBuilder(ListValue listValue) {
        return b.createBuilder(listValue);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<ListValue, Builder> implements ListValueOrBuilder {
        private Builder() {
            super(ListValue.b);
        }

        @Override // com.google.protobuf.ListValueOrBuilder
        public List<Value> getValuesList() {
            return Collections.unmodifiableList(((ListValue) this.instance).getValuesList());
        }

        @Override // com.google.protobuf.ListValueOrBuilder
        public int getValuesCount() {
            return ((ListValue) this.instance).getValuesCount();
        }

        @Override // com.google.protobuf.ListValueOrBuilder
        public Value getValues(int i) {
            return ((ListValue) this.instance).getValues(i);
        }

        public Builder setValues(int i, Value value) {
            copyOnWrite();
            ((ListValue) this.instance).a(i, value);
            return this;
        }

        public Builder setValues(int i, Value.Builder builder) {
            copyOnWrite();
            ((ListValue) this.instance).a(i, builder.build());
            return this;
        }

        public Builder addValues(Value value) {
            copyOnWrite();
            ((ListValue) this.instance).a(value);
            return this;
        }

        public Builder addValues(int i, Value value) {
            copyOnWrite();
            ((ListValue) this.instance).b(i, value);
            return this;
        }

        public Builder addValues(Value.Builder builder) {
            copyOnWrite();
            ((ListValue) this.instance).a(builder.build());
            return this;
        }

        public Builder addValues(int i, Value.Builder builder) {
            copyOnWrite();
            ((ListValue) this.instance).b(i, builder.build());
            return this;
        }

        public Builder addAllValues(Iterable<? extends Value> iterable) {
            copyOnWrite();
            ((ListValue) this.instance).a(iterable);
            return this;
        }

        public Builder clearValues() {
            copyOnWrite();
            ((ListValue) this.instance).f();
            return this;
        }

        public Builder removeValues(int i) {
            copyOnWrite();
            ((ListValue) this.instance).b(i);
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new ListValue();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"values_", Value.class});
            case GET_DEFAULT_INSTANCE:
                return b;
            case GET_PARSER:
                Parser<ListValue> parser = c;
                if (parser == null) {
                    synchronized (ListValue.class) {
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
        ListValue listValue = new ListValue();
        b = listValue;
        GeneratedMessageLite.registerDefaultInstance(ListValue.class, listValue);
    }

    public static ListValue getDefaultInstance() {
        return b;
    }

    public static Parser<ListValue> parser() {
        return b.getParserForType();
    }
}
