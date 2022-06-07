package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.primitives.Primitives;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeResolver;
import com.google.common.reflect.d;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes2.dex */
public abstract class TypeToken<T> extends b<T> implements Serializable {
    private static final long serialVersionUID = 3637540370352322684L;
    @MonotonicNonNullDecl
    private transient TypeResolver a;
    @MonotonicNonNullDecl
    private transient TypeResolver b;
    private final Type runtimeType;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier removed */
    /* loaded from: classes2.dex */
    public static abstract class f extends Enum<f> implements Predicate<TypeToken<?>> {
    }

    public TypeToken() {
        this.runtimeType = a();
        Preconditions.checkState(!(this.runtimeType instanceof TypeVariable), "Cannot construct a TypeToken for a type variable.\nYou probably meant to call new TypeToken<%s>(getClass()) that can resolve the type variable for you.\nIf you do need to create a TypeToken of a type variable, please use TypeToken.of() instead.", this.runtimeType);
    }

    protected TypeToken(Class<?> cls) {
        Type a2 = super.a();
        if (a2 instanceof Class) {
            this.runtimeType = a2;
        } else {
            this.runtimeType = TypeResolver.a(cls).resolveType(a2);
        }
    }

    private TypeToken(Type type) {
        this.runtimeType = (Type) Preconditions.checkNotNull(type);
    }

    public static <T> TypeToken<T> of(Class<T> cls) {
        return new d(cls);
    }

    public static TypeToken<?> of(Type type) {
        return new d(type);
    }

    public final Class<? super T> getRawType() {
        return f().iterator().next();
    }

    public final Type getType() {
        return this.runtimeType;
    }

    public final <X> TypeToken<T> where(TypeParameter<X> typeParameter, TypeToken<X> typeToken) {
        return new d(new TypeResolver().a(ImmutableMap.of(new TypeResolver.c(typeParameter.a), typeToken.runtimeType)).resolveType(this.runtimeType));
    }

    public final <X> TypeToken<T> where(TypeParameter<X> typeParameter, Class<X> cls) {
        return where(typeParameter, of((Class) cls));
    }

    public final TypeToken<?> resolveType(Type type) {
        Preconditions.checkNotNull(type);
        return of(i().resolveType(type));
    }

    private TypeToken<?> a(Type type) {
        TypeToken<?> of = of(h().resolveType(type));
        of.b = this.b;
        of.a = this.a;
        return of;
    }

    @NullableDecl
    final TypeToken<? super T> b() {
        Type type = this.runtimeType;
        if (type instanceof TypeVariable) {
            return b(((TypeVariable) type).getBounds()[0]);
        }
        if (type instanceof WildcardType) {
            return b(((WildcardType) type).getUpperBounds()[0]);
        }
        Type genericSuperclass = getRawType().getGenericSuperclass();
        if (genericSuperclass == null) {
            return null;
        }
        return (TypeToken<? super T>) a(genericSuperclass);
    }

    @NullableDecl
    private TypeToken<? super T> b(Type type) {
        TypeToken<? super T> typeToken = (TypeToken<? super T>) of(type);
        if (typeToken.getRawType().isInterface()) {
            return null;
        }
        return typeToken;
    }

    final ImmutableList<TypeToken<? super T>> c() {
        Type type = this.runtimeType;
        if (type instanceof TypeVariable) {
            return a(((TypeVariable) type).getBounds());
        }
        if (type instanceof WildcardType) {
            return a(((WildcardType) type).getUpperBounds());
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Type type2 : getRawType().getGenericInterfaces()) {
            builder.add((ImmutableList.Builder) a(type2));
        }
        return builder.build();
    }

