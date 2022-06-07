package com.xiaomi.idm.service.iot.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.MapFieldLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Map;

/* loaded from: classes3.dex */
public final class PropertyServiceProto {

    /* loaded from: classes3.dex */
    public interface GetPropertyCommandsOrBuilder extends MessageLiteOrBuilder {
        boolean containsPropertyMap(String str);

        int getAid();

        @Deprecated
        Map<String, String> getPropertyMap();

        int getPropertyMapCount();

        Map<String, String> getPropertyMapMap();

        String getPropertyMapOrDefault(String str, String str2);

        String getPropertyMapOrThrow(String str);
    }

    /* loaded from: classes3.dex */
    public interface GetPropertyOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getParamJson();

        ByteString getParamJsonBytes();
    }

    /* loaded from: classes3.dex */
    public interface PropertyEventOrBuilder extends MessageLiteOrBuilder {
        int getParam();

        String getParamStr();

        ByteString getParamStrBytes();
    }

    /* loaded from: classes3.dex */
    public interface PropertyResponseOrBuilder extends MessageLiteOrBuilder {
        int getCode();

        String getMessage();

        ByteString getMessageBytes();

        String getResponse();

        ByteString getResponseBytes();
    }

    /* loaded from: classes3.dex */
    public interface SetPropertyCommandsOrBuilder extends MessageLiteOrBuilder {
        boolean containsPropertyMap(String str);

        int getAid();

        @Deprecated
        Map<String, String> getPropertyMap();

        int getPropertyMapCount();

        Map<String, String> getPropertyMapMap();

        String getPropertyMapOrDefault(String str, String str2);

        String getPropertyMapOrThrow(String str);
    }

    /* loaded from: classes3.dex */
    public interface SetPropertyOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        boolean getIsSort();

        String getParamJson();

        ByteString getParamJsonBytes();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private PropertyServiceProto() {
    }

    /* loaded from: classes3.dex */
    public static final class SetProperty extends GeneratedMessageLite<SetProperty, Builder> implements SetPropertyOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int ISSORT_FIELD_NUMBER = 3;
        public static final int PARAMJSON_FIELD_NUMBER = 2;
        private static final SetProperty d;
        private static volatile Parser<SetProperty> e;
        private int a;
        private String b = "";
        private boolean c;

        private SetProperty() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyOrBuilder
        public int getAid() {
            return this.a;
        }

        public void b(int i) {
            this.a = i;
        }

        public void e() {
            this.a = 0;
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyOrBuilder
        public String getParamJson() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyOrBuilder
        public ByteString getParamJsonBytes() {
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
            this.b = getDefaultInstance().getParamJson();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyOrBuilder
        public boolean getIsSort() {
            return this.c;
        }

        public void a(boolean z) {
            this.c = z;
        }

        public void g() {
            this.c = false;
        }

        public static SetProperty parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (SetProperty) GeneratedMessageLite.parseFrom(d, byteBuffer);
        }

        public static SetProperty parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetProperty) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
        }

        public static SetProperty parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (SetProperty) GeneratedMessageLite.parseFrom(d, byteString);
        }

        public static SetProperty parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetProperty) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
        }

        public static SetProperty parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (SetProperty) GeneratedMessageLite.parseFrom(d, bArr);
        }

        public static SetProperty parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetProperty) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
        }

        public static SetProperty parseFrom(InputStream inputStream) throws IOException {
            return (SetProperty) GeneratedMessageLite.parseFrom(d, inputStream);
        }

        public static SetProperty parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetProperty) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
        }

        public static SetProperty parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (SetProperty) parseDelimitedFrom(d, inputStream);
        }

        public static SetProperty parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetProperty) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
        }

        public static SetProperty parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (SetProperty) GeneratedMessageLite.parseFrom(d, codedInputStream);
        }

        public static SetProperty parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetProperty) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return d.createBuilder();
        }

        public static Builder newBuilder(SetProperty setProperty) {
            return d.createBuilder(setProperty);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<SetProperty, Builder> implements SetPropertyOrBuilder {
            private Builder() {
                super(SetProperty.d);
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyOrBuilder
            public int getAid() {
                return ((SetProperty) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((SetProperty) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((SetProperty) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyOrBuilder
            public String getParamJson() {
                return ((SetProperty) this.instance).getParamJson();
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyOrBuilder
            public ByteString getParamJsonBytes() {
                return ((SetProperty) this.instance).getParamJsonBytes();
            }

            public Builder setParamJson(String str) {
                copyOnWrite();
                ((SetProperty) this.instance).a(str);
                return this;
            }

            public Builder clearParamJson() {
                copyOnWrite();
                ((SetProperty) this.instance).f();
                return this;
            }

            public Builder setParamJsonBytes(ByteString byteString) {
                copyOnWrite();
                ((SetProperty) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyOrBuilder
            public boolean getIsSort() {
                return ((SetProperty) this.instance).getIsSort();
            }

            public Builder setIsSort(boolean z) {
                copyOnWrite();
                ((SetProperty) this.instance).a(z);
                return this;
            }

            public Builder clearIsSort() {
                copyOnWrite();
                ((SetProperty) this.instance).g();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new SetProperty();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003\u0007", new Object[]{"aid_", "paramJson_", "isSort_"});
                case GET_DEFAULT_INSTANCE:
                    return d;
                case GET_PARSER:
                    Parser<SetProperty> parser = e;
                    if (parser == null) {
                        synchronized (SetProperty.class) {
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
            SetProperty setProperty = new SetProperty();
            d = setProperty;
            GeneratedMessageLite.registerDefaultInstance(SetProperty.class, setProperty);
        }

        public static SetProperty getDefaultInstance() {
            return d;
        }

        public static Parser<SetProperty> parser() {
            return d.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class GetProperty extends GeneratedMessageLite<GetProperty, Builder> implements GetPropertyOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int PARAMJSON_FIELD_NUMBER = 2;
        private static final GetProperty c;
        private static volatile Parser<GetProperty> d;
        private int a;
        private String b = "";

        private GetProperty() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyOrBuilder
        public int getAid() {
            return this.a;
        }

        public void b(int i) {
            this.a = i;
        }

        public void e() {
            this.a = 0;
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyOrBuilder
        public String getParamJson() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyOrBuilder
        public ByteString getParamJsonBytes() {
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
            this.b = getDefaultInstance().getParamJson();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static GetProperty parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetProperty) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static GetProperty parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetProperty) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static GetProperty parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetProperty) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static GetProperty parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetProperty) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static GetProperty parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetProperty) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static GetProperty parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetProperty) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static GetProperty parseFrom(InputStream inputStream) throws IOException {
            return (GetProperty) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static GetProperty parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetProperty) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static GetProperty parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetProperty) parseDelimitedFrom(c, inputStream);
        }

        public static GetProperty parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetProperty) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static GetProperty parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetProperty) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static GetProperty parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetProperty) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(GetProperty getProperty) {
            return c.createBuilder(getProperty);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<GetProperty, Builder> implements GetPropertyOrBuilder {
            private Builder() {
                super(GetProperty.c);
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyOrBuilder
            public int getAid() {
                return ((GetProperty) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((GetProperty) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((GetProperty) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyOrBuilder
            public String getParamJson() {
                return ((GetProperty) this.instance).getParamJson();
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyOrBuilder
            public ByteString getParamJsonBytes() {
                return ((GetProperty) this.instance).getParamJsonBytes();
            }

            public Builder setParamJson(String str) {
                copyOnWrite();
                ((GetProperty) this.instance).a(str);
                return this;
            }

            public Builder clearParamJson() {
                copyOnWrite();
                ((GetProperty) this.instance).f();
                return this;
            }

            public Builder setParamJsonBytes(ByteString byteString) {
                copyOnWrite();
                ((GetProperty) this.instance).a(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new GetProperty();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0002Ȉ", new Object[]{"aid_", "paramJson_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<GetProperty> parser = d;
                    if (parser == null) {
                        synchronized (GetProperty.class) {
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
            GetProperty getProperty = new GetProperty();
            c = getProperty;
            GeneratedMessageLite.registerDefaultInstance(GetProperty.class, getProperty);
        }

        public static GetProperty getDefaultInstance() {
            return c;
        }

        public static Parser<GetProperty> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class SetPropertyCommands extends GeneratedMessageLite<SetPropertyCommands, Builder> implements SetPropertyCommandsOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int PROPERTYMAP_FIELD_NUMBER = 2;
        private static final SetPropertyCommands c;
        private static volatile Parser<SetPropertyCommands> d;
        private int a;
        private MapFieldLite<String, String> b = MapFieldLite.emptyMapField();

        /* loaded from: classes3.dex */
        private static final class a {
            static final MapEntryLite<String, String> a = MapEntryLite.newDefaultInstance(WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");
        }

        private SetPropertyCommands() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyCommandsOrBuilder
        public int getAid() {
            return this.a;
        }

        public void b(int i) {
            this.a = i;
        }

        public void e() {
            this.a = 0;
        }

        private MapFieldLite<String, String> f() {
            return this.b;
        }

        private MapFieldLite<String, String> g() {
            if (!this.b.isMutable()) {
                this.b = this.b.mutableCopy();
            }
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyCommandsOrBuilder
        public int getPropertyMapCount() {
            return f().size();
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyCommandsOrBuilder
        public boolean containsPropertyMap(String str) {
            if (str != null) {
                return f().containsKey(str);
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyCommandsOrBuilder
        @Deprecated
        public Map<String, String> getPropertyMap() {
            return getPropertyMapMap();
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyCommandsOrBuilder
        public Map<String, String> getPropertyMapMap() {
            return Collections.unmodifiableMap(f());
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyCommandsOrBuilder
        public String getPropertyMapOrDefault(String str, String str2) {
            if (str != null) {
                MapFieldLite<String, String> f = f();
                return f.containsKey(str) ? f.get(str) : str2;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyCommandsOrBuilder
        public String getPropertyMapOrThrow(String str) {
            if (str != null) {
                MapFieldLite<String, String> f = f();
                if (f.containsKey(str)) {
                    return f.get(str);
                }
                throw new IllegalArgumentException();
            }
            throw new NullPointerException();
        }

        public Map<String, String> h() {
            return g();
        }

        public static SetPropertyCommands parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (SetPropertyCommands) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static SetPropertyCommands parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetPropertyCommands) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static SetPropertyCommands parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (SetPropertyCommands) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static SetPropertyCommands parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetPropertyCommands) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static SetPropertyCommands parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (SetPropertyCommands) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static SetPropertyCommands parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SetPropertyCommands) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static SetPropertyCommands parseFrom(InputStream inputStream) throws IOException {
            return (SetPropertyCommands) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static SetPropertyCommands parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetPropertyCommands) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static SetPropertyCommands parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (SetPropertyCommands) parseDelimitedFrom(c, inputStream);
        }

        public static SetPropertyCommands parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetPropertyCommands) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static SetPropertyCommands parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (SetPropertyCommands) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static SetPropertyCommands parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SetPropertyCommands) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(SetPropertyCommands setPropertyCommands) {
            return c.createBuilder(setPropertyCommands);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<SetPropertyCommands, Builder> implements SetPropertyCommandsOrBuilder {
            private Builder() {
                super(SetPropertyCommands.c);
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyCommandsOrBuilder
            public int getAid() {
                return ((SetPropertyCommands) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((SetPropertyCommands) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((SetPropertyCommands) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyCommandsOrBuilder
            public int getPropertyMapCount() {
                return ((SetPropertyCommands) this.instance).getPropertyMapMap().size();
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyCommandsOrBuilder
            public boolean containsPropertyMap(String str) {
                if (str != null) {
                    return ((SetPropertyCommands) this.instance).getPropertyMapMap().containsKey(str);
                }
                throw new NullPointerException();
            }

            public Builder clearPropertyMap() {
                copyOnWrite();
                ((SetPropertyCommands) this.instance).h().clear();
                return this;
            }

            public Builder removePropertyMap(String str) {
                if (str != null) {
                    copyOnWrite();
                    ((SetPropertyCommands) this.instance).h().remove(str);
                    return this;
                }
                throw new NullPointerException();
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyCommandsOrBuilder
            @Deprecated
            public Map<String, String> getPropertyMap() {
                return getPropertyMapMap();
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyCommandsOrBuilder
            public Map<String, String> getPropertyMapMap() {
                return Collections.unmodifiableMap(((SetPropertyCommands) this.instance).getPropertyMapMap());
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyCommandsOrBuilder
            public String getPropertyMapOrDefault(String str, String str2) {
                if (str != null) {
                    Map<String, String> propertyMapMap = ((SetPropertyCommands) this.instance).getPropertyMapMap();
                    return propertyMapMap.containsKey(str) ? propertyMapMap.get(str) : str2;
                }
                throw new NullPointerException();
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.SetPropertyCommandsOrBuilder
            public String getPropertyMapOrThrow(String str) {
                if (str != null) {
                    Map<String, String> propertyMapMap = ((SetPropertyCommands) this.instance).getPropertyMapMap();
                    if (propertyMapMap.containsKey(str)) {
                        return propertyMapMap.get(str);
                    }
                    throw new IllegalArgumentException();
                }
                throw new NullPointerException();
            }

            public Builder putPropertyMap(String str, String str2) {
                if (str == null) {
                    throw new NullPointerException();
                } else if (str2 != null) {
                    copyOnWrite();
                    ((SetPropertyCommands) this.instance).h().put(str, str2);
                    return this;
                } else {
                    throw new NullPointerException();
                }
            }

            public Builder putAllPropertyMap(Map<String, String> map) {
                copyOnWrite();
                ((SetPropertyCommands) this.instance).h().putAll(map);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new SetPropertyCommands();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0001\u0000\u0000\u0001\u0004\u00022", new Object[]{"aid_", "propertyMap_", a.a});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<SetPropertyCommands> parser = d;
                    if (parser == null) {
                        synchronized (SetPropertyCommands.class) {
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
            SetPropertyCommands setPropertyCommands = new SetPropertyCommands();
            c = setPropertyCommands;
            GeneratedMessageLite.registerDefaultInstance(SetPropertyCommands.class, setPropertyCommands);
        }

        public static SetPropertyCommands getDefaultInstance() {
            return c;
        }

        public static Parser<SetPropertyCommands> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class GetPropertyCommands extends GeneratedMessageLite<GetPropertyCommands, Builder> implements GetPropertyCommandsOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int PROPERTYMAP_FIELD_NUMBER = 2;
        private static final GetPropertyCommands c;
        private static volatile Parser<GetPropertyCommands> d;
        private int a;
        private MapFieldLite<String, String> b = MapFieldLite.emptyMapField();

        /* loaded from: classes3.dex */
        private static final class a {
            static final MapEntryLite<String, String> a = MapEntryLite.newDefaultInstance(WireFormat.FieldType.STRING, "", WireFormat.FieldType.STRING, "");
        }

        private GetPropertyCommands() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyCommandsOrBuilder
        public int getAid() {
            return this.a;
        }

        public void b(int i) {
            this.a = i;
        }

        public void e() {
            this.a = 0;
        }

        private MapFieldLite<String, String> f() {
            return this.b;
        }

        private MapFieldLite<String, String> g() {
            if (!this.b.isMutable()) {
                this.b = this.b.mutableCopy();
            }
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyCommandsOrBuilder
        public int getPropertyMapCount() {
            return f().size();
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyCommandsOrBuilder
        public boolean containsPropertyMap(String str) {
            if (str != null) {
                return f().containsKey(str);
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyCommandsOrBuilder
        @Deprecated
        public Map<String, String> getPropertyMap() {
            return getPropertyMapMap();
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyCommandsOrBuilder
        public Map<String, String> getPropertyMapMap() {
            return Collections.unmodifiableMap(f());
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyCommandsOrBuilder
        public String getPropertyMapOrDefault(String str, String str2) {
            if (str != null) {
                MapFieldLite<String, String> f = f();
                return f.containsKey(str) ? f.get(str) : str2;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyCommandsOrBuilder
        public String getPropertyMapOrThrow(String str) {
            if (str != null) {
                MapFieldLite<String, String> f = f();
                if (f.containsKey(str)) {
                    return f.get(str);
                }
                throw new IllegalArgumentException();
            }
            throw new NullPointerException();
        }

        public Map<String, String> h() {
            return g();
        }

        public static GetPropertyCommands parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetPropertyCommands) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static GetPropertyCommands parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetPropertyCommands) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static GetPropertyCommands parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetPropertyCommands) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static GetPropertyCommands parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetPropertyCommands) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static GetPropertyCommands parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetPropertyCommands) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static GetPropertyCommands parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetPropertyCommands) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static GetPropertyCommands parseFrom(InputStream inputStream) throws IOException {
            return (GetPropertyCommands) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static GetPropertyCommands parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetPropertyCommands) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static GetPropertyCommands parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetPropertyCommands) parseDelimitedFrom(c, inputStream);
        }

        public static GetPropertyCommands parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetPropertyCommands) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static GetPropertyCommands parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetPropertyCommands) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static GetPropertyCommands parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetPropertyCommands) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(GetPropertyCommands getPropertyCommands) {
            return c.createBuilder(getPropertyCommands);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<GetPropertyCommands, Builder> implements GetPropertyCommandsOrBuilder {
            private Builder() {
                super(GetPropertyCommands.c);
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyCommandsOrBuilder
            public int getAid() {
                return ((GetPropertyCommands) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((GetPropertyCommands) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((GetPropertyCommands) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyCommandsOrBuilder
            public int getPropertyMapCount() {
                return ((GetPropertyCommands) this.instance).getPropertyMapMap().size();
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyCommandsOrBuilder
            public boolean containsPropertyMap(String str) {
                if (str != null) {
                    return ((GetPropertyCommands) this.instance).getPropertyMapMap().containsKey(str);
                }
                throw new NullPointerException();
            }

            public Builder clearPropertyMap() {
                copyOnWrite();
                ((GetPropertyCommands) this.instance).h().clear();
                return this;
            }

            public Builder removePropertyMap(String str) {
                if (str != null) {
                    copyOnWrite();
                    ((GetPropertyCommands) this.instance).h().remove(str);
                    return this;
                }
                throw new NullPointerException();
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyCommandsOrBuilder
            @Deprecated
            public Map<String, String> getPropertyMap() {
                return getPropertyMapMap();
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyCommandsOrBuilder
            public Map<String, String> getPropertyMapMap() {
                return Collections.unmodifiableMap(((GetPropertyCommands) this.instance).getPropertyMapMap());
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyCommandsOrBuilder
            public String getPropertyMapOrDefault(String str, String str2) {
                if (str != null) {
                    Map<String, String> propertyMapMap = ((GetPropertyCommands) this.instance).getPropertyMapMap();
                    return propertyMapMap.containsKey(str) ? propertyMapMap.get(str) : str2;
                }
                throw new NullPointerException();
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.GetPropertyCommandsOrBuilder
            public String getPropertyMapOrThrow(String str) {
                if (str != null) {
                    Map<String, String> propertyMapMap = ((GetPropertyCommands) this.instance).getPropertyMapMap();
                    if (propertyMapMap.containsKey(str)) {
                        return propertyMapMap.get(str);
                    }
                    throw new IllegalArgumentException();
                }
                throw new NullPointerException();
            }

            public Builder putPropertyMap(String str, String str2) {
                if (str == null) {
                    throw new NullPointerException();
                } else if (str2 != null) {
                    copyOnWrite();
                    ((GetPropertyCommands) this.instance).h().put(str, str2);
                    return this;
                } else {
                    throw new NullPointerException();
                }
            }

            public Builder putAllPropertyMap(Map<String, String> map) {
                copyOnWrite();
                ((GetPropertyCommands) this.instance).h().putAll(map);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new GetPropertyCommands();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0001\u0000\u0000\u0001\u0004\u00022", new Object[]{"aid_", "propertyMap_", a.a});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<GetPropertyCommands> parser = d;
                    if (parser == null) {
                        synchronized (GetPropertyCommands.class) {
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
            GetPropertyCommands getPropertyCommands = new GetPropertyCommands();
            c = getPropertyCommands;
            GeneratedMessageLite.registerDefaultInstance(GetPropertyCommands.class, getPropertyCommands);
        }

        public static GetPropertyCommands getDefaultInstance() {
            return c;
        }

        public static Parser<GetPropertyCommands> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class PropertyResponse extends GeneratedMessageLite<PropertyResponse, Builder> implements PropertyResponseOrBuilder {
        public static final int CODE_FIELD_NUMBER = 1;
        public static final int MESSAGE_FIELD_NUMBER = 2;
        public static final int RESPONSE_FIELD_NUMBER = 3;
        private static final PropertyResponse d;
        private static volatile Parser<PropertyResponse> e;
        private int a;
        private String b = "";
        private String c = "";

        private PropertyResponse() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyResponseOrBuilder
        public int getCode() {
            return this.a;
        }

        public void b(int i) {
            this.a = i;
        }

        public void e() {
            this.a = 0;
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyResponseOrBuilder
        public String getMessage() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyResponseOrBuilder
        public ByteString getMessageBytes() {
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
            this.b = getDefaultInstance().getMessage();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyResponseOrBuilder
        public String getResponse() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyResponseOrBuilder
        public ByteString getResponseBytes() {
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
            this.c = getDefaultInstance().getResponse();
        }

        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.c = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static PropertyResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (PropertyResponse) GeneratedMessageLite.parseFrom(d, byteBuffer);
        }

        public static PropertyResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (PropertyResponse) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
        }

        public static PropertyResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (PropertyResponse) GeneratedMessageLite.parseFrom(d, byteString);
        }

        public static PropertyResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (PropertyResponse) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
        }

        public static PropertyResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (PropertyResponse) GeneratedMessageLite.parseFrom(d, bArr);
        }

        public static PropertyResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (PropertyResponse) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
        }

        public static PropertyResponse parseFrom(InputStream inputStream) throws IOException {
            return (PropertyResponse) GeneratedMessageLite.parseFrom(d, inputStream);
        }

        public static PropertyResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PropertyResponse) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
        }

        public static PropertyResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (PropertyResponse) parseDelimitedFrom(d, inputStream);
        }

        public static PropertyResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PropertyResponse) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
        }

        public static PropertyResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (PropertyResponse) GeneratedMessageLite.parseFrom(d, codedInputStream);
        }

        public static PropertyResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PropertyResponse) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return d.createBuilder();
        }

        public static Builder newBuilder(PropertyResponse propertyResponse) {
            return d.createBuilder(propertyResponse);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<PropertyResponse, Builder> implements PropertyResponseOrBuilder {
            private Builder() {
                super(PropertyResponse.d);
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyResponseOrBuilder
            public int getCode() {
                return ((PropertyResponse) this.instance).getCode();
            }

            public Builder setCode(int i) {
                copyOnWrite();
                ((PropertyResponse) this.instance).b(i);
                return this;
            }

            public Builder clearCode() {
                copyOnWrite();
                ((PropertyResponse) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyResponseOrBuilder
            public String getMessage() {
                return ((PropertyResponse) this.instance).getMessage();
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyResponseOrBuilder
            public ByteString getMessageBytes() {
                return ((PropertyResponse) this.instance).getMessageBytes();
            }

            public Builder setMessage(String str) {
                copyOnWrite();
                ((PropertyResponse) this.instance).a(str);
                return this;
            }

            public Builder clearMessage() {
                copyOnWrite();
                ((PropertyResponse) this.instance).f();
                return this;
            }

            public Builder setMessageBytes(ByteString byteString) {
                copyOnWrite();
                ((PropertyResponse) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyResponseOrBuilder
            public String getResponse() {
                return ((PropertyResponse) this.instance).getResponse();
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyResponseOrBuilder
            public ByteString getResponseBytes() {
                return ((PropertyResponse) this.instance).getResponseBytes();
            }

            public Builder setResponse(String str) {
                copyOnWrite();
                ((PropertyResponse) this.instance).b(str);
                return this;
            }

            public Builder clearResponse() {
                copyOnWrite();
                ((PropertyResponse) this.instance).g();
                return this;
            }

            public Builder setResponseBytes(ByteString byteString) {
                copyOnWrite();
                ((PropertyResponse) this.instance).b(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new PropertyResponse();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ", new Object[]{"code_", "message_", "response_"});
                case GET_DEFAULT_INSTANCE:
                    return d;
                case GET_PARSER:
                    Parser<PropertyResponse> parser = e;
                    if (parser == null) {
                        synchronized (PropertyResponse.class) {
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
            PropertyResponse propertyResponse = new PropertyResponse();
            d = propertyResponse;
            GeneratedMessageLite.registerDefaultInstance(PropertyResponse.class, propertyResponse);
        }

        public static PropertyResponse getDefaultInstance() {
            return d;
        }

        public static Parser<PropertyResponse> parser() {
            return d.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class PropertyEvent extends GeneratedMessageLite<PropertyEvent, Builder> implements PropertyEventOrBuilder {
        public static final int PARAMSTR_FIELD_NUMBER = 2;
        public static final int PARAM_FIELD_NUMBER = 1;
        private static final PropertyEvent c;
        private static volatile Parser<PropertyEvent> d;
        private int a;
        private String b = "";

        private PropertyEvent() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyEventOrBuilder
        public int getParam() {
            return this.a;
        }

        public void b(int i) {
            this.a = i;
        }

        public void e() {
            this.a = 0;
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyEventOrBuilder
        public String getParamStr() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyEventOrBuilder
        public ByteString getParamStrBytes() {
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
            this.b = getDefaultInstance().getParamStr();
        }

        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static PropertyEvent parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (PropertyEvent) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static PropertyEvent parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (PropertyEvent) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static PropertyEvent parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (PropertyEvent) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static PropertyEvent parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (PropertyEvent) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static PropertyEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (PropertyEvent) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static PropertyEvent parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (PropertyEvent) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static PropertyEvent parseFrom(InputStream inputStream) throws IOException {
            return (PropertyEvent) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static PropertyEvent parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PropertyEvent) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static PropertyEvent parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (PropertyEvent) parseDelimitedFrom(c, inputStream);
        }

        public static PropertyEvent parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PropertyEvent) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static PropertyEvent parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (PropertyEvent) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static PropertyEvent parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PropertyEvent) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(PropertyEvent propertyEvent) {
            return c.createBuilder(propertyEvent);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<PropertyEvent, Builder> implements PropertyEventOrBuilder {
            private Builder() {
                super(PropertyEvent.c);
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyEventOrBuilder
            public int getParam() {
                return ((PropertyEvent) this.instance).getParam();
            }

            public Builder setParam(int i) {
                copyOnWrite();
                ((PropertyEvent) this.instance).b(i);
                return this;
            }

            public Builder clearParam() {
                copyOnWrite();
                ((PropertyEvent) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyEventOrBuilder
            public String getParamStr() {
                return ((PropertyEvent) this.instance).getParamStr();
            }

            @Override // com.xiaomi.idm.service.iot.proto.PropertyServiceProto.PropertyEventOrBuilder
            public ByteString getParamStrBytes() {
                return ((PropertyEvent) this.instance).getParamStrBytes();
            }

            public Builder setParamStr(String str) {
                copyOnWrite();
                ((PropertyEvent) this.instance).a(str);
                return this;
            }

            public Builder clearParamStr() {
                copyOnWrite();
                ((PropertyEvent) this.instance).f();
                return this;
            }

            public Builder setParamStrBytes(ByteString byteString) {
                copyOnWrite();
                ((PropertyEvent) this.instance).a(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new PropertyEvent();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0002Ȉ", new Object[]{"param_", "paramStr_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<PropertyEvent> parser = d;
                    if (parser == null) {
                        synchronized (PropertyEvent.class) {
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
            PropertyEvent propertyEvent = new PropertyEvent();
            c = propertyEvent;
            GeneratedMessageLite.registerDefaultInstance(PropertyEvent.class, propertyEvent);
        }

        public static PropertyEvent getDefaultInstance() {
            return c;
        }

        public static Parser<PropertyEvent> parser() {
            return c.getParserForType();
        }
    }
}
