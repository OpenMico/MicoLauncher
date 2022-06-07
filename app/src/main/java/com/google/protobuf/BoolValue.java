package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class BoolValue extends GeneratedMessageLite<BoolValue, Builder> implements BoolValueOrBuilder {
    public static final int VALUE_FIELD_NUMBER = 1;
    private static final BoolValue b;
    private static volatile Parser<BoolValue> c;
    private boolean a;

    private BoolValue() {
    }

    @Override // com.google.protobuf.BoolValueOrBuilder
    public boolean getValue() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        this.a = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        this.a = false;
    }

    public static BoolValue parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (BoolValue) GeneratedMessageLite.parseFrom(b, byteBuffer);
    }

    public static BoolValue parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (BoolValue) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
    }

    public static BoolValue parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (BoolValue) GeneratedMessageLite.parseFrom(b, byteString);
    }

    public static BoolValue parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (BoolValue) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
    }

    public static BoolValue parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (BoolValue) GeneratedMessageLite.parseFrom(b, bArr);
    }

    public static BoolValue parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (BoolValue) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
    }

    public static BoolValue parseFrom(InputStream inputStream) throws IOException {
        return (BoolValue) GeneratedMessageLite.parseFrom(b, inputStream);
    }

    public static BoolValue parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (BoolValue) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
    }

    public static BoolValue parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (BoolValue) parseDelimitedFrom(b, inputStream);
    }

    public static BoolValue parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (BoolValue) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
    }

    public static BoolValue parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (BoolValue) GeneratedMessageLite.parseFrom(b, codedInputStream);
    }

    public static BoolValue parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (BoolValue) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return b.createBuilder();
    }

    public static Builder newBuilder(BoolValue boolValue) {
        return b.createBuilder(boolValue);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<BoolValue, Builder> implements BoolValueOrBuilder {
        private Builder() {
            super(BoolValue.b);
        }

        @Override // com.google.protobuf.BoolValueOrBuilder
        public boolean getValue() {
            return ((BoolValue) this.instance).getValue();
        }

        public Builder setValue(boolean z) {
            copyOnWrite();
            ((BoolValue) this.instance).a(z);
            return this;
        }

        public Builder clearValue() {
            copyOnWrite();
            ((BoolValue) this.instance).e();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new BoolValue();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0007", new Object[]{"value_"});
            case GET_DEFAULT_INSTANCE:
                return b;
            case GET_PARSER:
                Parser<BoolValue> parser = c;
                if (parser == null) {
                    synchronized (BoolValue.class) {
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
        BoolValue boolValue = new BoolValue();
        b = boolValue;
        GeneratedMessageLite.registerDefaultInstance(BoolValue.class, boolValue);
    }

    public static BoolValue getDefaultInstance() {
        return b;
    }

    public static BoolValue of(boolean z) {
        return newBuilder().setValue(z).build();
    }

    public static Parser<BoolValue> parser() {
        return b.getParserForType();
    }
}
