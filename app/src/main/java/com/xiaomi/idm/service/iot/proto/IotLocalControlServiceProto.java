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
public final class IotLocalControlServiceProto {

    /* loaded from: classes3.dex */
    public interface ExeScenesOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getAppId();

        ByteString getAppIdBytes();

        String getSceneId();

        ByteString getSceneIdBytes();

        String getServiceToken();

        ByteString getServiceTokenBytes();
    }

    /* loaded from: classes3.dex */
    public interface GetDeviceInformationsOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getAppId();

        ByteString getAppIdBytes();

        String getDeviceId();

        ByteString getDeviceIdBytes();

        String getServiceToken();

        ByteString getServiceTokenBytes();
    }

    /* loaded from: classes3.dex */
    public interface GetDevicePropertiesOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getAppId();

        ByteString getAppIdBytes();

        String getPropertyId();

        ByteString getPropertyIdBytes();

        String getServiceToken();

        ByteString getServiceTokenBytes();
    }

    /* loaded from: classes3.dex */
    public interface GetDevicesOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getAppId();

        ByteString getAppIdBytes();

        boolean getIsLocal();

        String getServiceToken();

        ByteString getServiceTokenBytes();
    }

    /* loaded from: classes3.dex */
    public interface GetHomeFastCommandsOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getAppId();

        ByteString getAppIdBytes();

        String getServiceToken();

        ByteString getServiceTokenBytes();
    }

    /* loaded from: classes3.dex */
    public interface GetHomesOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getAppId();

        ByteString getAppIdBytes();

        String getServiceToken();

        ByteString getServiceTokenBytes();
    }

    /* loaded from: classes3.dex */
    public interface GetSceneAliasOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getAppId();

        ByteString getAppIdBytes();

        String getSceneName();

        ByteString getSceneNameBytes();

        String getServiceToken();

        ByteString getServiceTokenBytes();
    }

    /* loaded from: classes3.dex */
    public interface GetScenesOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getAppId();

        ByteString getAppIdBytes();

        String getServiceToken();

        ByteString getServiceTokenBytes();
    }

    /* loaded from: classes3.dex */
    public interface InvokeActionOrBuilder extends MessageLiteOrBuilder {
        String getActionBody();

        ByteString getActionBodyBytes();

        int getAid();

        String getAppId();

        ByteString getAppIdBytes();

        String getServiceToken();

        ByteString getServiceTokenBytes();
    }

    /* loaded from: classes3.dex */
    public interface IotResponseOrBuilder extends MessageLiteOrBuilder {
        int getCode();

        String getMessage();

        ByteString getMessageBytes();

        String getResponse();

        ByteString getResponseBytes();
    }

    /* loaded from: classes3.dex */
    public interface SetDevicePropertiesOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getAppId();

        ByteString getAppIdBytes();

        boolean getIsSort();

        String getPropertyBody();

        ByteString getPropertyBodyBytes();

        String getServiceToken();

        ByteString getServiceTokenBytes();
    }

    /* loaded from: classes3.dex */
    public interface SetTokenOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getAppId();

        ByteString getAppIdBytes();

        String getServiceToken();

        ByteString getServiceTokenBytes();
    }

    /* loaded from: classes3.dex */
    public interface StopOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getAppId();

        ByteString getAppIdBytes();

        String getServiceToken();

        ByteString getServiceTokenBytes();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private IotLocalControlServiceProto() {
    }

    /* loaded from: classes3.dex */
    public static final class GetDevices extends GeneratedMessageLite<GetDevices, Builder> implements GetDevicesOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int APPID_FIELD_NUMBER = 3;
        public static final int ISLOCAL_FIELD_NUMBER = 4;
        public static final int SERVICETOKEN_FIELD_NUMBER = 2;
        private static final GetDevices e;
        private static volatile Parser<GetDevices> f;
        private int a;
        private String b = "";
        private String c = "";
        private boolean d;

        private GetDevices() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicesOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicesOrBuilder
        public String getServiceToken() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicesOrBuilder
        public ByteString getServiceTokenBytes() {
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
            this.b = getDefaultInstance().getServiceToken();
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicesOrBuilder
        public String getAppId() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicesOrBuilder
        public ByteString getAppIdBytes() {
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
            this.c = getDefaultInstance().getAppId();
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicesOrBuilder
        public boolean getIsLocal() {
            return this.d;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(boolean z) {
            this.d = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.d = false;
        }

        public static GetDevices parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetDevices) GeneratedMessageLite.parseFrom(e, byteBuffer);
        }

        public static GetDevices parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetDevices) GeneratedMessageLite.parseFrom(e, byteBuffer, extensionRegistryLite);
        }

        public static GetDevices parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetDevices) GeneratedMessageLite.parseFrom(e, byteString);
        }

        public static GetDevices parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetDevices) GeneratedMessageLite.parseFrom(e, byteString, extensionRegistryLite);
        }

        public static GetDevices parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetDevices) GeneratedMessageLite.parseFrom(e, bArr);
        }

        public static GetDevices parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetDevices) GeneratedMessageLite.parseFrom(e, bArr, extensionRegistryLite);
        }

        public static GetDevices parseFrom(InputStream inputStream) throws IOException {
            return (GetDevices) GeneratedMessageLite.parseFrom(e, inputStream);
        }

        public static GetDevices parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetDevices) GeneratedMessageLite.parseFrom(e, inputStream, extensionRegistryLite);
        }

        public static GetDevices parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetDevices) parseDelimitedFrom(e, inputStream);
        }

        public static GetDevices parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetDevices) parseDelimitedFrom(e, inputStream, extensionRegistryLite);
        }

        public static GetDevices parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetDevices) GeneratedMessageLite.parseFrom(e, codedInputStream);
        }

        public static GetDevices parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetDevices) GeneratedMessageLite.parseFrom(e, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return e.createBuilder();
        }

        public static Builder newBuilder(GetDevices getDevices) {
            return e.createBuilder(getDevices);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<GetDevices, Builder> implements GetDevicesOrBuilder {
            private Builder() {
                super(GetDevices.e);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicesOrBuilder
            public int getAid() {
                return ((GetDevices) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((GetDevices) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((GetDevices) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicesOrBuilder
            public String getServiceToken() {
                return ((GetDevices) this.instance).getServiceToken();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicesOrBuilder
            public ByteString getServiceTokenBytes() {
                return ((GetDevices) this.instance).getServiceTokenBytes();
            }

            public Builder setServiceToken(String str) {
                copyOnWrite();
                ((GetDevices) this.instance).a(str);
                return this;
            }

            public Builder clearServiceToken() {
                copyOnWrite();
                ((GetDevices) this.instance).f();
                return this;
            }

            public Builder setServiceTokenBytes(ByteString byteString) {
                copyOnWrite();
                ((GetDevices) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicesOrBuilder
            public String getAppId() {
                return ((GetDevices) this.instance).getAppId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicesOrBuilder
            public ByteString getAppIdBytes() {
                return ((GetDevices) this.instance).getAppIdBytes();
            }

            public Builder setAppId(String str) {
                copyOnWrite();
                ((GetDevices) this.instance).b(str);
                return this;
            }

            public Builder clearAppId() {
                copyOnWrite();
                ((GetDevices) this.instance).g();
                return this;
            }

            public Builder setAppIdBytes(ByteString byteString) {
                copyOnWrite();
                ((GetDevices) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicesOrBuilder
            public boolean getIsLocal() {
                return ((GetDevices) this.instance).getIsLocal();
            }

            public Builder setIsLocal(boolean z) {
                copyOnWrite();
                ((GetDevices) this.instance).a(z);
                return this;
            }

            public Builder clearIsLocal() {
                copyOnWrite();
                ((GetDevices) this.instance).h();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new GetDevices();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(e, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ\u0004\u0007", new Object[]{"aid_", "serviceToken_", "appId_", "isLocal_"});
                case GET_DEFAULT_INSTANCE:
                    return e;
                case GET_PARSER:
                    Parser<GetDevices> parser = f;
                    if (parser == null) {
                        synchronized (GetDevices.class) {
                            parser = f;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(e);
                                f = parser;
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
            GetDevices getDevices = new GetDevices();
            e = getDevices;
            GeneratedMessageLite.registerDefaultInstance(GetDevices.class, getDevices);
        }

        public static GetDevices getDefaultInstance() {
            return e;
        }

        public static Parser<GetDevices> parser() {
            return e.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class GetHomes extends GeneratedMessageLite<GetHomes, Builder> implements GetHomesOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int APPID_FIELD_NUMBER = 3;
        public static final int SERVICETOKEN_FIELD_NUMBER = 2;
        private static final GetHomes d;
        private static volatile Parser<GetHomes> e;
        private int a;
        private String b = "";
        private String c = "";

        private GetHomes() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomesOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomesOrBuilder
        public String getServiceToken() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomesOrBuilder
        public ByteString getServiceTokenBytes() {
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
            this.b = getDefaultInstance().getServiceToken();
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomesOrBuilder
        public String getAppId() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomesOrBuilder
        public ByteString getAppIdBytes() {
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
            this.c = getDefaultInstance().getAppId();
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

        public static GetHomes parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetHomes) GeneratedMessageLite.parseFrom(d, byteBuffer);
        }

        public static GetHomes parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetHomes) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
        }

        public static GetHomes parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetHomes) GeneratedMessageLite.parseFrom(d, byteString);
        }

        public static GetHomes parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetHomes) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
        }

        public static GetHomes parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetHomes) GeneratedMessageLite.parseFrom(d, bArr);
        }

        public static GetHomes parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetHomes) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
        }

        public static GetHomes parseFrom(InputStream inputStream) throws IOException {
            return (GetHomes) GeneratedMessageLite.parseFrom(d, inputStream);
        }

        public static GetHomes parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetHomes) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
        }

        public static GetHomes parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetHomes) parseDelimitedFrom(d, inputStream);
        }

        public static GetHomes parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetHomes) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
        }

        public static GetHomes parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetHomes) GeneratedMessageLite.parseFrom(d, codedInputStream);
        }

        public static GetHomes parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetHomes) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return d.createBuilder();
        }

        public static Builder newBuilder(GetHomes getHomes) {
            return d.createBuilder(getHomes);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<GetHomes, Builder> implements GetHomesOrBuilder {
            private Builder() {
                super(GetHomes.d);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomesOrBuilder
            public int getAid() {
                return ((GetHomes) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((GetHomes) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((GetHomes) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomesOrBuilder
            public String getServiceToken() {
                return ((GetHomes) this.instance).getServiceToken();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomesOrBuilder
            public ByteString getServiceTokenBytes() {
                return ((GetHomes) this.instance).getServiceTokenBytes();
            }

            public Builder setServiceToken(String str) {
                copyOnWrite();
                ((GetHomes) this.instance).a(str);
                return this;
            }

            public Builder clearServiceToken() {
                copyOnWrite();
                ((GetHomes) this.instance).f();
                return this;
            }

            public Builder setServiceTokenBytes(ByteString byteString) {
                copyOnWrite();
                ((GetHomes) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomesOrBuilder
            public String getAppId() {
                return ((GetHomes) this.instance).getAppId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomesOrBuilder
            public ByteString getAppIdBytes() {
                return ((GetHomes) this.instance).getAppIdBytes();
            }

            public Builder setAppId(String str) {
                copyOnWrite();
                ((GetHomes) this.instance).b(str);
                return this;
            }

            public Builder clearAppId() {
                copyOnWrite();
                ((GetHomes) this.instance).g();
                return this;
            }

            public Builder setAppIdBytes(ByteString byteString) {
                copyOnWrite();
                ((GetHomes) this.instance).b(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new GetHomes();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ", new Object[]{"aid_", "serviceToken_", "appId_"});
                case GET_DEFAULT_INSTANCE:
                    return d;
                case GET_PARSER:
                    Parser<GetHomes> parser = e;
                    if (parser == null) {
                        synchronized (GetHomes.class) {
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
            GetHomes getHomes = new GetHomes();
            d = getHomes;
            GeneratedMessageLite.registerDefaultInstance(GetHomes.class, getHomes);
        }

        public static GetHomes getDefaultInstance() {
            return d;
        }

        public static Parser<GetHomes> parser() {
            return d.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class GetScenes extends GeneratedMessageLite<GetScenes, Builder> implements GetScenesOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int APPID_FIELD_NUMBER = 3;
        public static final int SERVICETOKEN_FIELD_NUMBER = 2;
        private static final GetScenes d;
        private static volatile Parser<GetScenes> e;
        private int a;
        private String b = "";
        private String c = "";

        private GetScenes() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetScenesOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetScenesOrBuilder
        public String getServiceToken() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetScenesOrBuilder
        public ByteString getServiceTokenBytes() {
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
            this.b = getDefaultInstance().getServiceToken();
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetScenesOrBuilder
        public String getAppId() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetScenesOrBuilder
        public ByteString getAppIdBytes() {
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
            this.c = getDefaultInstance().getAppId();
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

        public static GetScenes parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetScenes) GeneratedMessageLite.parseFrom(d, byteBuffer);
        }

        public static GetScenes parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetScenes) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
        }

        public static GetScenes parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetScenes) GeneratedMessageLite.parseFrom(d, byteString);
        }

        public static GetScenes parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetScenes) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
        }

        public static GetScenes parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetScenes) GeneratedMessageLite.parseFrom(d, bArr);
        }

        public static GetScenes parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetScenes) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
        }

        public static GetScenes parseFrom(InputStream inputStream) throws IOException {
            return (GetScenes) GeneratedMessageLite.parseFrom(d, inputStream);
        }

        public static GetScenes parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetScenes) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
        }

        public static GetScenes parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetScenes) parseDelimitedFrom(d, inputStream);
        }

        public static GetScenes parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetScenes) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
        }

        public static GetScenes parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetScenes) GeneratedMessageLite.parseFrom(d, codedInputStream);
        }

        public static GetScenes parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetScenes) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return d.createBuilder();
        }

        public static Builder newBuilder(GetScenes getScenes) {
            return d.createBuilder(getScenes);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<GetScenes, Builder> implements GetScenesOrBuilder {
            private Builder() {
                super(GetScenes.d);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetScenesOrBuilder
            public int getAid() {
                return ((GetScenes) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((GetScenes) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((GetScenes) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetScenesOrBuilder
            public String getServiceToken() {
                return ((GetScenes) this.instance).getServiceToken();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetScenesOrBuilder
            public ByteString getServiceTokenBytes() {
                return ((GetScenes) this.instance).getServiceTokenBytes();
            }

            public Builder setServiceToken(String str) {
                copyOnWrite();
                ((GetScenes) this.instance).a(str);
                return this;
            }

            public Builder clearServiceToken() {
                copyOnWrite();
                ((GetScenes) this.instance).f();
                return this;
            }

            public Builder setServiceTokenBytes(ByteString byteString) {
                copyOnWrite();
                ((GetScenes) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetScenesOrBuilder
            public String getAppId() {
                return ((GetScenes) this.instance).getAppId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetScenesOrBuilder
            public ByteString getAppIdBytes() {
                return ((GetScenes) this.instance).getAppIdBytes();
            }

            public Builder setAppId(String str) {
                copyOnWrite();
                ((GetScenes) this.instance).b(str);
                return this;
            }

            public Builder clearAppId() {
                copyOnWrite();
                ((GetScenes) this.instance).g();
                return this;
            }

            public Builder setAppIdBytes(ByteString byteString) {
                copyOnWrite();
                ((GetScenes) this.instance).b(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new GetScenes();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ", new Object[]{"aid_", "serviceToken_", "appId_"});
                case GET_DEFAULT_INSTANCE:
                    return d;
                case GET_PARSER:
                    Parser<GetScenes> parser = e;
                    if (parser == null) {
                        synchronized (GetScenes.class) {
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
            GetScenes getScenes = new GetScenes();
            d = getScenes;
            GeneratedMessageLite.registerDefaultInstance(GetScenes.class, getScenes);
        }

        public static GetScenes getDefaultInstance() {
            return d;
        }

        public static Parser<GetScenes> parser() {
            return d.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class GetDeviceInformations extends GeneratedMessageLite<GetDeviceInformations, Builder> implements GetDeviceInformationsOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int APPID_FIELD_NUMBER = 3;
        public static final int DEVICEID_FIELD_NUMBER = 4;
        public static final int SERVICETOKEN_FIELD_NUMBER = 2;
        private static final GetDeviceInformations e;
        private static volatile Parser<GetDeviceInformations> f;
        private int a;
        private String b = "";
        private String c = "";
        private String d = "";

        private GetDeviceInformations() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDeviceInformationsOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDeviceInformationsOrBuilder
        public String getServiceToken() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDeviceInformationsOrBuilder
        public ByteString getServiceTokenBytes() {
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
            this.b = getDefaultInstance().getServiceToken();
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDeviceInformationsOrBuilder
        public String getAppId() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDeviceInformationsOrBuilder
        public ByteString getAppIdBytes() {
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
            this.c = getDefaultInstance().getAppId();
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDeviceInformationsOrBuilder
        public String getDeviceId() {
            return this.d;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDeviceInformationsOrBuilder
        public ByteString getDeviceIdBytes() {
            return ByteString.copyFromUtf8(this.d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(String str) {
            if (str != null) {
                this.d = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.d = getDefaultInstance().getDeviceId();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.d = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static GetDeviceInformations parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetDeviceInformations) GeneratedMessageLite.parseFrom(e, byteBuffer);
        }

        public static GetDeviceInformations parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetDeviceInformations) GeneratedMessageLite.parseFrom(e, byteBuffer, extensionRegistryLite);
        }

        public static GetDeviceInformations parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetDeviceInformations) GeneratedMessageLite.parseFrom(e, byteString);
        }

        public static GetDeviceInformations parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetDeviceInformations) GeneratedMessageLite.parseFrom(e, byteString, extensionRegistryLite);
        }

        public static GetDeviceInformations parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetDeviceInformations) GeneratedMessageLite.parseFrom(e, bArr);
        }

        public static GetDeviceInformations parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetDeviceInformations) GeneratedMessageLite.parseFrom(e, bArr, extensionRegistryLite);
        }

        public static GetDeviceInformations parseFrom(InputStream inputStream) throws IOException {
            return (GetDeviceInformations) GeneratedMessageLite.parseFrom(e, inputStream);
        }

        public static GetDeviceInformations parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetDeviceInformations) GeneratedMessageLite.parseFrom(e, inputStream, extensionRegistryLite);
        }

        public static GetDeviceInformations parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetDeviceInformations) parseDelimitedFrom(e, inputStream);
        }

        public static GetDeviceInformations parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetDeviceInformations) parseDelimitedFrom(e, inputStream, extensionRegistryLite);
        }

        public static GetDeviceInformations parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetDeviceInformations) GeneratedMessageLite.parseFrom(e, codedInputStream);
        }

        public static GetDeviceInformations parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetDeviceInformations) GeneratedMessageLite.parseFrom(e, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return e.createBuilder();
        }

        public static Builder newBuilder(GetDeviceInformations getDeviceInformations) {
            return e.createBuilder(getDeviceInformations);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<GetDeviceInformations, Builder> implements GetDeviceInformationsOrBuilder {
            private Builder() {
                super(GetDeviceInformations.e);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDeviceInformationsOrBuilder
            public int getAid() {
                return ((GetDeviceInformations) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((GetDeviceInformations) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((GetDeviceInformations) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDeviceInformationsOrBuilder
            public String getServiceToken() {
                return ((GetDeviceInformations) this.instance).getServiceToken();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDeviceInformationsOrBuilder
            public ByteString getServiceTokenBytes() {
                return ((GetDeviceInformations) this.instance).getServiceTokenBytes();
            }

            public Builder setServiceToken(String str) {
                copyOnWrite();
                ((GetDeviceInformations) this.instance).a(str);
                return this;
            }

            public Builder clearServiceToken() {
                copyOnWrite();
                ((GetDeviceInformations) this.instance).f();
                return this;
            }

            public Builder setServiceTokenBytes(ByteString byteString) {
                copyOnWrite();
                ((GetDeviceInformations) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDeviceInformationsOrBuilder
            public String getAppId() {
                return ((GetDeviceInformations) this.instance).getAppId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDeviceInformationsOrBuilder
            public ByteString getAppIdBytes() {
                return ((GetDeviceInformations) this.instance).getAppIdBytes();
            }

            public Builder setAppId(String str) {
                copyOnWrite();
                ((GetDeviceInformations) this.instance).b(str);
                return this;
            }

            public Builder clearAppId() {
                copyOnWrite();
                ((GetDeviceInformations) this.instance).g();
                return this;
            }

            public Builder setAppIdBytes(ByteString byteString) {
                copyOnWrite();
                ((GetDeviceInformations) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDeviceInformationsOrBuilder
            public String getDeviceId() {
                return ((GetDeviceInformations) this.instance).getDeviceId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDeviceInformationsOrBuilder
            public ByteString getDeviceIdBytes() {
                return ((GetDeviceInformations) this.instance).getDeviceIdBytes();
            }

            public Builder setDeviceId(String str) {
                copyOnWrite();
                ((GetDeviceInformations) this.instance).c(str);
                return this;
            }

            public Builder clearDeviceId() {
                copyOnWrite();
                ((GetDeviceInformations) this.instance).h();
                return this;
            }

            public Builder setDeviceIdBytes(ByteString byteString) {
                copyOnWrite();
                ((GetDeviceInformations) this.instance).c(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new GetDeviceInformations();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(e, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ\u0004Ȉ", new Object[]{"aid_", "serviceToken_", "appId_", "deviceId_"});
                case GET_DEFAULT_INSTANCE:
                    return e;
                case GET_PARSER:
                    Parser<GetDeviceInformations> parser = f;
                    if (parser == null) {
                        synchronized (GetDeviceInformations.class) {
                            parser = f;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(e);
                                f = parser;
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
            GetDeviceInformations getDeviceInformations = new GetDeviceInformations();
            e = getDeviceInformations;
            GeneratedMessageLite.registerDefaultInstance(GetDeviceInformations.class, getDeviceInformations);
        }

        public static GetDeviceInformations getDefaultInstance() {
            return e;
        }

        public static Parser<GetDeviceInformations> parser() {
            return e.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class GetHomeFastCommands extends GeneratedMessageLite<GetHomeFastCommands, Builder> implements GetHomeFastCommandsOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int APPID_FIELD_NUMBER = 3;
        public static final int SERVICETOKEN_FIELD_NUMBER = 2;
        private static final GetHomeFastCommands d;
        private static volatile Parser<GetHomeFastCommands> e;
        private int a;
        private String b = "";
        private String c = "";

        private GetHomeFastCommands() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomeFastCommandsOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomeFastCommandsOrBuilder
        public String getServiceToken() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomeFastCommandsOrBuilder
        public ByteString getServiceTokenBytes() {
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
            this.b = getDefaultInstance().getServiceToken();
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomeFastCommandsOrBuilder
        public String getAppId() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomeFastCommandsOrBuilder
        public ByteString getAppIdBytes() {
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
            this.c = getDefaultInstance().getAppId();
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

        public static GetHomeFastCommands parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetHomeFastCommands) GeneratedMessageLite.parseFrom(d, byteBuffer);
        }

        public static GetHomeFastCommands parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetHomeFastCommands) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
        }

        public static GetHomeFastCommands parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetHomeFastCommands) GeneratedMessageLite.parseFrom(d, byteString);
        }

        public static GetHomeFastCommands parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetHomeFastCommands) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
        }

        public static GetHomeFastCommands parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetHomeFastCommands) GeneratedMessageLite.parseFrom(d, bArr);
        }

        public static GetHomeFastCommands parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetHomeFastCommands) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
        }

        public static GetHomeFastCommands parseFrom(InputStream inputStream) throws IOException {
            return (GetHomeFastCommands) GeneratedMessageLite.parseFrom(d, inputStream);
        }

        public static GetHomeFastCommands parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetHomeFastCommands) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
        }

        public static GetHomeFastCommands parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetHomeFastCommands) parseDelimitedFrom(d, inputStream);
        }

        public static GetHomeFastCommands parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetHomeFastCommands) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
        }

        public static GetHomeFastCommands parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetHomeFastCommands) GeneratedMessageLite.parseFrom(d, codedInputStream);
        }

        public static GetHomeFastCommands parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetHomeFastCommands) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return d.createBuilder();
        }

        public static Builder newBuilder(GetHomeFastCommands getHomeFastCommands) {
            return d.createBuilder(getHomeFastCommands);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<GetHomeFastCommands, Builder> implements GetHomeFastCommandsOrBuilder {
            private Builder() {
                super(GetHomeFastCommands.d);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomeFastCommandsOrBuilder
            public int getAid() {
                return ((GetHomeFastCommands) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((GetHomeFastCommands) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((GetHomeFastCommands) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomeFastCommandsOrBuilder
            public String getServiceToken() {
                return ((GetHomeFastCommands) this.instance).getServiceToken();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomeFastCommandsOrBuilder
            public ByteString getServiceTokenBytes() {
                return ((GetHomeFastCommands) this.instance).getServiceTokenBytes();
            }

            public Builder setServiceToken(String str) {
                copyOnWrite();
                ((GetHomeFastCommands) this.instance).a(str);
                return this;
            }

            public Builder clearServiceToken() {
                copyOnWrite();
                ((GetHomeFastCommands) this.instance).f();
                return this;
            }

            public Builder setServiceTokenBytes(ByteString byteString) {
                copyOnWrite();
                ((GetHomeFastCommands) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomeFastCommandsOrBuilder
            public String getAppId() {
                return ((GetHomeFastCommands) this.instance).getAppId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetHomeFastCommandsOrBuilder
            public ByteString getAppIdBytes() {
                return ((GetHomeFastCommands) this.instance).getAppIdBytes();
            }

            public Builder setAppId(String str) {
                copyOnWrite();
                ((GetHomeFastCommands) this.instance).b(str);
                return this;
            }

            public Builder clearAppId() {
                copyOnWrite();
                ((GetHomeFastCommands) this.instance).g();
                return this;
            }

            public Builder setAppIdBytes(ByteString byteString) {
                copyOnWrite();
                ((GetHomeFastCommands) this.instance).b(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new GetHomeFastCommands();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ", new Object[]{"aid_", "serviceToken_", "appId_"});
                case GET_DEFAULT_INSTANCE:
                    return d;
                case GET_PARSER:
                    Parser<GetHomeFastCommands> parser = e;
                    if (parser == null) {
                        synchronized (GetHomeFastCommands.class) {
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
            GetHomeFastCommands getHomeFastCommands = new GetHomeFastCommands();
            d = getHomeFastCommands;
            GeneratedMessageLite.registerDefaultInstance(GetHomeFastCommands.class, getHomeFastCommands);
        }

        public static GetHomeFastCommands getDefaultInstance() {
            return d;
        }

        public static Parser<GetHomeFastCommands> parser() {
            return d.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class GetDeviceProperties extends GeneratedMessageLite<GetDeviceProperties, Builder> implements GetDevicePropertiesOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int APPID_FIELD_NUMBER = 3;
        public static final int PROPERTYID_FIELD_NUMBER = 4;
        public static final int SERVICETOKEN_FIELD_NUMBER = 2;
        private static final GetDeviceProperties e;
        private static volatile Parser<GetDeviceProperties> f;
        private int a;
        private String b = "";
        private String c = "";
        private String d = "";

        private GetDeviceProperties() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicePropertiesOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicePropertiesOrBuilder
        public String getServiceToken() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicePropertiesOrBuilder
        public ByteString getServiceTokenBytes() {
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
            this.b = getDefaultInstance().getServiceToken();
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicePropertiesOrBuilder
        public String getAppId() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicePropertiesOrBuilder
        public ByteString getAppIdBytes() {
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
            this.c = getDefaultInstance().getAppId();
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicePropertiesOrBuilder
        public String getPropertyId() {
            return this.d;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicePropertiesOrBuilder
        public ByteString getPropertyIdBytes() {
            return ByteString.copyFromUtf8(this.d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(String str) {
            if (str != null) {
                this.d = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.d = getDefaultInstance().getPropertyId();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.d = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static GetDeviceProperties parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetDeviceProperties) GeneratedMessageLite.parseFrom(e, byteBuffer);
        }

        public static GetDeviceProperties parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetDeviceProperties) GeneratedMessageLite.parseFrom(e, byteBuffer, extensionRegistryLite);
        }

        public static GetDeviceProperties parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetDeviceProperties) GeneratedMessageLite.parseFrom(e, byteString);
        }

        public static GetDeviceProperties parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetDeviceProperties) GeneratedMessageLite.parseFrom(e, byteString, extensionRegistryLite);
        }

        public static GetDeviceProperties parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetDeviceProperties) GeneratedMessageLite.parseFrom(e, bArr);
        }

        public static GetDeviceProperties parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetDeviceProperties) GeneratedMessageLite.parseFrom(e, bArr, extensionRegistryLite);
        }

        public static GetDeviceProperties parseFrom(InputStream inputStream) throws IOException {
            return (GetDeviceProperties) GeneratedMessageLite.parseFrom(e, inputStream);
        }

        public static GetDeviceProperties parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetDeviceProperties) GeneratedMessageLite.parseFrom(e, inputStream, extensionRegistryLite);
        }

        public static GetDeviceProperties parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetDeviceProperties) parseDelimitedFrom(e, inputStream);
        }

        public static GetDeviceProperties parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetDeviceProperties) parseDelimitedFrom(e, inputStream, extensionRegistryLite);
        }

        public static GetDeviceProperties parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetDeviceProperties) GeneratedMessageLite.parseFrom(e, codedInputStream);
        }

        public static GetDeviceProperties parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetDeviceProperties) GeneratedMessageLite.parseFrom(e, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return e.createBuilder();
        }

        public static Builder newBuilder(GetDeviceProperties getDeviceProperties) {
            return e.createBuilder(getDeviceProperties);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<GetDeviceProperties, Builder> implements GetDevicePropertiesOrBuilder {
            private Builder() {
                super(GetDeviceProperties.e);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicePropertiesOrBuilder
            public int getAid() {
                return ((GetDeviceProperties) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((GetDeviceProperties) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((GetDeviceProperties) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicePropertiesOrBuilder
            public String getServiceToken() {
                return ((GetDeviceProperties) this.instance).getServiceToken();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicePropertiesOrBuilder
            public ByteString getServiceTokenBytes() {
                return ((GetDeviceProperties) this.instance).getServiceTokenBytes();
            }

            public Builder setServiceToken(String str) {
                copyOnWrite();
                ((GetDeviceProperties) this.instance).a(str);
                return this;
            }

            public Builder clearServiceToken() {
                copyOnWrite();
                ((GetDeviceProperties) this.instance).f();
                return this;
            }

            public Builder setServiceTokenBytes(ByteString byteString) {
                copyOnWrite();
                ((GetDeviceProperties) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicePropertiesOrBuilder
            public String getAppId() {
                return ((GetDeviceProperties) this.instance).getAppId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicePropertiesOrBuilder
            public ByteString getAppIdBytes() {
                return ((GetDeviceProperties) this.instance).getAppIdBytes();
            }

            public Builder setAppId(String str) {
                copyOnWrite();
                ((GetDeviceProperties) this.instance).b(str);
                return this;
            }

            public Builder clearAppId() {
                copyOnWrite();
                ((GetDeviceProperties) this.instance).g();
                return this;
            }

            public Builder setAppIdBytes(ByteString byteString) {
                copyOnWrite();
                ((GetDeviceProperties) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicePropertiesOrBuilder
            public String getPropertyId() {
                return ((GetDeviceProperties) this.instance).getPropertyId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetDevicePropertiesOrBuilder
            public ByteString getPropertyIdBytes() {
                return ((GetDeviceProperties) this.instance).getPropertyIdBytes();
            }

            public Builder setPropertyId(String str) {
                copyOnWrite();
                ((GetDeviceProperties) this.instance).c(str);
                return this;
            }

            public Builder clearPropertyId() {
                copyOnWrite();
                ((GetDeviceProperties) this.instance).h();
                return this;
            }

            public Builder setPropertyIdBytes(ByteString byteString) {
                copyOnWrite();
                ((GetDeviceProperties) this.instance).c(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new GetDeviceProperties();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(e, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ\u0004Ȉ", new Object[]{"aid_", "serviceToken_", "appId_", "propertyId_"});
                case GET_DEFAULT_INSTANCE:
                    return e;
                case GET_PARSER:
                    Parser<GetDeviceProperties> parser = f;
                    if (parser == null) {
                        synchronized (GetDeviceProperties.class) {
                            parser = f;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(e);
                                f = parser;
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
            GetDeviceProperties getDeviceProperties = new GetDeviceProperties();
            e = getDeviceProperties;
            GeneratedMessageLite.registerDefaultInstance(GetDeviceProperties.class, getDeviceProperties);
        }

        public static GetDeviceProperties getDefaultInstance() {
            return e;
        }

        public static Parser<GetDeviceProperties> parser() {
            return e.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class SetDeviceProperties extends GeneratedMessageLite<SetDeviceProperties, Builder> implements SetDevicePropertiesOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int APPID_FIELD_NUMBER = 3;
        public static final int ISSORT_FIELD_NUMBER = 5;
        public static final int PROPERTYBODY_FIELD_NUMBER = 4;
        public static final int SERVICETOKEN_FIELD_NUMBER = 2;
        private static final SetDeviceProperties f;
        private static volatile Parser<SetDeviceProperties> g;
        private int a;
        private String b = "";
        private String c = "";
        private String d = "";
        private boolean e;

        private SetDeviceProperties() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
        public String getServiceToken() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
        public ByteString getServiceTokenBytes() {
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
            this.b = getDefaultInstance().getServiceToken();
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
        public String getAppId() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
        public ByteString getAppIdBytes() {
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
            this.c = getDefaultInstance().getAppId();
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
        public String getPropertyBody() {
            return this.d;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
        public ByteString getPropertyBodyBytes() {
            return ByteString.copyFromUtf8(this.d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(String str) {
            if (str != null) {
                this.d = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.d = getDefaultInstance().getPropertyBody();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.d = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
        public boolean getIsSort() {
            return this.e;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(boolean z) {
            this.e = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void i() {
            this.e = false;
        }

        public static SetDeviceProperties parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (SetDeviceProperties) GeneratedMessageLite.parseFrom(f, byteBuffer);
        }

        public static SetDeviceProperties parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetDeviceProperties) GeneratedMessageLite.parseFrom(f, byteBuffer, extensionRegistryLite);
        }

        public static SetDeviceProperties parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (SetDeviceProperties) GeneratedMessageLite.parseFrom(f, byteString);
        }

        public static SetDeviceProperties parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetDeviceProperties) GeneratedMessageLite.parseFrom(f, byteString, extensionRegistryLite);
        }

        public static SetDeviceProperties parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (SetDeviceProperties) GeneratedMessageLite.parseFrom(f, bArr);
        }

        public static SetDeviceProperties parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetDeviceProperties) GeneratedMessageLite.parseFrom(f, bArr, extensionRegistryLite);
        }

        public static SetDeviceProperties parseFrom(InputStream inputStream) throws IOException {
            return (SetDeviceProperties) GeneratedMessageLite.parseFrom(f, inputStream);
        }

        public static SetDeviceProperties parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetDeviceProperties) GeneratedMessageLite.parseFrom(f, inputStream, extensionRegistryLite);
        }

        public static SetDeviceProperties parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (SetDeviceProperties) parseDelimitedFrom(f, inputStream);
        }

        public static SetDeviceProperties parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetDeviceProperties) parseDelimitedFrom(f, inputStream, extensionRegistryLite);
        }

        public static SetDeviceProperties parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (SetDeviceProperties) GeneratedMessageLite.parseFrom(f, codedInputStream);
        }

        public static SetDeviceProperties parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetDeviceProperties) GeneratedMessageLite.parseFrom(f, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return f.createBuilder();
        }

        public static Builder newBuilder(SetDeviceProperties setDeviceProperties) {
            return f.createBuilder(setDeviceProperties);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<SetDeviceProperties, Builder> implements SetDevicePropertiesOrBuilder {
            private Builder() {
                super(SetDeviceProperties.f);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
            public int getAid() {
                return ((SetDeviceProperties) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((SetDeviceProperties) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((SetDeviceProperties) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
            public String getServiceToken() {
                return ((SetDeviceProperties) this.instance).getServiceToken();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
            public ByteString getServiceTokenBytes() {
                return ((SetDeviceProperties) this.instance).getServiceTokenBytes();
            }

            public Builder setServiceToken(String str) {
                copyOnWrite();
                ((SetDeviceProperties) this.instance).a(str);
                return this;
            }

            public Builder clearServiceToken() {
                copyOnWrite();
                ((SetDeviceProperties) this.instance).f();
                return this;
            }

            public Builder setServiceTokenBytes(ByteString byteString) {
                copyOnWrite();
                ((SetDeviceProperties) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
            public String getAppId() {
                return ((SetDeviceProperties) this.instance).getAppId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
            public ByteString getAppIdBytes() {
                return ((SetDeviceProperties) this.instance).getAppIdBytes();
            }

            public Builder setAppId(String str) {
                copyOnWrite();
                ((SetDeviceProperties) this.instance).b(str);
                return this;
            }

            public Builder clearAppId() {
                copyOnWrite();
                ((SetDeviceProperties) this.instance).g();
                return this;
            }

            public Builder setAppIdBytes(ByteString byteString) {
                copyOnWrite();
                ((SetDeviceProperties) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
            public String getPropertyBody() {
                return ((SetDeviceProperties) this.instance).getPropertyBody();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
            public ByteString getPropertyBodyBytes() {
                return ((SetDeviceProperties) this.instance).getPropertyBodyBytes();
            }

            public Builder setPropertyBody(String str) {
                copyOnWrite();
                ((SetDeviceProperties) this.instance).c(str);
                return this;
            }

            public Builder clearPropertyBody() {
                copyOnWrite();
                ((SetDeviceProperties) this.instance).h();
                return this;
            }

            public Builder setPropertyBodyBytes(ByteString byteString) {
                copyOnWrite();
                ((SetDeviceProperties) this.instance).c(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetDevicePropertiesOrBuilder
            public boolean getIsSort() {
                return ((SetDeviceProperties) this.instance).getIsSort();
            }

            public Builder setIsSort(boolean z) {
                copyOnWrite();
                ((SetDeviceProperties) this.instance).a(z);
                return this;
            }

            public Builder clearIsSort() {
                copyOnWrite();
                ((SetDeviceProperties) this.instance).i();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new SetDeviceProperties();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(f, "\u0000\u0005\u0000\u0000\u0001\u0005\u0005\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ\u0004Ȉ\u0005\u0007", new Object[]{"aid_", "serviceToken_", "appId_", "propertyBody_", "isSort_"});
                case GET_DEFAULT_INSTANCE:
                    return f;
                case GET_PARSER:
                    Parser<SetDeviceProperties> parser = g;
                    if (parser == null) {
                        synchronized (SetDeviceProperties.class) {
                            parser = g;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(f);
                                g = parser;
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
            SetDeviceProperties setDeviceProperties = new SetDeviceProperties();
            f = setDeviceProperties;
            GeneratedMessageLite.registerDefaultInstance(SetDeviceProperties.class, setDeviceProperties);
        }

        public static SetDeviceProperties getDefaultInstance() {
            return f;
        }

        public static Parser<SetDeviceProperties> parser() {
            return f.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class ExeScenes extends GeneratedMessageLite<ExeScenes, Builder> implements ExeScenesOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int APPID_FIELD_NUMBER = 3;
        public static final int SCENEID_FIELD_NUMBER = 4;
        public static final int SERVICETOKEN_FIELD_NUMBER = 2;
        private static final ExeScenes e;
        private static volatile Parser<ExeScenes> f;
        private int a;
        private String b = "";
        private String c = "";
        private String d = "";

        private ExeScenes() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.ExeScenesOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.ExeScenesOrBuilder
        public String getServiceToken() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.ExeScenesOrBuilder
        public ByteString getServiceTokenBytes() {
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
            this.b = getDefaultInstance().getServiceToken();
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.ExeScenesOrBuilder
        public String getAppId() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.ExeScenesOrBuilder
        public ByteString getAppIdBytes() {
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
            this.c = getDefaultInstance().getAppId();
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.ExeScenesOrBuilder
        public String getSceneId() {
            return this.d;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.ExeScenesOrBuilder
        public ByteString getSceneIdBytes() {
            return ByteString.copyFromUtf8(this.d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(String str) {
            if (str != null) {
                this.d = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.d = getDefaultInstance().getSceneId();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.d = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static ExeScenes parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ExeScenes) GeneratedMessageLite.parseFrom(e, byteBuffer);
        }

        public static ExeScenes parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ExeScenes) GeneratedMessageLite.parseFrom(e, byteBuffer, extensionRegistryLite);
        }

        public static ExeScenes parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ExeScenes) GeneratedMessageLite.parseFrom(e, byteString);
        }

        public static ExeScenes parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ExeScenes) GeneratedMessageLite.parseFrom(e, byteString, extensionRegistryLite);
        }

        public static ExeScenes parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ExeScenes) GeneratedMessageLite.parseFrom(e, bArr);
        }

        public static ExeScenes parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ExeScenes) GeneratedMessageLite.parseFrom(e, bArr, extensionRegistryLite);
        }

        public static ExeScenes parseFrom(InputStream inputStream) throws IOException {
            return (ExeScenes) GeneratedMessageLite.parseFrom(e, inputStream);
        }

        public static ExeScenes parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ExeScenes) GeneratedMessageLite.parseFrom(e, inputStream, extensionRegistryLite);
        }

        public static ExeScenes parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ExeScenes) parseDelimitedFrom(e, inputStream);
        }

        public static ExeScenes parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ExeScenes) parseDelimitedFrom(e, inputStream, extensionRegistryLite);
        }

        public static ExeScenes parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ExeScenes) GeneratedMessageLite.parseFrom(e, codedInputStream);
        }

        public static ExeScenes parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ExeScenes) GeneratedMessageLite.parseFrom(e, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return e.createBuilder();
        }

        public static Builder newBuilder(ExeScenes exeScenes) {
            return e.createBuilder(exeScenes);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<ExeScenes, Builder> implements ExeScenesOrBuilder {
            private Builder() {
                super(ExeScenes.e);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.ExeScenesOrBuilder
            public int getAid() {
                return ((ExeScenes) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((ExeScenes) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((ExeScenes) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.ExeScenesOrBuilder
            public String getServiceToken() {
                return ((ExeScenes) this.instance).getServiceToken();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.ExeScenesOrBuilder
            public ByteString getServiceTokenBytes() {
                return ((ExeScenes) this.instance).getServiceTokenBytes();
            }

            public Builder setServiceToken(String str) {
                copyOnWrite();
                ((ExeScenes) this.instance).a(str);
                return this;
            }

            public Builder clearServiceToken() {
                copyOnWrite();
                ((ExeScenes) this.instance).f();
                return this;
            }

            public Builder setServiceTokenBytes(ByteString byteString) {
                copyOnWrite();
                ((ExeScenes) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.ExeScenesOrBuilder
            public String getAppId() {
                return ((ExeScenes) this.instance).getAppId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.ExeScenesOrBuilder
            public ByteString getAppIdBytes() {
                return ((ExeScenes) this.instance).getAppIdBytes();
            }

            public Builder setAppId(String str) {
                copyOnWrite();
                ((ExeScenes) this.instance).b(str);
                return this;
            }

            public Builder clearAppId() {
                copyOnWrite();
                ((ExeScenes) this.instance).g();
                return this;
            }

            public Builder setAppIdBytes(ByteString byteString) {
                copyOnWrite();
                ((ExeScenes) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.ExeScenesOrBuilder
            public String getSceneId() {
                return ((ExeScenes) this.instance).getSceneId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.ExeScenesOrBuilder
            public ByteString getSceneIdBytes() {
                return ((ExeScenes) this.instance).getSceneIdBytes();
            }

            public Builder setSceneId(String str) {
                copyOnWrite();
                ((ExeScenes) this.instance).c(str);
                return this;
            }

            public Builder clearSceneId() {
                copyOnWrite();
                ((ExeScenes) this.instance).h();
                return this;
            }

            public Builder setSceneIdBytes(ByteString byteString) {
                copyOnWrite();
                ((ExeScenes) this.instance).c(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new ExeScenes();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(e, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ\u0004Ȉ", new Object[]{"aid_", "serviceToken_", "appId_", "sceneId_"});
                case GET_DEFAULT_INSTANCE:
                    return e;
                case GET_PARSER:
                    Parser<ExeScenes> parser = f;
                    if (parser == null) {
                        synchronized (ExeScenes.class) {
                            parser = f;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(e);
                                f = parser;
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
            ExeScenes exeScenes = new ExeScenes();
            e = exeScenes;
            GeneratedMessageLite.registerDefaultInstance(ExeScenes.class, exeScenes);
        }

        public static ExeScenes getDefaultInstance() {
            return e;
        }

        public static Parser<ExeScenes> parser() {
            return e.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class SetToken extends GeneratedMessageLite<SetToken, Builder> implements SetTokenOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int APPID_FIELD_NUMBER = 2;
        public static final int SERVICETOKEN_FIELD_NUMBER = 3;
        private static final SetToken d;
        private static volatile Parser<SetToken> e;
        private int a;
        private String b = "";
        private String c = "";

        private SetToken() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetTokenOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetTokenOrBuilder
        public String getAppId() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetTokenOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetTokenOrBuilder
        public String getServiceToken() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetTokenOrBuilder
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

        public static SetToken parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (SetToken) GeneratedMessageLite.parseFrom(d, byteBuffer);
        }

        public static SetToken parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetToken) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
        }

        public static SetToken parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (SetToken) GeneratedMessageLite.parseFrom(d, byteString);
        }

        public static SetToken parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetToken) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
        }

        public static SetToken parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (SetToken) GeneratedMessageLite.parseFrom(d, bArr);
        }

        public static SetToken parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetToken) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
        }

        public static SetToken parseFrom(InputStream inputStream) throws IOException {
            return (SetToken) GeneratedMessageLite.parseFrom(d, inputStream);
        }

        public static SetToken parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetToken) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
        }

        public static SetToken parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (SetToken) parseDelimitedFrom(d, inputStream);
        }

        public static SetToken parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetToken) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
        }

        public static SetToken parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (SetToken) GeneratedMessageLite.parseFrom(d, codedInputStream);
        }

        public static SetToken parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetToken) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return d.createBuilder();
        }

        public static Builder newBuilder(SetToken setToken) {
            return d.createBuilder(setToken);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<SetToken, Builder> implements SetTokenOrBuilder {
            private Builder() {
                super(SetToken.d);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetTokenOrBuilder
            public int getAid() {
                return ((SetToken) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((SetToken) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((SetToken) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetTokenOrBuilder
            public String getAppId() {
                return ((SetToken) this.instance).getAppId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetTokenOrBuilder
            public ByteString getAppIdBytes() {
                return ((SetToken) this.instance).getAppIdBytes();
            }

            public Builder setAppId(String str) {
                copyOnWrite();
                ((SetToken) this.instance).a(str);
                return this;
            }

            public Builder clearAppId() {
                copyOnWrite();
                ((SetToken) this.instance).f();
                return this;
            }

            public Builder setAppIdBytes(ByteString byteString) {
                copyOnWrite();
                ((SetToken) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetTokenOrBuilder
            public String getServiceToken() {
                return ((SetToken) this.instance).getServiceToken();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.SetTokenOrBuilder
            public ByteString getServiceTokenBytes() {
                return ((SetToken) this.instance).getServiceTokenBytes();
            }

            public Builder setServiceToken(String str) {
                copyOnWrite();
                ((SetToken) this.instance).b(str);
                return this;
            }

            public Builder clearServiceToken() {
                copyOnWrite();
                ((SetToken) this.instance).g();
                return this;
            }

            public Builder setServiceTokenBytes(ByteString byteString) {
                copyOnWrite();
                ((SetToken) this.instance).b(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new SetToken();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ", new Object[]{"aid_", "appId_", "serviceToken_"});
                case GET_DEFAULT_INSTANCE:
                    return d;
                case GET_PARSER:
                    Parser<SetToken> parser = e;
                    if (parser == null) {
                        synchronized (SetToken.class) {
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
            SetToken setToken = new SetToken();
            d = setToken;
            GeneratedMessageLite.registerDefaultInstance(SetToken.class, setToken);
        }

        public static SetToken getDefaultInstance() {
            return d;
        }

        public static Parser<SetToken> parser() {
            return d.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class Stop extends GeneratedMessageLite<Stop, Builder> implements StopOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int APPID_FIELD_NUMBER = 2;
        public static final int SERVICETOKEN_FIELD_NUMBER = 3;
        private static final Stop d;
        private static volatile Parser<Stop> e;
        private int a;
        private String b = "";
        private String c = "";

        private Stop() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.StopOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.StopOrBuilder
        public String getAppId() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.StopOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.StopOrBuilder
        public String getServiceToken() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.StopOrBuilder
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

        public static Stop parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Stop) GeneratedMessageLite.parseFrom(d, byteBuffer);
        }

        public static Stop parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Stop) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
        }

        public static Stop parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Stop) GeneratedMessageLite.parseFrom(d, byteString);
        }

        public static Stop parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Stop) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
        }

        public static Stop parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Stop) GeneratedMessageLite.parseFrom(d, bArr);
        }

        public static Stop parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Stop) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
        }

        public static Stop parseFrom(InputStream inputStream) throws IOException {
            return (Stop) GeneratedMessageLite.parseFrom(d, inputStream);
        }

        public static Stop parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Stop) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
        }

        public static Stop parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Stop) parseDelimitedFrom(d, inputStream);
        }

        public static Stop parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Stop) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
        }

        public static Stop parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Stop) GeneratedMessageLite.parseFrom(d, codedInputStream);
        }

        public static Stop parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Stop) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return d.createBuilder();
        }

        public static Builder newBuilder(Stop stop) {
            return d.createBuilder(stop);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<Stop, Builder> implements StopOrBuilder {
            private Builder() {
                super(Stop.d);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.StopOrBuilder
            public int getAid() {
                return ((Stop) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((Stop) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((Stop) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.StopOrBuilder
            public String getAppId() {
                return ((Stop) this.instance).getAppId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.StopOrBuilder
            public ByteString getAppIdBytes() {
                return ((Stop) this.instance).getAppIdBytes();
            }

            public Builder setAppId(String str) {
                copyOnWrite();
                ((Stop) this.instance).a(str);
                return this;
            }

            public Builder clearAppId() {
                copyOnWrite();
                ((Stop) this.instance).f();
                return this;
            }

            public Builder setAppIdBytes(ByteString byteString) {
                copyOnWrite();
                ((Stop) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.StopOrBuilder
            public String getServiceToken() {
                return ((Stop) this.instance).getServiceToken();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.StopOrBuilder
            public ByteString getServiceTokenBytes() {
                return ((Stop) this.instance).getServiceTokenBytes();
            }

            public Builder setServiceToken(String str) {
                copyOnWrite();
                ((Stop) this.instance).b(str);
                return this;
            }

            public Builder clearServiceToken() {
                copyOnWrite();
                ((Stop) this.instance).g();
                return this;
            }

            public Builder setServiceTokenBytes(ByteString byteString) {
                copyOnWrite();
                ((Stop) this.instance).b(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new Stop();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ", new Object[]{"aid_", "appId_", "serviceToken_"});
                case GET_DEFAULT_INSTANCE:
                    return d;
                case GET_PARSER:
                    Parser<Stop> parser = e;
                    if (parser == null) {
                        synchronized (Stop.class) {
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
            Stop stop = new Stop();
            d = stop;
            GeneratedMessageLite.registerDefaultInstance(Stop.class, stop);
        }

        public static Stop getDefaultInstance() {
            return d;
        }

        public static Parser<Stop> parser() {
            return d.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class GetSceneAlias extends GeneratedMessageLite<GetSceneAlias, Builder> implements GetSceneAliasOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int APPID_FIELD_NUMBER = 2;
        public static final int SCENENAME_FIELD_NUMBER = 4;
        public static final int SERVICETOKEN_FIELD_NUMBER = 3;
        private static final GetSceneAlias e;
        private static volatile Parser<GetSceneAlias> f;
        private int a;
        private String b = "";
        private String c = "";
        private String d = "";

        private GetSceneAlias() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetSceneAliasOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetSceneAliasOrBuilder
        public String getAppId() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetSceneAliasOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetSceneAliasOrBuilder
        public String getServiceToken() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetSceneAliasOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetSceneAliasOrBuilder
        public String getSceneName() {
            return this.d;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetSceneAliasOrBuilder
        public ByteString getSceneNameBytes() {
            return ByteString.copyFromUtf8(this.d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(String str) {
            if (str != null) {
                this.d = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.d = getDefaultInstance().getSceneName();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.d = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static GetSceneAlias parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetSceneAlias) GeneratedMessageLite.parseFrom(e, byteBuffer);
        }

        public static GetSceneAlias parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetSceneAlias) GeneratedMessageLite.parseFrom(e, byteBuffer, extensionRegistryLite);
        }

        public static GetSceneAlias parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetSceneAlias) GeneratedMessageLite.parseFrom(e, byteString);
        }

        public static GetSceneAlias parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetSceneAlias) GeneratedMessageLite.parseFrom(e, byteString, extensionRegistryLite);
        }

        public static GetSceneAlias parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetSceneAlias) GeneratedMessageLite.parseFrom(e, bArr);
        }

        public static GetSceneAlias parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetSceneAlias) GeneratedMessageLite.parseFrom(e, bArr, extensionRegistryLite);
        }

        public static GetSceneAlias parseFrom(InputStream inputStream) throws IOException {
            return (GetSceneAlias) GeneratedMessageLite.parseFrom(e, inputStream);
        }

        public static GetSceneAlias parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetSceneAlias) GeneratedMessageLite.parseFrom(e, inputStream, extensionRegistryLite);
        }

        public static GetSceneAlias parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetSceneAlias) parseDelimitedFrom(e, inputStream);
        }

        public static GetSceneAlias parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetSceneAlias) parseDelimitedFrom(e, inputStream, extensionRegistryLite);
        }

        public static GetSceneAlias parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetSceneAlias) GeneratedMessageLite.parseFrom(e, codedInputStream);
        }

        public static GetSceneAlias parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetSceneAlias) GeneratedMessageLite.parseFrom(e, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return e.createBuilder();
        }

        public static Builder newBuilder(GetSceneAlias getSceneAlias) {
            return e.createBuilder(getSceneAlias);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<GetSceneAlias, Builder> implements GetSceneAliasOrBuilder {
            private Builder() {
                super(GetSceneAlias.e);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetSceneAliasOrBuilder
            public int getAid() {
                return ((GetSceneAlias) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((GetSceneAlias) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((GetSceneAlias) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetSceneAliasOrBuilder
            public String getAppId() {
                return ((GetSceneAlias) this.instance).getAppId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetSceneAliasOrBuilder
            public ByteString getAppIdBytes() {
                return ((GetSceneAlias) this.instance).getAppIdBytes();
            }

            public Builder setAppId(String str) {
                copyOnWrite();
                ((GetSceneAlias) this.instance).a(str);
                return this;
            }

            public Builder clearAppId() {
                copyOnWrite();
                ((GetSceneAlias) this.instance).f();
                return this;
            }

            public Builder setAppIdBytes(ByteString byteString) {
                copyOnWrite();
                ((GetSceneAlias) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetSceneAliasOrBuilder
            public String getServiceToken() {
                return ((GetSceneAlias) this.instance).getServiceToken();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetSceneAliasOrBuilder
            public ByteString getServiceTokenBytes() {
                return ((GetSceneAlias) this.instance).getServiceTokenBytes();
            }

            public Builder setServiceToken(String str) {
                copyOnWrite();
                ((GetSceneAlias) this.instance).b(str);
                return this;
            }

            public Builder clearServiceToken() {
                copyOnWrite();
                ((GetSceneAlias) this.instance).g();
                return this;
            }

            public Builder setServiceTokenBytes(ByteString byteString) {
                copyOnWrite();
                ((GetSceneAlias) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetSceneAliasOrBuilder
            public String getSceneName() {
                return ((GetSceneAlias) this.instance).getSceneName();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.GetSceneAliasOrBuilder
            public ByteString getSceneNameBytes() {
                return ((GetSceneAlias) this.instance).getSceneNameBytes();
            }

            public Builder setSceneName(String str) {
                copyOnWrite();
                ((GetSceneAlias) this.instance).c(str);
                return this;
            }

            public Builder clearSceneName() {
                copyOnWrite();
                ((GetSceneAlias) this.instance).h();
                return this;
            }

            public Builder setSceneNameBytes(ByteString byteString) {
                copyOnWrite();
                ((GetSceneAlias) this.instance).c(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new GetSceneAlias();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(e, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ\u0004Ȉ", new Object[]{"aid_", "appId_", "serviceToken_", "sceneName_"});
                case GET_DEFAULT_INSTANCE:
                    return e;
                case GET_PARSER:
                    Parser<GetSceneAlias> parser = f;
                    if (parser == null) {
                        synchronized (GetSceneAlias.class) {
                            parser = f;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(e);
                                f = parser;
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
            GetSceneAlias getSceneAlias = new GetSceneAlias();
            e = getSceneAlias;
            GeneratedMessageLite.registerDefaultInstance(GetSceneAlias.class, getSceneAlias);
        }

        public static GetSceneAlias getDefaultInstance() {
            return e;
        }

        public static Parser<GetSceneAlias> parser() {
            return e.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class InvokeAction extends GeneratedMessageLite<InvokeAction, Builder> implements InvokeActionOrBuilder {
        public static final int ACTIONBODY_FIELD_NUMBER = 4;
        public static final int AID_FIELD_NUMBER = 1;
        public static final int APPID_FIELD_NUMBER = 2;
        public static final int SERVICETOKEN_FIELD_NUMBER = 3;
        private static final InvokeAction e;
        private static volatile Parser<InvokeAction> f;
        private int a;
        private String b = "";
        private String c = "";
        private String d = "";

        private InvokeAction() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.InvokeActionOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.InvokeActionOrBuilder
        public String getAppId() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.InvokeActionOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.InvokeActionOrBuilder
        public String getServiceToken() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.InvokeActionOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.InvokeActionOrBuilder
        public String getActionBody() {
            return this.d;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.InvokeActionOrBuilder
        public ByteString getActionBodyBytes() {
            return ByteString.copyFromUtf8(this.d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(String str) {
            if (str != null) {
                this.d = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.d = getDefaultInstance().getActionBody();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.d = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static InvokeAction parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (InvokeAction) GeneratedMessageLite.parseFrom(e, byteBuffer);
        }

        public static InvokeAction parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (InvokeAction) GeneratedMessageLite.parseFrom(e, byteBuffer, extensionRegistryLite);
        }

        public static InvokeAction parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (InvokeAction) GeneratedMessageLite.parseFrom(e, byteString);
        }

        public static InvokeAction parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (InvokeAction) GeneratedMessageLite.parseFrom(e, byteString, extensionRegistryLite);
        }

        public static InvokeAction parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (InvokeAction) GeneratedMessageLite.parseFrom(e, bArr);
        }

        public static InvokeAction parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (InvokeAction) GeneratedMessageLite.parseFrom(e, bArr, extensionRegistryLite);
        }

        public static InvokeAction parseFrom(InputStream inputStream) throws IOException {
            return (InvokeAction) GeneratedMessageLite.parseFrom(e, inputStream);
        }

        public static InvokeAction parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (InvokeAction) GeneratedMessageLite.parseFrom(e, inputStream, extensionRegistryLite);
        }

        public static InvokeAction parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (InvokeAction) parseDelimitedFrom(e, inputStream);
        }

        public static InvokeAction parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (InvokeAction) parseDelimitedFrom(e, inputStream, extensionRegistryLite);
        }

        public static InvokeAction parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (InvokeAction) GeneratedMessageLite.parseFrom(e, codedInputStream);
        }

        public static InvokeAction parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (InvokeAction) GeneratedMessageLite.parseFrom(e, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return e.createBuilder();
        }

        public static Builder newBuilder(InvokeAction invokeAction) {
            return e.createBuilder(invokeAction);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<InvokeAction, Builder> implements InvokeActionOrBuilder {
            private Builder() {
                super(InvokeAction.e);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.InvokeActionOrBuilder
            public int getAid() {
                return ((InvokeAction) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((InvokeAction) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((InvokeAction) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.InvokeActionOrBuilder
            public String getAppId() {
                return ((InvokeAction) this.instance).getAppId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.InvokeActionOrBuilder
            public ByteString getAppIdBytes() {
                return ((InvokeAction) this.instance).getAppIdBytes();
            }

            public Builder setAppId(String str) {
                copyOnWrite();
                ((InvokeAction) this.instance).a(str);
                return this;
            }

            public Builder clearAppId() {
                copyOnWrite();
                ((InvokeAction) this.instance).f();
                return this;
            }

            public Builder setAppIdBytes(ByteString byteString) {
                copyOnWrite();
                ((InvokeAction) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.InvokeActionOrBuilder
            public String getServiceToken() {
                return ((InvokeAction) this.instance).getServiceToken();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.InvokeActionOrBuilder
            public ByteString getServiceTokenBytes() {
                return ((InvokeAction) this.instance).getServiceTokenBytes();
            }

            public Builder setServiceToken(String str) {
                copyOnWrite();
                ((InvokeAction) this.instance).b(str);
                return this;
            }

            public Builder clearServiceToken() {
                copyOnWrite();
                ((InvokeAction) this.instance).g();
                return this;
            }

            public Builder setServiceTokenBytes(ByteString byteString) {
                copyOnWrite();
                ((InvokeAction) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.InvokeActionOrBuilder
            public String getActionBody() {
                return ((InvokeAction) this.instance).getActionBody();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.InvokeActionOrBuilder
            public ByteString getActionBodyBytes() {
                return ((InvokeAction) this.instance).getActionBodyBytes();
            }

            public Builder setActionBody(String str) {
                copyOnWrite();
                ((InvokeAction) this.instance).c(str);
                return this;
            }

            public Builder clearActionBody() {
                copyOnWrite();
                ((InvokeAction) this.instance).h();
                return this;
            }

            public Builder setActionBodyBytes(ByteString byteString) {
                copyOnWrite();
                ((InvokeAction) this.instance).c(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new InvokeAction();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(e, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ\u0004Ȉ", new Object[]{"aid_", "appId_", "serviceToken_", "actionBody_"});
                case GET_DEFAULT_INSTANCE:
                    return e;
                case GET_PARSER:
                    Parser<InvokeAction> parser = f;
                    if (parser == null) {
                        synchronized (InvokeAction.class) {
                            parser = f;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(e);
                                f = parser;
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
            InvokeAction invokeAction = new InvokeAction();
            e = invokeAction;
            GeneratedMessageLite.registerDefaultInstance(InvokeAction.class, invokeAction);
        }

        public static InvokeAction getDefaultInstance() {
            return e;
        }

        public static Parser<InvokeAction> parser() {
            return e.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IotResponse extends GeneratedMessageLite<IotResponse, Builder> implements IotResponseOrBuilder {
        public static final int CODE_FIELD_NUMBER = 1;
        public static final int MESSAGE_FIELD_NUMBER = 2;
        public static final int RESPONSE_FIELD_NUMBER = 3;
        private static final IotResponse d;
        private static volatile Parser<IotResponse> e;
        private int a;
        private String b = "";
        private String c = "";

        private IotResponse() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.IotResponseOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.IotResponseOrBuilder
        public String getMessage() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.IotResponseOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.IotResponseOrBuilder
        public String getResponse() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.IotResponseOrBuilder
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

        public static IotResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IotResponse) GeneratedMessageLite.parseFrom(d, byteBuffer);
        }

        public static IotResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IotResponse) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
        }

        public static IotResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IotResponse) GeneratedMessageLite.parseFrom(d, byteString);
        }

        public static IotResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IotResponse) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
        }

        public static IotResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IotResponse) GeneratedMessageLite.parseFrom(d, bArr);
        }

        public static IotResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IotResponse) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
        }

        public static IotResponse parseFrom(InputStream inputStream) throws IOException {
            return (IotResponse) GeneratedMessageLite.parseFrom(d, inputStream);
        }

        public static IotResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IotResponse) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
        }

        public static IotResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IotResponse) parseDelimitedFrom(d, inputStream);
        }

        public static IotResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IotResponse) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
        }

        public static IotResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IotResponse) GeneratedMessageLite.parseFrom(d, codedInputStream);
        }

        public static IotResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IotResponse) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return d.createBuilder();
        }

        public static Builder newBuilder(IotResponse iotResponse) {
            return d.createBuilder(iotResponse);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IotResponse, Builder> implements IotResponseOrBuilder {
            private Builder() {
                super(IotResponse.d);
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.IotResponseOrBuilder
            public int getCode() {
                return ((IotResponse) this.instance).getCode();
            }

            public Builder setCode(int i) {
                copyOnWrite();
                ((IotResponse) this.instance).b(i);
                return this;
            }

            public Builder clearCode() {
                copyOnWrite();
                ((IotResponse) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.IotResponseOrBuilder
            public String getMessage() {
                return ((IotResponse) this.instance).getMessage();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.IotResponseOrBuilder
            public ByteString getMessageBytes() {
                return ((IotResponse) this.instance).getMessageBytes();
            }

            public Builder setMessage(String str) {
                copyOnWrite();
                ((IotResponse) this.instance).a(str);
                return this;
            }

            public Builder clearMessage() {
                copyOnWrite();
                ((IotResponse) this.instance).f();
                return this;
            }

            public Builder setMessageBytes(ByteString byteString) {
                copyOnWrite();
                ((IotResponse) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.IotResponseOrBuilder
            public String getResponse() {
                return ((IotResponse) this.instance).getResponse();
            }

            @Override // com.xiaomi.idm.service.iot.proto.IotLocalControlServiceProto.IotResponseOrBuilder
            public ByteString getResponseBytes() {
                return ((IotResponse) this.instance).getResponseBytes();
            }

            public Builder setResponse(String str) {
                copyOnWrite();
                ((IotResponse) this.instance).b(str);
                return this;
            }

            public Builder clearResponse() {
                copyOnWrite();
                ((IotResponse) this.instance).g();
                return this;
            }

            public Builder setResponseBytes(ByteString byteString) {
                copyOnWrite();
                ((IotResponse) this.instance).b(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IotResponse();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ", new Object[]{"code_", "message_", "response_"});
                case GET_DEFAULT_INSTANCE:
                    return d;
                case GET_PARSER:
                    Parser<IotResponse> parser = e;
                    if (parser == null) {
                        synchronized (IotResponse.class) {
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
            IotResponse iotResponse = new IotResponse();
            d = iotResponse;
            GeneratedMessageLite.registerDefaultInstance(IotResponse.class, iotResponse);
        }

        public static IotResponse getDefaultInstance() {
            return d;
        }

        public static Parser<IotResponse> parser() {
            return d.getParserForType();
        }
    }
}
