package com.google.protobuf;

import com.google.protobuf.Any;
import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class Option extends GeneratedMessageLite<Option, Builder> implements OptionOrBuilder {
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int VALUE_FIELD_NUMBER = 2;
    private static final Option c;
    private static volatile Parser<Option> d;
    private String a = "";
    private Any b;

    private Option() {
    }

    @Override // com.google.protobuf.OptionOrBuilder
    public String getName() {
        return this.a;
    }

    @Override // com.google.protobuf.OptionOrBuilder
    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.a);
    }

    public void a(String str) {
        str.getClass();
        this.a = str;
    }

    public void e() {
        this.a = getDefaultInstance().getName();
    }

    public void a(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        this.a = byteString.toStringUtf8();
    }

    @Override // com.google.protobuf.OptionOrBuilder
    public boolean hasValue() {
        return this.b != null;
    }

    @Override // com.google.protobuf.OptionOrBuilder
    public Any getValue() {
        Any any = this.b;
        return any == null ? Any.getDefaultInstance() : any;
    }

    public void a(Any any) {
        any.getClass();
        this.b = any;
    }

    public void b(Any any) {
        any.getClass();
        Any any2 = this.b;
        if (any2 == null || any2 == Any.getDefaultInstance()) {
            this.b = any;
        } else {
            this.b = Any.newBuilder(this.b).mergeFrom((Any.Builder) any).buildPartial();
        }
    }

    public void f() {
        this.b = null;
    }

    public static Option parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Option) GeneratedMessageLite.parseFrom(c, byteBuffer);
    }

    public static Option parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Option) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
    }

    public static Option parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Option) GeneratedMessageLite.parseFrom(c, byteString);
    }

    public static Option parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Option) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
    }

    public static Option parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Option) GeneratedMessageLite.parseFrom(c, bArr);
    }

    public static Option parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Option) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
    }

    public static Option parseFrom(InputStream inputStream) throws IOException {
        return (Option) GeneratedMessageLite.parseFrom(c, inputStream);
    }

    public static Option parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Option) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
    }

    public static Option parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Option) parseDelimitedFrom(c, inputStream);
    }

    public static Option parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Option) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
    }

    public static Option parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Option) GeneratedMessageLite.parseFrom(c, codedInputStream);
    }

    public static Option parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Option) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return c.createBuilder();
    }

    public static Builder newBuilder(Option option) {
        return c.createBuilder(option);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<Option, Builder> implements OptionOrBuilder {
        private Builder() {
            super(Option.c);
        }

        @Override // com.google.protobuf.OptionOrBuilder
        public String getName() {
            return ((Option) this.instance).getName();
        }

        @Override // com.google.protobuf.OptionOrBuilder
        public ByteString getNameBytes() {
            return ((Option) this.instance).getNameBytes();
        }

        public Builder setName(String str) {
            copyOnWrite();
            ((Option) this.instance).a(str);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((Option) this.instance).e();
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            copyOnWrite();
            ((Option) this.instance).a(byteString);
            return this;
        }

        @Override // com.google.protobuf.OptionOrBuilder
        public boolean hasValue() {
            return ((Option) this.instance).hasValue();
        }

        @Override // com.google.protobuf.OptionOrBuilder
        public Any getValue() {
            return ((Option) this.instance).getValue();
        }

        public Builder setValue(Any any) {
            copyOnWrite();
            ((Option) this.instance).a(any);
            return this;
        }

        public Builder setValue(Any.Builder builder) {
            copyOnWrite();
            ((Option) this.instance).a(builder.build());
            return this;
        }

        public Builder mergeValue(Any any) {
            copyOnWrite();
            ((Option) this.instance).b(any);
            return this;
        }

        public Builder clearValue() {
            copyOnWrite();
            ((Option) this.instance).f();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Option();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\t", new Object[]{"name_", "value_"});
            case GET_DEFAULT_INSTANCE:
                return c;
            case GET_PARSER:
                Parser<Option> parser = d;
                if (parser == null) {
                    synchronized (Option.class) {
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
        Option option = new Option();
        c = option;
        GeneratedMessageLite.registerDefaultInstance(Option.class, option);
    }

    public static Option getDefaultInstance() {
        return c;
    }

    public static Parser<Option> parser() {
        return c.getParserForType();
    }
}
