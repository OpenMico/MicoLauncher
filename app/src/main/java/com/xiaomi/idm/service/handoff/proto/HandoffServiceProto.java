package com.xiaomi.idm.service.handoff.proto;

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
public final class HandoffServiceProto {

    /* loaded from: classes3.dex */
    public interface AllHandoffDataOrBuilder extends MessageLiteOrBuilder {
        HandoffData getHandoffDataList(int i);

        int getHandoffDataListCount();

        List<HandoffData> getHandoffDataListList();

        String getIdHash();

        ByteString getIdHashBytes();
    }

    /* loaded from: classes3.dex */
    public interface DataOrBuilder extends MessageLiteOrBuilder {
        String getKey();

        ByteString getKeyBytes();

        ByteString getValue();
    }

    /* loaded from: classes3.dex */
    public interface HandoffDataOrBuilder extends MessageLiteOrBuilder {
        String getAppKey();

        ByteString getAppKeyBytes();

        Data getDataList(int i);

        int getDataListCount();

        List<Data> getDataListList();
    }

    /* loaded from: classes3.dex */
    public interface HandoffEventOrBuilder extends MessageLiteOrBuilder {
        String getAppKey();

        ByteString getAppKeyBytes();

        String getIdHash();

        ByteString getIdHashBytes();

        String getKey();

        ByteString getKeyBytes();

        ByteString getValue();
    }

    /* loaded from: classes3.dex */
    public interface RequestHandoffDataOrBuilder extends MessageLiteOrBuilder {
        int getAid();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private HandoffServiceProto() {
    }

    /* loaded from: classes3.dex */
    public static final class RequestHandoffData extends GeneratedMessageLite<RequestHandoffData, Builder> implements RequestHandoffDataOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        private static final RequestHandoffData b;
        private static volatile Parser<RequestHandoffData> c;
        private int a;

        private RequestHandoffData() {
        }

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.RequestHandoffDataOrBuilder
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

