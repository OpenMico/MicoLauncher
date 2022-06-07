package com.google.protobuf;

import com.google.protobuf.EnumValue;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.Option;
import com.google.protobuf.SourceContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class Enum extends GeneratedMessageLite<Enum, Builder> implements EnumOrBuilder {
    public static final int ENUMVALUE_FIELD_NUMBER = 2;
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int OPTIONS_FIELD_NUMBER = 3;
    public static final int SOURCE_CONTEXT_FIELD_NUMBER = 4;
    public static final int SYNTAX_FIELD_NUMBER = 5;
    private static final Enum f;
    private static volatile Parser<Enum> g;
    private String a = "";
    private Internal.ProtobufList<EnumValue> b = emptyProtobufList();
    private Internal.ProtobufList<Option> c = emptyProtobufList();
    private SourceContext d;
    private int e;

    private Enum() {
    }

    @Override // com.google.protobuf.EnumOrBuilder
    public String getName() {
        return this.a;
    }

    @Override // com.google.protobuf.EnumOrBuilder
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

    @Override // com.google.protobuf.EnumOrBuilder
    public List<EnumValue> getEnumvalueList() {
        return this.b;
    }

    public List<? extends EnumValueOrBuilder> getEnumvalueOrBuilderList() {
        return this.b;
    }

    @Override // com.google.protobuf.EnumOrBuilder
    public int getEnumvalueCount() {
        return this.b.size();
    }

    @Override // com.google.protobuf.EnumOrBuilder
    public EnumValue getEnumvalue(int i) {
        return this.b.get(i);
    }

    public EnumValueOrBuilder getEnumvalueOrBuilder(int i) {
        return this.b.get(i);
    }

    private void f() {
        if (!this.b.isModifiable()) {
            this.b = GeneratedMessageLite.mutableCopy(this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, EnumValue enumValue) {
        enumValue.getClass();
        f();
        this.b.set(i, enumValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(EnumValue enumValue) {
        enumValue.getClass();
        f();
        this.b.add(enumValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i, EnumValue enumValue) {
        enumValue.getClass();
        f();
        this.b.add(i, enumValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Iterable<? extends EnumValue> iterable) {
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

    @Override // com.google.protobuf.EnumOrBuilder
    public List<Option> getOptionsList() {
        return this.c;
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.c;
    }

    @Override // com.google.protobuf.EnumOrBuilder
    public int getOptionsCount() {
        return this.c.size();
    }

    @Override // com.google.protobuf.EnumOrBuilder
    public Option getOptions(int i) {
        return this.c.get(i);
    }

    public OptionOrBuilder getOptionsOrBuilder(int i) {
        return this.c.get(i);
    }

    private void h() {
        if (!this.c.isModifiable()) {
            this.c = GeneratedMessageLite.mutableCopy(this.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, Option option) {
        option.getClass();
        h();
        this.c.set(i, option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Option option) {
        option.getClass();
        h();
        this.c.add(option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i, Option option) {
        option.getClass();
        h();
        this.c.add(i, option);
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
    public void c(int i) {
        h();
        this.c.remove(i);
    }

    @Override // com.google.protobuf.EnumOrBuilder
    public boolean hasSourceContext() {
        return this.d != null;
    }

    @Override // com.google.protobuf.EnumOrBuilder
    public SourceContext getSourceContext() {
        SourceContext sourceContext = this.d;
        return sourceContext == null ? SourceContext.getDefaultInstance() : sourceContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(SourceContext sourceContext) {
        sourceContext.getClass();
        this.d = sourceContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(SourceContext sourceContext) {
        sourceContext.getClass();
        SourceContext sourceContext2 = this.d;
        if (sourceContext2 == null || sourceContext2 == SourceContext.getDefaultInstance()) {
            this.d = sourceContext;
        } else {
            this.d = SourceContext.newBuilder(this.d).mergeFrom((SourceContext.Builder) sourceContext).buildPartial();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        this.d = null;
    }

    @Override // com.google.protobuf.EnumOrBuilder
    public int getSyntaxValue() {
        return this.e;
    }

    @Override // com.google.protobuf.EnumOrBuilder
    public Syntax getSyntax() {
        Syntax forNumber = Syntax.forNumber(this.e);
        return forNumber == null ? Syntax.UNRECOGNIZED : forNumber;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(int i) {
        this.e = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Syntax syntax) {
        this.e = syntax.getNumber();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        this.e = 0;
    }

    public static Enum parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(f, byteBuffer);
    }

    public static Enum parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(f, byteBuffer, extensionRegistryLite);
    }

    public static Enum parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(f, byteString);
    }

    public static Enum parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(f, byteString, extensionRegistryLite);
    }

    public static Enum parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(f, bArr);
    }

    public static Enum parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(f, bArr, extensionRegistryLite);
    }

    public static Enum parseFrom(InputStream inputStream) throws IOException {
        return (Enum) GeneratedMessageLite.parseFrom(f, inputStream);
    }

    public static Enum parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Enum) GeneratedMessageLite.parseFrom(f, inputStream, extensionRegistryLite);
    }

    public static Enum parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Enum) parseDelimitedFrom(f, inputStream);
    }

    public static Enum parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Enum) parseDelimitedFrom(f, inputStream, extensionRegistryLite);
    }

    public static Enum parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Enum) GeneratedMessageLite.parseFrom(f, codedInputStream);
    }

    public static Enum parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Enum) GeneratedMessageLite.parseFrom(f, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return f.createBuilder();
    }

    public static Builder newBuilder(Enum r1) {
        return f.createBuilder(r1);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<Enum, Builder> implements EnumOrBuilder {
        private Builder() {
            super(Enum.f);
        }

        @Override // com.google.protobuf.EnumOrBuilder
        public String getName() {
            return ((Enum) this.instance).getName();
        }

        @Override // com.google.protobuf.EnumOrBuilder
        public ByteString getNameBytes() {
            return ((Enum) this.instance).getNameBytes();
        }

        public Builder setName(String str) {
            copyOnWrite();
            ((Enum) this.instance).a(str);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((Enum) this.instance).e();
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            copyOnWrite();
            ((Enum) this.instance).a(byteString);
            return this;
        }

        @Override // com.google.protobuf.EnumOrBuilder
        public List<EnumValue> getEnumvalueList() {
            return Collections.unmodifiableList(((Enum) this.instance).getEnumvalueList());
        }

        @Override // com.google.protobuf.EnumOrBuilder
        public int getEnumvalueCount() {
            return ((Enum) this.instance).getEnumvalueCount();
        }

        @Override // com.google.protobuf.EnumOrBuilder
        public EnumValue getEnumvalue(int i) {
            return ((Enum) this.instance).getEnumvalue(i);
        }

        public Builder setEnumvalue(int i, EnumValue enumValue) {
            copyOnWrite();
            ((Enum) this.instance).a(i, enumValue);
            return this;
        }

        public Builder setEnumvalue(int i, EnumValue.Builder builder) {
            copyOnWrite();
            ((Enum) this.instance).a(i, builder.build());
            return this;
        }

        public Builder addEnumvalue(EnumValue enumValue) {
            copyOnWrite();
            ((Enum) this.instance).a(enumValue);
            return this;
        }

        public Builder addEnumvalue(int i, EnumValue enumValue) {
            copyOnWrite();
            ((Enum) this.instance).b(i, enumValue);
            return this;
        }

        public Builder addEnumvalue(EnumValue.Builder builder) {
            copyOnWrite();
            ((Enum) this.instance).a(builder.build());
            return this;
        }

        public Builder addEnumvalue(int i, EnumValue.Builder builder) {
            copyOnWrite();
            ((Enum) this.instance).b(i, builder.build());
            return this;
        }

        public Builder addAllEnumvalue(Iterable<? extends EnumValue> iterable) {
            copyOnWrite();
            ((Enum) this.instance).a(iterable);
            return this;
        }

        public Builder clearEnumvalue() {
            copyOnWrite();
            ((Enum) this.instance).g();
            return this;
        }

        public Builder removeEnumvalue(int i) {
            copyOnWrite();
            ((Enum) this.instance).b(i);
            return this;
        }

        @Override // com.google.protobuf.EnumOrBuilder
        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((Enum) this.instance).getOptionsList());
        }

        @Override // com.google.protobuf.EnumOrBuilder
        public int getOptionsCount() {
            return ((Enum) this.instance).getOptionsCount();
        }

        @Override // com.google.protobuf.EnumOrBuilder
        public Option getOptions(int i) {
            return ((Enum) this.instance).getOptions(i);
        }

        public Builder setOptions(int i, Option option) {
            copyOnWrite();
            ((Enum) this.instance).a(i, option);
            return this;
        }

        public Builder setOptions(int i, Option.Builder builder) {
            copyOnWrite();
            ((Enum) this.instance).a(i, builder.build());
            return this;
        }

        public Builder addOptions(Option option) {
            copyOnWrite();
            ((Enum) this.instance).a(option);
            return this;
        }

        public Builder addOptions(int i, Option option) {
            copyOnWrite();
            ((Enum) this.instance).b(i, option);
            return this;
        }

        public Builder addOptions(Option.Builder builder) {
            copyOnWrite();
            ((Enum) this.instance).a(builder.build());
            return this;
        }

        public Builder addOptions(int i, Option.Builder builder) {
            copyOnWrite();
            ((Enum) this.instance).b(i, builder.build());
            return this;
        }

        public Builder addAllOptions(Iterable<? extends Option> iterable) {
            copyOnWrite();
            ((Enum) this.instance).b(iterable);
            return this;
        }

        public Builder clearOptions() {
            copyOnWrite();
            ((Enum) this.instance).i();
            return this;
        }

        public Builder removeOptions(int i) {
            copyOnWrite();
            ((Enum) this.instance).c(i);
            return this;
        }

        @Override // com.google.protobuf.EnumOrBuilder
        public boolean hasSourceContext() {
            return ((Enum) this.instance).hasSourceContext();
        }

        @Override // com.google.protobuf.EnumOrBuilder
        public SourceContext getSourceContext() {
            return ((Enum) this.instance).getSourceContext();
        }

        public Builder setSourceContext(SourceContext sourceContext) {
            copyOnWrite();
            ((Enum) this.instance).a(sourceContext);
            return this;
        }

        public Builder setSourceContext(SourceContext.Builder builder) {
            copyOnWrite();
            ((Enum) this.instance).a(builder.build());
            return this;
        }

        public Builder mergeSourceContext(SourceContext sourceContext) {
            copyOnWrite();
            ((Enum) this.instance).b(sourceContext);
            return this;
        }

        public Builder clearSourceContext() {
            copyOnWrite();
            ((Enum) this.instance).j();
            return this;
        }

        @Override // com.google.protobuf.EnumOrBuilder
        public int getSyntaxValue() {
            return ((Enum) this.instance).getSyntaxValue();
        }

        public Builder setSyntaxValue(int i) {
            copyOnWrite();
            ((Enum) this.instance).d(i);
            return this;
        }

        @Override // com.google.protobuf.EnumOrBuilder
        public Syntax getSyntax() {
            return ((Enum) this.instance).getSyntax();
        }

        public Builder setSyntax(Syntax syntax) {
            copyOnWrite();
            ((Enum) this.instance).a(syntax);
            return this;
        }

        public Builder clearSyntax() {
            copyOnWrite();
            ((Enum) this.instance).k();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Enum();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(f, "\u0000\u0005\u0000\u0000\u0001\u0005\u0005\u0000\u0002\u0000\u0001Ȉ\u0002\u001b\u0003\u001b\u0004\t\u0005\f", new Object[]{"name_", "enumvalue_", EnumValue.class, "options_", Option.class, "sourceContext_", "syntax_"});
            case GET_DEFAULT_INSTANCE:
                return f;
            case GET_PARSER:
                Parser<Enum> parser = g;
                if (parser == null) {
                    synchronized (Enum.class) {
                        parser = g;
                        if (parser == null) {
                            parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(f);
                            g = parser;
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
        Enum r0 = new Enum();
        f = r0;
        GeneratedMessageLite.registerDefaultInstance(Enum.class, r0);
    }

    public static Enum getDefaultInstance() {
        return f;
    }

    public static Parser<Enum> parser() {
        return f.getParserForType();
    }
}
