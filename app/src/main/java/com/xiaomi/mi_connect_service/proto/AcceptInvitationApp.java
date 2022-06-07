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
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class AcceptInvitationApp {

    /* loaded from: classes3.dex */
    public interface AcceptInvitationAppsOrBuilder extends MessageLiteOrBuilder {
        App getApps(int i);

        int getAppsCount();

        List<App> getAppsList();

        ByteString getBtAddr();

        int getDeviceType();

        ByteString getIdHash();

        int getMcVersion();

        String getName();

        ByteString getNameBytes();

        int getPbVersion();

        SecurityMode getSecurityMode();

        int getSecurityModeValue();

        ByteString getSwVersion();
    }

    /* loaded from: classes3.dex */
    public interface AppOrBuilder extends MessageLiteOrBuilder {
        ByteString getAdvData();

        int getAppId();
    }

    /* loaded from: classes3.dex */
    public interface ConnectionQRCodeOrBuilder extends MessageLiteOrBuilder {
        App getApps(int i);

        int getAppsCount();

        List<App> getAppsList();

        ByteString getBtAddr();

        int getChannel();

        int getConnType();

        int getDeviceType();

        String getIdHash();

        ByteString getIdHashBytes();

        String getMacAddr();

        ByteString getMacAddrBytes();

        int getMcVersion();

        String getName();

        ByteString getNameBytes();

        int getPbVersion();

        String getPwd();

        ByteString getPwdBytes();

        RoleType getRole();

        int getRoleValue();

        SecurityMode getSecurityMode();

        int getSecurityModeValue();

        String getSsid();

        ByteString getSsidBytes();

        ByteString getSwVersion();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private AcceptInvitationApp() {
    }

    /* loaded from: classes3.dex */
    public enum RoleType implements Internal.EnumLite {
        MC_ROLE_TYPE_INVALID(0),
        MC_ROLE_TYPE_SERVER(1),
        MC_ROLE_TYPE_CLIENT(2),
        UNRECOGNIZED(-1);
        
        public static final int MC_ROLE_TYPE_CLIENT_VALUE = 2;
        public static final int MC_ROLE_TYPE_INVALID_VALUE = 0;
        public static final int MC_ROLE_TYPE_SERVER_VALUE = 1;
        private static final Internal.EnumLiteMap<RoleType> a = new Internal.EnumLiteMap<RoleType>() { // from class: com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.RoleType.1
            /* renamed from: a */
            public RoleType findValueByNumber(int i) {
                return RoleType.forNumber(i);
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
        public static RoleType valueOf(int i) {
            return forNumber(i);
        }

        public static RoleType forNumber(int i) {
            switch (i) {
                case 0:
                    return MC_ROLE_TYPE_INVALID;
                case 1:
                    return MC_ROLE_TYPE_SERVER;
                case 2:
                    return MC_ROLE_TYPE_CLIENT;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<RoleType> internalGetValueMap() {
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
                return RoleType.forNumber(i) != null;
            }
        }

        RoleType(int i) {
            this.value = i;
        }
    }

    /* loaded from: classes3.dex */
    public enum SecurityMode implements Internal.EnumLite {
        MC_MI_SEC_NONE(0),
        MC_MI_SEC_COMM(1),
        MC_MI_SEC_TRANS(2),
        MC_MI_SEC_COMM_TRANS(3),
        UNRECOGNIZED(-1);
        
        public static final int MC_MI_SEC_COMM_TRANS_VALUE = 3;
        public static final int MC_MI_SEC_COMM_VALUE = 1;
        public static final int MC_MI_SEC_NONE_VALUE = 0;
        public static final int MC_MI_SEC_TRANS_VALUE = 2;
        private static final Internal.EnumLiteMap<SecurityMode> a = new Internal.EnumLiteMap<SecurityMode>() { // from class: com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.SecurityMode.1
            /* renamed from: a */
            public SecurityMode findValueByNumber(int i) {
                return SecurityMode.forNumber(i);
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
        public static SecurityMode valueOf(int i) {
            return forNumber(i);
        }

        public static SecurityMode forNumber(int i) {
            switch (i) {
                case 0:
                    return MC_MI_SEC_NONE;
                case 1:
                    return MC_MI_SEC_COMM;
                case 2:
                    return MC_MI_SEC_TRANS;
                case 3:
                    return MC_MI_SEC_COMM_TRANS;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<SecurityMode> internalGetValueMap() {
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
                return SecurityMode.forNumber(i) != null;
            }
        }

        SecurityMode(int i) {
            this.value = i;
        }
    }

    /* loaded from: classes3.dex */
    public static final class App extends GeneratedMessageLite<App, Builder> implements AppOrBuilder {
        public static final int ADV_DATA_FIELD_NUMBER = 2;
        public static final int APP_ID_FIELD_NUMBER = 1;
        private static final App c;
        private static volatile Parser<App> d;
        private int a;
        private ByteString b = ByteString.EMPTY;

        private App() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AppOrBuilder
        public int getAppId() {
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

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AppOrBuilder
        public ByteString getAdvData() {
            return this.b;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(ByteString byteString) {
            if (byteString != null) {
                this.b = byteString;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = getDefaultInstance().getAdvData();
        }

        public static App parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (App) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static App parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (App) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static App parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (App) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static App parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (App) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static App parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (App) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static App parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (App) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static App parseFrom(InputStream inputStream) throws IOException {
            return (App) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static App parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (App) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static App parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (App) parseDelimitedFrom(c, inputStream);
        }

        public static App parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (App) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static App parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (App) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static App parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (App) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(App app2) {
            return c.createBuilder(app2);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<App, Builder> implements AppOrBuilder {
            private Builder() {
                super(App.c);
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AppOrBuilder
            public int getAppId() {
                return ((App) this.instance).getAppId();
            }

            public Builder setAppId(int i) {
                copyOnWrite();
                ((App) this.instance).b(i);
                return this;
            }

            public Builder clearAppId() {
                copyOnWrite();
                ((App) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AppOrBuilder
            public ByteString getAdvData() {
                return ((App) this.instance).getAdvData();
            }

            public Builder setAdvData(ByteString byteString) {
                copyOnWrite();
                ((App) this.instance).a(byteString);
                return this;
            }

            public Builder clearAdvData() {
                copyOnWrite();
                ((App) this.instance).f();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new App();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u000b\u0002\n", new Object[]{"appId_", "advData_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<App> parser = d;
                    if (parser == null) {
                        synchronized (App.class) {
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
            App app2 = new App();
            c = app2;
            GeneratedMessageLite.registerDefaultInstance(App.class, app2);
        }

        public static App getDefaultInstance() {
            return c;
        }

        public static Parser<App> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class AcceptInvitationApps extends GeneratedMessageLite<AcceptInvitationApps, Builder> implements AcceptInvitationAppsOrBuilder {
        public static final int APPS_FIELD_NUMBER = 8;
        public static final int BT_ADDR_FIELD_NUMBER = 9;
        public static final int DEVICE_TYPE_FIELD_NUMBER = 6;
        public static final int ID_HASH_FIELD_NUMBER = 5;
        public static final int MC_VERSION_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 4;
        public static final int PB_VERSION_FIELD_NUMBER = 1;
        public static final int SECURITY_MODE_FIELD_NUMBER = 7;
        public static final int SW_VERSION_FIELD_NUMBER = 3;
        private static final AcceptInvitationApps j;
        private static volatile Parser<AcceptInvitationApps> k;
        private int a;
        private int b;
        private int f;
        private int g;
        private ByteString c = ByteString.EMPTY;
        private String d = "";
        private ByteString e = ByteString.EMPTY;
        private Internal.ProtobufList<App> h = emptyProtobufList();
        private ByteString i = ByteString.EMPTY;

        private AcceptInvitationApps() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
        public int getPbVersion() {
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

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
        public int getMcVersion() {
            return this.b;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(int i) {
            this.b = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = 0;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
        public ByteString getSwVersion() {
            return this.c;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(ByteString byteString) {
            if (byteString != null) {
                this.c = byteString;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g() {
            this.c = getDefaultInstance().getSwVersion();
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
        public String getName() {
            return this.d;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(String str) {
            if (str != null) {
                this.d = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.d = getDefaultInstance().getName();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.d = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
        public ByteString getIdHash() {
            return this.e;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(ByteString byteString) {
            if (byteString != null) {
                this.e = byteString;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void i() {
            this.e = getDefaultInstance().getIdHash();
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
        public int getDeviceType() {
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

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
        public int getSecurityModeValue() {
            return this.g;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
        public SecurityMode getSecurityMode() {
            SecurityMode forNumber = SecurityMode.forNumber(this.g);
            return forNumber == null ? SecurityMode.UNRECOGNIZED : forNumber;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e(int i) {
            this.g = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(SecurityMode securityMode) {
            if (securityMode != null) {
                this.g = securityMode.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void k() {
            this.g = 0;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
        public List<App> getAppsList() {
            return this.h;
        }

        public List<? extends AppOrBuilder> getAppsOrBuilderList() {
            return this.h;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
        public int getAppsCount() {
            return this.h.size();
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
        public App getApps(int i) {
            return this.h.get(i);
        }

        public AppOrBuilder getAppsOrBuilder(int i) {
            return this.h.get(i);
        }

        private void l() {
            if (!this.h.isModifiable()) {
                this.h = GeneratedMessageLite.mutableCopy(this.h);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i, App app2) {
            if (app2 != null) {
                l();
                this.h.set(i, app2);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i, App.Builder builder) {
            l();
            this.h.set(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(App app2) {
            if (app2 != null) {
                l();
                this.h.add(app2);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i, App app2) {
            if (app2 != null) {
                l();
                this.h.add(i, app2);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(App.Builder builder) {
            l();
            this.h.add(builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i, App.Builder builder) {
            l();
            this.h.add(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Iterable<? extends App> iterable) {
            l();
            AbstractMessageLite.addAll((Iterable) iterable, (List) this.h);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void m() {
            this.h = emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f(int i) {
            l();
            this.h.remove(i);
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
        public ByteString getBtAddr() {
            return this.i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d(ByteString byteString) {
            if (byteString != null) {
                this.i = byteString;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void n() {
            this.i = getDefaultInstance().getBtAddr();
        }

        public static AcceptInvitationApps parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (AcceptInvitationApps) GeneratedMessageLite.parseFrom(j, byteBuffer);
        }

        public static AcceptInvitationApps parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AcceptInvitationApps) GeneratedMessageLite.parseFrom(j, byteBuffer, extensionRegistryLite);
        }

        public static AcceptInvitationApps parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (AcceptInvitationApps) GeneratedMessageLite.parseFrom(j, byteString);
        }

        public static AcceptInvitationApps parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AcceptInvitationApps) GeneratedMessageLite.parseFrom(j, byteString, extensionRegistryLite);
        }

        public static AcceptInvitationApps parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (AcceptInvitationApps) GeneratedMessageLite.parseFrom(j, bArr);
        }

        public static AcceptInvitationApps parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AcceptInvitationApps) GeneratedMessageLite.parseFrom(j, bArr, extensionRegistryLite);
        }

        public static AcceptInvitationApps parseFrom(InputStream inputStream) throws IOException {
            return (AcceptInvitationApps) GeneratedMessageLite.parseFrom(j, inputStream);
        }

        public static AcceptInvitationApps parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AcceptInvitationApps) GeneratedMessageLite.parseFrom(j, inputStream, extensionRegistryLite);
        }

        public static AcceptInvitationApps parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (AcceptInvitationApps) parseDelimitedFrom(j, inputStream);
        }

        public static AcceptInvitationApps parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AcceptInvitationApps) parseDelimitedFrom(j, inputStream, extensionRegistryLite);
        }

        public static AcceptInvitationApps parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (AcceptInvitationApps) GeneratedMessageLite.parseFrom(j, codedInputStream);
        }

        public static AcceptInvitationApps parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AcceptInvitationApps) GeneratedMessageLite.parseFrom(j, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return j.createBuilder();
        }

        public static Builder newBuilder(AcceptInvitationApps acceptInvitationApps) {
            return j.createBuilder(acceptInvitationApps);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<AcceptInvitationApps, Builder> implements AcceptInvitationAppsOrBuilder {
            private Builder() {
                super(AcceptInvitationApps.j);
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
            public int getPbVersion() {
                return ((AcceptInvitationApps) this.instance).getPbVersion();
            }

            public Builder setPbVersion(int i) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).b(i);
                return this;
            }

            public Builder clearPbVersion() {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
            public int getMcVersion() {
                return ((AcceptInvitationApps) this.instance).getMcVersion();
            }

            public Builder setMcVersion(int i) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).c(i);
                return this;
            }

            public Builder clearMcVersion() {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).f();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
            public ByteString getSwVersion() {
                return ((AcceptInvitationApps) this.instance).getSwVersion();
            }

            public Builder setSwVersion(ByteString byteString) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).a(byteString);
                return this;
            }

            public Builder clearSwVersion() {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).g();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
            public String getName() {
                return ((AcceptInvitationApps) this.instance).getName();
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
            public ByteString getNameBytes() {
                return ((AcceptInvitationApps) this.instance).getNameBytes();
            }

            public Builder setName(String str) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).a(str);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).h();
                return this;
            }

            public Builder setNameBytes(ByteString byteString) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
            public ByteString getIdHash() {
                return ((AcceptInvitationApps) this.instance).getIdHash();
            }

            public Builder setIdHash(ByteString byteString) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).c(byteString);
                return this;
            }

            public Builder clearIdHash() {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).i();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
            public int getDeviceType() {
                return ((AcceptInvitationApps) this.instance).getDeviceType();
            }

            public Builder setDeviceType(int i) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).d(i);
                return this;
            }

            public Builder clearDeviceType() {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).j();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
            public int getSecurityModeValue() {
                return ((AcceptInvitationApps) this.instance).getSecurityModeValue();
            }

            public Builder setSecurityModeValue(int i) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).e(i);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
            public SecurityMode getSecurityMode() {
                return ((AcceptInvitationApps) this.instance).getSecurityMode();
            }

            public Builder setSecurityMode(SecurityMode securityMode) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).a(securityMode);
                return this;
            }

            public Builder clearSecurityMode() {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).k();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
            public List<App> getAppsList() {
                return Collections.unmodifiableList(((AcceptInvitationApps) this.instance).getAppsList());
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
            public int getAppsCount() {
                return ((AcceptInvitationApps) this.instance).getAppsCount();
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
            public App getApps(int i) {
                return ((AcceptInvitationApps) this.instance).getApps(i);
            }

            public Builder setApps(int i, App app2) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).a(i, app2);
                return this;
            }

            public Builder setApps(int i, App.Builder builder) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).a(i, builder);
                return this;
            }

            public Builder addApps(App app2) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).a(app2);
                return this;
            }

            public Builder addApps(int i, App app2) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).b(i, app2);
                return this;
            }

            public Builder addApps(App.Builder builder) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).a(builder);
                return this;
            }

            public Builder addApps(int i, App.Builder builder) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).b(i, builder);
                return this;
            }

            public Builder addAllApps(Iterable<? extends App> iterable) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).a(iterable);
                return this;
            }

            public Builder clearApps() {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).m();
                return this;
            }

            public Builder removeApps(int i) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).f(i);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.AcceptInvitationAppsOrBuilder
            public ByteString getBtAddr() {
                return ((AcceptInvitationApps) this.instance).getBtAddr();
            }

            public Builder setBtAddr(ByteString byteString) {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).d(byteString);
                return this;
            }

            public Builder clearBtAddr() {
                copyOnWrite();
                ((AcceptInvitationApps) this.instance).n();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new AcceptInvitationApps();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(j, "\u0000\t\u0000\u0000\u0001\t\t\u0000\u0001\u0000\u0001\u000b\u0002\u000b\u0003\n\u0004Ȉ\u0005\n\u0006\u000b\u0007\f\b\u001b\t\n", new Object[]{"pbVersion_", "mcVersion_", "swVersion_", "name_", "idHash_", "deviceType_", "securityMode_", "apps_", App.class, "btAddr_"});
                case GET_DEFAULT_INSTANCE:
                    return j;
                case GET_PARSER:
                    Parser<AcceptInvitationApps> parser = k;
                    if (parser == null) {
                        synchronized (AcceptInvitationApps.class) {
                            parser = k;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(j);
                                k = parser;
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
            AcceptInvitationApps acceptInvitationApps = new AcceptInvitationApps();
            j = acceptInvitationApps;
            GeneratedMessageLite.registerDefaultInstance(AcceptInvitationApps.class, acceptInvitationApps);
        }

        public static AcceptInvitationApps getDefaultInstance() {
            return j;
        }

        public static Parser<AcceptInvitationApps> parser() {
            return j.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class ConnectionQRCode extends GeneratedMessageLite<ConnectionQRCode, Builder> implements ConnectionQRCodeOrBuilder {
        public static final int APPS_FIELD_NUMBER = 13;
        public static final int BT_ADDR_FIELD_NUMBER = 8;
        public static final int CHANNEL_FIELD_NUMBER = 2;
        public static final int CONN_TYPE_FIELD_NUMBER = 1;
        public static final int DEVICE_TYPE_FIELD_NUMBER = 14;
        public static final int ID_HASH_FIELD_NUMBER = 6;
        public static final int MAC_ADDR_FIELD_NUMBER = 3;
        public static final int MC_VERSION_FIELD_NUMBER = 10;
        public static final int NAME_FIELD_NUMBER = 7;
        public static final int PB_VERSION_FIELD_NUMBER = 9;
        public static final int PWD_FIELD_NUMBER = 4;
        public static final int ROLE_FIELD_NUMBER = 12;
        public static final int SECURITY_MODE_FIELD_NUMBER = 15;
        public static final int SSID_FIELD_NUMBER = 5;
        public static final int SW_VERSION_FIELD_NUMBER = 11;
        private static final ConnectionQRCode p;
        private static volatile Parser<ConnectionQRCode> q;
        private int a;
        private int b;
        private int i;
        private int j;
        private int l;
        private int n;
        private int o;
        private String c = "";
        private String d = "";
        private String e = "";
        private String f = "";
        private String g = "";
        private ByteString h = ByteString.EMPTY;
        private ByteString k = ByteString.EMPTY;
        private Internal.ProtobufList<App> m = emptyProtobufList();

        private ConnectionQRCode() {
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public int getConnType() {
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

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public int getChannel() {
            return this.b;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(int i) {
            this.b = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = 0;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public String getMacAddr() {
            return this.c;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public ByteString getMacAddrBytes() {
            return ByteString.copyFromUtf8(this.c);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(String str) {
            if (str != null) {
                this.c = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g() {
            this.c = getDefaultInstance().getMacAddr();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.c = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public String getPwd() {
            return this.d;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public ByteString getPwdBytes() {
            return ByteString.copyFromUtf8(this.d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(String str) {
            if (str != null) {
                this.d = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.d = getDefaultInstance().getPwd();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.d = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public String getSsid() {
            return this.e;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public ByteString getSsidBytes() {
            return ByteString.copyFromUtf8(this.e);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(String str) {
            if (str != null) {
                this.e = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void i() {
            this.e = getDefaultInstance().getSsid();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.e = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public String getIdHash() {
            return this.f;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public ByteString getIdHashBytes() {
            return ByteString.copyFromUtf8(this.f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d(String str) {
            if (str != null) {
                this.f = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void j() {
            this.f = getDefaultInstance().getIdHash();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.f = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public String getName() {
            return this.g;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.g);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e(String str) {
            if (str != null) {
                this.g = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void k() {
            this.g = getDefaultInstance().getName();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.g = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public ByteString getBtAddr() {
            return this.h;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f(ByteString byteString) {
            if (byteString != null) {
                this.h = byteString;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void l() {
            this.h = getDefaultInstance().getBtAddr();
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public int getPbVersion() {
            return this.i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d(int i) {
            this.i = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void m() {
            this.i = 0;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public int getMcVersion() {
            return this.j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e(int i) {
            this.j = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void n() {
            this.j = 0;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public ByteString getSwVersion() {
            return this.k;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g(ByteString byteString) {
            if (byteString != null) {
                this.k = byteString;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void o() {
            this.k = getDefaultInstance().getSwVersion();
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public int getRoleValue() {
            return this.l;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public RoleType getRole() {
            RoleType forNumber = RoleType.forNumber(this.l);
            return forNumber == null ? RoleType.UNRECOGNIZED : forNumber;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f(int i) {
            this.l = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(RoleType roleType) {
            if (roleType != null) {
                this.l = roleType.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void p() {
            this.l = 0;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public List<App> getAppsList() {
            return this.m;
        }

        public List<? extends AppOrBuilder> getAppsOrBuilderList() {
            return this.m;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public int getAppsCount() {
            return this.m.size();
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public App getApps(int i) {
            return this.m.get(i);
        }

        public AppOrBuilder getAppsOrBuilder(int i) {
            return this.m.get(i);
        }

        private void q() {
            if (!this.m.isModifiable()) {
                this.m = GeneratedMessageLite.mutableCopy(this.m);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i, App app2) {
            if (app2 != null) {
                q();
                this.m.set(i, app2);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i, App.Builder builder) {
            q();
            this.m.set(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(App app2) {
            if (app2 != null) {
                q();
                this.m.add(app2);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i, App app2) {
            if (app2 != null) {
                q();
                this.m.add(i, app2);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(App.Builder builder) {
            q();
            this.m.add(builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i, App.Builder builder) {
            q();
            this.m.add(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Iterable<? extends App> iterable) {
            q();
            AbstractMessageLite.addAll((Iterable) iterable, (List) this.m);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void r() {
            this.m = emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g(int i) {
            q();
            this.m.remove(i);
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public int getDeviceType() {
            return this.n;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h(int i) {
            this.n = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void s() {
            this.n = 0;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public int getSecurityModeValue() {
            return this.o;
        }

        @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
        public SecurityMode getSecurityMode() {
            SecurityMode forNumber = SecurityMode.forNumber(this.o);
            return forNumber == null ? SecurityMode.UNRECOGNIZED : forNumber;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void i(int i) {
            this.o = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(SecurityMode securityMode) {
            if (securityMode != null) {
                this.o = securityMode.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void t() {
            this.o = 0;
        }

        public static ConnectionQRCode parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(p, byteBuffer);
        }

        public static ConnectionQRCode parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(p, byteBuffer, extensionRegistryLite);
        }

        public static ConnectionQRCode parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(p, byteString);
        }

        public static ConnectionQRCode parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(p, byteString, extensionRegistryLite);
        }

        public static ConnectionQRCode parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(p, bArr);
        }

        public static ConnectionQRCode parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(p, bArr, extensionRegistryLite);
        }

        public static ConnectionQRCode parseFrom(InputStream inputStream) throws IOException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(p, inputStream);
        }

        public static ConnectionQRCode parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(p, inputStream, extensionRegistryLite);
        }

        public static ConnectionQRCode parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ConnectionQRCode) parseDelimitedFrom(p, inputStream);
        }

        public static ConnectionQRCode parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnectionQRCode) parseDelimitedFrom(p, inputStream, extensionRegistryLite);
        }

        public static ConnectionQRCode parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(p, codedInputStream);
        }

        public static ConnectionQRCode parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnectionQRCode) GeneratedMessageLite.parseFrom(p, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return p.createBuilder();
        }

        public static Builder newBuilder(ConnectionQRCode connectionQRCode) {
            return p.createBuilder(connectionQRCode);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<ConnectionQRCode, Builder> implements ConnectionQRCodeOrBuilder {
            private Builder() {
                super(ConnectionQRCode.p);
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
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

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
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

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public String getMacAddr() {
                return ((ConnectionQRCode) this.instance).getMacAddr();
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
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

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public String getPwd() {
                return ((ConnectionQRCode) this.instance).getPwd();
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
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

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public String getSsid() {
                return ((ConnectionQRCode) this.instance).getSsid();
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
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

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public String getIdHash() {
                return ((ConnectionQRCode) this.instance).getIdHash();
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
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

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public String getName() {
                return ((ConnectionQRCode) this.instance).getName();
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public ByteString getNameBytes() {
                return ((ConnectionQRCode) this.instance).getNameBytes();
            }

            public Builder setName(String str) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).e(str);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).k();
                return this;
            }

            public Builder setNameBytes(ByteString byteString) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).e(byteString);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public ByteString getBtAddr() {
                return ((ConnectionQRCode) this.instance).getBtAddr();
            }

            public Builder setBtAddr(ByteString byteString) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).f(byteString);
                return this;
            }

            public Builder clearBtAddr() {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).l();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public int getPbVersion() {
                return ((ConnectionQRCode) this.instance).getPbVersion();
            }

            public Builder setPbVersion(int i) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).d(i);
                return this;
            }

            public Builder clearPbVersion() {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).m();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public int getMcVersion() {
                return ((ConnectionQRCode) this.instance).getMcVersion();
            }

            public Builder setMcVersion(int i) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).e(i);
                return this;
            }

            public Builder clearMcVersion() {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).n();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public ByteString getSwVersion() {
                return ((ConnectionQRCode) this.instance).getSwVersion();
            }

            public Builder setSwVersion(ByteString byteString) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).g(byteString);
                return this;
            }

            public Builder clearSwVersion() {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).o();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public int getRoleValue() {
                return ((ConnectionQRCode) this.instance).getRoleValue();
            }

            public Builder setRoleValue(int i) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).f(i);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public RoleType getRole() {
                return ((ConnectionQRCode) this.instance).getRole();
            }

            public Builder setRole(RoleType roleType) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).a(roleType);
                return this;
            }

            public Builder clearRole() {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).p();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public List<App> getAppsList() {
                return Collections.unmodifiableList(((ConnectionQRCode) this.instance).getAppsList());
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public int getAppsCount() {
                return ((ConnectionQRCode) this.instance).getAppsCount();
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public App getApps(int i) {
                return ((ConnectionQRCode) this.instance).getApps(i);
            }

            public Builder setApps(int i, App app2) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).a(i, app2);
                return this;
            }

            public Builder setApps(int i, App.Builder builder) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).a(i, builder);
                return this;
            }

            public Builder addApps(App app2) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).a(app2);
                return this;
            }

            public Builder addApps(int i, App app2) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).b(i, app2);
                return this;
            }

            public Builder addApps(App.Builder builder) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).a(builder);
                return this;
            }

            public Builder addApps(int i, App.Builder builder) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).b(i, builder);
                return this;
            }

            public Builder addAllApps(Iterable<? extends App> iterable) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).a(iterable);
                return this;
            }

            public Builder clearApps() {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).r();
                return this;
            }

            public Builder removeApps(int i) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).g(i);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public int getDeviceType() {
                return ((ConnectionQRCode) this.instance).getDeviceType();
            }

            public Builder setDeviceType(int i) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).h(i);
                return this;
            }

            public Builder clearDeviceType() {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).s();
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public int getSecurityModeValue() {
                return ((ConnectionQRCode) this.instance).getSecurityModeValue();
            }

            public Builder setSecurityModeValue(int i) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).i(i);
                return this;
            }

            @Override // com.xiaomi.mi_connect_service.proto.AcceptInvitationApp.ConnectionQRCodeOrBuilder
            public SecurityMode getSecurityMode() {
                return ((ConnectionQRCode) this.instance).getSecurityMode();
            }

            public Builder setSecurityMode(SecurityMode securityMode) {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).a(securityMode);
                return this;
            }

            public Builder clearSecurityMode() {
                copyOnWrite();
                ((ConnectionQRCode) this.instance).t();
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
                    return newMessageInfo(p, "\u0000\u000f\u0000\u0000\u0001\u000f\u000f\u0000\u0001\u0000\u0001\u0004\u0002\u0004\u0003Ȉ\u0004Ȉ\u0005Ȉ\u0006Ȉ\u0007Ȉ\b\n\t\u000b\n\u000b\u000b\n\f\f\r\u001b\u000e\u000b\u000f\f", new Object[]{"connType_", "channel_", "macAddr_", "pwd_", "ssid_", "idHash_", "name_", "btAddr_", "pbVersion_", "mcVersion_", "swVersion_", "role_", "apps_", App.class, "deviceType_", "securityMode_"});
                case GET_DEFAULT_INSTANCE:
                    return p;
                case GET_PARSER:
                    Parser<ConnectionQRCode> parser = q;
                    if (parser == null) {
                        synchronized (ConnectionQRCode.class) {
                            parser = q;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(p);
                                q = parser;
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
            p = connectionQRCode;
            GeneratedMessageLite.registerDefaultInstance(ConnectionQRCode.class, connectionQRCode);
        }

        public static ConnectionQRCode getDefaultInstance() {
            return p;
        }

        public static Parser<ConnectionQRCode> parser() {
            return p.getParserForType();
        }
    }
}
