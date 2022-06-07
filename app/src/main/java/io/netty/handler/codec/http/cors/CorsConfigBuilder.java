package io.netty.handler.codec.http.cors;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class CorsConfigBuilder {
    final Set<String> a;
    final boolean b;
    boolean c;
    boolean d;
    boolean e;
    final Set<String> f;
    long g;
    final Set<HttpMethod> h;
    final Set<String> i;
    final Map<CharSequence, Callable<?>> j;
    boolean k;
    private boolean l;

    public static CorsConfigBuilder forAnyOrigin() {
        return new CorsConfigBuilder();
    }

    public static CorsConfigBuilder forOrigin(String str) {
        if ("*".equals(str)) {
            return new CorsConfigBuilder();
        }
        return new CorsConfigBuilder(str);
    }

    public static CorsConfigBuilder forOrigins(String... strArr) {
        return new CorsConfigBuilder(strArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CorsConfigBuilder(String... strArr) {
        this.d = true;
        this.f = new HashSet();
        this.h = new HashSet();
        this.i = new HashSet();
        this.j = new HashMap();
        this.a = new LinkedHashSet(Arrays.asList(strArr));
        this.b = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CorsConfigBuilder() {
        this.d = true;
        this.f = new HashSet();
        this.h = new HashSet();
        this.i = new HashSet();
        this.j = new HashMap();
        this.b = true;
        this.a = Collections.emptySet();
    }

    public CorsConfigBuilder allowNullOrigin() {
        this.c = true;
        return this;
    }

    public CorsConfigBuilder disable() {
        this.d = false;
        return this;
    }

    public CorsConfigBuilder exposeHeaders(String... strArr) {
        this.f.addAll(Arrays.asList(strArr));
        return this;
    }

    public CorsConfigBuilder allowCredentials() {
        this.e = true;
        return this;
    }

    public CorsConfigBuilder maxAge(long j) {
        this.g = j;
        return this;
    }

    public CorsConfigBuilder allowedRequestMethods(HttpMethod... httpMethodArr) {
        this.h.addAll(Arrays.asList(httpMethodArr));
        return this;
    }

    public CorsConfigBuilder allowedRequestHeaders(String... strArr) {
        this.i.addAll(Arrays.asList(strArr));
        return this;
    }

    public CorsConfigBuilder preflightResponseHeader(CharSequence charSequence, Object... objArr) {
        if (objArr.length == 1) {
            this.j.put(charSequence, new a(objArr[0]));
        } else {
            preflightResponseHeader(charSequence, Arrays.asList(objArr));
        }
        return this;
    }

    public <T> CorsConfigBuilder preflightResponseHeader(CharSequence charSequence, Iterable<T> iterable) {
        this.j.put(charSequence, new a(iterable));
        return this;
    }

    public <T> CorsConfigBuilder preflightResponseHeader(CharSequence charSequence, Callable<T> callable) {
        this.j.put(charSequence, callable);
        return this;
    }

    public CorsConfigBuilder noPreflightResponseHeaders() {
        this.l = true;
        return this;
    }

    public CorsConfigBuilder shortCircuit() {
        this.k = true;
        return this;
    }

    public CorsConfig build() {
        if (this.j.isEmpty() && !this.l) {
            this.j.put(HttpHeaderNames.DATE, b.a);
            this.j.put(HttpHeaderNames.CONTENT_LENGTH, new a("0"));
        }
        return new CorsConfig(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static final class a implements Callable<Object> {
        private final Object a;

        private a(Object obj) {
            if (obj != null) {
                this.a = obj;
                return;
            }
            throw new IllegalArgumentException("value must not be null");
        }

        @Override // java.util.concurrent.Callable
        public Object call() {
            return this.a;
        }
    }

    /* loaded from: classes4.dex */
    private static final class b implements Callable<Date> {
        static final b a = new b();

        private b() {
        }

        /* renamed from: a */
        public Date call() throws Exception {
            return new Date();
        }
    }
}
