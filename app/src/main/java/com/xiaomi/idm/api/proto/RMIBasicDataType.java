package com.xiaomi.idm.api.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public final class RMIBasicDataType {

    /* loaded from: classes3.dex */
    public interface DoubleOrBuilder extends MessageLiteOrBuilder {
        double getV();
    }

    /* loaded from: classes3.dex */
    public interface FloatOrBuilder extends MessageLiteOrBuilder {
        float getV();
    }

    /* loaded from: classes3.dex */
    public interface IntegerOrBuilder extends MessageLiteOrBuilder {
        int getV();
    }

    /* loaded from: classes3.dex */
    public interface LongOrBuilder extends MessageLiteOrBuilder {
        long getV();
    }

    /* loaded from: classes3.dex */
    public interface StringOrBuilder extends MessageLiteOrBuilder {
        String getV();

        ByteString getVBytes();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private RMIBasicDataType() {
    }

    /* loaded from: classes3.dex */
    public static final class Integer extends GeneratedMessageLite<Integer, Builder> implements IntegerOrBuilder {
        public static final int V_FIELD_NUMBER = 1;
        private static final Integer b;
        private static volatile Parser<Integer> c;
        private int a;

        private Integer() {
        }

        @Override // com.xiaomi.idm.api.proto.RMIBasicDataType.IntegerOrBuilder
        public int getV() {
            return this.a;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i) {
            this.a = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = 0;
        }

        public static Integer parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Integer) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static Integer parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Integer) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static Integer parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Integer) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static Integer parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Integer) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static Integer parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Integer) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static Integer parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Integer) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static Integer parseFrom(InputStream inputStream) throws IOException {
            return (Integer) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static Integer parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Integer) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static Integer parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Integer) parseDelimitedFrom(b, inputStream);
        }

        public static Integer parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Integer) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static Integer parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Integer) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static Integer parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Integer) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(Integer integer) {
            return b.createBuilder(integer);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<Integer, Builder> implements IntegerOrBuilder {
            private Builder() {
                super(Integer.b);
            }

            @Override // com.xiaomi.idm.api.proto.RMIBasicDataType.IntegerOrBuilder
            public int getV() {
                return ((Integer) this.instance).getV();
            }

            public Builder setV(int i) {
                copyOnWrite();
                ((Integer) this.instance).b(i);
                return this;
            }

            public Builder clearV() {
                copyOnWrite();
                ((Integer) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new Integer();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004", new Object[]{"v_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<Integer> parser = c;
                    if (parser == null) {
                        synchronized (Integer.class) {
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
            Integer integer = new Integer();
            b = integer;
            GeneratedMessageLite.registerDefaultInstance(Integer.class, integer);
        }

        public static Integer getDefaultInstance() {
            return b;
        }

        public static Parser<Integer> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class Long extends GeneratedMessageLite<Long, Builder> implements LongOrBuilder {
        public static final int V_FIELD_NUMBER = 1;
        private static final Long b;
        private static volatile Parser<Long> c;
        private long a;

        private Long() {
        }

        @Override // com.xiaomi.idm.api.proto.RMIBasicDataType.LongOrBuilder
        public long getV() {
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

        public static Long parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Long) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static Long parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Long) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static Long parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Long) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static Long parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Long) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static Long parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Long) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static Long parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Long) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static Long parseFrom(InputStream inputStream) throws IOException {
            return (Long) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static Long parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Long) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static Long parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Long) parseDelimitedFrom(b, inputStream);
        }

        public static Long parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Long) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static Long parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Long) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static Long parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Long) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(Long r1) {
            return b.createBuilder(r1);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<Long, Builder> implements LongOrBuilder {
            private Builder() {
                super(Long.b);
            }

            @Override // com.xiaomi.idm.api.proto.RMIBasicDataType.LongOrBuilder
            public long getV() {
                return ((Long) this.instance).getV();
            }

            public Builder setV(long j) {
                copyOnWrite();
                ((Long) this.instance).a(j);
                return this;
            }

            public Builder clearV() {
                copyOnWrite();
                ((Long) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new Long();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0002", new Object[]{"v_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<Long> parser = c;
                    if (parser == null) {
                        synchronized (Long.class) {
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
            Long r0 = new Long();
            b = r0;
            GeneratedMessageLite.registerDefaultInstance(Long.class, r0);
        }

        public static Long getDefaultInstance() {
            return b;
        }

        public static Parser<Long> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class Float extends GeneratedMessageLite<Float, Builder> implements FloatOrBuilder {
        public static final int V_FIELD_NUMBER = 1;
        private static final Float b;
        private static volatile Parser<Float> c;
        private float a;

        private Float() {
        }

        @Override // com.xiaomi.idm.api.proto.RMIBasicDataType.FloatOrBuilder
        public float getV() {
            return this.a;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(float f) {
            this.a = f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = 0.0f;
        }

        public static Float parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Float) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static Float parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Float) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static Float parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Float) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static Float parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Float) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static Float parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Float) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static Float parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Float) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static Float parseFrom(InputStream inputStream) throws IOException {
            return (Float) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static Float parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Float) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static Float parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Float) parseDelimitedFrom(b, inputStream);
        }

        public static Float parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Float) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static Float parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Float) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static Float parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Float) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(Float r1) {
            return b.createBuilder(r1);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<Float, Builder> implements FloatOrBuilder {
            private Builder() {
                super(Float.b);
            }

            @Override // com.xiaomi.idm.api.proto.RMIBasicDataType.FloatOrBuilder
            public float getV() {
                return ((Float) this.instance).getV();
            }

            public Builder setV(float f) {
                copyOnWrite();
                ((Float) this.instance).a(f);
                return this;
            }

            public Builder clearV() {
                copyOnWrite();
                ((Float) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new Float();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0001", new Object[]{"v_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<Float> parser = c;
                    if (parser == null) {
                        synchronized (Float.class) {
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
            Float r0 = new Float();
            b = r0;
            GeneratedMessageLite.registerDefaultInstance(Float.class, r0);
        }

        public static Float getDefaultInstance() {
            return b;
        }

        public static Parser<Float> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class Double extends GeneratedMessageLite<Double, Builder> implements DoubleOrBuilder {
        public static final int V_FIELD_NUMBER = 1;
        private static final Double b;
        private static volatile Parser<Double> c;
        private double a;

        private Double() {
        }

        @Override // com.xiaomi.idm.api.proto.RMIBasicDataType.DoubleOrBuilder
        public double getV() {
            return this.a;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(double d) {
            this.a = d;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = 0.0d;
        }

        public static Double parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Double) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static Double parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Double) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static Double parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Double) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static Double parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Double) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static Double parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Double) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static Double parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Double) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static Double parseFrom(InputStream inputStream) throws IOException {
            return (Double) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static Double parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Double) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static Double parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Double) parseDelimitedFrom(b, inputStream);
        }

        public static Double parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Double) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static Double parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Double) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static Double parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Double) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(Double r1) {
            return b.createBuilder(r1);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<Double, Builder> implements DoubleOrBuilder {
            private Builder() {
                super(Double.b);
            }

            @Override // com.xiaomi.idm.api.proto.RMIBasicDataType.DoubleOrBuilder
            public double getV() {
                return ((Double) this.instance).getV();
            }

            public Builder setV(double d) {
                copyOnWrite();
                ((Double) this.instance).a(d);
                return this;
            }

            public Builder clearV() {
                copyOnWrite();
                ((Double) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new Double();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0000", new Object[]{"v_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<Double> parser = c;
                    if (parser == null) {
                        synchronized (Double.class) {
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
            Double r0 = new Double();
            b = r0;
            GeneratedMessageLite.registerDefaultInstance(Double.class, r0);
        }

        public static Double getDefaultInstance() {
            return b;
        }

        public static Parser<Double> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class String extends GeneratedMessageLite<String, Builder> implements StringOrBuilder {
        public static final int V_FIELD_NUMBER = 1;
        private static final String b;
        private static volatile Parser<String> c;
        private String a = "";

        private String() {
        }

        @Override // com.xiaomi.idm.api.proto.RMIBasicDataType.StringOrBuilder
        public String getV() {
            return this.a;
        }

        @Override // com.xiaomi.idm.api.proto.RMIBasicDataType.StringOrBuilder
        public ByteString getVBytes() {
            return ByteString.copyFromUtf8(this.a);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(String str) {
            if (str != null) {
                this.a = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = getDefaultInstance().getV();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.a = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static String parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (String) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static String parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (String) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static String parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (String) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static String parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (String) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static String parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (String) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static String parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (String) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static String parseFrom(InputStream inputStream) throws IOException {
            return (String) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static String parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (String) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static String parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (String) parseDelimitedFrom(b, inputStream);
        }

        public static String parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (String) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static String parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (String) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static String parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (String) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(String string) {
            return b.createBuilder(string);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<String, Builder> implements StringOrBuilder {
            private Builder() {
                super(String.b);
            }

            @Override // com.xiaomi.idm.api.proto.RMIBasicDataType.StringOrBuilder
            public String getV() {
                return ((String) this.instance).getV();
            }

            @Override // com.xiaomi.idm.api.proto.RMIBasicDataType.StringOrBuilder
            public ByteString getVBytes() {
                return ((String) this.instance).getVBytes();
            }

            public Builder setV(String str) {
                copyOnWrite();
                ((String) this.instance).a(str);
                return this;
            }

            public Builder clearV() {
                copyOnWrite();
                ((String) this.instance).e();
                return this;
            }

            public Builder setVBytes(ByteString byteString) {
                copyOnWrite();
                ((String) this.instance).a(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new String();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ", new Object[]{"v_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<String> parser = c;
                    if (parser == null) {
                        synchronized (String.class) {
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
            String string = new String();
            b = string;
            GeneratedMessageLite.registerDefaultInstance(String.class, string);
        }

        public static String getDefaultInstance() {
            return b;
        }

        public static Parser<String> parser() {
            return b.getParserForType();
        }
    }
}
