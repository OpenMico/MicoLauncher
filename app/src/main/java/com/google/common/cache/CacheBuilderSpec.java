package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.cache.a;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.netty.util.internal.StringUtil;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
/* loaded from: classes2.dex */
public final class CacheBuilderSpec {
    private static final Splitter n = Splitter.on((char) StringUtil.COMMA).trimResults();
    private static final Splitter o = Splitter.on('=').trimResults();
    private static final ImmutableMap<String, l> p = ImmutableMap.builder().put("initialCapacity", new d()).put("maximumSize", new h()).put("maximumWeight", new i()).put("concurrencyLevel", new b()).put("weakKeys", new f(a.r.WEAK)).put("softValues", new m(a.r.SOFT)).put("weakValues", new m(a.r.WEAK)).put("recordStats", new j()).put("expireAfterAccess", new a()).put("expireAfterWrite", new n()).put("refreshAfterWrite", new k()).put("refreshInterval", new k()).build();
    @VisibleForTesting
    @MonotonicNonNullDecl
    Integer a;
    @VisibleForTesting
    @MonotonicNonNullDecl
    Long b;
    @VisibleForTesting
    @MonotonicNonNullDecl
    Long c;
    @VisibleForTesting
    @MonotonicNonNullDecl
    Integer d;
    @VisibleForTesting
    @MonotonicNonNullDecl
    a.r e;
    @VisibleForTesting
    @MonotonicNonNullDecl
    a.r f;
    @VisibleForTesting
    @MonotonicNonNullDecl
    Boolean g;
    @VisibleForTesting
    long h;
    @VisibleForTesting
    @MonotonicNonNullDecl
    TimeUnit i;
    @VisibleForTesting
    long j;
    @VisibleForTesting
    @MonotonicNonNullDecl
    TimeUnit k;
    @VisibleForTesting
    long l;
    @VisibleForTesting
    @MonotonicNonNullDecl
    TimeUnit m;
    private final String q;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public interface l {
        void a(CacheBuilderSpec cacheBuilderSpec, String str, @NullableDecl String str2);
    }

    private CacheBuilderSpec(String str) {
        this.q = str;
    }

    public static CacheBuilderSpec parse(String str) {
        CacheBuilderSpec cacheBuilderSpec = new CacheBuilderSpec(str);
        if (!str.isEmpty()) {
            for (String str2 : n.split(str)) {
                ImmutableList copyOf = ImmutableList.copyOf(o.split(str2));
                Preconditions.checkArgument(!copyOf.isEmpty(), "blank key-value pair");
                boolean z = false;
                Preconditions.checkArgument(copyOf.size() <= 2, "key-value pair %s with more than one equals sign", str2);
                String str3 = (String) copyOf.get(0);
                l lVar = p.get(str3);
                if (lVar != null) {
                    z = true;
                }
                Preconditions.checkArgument(z, "unknown key %s", str3);
                lVar.a(cacheBuilderSpec, str3, copyOf.size() == 1 ? null : (String) copyOf.get(1));
            }
        }
        return cacheBuilderSpec;
    }

