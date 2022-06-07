package retrofit2;

import com.google.common.net.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ParameterHandler.java */
/* loaded from: classes6.dex */
public abstract class g<T> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(i iVar, @Nullable T t) throws IOException;

    g() {
    }

    final g<Iterable<T>> a() {
        return new g<Iterable<T>>() { // from class: retrofit2.g.1
            @Override // retrofit2.g
            /* bridge */ /* synthetic */ void a(i iVar, @Nullable Object obj) throws IOException {
                a(iVar, (Iterable) ((Iterable) obj));
            }

            void a(i iVar, @Nullable Iterable<T> iterable) throws IOException {
                if (iterable != null) {
                    for (T t : iterable) {
                        g.this.a(iVar, t);
                    }
                }
            }
        };
    }

    final g<Object> b() {
        return new g<Object>() { // from class: retrofit2.g.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // retrofit2.g
            void a(i iVar, @Nullable Object obj) throws IOException {
                if (obj != null) {
                    int length = Array.getLength(obj);
                    for (int i2 = 0; i2 < length; i2++) {
                        g.this.a(iVar, Array.get(obj, i2));
                    }
                }
            }
        };
    }

    /* compiled from: ParameterHandler.java */
    /* loaded from: classes6.dex */
    static final class n extends g<Object> {
        private final Method a;
        private final int b;

        /* JADX INFO: Access modifiers changed from: package-private */
        public n(Method method, int i) {
            this.a = method;
            this.b = i;
        }

        @Override // retrofit2.g
        void a(i iVar, @Nullable Object obj) {
            if (obj != null) {
                iVar.a(obj);
                return;
            }
            throw m.a(this.a, this.b, "@Url parameter is null.", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler.java */
    /* loaded from: classes6.dex */
    static final class d<T> extends g<T> {
        private final String a;
        private final Converter<T, String> b;

        /* JADX INFO: Access modifiers changed from: package-private */
        public d(String str, Converter<T, String> converter) {
            this.a = (String) Objects.requireNonNull(str, "name == null");
            this.b = converter;
        }

        @Override // retrofit2.g
        void a(i iVar, @Nullable T t) throws IOException {
            String convert;
            if (t != null && (convert = this.b.convert(t)) != null) {
                iVar.a(this.a, convert);
            }
        }
    }

    /* compiled from: ParameterHandler.java */
    /* loaded from: classes6.dex */
    static final class i<T> extends g<T> {
        private final Method a;
        private final int b;
        private final String c;
        private final Converter<T, String> d;
        private final boolean e;

        /* JADX INFO: Access modifiers changed from: package-private */
        public i(Method method, int i, String str, Converter<T, String> converter, boolean z) {
            this.a = method;
            this.b = i;
            this.c = (String) Objects.requireNonNull(str, "name == null");
            this.d = converter;
            this.e = z;
        }

        @Override // retrofit2.g
        void a(i iVar, @Nullable T t) throws IOException {
            if (t != null) {
                iVar.a(this.c, this.d.convert(t), this.e);
                return;
            }
            Method method = this.a;
            int i = this.b;
            throw m.a(method, i, "Path parameter \"" + this.c + "\" value must not be null.", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler.java */
    /* loaded from: classes6.dex */
    static final class j<T> extends g<T> {
        private final String a;
        private final Converter<T, String> b;
        private final boolean c;

        /* JADX INFO: Access modifiers changed from: package-private */
        public j(String str, Converter<T, String> converter, boolean z) {
            this.a = (String) Objects.requireNonNull(str, "name == null");
            this.b = converter;
            this.c = z;
        }

        @Override // retrofit2.g
        void a(i iVar, @Nullable T t) throws IOException {
            String convert;
            if (t != null && (convert = this.b.convert(t)) != null) {
                iVar.b(this.a, convert, this.c);
            }
        }
    }

    /* compiled from: ParameterHandler.java */
    /* loaded from: classes6.dex */
    static final class l<T> extends g<T> {
        private final Converter<T, String> a;
        private final boolean b;

        /* JADX INFO: Access modifiers changed from: package-private */
        public l(Converter<T, String> converter, boolean z) {
            this.a = converter;
            this.b = z;
        }

        @Override // retrofit2.g
        void a(i iVar, @Nullable T t) throws IOException {
            if (t != null) {
                iVar.b(this.a.convert(t), null, this.b);
            }
        }
    }

    /* compiled from: ParameterHandler.java */
    /* loaded from: classes6.dex */
    static final class k<T> extends g<Map<String, T>> {
        private final Method a;
        private final int b;
        private final Converter<T, String> c;
        private final boolean d;

        @Override // retrofit2.g
        /* bridge */ /* synthetic */ void a(i iVar, @Nullable Object obj) throws IOException {
            a(iVar, (Map) ((Map) obj));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public k(Method method, int i, Converter<T, String> converter, boolean z) {
            this.a = method;
            this.b = i;
            this.c = converter;
            this.d = z;
        }

        void a(i iVar, @Nullable Map<String, T> map) throws IOException {
            if (map != null) {
                for (Map.Entry<String, T> entry : map.entrySet()) {
                    String key = entry.getKey();
                    if (key != null) {
                        T value = entry.getValue();
                        if (value != null) {
                            String convert = this.c.convert(value);
                            if (convert != null) {
                                iVar.b(key, convert, this.d);
                            } else {
                                Method method = this.a;
                                int i = this.b;
                                throw m.a(method, i, "Query map value '" + value + "' converted to null by " + this.c.getClass().getName() + " for key '" + key + "'.", new Object[0]);
                            }
                        } else {
                            Method method2 = this.a;
                            int i2 = this.b;
                            throw m.a(method2, i2, "Query map contained null value for key '" + key + "'.", new Object[0]);
                        }
                    } else {
                        throw m.a(this.a, this.b, "Query map contained null key.", new Object[0]);
                    }
                }
                return;
            }
            throw m.a(this.a, this.b, "Query map was null", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler.java */
    /* loaded from: classes6.dex */
    static final class e<T> extends g<Map<String, T>> {
        private final Method a;
        private final int b;
        private final Converter<T, String> c;

        @Override // retrofit2.g
        /* bridge */ /* synthetic */ void a(i iVar, @Nullable Object obj) throws IOException {
            a(iVar, (Map) ((Map) obj));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public e(Method method, int i, Converter<T, String> converter) {
            this.a = method;
            this.b = i;
            this.c = converter;
        }

        void a(i iVar, @Nullable Map<String, T> map) throws IOException {
            if (map != null) {
                for (Map.Entry<String, T> entry : map.entrySet()) {
                    String key = entry.getKey();
                    if (key != null) {
                        T value = entry.getValue();
                        if (value != null) {
                            iVar.a(key, this.c.convert(value));
                        } else {
                            Method method = this.a;
                            int i = this.b;
                            throw m.a(method, i, "Header map contained null value for key '" + key + "'.", new Object[0]);
                        }
                    } else {
                        throw m.a(this.a, this.b, "Header map contained null key.", new Object[0]);
                    }
                }
                return;
            }
            throw m.a(this.a, this.b, "Header map was null.", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler.java */
    /* loaded from: classes6.dex */
    static final class f extends g<Headers> {
        private final Method a;
        private final int b;

        /* JADX INFO: Access modifiers changed from: package-private */
        public f(Method method, int i) {
            this.a = method;
            this.b = i;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void a(i iVar, @Nullable Headers headers) {
            if (headers != null) {
                iVar.a(headers);
                return;
            }
            throw m.a(this.a, this.b, "Headers parameter must not be null.", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler.java */
    /* loaded from: classes6.dex */
    static final class b<T> extends g<T> {
        private final String a;
        private final Converter<T, String> b;
        private final boolean c;

        /* JADX INFO: Access modifiers changed from: package-private */
        public b(String str, Converter<T, String> converter, boolean z) {
            this.a = (String) Objects.requireNonNull(str, "name == null");
            this.b = converter;
            this.c = z;
        }

        @Override // retrofit2.g
        void a(i iVar, @Nullable T t) throws IOException {
            String convert;
            if (t != null && (convert = this.b.convert(t)) != null) {
                iVar.c(this.a, convert, this.c);
            }
        }
    }

    /* compiled from: ParameterHandler.java */
    /* loaded from: classes6.dex */
    static final class c<T> extends g<Map<String, T>> {
        private final Method a;
        private final int b;
        private final Converter<T, String> c;
        private final boolean d;

        @Override // retrofit2.g
        /* bridge */ /* synthetic */ void a(i iVar, @Nullable Object obj) throws IOException {
            a(iVar, (Map) ((Map) obj));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public c(Method method, int i, Converter<T, String> converter, boolean z) {
            this.a = method;
            this.b = i;
            this.c = converter;
            this.d = z;
        }

        void a(i iVar, @Nullable Map<String, T> map) throws IOException {
            if (map != null) {
                for (Map.Entry<String, T> entry : map.entrySet()) {
                    String key = entry.getKey();
                    if (key != null) {
                        T value = entry.getValue();
                        if (value != null) {
                            String convert = this.c.convert(value);
                            if (convert != null) {
                                iVar.c(key, convert, this.d);
                            } else {
                                Method method = this.a;
                                int i = this.b;
                                throw m.a(method, i, "Field map value '" + value + "' converted to null by " + this.c.getClass().getName() + " for key '" + key + "'.", new Object[0]);
                            }
                        } else {
                            Method method2 = this.a;
                            int i2 = this.b;
                            throw m.a(method2, i2, "Field map contained null value for key '" + key + "'.", new Object[0]);
                        }
                    } else {
                        throw m.a(this.a, this.b, "Field map contained null key.", new Object[0]);
                    }
                }
                return;
            }
            throw m.a(this.a, this.b, "Field map was null.", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler.java */
    /* renamed from: retrofit2.g$g  reason: collision with other inner class name */
    /* loaded from: classes6.dex */
    static final class C0394g<T> extends g<T> {
        private final Method a;
        private final int b;
        private final Headers c;
        private final Converter<T, RequestBody> d;

        /* JADX INFO: Access modifiers changed from: package-private */
        public C0394g(Method method, int i, Headers headers, Converter<T, RequestBody> converter) {
            this.a = method;
            this.b = i;
            this.c = headers;
            this.d = converter;
        }

        @Override // retrofit2.g
        void a(i iVar, @Nullable T t) {
            if (t != null) {
                try {
                    iVar.a(this.c, this.d.convert(t));
                } catch (IOException e) {
                    Method method = this.a;
                    int i = this.b;
                    throw m.a(method, i, "Unable to convert " + t + " to RequestBody", e);
                }
            }
        }
    }

    /* compiled from: ParameterHandler.java */
    /* loaded from: classes6.dex */
    static final class m extends g<MultipartBody.Part> {
        static final m a = new m();

        private m() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void a(i iVar, @Nullable MultipartBody.Part part) {
            if (part != null) {
                iVar.a(part);
            }
        }
    }

    /* compiled from: ParameterHandler.java */
    /* loaded from: classes6.dex */
    static final class h<T> extends g<Map<String, T>> {
        private final Method a;
        private final int b;
        private final Converter<T, RequestBody> c;
        private final String d;

        @Override // retrofit2.g
        /* bridge */ /* synthetic */ void a(i iVar, @Nullable Object obj) throws IOException {
            a(iVar, (Map) ((Map) obj));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public h(Method method, int i, Converter<T, RequestBody> converter, String str) {
            this.a = method;
            this.b = i;
            this.c = converter;
            this.d = str;
        }

        void a(i iVar, @Nullable Map<String, T> map) throws IOException {
            if (map != null) {
                for (Map.Entry<String, T> entry : map.entrySet()) {
                    String key = entry.getKey();
                    if (key != null) {
                        T value = entry.getValue();
                        if (value != null) {
                            iVar.a(Headers.of(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"" + key + "\"", HttpHeaders.Names.CONTENT_TRANSFER_ENCODING, this.d), this.c.convert(value));
                        } else {
                            Method method = this.a;
                            int i = this.b;
                            throw m.a(method, i, "Part map contained null value for key '" + key + "'.", new Object[0]);
                        }
                    } else {
                        throw m.a(this.a, this.b, "Part map contained null key.", new Object[0]);
                    }
                }
                return;
            }
            throw m.a(this.a, this.b, "Part map was null.", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler.java */
    /* loaded from: classes6.dex */
    static final class a<T> extends g<T> {
        private final Method a;
        private final int b;
        private final Converter<T, RequestBody> c;

        /* JADX INFO: Access modifiers changed from: package-private */
        public a(Method method, int i, Converter<T, RequestBody> converter) {
            this.a = method;
            this.b = i;
            this.c = converter;
        }

        @Override // retrofit2.g
        void a(i iVar, @Nullable T t) {
            if (t != null) {
                try {
                    iVar.a(this.c.convert(t));
                } catch (IOException e) {
                    Method method = this.a;
                    int i = this.b;
                    throw m.a(method, e, i, "Unable to convert " + t + " to RequestBody", new Object[0]);
                }
            } else {
                throw m.a(this.a, this.b, "Body parameter value must not be null.", new Object[0]);
            }
        }
    }

    /* compiled from: ParameterHandler.java */
    /* loaded from: classes6.dex */
    static final class o<T> extends g<T> {
        final Class<T> a;

        /* JADX INFO: Access modifiers changed from: package-private */
        public o(Class<T> cls) {
            this.a = cls;
        }

        @Override // retrofit2.g
        void a(i iVar, @Nullable T t) {
            iVar.a((Class<Class<T>>) this.a, (Class<T>) t);
        }
    }
}
