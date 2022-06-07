package io.netty.handler.codec.http2;

import io.netty.handler.codec.Headers;
import io.netty.util.AsciiString;
import java.util.Iterator;
import java.util.Map;
import okhttp3.internal.http2.Header;

/* loaded from: classes4.dex */
public interface Http2Headers extends Headers<CharSequence, CharSequence, Http2Headers> {
    Http2Headers authority(CharSequence charSequence);

    CharSequence authority();

    @Override // io.netty.handler.codec.Headers, java.lang.Iterable
    Iterator<Map.Entry<CharSequence, CharSequence>> iterator();

    Http2Headers method(CharSequence charSequence);

    CharSequence method();

    Http2Headers path(CharSequence charSequence);

    CharSequence path();

    Http2Headers scheme(CharSequence charSequence);

    CharSequence scheme();

    Http2Headers status(CharSequence charSequence);

    CharSequence status();

    /* loaded from: classes4.dex */
    public enum PseudoHeaderName {
        METHOD(Header.TARGET_METHOD_UTF8),
        SCHEME(Header.TARGET_SCHEME_UTF8),
        AUTHORITY(Header.TARGET_AUTHORITY_UTF8),
        PATH(Header.TARGET_PATH_UTF8),
        STATUS(Header.RESPONSE_STATUS_UTF8);
        
        private static final b<AsciiString> a = new b<>();
        private final AsciiString value;

        static {
            for (PseudoHeaderName pseudoHeaderName : values()) {
                a.add((b<AsciiString>) pseudoHeaderName.value(), AsciiString.EMPTY_STRING);
            }
        }

        PseudoHeaderName(String str) {
            this.value = new AsciiString(str);
        }

        public AsciiString value() {
            return this.value;
        }

        public static boolean isPseudoHeader(CharSequence charSequence) {
            return a.contains(charSequence);
        }
    }
}
