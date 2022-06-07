package com.xiaomi.idm.api.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public final class IDMServiceProto {

    /* loaded from: classes3.dex */
    public interface BLEConfigOrBuilder extends MessageLiteOrBuilder {
        String getBleAddress();

        ByteString getBleAddressBytes();

        int getBleRole();

        int getRssi();
    }

    /* loaded from: classes3.dex */
    public interface BTConfigOrBuilder extends MessageLiteOrBuilder {
        int getRssi();

        String getStaticBTAddress();

        ByteString getStaticBTAddressBytes();
    }

    /* loaded from: classes3.dex */
    public interface ConnParamOrBuilder extends MessageLiteOrBuilder {
        ByteString getConfig();

        int getConnLevel();

        ConnParam.ConnType getConnType();

        int getConnTypeValue();

        int getErrCode();

        String getErrMsg();

        ByteString getErrMsgBytes();

        String getIdHash();

        ByteString getIdHashBytes();

        ByteString getPrivateData();
    }

    /* loaded from: classes3.dex */
    public interface ConnectionQRCodeOrBuilder extends MessageLiteOrBuilder {
        int getChannel();

        int getConnType();

        String getIdHash();

        ByteString getIdHashBytes();

        String getMacAddr();

        ByteString getMacAddrBytes();

        String getPwd();

        ByteString getPwdBytes();

        String getSsid();

        ByteString getSsidBytes();
    }

    /* loaded from: classes3.dex */
    public interface EndpointOrBuilder extends MessageLiteOrBuilder {
        String getBdAddr();

        ByteString getBdAddrBytes();

        String getIdhash();

        ByteString getIdhashBytes();

        String getIp();

        ByteString getIpBytes();

        String getMac();

        ByteString getMacBytes();

        int getMcVersion();

        String getName();

        ByteString getNameBytes();

        int getVerifyStatus();
    }

    /* loaded from: classes3.dex */
    public interface IDMAdvertisingResultOrBuilder extends MessageLiteOrBuilder {
        String getServiceId();

        ByteString getServiceIdBytes();

        int getStatus();
    }

    /* loaded from: classes3.dex */
    public interface IDMConnectServiceRequestOrBuilder extends MessageLiteOrBuilder {
        String getClientId();

        ByteString getClientIdBytes();

        ConnParam getConnParam();

        Endpoint getEndpoint();

        String getServiceId();

        ByteString getServiceIdBytes();

        int getStatus();

        boolean hasConnParam();

        boolean hasEndpoint();
    }

    /* loaded from: classes3.dex */
    public interface IDMConnectServiceResponseOrBuilder extends MessageLiteOrBuilder {
        String getClientId();

        ByteString getClientIdBytes();

        int getConnLevel();

        ConnParam getConnParam();

        Endpoint getEndpoint();

        String getServiceId();

        ByteString getServiceIdBytes();

        int getStatus();

        boolean hasConnParam();

        boolean hasEndpoint();
    }

    /* loaded from: classes3.dex */
    public interface IDMEventOrBuilder extends MessageLiteOrBuilder {
        int getEid();

        boolean getEnable();

        ByteString getEvent();

        String getUuid();

        ByteString getUuidBytes();
    }

    /* loaded from: classes3.dex */
    public interface IDMRequestOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getClientId();

        ByteString getClientIdBytes();

        ByteString getRequest();

        String getRequestId();

        ByteString getRequestIdBytes();

        String getUuid();

        ByteString getUuidBytes();
    }

    /* loaded from: classes3.dex */
    public interface IDMResponseOrBuilder extends MessageLiteOrBuilder {
        String getClientId();

        ByteString getClientIdBytes();

        int getCode();

        String getMsg();

        ByteString getMsgBytes();

        String getRequestId();

        ByteString getRequestIdBytes();

        ByteString getResponse();

        String getUuid();

        ByteString getUuidBytes();
    }

    /* loaded from: classes3.dex */
    public interface IDMServiceOrBuilder extends MessageLiteOrBuilder {
        Endpoint getEndpoint();

        String getName();

        ByteString getNameBytes();

        String getOriginalUuid();

        ByteString getOriginalUuidBytes();

        String getSuperType();

        ByteString getSuperTypeBytes();

        String getType();

        ByteString getTypeBytes();

        String getUuid();

        ByteString getUuidBytes();

        boolean hasEndpoint();
    }

    /* loaded from: classes3.dex */
    public interface OnAccountChangeResultOrBuilder extends MessageLiteOrBuilder {
        String getNewAccount();

        ByteString getNewAccountBytes();

        ByteString getNewIdHash();

        String getOldAccount();

        ByteString getOldAccountBytes();

        OnAccountChangeResult.SubChangeType getSubChangeType();

        int getSubChangeTypeValue();
    }

    /* loaded from: classes3.dex */
    public interface OnServiceChangeResultOrBuilder extends MessageLiteOrBuilder {
        String getNewServiceId();

        ByteString getNewServiceIdBytes();

        String getOldServiceId();

        ByteString getOldServiceIdBytes();
    }

    /* loaded from: classes3.dex */
    public interface WifiConfigOrBuilder extends MessageLiteOrBuilder {
        int getChannel();

        String getLocalIp();

        ByteString getLocalIpBytes();

        String getMacAddr();

        ByteString getMacAddrBytes();

        String getPwd();

        ByteString getPwdBytes();

        String getRemoteIp();

        ByteString getRemoteIpBytes();

        String getSsid();

        ByteString getSsidBytes();

        boolean getUse5GBand();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private IDMServiceProto() {
    }

    /* loaded from: classes3.dex */
    public static final class IDMService extends GeneratedMessageLite<IDMService, Builder> implements IDMServiceOrBuilder {
        public static final int ENDPOINT_FIELD_NUMBER = 4;
        public static final int NAME_FIELD_NUMBER = 3;
        public static final int ORIGINALUUID_FIELD_NUMBER = 5;
        public static final int SUPERTYPE_FIELD_NUMBER = 6;
        public static final int TYPE_FIELD_NUMBER = 2;
        public static final int UUID_FIELD_NUMBER = 1;
        private static final IDMService g;
        private static volatile Parser<IDMService> h;
        private Endpoint d;
        private String a = "";
        private String b = "";
        private String c = "";
        private String e = "";
        private String f = "";

        private IDMService() {
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
        public String getUuid() {
            return this.a;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
        public ByteString getUuidBytes() {
            return ByteString.copyFromUtf8(this.a);
        }

        public void a(String str) {
            if (str != null) {
                this.a = str;
                return;
            }
            throw new NullPointerException();
        }

        public void e() {
            this.a = getDefaultInstance().getUuid();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.a = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
        public String getType() {
            return this.b;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
        public ByteString getTypeBytes() {
            return ByteString.copyFromUtf8(this.b);
        }

        public void b(String str) {
            if (str != null) {
                this.b = str;
                return;
            }
            throw new NullPointerException();
        }

        public void f() {
            this.b = getDefaultInstance().getType();
        }

        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
        public String getName() {
            return this.c;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.c);
        }

        public void c(String str) {
            if (str != null) {
                this.c = str;
                return;
            }
            throw new NullPointerException();
        }

        public void g() {
            this.c = getDefaultInstance().getName();
        }

        public void c(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.c = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
        public boolean hasEndpoint() {
            return this.d != null;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
        public Endpoint getEndpoint() {
            Endpoint endpoint = this.d;
            return endpoint == null ? Endpoint.getDefaultInstance() : endpoint;
        }

        public void a(Endpoint endpoint) {
            if (endpoint != null) {
                this.d = endpoint;
                return;
            }
            throw new NullPointerException();
        }

        public void a(Endpoint.Builder builder) {
            this.d = builder.build();
        }

        public void b(Endpoint endpoint) {
            if (endpoint != null) {
                Endpoint endpoint2 = this.d;
                if (endpoint2 == null || endpoint2 == Endpoint.getDefaultInstance()) {
                    this.d = endpoint;
                } else {
                    this.d = Endpoint.newBuilder(this.d).mergeFrom((Endpoint.Builder) endpoint).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        public void h() {
            this.d = null;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
        public String getOriginalUuid() {
            return this.e;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
        public ByteString getOriginalUuidBytes() {
            return ByteString.copyFromUtf8(this.e);
        }

        public void d(String str) {
            if (str != null) {
                this.e = str;
                return;
            }
            throw new NullPointerException();
        }

        public void i() {
            this.e = getDefaultInstance().getOriginalUuid();
        }

        public void d(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.e = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
        public String getSuperType() {
            return this.f;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
        public ByteString getSuperTypeBytes() {
            return ByteString.copyFromUtf8(this.f);
        }

        public void e(String str) {
            if (str != null) {
                this.f = str;
                return;
            }
            throw new NullPointerException();
        }

        public void j() {
            this.f = getDefaultInstance().getSuperType();
        }

        public void e(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.f = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static IDMService parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMService) GeneratedMessageLite.parseFrom(g, byteBuffer);
        }

        public static IDMService parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMService) GeneratedMessageLite.parseFrom(g, byteBuffer, extensionRegistryLite);
        }

        public static IDMService parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMService) GeneratedMessageLite.parseFrom(g, byteString);
        }

        public static IDMService parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMService) GeneratedMessageLite.parseFrom(g, byteString, extensionRegistryLite);
        }

        public static IDMService parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMService) GeneratedMessageLite.parseFrom(g, bArr);
        }

        public static IDMService parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMService) GeneratedMessageLite.parseFrom(g, bArr, extensionRegistryLite);
        }

        public static IDMService parseFrom(InputStream inputStream) throws IOException {
            return (IDMService) GeneratedMessageLite.parseFrom(g, inputStream);
        }

        public static IDMService parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMService) GeneratedMessageLite.parseFrom(g, inputStream, extensionRegistryLite);
        }

        public static IDMService parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMService) parseDelimitedFrom(g, inputStream);
        }

        public static IDMService parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMService) parseDelimitedFrom(g, inputStream, extensionRegistryLite);
        }

        public static IDMService parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMService) GeneratedMessageLite.parseFrom(g, codedInputStream);
        }

        public static IDMService parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMService) GeneratedMessageLite.parseFrom(g, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return g.createBuilder();
        }

        public static Builder newBuilder(IDMService iDMService) {
            return g.createBuilder(iDMService);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMService, Builder> implements IDMServiceOrBuilder {
            private Builder() {
                super(IDMService.g);
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
            public String getUuid() {
                return ((IDMService) this.instance).getUuid();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
            public ByteString getUuidBytes() {
                return ((IDMService) this.instance).getUuidBytes();
            }

            public Builder setUuid(String str) {
                copyOnWrite();
                ((IDMService) this.instance).a(str);
                return this;
            }

            public Builder clearUuid() {
                copyOnWrite();
                ((IDMService) this.instance).e();
                return this;
            }

            public Builder setUuidBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMService) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
            public String getType() {
                return ((IDMService) this.instance).getType();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
            public ByteString getTypeBytes() {
                return ((IDMService) this.instance).getTypeBytes();
            }

            public Builder setType(String str) {
                copyOnWrite();
                ((IDMService) this.instance).b(str);
                return this;
            }

            public Builder clearType() {
                copyOnWrite();
                ((IDMService) this.instance).f();
                return this;
            }

            public Builder setTypeBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMService) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
            public String getName() {
                return ((IDMService) this.instance).getName();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
            public ByteString getNameBytes() {
                return ((IDMService) this.instance).getNameBytes();
            }

            public Builder setName(String str) {
                copyOnWrite();
                ((IDMService) this.instance).c(str);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((IDMService) this.instance).g();
                return this;
            }

            public Builder setNameBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMService) this.instance).c(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
            public boolean hasEndpoint() {
                return ((IDMService) this.instance).hasEndpoint();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
            public Endpoint getEndpoint() {
                return ((IDMService) this.instance).getEndpoint();
            }

            public Builder setEndpoint(Endpoint endpoint) {
                copyOnWrite();
                ((IDMService) this.instance).a(endpoint);
                return this;
            }

            public Builder setEndpoint(Endpoint.Builder builder) {
                copyOnWrite();
                ((IDMService) this.instance).a(builder);
                return this;
            }

            public Builder mergeEndpoint(Endpoint endpoint) {
                copyOnWrite();
                ((IDMService) this.instance).b(endpoint);
                return this;
            }

            public Builder clearEndpoint() {
                copyOnWrite();
                ((IDMService) this.instance).h();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
            public String getOriginalUuid() {
                return ((IDMService) this.instance).getOriginalUuid();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
            public ByteString getOriginalUuidBytes() {
                return ((IDMService) this.instance).getOriginalUuidBytes();
            }

            public Builder setOriginalUuid(String str) {
                copyOnWrite();
                ((IDMService) this.instance).d(str);
                return this;
            }

            public Builder clearOriginalUuid() {
                copyOnWrite();
                ((IDMService) this.instance).i();
                return this;
            }

            public Builder setOriginalUuidBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMService) this.instance).d(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
            public String getSuperType() {
                return ((IDMService) this.instance).getSuperType();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMServiceOrBuilder
            public ByteString getSuperTypeBytes() {
                return ((IDMService) this.instance).getSuperTypeBytes();
            }

            public Builder setSuperType(String str) {
                copyOnWrite();
                ((IDMService) this.instance).e(str);
                return this;
            }

            public Builder clearSuperType() {
                copyOnWrite();
                ((IDMService) this.instance).j();
                return this;
            }

            public Builder setSuperTypeBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMService) this.instance).e(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMService();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(g, "\u0000\u0006\u0000\u0000\u0001\u0006\u0006\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ\u0004\t\u0005Ȉ\u0006Ȉ", new Object[]{"uuid_", "type_", "name_", "endpoint_", "originalUuid_", "superType_"});
                case GET_DEFAULT_INSTANCE:
                    return g;
                case GET_PARSER:
                    Parser<IDMService> parser = h;
                    if (parser == null) {
                        synchronized (IDMService.class) {
                            parser = h;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(g);
                                h = parser;
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
            IDMService iDMService = new IDMService();
            g = iDMService;
            GeneratedMessageLite.registerDefaultInstance(IDMService.class, iDMService);
        }

        public static IDMService getDefaultInstance() {
            return g;
        }

        public static Parser<IDMService> parser() {
            return g.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class Endpoint extends GeneratedMessageLite<Endpoint, Builder> implements EndpointOrBuilder {
        public static final int BDADDR_FIELD_NUMBER = 5;
        public static final int IDHASH_FIELD_NUMBER = 1;
        public static final int IP_FIELD_NUMBER = 4;
        public static final int MAC_FIELD_NUMBER = 3;
        public static final int MCVERSION_FIELD_NUMBER = 6;
        public static final int NAME_FIELD_NUMBER = 2;
        public static final int VERIFYSTATUS_FIELD_NUMBER = 7;
        private static final Endpoint h;
        private static volatile Parser<Endpoint> i;
        private String a = "";
        private String b = "";
        private String c = "";
        private String d = "";
        private String e = "";
        private int f;
        private int g;

        private Endpoint() {
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
        public String getIdhash() {
            return this.a;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
        public ByteString getIdhashBytes() {
            return ByteString.copyFromUtf8(this.a);
        }

        public void a(String str) {
            if (str != null) {
                this.a = str;
                return;
            }
            throw new NullPointerException();
        }

        public void e() {
            this.a = getDefaultInstance().getIdhash();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.a = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
        public String getName() {
            return this.b;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.b);
        }

        public void b(String str) {
            if (str != null) {
                this.b = str;
                return;
            }
            throw new NullPointerException();
        }

        public void f() {
            this.b = getDefaultInstance().getName();
        }

        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
        public String getMac() {
            return this.c;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
        public ByteString getMacBytes() {
            return ByteString.copyFromUtf8(this.c);
        }

        public void c(String str) {
            if (str != null) {
                this.c = str;
                return;
            }
            throw new NullPointerException();
        }

        public void g() {
            this.c = getDefaultInstance().getMac();
        }

        public void c(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.c = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
        public String getIp() {
            return this.d;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
        public ByteString getIpBytes() {
            return ByteString.copyFromUtf8(this.d);
        }

        public void d(String str) {
            if (str != null) {
                this.d = str;
                return;
            }
            throw new NullPointerException();
        }

        public void h() {
            this.d = getDefaultInstance().getIp();
        }

        public void d(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.d = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
        public String getBdAddr() {
            return this.e;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
        public ByteString getBdAddrBytes() {
            return ByteString.copyFromUtf8(this.e);
        }

        public void e(String str) {
            if (str != null) {
                this.e = str;
                return;
            }
            throw new NullPointerException();
        }

        public void i() {
            this.e = getDefaultInstance().getBdAddr();
        }

        public void e(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.e = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
        public int getMcVersion() {
            return this.f;
        }

        public void b(int i2) {
            this.f = i2;
        }

        public void j() {
            this.f = 0;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
        public int getVerifyStatus() {
            return this.g;
        }

        public void c(int i2) {
            this.g = i2;
        }

        public void k() {
            this.g = 0;
        }

        public static Endpoint parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Endpoint) GeneratedMessageLite.parseFrom(h, byteBuffer);
        }

        public static Endpoint parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Endpoint) GeneratedMessageLite.parseFrom(h, byteBuffer, extensionRegistryLite);
        }

        public static Endpoint parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Endpoint) GeneratedMessageLite.parseFrom(h, byteString);
        }

        public static Endpoint parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Endpoint) GeneratedMessageLite.parseFrom(h, byteString, extensionRegistryLite);
        }

        public static Endpoint parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Endpoint) GeneratedMessageLite.parseFrom(h, bArr);
        }

        public static Endpoint parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Endpoint) GeneratedMessageLite.parseFrom(h, bArr, extensionRegistryLite);
        }

        public static Endpoint parseFrom(InputStream inputStream) throws IOException {
            return (Endpoint) GeneratedMessageLite.parseFrom(h, inputStream);
        }

        public static Endpoint parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Endpoint) GeneratedMessageLite.parseFrom(h, inputStream, extensionRegistryLite);
        }

        public static Endpoint parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Endpoint) parseDelimitedFrom(h, inputStream);
        }

        public static Endpoint parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Endpoint) parseDelimitedFrom(h, inputStream, extensionRegistryLite);
        }

        public static Endpoint parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Endpoint) GeneratedMessageLite.parseFrom(h, codedInputStream);
        }

        public static Endpoint parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Endpoint) GeneratedMessageLite.parseFrom(h, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return h.createBuilder();
        }

        public static Builder newBuilder(Endpoint endpoint) {
            return h.createBuilder(endpoint);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<Endpoint, Builder> implements EndpointOrBuilder {
            private Builder() {
                super(Endpoint.h);
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
            public String getIdhash() {
                return ((Endpoint) this.instance).getIdhash();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
            public ByteString getIdhashBytes() {
                return ((Endpoint) this.instance).getIdhashBytes();
            }

            public Builder setIdhash(String str) {
                copyOnWrite();
                ((Endpoint) this.instance).a(str);
                return this;
            }

            public Builder clearIdhash() {
                copyOnWrite();
                ((Endpoint) this.instance).e();
                return this;
            }

            public Builder setIdhashBytes(ByteString byteString) {
                copyOnWrite();
                ((Endpoint) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
            public String getName() {
                return ((Endpoint) this.instance).getName();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
            public ByteString getNameBytes() {
                return ((Endpoint) this.instance).getNameBytes();
            }

            public Builder setName(String str) {
                copyOnWrite();
                ((Endpoint) this.instance).b(str);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((Endpoint) this.instance).f();
                return this;
            }

            public Builder setNameBytes(ByteString byteString) {
                copyOnWrite();
                ((Endpoint) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
            public String getMac() {
                return ((Endpoint) this.instance).getMac();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
            public ByteString getMacBytes() {
                return ((Endpoint) this.instance).getMacBytes();
            }

            public Builder setMac(String str) {
                copyOnWrite();
                ((Endpoint) this.instance).c(str);
                return this;
            }

            public Builder clearMac() {
                copyOnWrite();
                ((Endpoint) this.instance).g();
                return this;
            }

            public Builder setMacBytes(ByteString byteString) {
                copyOnWrite();
                ((Endpoint) this.instance).c(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
            public String getIp() {
                return ((Endpoint) this.instance).getIp();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
            public ByteString getIpBytes() {
                return ((Endpoint) this.instance).getIpBytes();
            }

            public Builder setIp(String str) {
                copyOnWrite();
                ((Endpoint) this.instance).d(str);
                return this;
            }

            public Builder clearIp() {
                copyOnWrite();
                ((Endpoint) this.instance).h();
                return this;
            }

            public Builder setIpBytes(ByteString byteString) {
                copyOnWrite();
                ((Endpoint) this.instance).d(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
            public String getBdAddr() {
                return ((Endpoint) this.instance).getBdAddr();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
            public ByteString getBdAddrBytes() {
                return ((Endpoint) this.instance).getBdAddrBytes();
            }

            public Builder setBdAddr(String str) {
                copyOnWrite();
                ((Endpoint) this.instance).e(str);
                return this;
            }

            public Builder clearBdAddr() {
                copyOnWrite();
                ((Endpoint) this.instance).i();
                return this;
            }

            public Builder setBdAddrBytes(ByteString byteString) {
                copyOnWrite();
                ((Endpoint) this.instance).e(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
            public int getMcVersion() {
                return ((Endpoint) this.instance).getMcVersion();
            }

            public Builder setMcVersion(int i) {
                copyOnWrite();
                ((Endpoint) this.instance).b(i);
                return this;
            }

            public Builder clearMcVersion() {
                copyOnWrite();
                ((Endpoint) this.instance).j();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.EndpointOrBuilder
            public int getVerifyStatus() {
                return ((Endpoint) this.instance).getVerifyStatus();
            }

            public Builder setVerifyStatus(int i) {
                copyOnWrite();
                ((Endpoint) this.instance).c(i);
                return this;
            }

            public Builder clearVerifyStatus() {
                copyOnWrite();
                ((Endpoint) this.instance).k();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new Endpoint();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(h, "\u0000\u0007\u0000\u0000\u0001\u0007\u0007\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ\u0004Ȉ\u0005Ȉ\u0006\u0004\u0007\u0004", new Object[]{"idhash_", "name_", "mac_", "ip_", "bdAddr_", "mcVersion_", "verifyStatus_"});
                case GET_DEFAULT_INSTANCE:
                    return h;
                case GET_PARSER:
                    Parser<Endpoint> parser = i;
                    if (parser == null) {
                        synchronized (Endpoint.class) {
                            parser = i;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(h);
                                i = parser;
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
            Endpoint endpoint = new Endpoint();
            h = endpoint;
            GeneratedMessageLite.registerDefaultInstance(Endpoint.class, endpoint);
        }

        public static Endpoint getDefaultInstance() {
            return h;
        }

        public static Parser<Endpoint> parser() {
            return h.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IDMRequest extends GeneratedMessageLite<IDMRequest, Builder> implements IDMRequestOrBuilder {
        public static final int AID_FIELD_NUMBER = 2;
        public static final int CLIENTID_FIELD_NUMBER = 4;
        public static final int REQUESTID_FIELD_NUMBER = 3;
        public static final int REQUEST_FIELD_NUMBER = 15;
        public static final int UUID_FIELD_NUMBER = 1;
        private static final IDMRequest f;
        private static volatile Parser<IDMRequest> g;
        private int b;
        private String a = "";
        private String c = "";
        private String d = "";
        private ByteString e = ByteString.EMPTY;

        private IDMRequest() {
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
        public String getUuid() {
            return this.a;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
        public ByteString getUuidBytes() {
            return ByteString.copyFromUtf8(this.a);
        }

        public void a(String str) {
            if (str != null) {
                this.a = str;
                return;
            }
            throw new NullPointerException();
        }

        public void e() {
            this.a = getDefaultInstance().getUuid();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.a = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
        public int getAid() {
            return this.b;
        }

        public void b(int i) {
            this.b = i;
        }

        public void f() {
            this.b = 0;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
        public String getRequestId() {
            return this.c;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
        public ByteString getRequestIdBytes() {
            return ByteString.copyFromUtf8(this.c);
        }

        public void b(String str) {
            if (str != null) {
                this.c = str;
                return;
            }
            throw new NullPointerException();
        }

        public void g() {
            this.c = getDefaultInstance().getRequestId();
        }

        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.c = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
        public String getClientId() {
            return this.d;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
        public ByteString getClientIdBytes() {
            return ByteString.copyFromUtf8(this.d);
        }

        public void c(String str) {
            if (str != null) {
                this.d = str;
                return;
            }
            throw new NullPointerException();
        }

        public void h() {
            this.d = getDefaultInstance().getClientId();
        }

        public void c(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.d = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
        public ByteString getRequest() {
            return this.e;
        }

        public void d(ByteString byteString) {
            if (byteString != null) {
                this.e = byteString;
                return;
            }
            throw new NullPointerException();
        }

        public void i() {
            this.e = getDefaultInstance().getRequest();
        }

        public static IDMRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMRequest) GeneratedMessageLite.parseFrom(f, byteBuffer);
        }

        public static IDMRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMRequest) GeneratedMessageLite.parseFrom(f, byteBuffer, extensionRegistryLite);
        }

        public static IDMRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMRequest) GeneratedMessageLite.parseFrom(f, byteString);
        }

        public static IDMRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMRequest) GeneratedMessageLite.parseFrom(f, byteString, extensionRegistryLite);
        }

        public static IDMRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMRequest) GeneratedMessageLite.parseFrom(f, bArr);
        }

        public static IDMRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMRequest) GeneratedMessageLite.parseFrom(f, bArr, extensionRegistryLite);
        }

        public static IDMRequest parseFrom(InputStream inputStream) throws IOException {
            return (IDMRequest) GeneratedMessageLite.parseFrom(f, inputStream);
        }

        public static IDMRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMRequest) GeneratedMessageLite.parseFrom(f, inputStream, extensionRegistryLite);
        }

        public static IDMRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMRequest) parseDelimitedFrom(f, inputStream);
        }

        public static IDMRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMRequest) parseDelimitedFrom(f, inputStream, extensionRegistryLite);
        }

        public static IDMRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMRequest) GeneratedMessageLite.parseFrom(f, codedInputStream);
        }

        public static IDMRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMRequest) GeneratedMessageLite.parseFrom(f, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return f.createBuilder();
        }

        public static Builder newBuilder(IDMRequest iDMRequest) {
            return f.createBuilder(iDMRequest);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMRequest, Builder> implements IDMRequestOrBuilder {
            private Builder() {
                super(IDMRequest.f);
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
            public String getUuid() {
                return ((IDMRequest) this.instance).getUuid();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
            public ByteString getUuidBytes() {
                return ((IDMRequest) this.instance).getUuidBytes();
            }

            public Builder setUuid(String str) {
                copyOnWrite();
                ((IDMRequest) this.instance).a(str);
                return this;
            }

            public Builder clearUuid() {
                copyOnWrite();
                ((IDMRequest) this.instance).e();
                return this;
            }

            public Builder setUuidBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMRequest) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
            public int getAid() {
                return ((IDMRequest) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((IDMRequest) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((IDMRequest) this.instance).f();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
            public String getRequestId() {
                return ((IDMRequest) this.instance).getRequestId();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
            public ByteString getRequestIdBytes() {
                return ((IDMRequest) this.instance).getRequestIdBytes();
            }

            public Builder setRequestId(String str) {
                copyOnWrite();
                ((IDMRequest) this.instance).b(str);
                return this;
            }

            public Builder clearRequestId() {
                copyOnWrite();
                ((IDMRequest) this.instance).g();
                return this;
            }

            public Builder setRequestIdBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMRequest) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
            public String getClientId() {
                return ((IDMRequest) this.instance).getClientId();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
            public ByteString getClientIdBytes() {
                return ((IDMRequest) this.instance).getClientIdBytes();
            }

            public Builder setClientId(String str) {
                copyOnWrite();
                ((IDMRequest) this.instance).c(str);
                return this;
            }

            public Builder clearClientId() {
                copyOnWrite();
                ((IDMRequest) this.instance).h();
                return this;
            }

            public Builder setClientIdBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMRequest) this.instance).c(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequestOrBuilder
            public ByteString getRequest() {
                return ((IDMRequest) this.instance).getRequest();
            }

            public Builder setRequest(ByteString byteString) {
                copyOnWrite();
                ((IDMRequest) this.instance).d(byteString);
                return this;
            }

            public Builder clearRequest() {
                copyOnWrite();
                ((IDMRequest) this.instance).i();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMRequest();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(f, "\u0000\u0005\u0000\u0000\u0001\u000f\u0005\u0000\u0000\u0000\u0001Ȉ\u0002\u0004\u0003Ȉ\u0004Ȉ\u000f\n", new Object[]{"uuid_", "aid_", "requestId_", "clientId_", "request_"});
                case GET_DEFAULT_INSTANCE:
                    return f;
                case GET_PARSER:
                    Parser<IDMRequest> parser = g;
                    if (parser == null) {
                        synchronized (IDMRequest.class) {
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
            IDMRequest iDMRequest = new IDMRequest();
            f = iDMRequest;
            GeneratedMessageLite.registerDefaultInstance(IDMRequest.class, iDMRequest);
        }

        public static IDMRequest getDefaultInstance() {
            return f;
        }

        public static Parser<IDMRequest> parser() {
            return f.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IDMResponse extends GeneratedMessageLite<IDMResponse, Builder> implements IDMResponseOrBuilder {
        public static final int CLIENTID_FIELD_NUMBER = 5;
        public static final int CODE_FIELD_NUMBER = 1;
        public static final int MSG_FIELD_NUMBER = 2;
        public static final int REQUESTID_FIELD_NUMBER = 3;
        public static final int RESPONSE_FIELD_NUMBER = 15;
        public static final int UUID_FIELD_NUMBER = 4;
        private static final IDMResponse g;
        private static volatile Parser<IDMResponse> h;
        private int a;
        private String b = "";
        private String c = "";
        private String d = "";
        private String e = "";
        private ByteString f = ByteString.EMPTY;

        private IDMResponse() {
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
        public int getCode() {
            return this.a;
        }

        public void b(int i) {
            this.a = i;
        }

        public void e() {
            this.a = 0;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
        public String getMsg() {
            return this.b;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
        public ByteString getMsgBytes() {
            return ByteString.copyFromUtf8(this.b);
        }

        public void a(String str) {
            if (str != null) {
                this.b = str;
                return;
            }
            throw new NullPointerException();
        }

        public void f() {
            this.b = getDefaultInstance().getMsg();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
        public String getRequestId() {
            return this.c;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
        public ByteString getRequestIdBytes() {
            return ByteString.copyFromUtf8(this.c);
        }

        public void b(String str) {
            if (str != null) {
                this.c = str;
                return;
            }
            throw new NullPointerException();
        }

        public void g() {
            this.c = getDefaultInstance().getRequestId();
        }

        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.c = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
        public String getUuid() {
            return this.d;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
        public ByteString getUuidBytes() {
            return ByteString.copyFromUtf8(this.d);
        }

        public void c(String str) {
            if (str != null) {
                this.d = str;
                return;
            }
            throw new NullPointerException();
        }

        public void h() {
            this.d = getDefaultInstance().getUuid();
        }

        public void c(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.d = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
        public String getClientId() {
            return this.e;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
        public ByteString getClientIdBytes() {
            return ByteString.copyFromUtf8(this.e);
        }

        public void d(String str) {
            if (str != null) {
                this.e = str;
                return;
            }
            throw new NullPointerException();
        }

        public void i() {
            this.e = getDefaultInstance().getClientId();
        }

        public void d(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.e = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
        public ByteString getResponse() {
            return this.f;
        }

        public void e(ByteString byteString) {
            if (byteString != null) {
                this.f = byteString;
                return;
            }
            throw new NullPointerException();
        }

        public void j() {
            this.f = getDefaultInstance().getResponse();
        }

        public static IDMResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMResponse) GeneratedMessageLite.parseFrom(g, byteBuffer);
        }

        public static IDMResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMResponse) GeneratedMessageLite.parseFrom(g, byteBuffer, extensionRegistryLite);
        }

        public static IDMResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMResponse) GeneratedMessageLite.parseFrom(g, byteString);
        }

        public static IDMResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMResponse) GeneratedMessageLite.parseFrom(g, byteString, extensionRegistryLite);
        }

        public static IDMResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMResponse) GeneratedMessageLite.parseFrom(g, bArr);
        }

        public static IDMResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMResponse) GeneratedMessageLite.parseFrom(g, bArr, extensionRegistryLite);
        }

        public static IDMResponse parseFrom(InputStream inputStream) throws IOException {
            return (IDMResponse) GeneratedMessageLite.parseFrom(g, inputStream);
        }

        public static IDMResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMResponse) GeneratedMessageLite.parseFrom(g, inputStream, extensionRegistryLite);
        }

        public static IDMResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMResponse) parseDelimitedFrom(g, inputStream);
        }

        public static IDMResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMResponse) parseDelimitedFrom(g, inputStream, extensionRegistryLite);
        }

        public static IDMResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMResponse) GeneratedMessageLite.parseFrom(g, codedInputStream);
        }

        public static IDMResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMResponse) GeneratedMessageLite.parseFrom(g, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return g.createBuilder();
        }

        public static Builder newBuilder(IDMResponse iDMResponse) {
            return g.createBuilder(iDMResponse);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMResponse, Builder> implements IDMResponseOrBuilder {
            private Builder() {
                super(IDMResponse.g);
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
            public int getCode() {
                return ((IDMResponse) this.instance).getCode();
            }

            public Builder setCode(int i) {
                copyOnWrite();
                ((IDMResponse) this.instance).b(i);
                return this;
            }

            public Builder clearCode() {
                copyOnWrite();
                ((IDMResponse) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
            public String getMsg() {
                return ((IDMResponse) this.instance).getMsg();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
            public ByteString getMsgBytes() {
                return ((IDMResponse) this.instance).getMsgBytes();
            }

            public Builder setMsg(String str) {
                copyOnWrite();
                ((IDMResponse) this.instance).a(str);
                return this;
            }

            public Builder clearMsg() {
                copyOnWrite();
                ((IDMResponse) this.instance).f();
                return this;
            }

            public Builder setMsgBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMResponse) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
            public String getRequestId() {
                return ((IDMResponse) this.instance).getRequestId();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
            public ByteString getRequestIdBytes() {
                return ((IDMResponse) this.instance).getRequestIdBytes();
            }

            public Builder setRequestId(String str) {
                copyOnWrite();
                ((IDMResponse) this.instance).b(str);
                return this;
            }

            public Builder clearRequestId() {
                copyOnWrite();
                ((IDMResponse) this.instance).g();
                return this;
            }

            public Builder setRequestIdBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMResponse) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
            public String getUuid() {
                return ((IDMResponse) this.instance).getUuid();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
            public ByteString getUuidBytes() {
                return ((IDMResponse) this.instance).getUuidBytes();
            }

            public Builder setUuid(String str) {
                copyOnWrite();
                ((IDMResponse) this.instance).c(str);
                return this;
            }

            public Builder clearUuid() {
                copyOnWrite();
                ((IDMResponse) this.instance).h();
                return this;
            }

            public Builder setUuidBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMResponse) this.instance).c(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
            public String getClientId() {
                return ((IDMResponse) this.instance).getClientId();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
            public ByteString getClientIdBytes() {
                return ((IDMResponse) this.instance).getClientIdBytes();
            }

            public Builder setClientId(String str) {
                copyOnWrite();
                ((IDMResponse) this.instance).d(str);
                return this;
            }

            public Builder clearClientId() {
                copyOnWrite();
                ((IDMResponse) this.instance).i();
                return this;
            }

            public Builder setClientIdBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMResponse) this.instance).d(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponseOrBuilder
            public ByteString getResponse() {
                return ((IDMResponse) this.instance).getResponse();
            }

            public Builder setResponse(ByteString byteString) {
                copyOnWrite();
                ((IDMResponse) this.instance).e(byteString);
                return this;
            }

            public Builder clearResponse() {
                copyOnWrite();
                ((IDMResponse) this.instance).j();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMResponse();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(g, "\u0000\u0006\u0000\u0000\u0001\u000f\u0006\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ\u0004Ȉ\u0005Ȉ\u000f\n", new Object[]{"code_", "msg_", "requestId_", "uuid_", "clientId_", "response_"});
                case GET_DEFAULT_INSTANCE:
                    return g;
                case GET_PARSER:
                    Parser<IDMResponse> parser = h;
                    if (parser == null) {
                        synchronized (IDMResponse.class) {
                            parser = h;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(g);
                                h = parser;
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
            IDMResponse iDMResponse = new IDMResponse();
            g = iDMResponse;
            GeneratedMessageLite.registerDefaultInstance(IDMResponse.class, iDMResponse);
        }

        public static IDMResponse getDefaultInstance() {
            return g;
        }

        public static Parser<IDMResponse> parser() {
            return g.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IDMEvent extends GeneratedMessageLite<IDMEvent, Builder> implements IDMEventOrBuilder {
        public static final int EID_FIELD_NUMBER = 2;
        public static final int ENABLE_FIELD_NUMBER = 3;
        public static final int EVENT_FIELD_NUMBER = 15;
        public static final int UUID_FIELD_NUMBER = 1;
        private static final IDMEvent e;
        private static volatile Parser<IDMEvent> f;
        private int b;
        private boolean c;
        private String a = "";
        private ByteString d = ByteString.EMPTY;

        private IDMEvent() {
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMEventOrBuilder
        public String getUuid() {
            return this.a;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMEventOrBuilder
        public ByteString getUuidBytes() {
            return ByteString.copyFromUtf8(this.a);
        }

        public void a(String str) {
            if (str != null) {
                this.a = str;
                return;
            }
            throw new NullPointerException();
        }

        public void e() {
            this.a = getDefaultInstance().getUuid();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.a = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMEventOrBuilder
        public int getEid() {
            return this.b;
        }

        public void b(int i) {
            this.b = i;
        }

        public void f() {
            this.b = 0;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMEventOrBuilder
        public boolean getEnable() {
            return this.c;
        }

        public void a(boolean z) {
            this.c = z;
        }

        public void g() {
            this.c = false;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMEventOrBuilder
        public ByteString getEvent() {
            return this.d;
        }

        public void b(ByteString byteString) {
            if (byteString != null) {
                this.d = byteString;
                return;
            }
            throw new NullPointerException();
        }

        public void h() {
            this.d = getDefaultInstance().getEvent();
        }

        public static IDMEvent parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMEvent) GeneratedMessageLite.parseFrom(e, byteBuffer);
        }

        public static IDMEvent parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMEvent) GeneratedMessageLite.parseFrom(e, byteBuffer, extensionRegistryLite);
        }

        public static IDMEvent parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMEvent) GeneratedMessageLite.parseFrom(e, byteString);
        }

        public static IDMEvent parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMEvent) GeneratedMessageLite.parseFrom(e, byteString, extensionRegistryLite);
        }

        public static IDMEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMEvent) GeneratedMessageLite.parseFrom(e, bArr);
        }

        public static IDMEvent parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMEvent) GeneratedMessageLite.parseFrom(e, bArr, extensionRegistryLite);
        }

        public static IDMEvent parseFrom(InputStream inputStream) throws IOException {
            return (IDMEvent) GeneratedMessageLite.parseFrom(e, inputStream);
        }

        public static IDMEvent parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMEvent) GeneratedMessageLite.parseFrom(e, inputStream, extensionRegistryLite);
        }

        public static IDMEvent parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMEvent) parseDelimitedFrom(e, inputStream);
        }

        public static IDMEvent parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMEvent) parseDelimitedFrom(e, inputStream, extensionRegistryLite);
        }

        public static IDMEvent parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMEvent) GeneratedMessageLite.parseFrom(e, codedInputStream);
        }

        public static IDMEvent parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMEvent) GeneratedMessageLite.parseFrom(e, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return e.createBuilder();
        }

        public static Builder newBuilder(IDMEvent iDMEvent) {
            return e.createBuilder(iDMEvent);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMEvent, Builder> implements IDMEventOrBuilder {
            private Builder() {
                super(IDMEvent.e);
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMEventOrBuilder
            public String getUuid() {
                return ((IDMEvent) this.instance).getUuid();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMEventOrBuilder
            public ByteString getUuidBytes() {
                return ((IDMEvent) this.instance).getUuidBytes();
            }

            public Builder setUuid(String str) {
                copyOnWrite();
                ((IDMEvent) this.instance).a(str);
                return this;
            }

            public Builder clearUuid() {
                copyOnWrite();
                ((IDMEvent) this.instance).e();
                return this;
            }

            public Builder setUuidBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMEvent) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMEventOrBuilder
            public int getEid() {
                return ((IDMEvent) this.instance).getEid();
            }

            public Builder setEid(int i) {
                copyOnWrite();
                ((IDMEvent) this.instance).b(i);
                return this;
            }

            public Builder clearEid() {
                copyOnWrite();
                ((IDMEvent) this.instance).f();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMEventOrBuilder
            public boolean getEnable() {
                return ((IDMEvent) this.instance).getEnable();
            }

            public Builder setEnable(boolean z) {
                copyOnWrite();
                ((IDMEvent) this.instance).a(z);
                return this;
            }

            public Builder clearEnable() {
                copyOnWrite();
                ((IDMEvent) this.instance).g();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMEventOrBuilder
            public ByteString getEvent() {
                return ((IDMEvent) this.instance).getEvent();
            }

            public Builder setEvent(ByteString byteString) {
                copyOnWrite();
                ((IDMEvent) this.instance).b(byteString);
                return this;
            }

            public Builder clearEvent() {
                copyOnWrite();
                ((IDMEvent) this.instance).h();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMEvent();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(e, "\u0000\u0004\u0000\u0000\u0001\u000f\u0004\u0000\u0000\u0000\u0001Ȉ\u0002\u0004\u0003\u0007\u000f\n", new Object[]{"uuid_", "eid_", "enable_", "event_"});
                case GET_DEFAULT_INSTANCE:
                    return e;
                case GET_PARSER:
                    Parser<IDMEvent> parser = f;
                    if (parser == null) {
                        synchronized (IDMEvent.class) {
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
            IDMEvent iDMEvent = new IDMEvent();
            e = iDMEvent;
            GeneratedMessageLite.registerDefaultInstance(IDMEvent.class, iDMEvent);
        }

        public static IDMEvent getDefaultInstance() {
            return e;
        }

        public static Parser<IDMEvent> parser() {
            return e.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IDMConnectServiceRequest extends GeneratedMessageLite<IDMConnectServiceRequest, Builder> implements IDMConnectServiceRequestOrBuilder {
        public static final int CLIENTID_FIELD_NUMBER = 5;
        public static final int CONNPARAM_FIELD_NUMBER = 4;
        public static final int ENDPOINT_FIELD_NUMBER = 3;
        public static final int SERVICEID_FIELD_NUMBER = 2;
        public static final int STATUS_FIELD_NUMBER = 1;
        private static final IDMConnectServiceRequest f;
        private static volatile Parser<IDMConnectServiceRequest> g;
        private int a;
        private Endpoint c;
        private ConnParam d;
        private String b = "";
        private String e = "";

        private IDMConnectServiceRequest() {
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
        public int getStatus() {
            return this.a;
        }

        public void b(int i) {
            this.a = i;
        }

        public void e() {
            this.a = 0;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
        public String getServiceId() {
            return this.b;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
        public ByteString getServiceIdBytes() {
            return ByteString.copyFromUtf8(this.b);
        }

        public void a(String str) {
            if (str != null) {
                this.b = str;
                return;
            }
            throw new NullPointerException();
        }

        public void f() {
            this.b = getDefaultInstance().getServiceId();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
        public boolean hasEndpoint() {
            return this.c != null;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
        public Endpoint getEndpoint() {
            Endpoint endpoint = this.c;
            return endpoint == null ? Endpoint.getDefaultInstance() : endpoint;
        }

        public void a(Endpoint endpoint) {
            if (endpoint != null) {
                this.c = endpoint;
                return;
            }
            throw new NullPointerException();
        }

        public void a(Endpoint.Builder builder) {
            this.c = builder.build();
        }

        public void b(Endpoint endpoint) {
            if (endpoint != null) {
                Endpoint endpoint2 = this.c;
                if (endpoint2 == null || endpoint2 == Endpoint.getDefaultInstance()) {
                    this.c = endpoint;
                } else {
                    this.c = Endpoint.newBuilder(this.c).mergeFrom((Endpoint.Builder) endpoint).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        public void g() {
            this.c = null;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
        public boolean hasConnParam() {
            return this.d != null;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
        public ConnParam getConnParam() {
            ConnParam connParam = this.d;
            return connParam == null ? ConnParam.getDefaultInstance() : connParam;
        }

        public void a(ConnParam connParam) {
            if (connParam != null) {
                this.d = connParam;
                return;
            }
            throw new NullPointerException();
        }

        public void a(ConnParam.Builder builder) {
            this.d = builder.build();
        }

        public void b(ConnParam connParam) {
            if (connParam != null) {
                ConnParam connParam2 = this.d;
                if (connParam2 == null || connParam2 == ConnParam.getDefaultInstance()) {
                    this.d = connParam;
                } else {
                    this.d = ConnParam.newBuilder(this.d).mergeFrom((ConnParam.Builder) connParam).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        public void h() {
            this.d = null;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
        public String getClientId() {
            return this.e;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
        public ByteString getClientIdBytes() {
            return ByteString.copyFromUtf8(this.e);
        }

        public void b(String str) {
            if (str != null) {
                this.e = str;
                return;
            }
            throw new NullPointerException();
        }

        public void i() {
            this.e = getDefaultInstance().getClientId();
        }

        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.e = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static IDMConnectServiceRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMConnectServiceRequest) GeneratedMessageLite.parseFrom(f, byteBuffer);
        }

        public static IDMConnectServiceRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMConnectServiceRequest) GeneratedMessageLite.parseFrom(f, byteBuffer, extensionRegistryLite);
        }

        public static IDMConnectServiceRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMConnectServiceRequest) GeneratedMessageLite.parseFrom(f, byteString);
        }

        public static IDMConnectServiceRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMConnectServiceRequest) GeneratedMessageLite.parseFrom(f, byteString, extensionRegistryLite);
        }

        public static IDMConnectServiceRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMConnectServiceRequest) GeneratedMessageLite.parseFrom(f, bArr);
        }

        public static IDMConnectServiceRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMConnectServiceRequest) GeneratedMessageLite.parseFrom(f, bArr, extensionRegistryLite);
        }

        public static IDMConnectServiceRequest parseFrom(InputStream inputStream) throws IOException {
            return (IDMConnectServiceRequest) GeneratedMessageLite.parseFrom(f, inputStream);
        }

        public static IDMConnectServiceRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMConnectServiceRequest) GeneratedMessageLite.parseFrom(f, inputStream, extensionRegistryLite);
        }

        public static IDMConnectServiceRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMConnectServiceRequest) parseDelimitedFrom(f, inputStream);
        }

        public static IDMConnectServiceRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMConnectServiceRequest) parseDelimitedFrom(f, inputStream, extensionRegistryLite);
        }

        public static IDMConnectServiceRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMConnectServiceRequest) GeneratedMessageLite.parseFrom(f, codedInputStream);
        }

        public static IDMConnectServiceRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMConnectServiceRequest) GeneratedMessageLite.parseFrom(f, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return f.createBuilder();
        }

        public static Builder newBuilder(IDMConnectServiceRequest iDMConnectServiceRequest) {
            return f.createBuilder(iDMConnectServiceRequest);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMConnectServiceRequest, Builder> implements IDMConnectServiceRequestOrBuilder {
            private Builder() {
                super(IDMConnectServiceRequest.f);
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
            public int getStatus() {
                return ((IDMConnectServiceRequest) this.instance).getStatus();
            }

            public Builder setStatus(int i) {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).b(i);
                return this;
            }

            public Builder clearStatus() {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
            public String getServiceId() {
                return ((IDMConnectServiceRequest) this.instance).getServiceId();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
            public ByteString getServiceIdBytes() {
                return ((IDMConnectServiceRequest) this.instance).getServiceIdBytes();
            }

            public Builder setServiceId(String str) {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).a(str);
                return this;
            }

            public Builder clearServiceId() {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).f();
                return this;
            }

            public Builder setServiceIdBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
            public boolean hasEndpoint() {
                return ((IDMConnectServiceRequest) this.instance).hasEndpoint();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
            public Endpoint getEndpoint() {
                return ((IDMConnectServiceRequest) this.instance).getEndpoint();
            }

            public Builder setEndpoint(Endpoint endpoint) {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).a(endpoint);
                return this;
            }

            public Builder setEndpoint(Endpoint.Builder builder) {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).a(builder);
                return this;
            }

            public Builder mergeEndpoint(Endpoint endpoint) {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).b(endpoint);
                return this;
            }

            public Builder clearEndpoint() {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).g();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
            public boolean hasConnParam() {
                return ((IDMConnectServiceRequest) this.instance).hasConnParam();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
            public ConnParam getConnParam() {
                return ((IDMConnectServiceRequest) this.instance).getConnParam();
            }

            public Builder setConnParam(ConnParam connParam) {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).a(connParam);
                return this;
            }

            public Builder setConnParam(ConnParam.Builder builder) {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).a(builder);
                return this;
            }

            public Builder mergeConnParam(ConnParam connParam) {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).b(connParam);
                return this;
            }

            public Builder clearConnParam() {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).h();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
            public String getClientId() {
                return ((IDMConnectServiceRequest) this.instance).getClientId();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceRequestOrBuilder
            public ByteString getClientIdBytes() {
                return ((IDMConnectServiceRequest) this.instance).getClientIdBytes();
            }

            public Builder setClientId(String str) {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).b(str);
                return this;
            }

            public Builder clearClientId() {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).i();
                return this;
            }

            public Builder setClientIdBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMConnectServiceRequest) this.instance).b(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMConnectServiceRequest();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(f, "\u0000\u0005\u0000\u0000\u0001\u0005\u0005\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003\t\u0004\t\u0005Ȉ", new Object[]{"status_", "serviceId_", "endpoint_", "connParam_", "clientId_"});
                case GET_DEFAULT_INSTANCE:
                    return f;
                case GET_PARSER:
                    Parser<IDMConnectServiceRequest> parser = g;
                    if (parser == null) {
                        synchronized (IDMConnectServiceRequest.class) {
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
            IDMConnectServiceRequest iDMConnectServiceRequest = new IDMConnectServiceRequest();
            f = iDMConnectServiceRequest;
            GeneratedMessageLite.registerDefaultInstance(IDMConnectServiceRequest.class, iDMConnectServiceRequest);
        }

        public static IDMConnectServiceRequest getDefaultInstance() {
            return f;
        }

        public static Parser<IDMConnectServiceRequest> parser() {
            return f.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IDMConnectServiceResponse extends GeneratedMessageLite<IDMConnectServiceResponse, Builder> implements IDMConnectServiceResponseOrBuilder {
        public static final int CLIENTID_FIELD_NUMBER = 5;
        public static final int CONNLEVEL_FIELD_NUMBER = 6;
        public static final int CONNPARAM_FIELD_NUMBER = 4;
        public static final int ENDPOINT_FIELD_NUMBER = 3;
        public static final int SERVICEID_FIELD_NUMBER = 2;
        public static final int STATUS_FIELD_NUMBER = 1;
        private static final IDMConnectServiceResponse g;
        private static volatile Parser<IDMConnectServiceResponse> h;
        private int a;
        private Endpoint c;
        private ConnParam d;
        private int f;
        private String b = "";
        private String e = "";

        private IDMConnectServiceResponse() {
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
        public int getStatus() {
            return this.a;
        }

        public void b(int i) {
            this.a = i;
        }

        public void e() {
            this.a = 0;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
        public String getServiceId() {
            return this.b;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
        public ByteString getServiceIdBytes() {
            return ByteString.copyFromUtf8(this.b);
        }

        public void a(String str) {
            if (str != null) {
                this.b = str;
                return;
            }
            throw new NullPointerException();
        }

        public void f() {
            this.b = getDefaultInstance().getServiceId();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
        public boolean hasEndpoint() {
            return this.c != null;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
        public Endpoint getEndpoint() {
            Endpoint endpoint = this.c;
            return endpoint == null ? Endpoint.getDefaultInstance() : endpoint;
        }

        public void a(Endpoint endpoint) {
            if (endpoint != null) {
                this.c = endpoint;
                return;
            }
            throw new NullPointerException();
        }

        public void a(Endpoint.Builder builder) {
            this.c = builder.build();
        }

        public void b(Endpoint endpoint) {
            if (endpoint != null) {
                Endpoint endpoint2 = this.c;
                if (endpoint2 == null || endpoint2 == Endpoint.getDefaultInstance()) {
                    this.c = endpoint;
                } else {
                    this.c = Endpoint.newBuilder(this.c).mergeFrom((Endpoint.Builder) endpoint).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        public void g() {
            this.c = null;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
        public boolean hasConnParam() {
            return this.d != null;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
        public ConnParam getConnParam() {
            ConnParam connParam = this.d;
            return connParam == null ? ConnParam.getDefaultInstance() : connParam;
        }

        public void a(ConnParam connParam) {
            if (connParam != null) {
                this.d = connParam;
                return;
            }
            throw new NullPointerException();
        }

        public void a(ConnParam.Builder builder) {
            this.d = builder.build();
        }

        public void b(ConnParam connParam) {
            if (connParam != null) {
                ConnParam connParam2 = this.d;
                if (connParam2 == null || connParam2 == ConnParam.getDefaultInstance()) {
                    this.d = connParam;
                } else {
                    this.d = ConnParam.newBuilder(this.d).mergeFrom((ConnParam.Builder) connParam).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        public void h() {
            this.d = null;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
        public String getClientId() {
            return this.e;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
        public ByteString getClientIdBytes() {
            return ByteString.copyFromUtf8(this.e);
        }

        public void b(String str) {
            if (str != null) {
                this.e = str;
                return;
            }
            throw new NullPointerException();
        }

        public void i() {
            this.e = getDefaultInstance().getClientId();
        }

        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.e = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
        public int getConnLevel() {
            return this.f;
        }

        public void c(int i) {
            this.f = i;
        }

        public void j() {
            this.f = 0;
        }

        public static IDMConnectServiceResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMConnectServiceResponse) GeneratedMessageLite.parseFrom(g, byteBuffer);
        }

        public static IDMConnectServiceResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMConnectServiceResponse) GeneratedMessageLite.parseFrom(g, byteBuffer, extensionRegistryLite);
        }

        public static IDMConnectServiceResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMConnectServiceResponse) GeneratedMessageLite.parseFrom(g, byteString);
        }

        public static IDMConnectServiceResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMConnectServiceResponse) GeneratedMessageLite.parseFrom(g, byteString, extensionRegistryLite);
        }

        public static IDMConnectServiceResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMConnectServiceResponse) GeneratedMessageLite.parseFrom(g, bArr);
        }

        public static IDMConnectServiceResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMConnectServiceResponse) GeneratedMessageLite.parseFrom(g, bArr, extensionRegistryLite);
        }

        public static IDMConnectServiceResponse parseFrom(InputStream inputStream) throws IOException {
            return (IDMConnectServiceResponse) GeneratedMessageLite.parseFrom(g, inputStream);
        }

        public static IDMConnectServiceResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMConnectServiceResponse) GeneratedMessageLite.parseFrom(g, inputStream, extensionRegistryLite);
        }

        public static IDMConnectServiceResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMConnectServiceResponse) parseDelimitedFrom(g, inputStream);
        }

        public static IDMConnectServiceResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMConnectServiceResponse) parseDelimitedFrom(g, inputStream, extensionRegistryLite);
        }

        public static IDMConnectServiceResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMConnectServiceResponse) GeneratedMessageLite.parseFrom(g, codedInputStream);
        }

        public static IDMConnectServiceResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMConnectServiceResponse) GeneratedMessageLite.parseFrom(g, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return g.createBuilder();
        }

        public static Builder newBuilder(IDMConnectServiceResponse iDMConnectServiceResponse) {
            return g.createBuilder(iDMConnectServiceResponse);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMConnectServiceResponse, Builder> implements IDMConnectServiceResponseOrBuilder {
            private Builder() {
                super(IDMConnectServiceResponse.g);
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
            public int getStatus() {
                return ((IDMConnectServiceResponse) this.instance).getStatus();
            }

            public Builder setStatus(int i) {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).b(i);
                return this;
            }

            public Builder clearStatus() {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
            public String getServiceId() {
                return ((IDMConnectServiceResponse) this.instance).getServiceId();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
            public ByteString getServiceIdBytes() {
                return ((IDMConnectServiceResponse) this.instance).getServiceIdBytes();
            }

            public Builder setServiceId(String str) {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).a(str);
                return this;
            }

            public Builder clearServiceId() {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).f();
                return this;
            }

            public Builder setServiceIdBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
            public boolean hasEndpoint() {
                return ((IDMConnectServiceResponse) this.instance).hasEndpoint();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
            public Endpoint getEndpoint() {
                return ((IDMConnectServiceResponse) this.instance).getEndpoint();
            }

            public Builder setEndpoint(Endpoint endpoint) {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).a(endpoint);
                return this;
            }

            public Builder setEndpoint(Endpoint.Builder builder) {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).a(builder);
                return this;
            }

            public Builder mergeEndpoint(Endpoint endpoint) {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).b(endpoint);
                return this;
            }

            public Builder clearEndpoint() {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).g();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
            public boolean hasConnParam() {
                return ((IDMConnectServiceResponse) this.instance).hasConnParam();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
            public ConnParam getConnParam() {
                return ((IDMConnectServiceResponse) this.instance).getConnParam();
            }

            public Builder setConnParam(ConnParam connParam) {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).a(connParam);
                return this;
            }

            public Builder setConnParam(ConnParam.Builder builder) {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).a(builder);
                return this;
            }

            public Builder mergeConnParam(ConnParam connParam) {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).b(connParam);
                return this;
            }

            public Builder clearConnParam() {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).h();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
            public String getClientId() {
                return ((IDMConnectServiceResponse) this.instance).getClientId();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
            public ByteString getClientIdBytes() {
                return ((IDMConnectServiceResponse) this.instance).getClientIdBytes();
            }

            public Builder setClientId(String str) {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).b(str);
                return this;
            }

            public Builder clearClientId() {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).i();
                return this;
            }

            public Builder setClientIdBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMConnectServiceResponseOrBuilder
            public int getConnLevel() {
                return ((IDMConnectServiceResponse) this.instance).getConnLevel();
            }

            public Builder setConnLevel(int i) {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).c(i);
                return this;
            }

            public Builder clearConnLevel() {
                copyOnWrite();
                ((IDMConnectServiceResponse) this.instance).j();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMConnectServiceResponse();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(g, "\u0000\u0006\u0000\u0000\u0001\u0006\u0006\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003\t\u0004\t\u0005Ȉ\u0006\u0004", new Object[]{"status_", "serviceId_", "endpoint_", "connParam_", "clientId_", "connLevel_"});
                case GET_DEFAULT_INSTANCE:
                    return g;
                case GET_PARSER:
                    Parser<IDMConnectServiceResponse> parser = h;
                    if (parser == null) {
                        synchronized (IDMConnectServiceResponse.class) {
                            parser = h;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(g);
                                h = parser;
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
            IDMConnectServiceResponse iDMConnectServiceResponse = new IDMConnectServiceResponse();
            g = iDMConnectServiceResponse;
            GeneratedMessageLite.registerDefaultInstance(IDMConnectServiceResponse.class, iDMConnectServiceResponse);
        }

        public static IDMConnectServiceResponse getDefaultInstance() {
            return g;
        }

        public static Parser<IDMConnectServiceResponse> parser() {
            return g.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IDMAdvertisingResult extends GeneratedMessageLite<IDMAdvertisingResult, Builder> implements IDMAdvertisingResultOrBuilder {
        public static final int SERVICEID_FIELD_NUMBER = 2;
        public static final int STATUS_FIELD_NUMBER = 1;
        private static final IDMAdvertisingResult c;
        private static volatile Parser<IDMAdvertisingResult> d;
        private int a;
        private String b = "";

        private IDMAdvertisingResult() {
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMAdvertisingResultOrBuilder
        public int getStatus() {
            return this.a;
        }

        public void b(int i) {
            this.a = i;
        }

        public void e() {
            this.a = 0;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMAdvertisingResultOrBuilder
        public String getServiceId() {
            return this.b;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMAdvertisingResultOrBuilder
        public ByteString getServiceIdBytes() {
            return ByteString.copyFromUtf8(this.b);
        }

        public void a(String str) {
            if (str != null) {
                this.b = str;
                return;
            }
            throw new NullPointerException();
        }

        public void f() {
            this.b = getDefaultInstance().getServiceId();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static IDMAdvertisingResult parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMAdvertisingResult) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static IDMAdvertisingResult parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMAdvertisingResult) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static IDMAdvertisingResult parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMAdvertisingResult) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static IDMAdvertisingResult parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMAdvertisingResult) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static IDMAdvertisingResult parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMAdvertisingResult) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static IDMAdvertisingResult parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMAdvertisingResult) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static IDMAdvertisingResult parseFrom(InputStream inputStream) throws IOException {
            return (IDMAdvertisingResult) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static IDMAdvertisingResult parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMAdvertisingResult) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static IDMAdvertisingResult parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMAdvertisingResult) parseDelimitedFrom(c, inputStream);
        }

        public static IDMAdvertisingResult parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMAdvertisingResult) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static IDMAdvertisingResult parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMAdvertisingResult) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static IDMAdvertisingResult parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMAdvertisingResult) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(IDMAdvertisingResult iDMAdvertisingResult) {
            return c.createBuilder(iDMAdvertisingResult);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMAdvertisingResult, Builder> implements IDMAdvertisingResultOrBuilder {
            private Builder() {
                super(IDMAdvertisingResult.c);
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMAdvertisingResultOrBuilder
            public int getStatus() {
                return ((IDMAdvertisingResult) this.instance).getStatus();
            }

            public Builder setStatus(int i) {
                copyOnWrite();
                ((IDMAdvertisingResult) this.instance).b(i);
                return this;
            }

            public Builder clearStatus() {
                copyOnWrite();
                ((IDMAdvertisingResult) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMAdvertisingResultOrBuilder
            public String getServiceId() {
                return ((IDMAdvertisingResult) this.instance).getServiceId();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.IDMAdvertisingResultOrBuilder
            public ByteString getServiceIdBytes() {
                return ((IDMAdvertisingResult) this.instance).getServiceIdBytes();
            }

            public Builder setServiceId(String str) {
                copyOnWrite();
                ((IDMAdvertisingResult) this.instance).a(str);
                return this;
            }

            public Builder clearServiceId() {
                copyOnWrite();
                ((IDMAdvertisingResult) this.instance).f();
                return this;
            }

            public Builder setServiceIdBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMAdvertisingResult) this.instance).a(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMAdvertisingResult();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0002Ȉ", new Object[]{"status_", "serviceId_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<IDMAdvertisingResult> parser = d;
                    if (parser == null) {
                        synchronized (IDMAdvertisingResult.class) {
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
            IDMAdvertisingResult iDMAdvertisingResult = new IDMAdvertisingResult();
            c = iDMAdvertisingResult;
            GeneratedMessageLite.registerDefaultInstance(IDMAdvertisingResult.class, iDMAdvertisingResult);
        }

        public static IDMAdvertisingResult getDefaultInstance() {
            return c;
        }

        public static Parser<IDMAdvertisingResult> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class OnServiceChangeResult extends GeneratedMessageLite<OnServiceChangeResult, Builder> implements OnServiceChangeResultOrBuilder {
        public static final int NEWSERVICEID_FIELD_NUMBER = 2;
        public static final int OLDSERVICEID_FIELD_NUMBER = 1;
        private static final OnServiceChangeResult c;
        private static volatile Parser<OnServiceChangeResult> d;
        private String a = "";
        private String b = "";

        private OnServiceChangeResult() {
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnServiceChangeResultOrBuilder
        public String getOldServiceId() {
            return this.a;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnServiceChangeResultOrBuilder
        public ByteString getOldServiceIdBytes() {
            return ByteString.copyFromUtf8(this.a);
        }

        public void a(String str) {
            if (str != null) {
                this.a = str;
                return;
            }
            throw new NullPointerException();
        }

        public void e() {
            this.a = getDefaultInstance().getOldServiceId();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.a = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnServiceChangeResultOrBuilder
        public String getNewServiceId() {
            return this.b;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnServiceChangeResultOrBuilder
        public ByteString getNewServiceIdBytes() {
            return ByteString.copyFromUtf8(this.b);
        }

        public void b(String str) {
            if (str != null) {
                this.b = str;
                return;
            }
            throw new NullPointerException();
        }

        public void f() {
            this.b = getDefaultInstance().getNewServiceId();
        }

        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static OnServiceChangeResult parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (OnServiceChangeResult) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static OnServiceChangeResult parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnServiceChangeResult) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static OnServiceChangeResult parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (OnServiceChangeResult) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static OnServiceChangeResult parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnServiceChangeResult) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static OnServiceChangeResult parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (OnServiceChangeResult) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static OnServiceChangeResult parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnServiceChangeResult) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static OnServiceChangeResult parseFrom(InputStream inputStream) throws IOException {
            return (OnServiceChangeResult) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static OnServiceChangeResult parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnServiceChangeResult) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static OnServiceChangeResult parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (OnServiceChangeResult) parseDelimitedFrom(c, inputStream);
        }

        public static OnServiceChangeResult parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnServiceChangeResult) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static OnServiceChangeResult parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (OnServiceChangeResult) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static OnServiceChangeResult parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnServiceChangeResult) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(OnServiceChangeResult onServiceChangeResult) {
            return c.createBuilder(onServiceChangeResult);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<OnServiceChangeResult, Builder> implements OnServiceChangeResultOrBuilder {
            private Builder() {
                super(OnServiceChangeResult.c);
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnServiceChangeResultOrBuilder
            public String getOldServiceId() {
                return ((OnServiceChangeResult) this.instance).getOldServiceId();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnServiceChangeResultOrBuilder
            public ByteString getOldServiceIdBytes() {
                return ((OnServiceChangeResult) this.instance).getOldServiceIdBytes();
            }

            public Builder setOldServiceId(String str) {
                copyOnWrite();
                ((OnServiceChangeResult) this.instance).a(str);
                return this;
            }

            public Builder clearOldServiceId() {
                copyOnWrite();
                ((OnServiceChangeResult) this.instance).e();
                return this;
            }

            public Builder setOldServiceIdBytes(ByteString byteString) {
                copyOnWrite();
                ((OnServiceChangeResult) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnServiceChangeResultOrBuilder
            public String getNewServiceId() {
                return ((OnServiceChangeResult) this.instance).getNewServiceId();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnServiceChangeResultOrBuilder
            public ByteString getNewServiceIdBytes() {
                return ((OnServiceChangeResult) this.instance).getNewServiceIdBytes();
            }

            public Builder setNewServiceId(String str) {
                copyOnWrite();
                ((OnServiceChangeResult) this.instance).b(str);
                return this;
            }

            public Builder clearNewServiceId() {
                copyOnWrite();
                ((OnServiceChangeResult) this.instance).f();
                return this;
            }

            public Builder setNewServiceIdBytes(ByteString byteString) {
                copyOnWrite();
                ((OnServiceChangeResult) this.instance).b(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new OnServiceChangeResult();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ", new Object[]{"oldServiceId_", "newServiceId_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<OnServiceChangeResult> parser = d;
                    if (parser == null) {
                        synchronized (OnServiceChangeResult.class) {
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
            OnServiceChangeResult onServiceChangeResult = new OnServiceChangeResult();
            c = onServiceChangeResult;
            GeneratedMessageLite.registerDefaultInstance(OnServiceChangeResult.class, onServiceChangeResult);
        }

        public static OnServiceChangeResult getDefaultInstance() {
            return c;
        }

        public static Parser<OnServiceChangeResult> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class OnAccountChangeResult extends GeneratedMessageLite<OnAccountChangeResult, Builder> implements OnAccountChangeResultOrBuilder {
        public static final int NEWACCOUNT_FIELD_NUMBER = 4;
        public static final int NEWIDHASH_FIELD_NUMBER = 2;
        public static final int OLDACCOUNT_FIELD_NUMBER = 3;
        public static final int SUBCHANGETYPE_FIELD_NUMBER = 1;
        private static final OnAccountChangeResult e;
        private static volatile Parser<OnAccountChangeResult> f;
        private int a;
        private ByteString b = ByteString.EMPTY;
        private String c = "";
        private String d = "";

        private OnAccountChangeResult() {
        }

        /* loaded from: classes3.dex */
        public enum SubChangeType implements Internal.EnumLite {
            LOGIN(0),
            LOGOUT(1),
            CHANGE(2),
            UNRECOGNIZED(-1);
            
            public static final int CHANGE_VALUE = 2;
            public static final int LOGIN_VALUE = 0;
            public static final int LOGOUT_VALUE = 1;
            private static final Internal.EnumLiteMap<SubChangeType> a = new Internal.EnumLiteMap<SubChangeType>() { // from class: com.xiaomi.idm.api.proto.IDMServiceProto.OnAccountChangeResult.SubChangeType.1
                /* renamed from: a */
                public SubChangeType findValueByNumber(int i) {
                    return SubChangeType.forNumber(i);
                }
            };
            private final int value;

            @Override // com.google.protobuf.Internal.EnumLite
            public final int getNumber() {
                if (this != UNRECOGNIZED) {
                    return this.value;
                }
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }

            @Deprecated
            public static SubChangeType valueOf(int i) {
                return forNumber(i);
            }

            public static SubChangeType forNumber(int i) {
                switch (i) {
                    case 0:
                        return LOGIN;
                    case 1:
                        return LOGOUT;
                    case 2:
                        return CHANGE;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<SubChangeType> internalGetValueMap() {
                return a;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return a.a;
            }

            /* loaded from: classes3.dex */
            private static final class a implements Internal.EnumVerifier {
                static final Internal.EnumVerifier a = new a();

                private a() {
                }

                @Override // com.google.protobuf.Internal.EnumVerifier
                public boolean isInRange(int i) {
                    return SubChangeType.forNumber(i) != null;
                }
            }

            SubChangeType(int i) {
                this.value = i;
            }
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnAccountChangeResultOrBuilder
        public int getSubChangeTypeValue() {
            return this.a;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnAccountChangeResultOrBuilder
        public SubChangeType getSubChangeType() {
            SubChangeType forNumber = SubChangeType.forNumber(this.a);
            return forNumber == null ? SubChangeType.UNRECOGNIZED : forNumber;
        }

        public void b(int i) {
            this.a = i;
        }

        public void a(SubChangeType subChangeType) {
            if (subChangeType != null) {
                this.a = subChangeType.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        public void e() {
            this.a = 0;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnAccountChangeResultOrBuilder
        public ByteString getNewIdHash() {
            return this.b;
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                this.b = byteString;
                return;
            }
            throw new NullPointerException();
        }

        public void f() {
            this.b = getDefaultInstance().getNewIdHash();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnAccountChangeResultOrBuilder
        public String getOldAccount() {
            return this.c;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnAccountChangeResultOrBuilder
        public ByteString getOldAccountBytes() {
            return ByteString.copyFromUtf8(this.c);
        }

        public void a(String str) {
            if (str != null) {
                this.c = str;
                return;
            }
            throw new NullPointerException();
        }

        public void g() {
            this.c = getDefaultInstance().getOldAccount();
        }

        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.c = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnAccountChangeResultOrBuilder
        public String getNewAccount() {
            return this.d;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnAccountChangeResultOrBuilder
        public ByteString getNewAccountBytes() {
            return ByteString.copyFromUtf8(this.d);
        }

        public void b(String str) {
            if (str != null) {
                this.d = str;
                return;
            }
            throw new NullPointerException();
        }

        public void h() {
            this.d = getDefaultInstance().getNewAccount();
        }

        public void c(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.d = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static OnAccountChangeResult parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (OnAccountChangeResult) GeneratedMessageLite.parseFrom(e, byteBuffer);
        }

        public static OnAccountChangeResult parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnAccountChangeResult) GeneratedMessageLite.parseFrom(e, byteBuffer, extensionRegistryLite);
        }

        public static OnAccountChangeResult parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (OnAccountChangeResult) GeneratedMessageLite.parseFrom(e, byteString);
        }

        public static OnAccountChangeResult parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnAccountChangeResult) GeneratedMessageLite.parseFrom(e, byteString, extensionRegistryLite);
        }

        public static OnAccountChangeResult parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (OnAccountChangeResult) GeneratedMessageLite.parseFrom(e, bArr);
        }

        public static OnAccountChangeResult parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnAccountChangeResult) GeneratedMessageLite.parseFrom(e, bArr, extensionRegistryLite);
        }

        public static OnAccountChangeResult parseFrom(InputStream inputStream) throws IOException {
            return (OnAccountChangeResult) GeneratedMessageLite.parseFrom(e, inputStream);
        }

        public static OnAccountChangeResult parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnAccountChangeResult) GeneratedMessageLite.parseFrom(e, inputStream, extensionRegistryLite);
        }

        public static OnAccountChangeResult parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (OnAccountChangeResult) parseDelimitedFrom(e, inputStream);
        }

        public static OnAccountChangeResult parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnAccountChangeResult) parseDelimitedFrom(e, inputStream, extensionRegistryLite);
        }

        public static OnAccountChangeResult parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (OnAccountChangeResult) GeneratedMessageLite.parseFrom(e, codedInputStream);
        }

        public static OnAccountChangeResult parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnAccountChangeResult) GeneratedMessageLite.parseFrom(e, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return e.createBuilder();
        }

        public static Builder newBuilder(OnAccountChangeResult onAccountChangeResult) {
            return e.createBuilder(onAccountChangeResult);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<OnAccountChangeResult, Builder> implements OnAccountChangeResultOrBuilder {
            private Builder() {
                super(OnAccountChangeResult.e);
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnAccountChangeResultOrBuilder
            public int getSubChangeTypeValue() {
                return ((OnAccountChangeResult) this.instance).getSubChangeTypeValue();
            }

            public Builder setSubChangeTypeValue(int i) {
                copyOnWrite();
                ((OnAccountChangeResult) this.instance).b(i);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnAccountChangeResultOrBuilder
            public SubChangeType getSubChangeType() {
                return ((OnAccountChangeResult) this.instance).getSubChangeType();
            }

            public Builder setSubChangeType(SubChangeType subChangeType) {
                copyOnWrite();
                ((OnAccountChangeResult) this.instance).a(subChangeType);
                return this;
            }

            public Builder clearSubChangeType() {
                copyOnWrite();
                ((OnAccountChangeResult) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnAccountChangeResultOrBuilder
            public ByteString getNewIdHash() {
                return ((OnAccountChangeResult) this.instance).getNewIdHash();
            }

            public Builder setNewIdHash(ByteString byteString) {
                copyOnWrite();
                ((OnAccountChangeResult) this.instance).a(byteString);
                return this;
            }

            public Builder clearNewIdHash() {
                copyOnWrite();
                ((OnAccountChangeResult) this.instance).f();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnAccountChangeResultOrBuilder
            public String getOldAccount() {
                return ((OnAccountChangeResult) this.instance).getOldAccount();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnAccountChangeResultOrBuilder
            public ByteString getOldAccountBytes() {
                return ((OnAccountChangeResult) this.instance).getOldAccountBytes();
            }

            public Builder setOldAccount(String str) {
                copyOnWrite();
                ((OnAccountChangeResult) this.instance).a(str);
                return this;
            }

            public Builder clearOldAccount() {
                copyOnWrite();
                ((OnAccountChangeResult) this.instance).g();
                return this;
            }

            public Builder setOldAccountBytes(ByteString byteString) {
                copyOnWrite();
                ((OnAccountChangeResult) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnAccountChangeResultOrBuilder
            public String getNewAccount() {
                return ((OnAccountChangeResult) this.instance).getNewAccount();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.OnAccountChangeResultOrBuilder
            public ByteString getNewAccountBytes() {
                return ((OnAccountChangeResult) this.instance).getNewAccountBytes();
            }

            public Builder setNewAccount(String str) {
                copyOnWrite();
                ((OnAccountChangeResult) this.instance).b(str);
                return this;
            }

            public Builder clearNewAccount() {
                copyOnWrite();
                ((OnAccountChangeResult) this.instance).h();
                return this;
            }

            public Builder setNewAccountBytes(ByteString byteString) {
                copyOnWrite();
                ((OnAccountChangeResult) this.instance).c(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new OnAccountChangeResult();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(e, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\f\u0002\n\u0003Ȉ\u0004Ȉ", new Object[]{"subChangeType_", "newIdHash_", "oldAccount_", "newAccount_"});
                case GET_DEFAULT_INSTANCE:
                    return e;
                case GET_PARSER:
                    Parser<OnAccountChangeResult> parser = f;
                    if (parser == null) {
                        synchronized (OnAccountChangeResult.class) {
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
            OnAccountChangeResult onAccountChangeResult = new OnAccountChangeResult();
            e = onAccountChangeResult;
            GeneratedMessageLite.registerDefaultInstance(OnAccountChangeResult.class, onAccountChangeResult);
        }

        public static OnAccountChangeResult getDefaultInstance() {
            return e;
        }

        public static Parser<OnAccountChangeResult> parser() {
            return e.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class ConnParam extends GeneratedMessageLite<ConnParam, Builder> implements ConnParamOrBuilder {
        public static final int CONFIG_FIELD_NUMBER = 15;
        public static final int CONNLEVEL_FIELD_NUMBER = 5;
        public static final int CONNTYPE_FIELD_NUMBER = 1;
        public static final int ERRCODE_FIELD_NUMBER = 2;
        public static final int ERRMSG_FIELD_NUMBER = 3;
        public static final int IDHASH_FIELD_NUMBER = 4;
        public static final int PRIVATEDATA_FIELD_NUMBER = 6;
        private static final ConnParam h;
        private static volatile Parser<ConnParam> i;
        private int a;
        private int b;
        private int e;
        private String c = "";
        private String d = "";
        private ByteString f = ByteString.EMPTY;
        private ByteString g = ByteString.EMPTY;

        private ConnParam() {
        }

        /* loaded from: classes3.dex */
        public enum ConnType implements Internal.EnumLite {
            WIFI_P2P_GO(0),
            WIFI_P2P_GC(1),
            WIFI_SOFTAP(2),
            WIFI_STATION(3),
            BT_RFCOMM(4),
            BT_GATT(5),
            BLE_GATT(6),
            COAP(7),
            NFC(8),
            IDB(9),
            WLAN_P2P(10),
            WLAN_SOFTAP(11),
            WLAN_GC_SOFTAP(12),
            UNKNOWN(-1),
            UNRECOGNIZED(-1);
            
            public static final int BLE_GATT_VALUE = 6;
            public static final int BT_GATT_VALUE = 5;
            public static final int BT_RFCOMM_VALUE = 4;
            public static final int COAP_VALUE = 7;
            public static final int IDB_VALUE = 9;
            public static final int NFC_VALUE = 8;
            public static final int UNKNOWN_VALUE = -1;
            public static final int WIFI_P2P_GC_VALUE = 1;
            public static final int WIFI_P2P_GO_VALUE = 0;
            public static final int WIFI_SOFTAP_VALUE = 2;
            public static final int WIFI_STATION_VALUE = 3;
            public static final int WLAN_GC_SOFTAP_VALUE = 12;
            public static final int WLAN_P2P_VALUE = 10;
            public static final int WLAN_SOFTAP_VALUE = 11;
            private static final Internal.EnumLiteMap<ConnType> a = new Internal.EnumLiteMap<ConnType>() { // from class: com.xiaomi.idm.api.proto.IDMServiceProto.ConnParam.ConnType.1
                /* renamed from: a */
                public ConnType findValueByNumber(int i) {
                    return ConnType.forNumber(i);
                }
            };
            private final int value;

            @Override // com.google.protobuf.Internal.EnumLite
            public final int getNumber() {
                if (this != UNRECOGNIZED) {
                    return this.value;
                }
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }

            @Deprecated
            public static ConnType valueOf(int i) {
                return forNumber(i);
            }

            public static ConnType forNumber(int i) {
                switch (i) {
                    case -1:
                        return UNKNOWN;
                    case 0:
                        return WIFI_P2P_GO;
                    case 1:
                        return WIFI_P2P_GC;
                    case 2:
                        return WIFI_SOFTAP;
                    case 3:
                        return WIFI_STATION;
                    case 4:
                        return BT_RFCOMM;
                    case 5:
                        return BT_GATT;
                    case 6:
                        return BLE_GATT;
                    case 7:
                        return COAP;
                    case 8:
                        return NFC;
                    case 9:
                        return IDB;
                    case 10:
                        return WLAN_P2P;
                    case 11:
                        return WLAN_SOFTAP;
                    case 12:
                        return WLAN_GC_SOFTAP;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<ConnType> internalGetValueMap() {
                return a;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return a.a;
            }

            /* loaded from: classes3.dex */
            private static final class a implements Internal.EnumVerifier {
                static final Internal.EnumVerifier a = new a();

                private a() {
                }

                @Override // com.google.protobuf.Internal.EnumVerifier
                public boolean isInRange(int i) {
                    return ConnType.forNumber(i) != null;
                }
            }

            ConnType(int i) {
                this.value = i;
            }
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
        public int getConnTypeValue() {
            return this.a;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
        public ConnType getConnType() {
            ConnType forNumber = ConnType.forNumber(this.a);
            return forNumber == null ? ConnType.UNRECOGNIZED : forNumber;
        }

        public void b(int i2) {
            this.a = i2;
        }

        public void a(ConnType connType) {
            if (connType != null) {
                this.a = connType.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        public void e() {
            this.a = 0;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
        public int getErrCode() {
            return this.b;
        }

        public void c(int i2) {
            this.b = i2;
        }

        public void f() {
            this.b = 0;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
        public String getErrMsg() {
            return this.c;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
        public ByteString getErrMsgBytes() {
            return ByteString.copyFromUtf8(this.c);
        }

        public void a(String str) {
            if (str != null) {
                this.c = str;
                return;
            }
            throw new NullPointerException();
        }

        public void g() {
            this.c = getDefaultInstance().getErrMsg();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.c = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
        public String getIdHash() {
            return this.d;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
        public ByteString getIdHashBytes() {
            return ByteString.copyFromUtf8(this.d);
        }

        public void b(String str) {
            if (str != null) {
                this.d = str;
                return;
            }
            throw new NullPointerException();
        }

        public void h() {
            this.d = getDefaultInstance().getIdHash();
        }

        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.d = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
        public int getConnLevel() {
            return this.e;
        }

        public void d(int i2) {
            this.e = i2;
        }

        public void i() {
            this.e = 0;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
        public ByteString getPrivateData() {
            return this.f;
        }

        public void c(ByteString byteString) {
            if (byteString != null) {
                this.f = byteString;
                return;
            }
            throw new NullPointerException();
        }

        public void j() {
            this.f = getDefaultInstance().getPrivateData();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
        public ByteString getConfig() {
            return this.g;
        }

        public void d(ByteString byteString) {
            if (byteString != null) {
                this.g = byteString;
                return;
            }
            throw new NullPointerException();
        }

        public void k() {
            this.g = getDefaultInstance().getConfig();
        }

        public static ConnParam parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ConnParam) GeneratedMessageLite.parseFrom(h, byteBuffer);
        }

        public static ConnParam parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ConnParam) GeneratedMessageLite.parseFrom(h, byteBuffer, extensionRegistryLite);
        }

        public static ConnParam parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ConnParam) GeneratedMessageLite.parseFrom(h, byteString);
        }

        public static ConnParam parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ConnParam) GeneratedMessageLite.parseFrom(h, byteString, extensionRegistryLite);
        }

        public static ConnParam parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ConnParam) GeneratedMessageLite.parseFrom(h, bArr);
        }

        public static ConnParam parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ConnParam) GeneratedMessageLite.parseFrom(h, bArr, extensionRegistryLite);
        }

        public static ConnParam parseFrom(InputStream inputStream) throws IOException {
            return (ConnParam) GeneratedMessageLite.parseFrom(h, inputStream);
        }

        public static ConnParam parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnParam) GeneratedMessageLite.parseFrom(h, inputStream, extensionRegistryLite);
        }

        public static ConnParam parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ConnParam) parseDelimitedFrom(h, inputStream);
        }

        public static ConnParam parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnParam) parseDelimitedFrom(h, inputStream, extensionRegistryLite);
        }

        public static ConnParam parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ConnParam) GeneratedMessageLite.parseFrom(h, codedInputStream);
        }

        public static ConnParam parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnParam) GeneratedMessageLite.parseFrom(h, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return h.createBuilder();
        }

        public static Builder newBuilder(ConnParam connParam) {
            return h.createBuilder(connParam);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<ConnParam, Builder> implements ConnParamOrBuilder {
            private Builder() {
                super(ConnParam.h);
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
            public int getConnTypeValue() {
                return ((ConnParam) this.instance).getConnTypeValue();
            }

            public Builder setConnTypeValue(int i) {
                copyOnWrite();
                ((ConnParam) this.instance).b(i);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
            public ConnType getConnType() {
                return ((ConnParam) this.instance).getConnType();
            }

            public Builder setConnType(ConnType connType) {
                copyOnWrite();
                ((ConnParam) this.instance).a(connType);
                return this;
            }

            public Builder clearConnType() {
                copyOnWrite();
                ((ConnParam) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
            public int getErrCode() {
                return ((ConnParam) this.instance).getErrCode();
            }

            public Builder setErrCode(int i) {
                copyOnWrite();
                ((ConnParam) this.instance).c(i);
                return this;
            }

            public Builder clearErrCode() {
                copyOnWrite();
                ((ConnParam) this.instance).f();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
            public String getErrMsg() {
                return ((ConnParam) this.instance).getErrMsg();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
            public ByteString getErrMsgBytes() {
                return ((ConnParam) this.instance).getErrMsgBytes();
            }

            public Builder setErrMsg(String str) {
                copyOnWrite();
                ((ConnParam) this.instance).a(str);
                return this;
            }

            public Builder clearErrMsg() {
                copyOnWrite();
                ((ConnParam) this.instance).g();
                return this;
            }

            public Builder setErrMsgBytes(ByteString byteString) {
                copyOnWrite();
                ((ConnParam) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
            public String getIdHash() {
                return ((ConnParam) this.instance).getIdHash();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
            public ByteString getIdHashBytes() {
                return ((ConnParam) this.instance).getIdHashBytes();
            }

            public Builder setIdHash(String str) {
                copyOnWrite();
                ((ConnParam) this.instance).b(str);
                return this;
            }

            public Builder clearIdHash() {
                copyOnWrite();
                ((ConnParam) this.instance).h();
                return this;
            }

            public Builder setIdHashBytes(ByteString byteString) {
                copyOnWrite();
                ((ConnParam) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
            public int getConnLevel() {
                return ((ConnParam) this.instance).getConnLevel();
            }

            public Builder setConnLevel(int i) {
                copyOnWrite();
                ((ConnParam) this.instance).d(i);
                return this;
            }

            public Builder clearConnLevel() {
                copyOnWrite();
                ((ConnParam) this.instance).i();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
            public ByteString getPrivateData() {
                return ((ConnParam) this.instance).getPrivateData();
            }

            public Builder setPrivateData(ByteString byteString) {
                copyOnWrite();
                ((ConnParam) this.instance).c(byteString);
                return this;
            }

            public Builder clearPrivateData() {
                copyOnWrite();
                ((ConnParam) this.instance).j();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnParamOrBuilder
            public ByteString getConfig() {
                return ((ConnParam) this.instance).getConfig();
            }

            public Builder setConfig(ByteString byteString) {
                copyOnWrite();
                ((ConnParam) this.instance).d(byteString);
                return this;
            }

            public Builder clearConfig() {
                copyOnWrite();
                ((ConnParam) this.instance).k();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new ConnParam();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(h, "\u0000\u0007\u0000\u0000\u0001\u000f\u0007\u0000\u0000\u0000\u0001\f\u0002\u0004\u0003Ȉ\u0004Ȉ\u0005\u0004\u0006\n\u000f\n", new Object[]{"connType_", "errCode_", "errMsg_", "idHash_", "connLevel_", "privateData_", "config_"});
                case GET_DEFAULT_INSTANCE:
                    return h;
                case GET_PARSER:
                    Parser<ConnParam> parser = i;
                    if (parser == null) {
                        synchronized (ConnParam.class) {
                            parser = i;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(h);
                                i = parser;
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
            ConnParam connParam = new ConnParam();
            h = connParam;
            GeneratedMessageLite.registerDefaultInstance(ConnParam.class, connParam);
        }

        public static ConnParam getDefaultInstance() {
            return h;
        }

        public static Parser<ConnParam> parser() {
            return h.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class WifiConfig extends GeneratedMessageLite<WifiConfig, Builder> implements WifiConfigOrBuilder {
        public static final int CHANNEL_FIELD_NUMBER = 4;
        public static final int LOCALIP_FIELD_NUMBER = 7;
        public static final int MACADDR_FIELD_NUMBER = 5;
        public static final int PWD_FIELD_NUMBER = 2;
        public static final int REMOTEIP_FIELD_NUMBER = 6;
        public static final int SSID_FIELD_NUMBER = 1;
        public static final int USE5GBAND_FIELD_NUMBER = 3;
        private static final WifiConfig h;
        private static volatile Parser<WifiConfig> i;
        private boolean c;
        private int d;
        private String a = "";
        private String b = "";
        private String e = "";
        private String f = "";
        private String g = "";

        private WifiConfig() {
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
        public String getSsid() {
            return this.a;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
        public ByteString getSsidBytes() {
            return ByteString.copyFromUtf8(this.a);
        }

        public void a(String str) {
            if (str != null) {
                this.a = str;
                return;
            }
            throw new NullPointerException();
        }

        public void e() {
            this.a = getDefaultInstance().getSsid();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.a = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
        public String getPwd() {
            return this.b;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
        public ByteString getPwdBytes() {
            return ByteString.copyFromUtf8(this.b);
        }

        public void b(String str) {
            if (str != null) {
                this.b = str;
                return;
            }
            throw new NullPointerException();
        }

        public void f() {
            this.b = getDefaultInstance().getPwd();
        }

        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
        public boolean getUse5GBand() {
            return this.c;
        }

        public void a(boolean z) {
            this.c = z;
        }

        public void g() {
            this.c = false;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
        public int getChannel() {
            return this.d;
        }

        public void b(int i2) {
            this.d = i2;
        }

        public void h() {
            this.d = 0;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
        public String getMacAddr() {
            return this.e;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
        public ByteString getMacAddrBytes() {
            return ByteString.copyFromUtf8(this.e);
        }

        public void c(String str) {
            if (str != null) {
                this.e = str;
                return;
            }
            throw new NullPointerException();
        }

        public void i() {
            this.e = getDefaultInstance().getMacAddr();
        }

        public void c(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.e = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
        public String getRemoteIp() {
            return this.f;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
        public ByteString getRemoteIpBytes() {
            return ByteString.copyFromUtf8(this.f);
        }

        public void d(String str) {
            if (str != null) {
                this.f = str;
                return;
            }
            throw new NullPointerException();
        }

        public void j() {
            this.f = getDefaultInstance().getRemoteIp();
        }

        public void d(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.f = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
        public String getLocalIp() {
            return this.g;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
        public ByteString getLocalIpBytes() {
            return ByteString.copyFromUtf8(this.g);
        }

        public void e(String str) {
            if (str != null) {
                this.g = str;
                return;
            }
            throw new NullPointerException();
        }

        public void k() {
            this.g = getDefaultInstance().getLocalIp();
        }

        public void e(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.g = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static WifiConfig parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (WifiConfig) GeneratedMessageLite.parseFrom(h, byteBuffer);
        }

        public static WifiConfig parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (WifiConfig) GeneratedMessageLite.parseFrom(h, byteBuffer, extensionRegistryLite);
        }

        public static WifiConfig parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (WifiConfig) GeneratedMessageLite.parseFrom(h, byteString);
        }

        public static WifiConfig parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (WifiConfig) GeneratedMessageLite.parseFrom(h, byteString, extensionRegistryLite);
        }

        public static WifiConfig parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (WifiConfig) GeneratedMessageLite.parseFrom(h, bArr);
        }

        public static WifiConfig parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (WifiConfig) GeneratedMessageLite.parseFrom(h, bArr, extensionRegistryLite);
        }

        public static WifiConfig parseFrom(InputStream inputStream) throws IOException {
            return (WifiConfig) GeneratedMessageLite.parseFrom(h, inputStream);
        }

        public static WifiConfig parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (WifiConfig) GeneratedMessageLite.parseFrom(h, inputStream, extensionRegistryLite);
        }

        public static WifiConfig parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (WifiConfig) parseDelimitedFrom(h, inputStream);
        }

        public static WifiConfig parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (WifiConfig) parseDelimitedFrom(h, inputStream, extensionRegistryLite);
        }

        public static WifiConfig parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (WifiConfig) GeneratedMessageLite.parseFrom(h, codedInputStream);
        }

        public static WifiConfig parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (WifiConfig) GeneratedMessageLite.parseFrom(h, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return h.createBuilder();
        }

        public static Builder newBuilder(WifiConfig wifiConfig) {
            return h.createBuilder(wifiConfig);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<WifiConfig, Builder> implements WifiConfigOrBuilder {
            private Builder() {
                super(WifiConfig.h);
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
            public String getSsid() {
                return ((WifiConfig) this.instance).getSsid();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
            public ByteString getSsidBytes() {
                return ((WifiConfig) this.instance).getSsidBytes();
            }

            public Builder setSsid(String str) {
                copyOnWrite();
                ((WifiConfig) this.instance).a(str);
                return this;
            }

            public Builder clearSsid() {
                copyOnWrite();
                ((WifiConfig) this.instance).e();
                return this;
            }

            public Builder setSsidBytes(ByteString byteString) {
                copyOnWrite();
                ((WifiConfig) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
            public String getPwd() {
                return ((WifiConfig) this.instance).getPwd();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
            public ByteString getPwdBytes() {
                return ((WifiConfig) this.instance).getPwdBytes();
            }

            public Builder setPwd(String str) {
                copyOnWrite();
                ((WifiConfig) this.instance).b(str);
                return this;
            }

            public Builder clearPwd() {
                copyOnWrite();
                ((WifiConfig) this.instance).f();
                return this;
            }

            public Builder setPwdBytes(ByteString byteString) {
                copyOnWrite();
                ((WifiConfig) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
            public boolean getUse5GBand() {
                return ((WifiConfig) this.instance).getUse5GBand();
            }

            public Builder setUse5GBand(boolean z) {
                copyOnWrite();
                ((WifiConfig) this.instance).a(z);
                return this;
            }

            public Builder clearUse5GBand() {
                copyOnWrite();
                ((WifiConfig) this.instance).g();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
            public int getChannel() {
                return ((WifiConfig) this.instance).getChannel();
            }

            public Builder setChannel(int i) {
                copyOnWrite();
                ((WifiConfig) this.instance).b(i);
                return this;
            }

            public Builder clearChannel() {
                copyOnWrite();
                ((WifiConfig) this.instance).h();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
            public String getMacAddr() {
                return ((WifiConfig) this.instance).getMacAddr();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
            public ByteString getMacAddrBytes() {
                return ((WifiConfig) this.instance).getMacAddrBytes();
            }

            public Builder setMacAddr(String str) {
                copyOnWrite();
                ((WifiConfig) this.instance).c(str);
                return this;
            }

            public Builder clearMacAddr() {
                copyOnWrite();
                ((WifiConfig) this.instance).i();
                return this;
            }

            public Builder setMacAddrBytes(ByteString byteString) {
                copyOnWrite();
                ((WifiConfig) this.instance).c(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
            public String getRemoteIp() {
                return ((WifiConfig) this.instance).getRemoteIp();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
            public ByteString getRemoteIpBytes() {
                return ((WifiConfig) this.instance).getRemoteIpBytes();
            }

            public Builder setRemoteIp(String str) {
                copyOnWrite();
                ((WifiConfig) this.instance).d(str);
                return this;
            }

            public Builder clearRemoteIp() {
                copyOnWrite();
                ((WifiConfig) this.instance).j();
                return this;
            }

            public Builder setRemoteIpBytes(ByteString byteString) {
                copyOnWrite();
                ((WifiConfig) this.instance).d(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
            public String getLocalIp() {
                return ((WifiConfig) this.instance).getLocalIp();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.WifiConfigOrBuilder
            public ByteString getLocalIpBytes() {
                return ((WifiConfig) this.instance).getLocalIpBytes();
            }

            public Builder setLocalIp(String str) {
                copyOnWrite();
                ((WifiConfig) this.instance).e(str);
                return this;
            }

            public Builder clearLocalIp() {
                copyOnWrite();
                ((WifiConfig) this.instance).k();
                return this;
            }

            public Builder setLocalIpBytes(ByteString byteString) {
                copyOnWrite();
                ((WifiConfig) this.instance).e(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new WifiConfig();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(h, "\u0000\u0007\u0000\u0000\u0001\u0007\u0007\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003\u0007\u0004\u0004\u0005Ȉ\u0006Ȉ\u0007Ȉ", new Object[]{"ssid_", "pwd_", "use5GBand_", "channel_", "macAddr_", "remoteIp_", "localIp_"});
                case GET_DEFAULT_INSTANCE:
                    return h;
                case GET_PARSER:
                    Parser<WifiConfig> parser = i;
                    if (parser == null) {
                        synchronized (WifiConfig.class) {
                            parser = i;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(h);
                                i = parser;
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
            WifiConfig wifiConfig = new WifiConfig();
            h = wifiConfig;
            GeneratedMessageLite.registerDefaultInstance(WifiConfig.class, wifiConfig);
        }

        public static WifiConfig getDefaultInstance() {
            return h;
        }

        public static Parser<WifiConfig> parser() {
            return h.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class BTConfig extends GeneratedMessageLite<BTConfig, Builder> implements BTConfigOrBuilder {
        public static final int RSSI_FIELD_NUMBER = 2;
        public static final int STATICBTADDRESS_FIELD_NUMBER = 1;
        private static final BTConfig c;
        private static volatile Parser<BTConfig> d;
        private String a = "";
        private int b;

        private BTConfig() {
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.BTConfigOrBuilder
        public String getStaticBTAddress() {
            return this.a;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.BTConfigOrBuilder
        public ByteString getStaticBTAddressBytes() {
            return ByteString.copyFromUtf8(this.a);
        }

        public void a(String str) {
            if (str != null) {
                this.a = str;
                return;
            }
            throw new NullPointerException();
        }

        public void e() {
            this.a = getDefaultInstance().getStaticBTAddress();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.a = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.BTConfigOrBuilder
        public int getRssi() {
            return this.b;
        }

        public void b(int i) {
            this.b = i;
        }

        public void f() {
            this.b = 0;
        }

        public static BTConfig parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (BTConfig) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static BTConfig parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (BTConfig) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static BTConfig parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (BTConfig) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static BTConfig parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (BTConfig) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static BTConfig parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (BTConfig) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static BTConfig parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (BTConfig) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static BTConfig parseFrom(InputStream inputStream) throws IOException {
            return (BTConfig) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static BTConfig parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (BTConfig) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static BTConfig parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (BTConfig) parseDelimitedFrom(c, inputStream);
        }

        public static BTConfig parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (BTConfig) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static BTConfig parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (BTConfig) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static BTConfig parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (BTConfig) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(BTConfig bTConfig) {
            return c.createBuilder(bTConfig);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<BTConfig, Builder> implements BTConfigOrBuilder {
            private Builder() {
                super(BTConfig.c);
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.BTConfigOrBuilder
            public String getStaticBTAddress() {
                return ((BTConfig) this.instance).getStaticBTAddress();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.BTConfigOrBuilder
            public ByteString getStaticBTAddressBytes() {
                return ((BTConfig) this.instance).getStaticBTAddressBytes();
            }

            public Builder setStaticBTAddress(String str) {
                copyOnWrite();
                ((BTConfig) this.instance).a(str);
                return this;
            }

            public Builder clearStaticBTAddress() {
                copyOnWrite();
                ((BTConfig) this.instance).e();
                return this;
            }

            public Builder setStaticBTAddressBytes(ByteString byteString) {
                copyOnWrite();
                ((BTConfig) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.BTConfigOrBuilder
            public int getRssi() {
                return ((BTConfig) this.instance).getRssi();
            }

            public Builder setRssi(int i) {
                copyOnWrite();
                ((BTConfig) this.instance).b(i);
                return this;
            }

            public Builder clearRssi() {
                copyOnWrite();
                ((BTConfig) this.instance).f();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new BTConfig();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\u0004", new Object[]{"staticBTAddress_", "rssi_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<BTConfig> parser = d;
                    if (parser == null) {
                        synchronized (BTConfig.class) {
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
            BTConfig bTConfig = new BTConfig();
            c = bTConfig;
            GeneratedMessageLite.registerDefaultInstance(BTConfig.class, bTConfig);
        }

        public static BTConfig getDefaultInstance() {
            return c;
        }

        public static Parser<BTConfig> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class BLEConfig extends GeneratedMessageLite<BLEConfig, Builder> implements BLEConfigOrBuilder {
        public static final int BLEADDRESS_FIELD_NUMBER = 2;
        public static final int BLEROLE_FIELD_NUMBER = 1;
        public static final int RSSI_FIELD_NUMBER = 3;
        private static final BLEConfig d;
        private static volatile Parser<BLEConfig> e;
        private int a;
        private String b = "";
        private int c;

        private BLEConfig() {
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.BLEConfigOrBuilder
        public int getBleRole() {
            return this.a;
        }

        public void b(int i) {
            this.a = i;
        }

        public void e() {
            this.a = 0;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.BLEConfigOrBuilder
        public String getBleAddress() {
            return this.b;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.BLEConfigOrBuilder
        public ByteString getBleAddressBytes() {
            return ByteString.copyFromUtf8(this.b);
        }

        public void a(String str) {
            if (str != null) {
                this.b = str;
                return;
            }
            throw new NullPointerException();
        }

        public void f() {
            this.b = getDefaultInstance().getBleAddress();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.BLEConfigOrBuilder
        public int getRssi() {
            return this.c;
        }

        public void c(int i) {
            this.c = i;
        }

        public void g() {
            this.c = 0;
        }

        public static BLEConfig parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (BLEConfig) GeneratedMessageLite.parseFrom(d, byteBuffer);
        }

        public static BLEConfig parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (BLEConfig) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
        }

        public static BLEConfig parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (BLEConfig) GeneratedMessageLite.parseFrom(d, byteString);
        }

        public static BLEConfig parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (BLEConfig) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
        }

        public static BLEConfig parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (BLEConfig) GeneratedMessageLite.parseFrom(d, bArr);
        }

        public static BLEConfig parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (BLEConfig) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
        }

        public static BLEConfig parseFrom(InputStream inputStream) throws IOException {
            return (BLEConfig) GeneratedMessageLite.parseFrom(d, inputStream);
        }

        public static BLEConfig parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (BLEConfig) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
        }

        public static BLEConfig parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (BLEConfig) parseDelimitedFrom(d, inputStream);
        }

        public static BLEConfig parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (BLEConfig) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
        }

        public static BLEConfig parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (BLEConfig) GeneratedMessageLite.parseFrom(d, codedInputStream);
        }

        public static BLEConfig parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (BLEConfig) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return d.createBuilder();
        }

        public static Builder newBuilder(BLEConfig bLEConfig) {
            return d.createBuilder(bLEConfig);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<BLEConfig, Builder> implements BLEConfigOrBuilder {
            private Builder() {
                super(BLEConfig.d);
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.BLEConfigOrBuilder
            public int getBleRole() {
                return ((BLEConfig) this.instance).getBleRole();
            }

            public Builder setBleRole(int i) {
                copyOnWrite();
                ((BLEConfig) this.instance).b(i);
                return this;
            }

            public Builder clearBleRole() {
                copyOnWrite();
                ((BLEConfig) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.BLEConfigOrBuilder
            public String getBleAddress() {
                return ((BLEConfig) this.instance).getBleAddress();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.BLEConfigOrBuilder
            public ByteString getBleAddressBytes() {
                return ((BLEConfig) this.instance).getBleAddressBytes();
            }

            public Builder setBleAddress(String str) {
                copyOnWrite();
                ((BLEConfig) this.instance).a(str);
                return this;
            }

            public Builder clearBleAddress() {
                copyOnWrite();
                ((BLEConfig) this.instance).f();
                return this;
            }

            public Builder setBleAddressBytes(ByteString byteString) {
                copyOnWrite();
                ((BLEConfig) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.BLEConfigOrBuilder
            public int getRssi() {
                return ((BLEConfig) this.instance).getRssi();
            }

            public Builder setRssi(int i) {
                copyOnWrite();
                ((BLEConfig) this.instance).c(i);
                return this;
            }

            public Builder clearRssi() {
                copyOnWrite();
                ((BLEConfig) this.instance).g();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new BLEConfig();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003\u0004", new Object[]{"bleRole_", "bleAddress_", "rssi_"});
                case GET_DEFAULT_INSTANCE:
                    return d;
                case GET_PARSER:
                    Parser<BLEConfig> parser = e;
                    if (parser == null) {
                        synchronized (BLEConfig.class) {
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
            BLEConfig bLEConfig = new BLEConfig();
            d = bLEConfig;
            GeneratedMessageLite.registerDefaultInstance(BLEConfig.class, bLEConfig);
        }

        public static BLEConfig getDefaultInstance() {
            return d;
        }

        public static Parser<BLEConfig> parser() {
            return d.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class ConnectionQRCode extends GeneratedMessageLite<ConnectionQRCode, Builder> implements ConnectionQRCodeOrBuilder {
        public static final int CHANNEL_FIELD_NUMBER = 2;
        public static final int CONNTYPE_FIELD_NUMBER = 1;
        public static final int IDHASH_FIELD_NUMBER = 6;
        public static final int MACADDR_FIELD_NUMBER = 3;
        public static final int PWD_FIELD_NUMBER = 4;
        public static final int SSID_FIELD_NUMBER = 5;
        private static final ConnectionQRCode g;
        private static volatile Parser<ConnectionQRCode> h;
        private int a;
        private int b;
        private String c = "";
        private String d = "";
        private String e = "";
        private String f = "";

        private ConnectionQRCode() {
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
        public int getConnType() {
            return this.a;
        }

        public void b(int i) {
            this.a = i;
        }

        public void e() {
            this.a = 0;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
        public int getChannel() {
            return this.b;
        }

        public void c(int i) {
            this.b = i;
        }

        public void f() {
            this.b = 0;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
        public String getMacAddr() {
            return this.c;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
        public ByteString getMacAddrBytes() {
            return ByteString.copyFromUtf8(this.c);
        }

        public void a(String str) {
            if (str != null) {
                this.c = str;
                return;
            }
            throw new NullPointerException();
        }

        public void g() {
            this.c = getDefaultInstance().getMacAddr();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.c = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
        public String getPwd() {
            return this.d;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
        public ByteString getPwdBytes() {
            return ByteString.copyFromUtf8(this.d);
        }

        public void b(String str) {
            if (str != null) {
                this.d = str;
                return;
            }
            throw new NullPointerException();
        }

        public void h() {
            this.d = getDefaultInstance().getPwd();
        }

        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.d = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
        public String getSsid() {
            return this.e;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
        public ByteString getSsidBytes() {
            return ByteString.copyFromUtf8(this.e);
        }

        public void c(String str) {
            if (str != null) {
                this.e = str;
                return;
            }
            throw new NullPointerException();
        }

        public void i() {
            this.e = getDefaultInstance().getSsid();
        }

        public void c(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.e = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
        public String getIdHash() {
            return this.f;
        }

        @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
        public ByteString getIdHashBytes() {
            return ByteString.copyFromUtf8(this.f);
        }

        public void d(String str) {
            if (str != null) {
                this.f = str;
                return;
            }
            throw new NullPointerException();
        }

        public void j() {
            this.f = getDefaultInstance().getIdHash();
        }

        public void d(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.f = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static ConnectionQRCode parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(g, byteBuffer);
        }

        public static ConnectionQRCode parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(g, byteBuffer, extensionRegistryLite);
        }

        public static ConnectionQRCode parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(g, byteString);
        }

        public static ConnectionQRCode parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(g, byteString, extensionRegistryLite);
        }

        public static ConnectionQRCode parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(g, bArr);
        }

        public static ConnectionQRCode parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(g, bArr, extensionRegistryLite);
        }

        public static ConnectionQRCode parseFrom(InputStream inputStream) throws IOException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(g, inputStream);
        }

        public static ConnectionQRCode parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(g, inputStream, extensionRegistryLite);
        }

        public static ConnectionQRCode parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ConnectionQRCode) parseDelimitedFrom(g, inputStream);
        }

        public static ConnectionQRCode parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnectionQRCode) parseDelimitedFrom(g, inputStream, extensionRegistryLite);
        }

        public static ConnectionQRCode parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(g, codedInputStream);
        }

        public static ConnectionQRCode parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(g, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return g.createBuilder();
        }

        public static Builder newBuilder(ConnectionQRCode connectionQRCode) {
            return g.createBuilder(connectionQRCode);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<ConnectionQRCode, Builder> implements ConnectionQRCodeOrBuilder {
            private Builder() {
                super(ConnectionQRCode.g);
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
            public int getConnType() {
                return ((ConnectionQRCode) this.instance).getConnType();
            }

            public Builder setConnType(int i) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).b(i);
                return this;
            }

            public Builder clearConnType() {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
            public int getChannel() {
                return ((ConnectionQRCode) this.instance).getChannel();
            }

            public Builder setChannel(int i) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).c(i);
                return this;
            }

            public Builder clearChannel() {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).f();
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
            public String getMacAddr() {
                return ((ConnectionQRCode) this.instance).getMacAddr();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
            public ByteString getMacAddrBytes() {
                return ((ConnectionQRCode) this.instance).getMacAddrBytes();
            }

            public Builder setMacAddr(String str) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).a(str);
                return this;
            }

            public Builder clearMacAddr() {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).g();
                return this;
            }

            public Builder setMacAddrBytes(ByteString byteString) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
            public String getPwd() {
                return ((ConnectionQRCode) this.instance).getPwd();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
            public ByteString getPwdBytes() {
                return ((ConnectionQRCode) this.instance).getPwdBytes();
            }

            public Builder setPwd(String str) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).b(str);
                return this;
            }

            public Builder clearPwd() {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).h();
                return this;
            }

            public Builder setPwdBytes(ByteString byteString) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
            public String getSsid() {
                return ((ConnectionQRCode) this.instance).getSsid();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
            public ByteString getSsidBytes() {
                return ((ConnectionQRCode) this.instance).getSsidBytes();
            }

            public Builder setSsid(String str) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).c(str);
                return this;
            }

            public Builder clearSsid() {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).i();
                return this;
            }

            public Builder setSsidBytes(ByteString byteString) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).c(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
            public String getIdHash() {
                return ((ConnectionQRCode) this.instance).getIdHash();
            }

            @Override // com.xiaomi.idm.api.proto.IDMServiceProto.ConnectionQRCodeOrBuilder
            public ByteString getIdHashBytes() {
                return ((ConnectionQRCode) this.instance).getIdHashBytes();
            }

            public Builder setIdHash(String str) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).d(str);
                return this;
            }

            public Builder clearIdHash() {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).j();
                return this;
            }

            public Builder setIdHashBytes(ByteString byteString) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).d(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new ConnectionQRCode();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(g, "\u0000\u0006\u0000\u0000\u0001\u0006\u0006\u0000\u0000\u0000\u0001\u0004\u0002\u0004\u0003Ȉ\u0004Ȉ\u0005Ȉ\u0006Ȉ", new Object[]{"connType_", "channel_", "macAddr_", "pwd_", "ssid_", "idHash_"});
                case GET_DEFAULT_INSTANCE:
                    return g;
                case GET_PARSER:
                    Parser<ConnectionQRCode> parser = h;
                    if (parser == null) {
                        synchronized (ConnectionQRCode.class) {
                            parser = h;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(g);
                                h = parser;
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
            ConnectionQRCode connectionQRCode = new ConnectionQRCode();
            g = connectionQRCode;
            GeneratedMessageLite.registerDefaultInstance(ConnectionQRCode.class, connectionQRCode);
        }

        public static ConnectionQRCode getDefaultInstance() {
            return g;
        }

        public static Parser<ConnectionQRCode> parser() {
            return g.getParserForType();
        }
    }
}
