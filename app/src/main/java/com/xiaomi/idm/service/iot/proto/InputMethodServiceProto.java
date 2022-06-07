package com.xiaomi.idm.service.iot.proto;

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
public final class InputMethodServiceProto {

    /* loaded from: classes3.dex */
    public interface InputCompleteEventOrBuilder extends MessageLiteOrBuilder {
        int getActionData();
    }

    /* loaded from: classes3.dex */
    public interface InputMethodResponseOrBuilder extends MessageLiteOrBuilder {
        int getCode();

        String getMessage();

        ByteString getMessageBytes();

        String getResponse();

        ByteString getResponseBytes();
    }

    /* loaded from: classes3.dex */
    public interface StartInputBoxOrBuilder extends MessageLiteOrBuilder {
        int getAid();

        int getCharacterType();

        String getClientId();

        ByteString getClientIdBytes();

        int getImeOptions();

        String getInputContent();

        ByteString getInputContentBytes();

        int getInputTextLength();

        int getMethodType();
    }

    /* loaded from: classes3.dex */
    public interface TextEventOrBuilder extends MessageLiteOrBuilder {
        String getInputData();

        ByteString getInputDataBytes();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private InputMethodServiceProto() {
    }

    /* loaded from: classes3.dex */
    public static final class StartInputBox extends GeneratedMessageLite<StartInputBox, Builder> implements StartInputBoxOrBuilder {
        public static final int AID_FIELD_NUMBER = 1;
        public static final int CHARACTERTYPE_FIELD_NUMBER = 7;
        public static final int CLIENTID_FIELD_NUMBER = 2;
        public static final int IMEOPTIONS_FIELD_NUMBER = 4;
        public static final int INPUTCONTENT_FIELD_NUMBER = 5;
        public static final int INPUTTEXTLENGTH_FIELD_NUMBER = 6;
        public static final int METHODTYPE_FIELD_NUMBER = 3;
        private static final StartInputBox h;
        private static volatile Parser<StartInputBox> i;
        private int a;
        private int c;
        private int d;
        private int f;
        private int g;
        private String b = "";
        private String e = "";

