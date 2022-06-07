package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class DoubleValue extends GeneratedMessageLite<DoubleValue, Builder> implements DoubleValueOrBuilder {
    public static final int VALUE_FIELD_NUMBER = 1;
    private static final DoubleValue b;
    private static volatile Parser<DoubleValue> c;
    private double a;

    private DoubleValue() {
    }

    @Override // com.google.protobuf.DoubleValueOrBuilder
    public double getValue() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(double d) {
        this.a = d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        this.a = 0.0d;
    }

    public static DoubleValue parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (DoubleValue) GeneratedMessageLite.parseFrom(b, byteBuffer);
    }

    public static DoubleValue parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (DoubleValue) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
    }

    public static DoubleValue parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (DoubleValue) GeneratedMessageLite.parseFrom(b, byteString);
    }

    public static DoubleValue parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (DoubleValue) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
    }

    public static DoubleValue parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (DoubleValue) GeneratedMessageLite.parseFrom(b, bArr);
    }

    public static DoubleValue parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (DoubleValue) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
    }

    public static DoubleValue parseFrom(InputStream inputStream) throws IOException {
        return (DoubleValue) GeneratedMessageLite.parseFrom(b, inputStream);
    }

    public static DoubleValue parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (DoubleValue) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
    }

    public static DoubleValue parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (DoubleValue) parseDelimitedFrom(b, inputStream);
    }

    public static DoubleValue parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (DoubleValue) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
    }

    public static DoubleValue parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (DoubleValue) GeneratedMessageLite.parseFrom(b, codedInputStream);
    }

    public static DoubleValue parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (DoubleValue) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return b.createBuilder();
    }

    public static Builder newBuilder(DoubleValue doubleValue) {
        return b.createBuilder(doubleValue);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<DoubleValue, Builder> implements DoubleValueOrBuilder {
        private Builder() {
            super(DoubleValue.b);
        }

        @Override // com.google.protobuf.DoubleValueOrBuilder
        public double getValue() {
            return ((DoubleValue) this.instance).getValue();
        }

        public Builder setValue(double d) {
            copyOnWrite();
            ((DoubleValue) this.instance).a(d);
            return this;
        }

        public Builder clearValue() {
            copyOnWrite();
            ((DoubleValue) this.instance).e();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new DoubleValue();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0000", new Object[]{"value_"});
            case GET_DEFAULT_INSTANCE:
                return b;
            case GET_PARSER:
                Parser<DoubleValue> parser = c;
                if (parser == null) {
                    synchronized (DoubleValue.class) {
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
        DoubleValue doubleValue = new DoubleValue();
        b = doubleValue;
        GeneratedMessageLite.registerDefaultInstance(DoubleValue.class, doubleValue);
    }

    public static DoubleValue getDefaultInstance() {
        return b;
    }

    public static DoubleValue of(double d) {
        return newBuilder().setValue(d).build();
    }

    public static Parser<DoubleValue> parser() {
        return b.getParserForType();
    }
}