    private ImmutableList<TypeToken<? super T>> a(Type[] typeArr) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Type type : typeArr) {
            TypeToken<?> of = of(type);
            if (of.getRawType().isInterface()) {
                builder.add((ImmutableList.Builder) of);
            }
        }
        return builder.build();
    }

    public final TypeToken<T>.TypeSet getTypes() {
        return new TypeSet();
    }

    public final TypeToken<? super T> getSupertype(Class<? super T> cls) {
        Preconditions.checkArgument(b((Class<?>) cls), "%s is not a super class of %s", cls, this);
        Type type = this.runtimeType;
        if (type instanceof TypeVariable) {
            return a(cls, ((TypeVariable) type).getBounds());
        }
        if (type instanceof WildcardType) {
            return a(cls, ((WildcardType) type).getUpperBounds());
        }
        if (cls.isArray()) {
            return c((Class) cls);
        }
        return (TypeToken<? super T>) a(a((Class) cls).runtimeType);
    }

    public final TypeToken<? extends T> getSubtype(Class<?> cls) {
        Preconditions.checkArgument(!(this.runtimeType instanceof TypeVariable), "Cannot get subtype of type variable <%s>", this);
        Type type = this.runtimeType;
        if (type instanceof WildcardType) {
            return b(cls, ((WildcardType) type).getLowerBounds());
        }
        if (isArray()) {
            return d(cls);
        }
        Preconditions.checkArgument(getRawType().isAssignableFrom(cls), "%s isn't a subclass of %s", cls, this);
        TypeToken<? extends T> typeToken = (TypeToken<? extends T>) of(e(cls));
        Preconditions.checkArgument(typeToken.isSubtypeOf((TypeToken<?>) this), "%s does not appear to be a subtype of %s", typeToken, this);
        return typeToken;
    }

    public final boolean isSupertypeOf(TypeToken<?> typeToken) {
        return typeToken.isSubtypeOf(getType());
    }

    public final boolean isSupertypeOf(Type type) {
        return of(type).isSubtypeOf(getType());
    }

    public final boolean isSubtypeOf(TypeToken<?> typeToken) {
        return isSubtypeOf(typeToken.getType());
    }

    public final boolean isSubtypeOf(Type type) {
        Preconditions.checkNotNull(type);
        if (type instanceof WildcardType) {
            return c(((WildcardType) type).getLowerBounds()).b(this.runtimeType);
        }
        Type type2 = this.runtimeType;
        if (type2 instanceof WildcardType) {
            return c(((WildcardType) type2).getUpperBounds()).a(type);
        }
        if (type2 instanceof TypeVariable) {
            return type2.equals(type) || c(((TypeVariable) this.runtimeType).getBounds()).a(type);
        }
        if (type2 instanceof GenericArrayType) {
            return of(type).b((GenericArrayType) this.runtimeType);
        }
        if (type instanceof Class) {
            return b((Class) type);
        }
        if (type instanceof ParameterizedType) {
            return a((ParameterizedType) type);
        }
        if (type instanceof GenericArrayType) {
            return a((GenericArrayType) type);
        }
        return false;
    }

    public final boolean isArray() {
        return getComponentType() != null;
    }

    public final boolean isPrimitive() {
        Type type = this.runtimeType;
        return (type instanceof Class) && ((Class) type).isPrimitive();
    }

    public final TypeToken<T> wrap() {
        return isPrimitive() ? of(Primitives.wrap((Class) this.runtimeType)) : this;
    }

    private boolean e() {
        return Primitives.allWrapperTypes().contains(this.runtimeType);
    }

    public final TypeToken<T> unwrap() {
        return e() ? of(Primitives.unwrap((Class) this.runtimeType)) : this;
    }

    @NullableDecl
    public final TypeToken<?> getComponentType() {
        Type e2 = d.e(this.runtimeType);
        if (e2 == null) {
            return null;
        }
        return of(e2);
    }

    public final Invokable<T, Object> method(Method method) {
        Preconditions.checkArgument(b(method.getDeclaringClass()), "%s not declared by %s", method, this);
        return new Invokable.b<T>(method) { // from class: com.google.common.reflect.TypeToken.1
            @Override // com.google.common.reflect.Invokable.b, com.google.common.reflect.Invokable
            public Type d() {
                return TypeToken.this.h().resolveType(super.d());
            }

            @Override // com.google.common.reflect.Invokable.b, com.google.common.reflect.Invokable
            public Type[] a() {
                return TypeToken.this.i().a(super.a());
            }

            @Override // com.google.common.reflect.Invokable.b, com.google.common.reflect.Invokable
            public Type[] b() {
                return TypeToken.this.h().a(super.b());
            }

            @Override // com.google.common.reflect.Invokable, com.google.common.reflect.a
            public TypeToken<T> getOwnerType() {
                return TypeToken.this;
            }

            @Override // com.google.common.reflect.Invokable, com.google.common.reflect.a
            public String toString() {
                return getOwnerType() + "." + super.toString();
            }
        };
    }

    public final Invokable<T, T> constructor(Constructor<?> constructor) {
        Preconditions.checkArgument(constructor.getDeclaringClass() == getRawType(), "%s not declared by %s", constructor, getRawType());
        return new Invokable.a<T>(constructor) { // from class: com.google.common.reflect.TypeToken.2
            @Override // com.google.common.reflect.Invokable.a, com.google.common.reflect.Invokable
            public Type d() {
                return TypeToken.this.h().resolveType(super.d());
            }

            @Override // com.google.common.reflect.Invokable.a, com.google.common.reflect.Invokable
            public Type[] a() {
                return TypeToken.this.i().a(super.a());
            }

            @Override // com.google.common.reflect.Invokable.a, com.google.common.reflect.Invokable
            public Type[] b() {
                return TypeToken.this.h().a(super.b());
            }

            @Override // com.google.common.reflect.Invokable, com.google.common.reflect.a
            public TypeToken<T> getOwnerType() {
                return TypeToken.this;
            }

            @Override // com.google.common.reflect.Invokable, com.google.common.reflect.a
            public String toString() {
                return getOwnerType() + "(" + Joiner.on(", ").join(a()) + ")";
            }
        };
    }

    /* loaded from: classes2.dex */
    public class TypeSet extends ForwardingSet<TypeToken<? super T>> implements Serializable {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        private transient ImmutableSet<TypeToken<? super T>> a;

        TypeSet() {
            TypeToken.this = r1;
        }

        public TypeToken<T>.TypeSet interfaces() {
            return new c(this);
        }

        public TypeToken<T>.TypeSet classes() {
            return new b();
        }

        @Override // com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Set<TypeToken<? super T>> delegate() {
            ImmutableSet<TypeToken<? super T>> immutableSet = this.a;
            if (immutableSet != null) {
                return immutableSet;
            }
            ImmutableSet<TypeToken<? super T>> set = FluentIterable.from(e.a.a((e<TypeToken<?>>) TypeToken.this)).filter(f.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
            this.a = set;
            return set;
        }

        public Set<Class<? super T>> rawTypes() {
            return ImmutableSet.copyOf((Collection) e.b.a(TypeToken.this.f()));
        }
    }

    /* loaded from: classes2.dex */
    public final class c extends TypeToken<T>.TypeSet {
        private static final long serialVersionUID = 0;
        private final transient TypeToken<T>.TypeSet a;
        @MonotonicNonNullDecl
        private transient ImmutableSet<TypeToken<? super T>> b;

        @Override // com.google.common.reflect.TypeToken.TypeSet
        public TypeToken<T>.TypeSet interfaces() {
            return this;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        c(TypeToken<T>.TypeSet typeSet) {
            super();
            TypeToken.this = r1;
            this.a = typeSet;
        }

        @Override // com.google.common.reflect.TypeToken.TypeSet, com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Set<TypeToken<? super T>> delegate() {
            ImmutableSet<TypeToken<? super T>> immutableSet = this.b;
            if (immutableSet != null) {
                return immutableSet;
            }
            ImmutableSet<TypeToken<? super T>> set = FluentIterable.from(this.a).filter(f.INTERFACE_ONLY).toSet();
            this.b = set;
            return set;
        }

        @Override // com.google.common.reflect.TypeToken.TypeSet
        public Set<Class<? super T>> rawTypes() {
            return FluentIterable.from(e.b.a(TypeToken.this.f())).filter(new Predicate<Class<?>>() { // from class: com.google.common.reflect.TypeToken.c.1
                /* renamed from: a */
                public boolean apply(Class<?> cls) {
                    return cls.isInterface();
                }
            }).toSet();
        }

        @Override // com.google.common.reflect.TypeToken.TypeSet
        public TypeToken<T>.TypeSet classes() {
            throw new UnsupportedOperationException("interfaces().classes() not supported.");
        }

        private Object readResolve() {
            return TypeToken.this.getTypes().interfaces();
        }
    }

    /* loaded from: classes2.dex */
    public final class b extends TypeToken<T>.TypeSet {
        private static final long serialVersionUID = 0;
        @MonotonicNonNullDecl
        private transient ImmutableSet<TypeToken<? super T>> a;

        @Override // com.google.common.reflect.TypeToken.TypeSet
        public TypeToken<T>.TypeSet classes() {
            return this;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private b() {
            super();
            TypeToken.this = r1;
        }

        @Override // com.google.common.reflect.TypeToken.TypeSet, com.google.common.collect.ForwardingSet, com.google.common.collect.ForwardingCollection, com.google.common.collect.ForwardingObject
        public Set<TypeToken<? super T>> delegate() {
            ImmutableSet<TypeToken<? super T>> immutableSet = this.a;
            if (immutableSet != null) {
                return immutableSet;
            }
            ImmutableSet<TypeToken<? super T>> set = FluentIterable.from(e.a.a().a((e<TypeToken<?>>) TypeToken.this)).filter(f.IGNORE_TYPE_VARIABLE_OR_WILDCARD).toSet();
            this.a = set;
            return set;
        }

        @Override // com.google.common.reflect.TypeToken.TypeSet
        public Set<Class<? super T>> rawTypes() {
            return ImmutableSet.copyOf((Collection) e.b.a().a(TypeToken.this.f()));
        }

        @Override // com.google.common.reflect.TypeToken.TypeSet
        public TypeToken<T>.TypeSet interfaces() {
            throw new UnsupportedOperationException("classes().interfaces() not supported.");
        }

        private Object readResolve() {
            return TypeToken.this.getTypes().classes();
        }
    }

    public boolean equals(@NullableDecl Object obj) {
        if (obj instanceof TypeToken) {
            return this.runtimeType.equals(((TypeToken) obj).runtimeType);
        }
        return false;
    }

    public int hashCode() {
        return this.runtimeType.hashCode();
    }

    public String toString() {
        return d.d(this.runtimeType);
    }

    protected Object writeReplace() {
        return of(new TypeResolver().resolveType(this.runtimeType));
    }

    @CanIgnoreReturnValue
    public final TypeToken<T> d() {
        new c() { // from class: com.google.common.reflect.TypeToken.3
            @Override // com.google.common.reflect.c
            void a(TypeVariable<?> typeVariable) {
                throw new IllegalArgumentException(TypeToken.this.runtimeType + "contains a type variable and is not safe for the operation");
            }

            @Override // com.google.common.reflect.c
            void a(WildcardType wildcardType) {
                a(wildcardType.getLowerBounds());
                a(wildcardType.getUpperBounds());
            }

            @Override // com.google.common.reflect.c
            void a(ParameterizedType parameterizedType) {
                a(parameterizedType.getActualTypeArguments());
                a(parameterizedType.getOwnerType());
            }

            @Override // com.google.common.reflect.c
            void a(GenericArrayType genericArrayType) {
                a(genericArrayType.getGenericComponentType());
            }
        }.a(this.runtimeType);
        return this;
    }

    private boolean b(Class<?> cls) {
        UnmodifiableIterator<Class<? super T>> it = f().iterator();
        while (it.hasNext()) {
            if (cls.isAssignableFrom(it.next())) {
                return true;
            }
        }
        return false;
    }

    private boolean a(ParameterizedType parameterizedType) {
        Class<? super Object> rawType = of(parameterizedType).getRawType();
        if (!b((Class<?>) rawType)) {
            return false;
        }
        TypeVariable<Class<? super Object>>[] typeParameters = rawType.getTypeParameters();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        for (int i = 0; i < typeParameters.length; i++) {
            if (!of(h().resolveType(typeParameters[i])).a(actualTypeArguments[i], typeParameters[i])) {
                return false;
            }
        }
        return Modifier.isStatic(((Class) parameterizedType.getRawType()).getModifiers()) || parameterizedType.getOwnerType() == null || d(parameterizedType.getOwnerType());
    }

    private boolean a(GenericArrayType genericArrayType) {
        Type type = this.runtimeType;
        if (type instanceof Class) {
            Class cls = (Class) type;
            if (!cls.isArray()) {
                return false;
            }
            return of((Class) cls.getComponentType()).isSubtypeOf(genericArrayType.getGenericComponentType());
        } else if (type instanceof GenericArrayType) {
            return of(((GenericArrayType) type).getGenericComponentType()).isSubtypeOf(genericArrayType.getGenericComponentType());
        } else {
            return false;
        }
    }

    private boolean b(GenericArrayType genericArrayType) {
        Type type = this.runtimeType;
        if (type instanceof Class) {
            Class cls = (Class) type;
            if (!cls.isArray()) {
                return cls.isAssignableFrom(Object[].class);
            }
            return of(genericArrayType.getGenericComponentType()).isSubtypeOf(cls.getComponentType());
        } else if (type instanceof GenericArrayType) {
            return of(genericArrayType.getGenericComponentType()).isSubtypeOf(((GenericArrayType) this.runtimeType).getGenericComponentType());
        } else {
            return false;
        }
    }

    private boolean a(Type type, TypeVariable<?> typeVariable) {
        if (this.runtimeType.equals(type)) {
            return true;
        }
        if (!(type instanceof WildcardType)) {
            return c(this.runtimeType).equals(c(type));
        }
        WildcardType a2 = a(typeVariable, (WildcardType) type);
        return b(a2.getUpperBounds()).b(this.runtimeType) && b(a2.getLowerBounds()).a(this.runtimeType);
    }

    private static Type a(TypeVariable<?> typeVariable, Type type) {
        if (type instanceof WildcardType) {
            return a(typeVariable, (WildcardType) type);
        }
        return c(type);
    }

    private static Type c(Type type) {
        if (type instanceof ParameterizedType) {
            return b((ParameterizedType) type);
        }
        return type instanceof GenericArrayType ? d.a(c(((GenericArrayType) type).getGenericComponentType())) : type;
    }

    private static WildcardType a(TypeVariable<?> typeVariable, WildcardType wildcardType) {
        Type[] bounds = typeVariable.getBounds();
        ArrayList arrayList = new ArrayList();
        Type[] upperBounds = wildcardType.getUpperBounds();
        for (Type type : upperBounds) {
            if (!c(bounds).a(type)) {
                arrayList.add(c(type));
            }
        }
        return new d.h(wildcardType.getLowerBounds(), (Type[]) arrayList.toArray(new Type[0]));
    }

    private static ParameterizedType b(ParameterizedType parameterizedType) {
        Class cls = (Class) parameterizedType.getRawType();
        TypeVariable<Class<T>>[] typeParameters = cls.getTypeParameters();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        for (int i = 0; i < actualTypeArguments.length; i++) {
            actualTypeArguments[i] = a(typeParameters[i], actualTypeArguments[i]);
        }
        return d.a(parameterizedType.getOwnerType(), cls, actualTypeArguments);
    }

    private static a b(Type[] typeArr) {
        return new a(typeArr, false);
    }

    private static a c(Type[] typeArr) {
        return new a(typeArr, true);
    }

    /* loaded from: classes2.dex */
    public static class a {
        private final Type[] a;
        private final boolean b;

        a(Type[] typeArr, boolean z) {
            this.a = typeArr;
            this.b = z;
        }

        boolean a(Type type) {
            for (Type type2 : this.a) {
                boolean isSubtypeOf = TypeToken.of(type2).isSubtypeOf(type);
                boolean z = this.b;
                if (isSubtypeOf == z) {
                    return z;
                }
            }
            return !this.b;
        }

        boolean b(Type type) {
            TypeToken<?> of = TypeToken.of(type);
            for (Type type2 : this.a) {
                boolean isSubtypeOf = of.isSubtypeOf(type2);
                boolean z = this.b;
                if (isSubtypeOf == z) {
                    return z;
                }
            }
            return !this.b;
        }
    }

    public ImmutableSet<Class<? super T>> f() {
        final ImmutableSet.Builder builder = ImmutableSet.builder();
        new c() { // from class: com.google.common.reflect.TypeToken.4
            @Override // com.google.common.reflect.c
            void a(TypeVariable<?> typeVariable) {
                a(typeVariable.getBounds());
            }

            @Override // com.google.common.reflect.c
            void a(WildcardType wildcardType) {
                a(wildcardType.getUpperBounds());
            }

            @Override // com.google.common.reflect.c
            void a(ParameterizedType parameterizedType) {
                builder.add((ImmutableSet.Builder) ((Class) parameterizedType.getRawType()));
            }

            @Override // com.google.common.reflect.c
            void a(Class<?> cls) {
                builder.add((ImmutableSet.Builder) cls);
            }

            @Override // com.google.common.reflect.c
            void a(GenericArrayType genericArrayType) {
                builder.add((ImmutableSet.Builder) d.a((Class<?>) TypeToken.of(genericArrayType.getGenericComponentType()).getRawType()));
            }
        }.a(this.runtimeType);
        return builder.build();
    }

    private boolean d(Type type) {
        Iterator<TypeToken<? super T>> it = getTypes().iterator();
        while (it.hasNext()) {
            Type g = it.next().g();
            if (g != null && of(g).isSubtypeOf(type)) {
                return true;
            }
        }
        return false;
    }

    @NullableDecl
    private Type g() {
        Type type = this.runtimeType;
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType) type).getOwnerType();
        }
        if (type instanceof Class) {
            return ((Class) type).getEnclosingClass();
        }
        return null;
    }

    @VisibleForTesting
    static <T> TypeToken<? extends T> a(Class<T> cls) {
        if (cls.isArray()) {
            return (TypeToken<? extends T>) of(d.a(a((Class) cls.getComponentType()).runtimeType));
        }
        TypeVariable<Class<T>>[] typeParameters = cls.getTypeParameters();
        Type type = (!cls.isMemberClass() || Modifier.isStatic(cls.getModifiers())) ? null : a((Class) cls.getEnclosingClass()).runtimeType;
        if (typeParameters.length > 0 || (type != null && type != cls.getEnclosingClass())) {
            return (TypeToken<? extends T>) of(d.a(type, (Class<?>) cls, (Type[]) typeParameters));
        }
        return of((Class) cls);
    }

    public TypeResolver h() {
        TypeResolver typeResolver = this.b;
        if (typeResolver != null) {
            return typeResolver;
        }
        TypeResolver a2 = TypeResolver.a(this.runtimeType);
        this.b = a2;
        return a2;
    }

    public TypeResolver i() {
        TypeResolver typeResolver = this.a;
        if (typeResolver != null) {
            return typeResolver;
        }
        TypeResolver b2 = TypeResolver.b(this.runtimeType);
        this.a = b2;
        return b2;
    }

    private TypeToken<? super T> a(Class<? super T> cls, Type[] typeArr) {
        for (Type type : typeArr) {
            TypeToken<?> of = of(type);
            if (of.isSubtypeOf(cls)) {
                return (TypeToken<? super T>) of.getSupertype(cls);
            }
        }
        throw new IllegalArgumentException(cls + " isn't a super type of " + this);
    }

    private TypeToken<? extends T> b(Class<?> cls, Type[] typeArr) {
        if (typeArr.length > 0) {
            return (TypeToken<? extends T>) of(typeArr[0]).getSubtype(cls);
        }
        throw new IllegalArgumentException(cls + " isn't a subclass of " + this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private TypeToken<? super T> c(Class<? super T> cls) {
        return (TypeToken<? super T>) of(e(((TypeToken) Preconditions.checkNotNull(getComponentType(), "%s isn't a super type of %s", cls, this)).getSupertype(cls.getComponentType()).runtimeType));
    }

    private TypeToken<? extends T> d(Class<?> cls) {
        return (TypeToken<? extends T>) of(e(getComponentType().getSubtype(cls.getComponentType()).runtimeType));
    }

    private Type e(Class<?> cls) {
        if ((this.runtimeType instanceof Class) && (cls.getTypeParameters().length == 0 || getRawType().getTypeParameters().length != 0)) {
            return cls;
        }
        TypeToken a2 = a((Class) cls);
        return new TypeResolver().where(a2.getSupertype(getRawType()).runtimeType, this.runtimeType).resolveType(a2.runtimeType);
    }

    private static Type e(Type type) {
        return d.c.JAVA7.a(type);
    }

    /* loaded from: classes2.dex */
    public static final class d<T> extends TypeToken<T> {
        private static final long serialVersionUID = 0;

        d(Type type) {
            super(type);
        }
    }

    /* loaded from: classes2.dex */
    public static abstract class e<K> {
        static final e<TypeToken<?>> a = new e<TypeToken<?>>() { // from class: com.google.common.reflect.TypeToken.e.1
            /* renamed from: a */
            public Class<?> b(TypeToken<?> typeToken) {
                return typeToken.getRawType();
            }

            /* renamed from: b */
            public Iterable<? extends TypeToken<?>> c(TypeToken<?> typeToken) {
                return typeToken.c();
            }

            @NullableDecl
            /* renamed from: c */
            public TypeToken<?> d(TypeToken<?> typeToken) {
                return typeToken.b();
            }
        };
        static final e<Class<?>> b = new e<Class<?>>() { // from class: com.google.common.reflect.TypeToken.e.2
            /* renamed from: a */
            public Class<?> b(Class<?> cls) {
                return cls;
            }

            /* renamed from: b */
            public Iterable<? extends Class<?>> c(Class<?> cls) {
                return Arrays.asList(cls.getInterfaces());
            }

            @NullableDecl
            /* renamed from: c */
            public Class<?> d(Class<?> cls) {
                return cls.getSuperclass();
            }
        };

        abstract Class<?> b(K k);

        abstract Iterable<? extends K> c(K k);

        @NullableDecl
        abstract K d(K k);

        private e() {
        }

        final e<K> a() {
            return new a<K>(this) { // from class: com.google.common.reflect.TypeToken.e.3
                @Override // com.google.common.reflect.TypeToken.e.a, com.google.common.reflect.TypeToken.e
                Iterable<? extends K> c(K k) {
                    return ImmutableSet.of();
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.common.reflect.TypeToken.e
                ImmutableList<K> a(Iterable<? extends K> iterable) {
                    ImmutableList.Builder builder = ImmutableList.builder();
                    for (Object obj : iterable) {
                        if (!b(obj).isInterface()) {
                            builder.add((ImmutableList.Builder) obj);
                        }
                    }
                    return super.a((Iterable) builder.build());
                }
            };
        }

        final ImmutableList<K> a(K k) {
            return a((Iterable) ImmutableList.of(k));
        }

        /* JADX WARN: Multi-variable type inference failed */
        ImmutableList<K> a(Iterable<? extends K> iterable) {
            HashMap newHashMap = Maps.newHashMap();
            Iterator<? extends K> it = iterable.iterator();
            while (it.hasNext()) {
                a((e<K>) it.next(), (Map<? super e<K>, Integer>) newHashMap);
            }
            return a(newHashMap, Ordering.natural().reverse());
        }

        /* JADX WARN: Multi-variable type inference failed */
        @CanIgnoreReturnValue
        private int a(K k, Map<? super K, Integer> map) {
            Integer num = map.get(k);
            if (num != null) {
                return num.intValue();
            }
            boolean isInterface = b(k).isInterface();
            Iterator<? extends K> it = c(k).iterator();
            int i = isInterface;
            while (it.hasNext()) {
                i = Math.max(i, a((e<K>) it.next(), (Map<? super e<K>, Integer>) map));
            }
            K d = d(k);
            int i2 = i;
            if (d != null) {
                i2 = Math.max(i, a((e<K>) d, (Map<? super e<K>, Integer>) map));
            }
            int i3 = (i2 == 1 ? 1 : 0) + 1;
            map.put(k, Integer.valueOf(i3));
            return i3;
        }

        private static <K, V> ImmutableList<K> a(final Map<K, V> map, final Comparator<? super V> comparator) {
            return (ImmutableList<K>) new Ordering<K>() { // from class: com.google.common.reflect.TypeToken.e.4
                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.google.common.collect.Ordering, java.util.Comparator
                public int compare(K k, K k2) {
                    return comparator.compare(map.get(k), map.get(k2));
                }
            }.immutableSortedCopy(map.keySet());
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static class a<K> extends e<K> {
            private final e<K> c;

            a(e<K> eVar) {
                super();
                this.c = eVar;
            }

            @Override // com.google.common.reflect.TypeToken.e
            Class<?> b(K k) {
                return this.c.b(k);
            }

            @Override // com.google.common.reflect.TypeToken.e
            Iterable<? extends K> c(K k) {
                return this.c.c(k);
            }

            @Override // com.google.common.reflect.TypeToken.e
            K d(K k) {
                return this.c.d(k);
            }
        }
    }
}
