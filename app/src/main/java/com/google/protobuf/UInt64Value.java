package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class UInt64Value extends GeneratedMessageLite<UInt64Value, Builder> implements UInt64ValueOrBuilder {
    public static final int VALUE_FIELD_NUMBER = 1;
    private static final UInt64Value b;
    private static volatile Parser<UInt64Value> c;
    private long a;

    private UInt64Value() {
    }

    @Override // com.google.protobuf.UInt64ValueOrBuilder
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

    public static UInt64Value parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (UInt64Value) GeneratedMessageLite.parseFrom(b, byteBuffer);
    }

    public static UInt64Value parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (UInt64Value) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
    }

    public static UInt64Value parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (UInt64Value) GeneratedMessageLite.parseFrom(b, byteString);
    }

    public static UInt64Value parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (UInt64Value) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
    }

    public static UInt64Value parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (UInt64Value) GeneratedMessageLite.parseFrom(b, bArr);
    }

    public static UInt64Value parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (UInt64Value) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
    }

    public static UInt64Value parseFrom(InputStream inputStream) throws IOException {
        return (UInt64Value) GeneratedMessageLite.parseFrom(b, inputStream);
    }

    public static UInt64Value parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (UInt64Value) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
    }

    public static UInt64Value parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (UInt64Value) parseDelimitedFrom(b, inputStream);
    }

    public static UInt64Value parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (UInt64Value) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
    }

    public static UInt64Value parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (UInt64Value) GeneratedMessageLite.parseFrom(b, codedInputStream);
    }

    public static UInt64Value parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (UInt64Value) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return b.createBuilder();
    }

    public static Builder newBuilder(UInt64Value uInt64Value) {
        return b.createBuilder(uInt64Value);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<UInt64Value, Builder> implements UInt64ValueOrBuilder {
        private Builder() {
            super(UInt64Value.b);
        }

        @Override // com.google.protobuf.UInt64ValueOrBuilder
        public long getValue() {
            return ((UInt64Value) this.instance).getValue();
        }

        public Builder setValue(long j) {
            copyOnWrite();
            ((UInt64Value) this.instance).a(j);
            return this;
        }

        public Builder clearValue() {
            copyOnWrite();
            ((UInt64Value) this.instance).e();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new UInt64Value();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0003", new Object[]{"value_"});
            case GET_DEFAULT_INSTANCE:
                return b;
            case GET_PARSER:
                Parser<UInt64Value> parser = c;
                if (parser == null) {
                    synchronized (UInt64Value.class) {
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
        UInt64Value uInt64Value = new UInt64Value();
        b = uInt64Value;
        GeneratedMessageLite.registerDefaultInstance(UInt64Value.class, uInt64Value);
    }

    public static UInt64Value getDefaultInstance() {
        return b;
    }

    public static UInt64Value of(long j) {
        return newBuilder().setValue(j).build();
    }

    public static Parser<UInt64Value> parser() {
        return b.getParserForType();
    }
}
