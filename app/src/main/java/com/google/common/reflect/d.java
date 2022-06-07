package com.google.common.reflect;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.security.AccessControlException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.text.Typography;
import okhttp3.HttpUrl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* compiled from: Types.java */
/* loaded from: classes2.dex */
public final class d {
    private static final Function<Type, String> a = new Function<Type, String>() { // from class: com.google.common.reflect.d.1
        /* renamed from: a */
        public String apply(Type type) {
            return c.e.c(type);
        }
    };
    private static final Joiner b = Joiner.on(", ").useForNull("null");

    public static Type a(Type type) {
        if (!(type instanceof WildcardType)) {
            return c.e.a(type);
        }
        WildcardType wildcardType = (WildcardType) type;
        Type[] lowerBounds = wildcardType.getLowerBounds();
        boolean z = true;
        Preconditions.checkArgument(lowerBounds.length <= 1, "Wildcard cannot have more than one lower bounds.");
        if (lowerBounds.length == 1) {
            return c(a(lowerBounds[0]));
        }
        Type[] upperBounds = wildcardType.getUpperBounds();
        if (upperBounds.length != 1) {
            z = false;
        }
        Preconditions.checkArgument(z, "Wildcard should have only one upper bound.");
        return b(a(upperBounds[0]));
    }

    public static ParameterizedType a(@NullableDecl Type type, Class<?> cls, Type... typeArr) {
        if (type == null) {
            return a(cls, typeArr);
        }
        Preconditions.checkNotNull(typeArr);
        Preconditions.checkArgument(cls.getEnclosingClass() != null, "Owner type for unenclosed %s", cls);
        return new e(type, cls, typeArr);
    }