    public static CacheBuilderSpec disableCaching() {
        return parse("maximumSize=0");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CacheBuilder<Object, Object> a() {
        CacheBuilder<Object, Object> newBuilder = CacheBuilder.newBuilder();
        Integer num = this.a;
        if (num != null) {
            newBuilder.initialCapacity(num.intValue());
        }
        Long l2 = this.b;
        if (l2 != null) {
            newBuilder.maximumSize(l2.longValue());
        }
        Long l3 = this.c;
        if (l3 != null) {
            newBuilder.maximumWeight(l3.longValue());
        }
        Integer num2 = this.d;
        if (num2 != null) {
            newBuilder.concurrencyLevel(num2.intValue());
        }
        if (this.e != null) {
            if (AnonymousClass1.a[this.e.ordinal()] == 1) {
                newBuilder.weakKeys();
            } else {
                throw new AssertionError();
            }
        }
        if (this.f != null) {
            switch (this.f) {
                case a.r.WEAK:
                    newBuilder.weakValues();
                    break;
                case a.r.SOFT:
                    newBuilder.softValues();
                    break;
                default:
                    throw new AssertionError();
            }
        }
        Boolean bool = this.g;
        if (bool != null && bool.booleanValue()) {
            newBuilder.recordStats();
        }
        TimeUnit timeUnit = this.i;
        if (timeUnit != null) {
            newBuilder.expireAfterWrite(this.h, timeUnit);
        }
        TimeUnit timeUnit2 = this.k;
        if (timeUnit2 != null) {
            newBuilder.expireAfterAccess(this.j, timeUnit2);
        }
        TimeUnit timeUnit3 = this.m;
        if (timeUnit3 != null) {
            newBuilder.refreshAfterWrite(this.l, timeUnit3);
        }
        return newBuilder;
    }

    public String toParsableString() {
        return this.q;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(toParsableString()).toString();
    }

    public int hashCode() {
        return Objects.hashCode(this.a, this.b, this.c, this.d, this.e, this.f, this.g, a(this.h, this.i), a(this.j, this.k), a(this.l, this.m));
    }

    public boolean equals(@NullableDecl Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CacheBuilderSpec)) {
            return false;
        }
        CacheBuilderSpec cacheBuilderSpec = (CacheBuilderSpec) obj;
        return Objects.equal(this.a, cacheBuilderSpec.a) && Objects.equal(this.b, cacheBuilderSpec.b) && Objects.equal(this.c, cacheBuilderSpec.c) && Objects.equal(this.d, cacheBuilderSpec.d) && Objects.equal(this.e, cacheBuilderSpec.e) && Objects.equal(this.f, cacheBuilderSpec.f) && Objects.equal(this.g, cacheBuilderSpec.g) && Objects.equal(a(this.h, this.i), a(cacheBuilderSpec.h, cacheBuilderSpec.i)) && Objects.equal(a(this.j, this.k), a(cacheBuilderSpec.j, cacheBuilderSpec.k)) && Objects.equal(a(this.l, this.m), a(cacheBuilderSpec.l, cacheBuilderSpec.m));
    }

    @NullableDecl
    private static Long a(long j2, @NullableDecl TimeUnit timeUnit) {
        if (timeUnit == null) {
            return null;
        }
        return Long.valueOf(timeUnit.toNanos(j2));
    }

    /* loaded from: classes2.dex */
    static abstract class e implements l {
        protected abstract void a(CacheBuilderSpec cacheBuilderSpec, int i);

        e() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.l
        public void a(CacheBuilderSpec cacheBuilderSpec, String str, String str2) {
            Preconditions.checkArgument(str2 != null && !str2.isEmpty(), "value of key %s omitted", str);
            try {
                a(cacheBuilderSpec, Integer.parseInt(str2));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(CacheBuilderSpec.b("key %s value set to %s, must be integer", str, str2), e);
            }
        }
    }

    /* loaded from: classes2.dex */
    static abstract class g implements l {
        protected abstract void a(CacheBuilderSpec cacheBuilderSpec, long j);

        g() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.l
        public void a(CacheBuilderSpec cacheBuilderSpec, String str, String str2) {
            Preconditions.checkArgument(str2 != null && !str2.isEmpty(), "value of key %s omitted", str);
            try {
                a(cacheBuilderSpec, Long.parseLong(str2));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(CacheBuilderSpec.b("key %s value set to %s, must be integer", str, str2), e);
            }
        }
    }

    /* loaded from: classes2.dex */
    static class d extends e {
        d() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.e
        protected void a(CacheBuilderSpec cacheBuilderSpec, int i) {
            Preconditions.checkArgument(cacheBuilderSpec.a == null, "initial capacity was already set to ", cacheBuilderSpec.a);
            cacheBuilderSpec.a = Integer.valueOf(i);
        }
    }

    /* loaded from: classes2.dex */
    static class h extends g {
        h() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.g
        protected void a(CacheBuilderSpec cacheBuilderSpec, long j) {
            boolean z = true;
            Preconditions.checkArgument(cacheBuilderSpec.b == null, "maximum size was already set to ", cacheBuilderSpec.b);
            if (cacheBuilderSpec.c != null) {
                z = false;
            }
            Preconditions.checkArgument(z, "maximum weight was already set to ", cacheBuilderSpec.c);
            cacheBuilderSpec.b = Long.valueOf(j);
        }
    }

    /* loaded from: classes2.dex */
    static class i extends g {
        i() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.g
        protected void a(CacheBuilderSpec cacheBuilderSpec, long j) {
            boolean z = true;
            Preconditions.checkArgument(cacheBuilderSpec.c == null, "maximum weight was already set to ", cacheBuilderSpec.c);
            if (cacheBuilderSpec.b != null) {
                z = false;
            }
            Preconditions.checkArgument(z, "maximum size was already set to ", cacheBuilderSpec.b);
            cacheBuilderSpec.c = Long.valueOf(j);
        }
    }

    /* loaded from: classes2.dex */
    static class b extends e {
        b() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.e
        protected void a(CacheBuilderSpec cacheBuilderSpec, int i) {
            Preconditions.checkArgument(cacheBuilderSpec.d == null, "concurrency level was already set to ", cacheBuilderSpec.d);
            cacheBuilderSpec.d = Integer.valueOf(i);
        }
    }

    /* loaded from: classes2.dex */
    static class f implements l {
        private final a.r a;

        public f(a.r rVar) {
            this.a = rVar;
        }

        @Override // com.google.common.cache.CacheBuilderSpec.l
        public void a(CacheBuilderSpec cacheBuilderSpec, String str, @NullableDecl String str2) {
            boolean z = true;
            Preconditions.checkArgument(str2 == null, "key %s does not take values", str);
            if (cacheBuilderSpec.e != null) {
                z = false;
            }
            Preconditions.checkArgument(z, "%s was already set to %s", str, cacheBuilderSpec.e);
            cacheBuilderSpec.e = this.a;
        }
    }

    /* loaded from: classes2.dex */
    static class m implements l {
        private final a.r a;

        public m(a.r rVar) {
            this.a = rVar;
        }

        @Override // com.google.common.cache.CacheBuilderSpec.l
        public void a(CacheBuilderSpec cacheBuilderSpec, String str, @NullableDecl String str2) {
            boolean z = true;
            Preconditions.checkArgument(str2 == null, "key %s does not take values", str);
            if (cacheBuilderSpec.f != null) {
                z = false;
            }
            Preconditions.checkArgument(z, "%s was already set to %s", str, cacheBuilderSpec.f);
            cacheBuilderSpec.f = this.a;
        }
    }

    /* loaded from: classes2.dex */
    static class j implements l {
        j() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.l
        public void a(CacheBuilderSpec cacheBuilderSpec, String str, @NullableDecl String str2) {
            boolean z = false;
            Preconditions.checkArgument(str2 == null, "recordStats does not take values");
            if (cacheBuilderSpec.g == null) {
                z = true;
            }
            Preconditions.checkArgument(z, "recordStats already set");
            cacheBuilderSpec.g = true;
        }
    }

    /* loaded from: classes2.dex */
    static abstract class c implements l {
        protected abstract void a(CacheBuilderSpec cacheBuilderSpec, long j, TimeUnit timeUnit);

        c() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.l
        public void a(CacheBuilderSpec cacheBuilderSpec, String str, String str2) {
            TimeUnit timeUnit;
            Preconditions.checkArgument(str2 != null && !str2.isEmpty(), "value of key %s omitted", str);
            try {
                char charAt = str2.charAt(str2.length() - 1);
                if (charAt == 'd') {
                    timeUnit = TimeUnit.DAYS;
                } else if (charAt == 'h') {
                    timeUnit = TimeUnit.HOURS;
                } else if (charAt == 'm') {
                    timeUnit = TimeUnit.MINUTES;
                } else if (charAt == 's') {
                    timeUnit = TimeUnit.SECONDS;
                } else {
                    throw new IllegalArgumentException(CacheBuilderSpec.b("key %s invalid format.  was %s, must end with one of [dDhHmMsS]", str, str2));
                }
                a(cacheBuilderSpec, Long.parseLong(str2.substring(0, str2.length() - 1)), timeUnit);
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException(CacheBuilderSpec.b("key %s value set to %s, must be integer", str, str2));
            }
        }
    }

    /* loaded from: classes2.dex */
    static class a extends c {
        a() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.c
        protected void a(CacheBuilderSpec cacheBuilderSpec, long j, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.k == null, "expireAfterAccess already set");
            cacheBuilderSpec.j = j;
            cacheBuilderSpec.k = timeUnit;
        }
    }

    /* loaded from: classes2.dex */
    static class n extends c {
        n() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.c
        protected void a(CacheBuilderSpec cacheBuilderSpec, long j, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.i == null, "expireAfterWrite already set");
            cacheBuilderSpec.h = j;
            cacheBuilderSpec.i = timeUnit;
        }
    }

    /* loaded from: classes2.dex */
    static class k extends c {
        k() {
        }

        @Override // com.google.common.cache.CacheBuilderSpec.c
        protected void a(CacheBuilderSpec cacheBuilderSpec, long j, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.m == null, "refreshAfterWrite already set");
            cacheBuilderSpec.l = j;
            cacheBuilderSpec.m = timeUnit;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(String str, Object... objArr) {
        return String.format(Locale.ROOT, str, objArr);
    }
}
