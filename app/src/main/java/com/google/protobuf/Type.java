package com.google.protobuf;

import com.google.protobuf.Field;
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
public final class Type extends GeneratedMessageLite<Type, Builder> implements TypeOrBuilder {
    public static final int FIELDS_FIELD_NUMBER = 2;
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int ONEOFS_FIELD_NUMBER = 3;
    public static final int OPTIONS_FIELD_NUMBER = 4;
    public static final int SOURCE_CONTEXT_FIELD_NUMBER = 5;
    public static final int SYNTAX_FIELD_NUMBER = 6;
    private static final Type g;
    private static volatile Parser<Type> h;
    private String a = "";
    private Internal.ProtobufList<Field> b = emptyProtobufList();
    private Internal.ProtobufList<String> c = GeneratedMessageLite.emptyProtobufList();
    private Internal.ProtobufList<Option> d = emptyProtobufList();
    private SourceContext e;
    private int f;

    private Type() {
    }

    @Override // com.google.protobuf.TypeOrBuilder
    public String getName() {
        return this.a;
    }

    @Override // com.google.protobuf.TypeOrBuilder
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

    @Override // com.google.protobuf.TypeOrBuilder
    public List<Field> getFieldsList() {
        return this.b;
    }

    public List<? extends FieldOrBuilder> getFieldsOrBuilderList() {
        return this.b;
    }

    @Override // com.google.protobuf.TypeOrBuilder
    public int getFieldsCount() {
        return this.b.size();
    }

    @Override // com.google.protobuf.TypeOrBuilder
    public Field getFields(int i) {
        return this.b.get(i);
    }

    public FieldOrBuilder getFieldsOrBuilder(int i) {
        return this.b.get(i);
    }

