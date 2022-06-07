package io.netty.handler.codec.http;

import io.netty.handler.codec.CharSequenceValueConverter;
import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.DefaultHeadersImpl;
import io.netty.handler.codec.HeadersUtils;
import io.netty.handler.codec.ValueConverter;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.PlatformDependent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public class DefaultHttpHeaders extends HttpHeaders {
    private final DefaultHeaders<CharSequence, CharSequence, ?> c;
    private static final ByteProcessor b = new ByteProcessor() { // from class: io.netty.handler.codec.http.DefaultHttpHeaders.1
        @Override // io.netty.util.ByteProcessor
        public boolean process(byte b2) throws Exception {
            DefaultHttpHeaders.b(b2);
            return true;
        }
    };
    static final DefaultHeaders.NameValidator<CharSequence> a = new DefaultHeaders.NameValidator<CharSequence>() { // from class: io.netty.handler.codec.http.DefaultHttpHeaders.2
        /* renamed from: a */
        public void validateName(CharSequence charSequence) {
            if (charSequence == null || charSequence.length() == 0) {
                throw new IllegalArgumentException("empty headers are not allowed [" + ((Object) charSequence) + "]");
            } else if (charSequence instanceof AsciiString) {
                try {
                    ((AsciiString) charSequence).forEachByte(DefaultHttpHeaders.b);
                } catch (Exception e) {
                    PlatformDependent.throwException(e);
                }
            } else {
                for (int i = 0; i < charSequence.length(); i++) {
                    DefaultHttpHeaders.b(charSequence.charAt(i));
                }
            }
        }
    };

    public DefaultHttpHeaders() {
        this(true);
    }

    public DefaultHttpHeaders(boolean z) {
        this(z, b(z));
    }

    public DefaultHttpHeaders(boolean z, DefaultHeaders.NameValidator<CharSequence> nameValidator) {
        this(new DefaultHeadersImpl(AsciiString.CASE_INSENSITIVE_HASHER, a(z), nameValidator));
    }

    public DefaultHttpHeaders(DefaultHeaders<CharSequence, CharSequence, ?> defaultHeaders) {
        this.c = defaultHeaders;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders add(HttpHeaders httpHeaders) {
        if (!(httpHeaders instanceof DefaultHttpHeaders)) {
            return super.add(httpHeaders);
        }
        this.c.add(((DefaultHttpHeaders) httpHeaders).c);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders set(HttpHeaders httpHeaders) {
        if (!(httpHeaders instanceof DefaultHttpHeaders)) {
            return super.set(httpHeaders);
        }
        this.c.set(((DefaultHttpHeaders) httpHeaders).c);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders add(String str, Object obj) {
        this.c.addObject((DefaultHeaders<CharSequence, CharSequence, ?>) str, obj);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders add(CharSequence charSequence, Object obj) {
        this.c.addObject((DefaultHeaders<CharSequence, CharSequence, ?>) charSequence, obj);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders add(String str, Iterable<?> iterable) {
        this.c.addObject((DefaultHeaders<CharSequence, CharSequence, ?>) str, iterable);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders add(CharSequence charSequence, Iterable<?> iterable) {
        this.c.addObject((DefaultHeaders<CharSequence, CharSequence, ?>) charSequence, iterable);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders addInt(CharSequence charSequence, int i) {
        this.c.addInt(charSequence, i);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders addShort(CharSequence charSequence, short s) {
        this.c.addShort(charSequence, s);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders remove(String str) {
        this.c.remove(str);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders remove(CharSequence charSequence) {
        this.c.remove(charSequence);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders set(String str, Object obj) {
        this.c.setObject((DefaultHeaders<CharSequence, CharSequence, ?>) str, obj);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders set(CharSequence charSequence, Object obj) {
        this.c.setObject((DefaultHeaders<CharSequence, CharSequence, ?>) charSequence, obj);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders set(String str, Iterable<?> iterable) {
        this.c.setObject((DefaultHeaders<CharSequence, CharSequence, ?>) str, iterable);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders set(CharSequence charSequence, Iterable<?> iterable) {
        this.c.setObject((DefaultHeaders<CharSequence, CharSequence, ?>) charSequence, iterable);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders setInt(CharSequence charSequence, int i) {
        this.c.setInt(charSequence, i);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders setShort(CharSequence charSequence, short s) {
        this.c.setShort(charSequence, s);
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public HttpHeaders clear() {
        this.c.clear();
        return this;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public String get(String str) {
        return get((CharSequence) str);
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public String get(CharSequence charSequence) {
        return HeadersUtils.getAsString(this.c, charSequence);
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public Integer getInt(CharSequence charSequence) {
        return this.c.getInt(charSequence);
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public int getInt(CharSequence charSequence, int i) {
        return this.c.getInt(charSequence, i);
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public Short getShort(CharSequence charSequence) {
        return this.c.getShort(charSequence);
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public short getShort(CharSequence charSequence, short s) {
        return this.c.getShort(charSequence, s);
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public Long getTimeMillis(CharSequence charSequence) {
        return this.c.getTimeMillis(charSequence);
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public long getTimeMillis(CharSequence charSequence, long j) {
        return this.c.getTimeMillis(charSequence, j);
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public List<String> getAll(String str) {
        return getAll((CharSequence) str);
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public List<String> getAll(CharSequence charSequence) {
        return HeadersUtils.getAllAsString(this.c, charSequence);
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public List<Map.Entry<String, String>> entries() {
        if (isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(this.c.size());
        Iterator<Map.Entry<String, String>> it = iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }

    @Override // io.netty.handler.codec.http.HttpHeaders, java.lang.Iterable
    @Deprecated
    public Iterator<Map.Entry<String, String>> iterator() {
        return HeadersUtils.iteratorAsString(this.c);
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public Iterator<Map.Entry<CharSequence, CharSequence>> iteratorCharSequence() {
        return this.c.iterator();
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public boolean contains(String str) {
        return contains((CharSequence) str);
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public boolean contains(CharSequence charSequence) {
        return this.c.contains(charSequence);
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public boolean isEmpty() {
        return this.c.isEmpty();
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public int size() {
        return this.c.size();
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public boolean contains(String str, String str2, boolean z) {
        return contains((CharSequence) str, (CharSequence) str2, z);
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public boolean contains(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        return this.c.contains(charSequence, charSequence2, z ? AsciiString.CASE_INSENSITIVE_HASHER : AsciiString.CASE_SENSITIVE_HASHER);
    }

    @Override // io.netty.handler.codec.http.HttpHeaders
    public Set<String> names() {
        return HeadersUtils.namesAsString(this.c);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DefaultHttpHeaders)) {
            return false;
        }
        return this.c.equals(((DefaultHttpHeaders) obj).c, AsciiString.CASE_SENSITIVE_HASHER);
    }

    public int hashCode() {
        return this.c.hashCode(AsciiString.CASE_SENSITIVE_HASHER);
    }

    public static void b(byte b2) {
        if (!(b2 == 0 || b2 == 32 || b2 == 44 || b2 == 61)) {
            switch (b2) {
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                    break;
                default:
                    switch (b2) {
                        case 58:
                        case 59:
                            break;
                        default:
                            if (b2 < 0) {
                                throw new IllegalArgumentException("a header name cannot contain non-ASCII character: " + ((int) b2));
                            }
                            return;
                    }
            }
        }
        throw new IllegalArgumentException("a header name cannot contain the following prohibited characters: =,;: \\t\\r\\n\\v\\f: " + ((int) b2));
    }

    public static void b(char c) {
        if (!(c == 0 || c == ' ' || c == ',' || c == '=')) {
            switch (c) {
                case '\t':
                case '\n':
                case 11:
                case '\f':
                case '\r':
                    break;
                default:
                    switch (c) {
                        case ':':
                        case ';':
                            break;
                        default:
                            if (c > 127) {
                                throw new IllegalArgumentException("a header name cannot contain non-ASCII character: " + c);
                            }
                            return;
                    }
            }
        }
        throw new IllegalArgumentException("a header name cannot contain the following prohibited characters: =,;: \\t\\r\\n\\v\\f: " + c);
    }

    static ValueConverter<CharSequence> a(boolean z) {
        return z ? b.b : a.a;
    }

    static DefaultHeaders.NameValidator<CharSequence> b(boolean z) {
        return z ? a : DefaultHeaders.NameValidator.NOT_NULL;
    }

    /* loaded from: classes4.dex */
    public static class a extends CharSequenceValueConverter {
        static final a a = new a();

        private a() {
        }

        @Override // io.netty.handler.codec.CharSequenceValueConverter, io.netty.handler.codec.ValueConverter
        public CharSequence convertObject(Object obj) {
            if (obj instanceof CharSequence) {
                return (CharSequence) obj;
            }
            if (obj instanceof Date) {
                return HttpHeaderDateFormat.get().format((Date) obj);
            }
            if (obj instanceof Calendar) {
                return HttpHeaderDateFormat.get().format(((Calendar) obj).getTime());
            }
            return obj.toString();
        }
    }

    /* loaded from: classes4.dex */
    public static final class b extends a {
        static final b b = new b();

        private b() {
            super();
        }

        @Override // io.netty.handler.codec.http.DefaultHttpHeaders.a, io.netty.handler.codec.CharSequenceValueConverter, io.netty.handler.codec.ValueConverter
        public CharSequence convertObject(Object obj) {
            CharSequence convertObject = super.convertObject(obj);
            int i = 0;
            for (int i2 = 0; i2 < convertObject.length(); i2++) {
                i = a(convertObject, i, convertObject.charAt(i2));
            }
            if (i == 0) {
                return convertObject;
            }
            throw new IllegalArgumentException("a header value must not end with '\\r' or '\\n':" + ((Object) convertObject));
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private static int a(CharSequence charSequence, int i, char c) {
            if ((c & 65520) == 0) {
                if (c != 0) {
                    switch (c) {
                        case 11:
                            throw new IllegalArgumentException("a header value contains a prohibited character '\\v': " + ((Object) charSequence));
                        case '\f':
                            throw new IllegalArgumentException("a header value contains a prohibited character '\\f': " + ((Object) charSequence));
                    }
                } else {
                    throw new IllegalArgumentException("a header value contains a prohibited character '\u0000': " + ((Object) charSequence));
                }
            }
            switch (i) {
                case 0:
                    if (c == '\n') {
                        return 2;
                    }
                    if (c == '\r') {
                        return 1;
                    }
                    break;
                case 1:
                    if (c == '\n') {
                        return 2;
                    }
                    throw new IllegalArgumentException("only '\\n' is allowed after '\\r': " + ((Object) charSequence));
                case 2:
                    if (c == '\t' || c == ' ') {
                        return 0;
                    }
                    throw new IllegalArgumentException("only ' ' and '\\t' are allowed after '\\n': " + ((Object) charSequence));
            }
            return i;
        }
    }
}
