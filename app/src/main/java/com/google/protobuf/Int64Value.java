package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class Int64Value extends GeneratedMessageLite<Int64Value, Builder> implements Int64ValueOrBuilder {
    public static final int VALUE_FIELD_NUMBER = 1;
    private static final Int64Value b;
    private static volatile Parser<Int64Value> c;
    private long a;

    private Int64Value() {
    }

    @Override // com.google.protobuf.Int64ValueOrBuilder
    public long getValue() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(long j) {
        this.a = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        this.a = 0L;
    }

    public static Int64Value parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Int64Value) GeneratedMessageLite.parseFrom(b, byteBuffer);
    }

    public static Int64Value parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Int64Value) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
    }

    public static Int64Value parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Int64Value) GeneratedMessageLite.parseFrom(b, byteString);
    }

    public static Int64Value parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Int64Value) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
    }

    public static Int64Value parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Int64Value) GeneratedMessageLite.parseFrom(b, bArr);
    }

    public static Int64Value parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Int64Value) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
    }

    public static Int64Value parseFrom(InputStream inputStream) throws IOException {
        return (Int64Value) GeneratedMessageLite.parseFrom(b, inputStream);
    }

    public static Int64Value parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Int64Value) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
    }

    public static Int64Value parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Int64Value) parseDelimitedFrom(b, inputStream);
    }

    public static Int64Value parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Int64Value) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
    }

    public static Int64Value parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Int64Value) GeneratedMessageLite.parseFrom(b, codedInputStream);
    }

    public static Int64Value parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Int64Value) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return b.createBuilder();
    }

    public static Builder newBuilder(Int64Value int64Value) {
        return b.createBuilder(int64Value);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<Int64Value, Builder> implements Int64ValueOrBuilder {
        private Builder() {
            super(Int64Value.b);
        }

        @Override // com.google.protobuf.Int64ValueOrBuilder
        public long getValue() {
            return ((Int64Value) this.instance).getValue();
        }

        public Builder setValue(long j) {
            copyOnWrite();
            ((Int64Value) this.instance).a(j);
            return this;
        }

        public Builder clearValue() {
            copyOnWrite();
            ((Int64Value) this.instance).e();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Int64Value();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0002", new Object[]{"value_"});
            case GET_DEFAULT_INSTANCE:
                return b;
            case GET_PARSER:
                Parser<Int64Value> parser = c;
                if (parser == null) {
                    synchronized (Int64Value.class) {
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
        Int64Value int64Value = new Int64Value();
        b = int64Value;
        GeneratedMessageLite.registerDefaultInstance(Int64Value.class, int64Value);
    }

    public static Int64Value getDefaultInstance() {
        return b;
    }

    public static Int64Value of(long j) {
        return newBuilder().setValue(j).build();
    }

    public static Parser<Int64Value> parser() {
        return b.getParserForType();
    }
}
