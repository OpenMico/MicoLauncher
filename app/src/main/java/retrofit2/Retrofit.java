package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.a;

/* loaded from: classes6.dex */
public final class Retrofit {
    final Call.Factory a;
    final HttpUrl b;
    final List<Converter.Factory> c;
    final List<CallAdapter.Factory> d;
    @Nullable
    final Executor e;
    final boolean f;
    private final Map<Method, k<?>> g = new ConcurrentHashMap();

    Retrofit(Call.Factory factory, HttpUrl httpUrl, List<Converter.Factory> list, List<CallAdapter.Factory> list2, @Nullable Executor executor, boolean z) {
        this.a = factory;
        this.b = httpUrl;
        this.c = list;
        this.d = list2;
        this.e = executor;
        this.f = z;
    }

    public <T> T create(final Class<T> cls) {
        a((Class<?>) cls);
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new InvocationHandler() { // from class: retrofit2.Retrofit.1
            private final h c = h.a();
            private final Object[] d = new Object[0];

            @Override // java.lang.reflect.InvocationHandler
            @Nullable
            public Object invoke(Object obj, Method method, @Nullable Object[] objArr) throws Throwable {
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, objArr);
                }
                if (objArr == null) {
                    objArr = this.d;
                }
                if (this.c.a(method)) {
                    return this.c.a(method, cls, obj, objArr);
                }
                return Retrofit.this.a(method).a(objArr);
            }
        });
    }

    private void a(Class<?> cls) {
        if (cls.isInterface()) {
            ArrayDeque arrayDeque = new ArrayDeque(1);
            arrayDeque.add(cls);
            while (!arrayDeque.isEmpty()) {
                Class<?> cls2 = (Class) arrayDeque.removeFirst();
                if (cls2.getTypeParameters().length != 0) {
                    StringBuilder sb = new StringBuilder("Type parameters are unsupported on ");
                    sb.append(cls2.getName());
                    if (cls2 != cls) {
                        sb.append(" which is an interface of ");
                        sb.append(cls.getName());
                    }
                    throw new IllegalArgumentException(sb.toString());
                }
                Collections.addAll(arrayDeque, cls2.getInterfaces());
            }
            if (this.f) {
                h a = h.a();
                Method[] declaredMethods = cls.getDeclaredMethods();
                for (Method method : declaredMethods) {
                    if (!a.a(method) && !Modifier.isStatic(method.getModifiers())) {
                        a(method);
                    }
                }
                return;
            }
            return;
        }
        throw new IllegalArgumentException("API declarations must be interfaces.");
    }

    k<?> a(Method method) {
        k<?> kVar;
        k<?> kVar2 = this.g.get(method);
        if (kVar2 != null) {
            return kVar2;
        }
        synchronized (this.g) {
            kVar = this.g.get(method);
            if (kVar == null) {
                kVar = k.a(this, method);
                this.g.put(method, kVar);
            }
        }
        return kVar;
    }

    public Call.Factory callFactory() {
        return this.a;
    }

    public HttpUrl baseUrl() {
        return this.b;
    }

    public List<CallAdapter.Factory> callAdapterFactories() {
        return this.d;
    }

    public CallAdapter<?, ?> callAdapter(Type type, Annotation[] annotationArr) {
        return nextCallAdapter(null, type, annotationArr);
    }

    public CallAdapter<?, ?> nextCallAdapter(@Nullable CallAdapter.Factory factory, Type type, Annotation[] annotationArr) {
        Objects.requireNonNull(type, "returnType == null");
        Objects.requireNonNull(annotationArr, "annotations == null");
        int indexOf = this.d.indexOf(factory) + 1;
        int size = this.d.size();
        for (int i = indexOf; i < size; i++) {
            CallAdapter<?, ?> callAdapter = this.d.get(i).get(type, annotationArr, this);
            if (callAdapter != null) {
                return callAdapter;
            }
        }
        StringBuilder sb = new StringBuilder("Could not locate call adapter for ");
        sb.append(type);
        sb.append(".\n");
        if (factory != null) {
            sb.append("  Skipped:");
            for (int i2 = 0; i2 < indexOf; i2++) {
                sb.append("\n   * ");
                sb.append(this.d.get(i2).getClass().getName());
            }
            sb.append('\n');
        }
        sb.append("  Tried:");
        int size2 = this.d.size();
        while (indexOf < size2) {
            sb.append("\n   * ");
            sb.append(this.d.get(indexOf).getClass().getName());
            indexOf++;
        }
        throw new IllegalArgumentException(sb.toString());
    }

    public List<Converter.Factory> converterFactories() {
        return this.c;
    }

    public <T> Converter<T, RequestBody> requestBodyConverter(Type type, Annotation[] annotationArr, Annotation[] annotationArr2) {
        return nextRequestBodyConverter(null, type, annotationArr, annotationArr2);
    }

    public <T> Converter<T, RequestBody> nextRequestBodyConverter(@Nullable Converter.Factory factory, Type type, Annotation[] annotationArr, Annotation[] annotationArr2) {
        Objects.requireNonNull(type, "type == null");
        Objects.requireNonNull(annotationArr, "parameterAnnotations == null");
        Objects.requireNonNull(annotationArr2, "methodAnnotations == null");
        int indexOf = this.c.indexOf(factory) + 1;
        int size = this.c.size();
        for (int i = indexOf; i < size; i++) {
            Converter<T, RequestBody> converter = (Converter<T, RequestBody>) this.c.get(i).requestBodyConverter(type, annotationArr, annotationArr2, this);
            if (converter != null) {
                return converter;
            }
        }
        StringBuilder sb = new StringBuilder("Could not locate RequestBody converter for ");
        sb.append(type);
        sb.append(".\n");
        if (factory != null) {
            sb.append("  Skipped:");
            for (int i2 = 0; i2 < indexOf; i2++) {
                sb.append("\n   * ");
                sb.append(this.c.get(i2).getClass().getName());
            }
            sb.append('\n');
        }
        sb.append("  Tried:");
        int size2 = this.c.size();
        while (indexOf < size2) {
            sb.append("\n   * ");
            sb.append(this.c.get(indexOf).getClass().getName());
            indexOf++;
        }
        throw new IllegalArgumentException(sb.toString());
    }

    public <T> Converter<ResponseBody, T> responseBodyConverter(Type type, Annotation[] annotationArr) {
        return nextResponseBodyConverter(null, type, annotationArr);
    }

    public <T> Converter<ResponseBody, T> nextResponseBodyConverter(@Nullable Converter.Factory factory, Type type, Annotation[] annotationArr) {
        Objects.requireNonNull(type, "type == null");
        Objects.requireNonNull(annotationArr, "annotations == null");
        int indexOf = this.c.indexOf(factory) + 1;
        int size = this.c.size();
        for (int i = indexOf; i < size; i++) {
            Converter<ResponseBody, T> converter = (Converter<ResponseBody, T>) this.c.get(i).responseBodyConverter(type, annotationArr, this);
            if (converter != null) {
                return converter;
            }
        }
        StringBuilder sb = new StringBuilder("Could not locate ResponseBody converter for ");
        sb.append(type);
        sb.append(".\n");
        if (factory != null) {
            sb.append("  Skipped:");
            for (int i2 = 0; i2 < indexOf; i2++) {
                sb.append("\n   * ");
                sb.append(this.c.get(i2).getClass().getName());
            }
            sb.append('\n');
        }
        sb.append("  Tried:");
        int size2 = this.c.size();
        while (indexOf < size2) {
            sb.append("\n   * ");
            sb.append(this.c.get(indexOf).getClass().getName());
            indexOf++;
        }
        throw new IllegalArgumentException(sb.toString());
    }

    public <T> Converter<T, String> stringConverter(Type type, Annotation[] annotationArr) {
        Objects.requireNonNull(type, "type == null");
        Objects.requireNonNull(annotationArr, "annotations == null");
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            Converter<T, String> converter = (Converter<T, String>) this.c.get(i).stringConverter(type, annotationArr, this);
            if (converter != null) {
                return converter;
            }
        }
        return a.d.a;
    }

    @Nullable
    public Executor callbackExecutor() {
        return this.e;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    /* loaded from: classes6.dex */
    public static final class Builder {
        private final h a;
        @Nullable
        private Call.Factory b;
        @Nullable
        private HttpUrl c;
        private final List<Converter.Factory> d;
        private final List<CallAdapter.Factory> e;
        @Nullable
        private Executor f;
        private boolean g;

        Builder(h hVar) {
            this.d = new ArrayList();
            this.e = new ArrayList();
            this.a = hVar;
        }

        public Builder() {
            this(h.a());
        }

        Builder(Retrofit retrofit) {
            this.d = new ArrayList();
            this.e = new ArrayList();
            this.a = h.a();
            this.b = retrofit.a;
            this.c = retrofit.b;
            int size = retrofit.c.size() - this.a.e();
            for (int i = 1; i < size; i++) {
                this.d.add(retrofit.c.get(i));
            }
            int size2 = retrofit.d.size() - this.a.c();
            for (int i2 = 0; i2 < size2; i2++) {
                this.e.add(retrofit.d.get(i2));
            }
            this.f = retrofit.e;
            this.g = retrofit.f;
        }

        public Builder client(OkHttpClient okHttpClient) {
            return callFactory((Call.Factory) Objects.requireNonNull(okHttpClient, "client == null"));
        }

        public Builder callFactory(Call.Factory factory) {
            this.b = (Call.Factory) Objects.requireNonNull(factory, "factory == null");
            return this;
        }

        public Builder baseUrl(URL url) {
            Objects.requireNonNull(url, "baseUrl == null");
            return baseUrl(HttpUrl.get(url.toString()));
        }

        public Builder baseUrl(String str) {
            Objects.requireNonNull(str, "baseUrl == null");
            return baseUrl(HttpUrl.get(str));
        }

        public Builder baseUrl(HttpUrl httpUrl) {
            Objects.requireNonNull(httpUrl, "baseUrl == null");
            List<String> pathSegments = httpUrl.pathSegments();
            if ("".equals(pathSegments.get(pathSegments.size() - 1))) {
                this.c = httpUrl;
                return this;
            }
            throw new IllegalArgumentException("baseUrl must end in /: " + httpUrl);
        }

        public Builder addConverterFactory(Converter.Factory factory) {
            this.d.add((Converter.Factory) Objects.requireNonNull(factory, "factory == null"));
            return this;
        }

        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            this.e.add((CallAdapter.Factory) Objects.requireNonNull(factory, "factory == null"));
            return this;
        }

        public Builder callbackExecutor(Executor executor) {
            this.f = (Executor) Objects.requireNonNull(executor, "executor == null");
            return this;
        }

        public List<CallAdapter.Factory> callAdapterFactories() {
            return this.e;
        }

        public List<Converter.Factory> converterFactories() {
            return this.d;
        }

        public Builder validateEagerly(boolean z) {
            this.g = z;
            return this;
        }

        public Retrofit build() {
            if (this.c != null) {
                Call.Factory factory = this.b;
                OkHttpClient okHttpClient = factory == null ? new OkHttpClient() : factory;
                Executor executor = this.f;
                Executor b = executor == null ? this.a.b() : executor;
                ArrayList arrayList = new ArrayList(this.e);
                arrayList.addAll(this.a.a(b));
                ArrayList arrayList2 = new ArrayList(this.d.size() + 1 + this.a.e());
                arrayList2.add(new a());
                arrayList2.addAll(this.d);
                arrayList2.addAll(this.a.d());
                return new Retrofit(okHttpClient, this.c, Collections.unmodifiableList(arrayList2), Collections.unmodifiableList(arrayList), b, this.g);
            }
            throw new IllegalStateException("Base URL required.");
        }
    }
}
