package com.google.protobuf;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.FieldSet;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.Builder;
import com.google.protobuf.Internal;
import com.google.protobuf.MessageLite;
import com.google.protobuf.WireFormat;
import com.google.protobuf.c;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public abstract class GeneratedMessageLite<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> extends AbstractMessageLite<MessageType, BuilderType> {
    private static Map<Object, GeneratedMessageLite<?, ?>> a = new ConcurrentHashMap();
    protected UnknownFieldSetLite unknownFields = UnknownFieldSetLite.getDefaultInstance();
    protected int memoizedSerializedSize = -1;

    /* loaded from: classes2.dex */
    public interface ExtendableMessageOrBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends MessageLiteOrBuilder {
        <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite);

        <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i);

        <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite);

        <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite);
    }

    /* loaded from: classes2.dex */
    public enum MethodToInvoke {
        GET_MEMOIZED_IS_INITIALIZED,
        SET_MEMOIZED_IS_INITIALIZED,
        BUILD_MESSAGE_INFO,
        NEW_MUTABLE_INSTANCE,
        NEW_BUILDER,
        GET_DEFAULT_INSTANCE,
        GET_PARSER
    }

    protected abstract Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2);

    @Override // com.google.protobuf.MessageLite
    public final Parser<MessageType> getParserForType() {
        return (Parser) dynamicMethod(MethodToInvoke.GET_PARSER);
    }

    @Override // com.google.protobuf.MessageLiteOrBuilder
    public final MessageType getDefaultInstanceForType() {
        return (MessageType) ((GeneratedMessageLite) dynamicMethod(MethodToInvoke.GET_DEFAULT_INSTANCE));
    }

    @Override // com.google.protobuf.MessageLite
    public final BuilderType newBuilderForType() {
        return (BuilderType) ((Builder) dynamicMethod(MethodToInvoke.NEW_BUILDER));
    }

    public String toString() {
        return y.a(this, super.toString());
    }

    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        this.memoizedHashCode = ah.a().a((ah) this).a(this);
        return this.memoizedHashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!getDefaultInstanceForType().getClass().isInstance(obj)) {
            return false;
        }
        return ah.a().a((ah) this).a(this, (GeneratedMessageLite) obj);
    }

    private final void c() {
        if (this.unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
            this.unknownFields = UnknownFieldSetLite.a();
        }
    }

    protected boolean parseUnknownField(int i, CodedInputStream codedInputStream) throws IOException {
        if (WireFormat.getTagWireType(i) == 4) {
            return false;
        }
        c();
        return this.unknownFields.a(i, codedInputStream);
    }

    protected void mergeVarintField(int i, int i2) {
        c();
        this.unknownFields.a(i, i2);
    }

    protected void mergeLengthDelimitedField(int i, ByteString byteString) {
        c();
        this.unknownFields.a(i, byteString);
    }

    protected void makeImmutable() {
        ah.a().a((ah) this).d(this);
    }

    protected final <MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> BuilderType createBuilder() {
        return (BuilderType) ((Builder) dynamicMethod(MethodToInvoke.NEW_BUILDER));
    }

    protected final <MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> BuilderType createBuilder(MessageType messagetype) {
        return (BuilderType) createBuilder().mergeFrom(messagetype);
    }

    @Override // com.google.protobuf.MessageLiteOrBuilder
    public final boolean isInitialized() {
        return isInitialized(this, Boolean.TRUE.booleanValue());
    }

    @Override // com.google.protobuf.MessageLite
    public final BuilderType toBuilder() {
        BuilderType buildertype = (BuilderType) ((Builder) dynamicMethod(MethodToInvoke.NEW_BUILDER));
        buildertype.mergeFrom(this);
        return buildertype;
    }

    protected Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj) {
        return dynamicMethod(methodToInvoke, obj, null);
    }

    public Object dynamicMethod(MethodToInvoke methodToInvoke) {
        return dynamicMethod(methodToInvoke, null, null);
    }

    @Override // com.google.protobuf.AbstractMessageLite
    int a() {
        return this.memoizedSerializedSize;
    }

    @Override // com.google.protobuf.AbstractMessageLite
    void a(int i) {
        this.memoizedSerializedSize = i;
    }

    @Override // com.google.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        ah.a().a((ah) this).a((am) this, (Writer) g.a(codedOutputStream));
    }

    @Override // com.google.protobuf.MessageLite
    public int getSerializedSize() {
        if (this.memoizedSerializedSize == -1) {
            this.memoizedSerializedSize = ah.a().a((ah) this).b(this);
        }
        return this.memoizedSerializedSize;
    }

    public Object d() throws Exception {
        return dynamicMethod(MethodToInvoke.BUILD_MESSAGE_INFO);
    }

    public static <T extends GeneratedMessageLite<?, ?>> T a(Class<T> cls) {
        GeneratedMessageLite<?, ?> generatedMessageLite = a.get(cls);
        if (generatedMessageLite == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                generatedMessageLite = a.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (generatedMessageLite == null) {
            generatedMessageLite = (T) ((GeneratedMessageLite) at.a(cls)).getDefaultInstanceForType();
            if (generatedMessageLite != null) {
                a.put(cls, generatedMessageLite);
            } else {
                throw new IllegalStateException();
            }
        }
        return (T) generatedMessageLite;
    }

    public static <T extends GeneratedMessageLite<?, ?>> void registerDefaultInstance(Class<T> cls, T t) {
        a.put(cls, t);
    }

    protected static Object newMessageInfo(MessageLite messageLite, String str, Object[] objArr) {
        return new aj(messageLite, str, objArr);
    }

    protected final void mergeUnknownFields(UnknownFieldSetLite unknownFieldSetLite) {
        this.unknownFields = UnknownFieldSetLite.a(this.unknownFields, unknownFieldSetLite);
    }

    /* loaded from: classes2.dex */
    public static abstract class Builder<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> extends AbstractMessageLite.Builder<MessageType, BuilderType> {
        private final MessageType a;
        protected MessageType instance;
        protected boolean isBuilt = false;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.protobuf.AbstractMessageLite.Builder
        protected /* bridge */ /* synthetic */ AbstractMessageLite.Builder internalMergeFrom(AbstractMessageLite abstractMessageLite) {
            return internalMergeFrom((Builder<MessageType, BuilderType>) ((GeneratedMessageLite) abstractMessageLite));
        }

        public Builder(MessageType messagetype) {
            this.a = messagetype;
            this.instance = (MessageType) ((GeneratedMessageLite) messagetype.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE));
        }

        protected final void copyOnWrite() {
            if (this.isBuilt) {
                copyOnWriteInternal();
                this.isBuilt = false;
            }
        }

        protected void copyOnWriteInternal() {
            MessageType messagetype = (MessageType) ((GeneratedMessageLite) this.instance.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE));
            a(messagetype, this.instance);
            this.instance = messagetype;
        }

        @Override // com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return GeneratedMessageLite.isInitialized(this.instance, false);
        }

        @Override // com.google.protobuf.MessageLite.Builder
        public final BuilderType clear() {
            this.instance = (MessageType) ((GeneratedMessageLite) this.instance.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE));
            return this;
        }

        @Override // com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder
        public BuilderType clone() {
            BuilderType buildertype = (BuilderType) getDefaultInstanceForType().newBuilderForType();
            buildertype.mergeFrom(buildPartial());
            return buildertype;
        }

        @Override // com.google.protobuf.MessageLite.Builder
        public MessageType buildPartial() {
            if (this.isBuilt) {
                return this.instance;
            }
            this.instance.makeImmutable();
            this.isBuilt = true;
            return this.instance;
        }

        @Override // com.google.protobuf.MessageLite.Builder
        public final MessageType build() {
            MessageType buildPartial = buildPartial();
            if (buildPartial.isInitialized()) {
                return buildPartial;
            }
            throw newUninitializedMessageException(buildPartial);
        }

        protected BuilderType internalMergeFrom(MessageType messagetype) {
            return mergeFrom((Builder<MessageType, BuilderType>) messagetype);
        }

        public BuilderType mergeFrom(MessageType messagetype) {
            copyOnWrite();
            a(this.instance, messagetype);
            return this;
        }

        private void a(MessageType messagetype, MessageType messagetype2) {
            ah.a().a((ah) messagetype).b(messagetype, messagetype2);
        }

        @Override // com.google.protobuf.MessageLiteOrBuilder
        public MessageType getDefaultInstanceForType() {
            return this.a;
        }

        @Override // com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(byte[] bArr, int i, int i2, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            copyOnWrite();
            try {
                ah.a().a((ah) this.instance).a(this.instance, bArr, i, i + i2, new c.a(extensionRegistryLite));
                return this;
            } catch (InvalidProtocolBufferException e) {
                throw e;
            } catch (IOException e2) {
                throw new RuntimeException("Reading from byte array should not throw IOException.", e2);
            } catch (IndexOutOfBoundsException unused) {
                throw InvalidProtocolBufferException.a();
            }
        }

        @Override // com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(byte[] bArr, int i, int i2) throws InvalidProtocolBufferException {
            return mergeFrom(bArr, i, i2, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            copyOnWrite();
            try {
                ah.a().a((ah) this.instance).a(this.instance, f.a(codedInputStream), extensionRegistryLite);
                return this;
            } catch (RuntimeException e) {
                if (e.getCause() instanceof IOException) {
                    throw ((IOException) e.getCause());
                }
                throw e;
            }
        }
    }

    /* loaded from: classes2.dex */
    public static abstract class ExtendableMessage<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends GeneratedMessageLite<MessageType, BuilderType> implements ExtendableMessageOrBuilder<MessageType, BuilderType> {
        protected FieldSet<a> extensions = FieldSet.b();

        @Override // com.google.protobuf.GeneratedMessageLite, com.google.protobuf.MessageLiteOrBuilder
        public /* bridge */ /* synthetic */ MessageLite getDefaultInstanceForType() {
            return GeneratedMessageLite.super.getDefaultInstanceForType();
        }

        @Override // com.google.protobuf.GeneratedMessageLite, com.google.protobuf.MessageLite
        public /* bridge */ /* synthetic */ MessageLite.Builder newBuilderForType() {
            return GeneratedMessageLite.super.newBuilderForType();
        }

        @Override // com.google.protobuf.GeneratedMessageLite, com.google.protobuf.MessageLite
        public /* bridge */ /* synthetic */ MessageLite.Builder toBuilder() {
            return GeneratedMessageLite.super.toBuilder();
        }

        protected final void mergeExtensionFields(MessageType messagetype) {
            if (this.extensions.e()) {
                this.extensions = this.extensions.clone();
            }
            this.extensions.a(messagetype.extensions);
        }

        protected <MessageType extends MessageLite> boolean parseUnknownField(MessageType messagetype, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, int i) throws IOException {
            int tagFieldNumber = WireFormat.getTagFieldNumber(i);
            return a(codedInputStream, extensionRegistryLite, extensionRegistryLite.findLiteExtensionByNumber(messagetype, tagFieldNumber), i, tagFieldNumber);
        }

        private boolean a(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, GeneratedExtension<?, ?> generatedExtension, int i, int i2) throws IOException {
            boolean z;
            boolean z2;
            Object obj;
            MessageLite messageLite;
            int tagWireType = WireFormat.getTagWireType(i);
            if (generatedExtension == null) {
                z = false;
                z2 = true;
            } else if (tagWireType == FieldSet.a(generatedExtension.d.getLiteType(), false)) {
                z2 = false;
                z = false;
            } else if (!generatedExtension.d.d || !generatedExtension.d.c.isPackable() || tagWireType != FieldSet.a(generatedExtension.d.getLiteType(), true)) {
                z = false;
                z2 = true;
            } else {
                z2 = false;
                z = true;
            }
            if (z2) {
                return parseUnknownField(i, codedInputStream);
            }
            c();
            if (z) {
                int pushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
                if (generatedExtension.d.getLiteType() == WireFormat.FieldType.ENUM) {
                    while (codedInputStream.getBytesUntilLimit() > 0) {
                        Internal.EnumLite findValueByNumber = generatedExtension.d.getEnumType().findValueByNumber(codedInputStream.readEnum());
                        if (findValueByNumber == null) {
                            return true;
                        }
                        this.extensions.b((FieldSet<a>) generatedExtension.d, generatedExtension.d(findValueByNumber));
                    }
                } else {
                    while (codedInputStream.getBytesUntilLimit() > 0) {
                        this.extensions.b((FieldSet<a>) generatedExtension.d, FieldSet.a(codedInputStream, generatedExtension.d.getLiteType(), false));
                    }
                }
                codedInputStream.popLimit(pushLimit);
            } else {
                switch (generatedExtension.d.getLiteJavaType()) {
                    case MESSAGE:
                        MessageLite.Builder builder = null;
                        if (!generatedExtension.d.isRepeated() && (messageLite = (MessageLite) this.extensions.b((FieldSet<a>) generatedExtension.d)) != null) {
                            builder = messageLite.toBuilder();
                        }
                        if (builder == null) {
                            builder = generatedExtension.getMessageDefaultInstance().newBuilderForType();
                        }
                        if (generatedExtension.d.getLiteType() == WireFormat.FieldType.GROUP) {
                            codedInputStream.readGroup(generatedExtension.getNumber(), builder, extensionRegistryLite);
                        } else {
                            codedInputStream.readMessage(builder, extensionRegistryLite);
                        }
                        obj = builder.build();
                        break;
                    case ENUM:
                        int readEnum = codedInputStream.readEnum();
                        obj = generatedExtension.d.getEnumType().findValueByNumber(readEnum);
                        if (obj == null) {
                            mergeVarintField(i2, readEnum);
                            return true;
                        }
                        break;
                    default:
                        obj = FieldSet.a(codedInputStream, generatedExtension.d.getLiteType(), false);
                        break;
                }
                if (generatedExtension.d.isRepeated()) {
                    this.extensions.b((FieldSet<a>) generatedExtension.d, generatedExtension.d(obj));
                } else {
                    this.extensions.a((FieldSet<a>) generatedExtension.d, generatedExtension.d(obj));
                }
            }
            return true;
        }

        protected <MessageType extends MessageLite> boolean parseUnknownFieldAsMessageSet(MessageType messagetype, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, int i) throws IOException {
            if (i == WireFormat.a) {
                a((ExtendableMessage<MessageType, BuilderType>) messagetype, codedInputStream, extensionRegistryLite);
                return true;
            } else if (WireFormat.getTagWireType(i) == 2) {
                return parseUnknownField(messagetype, codedInputStream, extensionRegistryLite, i);
            } else {
                return codedInputStream.skipField(i);
            }
        }

        private <MessageType extends MessageLite> void a(MessageType messagetype, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int i = 0;
            ByteString byteString = null;
            GeneratedExtension<?, ?> generatedExtension = null;
            while (true) {
                int readTag = codedInputStream.readTag();
                if (readTag == 0) {
                    break;
                } else if (readTag == WireFormat.c) {
                    i = codedInputStream.readUInt32();
                    if (i != 0) {
                        generatedExtension = extensionRegistryLite.findLiteExtensionByNumber(messagetype, i);
                    }
                } else if (readTag == WireFormat.d) {
                    if (i == 0 || generatedExtension == null) {
                        byteString = codedInputStream.readBytes();
                    } else {
                        a(codedInputStream, generatedExtension, extensionRegistryLite, i);
                        byteString = null;
                    }
                } else if (!codedInputStream.skipField(readTag)) {
                    break;
                }
            }
            codedInputStream.checkLastTagWas(WireFormat.b);
            if (byteString != null && i != 0) {
                if (generatedExtension != null) {
                    a(byteString, extensionRegistryLite, generatedExtension);
                } else if (byteString != null) {
                    mergeLengthDelimitedField(i, byteString);
                }
            }
        }

        private void a(CodedInputStream codedInputStream, GeneratedExtension<?, ?> generatedExtension, ExtensionRegistryLite extensionRegistryLite, int i) throws IOException {
            a(codedInputStream, extensionRegistryLite, generatedExtension, WireFormat.a(i, 2), i);
        }

        private void a(ByteString byteString, ExtensionRegistryLite extensionRegistryLite, GeneratedExtension<?, ?> generatedExtension) throws IOException {
            MessageLite messageLite = (MessageLite) this.extensions.b((FieldSet<a>) generatedExtension.d);
            MessageLite.Builder builder = messageLite != null ? messageLite.toBuilder() : null;
            if (builder == null) {
                builder = generatedExtension.getMessageDefaultInstance().newBuilderForType();
            }
            builder.mergeFrom(byteString, extensionRegistryLite);
            c().a((FieldSet<a>) generatedExtension.d, generatedExtension.d(builder.build()));
        }

        public FieldSet<a> c() {
            if (this.extensions.e()) {
                this.extensions = this.extensions.clone();
            }
            return this.extensions;
        }

        private void a(GeneratedExtension<MessageType, ?> generatedExtension) {
            if (generatedExtension.getContainingTypeDefaultInstance() != getDefaultInstanceForType()) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite) {
            GeneratedExtension<MessageType, ?> b = GeneratedMessageLite.b(extensionLite);
            a((GeneratedExtension) b);
            return this.extensions.a((FieldSet<a>) b.d);
        }

        @Override // com.google.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite) {
            GeneratedExtension<MessageType, ?> b = GeneratedMessageLite.b(extensionLite);
            a((GeneratedExtension) b);
            return this.extensions.d(b.d);
        }

        @Override // com.google.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite) {
            GeneratedExtension<MessageType, ?> b = GeneratedMessageLite.b(extensionLite);
            a((GeneratedExtension) b);
            Object b2 = this.extensions.b((FieldSet<a>) b.d);
            if (b2 == null) {
                return b.b;
            }
            return (Type) b.a(b2);
        }

        @Override // com.google.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i) {
            GeneratedExtension<MessageType, ?> b = GeneratedMessageLite.b(extensionLite);
            a((GeneratedExtension) b);
            return (Type) b.b(this.extensions.a((FieldSet<a>) b.d, i));
        }

        protected boolean extensionsAreInitialized() {
            return this.extensions.i();
        }

        /* loaded from: classes2.dex */
        protected class ExtensionWriter {
            private final Iterator<Map.Entry<a, Object>> b;
            private Map.Entry<a, Object> c;
            private final boolean d;

            private ExtensionWriter(boolean z) {
                ExtendableMessage.this = r1;
                this.b = ExtendableMessage.this.extensions.g();
                if (this.b.hasNext()) {
                    this.c = this.b.next();
                }
                this.d = z;
            }

            public void writeUntil(int i, CodedOutputStream codedOutputStream) throws IOException {
                while (true) {
                    Map.Entry<a, Object> entry = this.c;
                    if (entry != null && entry.getKey().getNumber() < i) {
                        a key = this.c.getKey();
                        if (!this.d || key.getLiteJavaType() != WireFormat.JavaType.MESSAGE || key.isRepeated()) {
                            FieldSet.a(key, this.c.getValue(), codedOutputStream);
                        } else {
                            codedOutputStream.writeMessageSetExtension(key.getNumber(), (MessageLite) this.c.getValue());
                        }
                        if (this.b.hasNext()) {
                            this.c = this.b.next();
                        } else {
                            this.c = null;
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        protected ExtendableMessage<MessageType, BuilderType>.ExtensionWriter newExtensionWriter() {
            return new ExtensionWriter(false);
        }

        protected ExtendableMessage<MessageType, BuilderType>.ExtensionWriter newMessageSetExtensionWriter() {
            return new ExtensionWriter(true);
        }

        protected int extensionsSerializedSize() {
            return this.extensions.j();
        }

        protected int extensionsSerializedSizeAsMessageSet() {
            return this.extensions.k();
        }
    }

    /* loaded from: classes2.dex */
    public static abstract class ExtendableBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends Builder<MessageType, BuilderType> implements ExtendableMessageOrBuilder<MessageType, BuilderType> {
        protected ExtendableBuilder(MessageType messagetype) {
            super(messagetype);
        }

        @Override // com.google.protobuf.GeneratedMessageLite.Builder
        protected void copyOnWriteInternal() {
            super.copyOnWriteInternal();
            ((ExtendableMessage) this.instance).extensions = ((ExtendableMessage) this.instance).extensions.clone();
        }

        private FieldSet<a> a() {
            FieldSet<a> fieldSet = ((ExtendableMessage) this.instance).extensions;
            if (!fieldSet.e()) {
                return fieldSet;
            }
            FieldSet<a> f = fieldSet.clone();
            ((ExtendableMessage) this.instance).extensions = f;
            return f;
        }

        @Override // com.google.protobuf.GeneratedMessageLite.Builder, com.google.protobuf.MessageLite.Builder
        public final MessageType buildPartial() {
            if (this.isBuilt) {
                return (MessageType) ((ExtendableMessage) this.instance);
            }
            ((ExtendableMessage) this.instance).extensions.d();
            return (MessageType) ((ExtendableMessage) super.buildPartial());
        }

        private void a(GeneratedExtension<MessageType, ?> generatedExtension) {
            if (generatedExtension.getContainingTypeDefaultInstance() != getDefaultInstanceForType()) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite) {
            return ((ExtendableMessage) this.instance).hasExtension(extensionLite);
        }

        @Override // com.google.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite) {
            return ((ExtendableMessage) this.instance).getExtensionCount(extensionLite);
        }

        @Override // com.google.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite) {
            return (Type) ((ExtendableMessage) this.instance).getExtension(extensionLite);
        }

        @Override // com.google.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i) {
            return (Type) ((ExtendableMessage) this.instance).getExtension(extensionLite, i);
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, Type> extensionLite, Type type) {
            GeneratedExtension<MessageType, ?> b = GeneratedMessageLite.b(extensionLite);
            a(b);
            copyOnWrite();
            a().a((FieldSet<a>) b.d, b.c(type));
            return this;
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i, Type type) {
            GeneratedExtension<MessageType, ?> b = GeneratedMessageLite.b(extensionLite);
            a(b);
            copyOnWrite();
            a().a((FieldSet<a>) b.d, i, b.d(type));
            return this;
        }

        public final <Type> BuilderType addExtension(ExtensionLite<MessageType, List<Type>> extensionLite, Type type) {
            GeneratedExtension<MessageType, ?> b = GeneratedMessageLite.b(extensionLite);
            a(b);
            copyOnWrite();
            a().b((FieldSet<a>) b.d, b.d(type));
            return this;
        }

        public final BuilderType clearExtension(ExtensionLite<MessageType, ?> extensionLite) {
            GeneratedExtension<MessageType, ?> b = GeneratedMessageLite.b(extensionLite);
            a(b);
            copyOnWrite();
            a().c((FieldSet<a>) b.d);
            return this;
        }
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newSingularGeneratedExtension(ContainingType containingtype, Type type, MessageLite messageLite, Internal.EnumLiteMap<?> enumLiteMap, int i, WireFormat.FieldType fieldType, Class cls) {
        return new GeneratedExtension<>(containingtype, type, messageLite, new a(enumLiteMap, i, fieldType, false, false), cls);
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newRepeatedGeneratedExtension(ContainingType containingtype, MessageLite messageLite, Internal.EnumLiteMap<?> enumLiteMap, int i, WireFormat.FieldType fieldType, boolean z, Class cls) {
        return new GeneratedExtension<>(containingtype, Collections.emptyList(), messageLite, new a(enumLiteMap, i, fieldType, true, z), cls);
    }

    /* loaded from: classes2.dex */
    public static final class a implements FieldSet.FieldDescriptorLite<a> {
        final Internal.EnumLiteMap<?> a;
        final int b;
        final WireFormat.FieldType c;
        final boolean d;
        final boolean e;

        a(Internal.EnumLiteMap<?> enumLiteMap, int i, WireFormat.FieldType fieldType, boolean z, boolean z2) {
            this.a = enumLiteMap;
            this.b = i;
            this.c = fieldType;
            this.d = z;
            this.e = z2;
        }

        @Override // com.google.protobuf.FieldSet.FieldDescriptorLite
        public int getNumber() {
            return this.b;
        }

        @Override // com.google.protobuf.FieldSet.FieldDescriptorLite
        public WireFormat.FieldType getLiteType() {
            return this.c;
        }

        @Override // com.google.protobuf.FieldSet.FieldDescriptorLite
        public WireFormat.JavaType getLiteJavaType() {
            return this.c.getJavaType();
        }

        @Override // com.google.protobuf.FieldSet.FieldDescriptorLite
        public boolean isRepeated() {
            return this.d;
        }

        @Override // com.google.protobuf.FieldSet.FieldDescriptorLite
        public boolean isPacked() {
            return this.e;
        }

        @Override // com.google.protobuf.FieldSet.FieldDescriptorLite
        public Internal.EnumLiteMap<?> getEnumType() {
            return this.a;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.protobuf.FieldSet.FieldDescriptorLite
        public MessageLite.Builder internalMergeFrom(MessageLite.Builder builder, MessageLite messageLite) {
            return ((Builder) builder).mergeFrom((Builder) ((GeneratedMessageLite) messageLite));
        }

        /* renamed from: a */
        public int compareTo(a aVar) {
            return this.b - aVar.b;
        }
    }

    public static Object a(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class GeneratedExtension<ContainingType extends MessageLite, Type> extends ExtensionLite<ContainingType, Type> {
        final ContainingType a;
        final Type b;
        final MessageLite c;
        final a d;

        GeneratedExtension(ContainingType containingtype, Type type, MessageLite messageLite, a aVar, Class cls) {
            if (containingtype == null) {
                throw new IllegalArgumentException("Null containingTypeDefaultInstance");
            } else if (aVar.getLiteType() == WireFormat.FieldType.MESSAGE && messageLite == null) {
                throw new IllegalArgumentException("Null messageDefaultInstance");
            } else {
                this.a = containingtype;
                this.b = type;
                this.c = messageLite;
                this.d = aVar;
            }
        }

        public ContainingType getContainingTypeDefaultInstance() {
            return this.a;
        }

        @Override // com.google.protobuf.ExtensionLite
        public int getNumber() {
            return this.d.getNumber();
        }

        @Override // com.google.protobuf.ExtensionLite
        public MessageLite getMessageDefaultInstance() {
            return this.c;
        }

        Object a(Object obj) {
            if (!this.d.isRepeated()) {
                return b(obj);
            }
            if (this.d.getLiteJavaType() != WireFormat.JavaType.ENUM) {
                return obj;
            }
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : (List) obj) {
                arrayList.add(b(obj2));
            }
            return arrayList;
        }

        Object b(Object obj) {
            return this.d.getLiteJavaType() == WireFormat.JavaType.ENUM ? this.d.a.findValueByNumber(((Integer) obj).intValue()) : obj;
        }

        Object c(Object obj) {
            if (!this.d.isRepeated()) {
                return d(obj);
            }
            if (this.d.getLiteJavaType() != WireFormat.JavaType.ENUM) {
                return obj;
            }
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : (List) obj) {
                arrayList.add(d(obj2));
            }
            return arrayList;
        }

        Object d(Object obj) {
            return this.d.getLiteJavaType() == WireFormat.JavaType.ENUM ? Integer.valueOf(((Internal.EnumLite) obj).getNumber()) : obj;
        }

        @Override // com.google.protobuf.ExtensionLite
        public WireFormat.FieldType getLiteType() {
            return this.d.getLiteType();
        }

        @Override // com.google.protobuf.ExtensionLite
        public boolean isRepeated() {
            return this.d.d;
        }

        @Override // com.google.protobuf.ExtensionLite
        public Type getDefaultValue() {
            return this.b;
        }
    }

    /* loaded from: classes2.dex */
    protected static final class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        private final byte[] asBytes;
        private final Class<?> messageClass;
        private final String messageClassName;

        public static SerializedForm of(MessageLite messageLite) {
            return new SerializedForm(messageLite);
        }

        SerializedForm(MessageLite messageLite) {
            this.messageClass = messageLite.getClass();
            this.messageClassName = this.messageClass.getName();
            this.asBytes = messageLite.toByteArray();
        }

        protected Object readResolve() throws ObjectStreamException {
            try {
                Field declaredField = b().getDeclaredField("DEFAULT_INSTANCE");
                declaredField.setAccessible(true);
                return ((MessageLite) declaredField.get(null)).newBuilderForType().mergeFrom(this.asBytes).buildPartial();
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException("Unable to understand proto buffer", e);
            } catch (ClassNotFoundException e2) {
                throw new RuntimeException("Unable to find proto buffer class: " + this.messageClassName, e2);
            } catch (IllegalAccessException e3) {
                throw new RuntimeException("Unable to call parsePartialFrom", e3);
            } catch (NoSuchFieldException unused) {
                return a();
            } catch (SecurityException e4) {
                throw new RuntimeException("Unable to call DEFAULT_INSTANCE in " + this.messageClassName, e4);
            }
        }

        @Deprecated
        private Object a() throws ObjectStreamException {
            try {
                Field declaredField = b().getDeclaredField("defaultInstance");
                declaredField.setAccessible(true);
                return ((MessageLite) declaredField.get(null)).newBuilderForType().mergeFrom(this.asBytes).buildPartial();
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException("Unable to understand proto buffer", e);
            } catch (ClassNotFoundException e2) {
                throw new RuntimeException("Unable to find proto buffer class: " + this.messageClassName, e2);
            } catch (IllegalAccessException e3) {
                throw new RuntimeException("Unable to call parsePartialFrom", e3);
            } catch (NoSuchFieldException e4) {
                throw new RuntimeException("Unable to find defaultInstance in " + this.messageClassName, e4);
            } catch (SecurityException e5) {
                throw new RuntimeException("Unable to call defaultInstance in " + this.messageClassName, e5);
            }
        }

        private Class<?> b() throws ClassNotFoundException {
            Class<?> cls = this.messageClass;
            return cls != null ? cls : Class.forName(this.messageClassName);
        }
    }

    public static <MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>, T> GeneratedExtension<MessageType, T> b(ExtensionLite<MessageType, T> extensionLite) {
        if (extensionLite.a()) {
            return (GeneratedExtension) extensionLite;
        }
        throw new IllegalArgumentException("Expected a lite extension.");
    }

    protected static final <T extends GeneratedMessageLite<T, ?>> boolean isInitialized(T t, boolean z) {
        byte byteValue = ((Byte) t.dynamicMethod(MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED)).byteValue();
        if (byteValue == 1) {
            return true;
        }
        if (byteValue == 0) {
            return false;
        }
        boolean e = ah.a().a((ah) t).e(t);
        if (z) {
            t.dynamicMethod(MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED, e ? t : null);
        }
        return e;
    }

    protected static Internal.IntList emptyIntList() {
        return o.a();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.google.protobuf.Internal$IntList] */
    protected static Internal.IntList mutableCopy(Internal.IntList intList) {
        int size = intList.size();
        return intList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    protected static Internal.LongList emptyLongList() {
        return r.a();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.google.protobuf.Internal$LongList] */
    protected static Internal.LongList mutableCopy(Internal.LongList longList) {
        int size = longList.size();
        return longList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    protected static Internal.FloatList emptyFloatList() {
        return m.a();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.google.protobuf.Internal$FloatList] */
    protected static Internal.FloatList mutableCopy(Internal.FloatList floatList) {
        int size = floatList.size();
        return floatList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    protected static Internal.DoubleList emptyDoubleList() {
        return h.a();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.google.protobuf.Internal$DoubleList] */
    protected static Internal.DoubleList mutableCopy(Internal.DoubleList doubleList) {
        int size = doubleList.size();
        return doubleList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    protected static Internal.BooleanList emptyBooleanList() {
        return e.a();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.google.protobuf.Internal$BooleanList] */
    protected static Internal.BooleanList mutableCopy(Internal.BooleanList booleanList) {
        int size = booleanList.size();
        return booleanList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    public static <E> Internal.ProtobufList<E> emptyProtobufList() {
        return ai.a();
    }

    public static <E> Internal.ProtobufList<E> mutableCopy(Internal.ProtobufList<E> protobufList) {
        int size = protobufList.size();
        return protobufList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    /* loaded from: classes2.dex */
    protected static class DefaultInstanceBasedParser<T extends GeneratedMessageLite<T, ?>> extends AbstractParser<T> {
        private final T a;

        public DefaultInstanceBasedParser(T t) {
            this.a = t;
        }

        @Override // com.google.protobuf.Parser
        public T parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (T) GeneratedMessageLite.a(this.a, codedInputStream, extensionRegistryLite);
        }

        @Override // com.google.protobuf.AbstractParser, com.google.protobuf.Parser
        public T parsePartialFrom(byte[] bArr, int i, int i2, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (T) GeneratedMessageLite.a(this.a, bArr, i, i2, extensionRegistryLite);
        }
    }

    static <T extends GeneratedMessageLite<T, ?>> T a(T t, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        T t2 = (T) ((GeneratedMessageLite) t.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE));
        try {
            am a2 = ah.a().a((ah) t2);
            a2.a(t2, f.a(codedInputStream), extensionRegistryLite);
            a2.d(t2);
            return t2;
        } catch (IOException e) {
            if (e.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e.getCause());
            }
            throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(t2);
        } catch (RuntimeException e2) {
            if (e2.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e2.getCause());
            }
            throw e2;
        }
    }

    static <T extends GeneratedMessageLite<T, ?>> T a(T t, byte[] bArr, int i, int i2, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        T t2 = (T) ((GeneratedMessageLite) t.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE));
        try {
            am a2 = ah.a().a((ah) t2);
            a2.a(t2, bArr, i, i + i2, new c.a(extensionRegistryLite));
            a2.d(t2);
            if (((GeneratedMessageLite) t2).memoizedHashCode == 0) {
                return t2;
            }
            throw new RuntimeException();
        } catch (IOException e) {
            if (e.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e.getCause());
            }
            throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(t2);
        } catch (IndexOutOfBoundsException unused) {
            throw InvalidProtocolBufferException.a().setUnfinishedMessage(t2);
        }
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T t, CodedInputStream codedInputStream) throws InvalidProtocolBufferException {
        return (T) a(t, codedInputStream, ExtensionRegistryLite.getEmptyRegistry());
    }

    private static <T extends GeneratedMessageLite<T, ?>> T a(T t) throws InvalidProtocolBufferException {
        if (t == null || t.isInitialized()) {
            return t;
        }
        throw t.b().asInvalidProtocolBufferException().setUnfinishedMessage(t);
    }

    public static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (T) a(parseFrom(t, CodedInputStream.newInstance(byteBuffer), extensionRegistryLite));
    }

    public static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (T) parseFrom(t, byteBuffer, ExtensionRegistryLite.getEmptyRegistry());
    }

    public static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, ByteString byteString) throws InvalidProtocolBufferException {
        return (T) a(parseFrom(t, byteString, ExtensionRegistryLite.getEmptyRegistry()));
    }

    public static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (T) a(a(t, byteString, extensionRegistryLite));
    }

    private static <T extends GeneratedMessageLite<T, ?>> T a(T t, ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        try {
            CodedInputStream newCodedInput = byteString.newCodedInput();
            T t2 = (T) a(t, newCodedInput, extensionRegistryLite);
            try {
                newCodedInput.checkLastTagWas(0);
                return t2;
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(t2);
            }
        } catch (InvalidProtocolBufferException e2) {
            throw e2;
        }
    }

    public static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, byte[] bArr) throws InvalidProtocolBufferException {
        return (T) a(a(t, bArr, 0, bArr.length, ExtensionRegistryLite.getEmptyRegistry()));
    }

    public static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (T) a(a(t, bArr, 0, bArr.length, extensionRegistryLite));
    }

    public static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, InputStream inputStream) throws InvalidProtocolBufferException {
        return (T) a(a(t, CodedInputStream.newInstance(inputStream), ExtensionRegistryLite.getEmptyRegistry()));
    }

    public static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (T) a(a(t, CodedInputStream.newInstance(inputStream), extensionRegistryLite));
    }

    public static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, CodedInputStream codedInputStream) throws InvalidProtocolBufferException {
        return (T) parseFrom(t, codedInputStream, ExtensionRegistryLite.getEmptyRegistry());
    }

    public static <T extends GeneratedMessageLite<T, ?>> T parseFrom(T t, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (T) a(a(t, codedInputStream, extensionRegistryLite));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(T t, InputStream inputStream) throws InvalidProtocolBufferException {
        return (T) a(a(t, inputStream, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(T t, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (T) a(a(t, inputStream, extensionRegistryLite));
    }

    private static <T extends GeneratedMessageLite<T, ?>> T a(T t, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        try {
            int read = inputStream.read();
            if (read == -1) {
                return null;
            }
            CodedInputStream newInstance = CodedInputStream.newInstance(new AbstractMessageLite.Builder.a(inputStream, CodedInputStream.readRawVarint32(read, inputStream)));
            T t2 = (T) a(t, newInstance, extensionRegistryLite);
            try {
                newInstance.checkLastTagWas(0);
                return t2;
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(t2);
            }
        } catch (IOException e2) {
            throw new InvalidProtocolBufferException(e2.getMessage());
        }
    }
}
