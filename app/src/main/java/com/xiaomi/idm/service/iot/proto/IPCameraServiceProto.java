package com.xiaomi.idm.service.iot.proto;

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
public final class IPCameraServiceProto {

    /* loaded from: classes3.dex */
    public interface GetIpcSkeletonInfoOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getAppId();

        ByteString getAppIdBytes();

        String getServiceToken();

        ByteString getServiceTokenBytes();
    }

    /* loaded from: classes3.dex */
    public interface IPCResponseOrBuilder extends MessageLiteOrBuilder {
        int getCode();

        String getMessage();

        ByteString getMessageBytes();

        String getResponse();

        ByteString getResponseBytes();
    }

    /* loaded from: classes3.dex */
    public interface SkeletonEventOrBuilder extends MessageLiteOrBuilder {
        ByteString getBytes();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private IPCameraServiceProto() {
    }

    /* loaded from: classes3.dex */
    public static final class GetIpcSkeletonInfo extends GeneratedMessageLite<GetIpcSkeletonInfo, Builder> implements GetIpcSkeletonInfoOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int APPID_FIELD_NUMBER = 2;
        public static final int SERVICETOKEN_FIELD_NUMBER = 3;
        private static final GetIpcSkeletonInfo d;
        private static volatile Parser<GetIpcSkeletonInfo> e;
        private int a;
        private String b = "";
        private String c = "";

        private GetIpcSkeletonInfo() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.GetIpcSkeletonInfoOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.GetIpcSkeletonInfoOrBuilder
        public String getAppId() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.GetIpcSkeletonInfoOrBuilder
        public ByteString getAppIdBytes() {
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
            this.b = getDefaultInstance().getAppId();
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

        @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.GetIpcSkeletonInfoOrBuilder
        public String getServiceToken() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.GetIpcSkeletonInfoOrBuilder
        public ByteString getServiceTokenBytes() {
            return ByteString.copyFromUtf8(this.c);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(String str) {
            if (str != null) {
                this.c = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g() {
            this.c = getDefaultInstance().getServiceToken();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.c = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static GetIpcSkeletonInfo parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetIpcSkeletonInfo) GeneratedMessageLite.parseFrom(d, byteBuffer);
        }

        public static GetIpcSkeletonInfo parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetIpcSkeletonInfo) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
        }

        public static GetIpcSkeletonInfo parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetIpcSkeletonInfo) GeneratedMessageLite.parseFrom(d, byteString);
        }

        public static GetIpcSkeletonInfo parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetIpcSkeletonInfo) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
        }

        public static GetIpcSkeletonInfo parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetIpcSkeletonInfo) GeneratedMessageLite.parseFrom(d, bArr);
        }

        public static GetIpcSkeletonInfo parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetIpcSkeletonInfo) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
        }

        public static GetIpcSkeletonInfo parseFrom(InputStream inputStream) throws IOException {
            return (GetIpcSkeletonInfo) GeneratedMessageLite.parseFrom(d, inputStream);
        }

        public static GetIpcSkeletonInfo parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetIpcSkeletonInfo) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
        }

        public static GetIpcSkeletonInfo parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetIpcSkeletonInfo) parseDelimitedFrom(d, inputStream);
        }

        public static GetIpcSkeletonInfo parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetIpcSkeletonInfo) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
        }

        public static GetIpcSkeletonInfo parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetIpcSkeletonInfo) GeneratedMessageLite.parseFrom(d, codedInputStream);
        }

        public static GetIpcSkeletonInfo parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetIpcSkeletonInfo) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return d.createBuilder();
        }

        public static Builder newBuilder(GetIpcSkeletonInfo getIpcSkeletonInfo) {
            return d.createBuilder(getIpcSkeletonInfo);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<GetIpcSkeletonInfo, Builder> implements GetIpcSkeletonInfoOrBuilder {
            private Builder() {
                super(GetIpcSkeletonInfo.d);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.GetIpcSkeletonInfoOrBuilder
            public int getAid() {
                return ((GetIpcSkeletonInfo) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((GetIpcSkeletonInfo) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((GetIpcSkeletonInfo) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.GetIpcSkeletonInfoOrBuilder
            public String getAppId() {
                return ((GetIpcSkeletonInfo) this.instance).getAppId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.GetIpcSkeletonInfoOrBuilder
            public ByteString getAppIdBytes() {
                return ((GetIpcSkeletonInfo) this.instance).getAppIdBytes();
            }

            public Builder setAppId(String str) {
                copyOnWrite();
                ((GetIpcSkeletonInfo) this.instance).a(str);
                return this;
            }

            public Builder clearAppId() {
                copyOnWrite();
                ((GetIpcSkeletonInfo) this.instance).f();
                return this;
            }

            public Builder setAppIdBytes(ByteString byteString) {
                copyOnWrite();
                ((GetIpcSkeletonInfo) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.GetIpcSkeletonInfoOrBuilder
            public String getServiceToken() {
                return ((GetIpcSkeletonInfo) this.instance).getServiceToken();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.GetIpcSkeletonInfoOrBuilder
            public ByteString getServiceTokenBytes() {
                return ((GetIpcSkeletonInfo) this.instance).getServiceTokenBytes();
            }

            public Builder setServiceToken(String str) {
                copyOnWrite();
                ((GetIpcSkeletonInfo) this.instance).b(str);
                return this;
            }

            public Builder clearServiceToken() {
                copyOnWrite();
                ((GetIpcSkeletonInfo) this.instance).g();
                return this;
            }

            public Builder setServiceTokenBytes(ByteString byteString) {
                copyOnWrite();
                ((GetIpcSkeletonInfo) this.instance).b(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new GetIpcSkeletonInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ", new Object[]{"aid_", "appId_", "serviceToken_"});
                case GET_DEFAULT_INSTANCE:
                    return d;
                case GET_PARSER:
                    Parser<GetIpcSkeletonInfo> parser = e;
                    if (parser == null) {
                        synchronized (GetIpcSkeletonInfo.class) {
                            parser = e;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(d);
                                e = parser;
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
            GetIpcSkeletonInfo getIpcSkeletonInfo = new GetIpcSkeletonInfo();
            d = getIpcSkeletonInfo;
            GeneratedMessageLite.registerDefaultInstance(GetIpcSkeletonInfo.class, getIpcSkeletonInfo);
        }

        public static GetIpcSkeletonInfo getDefaultInstance() {
            return d;
        }

        public static Parser<GetIpcSkeletonInfo> parser() {
            return d.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IPCResponse extends GeneratedMessageLite<IPCResponse, Builder> implements IPCResponseOrBuilder {
        public static final int CODE_FIELD_NUMBER = 1;
        public static final int MESSAGE_FIELD_NUMBER = 2;
        public static final int RESPONSE_FIELD_NUMBER = 3;
        private static final IPCResponse d;
        private static volatile Parser<IPCResponse> e;
        private int a;
        private String b = "";
        private String c = "";

        private IPCResponse() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.IPCResponseOrBuilder
        public int getCode() {
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

        @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.IPCResponseOrBuilder
        public String getMessage() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.IPCResponseOrBuilder
        public ByteString getMessageBytes() {
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
            this.b = getDefaultInstance().getMessage();
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

        @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.IPCResponseOrBuilder
        public String getResponse() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.IPCResponseOrBuilder
        public ByteString getResponseBytes() {
            return ByteString.copyFromUtf8(this.c);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(String str) {
            if (str != null) {
                this.c = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g() {
            this.c = getDefaultInstance().getResponse();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.c = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static IPCResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IPCResponse) GeneratedMessageLite.parseFrom(d, byteBuffer);
        }

        public static IPCResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IPCResponse) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
        }

        public static IPCResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IPCResponse) GeneratedMessageLite.parseFrom(d, byteString);
        }

        public static IPCResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IPCResponse) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
        }

        public static IPCResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IPCResponse) GeneratedMessageLite.parseFrom(d, bArr);
        }

        public static IPCResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IPCResponse) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
        }

        public static IPCResponse parseFrom(InputStream inputStream) throws IOException {
            return (IPCResponse) GeneratedMessageLite.parseFrom(d, inputStream);
        }

        public static IPCResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IPCResponse) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
        }

        public static IPCResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IPCResponse) parseDelimitedFrom(d, inputStream);
        }

        public static IPCResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IPCResponse) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
        }

        public static IPCResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IPCResponse) GeneratedMessageLite.parseFrom(d, codedInputStream);
        }

        public static IPCResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IPCResponse) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return d.createBuilder();
        }

        public static Builder newBuilder(IPCResponse iPCResponse) {
            return d.createBuilder(iPCResponse);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IPCResponse, Builder> implements IPCResponseOrBuilder {
            private Builder() {
                super(IPCResponse.d);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.IPCResponseOrBuilder
            public int getCode() {
                return ((IPCResponse) this.instance).getCode();
            }

            public Builder setCode(int i) {
                copyOnWrite();
                ((IPCResponse) this.instance).b(i);
                return this;
            }

            public Builder clearCode() {
                copyOnWrite();
                ((IPCResponse) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.IPCResponseOrBuilder
            public String getMessage() {
                return ((IPCResponse) this.instance).getMessage();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.IPCResponseOrBuilder
            public ByteString getMessageBytes() {
                return ((IPCResponse) this.instance).getMessageBytes();
            }

            public Builder setMessage(String str) {
                copyOnWrite();
                ((IPCResponse) this.instance).a(str);
                return this;
            }

            public Builder clearMessage() {
                copyOnWrite();
                ((IPCResponse) this.instance).f();
                return this;
            }

            public Builder setMessageBytes(ByteString byteString) {
                copyOnWrite();
                ((IPCResponse) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.IPCResponseOrBuilder
            public String getResponse() {
                return ((IPCResponse) this.instance).getResponse();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.IPCResponseOrBuilder
            public ByteString getResponseBytes() {
                return ((IPCResponse) this.instance).getResponseBytes();
            }

            public Builder setResponse(String str) {
                copyOnWrite();
                ((IPCResponse) this.instance).b(str);
                return this;
            }

            public Builder clearResponse() {
                copyOnWrite();
                ((IPCResponse) this.instance).g();
                return this;
            }

            public Builder setResponseBytes(ByteString byteString) {
                copyOnWrite();
                ((IPCResponse) this.instance).b(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IPCResponse();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ", new Object[]{"code_", "message_", "response_"});
                case GET_DEFAULT_INSTANCE:
                    return d;
                case GET_PARSER:
                    Parser<IPCResponse> parser = e;
                    if (parser == null) {
                        synchronized (IPCResponse.class) {
                            parser = e;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(d);
                                e = parser;
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
            IPCResponse iPCResponse = new IPCResponse();
            d = iPCResponse;
            GeneratedMessageLite.registerDefaultInstance(IPCResponse.class, iPCResponse);
        }

        public static IPCResponse getDefaultInstance() {
            return d;
        }

        public static Parser<IPCResponse> parser() {
            return d.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class SkeletonEvent extends GeneratedMessageLite<SkeletonEvent, Builder> implements SkeletonEventOrBuilder {
        public static final int BYTES_FIELD_NUMBER = 1;
        private static final SkeletonEvent b;
        private static volatile Parser<SkeletonEvent> c;
        private ByteString a = ByteString.EMPTY;

        private SkeletonEvent() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.SkeletonEventOrBuilder
        public ByteString getBytes() {
            return this.a;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(ByteString byteString) {
            if (byteString != null) {
                this.a = byteString;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = getDefaultInstance().getBytes();
        }

        public static SkeletonEvent parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (SkeletonEvent) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static SkeletonEvent parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SkeletonEvent) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static SkeletonEvent parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (SkeletonEvent) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static SkeletonEvent parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SkeletonEvent) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static SkeletonEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (SkeletonEvent) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static SkeletonEvent parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SkeletonEvent) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static SkeletonEvent parseFrom(InputStream inputStream) throws IOException {
            return (SkeletonEvent) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static SkeletonEvent parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SkeletonEvent) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static SkeletonEvent parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (SkeletonEvent) parseDelimitedFrom(b, inputStream);
        }

        public static SkeletonEvent parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SkeletonEvent) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static SkeletonEvent parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (SkeletonEvent) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static SkeletonEvent parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SkeletonEvent) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(SkeletonEvent skeletonEvent) {
            return b.createBuilder(skeletonEvent);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<SkeletonEvent, Builder> implements SkeletonEventOrBuilder {
            private Builder() {
                super(SkeletonEvent.b);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IPCameraServiceProto.SkeletonEventOrBuilder
            public ByteString getBytes() {
                return ((SkeletonEvent) this.instance).getBytes();
            }

            public Builder setBytes(ByteString byteString) {
                copyOnWrite();
                ((SkeletonEvent) this.instance).a(byteString);
                return this;
            }

            public Builder clearBytes() {
                copyOnWrite();
                ((SkeletonEvent) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new SkeletonEvent();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\n", new Object[]{"bytes_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<SkeletonEvent> parser = c;
                    if (parser == null) {
                        synchronized (SkeletonEvent.class) {
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
            SkeletonEvent skeletonEvent = new SkeletonEvent();
            b = skeletonEvent;
            GeneratedMessageLite.registerDefaultInstance(SkeletonEvent.class, skeletonEvent);
        }

        public static SkeletonEvent getDefaultInstance() {
            return b;
        }

        public static Parser<SkeletonEvent> parser() {
            return b.getParserForType();
        }
    }
}
