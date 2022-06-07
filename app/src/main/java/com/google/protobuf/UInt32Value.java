package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class UInt32Value extends GeneratedMessageLite<UInt32Value, Builder> implements UInt32ValueOrBuilder {
    public static final int VALUE_FIELD_NUMBER = 1;
    private static final UInt32Value b;
    private static volatile Parser<UInt32Value> c;
    private int a;

    private UInt32Value() {
    }

    @Override // com.google.protobuf.UInt32ValueOrBuilder
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

    public static UInt32Value parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (UInt32Value) GeneratedMessageLite.parseFrom(b, byteBuffer);
    }

    public static UInt32Value parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (UInt32Value) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
    }

    public static UInt32Value parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (UInt32Value) GeneratedMessageLite.parseFrom(b, byteString);
    }

    public static UInt32Value parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (UInt32Value) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
    }

    public static UInt32Value parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (UInt32Value) GeneratedMessageLite.parseFrom(b, bArr);
    }

    public static UInt32Value parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (UInt32Value) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
    }

    public static UInt32Value parseFrom(InputStream inputStream) throws IOException {
        return (UInt32Value) GeneratedMessageLite.parseFrom(b, inputStream);
    }

    public static UInt32Value parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (UInt32Value) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
    }

    public static UInt32Value parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (UInt32Value) parseDelimitedFrom(b, inputStream);
    }

    public static UInt32Value parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (UInt32Value) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
    }

    public static UInt32Value parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (UInt32Value) GeneratedMessageLite.parseFrom(b, codedInputStream);
    }

    public static UInt32Value parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (UInt32Value) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return b.createBuilder();
    }

    public static Builder newBuilder(UInt32Value uInt32Value) {
        return b.createBuilder(uInt32Value);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<UInt32Value, Builder> implements UInt32ValueOrBuilder {
        private Builder() {
            super(UInt32Value.b);
        }

        @Override // com.google.protobuf.UInt32ValueOrBuilder
        public int getValue() {
            return ((UInt32Value) this.instance).getValue();
        }

        public Builder setValue(int i) {
            copyOnWrite();
            ((UInt32Value) this.instance).b(i);
            return this;
        }

        public Builder clearValue() {
            copyOnWrite();
            ((UInt32Value) this.instance).e();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new UInt32Value();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u000b", new Object[]{"value_"});
            case GET_DEFAULT_INSTANCE:
                return b;
            case GET_PARSER:
                Parser<UInt32Value> parser = c;
                if (parser == null) {
                    synchronized (UInt32Value.class) {
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
        UInt32Value uInt32Value = new UInt32Value();
        b = uInt32Value;
        GeneratedMessageLite.registerDefaultInstance(UInt32Value.class, uInt32Value);
    }

    public static UInt32Value getDefaultInstance() {
        return b;
    }

    public static UInt32Value of(int i) {
        return newBuilder().setValue(i).build();
    }

    public static Parser<UInt32Value> parser() {
        return b.getParserForType();
    }
}
