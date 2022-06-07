package com.xiaomi.mi_connect_service.proto;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class IPCParam {

    /* loaded from: classes3.dex */
    public interface AbortInvitationOrBuilder extends MessageLiteOrBuilder {
        String getServiceType();

        ByteString getServiceTypeBytes();
    }

    /* loaded from: classes3.dex */
    public interface AcceptInvitationOrBuilder extends MessageLiteOrBuilder {
        String getInviteStr();

        ByteString getInviteStrBytes();

        String getServiceId();

        ByteString getServiceIdBytes();
    }

    /* loaded from: classes3.dex */
    public interface ClientAcceptConnectionOrBuilder extends MessageLiteOrBuilder {
        String getServiceId();

        ByteString getServiceIdBytes();
    }

    /* loaded from: classes3.dex */
    public interface ClientOnAccountChangedOrBuilder extends MessageLiteOrBuilder {
        String getNewIdHash();

        ByteString getNewIdHashBytes();

        IDMServiceProto.OnAccountChangeResult.SubChangeType getSubChangeType();

        int getSubChangeTypeValue();
    }

    /* loaded from: classes3.dex */
    public interface ClientRejectConnectionOrBuilder extends MessageLiteOrBuilder {
        String getServiceId();

        ByteString getServiceIdBytes();
    }

    /* loaded from: classes3.dex */
    public interface ConnectServiceOrBuilder extends MessageLiteOrBuilder {
        int getCommDataType();

        int getCommType();

        int getConnLevel();

        IDMServiceProto.IDMService getIdmService();

        ByteString getPrivateData();

        boolean getVerifySameAccount();

        boolean hasIdmService();
    }

    /* loaded from: classes3.dex */
    public interface ConnectServiceResponseOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMConnectServiceResponse getIdmConnectServiceResponse();

        boolean hasIdmConnectServiceResponse();
    }

    /* loaded from: classes3.dex */
    public interface DisconnectServiceOrBuilder extends MessageLiteOrBuilder {
        int getConnLevel();

        String getServiceId();

        ByteString getServiceIdBytes();
    }

    /* loaded from: classes3.dex */
    public interface EventOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMEvent getIdmEvent();

        IDMServiceProto.IDMService getIdmService();

        boolean hasIdmEvent();

        boolean hasIdmService();
    }

    /* loaded from: classes3.dex */
    public interface IDMActionRequestOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMRequest getIdmRequest();

        boolean hasIdmRequest();
    }

    /* loaded from: classes3.dex */
    public interface IDMActionResponseOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMResponse getIdmResponse();

        boolean hasIdmResponse();
    }

    /* loaded from: classes3.dex */
    public interface IDMEnableEventRequestOrBuilder extends MessageLiteOrBuilder {
        int getEid();

        boolean getEnable();

        String getServiceUuid();

        ByteString getServiceUuidBytes();
    }

    /* loaded from: classes3.dex */
    public interface IDMEnableEventResponseOrBuilder extends MessageLiteOrBuilder {
        boolean getSuccess();
    }

    /* loaded from: classes3.dex */
    public interface IDMNotifyEventOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMEvent getIdmEvent();

        boolean hasIdmEvent();
    }

    /* loaded from: classes3.dex */
    public interface IDMNotifyHandoffEventOrBuilder extends MessageLiteOrBuilder {
        String getKey();

        ByteString getKeyBytes();

        ByteString getValue();
    }

    /* loaded from: classes3.dex */
    public interface IDMScanServicesRequestOrBuilder extends MessageLiteOrBuilder {
        String getClientId();

        ByteString getClientIdBytes();

        StartDiscovery getServiceFilter();

        boolean hasServiceFilter();
    }

    /* loaded from: classes3.dex */
    public interface IDMScanServicesResponseOrBuilder extends MessageLiteOrBuilder {
        String getClientId();

        ByteString getClientIdBytes();

        IDMServiceProto.IDMService getService(int i);

        int getServiceCount();

        List<IDMServiceProto.IDMService> getServiceList();
    }

    /* loaded from: classes3.dex */
    public interface IDMSetClipBoardMessageOrBuilder extends MessageLiteOrBuilder {
        String getMsg();

        ByteString getMsgBytes();
    }

    /* loaded from: classes3.dex */
    public interface IdentifyParamOrBuilder extends MessageLiteOrBuilder {
        String getAppId();

        ByteString getAppIdBytes();

        String getCUserId();

        ByteString getCUserIdBytes();

        String getDomain();

        ByteString getDomainBytes();

        String getServiceToken();

        ByteString getServiceTokenBytes();

        String getSid();

        ByteString getSidBytes();

        String getSsecurity();

        ByteString getSsecurityBytes();

        String getTimeDiff();

        ByteString getTimeDiffBytes();

        String getUserId();

        ByteString getUserIdBytes();
    }

    /* loaded from: classes3.dex */
    public interface InviteConnectionOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.ConnParam getConnParam();

        String getServiceType();

        ByteString getServiceTypeBytes();

        boolean hasConnParam();
    }

    /* loaded from: classes3.dex */
    public interface OnConnectServiceRequestOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMConnectServiceRequest getIdmConnectServiceRequest();

        boolean hasIdmConnectServiceRequest();
    }

    /* loaded from: classes3.dex */
    public interface OnDiscoveryResultOrBuilder extends MessageLiteOrBuilder {
        int getStatus();
    }

    /* loaded from: classes3.dex */
    public interface OnEventOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMEvent getIdmEvent();

        boolean hasIdmEvent();
    }

    /* loaded from: classes3.dex */
    public interface OnIDMAdvertisingResultOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMAdvertisingResult getIdmAdvertisingResult();

        boolean hasIdmAdvertisingResult();
    }

    /* loaded from: classes3.dex */
    public interface OnInvitationAcceptedOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMService getIdmService();

        boolean hasIdmService();
    }

    /* loaded from: classes3.dex */
    public interface OnInviteConnectionOrBuilder extends MessageLiteOrBuilder {
        int getCode();

        String getInviteStr();

        ByteString getInviteStrBytes();
    }

    /* loaded from: classes3.dex */
    public interface OnRequestOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMRequest getIdmRequest();

        boolean hasIdmRequest();
    }

    /* loaded from: classes3.dex */
    public interface OnResponseOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMResponse getIdmResponse();

        boolean hasIdmResponse();
    }

    /* loaded from: classes3.dex */
    public interface OnServiceConnectStatusOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.ConnParam getConnParam();

        IDMServiceProto.Endpoint getEndpoint();

        String getServiceId();

        ByteString getServiceIdBytes();

        int getStatus();

        boolean hasConnParam();

        boolean hasEndpoint();
    }

    /* loaded from: classes3.dex */
    public interface OnServiceFoundOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMService getIdmService();

        boolean hasIdmService();
    }

    /* loaded from: classes3.dex */
    public interface OnServiceLostOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMService getIdmService();

        String getServiceId();

        ByteString getServiceIdBytes();

        boolean hasIdmService();
    }

    /* loaded from: classes3.dex */
    public interface OnSetEventCallbackOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMEvent getIdmEvent();

        boolean hasIdmEvent();
    }

    /* loaded from: classes3.dex */
    public interface RegisterIDMClientOrBuilder extends MessageLiteOrBuilder {
        IdentifyParam getIdentify();

        int getSdkVersion();

        boolean hasIdentify();
    }

    /* loaded from: classes3.dex */
    public interface RegisterIDMServerOrBuilder extends MessageLiteOrBuilder {
        int getSdkVersion();
    }

    /* loaded from: classes3.dex */
    public interface RegisterProcOrBuilder extends MessageLiteOrBuilder {
    }

    /* loaded from: classes3.dex */
    public interface RequestOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMRequest getIdmRequest();

        boolean hasIdmRequest();
    }

    /* loaded from: classes3.dex */
    public interface ResponseOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMResponse getIdmResponse();

        boolean hasIdmResponse();
    }

    /* loaded from: classes3.dex */
    public interface ServiceOnServiceChangedOrBuilder extends MessageLiteOrBuilder {
        String getNewServiceId();

        ByteString getNewServiceIdBytes();

        String getOldServiceId();

        ByteString getOldServiceIdBytes();
    }

    /* loaded from: classes3.dex */
    public interface SetConnParamOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.ConnParam getConnParam();

        boolean hasConnParam();
    }

    /* loaded from: classes3.dex */
    public interface SetEventCallbackOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMEvent getIdmEvent();

        boolean hasIdmEvent();
    }

    /* loaded from: classes3.dex */
    public interface StartAdvertisingIDMOrBuilder extends MessageLiteOrBuilder {
        int getCommType();

        int getDiscType();

        IDMServiceProto.IDMService getIdmService();

        String getIntentStr();

        ByteString getIntentStrBytes();

        String getIntentType();

        ByteString getIntentTypeBytes();

        int getServiceSecurityType();

        boolean hasIdmService();
    }

    /* loaded from: classes3.dex */
    public interface StartDiscoveryIDMOrBuilder extends MessageLiteOrBuilder {
    }

    /* loaded from: classes3.dex */
    public interface StartDiscoveryOrBuilder extends MessageLiteOrBuilder {
        int getDiscType();

        int getServiceSecurityType();

        String getServiceTypes(int i);

        ByteString getServiceTypesBytes(int i);

        int getServiceTypesCount();

        List<String> getServiceTypesList();

        String getServiceUuids(int i);

        ByteString getServiceUuidsBytes(int i);

        int getServiceUuidsCount();

        List<String> getServiceUuidsList();
    }

    /* loaded from: classes3.dex */
    public interface StopAdvertisingIDMOrBuilder extends MessageLiteOrBuilder {
        IDMServiceProto.IDMService getIdmService();

        boolean hasIdmService();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private IPCParam() {
    }

    /* loaded from: classes3.dex */
    public static final class IdentifyParam extends GeneratedMessageLite<IdentifyParam, Builder> implements IdentifyParamOrBuilder {
        public static final int APPID_FIELD_NUMBER = 8;
        public static final int CUSERID_FIELD_NUMBER = 3;
        public static final int DOMAIN_FIELD_NUMBER = 7;
        public static final int SERVICETOKEN_FIELD_NUMBER = 4;
        public static final int SID_FIELD_NUMBER = 2;
        public static final int SSECURITY_FIELD_NUMBER = 5;
        public static final int TIMEDIFF_FIELD_NUMBER = 6;
        public static final int USERID_FIELD_NUMBER = 1;
        private static final IdentifyParam i;
        private static volatile Parser<IdentifyParam> j;
        private String a = "";
        private String b = "";
        private String c = "";
        private String d = "";
        private String e = "";
        private String f = "";
        private String g = "";
        private String h = "";

        private IdentifyParam() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public String getUserId() {
            return this.a;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public ByteString getUserIdBytes() {
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
            this.a = getDefaultInstance().getUserId();
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public String getSid() {
            return this.b;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public ByteString getSidBytes() {
            return ByteString.copyFromUtf8(this.b);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(String str) {
            if (str != null) {
                this.b = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = getDefaultInstance().getSid();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public String getCUserId() {
            return this.c;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public ByteString getCUserIdBytes() {
            return ByteString.copyFromUtf8(this.c);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(String str) {
            if (str != null) {
                this.c = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g() {
            this.c = getDefaultInstance().getCUserId();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.c = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public String getServiceToken() {
            return this.d;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public ByteString getServiceTokenBytes() {
            return ByteString.copyFromUtf8(this.d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d(String str) {
            if (str != null) {
                this.d = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.d = getDefaultInstance().getServiceToken();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.d = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public String getSsecurity() {
            return this.e;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public ByteString getSsecurityBytes() {
            return ByteString.copyFromUtf8(this.e);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e(String str) {
            if (str != null) {
                this.e = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void i() {
            this.e = getDefaultInstance().getSsecurity();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.e = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public String getTimeDiff() {
            return this.f;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public ByteString getTimeDiffBytes() {
            return ByteString.copyFromUtf8(this.f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f(String str) {
            if (str != null) {
                this.f = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void j() {
            this.f = getDefaultInstance().getTimeDiff();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.f = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public String getDomain() {
            return this.g;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public ByteString getDomainBytes() {
            return ByteString.copyFromUtf8(this.g);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g(String str) {
            if (str != null) {
                this.g = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void k() {
            this.g = getDefaultInstance().getDomain();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.g = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public String getAppId() {
            return this.h;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
        public ByteString getAppIdBytes() {
            return ByteString.copyFromUtf8(this.h);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h(String str) {
            if (str != null) {
                this.h = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void l() {
            this.h = getDefaultInstance().getAppId();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.h = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static IdentifyParam parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IdentifyParam) GeneratedMessageLite.parseFrom(i, byteBuffer);
        }

        public static IdentifyParam parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IdentifyParam) GeneratedMessageLite.parseFrom(i, byteBuffer, extensionRegistryLite);
        }

        public static IdentifyParam parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IdentifyParam) GeneratedMessageLite.parseFrom(i, byteString);
        }

        public static IdentifyParam parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IdentifyParam) GeneratedMessageLite.parseFrom(i, byteString, extensionRegistryLite);
        }

        public static IdentifyParam parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IdentifyParam) GeneratedMessageLite.parseFrom(i, bArr);
        }

        public static IdentifyParam parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IdentifyParam) GeneratedMessageLite.parseFrom(i, bArr, extensionRegistryLite);
        }

        public static IdentifyParam parseFrom(InputStream inputStream) throws IOException {
            return (IdentifyParam) GeneratedMessageLite.parseFrom(i, inputStream);
        }

        public static IdentifyParam parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IdentifyParam) GeneratedMessageLite.parseFrom(i, inputStream, extensionRegistryLite);
        }

        public static IdentifyParam parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IdentifyParam) parseDelimitedFrom(i, inputStream);
        }

        public static IdentifyParam parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IdentifyParam) parseDelimitedFrom(i, inputStream, extensionRegistryLite);
        }

        public static IdentifyParam parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IdentifyParam) GeneratedMessageLite.parseFrom(i, codedInputStream);
        }

        public static IdentifyParam parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IdentifyParam) GeneratedMessageLite.parseFrom(i, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return i.createBuilder();
        }

        public static Builder newBuilder(IdentifyParam identifyParam) {
            return i.createBuilder(identifyParam);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IdentifyParam, Builder> implements IdentifyParamOrBuilder {
            private Builder() {
                super(IdentifyParam.i);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public String getUserId() {
                return ((IdentifyParam) this.instance).getUserId();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public ByteString getUserIdBytes() {
                return ((IdentifyParam) this.instance).getUserIdBytes();
            }

            public Builder setUserId(String str) {
                copyOnWrite();
                ((IdentifyParam) this.instance).a(str);
                return this;
            }

            public Builder clearUserId() {
                copyOnWrite();
                ((IdentifyParam) this.instance).e();
                return this;
            }

            public Builder setUserIdBytes(ByteString byteString) {
                copyOnWrite();
                ((IdentifyParam) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public String getSid() {
                return ((IdentifyParam) this.instance).getSid();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public ByteString getSidBytes() {
                return ((IdentifyParam) this.instance).getSidBytes();
            }

            public Builder setSid(String str) {
                copyOnWrite();
                ((IdentifyParam) this.instance).b(str);
                return this;
            }

            public Builder clearSid() {
                copyOnWrite();
                ((IdentifyParam) this.instance).f();
                return this;
            }

            public Builder setSidBytes(ByteString byteString) {
                copyOnWrite();
                ((IdentifyParam) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public String getCUserId() {
                return ((IdentifyParam) this.instance).getCUserId();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public ByteString getCUserIdBytes() {
                return ((IdentifyParam) this.instance).getCUserIdBytes();
            }

            public Builder setCUserId(String str) {
                copyOnWrite();
                ((IdentifyParam) this.instance).c(str);
                return this;
            }

            public Builder clearCUserId() {
                copyOnWrite();
                ((IdentifyParam) this.instance).g();
                return this;
            }

            public Builder setCUserIdBytes(ByteString byteString) {
                copyOnWrite();
                ((IdentifyParam) this.instance).c(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public String getServiceToken() {
                return ((IdentifyParam) this.instance).getServiceToken();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public ByteString getServiceTokenBytes() {
                return ((IdentifyParam) this.instance).getServiceTokenBytes();
            }

            public Builder setServiceToken(String str) {
                copyOnWrite();
                ((IdentifyParam) this.instance).d(str);
                return this;
            }

            public Builder clearServiceToken() {
                copyOnWrite();
                ((IdentifyParam) this.instance).h();
                return this;
            }

            public Builder setServiceTokenBytes(ByteString byteString) {
                copyOnWrite();
                ((IdentifyParam) this.instance).d(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public String getSsecurity() {
                return ((IdentifyParam) this.instance).getSsecurity();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public ByteString getSsecurityBytes() {
                return ((IdentifyParam) this.instance).getSsecurityBytes();
            }

            public Builder setSsecurity(String str) {
                copyOnWrite();
                ((IdentifyParam) this.instance).e(str);
                return this;
            }

            public Builder clearSsecurity() {
                copyOnWrite();
                ((IdentifyParam) this.instance).i();
                return this;
            }

            public Builder setSsecurityBytes(ByteString byteString) {
                copyOnWrite();
                ((IdentifyParam) this.instance).e(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public String getTimeDiff() {
                return ((IdentifyParam) this.instance).getTimeDiff();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public ByteString getTimeDiffBytes() {
                return ((IdentifyParam) this.instance).getTimeDiffBytes();
            }

            public Builder setTimeDiff(String str) {
                copyOnWrite();
                ((IdentifyParam) this.instance).f(str);
                return this;
            }

            public Builder clearTimeDiff() {
                copyOnWrite();
                ((IdentifyParam) this.instance).j();
                return this;
            }

            public Builder setTimeDiffBytes(ByteString byteString) {
                copyOnWrite();
                ((IdentifyParam) this.instance).f(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public String getDomain() {
                return ((IdentifyParam) this.instance).getDomain();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public ByteString getDomainBytes() {
                return ((IdentifyParam) this.instance).getDomainBytes();
            }

            public Builder setDomain(String str) {
                copyOnWrite();
                ((IdentifyParam) this.instance).g(str);
                return this;
            }

            public Builder clearDomain() {
                copyOnWrite();
                ((IdentifyParam) this.instance).k();
                return this;
            }

            public Builder setDomainBytes(ByteString byteString) {
                copyOnWrite();
                ((IdentifyParam) this.instance).g(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public String getAppId() {
                return ((IdentifyParam) this.instance).getAppId();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IdentifyParamOrBuilder
            public ByteString getAppIdBytes() {
                return ((IdentifyParam) this.instance).getAppIdBytes();
            }

            public Builder setAppId(String str) {
                copyOnWrite();
                ((IdentifyParam) this.instance).h(str);
                return this;
            }

            public Builder clearAppId() {
                copyOnWrite();
                ((IdentifyParam) this.instance).l();
                return this;
            }

            public Builder setAppIdBytes(ByteString byteString) {
                copyOnWrite();
                ((IdentifyParam) this.instance).h(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IdentifyParam();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(i, "\u0000\b\u0000\u0000\u0001\b\b\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ\u0004Ȉ\u0005Ȉ\u0006Ȉ\u0007Ȉ\bȈ", new Object[]{"userId_", "sid_", "cUserId_", "serviceToken_", "ssecurity_", "timeDiff_", "domain_", "appId_"});
                case GET_DEFAULT_INSTANCE:
                    return i;
                case GET_PARSER:
                    Parser<IdentifyParam> parser = j;
                    if (parser == null) {
                        synchronized (IdentifyParam.class) {
                            parser = j;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(i);
                                j = parser;
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
            IdentifyParam identifyParam = new IdentifyParam();
            i = identifyParam;
            GeneratedMessageLite.registerDefaultInstance(IdentifyParam.class, identifyParam);
        }

        public static IdentifyParam getDefaultInstance() {
            return i;
        }

        public static Parser<IdentifyParam> parser() {
            return i.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class SetConnParam extends GeneratedMessageLite<SetConnParam, Builder> implements SetConnParamOrBuilder {
        public static final int CONNPARAM_FIELD_NUMBER = 1;
        private static final SetConnParam b;
        private static volatile Parser<SetConnParam> c;
        private IDMServiceProto.ConnParam a;

        private SetConnParam() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.SetConnParamOrBuilder
        public boolean hasConnParam() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.SetConnParamOrBuilder
        public IDMServiceProto.ConnParam getConnParam() {
            IDMServiceProto.ConnParam connParam = this.a;
            return connParam == null ? IDMServiceProto.ConnParam.getDefaultInstance() : connParam;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.ConnParam connParam) {
            if (connParam != null) {
                this.a = connParam;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.ConnParam.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.ConnParam connParam) {
            if (connParam != null) {
                IDMServiceProto.ConnParam connParam2 = this.a;
                if (connParam2 == null || connParam2 == IDMServiceProto.ConnParam.getDefaultInstance()) {
                    this.a = connParam;
                } else {
                    this.a = IDMServiceProto.ConnParam.newBuilder(this.a).mergeFrom((IDMServiceProto.ConnParam.Builder) connParam).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static SetConnParam parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (SetConnParam) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static SetConnParam parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetConnParam) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static SetConnParam parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (SetConnParam) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static SetConnParam parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetConnParam) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static SetConnParam parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (SetConnParam) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static SetConnParam parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetConnParam) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static SetConnParam parseFrom(InputStream inputStream) throws IOException {
            return (SetConnParam) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static SetConnParam parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetConnParam) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static SetConnParam parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (SetConnParam) parseDelimitedFrom(b, inputStream);
        }

        public static SetConnParam parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetConnParam) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static SetConnParam parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (SetConnParam) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static SetConnParam parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetConnParam) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(SetConnParam setConnParam) {
            return b.createBuilder(setConnParam);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<SetConnParam, Builder> implements SetConnParamOrBuilder {
            private Builder() {
                super(SetConnParam.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.SetConnParamOrBuilder
            public boolean hasConnParam() {
                return ((SetConnParam) this.instance).hasConnParam();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.SetConnParamOrBuilder
            public IDMServiceProto.ConnParam getConnParam() {
                return ((SetConnParam) this.instance).getConnParam();
            }

            public Builder setConnParam(IDMServiceProto.ConnParam connParam) {
                copyOnWrite();
                ((SetConnParam) this.instance).a(connParam);
                return this;
            }

            public Builder setConnParam(IDMServiceProto.ConnParam.Builder builder) {
                copyOnWrite();
                ((SetConnParam) this.instance).a(builder);
                return this;
            }

            public Builder mergeConnParam(IDMServiceProto.ConnParam connParam) {
                copyOnWrite();
                ((SetConnParam) this.instance).b(connParam);
                return this;
            }

            public Builder clearConnParam() {
                copyOnWrite();
                ((SetConnParam) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new SetConnParam();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"connParam_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<SetConnParam> parser = c;
                    if (parser == null) {
                        synchronized (SetConnParam.class) {
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
            SetConnParam setConnParam = new SetConnParam();
            b = setConnParam;
            GeneratedMessageLite.registerDefaultInstance(SetConnParam.class, setConnParam);
        }

        public static SetConnParam getDefaultInstance() {
            return b;
        }

        public static Parser<SetConnParam> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class RegisterIDMClient extends GeneratedMessageLite<RegisterIDMClient, Builder> implements RegisterIDMClientOrBuilder {
        public static final int IDENTIFY_FIELD_NUMBER = 1;
        public static final int SDKVERSION_FIELD_NUMBER = 15;
        private static final RegisterIDMClient c;
        private static volatile Parser<RegisterIDMClient> d;
        private IdentifyParam a;
        private int b;

        private RegisterIDMClient() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.RegisterIDMClientOrBuilder
        public boolean hasIdentify() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.RegisterIDMClientOrBuilder
        public IdentifyParam getIdentify() {
            IdentifyParam identifyParam = this.a;
            return identifyParam == null ? IdentifyParam.getDefaultInstance() : identifyParam;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IdentifyParam identifyParam) {
            if (identifyParam != null) {
                this.a = identifyParam;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IdentifyParam.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IdentifyParam identifyParam) {
            if (identifyParam != null) {
                IdentifyParam identifyParam2 = this.a;
                if (identifyParam2 == null || identifyParam2 == IdentifyParam.getDefaultInstance()) {
                    this.a = identifyParam;
                } else {
                    this.a = IdentifyParam.newBuilder(this.a).mergeFrom((IdentifyParam.Builder) identifyParam).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.RegisterIDMClientOrBuilder
        public int getSdkVersion() {
            return this.b;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i) {
            this.b = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = 0;
        }

        public static RegisterIDMClient parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (RegisterIDMClient) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static RegisterIDMClient parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RegisterIDMClient) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static RegisterIDMClient parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (RegisterIDMClient) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static RegisterIDMClient parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RegisterIDMClient) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static RegisterIDMClient parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (RegisterIDMClient) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static RegisterIDMClient parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RegisterIDMClient) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static RegisterIDMClient parseFrom(InputStream inputStream) throws IOException {
            return (RegisterIDMClient) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static RegisterIDMClient parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RegisterIDMClient) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static RegisterIDMClient parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (RegisterIDMClient) parseDelimitedFrom(c, inputStream);
        }

        public static RegisterIDMClient parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RegisterIDMClient) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static RegisterIDMClient parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (RegisterIDMClient) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static RegisterIDMClient parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RegisterIDMClient) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(RegisterIDMClient registerIDMClient) {
            return c.createBuilder(registerIDMClient);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<RegisterIDMClient, Builder> implements RegisterIDMClientOrBuilder {
            private Builder() {
                super(RegisterIDMClient.c);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.RegisterIDMClientOrBuilder
            public boolean hasIdentify() {
                return ((RegisterIDMClient) this.instance).hasIdentify();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.RegisterIDMClientOrBuilder
            public IdentifyParam getIdentify() {
                return ((RegisterIDMClient) this.instance).getIdentify();
            }

            public Builder setIdentify(IdentifyParam identifyParam) {
                copyOnWrite();
                ((RegisterIDMClient) this.instance).a(identifyParam);
                return this;
            }

            public Builder setIdentify(IdentifyParam.Builder builder) {
                copyOnWrite();
                ((RegisterIDMClient) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdentify(IdentifyParam identifyParam) {
                copyOnWrite();
                ((RegisterIDMClient) this.instance).b(identifyParam);
                return this;
            }

            public Builder clearIdentify() {
                copyOnWrite();
                ((RegisterIDMClient) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.RegisterIDMClientOrBuilder
            public int getSdkVersion() {
                return ((RegisterIDMClient) this.instance).getSdkVersion();
            }

            public Builder setSdkVersion(int i) {
                copyOnWrite();
                ((RegisterIDMClient) this.instance).b(i);
                return this;
            }

            public Builder clearSdkVersion() {
                copyOnWrite();
                ((RegisterIDMClient) this.instance).f();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new RegisterIDMClient();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u000f\u0002\u0000\u0000\u0000\u0001\t\u000f\u0004", new Object[]{"identify_", "sdkVersion_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<RegisterIDMClient> parser = d;
                    if (parser == null) {
                        synchronized (RegisterIDMClient.class) {
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
            RegisterIDMClient registerIDMClient = new RegisterIDMClient();
            c = registerIDMClient;
            GeneratedMessageLite.registerDefaultInstance(RegisterIDMClient.class, registerIDMClient);
        }

        public static RegisterIDMClient getDefaultInstance() {
            return c;
        }

        public static Parser<RegisterIDMClient> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class RegisterIDMServer extends GeneratedMessageLite<RegisterIDMServer, Builder> implements RegisterIDMServerOrBuilder {
        public static final int SDKVERSION_FIELD_NUMBER = 15;
        private static final RegisterIDMServer b;
        private static volatile Parser<RegisterIDMServer> c;
        private int a;

        private RegisterIDMServer() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.RegisterIDMServerOrBuilder
        public int getSdkVersion() {
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

        public static RegisterIDMServer parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (RegisterIDMServer) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static RegisterIDMServer parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RegisterIDMServer) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static RegisterIDMServer parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (RegisterIDMServer) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static RegisterIDMServer parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RegisterIDMServer) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static RegisterIDMServer parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (RegisterIDMServer) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static RegisterIDMServer parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RegisterIDMServer) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static RegisterIDMServer parseFrom(InputStream inputStream) throws IOException {
            return (RegisterIDMServer) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static RegisterIDMServer parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RegisterIDMServer) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static RegisterIDMServer parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (RegisterIDMServer) parseDelimitedFrom(b, inputStream);
        }

        public static RegisterIDMServer parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RegisterIDMServer) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static RegisterIDMServer parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (RegisterIDMServer) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static RegisterIDMServer parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RegisterIDMServer) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(RegisterIDMServer registerIDMServer) {
            return b.createBuilder(registerIDMServer);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<RegisterIDMServer, Builder> implements RegisterIDMServerOrBuilder {
            private Builder() {
                super(RegisterIDMServer.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.RegisterIDMServerOrBuilder
            public int getSdkVersion() {
                return ((RegisterIDMServer) this.instance).getSdkVersion();
            }

            public Builder setSdkVersion(int i) {
                copyOnWrite();
                ((RegisterIDMServer) this.instance).b(i);
                return this;
            }

            public Builder clearSdkVersion() {
                copyOnWrite();
                ((RegisterIDMServer) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new RegisterIDMServer();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u000f\u000f\u0001\u0000\u0000\u0000\u000f\u0004", new Object[]{"sdkVersion_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<RegisterIDMServer> parser = c;
                    if (parser == null) {
                        synchronized (RegisterIDMServer.class) {
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
            RegisterIDMServer registerIDMServer = new RegisterIDMServer();
            b = registerIDMServer;
            GeneratedMessageLite.registerDefaultInstance(RegisterIDMServer.class, registerIDMServer);
        }

        public static RegisterIDMServer getDefaultInstance() {
            return b;
        }

        public static Parser<RegisterIDMServer> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class Request extends GeneratedMessageLite<Request, Builder> implements RequestOrBuilder {
        public static final int IDMREQUEST_FIELD_NUMBER = 1;
        private static final Request b;
        private static volatile Parser<Request> c;
        private IDMServiceProto.IDMRequest a;

        private Request() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.RequestOrBuilder
        public boolean hasIdmRequest() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.RequestOrBuilder
        public IDMServiceProto.IDMRequest getIdmRequest() {
            IDMServiceProto.IDMRequest iDMRequest = this.a;
            return iDMRequest == null ? IDMServiceProto.IDMRequest.getDefaultInstance() : iDMRequest;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMRequest iDMRequest) {
            if (iDMRequest != null) {
                this.a = iDMRequest;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMRequest.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMRequest iDMRequest) {
            if (iDMRequest != null) {
                IDMServiceProto.IDMRequest iDMRequest2 = this.a;
                if (iDMRequest2 == null || iDMRequest2 == IDMServiceProto.IDMRequest.getDefaultInstance()) {
                    this.a = iDMRequest;
                } else {
                    this.a = IDMServiceProto.IDMRequest.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMRequest.Builder) iDMRequest).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static Request parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Request) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static Request parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Request) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static Request parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Request) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static Request parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Request) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static Request parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Request) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static Request parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Request) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static Request parseFrom(InputStream inputStream) throws IOException {
            return (Request) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static Request parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Request) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static Request parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Request) parseDelimitedFrom(b, inputStream);
        }

        public static Request parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Request) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static Request parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Request) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static Request parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Request) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(Request request) {
            return b.createBuilder(request);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<Request, Builder> implements RequestOrBuilder {
            private Builder() {
                super(Request.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.RequestOrBuilder
            public boolean hasIdmRequest() {
                return ((Request) this.instance).hasIdmRequest();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.RequestOrBuilder
            public IDMServiceProto.IDMRequest getIdmRequest() {
                return ((Request) this.instance).getIdmRequest();
            }

            public Builder setIdmRequest(IDMServiceProto.IDMRequest iDMRequest) {
                copyOnWrite();
                ((Request) this.instance).a(iDMRequest);
                return this;
            }

            public Builder setIdmRequest(IDMServiceProto.IDMRequest.Builder builder) {
                copyOnWrite();
                ((Request) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmRequest(IDMServiceProto.IDMRequest iDMRequest) {
                copyOnWrite();
                ((Request) this.instance).b(iDMRequest);
                return this;
            }

            public Builder clearIdmRequest() {
                copyOnWrite();
                ((Request) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new Request();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"idmRequest_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<Request> parser = c;
                    if (parser == null) {
                        synchronized (Request.class) {
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
            Request request = new Request();
            b = request;
            GeneratedMessageLite.registerDefaultInstance(Request.class, request);
        }

        public static Request getDefaultInstance() {
            return b;
        }

        public static Parser<Request> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class StartDiscoveryIDM extends GeneratedMessageLite<StartDiscoveryIDM, Builder> implements StartDiscoveryIDMOrBuilder {
        private static final StartDiscoveryIDM a;
        private static volatile Parser<StartDiscoveryIDM> b;

        private StartDiscoveryIDM() {
        }

        public static StartDiscoveryIDM parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (StartDiscoveryIDM) GeneratedMessageLite.parseFrom(a, byteBuffer);
        }

        public static StartDiscoveryIDM parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (StartDiscoveryIDM) GeneratedMessageLite.parseFrom(a, byteBuffer, extensionRegistryLite);
        }

        public static StartDiscoveryIDM parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (StartDiscoveryIDM) GeneratedMessageLite.parseFrom(a, byteString);
        }

        public static StartDiscoveryIDM parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (StartDiscoveryIDM) GeneratedMessageLite.parseFrom(a, byteString, extensionRegistryLite);
        }

        public static StartDiscoveryIDM parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (StartDiscoveryIDM) GeneratedMessageLite.parseFrom(a, bArr);
        }

        public static StartDiscoveryIDM parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (StartDiscoveryIDM) GeneratedMessageLite.parseFrom(a, bArr, extensionRegistryLite);
        }

        public static StartDiscoveryIDM parseFrom(InputStream inputStream) throws IOException {
            return (StartDiscoveryIDM) GeneratedMessageLite.parseFrom(a, inputStream);
        }

        public static StartDiscoveryIDM parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (StartDiscoveryIDM) GeneratedMessageLite.parseFrom(a, inputStream, extensionRegistryLite);
        }

        public static StartDiscoveryIDM parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (StartDiscoveryIDM) parseDelimitedFrom(a, inputStream);
        }

        public static StartDiscoveryIDM parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (StartDiscoveryIDM) parseDelimitedFrom(a, inputStream, extensionRegistryLite);
        }

        public static StartDiscoveryIDM parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (StartDiscoveryIDM) GeneratedMessageLite.parseFrom(a, codedInputStream);
        }

        public static StartDiscoveryIDM parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (StartDiscoveryIDM) GeneratedMessageLite.parseFrom(a, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return a.createBuilder();
        }

        public static Builder newBuilder(StartDiscoveryIDM startDiscoveryIDM) {
            return a.createBuilder(startDiscoveryIDM);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<StartDiscoveryIDM, Builder> implements StartDiscoveryIDMOrBuilder {
            private Builder() {
                super(StartDiscoveryIDM.a);
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new StartDiscoveryIDM();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(a, "\u0000\u0000", null);
                case GET_DEFAULT_INSTANCE:
                    return a;
                case GET_PARSER:
                    Parser<StartDiscoveryIDM> parser = b;
                    if (parser == null) {
                        synchronized (StartDiscoveryIDM.class) {
                            parser = b;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(a);
                                b = parser;
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
            StartDiscoveryIDM startDiscoveryIDM = new StartDiscoveryIDM();
            a = startDiscoveryIDM;
            GeneratedMessageLite.registerDefaultInstance(StartDiscoveryIDM.class, startDiscoveryIDM);
        }

        public static StartDiscoveryIDM getDefaultInstance() {
            return a;
        }

        public static Parser<StartDiscoveryIDM> parser() {
            return a.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class SetEventCallback extends GeneratedMessageLite<SetEventCallback, Builder> implements SetEventCallbackOrBuilder {
        public static final int IDMEVENT_FIELD_NUMBER = 1;
        private static final SetEventCallback b;
        private static volatile Parser<SetEventCallback> c;
        private IDMServiceProto.IDMEvent a;

        private SetEventCallback() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.SetEventCallbackOrBuilder
        public boolean hasIdmEvent() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.SetEventCallbackOrBuilder
        public IDMServiceProto.IDMEvent getIdmEvent() {
            IDMServiceProto.IDMEvent iDMEvent = this.a;
            return iDMEvent == null ? IDMServiceProto.IDMEvent.getDefaultInstance() : iDMEvent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMEvent iDMEvent) {
            if (iDMEvent != null) {
                this.a = iDMEvent;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMEvent.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMEvent iDMEvent) {
            if (iDMEvent != null) {
                IDMServiceProto.IDMEvent iDMEvent2 = this.a;
                if (iDMEvent2 == null || iDMEvent2 == IDMServiceProto.IDMEvent.getDefaultInstance()) {
                    this.a = iDMEvent;
                } else {
                    this.a = IDMServiceProto.IDMEvent.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMEvent.Builder) iDMEvent).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static SetEventCallback parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (SetEventCallback) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static SetEventCallback parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetEventCallback) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static SetEventCallback parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (SetEventCallback) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static SetEventCallback parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetEventCallback) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static SetEventCallback parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (SetEventCallback) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static SetEventCallback parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetEventCallback) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static SetEventCallback parseFrom(InputStream inputStream) throws IOException {
            return (SetEventCallback) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static SetEventCallback parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetEventCallback) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static SetEventCallback parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (SetEventCallback) parseDelimitedFrom(b, inputStream);
        }

        public static SetEventCallback parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetEventCallback) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static SetEventCallback parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (SetEventCallback) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static SetEventCallback parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetEventCallback) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(SetEventCallback setEventCallback) {
            return b.createBuilder(setEventCallback);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<SetEventCallback, Builder> implements SetEventCallbackOrBuilder {
            private Builder() {
                super(SetEventCallback.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.SetEventCallbackOrBuilder
            public boolean hasIdmEvent() {
                return ((SetEventCallback) this.instance).hasIdmEvent();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.SetEventCallbackOrBuilder
            public IDMServiceProto.IDMEvent getIdmEvent() {
                return ((SetEventCallback) this.instance).getIdmEvent();
            }

            public Builder setIdmEvent(IDMServiceProto.IDMEvent iDMEvent) {
                copyOnWrite();
                ((SetEventCallback) this.instance).a(iDMEvent);
                return this;
            }

            public Builder setIdmEvent(IDMServiceProto.IDMEvent.Builder builder) {
                copyOnWrite();
                ((SetEventCallback) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmEvent(IDMServiceProto.IDMEvent iDMEvent) {
                copyOnWrite();
                ((SetEventCallback) this.instance).b(iDMEvent);
                return this;
            }

            public Builder clearIdmEvent() {
                copyOnWrite();
                ((SetEventCallback) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new SetEventCallback();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"idmEvent_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<SetEventCallback> parser = c;
                    if (parser == null) {
                        synchronized (SetEventCallback.class) {
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
            SetEventCallback setEventCallback = new SetEventCallback();
            b = setEventCallback;
            GeneratedMessageLite.registerDefaultInstance(SetEventCallback.class, setEventCallback);
        }

        public static SetEventCallback getDefaultInstance() {
            return b;
        }

        public static Parser<SetEventCallback> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class ConnectService extends GeneratedMessageLite<ConnectService, Builder> implements ConnectServiceOrBuilder {
        public static final int COMMDATATYPE_FIELD_NUMBER = 3;
        public static final int COMMTYPE_FIELD_NUMBER = 2;
        public static final int CONNLEVEL_FIELD_NUMBER = 4;
        public static final int IDMSERVICE_FIELD_NUMBER = 1;
        public static final int PRIVATEDATA_FIELD_NUMBER = 6;
        public static final int VERIFYSAMEACCOUNT_FIELD_NUMBER = 5;
        private static final ConnectService g;
        private static volatile Parser<ConnectService> h;
        private IDMServiceProto.IDMService a;
        private int b;
        private int c;
        private int d;
        private boolean e;
        private ByteString f = ByteString.EMPTY;

        private ConnectService() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceOrBuilder
        public boolean hasIdmService() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceOrBuilder
        public IDMServiceProto.IDMService getIdmService() {
            IDMServiceProto.IDMService iDMService = this.a;
            return iDMService == null ? IDMServiceProto.IDMService.getDefaultInstance() : iDMService;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                this.a = iDMService;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                IDMServiceProto.IDMService iDMService2 = this.a;
                if (iDMService2 == null || iDMService2 == IDMServiceProto.IDMService.getDefaultInstance()) {
                    this.a = iDMService;
                } else {
                    this.a = IDMServiceProto.IDMService.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMService.Builder) iDMService).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceOrBuilder
        public int getCommType() {
            return this.b;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i) {
            this.b = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = 0;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceOrBuilder
        public int getCommDataType() {
            return this.c;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(int i) {
            this.c = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g() {
            this.c = 0;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceOrBuilder
        public int getConnLevel() {
            return this.d;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d(int i) {
            this.d = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.d = 0;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceOrBuilder
        public boolean getVerifySameAccount() {
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceOrBuilder
        public ByteString getPrivateData() {
            return this.f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(ByteString byteString) {
            if (byteString != null) {
                this.f = byteString;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void j() {
            this.f = getDefaultInstance().getPrivateData();
        }

        public static ConnectService parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ConnectService) GeneratedMessageLite.parseFrom(g, byteBuffer);
        }

        public static ConnectService parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ConnectService) GeneratedMessageLite.parseFrom(g, byteBuffer, extensionRegistryLite);
        }

        public static ConnectService parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ConnectService) GeneratedMessageLite.parseFrom(g, byteString);
        }

        public static ConnectService parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ConnectService) GeneratedMessageLite.parseFrom(g, byteString, extensionRegistryLite);
        }

        public static ConnectService parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ConnectService) GeneratedMessageLite.parseFrom(g, bArr);
        }

        public static ConnectService parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ConnectService) GeneratedMessageLite.parseFrom(g, bArr, extensionRegistryLite);
        }

        public static ConnectService parseFrom(InputStream inputStream) throws IOException {
            return (ConnectService) GeneratedMessageLite.parseFrom(g, inputStream);
        }

        public static ConnectService parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnectService) GeneratedMessageLite.parseFrom(g, inputStream, extensionRegistryLite);
        }

        public static ConnectService parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ConnectService) parseDelimitedFrom(g, inputStream);
        }

        public static ConnectService parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnectService) parseDelimitedFrom(g, inputStream, extensionRegistryLite);
        }

        public static ConnectService parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ConnectService) GeneratedMessageLite.parseFrom(g, codedInputStream);
        }

        public static ConnectService parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnectService) GeneratedMessageLite.parseFrom(g, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return g.createBuilder();
        }

        public static Builder newBuilder(ConnectService connectService) {
            return g.createBuilder(connectService);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<ConnectService, Builder> implements ConnectServiceOrBuilder {
            private Builder() {
                super(ConnectService.g);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceOrBuilder
            public boolean hasIdmService() {
                return ((ConnectService) this.instance).hasIdmService();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceOrBuilder
            public IDMServiceProto.IDMService getIdmService() {
                return ((ConnectService) this.instance).getIdmService();
            }

            public Builder setIdmService(IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((ConnectService) this.instance).a(iDMService);
                return this;
            }

            public Builder setIdmService(IDMServiceProto.IDMService.Builder builder) {
                copyOnWrite();
                ((ConnectService) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmService(IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((ConnectService) this.instance).b(iDMService);
                return this;
            }

            public Builder clearIdmService() {
                copyOnWrite();
                ((ConnectService) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceOrBuilder
            public int getCommType() {
                return ((ConnectService) this.instance).getCommType();
            }

            public Builder setCommType(int i) {
                copyOnWrite();
                ((ConnectService) this.instance).b(i);
                return this;
            }

            public Builder clearCommType() {
                copyOnWrite();
                ((ConnectService) this.instance).f();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceOrBuilder
            public int getCommDataType() {
                return ((ConnectService) this.instance).getCommDataType();
            }

            public Builder setCommDataType(int i) {
                copyOnWrite();
                ((ConnectService) this.instance).c(i);
                return this;
            }

            public Builder clearCommDataType() {
                copyOnWrite();
                ((ConnectService) this.instance).g();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceOrBuilder
            public int getConnLevel() {
                return ((ConnectService) this.instance).getConnLevel();
            }

            public Builder setConnLevel(int i) {
                copyOnWrite();
                ((ConnectService) this.instance).d(i);
                return this;
            }

            public Builder clearConnLevel() {
                copyOnWrite();
                ((ConnectService) this.instance).h();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceOrBuilder
            public boolean getVerifySameAccount() {
                return ((ConnectService) this.instance).getVerifySameAccount();
            }

            public Builder setVerifySameAccount(boolean z) {
                copyOnWrite();
                ((ConnectService) this.instance).a(z);
                return this;
            }

            public Builder clearVerifySameAccount() {
                copyOnWrite();
                ((ConnectService) this.instance).i();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceOrBuilder
            public ByteString getPrivateData() {
                return ((ConnectService) this.instance).getPrivateData();
            }

            public Builder setPrivateData(ByteString byteString) {
                copyOnWrite();
                ((ConnectService) this.instance).a(byteString);
                return this;
            }

            public Builder clearPrivateData() {
                copyOnWrite();
                ((ConnectService) this.instance).j();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new ConnectService();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(g, "\u0000\u0006\u0000\u0000\u0001\u0006\u0006\u0000\u0000\u0000\u0001\t\u0002\u0004\u0003\u0004\u0004\u0004\u0005\u0007\u0006\n", new Object[]{"idmService_", "commType_", "commDataType_", "connLevel_", "verifySameAccount_", "privateData_"});
                case GET_DEFAULT_INSTANCE:
                    return g;
                case GET_PARSER:
                    Parser<ConnectService> parser = h;
                    if (parser == null) {
                        synchronized (ConnectService.class) {
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
            ConnectService connectService = new ConnectService();
            g = connectService;
            GeneratedMessageLite.registerDefaultInstance(ConnectService.class, connectService);
        }

        public static ConnectService getDefaultInstance() {
            return g;
        }

        public static Parser<ConnectService> parser() {
            return g.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class DisconnectService extends GeneratedMessageLite<DisconnectService, Builder> implements DisconnectServiceOrBuilder {
        public static final int CONNLEVEL_FIELD_NUMBER = 2;
        public static final int SERVICEID_FIELD_NUMBER = 1;
        private static final DisconnectService c;
        private static volatile Parser<DisconnectService> d;
        private String a = "";
        private int b;

        private DisconnectService() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.DisconnectServiceOrBuilder
        public String getServiceId() {
            return this.a;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.DisconnectServiceOrBuilder
        public ByteString getServiceIdBytes() {
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
            this.a = getDefaultInstance().getServiceId();
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.DisconnectServiceOrBuilder
        public int getConnLevel() {
            return this.b;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i) {
            this.b = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = 0;
        }

        public static DisconnectService parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (DisconnectService) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static DisconnectService parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DisconnectService) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static DisconnectService parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (DisconnectService) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static DisconnectService parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DisconnectService) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static DisconnectService parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (DisconnectService) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static DisconnectService parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DisconnectService) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static DisconnectService parseFrom(InputStream inputStream) throws IOException {
            return (DisconnectService) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static DisconnectService parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DisconnectService) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static DisconnectService parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (DisconnectService) parseDelimitedFrom(c, inputStream);
        }

        public static DisconnectService parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DisconnectService) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static DisconnectService parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (DisconnectService) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static DisconnectService parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DisconnectService) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(DisconnectService disconnectService) {
            return c.createBuilder(disconnectService);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<DisconnectService, Builder> implements DisconnectServiceOrBuilder {
            private Builder() {
                super(DisconnectService.c);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.DisconnectServiceOrBuilder
            public String getServiceId() {
                return ((DisconnectService) this.instance).getServiceId();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.DisconnectServiceOrBuilder
            public ByteString getServiceIdBytes() {
                return ((DisconnectService) this.instance).getServiceIdBytes();
            }

            public Builder setServiceId(String str) {
                copyOnWrite();
                ((DisconnectService) this.instance).a(str);
                return this;
            }

            public Builder clearServiceId() {
                copyOnWrite();
                ((DisconnectService) this.instance).e();
                return this;
            }

            public Builder setServiceIdBytes(ByteString byteString) {
                copyOnWrite();
                ((DisconnectService) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.DisconnectServiceOrBuilder
            public int getConnLevel() {
                return ((DisconnectService) this.instance).getConnLevel();
            }

            public Builder setConnLevel(int i) {
                copyOnWrite();
                ((DisconnectService) this.instance).b(i);
                return this;
            }

            public Builder clearConnLevel() {
                copyOnWrite();
                ((DisconnectService) this.instance).f();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new DisconnectService();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\u0004", new Object[]{"serviceId_", "connLevel_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<DisconnectService> parser = d;
                    if (parser == null) {
                        synchronized (DisconnectService.class) {
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
            DisconnectService disconnectService = new DisconnectService();
            c = disconnectService;
            GeneratedMessageLite.registerDefaultInstance(DisconnectService.class, disconnectService);
        }

        public static DisconnectService getDefaultInstance() {
            return c;
        }

        public static Parser<DisconnectService> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class ClientAcceptConnection extends GeneratedMessageLite<ClientAcceptConnection, Builder> implements ClientAcceptConnectionOrBuilder {
        public static final int SERVICEID_FIELD_NUMBER = 1;
        private static final ClientAcceptConnection b;
        private static volatile Parser<ClientAcceptConnection> c;
        private String a = "";

        private ClientAcceptConnection() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientAcceptConnectionOrBuilder
        public String getServiceId() {
            return this.a;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientAcceptConnectionOrBuilder
        public ByteString getServiceIdBytes() {
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
            this.a = getDefaultInstance().getServiceId();
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

        public static ClientAcceptConnection parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ClientAcceptConnection) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static ClientAcceptConnection parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ClientAcceptConnection) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static ClientAcceptConnection parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ClientAcceptConnection) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static ClientAcceptConnection parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ClientAcceptConnection) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static ClientAcceptConnection parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ClientAcceptConnection) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static ClientAcceptConnection parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ClientAcceptConnection) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static ClientAcceptConnection parseFrom(InputStream inputStream) throws IOException {
            return (ClientAcceptConnection) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static ClientAcceptConnection parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ClientAcceptConnection) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static ClientAcceptConnection parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ClientAcceptConnection) parseDelimitedFrom(b, inputStream);
        }

        public static ClientAcceptConnection parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ClientAcceptConnection) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static ClientAcceptConnection parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ClientAcceptConnection) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static ClientAcceptConnection parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ClientAcceptConnection) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(ClientAcceptConnection clientAcceptConnection) {
            return b.createBuilder(clientAcceptConnection);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<ClientAcceptConnection, Builder> implements ClientAcceptConnectionOrBuilder {
            private Builder() {
                super(ClientAcceptConnection.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientAcceptConnectionOrBuilder
            public String getServiceId() {
                return ((ClientAcceptConnection) this.instance).getServiceId();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientAcceptConnectionOrBuilder
            public ByteString getServiceIdBytes() {
                return ((ClientAcceptConnection) this.instance).getServiceIdBytes();
            }

            public Builder setServiceId(String str) {
                copyOnWrite();
                ((ClientAcceptConnection) this.instance).a(str);
                return this;
            }

            public Builder clearServiceId() {
                copyOnWrite();
                ((ClientAcceptConnection) this.instance).e();
                return this;
            }

            public Builder setServiceIdBytes(ByteString byteString) {
                copyOnWrite();
                ((ClientAcceptConnection) this.instance).a(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new ClientAcceptConnection();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ", new Object[]{"serviceId_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<ClientAcceptConnection> parser = c;
                    if (parser == null) {
                        synchronized (ClientAcceptConnection.class) {
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
            ClientAcceptConnection clientAcceptConnection = new ClientAcceptConnection();
            b = clientAcceptConnection;
            GeneratedMessageLite.registerDefaultInstance(ClientAcceptConnection.class, clientAcceptConnection);
        }

        public static ClientAcceptConnection getDefaultInstance() {
            return b;
        }

        public static Parser<ClientAcceptConnection> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class ClientRejectConnection extends GeneratedMessageLite<ClientRejectConnection, Builder> implements ClientRejectConnectionOrBuilder {
        public static final int SERVICEID_FIELD_NUMBER = 1;
        private static final ClientRejectConnection b;
        private static volatile Parser<ClientRejectConnection> c;
        private String a = "";

        private ClientRejectConnection() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientRejectConnectionOrBuilder
        public String getServiceId() {
            return this.a;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientRejectConnectionOrBuilder
        public ByteString getServiceIdBytes() {
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
            this.a = getDefaultInstance().getServiceId();
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

        public static ClientRejectConnection parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ClientRejectConnection) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static ClientRejectConnection parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ClientRejectConnection) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static ClientRejectConnection parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ClientRejectConnection) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static ClientRejectConnection parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ClientRejectConnection) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static ClientRejectConnection parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ClientRejectConnection) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static ClientRejectConnection parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ClientRejectConnection) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static ClientRejectConnection parseFrom(InputStream inputStream) throws IOException {
            return (ClientRejectConnection) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static ClientRejectConnection parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ClientRejectConnection) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static ClientRejectConnection parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ClientRejectConnection) parseDelimitedFrom(b, inputStream);
        }

        public static ClientRejectConnection parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ClientRejectConnection) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static ClientRejectConnection parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ClientRejectConnection) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static ClientRejectConnection parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ClientRejectConnection) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(ClientRejectConnection clientRejectConnection) {
            return b.createBuilder(clientRejectConnection);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<ClientRejectConnection, Builder> implements ClientRejectConnectionOrBuilder {
            private Builder() {
                super(ClientRejectConnection.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientRejectConnectionOrBuilder
            public String getServiceId() {
                return ((ClientRejectConnection) this.instance).getServiceId();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientRejectConnectionOrBuilder
            public ByteString getServiceIdBytes() {
                return ((ClientRejectConnection) this.instance).getServiceIdBytes();
            }

            public Builder setServiceId(String str) {
                copyOnWrite();
                ((ClientRejectConnection) this.instance).a(str);
                return this;
            }

            public Builder clearServiceId() {
                copyOnWrite();
                ((ClientRejectConnection) this.instance).e();
                return this;
            }

            public Builder setServiceIdBytes(ByteString byteString) {
                copyOnWrite();
                ((ClientRejectConnection) this.instance).a(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new ClientRejectConnection();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ", new Object[]{"serviceId_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<ClientRejectConnection> parser = c;
                    if (parser == null) {
                        synchronized (ClientRejectConnection.class) {
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
            ClientRejectConnection clientRejectConnection = new ClientRejectConnection();
            b = clientRejectConnection;
            GeneratedMessageLite.registerDefaultInstance(ClientRejectConnection.class, clientRejectConnection);
        }

        public static ClientRejectConnection getDefaultInstance() {
            return b;
        }

        public static Parser<ClientRejectConnection> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class RegisterProc extends GeneratedMessageLite<RegisterProc, Builder> implements RegisterProcOrBuilder {
        private static final RegisterProc a;
        private static volatile Parser<RegisterProc> b;

        private RegisterProc() {
        }

        public static RegisterProc parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (RegisterProc) GeneratedMessageLite.parseFrom(a, byteBuffer);
        }

        public static RegisterProc parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RegisterProc) GeneratedMessageLite.parseFrom(a, byteBuffer, extensionRegistryLite);
        }

        public static RegisterProc parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (RegisterProc) GeneratedMessageLite.parseFrom(a, byteString);
        }

        public static RegisterProc parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RegisterProc) GeneratedMessageLite.parseFrom(a, byteString, extensionRegistryLite);
        }

        public static RegisterProc parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (RegisterProc) GeneratedMessageLite.parseFrom(a, bArr);
        }

        public static RegisterProc parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RegisterProc) GeneratedMessageLite.parseFrom(a, bArr, extensionRegistryLite);
        }

        public static RegisterProc parseFrom(InputStream inputStream) throws IOException {
            return (RegisterProc) GeneratedMessageLite.parseFrom(a, inputStream);
        }

        public static RegisterProc parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RegisterProc) GeneratedMessageLite.parseFrom(a, inputStream, extensionRegistryLite);
        }

        public static RegisterProc parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (RegisterProc) parseDelimitedFrom(a, inputStream);
        }

        public static RegisterProc parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RegisterProc) parseDelimitedFrom(a, inputStream, extensionRegistryLite);
        }

        public static RegisterProc parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (RegisterProc) GeneratedMessageLite.parseFrom(a, codedInputStream);
        }

        public static RegisterProc parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RegisterProc) GeneratedMessageLite.parseFrom(a, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return a.createBuilder();
        }

        public static Builder newBuilder(RegisterProc registerProc) {
            return a.createBuilder(registerProc);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<RegisterProc, Builder> implements RegisterProcOrBuilder {
            private Builder() {
                super(RegisterProc.a);
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new RegisterProc();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(a, "\u0000\u0000", null);
                case GET_DEFAULT_INSTANCE:
                    return a;
                case GET_PARSER:
                    Parser<RegisterProc> parser = b;
                    if (parser == null) {
                        synchronized (RegisterProc.class) {
                            parser = b;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(a);
                                b = parser;
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
            RegisterProc registerProc = new RegisterProc();
            a = registerProc;
            GeneratedMessageLite.registerDefaultInstance(RegisterProc.class, registerProc);
        }

        public static RegisterProc getDefaultInstance() {
            return a;
        }

        public static Parser<RegisterProc> parser() {
            return a.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class StartAdvertisingIDM extends GeneratedMessageLite<StartAdvertisingIDM, Builder> implements StartAdvertisingIDMOrBuilder {
        public static final int COMMTYPE_FIELD_NUMBER = 5;
        public static final int DISCTYPE_FIELD_NUMBER = 4;
        public static final int IDMSERVICE_FIELD_NUMBER = 1;
        public static final int INTENTSTR_FIELD_NUMBER = 2;
        public static final int INTENTTYPE_FIELD_NUMBER = 3;
        public static final int SERVICESECURITYTYPE_FIELD_NUMBER = 6;
        private static final StartAdvertisingIDM g;
        private static volatile Parser<StartAdvertisingIDM> h;
        private IDMServiceProto.IDMService a;
        private String b = "";
        private String c = "";
        private int d;
        private int e;
        private int f;

        private StartAdvertisingIDM() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
        public boolean hasIdmService() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
        public IDMServiceProto.IDMService getIdmService() {
            IDMServiceProto.IDMService iDMService = this.a;
            return iDMService == null ? IDMServiceProto.IDMService.getDefaultInstance() : iDMService;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                this.a = iDMService;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                IDMServiceProto.IDMService iDMService2 = this.a;
                if (iDMService2 == null || iDMService2 == IDMServiceProto.IDMService.getDefaultInstance()) {
                    this.a = iDMService;
                } else {
                    this.a = IDMServiceProto.IDMService.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMService.Builder) iDMService).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
        public String getIntentStr() {
            return this.b;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
        public ByteString getIntentStrBytes() {
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
            this.b = getDefaultInstance().getIntentStr();
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
        public String getIntentType() {
            return this.c;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
        public ByteString getIntentTypeBytes() {
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
            this.c = getDefaultInstance().getIntentType();
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
        public int getDiscType() {
            return this.d;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i) {
            this.d = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.d = 0;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
        public int getCommType() {
            return this.e;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(int i) {
            this.e = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void i() {
            this.e = 0;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
        public int getServiceSecurityType() {
            return this.f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d(int i) {
            this.f = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void j() {
            this.f = 0;
        }

        public static StartAdvertisingIDM parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (StartAdvertisingIDM) GeneratedMessageLite.parseFrom(g, byteBuffer);
        }

        public static StartAdvertisingIDM parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (StartAdvertisingIDM) GeneratedMessageLite.parseFrom(g, byteBuffer, extensionRegistryLite);
        }

        public static StartAdvertisingIDM parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (StartAdvertisingIDM) GeneratedMessageLite.parseFrom(g, byteString);
        }

        public static StartAdvertisingIDM parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (StartAdvertisingIDM) GeneratedMessageLite.parseFrom(g, byteString, extensionRegistryLite);
        }

        public static StartAdvertisingIDM parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (StartAdvertisingIDM) GeneratedMessageLite.parseFrom(g, bArr);
        }

        public static StartAdvertisingIDM parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (StartAdvertisingIDM) GeneratedMessageLite.parseFrom(g, bArr, extensionRegistryLite);
        }

        public static StartAdvertisingIDM parseFrom(InputStream inputStream) throws IOException {
            return (StartAdvertisingIDM) GeneratedMessageLite.parseFrom(g, inputStream);
        }

        public static StartAdvertisingIDM parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (StartAdvertisingIDM) GeneratedMessageLite.parseFrom(g, inputStream, extensionRegistryLite);
        }

        public static StartAdvertisingIDM parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (StartAdvertisingIDM) parseDelimitedFrom(g, inputStream);
        }

        public static StartAdvertisingIDM parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (StartAdvertisingIDM) parseDelimitedFrom(g, inputStream, extensionRegistryLite);
        }

        public static StartAdvertisingIDM parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (StartAdvertisingIDM) GeneratedMessageLite.parseFrom(g, codedInputStream);
        }

        public static StartAdvertisingIDM parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (StartAdvertisingIDM) GeneratedMessageLite.parseFrom(g, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return g.createBuilder();
        }

        public static Builder newBuilder(StartAdvertisingIDM startAdvertisingIDM) {
            return g.createBuilder(startAdvertisingIDM);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<StartAdvertisingIDM, Builder> implements StartAdvertisingIDMOrBuilder {
            private Builder() {
                super(StartAdvertisingIDM.g);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
            public boolean hasIdmService() {
                return ((StartAdvertisingIDM) this.instance).hasIdmService();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
            public IDMServiceProto.IDMService getIdmService() {
                return ((StartAdvertisingIDM) this.instance).getIdmService();
            }

            public Builder setIdmService(IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).a(iDMService);
                return this;
            }

            public Builder setIdmService(IDMServiceProto.IDMService.Builder builder) {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmService(IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).b(iDMService);
                return this;
            }

            public Builder clearIdmService() {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
            public String getIntentStr() {
                return ((StartAdvertisingIDM) this.instance).getIntentStr();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
            public ByteString getIntentStrBytes() {
                return ((StartAdvertisingIDM) this.instance).getIntentStrBytes();
            }

            public Builder setIntentStr(String str) {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).a(str);
                return this;
            }

            public Builder clearIntentStr() {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).f();
                return this;
            }

            public Builder setIntentStrBytes(ByteString byteString) {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
            public String getIntentType() {
                return ((StartAdvertisingIDM) this.instance).getIntentType();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
            public ByteString getIntentTypeBytes() {
                return ((StartAdvertisingIDM) this.instance).getIntentTypeBytes();
            }

            public Builder setIntentType(String str) {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).b(str);
                return this;
            }

            public Builder clearIntentType() {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).g();
                return this;
            }

            public Builder setIntentTypeBytes(ByteString byteString) {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
            public int getDiscType() {
                return ((StartAdvertisingIDM) this.instance).getDiscType();
            }

            public Builder setDiscType(int i) {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).b(i);
                return this;
            }

            public Builder clearDiscType() {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).h();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
            public int getCommType() {
                return ((StartAdvertisingIDM) this.instance).getCommType();
            }

            public Builder setCommType(int i) {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).c(i);
                return this;
            }

            public Builder clearCommType() {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).i();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartAdvertisingIDMOrBuilder
            public int getServiceSecurityType() {
                return ((StartAdvertisingIDM) this.instance).getServiceSecurityType();
            }

            public Builder setServiceSecurityType(int i) {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).d(i);
                return this;
            }

            public Builder clearServiceSecurityType() {
                copyOnWrite();
                ((StartAdvertisingIDM) this.instance).j();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new StartAdvertisingIDM();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(g, "\u0000\u0006\u0000\u0000\u0001\u0006\u0006\u0000\u0000\u0000\u0001\t\u0002Ȉ\u0003Ȉ\u0004\u0004\u0005\u0004\u0006\u0004", new Object[]{"idmService_", "intentStr_", "intentType_", "discType_", "commType_", "serviceSecurityType_"});
                case GET_DEFAULT_INSTANCE:
                    return g;
                case GET_PARSER:
                    Parser<StartAdvertisingIDM> parser = h;
                    if (parser == null) {
                        synchronized (StartAdvertisingIDM.class) {
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
            StartAdvertisingIDM startAdvertisingIDM = new StartAdvertisingIDM();
            g = startAdvertisingIDM;
            GeneratedMessageLite.registerDefaultInstance(StartAdvertisingIDM.class, startAdvertisingIDM);
        }

        public static StartAdvertisingIDM getDefaultInstance() {
            return g;
        }

        public static Parser<StartAdvertisingIDM> parser() {
            return g.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class StopAdvertisingIDM extends GeneratedMessageLite<StopAdvertisingIDM, Builder> implements StopAdvertisingIDMOrBuilder {
        public static final int IDMSERVICE_FIELD_NUMBER = 1;
        private static final StopAdvertisingIDM b;
        private static volatile Parser<StopAdvertisingIDM> c;
        private IDMServiceProto.IDMService a;

        private StopAdvertisingIDM() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StopAdvertisingIDMOrBuilder
        public boolean hasIdmService() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StopAdvertisingIDMOrBuilder
        public IDMServiceProto.IDMService getIdmService() {
            IDMServiceProto.IDMService iDMService = this.a;
            return iDMService == null ? IDMServiceProto.IDMService.getDefaultInstance() : iDMService;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                this.a = iDMService;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                IDMServiceProto.IDMService iDMService2 = this.a;
                if (iDMService2 == null || iDMService2 == IDMServiceProto.IDMService.getDefaultInstance()) {
                    this.a = iDMService;
                } else {
                    this.a = IDMServiceProto.IDMService.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMService.Builder) iDMService).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static StopAdvertisingIDM parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (StopAdvertisingIDM) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static StopAdvertisingIDM parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (StopAdvertisingIDM) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static StopAdvertisingIDM parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (StopAdvertisingIDM) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static StopAdvertisingIDM parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (StopAdvertisingIDM) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static StopAdvertisingIDM parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (StopAdvertisingIDM) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static StopAdvertisingIDM parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (StopAdvertisingIDM) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static StopAdvertisingIDM parseFrom(InputStream inputStream) throws IOException {
            return (StopAdvertisingIDM) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static StopAdvertisingIDM parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (StopAdvertisingIDM) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static StopAdvertisingIDM parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (StopAdvertisingIDM) parseDelimitedFrom(b, inputStream);
        }

        public static StopAdvertisingIDM parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (StopAdvertisingIDM) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static StopAdvertisingIDM parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (StopAdvertisingIDM) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static StopAdvertisingIDM parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (StopAdvertisingIDM) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(StopAdvertisingIDM stopAdvertisingIDM) {
            return b.createBuilder(stopAdvertisingIDM);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<StopAdvertisingIDM, Builder> implements StopAdvertisingIDMOrBuilder {
            private Builder() {
                super(StopAdvertisingIDM.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StopAdvertisingIDMOrBuilder
            public boolean hasIdmService() {
                return ((StopAdvertisingIDM) this.instance).hasIdmService();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StopAdvertisingIDMOrBuilder
            public IDMServiceProto.IDMService getIdmService() {
                return ((StopAdvertisingIDM) this.instance).getIdmService();
            }

            public Builder setIdmService(IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((StopAdvertisingIDM) this.instance).a(iDMService);
                return this;
            }

            public Builder setIdmService(IDMServiceProto.IDMService.Builder builder) {
                copyOnWrite();
                ((StopAdvertisingIDM) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmService(IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((StopAdvertisingIDM) this.instance).b(iDMService);
                return this;
            }

            public Builder clearIdmService() {
                copyOnWrite();
                ((StopAdvertisingIDM) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new StopAdvertisingIDM();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"idmService_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<StopAdvertisingIDM> parser = c;
                    if (parser == null) {
                        synchronized (StopAdvertisingIDM.class) {
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
            StopAdvertisingIDM stopAdvertisingIDM = new StopAdvertisingIDM();
            b = stopAdvertisingIDM;
            GeneratedMessageLite.registerDefaultInstance(StopAdvertisingIDM.class, stopAdvertisingIDM);
        }

        public static StopAdvertisingIDM getDefaultInstance() {
            return b;
        }

        public static Parser<StopAdvertisingIDM> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class StartDiscovery extends GeneratedMessageLite<StartDiscovery, Builder> implements StartDiscoveryOrBuilder {
        public static final int DISCTYPE_FIELD_NUMBER = 3;
        public static final int SERVICESECURITYTYPE_FIELD_NUMBER = 4;
        public static final int SERVICETYPES_FIELD_NUMBER = 1;
        public static final int SERVICEUUIDS_FIELD_NUMBER = 2;
        private static final StartDiscovery e;
        private static volatile Parser<StartDiscovery> f;
        private Internal.ProtobufList<String> a = GeneratedMessageLite.emptyProtobufList();
        private Internal.ProtobufList<String> b = GeneratedMessageLite.emptyProtobufList();
        private int c;
        private int d;

        private StartDiscovery() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
        public List<String> getServiceTypesList() {
            return this.a;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
        public int getServiceTypesCount() {
            return this.a.size();
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
        public String getServiceTypes(int i) {
            return this.a.get(i);
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
        public ByteString getServiceTypesBytes(int i) {
            return ByteString.copyFromUtf8(this.a.get(i));
        }

        private void e() {
            if (!this.a.isModifiable()) {
                this.a = GeneratedMessageLite.mutableCopy(this.a);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i, String str) {
            if (str != null) {
                e();
                this.a.set(i, str);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(String str) {
            if (str != null) {
                e();
                this.a.add(str);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Iterable<String> iterable) {
            e();
            AbstractMessageLite.addAll((Iterable) iterable, (List) this.a);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.a = GeneratedMessageLite.emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                e();
                this.a.add(byteString.toStringUtf8());
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
        public List<String> getServiceUuidsList() {
            return this.b;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
        public int getServiceUuidsCount() {
            return this.b.size();
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
        public String getServiceUuids(int i) {
            return this.b.get(i);
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
        public ByteString getServiceUuidsBytes(int i) {
            return ByteString.copyFromUtf8(this.b.get(i));
        }

        private void g() {
            if (!this.b.isModifiable()) {
                this.b = GeneratedMessageLite.mutableCopy(this.b);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i, String str) {
            if (str != null) {
                g();
                this.b.set(i, str);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(String str) {
            if (str != null) {
                g();
                this.b.add(str);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(Iterable<String> iterable) {
            g();
            AbstractMessageLite.addAll((Iterable) iterable, (List) this.b);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.b = GeneratedMessageLite.emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                g();
                this.b.add(byteString.toStringUtf8());
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
        public int getDiscType() {
            return this.c;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i) {
            this.c = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void i() {
            this.c = 0;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
        public int getServiceSecurityType() {
            return this.d;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(int i) {
            this.d = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void j() {
            this.d = 0;
        }

        public static StartDiscovery parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (StartDiscovery) GeneratedMessageLite.parseFrom(e, byteBuffer);
        }

        public static StartDiscovery parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (StartDiscovery) GeneratedMessageLite.parseFrom(e, byteBuffer, extensionRegistryLite);
        }

        public static StartDiscovery parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (StartDiscovery) GeneratedMessageLite.parseFrom(e, byteString);
        }

        public static StartDiscovery parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (StartDiscovery) GeneratedMessageLite.parseFrom(e, byteString, extensionRegistryLite);
        }

        public static StartDiscovery parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (StartDiscovery) GeneratedMessageLite.parseFrom(e, bArr);
        }

        public static StartDiscovery parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (StartDiscovery) GeneratedMessageLite.parseFrom(e, bArr, extensionRegistryLite);
        }

        public static StartDiscovery parseFrom(InputStream inputStream) throws IOException {
            return (StartDiscovery) GeneratedMessageLite.parseFrom(e, inputStream);
        }

        public static StartDiscovery parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (StartDiscovery) GeneratedMessageLite.parseFrom(e, inputStream, extensionRegistryLite);
        }

        public static StartDiscovery parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (StartDiscovery) parseDelimitedFrom(e, inputStream);
        }

        public static StartDiscovery parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (StartDiscovery) parseDelimitedFrom(e, inputStream, extensionRegistryLite);
        }

        public static StartDiscovery parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (StartDiscovery) GeneratedMessageLite.parseFrom(e, codedInputStream);
        }

        public static StartDiscovery parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (StartDiscovery) GeneratedMessageLite.parseFrom(e, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return e.createBuilder();
        }

        public static Builder newBuilder(StartDiscovery startDiscovery) {
            return e.createBuilder(startDiscovery);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<StartDiscovery, Builder> implements StartDiscoveryOrBuilder {
            private Builder() {
                super(StartDiscovery.e);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
            public List<String> getServiceTypesList() {
                return Collections.unmodifiableList(((StartDiscovery) this.instance).getServiceTypesList());
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
            public int getServiceTypesCount() {
                return ((StartDiscovery) this.instance).getServiceTypesCount();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
            public String getServiceTypes(int i) {
                return ((StartDiscovery) this.instance).getServiceTypes(i);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
            public ByteString getServiceTypesBytes(int i) {
                return ((StartDiscovery) this.instance).getServiceTypesBytes(i);
            }

            public Builder setServiceTypes(int i, String str) {
                copyOnWrite();
                ((StartDiscovery) this.instance).a(i, str);
                return this;
            }

            public Builder addServiceTypes(String str) {
                copyOnWrite();
                ((StartDiscovery) this.instance).a(str);
                return this;
            }

            public Builder addAllServiceTypes(Iterable<String> iterable) {
                copyOnWrite();
                ((StartDiscovery) this.instance).a(iterable);
                return this;
            }

            public Builder clearServiceTypes() {
                copyOnWrite();
                ((StartDiscovery) this.instance).f();
                return this;
            }

            public Builder addServiceTypesBytes(ByteString byteString) {
                copyOnWrite();
                ((StartDiscovery) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
            public List<String> getServiceUuidsList() {
                return Collections.unmodifiableList(((StartDiscovery) this.instance).getServiceUuidsList());
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
            public int getServiceUuidsCount() {
                return ((StartDiscovery) this.instance).getServiceUuidsCount();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
            public String getServiceUuids(int i) {
                return ((StartDiscovery) this.instance).getServiceUuids(i);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
            public ByteString getServiceUuidsBytes(int i) {
                return ((StartDiscovery) this.instance).getServiceUuidsBytes(i);
            }

            public Builder setServiceUuids(int i, String str) {
                copyOnWrite();
                ((StartDiscovery) this.instance).b(i, str);
                return this;
            }

            public Builder addServiceUuids(String str) {
                copyOnWrite();
                ((StartDiscovery) this.instance).b(str);
                return this;
            }

            public Builder addAllServiceUuids(Iterable<String> iterable) {
                copyOnWrite();
                ((StartDiscovery) this.instance).b(iterable);
                return this;
            }

            public Builder clearServiceUuids() {
                copyOnWrite();
                ((StartDiscovery) this.instance).h();
                return this;
            }

            public Builder addServiceUuidsBytes(ByteString byteString) {
                copyOnWrite();
                ((StartDiscovery) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
            public int getDiscType() {
                return ((StartDiscovery) this.instance).getDiscType();
            }

            public Builder setDiscType(int i) {
                copyOnWrite();
                ((StartDiscovery) this.instance).b(i);
                return this;
            }

            public Builder clearDiscType() {
                copyOnWrite();
                ((StartDiscovery) this.instance).i();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.StartDiscoveryOrBuilder
            public int getServiceSecurityType() {
                return ((StartDiscovery) this.instance).getServiceSecurityType();
            }

            public Builder setServiceSecurityType(int i) {
                copyOnWrite();
                ((StartDiscovery) this.instance).c(i);
                return this;
            }

            public Builder clearServiceSecurityType() {
                copyOnWrite();
                ((StartDiscovery) this.instance).j();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new StartDiscovery();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(e, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0002\u0000\u0001Ț\u0002Ț\u0003\u0004\u0004\u0004", new Object[]{"serviceTypes_", "serviceUuids_", "discType_", "serviceSecurityType_"});
                case GET_DEFAULT_INSTANCE:
                    return e;
                case GET_PARSER:
                    Parser<StartDiscovery> parser = f;
                    if (parser == null) {
                        synchronized (StartDiscovery.class) {
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
            StartDiscovery startDiscovery = new StartDiscovery();
            e = startDiscovery;
            GeneratedMessageLite.registerDefaultInstance(StartDiscovery.class, startDiscovery);
        }

        public static StartDiscovery getDefaultInstance() {
            return e;
        }

        public static Parser<StartDiscovery> parser() {
            return e.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class Response extends GeneratedMessageLite<Response, Builder> implements ResponseOrBuilder {
        public static final int IDMRESPONSE_FIELD_NUMBER = 1;
        private static final Response b;
        private static volatile Parser<Response> c;
        private IDMServiceProto.IDMResponse a;

        private Response() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ResponseOrBuilder
        public boolean hasIdmResponse() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ResponseOrBuilder
        public IDMServiceProto.IDMResponse getIdmResponse() {
            IDMServiceProto.IDMResponse iDMResponse = this.a;
            return iDMResponse == null ? IDMServiceProto.IDMResponse.getDefaultInstance() : iDMResponse;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMResponse iDMResponse) {
            if (iDMResponse != null) {
                this.a = iDMResponse;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMResponse.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMResponse iDMResponse) {
            if (iDMResponse != null) {
                IDMServiceProto.IDMResponse iDMResponse2 = this.a;
                if (iDMResponse2 == null || iDMResponse2 == IDMServiceProto.IDMResponse.getDefaultInstance()) {
                    this.a = iDMResponse;
                } else {
                    this.a = IDMServiceProto.IDMResponse.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMResponse.Builder) iDMResponse).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
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

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ResponseOrBuilder
            public boolean hasIdmResponse() {
                return ((Response) this.instance).hasIdmResponse();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ResponseOrBuilder
            public IDMServiceProto.IDMResponse getIdmResponse() {
                return ((Response) this.instance).getIdmResponse();
            }

            public Builder setIdmResponse(IDMServiceProto.IDMResponse iDMResponse) {
                copyOnWrite();
                ((Response) this.instance).a(iDMResponse);
                return this;
            }

            public Builder setIdmResponse(IDMServiceProto.IDMResponse.Builder builder) {
                copyOnWrite();
                ((Response) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmResponse(IDMServiceProto.IDMResponse iDMResponse) {
                copyOnWrite();
                ((Response) this.instance).b(iDMResponse);
                return this;
            }

            public Builder clearIdmResponse() {
                copyOnWrite();
                ((Response) this.instance).e();
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
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"idmResponse_"});
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

    /* loaded from: classes3.dex */
    public static final class ConnectServiceResponse extends GeneratedMessageLite<ConnectServiceResponse, Builder> implements ConnectServiceResponseOrBuilder {
        public static final int IDMCONNECTSERVICERESPONSE_FIELD_NUMBER = 1;
        private static final ConnectServiceResponse b;
        private static volatile Parser<ConnectServiceResponse> c;
        private IDMServiceProto.IDMConnectServiceResponse a;

        private ConnectServiceResponse() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceResponseOrBuilder
        public boolean hasIdmConnectServiceResponse() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceResponseOrBuilder
        public IDMServiceProto.IDMConnectServiceResponse getIdmConnectServiceResponse() {
            IDMServiceProto.IDMConnectServiceResponse iDMConnectServiceResponse = this.a;
            return iDMConnectServiceResponse == null ? IDMServiceProto.IDMConnectServiceResponse.getDefaultInstance() : iDMConnectServiceResponse;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMConnectServiceResponse iDMConnectServiceResponse) {
            if (iDMConnectServiceResponse != null) {
                this.a = iDMConnectServiceResponse;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMConnectServiceResponse.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMConnectServiceResponse iDMConnectServiceResponse) {
            if (iDMConnectServiceResponse != null) {
                IDMServiceProto.IDMConnectServiceResponse iDMConnectServiceResponse2 = this.a;
                if (iDMConnectServiceResponse2 == null || iDMConnectServiceResponse2 == IDMServiceProto.IDMConnectServiceResponse.getDefaultInstance()) {
                    this.a = iDMConnectServiceResponse;
                } else {
                    this.a = IDMServiceProto.IDMConnectServiceResponse.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMConnectServiceResponse.Builder) iDMConnectServiceResponse).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static ConnectServiceResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ConnectServiceResponse) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static ConnectServiceResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ConnectServiceResponse) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static ConnectServiceResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ConnectServiceResponse) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static ConnectServiceResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ConnectServiceResponse) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static ConnectServiceResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ConnectServiceResponse) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static ConnectServiceResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ConnectServiceResponse) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static ConnectServiceResponse parseFrom(InputStream inputStream) throws IOException {
            return (ConnectServiceResponse) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static ConnectServiceResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnectServiceResponse) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static ConnectServiceResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ConnectServiceResponse) parseDelimitedFrom(b, inputStream);
        }

        public static ConnectServiceResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnectServiceResponse) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static ConnectServiceResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ConnectServiceResponse) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static ConnectServiceResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnectServiceResponse) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(ConnectServiceResponse connectServiceResponse) {
            return b.createBuilder(connectServiceResponse);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<ConnectServiceResponse, Builder> implements ConnectServiceResponseOrBuilder {
            private Builder() {
                super(ConnectServiceResponse.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceResponseOrBuilder
            public boolean hasIdmConnectServiceResponse() {
                return ((ConnectServiceResponse) this.instance).hasIdmConnectServiceResponse();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ConnectServiceResponseOrBuilder
            public IDMServiceProto.IDMConnectServiceResponse getIdmConnectServiceResponse() {
                return ((ConnectServiceResponse) this.instance).getIdmConnectServiceResponse();
            }

            public Builder setIdmConnectServiceResponse(IDMServiceProto.IDMConnectServiceResponse iDMConnectServiceResponse) {
                copyOnWrite();
                ((ConnectServiceResponse) this.instance).a(iDMConnectServiceResponse);
                return this;
            }

            public Builder setIdmConnectServiceResponse(IDMServiceProto.IDMConnectServiceResponse.Builder builder) {
                copyOnWrite();
                ((ConnectServiceResponse) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmConnectServiceResponse(IDMServiceProto.IDMConnectServiceResponse iDMConnectServiceResponse) {
                copyOnWrite();
                ((ConnectServiceResponse) this.instance).b(iDMConnectServiceResponse);
                return this;
            }

            public Builder clearIdmConnectServiceResponse() {
                copyOnWrite();
                ((ConnectServiceResponse) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new ConnectServiceResponse();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"idmConnectServiceResponse_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<ConnectServiceResponse> parser = c;
                    if (parser == null) {
                        synchronized (ConnectServiceResponse.class) {
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
            ConnectServiceResponse connectServiceResponse = new ConnectServiceResponse();
            b = connectServiceResponse;
            GeneratedMessageLite.registerDefaultInstance(ConnectServiceResponse.class, connectServiceResponse);
        }

        public static ConnectServiceResponse getDefaultInstance() {
            return b;
        }

        public static Parser<ConnectServiceResponse> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class Event extends GeneratedMessageLite<Event, Builder> implements EventOrBuilder {
        public static final int IDMEVENT_FIELD_NUMBER = 2;
        public static final int IDMSERVICE_FIELD_NUMBER = 1;
        private static final Event c;
        private static volatile Parser<Event> d;
        private IDMServiceProto.IDMService a;
        private IDMServiceProto.IDMEvent b;

        private Event() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.EventOrBuilder
        public boolean hasIdmService() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.EventOrBuilder
        public IDMServiceProto.IDMService getIdmService() {
            IDMServiceProto.IDMService iDMService = this.a;
            return iDMService == null ? IDMServiceProto.IDMService.getDefaultInstance() : iDMService;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                this.a = iDMService;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                IDMServiceProto.IDMService iDMService2 = this.a;
                if (iDMService2 == null || iDMService2 == IDMServiceProto.IDMService.getDefaultInstance()) {
                    this.a = iDMService;
                } else {
                    this.a = IDMServiceProto.IDMService.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMService.Builder) iDMService).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.EventOrBuilder
        public boolean hasIdmEvent() {
            return this.b != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.EventOrBuilder
        public IDMServiceProto.IDMEvent getIdmEvent() {
            IDMServiceProto.IDMEvent iDMEvent = this.b;
            return iDMEvent == null ? IDMServiceProto.IDMEvent.getDefaultInstance() : iDMEvent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMEvent iDMEvent) {
            if (iDMEvent != null) {
                this.b = iDMEvent;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMEvent.Builder builder) {
            this.b = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMEvent iDMEvent) {
            if (iDMEvent != null) {
                IDMServiceProto.IDMEvent iDMEvent2 = this.b;
                if (iDMEvent2 == null || iDMEvent2 == IDMServiceProto.IDMEvent.getDefaultInstance()) {
                    this.b = iDMEvent;
                } else {
                    this.b = IDMServiceProto.IDMEvent.newBuilder(this.b).mergeFrom((IDMServiceProto.IDMEvent.Builder) iDMEvent).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = null;
        }

        public static Event parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Event) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static Event parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Event) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static Event parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Event) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static Event parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Event) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static Event parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Event) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static Event parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Event) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static Event parseFrom(InputStream inputStream) throws IOException {
            return (Event) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static Event parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Event) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static Event parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Event) parseDelimitedFrom(c, inputStream);
        }

        public static Event parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Event) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static Event parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Event) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static Event parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Event) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(Event event) {
            return c.createBuilder(event);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<Event, Builder> implements EventOrBuilder {
            private Builder() {
                super(Event.c);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.EventOrBuilder
            public boolean hasIdmService() {
                return ((Event) this.instance).hasIdmService();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.EventOrBuilder
            public IDMServiceProto.IDMService getIdmService() {
                return ((Event) this.instance).getIdmService();
            }

            public Builder setIdmService(IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((Event) this.instance).a(iDMService);
                return this;
            }

            public Builder setIdmService(IDMServiceProto.IDMService.Builder builder) {
                copyOnWrite();
                ((Event) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmService(IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((Event) this.instance).b(iDMService);
                return this;
            }

            public Builder clearIdmService() {
                copyOnWrite();
                ((Event) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.EventOrBuilder
            public boolean hasIdmEvent() {
                return ((Event) this.instance).hasIdmEvent();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.EventOrBuilder
            public IDMServiceProto.IDMEvent getIdmEvent() {
                return ((Event) this.instance).getIdmEvent();
            }

            public Builder setIdmEvent(IDMServiceProto.IDMEvent iDMEvent) {
                copyOnWrite();
                ((Event) this.instance).a(iDMEvent);
                return this;
            }

            public Builder setIdmEvent(IDMServiceProto.IDMEvent.Builder builder) {
                copyOnWrite();
                ((Event) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmEvent(IDMServiceProto.IDMEvent iDMEvent) {
                copyOnWrite();
                ((Event) this.instance).b(iDMEvent);
                return this;
            }

            public Builder clearIdmEvent() {
                copyOnWrite();
                ((Event) this.instance).f();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new Event();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\t\u0002\t", new Object[]{"idmService_", "idmEvent_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<Event> parser = d;
                    if (parser == null) {
                        synchronized (Event.class) {
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
            Event event = new Event();
            c = event;
            GeneratedMessageLite.registerDefaultInstance(Event.class, event);
        }

        public static Event getDefaultInstance() {
            return c;
        }

        public static Parser<Event> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class InviteConnection extends GeneratedMessageLite<InviteConnection, Builder> implements InviteConnectionOrBuilder {
        public static final int CONNPARAM_FIELD_NUMBER = 2;
        public static final int SERVICETYPE_FIELD_NUMBER = 1;
        private static final InviteConnection c;
        private static volatile Parser<InviteConnection> d;
        private String a = "";
        private IDMServiceProto.ConnParam b;

        private InviteConnection() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.InviteConnectionOrBuilder
        public String getServiceType() {
            return this.a;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.InviteConnectionOrBuilder
        public ByteString getServiceTypeBytes() {
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
            this.a = getDefaultInstance().getServiceType();
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.InviteConnectionOrBuilder
        public boolean hasConnParam() {
            return this.b != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.InviteConnectionOrBuilder
        public IDMServiceProto.ConnParam getConnParam() {
            IDMServiceProto.ConnParam connParam = this.b;
            return connParam == null ? IDMServiceProto.ConnParam.getDefaultInstance() : connParam;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.ConnParam connParam) {
            if (connParam != null) {
                this.b = connParam;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.ConnParam.Builder builder) {
            this.b = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.ConnParam connParam) {
            if (connParam != null) {
                IDMServiceProto.ConnParam connParam2 = this.b;
                if (connParam2 == null || connParam2 == IDMServiceProto.ConnParam.getDefaultInstance()) {
                    this.b = connParam;
                } else {
                    this.b = IDMServiceProto.ConnParam.newBuilder(this.b).mergeFrom((IDMServiceProto.ConnParam.Builder) connParam).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = null;
        }

        public static InviteConnection parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (InviteConnection) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static InviteConnection parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (InviteConnection) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static InviteConnection parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (InviteConnection) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static InviteConnection parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (InviteConnection) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static InviteConnection parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (InviteConnection) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static InviteConnection parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (InviteConnection) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static InviteConnection parseFrom(InputStream inputStream) throws IOException {
            return (InviteConnection) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static InviteConnection parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (InviteConnection) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static InviteConnection parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (InviteConnection) parseDelimitedFrom(c, inputStream);
        }

        public static InviteConnection parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (InviteConnection) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static InviteConnection parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (InviteConnection) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static InviteConnection parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (InviteConnection) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(InviteConnection inviteConnection) {
            return c.createBuilder(inviteConnection);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<InviteConnection, Builder> implements InviteConnectionOrBuilder {
            private Builder() {
                super(InviteConnection.c);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.InviteConnectionOrBuilder
            public String getServiceType() {
                return ((InviteConnection) this.instance).getServiceType();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.InviteConnectionOrBuilder
            public ByteString getServiceTypeBytes() {
                return ((InviteConnection) this.instance).getServiceTypeBytes();
            }

            public Builder setServiceType(String str) {
                copyOnWrite();
                ((InviteConnection) this.instance).a(str);
                return this;
            }

            public Builder clearServiceType() {
                copyOnWrite();
                ((InviteConnection) this.instance).e();
                return this;
            }

            public Builder setServiceTypeBytes(ByteString byteString) {
                copyOnWrite();
                ((InviteConnection) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.InviteConnectionOrBuilder
            public boolean hasConnParam() {
                return ((InviteConnection) this.instance).hasConnParam();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.InviteConnectionOrBuilder
            public IDMServiceProto.ConnParam getConnParam() {
                return ((InviteConnection) this.instance).getConnParam();
            }

            public Builder setConnParam(IDMServiceProto.ConnParam connParam) {
                copyOnWrite();
                ((InviteConnection) this.instance).a(connParam);
                return this;
            }

            public Builder setConnParam(IDMServiceProto.ConnParam.Builder builder) {
                copyOnWrite();
                ((InviteConnection) this.instance).a(builder);
                return this;
            }

            public Builder mergeConnParam(IDMServiceProto.ConnParam connParam) {
                copyOnWrite();
                ((InviteConnection) this.instance).b(connParam);
                return this;
            }

            public Builder clearConnParam() {
                copyOnWrite();
                ((InviteConnection) this.instance).f();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new InviteConnection();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\t", new Object[]{"serviceType_", "connParam_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<InviteConnection> parser = d;
                    if (parser == null) {
                        synchronized (InviteConnection.class) {
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
            InviteConnection inviteConnection = new InviteConnection();
            c = inviteConnection;
            GeneratedMessageLite.registerDefaultInstance(InviteConnection.class, inviteConnection);
        }

        public static InviteConnection getDefaultInstance() {
            return c;
        }

        public static Parser<InviteConnection> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class AbortInvitation extends GeneratedMessageLite<AbortInvitation, Builder> implements AbortInvitationOrBuilder {
        public static final int SERVICETYPE_FIELD_NUMBER = 1;
        private static final AbortInvitation b;
        private static volatile Parser<AbortInvitation> c;
        private String a = "";

        private AbortInvitation() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.AbortInvitationOrBuilder
        public String getServiceType() {
            return this.a;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.AbortInvitationOrBuilder
        public ByteString getServiceTypeBytes() {
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
            this.a = getDefaultInstance().getServiceType();
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

        public static AbortInvitation parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (AbortInvitation) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static AbortInvitation parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AbortInvitation) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static AbortInvitation parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (AbortInvitation) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static AbortInvitation parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AbortInvitation) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static AbortInvitation parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (AbortInvitation) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static AbortInvitation parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AbortInvitation) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static AbortInvitation parseFrom(InputStream inputStream) throws IOException {
            return (AbortInvitation) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static AbortInvitation parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AbortInvitation) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static AbortInvitation parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (AbortInvitation) parseDelimitedFrom(b, inputStream);
        }

        public static AbortInvitation parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AbortInvitation) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static AbortInvitation parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (AbortInvitation) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static AbortInvitation parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AbortInvitation) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(AbortInvitation abortInvitation) {
            return b.createBuilder(abortInvitation);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<AbortInvitation, Builder> implements AbortInvitationOrBuilder {
            private Builder() {
                super(AbortInvitation.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.AbortInvitationOrBuilder
            public String getServiceType() {
                return ((AbortInvitation) this.instance).getServiceType();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.AbortInvitationOrBuilder
            public ByteString getServiceTypeBytes() {
                return ((AbortInvitation) this.instance).getServiceTypeBytes();
            }

            public Builder setServiceType(String str) {
                copyOnWrite();
                ((AbortInvitation) this.instance).a(str);
                return this;
            }

            public Builder clearServiceType() {
                copyOnWrite();
                ((AbortInvitation) this.instance).e();
                return this;
            }

            public Builder setServiceTypeBytes(ByteString byteString) {
                copyOnWrite();
                ((AbortInvitation) this.instance).a(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new AbortInvitation();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ", new Object[]{"serviceType_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<AbortInvitation> parser = c;
                    if (parser == null) {
                        synchronized (AbortInvitation.class) {
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
            AbortInvitation abortInvitation = new AbortInvitation();
            b = abortInvitation;
            GeneratedMessageLite.registerDefaultInstance(AbortInvitation.class, abortInvitation);
        }

        public static AbortInvitation getDefaultInstance() {
            return b;
        }

        public static Parser<AbortInvitation> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class AcceptInvitation extends GeneratedMessageLite<AcceptInvitation, Builder> implements AcceptInvitationOrBuilder {
        public static final int INVITESTR_FIELD_NUMBER = 2;
        public static final int SERVICEID_FIELD_NUMBER = 1;
        private static final AcceptInvitation c;
        private static volatile Parser<AcceptInvitation> d;
        private String a = "";
        private String b = "";

        private AcceptInvitation() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.AcceptInvitationOrBuilder
        public String getServiceId() {
            return this.a;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.AcceptInvitationOrBuilder
        public ByteString getServiceIdBytes() {
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
            this.a = getDefaultInstance().getServiceId();
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.AcceptInvitationOrBuilder
        public String getInviteStr() {
            return this.b;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.AcceptInvitationOrBuilder
        public ByteString getInviteStrBytes() {
            return ByteString.copyFromUtf8(this.b);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(String str) {
            if (str != null) {
                this.b = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = getDefaultInstance().getInviteStr();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static AcceptInvitation parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (AcceptInvitation) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static AcceptInvitation parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AcceptInvitation) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static AcceptInvitation parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (AcceptInvitation) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static AcceptInvitation parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AcceptInvitation) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static AcceptInvitation parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (AcceptInvitation) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static AcceptInvitation parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AcceptInvitation) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static AcceptInvitation parseFrom(InputStream inputStream) throws IOException {
            return (AcceptInvitation) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static AcceptInvitation parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AcceptInvitation) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static AcceptInvitation parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (AcceptInvitation) parseDelimitedFrom(c, inputStream);
        }

        public static AcceptInvitation parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AcceptInvitation) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static AcceptInvitation parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (AcceptInvitation) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static AcceptInvitation parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AcceptInvitation) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(AcceptInvitation acceptInvitation) {
            return c.createBuilder(acceptInvitation);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<AcceptInvitation, Builder> implements AcceptInvitationOrBuilder {
            private Builder() {
                super(AcceptInvitation.c);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.AcceptInvitationOrBuilder
            public String getServiceId() {
                return ((AcceptInvitation) this.instance).getServiceId();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.AcceptInvitationOrBuilder
            public ByteString getServiceIdBytes() {
                return ((AcceptInvitation) this.instance).getServiceIdBytes();
            }

            public Builder setServiceId(String str) {
                copyOnWrite();
                ((AcceptInvitation) this.instance).a(str);
                return this;
            }

            public Builder clearServiceId() {
                copyOnWrite();
                ((AcceptInvitation) this.instance).e();
                return this;
            }

            public Builder setServiceIdBytes(ByteString byteString) {
                copyOnWrite();
                ((AcceptInvitation) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.AcceptInvitationOrBuilder
            public String getInviteStr() {
                return ((AcceptInvitation) this.instance).getInviteStr();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.AcceptInvitationOrBuilder
            public ByteString getInviteStrBytes() {
                return ((AcceptInvitation) this.instance).getInviteStrBytes();
            }

            public Builder setInviteStr(String str) {
                copyOnWrite();
                ((AcceptInvitation) this.instance).b(str);
                return this;
            }

            public Builder clearInviteStr() {
                copyOnWrite();
                ((AcceptInvitation) this.instance).f();
                return this;
            }

            public Builder setInviteStrBytes(ByteString byteString) {
                copyOnWrite();
                ((AcceptInvitation) this.instance).b(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new AcceptInvitation();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ", new Object[]{"serviceId_", "inviteStr_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<AcceptInvitation> parser = d;
                    if (parser == null) {
                        synchronized (AcceptInvitation.class) {
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
            AcceptInvitation acceptInvitation = new AcceptInvitation();
            c = acceptInvitation;
            GeneratedMessageLite.registerDefaultInstance(AcceptInvitation.class, acceptInvitation);
        }

        public static AcceptInvitation getDefaultInstance() {
            return c;
        }

        public static Parser<AcceptInvitation> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class OnServiceFound extends GeneratedMessageLite<OnServiceFound, Builder> implements OnServiceFoundOrBuilder {
        public static final int IDMSERVICE_FIELD_NUMBER = 1;
        private static final OnServiceFound b;
        private static volatile Parser<OnServiceFound> c;
        private IDMServiceProto.IDMService a;

        private OnServiceFound() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceFoundOrBuilder
        public boolean hasIdmService() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceFoundOrBuilder
        public IDMServiceProto.IDMService getIdmService() {
            IDMServiceProto.IDMService iDMService = this.a;
            return iDMService == null ? IDMServiceProto.IDMService.getDefaultInstance() : iDMService;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                this.a = iDMService;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                IDMServiceProto.IDMService iDMService2 = this.a;
                if (iDMService2 == null || iDMService2 == IDMServiceProto.IDMService.getDefaultInstance()) {
                    this.a = iDMService;
                } else {
                    this.a = IDMServiceProto.IDMService.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMService.Builder) iDMService).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static OnServiceFound parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (OnServiceFound) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static OnServiceFound parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnServiceFound) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static OnServiceFound parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (OnServiceFound) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static OnServiceFound parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnServiceFound) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static OnServiceFound parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (OnServiceFound) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static OnServiceFound parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnServiceFound) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static OnServiceFound parseFrom(InputStream inputStream) throws IOException {
            return (OnServiceFound) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static OnServiceFound parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnServiceFound) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnServiceFound parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (OnServiceFound) parseDelimitedFrom(b, inputStream);
        }

        public static OnServiceFound parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnServiceFound) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnServiceFound parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (OnServiceFound) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static OnServiceFound parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnServiceFound) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(OnServiceFound onServiceFound) {
            return b.createBuilder(onServiceFound);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<OnServiceFound, Builder> implements OnServiceFoundOrBuilder {
            private Builder() {
                super(OnServiceFound.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceFoundOrBuilder
            public boolean hasIdmService() {
                return ((OnServiceFound) this.instance).hasIdmService();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceFoundOrBuilder
            public IDMServiceProto.IDMService getIdmService() {
                return ((OnServiceFound) this.instance).getIdmService();
            }

            public Builder setIdmService(IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((OnServiceFound) this.instance).a(iDMService);
                return this;
            }

            public Builder setIdmService(IDMServiceProto.IDMService.Builder builder) {
                copyOnWrite();
                ((OnServiceFound) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmService(IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((OnServiceFound) this.instance).b(iDMService);
                return this;
            }

            public Builder clearIdmService() {
                copyOnWrite();
                ((OnServiceFound) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new OnServiceFound();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"idmService_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<OnServiceFound> parser = c;
                    if (parser == null) {
                        synchronized (OnServiceFound.class) {
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
            OnServiceFound onServiceFound = new OnServiceFound();
            b = onServiceFound;
            GeneratedMessageLite.registerDefaultInstance(OnServiceFound.class, onServiceFound);
        }

        public static OnServiceFound getDefaultInstance() {
            return b;
        }

        public static Parser<OnServiceFound> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class OnServiceLost extends GeneratedMessageLite<OnServiceLost, Builder> implements OnServiceLostOrBuilder {
        public static final int IDMSERVICE_FIELD_NUMBER = 1;
        public static final int SERVICEID_FIELD_NUMBER = 2;
        private static final OnServiceLost c;
        private static volatile Parser<OnServiceLost> d;
        private IDMServiceProto.IDMService a;
        private String b = "";

        private OnServiceLost() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceLostOrBuilder
        public boolean hasIdmService() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceLostOrBuilder
        public IDMServiceProto.IDMService getIdmService() {
            IDMServiceProto.IDMService iDMService = this.a;
            return iDMService == null ? IDMServiceProto.IDMService.getDefaultInstance() : iDMService;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                this.a = iDMService;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                IDMServiceProto.IDMService iDMService2 = this.a;
                if (iDMService2 == null || iDMService2 == IDMServiceProto.IDMService.getDefaultInstance()) {
                    this.a = iDMService;
                } else {
                    this.a = IDMServiceProto.IDMService.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMService.Builder) iDMService).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceLostOrBuilder
        public String getServiceId() {
            return this.b;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceLostOrBuilder
        public ByteString getServiceIdBytes() {
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
            this.b = getDefaultInstance().getServiceId();
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

        public static OnServiceLost parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (OnServiceLost) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static OnServiceLost parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnServiceLost) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static OnServiceLost parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (OnServiceLost) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static OnServiceLost parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnServiceLost) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static OnServiceLost parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (OnServiceLost) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static OnServiceLost parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnServiceLost) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static OnServiceLost parseFrom(InputStream inputStream) throws IOException {
            return (OnServiceLost) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static OnServiceLost parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnServiceLost) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static OnServiceLost parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (OnServiceLost) parseDelimitedFrom(c, inputStream);
        }

        public static OnServiceLost parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnServiceLost) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static OnServiceLost parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (OnServiceLost) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static OnServiceLost parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnServiceLost) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(OnServiceLost onServiceLost) {
            return c.createBuilder(onServiceLost);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<OnServiceLost, Builder> implements OnServiceLostOrBuilder {
            private Builder() {
                super(OnServiceLost.c);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceLostOrBuilder
            public boolean hasIdmService() {
                return ((OnServiceLost) this.instance).hasIdmService();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceLostOrBuilder
            public IDMServiceProto.IDMService getIdmService() {
                return ((OnServiceLost) this.instance).getIdmService();
            }

            public Builder setIdmService(IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((OnServiceLost) this.instance).a(iDMService);
                return this;
            }

            public Builder setIdmService(IDMServiceProto.IDMService.Builder builder) {
                copyOnWrite();
                ((OnServiceLost) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmService(IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((OnServiceLost) this.instance).b(iDMService);
                return this;
            }

            public Builder clearIdmService() {
                copyOnWrite();
                ((OnServiceLost) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceLostOrBuilder
            public String getServiceId() {
                return ((OnServiceLost) this.instance).getServiceId();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceLostOrBuilder
            public ByteString getServiceIdBytes() {
                return ((OnServiceLost) this.instance).getServiceIdBytes();
            }

            public Builder setServiceId(String str) {
                copyOnWrite();
                ((OnServiceLost) this.instance).a(str);
                return this;
            }

            public Builder clearServiceId() {
                copyOnWrite();
                ((OnServiceLost) this.instance).f();
                return this;
            }

            public Builder setServiceIdBytes(ByteString byteString) {
                copyOnWrite();
                ((OnServiceLost) this.instance).a(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new OnServiceLost();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\t\u0002Ȉ", new Object[]{"idmService_", "serviceId_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<OnServiceLost> parser = d;
                    if (parser == null) {
                        synchronized (OnServiceLost.class) {
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
            OnServiceLost onServiceLost = new OnServiceLost();
            c = onServiceLost;
            GeneratedMessageLite.registerDefaultInstance(OnServiceLost.class, onServiceLost);
        }

        public static OnServiceLost getDefaultInstance() {
            return c;
        }

        public static Parser<OnServiceLost> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class OnResponse extends GeneratedMessageLite<OnResponse, Builder> implements OnResponseOrBuilder {
        public static final int IDMRESPONSE_FIELD_NUMBER = 1;
        private static final OnResponse b;
        private static volatile Parser<OnResponse> c;
        private IDMServiceProto.IDMResponse a;

        private OnResponse() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnResponseOrBuilder
        public boolean hasIdmResponse() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnResponseOrBuilder
        public IDMServiceProto.IDMResponse getIdmResponse() {
            IDMServiceProto.IDMResponse iDMResponse = this.a;
            return iDMResponse == null ? IDMServiceProto.IDMResponse.getDefaultInstance() : iDMResponse;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMResponse iDMResponse) {
            if (iDMResponse != null) {
                this.a = iDMResponse;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMResponse.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMResponse iDMResponse) {
            if (iDMResponse != null) {
                IDMServiceProto.IDMResponse iDMResponse2 = this.a;
                if (iDMResponse2 == null || iDMResponse2 == IDMServiceProto.IDMResponse.getDefaultInstance()) {
                    this.a = iDMResponse;
                } else {
                    this.a = IDMServiceProto.IDMResponse.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMResponse.Builder) iDMResponse).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static OnResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (OnResponse) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static OnResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnResponse) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static OnResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (OnResponse) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static OnResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnResponse) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static OnResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (OnResponse) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static OnResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnResponse) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static OnResponse parseFrom(InputStream inputStream) throws IOException {
            return (OnResponse) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static OnResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnResponse) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (OnResponse) parseDelimitedFrom(b, inputStream);
        }

        public static OnResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnResponse) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (OnResponse) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static OnResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnResponse) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(OnResponse onResponse) {
            return b.createBuilder(onResponse);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<OnResponse, Builder> implements OnResponseOrBuilder {
            private Builder() {
                super(OnResponse.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnResponseOrBuilder
            public boolean hasIdmResponse() {
                return ((OnResponse) this.instance).hasIdmResponse();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnResponseOrBuilder
            public IDMServiceProto.IDMResponse getIdmResponse() {
                return ((OnResponse) this.instance).getIdmResponse();
            }

            public Builder setIdmResponse(IDMServiceProto.IDMResponse iDMResponse) {
                copyOnWrite();
                ((OnResponse) this.instance).a(iDMResponse);
                return this;
            }

            public Builder setIdmResponse(IDMServiceProto.IDMResponse.Builder builder) {
                copyOnWrite();
                ((OnResponse) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmResponse(IDMServiceProto.IDMResponse iDMResponse) {
                copyOnWrite();
                ((OnResponse) this.instance).b(iDMResponse);
                return this;
            }

            public Builder clearIdmResponse() {
                copyOnWrite();
                ((OnResponse) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new OnResponse();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"idmResponse_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<OnResponse> parser = c;
                    if (parser == null) {
                        synchronized (OnResponse.class) {
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
            OnResponse onResponse = new OnResponse();
            b = onResponse;
            GeneratedMessageLite.registerDefaultInstance(OnResponse.class, onResponse);
        }

        public static OnResponse getDefaultInstance() {
            return b;
        }

        public static Parser<OnResponse> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class OnEvent extends GeneratedMessageLite<OnEvent, Builder> implements OnEventOrBuilder {
        public static final int IDMEVENT_FIELD_NUMBER = 1;
        private static final OnEvent b;
        private static volatile Parser<OnEvent> c;
        private IDMServiceProto.IDMEvent a;

        private OnEvent() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnEventOrBuilder
        public boolean hasIdmEvent() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnEventOrBuilder
        public IDMServiceProto.IDMEvent getIdmEvent() {
            IDMServiceProto.IDMEvent iDMEvent = this.a;
            return iDMEvent == null ? IDMServiceProto.IDMEvent.getDefaultInstance() : iDMEvent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMEvent iDMEvent) {
            if (iDMEvent != null) {
                this.a = iDMEvent;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMEvent.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMEvent iDMEvent) {
            if (iDMEvent != null) {
                IDMServiceProto.IDMEvent iDMEvent2 = this.a;
                if (iDMEvent2 == null || iDMEvent2 == IDMServiceProto.IDMEvent.getDefaultInstance()) {
                    this.a = iDMEvent;
                } else {
                    this.a = IDMServiceProto.IDMEvent.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMEvent.Builder) iDMEvent).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static OnEvent parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (OnEvent) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static OnEvent parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnEvent) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static OnEvent parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (OnEvent) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static OnEvent parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnEvent) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static OnEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (OnEvent) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static OnEvent parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnEvent) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static OnEvent parseFrom(InputStream inputStream) throws IOException {
            return (OnEvent) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static OnEvent parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnEvent) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnEvent parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (OnEvent) parseDelimitedFrom(b, inputStream);
        }

        public static OnEvent parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnEvent) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnEvent parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (OnEvent) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static OnEvent parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnEvent) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(OnEvent onEvent) {
            return b.createBuilder(onEvent);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<OnEvent, Builder> implements OnEventOrBuilder {
            private Builder() {
                super(OnEvent.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnEventOrBuilder
            public boolean hasIdmEvent() {
                return ((OnEvent) this.instance).hasIdmEvent();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnEventOrBuilder
            public IDMServiceProto.IDMEvent getIdmEvent() {
                return ((OnEvent) this.instance).getIdmEvent();
            }

            public Builder setIdmEvent(IDMServiceProto.IDMEvent iDMEvent) {
                copyOnWrite();
                ((OnEvent) this.instance).a(iDMEvent);
                return this;
            }

            public Builder setIdmEvent(IDMServiceProto.IDMEvent.Builder builder) {
                copyOnWrite();
                ((OnEvent) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmEvent(IDMServiceProto.IDMEvent iDMEvent) {
                copyOnWrite();
                ((OnEvent) this.instance).b(iDMEvent);
                return this;
            }

            public Builder clearIdmEvent() {
                copyOnWrite();
                ((OnEvent) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new OnEvent();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"idmEvent_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<OnEvent> parser = c;
                    if (parser == null) {
                        synchronized (OnEvent.class) {
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
            OnEvent onEvent = new OnEvent();
            b = onEvent;
            GeneratedMessageLite.registerDefaultInstance(OnEvent.class, onEvent);
        }

        public static OnEvent getDefaultInstance() {
            return b;
        }

        public static Parser<OnEvent> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class OnServiceConnectStatus extends GeneratedMessageLite<OnServiceConnectStatus, Builder> implements OnServiceConnectStatusOrBuilder {
        public static final int CONNPARAM_FIELD_NUMBER = 4;
        public static final int ENDPOINT_FIELD_NUMBER = 3;
        public static final int SERVICEID_FIELD_NUMBER = 2;
        public static final int STATUS_FIELD_NUMBER = 1;
        private static final OnServiceConnectStatus e;
        private static volatile Parser<OnServiceConnectStatus> f;
        private int a;
        private String b = "";
        private IDMServiceProto.Endpoint c;
        private IDMServiceProto.ConnParam d;

        private OnServiceConnectStatus() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceConnectStatusOrBuilder
        public int getStatus() {
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceConnectStatusOrBuilder
        public String getServiceId() {
            return this.b;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceConnectStatusOrBuilder
        public ByteString getServiceIdBytes() {
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
            this.b = getDefaultInstance().getServiceId();
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceConnectStatusOrBuilder
        public boolean hasEndpoint() {
            return this.c != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceConnectStatusOrBuilder
        public IDMServiceProto.Endpoint getEndpoint() {
            IDMServiceProto.Endpoint endpoint = this.c;
            return endpoint == null ? IDMServiceProto.Endpoint.getDefaultInstance() : endpoint;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.Endpoint endpoint) {
            if (endpoint != null) {
                this.c = endpoint;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.Endpoint.Builder builder) {
            this.c = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.Endpoint endpoint) {
            if (endpoint != null) {
                IDMServiceProto.Endpoint endpoint2 = this.c;
                if (endpoint2 == null || endpoint2 == IDMServiceProto.Endpoint.getDefaultInstance()) {
                    this.c = endpoint;
                } else {
                    this.c = IDMServiceProto.Endpoint.newBuilder(this.c).mergeFrom((IDMServiceProto.Endpoint.Builder) endpoint).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g() {
            this.c = null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceConnectStatusOrBuilder
        public boolean hasConnParam() {
            return this.d != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceConnectStatusOrBuilder
        public IDMServiceProto.ConnParam getConnParam() {
            IDMServiceProto.ConnParam connParam = this.d;
            return connParam == null ? IDMServiceProto.ConnParam.getDefaultInstance() : connParam;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.ConnParam connParam) {
            if (connParam != null) {
                this.d = connParam;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.ConnParam.Builder builder) {
            this.d = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.ConnParam connParam) {
            if (connParam != null) {
                IDMServiceProto.ConnParam connParam2 = this.d;
                if (connParam2 == null || connParam2 == IDMServiceProto.ConnParam.getDefaultInstance()) {
                    this.d = connParam;
                } else {
                    this.d = IDMServiceProto.ConnParam.newBuilder(this.d).mergeFrom((IDMServiceProto.ConnParam.Builder) connParam).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.d = null;
        }

        public static OnServiceConnectStatus parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (OnServiceConnectStatus) GeneratedMessageLite.parseFrom(e, byteBuffer);
        }

        public static OnServiceConnectStatus parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnServiceConnectStatus) GeneratedMessageLite.parseFrom(e, byteBuffer, extensionRegistryLite);
        }

        public static OnServiceConnectStatus parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (OnServiceConnectStatus) GeneratedMessageLite.parseFrom(e, byteString);
        }

        public static OnServiceConnectStatus parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnServiceConnectStatus) GeneratedMessageLite.parseFrom(e, byteString, extensionRegistryLite);
        }

        public static OnServiceConnectStatus parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (OnServiceConnectStatus) GeneratedMessageLite.parseFrom(e, bArr);
        }

        public static OnServiceConnectStatus parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnServiceConnectStatus) GeneratedMessageLite.parseFrom(e, bArr, extensionRegistryLite);
        }

        public static OnServiceConnectStatus parseFrom(InputStream inputStream) throws IOException {
            return (OnServiceConnectStatus) GeneratedMessageLite.parseFrom(e, inputStream);
        }

        public static OnServiceConnectStatus parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnServiceConnectStatus) GeneratedMessageLite.parseFrom(e, inputStream, extensionRegistryLite);
        }

        public static OnServiceConnectStatus parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (OnServiceConnectStatus) parseDelimitedFrom(e, inputStream);
        }

        public static OnServiceConnectStatus parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnServiceConnectStatus) parseDelimitedFrom(e, inputStream, extensionRegistryLite);
        }

        public static OnServiceConnectStatus parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (OnServiceConnectStatus) GeneratedMessageLite.parseFrom(e, codedInputStream);
        }

        public static OnServiceConnectStatus parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnServiceConnectStatus) GeneratedMessageLite.parseFrom(e, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return e.createBuilder();
        }

        public static Builder newBuilder(OnServiceConnectStatus onServiceConnectStatus) {
            return e.createBuilder(onServiceConnectStatus);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<OnServiceConnectStatus, Builder> implements OnServiceConnectStatusOrBuilder {
            private Builder() {
                super(OnServiceConnectStatus.e);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceConnectStatusOrBuilder
            public int getStatus() {
                return ((OnServiceConnectStatus) this.instance).getStatus();
            }

            public Builder setStatus(int i) {
                copyOnWrite();
                ((OnServiceConnectStatus) this.instance).b(i);
                return this;
            }

            public Builder clearStatus() {
                copyOnWrite();
                ((OnServiceConnectStatus) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceConnectStatusOrBuilder
            public String getServiceId() {
                return ((OnServiceConnectStatus) this.instance).getServiceId();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceConnectStatusOrBuilder
            public ByteString getServiceIdBytes() {
                return ((OnServiceConnectStatus) this.instance).getServiceIdBytes();
            }

            public Builder setServiceId(String str) {
                copyOnWrite();
                ((OnServiceConnectStatus) this.instance).a(str);
                return this;
            }

            public Builder clearServiceId() {
                copyOnWrite();
                ((OnServiceConnectStatus) this.instance).f();
                return this;
            }

            public Builder setServiceIdBytes(ByteString byteString) {
                copyOnWrite();
                ((OnServiceConnectStatus) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceConnectStatusOrBuilder
            public boolean hasEndpoint() {
                return ((OnServiceConnectStatus) this.instance).hasEndpoint();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceConnectStatusOrBuilder
            public IDMServiceProto.Endpoint getEndpoint() {
                return ((OnServiceConnectStatus) this.instance).getEndpoint();
            }

            public Builder setEndpoint(IDMServiceProto.Endpoint endpoint) {
                copyOnWrite();
                ((OnServiceConnectStatus) this.instance).a(endpoint);
                return this;
            }

            public Builder setEndpoint(IDMServiceProto.Endpoint.Builder builder) {
                copyOnWrite();
                ((OnServiceConnectStatus) this.instance).a(builder);
                return this;
            }

            public Builder mergeEndpoint(IDMServiceProto.Endpoint endpoint) {
                copyOnWrite();
                ((OnServiceConnectStatus) this.instance).b(endpoint);
                return this;
            }

            public Builder clearEndpoint() {
                copyOnWrite();
                ((OnServiceConnectStatus) this.instance).g();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceConnectStatusOrBuilder
            public boolean hasConnParam() {
                return ((OnServiceConnectStatus) this.instance).hasConnParam();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnServiceConnectStatusOrBuilder
            public IDMServiceProto.ConnParam getConnParam() {
                return ((OnServiceConnectStatus) this.instance).getConnParam();
            }

            public Builder setConnParam(IDMServiceProto.ConnParam connParam) {
                copyOnWrite();
                ((OnServiceConnectStatus) this.instance).a(connParam);
                return this;
            }

            public Builder setConnParam(IDMServiceProto.ConnParam.Builder builder) {
                copyOnWrite();
                ((OnServiceConnectStatus) this.instance).a(builder);
                return this;
            }

            public Builder mergeConnParam(IDMServiceProto.ConnParam connParam) {
                copyOnWrite();
                ((OnServiceConnectStatus) this.instance).b(connParam);
                return this;
            }

            public Builder clearConnParam() {
                copyOnWrite();
                ((OnServiceConnectStatus) this.instance).h();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new OnServiceConnectStatus();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(e, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003\t\u0004\t", new Object[]{"status_", "serviceId_", "endpoint_", "connParam_"});
                case GET_DEFAULT_INSTANCE:
                    return e;
                case GET_PARSER:
                    Parser<OnServiceConnectStatus> parser = f;
                    if (parser == null) {
                        synchronized (OnServiceConnectStatus.class) {
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
            OnServiceConnectStatus onServiceConnectStatus = new OnServiceConnectStatus();
            e = onServiceConnectStatus;
            GeneratedMessageLite.registerDefaultInstance(OnServiceConnectStatus.class, onServiceConnectStatus);
        }

        public static OnServiceConnectStatus getDefaultInstance() {
            return e;
        }

        public static Parser<OnServiceConnectStatus> parser() {
            return e.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class OnInviteConnection extends GeneratedMessageLite<OnInviteConnection, Builder> implements OnInviteConnectionOrBuilder {
        public static final int CODE_FIELD_NUMBER = 1;
        public static final int INVITESTR_FIELD_NUMBER = 2;
        private static final OnInviteConnection c;
        private static volatile Parser<OnInviteConnection> d;
        private int a;
        private String b = "";

        private OnInviteConnection() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnInviteConnectionOrBuilder
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnInviteConnectionOrBuilder
        public String getInviteStr() {
            return this.b;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnInviteConnectionOrBuilder
        public ByteString getInviteStrBytes() {
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
            this.b = getDefaultInstance().getInviteStr();
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

        public static OnInviteConnection parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (OnInviteConnection) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static OnInviteConnection parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnInviteConnection) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static OnInviteConnection parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (OnInviteConnection) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static OnInviteConnection parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnInviteConnection) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static OnInviteConnection parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (OnInviteConnection) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static OnInviteConnection parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnInviteConnection) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static OnInviteConnection parseFrom(InputStream inputStream) throws IOException {
            return (OnInviteConnection) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static OnInviteConnection parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnInviteConnection) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static OnInviteConnection parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (OnInviteConnection) parseDelimitedFrom(c, inputStream);
        }

        public static OnInviteConnection parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnInviteConnection) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static OnInviteConnection parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (OnInviteConnection) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static OnInviteConnection parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnInviteConnection) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(OnInviteConnection onInviteConnection) {
            return c.createBuilder(onInviteConnection);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<OnInviteConnection, Builder> implements OnInviteConnectionOrBuilder {
            private Builder() {
                super(OnInviteConnection.c);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnInviteConnectionOrBuilder
            public int getCode() {
                return ((OnInviteConnection) this.instance).getCode();
            }

            public Builder setCode(int i) {
                copyOnWrite();
                ((OnInviteConnection) this.instance).b(i);
                return this;
            }

            public Builder clearCode() {
                copyOnWrite();
                ((OnInviteConnection) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnInviteConnectionOrBuilder
            public String getInviteStr() {
                return ((OnInviteConnection) this.instance).getInviteStr();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnInviteConnectionOrBuilder
            public ByteString getInviteStrBytes() {
                return ((OnInviteConnection) this.instance).getInviteStrBytes();
            }

            public Builder setInviteStr(String str) {
                copyOnWrite();
                ((OnInviteConnection) this.instance).a(str);
                return this;
            }

            public Builder clearInviteStr() {
                copyOnWrite();
                ((OnInviteConnection) this.instance).f();
                return this;
            }

            public Builder setInviteStrBytes(ByteString byteString) {
                copyOnWrite();
                ((OnInviteConnection) this.instance).a(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new OnInviteConnection();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0002Ȉ", new Object[]{"code_", "inviteStr_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<OnInviteConnection> parser = d;
                    if (parser == null) {
                        synchronized (OnInviteConnection.class) {
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
            OnInviteConnection onInviteConnection = new OnInviteConnection();
            c = onInviteConnection;
            GeneratedMessageLite.registerDefaultInstance(OnInviteConnection.class, onInviteConnection);
        }

        public static OnInviteConnection getDefaultInstance() {
            return c;
        }

        public static Parser<OnInviteConnection> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class OnInvitationAccepted extends GeneratedMessageLite<OnInvitationAccepted, Builder> implements OnInvitationAcceptedOrBuilder {
        public static final int IDMSERVICE_FIELD_NUMBER = 1;
        private static final OnInvitationAccepted b;
        private static volatile Parser<OnInvitationAccepted> c;
        private IDMServiceProto.IDMService a;

        private OnInvitationAccepted() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnInvitationAcceptedOrBuilder
        public boolean hasIdmService() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnInvitationAcceptedOrBuilder
        public IDMServiceProto.IDMService getIdmService() {
            IDMServiceProto.IDMService iDMService = this.a;
            return iDMService == null ? IDMServiceProto.IDMService.getDefaultInstance() : iDMService;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                this.a = iDMService;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                IDMServiceProto.IDMService iDMService2 = this.a;
                if (iDMService2 == null || iDMService2 == IDMServiceProto.IDMService.getDefaultInstance()) {
                    this.a = iDMService;
                } else {
                    this.a = IDMServiceProto.IDMService.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMService.Builder) iDMService).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static OnInvitationAccepted parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (OnInvitationAccepted) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static OnInvitationAccepted parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnInvitationAccepted) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static OnInvitationAccepted parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (OnInvitationAccepted) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static OnInvitationAccepted parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnInvitationAccepted) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static OnInvitationAccepted parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (OnInvitationAccepted) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static OnInvitationAccepted parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnInvitationAccepted) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static OnInvitationAccepted parseFrom(InputStream inputStream) throws IOException {
            return (OnInvitationAccepted) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static OnInvitationAccepted parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnInvitationAccepted) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnInvitationAccepted parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (OnInvitationAccepted) parseDelimitedFrom(b, inputStream);
        }

        public static OnInvitationAccepted parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnInvitationAccepted) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnInvitationAccepted parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (OnInvitationAccepted) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static OnInvitationAccepted parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnInvitationAccepted) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(OnInvitationAccepted onInvitationAccepted) {
            return b.createBuilder(onInvitationAccepted);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<OnInvitationAccepted, Builder> implements OnInvitationAcceptedOrBuilder {
            private Builder() {
                super(OnInvitationAccepted.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnInvitationAcceptedOrBuilder
            public boolean hasIdmService() {
                return ((OnInvitationAccepted) this.instance).hasIdmService();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnInvitationAcceptedOrBuilder
            public IDMServiceProto.IDMService getIdmService() {
                return ((OnInvitationAccepted) this.instance).getIdmService();
            }

            public Builder setIdmService(IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((OnInvitationAccepted) this.instance).a(iDMService);
                return this;
            }

            public Builder setIdmService(IDMServiceProto.IDMService.Builder builder) {
                copyOnWrite();
                ((OnInvitationAccepted) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmService(IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((OnInvitationAccepted) this.instance).b(iDMService);
                return this;
            }

            public Builder clearIdmService() {
                copyOnWrite();
                ((OnInvitationAccepted) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new OnInvitationAccepted();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"idmService_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<OnInvitationAccepted> parser = c;
                    if (parser == null) {
                        synchronized (OnInvitationAccepted.class) {
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
            OnInvitationAccepted onInvitationAccepted = new OnInvitationAccepted();
            b = onInvitationAccepted;
            GeneratedMessageLite.registerDefaultInstance(OnInvitationAccepted.class, onInvitationAccepted);
        }

        public static OnInvitationAccepted getDefaultInstance() {
            return b;
        }

        public static Parser<OnInvitationAccepted> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class OnDiscoveryResult extends GeneratedMessageLite<OnDiscoveryResult, Builder> implements OnDiscoveryResultOrBuilder {
        public static final int STATUS_FIELD_NUMBER = 1;
        private static final OnDiscoveryResult b;
        private static volatile Parser<OnDiscoveryResult> c;
        private int a;

        private OnDiscoveryResult() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnDiscoveryResultOrBuilder
        public int getStatus() {
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

        public static OnDiscoveryResult parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (OnDiscoveryResult) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static OnDiscoveryResult parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnDiscoveryResult) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static OnDiscoveryResult parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (OnDiscoveryResult) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static OnDiscoveryResult parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnDiscoveryResult) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static OnDiscoveryResult parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (OnDiscoveryResult) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static OnDiscoveryResult parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnDiscoveryResult) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static OnDiscoveryResult parseFrom(InputStream inputStream) throws IOException {
            return (OnDiscoveryResult) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static OnDiscoveryResult parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnDiscoveryResult) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnDiscoveryResult parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (OnDiscoveryResult) parseDelimitedFrom(b, inputStream);
        }

        public static OnDiscoveryResult parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnDiscoveryResult) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnDiscoveryResult parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (OnDiscoveryResult) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static OnDiscoveryResult parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnDiscoveryResult) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(OnDiscoveryResult onDiscoveryResult) {
            return b.createBuilder(onDiscoveryResult);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<OnDiscoveryResult, Builder> implements OnDiscoveryResultOrBuilder {
            private Builder() {
                super(OnDiscoveryResult.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnDiscoveryResultOrBuilder
            public int getStatus() {
                return ((OnDiscoveryResult) this.instance).getStatus();
            }

            public Builder setStatus(int i) {
                copyOnWrite();
                ((OnDiscoveryResult) this.instance).b(i);
                return this;
            }

            public Builder clearStatus() {
                copyOnWrite();
                ((OnDiscoveryResult) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new OnDiscoveryResult();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004", new Object[]{"status_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<OnDiscoveryResult> parser = c;
                    if (parser == null) {
                        synchronized (OnDiscoveryResult.class) {
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
            OnDiscoveryResult onDiscoveryResult = new OnDiscoveryResult();
            b = onDiscoveryResult;
            GeneratedMessageLite.registerDefaultInstance(OnDiscoveryResult.class, onDiscoveryResult);
        }

        public static OnDiscoveryResult getDefaultInstance() {
            return b;
        }

        public static Parser<OnDiscoveryResult> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class ClientOnAccountChanged extends GeneratedMessageLite<ClientOnAccountChanged, Builder> implements ClientOnAccountChangedOrBuilder {
        public static final int NEWIDHASH_FIELD_NUMBER = 1;
        public static final int SUBCHANGETYPE_FIELD_NUMBER = 2;
        private static final ClientOnAccountChanged c;
        private static volatile Parser<ClientOnAccountChanged> d;
        private String a = "";
        private int b;

        private ClientOnAccountChanged() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientOnAccountChangedOrBuilder
        public String getNewIdHash() {
            return this.a;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientOnAccountChangedOrBuilder
        public ByteString getNewIdHashBytes() {
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
            this.a = getDefaultInstance().getNewIdHash();
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientOnAccountChangedOrBuilder
        public int getSubChangeTypeValue() {
            return this.b;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientOnAccountChangedOrBuilder
        public IDMServiceProto.OnAccountChangeResult.SubChangeType getSubChangeType() {
            IDMServiceProto.OnAccountChangeResult.SubChangeType forNumber = IDMServiceProto.OnAccountChangeResult.SubChangeType.forNumber(this.b);
            return forNumber == null ? IDMServiceProto.OnAccountChangeResult.SubChangeType.UNRECOGNIZED : forNumber;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i) {
            this.b = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.OnAccountChangeResult.SubChangeType subChangeType) {
            if (subChangeType != null) {
                this.b = subChangeType.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = 0;
        }

        public static ClientOnAccountChanged parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ClientOnAccountChanged) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static ClientOnAccountChanged parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ClientOnAccountChanged) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static ClientOnAccountChanged parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ClientOnAccountChanged) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static ClientOnAccountChanged parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ClientOnAccountChanged) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static ClientOnAccountChanged parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ClientOnAccountChanged) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static ClientOnAccountChanged parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ClientOnAccountChanged) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static ClientOnAccountChanged parseFrom(InputStream inputStream) throws IOException {
            return (ClientOnAccountChanged) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static ClientOnAccountChanged parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ClientOnAccountChanged) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static ClientOnAccountChanged parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ClientOnAccountChanged) parseDelimitedFrom(c, inputStream);
        }

        public static ClientOnAccountChanged parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ClientOnAccountChanged) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static ClientOnAccountChanged parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ClientOnAccountChanged) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static ClientOnAccountChanged parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ClientOnAccountChanged) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(ClientOnAccountChanged clientOnAccountChanged) {
            return c.createBuilder(clientOnAccountChanged);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<ClientOnAccountChanged, Builder> implements ClientOnAccountChangedOrBuilder {
            private Builder() {
                super(ClientOnAccountChanged.c);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientOnAccountChangedOrBuilder
            public String getNewIdHash() {
                return ((ClientOnAccountChanged) this.instance).getNewIdHash();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientOnAccountChangedOrBuilder
            public ByteString getNewIdHashBytes() {
                return ((ClientOnAccountChanged) this.instance).getNewIdHashBytes();
            }

            public Builder setNewIdHash(String str) {
                copyOnWrite();
                ((ClientOnAccountChanged) this.instance).a(str);
                return this;
            }

            public Builder clearNewIdHash() {
                copyOnWrite();
                ((ClientOnAccountChanged) this.instance).e();
                return this;
            }

            public Builder setNewIdHashBytes(ByteString byteString) {
                copyOnWrite();
                ((ClientOnAccountChanged) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientOnAccountChangedOrBuilder
            public int getSubChangeTypeValue() {
                return ((ClientOnAccountChanged) this.instance).getSubChangeTypeValue();
            }

            public Builder setSubChangeTypeValue(int i) {
                copyOnWrite();
                ((ClientOnAccountChanged) this.instance).b(i);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ClientOnAccountChangedOrBuilder
            public IDMServiceProto.OnAccountChangeResult.SubChangeType getSubChangeType() {
                return ((ClientOnAccountChanged) this.instance).getSubChangeType();
            }

            public Builder setSubChangeType(IDMServiceProto.OnAccountChangeResult.SubChangeType subChangeType) {
                copyOnWrite();
                ((ClientOnAccountChanged) this.instance).a(subChangeType);
                return this;
            }

            public Builder clearSubChangeType() {
                copyOnWrite();
                ((ClientOnAccountChanged) this.instance).f();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new ClientOnAccountChanged();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\f", new Object[]{"newIdHash_", "subChangeType_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<ClientOnAccountChanged> parser = d;
                    if (parser == null) {
                        synchronized (ClientOnAccountChanged.class) {
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
            ClientOnAccountChanged clientOnAccountChanged = new ClientOnAccountChanged();
            c = clientOnAccountChanged;
            GeneratedMessageLite.registerDefaultInstance(ClientOnAccountChanged.class, clientOnAccountChanged);
        }

        public static ClientOnAccountChanged getDefaultInstance() {
            return c;
        }

        public static Parser<ClientOnAccountChanged> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class OnRequest extends GeneratedMessageLite<OnRequest, Builder> implements OnRequestOrBuilder {
        public static final int IDMREQUEST_FIELD_NUMBER = 1;
        private static final OnRequest b;
        private static volatile Parser<OnRequest> c;
        private IDMServiceProto.IDMRequest a;

        private OnRequest() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnRequestOrBuilder
        public boolean hasIdmRequest() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnRequestOrBuilder
        public IDMServiceProto.IDMRequest getIdmRequest() {
            IDMServiceProto.IDMRequest iDMRequest = this.a;
            return iDMRequest == null ? IDMServiceProto.IDMRequest.getDefaultInstance() : iDMRequest;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMRequest iDMRequest) {
            if (iDMRequest != null) {
                this.a = iDMRequest;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMRequest.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMRequest iDMRequest) {
            if (iDMRequest != null) {
                IDMServiceProto.IDMRequest iDMRequest2 = this.a;
                if (iDMRequest2 == null || iDMRequest2 == IDMServiceProto.IDMRequest.getDefaultInstance()) {
                    this.a = iDMRequest;
                } else {
                    this.a = IDMServiceProto.IDMRequest.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMRequest.Builder) iDMRequest).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static OnRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (OnRequest) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static OnRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnRequest) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static OnRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (OnRequest) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static OnRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnRequest) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static OnRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (OnRequest) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static OnRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnRequest) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static OnRequest parseFrom(InputStream inputStream) throws IOException {
            return (OnRequest) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static OnRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnRequest) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (OnRequest) parseDelimitedFrom(b, inputStream);
        }

        public static OnRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnRequest) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (OnRequest) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static OnRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnRequest) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(OnRequest onRequest) {
            return b.createBuilder(onRequest);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<OnRequest, Builder> implements OnRequestOrBuilder {
            private Builder() {
                super(OnRequest.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnRequestOrBuilder
            public boolean hasIdmRequest() {
                return ((OnRequest) this.instance).hasIdmRequest();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnRequestOrBuilder
            public IDMServiceProto.IDMRequest getIdmRequest() {
                return ((OnRequest) this.instance).getIdmRequest();
            }

            public Builder setIdmRequest(IDMServiceProto.IDMRequest iDMRequest) {
                copyOnWrite();
                ((OnRequest) this.instance).a(iDMRequest);
                return this;
            }

            public Builder setIdmRequest(IDMServiceProto.IDMRequest.Builder builder) {
                copyOnWrite();
                ((OnRequest) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmRequest(IDMServiceProto.IDMRequest iDMRequest) {
                copyOnWrite();
                ((OnRequest) this.instance).b(iDMRequest);
                return this;
            }

            public Builder clearIdmRequest() {
                copyOnWrite();
                ((OnRequest) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new OnRequest();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"idmRequest_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<OnRequest> parser = c;
                    if (parser == null) {
                        synchronized (OnRequest.class) {
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
            OnRequest onRequest = new OnRequest();
            b = onRequest;
            GeneratedMessageLite.registerDefaultInstance(OnRequest.class, onRequest);
        }

        public static OnRequest getDefaultInstance() {
            return b;
        }

        public static Parser<OnRequest> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class OnConnectServiceRequest extends GeneratedMessageLite<OnConnectServiceRequest, Builder> implements OnConnectServiceRequestOrBuilder {
        public static final int IDMCONNECTSERVICEREQUEST_FIELD_NUMBER = 1;
        private static final OnConnectServiceRequest b;
        private static volatile Parser<OnConnectServiceRequest> c;
        private IDMServiceProto.IDMConnectServiceRequest a;

        private OnConnectServiceRequest() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnConnectServiceRequestOrBuilder
        public boolean hasIdmConnectServiceRequest() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnConnectServiceRequestOrBuilder
        public IDMServiceProto.IDMConnectServiceRequest getIdmConnectServiceRequest() {
            IDMServiceProto.IDMConnectServiceRequest iDMConnectServiceRequest = this.a;
            return iDMConnectServiceRequest == null ? IDMServiceProto.IDMConnectServiceRequest.getDefaultInstance() : iDMConnectServiceRequest;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMConnectServiceRequest iDMConnectServiceRequest) {
            if (iDMConnectServiceRequest != null) {
                this.a = iDMConnectServiceRequest;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMConnectServiceRequest.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMConnectServiceRequest iDMConnectServiceRequest) {
            if (iDMConnectServiceRequest != null) {
                IDMServiceProto.IDMConnectServiceRequest iDMConnectServiceRequest2 = this.a;
                if (iDMConnectServiceRequest2 == null || iDMConnectServiceRequest2 == IDMServiceProto.IDMConnectServiceRequest.getDefaultInstance()) {
                    this.a = iDMConnectServiceRequest;
                } else {
                    this.a = IDMServiceProto.IDMConnectServiceRequest.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMConnectServiceRequest.Builder) iDMConnectServiceRequest).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static OnConnectServiceRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (OnConnectServiceRequest) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static OnConnectServiceRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnConnectServiceRequest) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static OnConnectServiceRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (OnConnectServiceRequest) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static OnConnectServiceRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnConnectServiceRequest) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static OnConnectServiceRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (OnConnectServiceRequest) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static OnConnectServiceRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnConnectServiceRequest) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static OnConnectServiceRequest parseFrom(InputStream inputStream) throws IOException {
            return (OnConnectServiceRequest) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static OnConnectServiceRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnConnectServiceRequest) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnConnectServiceRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (OnConnectServiceRequest) parseDelimitedFrom(b, inputStream);
        }

        public static OnConnectServiceRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnConnectServiceRequest) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnConnectServiceRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (OnConnectServiceRequest) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static OnConnectServiceRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnConnectServiceRequest) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(OnConnectServiceRequest onConnectServiceRequest) {
            return b.createBuilder(onConnectServiceRequest);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<OnConnectServiceRequest, Builder> implements OnConnectServiceRequestOrBuilder {
            private Builder() {
                super(OnConnectServiceRequest.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnConnectServiceRequestOrBuilder
            public boolean hasIdmConnectServiceRequest() {
                return ((OnConnectServiceRequest) this.instance).hasIdmConnectServiceRequest();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnConnectServiceRequestOrBuilder
            public IDMServiceProto.IDMConnectServiceRequest getIdmConnectServiceRequest() {
                return ((OnConnectServiceRequest) this.instance).getIdmConnectServiceRequest();
            }

            public Builder setIdmConnectServiceRequest(IDMServiceProto.IDMConnectServiceRequest iDMConnectServiceRequest) {
                copyOnWrite();
                ((OnConnectServiceRequest) this.instance).a(iDMConnectServiceRequest);
                return this;
            }

            public Builder setIdmConnectServiceRequest(IDMServiceProto.IDMConnectServiceRequest.Builder builder) {
                copyOnWrite();
                ((OnConnectServiceRequest) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmConnectServiceRequest(IDMServiceProto.IDMConnectServiceRequest iDMConnectServiceRequest) {
                copyOnWrite();
                ((OnConnectServiceRequest) this.instance).b(iDMConnectServiceRequest);
                return this;
            }

            public Builder clearIdmConnectServiceRequest() {
                copyOnWrite();
                ((OnConnectServiceRequest) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new OnConnectServiceRequest();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"idmConnectServiceRequest_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<OnConnectServiceRequest> parser = c;
                    if (parser == null) {
                        synchronized (OnConnectServiceRequest.class) {
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
            OnConnectServiceRequest onConnectServiceRequest = new OnConnectServiceRequest();
            b = onConnectServiceRequest;
            GeneratedMessageLite.registerDefaultInstance(OnConnectServiceRequest.class, onConnectServiceRequest);
        }

        public static OnConnectServiceRequest getDefaultInstance() {
            return b;
        }

        public static Parser<OnConnectServiceRequest> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class OnIDMAdvertisingResult extends GeneratedMessageLite<OnIDMAdvertisingResult, Builder> implements OnIDMAdvertisingResultOrBuilder {
        public static final int IDMADVERTISINGRESULT_FIELD_NUMBER = 1;
        private static final OnIDMAdvertisingResult b;
        private static volatile Parser<OnIDMAdvertisingResult> c;
        private IDMServiceProto.IDMAdvertisingResult a;

        private OnIDMAdvertisingResult() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnIDMAdvertisingResultOrBuilder
        public boolean hasIdmAdvertisingResult() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnIDMAdvertisingResultOrBuilder
        public IDMServiceProto.IDMAdvertisingResult getIdmAdvertisingResult() {
            IDMServiceProto.IDMAdvertisingResult iDMAdvertisingResult = this.a;
            return iDMAdvertisingResult == null ? IDMServiceProto.IDMAdvertisingResult.getDefaultInstance() : iDMAdvertisingResult;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMAdvertisingResult iDMAdvertisingResult) {
            if (iDMAdvertisingResult != null) {
                this.a = iDMAdvertisingResult;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMAdvertisingResult.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMAdvertisingResult iDMAdvertisingResult) {
            if (iDMAdvertisingResult != null) {
                IDMServiceProto.IDMAdvertisingResult iDMAdvertisingResult2 = this.a;
                if (iDMAdvertisingResult2 == null || iDMAdvertisingResult2 == IDMServiceProto.IDMAdvertisingResult.getDefaultInstance()) {
                    this.a = iDMAdvertisingResult;
                } else {
                    this.a = IDMServiceProto.IDMAdvertisingResult.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMAdvertisingResult.Builder) iDMAdvertisingResult).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static OnIDMAdvertisingResult parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (OnIDMAdvertisingResult) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static OnIDMAdvertisingResult parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnIDMAdvertisingResult) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static OnIDMAdvertisingResult parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (OnIDMAdvertisingResult) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static OnIDMAdvertisingResult parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnIDMAdvertisingResult) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static OnIDMAdvertisingResult parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (OnIDMAdvertisingResult) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static OnIDMAdvertisingResult parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnIDMAdvertisingResult) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static OnIDMAdvertisingResult parseFrom(InputStream inputStream) throws IOException {
            return (OnIDMAdvertisingResult) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static OnIDMAdvertisingResult parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnIDMAdvertisingResult) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnIDMAdvertisingResult parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (OnIDMAdvertisingResult) parseDelimitedFrom(b, inputStream);
        }

        public static OnIDMAdvertisingResult parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnIDMAdvertisingResult) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnIDMAdvertisingResult parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (OnIDMAdvertisingResult) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static OnIDMAdvertisingResult parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnIDMAdvertisingResult) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(OnIDMAdvertisingResult onIDMAdvertisingResult) {
            return b.createBuilder(onIDMAdvertisingResult);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<OnIDMAdvertisingResult, Builder> implements OnIDMAdvertisingResultOrBuilder {
            private Builder() {
                super(OnIDMAdvertisingResult.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnIDMAdvertisingResultOrBuilder
            public boolean hasIdmAdvertisingResult() {
                return ((OnIDMAdvertisingResult) this.instance).hasIdmAdvertisingResult();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnIDMAdvertisingResultOrBuilder
            public IDMServiceProto.IDMAdvertisingResult getIdmAdvertisingResult() {
                return ((OnIDMAdvertisingResult) this.instance).getIdmAdvertisingResult();
            }

            public Builder setIdmAdvertisingResult(IDMServiceProto.IDMAdvertisingResult iDMAdvertisingResult) {
                copyOnWrite();
                ((OnIDMAdvertisingResult) this.instance).a(iDMAdvertisingResult);
                return this;
            }

            public Builder setIdmAdvertisingResult(IDMServiceProto.IDMAdvertisingResult.Builder builder) {
                copyOnWrite();
                ((OnIDMAdvertisingResult) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmAdvertisingResult(IDMServiceProto.IDMAdvertisingResult iDMAdvertisingResult) {
                copyOnWrite();
                ((OnIDMAdvertisingResult) this.instance).b(iDMAdvertisingResult);
                return this;
            }

            public Builder clearIdmAdvertisingResult() {
                copyOnWrite();
                ((OnIDMAdvertisingResult) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new OnIDMAdvertisingResult();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"idmAdvertisingResult_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<OnIDMAdvertisingResult> parser = c;
                    if (parser == null) {
                        synchronized (OnIDMAdvertisingResult.class) {
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
            OnIDMAdvertisingResult onIDMAdvertisingResult = new OnIDMAdvertisingResult();
            b = onIDMAdvertisingResult;
            GeneratedMessageLite.registerDefaultInstance(OnIDMAdvertisingResult.class, onIDMAdvertisingResult);
        }

        public static OnIDMAdvertisingResult getDefaultInstance() {
            return b;
        }

        public static Parser<OnIDMAdvertisingResult> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class OnSetEventCallback extends GeneratedMessageLite<OnSetEventCallback, Builder> implements OnSetEventCallbackOrBuilder {
        public static final int IDMEVENT_FIELD_NUMBER = 1;
        private static final OnSetEventCallback b;
        private static volatile Parser<OnSetEventCallback> c;
        private IDMServiceProto.IDMEvent a;

        private OnSetEventCallback() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnSetEventCallbackOrBuilder
        public boolean hasIdmEvent() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnSetEventCallbackOrBuilder
        public IDMServiceProto.IDMEvent getIdmEvent() {
            IDMServiceProto.IDMEvent iDMEvent = this.a;
            return iDMEvent == null ? IDMServiceProto.IDMEvent.getDefaultInstance() : iDMEvent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMEvent iDMEvent) {
            if (iDMEvent != null) {
                this.a = iDMEvent;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMEvent.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMEvent iDMEvent) {
            if (iDMEvent != null) {
                IDMServiceProto.IDMEvent iDMEvent2 = this.a;
                if (iDMEvent2 == null || iDMEvent2 == IDMServiceProto.IDMEvent.getDefaultInstance()) {
                    this.a = iDMEvent;
                } else {
                    this.a = IDMServiceProto.IDMEvent.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMEvent.Builder) iDMEvent).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static OnSetEventCallback parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (OnSetEventCallback) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static OnSetEventCallback parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnSetEventCallback) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static OnSetEventCallback parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (OnSetEventCallback) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static OnSetEventCallback parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnSetEventCallback) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static OnSetEventCallback parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (OnSetEventCallback) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static OnSetEventCallback parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (OnSetEventCallback) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static OnSetEventCallback parseFrom(InputStream inputStream) throws IOException {
            return (OnSetEventCallback) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static OnSetEventCallback parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnSetEventCallback) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnSetEventCallback parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (OnSetEventCallback) parseDelimitedFrom(b, inputStream);
        }

        public static OnSetEventCallback parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnSetEventCallback) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static OnSetEventCallback parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (OnSetEventCallback) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static OnSetEventCallback parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (OnSetEventCallback) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(OnSetEventCallback onSetEventCallback) {
            return b.createBuilder(onSetEventCallback);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<OnSetEventCallback, Builder> implements OnSetEventCallbackOrBuilder {
            private Builder() {
                super(OnSetEventCallback.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnSetEventCallbackOrBuilder
            public boolean hasIdmEvent() {
                return ((OnSetEventCallback) this.instance).hasIdmEvent();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.OnSetEventCallbackOrBuilder
            public IDMServiceProto.IDMEvent getIdmEvent() {
                return ((OnSetEventCallback) this.instance).getIdmEvent();
            }

            public Builder setIdmEvent(IDMServiceProto.IDMEvent iDMEvent) {
                copyOnWrite();
                ((OnSetEventCallback) this.instance).a(iDMEvent);
                return this;
            }

            public Builder setIdmEvent(IDMServiceProto.IDMEvent.Builder builder) {
                copyOnWrite();
                ((OnSetEventCallback) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmEvent(IDMServiceProto.IDMEvent iDMEvent) {
                copyOnWrite();
                ((OnSetEventCallback) this.instance).b(iDMEvent);
                return this;
            }

            public Builder clearIdmEvent() {
                copyOnWrite();
                ((OnSetEventCallback) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new OnSetEventCallback();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"idmEvent_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<OnSetEventCallback> parser = c;
                    if (parser == null) {
                        synchronized (OnSetEventCallback.class) {
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
            OnSetEventCallback onSetEventCallback = new OnSetEventCallback();
            b = onSetEventCallback;
            GeneratedMessageLite.registerDefaultInstance(OnSetEventCallback.class, onSetEventCallback);
        }

        public static OnSetEventCallback getDefaultInstance() {
            return b;
        }

        public static Parser<OnSetEventCallback> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class ServiceOnServiceChanged extends GeneratedMessageLite<ServiceOnServiceChanged, Builder> implements ServiceOnServiceChangedOrBuilder {
        public static final int NEWSERVICEID_FIELD_NUMBER = 2;
        public static final int OLDSERVICEID_FIELD_NUMBER = 1;
        private static final ServiceOnServiceChanged c;
        private static volatile Parser<ServiceOnServiceChanged> d;
        private String a = "";
        private String b = "";

        private ServiceOnServiceChanged() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ServiceOnServiceChangedOrBuilder
        public String getOldServiceId() {
            return this.a;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ServiceOnServiceChangedOrBuilder
        public ByteString getOldServiceIdBytes() {
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
            this.a = getDefaultInstance().getOldServiceId();
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ServiceOnServiceChangedOrBuilder
        public String getNewServiceId() {
            return this.b;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ServiceOnServiceChangedOrBuilder
        public ByteString getNewServiceIdBytes() {
            return ByteString.copyFromUtf8(this.b);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(String str) {
            if (str != null) {
                this.b = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = getDefaultInstance().getNewServiceId();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static ServiceOnServiceChanged parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ServiceOnServiceChanged) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static ServiceOnServiceChanged parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ServiceOnServiceChanged) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static ServiceOnServiceChanged parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ServiceOnServiceChanged) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static ServiceOnServiceChanged parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ServiceOnServiceChanged) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static ServiceOnServiceChanged parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ServiceOnServiceChanged) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static ServiceOnServiceChanged parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ServiceOnServiceChanged) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static ServiceOnServiceChanged parseFrom(InputStream inputStream) throws IOException {
            return (ServiceOnServiceChanged) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static ServiceOnServiceChanged parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ServiceOnServiceChanged) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static ServiceOnServiceChanged parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ServiceOnServiceChanged) parseDelimitedFrom(c, inputStream);
        }

        public static ServiceOnServiceChanged parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ServiceOnServiceChanged) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static ServiceOnServiceChanged parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ServiceOnServiceChanged) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static ServiceOnServiceChanged parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ServiceOnServiceChanged) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(ServiceOnServiceChanged serviceOnServiceChanged) {
            return c.createBuilder(serviceOnServiceChanged);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<ServiceOnServiceChanged, Builder> implements ServiceOnServiceChangedOrBuilder {
            private Builder() {
                super(ServiceOnServiceChanged.c);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ServiceOnServiceChangedOrBuilder
            public String getOldServiceId() {
                return ((ServiceOnServiceChanged) this.instance).getOldServiceId();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ServiceOnServiceChangedOrBuilder
            public ByteString getOldServiceIdBytes() {
                return ((ServiceOnServiceChanged) this.instance).getOldServiceIdBytes();
            }

            public Builder setOldServiceId(String str) {
                copyOnWrite();
                ((ServiceOnServiceChanged) this.instance).a(str);
                return this;
            }

            public Builder clearOldServiceId() {
                copyOnWrite();
                ((ServiceOnServiceChanged) this.instance).e();
                return this;
            }

            public Builder setOldServiceIdBytes(ByteString byteString) {
                copyOnWrite();
                ((ServiceOnServiceChanged) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ServiceOnServiceChangedOrBuilder
            public String getNewServiceId() {
                return ((ServiceOnServiceChanged) this.instance).getNewServiceId();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.ServiceOnServiceChangedOrBuilder
            public ByteString getNewServiceIdBytes() {
                return ((ServiceOnServiceChanged) this.instance).getNewServiceIdBytes();
            }

            public Builder setNewServiceId(String str) {
                copyOnWrite();
                ((ServiceOnServiceChanged) this.instance).b(str);
                return this;
            }

            public Builder clearNewServiceId() {
                copyOnWrite();
                ((ServiceOnServiceChanged) this.instance).f();
                return this;
            }

            public Builder setNewServiceIdBytes(ByteString byteString) {
                copyOnWrite();
                ((ServiceOnServiceChanged) this.instance).b(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new ServiceOnServiceChanged();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ", new Object[]{"oldServiceId_", "newServiceId_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<ServiceOnServiceChanged> parser = d;
                    if (parser == null) {
                        synchronized (ServiceOnServiceChanged.class) {
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
            ServiceOnServiceChanged serviceOnServiceChanged = new ServiceOnServiceChanged();
            c = serviceOnServiceChanged;
            GeneratedMessageLite.registerDefaultInstance(ServiceOnServiceChanged.class, serviceOnServiceChanged);
        }

        public static ServiceOnServiceChanged getDefaultInstance() {
            return c;
        }

        public static Parser<ServiceOnServiceChanged> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IDMScanServicesRequest extends GeneratedMessageLite<IDMScanServicesRequest, Builder> implements IDMScanServicesRequestOrBuilder {
        public static final int CLIENTID_FIELD_NUMBER = 1;
        public static final int SERVICEFILTER_FIELD_NUMBER = 2;
        private static final IDMScanServicesRequest c;
        private static volatile Parser<IDMScanServicesRequest> d;
        private String a = "";
        private StartDiscovery b;

        private IDMScanServicesRequest() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesRequestOrBuilder
        public String getClientId() {
            return this.a;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesRequestOrBuilder
        public ByteString getClientIdBytes() {
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
            this.a = getDefaultInstance().getClientId();
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesRequestOrBuilder
        public boolean hasServiceFilter() {
            return this.b != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesRequestOrBuilder
        public StartDiscovery getServiceFilter() {
            StartDiscovery startDiscovery = this.b;
            return startDiscovery == null ? StartDiscovery.getDefaultInstance() : startDiscovery;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(StartDiscovery startDiscovery) {
            if (startDiscovery != null) {
                this.b = startDiscovery;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(StartDiscovery.Builder builder) {
            this.b = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(StartDiscovery startDiscovery) {
            if (startDiscovery != null) {
                StartDiscovery startDiscovery2 = this.b;
                if (startDiscovery2 == null || startDiscovery2 == StartDiscovery.getDefaultInstance()) {
                    this.b = startDiscovery;
                } else {
                    this.b = StartDiscovery.newBuilder(this.b).mergeFrom((StartDiscovery.Builder) startDiscovery).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = null;
        }

        public static IDMScanServicesRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMScanServicesRequest) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static IDMScanServicesRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMScanServicesRequest) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static IDMScanServicesRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMScanServicesRequest) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static IDMScanServicesRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMScanServicesRequest) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static IDMScanServicesRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMScanServicesRequest) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static IDMScanServicesRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMScanServicesRequest) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static IDMScanServicesRequest parseFrom(InputStream inputStream) throws IOException {
            return (IDMScanServicesRequest) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static IDMScanServicesRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMScanServicesRequest) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static IDMScanServicesRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMScanServicesRequest) parseDelimitedFrom(c, inputStream);
        }

        public static IDMScanServicesRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMScanServicesRequest) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static IDMScanServicesRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMScanServicesRequest) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static IDMScanServicesRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMScanServicesRequest) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(IDMScanServicesRequest iDMScanServicesRequest) {
            return c.createBuilder(iDMScanServicesRequest);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMScanServicesRequest, Builder> implements IDMScanServicesRequestOrBuilder {
            private Builder() {
                super(IDMScanServicesRequest.c);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesRequestOrBuilder
            public String getClientId() {
                return ((IDMScanServicesRequest) this.instance).getClientId();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesRequestOrBuilder
            public ByteString getClientIdBytes() {
                return ((IDMScanServicesRequest) this.instance).getClientIdBytes();
            }

            public Builder setClientId(String str) {
                copyOnWrite();
                ((IDMScanServicesRequest) this.instance).a(str);
                return this;
            }

            public Builder clearClientId() {
                copyOnWrite();
                ((IDMScanServicesRequest) this.instance).e();
                return this;
            }

            public Builder setClientIdBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMScanServicesRequest) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesRequestOrBuilder
            public boolean hasServiceFilter() {
                return ((IDMScanServicesRequest) this.instance).hasServiceFilter();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesRequestOrBuilder
            public StartDiscovery getServiceFilter() {
                return ((IDMScanServicesRequest) this.instance).getServiceFilter();
            }

            public Builder setServiceFilter(StartDiscovery startDiscovery) {
                copyOnWrite();
                ((IDMScanServicesRequest) this.instance).a(startDiscovery);
                return this;
            }

            public Builder setServiceFilter(StartDiscovery.Builder builder) {
                copyOnWrite();
                ((IDMScanServicesRequest) this.instance).a(builder);
                return this;
            }

            public Builder mergeServiceFilter(StartDiscovery startDiscovery) {
                copyOnWrite();
                ((IDMScanServicesRequest) this.instance).b(startDiscovery);
                return this;
            }

            public Builder clearServiceFilter() {
                copyOnWrite();
                ((IDMScanServicesRequest) this.instance).f();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMScanServicesRequest();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\t", new Object[]{"clientId_", "serviceFilter_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<IDMScanServicesRequest> parser = d;
                    if (parser == null) {
                        synchronized (IDMScanServicesRequest.class) {
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
            IDMScanServicesRequest iDMScanServicesRequest = new IDMScanServicesRequest();
            c = iDMScanServicesRequest;
            GeneratedMessageLite.registerDefaultInstance(IDMScanServicesRequest.class, iDMScanServicesRequest);
        }

        public static IDMScanServicesRequest getDefaultInstance() {
            return c;
        }

        public static Parser<IDMScanServicesRequest> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IDMScanServicesResponse extends GeneratedMessageLite<IDMScanServicesResponse, Builder> implements IDMScanServicesResponseOrBuilder {
        public static final int CLIENTID_FIELD_NUMBER = 1;
        public static final int SERVICE_FIELD_NUMBER = 2;
        private static final IDMScanServicesResponse c;
        private static volatile Parser<IDMScanServicesResponse> d;
        private String a = "";
        private Internal.ProtobufList<IDMServiceProto.IDMService> b = emptyProtobufList();

        private IDMScanServicesResponse() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesResponseOrBuilder
        public String getClientId() {
            return this.a;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesResponseOrBuilder
        public ByteString getClientIdBytes() {
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
            this.a = getDefaultInstance().getClientId();
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesResponseOrBuilder
        public List<IDMServiceProto.IDMService> getServiceList() {
            return this.b;
        }

        public List<? extends IDMServiceProto.IDMServiceOrBuilder> getServiceOrBuilderList() {
            return this.b;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesResponseOrBuilder
        public int getServiceCount() {
            return this.b.size();
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesResponseOrBuilder
        public IDMServiceProto.IDMService getService(int i) {
            return this.b.get(i);
        }

        public IDMServiceProto.IDMServiceOrBuilder getServiceOrBuilder(int i) {
            return this.b.get(i);
        }

        private void f() {
            if (!this.b.isModifiable()) {
                this.b = GeneratedMessageLite.mutableCopy(this.b);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i, IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                f();
                this.b.set(i, iDMService);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i, IDMServiceProto.IDMService.Builder builder) {
            f();
            this.b.set(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                f();
                this.b.add(iDMService);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i, IDMServiceProto.IDMService iDMService) {
            if (iDMService != null) {
                f();
                this.b.add(i, iDMService);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMService.Builder builder) {
            f();
            this.b.add(builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i, IDMServiceProto.IDMService.Builder builder) {
            f();
            this.b.add(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Iterable<? extends IDMServiceProto.IDMService> iterable) {
            f();
            AbstractMessageLite.addAll((Iterable) iterable, (List) this.b);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g() {
            this.b = emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i) {
            f();
            this.b.remove(i);
        }

        public static IDMScanServicesResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMScanServicesResponse) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static IDMScanServicesResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMScanServicesResponse) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static IDMScanServicesResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMScanServicesResponse) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static IDMScanServicesResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMScanServicesResponse) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static IDMScanServicesResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMScanServicesResponse) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static IDMScanServicesResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMScanServicesResponse) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static IDMScanServicesResponse parseFrom(InputStream inputStream) throws IOException {
            return (IDMScanServicesResponse) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static IDMScanServicesResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMScanServicesResponse) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static IDMScanServicesResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMScanServicesResponse) parseDelimitedFrom(c, inputStream);
        }

        public static IDMScanServicesResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMScanServicesResponse) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static IDMScanServicesResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMScanServicesResponse) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static IDMScanServicesResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMScanServicesResponse) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(IDMScanServicesResponse iDMScanServicesResponse) {
            return c.createBuilder(iDMScanServicesResponse);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMScanServicesResponse, Builder> implements IDMScanServicesResponseOrBuilder {
            private Builder() {
                super(IDMScanServicesResponse.c);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesResponseOrBuilder
            public String getClientId() {
                return ((IDMScanServicesResponse) this.instance).getClientId();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesResponseOrBuilder
            public ByteString getClientIdBytes() {
                return ((IDMScanServicesResponse) this.instance).getClientIdBytes();
            }

            public Builder setClientId(String str) {
                copyOnWrite();
                ((IDMScanServicesResponse) this.instance).a(str);
                return this;
            }

            public Builder clearClientId() {
                copyOnWrite();
                ((IDMScanServicesResponse) this.instance).e();
                return this;
            }

            public Builder setClientIdBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMScanServicesResponse) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesResponseOrBuilder
            public List<IDMServiceProto.IDMService> getServiceList() {
                return Collections.unmodifiableList(((IDMScanServicesResponse) this.instance).getServiceList());
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesResponseOrBuilder
            public int getServiceCount() {
                return ((IDMScanServicesResponse) this.instance).getServiceCount();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMScanServicesResponseOrBuilder
            public IDMServiceProto.IDMService getService(int i) {
                return ((IDMScanServicesResponse) this.instance).getService(i);
            }

            public Builder setService(int i, IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((IDMScanServicesResponse) this.instance).a(i, iDMService);
                return this;
            }

            public Builder setService(int i, IDMServiceProto.IDMService.Builder builder) {
                copyOnWrite();
                ((IDMScanServicesResponse) this.instance).a(i, builder);
                return this;
            }

            public Builder addService(IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((IDMScanServicesResponse) this.instance).a(iDMService);
                return this;
            }

            public Builder addService(int i, IDMServiceProto.IDMService iDMService) {
                copyOnWrite();
                ((IDMScanServicesResponse) this.instance).b(i, iDMService);
                return this;
            }

            public Builder addService(IDMServiceProto.IDMService.Builder builder) {
                copyOnWrite();
                ((IDMScanServicesResponse) this.instance).a(builder);
                return this;
            }

            public Builder addService(int i, IDMServiceProto.IDMService.Builder builder) {
                copyOnWrite();
                ((IDMScanServicesResponse) this.instance).b(i, builder);
                return this;
            }

            public Builder addAllService(Iterable<? extends IDMServiceProto.IDMService> iterable) {
                copyOnWrite();
                ((IDMScanServicesResponse) this.instance).a(iterable);
                return this;
            }

            public Builder clearService() {
                copyOnWrite();
                ((IDMScanServicesResponse) this.instance).g();
                return this;
            }

            public Builder removeService(int i) {
                copyOnWrite();
                ((IDMScanServicesResponse) this.instance).b(i);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMScanServicesResponse();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0001\u0000\u0001Ȉ\u0002\u001b", new Object[]{"clientId_", "service_", IDMServiceProto.IDMService.class});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<IDMScanServicesResponse> parser = d;
                    if (parser == null) {
                        synchronized (IDMScanServicesResponse.class) {
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
            IDMScanServicesResponse iDMScanServicesResponse = new IDMScanServicesResponse();
            c = iDMScanServicesResponse;
            GeneratedMessageLite.registerDefaultInstance(IDMScanServicesResponse.class, iDMScanServicesResponse);
        }

        public static IDMScanServicesResponse getDefaultInstance() {
            return c;
        }

        public static Parser<IDMScanServicesResponse> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IDMActionRequest extends GeneratedMessageLite<IDMActionRequest, Builder> implements IDMActionRequestOrBuilder {
        public static final int IDMREQUEST_FIELD_NUMBER = 1;
        private static final IDMActionRequest b;
        private static volatile Parser<IDMActionRequest> c;
        private IDMServiceProto.IDMRequest a;

        private IDMActionRequest() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMActionRequestOrBuilder
        public boolean hasIdmRequest() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMActionRequestOrBuilder
        public IDMServiceProto.IDMRequest getIdmRequest() {
            IDMServiceProto.IDMRequest iDMRequest = this.a;
            return iDMRequest == null ? IDMServiceProto.IDMRequest.getDefaultInstance() : iDMRequest;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMRequest iDMRequest) {
            if (iDMRequest != null) {
                this.a = iDMRequest;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMRequest.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMRequest iDMRequest) {
            if (iDMRequest != null) {
                IDMServiceProto.IDMRequest iDMRequest2 = this.a;
                if (iDMRequest2 == null || iDMRequest2 == IDMServiceProto.IDMRequest.getDefaultInstance()) {
                    this.a = iDMRequest;
                } else {
                    this.a = IDMServiceProto.IDMRequest.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMRequest.Builder) iDMRequest).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static IDMActionRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMActionRequest) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static IDMActionRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMActionRequest) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static IDMActionRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMActionRequest) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static IDMActionRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMActionRequest) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static IDMActionRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMActionRequest) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static IDMActionRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMActionRequest) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static IDMActionRequest parseFrom(InputStream inputStream) throws IOException {
            return (IDMActionRequest) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static IDMActionRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMActionRequest) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static IDMActionRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMActionRequest) parseDelimitedFrom(b, inputStream);
        }

        public static IDMActionRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMActionRequest) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static IDMActionRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMActionRequest) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static IDMActionRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMActionRequest) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(IDMActionRequest iDMActionRequest) {
            return b.createBuilder(iDMActionRequest);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMActionRequest, Builder> implements IDMActionRequestOrBuilder {
            private Builder() {
                super(IDMActionRequest.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMActionRequestOrBuilder
            public boolean hasIdmRequest() {
                return ((IDMActionRequest) this.instance).hasIdmRequest();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMActionRequestOrBuilder
            public IDMServiceProto.IDMRequest getIdmRequest() {
                return ((IDMActionRequest) this.instance).getIdmRequest();
            }

            public Builder setIdmRequest(IDMServiceProto.IDMRequest iDMRequest) {
                copyOnWrite();
                ((IDMActionRequest) this.instance).a(iDMRequest);
                return this;
            }

            public Builder setIdmRequest(IDMServiceProto.IDMRequest.Builder builder) {
                copyOnWrite();
                ((IDMActionRequest) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmRequest(IDMServiceProto.IDMRequest iDMRequest) {
                copyOnWrite();
                ((IDMActionRequest) this.instance).b(iDMRequest);
                return this;
            }

            public Builder clearIdmRequest() {
                copyOnWrite();
                ((IDMActionRequest) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMActionRequest();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"idmRequest_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<IDMActionRequest> parser = c;
                    if (parser == null) {
                        synchronized (IDMActionRequest.class) {
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
            IDMActionRequest iDMActionRequest = new IDMActionRequest();
            b = iDMActionRequest;
            GeneratedMessageLite.registerDefaultInstance(IDMActionRequest.class, iDMActionRequest);
        }

        public static IDMActionRequest getDefaultInstance() {
            return b;
        }

        public static Parser<IDMActionRequest> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IDMActionResponse extends GeneratedMessageLite<IDMActionResponse, Builder> implements IDMActionResponseOrBuilder {
        public static final int IDMRESPONSE_FIELD_NUMBER = 1;
        private static final IDMActionResponse b;
        private static volatile Parser<IDMActionResponse> c;
        private IDMServiceProto.IDMResponse a;

        private IDMActionResponse() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMActionResponseOrBuilder
        public boolean hasIdmResponse() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMActionResponseOrBuilder
        public IDMServiceProto.IDMResponse getIdmResponse() {
            IDMServiceProto.IDMResponse iDMResponse = this.a;
            return iDMResponse == null ? IDMServiceProto.IDMResponse.getDefaultInstance() : iDMResponse;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMResponse iDMResponse) {
            if (iDMResponse != null) {
                this.a = iDMResponse;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMResponse.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMResponse iDMResponse) {
            if (iDMResponse != null) {
                IDMServiceProto.IDMResponse iDMResponse2 = this.a;
                if (iDMResponse2 == null || iDMResponse2 == IDMServiceProto.IDMResponse.getDefaultInstance()) {
                    this.a = iDMResponse;
                } else {
                    this.a = IDMServiceProto.IDMResponse.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMResponse.Builder) iDMResponse).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static IDMActionResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMActionResponse) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static IDMActionResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMActionResponse) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static IDMActionResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMActionResponse) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static IDMActionResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMActionResponse) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static IDMActionResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMActionResponse) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static IDMActionResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMActionResponse) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static IDMActionResponse parseFrom(InputStream inputStream) throws IOException {
            return (IDMActionResponse) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static IDMActionResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMActionResponse) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static IDMActionResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMActionResponse) parseDelimitedFrom(b, inputStream);
        }

        public static IDMActionResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMActionResponse) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static IDMActionResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMActionResponse) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static IDMActionResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMActionResponse) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(IDMActionResponse iDMActionResponse) {
            return b.createBuilder(iDMActionResponse);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMActionResponse, Builder> implements IDMActionResponseOrBuilder {
            private Builder() {
                super(IDMActionResponse.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMActionResponseOrBuilder
            public boolean hasIdmResponse() {
                return ((IDMActionResponse) this.instance).hasIdmResponse();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMActionResponseOrBuilder
            public IDMServiceProto.IDMResponse getIdmResponse() {
                return ((IDMActionResponse) this.instance).getIdmResponse();
            }

            public Builder setIdmResponse(IDMServiceProto.IDMResponse iDMResponse) {
                copyOnWrite();
                ((IDMActionResponse) this.instance).a(iDMResponse);
                return this;
            }

            public Builder setIdmResponse(IDMServiceProto.IDMResponse.Builder builder) {
                copyOnWrite();
                ((IDMActionResponse) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmResponse(IDMServiceProto.IDMResponse iDMResponse) {
                copyOnWrite();
                ((IDMActionResponse) this.instance).b(iDMResponse);
                return this;
            }

            public Builder clearIdmResponse() {
                copyOnWrite();
                ((IDMActionResponse) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMActionResponse();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"idmResponse_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<IDMActionResponse> parser = c;
                    if (parser == null) {
                        synchronized (IDMActionResponse.class) {
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
            IDMActionResponse iDMActionResponse = new IDMActionResponse();
            b = iDMActionResponse;
            GeneratedMessageLite.registerDefaultInstance(IDMActionResponse.class, iDMActionResponse);
        }

        public static IDMActionResponse getDefaultInstance() {
            return b;
        }

        public static Parser<IDMActionResponse> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IDMEnableEventRequest extends GeneratedMessageLite<IDMEnableEventRequest, Builder> implements IDMEnableEventRequestOrBuilder {
        public static final int EID_FIELD_NUMBER = 3;
        public static final int ENABLE_FIELD_NUMBER = 4;
        public static final int SERVICEUUID_FIELD_NUMBER = 1;
        private static final IDMEnableEventRequest d;
        private static volatile Parser<IDMEnableEventRequest> e;
        private String a = "";
        private int b;
        private boolean c;

        private IDMEnableEventRequest() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMEnableEventRequestOrBuilder
        public String getServiceUuid() {
            return this.a;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMEnableEventRequestOrBuilder
        public ByteString getServiceUuidBytes() {
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
            this.a = getDefaultInstance().getServiceUuid();
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMEnableEventRequestOrBuilder
        public int getEid() {
            return this.b;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i) {
            this.b = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = 0;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMEnableEventRequestOrBuilder
        public boolean getEnable() {
            return this.c;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(boolean z) {
            this.c = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g() {
            this.c = false;
        }

        public static IDMEnableEventRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMEnableEventRequest) GeneratedMessageLite.parseFrom(d, byteBuffer);
        }

        public static IDMEnableEventRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMEnableEventRequest) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
        }

        public static IDMEnableEventRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMEnableEventRequest) GeneratedMessageLite.parseFrom(d, byteString);
        }

        public static IDMEnableEventRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMEnableEventRequest) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
        }

        public static IDMEnableEventRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMEnableEventRequest) GeneratedMessageLite.parseFrom(d, bArr);
        }

        public static IDMEnableEventRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMEnableEventRequest) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
        }

        public static IDMEnableEventRequest parseFrom(InputStream inputStream) throws IOException {
            return (IDMEnableEventRequest) GeneratedMessageLite.parseFrom(d, inputStream);
        }

        public static IDMEnableEventRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMEnableEventRequest) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
        }

        public static IDMEnableEventRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMEnableEventRequest) parseDelimitedFrom(d, inputStream);
        }

        public static IDMEnableEventRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMEnableEventRequest) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
        }

        public static IDMEnableEventRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMEnableEventRequest) GeneratedMessageLite.parseFrom(d, codedInputStream);
        }

        public static IDMEnableEventRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMEnableEventRequest) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return d.createBuilder();
        }

        public static Builder newBuilder(IDMEnableEventRequest iDMEnableEventRequest) {
            return d.createBuilder(iDMEnableEventRequest);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMEnableEventRequest, Builder> implements IDMEnableEventRequestOrBuilder {
            private Builder() {
                super(IDMEnableEventRequest.d);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMEnableEventRequestOrBuilder
            public String getServiceUuid() {
                return ((IDMEnableEventRequest) this.instance).getServiceUuid();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMEnableEventRequestOrBuilder
            public ByteString getServiceUuidBytes() {
                return ((IDMEnableEventRequest) this.instance).getServiceUuidBytes();
            }

            public Builder setServiceUuid(String str) {
                copyOnWrite();
                ((IDMEnableEventRequest) this.instance).a(str);
                return this;
            }

            public Builder clearServiceUuid() {
                copyOnWrite();
                ((IDMEnableEventRequest) this.instance).e();
                return this;
            }

            public Builder setServiceUuidBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMEnableEventRequest) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMEnableEventRequestOrBuilder
            public int getEid() {
                return ((IDMEnableEventRequest) this.instance).getEid();
            }

            public Builder setEid(int i) {
                copyOnWrite();
                ((IDMEnableEventRequest) this.instance).b(i);
                return this;
            }

            public Builder clearEid() {
                copyOnWrite();
                ((IDMEnableEventRequest) this.instance).f();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMEnableEventRequestOrBuilder
            public boolean getEnable() {
                return ((IDMEnableEventRequest) this.instance).getEnable();
            }

            public Builder setEnable(boolean z) {
                copyOnWrite();
                ((IDMEnableEventRequest) this.instance).a(z);
                return this;
            }

            public Builder clearEnable() {
                copyOnWrite();
                ((IDMEnableEventRequest) this.instance).g();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMEnableEventRequest();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0004\u0003\u0000\u0000\u0000\u0001Ȉ\u0003\u0004\u0004\u0007", new Object[]{"serviceUuid_", "eid_", "enable_"});
                case GET_DEFAULT_INSTANCE:
                    return d;
                case GET_PARSER:
                    Parser<IDMEnableEventRequest> parser = e;
                    if (parser == null) {
                        synchronized (IDMEnableEventRequest.class) {
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
            IDMEnableEventRequest iDMEnableEventRequest = new IDMEnableEventRequest();
            d = iDMEnableEventRequest;
            GeneratedMessageLite.registerDefaultInstance(IDMEnableEventRequest.class, iDMEnableEventRequest);
        }

        public static IDMEnableEventRequest getDefaultInstance() {
            return d;
        }

        public static Parser<IDMEnableEventRequest> parser() {
            return d.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IDMEnableEventResponse extends GeneratedMessageLite<IDMEnableEventResponse, Builder> implements IDMEnableEventResponseOrBuilder {
        public static final int SUCCESS_FIELD_NUMBER = 1;
        private static final IDMEnableEventResponse b;
        private static volatile Parser<IDMEnableEventResponse> c;
        private boolean a;

        private IDMEnableEventResponse() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMEnableEventResponseOrBuilder
        public boolean getSuccess() {
            return this.a;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(boolean z) {
            this.a = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = false;
        }

        public static IDMEnableEventResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMEnableEventResponse) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static IDMEnableEventResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMEnableEventResponse) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static IDMEnableEventResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMEnableEventResponse) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static IDMEnableEventResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMEnableEventResponse) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static IDMEnableEventResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMEnableEventResponse) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static IDMEnableEventResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMEnableEventResponse) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static IDMEnableEventResponse parseFrom(InputStream inputStream) throws IOException {
            return (IDMEnableEventResponse) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static IDMEnableEventResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMEnableEventResponse) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static IDMEnableEventResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMEnableEventResponse) parseDelimitedFrom(b, inputStream);
        }

        public static IDMEnableEventResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMEnableEventResponse) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static IDMEnableEventResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMEnableEventResponse) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static IDMEnableEventResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMEnableEventResponse) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(IDMEnableEventResponse iDMEnableEventResponse) {
            return b.createBuilder(iDMEnableEventResponse);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMEnableEventResponse, Builder> implements IDMEnableEventResponseOrBuilder {
            private Builder() {
                super(IDMEnableEventResponse.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMEnableEventResponseOrBuilder
            public boolean getSuccess() {
                return ((IDMEnableEventResponse) this.instance).getSuccess();
            }

            public Builder setSuccess(boolean z) {
                copyOnWrite();
                ((IDMEnableEventResponse) this.instance).a(z);
                return this;
            }

            public Builder clearSuccess() {
                copyOnWrite();
                ((IDMEnableEventResponse) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMEnableEventResponse();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0007", new Object[]{"success_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<IDMEnableEventResponse> parser = c;
                    if (parser == null) {
                        synchronized (IDMEnableEventResponse.class) {
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
            IDMEnableEventResponse iDMEnableEventResponse = new IDMEnableEventResponse();
            b = iDMEnableEventResponse;
            GeneratedMessageLite.registerDefaultInstance(IDMEnableEventResponse.class, iDMEnableEventResponse);
        }

        public static IDMEnableEventResponse getDefaultInstance() {
            return b;
        }

        public static Parser<IDMEnableEventResponse> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IDMNotifyEvent extends GeneratedMessageLite<IDMNotifyEvent, Builder> implements IDMNotifyEventOrBuilder {
        public static final int IDMEVENT_FIELD_NUMBER = 2;
        private static final IDMNotifyEvent b;
        private static volatile Parser<IDMNotifyEvent> c;
        private IDMServiceProto.IDMEvent a;

        private IDMNotifyEvent() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMNotifyEventOrBuilder
        public boolean hasIdmEvent() {
            return this.a != null;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMNotifyEventOrBuilder
        public IDMServiceProto.IDMEvent getIdmEvent() {
            IDMServiceProto.IDMEvent iDMEvent = this.a;
            return iDMEvent == null ? IDMServiceProto.IDMEvent.getDefaultInstance() : iDMEvent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMEvent iDMEvent) {
            if (iDMEvent != null) {
                this.a = iDMEvent;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(IDMServiceProto.IDMEvent.Builder builder) {
            this.a = builder.build();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IDMServiceProto.IDMEvent iDMEvent) {
            if (iDMEvent != null) {
                IDMServiceProto.IDMEvent iDMEvent2 = this.a;
                if (iDMEvent2 == null || iDMEvent2 == IDMServiceProto.IDMEvent.getDefaultInstance()) {
                    this.a = iDMEvent;
                } else {
                    this.a = IDMServiceProto.IDMEvent.newBuilder(this.a).mergeFrom((IDMServiceProto.IDMEvent.Builder) iDMEvent).buildPartial();
                }
            } else {
                throw new NullPointerException();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = null;
        }

        public static IDMNotifyEvent parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMNotifyEvent) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static IDMNotifyEvent parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMNotifyEvent) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static IDMNotifyEvent parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMNotifyEvent) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static IDMNotifyEvent parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMNotifyEvent) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static IDMNotifyEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMNotifyEvent) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static IDMNotifyEvent parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMNotifyEvent) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static IDMNotifyEvent parseFrom(InputStream inputStream) throws IOException {
            return (IDMNotifyEvent) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static IDMNotifyEvent parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMNotifyEvent) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static IDMNotifyEvent parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMNotifyEvent) parseDelimitedFrom(b, inputStream);
        }

        public static IDMNotifyEvent parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMNotifyEvent) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static IDMNotifyEvent parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMNotifyEvent) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static IDMNotifyEvent parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMNotifyEvent) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(IDMNotifyEvent iDMNotifyEvent) {
            return b.createBuilder(iDMNotifyEvent);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMNotifyEvent, Builder> implements IDMNotifyEventOrBuilder {
            private Builder() {
                super(IDMNotifyEvent.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMNotifyEventOrBuilder
            public boolean hasIdmEvent() {
                return ((IDMNotifyEvent) this.instance).hasIdmEvent();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMNotifyEventOrBuilder
            public IDMServiceProto.IDMEvent getIdmEvent() {
                return ((IDMNotifyEvent) this.instance).getIdmEvent();
            }

            public Builder setIdmEvent(IDMServiceProto.IDMEvent iDMEvent) {
                copyOnWrite();
                ((IDMNotifyEvent) this.instance).a(iDMEvent);
                return this;
            }

            public Builder setIdmEvent(IDMServiceProto.IDMEvent.Builder builder) {
                copyOnWrite();
                ((IDMNotifyEvent) this.instance).a(builder);
                return this;
            }

            public Builder mergeIdmEvent(IDMServiceProto.IDMEvent iDMEvent) {
                copyOnWrite();
                ((IDMNotifyEvent) this.instance).b(iDMEvent);
                return this;
            }

            public Builder clearIdmEvent() {
                copyOnWrite();
                ((IDMNotifyEvent) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMNotifyEvent();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0002\u0002\u0001\u0000\u0000\u0000\u0002\t", new Object[]{"idmEvent_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<IDMNotifyEvent> parser = c;
                    if (parser == null) {
                        synchronized (IDMNotifyEvent.class) {
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
            IDMNotifyEvent iDMNotifyEvent = new IDMNotifyEvent();
            b = iDMNotifyEvent;
            GeneratedMessageLite.registerDefaultInstance(IDMNotifyEvent.class, iDMNotifyEvent);
        }

        public static IDMNotifyEvent getDefaultInstance() {
            return b;
        }

        public static Parser<IDMNotifyEvent> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IDMNotifyHandoffEvent extends GeneratedMessageLite<IDMNotifyHandoffEvent, Builder> implements IDMNotifyHandoffEventOrBuilder {
        public static final int KEY_FIELD_NUMBER = 1;
        public static final int VALUE_FIELD_NUMBER = 2;
        private static final IDMNotifyHandoffEvent c;
        private static volatile Parser<IDMNotifyHandoffEvent> d;
        private String a = "";
        private ByteString b = ByteString.EMPTY;

        private IDMNotifyHandoffEvent() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMNotifyHandoffEventOrBuilder
        public String getKey() {
            return this.a;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMNotifyHandoffEventOrBuilder
        public ByteString getKeyBytes() {
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
            this.a = getDefaultInstance().getKey();
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

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMNotifyHandoffEventOrBuilder
        public ByteString getValue() {
            return this.b;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(ByteString byteString) {
            if (byteString != null) {
                this.b = byteString;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = getDefaultInstance().getValue();
        }

        public static IDMNotifyHandoffEvent parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMNotifyHandoffEvent) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static IDMNotifyHandoffEvent parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMNotifyHandoffEvent) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static IDMNotifyHandoffEvent parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMNotifyHandoffEvent) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static IDMNotifyHandoffEvent parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMNotifyHandoffEvent) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static IDMNotifyHandoffEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMNotifyHandoffEvent) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static IDMNotifyHandoffEvent parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMNotifyHandoffEvent) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static IDMNotifyHandoffEvent parseFrom(InputStream inputStream) throws IOException {
            return (IDMNotifyHandoffEvent) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static IDMNotifyHandoffEvent parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMNotifyHandoffEvent) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static IDMNotifyHandoffEvent parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMNotifyHandoffEvent) parseDelimitedFrom(c, inputStream);
        }

        public static IDMNotifyHandoffEvent parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMNotifyHandoffEvent) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static IDMNotifyHandoffEvent parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMNotifyHandoffEvent) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static IDMNotifyHandoffEvent parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMNotifyHandoffEvent) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(IDMNotifyHandoffEvent iDMNotifyHandoffEvent) {
            return c.createBuilder(iDMNotifyHandoffEvent);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMNotifyHandoffEvent, Builder> implements IDMNotifyHandoffEventOrBuilder {
            private Builder() {
                super(IDMNotifyHandoffEvent.c);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMNotifyHandoffEventOrBuilder
            public String getKey() {
                return ((IDMNotifyHandoffEvent) this.instance).getKey();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMNotifyHandoffEventOrBuilder
            public ByteString getKeyBytes() {
                return ((IDMNotifyHandoffEvent) this.instance).getKeyBytes();
            }

            public Builder setKey(String str) {
                copyOnWrite();
                ((IDMNotifyHandoffEvent) this.instance).a(str);
                return this;
            }

            public Builder clearKey() {
                copyOnWrite();
                ((IDMNotifyHandoffEvent) this.instance).e();
                return this;
            }

            public Builder setKeyBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMNotifyHandoffEvent) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMNotifyHandoffEventOrBuilder
            public ByteString getValue() {
                return ((IDMNotifyHandoffEvent) this.instance).getValue();
            }

            public Builder setValue(ByteString byteString) {
                copyOnWrite();
                ((IDMNotifyHandoffEvent) this.instance).b(byteString);
                return this;
            }

            public Builder clearValue() {
                copyOnWrite();
                ((IDMNotifyHandoffEvent) this.instance).f();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMNotifyHandoffEvent();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\n", new Object[]{"key_", "value_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<IDMNotifyHandoffEvent> parser = d;
                    if (parser == null) {
                        synchronized (IDMNotifyHandoffEvent.class) {
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
            IDMNotifyHandoffEvent iDMNotifyHandoffEvent = new IDMNotifyHandoffEvent();
            c = iDMNotifyHandoffEvent;
            GeneratedMessageLite.registerDefaultInstance(IDMNotifyHandoffEvent.class, iDMNotifyHandoffEvent);
        }

        public static IDMNotifyHandoffEvent getDefaultInstance() {
            return c;
        }

        public static Parser<IDMNotifyHandoffEvent> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class IDMSetClipBoardMessage extends GeneratedMessageLite<IDMSetClipBoardMessage, Builder> implements IDMSetClipBoardMessageOrBuilder {
        public static final int MSG_FIELD_NUMBER = 1;
        private static final IDMSetClipBoardMessage b;
        private static volatile Parser<IDMSetClipBoardMessage> c;
        private String a = "";

        private IDMSetClipBoardMessage() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMSetClipBoardMessageOrBuilder
        public String getMsg() {
            return this.a;
        }

        @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMSetClipBoardMessageOrBuilder
        public ByteString getMsgBytes() {
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
            this.a = getDefaultInstance().getMsg();
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

        public static IDMSetClipBoardMessage parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (IDMSetClipBoardMessage) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static IDMSetClipBoardMessage parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMSetClipBoardMessage) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static IDMSetClipBoardMessage parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (IDMSetClipBoardMessage) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static IDMSetClipBoardMessage parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMSetClipBoardMessage) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static IDMSetClipBoardMessage parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (IDMSetClipBoardMessage) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static IDMSetClipBoardMessage parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (IDMSetClipBoardMessage) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static IDMSetClipBoardMessage parseFrom(InputStream inputStream) throws IOException {
            return (IDMSetClipBoardMessage) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static IDMSetClipBoardMessage parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMSetClipBoardMessage) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static IDMSetClipBoardMessage parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (IDMSetClipBoardMessage) parseDelimitedFrom(b, inputStream);
        }

        public static IDMSetClipBoardMessage parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMSetClipBoardMessage) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static IDMSetClipBoardMessage parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (IDMSetClipBoardMessage) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static IDMSetClipBoardMessage parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (IDMSetClipBoardMessage) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(IDMSetClipBoardMessage iDMSetClipBoardMessage) {
            return b.createBuilder(iDMSetClipBoardMessage);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<IDMSetClipBoardMessage, Builder> implements IDMSetClipBoardMessageOrBuilder {
            private Builder() {
                super(IDMSetClipBoardMessage.b);
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMSetClipBoardMessageOrBuilder
            public String getMsg() {
                return ((IDMSetClipBoardMessage) this.instance).getMsg();
            }

            @Override // com.xiaomi.mi_connect_service.proto.IPCParam.IDMSetClipBoardMessageOrBuilder
            public ByteString getMsgBytes() {
                return ((IDMSetClipBoardMessage) this.instance).getMsgBytes();
            }

            public Builder setMsg(String str) {
                copyOnWrite();
                ((IDMSetClipBoardMessage) this.instance).a(str);
                return this;
            }

            public Builder clearMsg() {
                copyOnWrite();
                ((IDMSetClipBoardMessage) this.instance).e();
                return this;
            }

            public Builder setMsgBytes(ByteString byteString) {
                copyOnWrite();
                ((IDMSetClipBoardMessage) this.instance).a(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new IDMSetClipBoardMessage();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ", new Object[]{"msg_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<IDMSetClipBoardMessage> parser = c;
                    if (parser == null) {
                        synchronized (IDMSetClipBoardMessage.class) {
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
            IDMSetClipBoardMessage iDMSetClipBoardMessage = new IDMSetClipBoardMessage();
            b = iDMSetClipBoardMessage;
            GeneratedMessageLite.registerDefaultInstance(IDMSetClipBoardMessage.class, iDMSetClipBoardMessage);
        }

        public static IDMSetClipBoardMessage getDefaultInstance() {
            return b;
        }

        public static Parser<IDMSetClipBoardMessage> parser() {
            return b.getParserForType();
        }
    }
}
