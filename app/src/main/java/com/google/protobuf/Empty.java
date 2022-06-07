package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public final class Empty extends GeneratedMessageLite<Empty, Builder> implements EmptyOrBuilder {
    private static final Empty a;
    private static volatile Parser<Empty> b;

    private Empty() {
    }

    public static Empty parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Empty) GeneratedMessageLite.parseFrom(a, byteBuffer);
    }

    public static Empty parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Empty) GeneratedMessageLite.parseFrom(a, byteBuffer, extensionRegistryLite);
    }

    public static Empty parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Empty) GeneratedMessageLite.parseFrom(a, byteString);
    }

    public static Empty parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Empty) GeneratedMessageLite.parseFrom(a, byteString, extensionRegistryLite);
    }

    public static Empty parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Empty) GeneratedMessageLite.parseFrom(a, bArr);
    }

    public static Empty parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Empty) GeneratedMessageLite.parseFrom(a, bArr, extensionRegistryLite);
    }

    public static Empty parseFrom(InputStream inputStream) throws IOException {
        return (Empty) GeneratedMessageLite.parseFrom(a, inputStream);
    }

    public static Empty parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Empty) GeneratedMessageLite.parseFrom(a, inputStream, extensionRegistryLite);
    }

    public static Empty parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Empty) parseDelimitedFrom(a, inputStream);
    }

    public static Empty parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Empty) parseDelimitedFrom(a, inputStream, extensionRegistryLite);
    }

    public static Empty parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Empty) GeneratedMessageLite.parseFrom(a, codedInputStream);
    }

    public static Empty parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Empty) GeneratedMessageLite.parseFrom(a, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return a.createBuilder();
    }

    public static Builder newBuilder(Empty empty) {
        return a.createBuilder(empty);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<Empty, Builder> implements EmptyOrBuilder {
        private Builder() {
            super(Empty.a);
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Empty();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(a, "\u0000\u0000", null);
            case GET_DEFAULT_INSTANCE:
                return a;
            case GET_PARSER:
                Parser<Empty> parser = b;
                if (parser == null) {
                    synchronized (Empty.class) {
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
        Empty empty = new Empty();
        a = empty;
        GeneratedMessageLite.registerDefaultInstance(Empty.class, empty);
    }

    public static Empty getDefaultInstance() {
        return a;
    }

    public static Parser<Empty> parser() {
        return a.getParserForType();
    }
}
