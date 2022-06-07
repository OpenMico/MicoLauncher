package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class BytesValue extends GeneratedMessageLite<BytesValue, Builder> implements BytesValueOrBuilder {
    public static final int VALUE_FIELD_NUMBER = 1;
    private static final BytesValue b;
    private static volatile Parser<BytesValue> c;
    private ByteString a = ByteString.EMPTY;

    private BytesValue() {
    }

    @Override // com.google.protobuf.BytesValueOrBuilder
    public ByteString getValue() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ByteString byteString) {
        byteString.getClass();
        this.a = byteString;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        this.a = getDefaultInstance().getValue();
    }

    public static BytesValue parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (BytesValue) GeneratedMessageLite.parseFrom(b, byteBuffer);
    }

    public static BytesValue parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (BytesValue) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
    }

    public static BytesValue parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (BytesValue) GeneratedMessageLite.parseFrom(b, byteString);
    }

    public static BytesValue parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (BytesValue) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
    }

    public static BytesValue parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (BytesValue) GeneratedMessageLite.parseFrom(b, bArr);
    }

    public static BytesValue parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (BytesValue) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
    }

    public static BytesValue parseFrom(InputStream inputStream) throws IOException {
        return (BytesValue) GeneratedMessageLite.parseFrom(b, inputStream);
    }

    public static BytesValue parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (BytesValue) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
    }

    public static BytesValue parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (BytesValue) parseDelimitedFrom(b, inputStream);
    }

    public static BytesValue parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (BytesValue) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
    }

    public static BytesValue parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (BytesValue) GeneratedMessageLite.parseFrom(b, codedInputStream);
    }

    public static BytesValue parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (BytesValue) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return b.createBuilder();
    }

    public static Builder newBuilder(BytesValue bytesValue) {
        return b.createBuilder(bytesValue);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<BytesValue, Builder> implements BytesValueOrBuilder {
        private Builder() {
            super(BytesValue.b);
        }

        @Override // com.google.protobuf.BytesValueOrBuilder
        public ByteString getValue() {
            return ((BytesValue) this.instance).getValue();
        }

        public Builder setValue(ByteString byteString) {
            copyOnWrite();
            ((BytesValue) this.instance).a(byteString);
            return this;
        }

        public Builder clearValue() {
            copyOnWrite();
            ((BytesValue) this.instance).e();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new BytesValue();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\n", new Object[]{"value_"});
            case GET_DEFAULT_INSTANCE:
                return b;
            case GET_PARSER:
                Parser<BytesValue> parser = c;
                if (parser == null) {
                    synchronized (BytesValue.class) {
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
        BytesValue bytesValue = new BytesValue();
        b = bytesValue;
        GeneratedMessageLite.registerDefaultInstance(BytesValue.class, bytesValue);
    }

    public static BytesValue getDefaultInstance() {
        return b;
    }

    public static BytesValue of(ByteString byteString) {
        return newBuilder().setValue(byteString).build();
    }

    public static Parser<BytesValue> parser() {
        return b.getParserForType();
    }
}
