package com.milink.base.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.milink.base.utils.Sugar;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class Proxies<T> {
    private final List<Class<?>> a = new ArrayList();
    private Executor b;
    private Advice c;

    /* loaded from: classes2.dex */
    public interface ProcessJoinPoint<T> {
        T processed() throws Throwable;
    }

    private Proxies(@NonNull Class<T> cls) {
        c(cls);
    }

    public static <T> Proxies<T> of(@NonNull Class<T> cls) {
        return new Proxies<>(cls);
    }

    @Nullable
    private static Object a(Class<?> cls) {
        if (cls == Boolean.TYPE || cls == Boolean.class) {
            return false;
        }
        if (cls == Integer.TYPE || cls == Integer.class) {
            return 0;
        }
        if (cls == Long.TYPE || cls == Long.class) {
            return 0L;
        }
        if (cls == Short.TYPE || cls == Short.class) {
            return 0;
        }
        if (cls == Character.TYPE || cls == Character.class) {
            return 0;
        }
        if (cls == Byte.TYPE || cls == Byte.class) {
            return 0;
        }
        if (cls == Float.TYPE || cls == Float.class) {
            return Float.valueOf(0.0f);
        }
        if (cls == Double.TYPE || cls == Double.class) {
            return Double.valueOf(0.0d);
        }
        return null;
    }

    private static boolean b(Class<?> cls) {
        return cls != null && cls.isInterface();
    }

    private void c(@NonNull Class<?> cls) {
        if (b(cls)) {
            this.a.add(cls);
            return;
        }
        throw new IllegalArgumentException("only support interface, but " + cls);
    }

    public Proxies<T> and(@NonNull Class<?> cls) {
        c(cls);
        return this;
    }

    public Proxies<T> setAdvice(@Nullable Advice advice) {
        this.c = advice;
        return this;
    }

    public Proxies<T> setInvokeExecutor(Executor executor) {
        this.b = executor;
        return this;
    }

    public T by(final T t) {
        return (T) Proxy.newProxyInstance(t != null ? t.getClass().getClassLoader() : this.a.get(0).getClassLoader(), (Class[]) this.a.toArray(new Class[0]), new InvocationHandler() { // from class: com.milink.base.utils.-$$Lambda$Proxies$6-jFwkPVXdTDkruSYueY8OqcZnU
            @Override // java.lang.reflect.InvocationHandler
            public final Object invoke(Object obj, Method method, Object[] objArr) {
                Object a2;
                a2 = Proxies.this.a(t, obj, method, objArr);
                return a2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object a(final Object obj, Object obj2, final Method method, final Object[] objArr) throws Throwable {
        if (obj == null) {
            return a(method.getReturnType());
        }
        Executor executor = this.b;
        if (executor == null) {
            return a(method, objArr, obj);
        }
        final ObservableFutureImpl observableFutureImpl = new ObservableFutureImpl();
        executor.execute(new Runnable() { // from class: com.milink.base.utils.-$$Lambda$Proxies$R0Rk_Y-UmME5UsG1Cf46lruX9EA
            @Override // java.lang.Runnable
            public final void run() {
                Proxies.this.a(method, objArr, obj, observableFutureImpl);
            }
        });
        Object obj3 = method.getReturnType() != Void.TYPE ? observableFutureImpl.get() : null;
        if (!(obj3 instanceof ProxyExecuteException)) {
            return obj3;
        }
        throw ((Throwable) Objects.requireNonNull(((ProxyExecuteException) obj3).getCause()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void a(Method method, Object[] objArr, Object obj, ObservableFutureImpl observableFutureImpl) {
        Object e;
        try {
            e = a(method, objArr, obj);
        } catch (ProxyExecuteException e2) {
            e = e2;
        }
        observableFutureImpl.setData(e);
    }

    private Object a(final Method method, final Object[] objArr, final Object obj) {
        Sugar.eat(new Sugar.FuncV() { // from class: com.milink.base.utils.-$$Lambda$Proxies$70XiO1tjK4JGYy4NTRVJbQ2WzxI
            @Override // com.milink.base.utils.Sugar.FuncV
            public final void apply() {
                Proxies.this.b();
            }
        });
        try {
            a aVar = new a(new ProcessJoinPoint() { // from class: com.milink.base.utils.-$$Lambda$Proxies$RB8fc4EfWZHb44Z0SeTqudWMrfY
                @Override // com.milink.base.utils.Proxies.ProcessJoinPoint
                public final Object processed() {
                    Object invoke;
                    invoke = method.invoke(obj, objArr);
                    return invoke;
                }
            });
            if (this.c == null) {
                return aVar.processed();
            }
            Object around = this.c.around(aVar);
            if (around != Advice.NO_OP) {
                return around;
            }
            return aVar.a ? a(method.getReturnType()) : aVar.processed();
        } finally {
            Sugar.eat(new Sugar.FuncV() { // from class: com.milink.base.utils.-$$Lambda$Proxies$WKp9OARladhtVhACs64JZmwpY08
                @Override // com.milink.base.utils.Sugar.FuncV
                public final void apply() {
                    Proxies.this.a();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() throws Exception {
        Advice advice = this.c;
        if (advice != null) {
            advice.before();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a() throws Exception {
        Advice advice = this.c;
        if (advice != null) {
            advice.after();
        }
    }

    /* loaded from: classes2.dex */
    public interface Advice {
        public static final Object NO_OP = new Object();

        default void after() {
        }

        @Nullable
        default Object afterCatch(@NonNull Throwable th) {
            return th;
        }

        default void before() {
        }

        @Nullable
        default Object around(@NonNull ProcessJoinPoint<?> processJoinPoint) {
            return NO_OP;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class a<T> implements ProcessJoinPoint<T> {
        boolean a;
        private final ProcessJoinPoint<T> b;

        a(ProcessJoinPoint<T> processJoinPoint) {
            this.b = (ProcessJoinPoint) Objects.requireNonNull(processJoinPoint);
        }

        @Override // com.milink.base.utils.Proxies.ProcessJoinPoint
        public final T processed() throws Throwable {
            try {
                return this.b.processed();
            } finally {
                this.a = true;
            }
        }
    }

    /* loaded from: classes2.dex */
    public static class ProxyExecuteException extends RuntimeException {
        ProxyExecuteException(String str, @NonNull Throwable th) {
            super(str, (Throwable) Objects.requireNonNull(th));
        }
    }
}
