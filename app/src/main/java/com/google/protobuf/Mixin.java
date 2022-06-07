package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class Mixin extends GeneratedMessageLite<Mixin, Builder> implements MixinOrBuilder {
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int ROOT_FIELD_NUMBER = 2;
    private static final Mixin c;
    private static volatile Parser<Mixin> d;
    private String a = "";
    private String b = "";

    private Mixin() {
    }

    @Override // com.google.protobuf.MixinOrBuilder
    public String getName() {
        return this.a;
    }

    @Override // com.google.protobuf.MixinOrBuilder
    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        str.getClass();
        this.a = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        this.a = getDefaultInstance().getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        this.a = byteString.toStringUtf8();
    }

    @Override // com.google.protobuf.MixinOrBuilder
    public String getRoot() {
        return this.b;
    }

    @Override // com.google.protobuf.MixinOrBuilder
    public ByteString getRootBytes() {
        return ByteString.copyFromUtf8(this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        str.getClass();
        this.b = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        this.b = getDefaultInstance().getRoot();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        this.b = byteString.toStringUtf8();
    }

    public static Mixin parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Mixin) GeneratedMessageLite.parseFrom(c, byteBuffer);
    }

    public static Mixin parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Mixin) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
    }

    public static Mixin parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Mixin) GeneratedMessageLite.parseFrom(c, byteString);
    }

    public static Mixin parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Mixin) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
    }

    public static Mixin parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Mixin) GeneratedMessageLite.parseFrom(c, bArr);
    }

    public static Mixin parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Mixin) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
    }

    public static Mixin parseFrom(InputStream inputStream) throws IOException {
        return (Mixin) GeneratedMessageLite.parseFrom(c, inputStream);
    }

    public static Mixin parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Mixin) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
    }

    public static Mixin parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Mixin) parseDelimitedFrom(c, inputStream);
    }

    public static Mixin parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Mixin) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
    }

    public static Mixin parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Mixin) GeneratedMessageLite.parseFrom(c, codedInputStream);
    }

    public static Mixin parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Mixin) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return c.createBuilder();
    }

    public static Builder newBuilder(Mixin mixin) {
        return c.createBuilder(mixin);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<Mixin, Builder> implements MixinOrBuilder {
        private Builder() {
            super(Mixin.c);
        }

        @Override // com.google.protobuf.MixinOrBuilder
        public String getName() {
            return ((Mixin) this.instance).getName();
        }

        @Override // com.google.protobuf.MixinOrBuilder
        public ByteString getNameBytes() {
            return ((Mixin) this.instance).getNameBytes();
        }

        public Builder setName(String str) {
            copyOnWrite();
            ((Mixin) this.instance).a(str);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((Mixin) this.instance).e();
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            copyOnWrite();
            ((Mixin) this.instance).a(byteString);
            return this;
        }

        @Override // com.google.protobuf.MixinOrBuilder
        public String getRoot() {
            return ((Mixin) this.instance).getRoot();
        }

        @Override // com.google.protobuf.MixinOrBuilder
        public ByteString getRootBytes() {
            return ((Mixin) this.instance).getRootBytes();
        }

        public Builder setRoot(String str) {
            copyOnWrite();
            ((Mixin) this.instance).b(str);
            return this;
        }

        public Builder clearRoot() {
            copyOnWrite();
            ((Mixin) this.instance).f();
            return this;
        }

        public Builder setRootBytes(ByteString byteString) {
            copyOnWrite();
            ((Mixin) this.instance).b(byteString);
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Mixin();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ", new Object[]{"name_", "root_"});
            case GET_DEFAULT_INSTANCE:
                return c;
            case GET_PARSER:
                Parser<Mixin> parser = d;
                if (parser == null) {
                    synchronized (Mixin.class) {
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
        Mixin mixin = new Mixin();
        c = mixin;
        GeneratedMessageLite.registerDefaultInstance(Mixin.class, mixin);
    }

    public static Mixin getDefaultInstance() {
        return c;
    }

    public static Parser<Mixin> parser() {
        return c.getParserForType();
    }
}
