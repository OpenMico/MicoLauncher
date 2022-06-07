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
public final class ActionsProto {

    /* loaded from: classes3.dex */
    public interface GetSomeStringOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getParam();

        ByteString getParamBytes();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private ActionsProto() {
    }

    /* loaded from: classes3.dex */
    public static final class GetSomeString extends GeneratedMessageLite<GetSomeString, Builder> implements GetSomeStringOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int PARAM_FIELD_NUMBER = 2;
        private static final GetSomeString c;
        private static volatile Parser<GetSomeString> d;
        private int a;
        private String b = "";

        private GetSomeString() {
        }

        @Override // com.xiaomi.idm.service.test.localetestservice.proto.ActionsProto.GetSomeStringOrBuilder
        public int getAid() {
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

        @Override // com.xiaomi.idm.service.test.localetestservice.proto.ActionsProto.GetSomeStringOrBuilder
        public String getParam() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.test.localetestservice.proto.ActionsProto.GetSomeStringOrBuilder
        public ByteString getParamBytes() {
            return ByteString.copyFromUtf8(this.b);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(String str) {
            if (str != null) {
                this.b = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = getDefaultInstance().getParam();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static GetSomeString parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static GetSomeString parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static GetSomeString parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static GetSomeString parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static GetSomeString parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static GetSomeString parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static GetSomeString parseFrom(InputStream inputStream) throws IOException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static GetSomeString parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static GetSomeString parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetSomeString) parseDelimitedFrom(c, inputStream);
        }

        public static GetSomeString parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetSomeString) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static GetSomeString parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static GetSomeString parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(GetSomeString getSomeString) {
            return c.createBuilder(getSomeString);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<GetSomeString, Builder> implements GetSomeStringOrBuilder {
            private Builder() {
                super(GetSomeString.c);
            }

            @Override // com.xiaomi.idm.service.test.localetestservice.proto.ActionsProto.GetSomeStringOrBuilder
            public int getAid() {
                return ((GetSomeString) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((GetSomeString) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((GetSomeString) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.test.localetestservice.proto.ActionsProto.GetSomeStringOrBuilder
            public String getParam() {
                return ((GetSomeString) this.instance).getParam();
            }

            @Override // com.xiaomi.idm.service.test.localetestservice.proto.ActionsProto.GetSomeStringOrBuilder
            public ByteString getParamBytes() {
                return ((GetSomeString) this.instance).getParamBytes();
            }

            public Builder setParam(String str) {
                copyOnWrite();
                ((GetSomeString) this.instance).a(str);
                return this;
            }

            public Builder clearParam() {
                copyOnWrite();
                ((GetSomeString) this.instance).f();
                return this;
            }

            public Builder setParamBytes(ByteString byteString) {
                copyOnWrite();
                ((GetSomeString) this.instance).a(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new GetSomeString();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0002Ȉ", new Object[]{"aid_", "param_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<GetSomeString> parser = d;
                    if (parser == null) {
                        synchronized (GetSomeString.class) {
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
            GetSomeString getSomeString = new GetSomeString();
            c = getSomeString;
            GeneratedMessageLite.registerDefaultInstance(GetSomeString.class, getSomeString);
        }

        public static GetSomeString getDefaultInstance() {
            return c;
        }

        public static Parser<GetSomeString> parser() {
            return c.getParserForType();
        }
    }
}
