package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class FloatValue extends GeneratedMessageLite<FloatValue, Builder> implements FloatValueOrBuilder {
    public static final int VALUE_FIELD_NUMBER = 1;
    private static final FloatValue b;
    private static volatile Parser<FloatValue> c;
    private float a;

    private FloatValue() {
    }

    @Override // com.google.protobuf.FloatValueOrBuilder
    public float getValue() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(float f) {
        this.a = f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        this.a = 0.0f;
    }

    public static FloatValue parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (FloatValue) GeneratedMessageLite.parseFrom(b, byteBuffer);
    }

    public static FloatValue parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (FloatValue) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
    }

    public static FloatValue parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (FloatValue) GeneratedMessageLite.parseFrom(b, byteString);
    }

    public static FloatValue parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (FloatValue) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
    }

    public static FloatValue parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (FloatValue) GeneratedMessageLite.parseFrom(b, bArr);
    }

    public static FloatValue parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (FloatValue) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
    }

    public static FloatValue parseFrom(InputStream inputStream) throws IOException {
        return (FloatValue) GeneratedMessageLite.parseFrom(b, inputStream);
    }

    public static FloatValue parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (FloatValue) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
    }

    public static FloatValue parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (FloatValue) parseDelimitedFrom(b, inputStream);
    }

    public static FloatValue parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (FloatValue) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
    }

    public static FloatValue parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (FloatValue) GeneratedMessageLite.parseFrom(b, codedInputStream);
    }

    public static FloatValue parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (FloatValue) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return b.createBuilder();
    }

    public static Builder newBuilder(FloatValue floatValue) {
        return b.createBuilder(floatValue);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<FloatValue, Builder> implements FloatValueOrBuilder {
        private Builder() {
            super(FloatValue.b);
        }

        @Override // com.google.protobuf.FloatValueOrBuilder
        public float getValue() {
            return ((FloatValue) this.instance).getValue();
        }

        public Builder setValue(float f) {
            copyOnWrite();
            ((FloatValue) this.instance).a(f);
            return this;
        }

        public Builder clearValue() {
            copyOnWrite();
            ((FloatValue) this.instance).e();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new FloatValue();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0001", new Object[]{"value_"});
            case GET_DEFAULT_INSTANCE:
                return b;
            case GET_PARSER:
                Parser<FloatValue> parser = c;
                if (parser == null) {
                    synchronized (FloatValue.class) {
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
        FloatValue floatValue = new FloatValue();
        b = floatValue;
        GeneratedMessageLite.registerDefaultInstance(FloatValue.class, floatValue);
    }

    public static FloatValue getDefaultInstance() {
        return b;
    }

    public static FloatValue of(float f) {
        return newBuilder().setValue(f).build();
    }

    public static Parser<FloatValue> parser() {
        return b.getParserForType();
    }
}
