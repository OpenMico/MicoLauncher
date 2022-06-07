package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class SourceContext extends GeneratedMessageLite<SourceContext, Builder> implements SourceContextOrBuilder {
    public static final int FILE_NAME_FIELD_NUMBER = 1;
    private static final SourceContext b;
    private static volatile Parser<SourceContext> c;
    private String a = "";

    private SourceContext() {
    }

    @Override // com.google.protobuf.SourceContextOrBuilder
    public String getFileName() {
        return this.a;
    }

    @Override // com.google.protobuf.SourceContextOrBuilder
    public ByteString getFileNameBytes() {
        return ByteString.copyFromUtf8(this.a);
    }

    public void a(String str) {
        str.getClass();
        this.a = str;
    }

    public void e() {
        this.a = getDefaultInstance().getFileName();
    }

    public void a(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        this.a = byteString.toStringUtf8();
    }

    public static SourceContext parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (SourceContext) GeneratedMessageLite.parseFrom(b, byteBuffer);
    }

    public static SourceContext parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (SourceContext) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
    }

    public static SourceContext parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (SourceContext) GeneratedMessageLite.parseFrom(b, byteString);
    }

    public static SourceContext parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (SourceContext) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
    }

    public static SourceContext parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (SourceContext) GeneratedMessageLite.parseFrom(b, bArr);
    }

    public static SourceContext parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (SourceContext) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
    }

    public static SourceContext parseFrom(InputStream inputStream) throws IOException {
        return (SourceContext) GeneratedMessageLite.parseFrom(b, inputStream);
    }

    public static SourceContext parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (SourceContext) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
    }

    public static SourceContext parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (SourceContext) parseDelimitedFrom(b, inputStream);
    }

    public static SourceContext parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (SourceContext) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
    }

    public static SourceContext parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (SourceContext) GeneratedMessageLite.parseFrom(b, codedInputStream);
    }

    public static SourceContext parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (SourceContext) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return b.createBuilder();
    }

    public static Builder newBuilder(SourceContext sourceContext) {
        return b.createBuilder(sourceContext);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<SourceContext, Builder> implements SourceContextOrBuilder {
        private Builder() {
            super(SourceContext.b);
        }

        @Override // com.google.protobuf.SourceContextOrBuilder
        public String getFileName() {
            return ((SourceContext) this.instance).getFileName();
        }

        @Override // com.google.protobuf.SourceContextOrBuilder
        public ByteString getFileNameBytes() {
            return ((SourceContext) this.instance).getFileNameBytes();
        }

        public Builder setFileName(String str) {
            copyOnWrite();
            ((SourceContext) this.instance).a(str);
            return this;
        }

        public Builder clearFileName() {
            copyOnWrite();
            ((SourceContext) this.instance).e();
            return this;
        }

        public Builder setFileNameBytes(ByteString byteString) {
            copyOnWrite();
            ((SourceContext) this.instance).a(byteString);
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new SourceContext();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ", new Object[]{"fileName_"});
            case GET_DEFAULT_INSTANCE:
                return b;
            case GET_PARSER:
                Parser<SourceContext> parser = c;
                if (parser == null) {
                    synchronized (SourceContext.class) {
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
        SourceContext sourceContext = new SourceContext();
        b = sourceContext;
        GeneratedMessageLite.registerDefaultInstance(SourceContext.class, sourceContext);
    }

    public static SourceContext getDefaultInstance() {
        return b;
    }

    public static Parser<SourceContext> parser() {
        return b.getParserForType();
    }
}
