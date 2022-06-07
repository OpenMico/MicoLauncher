package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class Any extends GeneratedMessageLite<Any, Builder> implements AnyOrBuilder {
    public static final int TYPE_URL_FIELD_NUMBER = 1;
    public static final int VALUE_FIELD_NUMBER = 2;
    private static final Any c;
    private static volatile Parser<Any> d;
    private String a = "";
    private ByteString b = ByteString.EMPTY;

    private Any() {
    }

    @Override // com.google.protobuf.AnyOrBuilder
    public String getTypeUrl() {
        return this.a;
    }

    @Override // com.google.protobuf.AnyOrBuilder
    public ByteString getTypeUrlBytes() {
        return ByteString.copyFromUtf8(this.a);
    }

    public void a(String str) {
        str.getClass();
        this.a = str;
    }

    public void e() {
        this.a = getDefaultInstance().getTypeUrl();
    }

    public void a(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        this.a = byteString.toStringUtf8();
    }

    @Override // com.google.protobuf.AnyOrBuilder
    public ByteString getValue() {
        return this.b;
    }

    public void b(ByteString byteString) {
        byteString.getClass();
        this.b = byteString;
    }

    public void f() {
        this.b = getDefaultInstance().getValue();
    }

    public static Any parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Any) GeneratedMessageLite.parseFrom(c, byteBuffer);
    }

    public static Any parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Any) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
    }

    public static Any parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Any) GeneratedMessageLite.parseFrom(c, byteString);
    }

    public static Any parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Any) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
    }

    public static Any parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Any) GeneratedMessageLite.parseFrom(c, bArr);
    }

    public static Any parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Any) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
    }

    public static Any parseFrom(InputStream inputStream) throws IOException {
        return (Any) GeneratedMessageLite.parseFrom(c, inputStream);
    }

    public static Any parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Any) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
    }

    public static Any parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Any) parseDelimitedFrom(c, inputStream);
    }

    public static Any parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Any) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
    }

    public static Any parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Any) GeneratedMessageLite.parseFrom(c, codedInputStream);
    }

    public static Any parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Any) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return c.createBuilder();
    }

    public static Builder newBuilder(Any any) {
        return c.createBuilder(any);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<Any, Builder> implements AnyOrBuilder {
        private Builder() {
            super(Any.c);
        }

        @Override // com.google.protobuf.AnyOrBuilder
        public String getTypeUrl() {
            return ((Any) this.instance).getTypeUrl();
        }

        @Override // com.google.protobuf.AnyOrBuilder
        public ByteString getTypeUrlBytes() {
            return ((Any) this.instance).getTypeUrlBytes();
        }

        public Builder setTypeUrl(String str) {
            copyOnWrite();
            ((Any) this.instance).a(str);
            return this;
        }

        public Builder clearTypeUrl() {
            copyOnWrite();
            ((Any) this.instance).e();
            return this;
        }

        public Builder setTypeUrlBytes(ByteString byteString) {
            copyOnWrite();
            ((Any) this.instance).a(byteString);
            return this;
        }

        @Override // com.google.protobuf.AnyOrBuilder
        public ByteString getValue() {
            return ((Any) this.instance).getValue();
        }

        public Builder setValue(ByteString byteString) {
            copyOnWrite();
            ((Any) this.instance).b(byteString);
            return this;
        }

        public Builder clearValue() {
            copyOnWrite();
            ((Any) this.instance).f();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Any();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\n", new Object[]{"typeUrl_", "value_"});
            case GET_DEFAULT_INSTANCE:
                return c;
            case GET_PARSER:
                Parser<Any> parser = d;
                if (parser == null) {
                    synchronized (Any.class) {
                        parser = d;
                        if (parser == null) {
                            parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(c);
                            d = parser;
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
        Any any = new Any();
        c = any;
        GeneratedMessageLite.registerDefaultInstance(Any.class, any);
    }

    public static Any getDefaultInstance() {
        return c;
    }

    public static Parser<Any> parser() {
        return c.getParserForType();
    }
}
