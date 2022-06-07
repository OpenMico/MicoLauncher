package com.xiaomi.idm.service.test.localetestservice.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public final class DataProto {

    /* loaded from: classes3.dex */
    public interface ResponseOrBuilder extends MessageLiteOrBuilder {
        String getStr();

        ByteString getStrBytes();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private DataProto() {
    }

    /* loaded from: classes3.dex */
    public static final class Response extends GeneratedMessageLite<Response, Builder> implements ResponseOrBuilder {
        public static final int STR_FIELD_NUMBER = 1;
        private static final Response b;
        private static volatile Parser<Response> c;
        private String a = "";

        private Response() {
        }

        @Override // com.xiaomi.idm.service.test.localetestservice.proto.DataProto.ResponseOrBuilder
        public String getStr() {
            return this.a;
        }

        @Override // com.xiaomi.idm.service.test.localetestservice.proto.DataProto.ResponseOrBuilder
        public ByteString getStrBytes() {
            return ByteString.copyFromUtf8(this.a);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(String str) {
            if (str != null) {
                this.a = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = getDefaultInstance().getStr();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.a = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static Response parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Response) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static Response parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Response) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static Response parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Response) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static Response parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Response) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static Response parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Response) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static Response parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Response) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static Response parseFrom(InputStream inputStream) throws IOException {
            return (Response) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static Response parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Response) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static Response parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Response) parseDelimitedFrom(b, inputStream);
        }

        public static Response parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Response) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static Response parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Response) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static Response parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Response) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(Response response) {
            return b.createBuilder(response);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<Response, Builder> implements ResponseOrBuilder {
            private Builder() {
                super(Response.b);
            }

            @Override // com.xiaomi.idm.service.test.localetestservice.proto.DataProto.ResponseOrBuilder
            public String getStr() {
                return ((Response) this.instance).getStr();
            }

            @Override // com.xiaomi.idm.service.test.localetestservice.proto.DataProto.ResponseOrBuilder
            public ByteString getStrBytes() {
                return ((Response) this.instance).getStrBytes();
            }

            public Builder setStr(String str) {
                copyOnWrite();
                ((Response) this.instance).a(str);
                return this;
            }

            public Builder clearStr() {
                copyOnWrite();
                ((Response) this.instance).e();
                return this;
            }

            public Builder setStrBytes(ByteString byteString) {
                copyOnWrite();
                ((Response) this.instance).a(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new Response();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ", new Object[]{"str_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<Response> parser = c;
                    if (parser == null) {
                        synchronized (Response.class) {
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
            Response response = new Response();
            b = response;
            GeneratedMessageLite.registerDefaultInstance(Response.class, response);
        }

        public static Response getDefaultInstance() {
            return b;
        }

        public static Parser<Response> parser() {
            return b.getParserForType();
        }
    }
}
