package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FuturesGetChecked.java */
@GwtIncompatible
/* loaded from: classes2.dex */
public final class j {
    private static final Ordering<Constructor<?>> a = Ordering.natural().onResultOf(new Function<Constructor<?>, Boolean>() { // from class: com.google.common.util.concurrent.j.1
        /* renamed from: a */
        public Boolean apply(Constructor<?> constructor) {
            return Boolean.valueOf(Arrays.asList(constructor.getParameterTypes()).contains(String.class));
        }
    }).reverse();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: FuturesGetChecked.java */
    @VisibleForTesting
    /* loaded from: classes2.dex */
    public interface a {
        void a(Class<? extends Exception> cls);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @CanIgnoreReturnValue
    public static <V, X extends Exception> V a(Future<V> future, Class<X> cls) throws Exception {
        return (V) a(b(), future, cls);
    }

    @VisibleForTesting
    @CanIgnoreReturnValue
    static <V, X extends Exception> V a(a aVar, Future<V> future, Class<X> cls) throws Exception {
        aVar.a(cls);
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw a(cls, e);
        } catch (ExecutionException e2) {
            a(e2.getCause(), cls);
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @CanIgnoreReturnValue
    public static <V, X extends Exception> V a(Future<V> future, Class<X> cls, long j, TimeUnit timeUnit) throws Exception {
        b().a(cls);
        try {
            return future.get(j, timeUnit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw a(cls, e);
        } catch (ExecutionException e2) {
            a(e2.getCause(), cls);
            throw new AssertionError();
        } catch (TimeoutException e3) {
            throw a(cls, e3);
        }
    }

    private static a b() {
        return b.b;
    }

    @VisibleForTesting
    static a a() {
        return b.a.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: FuturesGetChecked.java */
    @VisibleForTesting
    /* loaded from: classes2.dex */
    public static class b {
        static final String a = b.class.getName() + "$ClassValueValidator";
        static final a b = a();

        b() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: FuturesGetChecked.java */
        /* loaded from: classes2.dex */
        public enum a implements a {
            INSTANCE;
            
            private static final Set<WeakReference<Class<? extends Exception>>> b = new CopyOnWriteArraySet();

            @Override // com.google.common.util.concurrent.j.a
            public void a(Class<? extends Exception> cls) {
                for (WeakReference<Class<? extends Exception>> weakReference : b) {
                    if (cls.equals(weakReference.get())) {
                        return;
                    }
                }
                j.b(cls);
                if (b.size() > 1000) {
                    b.clear();
                }
                b.add(new WeakReference<>(cls));
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        static a a() {
            try {
                return (a) Class.forName(a).getEnumConstants()[0];
            } catch (Throwable unused) {
                return j.a();
            }
        }
    }

    private static <X extends Exception> void a(Throwable th, Class<X> cls) throws Exception {
        if (th instanceof Error) {
            throw new ExecutionError((Error) th);
        } else if (th instanceof RuntimeException) {
            throw new UncheckedExecutionException(th);
        } else {
            throw a(cls, th);
        }
    }

    private static boolean c(Class<? extends Exception> cls) {
        try {
            a(cls, new Exception());
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    private static <X extends Exception> X a(Class<X> cls, Throwable th) {
        for (Constructor constructor : a(Arrays.asList(cls.getConstructors()))) {
            X x = (X) ((Exception) a(constructor, th));
            if (x != null) {
                if (x.getCause() == null) {
                    x.initCause(th);
                }
                return x;
            }
        }
        throw new IllegalArgumentException("No appropriate constructor for exception of type " + cls + " in response to chained exception", th);
    }

    private static <X extends Exception> List<Constructor<X>> a(List<Constructor<X>> list) {
        return (List<Constructor<X>>) a.sortedCopy(list);
    }

    @NullableDecl
    private static <X> X a(Constructor<X> constructor, Throwable th) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] objArr = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> cls = parameterTypes[i];
            if (cls.equals(String.class)) {
                objArr[i] = th.toString();
            } else if (!cls.equals(Throwable.class)) {
                return null;
            } else {
                objArr[i] = th;
            }
        }
        try {
            return constructor.newInstance(objArr);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException unused) {
            return null;
        }
    }

    @VisibleForTesting
    static boolean a(Class<? extends Exception> cls) {
        return !RuntimeException.class.isAssignableFrom(cls);
    }

    @VisibleForTesting
    static void b(Class<? extends Exception> cls) {
        Preconditions.checkArgument(a(cls), "Futures.getChecked exception type (%s) must not be a RuntimeException", cls);
        Preconditions.checkArgument(c(cls), "Futures.getChecked exception type (%s) must be an accessible class with an accessible constructor whose parameters (if any) must be of type String and/or Throwable", cls);
    }
}
