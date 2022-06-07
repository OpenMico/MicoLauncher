package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class Int32Value extends GeneratedMessageLite<Int32Value, Builder> implements Int32ValueOrBuilder {
    public static final int VALUE_FIELD_NUMBER = 1;
    private static final Int32Value b;
    private static volatile Parser<Int32Value> c;
    private int a;

    private Int32Value() {
    }

    @Override // com.google.protobuf.Int32ValueOrBuilder
    public int getValue() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i) {
        this.a = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        this.a = 0;
    }

    public static Int32Value parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Int32Value) GeneratedMessageLite.parseFrom(b, byteBuffer);
    }

    public static Int32Value parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Int32Value) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
    }

    public static Int32Value parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Int32Value) GeneratedMessageLite.parseFrom(b, byteString);
    }

    public static Int32Value parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Int32Value) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
    }

    public static Int32Value parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Int32Value) GeneratedMessageLite.parseFrom(b, bArr);
    }

    public static Int32Value parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Int32Value) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
    }

    public static Int32Value parseFrom(InputStream inputStream) throws IOException {
        return (Int32Value) GeneratedMessageLite.parseFrom(b, inputStream);
    }

    public static Int32Value parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Int32Value) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
    }

    public static Int32Value parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Int32Value) parseDelimitedFrom(b, inputStream);
    }

    public static Int32Value parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Int32Value) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
    }

    public static Int32Value parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Int32Value) GeneratedMessageLite.parseFrom(b, codedInputStream);
    }

    public static Int32Value parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Int32Value) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return b.createBuilder();
    }

    public static Builder newBuilder(Int32Value int32Value) {
        return b.createBuilder(int32Value);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<Int32Value, Builder> implements Int32ValueOrBuilder {
        private Builder() {
            super(Int32Value.b);
        }

        @Override // com.google.protobuf.Int32ValueOrBuilder
        public int getValue() {
            return ((Int32Value) this.instance).getValue();
        }

        public Builder setValue(int i) {
            copyOnWrite();
            ((Int32Value) this.instance).b(i);
            return this;
        }

        public Builder clearValue() {
            copyOnWrite();
            ((Int32Value) this.instance).e();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Int32Value();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004", new Object[]{"value_"});
            case GET_DEFAULT_INSTANCE:
                return b;
            case GET_PARSER:
                Parser<Int32Value> parser = c;
                if (parser == null) {
                    synchronized (Int32Value.class) {
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
        Int32Value int32Value = new Int32Value();
        b = int32Value;
        GeneratedMessageLite.registerDefaultInstance(Int32Value.class, int32Value);
    }

    public static Int32Value getDefaultInstance() {
        return b;
    }

    public static Int32Value of(int i) {
        return newBuilder().setValue(i).build();
    }

    public static Parser<Int32Value> parser() {
        return b.getParserForType();
    }
}
