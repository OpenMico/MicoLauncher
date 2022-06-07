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
public final class TestServiceProto {

    /* loaded from: classes3.dex */
    public interface GetAPlusBOrBuilder extends MessageLiteOrBuilder {
        int getA();

        int getAid();

        int getB();
    }

    /* loaded from: classes3.dex */
    public interface GetSomeStringOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        String getParam1();

        ByteString getParam1Bytes();

        String getParam2();

        ByteString getParam2Bytes();

        String getParam3();

        ByteString getParam3Bytes();
    }

    /* loaded from: classes3.dex */
    public interface GetSomeStringResOrBuilder extends MessageLiteOrBuilder {
        String getSome1();

        ByteString getSome1Bytes();

        String getSome2();

        ByteString getSome2Bytes();

        String getSome3();

        ByteString getSome3Bytes();
    }

    /* loaded from: classes3.dex */
    public interface GetTimestampOrBuilder extends MessageLiteOrBuilder {
        int getAid();
    }

    /* loaded from: classes3.dex */
    public interface MyTestEventOrBuilder extends MessageLiteOrBuilder {
        int getParam();

        String getParamStr();

        ByteString getParamStrBytes();
    }

    /* loaded from: classes3.dex */
    public interface TriggerClickOrBuilder extends MessageLiteOrBuilder {
        int getAid();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private TestServiceProto() {
    }

    /* loaded from: classes3.dex */
    public static final class GetSomeString extends GeneratedMessageLite<GetSomeString, Builder> implements GetSomeStringOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int PARAM1_FIELD_NUMBER = 2;
        public static final int PARAM2_FIELD_NUMBER = 3;
        public static final int PARAM3_FIELD_NUMBER = 4;
        private static final GetSomeString e;
        private static volatile Parser<GetSomeString> f;
        private int a;
        private String b = "";
        private String c = "";
        private String d = "";

        private GetSomeString() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringOrBuilder
        public String getParam1() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringOrBuilder
        public ByteString getParam1Bytes() {
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
            this.b = getDefaultInstance().getParam1();
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

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringOrBuilder
        public String getParam2() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringOrBuilder
        public ByteString getParam2Bytes() {
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
            this.c = getDefaultInstance().getParam2();
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

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringOrBuilder
        public String getParam3() {
            return this.d;
        }

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringOrBuilder
        public ByteString getParam3Bytes() {
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
            this.d = getDefaultInstance().getParam3();
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

        public static GetSomeString parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(e, byteBuffer);
        }

        public static GetSomeString parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(e, byteBuffer, extensionRegistryLite);
        }

        public static GetSomeString parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(e, byteString);
        }

        public static GetSomeString parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(e, byteString, extensionRegistryLite);
        }

        public static GetSomeString parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(e, bArr);
        }

        public static GetSomeString parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(e, bArr, extensionRegistryLite);
        }

        public static GetSomeString parseFrom(InputStream inputStream) throws IOException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(e, inputStream);
        }

        public static GetSomeString parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(e, inputStream, extensionRegistryLite);
        }

        public static GetSomeString parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetSomeString) parseDelimitedFrom(e, inputStream);
        }

        public static GetSomeString parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetSomeString) parseDelimitedFrom(e, inputStream, extensionRegistryLite);
        }

        public static GetSomeString parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(e, codedInputStream);
        }

        public static GetSomeString parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetSomeString) GeneratedMessageLite.parseFrom(e, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return e.createBuilder();
        }

        public static Builder newBuilder(GetSomeString getSomeString) {
            return e.createBuilder(getSomeString);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<GetSomeString, Builder> implements GetSomeStringOrBuilder {
            private Builder() {
                super(GetSomeString.e);
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringOrBuilder
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

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringOrBuilder
            public String getParam1() {
                return ((GetSomeString) this.instance).getParam1();
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringOrBuilder
            public ByteString getParam1Bytes() {
                return ((GetSomeString) this.instance).getParam1Bytes();
            }

            public Builder setParam1(String str) {
                copyOnWrite();
                ((GetSomeString) this.instance).a(str);
                return this;
            }

            public Builder clearParam1() {
                copyOnWrite();
                ((GetSomeString) this.instance).f();
                return this;
            }

            public Builder setParam1Bytes(ByteString byteString) {
                copyOnWrite();
                ((GetSomeString) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringOrBuilder
            public String getParam2() {
                return ((GetSomeString) this.instance).getParam2();
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringOrBuilder
            public ByteString getParam2Bytes() {
                return ((GetSomeString) this.instance).getParam2Bytes();
            }

            public Builder setParam2(String str) {
                copyOnWrite();
                ((GetSomeString) this.instance).b(str);
                return this;
            }

            public Builder clearParam2() {
                copyOnWrite();
                ((GetSomeString) this.instance).g();
                return this;
            }

            public Builder setParam2Bytes(ByteString byteString) {
                copyOnWrite();
                ((GetSomeString) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringOrBuilder
            public String getParam3() {
                return ((GetSomeString) this.instance).getParam3();
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringOrBuilder
            public ByteString getParam3Bytes() {
                return ((GetSomeString) this.instance).getParam3Bytes();
            }

            public Builder setParam3(String str) {
                copyOnWrite();
                ((GetSomeString) this.instance).c(str);
                return this;
            }

            public Builder clearParam3() {
                copyOnWrite();
                ((GetSomeString) this.instance).h();
                return this;
            }

            public Builder setParam3Bytes(ByteString byteString) {
                copyOnWrite();
                ((GetSomeString) this.instance).c(byteString);
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
                    return newMessageInfo(e, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ\u0004Ȉ", new Object[]{"aid_", "param1_", "param2_", "param3_"});
                case GET_DEFAULT_INSTANCE:
                    return e;
                case GET_PARSER:
                    Parser<GetSomeString> parser = f;
                    if (parser == null) {
                        synchronized (GetSomeString.class) {
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
            GetSomeString getSomeString = new GetSomeString();
            e = getSomeString;
            GeneratedMessageLite.registerDefaultInstance(GetSomeString.class, getSomeString);
        }

        public static GetSomeString getDefaultInstance() {
            return e;
        }

        public static Parser<GetSomeString> parser() {
            return e.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class GetAPlusB extends GeneratedMessageLite<GetAPlusB, Builder> implements GetAPlusBOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int A_FIELD_NUMBER = 2;
        public static final int B_FIELD_NUMBER = 3;
        private static final GetAPlusB d;
        private static volatile Parser<GetAPlusB> e;
        private int a;
        private int b;
        private int c;

        private GetAPlusB() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetAPlusBOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetAPlusBOrBuilder
        public int getA() {
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

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetAPlusBOrBuilder
        public int getB() {
            return this.c;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d(int i) {
            this.c = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g() {
            this.c = 0;
        }

        public static GetAPlusB parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetAPlusB) GeneratedMessageLite.parseFrom(d, byteBuffer);
        }

        public static GetAPlusB parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetAPlusB) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
        }

        public static GetAPlusB parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetAPlusB) GeneratedMessageLite.parseFrom(d, byteString);
        }

        public static GetAPlusB parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetAPlusB) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
        }

        public static GetAPlusB parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetAPlusB) GeneratedMessageLite.parseFrom(d, bArr);
        }

        public static GetAPlusB parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetAPlusB) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
        }

        public static GetAPlusB parseFrom(InputStream inputStream) throws IOException {
            return (GetAPlusB) GeneratedMessageLite.parseFrom(d, inputStream);
        }

        public static GetAPlusB parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetAPlusB) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
        }

        public static GetAPlusB parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetAPlusB) parseDelimitedFrom(d, inputStream);
        }

        public static GetAPlusB parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetAPlusB) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
        }

        public static GetAPlusB parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetAPlusB) GeneratedMessageLite.parseFrom(d, codedInputStream);
        }

        public static GetAPlusB parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetAPlusB) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return d.createBuilder();
        }

        public static Builder newBuilder(GetAPlusB getAPlusB) {
            return d.createBuilder(getAPlusB);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<GetAPlusB, Builder> implements GetAPlusBOrBuilder {
            private Builder() {
                super(GetAPlusB.d);
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetAPlusBOrBuilder
            public int getAid() {
                return ((GetAPlusB) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((GetAPlusB) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((GetAPlusB) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetAPlusBOrBuilder
            public int getA() {
                return ((GetAPlusB) this.instance).getA();
            }

            public Builder setA(int i) {
                copyOnWrite();
                ((GetAPlusB) this.instance).c(i);
                return this;
            }

            public Builder clearA() {
                copyOnWrite();
                ((GetAPlusB) this.instance).f();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetAPlusBOrBuilder
            public int getB() {
                return ((GetAPlusB) this.instance).getB();
            }

            public Builder setB(int i) {
                copyOnWrite();
                ((GetAPlusB) this.instance).d(i);
                return this;
            }

            public Builder clearB() {
                copyOnWrite();
                ((GetAPlusB) this.instance).g();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new GetAPlusB();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002\u0004\u0003\u0004", new Object[]{"aid_", "a_", "b_"});
                case GET_DEFAULT_INSTANCE:
                    return d;
                case GET_PARSER:
                    Parser<GetAPlusB> parser = e;
                    if (parser == null) {
                        synchronized (GetAPlusB.class) {
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
            GetAPlusB getAPlusB = new GetAPlusB();
            d = getAPlusB;
            GeneratedMessageLite.registerDefaultInstance(GetAPlusB.class, getAPlusB);
        }

        public static GetAPlusB getDefaultInstance() {
            return d;
        }

        public static Parser<GetAPlusB> parser() {
            return d.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class GetTimestamp extends GeneratedMessageLite<GetTimestamp, Builder> implements GetTimestampOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        private static final GetTimestamp b;
        private static volatile Parser<GetTimestamp> c;
        private int a;

        private GetTimestamp() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetTimestampOrBuilder
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

        public static GetTimestamp parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetTimestamp) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static GetTimestamp parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetTimestamp) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static GetTimestamp parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetTimestamp) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static GetTimestamp parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetTimestamp) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static GetTimestamp parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetTimestamp) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static GetTimestamp parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetTimestamp) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static GetTimestamp parseFrom(InputStream inputStream) throws IOException {
            return (GetTimestamp) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static GetTimestamp parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetTimestamp) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static GetTimestamp parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetTimestamp) parseDelimitedFrom(b, inputStream);
        }

        public static GetTimestamp parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetTimestamp) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static GetTimestamp parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetTimestamp) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static GetTimestamp parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetTimestamp) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(GetTimestamp getTimestamp) {
            return b.createBuilder(getTimestamp);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<GetTimestamp, Builder> implements GetTimestampOrBuilder {
            private Builder() {
                super(GetTimestamp.b);
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetTimestampOrBuilder
            public int getAid() {
                return ((GetTimestamp) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((GetTimestamp) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((GetTimestamp) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new GetTimestamp();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004", new Object[]{"aid_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<GetTimestamp> parser = c;
                    if (parser == null) {
                        synchronized (GetTimestamp.class) {
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
            GetTimestamp getTimestamp = new GetTimestamp();
            b = getTimestamp;
            GeneratedMessageLite.registerDefaultInstance(GetTimestamp.class, getTimestamp);
        }

        public static GetTimestamp getDefaultInstance() {
            return b;
        }

        public static Parser<GetTimestamp> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class TriggerClick extends GeneratedMessageLite<TriggerClick, Builder> implements TriggerClickOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        private static final TriggerClick b;
        private static volatile Parser<TriggerClick> c;
        private int a;

        private TriggerClick() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.TriggerClickOrBuilder
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

        public static TriggerClick parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (TriggerClick) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static TriggerClick parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (TriggerClick) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static TriggerClick parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (TriggerClick) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static TriggerClick parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (TriggerClick) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static TriggerClick parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (TriggerClick) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static TriggerClick parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (TriggerClick) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static TriggerClick parseFrom(InputStream inputStream) throws IOException {
            return (TriggerClick) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static TriggerClick parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (TriggerClick) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static TriggerClick parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (TriggerClick) parseDelimitedFrom(b, inputStream);
        }

        public static TriggerClick parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (TriggerClick) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static TriggerClick parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (TriggerClick) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static TriggerClick parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (TriggerClick) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(TriggerClick triggerClick) {
            return b.createBuilder(triggerClick);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<TriggerClick, Builder> implements TriggerClickOrBuilder {
            private Builder() {
                super(TriggerClick.b);
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.TriggerClickOrBuilder
            public int getAid() {
                return ((TriggerClick) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((TriggerClick) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((TriggerClick) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new TriggerClick();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004", new Object[]{"aid_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<TriggerClick> parser = c;
                    if (parser == null) {
                        synchronized (TriggerClick.class) {
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
            TriggerClick triggerClick = new TriggerClick();
            b = triggerClick;
            GeneratedMessageLite.registerDefaultInstance(TriggerClick.class, triggerClick);
        }

        public static TriggerClick getDefaultInstance() {
            return b;
        }

        public static Parser<TriggerClick> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class GetSomeStringRes extends GeneratedMessageLite<GetSomeStringRes, Builder> implements GetSomeStringResOrBuilder {
        public static final int SOME1_FIELD_NUMBER = 1;
        public static final int SOME2_FIELD_NUMBER = 2;
        public static final int SOME3_FIELD_NUMBER = 3;
        private static final GetSomeStringRes d;
        private static volatile Parser<GetSomeStringRes> e;
        private String a = "";
        private String b = "";
        private String c = "";

        private GetSomeStringRes() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringResOrBuilder
        public String getSome1() {
            return this.a;
        }

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringResOrBuilder
        public ByteString getSome1Bytes() {
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
            this.a = getDefaultInstance().getSome1();
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

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringResOrBuilder
        public String getSome2() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringResOrBuilder
        public ByteString getSome2Bytes() {
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
            this.b = getDefaultInstance().getSome2();
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

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringResOrBuilder
        public String getSome3() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringResOrBuilder
        public ByteString getSome3Bytes() {
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
            this.c = getDefaultInstance().getSome3();
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

        public static GetSomeStringRes parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetSomeStringRes) GeneratedMessageLite.parseFrom(d, byteBuffer);
        }

        public static GetSomeStringRes parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetSomeStringRes) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
        }

        public static GetSomeStringRes parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetSomeStringRes) GeneratedMessageLite.parseFrom(d, byteString);
        }

        public static GetSomeStringRes parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetSomeStringRes) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
        }

        public static GetSomeStringRes parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetSomeStringRes) GeneratedMessageLite.parseFrom(d, bArr);
        }

        public static GetSomeStringRes parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetSomeStringRes) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
        }

        public static GetSomeStringRes parseFrom(InputStream inputStream) throws IOException {
            return (GetSomeStringRes) GeneratedMessageLite.parseFrom(d, inputStream);
        }

        public static GetSomeStringRes parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetSomeStringRes) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
        }

        public static GetSomeStringRes parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetSomeStringRes) parseDelimitedFrom(d, inputStream);
        }

        public static GetSomeStringRes parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetSomeStringRes) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
        }

        public static GetSomeStringRes parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetSomeStringRes) GeneratedMessageLite.parseFrom(d, codedInputStream);
        }

        public static GetSomeStringRes parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetSomeStringRes) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return d.createBuilder();
        }

        public static Builder newBuilder(GetSomeStringRes getSomeStringRes) {
            return d.createBuilder(getSomeStringRes);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<GetSomeStringRes, Builder> implements GetSomeStringResOrBuilder {
            private Builder() {
                super(GetSomeStringRes.d);
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringResOrBuilder
            public String getSome1() {
                return ((GetSomeStringRes) this.instance).getSome1();
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringResOrBuilder
            public ByteString getSome1Bytes() {
                return ((GetSomeStringRes) this.instance).getSome1Bytes();
            }

            public Builder setSome1(String str) {
                copyOnWrite();
                ((GetSomeStringRes) this.instance).a(str);
                return this;
            }

            public Builder clearSome1() {
                copyOnWrite();
                ((GetSomeStringRes) this.instance).e();
                return this;
            }

            public Builder setSome1Bytes(ByteString byteString) {
                copyOnWrite();
                ((GetSomeStringRes) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringResOrBuilder
            public String getSome2() {
                return ((GetSomeStringRes) this.instance).getSome2();
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringResOrBuilder
            public ByteString getSome2Bytes() {
                return ((GetSomeStringRes) this.instance).getSome2Bytes();
            }

            public Builder setSome2(String str) {
                copyOnWrite();
                ((GetSomeStringRes) this.instance).b(str);
                return this;
            }

            public Builder clearSome2() {
                copyOnWrite();
                ((GetSomeStringRes) this.instance).f();
                return this;
            }

            public Builder setSome2Bytes(ByteString byteString) {
                copyOnWrite();
                ((GetSomeStringRes) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringResOrBuilder
            public String getSome3() {
                return ((GetSomeStringRes) this.instance).getSome3();
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.GetSomeStringResOrBuilder
            public ByteString getSome3Bytes() {
                return ((GetSomeStringRes) this.instance).getSome3Bytes();
            }

            public Builder setSome3(String str) {
                copyOnWrite();
                ((GetSomeStringRes) this.instance).c(str);
                return this;
            }

            public Builder clearSome3() {
                copyOnWrite();
                ((GetSomeStringRes) this.instance).g();
                return this;
            }

            public Builder setSome3Bytes(ByteString byteString) {
                copyOnWrite();
                ((GetSomeStringRes) this.instance).c(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new GetSomeStringRes();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ", new Object[]{"some1_", "some2_", "some3_"});
                case GET_DEFAULT_INSTANCE:
                    return d;
                case GET_PARSER:
                    Parser<GetSomeStringRes> parser = e;
                    if (parser == null) {
                        synchronized (GetSomeStringRes.class) {
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
            GetSomeStringRes getSomeStringRes = new GetSomeStringRes();
            d = getSomeStringRes;
            GeneratedMessageLite.registerDefaultInstance(GetSomeStringRes.class, getSomeStringRes);
        }

        public static GetSomeStringRes getDefaultInstance() {
            return d;
        }

        public static Parser<GetSomeStringRes> parser() {
            return d.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class MyTestEvent extends GeneratedMessageLite<MyTestEvent, Builder> implements MyTestEventOrBuilder {
        public static final int PARAMSTR_FIELD_NUMBER = 2;
        public static final int PARAM_FIELD_NUMBER = 1;
        private static final MyTestEvent c;
        private static volatile Parser<MyTestEvent> d;
        private int a;
        private String b = "";

        private MyTestEvent() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.MyTestEventOrBuilder
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

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.MyTestEventOrBuilder
        public String getParamStr() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.MyTestEventOrBuilder
        public ByteString getParamStrBytes() {
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
            this.b = getDefaultInstance().getParamStr();
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

        public static MyTestEvent parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (MyTestEvent) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static MyTestEvent parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (MyTestEvent) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static MyTestEvent parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (MyTestEvent) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static MyTestEvent parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (MyTestEvent) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static MyTestEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (MyTestEvent) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static MyTestEvent parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (MyTestEvent) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static MyTestEvent parseFrom(InputStream inputStream) throws IOException {
            return (MyTestEvent) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static MyTestEvent parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (MyTestEvent) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static MyTestEvent parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (MyTestEvent) parseDelimitedFrom(c, inputStream);
        }

        public static MyTestEvent parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (MyTestEvent) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static MyTestEvent parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (MyTestEvent) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static MyTestEvent parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (MyTestEvent) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(MyTestEvent myTestEvent) {
            return c.createBuilder(myTestEvent);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<MyTestEvent, Builder> implements MyTestEventOrBuilder {
            private Builder() {
                super(MyTestEvent.c);
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.MyTestEventOrBuilder
            public int getParam() {
                return ((MyTestEvent) this.instance).getParam();
            }

            public Builder setParam(int i) {
                copyOnWrite();
                ((MyTestEvent) this.instance).b(i);
                return this;
            }

            public Builder clearParam() {
                copyOnWrite();
                ((MyTestEvent) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.MyTestEventOrBuilder
            public String getParamStr() {
                return ((MyTestEvent) this.instance).getParamStr();
            }

            @Override // com.xiaomi.idm.service.iot.proto.TestServiceProto.MyTestEventOrBuilder
            public ByteString getParamStrBytes() {
                return ((MyTestEvent) this.instance).getParamStrBytes();
            }

            public Builder setParamStr(String str) {
                copyOnWrite();
                ((MyTestEvent) this.instance).a(str);
                return this;
            }

            public Builder clearParamStr() {
                copyOnWrite();
                ((MyTestEvent) this.instance).f();
                return this;
            }

            public Builder setParamStrBytes(ByteString byteString) {
                copyOnWrite();
                ((MyTestEvent) this.instance).a(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new MyTestEvent();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0002Ȉ", new Object[]{"param_", "paramStr_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<MyTestEvent> parser = d;
                    if (parser == null) {
                        synchronized (MyTestEvent.class) {
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
            MyTestEvent myTestEvent = new MyTestEvent();
            c = myTestEvent;
            GeneratedMessageLite.registerDefaultInstance(MyTestEvent.class, myTestEvent);
        }

        public static MyTestEvent getDefaultInstance() {
            return c;
        }

        public static Parser<MyTestEvent> parser() {
            return c.getParserForType();
        }
    }
}