    private void f() {
        if (!this.b.isModifiable()) {
            this.b = GeneratedMessageLite.mutableCopy(this.b);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, Field field) {
        field.getClass();
        f();
        this.b.set(i, field);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Field field) {
        field.getClass();
        f();
        this.b.add(field);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i, Field field) {
        field.getClass();
        f();
        this.b.add(i, field);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Iterable<? extends Field> iterable) {
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

    @Override // com.google.protobuf.TypeOrBuilder
    public List<String> getOneofsList() {
        return this.c;
    }

    @Override // com.google.protobuf.TypeOrBuilder
    public int getOneofsCount() {
        return this.c.size();
    }

    @Override // com.google.protobuf.TypeOrBuilder
    public String getOneofs(int i) {
        return this.c.get(i);
    }

    @Override // com.google.protobuf.TypeOrBuilder
    public ByteString getOneofsBytes(int i) {
        return ByteString.copyFromUtf8(this.c.get(i));
    }

    private void h() {
        if (!this.c.isModifiable()) {
            this.c = GeneratedMessageLite.mutableCopy(this.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, String str) {
        str.getClass();
        h();
        this.c.set(i, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        str.getClass();
        h();
        this.c.add(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Iterable<String> iterable) {
        h();
        AbstractMessageLite.addAll((Iterable) iterable, (List) this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        this.c = GeneratedMessageLite.emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(ByteString byteString) {
        checkByteStringIsUtf8(byteString);
        h();
        this.c.add(byteString.toStringUtf8());
    }

    @Override // com.google.protobuf.TypeOrBuilder
    public List<Option> getOptionsList() {
        return this.d;
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.d;
    }

    @Override // com.google.protobuf.TypeOrBuilder
    public int getOptionsCount() {
        return this.d.size();
    }

    @Override // com.google.protobuf.TypeOrBuilder
    public Option getOptions(int i) {
        return this.d.get(i);
    }

    public OptionOrBuilder getOptionsOrBuilder(int i) {
        return this.d.get(i);
    }

    private void j() {
        if (!this.d.isModifiable()) {
            this.d = GeneratedMessageLite.mutableCopy(this.d);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, Option option) {
        option.getClass();
        j();
        this.d.set(i, option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Option option) {
        option.getClass();
        j();
        this.d.add(option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i, Option option) {
        option.getClass();
        j();
        this.d.add(i, option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Iterable<? extends Option> iterable) {
        j();
        AbstractMessageLite.addAll((Iterable) iterable, (List) this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        this.d = emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(int i) {
        j();
        this.d.remove(i);
    }

    @Override // com.google.protobuf.TypeOrBuilder
    public boolean hasSourceContext() {
        return this.e != null;
    }

    @Override // com.google.protobuf.TypeOrBuilder
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
    public void l() {
        this.e = null;
    }

    @Override // com.google.protobuf.TypeOrBuilder
    public int getSyntaxValue() {
        return this.f;
    }

    @Override // com.google.protobuf.TypeOrBuilder
    public Syntax getSyntax() {
        Syntax forNumber = Syntax.forNumber(this.f);
        return forNumber == null ? Syntax.UNRECOGNIZED : forNumber;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(int i) {
        this.f = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Syntax syntax) {
        this.f = syntax.getNumber();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void m() {
        this.f = 0;
    }

    public static Type parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.parseFrom(g, byteBuffer);
    }

    public static Type parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.parseFrom(g, byteBuffer, extensionRegistryLite);
    }

    public static Type parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.parseFrom(g, byteString);
    }

    public static Type parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.parseFrom(g, byteString, extensionRegistryLite);
    }

    public static Type parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.parseFrom(g, bArr);
    }

    public static Type parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (Type) GeneratedMessageLite.parseFrom(g, bArr, extensionRegistryLite);
    }

    public static Type parseFrom(InputStream inputStream) throws IOException {
        return (Type) GeneratedMessageLite.parseFrom(g, inputStream);
    }

    public static Type parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Type) GeneratedMessageLite.parseFrom(g, inputStream, extensionRegistryLite);
    }

    public static Type parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (Type) parseDelimitedFrom(g, inputStream);
    }

    public static Type parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Type) parseDelimitedFrom(g, inputStream, extensionRegistryLite);
    }

    public static Type parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (Type) GeneratedMessageLite.parseFrom(g, codedInputStream);
    }

    public static Type parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (Type) GeneratedMessageLite.parseFrom(g, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return g.createBuilder();
    }

    public static Builder newBuilder(Type type) {
        return g.createBuilder(type);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<Type, Builder> implements TypeOrBuilder {
        private Builder() {
            super(Type.g);
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public String getName() {
            return ((Type) this.instance).getName();
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public ByteString getNameBytes() {
            return ((Type) this.instance).getNameBytes();
        }

        public Builder setName(String str) {
            copyOnWrite();
            ((Type) this.instance).a(str);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((Type) this.instance).e();
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            copyOnWrite();
            ((Type) this.instance).a(byteString);
            return this;
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public List<Field> getFieldsList() {
            return Collections.unmodifiableList(((Type) this.instance).getFieldsList());
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public int getFieldsCount() {
            return ((Type) this.instance).getFieldsCount();
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public Field getFields(int i) {
            return ((Type) this.instance).getFields(i);
        }

        public Builder setFields(int i, Field field) {
            copyOnWrite();
            ((Type) this.instance).a(i, field);
            return this;
        }

        public Builder setFields(int i, Field.Builder builder) {
            copyOnWrite();
            ((Type) this.instance).a(i, builder.build());
            return this;
        }

        public Builder addFields(Field field) {
            copyOnWrite();
            ((Type) this.instance).a(field);
            return this;
        }

        public Builder addFields(int i, Field field) {
            copyOnWrite();
            ((Type) this.instance).b(i, field);
            return this;
        }

        public Builder addFields(Field.Builder builder) {
            copyOnWrite();
            ((Type) this.instance).a(builder.build());
            return this;
        }

        public Builder addFields(int i, Field.Builder builder) {
            copyOnWrite();
            ((Type) this.instance).b(i, builder.build());
            return this;
        }

        public Builder addAllFields(Iterable<? extends Field> iterable) {
            copyOnWrite();
            ((Type) this.instance).a(iterable);
            return this;
        }

        public Builder clearFields() {
            copyOnWrite();
            ((Type) this.instance).g();
            return this;
        }

        public Builder removeFields(int i) {
            copyOnWrite();
            ((Type) this.instance).b(i);
            return this;
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public List<String> getOneofsList() {
            return Collections.unmodifiableList(((Type) this.instance).getOneofsList());
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public int getOneofsCount() {
            return ((Type) this.instance).getOneofsCount();
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public String getOneofs(int i) {
            return ((Type) this.instance).getOneofs(i);
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public ByteString getOneofsBytes(int i) {
            return ((Type) this.instance).getOneofsBytes(i);
        }

        public Builder setOneofs(int i, String str) {
            copyOnWrite();
            ((Type) this.instance).a(i, str);
            return this;
        }

        public Builder addOneofs(String str) {
            copyOnWrite();
            ((Type) this.instance).b(str);
            return this;
        }

        public Builder addAllOneofs(Iterable<String> iterable) {
            copyOnWrite();
            ((Type) this.instance).b(iterable);
            return this;
        }

        public Builder clearOneofs() {
            copyOnWrite();
            ((Type) this.instance).i();
            return this;
        }

        public Builder addOneofsBytes(ByteString byteString) {
            copyOnWrite();
            ((Type) this.instance).b(byteString);
            return this;
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((Type) this.instance).getOptionsList());
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public int getOptionsCount() {
            return ((Type) this.instance).getOptionsCount();
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public Option getOptions(int i) {
            return ((Type) this.instance).getOptions(i);
        }

        public Builder setOptions(int i, Option option) {
            copyOnWrite();
            ((Type) this.instance).a(i, option);
            return this;
        }

        public Builder setOptions(int i, Option.Builder builder) {
            copyOnWrite();
            ((Type) this.instance).a(i, builder.build());
            return this;
        }

        public Builder addOptions(Option option) {
            copyOnWrite();
            ((Type) this.instance).a(option);
            return this;
        }

        public Builder addOptions(int i, Option option) {
            copyOnWrite();
            ((Type) this.instance).b(i, option);
            return this;
        }

        public Builder addOptions(Option.Builder builder) {
            copyOnWrite();
            ((Type) this.instance).a(builder.build());
            return this;
        }

        public Builder addOptions(int i, Option.Builder builder) {
            copyOnWrite();
            ((Type) this.instance).b(i, builder.build());
            return this;
        }

        public Builder addAllOptions(Iterable<? extends Option> iterable) {
            copyOnWrite();
            ((Type) this.instance).c(iterable);
            return this;
        }

        public Builder clearOptions() {
            copyOnWrite();
            ((Type) this.instance).k();
            return this;
        }

        public Builder removeOptions(int i) {
            copyOnWrite();
            ((Type) this.instance).c(i);
            return this;
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public boolean hasSourceContext() {
            return ((Type) this.instance).hasSourceContext();
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public SourceContext getSourceContext() {
            return ((Type) this.instance).getSourceContext();
        }

        public Builder setSourceContext(SourceContext sourceContext) {
            copyOnWrite();
            ((Type) this.instance).a(sourceContext);
            return this;
        }

        public Builder setSourceContext(SourceContext.Builder builder) {
            copyOnWrite();
            ((Type) this.instance).a(builder.build());
            return this;
        }

        public Builder mergeSourceContext(SourceContext sourceContext) {
            copyOnWrite();
            ((Type) this.instance).b(sourceContext);
            return this;
        }

        public Builder clearSourceContext() {
            copyOnWrite();
            ((Type) this.instance).l();
            return this;
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public int getSyntaxValue() {
            return ((Type) this.instance).getSyntaxValue();
        }

        public Builder setSyntaxValue(int i) {
            copyOnWrite();
            ((Type) this.instance).d(i);
            return this;
        }

        @Override // com.google.protobuf.TypeOrBuilder
        public Syntax getSyntax() {
            return ((Type) this.instance).getSyntax();
        }

        public Builder setSyntax(Syntax syntax) {
            copyOnWrite();
            ((Type) this.instance).a(syntax);
            return this;
        }

        public Builder clearSyntax() {
            copyOnWrite();
            ((Type) this.instance).m();
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new Type();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(g, "\u0000\u0006\u0000\u0000\u0001\u0006\u0006\u0000\u0003\u0000\u0001Ȉ\u0002\u001b\u0003Ț\u0004\u001b\u0005\t\u0006\f", new Object[]{"name_", "fields_", Field.class, "oneofs_", "options_", Option.class, "sourceContext_", "syntax_"});
            case GET_DEFAULT_INSTANCE:
                return g;
            case GET_PARSER:
                Parser<Type> parser = h;
                if (parser == null) {
                    synchronized (Type.class) {
                        parser = h;
                        if (parser == null) {
                            parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(g);
                            h = parser;
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
        Type type = new Type();
        g = type;
        GeneratedMessageLite.registerDefaultInstance(Type.class, type);
    }

    public static Type getDefaultInstance() {
        return g;
    }

    public static Parser<Type> parser() {
        return g.getParserForType();
    }
}
