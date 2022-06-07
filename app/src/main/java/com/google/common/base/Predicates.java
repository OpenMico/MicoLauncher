package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import io.netty.util.internal.StringUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes2.dex */
public final class Predicates {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier removed */
    /* loaded from: classes2.dex */
    public static abstract class i extends Enum<i> implements Predicate<Object> {
        <T> Predicate<T> a() {
            return this;
        }
    }

    private Predicates() {
    }

    @GwtCompatible(serializable = true)
    public static <T> Predicate<T> alwaysTrue() {
        return i.ALWAYS_TRUE.a();
    }

    @GwtCompatible(serializable = true)
    public static <T> Predicate<T> alwaysFalse() {
        return i.ALWAYS_FALSE.a();
    }

    @GwtCompatible(serializable = true)
    public static <T> Predicate<T> isNull() {
        return i.IS_NULL.a();
    }

    @GwtCompatible(serializable = true)
    public static <T> Predicate<T> notNull() {
        return i.NOT_NULL.a();
    }

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return new h(predicate);
    }

    public static <T> Predicate<T> and(Iterable<? extends Predicate<? super T>> iterable) {
        return new a(a(iterable));
    }

    @SafeVarargs
    public static <T> Predicate<T> and(Predicate<? super T>... predicateArr) {
        return new a(a(predicateArr));
    }

    public static <T> Predicate<T> and(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        return new a(a((Predicate) Preconditions.checkNotNull(predicate), (Predicate) Preconditions.checkNotNull(predicate2)));
    }

    public static <T> Predicate<T> or(Iterable<? extends Predicate<? super T>> iterable) {
        return new j(a(iterable));
    }

    @SafeVarargs
    public static <T> Predicate<T> or(Predicate<? super T>... predicateArr) {
        return new j(a(predicateArr));
    }

    public static <T> Predicate<T> or(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        return new j(a((Predicate) Preconditions.checkNotNull(predicate), (Predicate) Preconditions.checkNotNull(predicate2)));
    }

    public static <T> Predicate<T> equalTo(@NullableDecl T t) {
        return t == null ? isNull() : new g(t);
    }

    @GwtIncompatible
    public static Predicate<Object> instanceOf(Class<?> cls) {
        return new f(cls);
    }

    @Beta
    @GwtIncompatible
    public static Predicate<Class<?>> subtypeOf(Class<?> cls) {
        return new k(cls);
    }

    public static <T> Predicate<T> in(Collection<? extends T> collection) {
        return new e(collection);
    }

    public static <A, B> Predicate<A> compose(Predicate<B> predicate, Function<A, ? extends B> function) {
        return new b(predicate, function);
    }

    @GwtIncompatible
    public static Predicate<CharSequence> containsPattern(String str) {
        return new c(str);
    }

    @GwtIncompatible("java.util.regex.Pattern")
    public static Predicate<CharSequence> contains(Pattern pattern) {
        return new d(new g(pattern));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class h<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        final Predicate<T> predicate;

        h(Predicate<T> predicate) {
            this.predicate = (Predicate) Preconditions.checkNotNull(predicate);
        }

        @Override // com.google.common.base.Predicate
        public boolean apply(@NullableDecl T t) {
            return !this.predicate.apply(t);
        }

        public int hashCode() {
            return ~this.predicate.hashCode();
        }

        @Override // com.google.common.base.Predicate
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof h) {
                return this.predicate.equals(((h) obj).predicate);
            }
            return false;
        }

        public String toString() {
            return "Predicates.not(" + this.predicate + ")";
        }
    }

    /* loaded from: classes2.dex */
    public static class a<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        private final List<? extends Predicate<? super T>> components;

        private a(List<? extends Predicate<? super T>> list) {
            this.components = list;
        }

        @Override // com.google.common.base.Predicate
        public boolean apply(@NullableDecl T t) {
            for (int i = 0; i < this.components.size(); i++) {
                if (!((Predicate) this.components.get(i)).apply(t)) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            return this.components.hashCode() + 306654252;
        }

        @Override // com.google.common.base.Predicate
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof a) {
                return this.components.equals(((a) obj).components);
            }
            return false;
        }

        public String toString() {
            return Predicates.b("and", this.components);
        }
    }

    /* loaded from: classes2.dex */
    private static class j<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        private final List<? extends Predicate<? super T>> components;

        private j(List<? extends Predicate<? super T>> list) {
            this.components = list;
        }

        @Override // com.google.common.base.Predicate
        public boolean apply(@NullableDecl T t) {
            for (int i = 0; i < this.components.size(); i++) {
                if (((Predicate) this.components.get(i)).apply(t)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return this.components.hashCode() + 87855567;
        }

        @Override // com.google.common.base.Predicate
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof j) {
                return this.components.equals(((j) obj).components);
            }
            return false;
        }

        public String toString() {
            return Predicates.b("or", this.components);
        }
    }

    public static String b(String str, Iterable<?> iterable) {
        StringBuilder sb = new StringBuilder("Predicates.");
        sb.append(str);
        sb.append('(');
        boolean z = true;
        for (Object obj : iterable) {
            if (!z) {
                sb.append(StringUtil.COMMA);
            }
            sb.append(obj);
            z = false;
        }
        sb.append(')');
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class g<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        private final T target;

        private g(T t) {
            this.target = t;
        }

        @Override // com.google.common.base.Predicate
        public boolean apply(T t) {
            return this.target.equals(t);
        }

        public int hashCode() {
            return this.target.hashCode();
        }

        @Override // com.google.common.base.Predicate
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof g) {
                return this.target.equals(((g) obj).target);
            }
            return false;
        }

        public String toString() {
            return "Predicates.equalTo(" + this.target + ")";
        }
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    public static class f implements Predicate<Object>, Serializable {
        private static final long serialVersionUID = 0;
        private final Class<?> clazz;

        private f(Class<?> cls) {
            this.clazz = (Class) Preconditions.checkNotNull(cls);
        }

        @Override // com.google.common.base.Predicate
        public boolean apply(@NullableDecl Object obj) {
            return this.clazz.isInstance(obj);
        }

        public int hashCode() {
            return this.clazz.hashCode();
        }

        @Override // com.google.common.base.Predicate
        public boolean equals(@NullableDecl Object obj) {
            return (obj instanceof f) && this.clazz == ((f) obj).clazz;
        }

        public String toString() {
            return "Predicates.instanceOf(" + this.clazz.getName() + ")";
        }
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    private static class k implements Predicate<Class<?>>, Serializable {
        private static final long serialVersionUID = 0;
        private final Class<?> clazz;

        private k(Class<?> cls) {
            this.clazz = (Class) Preconditions.checkNotNull(cls);
        }

        /* renamed from: a */
        public boolean apply(Class<?> cls) {
            return this.clazz.isAssignableFrom(cls);
        }

        public int hashCode() {
            return this.clazz.hashCode();
        }

        @Override // com.google.common.base.Predicate
        public boolean equals(@NullableDecl Object obj) {
            return (obj instanceof k) && this.clazz == ((k) obj).clazz;
        }

        public String toString() {
            return "Predicates.subtypeOf(" + this.clazz.getName() + ")";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class e<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;
        private final Collection<?> target;

        private e(Collection<?> collection) {
            this.target = (Collection) Preconditions.checkNotNull(collection);
        }

        @Override // com.google.common.base.Predicate
        public boolean apply(@NullableDecl T t) {
            try {
                return this.target.contains(t);
            } catch (ClassCastException | NullPointerException unused) {
                return false;
            }
        }

        @Override // com.google.common.base.Predicate
        public boolean equals(@NullableDecl Object obj) {
            if (obj instanceof e) {
                return this.target.equals(((e) obj).target);
            }
            return false;
        }

        public int hashCode() {
            return this.target.hashCode();
        }

        public String toString() {
            return "Predicates.in(" + this.target + ")";
        }
    }

    /* loaded from: classes2.dex */
    public static class b<A, B> implements Predicate<A>, Serializable {
        private static final long serialVersionUID = 0;
        final Function<A, ? extends B> f;
        final Predicate<B> p;

        private b(Predicate<B> predicate, Function<A, ? extends B> function) {
            this.p = (Predicate) Preconditions.checkNotNull(predicate);
            this.f = (Function) Preconditions.checkNotNull(function);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.base.Predicate
        public boolean apply(@NullableDecl A a) {
            return this.p.apply(this.f.apply(a));
        }

        @Override // com.google.common.base.Predicate
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return this.f.equals(bVar.f) && this.p.equals(bVar.p);
        }

        public int hashCode() {
            return this.f.hashCode() ^ this.p.hashCode();
        }

        public String toString() {
            return this.p + "(" + this.f + ")";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GwtIncompatible
    /* loaded from: classes2.dex */
    public static class d implements Predicate<CharSequence>, Serializable {
        private static final long serialVersionUID = 0;
        final d pattern;

        d(d dVar) {
            this.pattern = (d) Preconditions.checkNotNull(dVar);
        }

        /* renamed from: a */
        public boolean apply(CharSequence charSequence) {
            return this.pattern.a(charSequence).b();
        }

        public int hashCode() {
            return Objects.hashCode(this.pattern.a(), Integer.valueOf(this.pattern.b()));
        }

        @Override // com.google.common.base.Predicate
        public boolean equals(@NullableDecl Object obj) {
            if (!(obj instanceof d)) {
                return false;
            }
            d dVar = (d) obj;
            return Objects.equal(this.pattern.a(), dVar.pattern.a()) && this.pattern.b() == dVar.pattern.b();
        }

        public String toString() {
            String toStringHelper = MoreObjects.toStringHelper(this.pattern).add("pattern", this.pattern.a()).add("pattern.flags", this.pattern.b()).toString();
            return "Predicates.contains(" + toStringHelper + ")";
        }
    }

    @GwtIncompatible
    /* loaded from: classes2.dex */
    private static class c extends d {
        private static final long serialVersionUID = 0;

        c(String str) {
            super(j.d(str));
        }

        @Override // com.google.common.base.Predicates.d
        public String toString() {
            return "Predicates.containsPattern(" + this.pattern.a() + ")";
        }
    }

    private static <T> List<Predicate<? super T>> a(Predicate<? super T> predicate, Predicate<? super T> predicate2) {
        return Arrays.asList(predicate, predicate2);
    }

    private static <T> List<T> a(T... tArr) {
        return a(Arrays.asList(tArr));
    }

    static <T> List<T> a(Iterable<T> iterable) {
        ArrayList arrayList = new ArrayList();
        for (T t : iterable) {
            arrayList.add(Preconditions.checkNotNull(t));
        }
        return arrayList;
    }
}