        private StartInputBox() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
        public int getAid() {
            return this.a;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(int i2) {
            this.a = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e() {
            this.a = 0;
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
        public String getClientId() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
        public ByteString getClientIdBytes() {
            return ByteString.copyFromUtf8(this.b);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(String str) {
            if (str != null) {
                this.b = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = getDefaultInstance().getClientId();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
        public int getMethodType() {
            return this.c;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void c(int i2) {
            this.c = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g() {
            this.c = 0;
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
        public int getImeOptions() {
            return this.d;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void d(int i2) {
            this.d = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void h() {
            this.d = 0;
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
        public String getInputContent() {
            return this.e;
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
        public ByteString getInputContentBytes() {
            return ByteString.copyFromUtf8(this.e);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(String str) {
            if (str != null) {
                this.e = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void i() {
            this.e = getDefaultInstance().getInputContent();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.e = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
        public int getInputTextLength() {
            return this.f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void e(int i2) {
            this.f = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void j() {
            this.f = 0;
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
        public int getCharacterType() {
            return this.g;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f(int i2) {
            this.g = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void k() {
            this.g = 0;
        }

        public static StartInputBox parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (StartInputBox) GeneratedMessageLite.parseFrom(h, byteBuffer);
        }

        public static StartInputBox parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (StartInputBox) GeneratedMessageLite.parseFrom(h, byteBuffer, extensionRegistryLite);
        }

        public static StartInputBox parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (StartInputBox) GeneratedMessageLite.parseFrom(h, byteString);
        }

        public static StartInputBox parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (StartInputBox) GeneratedMessageLite.parseFrom(h, byteString, extensionRegistryLite);
        }

        public static StartInputBox parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (StartInputBox) GeneratedMessageLite.parseFrom(h, bArr);
        }

        public static StartInputBox parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (StartInputBox) GeneratedMessageLite.parseFrom(h, bArr, extensionRegistryLite);
        }

        public static StartInputBox parseFrom(InputStream inputStream) throws IOException {
            return (StartInputBox) GeneratedMessageLite.parseFrom(h, inputStream);
        }

        public static StartInputBox parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (StartInputBox) GeneratedMessageLite.parseFrom(h, inputStream, extensionRegistryLite);
        }

        public static StartInputBox parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (StartInputBox) parseDelimitedFrom(h, inputStream);
        }

        public static StartInputBox parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (StartInputBox) parseDelimitedFrom(h, inputStream, extensionRegistryLite);
        }

        public static StartInputBox parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (StartInputBox) GeneratedMessageLite.parseFrom(h, codedInputStream);
        }

        public static StartInputBox parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (StartInputBox) GeneratedMessageLite.parseFrom(h, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return h.createBuilder();
        }

        public static Builder newBuilder(StartInputBox startInputBox) {
            return h.createBuilder(startInputBox);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<StartInputBox, Builder> implements StartInputBoxOrBuilder {
            private Builder() {
                super(StartInputBox.h);
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
            public int getAid() {
                return ((StartInputBox) this.instance).getAid();
            }

            public Builder setAid(int i) {
                copyOnWrite();
                ((StartInputBox) this.instance).b(i);
                return this;
            }

            public Builder clearAid() {
                copyOnWrite();
                ((StartInputBox) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
            public String getClientId() {
                return ((StartInputBox) this.instance).getClientId();
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
            public ByteString getClientIdBytes() {
                return ((StartInputBox) this.instance).getClientIdBytes();
            }

            public Builder setClientId(String str) {
                copyOnWrite();
                ((StartInputBox) this.instance).a(str);
                return this;
            }

            public Builder clearClientId() {
                copyOnWrite();
                ((StartInputBox) this.instance).f();
                return this;
            }

            public Builder setClientIdBytes(ByteString byteString) {
                copyOnWrite();
                ((StartInputBox) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
            public int getMethodType() {
                return ((StartInputBox) this.instance).getMethodType();
            }

            public Builder setMethodType(int i) {
                copyOnWrite();
                ((StartInputBox) this.instance).c(i);
                return this;
            }

            public Builder clearMethodType() {
                copyOnWrite();
                ((StartInputBox) this.instance).g();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
            public int getImeOptions() {
                return ((StartInputBox) this.instance).getImeOptions();
            }

            public Builder setImeOptions(int i) {
                copyOnWrite();
                ((StartInputBox) this.instance).d(i);
                return this;
            }

            public Builder clearImeOptions() {
                copyOnWrite();
                ((StartInputBox) this.instance).h();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
            public String getInputContent() {
                return ((StartInputBox) this.instance).getInputContent();
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
            public ByteString getInputContentBytes() {
                return ((StartInputBox) this.instance).getInputContentBytes();
            }

            public Builder setInputContent(String str) {
                copyOnWrite();
                ((StartInputBox) this.instance).b(str);
                return this;
            }

            public Builder clearInputContent() {
                copyOnWrite();
                ((StartInputBox) this.instance).i();
                return this;
            }

            public Builder setInputContentBytes(ByteString byteString) {
                copyOnWrite();
                ((StartInputBox) this.instance).b(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
            public int getInputTextLength() {
                return ((StartInputBox) this.instance).getInputTextLength();
            }

            public Builder setInputTextLength(int i) {
                copyOnWrite();
                ((StartInputBox) this.instance).e(i);
                return this;
            }

            public Builder clearInputTextLength() {
                copyOnWrite();
                ((StartInputBox) this.instance).j();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.StartInputBoxOrBuilder
            public int getCharacterType() {
                return ((StartInputBox) this.instance).getCharacterType();
            }

            public Builder setCharacterType(int i) {
                copyOnWrite();
                ((StartInputBox) this.instance).f(i);
                return this;
            }

            public Builder clearCharacterType() {
                copyOnWrite();
                ((StartInputBox) this.instance).k();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new StartInputBox();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(h, "\u0000\u0007\u0000\u0000\u0001\u0007\u0007\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003\u0004\u0004\u0004\u0005Ȉ\u0006\u0004\u0007\u0004", new Object[]{"aid_", "clientId_", "methodType_", "imeOptions_", "inputContent_", "inputTextLength_", "characterType_"});
                case GET_DEFAULT_INSTANCE:
                    return h;
                case GET_PARSER:
                    Parser<StartInputBox> parser = i;
                    if (parser == null) {
                        synchronized (StartInputBox.class) {
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
            StartInputBox startInputBox = new StartInputBox();
            h = startInputBox;
            GeneratedMessageLite.registerDefaultInstance(StartInputBox.class, startInputBox);
        }

        public static StartInputBox getDefaultInstance() {
            return h;
        }

        public static Parser<StartInputBox> parser() {
            return h.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class InputMethodResponse extends GeneratedMessageLite<InputMethodResponse, Builder> implements InputMethodResponseOrBuilder {
        public static final int CODE_FIELD_NUMBER = 1;
        public static final int MESSAGE_FIELD_NUMBER = 2;
        public static final int RESPONSE_FIELD_NUMBER = 3;
        private static final InputMethodResponse d;
        private static volatile Parser<InputMethodResponse> e;
        private int a;
        private String b = "";
        private String c = "";

        private InputMethodResponse() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.InputMethodResponseOrBuilder
        public int getCode() {
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

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.InputMethodResponseOrBuilder
        public String getMessage() {
            return this.b;
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.InputMethodResponseOrBuilder
        public ByteString getMessageBytes() {
            return ByteString.copyFromUtf8(this.b);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(String str) {
            if (str != null) {
                this.b = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void f() {
            this.b = getDefaultInstance().getMessage();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.b = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.InputMethodResponseOrBuilder
        public String getResponse() {
            return this.c;
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.InputMethodResponseOrBuilder
        public ByteString getResponseBytes() {
            return ByteString.copyFromUtf8(this.c);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(String str) {
            if (str != null) {
                this.c = str;
                return;
            }
            throw new NullPointerException();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void g() {
            this.c = getDefaultInstance().getResponse();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(ByteString byteString) {
            if (byteString != null) {
                checkByteStringIsUtf8(byteString);
                this.c = byteString.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static InputMethodResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (InputMethodResponse) GeneratedMessageLite.parseFrom(d, byteBuffer);
        }

        public static InputMethodResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (InputMethodResponse) GeneratedMessageLite.parseFrom(d, byteBuffer, extensionRegistryLite);
        }

        public static InputMethodResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (InputMethodResponse) GeneratedMessageLite.parseFrom(d, byteString);
        }

        public static InputMethodResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (InputMethodResponse) GeneratedMessageLite.parseFrom(d, byteString, extensionRegistryLite);
        }

        public static InputMethodResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (InputMethodResponse) GeneratedMessageLite.parseFrom(d, bArr);
        }

        public static InputMethodResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (InputMethodResponse) GeneratedMessageLite.parseFrom(d, bArr, extensionRegistryLite);
        }

        public static InputMethodResponse parseFrom(InputStream inputStream) throws IOException {
            return (InputMethodResponse) GeneratedMessageLite.parseFrom(d, inputStream);
        }

        public static InputMethodResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (InputMethodResponse) GeneratedMessageLite.parseFrom(d, inputStream, extensionRegistryLite);
        }

        public static InputMethodResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (InputMethodResponse) parseDelimitedFrom(d, inputStream);
        }

        public static InputMethodResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (InputMethodResponse) parseDelimitedFrom(d, inputStream, extensionRegistryLite);
        }

        public static InputMethodResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (InputMethodResponse) GeneratedMessageLite.parseFrom(d, codedInputStream);
        }

        public static InputMethodResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (InputMethodResponse) GeneratedMessageLite.parseFrom(d, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return d.createBuilder();
        }

        public static Builder newBuilder(InputMethodResponse inputMethodResponse) {
            return d.createBuilder(inputMethodResponse);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<InputMethodResponse, Builder> implements InputMethodResponseOrBuilder {
            private Builder() {
                super(InputMethodResponse.d);
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.InputMethodResponseOrBuilder
            public int getCode() {
                return ((InputMethodResponse) this.instance).getCode();
            }

            public Builder setCode(int i) {
                copyOnWrite();
                ((InputMethodResponse) this.instance).b(i);
                return this;
            }

            public Builder clearCode() {
                copyOnWrite();
                ((InputMethodResponse) this.instance).e();
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.InputMethodResponseOrBuilder
            public String getMessage() {
                return ((InputMethodResponse) this.instance).getMessage();
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.InputMethodResponseOrBuilder
            public ByteString getMessageBytes() {
                return ((InputMethodResponse) this.instance).getMessageBytes();
            }

            public Builder setMessage(String str) {
                copyOnWrite();
                ((InputMethodResponse) this.instance).a(str);
                return this;
            }

            public Builder clearMessage() {
                copyOnWrite();
                ((InputMethodResponse) this.instance).f();
                return this;
            }

            public Builder setMessageBytes(ByteString byteString) {
                copyOnWrite();
                ((InputMethodResponse) this.instance).a(byteString);
                return this;
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.InputMethodResponseOrBuilder
            public String getResponse() {
                return ((InputMethodResponse) this.instance).getResponse();
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.InputMethodResponseOrBuilder
            public ByteString getResponseBytes() {
                return ((InputMethodResponse) this.instance).getResponseBytes();
            }

            public Builder setResponse(String str) {
                copyOnWrite();
                ((InputMethodResponse) this.instance).b(str);
                return this;
            }

            public Builder clearResponse() {
                copyOnWrite();
                ((InputMethodResponse) this.instance).g();
                return this;
            }

            public Builder setResponseBytes(ByteString byteString) {
                copyOnWrite();
                ((InputMethodResponse) this.instance).b(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new InputMethodResponse();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(d, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0002Ȉ\u0003Ȉ", new Object[]{"code_", "message_", "response_"});
                case GET_DEFAULT_INSTANCE:
                    return d;
                case GET_PARSER:
                    Parser<InputMethodResponse> parser = e;
                    if (parser == null) {
                        synchronized (InputMethodResponse.class) {
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
            InputMethodResponse inputMethodResponse = new InputMethodResponse();
            d = inputMethodResponse;
            GeneratedMessageLite.registerDefaultInstance(InputMethodResponse.class, inputMethodResponse);
        }

        public static InputMethodResponse getDefaultInstance() {
            return d;
        }

        public static Parser<InputMethodResponse> parser() {
            return d.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class TextEvent extends GeneratedMessageLite<TextEvent, Builder> implements TextEventOrBuilder {
        public static final int INPUTDATA_FIELD_NUMBER = 1;
        private static final TextEvent b;
        private static volatile Parser<TextEvent> c;
        private String a = "";

        private TextEvent() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.TextEventOrBuilder
        public String getInputData() {
            return this.a;
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.TextEventOrBuilder
        public ByteString getInputDataBytes() {
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
            this.a = getDefaultInstance().getInputData();
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

        public static TextEvent parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (TextEvent) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static TextEvent parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (TextEvent) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static TextEvent parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (TextEvent) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static TextEvent parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (TextEvent) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static TextEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (TextEvent) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static TextEvent parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (TextEvent) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static TextEvent parseFrom(InputStream inputStream) throws IOException {
            return (TextEvent) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static TextEvent parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (TextEvent) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static TextEvent parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (TextEvent) parseDelimitedFrom(b, inputStream);
        }

        public static TextEvent parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (TextEvent) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static TextEvent parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (TextEvent) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static TextEvent parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (TextEvent) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(TextEvent textEvent) {
            return b.createBuilder(textEvent);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<TextEvent, Builder> implements TextEventOrBuilder {
            private Builder() {
                super(TextEvent.b);
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.TextEventOrBuilder
            public String getInputData() {
                return ((TextEvent) this.instance).getInputData();
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.TextEventOrBuilder
            public ByteString getInputDataBytes() {
                return ((TextEvent) this.instance).getInputDataBytes();
            }

            public Builder setInputData(String str) {
                copyOnWrite();
                ((TextEvent) this.instance).a(str);
                return this;
            }

            public Builder clearInputData() {
                copyOnWrite();
                ((TextEvent) this.instance).e();
                return this;
            }

            public Builder setInputDataBytes(ByteString byteString) {
                copyOnWrite();
                ((TextEvent) this.instance).a(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new TextEvent();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ", new Object[]{"inputData_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<TextEvent> parser = c;
                    if (parser == null) {
                        synchronized (TextEvent.class) {
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
            TextEvent textEvent = new TextEvent();
            b = textEvent;
            GeneratedMessageLite.registerDefaultInstance(TextEvent.class, textEvent);
        }

        public static TextEvent getDefaultInstance() {
            return b;
        }

        public static Parser<TextEvent> parser() {
            return b.getParserForType();
        }
    }

    /* loaded from: classes3.dex */
    public static final class InputCompleteEvent extends GeneratedMessageLite<InputCompleteEvent, Builder> implements InputCompleteEventOrBuilder {
        public static final int ACTIONDATA_FIELD_NUMBER = 1;
        private static final InputCompleteEvent b;
        private static volatile Parser<InputCompleteEvent> c;
        private int a;

        private InputCompleteEvent() {
        }

        @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.InputCompleteEventOrBuilder
        public int getActionData() {
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

        public static InputCompleteEvent parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (InputCompleteEvent) GeneratedMessageLite.parseFrom(b, byteBuffer);
        }

        public static InputCompleteEvent parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (InputCompleteEvent) GeneratedMessageLite.parseFrom(b, byteBuffer, extensionRegistryLite);
        }

        public static InputCompleteEvent parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (InputCompleteEvent) GeneratedMessageLite.parseFrom(b, byteString);
        }

        public static InputCompleteEvent parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (InputCompleteEvent) GeneratedMessageLite.parseFrom(b, byteString, extensionRegistryLite);
        }

        public static InputCompleteEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (InputCompleteEvent) GeneratedMessageLite.parseFrom(b, bArr);
        }

        public static InputCompleteEvent parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (InputCompleteEvent) GeneratedMessageLite.parseFrom(b, bArr, extensionRegistryLite);
        }

        public static InputCompleteEvent parseFrom(InputStream inputStream) throws IOException {
            return (InputCompleteEvent) GeneratedMessageLite.parseFrom(b, inputStream);
        }

        public static InputCompleteEvent parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (InputCompleteEvent) GeneratedMessageLite.parseFrom(b, inputStream, extensionRegistryLite);
        }

        public static InputCompleteEvent parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (InputCompleteEvent) parseDelimitedFrom(b, inputStream);
        }

        public static InputCompleteEvent parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (InputCompleteEvent) parseDelimitedFrom(b, inputStream, extensionRegistryLite);
        }

        public static InputCompleteEvent parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (InputCompleteEvent) GeneratedMessageLite.parseFrom(b, codedInputStream);
        }

        public static InputCompleteEvent parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (InputCompleteEvent) GeneratedMessageLite.parseFrom(b, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return b.createBuilder();
        }

        public static Builder newBuilder(InputCompleteEvent inputCompleteEvent) {
            return b.createBuilder(inputCompleteEvent);
        }

        /* loaded from: classes3.dex */
        public static final class Builder extends GeneratedMessageLite.Builder<InputCompleteEvent, Builder> implements InputCompleteEventOrBuilder {
            private Builder() {
                super(InputCompleteEvent.b);
            }

            @Override // com.xiaomi.idm.service.iot.proto.InputMethodServiceProto.InputCompleteEventOrBuilder
            public int getActionData() {
                return ((InputCompleteEvent) this.instance).getActionData();
            }

            public Builder setActionData(int i) {
                copyOnWrite();
                ((InputCompleteEvent) this.instance).b(i);
                return this;
            }

            public Builder clearActionData() {
                copyOnWrite();
                ((InputCompleteEvent) this.instance).e();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new InputCompleteEvent();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(b, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004", new Object[]{"actionData_"});
                case GET_DEFAULT_INSTANCE:
                    return b;
                case GET_PARSER:
                    Parser<InputCompleteEvent> parser = c;
                    if (parser == null) {
                        synchronized (InputCompleteEvent.class) {
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
            InputCompleteEvent inputCompleteEvent = new InputCompleteEvent();
            b = inputCompleteEvent;
            GeneratedMessageLite.registerDefaultInstance(InputCompleteEvent.class, inputCompleteEvent);
        }

        public static InputCompleteEvent getDefaultInstance() {
            return b;
        }

        public static Parser<InputCompleteEvent> parser() {
            return b.getParserForType();
        }
    }
}
