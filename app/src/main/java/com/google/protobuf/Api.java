package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.Method;
import com.google.protobuf.Mixin;
import com.google.protobuf.Option;
import com.google.protobuf.SourceContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class Api extends GeneratedMessageLite<Api, Builder> implements ApiOrBuilder {
    public static final int METHODS_FIELD_NUMBER = 2;
    public static final int MIXINS_FIELD_NUMBER = 6;
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int OPTIONS_FIELD_NUMBER = 3;
    public static final int SOURCE_CONTEXT_FIELD_NUMBER = 5;
    public static final int SYNTAX_FIELD_NUMBER = 7;
    public static final int VERSION_FIELD_NUMBER = 4;
    private static final Api h;
    private static volatile Parser<Api> i;
    private SourceContext e;
    private int g;
    private String a = "";
    private Internal.ProtobufList<Method> b = emptyProtobufList();
    private Internal.ProtobufList<Option> c = emptyProtobufList();
    private String d = "";
    private Internal.ProtobufList<Mixin> f = emptyProtobufList();

    private Api() {
    }

    @Override // com.google.protobuf.ApiOrBuilder
    public String getName() {
        return this.a;
    }

    @Override // com.google.protobuf.ApiOrBuilder
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

    @Override // com.google.protobuf.ApiOrBuilder
    public List<Method> getMethodsList() {
        return this.b;
    }

    public List<? extends MethodOrBuilder> getMethodsOrBuilderList() {
        return this.b;
    }

    @Override // com.google.protobuf.ApiOrBuilder
    public int getMethodsCount() {
        return this.b.size();
    }

    @Override // com.google.protobuf.ApiOrBuilder
    public Method getMethods(int i2) {
        return this.b.get(i2);
    }

    public MethodOrBuilder getMethodsOrBuilder(int i2) {
        return this.b.get(i2);
    }

    private void f() {
        if (!this.b.isModifiable()) {
            this.b = GeneratedMessageLite.mutableCopy(this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2, Method method) {
        method.getClass();
        f();
        this.b.set(i2, method);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Method method) {
        method.getClass();
        f();
        this.b.add(method);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i2, Method method) {
        method.getClass();
        f();
        this.b.add(i2, method);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Iterable<? extends Method> iterable) {
        f();
        AbstractMessageLite.addAll((Iterable) iterable, (List) this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        this.b = emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i2) {
        f();
        this.b.remove(i2);
    }

    @Override // com.google.protobuf.ApiOrBuilder
    public List<Option> getOptionsList() {
        return this.c;
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.c;
    }

    @Override // com.google.protobuf.ApiOrBuilder
    public int getOptionsCount() {
        return this.c.size();
    }

    @Override // com.google.protobuf.ApiOrBuilder
    public Option getOptions(int i2) {
        return this.c.get(i2);
    }

    public OptionOrBuilder getOptionsOrBuilder(int i2) {
        return this.c.get(i2);
    }

    private void h() {
        if (!this.c.isModifiable()) {
            this.c = GeneratedMessageLite.mutableCopy(this.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2, Option option) {
        option.getClass();
        h();
        this.c.set(i2, option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Option option) {
        option.getClass();
        h();
        this.c.add(option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i2, Option option) {
        option.getClass();
        h();
        this.c.add(i2, option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Iterable<? extends Option> iterable) {
        h();
        AbstractMessageLite.addAll((Iterable) iterable, (List) this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        this.c = emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(int i2) {
        h();
        this.c.remove(i2);
    }

    @Override // com.google.protobuf.ApiOrBuilder
    public String getVersion() {
        return this.d;
    }

    @Override // com.google.protobuf.ApiOrBuilder
    public ByteString getVersionBytes() {
        return ByteString.copyFromUtf8(this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        str.getClass();
        this.d = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        this.d = getDefaultInstance().getVersion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        this.d = byteString.toStringUtf8();
    }

    @Override // com.google.protobuf.ApiOrBuilder
    public boolean hasSourceContext() {
        return this.e != null;
    }

    @Override // com.google.protobuf.ApiOrBuilder
    public SourceContext getSourceContext() {
        SourceContext sourceContext = this.e;
        return sourceContext == null ? SourceContext.getDefaultInstance() : sourceContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(SourceContext sourceContext) {
        sourceContext.getClass();
        this.e = sourceContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(SourceContext sourceContext) {
        sourceContext.getClass();
        SourceContext sourceContext2 = this.e;
        if (sourceContext2 == null || sourceContext2 == SourceContext.getDefaultInstance()) {
            this.e = sourceContext;
        } else {
            this.e = SourceContext.newBuilder(this.e).mergeFrom((SourceContext.Builder) sourceContext).buildPartial();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        this.e = null;
    }

    @Override // com.google.protobuf.ApiOrBuilder
    public List<Mixin> getMixinsList() {
        return this.f;
    }

    public List<? extends MixinOrBuilder> getMixinsOrBuilderList() {
        return this.f;
    }

    @Override // com.google.protobuf.ApiOrBuilder
    public int getMixinsCount() {
        return this.f.size();
    }

    @Override // com.google.protobuf.ApiOrBuilder
    public Mixin getMixins(int i2) {
        return this.f.get(i2);
    }

    public MixinOrBuilder getMixinsOrBuilder(int i2) {
        return this.f.get(i2);
    }

    private void l() {
        if (!this.f.isModifiable()) {
            this.f = GeneratedMessageLite.mutableCopy(this.f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2, Mixin mixin) {
        mixin.getClass();
        l();
        this.f.set(i2, mixin);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Mixin mixin) {
        mixin.getClass();
        l();
        this.f.add(mixin);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i2, Mixin mixin) {
        mixin.getClass();
        l();
        this.f.add(i2, mixin);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Iterable<? extends Mixin> iterable) {
        l();
        AbstractMessageLite.addAll((Iterable) iterable, (List) this.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void m() {
        this.f = emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(int i2) {
        l();
        this.f.remove(i2);
    }

    @Override // com.google.protobuf.ApiOrBuilder
    public int getSyntaxValue() {
        return this.g;
    }

    @Override // com.google.protobuf.ApiOrBuilder
    public Syntax getSyntax() {
        Syntax forNumber = Syntax.forNumber(this.g);
        return forNumber == null ? Syntax.UNRECOGNIZED : forNumber;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(int i2) {
        this.g = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Syntax syntax) {
        this.g = syntax.getNumber();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void n() {
        this.g = 0;
    }

    public static Api parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Api) GeneratedMessageLite.parseFrom(h, byteBuffer);
    }

    public static Api parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Api) GeneratedMessageLite.parseFrom(h, byteBuffer, extensionRegistryLite);
    }

    public static Api parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Api) GeneratedMessageLite.parseFrom(h, byteString);
    }

    public static Api parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Api) GeneratedMessageLite.parseFrom(h, byteString, extensionRegistryLite);
    }

    public static Api parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Api) GeneratedMessageLite.parseFrom(h, bArr);
    }

    public static Api parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Api) GeneratedMessageLite.parseFrom(h, bArr, extensionRegistryLite);
    }

    public static Api parseFrom(InputStream inputStream) throws IOException {
        return (Api) GeneratedMessageLite.parseFrom(h, inputStream);
    }

    public static Api parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Api) GeneratedMessageLite.parseFrom(h, inputStream, extensionRegistryLite);
    }

    public static Api parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Api) parseDelimitedFrom(h, inputStream);
    }

    public static Api parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Api) parseDelimitedFrom(h, inputStream, extensionRegistryLite);
    }

    public static Api parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Api) GeneratedMessageLite.parseFrom(h, codedInputStream);
    }

    public static Api parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Api) GeneratedMessageLite.parseFrom(h, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return h.createBuilder();
    }

    public static Builder newBuilder(Api api) {
        return h.createBuilder(api);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<Api, Builder> implements ApiOrBuilder {
        private Builder() {
            super(Api.h);
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public String getName() {
            return ((Api) this.instance).getName();
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public ByteString getNameBytes() {
            return ((Api) this.instance).getNameBytes();
        }

        public Builder setName(String str) {
            copyOnWrite();
            ((Api) this.instance).a(str);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((Api) this.instance).e();
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            copyOnWrite();
            ((Api) this.instance).a(byteString);
            return this;
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public List<Method> getMethodsList() {
            return Collections.unmodifiableList(((Api) this.instance).getMethodsList());
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public int getMethodsCount() {
            return ((Api) this.instance).getMethodsCount();
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public Method getMethods(int i) {
            return ((Api) this.instance).getMethods(i);
        }

        public Builder setMethods(int i, Method method) {
            copyOnWrite();
            ((Api) this.instance).a(i, method);
            return this;
        }

        public Builder setMethods(int i, Method.Builder builder) {
            copyOnWrite();
            ((Api) this.instance).a(i, builder.build());
            return this;
        }

        public Builder addMethods(Method method) {
            copyOnWrite();
            ((Api) this.instance).a(method);
            return this;
        }

        public Builder addMethods(int i, Method method) {
            copyOnWrite();
            ((Api) this.instance).b(i, method);
            return this;
        }

        public Builder addMethods(Method.Builder builder) {
            copyOnWrite();
            ((Api) this.instance).a(builder.build());
            return this;
        }

        public Builder addMethods(int i, Method.Builder builder) {
            copyOnWrite();
            ((Api) this.instance).b(i, builder.build());
            return this;
        }

        public Builder addAllMethods(Iterable<? extends Method> iterable) {
            copyOnWrite();
            ((Api) this.instance).a(iterable);
            return this;
        }

        public Builder clearMethods() {
            copyOnWrite();
            ((Api) this.instance).g();
            return this;
        }

        public Builder removeMethods(int i) {
            copyOnWrite();
            ((Api) this.instance).b(i);
            return this;
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((Api) this.instance).getOptionsList());
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public int getOptionsCount() {
            return ((Api) this.instance).getOptionsCount();
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public Option getOptions(int i) {
            return ((Api) this.instance).getOptions(i);
        }

        public Builder setOptions(int i, Option option) {
            copyOnWrite();
            ((Api) this.instance).a(i, option);
            return this;
        }

        public Builder setOptions(int i, Option.Builder builder) {
            copyOnWrite();
            ((Api) this.instance).a(i, builder.build());
            return this;
        }

        public Builder addOptions(Option option) {
            copyOnWrite();
            ((Api) this.instance).a(option);
            return this;
        }

        public Builder addOptions(int i, Option option) {
            copyOnWrite();
            ((Api) this.instance).b(i, option);
            return this;
        }

        public Builder addOptions(Option.Builder builder) {
            copyOnWrite();
            ((Api) this.instance).a(builder.build());
            return this;
        }

        public Builder addOptions(int i, Option.Builder builder) {
            copyOnWrite();
            ((Api) this.instance).b(i, builder.build());
            return this;
        }

        public Builder addAllOptions(Iterable<? extends Option> iterable) {
            copyOnWrite();
            ((Api) this.instance).b(iterable);
            return this;
        }

        public Builder clearOptions() {
            copyOnWrite();
            ((Api) this.instance).i();
            return this;
        }

        public Builder removeOptions(int i) {
            copyOnWrite();
            ((Api) this.instance).c(i);
            return this;
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public String getVersion() {
            return ((Api) this.instance).getVersion();
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public ByteString getVersionBytes() {
            return ((Api) this.instance).getVersionBytes();
        }

        public Builder setVersion(String str) {
            copyOnWrite();
            ((Api) this.instance).b(str);
            return this;
        }

        public Builder clearVersion() {
            copyOnWrite();
            ((Api) this.instance).j();
            return this;
        }

        public Builder setVersionBytes(ByteString byteString) {
            copyOnWrite();
            ((Api) this.instance).b(byteString);
            return this;
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public boolean hasSourceContext() {
            return ((Api) this.instance).hasSourceContext();
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public SourceContext getSourceContext() {
            return ((Api) this.instance).getSourceContext();
        }

        public Builder setSourceContext(SourceContext sourceContext) {
            copyOnWrite();
            ((Api) this.instance).a(sourceContext);
            return this;
        }

        public Builder setSourceContext(SourceContext.Builder builder) {
            copyOnWrite();
            ((Api) this.instance).a(builder.build());
            return this;
        }

        public Builder mergeSourceContext(SourceContext sourceContext) {
            copyOnWrite();
            ((Api) this.instance).b(sourceContext);
            return this;
        }

        public Builder clearSourceContext() {
            copyOnWrite();
            ((Api) this.instance).k();
            return this;
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public List<Mixin> getMixinsList() {
            return Collections.unmodifiableList(((Api) this.instance).getMixinsList());
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public int getMixinsCount() {
            return ((Api) this.instance).getMixinsCount();
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public Mixin getMixins(int i) {
            return ((Api) this.instance).getMixins(i);
        }

        public Builder setMixins(int i, Mixin mixin) {
            copyOnWrite();
            ((Api) this.instance).a(i, mixin);
            return this;
        }

        public Builder setMixins(int i, Mixin.Builder builder) {
            copyOnWrite();
            ((Api) this.instance).a(i, builder.build());
            return this;
        }

        public Builder addMixins(Mixin mixin) {
            copyOnWrite();
            ((Api) this.instance).a(mixin);
            return this;
        }

        public Builder addMixins(int i, Mixin mixin) {
            copyOnWrite();
            ((Api) this.instance).b(i, mixin);
            return this;
        }

        public Builder addMixins(Mixin.Builder builder) {
            copyOnWrite();
            ((Api) this.instance).a(builder.build());
            return this;
        }

        public Builder addMixins(int i, Mixin.Builder builder) {
            copyOnWrite();
            ((Api) this.instance).b(i, builder.build());
            return this;
        }

        public Builder addAllMixins(Iterable<? extends Mixin> iterable) {
            copyOnWrite();
            ((Api) this.instance).c(iterable);
            return this;
        }

        public Builder clearMixins() {
            copyOnWrite();
            ((Api) this.instance).m();
            return this;
        }

        public Builder removeMixins(int i) {
            copyOnWrite();
            ((Api) this.instance).d(i);
            return this;
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public int getSyntaxValue() {
            return ((Api) this.instance).getSyntaxValue();
        }

        public Builder setSyntaxValue(int i) {
            copyOnWrite();
            ((Api) this.instance).e(i);
            return this;
        }

        @Override // com.google.protobuf.ApiOrBuilder
        public Syntax getSyntax() {
            return ((Api) this.instance).getSyntax();
        }

        public Builder setSyntax(Syntax syntax) {
            copyOnWrite();
            ((Api) this.instance).a(syntax);
            return this;
        }

        public Builder clearSyntax() {
            copyOnWrite();
            ((Api) this.instance).n();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Api();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(h, "\u0000\u0007\u0000\u0000\u0001\u0007\u0007\u0000\u0003\u0000\u0001Ȉ\u0002\u001b\u0003\u001b\u0004Ȉ\u0005\t\u0006\u001b\u0007\f", new Object[]{"name_", "methods_", Method.class, "options_", Option.class, "version_", "sourceContext_", "mixins_", Mixin.class, "syntax_"});
            case GET_DEFAULT_INSTANCE:
                return h;
            case GET_PARSER:
                Parser<Api> parser = i;
                if (parser == null) {
                    synchronized (Api.class) {
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
        Api api = new Api();
        h = api;
        GeneratedMessageLite.registerDefaultInstance(Api.class, api);
    }

    public static Api getDefaultInstance() {
        return h;
    }

    public static Parser<Api> parser() {
        return h.getParserForType();
    }
}