    public static ParameterizedType a(Class<?> cls, Type... typeArr) {
        return new e(a.c.a(cls), cls, typeArr);
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier removed */
    /* compiled from: Types.java */
    /* loaded from: classes2.dex */
    public static abstract class a extends Enum<a> {
        static final a c = a();

        @NullableDecl
        abstract Class<?> a(Class<?> cls);

        /* compiled from: Types.java */
        /* renamed from: com.google.common.reflect.d$a$a */
        /* loaded from: classes2.dex */
        public class C0119a<T> {
            C0119a() {
            }
        }

        private static a a() {
            ParameterizedType parameterizedType = (ParameterizedType) new C0119a<String>() { // from class: com.google.common.reflect.d.a.3
            }.getClass().getGenericSuperclass();
            a[] values = values();
            for (a aVar : values) {
                if (aVar.a(C0119a.class) == parameterizedType.getOwnerType()) {
                    return aVar;
                }
            }
            throw new AssertionError();
        }
    }

    public static <D extends GenericDeclaration> TypeVariable<D> a(D d, String str, Type... typeArr) {
        if (typeArr.length == 0) {
            typeArr = new Type[]{Object.class};
        }
        return b(d, str, typeArr);
    }

    @VisibleForTesting
    static WildcardType b(Type type) {
        return new h(new Type[0], new Type[]{type});
    }

    @VisibleForTesting
    static WildcardType c(Type type) {
        return new h(new Type[]{type}, new Type[]{Object.class});
    }

    public static String d(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    @NullableDecl
    public static Type e(Type type) {
        Preconditions.checkNotNull(type);
        final AtomicReference atomicReference = new AtomicReference();
        new c() { // from class: com.google.common.reflect.d.2
            @Override // com.google.common.reflect.c
            void a(TypeVariable<?> typeVariable) {
                atomicReference.set(d.b(typeVariable.getBounds()));
            }

            @Override // com.google.common.reflect.c
            void a(WildcardType wildcardType) {
                atomicReference.set(d.b(wildcardType.getUpperBounds()));
            }

            @Override // com.google.common.reflect.c
            void a(GenericArrayType genericArrayType) {
                atomicReference.set(genericArrayType.getGenericComponentType());
            }

            @Override // com.google.common.reflect.c
            void a(Class<?> cls) {
                atomicReference.set(cls.getComponentType());
            }
        }.a(type);
        return (Type) atomicReference.get();
    }

    @NullableDecl
    public static Type b(Type[] typeArr) {
        for (Type type : typeArr) {
            Type e2 = e(type);
            if (e2 != null) {
                if (e2 instanceof Class) {
                    Class cls = (Class) e2;
                    if (cls.isPrimitive()) {
                        return cls;
                    }
                }
                return b(e2);
            }
        }
        return null;
    }

    /* compiled from: Types.java */
    /* loaded from: classes2.dex */
    public static final class b implements Serializable, GenericArrayType {
        private static final long serialVersionUID = 0;
        private final Type componentType;

        b(Type type) {
            this.componentType = c.e.b(type);
        }

        @Override // java.lang.reflect.GenericArrayType
        public Type getGenericComponentType() {
            return this.componentType;
        }

        public String toString() {
            return d.d(this.componentType) + HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj instanceof GenericArrayType) {
                return Objects.equal(getGenericComponentType(), ((GenericArrayType) obj).getGenericComponentType());
            }
            return false;
        }
    }

    /* compiled from: Types.java */
    /* loaded from: classes2.dex */
    public static final class e implements Serializable, ParameterizedType {
        private static final long serialVersionUID = 0;
        private final ImmutableList<Type> argumentsList;
        @NullableDecl
        private final Type ownerType;
        private final Class<?> rawType;

        e(@NullableDecl Type type, Class<?> cls, Type[] typeArr) {
            Preconditions.checkNotNull(cls);
            Preconditions.checkArgument(typeArr.length == cls.getTypeParameters().length);
            d.b(typeArr, "type parameter");
            this.ownerType = type;
            this.rawType = cls;
            this.argumentsList = c.e.a(typeArr);
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type[] getActualTypeArguments() {
            return d.b((Collection<Type>) this.argumentsList);
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type getRawType() {
            return this.rawType;
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type getOwnerType() {
            return this.ownerType;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.ownerType != null && c.e.a()) {
                sb.append(c.e.c(this.ownerType));
                sb.append('.');
            }
            sb.append(this.rawType.getName());
            sb.append(Typography.less);
            sb.append(d.b.join(Iterables.transform(this.argumentsList, d.a)));
            sb.append(Typography.greater);
            return sb.toString();
        }

        public int hashCode() {
            Type type = this.ownerType;
            return ((type == null ? 0 : type.hashCode()) ^ this.argumentsList.hashCode()) ^ this.rawType.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ParameterizedType)) {
                return false;
            }
            ParameterizedType parameterizedType = (ParameterizedType) obj;
            return getRawType().equals(parameterizedType.getRawType()) && Objects.equal(getOwnerType(), parameterizedType.getOwnerType()) && Arrays.equals(getActualTypeArguments(), parameterizedType.getActualTypeArguments());
        }
    }

    private static <D extends GenericDeclaration> TypeVariable<D> b(D d, String str, Type[] typeArr) {
        return (TypeVariable) Reflection.newProxy(TypeVariable.class, new g(new f(d, str, typeArr)));
    }

    /* compiled from: Types.java */
    /* loaded from: classes2.dex */
    public static final class g implements InvocationHandler {
        private static final ImmutableMap<String, Method> a;
        private final f<?> b;

        static {
            ImmutableMap.Builder builder = ImmutableMap.builder();
            Method[] methods = f.class.getMethods();
            for (Method method : methods) {
                if (method.getDeclaringClass().equals(f.class)) {
                    try {
                        method.setAccessible(true);
                    } catch (AccessControlException unused) {
                    }
                    builder.put(method.getName(), method);
                }
            }
            a = builder.build();
        }

        g(f<?> fVar) {
            this.b = fVar;
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            String name = method.getName();
            Method method2 = a.get(name);
            if (method2 != null) {
                try {
                    return method2.invoke(this.b, objArr);
                } catch (InvocationTargetException e) {
                    throw e.getCause();
                }
            } else {
                throw new UnsupportedOperationException(name);
            }
        }
    }

    /* compiled from: Types.java */
    /* loaded from: classes2.dex */
    public static final class f<D extends GenericDeclaration> {
        private final D a;
        private final String b;
        private final ImmutableList<Type> c;

        f(D d, String str, Type[] typeArr) {
            d.b(typeArr, "bound for type variable");
            this.a = (D) ((GenericDeclaration) Preconditions.checkNotNull(d));
            this.b = (String) Preconditions.checkNotNull(str);
            this.c = ImmutableList.copyOf(typeArr);
        }

        public D a() {
            return this.a;
        }

        public String b() {
            return this.b;
        }

        public String toString() {
            return this.b;
        }

        public int hashCode() {
            return this.a.hashCode() ^ this.b.hashCode();
        }

        public boolean equals(Object obj) {
            if (C0120d.a) {
                if (obj == null || !Proxy.isProxyClass(obj.getClass()) || !(Proxy.getInvocationHandler(obj) instanceof g)) {
                    return false;
                }
                f fVar = ((g) Proxy.getInvocationHandler(obj)).b;
                return this.b.equals(fVar.b()) && this.a.equals(fVar.a()) && this.c.equals(fVar.c);
            } else if (!(obj instanceof TypeVariable)) {
                return false;
            } else {
                TypeVariable typeVariable = (TypeVariable) obj;
                return this.b.equals(typeVariable.getName()) && this.a.equals(typeVariable.getGenericDeclaration());
            }
        }
    }

    /* compiled from: Types.java */
    /* loaded from: classes2.dex */
    public static final class h implements Serializable, WildcardType {
        private static final long serialVersionUID = 0;
        private final ImmutableList<Type> lowerBounds;
        private final ImmutableList<Type> upperBounds;

        public h(Type[] typeArr, Type[] typeArr2) {
            d.b(typeArr, "lower bound for wildcard");
            d.b(typeArr2, "upper bound for wildcard");
            this.lowerBounds = c.e.a(typeArr);
            this.upperBounds = c.e.a(typeArr2);
        }

        @Override // java.lang.reflect.WildcardType
        public Type[] getLowerBounds() {
            return d.b((Collection<Type>) this.lowerBounds);
        }

        @Override // java.lang.reflect.WildcardType
        public Type[] getUpperBounds() {
            return d.b((Collection<Type>) this.upperBounds);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof WildcardType)) {
                return false;
            }
            WildcardType wildcardType = (WildcardType) obj;
            return this.lowerBounds.equals(Arrays.asList(wildcardType.getLowerBounds())) && this.upperBounds.equals(Arrays.asList(wildcardType.getUpperBounds()));
        }

