package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.Option;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class Method extends GeneratedMessageLite<Method, Builder> implements MethodOrBuilder {
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int OPTIONS_FIELD_NUMBER = 6;
    public static final int REQUEST_STREAMING_FIELD_NUMBER = 3;
    public static final int REQUEST_TYPE_URL_FIELD_NUMBER = 2;
    public static final int RESPONSE_STREAMING_FIELD_NUMBER = 5;
    public static final int RESPONSE_TYPE_URL_FIELD_NUMBER = 4;
    public static final int SYNTAX_FIELD_NUMBER = 7;
    private static final Method h;
    private static volatile Parser<Method> i;
    private boolean c;
    private boolean e;
    private int g;
    private String a = "";
    private String b = "";
    private String d = "";
    private Internal.ProtobufList<Option> f = emptyProtobufList();

    private Method() {
    }

    @Override // com.google.protobuf.MethodOrBuilder
    public String getName() {
        return this.a;
    }

    @Override // com.google.protobuf.MethodOrBuilder
    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        str.getClass();
        this.a = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        this.a = getDefaultInstance().getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        this.a = byteString.toStringUtf8();
    }

    @Override // com.google.protobuf.MethodOrBuilder
    public String getRequestTypeUrl() {
        return this.b;
    }

    @Override // com.google.protobuf.MethodOrBuilder
    public ByteString getRequestTypeUrlBytes() {
        return ByteString.copyFromUtf8(this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        str.getClass();
        this.b = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        this.b = getDefaultInstance().getRequestTypeUrl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        this.b = byteString.toStringUtf8();
    }

    @Override // com.google.protobuf.MethodOrBuilder
    public boolean getRequestStreaming() {
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

    @Override // com.google.protobuf.MethodOrBuilder
    public String getResponseTypeUrl() {
        return this.d;
    }

    @Override // com.google.protobuf.MethodOrBuilder
    public ByteString getResponseTypeUrlBytes() {
        return ByteString.copyFromUtf8(this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(String str) {
        str.getClass();
        this.d = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        this.d = getDefaultInstance().getResponseTypeUrl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        this.d = byteString.toStringUtf8();
    }

    @Override // com.google.protobuf.MethodOrBuilder
    public boolean getResponseStreaming() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(boolean z) {
        this.e = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        this.e = false;
    }

    @Override // com.google.protobuf.MethodOrBuilder
    public List<Option> getOptionsList() {
        return this.f;
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.f;
    }

    @Override // com.google.protobuf.MethodOrBuilder
    public int getOptionsCount() {
        return this.f.size();
    }

    @Override // com.google.protobuf.MethodOrBuilder
    public Option getOptions(int i2) {
        return this.f.get(i2);
    }

    public OptionOrBuilder getOptionsOrBuilder(int i2) {
        return this.f.get(i2);
    }

    private void j() {
        if (!this.f.isModifiable()) {
            this.f = GeneratedMessageLite.mutableCopy(this.f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2, Option option) {
        option.getClass();
        j();
        this.f.set(i2, option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Option option) {
        option.getClass();
        j();
        this.f.add(option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i2, Option option) {
        option.getClass();
        j();
        this.f.add(i2, option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Iterable<? extends Option> iterable) {
        j();
        AbstractMessageLite.addAll((Iterable) iterable, (List) this.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        this.f = emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i2) {
        j();
        this.f.remove(i2);
    }

    @Override // com.google.protobuf.MethodOrBuilder
    public int getSyntaxValue() {
        return this.g;
    }

    @Override // com.google.protobuf.MethodOrBuilder
    public Syntax getSyntax() {
        Syntax forNumber = Syntax.forNumber(this.g);
        return forNumber == null ? Syntax.UNRECOGNIZED : forNumber;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(int i2) {
        this.g = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Syntax syntax) {
        this.g = syntax.getNumber();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void l() {
        this.g = 0;
    }

    public static Method parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Method) GeneratedMessageLite.parseFrom(h, byteBuffer);
    }

    public static Method parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Method) GeneratedMessageLite.parseFrom(h, byteBuffer, extensionRegistryLite);
    }

    public static Method parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Method) GeneratedMessageLite.parseFrom(h, byteString);
    }

    public static Method parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Method) GeneratedMessageLite.parseFrom(h, byteString, extensionRegistryLite);
    }

    public static Method parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Method) GeneratedMessageLite.parseFrom(h, bArr);
    }

    public static Method parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Method) GeneratedMessageLite.parseFrom(h, bArr, extensionRegistryLite);
    }

    public static Method parseFrom(InputStream inputStream) throws IOException {
        return (Method) GeneratedMessageLite.parseFrom(h, inputStream);
    }

    public static Method parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Method) GeneratedMessageLite.parseFrom(h, inputStream, extensionRegistryLite);
    }

    public static Method parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Method) parseDelimitedFrom(h, inputStream);
    }

    public static Method parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Method) parseDelimitedFrom(h, inputStream, extensionRegistryLite);
    }

    public static Method parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Method) GeneratedMessageLite.parseFrom(h, codedInputStream);
    }

    public static Method parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Method) GeneratedMessageLite.parseFrom(h, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return h.createBuilder();
    }

    public static Builder newBuilder(Method method) {
        return h.createBuilder(method);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<Method, Builder> implements MethodOrBuilder {
        private Builder() {
            super(Method.h);
        }

        @Override // com.google.protobuf.MethodOrBuilder
        public String getName() {
            return ((Method) this.instance).getName();
        }

        @Override // com.google.protobuf.MethodOrBuilder
        public ByteString getNameBytes() {
            return ((Method) this.instance).getNameBytes();
        }

        public Builder setName(String str) {
            copyOnWrite();
            ((Method) this.instance).a(str);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((Method) this.instance).e();
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            copyOnWrite();
            ((Method) this.instance).a(byteString);
            return this;
        }

        @Override // com.google.protobuf.MethodOrBuilder
        public String getRequestTypeUrl() {
            return ((Method) this.instance).getRequestTypeUrl();
        }

        @Override // com.google.protobuf.MethodOrBuilder
        public ByteString getRequestTypeUrlBytes() {
            return ((Method) this.instance).getRequestTypeUrlBytes();
        }

        public Builder setRequestTypeUrl(String str) {
            copyOnWrite();
            ((Method) this.instance).b(str);
            return this;
        }

        public Builder clearRequestTypeUrl() {
            copyOnWrite();
            ((Method) this.instance).f();
            return this;
        }

        public Builder setRequestTypeUrlBytes(ByteString byteString) {
            copyOnWrite();
            ((Method) this.instance).b(byteString);
            return this;
        }

        @Override // com.google.protobuf.MethodOrBuilder
        public boolean getRequestStreaming() {
            return ((Method) this.instance).getRequestStreaming();
        }

        public Builder setRequestStreaming(boolean z) {
            copyOnWrite();
            ((Method) this.instance).a(z);
            return this;
        }

        public Builder clearRequestStreaming() {
            copyOnWrite();
            ((Method) this.instance).g();
            return this;
        }

        @Override // com.google.protobuf.MethodOrBuilder
        public String getResponseTypeUrl() {
            return ((Method) this.instance).getResponseTypeUrl();
        }

        @Override // com.google.protobuf.MethodOrBuilder
        public ByteString getResponseTypeUrlBytes() {
            return ((Method) this.instance).getResponseTypeUrlBytes();
        }

        public Builder setResponseTypeUrl(String str) {
            copyOnWrite();
            ((Method) this.instance).c(str);
            return this;
        }

        public Builder clearResponseTypeUrl() {
            copyOnWrite();
            ((Method) this.instance).h();
            return this;
        }

        public Builder setResponseTypeUrlBytes(ByteString byteString) {
            copyOnWrite();
            ((Method) this.instance).c(byteString);
            return this;
        }

        @Override // com.google.protobuf.MethodOrBuilder
        public boolean getResponseStreaming() {
            return ((Method) this.instance).getResponseStreaming();
        }

        public Builder setResponseStreaming(boolean z) {
            copyOnWrite();
            ((Method) this.instance).b(z);
            return this;
        }

        public Builder clearResponseStreaming() {
            copyOnWrite();
            ((Method) this.instance).i();
            return this;
        }

        @Override // com.google.protobuf.MethodOrBuilder
        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((Method) this.instance).getOptionsList());
        }

        @Override // com.google.protobuf.MethodOrBuilder
        public int getOptionsCount() {
            return ((Method) this.instance).getOptionsCount();
        }

        @Override // com.google.protobuf.MethodOrBuilder
        public Option getOptions(int i) {
            return ((Method) this.instance).getOptions(i);
        }

        public Builder setOptions(int i, Option option) {
            copyOnWrite();
            ((Method) this.instance).a(i, option);
            return this;
        }

        public Builder setOptions(int i, Option.Builder builder) {
            copyOnWrite();
            ((Method) this.instance).a(i, builder.build());
            return this;
        }

        public Builder addOptions(Option option) {
            copyOnWrite();
            ((Method) this.instance).a(option);
            return this;
        }

        public Builder addOptions(int i, Option option) {
            copyOnWrite();
            ((Method) this.instance).b(i, option);
            return this;
        }

        public Builder addOptions(Option.Builder builder) {
            copyOnWrite();
            ((Method) this.instance).a(builder.build());
            return this;
        }

        public Builder addOptions(int i, Option.Builder builder) {
            copyOnWrite();
            ((Method) this.instance).b(i, builder.build());
            return this;
        }

        public Builder addAllOptions(Iterable<? extends Option> iterable) {
            copyOnWrite();
            ((Method) this.instance).a(iterable);
            return this;
        }

        public Builder clearOptions() {
            copyOnWrite();
            ((Method) this.instance).k();
            return this;
        }

        public Builder removeOptions(int i) {
            copyOnWrite();
            ((Method) this.instance).b(i);
            return this;
        }

        @Override // com.google.protobuf.MethodOrBuilder
        public int getSyntaxValue() {
            return ((Method) this.instance).getSyntaxValue();
        }

        public Builder setSyntaxValue(int i) {
            copyOnWrite();
            ((Method) this.instance).c(i);
            return this;
        }

        @Override // com.google.protobuf.MethodOrBuilder
        public Syntax getSyntax() {
            return ((Method) this.instance).getSyntax();
        }

        public Builder setSyntax(Syntax syntax) {
            copyOnWrite();
            ((Method) this.instance).a(syntax);
            return this;
        }

        public Builder clearSyntax() {
            copyOnWrite();
            ((Method) this.instance).l();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Method();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(h, "\u0000\u0007\u0000\u0000\u0001\u0007\u0007\u0000\u0001\u0000\u0001Ȉ\u0002Ȉ\u0003\u0007\u0004Ȉ\u0005\u0007\u0006\u001b\u0007\f", new Object[]{"name_", "requestTypeUrl_", "requestStreaming_", "responseTypeUrl_", "responseStreaming_", "options_", Option.class, "syntax_"});
            case GET_DEFAULT_INSTANCE:
                return h;
            case GET_PARSER:
                Parser<Method> parser = i;
                if (parser == null) {
                    synchronized (Method.class) {
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
        Method method = new Method();
        h = method;
        GeneratedMessageLite.registerDefaultInstance(Method.class, method);
    }

    public static Method getDefaultInstance() {
        return h;
    }

    public static Parser<Method> parser() {
        return h.getParserForType();
    }
}
