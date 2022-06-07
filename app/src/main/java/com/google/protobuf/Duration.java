package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class Duration extends GeneratedMessageLite<Duration, Builder> implements DurationOrBuilder {
    public static final int NANOS_FIELD_NUMBER = 2;
    public static final int SECONDS_FIELD_NUMBER = 1;
    private static final Duration c;
    private static volatile Parser<Duration> d;
    private long a;
    private int b;

    private Duration() {
    }

    @Override // com.google.protobuf.DurationOrBuilder
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

    @Override // com.google.protobuf.DurationOrBuilder
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

    public static Duration parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Duration) GeneratedMessageLite.parseFrom(c, byteBuffer);
    }

    public static Duration parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Duration) GeneratedMessageLite.parseFrom(c, byteBuffer, extensionRegistryLite);
    }

    public static Duration parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Duration) GeneratedMessageLite.parseFrom(c, byteString);
    }

    public static Duration parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Duration) GeneratedMessageLite.parseFrom(c, byteString, extensionRegistryLite);
    }

    public static Duration parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Duration) GeneratedMessageLite.parseFrom(c, bArr);
    }

    public static Duration parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Duration) GeneratedMessageLite.parseFrom(c, bArr, extensionRegistryLite);
    }

    public static Duration parseFrom(InputStream inputStream) throws IOException {
        return (Duration) GeneratedMessageLite.parseFrom(c, inputStream);
    }

    public static Duration parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Duration) GeneratedMessageLite.parseFrom(c, inputStream, extensionRegistryLite);
    }

    public static Duration parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Duration) parseDelimitedFrom(c, inputStream);
    }

    public static Duration parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Duration) parseDelimitedFrom(c, inputStream, extensionRegistryLite);
    }

    public static Duration parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Duration) GeneratedMessageLite.parseFrom(c, codedInputStream);
    }

    public static Duration parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Duration) GeneratedMessageLite.parseFrom(c, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return c.createBuilder();
    }

    public static Builder newBuilder(Duration duration) {
        return c.createBuilder(duration);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<Duration, Builder> implements DurationOrBuilder {
        private Builder() {
            super(Duration.c);
        }

        @Override // com.google.protobuf.DurationOrBuilder
        public long getSeconds() {
            return ((Duration) this.instance).getSeconds();
        }

        public Builder setSeconds(long j) {
            copyOnWrite();
            ((Duration) this.instance).a(j);
            return this;
        }

        public Builder clearSeconds() {
            copyOnWrite();
            ((Duration) this.instance).e();
            return this;
        }

        @Override // com.google.protobuf.DurationOrBuilder
        public int getNanos() {
            return ((Duration) this.instance).getNanos();
        }

        public Builder setNanos(int i) {
            copyOnWrite();
            ((Duration) this.instance).b(i);
            return this;
        }

        public Builder clearNanos() {
            copyOnWrite();
            ((Duration) this.instance).f();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Duration();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(c, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0002\u0002\u0004", new Object[]{"seconds_", "nanos_"});
            case GET_DEFAULT_INSTANCE:
                return c;
            case GET_PARSER:
                Parser<Duration> parser = d;
                if (parser == null) {
                    synchronized (Duration.class) {
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
        Duration duration = new Duration();
        c = duration;
        GeneratedMessageLite.registerDefaultInstance(Duration.class, duration);
    }

    public static Duration getDefaultInstance() {
        return c;
    }

    public static Parser<Duration> parser() {
        return c.getParserForType();
    }
}
