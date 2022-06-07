package io.netty.handler.codec.http.cors;

import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.internal.StringUtil;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
public final class CorsConfig {
    private final Set<String> a;
    private final boolean b;
    private final boolean c;
    private final Set<String> d;
    private final boolean e;
    private final long f;
    private final Set<HttpMethod> g;
    private final Set<String> h;
    private final boolean i;
    private final Map<CharSequence, Callable<?>> j;
    private final boolean k;

    public CorsConfig(CorsConfigBuilder corsConfigBuilder) {
        this.a = new LinkedHashSet(corsConfigBuilder.a);
        this.b = corsConfigBuilder.b;
        this.c = corsConfigBuilder.d;
        this.d = corsConfigBuilder.f;
        this.e = corsConfigBuilder.e;
        this.f = corsConfigBuilder.g;
        this.g = corsConfigBuilder.h;
        this.h = corsConfigBuilder.i;
        this.i = corsConfigBuilder.c;
        this.j = corsConfigBuilder.j;
        this.k = corsConfigBuilder.k;
    }

    public boolean isCorsSupportEnabled() {
        return this.c;
    }

    public boolean isAnyOriginSupported() {
        return this.b;
    }

    public String origin() {
        return this.a.isEmpty() ? "*" : this.a.iterator().next();
    }

    public Set<String> origins() {
        return this.a;
    }

    public boolean isNullOriginAllowed() {
        return this.i;
    }

    public Set<String> exposedHeaders() {
        return Collections.unmodifiableSet(this.d);
    }

    public boolean isCredentialsAllowed() {
        return this.e;
    }

    public long maxAge() {
        return this.f;
    }

    public Set<HttpMethod> allowedRequestMethods() {
        return Collections.unmodifiableSet(this.g);
    }

    public Set<String> allowedRequestHeaders() {
        return Collections.unmodifiableSet(this.h);
    }

    public HttpHeaders preflightResponseHeaders() {
        if (this.j.isEmpty()) {
            return EmptyHttpHeaders.INSTANCE;
        }
        DefaultHttpHeaders defaultHttpHeaders = new DefaultHttpHeaders();
        for (Map.Entry<CharSequence, Callable<?>> entry : this.j.entrySet()) {
            Object a = a(entry.getValue());
            if (a instanceof Iterable) {
                defaultHttpHeaders.add(entry.getKey(), (Iterable) a);
            } else {
                defaultHttpHeaders.add(entry.getKey(), a);
            }
        }
        return defaultHttpHeaders;
    }

    public boolean isShortCircuit() {
        return this.k;
    }

    @Deprecated
    public boolean isShortCurcuit() {
        return isShortCircuit();
    }

    private static <T> T a(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new IllegalStateException("Could not generate value for callable [" + callable + ']', e);
        }
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "[enabled=" + this.c + ", origins=" + this.a + ", anyOrigin=" + this.b + ", exposedHeaders=" + this.d + ", isCredentialsAllowed=" + this.e + ", maxAge=" + this.f + ", allowedRequestMethods=" + this.g + ", allowedRequestHeaders=" + this.h + ", preflightHeaders=" + this.j + ']';
    }

    @Deprecated
    public static Builder withAnyOrigin() {
        return new Builder();
    }

    @Deprecated
    public static Builder withOrigin(String str) {
        if ("*".equals(str)) {
            return new Builder();
        }
        return new Builder(str);
    }

    @Deprecated
    public static Builder withOrigins(String... strArr) {
        return new Builder(strArr);
    }

    @Deprecated
    /* loaded from: classes4.dex */
    public static class Builder {
        private final CorsConfigBuilder a;

        @Deprecated
        public Builder(String... strArr) {
            this.a = new CorsConfigBuilder(strArr);
        }

        @Deprecated
        public Builder() {
            this.a = new CorsConfigBuilder();
        }

        @Deprecated
        public Builder allowNullOrigin() {
            this.a.allowNullOrigin();
            return this;
        }

        @Deprecated
        public Builder disable() {
            this.a.disable();
            return this;
        }

        @Deprecated
        public Builder exposeHeaders(String... strArr) {
            this.a.exposeHeaders(strArr);
            return this;
        }

        @Deprecated
        public Builder allowCredentials() {
            this.a.allowCredentials();
            return this;
        }

        @Deprecated
        public Builder maxAge(long j) {
            this.a.maxAge(j);
            return this;
        }

        @Deprecated
        public Builder allowedRequestMethods(HttpMethod... httpMethodArr) {
            this.a.allowedRequestMethods(httpMethodArr);
            return this;
        }

        @Deprecated
        public Builder allowedRequestHeaders(String... strArr) {
            this.a.allowedRequestHeaders(strArr);
            return this;
        }

        @Deprecated
        public Builder preflightResponseHeader(CharSequence charSequence, Object... objArr) {
            this.a.preflightResponseHeader(charSequence, objArr);
            return this;
        }

        @Deprecated
        public <T> Builder preflightResponseHeader(CharSequence charSequence, Iterable<T> iterable) {
            this.a.preflightResponseHeader(charSequence, iterable);
            return this;
        }

        @Deprecated
        public <T> Builder preflightResponseHeader(String str, Callable<T> callable) {
            this.a.preflightResponseHeader(str, callable);
            return this;
        }

        @Deprecated
        public Builder noPreflightResponseHeaders() {
            this.a.noPreflightResponseHeaders();
            return this;
        }

        @Deprecated
        public CorsConfig build() {
            return this.a.build();
        }

        @Deprecated
        public Builder shortCurcuit() {
            this.a.shortCircuit();
            return this;
        }
    }

    @Deprecated
    /* loaded from: classes4.dex */
    public static final class DateValueGenerator implements Callable<Date> {
        @Override // java.util.concurrent.Callable
        public Date call() throws Exception {
            return new Date();
        }
    }
}
