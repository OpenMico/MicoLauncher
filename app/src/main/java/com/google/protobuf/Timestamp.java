package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class Timestamp extends GeneratedMessageLite<Timestamp, Builder> implements TimestampOrBuilder {
    public static final int NANOS_FIELD_NUMBER = 2;
    public static final int SECONDS_FIELD_NUMBER = 1;
    private static final Timestamp c;
    private static volatile Parser<Timestamp> d;
    private long a;
    private int b;

    private Timestamp() {
    }

    @Override // com.google.protobuf.TimestampOrBuilder
    public long getSeconds() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(long j) {
        this.a = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        this.a = 0L;
    }

    @Override // com.google.protobuf.TimestampOrBuilder
    public int getNanos() {
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

    public static Timestamp parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Timestamp) GeneratedMessageLite.parseFrom(c, byteBuffer);
    }

    public static Timestamp parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Timestamp) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
    }

    public static Timestamp parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Timestamp) GeneratedMessageLite.parseFrom(c, byteString);
    }

    public static Timestamp parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Timestamp) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
    }

    public static Timestamp parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Timestamp) GeneratedMessageLite.parseFrom(c, bArr);
    }

    public static Timestamp parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Timestamp) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
    }

    public static Timestamp parseFrom(InputStream inputStream) throws IOException {
        return (Timestamp) GeneratedMessageLite.parseFrom(c, inputStream);
    }

    public static Timestamp parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Timestamp) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
    }

    public static Timestamp parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Timestamp) parseDelimitedFrom(c, inputStream);
    }

    public static Timestamp parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Timestamp) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
    }

    public static Timestamp parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Timestamp) GeneratedMessageLite.parseFrom(c, codedInputStream);
    }

    public static Timestamp parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Timestamp) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return c.createBuilder();
    }

    public static Builder newBuilder(Timestamp timestamp) {
        return c.createBuilder(timestamp);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<Timestamp, Builder> implements TimestampOrBuilder {
        private Builder() {
            super(Timestamp.c);
        }

        @Override // com.google.protobuf.TimestampOrBuilder
        public long getSeconds() {
            return ((Timestamp) this.instance).getSeconds();
        }

        public Builder setSeconds(long j) {
            copyOnWrite();
            ((Timestamp) this.instance).a(j);
            return this;
        }

        public Builder clearSeconds() {
            copyOnWrite();
            ((Timestamp) this.instance).e();
            return this;
        }

        @Override // com.google.protobuf.TimestampOrBuilder
        public int getNanos() {
            return ((Timestamp) this.instance).getNanos();
        }

        public Builder setNanos(int i) {
            copyOnWrite();
            ((Timestamp) this.instance).b(i);
            return this;
        }

        public Builder clearNanos() {
            copyOnWrite();
            ((Timestamp) this.instance).f();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Timestamp();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0002\u0002\u0004", new Object[]{"seconds_", "nanos_"});
            case GET_DEFAULT_INSTANCE:
                return c;
            case GET_PARSER:
                Parser<Timestamp> parser = d;
                if (parser == null) {
                    synchronized (Timestamp.class) {
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
        Timestamp timestamp = new Timestamp();
        c = timestamp;
        GeneratedMessageLite.registerDefaultInstance(Timestamp.class, timestamp);
    }

    public static Timestamp getDefaultInstance() {
        return c;
    }

    public static Parser<Timestamp> parser() {
        return c.getParserForType();
    }
}
