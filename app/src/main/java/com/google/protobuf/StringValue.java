package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class StringValue extends GeneratedMessageLite<StringValue, Builder> implements StringValueOrBuilder {
    public static final int VALUE_FIELD_NUMBER = 1;
    private static final StringValue b;
    private static volatile Parser<StringValue> c;
    private String a = "";

    private StringValue() {
    }

    @Override // com.google.protobuf.StringValueOrBuilder
    public String getValue() {
        return this.a;
    }

    @Override // com.google.protobuf.StringValueOrBuilder
    public ByteString getValueBytes() {
        return ByteString.copyFromUtf8(this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        str.getClass();
        this.a = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        this.a = getDefaultInstance().getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        this.a = byteString.toStringUtf8();
    }

    public static StringValue parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (StringValue) GeneratedMessageLite.parseFrom(b, byteBuffer);
    }

    public static StringValue parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (StringValue) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
    }

    public static StringValue parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (StringValue) GeneratedMessageLite.parseFrom(b, byteString);
    }

    public static StringValue parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (StringValue) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
    }

    public static StringValue parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (StringValue) GeneratedMessageLite.parseFrom(b, bArr);
    }

    public static StringValue parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (StringValue) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
    }

    public static StringValue parseFrom(InputStream inputStream) throws IOException {
        return (StringValue) GeneratedMessageLite.parseFrom(b, inputStream);
    }

    public static StringValue parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (StringValue) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
    }

    public static StringValue parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (StringValue) parseDelimitedFrom(b, inputStream);
    }

    public static StringValue parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (StringValue) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
    }

    public static StringValue parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (StringValue) GeneratedMessageLite.parseFrom(b, codedInputStream);
    }

    public static StringValue parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (StringValue) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return b.createBuilder();
    }

    public static Builder newBuilder(StringValue stringValue) {
        return b.createBuilder(stringValue);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<StringValue, Builder> implements StringValueOrBuilder {
        private Builder() {
            super(StringValue.b);
        }

        @Override // com.google.protobuf.StringValueOrBuilder
        public String getValue() {
            return ((StringValue) this.instance).getValue();
        }

        @Override // com.google.protobuf.StringValueOrBuilder
        public ByteString getValueBytes() {
            return ((StringValue) this.instance).getValueBytes();
        }

        public Builder setValue(String str) {
            copyOnWrite();
            ((StringValue) this.instance).a(str);
            return this;
        }

        public Builder clearValue() {
            copyOnWrite();
            ((StringValue) this.instance).e();
            return this;
        }

        public Builder setValueBytes(ByteString byteString) {
            copyOnWrite();
            ((StringValue) this.instance).a(byteString);
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new StringValue();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ", new Object[]{"value_"});
            case GET_DEFAULT_INSTANCE:
                return b;
            case GET_PARSER:
                Parser<StringValue> parser = c;
                if (parser == null) {
                    synchronized (StringValue.class) {
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
        StringValue stringValue = new StringValue();
        b = stringValue;
        GeneratedMessageLite.registerDefaultInstance(StringValue.class, stringValue);
    }

    public static StringValue getDefaultInstance() {
        return b;
    }

    public static StringValue of(String str) {
        return newBuilder().setValue(str).build();
    }

    public static Parser<StringValue> parser() {
        return b.getParserForType();
    }
}
