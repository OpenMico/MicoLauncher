package io.netty.handler.codec.http;

import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.Headers;
import io.netty.handler.codec.ValueConverter;
import io.netty.util.AsciiString;
import io.netty.util.HashingStrategy;
import io.netty.util.internal.StringUtil;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class CombinedHttpHeaders extends DefaultHttpHeaders {
    public CombinedHttpHeaders(boolean z) {
        super(new a(AsciiString.CASE_INSENSITIVE_HASHER, a(z), b(z)));
    }

    /* loaded from: classes4.dex */
    private static final class a extends DefaultHeaders<CharSequence, CharSequence, a> {
        private AbstractC0202a<Object> b;
        private AbstractC0202a<CharSequence> c;

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: io.netty.handler.codec.http.CombinedHttpHeaders$a$a  reason: collision with other inner class name */
        /* loaded from: classes4.dex */
        public interface AbstractC0202a<T> {
            CharSequence a(T t);
        }

        @Override // io.netty.handler.codec.DefaultHeaders, io.netty.handler.codec.Headers
        public /* synthetic */ Headers add(Object obj, Iterable iterable) {
            return a((CharSequence) obj, (Iterable<? extends CharSequence>) iterable);
        }

        private AbstractC0202a<Object> a() {
            if (this.b == null) {
                this.b = new AbstractC0202a<Object>() { // from class: io.netty.handler.codec.http.CombinedHttpHeaders.a.1
                    @Override // io.netty.handler.codec.http.CombinedHttpHeaders.a.AbstractC0202a
                    public CharSequence a(Object obj) {
                        return StringUtil.escapeCsv((CharSequence) a.this.valueConverter().convertObject(obj));
                    }
                };
            }
            return this.b;
        }

        private AbstractC0202a<CharSequence> b() {
            if (this.c == null) {
                this.c = new AbstractC0202a<CharSequence>() { // from class: io.netty.handler.codec.http.CombinedHttpHeaders.a.2
                    public CharSequence a(CharSequence charSequence) {
                        return StringUtil.escapeCsv(charSequence);
                    }
                };
            }
            return this.c;
        }

        public a(HashingStrategy<CharSequence> hashingStrategy, ValueConverter<CharSequence> valueConverter, DefaultHeaders.NameValidator<CharSequence> nameValidator) {
            super(hashingStrategy, valueConverter, nameValidator);
        }

        /* renamed from: a */
        public List<CharSequence> getAll(CharSequence charSequence) {
            List<CharSequence> all = super.getAll(charSequence);
            if (all.isEmpty()) {
                return all;
            }
            if (all.size() == 1) {
                return StringUtil.unescapeCsvFields(all.get(0));
            }
            throw new IllegalStateException("CombinedHttpHeaders should only have one value");
        }

        /* renamed from: a */
        public a add(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
            if (headers != this) {
                if (!(headers instanceof a)) {
                    for (Map.Entry<? extends CharSequence, ? extends CharSequence> entry : headers) {
                        add((CharSequence) entry.getKey(), (CharSequence) entry.getValue());
                    }
                } else if (isEmpty()) {
                    addImpl(headers);
                } else {
                    for (Map.Entry<? extends CharSequence, ? extends CharSequence> entry2 : headers) {
                        b((CharSequence) entry2.getKey(), (CharSequence) entry2.getValue());
                    }
                }
                return this;
            }
            throw new IllegalArgumentException("can't add to itself.");
        }

        /* renamed from: b */
        public a set(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
            if (headers == this) {
                return this;
            }
            clear();
            return add(headers);
        }

        /* renamed from: c */
        public a setAll(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
            if (headers == this) {
                return this;
            }
            for (CharSequence charSequence : headers.names()) {
                remove(charSequence);
            }
            return add(headers);
        }

        /* renamed from: a */
        public a add(CharSequence charSequence, CharSequence charSequence2) {
            return b(charSequence, StringUtil.escapeCsv(charSequence2));
        }

        /* renamed from: a */
        public a add(CharSequence charSequence, CharSequence... charSequenceArr) {
            return b(charSequence, a(b(), charSequenceArr));
        }

        public a a(CharSequence charSequence, Iterable<? extends CharSequence> iterable) {
            return b(charSequence, a(b(), iterable));
        }

        /* renamed from: b */
        public a addObject(CharSequence charSequence, Iterable<?> iterable) {
            return b(charSequence, a(a(), iterable));
        }

        /* renamed from: a */
        public a addObject(CharSequence charSequence, Object... objArr) {
            return b(charSequence, a(a(), objArr));
        }

        /* renamed from: b */
        public a set(CharSequence charSequence, CharSequence... charSequenceArr) {
            super.set((a) charSequence, a(b(), charSequenceArr));
            return this;
        }

        /* renamed from: c */
        public a set(CharSequence charSequence, Iterable<? extends CharSequence> iterable) {
            super.set((a) charSequence, a(b(), iterable));
            return this;
        }

        /* renamed from: a */
        public a setObject(CharSequence charSequence, Object obj) {
            super.set((a) charSequence, a(a(), obj));
            return this;
        }

        /* renamed from: b */
        public a setObject(CharSequence charSequence, Object... objArr) {
            super.set((a) charSequence, a(a(), objArr));
            return this;
        }

        /* renamed from: d */
        public a setObject(CharSequence charSequence, Iterable<?> iterable) {
            super.set((a) charSequence, a(a(), iterable));
            return this;
        }

        private a b(CharSequence charSequence, CharSequence charSequence2) {
            CharSequence charSequence3 = (CharSequence) super.get(charSequence);
            if (charSequence3 == null) {
                super.add((a) charSequence, charSequence2);
            } else {
                super.set((a) charSequence, c(charSequence3, charSequence2));
            }
            return this;
        }

        private static <T> CharSequence a(AbstractC0202a<T> aVar, T... tArr) {
            StringBuilder sb = new StringBuilder(tArr.length * 10);
            if (tArr.length > 0) {
                int length = tArr.length - 1;
                for (int i = 0; i < length; i++) {
                    sb.append(aVar.a(tArr[i]));
                    sb.append(StringUtil.COMMA);
                }
                sb.append(aVar.a(tArr[length]));
            }
            return sb;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private static <T> CharSequence a(AbstractC0202a<T> aVar, Iterable<? extends T> iterable) {
            StringBuilder sb = iterable instanceof Collection ? new StringBuilder(((Collection) iterable).size() * 10) : new StringBuilder();
            Iterator<? extends T> it = iterable.iterator();
            if (it.hasNext()) {
                Object next = it.next();
                while (it.hasNext()) {
                    sb.append(aVar.a(next));
                    sb.append(StringUtil.COMMA);
                    next = it.next();
                }
                sb.append(aVar.a(next));
            }
            return sb;
        }

        private CharSequence c(CharSequence charSequence, CharSequence charSequence2) {
            StringBuilder sb = new StringBuilder(charSequence.length() + 1 + charSequence2.length());
            sb.append(charSequence);
            sb.append(StringUtil.COMMA);
            sb.append(charSequence2);
            return sb;
        }
    }
}
