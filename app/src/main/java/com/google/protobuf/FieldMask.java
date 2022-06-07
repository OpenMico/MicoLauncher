package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class FieldMask extends GeneratedMessageLite<FieldMask, Builder> implements FieldMaskOrBuilder {
    public static final int PATHS_FIELD_NUMBER = 1;
    private static final FieldMask b;
    private static volatile Parser<FieldMask> c;
    private Internal.ProtobufList<String> a = GeneratedMessageLite.emptyProtobufList();

    private FieldMask() {
    }

    @Override // com.google.protobuf.FieldMaskOrBuilder
    public List<String> getPathsList() {
        return this.a;
    }

    @Override // com.google.protobuf.FieldMaskOrBuilder
    public int getPathsCount() {
        return this.a.size();
    }

    @Override // com.google.protobuf.FieldMaskOrBuilder
    public String getPaths(int i) {
        return this.a.get(i);
    }

    @Override // com.google.protobuf.FieldMaskOrBuilder
    public ByteString getPathsBytes(int i) {
        return ByteString.copyFromUtf8(this.a.get(i));
    }

    private void e() {
        if (!this.a.isModifiable()) {
            this.a = GeneratedMessageLite.mutableCopy(this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, String str) {
        str.getClass();
        e();
        this.a.set(i, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        str.getClass();
        e();
        this.a.add(str);
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
        checkByteStringIsUtf8(byteString);
        e();
        this.a.add(byteString.toStringUtf8());
    }

    public static FieldMask parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (FieldMask) GeneratedMessageLite.parseFrom(b, byteBuffer);
    }

    public static FieldMask parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (FieldMask) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
    }

    public static FieldMask parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (FieldMask) GeneratedMessageLite.parseFrom(b, byteString);
    }

    public static FieldMask parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (FieldMask) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
    }

    public static FieldMask parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (FieldMask) GeneratedMessageLite.parseFrom(b, bArr);
    }

    public static FieldMask parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (FieldMask) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
    }

    public static FieldMask parseFrom(InputStream inputStream) throws IOException {
        return (FieldMask) GeneratedMessageLite.parseFrom(b, inputStream);
    }

    public static FieldMask parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (FieldMask) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
    }

    public static FieldMask parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (FieldMask) parseDelimitedFrom(b, inputStream);
    }

    public static FieldMask parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (FieldMask) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
    }

    public static FieldMask parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (FieldMask) GeneratedMessageLite.parseFrom(b, codedInputStream);
    }

    public static FieldMask parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (FieldMask) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return b.createBuilder();
    }

    public static Builder newBuilder(FieldMask fieldMask) {
        return b.createBuilder(fieldMask);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<FieldMask, Builder> implements FieldMaskOrBuilder {
        private Builder() {
            super(FieldMask.b);
        }

        @Override // com.google.protobuf.FieldMaskOrBuilder
        public List<String> getPathsList() {
            return Collections.unmodifiableList(((FieldMask) this.instance).getPathsList());
        }

        @Override // com.google.protobuf.FieldMaskOrBuilder
        public int getPathsCount() {
            return ((FieldMask) this.instance).getPathsCount();
        }

        @Override // com.google.protobuf.FieldMaskOrBuilder
        public String getPaths(int i) {
            return ((FieldMask) this.instance).getPaths(i);
        }

        @Override // com.google.protobuf.FieldMaskOrBuilder
        public ByteString getPathsBytes(int i) {
            return ((FieldMask) this.instance).getPathsBytes(i);
        }

        public Builder setPaths(int i, String str) {
            copyOnWrite();
            ((FieldMask) this.instance).a(i, str);
            return this;
        }

        public Builder addPaths(String str) {
            copyOnWrite();
            ((FieldMask) this.instance).a(str);
            return this;
        }

        public Builder addAllPaths(Iterable<String> iterable) {
            copyOnWrite();
            ((FieldMask) this.instance).a(iterable);
            return this;
        }

        public Builder clearPaths() {
            copyOnWrite();
            ((FieldMask) this.instance).f();
            return this;
        }

        public Builder addPathsBytes(ByteString byteString) {
            copyOnWrite();
            ((FieldMask) this.instance).a(byteString);
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new FieldMask();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001Ț", new Object[]{"paths_"});
            case GET_DEFAULT_INSTANCE:
                return b;
            case GET_PARSER:
                Parser<FieldMask> parser = c;
                if (parser == null) {
                    synchronized (FieldMask.class) {
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
        FieldMask fieldMask = new FieldMask();
        b = fieldMask;
        GeneratedMessageLite.registerDefaultInstance(FieldMask.class, fieldMask);
    }

    public static FieldMask getDefaultInstance() {
        return b;
    }

    public static Parser<FieldMask> parser() {
        return b.getParserForType();
    }
}
