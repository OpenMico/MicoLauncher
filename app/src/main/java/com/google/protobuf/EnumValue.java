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
public final class EnumValue extends GeneratedMessageLite<EnumValue, Builder> implements EnumValueOrBuilder {
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int NUMBER_FIELD_NUMBER = 2;
    public static final int OPTIONS_FIELD_NUMBER = 3;
    private static final EnumValue d;
    private static volatile Parser<EnumValue> e;
    private int b;
    private String a = "";
    private Internal.ProtobufList<Option> c = emptyProtobufList();

    private EnumValue() {
    }

    @Override // com.google.protobuf.EnumValueOrBuilder
    public String getName() {
        return this.a;
    }

    @Override // com.google.protobuf.EnumValueOrBuilder
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

    @Override // com.google.protobuf.EnumValueOrBuilder
    public int getNumber() {
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

    @Override // com.google.protobuf.EnumValueOrBuilder
    public List<Option> getOptionsList() {
        return this.c;
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.c;
    }

    @Override // com.google.protobuf.EnumValueOrBuilder
    public int getOptionsCount() {
        return this.c.size();
    }

    @Override // com.google.protobuf.EnumValueOrBuilder
    public Option getOptions(int i) {
        return this.c.get(i);
    }

    public OptionOrBuilder getOptionsOrBuilder(int i) {
        return this.c.get(i);
    }

    private void g() {
        if (!this.c.isModifiable()) {
            this.c = GeneratedMessageLite.mutableCopy(this.c);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, Option option) {
        option.getClass();
        g();
        this.c.set(i, option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Option option) {
        option.getClass();
        g();
        this.c.add(option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i, Option option) {
        option.getClass();
        g();
        this.c.add(i, option);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Iterable<? extends Option> iterable) {
        g();
        AbstractMessageLite.addAll((Iterable) iterable, (List) this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        this.c = emptyProtobufList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(int i) {
        g();
        this.c.remove(i);
    }

    public static EnumValue parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.parseFrom(d, byteBuffer);
    }

    public static EnumValue parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
    }

    public static EnumValue parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.parseFrom(d, byteString);
    }

    public static EnumValue parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
    }

    public static EnumValue parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.parseFrom(d, bArr);
    }

    public static EnumValue parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (EnumValue) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
    }

    public static EnumValue parseFrom(InputStream inputStream) throws IOException {
        return (EnumValue) GeneratedMessageLite.parseFrom(d, inputStream);
    }

    public static EnumValue parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (EnumValue) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
    }

    public static EnumValue parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (EnumValue) parseDelimitedFrom(d, inputStream);
    }

    public static EnumValue parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (EnumValue) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
    }

    public static EnumValue parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (EnumValue) GeneratedMessageLite.parseFrom(d, codedInputStream);
    }

    public static EnumValue parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (EnumValue) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return d.createBuilder();
    }

    public static Builder newBuilder(EnumValue enumValue) {
        return d.createBuilder(enumValue);
    }

    /* loaded from: classes2.dex */
    public static final class Builder extends GeneratedMessageLite.Builder<EnumValue, Builder> implements EnumValueOrBuilder {
        private Builder() {
            super(EnumValue.d);
        }

        @Override // com.google.protobuf.EnumValueOrBuilder
        public String getName() {
            return ((EnumValue) this.instance).getName();
        }

        @Override // com.google.protobuf.EnumValueOrBuilder
        public ByteString getNameBytes() {
            return ((EnumValue) this.instance).getNameBytes();
        }

        public Builder setName(String str) {
            copyOnWrite();
            ((EnumValue) this.instance).a(str);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((EnumValue) this.instance).e();
            return this;
        }

        public Builder setNameBytes(ByteString byteString) {
            copyOnWrite();
            ((EnumValue) this.instance).a(byteString);
            return this;
        }

        @Override // com.google.protobuf.EnumValueOrBuilder
        public int getNumber() {
            return ((EnumValue) this.instance).getNumber();
        }

        public Builder setNumber(int i) {
            copyOnWrite();
            ((EnumValue) this.instance).b(i);
            return this;
        }

        public Builder clearNumber() {
            copyOnWrite();
            ((EnumValue) this.instance).f();
            return this;
        }

        @Override // com.google.protobuf.EnumValueOrBuilder
        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((EnumValue) this.instance).getOptionsList());
        }

        @Override // com.google.protobuf.EnumValueOrBuilder
        public int getOptionsCount() {
            return ((EnumValue) this.instance).getOptionsCount();
        }

        @Override // com.google.protobuf.EnumValueOrBuilder
        public Option getOptions(int i) {
            return ((EnumValue) this.instance).getOptions(i);
        }

        public Builder setOptions(int i, Option option) {
            copyOnWrite();
            ((EnumValue) this.instance).a(i, option);
            return this;
        }

        public Builder setOptions(int i, Option.Builder builder) {
            copyOnWrite();
            ((EnumValue) this.instance).a(i, builder.build());
            return this;
        }

        public Builder addOptions(Option option) {
            copyOnWrite();
            ((EnumValue) this.instance).a(option);
            return this;
        }

        public Builder addOptions(int i, Option option) {
            copyOnWrite();
            ((EnumValue) this.instance).b(i, option);
            return this;
        }

        public Builder addOptions(Option.Builder builder) {
            copyOnWrite();
            ((EnumValue) this.instance).a(builder.build());
            return this;
        }

        public Builder addOptions(int i, Option.Builder builder) {
            copyOnWrite();
            ((EnumValue) this.instance).b(i, builder.build());
            return this;
        }

        public Builder addAllOptions(Iterable<? extends Option> iterable) {
            copyOnWrite();
            ((EnumValue) this.instance).a(iterable);
            return this;
        }

        public Builder clearOptions() {
            copyOnWrite();
            ((EnumValue) this.instance).h();
            return this;
        }

        public Builder removeOptions(int i) {
            copyOnWrite();
            ((EnumValue) this.instance).c(i);
            return this;
        }
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
        switch (methodToInvoke) {
            case NEW_MUTABLE_INSTANCE:
                return new EnumValue();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0001\u0000\u0001Ȉ\u0002\u0004\u0003\u001b", new Object[]{"name_", "number_", "options_", Option.class});
            case GET_DEFAULT_INSTANCE:
                return d;
            case GET_PARSER:
                Parser<EnumValue> parser = e;
                if (parser == null) {
                    synchronized (EnumValue.class) {
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
        EnumValue enumValue = new EnumValue();
        d = enumValue;
        GeneratedMessageLite.registerDefaultInstance(EnumValue.class, enumValue);
    }

    public static EnumValue getDefaultInstance() {
        return d;
    }

    public static Parser<EnumValue> parser() {
        return d.getParserForType();
    }
}
