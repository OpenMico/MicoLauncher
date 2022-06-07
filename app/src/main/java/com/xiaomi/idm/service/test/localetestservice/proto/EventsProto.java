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
public final class EventsProto {

    /* loaded from: classes3.dex */
    public interface SomeEventOrBuilder extends MessageLiteOrBuilder {
        int getParam();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private EventsProto() {
    }

    /* loaded from: classes3.dex */
    public static final class SomeEvent extends GeneratedMessageLite<SomeEvent, Builder> implements SomeEventOrBuilder {
        public static final int PARAM_FIELD_NUMBER = 1;
        private static final SomeEvent b;
        private static volatile Parser<SomeEvent> c;
        private int a;

        private SomeEvent() {
        }

        @Override // com.xiaomi.idm.service.test.localetestservice.proto.EventsProto.SomeEventOrBuilder
        public int getParam() {
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

        public static SomeEvent parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (SomeEvent) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static SomeEvent parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SomeEvent) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static SomeEvent parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (SomeEvent) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static SomeEvent parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SomeEvent) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static SomeEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (SomeEvent) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static SomeEvent parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SomeEvent) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static SomeEvent parseFrom(InputStream inputStream) throws IOException {
            return (SomeEvent) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static SomeEvent parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SomeEvent) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static SomeEvent parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (SomeEvent) parseDelimitedFrom(b, inputStream);
        }

        public static SomeEvent parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SomeEvent) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static SomeEvent parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (SomeEvent) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static SomeEvent parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SomeEvent) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(SomeEvent someEvent) {
            return b.createBuilder(someEvent);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<SomeEvent, Builder> implements SomeEventOrBuilder {
            private Builder() {
                super(SomeEvent.b);
            }

            @Override // com.xiaomi.idm.service.test.localetestservice.proto.EventsProto.SomeEventOrBuilder
            public int getParam() {
                return ((SomeEvent) this.instance).getParam();
            }

            public Builder setParam(int i) {
                copyOnWrite();
                ((SomeEvent) this.instance).b(i);
                return this;
            }

            public Builder clearParam() {
                copyOnWrite();
                ((SomeEvent) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new SomeEvent();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004", new Object[]{"param_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<SomeEvent> parser = c;
                    if (parser == null) {
                        synchronized (SomeEvent.class) {
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
            SomeEvent someEvent = new SomeEvent();
            b = someEvent;
            GeneratedMessageLite.registerDefaultInstance(SomeEvent.class, someEvent);
        }

        public static SomeEvent getDefaultInstance() {
            return b;
        }

        public static Parser<SomeEvent> parser() {
            return b.getParserForType();
        }
    }
}