        public static RequestHandoffData parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (RequestHandoffData) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static RequestHandoffData parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RequestHandoffData) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static RequestHandoffData parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (RequestHandoffData) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static RequestHandoffData parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RequestHandoffData) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static RequestHandoffData parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (RequestHandoffData) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static RequestHandoffData parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RequestHandoffData) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static RequestHandoffData parseFrom(InputStream inputStream) throws IOException {
            return (RequestHandoffData) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static RequestHandoffData parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RequestHandoffData) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static RequestHandoffData parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (RequestHandoffData) parseDelimitedFrom(b, inputStream);
        }

        public static RequestHandoffData parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RequestHandoffData) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static RequestHandoffData parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (RequestHandoffData) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static RequestHandoffData parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RequestHandoffData) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(RequestHandoffData requestHandoffData) {
            return b.createBuilder(requestHandoffData);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<RequestHandoffData, Builder> implements RequestHandoffDataOrBuilder {
            private Builder() {
                super(RequestHandoffData.b);
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.RequestHandoffDataOrBuilder
            public int getAid() {
                return ((RequestHandoffData) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((RequestHandoffData) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((RequestHandoffData) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new RequestHandoffData();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004", new Object[]{"aid_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<RequestHandoffData> parser = c;
                    if (parser == null) {
                        synchronized (RequestHandoffData.class) {
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
            RequestHandoffData requestHandoffData = new RequestHandoffData();
            b = requestHandoffData;
            GeneratedMessageLite.registerDefaultInstance(RequestHandoffData.class, requestHandoffData);
        }

        public static RequestHandoffData getDefaultInstance() {
            return b;
        }

        public static Parser<RequestHandoffData> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class AllHandoffData extends GeneratedMessageLite<AllHandoffData, Builder> implements AllHandoffDataOrBuilder {
        public static final int HANDOFFDATALIST_FIELD_NUMBER = 2;
        public static final int IDHASH_FIELD_NUMBER = 1;
        private static final AllHandoffData c;
        private static volatile Parser<AllHandoffData> d;
        private String a = "";
        private Internal.ProtobufList<HandoffData> b = emptyProtobufList();

        private AllHandoffData() {
        }

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.AllHandoffDataOrBuilder
        public String getIdHash() {
            return this.a;
        }

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.AllHandoffDataOrBuilder
        public ByteString getIdHashBytes() {
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
            this.a = getDefaultInstance().getIdHash();
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

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.AllHandoffDataOrBuilder
        public List<HandoffData> getHandoffDataListList() {
            return this.b;
        }

        public List<? extends HandoffDataOrBuilder> getHandoffDataListOrBuilderList() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.AllHandoffDataOrBuilder
        public int getHandoffDataListCount() {
            return this.b.size();
        }

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.AllHandoffDataOrBuilder
        public HandoffData getHandoffDataList(int i) {
            return this.b.get(i);
        }

        public HandoffDataOrBuilder getHandoffDataListOrBuilder(int i) {
            return this.b.get(i);
        }

        private void f() {
            if (!this.b.isModifiable()) {
                this.b = GeneratedMessageLite.mutableCopy(this.b);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i, HandoffData handoffData) {
            if (handoffData != null) {
                f();
                this.b.set(i, handoffData);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i, HandoffData.Builder builder) {
            f();
            this.b.set(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(HandoffData handoffData) {
            if (handoffData != null) {
                f();
                this.b.add(handoffData);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i, HandoffData handoffData) {
            if (handoffData != null) {
                f();
                this.b.add(i, handoffData);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(HandoffData.Builder builder) {
            f();
            this.b.add(builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i, HandoffData.Builder builder) {
            f();
            this.b.add(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Iterable<? extends HandoffData> iterable) {
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

        public static AllHandoffData parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (AllHandoffData) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static AllHandoffData parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AllHandoffData) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static AllHandoffData parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (AllHandoffData) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static AllHandoffData parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AllHandoffData) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static AllHandoffData parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (AllHandoffData) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static AllHandoffData parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AllHandoffData) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static AllHandoffData parseFrom(InputStream inputStream) throws IOException {
            return (AllHandoffData) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static AllHandoffData parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AllHandoffData) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static AllHandoffData parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (AllHandoffData) parseDelimitedFrom(c, inputStream);
        }

        public static AllHandoffData parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AllHandoffData) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static AllHandoffData parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (AllHandoffData) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static AllHandoffData parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AllHandoffData) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(AllHandoffData allHandoffData) {
            return c.createBuilder(allHandoffData);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<AllHandoffData, Builder> implements AllHandoffDataOrBuilder {
            private Builder() {
                super(AllHandoffData.c);
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.AllHandoffDataOrBuilder
            public String getIdHash() {
                return ((AllHandoffData) this.instance).getIdHash();
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.AllHandoffDataOrBuilder
            public ByteString getIdHashBytes() {
                return ((AllHandoffData) this.instance).getIdHashBytes();
            }

            public Builder setIdHash(String str) {
                copyOnWrite();
                ((AllHandoffData) this.instance).a(str);
                return this;
            }

            public Builder clearIdHash() {
                copyOnWrite();
                ((AllHandoffData) this.instance).e();
                return this;
            }

            public Builder setIdHashBytes(ByteString byteString) {
                copyOnWrite();
                ((AllHandoffData) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.AllHandoffDataOrBuilder
            public List<HandoffData> getHandoffDataListList() {
                return Collections.unmodifiableList(((AllHandoffData) this.instance).getHandoffDataListList());
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.AllHandoffDataOrBuilder
            public int getHandoffDataListCount() {
                return ((AllHandoffData) this.instance).getHandoffDataListCount();
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.AllHandoffDataOrBuilder
            public HandoffData getHandoffDataList(int i) {
                return ((AllHandoffData) this.instance).getHandoffDataList(i);
            }

            public Builder setHandoffDataList(int i, HandoffData handoffData) {
                copyOnWrite();
                ((AllHandoffData) this.instance).a(i, handoffData);
                return this;
            }

            public Builder setHandoffDataList(int i, HandoffData.Builder builder) {
                copyOnWrite();
                ((AllHandoffData) this.instance).a(i, builder);
                return this;
            }

            public Builder addHandoffDataList(HandoffData handoffData) {
                copyOnWrite();
                ((AllHandoffData) this.instance).a(handoffData);
                return this;
            }

            public Builder addHandoffDataList(int i, HandoffData handoffData) {
                copyOnWrite();
                ((AllHandoffData) this.instance).b(i, handoffData);
                return this;
            }

            public Builder addHandoffDataList(HandoffData.Builder builder) {
                copyOnWrite();
                ((AllHandoffData) this.instance).a(builder);
                return this;
            }

            public Builder addHandoffDataList(int i, HandoffData.Builder builder) {
                copyOnWrite();
                ((AllHandoffData) this.instance).b(i, builder);
                return this;
            }

            public Builder addAllHandoffDataList(Iterable<? extends HandoffData> iterable) {
                copyOnWrite();
                ((AllHandoffData) this.instance).a(iterable);
                return this;
            }

            public Builder clearHandoffDataList() {
                copyOnWrite();
                ((AllHandoffData) this.instance).g();
                return this;
            }

            public Builder removeHandoffDataList(int i) {
                copyOnWrite();
                ((AllHandoffData) this.instance).b(i);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new AllHandoffData();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0001\u0000\u0001Ȉ\u0002\u001b", new Object[]{"idHash_", "handoffDataList_", HandoffData.class});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<AllHandoffData> parser = d;
                    if (parser == null) {
                        synchronized (AllHandoffData.class) {
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
            AllHandoffData allHandoffData = new AllHandoffData();
            c = allHandoffData;
            GeneratedMessageLite.registerDefaultInstance(AllHandoffData.class, allHandoffData);
        }

        public static AllHandoffData getDefaultInstance() {
            return c;
        }

        public static Parser<AllHandoffData> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class HandoffData extends GeneratedMessageLite<HandoffData, Builder> implements HandoffDataOrBuilder {
        public static final int APPKEY_FIELD_NUMBER = 1;
        public static final int DATALIST_FIELD_NUMBER = 2;
        private static final HandoffData c;
        private static volatile Parser<HandoffData> d;
        private String a = "";
        private Internal.ProtobufList<Data> b = emptyProtobufList();

        private HandoffData() {
        }

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffDataOrBuilder
        public String getAppKey() {
            return this.a;
        }

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffDataOrBuilder
        public ByteString getAppKeyBytes() {
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
            this.a = getDefaultInstance().getAppKey();
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

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffDataOrBuilder
        public List<Data> getDataListList() {
            return this.b;
        }

        public List<? extends DataOrBuilder> getDataListOrBuilderList() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffDataOrBuilder
        public int getDataListCount() {
            return this.b.size();
        }

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffDataOrBuilder
        public Data getDataList(int i) {
            return this.b.get(i);
        }

        public DataOrBuilder getDataListOrBuilder(int i) {
            return this.b.get(i);
        }

        private void f() {
            if (!this.b.isModifiable()) {
                this.b = GeneratedMessageLite.mutableCopy(this.b);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i, Data data) {
            if (data != null) {
                f();
                this.b.set(i, data);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i, Data.Builder builder) {
            f();
            this.b.set(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Data data) {
            if (data != null) {
                f();
                this.b.add(data);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i, Data data) {
            if (data != null) {
                f();
                this.b.add(i, data);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Data.Builder builder) {
            f();
            this.b.add(builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i, Data.Builder builder) {
            f();
            this.b.add(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Iterable<? extends Data> iterable) {
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

        public static HandoffData parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (HandoffData) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static HandoffData parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (HandoffData) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static HandoffData parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (HandoffData) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static HandoffData parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (HandoffData) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static HandoffData parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (HandoffData) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static HandoffData parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (HandoffData) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static HandoffData parseFrom(InputStream inputStream) throws IOException {
            return (HandoffData) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static HandoffData parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (HandoffData) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static HandoffData parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (HandoffData) parseDelimitedFrom(c, inputStream);
        }

        public static HandoffData parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (HandoffData) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static HandoffData parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (HandoffData) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static HandoffData parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (HandoffData) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(HandoffData handoffData) {
            return c.createBuilder(handoffData);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<HandoffData, Builder> implements HandoffDataOrBuilder {
            private Builder() {
                super(HandoffData.c);
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffDataOrBuilder
            public String getAppKey() {
                return ((HandoffData) this.instance).getAppKey();
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffDataOrBuilder
            public ByteString getAppKeyBytes() {
                return ((HandoffData) this.instance).getAppKeyBytes();
            }

            public Builder setAppKey(String str) {
                copyOnWrite();
                ((HandoffData) this.instance).a(str);
                return this;
            }

            public Builder clearAppKey() {
                copyOnWrite();
                ((HandoffData) this.instance).e();
                return this;
            }

            public Builder setAppKeyBytes(ByteString byteString) {
                copyOnWrite();
                ((HandoffData) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffDataOrBuilder
            public List<Data> getDataListList() {
                return Collections.unmodifiableList(((HandoffData) this.instance).getDataListList());
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffDataOrBuilder
            public int getDataListCount() {
                return ((HandoffData) this.instance).getDataListCount();
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffDataOrBuilder
            public Data getDataList(int i) {
                return ((HandoffData) this.instance).getDataList(i);
            }

            public Builder setDataList(int i, Data data) {
                copyOnWrite();
                ((HandoffData) this.instance).a(i, data);
                return this;
            }

            public Builder setDataList(int i, Data.Builder builder) {
                copyOnWrite();
                ((HandoffData) this.instance).a(i, builder);
                return this;
            }

            public Builder addDataList(Data data) {
                copyOnWrite();
                ((HandoffData) this.instance).a(data);
                return this;
            }

            public Builder addDataList(int i, Data data) {
                copyOnWrite();
                ((HandoffData) this.instance).b(i, data);
                return this;
            }

            public Builder addDataList(Data.Builder builder) {
                copyOnWrite();
                ((HandoffData) this.instance).a(builder);
                return this;
            }

            public Builder addDataList(int i, Data.Builder builder) {
                copyOnWrite();
                ((HandoffData) this.instance).b(i, builder);
                return this;
            }

            public Builder addAllDataList(Iterable<? extends Data> iterable) {
                copyOnWrite();
                ((HandoffData) this.instance).a(iterable);
                return this;
            }

            public Builder clearDataList() {
                copyOnWrite();
                ((HandoffData) this.instance).g();
                return this;
            }

            public Builder removeDataList(int i) {
                copyOnWrite();
                ((HandoffData) this.instance).b(i);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new HandoffData();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0001\u0000\u0001Ȉ\u0002\u001b", new Object[]{"appKey_", "dataList_", Data.class});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<HandoffData> parser = d;
                    if (parser == null) {
                        synchronized (HandoffData.class) {
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
            HandoffData handoffData = new HandoffData();
            c = handoffData;
            GeneratedMessageLite.registerDefaultInstance(HandoffData.class, handoffData);
        }

        public static HandoffData getDefaultInstance() {
            return c;
        }

        public static Parser<HandoffData> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class Data extends GeneratedMessageLite<Data, Builder> implements DataOrBuilder {
        public static final int KEY_FIELD_NUMBER = 1;
        public static final int VALUE_FIELD_NUMBER = 2;
        private static final Data c;
        private static volatile Parser<Data> d;
        private String a = "";
        private ByteString b = ByteString.EMPTY;

        private Data() {
        }

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.DataOrBuilder
        public String getKey() {
            return this.a;
        }

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.DataOrBuilder
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

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.DataOrBuilder
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

        public static Data parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Data) GeneratedMessageLite.parseFrom(c, byteBuffer);
        }

        public static Data parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Data) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
        }

        public static Data parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Data) GeneratedMessageLite.parseFrom(c, byteString);
        }

        public static Data parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Data) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
        }

        public static Data parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Data) GeneratedMessageLite.parseFrom(c, bArr);
        }

        public static Data parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Data) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
        }

        public static Data parseFrom(InputStream inputStream) throws IOException {
            return (Data) GeneratedMessageLite.parseFrom(c, inputStream);
        }

        public static Data parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Data) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
        }

        public static Data parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Data) parseDelimitedFrom(c, inputStream);
        }

        public static Data parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Data) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
        }

        public static Data parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Data) GeneratedMessageLite.parseFrom(c, codedInputStream);
        }

        public static Data parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Data) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return c.createBuilder();
        }

        public static Builder newBuilder(Data data) {
            return c.createBuilder(data);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<Data, Builder> implements DataOrBuilder {
            private Builder() {
                super(Data.c);
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.DataOrBuilder
            public String getKey() {
                return ((Data) this.instance).getKey();
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.DataOrBuilder
            public ByteString getKeyBytes() {
                return ((Data) this.instance).getKeyBytes();
            }

            public Builder setKey(String str) {
                copyOnWrite();
                ((Data) this.instance).a(str);
                return this;
            }

            public Builder clearKey() {
                copyOnWrite();
                ((Data) this.instance).e();
                return this;
            }

            public Builder setKeyBytes(ByteString byteString) {
                copyOnWrite();
                ((Data) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.DataOrBuilder
            public ByteString getValue() {
                return ((Data) this.instance).getValue();
            }

            public Builder setValue(ByteString byteString) {
                copyOnWrite();
                ((Data) this.instance).b(byteString);
                return this;
            }

            public Builder clearValue() {
                copyOnWrite();
                ((Data) this.instance).f();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new Data();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\n", new Object[]{"key_", "value_"});
                case GET_DEFAULT_INSTANCE:
                    return c;
                case GET_PARSER:
                    Parser<Data> parser = d;
                    if (parser == null) {
                        synchronized (Data.class) {
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
            Data data = new Data();
            c = data;
            GeneratedMessageLite.registerDefaultInstance(Data.class, data);
        }

        public static Data getDefaultInstance() {
            return c;
        }

        public static Parser<Data> parser() {
            return c.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class HandoffEvent extends GeneratedMessageLite<HandoffEvent, Builder> implements HandoffEventOrBuilder {
        public static final int APPKEY_FIELD_NUMBER = 2;
        public static final int IDHASH_FIELD_NUMBER = 1;
        public static final int KEY_FIELD_NUMBER = 3;
        public static final int VALUE_FIELD_NUMBER = 4;
        private static final HandoffEvent e;
        private static volatile Parser<HandoffEvent> f;
        private String a = "";
        private String b = "";
        private String c = "";
        private ByteString d = ByteString.EMPTY;

        private HandoffEvent() {
        }

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffEventOrBuilder
        public String getIdHash() {
            return this.a;
        }

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffEventOrBuilder
        public ByteString getIdHashBytes() {
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
            this.a = getDefaultInstance().getIdHash();
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

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffEventOrBuilder
        public String getAppKey() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffEventOrBuilder
        public ByteString getAppKeyBytes() {
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
            this.b = getDefaultInstance().getAppKey();
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

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffEventOrBuilder
        public String getKey() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffEventOrBuilder
        public ByteString getKeyBytes() {
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
            this.c = getDefaultInstance().getKey();
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

        @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffEventOrBuilder
        public ByteString getValue() {
            return this.d;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d(ByteString byteString) {
            if (byteString != null) {
                this.d = byteString;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.d = getDefaultInstance().getValue();
        }

        public static HandoffEvent parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (HandoffEvent) GeneratedMessageLite.parseFrom(e, byteBuffer);
        }

        public static HandoffEvent parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (HandoffEvent) GeneratedMessageLite.parseFrom(e, byteBuffer, extensionRegistryLite);
        }

        public static HandoffEvent parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (HandoffEvent) GeneratedMessageLite.parseFrom(e, byteString);
        }

        public static HandoffEvent parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (HandoffEvent) GeneratedMessageLite.parseFrom(e, byteString, extensionRegistryLite);
        }

        public static HandoffEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (HandoffEvent) GeneratedMessageLite.parseFrom(e, bArr);
        }

        public static HandoffEvent parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (HandoffEvent) GeneratedMessageLite.parseFrom(e, bArr, extensionRegistryLite);
        }

        public static HandoffEvent parseFrom(InputStream inputStream) throws IOException {
            return (HandoffEvent) GeneratedMessageLite.parseFrom(e, inputStream);
        }

        public static HandoffEvent parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (HandoffEvent) GeneratedMessageLite.parseFrom(e, inputStream, extensionRegistryLite);
        }

        public static HandoffEvent parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (HandoffEvent) parseDelimitedFrom(e, inputStream);
        }

        public static HandoffEvent parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (HandoffEvent) parseDelimitedFrom(e, inputStream, extensionRegistryLite);
        }

        public static HandoffEvent parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (HandoffEvent) GeneratedMessageLite.parseFrom(e, codedInputStream);
        }

        public static HandoffEvent parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (HandoffEvent) GeneratedMessageLite.parseFrom(e, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return e.createBuilder();
        }

        public static Builder newBuilder(HandoffEvent handoffEvent) {
            return e.createBuilder(handoffEvent);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<HandoffEvent, Builder> implements HandoffEventOrBuilder {
            private Builder() {
                super(HandoffEvent.e);
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffEventOrBuilder
            public String getIdHash() {
                return ((HandoffEvent) this.instance).getIdHash();
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffEventOrBuilder
            public ByteString getIdHashBytes() {
                return ((HandoffEvent) this.instance).getIdHashBytes();
            }

            public Builder setIdHash(String str) {
                copyOnWrite();
                ((HandoffEvent) this.instance).a(str);
                return this;
            }

            public Builder clearIdHash() {
                copyOnWrite();
                ((HandoffEvent) this.instance).e();
                return this;
            }

            public Builder setIdHashBytes(ByteString byteString) {
                copyOnWrite();
                ((HandoffEvent) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffEventOrBuilder
            public String getAppKey() {
                return ((HandoffEvent) this.instance).getAppKey();
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffEventOrBuilder
            public ByteString getAppKeyBytes() {
                return ((HandoffEvent) this.instance).getAppKeyBytes();
            }

            public Builder setAppKey(String str) {
                copyOnWrite();
                ((HandoffEvent) this.instance).b(str);
                return this;
            }

            public Builder clearAppKey() {
                copyOnWrite();
                ((HandoffEvent) this.instance).f();
                return this;
            }

            public Builder setAppKeyBytes(ByteString byteString) {
                copyOnWrite();
                ((HandoffEvent) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffEventOrBuilder
            public String getKey() {
                return ((HandoffEvent) this.instance).getKey();
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffEventOrBuilder
            public ByteString getKeyBytes() {
                return ((HandoffEvent) this.instance).getKeyBytes();
            }

            public Builder setKey(String str) {
                copyOnWrite();
                ((HandoffEvent) this.instance).c(str);
                return this;
            }

            public Builder clearKey() {
                copyOnWrite();
                ((HandoffEvent) this.instance).g();
                return this;
            }

            public Builder setKeyBytes(ByteString byteString) {
                copyOnWrite();
                ((HandoffEvent) this.instance).c(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.handoff.proto.HandoffServiceProto.HandoffEventOrBuilder
            public ByteString getValue() {
                return ((HandoffEvent) this.instance).getValue();
            }

            public Builder setValue(ByteString byteString) {
                copyOnWrite();
                ((HandoffEvent) this.instance).d(byteString);
                return this;
            }

            public Builder clearValue() {
                copyOnWrite();
                ((HandoffEvent) this.instance).h();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new HandoffEvent();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(e, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ\u0004\n", new Object[]{"idHash_", "appKey_", "key_", "value_"});
                case GET_DEFAULT_INSTANCE:
                    return e;
                case GET_PARSER:
                    Parser<HandoffEvent> parser = f;
                    if (parser == null) {
                        synchronized (HandoffEvent.class) {
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
            HandoffEvent handoffEvent = new HandoffEvent();
            e = handoffEvent;
            GeneratedMessageLite.registerDefaultInstance(HandoffEvent.class, handoffEvent);
        }

        public static HandoffEvent getDefaultInstance() {
            return e;
        }

        public static Parser<HandoffEvent> parser() {
            return e.getParserForType();
        }
    }
}
