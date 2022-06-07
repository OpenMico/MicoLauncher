package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes2.dex */
public abstract class Invokable<T, R> extends a implements GenericDeclaration {
    abstract Object a(@NullableDecl Object obj, Object[] objArr) throws InvocationTargetException, IllegalAccessException;

    abstract Type[] a();

    abstract Type[] b();

    abstract Annotation[][] c();

    abstract Type d();

    public abstract boolean isOverridable();

    public abstract boolean isVarArgs();

    @Override // com.google.common.reflect.a
    public /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    @Override // com.google.common.reflect.a
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.google.common.reflect.a
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    <M extends AccessibleObject & Member> Invokable(M m) {
        super(m);
    }

    public static Invokable<?, Object> from(Method method) {
        return new b(method);
    }

    public static <T> Invokable<T, T> from(Constructor<T> constructor) {
        return new a(constructor);
    }

    @CanIgnoreReturnValue
    public final R invoke(@NullableDecl T t, Object... objArr) throws InvocationTargetException, IllegalAccessException {
        return (R) a(t, (Object[]) Preconditions.checkNotNull(objArr));
    }

    public final TypeToken<? extends R> getReturnType() {
        return (TypeToken<? extends R>) TypeToken.of(d());
    }

    public final ImmutableList<Parameter> getParameters() {
        Type[] a2 = a();
        Annotation[][] c = c();
        ImmutableList.Builder builder = ImmutableList.builder();
        for (int i = 0; i < a2.length; i++) {
            builder.add((ImmutableList.Builder) new Parameter(this, i, TypeToken.of(a2[i]), c[i]));
        }
        return builder.build();
    }

    public final ImmutableList<TypeToken<? extends Throwable>> getExceptionTypes() {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Type type : b()) {
            builder.add((ImmutableList.Builder) TypeToken.of(type));
        }
        return builder.build();
    }

    public final <R1 extends R> Invokable<T, R1> returning(Class<R1> cls) {
        return returning(TypeToken.of((Class) cls));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <R1 extends R> Invokable<T, R1> returning(TypeToken<R1> typeToken) {
        if (typeToken.isSupertypeOf(getReturnType())) {
            return this;
        }
        throw new IllegalArgumentException("Invokable is known to return " + getReturnType() + ", not " + typeToken);
    }

    @Override // com.google.common.reflect.a, java.lang.reflect.Member
    public final Class<? super T> getDeclaringClass() {
        return (Class<? super T>) super.getDeclaringClass();
    }

    @Override // com.google.common.reflect.a
    public TypeToken<T> getOwnerType() {
        return TypeToken.of((Class) getDeclaringClass());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class b<T> extends Invokable<T, Object> {
        final Method a;

        public b(Method method) {
            super(method);
            this.a = method;
        }

        @Override // com.google.common.reflect.Invokable
        final Object a(@NullableDecl Object obj, Object[] objArr) throws InvocationTargetException, IllegalAccessException {
            return this.a.invoke(obj, objArr);
        }

        @Override // com.google.common.reflect.Invokable
        public Type d() {
            return this.a.getGenericReturnType();
        }

        @Override // com.google.common.reflect.Invokable
        public Type[] a() {
            return this.a.getGenericParameterTypes();
        }

        @Override // com.google.common.reflect.Invokable
        public Type[] b() {
            return this.a.getGenericExceptionTypes();
        }

        @Override // com.google.common.reflect.Invokable
        final Annotation[][] c() {
            return this.a.getParameterAnnotations();
        }

        @Override // java.lang.reflect.GenericDeclaration
        public final TypeVariable<?>[] getTypeParameters() {
            return this.a.getTypeParameters();
        }

        @Override // com.google.common.reflect.Invokable
        public final boolean isOverridable() {
            return !isFinal() && !isPrivate() && !isStatic() && !Modifier.isFinal(getDeclaringClass().getModifiers());
        }

        @Override // com.google.common.reflect.Invokable
        public final boolean isVarArgs() {
            return this.a.isVarArgs();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class a<T> extends Invokable<T, T> {
        final Constructor<?> a;

        @Override // com.google.common.reflect.Invokable
        public final boolean isOverridable() {
            return false;
        }

        public a(Constructor<?> constructor) {
            super(constructor);
            this.a = constructor;
        }

        @Override // com.google.common.reflect.Invokable
        final Object a(@NullableDecl Object obj, Object[] objArr) throws InvocationTargetException, IllegalAccessException {
            try {
                return this.a.newInstance(objArr);
            } catch (InstantiationException e) {
                throw new RuntimeException(this.a + " failed.", e);
            }
        }

        @Override // com.google.common.reflect.Invokable
        public Type d() {
            Class<? super T> declaringClass = getDeclaringClass();
            TypeVariable<Class<? super T>>[] typeParameters = declaringClass.getTypeParameters();
            return typeParameters.length > 0 ? d.a(declaringClass, typeParameters) : declaringClass;
        }

        @Override // com.google.common.reflect.Invokable
        public Type[] a() {
            Type[] genericParameterTypes = this.a.getGenericParameterTypes();
            if (genericParameterTypes.length > 0 && e()) {
                Class<?>[] parameterTypes = this.a.getParameterTypes();
                if (genericParameterTypes.length == parameterTypes.length && parameterTypes[0] == getDeclaringClass().getEnclosingClass()) {
                    return (Type[]) Arrays.copyOfRange(genericParameterTypes, 1, genericParameterTypes.length);
                }
            }
            return genericParameterTypes;
        }

        @Override // com.google.common.reflect.Invokable
        public Type[] b() {
            return this.a.getGenericExceptionTypes();
        }

        @Override // com.google.common.reflect.Invokable
        final Annotation[][] c() {
            return this.a.getParameterAnnotations();
        }

        @Override // java.lang.reflect.GenericDeclaration
        public final TypeVariable<?>[] getTypeParameters() {
            TypeVariable<Class<? super T>>[] typeParameters = getDeclaringClass().getTypeParameters();
            TypeVariable<Constructor<?>>[] typeParameters2 = this.a.getTypeParameters();
            TypeVariable<?>[] typeVariableArr = new TypeVariable[typeParameters.length + typeParameters2.length];
            System.arraycopy(typeParameters, 0, typeVariableArr, 0, typeParameters.length);
            System.arraycopy(typeParameters2, 0, typeVariableArr, typeParameters.length, typeParameters2.length);
            return typeVariableArr;
        }

        @Override // com.google.common.reflect.Invokable
        public final boolean isVarArgs() {
            return this.a.isVarArgs();
        }

        private boolean e() {
            Class<?> declaringClass = this.a.getDeclaringClass();
            if (declaringClass.getEnclosingConstructor() != null) {
                return true;
            }
            Method enclosingMethod = declaringClass.getEnclosingMethod();
            if (enclosingMethod != null) {
                return !Modifier.isStatic(enclosingMethod.getModifiers());
            }
            return declaringClass.getEnclosingClass() != null && !Modifier.isStatic(declaringClass.getModifiers());
        }
    }
}
