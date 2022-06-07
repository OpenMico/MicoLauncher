package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.reflect.d;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.text.Typography;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes2.dex */
public final class TypeResolver {
    private final b a;

    public TypeResolver() {
        this.a = new b();
    }

    private TypeResolver(b bVar) {
        this.a = bVar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static TypeResolver a(Type type) {
        return new TypeResolver().a(a.a(type));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static TypeResolver b(Type type) {
        return new TypeResolver().a(a.a(d.a.a(type)));
    }

    public TypeResolver where(Type type, Type type2) {
        HashMap newHashMap = Maps.newHashMap();
        b(newHashMap, (Type) Preconditions.checkNotNull(type), (Type) Preconditions.checkNotNull(type2));
        return a(newHashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public TypeResolver a(Map<c, ? extends Type> map) {
        return new TypeResolver(this.a.a(map));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(final Map<c, Type> map, Type type, final Type type2) {
        if (!type.equals(type2)) {
            new c() { // from class: com.google.common.reflect.TypeResolver.1
                @Override // com.google.common.reflect.c
                void a(TypeVariable<?> typeVariable) {
                    map.put(new c(typeVariable), type2);
                }

                @Override // com.google.common.reflect.c
                void a(WildcardType wildcardType) {
                    Type type3 = type2;
                    if (type3 instanceof WildcardType) {
                        WildcardType wildcardType2 = (WildcardType) type3;
                        Type[] upperBounds = wildcardType.getUpperBounds();
                        Type[] upperBounds2 = wildcardType2.getUpperBounds();
                        Type[] lowerBounds = wildcardType.getLowerBounds();
                        Type[] lowerBounds2 = wildcardType2.getLowerBounds();
                        Preconditions.checkArgument(upperBounds.length == upperBounds2.length && lowerBounds.length == lowerBounds2.length, "Incompatible type: %s vs. %s", wildcardType, type2);
                        for (int i = 0; i < upperBounds.length; i++) {
                            TypeResolver.b(map, upperBounds[i], upperBounds2[i]);
                        }
                        for (int i2 = 0; i2 < lowerBounds.length; i2++) {
                            TypeResolver.b(map, lowerBounds[i2], lowerBounds2[i2]);
                        }
                    }
                }

                @Override // com.google.common.reflect.c
                void a(ParameterizedType parameterizedType) {
                    Type type3 = type2;
                    if (!(type3 instanceof WildcardType)) {
                        ParameterizedType parameterizedType2 = (ParameterizedType) TypeResolver.b(ParameterizedType.class, type3);
                        if (!(parameterizedType.getOwnerType() == null || parameterizedType2.getOwnerType() == null)) {
                            TypeResolver.b(map, parameterizedType.getOwnerType(), parameterizedType2.getOwnerType());
                        }
                        Preconditions.checkArgument(parameterizedType.getRawType().equals(parameterizedType2.getRawType()), "Inconsistent raw type: %s vs. %s", parameterizedType, type2);
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        Type[] actualTypeArguments2 = parameterizedType2.getActualTypeArguments();
                        Preconditions.checkArgument(actualTypeArguments.length == actualTypeArguments2.length, "%s not compatible with %s", parameterizedType, parameterizedType2);
                        for (int i = 0; i < actualTypeArguments.length; i++) {
                            TypeResolver.b(map, actualTypeArguments[i], actualTypeArguments2[i]);
                        }
                    }
                }

                @Override // com.google.common.reflect.c
                void a(GenericArrayType genericArrayType) {
                    Type type3 = type2;
                    if (!(type3 instanceof WildcardType)) {
                        Type e = d.e(type3);
                        Preconditions.checkArgument(e != null, "%s is not an array type.", type2);
                        TypeResolver.b(map, genericArrayType.getGenericComponentType(), e);
                    }
                }

                @Override // com.google.common.reflect.c
                void a(Class<?> cls) {
                    if (!(type2 instanceof WildcardType)) {
                        throw new IllegalArgumentException("No type mapping from " + cls + " to " + type2);
                    }
                }
            }.a(type);
        }
    }

    public Type resolveType(Type type) {
        Preconditions.checkNotNull(type);
        if (type instanceof TypeVariable) {
            return this.a.a((TypeVariable) type);
        }
        if (type instanceof ParameterizedType) {
            return a((ParameterizedType) type);
        }
        if (type instanceof GenericArrayType) {
            return a((GenericArrayType) type);
        }
        return type instanceof WildcardType ? a((WildcardType) type) : type;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Type[] a(Type[] typeArr) {
        for (int i = 0; i < typeArr.length; i++) {
            typeArr[i] = resolveType(typeArr[i]);
        }
        return typeArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Type[] b(Type[] typeArr) {
        Type[] typeArr2 = new Type[typeArr.length];
        for (int i = 0; i < typeArr.length; i++) {
            typeArr2[i] = resolveType(typeArr[i]);
        }
        return typeArr2;
    }

    private WildcardType a(WildcardType wildcardType) {
        return new d.h(b(wildcardType.getLowerBounds()), b(wildcardType.getUpperBounds()));
    }

    private Type a(GenericArrayType genericArrayType) {
        return d.a(resolveType(genericArrayType.getGenericComponentType()));
    }

    private ParameterizedType a(ParameterizedType parameterizedType) {
        Type ownerType = parameterizedType.getOwnerType();
        return d.a(ownerType == null ? null : resolveType(ownerType), (Class) resolveType(parameterizedType.getRawType()), b(parameterizedType.getActualTypeArguments()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T b(Class<T> cls, Object obj) {
        try {
            return cls.cast(obj);
        } catch (ClassCastException unused) {
            throw new IllegalArgumentException(obj + " is not a " + cls.getSimpleName());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class b {
        private final ImmutableMap<c, Type> a;

        b() {
            this.a = ImmutableMap.of();
        }

        private b(ImmutableMap<c, Type> immutableMap) {
            this.a = immutableMap;
        }

        final b a(Map<c, ? extends Type> map) {
            ImmutableMap.Builder builder = ImmutableMap.builder();
            builder.putAll(this.a);
            for (Map.Entry<c, ? extends Type> entry : map.entrySet()) {
                c key = entry.getKey();
                Type type = (Type) entry.getValue();
                Preconditions.checkArgument(!key.b(type), "Type variable %s bound to itself", key);
                builder.put(key, type);
            }
            return new b(builder.build());
        }

        final Type a(final TypeVariable<?> typeVariable) {
            return a(typeVariable, new b() { // from class: com.google.common.reflect.TypeResolver.b.1
                @Override // com.google.common.reflect.TypeResolver.b
                public Type a(TypeVariable<?> typeVariable2, b bVar) {
                    return typeVariable2.getGenericDeclaration().equals(typeVariable.getGenericDeclaration()) ? typeVariable2 : this.a(typeVariable2, bVar);
                }
            });
        }

        Type a(TypeVariable<?> typeVariable, b bVar) {
            Type type = this.a.get(new c(typeVariable));
            if (type != null) {
                return new TypeResolver(bVar).resolveType(type);
            }
            Type[] bounds = typeVariable.getBounds();
            if (bounds.length == 0) {
                return typeVariable;
            }
            Type[] b = new TypeResolver(bVar).b(bounds);
            return (!d.C0120d.a || !Arrays.equals(bounds, b)) ? d.a(typeVariable.getGenericDeclaration(), typeVariable.getName(), b) : typeVariable;
        }
    }

    /* loaded from: classes2.dex */
    private static final class a extends c {
        private final Map<c, Type> a = Maps.newHashMap();

        private a() {
        }

        static ImmutableMap<c, Type> a(Type type) {
            Preconditions.checkNotNull(type);
            a aVar = new a();
            aVar.a(type);
            return ImmutableMap.copyOf(aVar.a);
        }

        @Override // com.google.common.reflect.c
        void a(Class<?> cls) {
            a(cls.getGenericSuperclass());
            a(cls.getGenericInterfaces());
        }

        @Override // com.google.common.reflect.c
        void a(ParameterizedType parameterizedType) {
            Class cls = (Class) parameterizedType.getRawType();
            TypeVariable[] typeParameters = cls.getTypeParameters();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Preconditions.checkState(typeParameters.length == actualTypeArguments.length);
            for (int i = 0; i < typeParameters.length; i++) {
                a(new c(typeParameters[i]), actualTypeArguments[i]);
            }
            a(cls);
            a(parameterizedType.getOwnerType());
        }

        @Override // com.google.common.reflect.c
        void a(TypeVariable<?> typeVariable) {
            a(typeVariable.getBounds());
        }

        @Override // com.google.common.reflect.c
        void a(WildcardType wildcardType) {
            a(wildcardType.getUpperBounds());
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0014, code lost:
            r4 = r2.a.remove(com.google.common.reflect.TypeResolver.c.a(r4));
         */
        /* JADX WARN: Code restructure failed: missing block: B:11:0x0022, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x0012, code lost:
            if (r4 == null) goto L_0x0022;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void a(com.google.common.reflect.TypeResolver.c r3, java.lang.reflect.Type r4) {
            /*
                r2 = this;
                java.util.Map<com.google.common.reflect.TypeResolver$c, java.lang.reflect.Type> r0 = r2.a
                boolean r0 = r0.containsKey(r3)
                if (r0 == 0) goto L_0x0009
                return
            L_0x0009:
                r0 = r4
            L_0x000a:
                if (r0 == 0) goto L_0x0030
                boolean r1 = r3.b(r0)
                if (r1 == 0) goto L_0x0023
            L_0x0012:
                if (r4 == 0) goto L_0x0022
                java.util.Map<com.google.common.reflect.TypeResolver$c, java.lang.reflect.Type> r3 = r2.a
                com.google.common.reflect.TypeResolver$c r4 = com.google.common.reflect.TypeResolver.c.a(r4)
                java.lang.Object r3 = r3.remove(r4)
                r4 = r3
                java.lang.reflect.Type r4 = (java.lang.reflect.Type) r4
                goto L_0x0012
            L_0x0022:
                return
            L_0x0023:
                java.util.Map<com.google.common.reflect.TypeResolver$c, java.lang.reflect.Type> r1 = r2.a
                com.google.common.reflect.TypeResolver$c r0 = com.google.common.reflect.TypeResolver.c.a(r0)
                java.lang.Object r0 = r1.get(r0)
                java.lang.reflect.Type r0 = (java.lang.reflect.Type) r0
                goto L_0x000a
            L_0x0030:
                java.util.Map<com.google.common.reflect.TypeResolver$c, java.lang.reflect.Type> r0 = r2.a
                r0.put(r3, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.reflect.TypeResolver.a.a(com.google.common.reflect.TypeResolver$c, java.lang.reflect.Type):void");
        }
    }

    /* loaded from: classes2.dex */
    private static class d {
        static final d a = new d();
        private final AtomicInteger b;

        private d() {
            this(new AtomicInteger());
        }

        private d(AtomicInteger atomicInteger) {
            this.b = atomicInteger;
        }

        final Type a(Type type) {
            Preconditions.checkNotNull(type);
            if ((type instanceof Class) || (type instanceof TypeVariable)) {
                return type;
            }
            if (type instanceof GenericArrayType) {
                return d.a(a().a(((GenericArrayType) type).getGenericComponentType()));
            }
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class cls = (Class) parameterizedType.getRawType();
                TypeVariable<?>[] typeParameters = cls.getTypeParameters();
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    actualTypeArguments[i] = a(typeParameters[i]).a(actualTypeArguments[i]);
                }
                return d.a(a().b(parameterizedType.getOwnerType()), cls, actualTypeArguments);
            } else if (type instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) type;
                return wildcardType.getLowerBounds().length == 0 ? a(wildcardType.getUpperBounds()) : type;
            } else {
                throw new AssertionError("must have been one of the known types");
            }
        }

        TypeVariable<?> a(Type[] typeArr) {
            return d.a(d.class, "capture#" + this.b.incrementAndGet() + "-of ? extends " + Joiner.on((char) Typography.amp).join(typeArr), typeArr);
        }

        private d a(final TypeVariable<?> typeVariable) {
            return new d(this.b) { // from class: com.google.common.reflect.TypeResolver.d.1
                @Override // com.google.common.reflect.TypeResolver.d
                TypeVariable<?> a(Type[] typeArr) {
                    LinkedHashSet linkedHashSet = new LinkedHashSet(Arrays.asList(typeArr));
                    linkedHashSet.addAll(Arrays.asList(typeVariable.getBounds()));
                    if (linkedHashSet.size() > 1) {
                        linkedHashSet.remove(Object.class);
                    }
                    return super.a((Type[]) linkedHashSet.toArray(new Type[0]));
                }
            };
        }

        private d a() {
            return new d(this.b);
        }

        private Type b(@NullableDecl Type type) {
            if (type == null) {
                return null;
            }
            return a(type);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static final class c {
        private final TypeVariable<?> a;

        /* JADX INFO: Access modifiers changed from: package-private */
        public c(TypeVariable<?> typeVariable) {
            this.a = (TypeVariable) Preconditions.checkNotNull(typeVariable);
        }

        public int hashCode() {
            return Objects.hashCode(this.a.getGenericDeclaration(), this.a.getName());
        }

        public boolean equals(Object obj) {
            if (obj instanceof c) {
                return a(((c) obj).a);
            }
            return false;
        }

        public String toString() {
            return this.a.toString();
        }

        static c a(Type type) {
            if (type instanceof TypeVariable) {
                return new c((TypeVariable) type);
            }
            return null;
        }

        boolean b(Type type) {
            if (type instanceof TypeVariable) {
                return a((TypeVariable) type);
            }
            return false;
        }

        private boolean a(TypeVariable<?> typeVariable) {
            return this.a.getGenericDeclaration().equals(typeVariable.getGenericDeclaration()) && this.a.getName().equals(typeVariable.getName());
        }
    }
}
