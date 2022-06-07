package io.netty.handler.codec.http2;

import io.netty.handler.codec.CharSequenceValueConverter;
import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.PlatformDependent;

/* loaded from: classes4.dex */
public class DefaultHttp2Headers extends DefaultHeaders<CharSequence, CharSequence, Http2Headers> implements Http2Headers {
    private static final ByteProcessor b = new ByteProcessor() { // from class: io.netty.handler.codec.http2.DefaultHttp2Headers.1
        @Override // io.netty.util.ByteProcessor
        public boolean process(byte b2) throws Exception {
            return !AsciiString.isUpperCase(b2);
        }
    };
    private static final DefaultHeaders.NameValidator<CharSequence> c = new DefaultHeaders.NameValidator<CharSequence>() { // from class: io.netty.handler.codec.http2.DefaultHttp2Headers.2
        /* renamed from: a */
        public void validateName(CharSequence charSequence) {
            if (charSequence == null || charSequence.length() == 0) {
                PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "empty headers are not allowed [%s]", charSequence));
            }
            if (charSequence instanceof AsciiString) {
                try {
                    if (((AsciiString) charSequence).forEachByte(DefaultHttp2Headers.b) != -1) {
                        PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "invalid header name [%s]", charSequence));
                    }
                } catch (Http2Exception e) {
                    PlatformDependent.throwException(e);
                } catch (Throwable th) {
                    PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, th, "unexpected error. invalid header name [%s]", charSequence));
                }
            } else {
                for (int i = 0; i < charSequence.length(); i++) {
                    if (AsciiString.isUpperCase(charSequence.charAt(i))) {
                        PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "invalid header name [%s]", charSequence));
                    }
                }
            }
        }
    };
    private DefaultHeaders.HeaderEntry<CharSequence, CharSequence> d;

    public DefaultHttp2Headers() {
        this(true);
    }

    public DefaultHttp2Headers(boolean z) {
        super(AsciiString.CASE_SENSITIVE_HASHER, CharSequenceValueConverter.INSTANCE, z ? c : DefaultHeaders.NameValidator.NOT_NULL);
        this.d = this.head;
    }

    public DefaultHttp2Headers(boolean z, int i) {
        super(AsciiString.CASE_SENSITIVE_HASHER, CharSequenceValueConverter.INSTANCE, z ? c : DefaultHeaders.NameValidator.NOT_NULL, i);
        this.d = this.head;
    }

    @Override // io.netty.handler.codec.DefaultHeaders, io.netty.handler.codec.Headers
    public Http2Headers clear() {
        this.d = this.head;
        return (Http2Headers) super.clear();
    }

    @Override // io.netty.handler.codec.DefaultHeaders
    public boolean equals(Object obj) {
        if (!(obj instanceof Http2Headers)) {
            return false;
        }
        return equals((Http2Headers) obj, AsciiString.CASE_SENSITIVE_HASHER);
    }

    @Override // io.netty.handler.codec.DefaultHeaders
    public int hashCode() {
        return hashCode(AsciiString.CASE_SENSITIVE_HASHER);
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public Http2Headers method(CharSequence charSequence) {
        set((DefaultHttp2Headers) Http2Headers.PseudoHeaderName.METHOD.value(), (AsciiString) charSequence);
        return this;
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public Http2Headers scheme(CharSequence charSequence) {
        set((DefaultHttp2Headers) Http2Headers.PseudoHeaderName.SCHEME.value(), (AsciiString) charSequence);
        return this;
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public Http2Headers authority(CharSequence charSequence) {
        set((DefaultHttp2Headers) Http2Headers.PseudoHeaderName.AUTHORITY.value(), (AsciiString) charSequence);
        return this;
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public Http2Headers path(CharSequence charSequence) {
        set((DefaultHttp2Headers) Http2Headers.PseudoHeaderName.PATH.value(), (AsciiString) charSequence);
        return this;
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public Http2Headers status(CharSequence charSequence) {
        set((DefaultHttp2Headers) Http2Headers.PseudoHeaderName.STATUS.value(), (AsciiString) charSequence);
        return this;
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public CharSequence method() {
        return get(Http2Headers.PseudoHeaderName.METHOD.value());
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public CharSequence scheme() {
        return get(Http2Headers.PseudoHeaderName.SCHEME.value());
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public CharSequence authority() {
        return get(Http2Headers.PseudoHeaderName.AUTHORITY.value());
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public CharSequence path() {
        return get(Http2Headers.PseudoHeaderName.PATH.value());
    }

    @Override // io.netty.handler.codec.http2.Http2Headers
    public CharSequence status() {
        return get(Http2Headers.PseudoHeaderName.STATUS.value());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final DefaultHeaders.HeaderEntry<CharSequence, CharSequence> newHeaderEntry(int i, CharSequence charSequence, CharSequence charSequence2, DefaultHeaders.HeaderEntry<CharSequence, CharSequence> headerEntry) {
        return new a(i, charSequence, charSequence2, headerEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class a extends DefaultHeaders.HeaderEntry<CharSequence, CharSequence> {
        protected a(int i, CharSequence charSequence, CharSequence charSequence2, DefaultHeaders.HeaderEntry<CharSequence, CharSequence> headerEntry) {
            super(i, charSequence);
            this.value = charSequence2;
            this.next = headerEntry;
            if (charSequence.length() == 0 || charSequence.charAt(0) != ':') {
                this.after = DefaultHttp2Headers.this.head;
                this.before = DefaultHttp2Headers.this.head.before();
                if (DefaultHttp2Headers.this.d == DefaultHttp2Headers.this.head) {
                    DefaultHttp2Headers.this.d = this;
                }
            } else {
                this.after = DefaultHttp2Headers.this.d;
                this.before = DefaultHttp2Headers.this.d.before();
            }
            pointNeighborsToThis();
        }

        @Override // io.netty.handler.codec.DefaultHeaders.HeaderEntry
        protected void remove() {
            if (this == DefaultHttp2Headers.this.d) {
                DefaultHttp2Headers defaultHttp2Headers = DefaultHttp2Headers.this;
                defaultHttp2Headers.d = defaultHttp2Headers.d.after();
            }
            super.remove();
        }
    }
}