        public int hashCode() {
            return this.lowerBounds.hashCode() ^ this.upperBounds.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("?");
            UnmodifiableIterator<Type> it = this.lowerBounds.iterator();
            while (it.hasNext()) {
                sb.append(" super ");
                sb.append(c.e.c(it.next()));
            }
            for (Type type : d.b((Iterable<Type>) this.upperBounds)) {
                sb.append(" extends ");
                sb.append(c.e.c(type));
            }
            return sb.toString();
        }
    }

    public static Type[] b(Collection<Type> collection) {
        return (Type[]) collection.toArray(new Type[collection.size()]);
    }

    public static Iterable<Type> b(Iterable<Type> iterable) {
        return Iterables.filter(iterable, Predicates.not(Predicates.equalTo(Object.class)));
    }

    public static void b(Type[] typeArr, String str) {
        for (Type type : typeArr) {
            if (type instanceof Class) {
                Class cls = (Class) type;
                Preconditions.checkArgument(!cls.isPrimitive(), "Primitive type '%s' used as %s", cls, str);
            }
        }
    }

    public static Class<?> a(Class<?> cls) {
        return Array.newInstance(cls, 0).getClass();
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier removed */
    /* compiled from: Types.java */
    /* loaded from: classes2.dex */
    public static abstract class c extends Enum<c> {
        static final c e;

        /* JADX INFO: Access modifiers changed from: package-private */
        public abstract Type a(Type type);

        boolean a() {
            return true;
        }

        abstract Type b(Type type);

        static {
            if (AnnotatedElement.class.isAssignableFrom(TypeVariable.class)) {
                if (new b<Map.Entry<String, int[][]>>() { // from class: com.google.common.reflect.d.c.5
                }.a().toString().contains("java.util.Map.java.util.Map")) {
                    e = JAVA8;
                } else {
                    e = JAVA9;
                }
            } else if (new b<int[]>() { // from class: com.google.common.reflect.d.c.6
            }.a() instanceof Class) {
                e = JAVA7;
            } else {
                e = JAVA6;
            }
        }

        final ImmutableList<Type> a(Type[] typeArr) {
            ImmutableList.Builder builder = ImmutableList.builder();
            for (Type type : typeArr) {
                builder.add((ImmutableList.Builder) b(type));
            }
            return builder.build();
        }

        String c(Type type) {
            return d.d(type);
        }
    }

    /* compiled from: Types.java */
    /* renamed from: com.google.common.reflect.d$d */
    /* loaded from: classes2.dex */
    public static final class C0120d<X> {
        static final boolean a = !C0120d.class.getTypeParameters()[0].equals(d.a(C0120d.class, "X", new Type[0]));

        C0120d() {
        }
    }
}
