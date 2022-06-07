package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public final class SimpleTimeLimiter implements TimeLimiter {
    private final ExecutorService a;

    private SimpleTimeLimiter(ExecutorService executorService) {
        this.a = (ExecutorService) Preconditions.checkNotNull(executorService);
    }

    public static SimpleTimeLimiter create(ExecutorService executorService) {
        return new SimpleTimeLimiter(executorService);
    }

    @Override // com.google.common.util.concurrent.TimeLimiter
    public <T> T newProxy(final T t, Class<T> cls, final long j, final TimeUnit timeUnit) {
        Preconditions.checkNotNull(t);
        Preconditions.checkNotNull(cls);
        Preconditions.checkNotNull(timeUnit);
        a(j);
        Preconditions.checkArgument(cls.isInterface(), "interfaceType must be an interface type");
        final Set<Method> a = a((Class<?>) cls);
        return (T) a(cls, new InvocationHandler() { // from class: com.google.common.util.concurrent.SimpleTimeLimiter.1
            @Override // java.lang.reflect.InvocationHandler
            public Object invoke(Object obj, final Method method, final Object[] objArr) throws Throwable {
                return SimpleTimeLimiter.this.a(new Callable<Object>() { // from class: com.google.common.util.concurrent.SimpleTimeLimiter.1.1
                    @Override // java.util.concurrent.Callable
                    public Object call() throws Exception {
                        try {
                            return method.invoke(t, objArr);
                        } catch (InvocationTargetException e) {
                            throw SimpleTimeLimiter.b(e, false);
                        }
                    }
                }, j, timeUnit, a.contains(method));
            }
        });
    }

    private static <T> T a(Class<T> cls, InvocationHandler invocationHandler) {
        return cls.cast(Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, invocationHandler));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> T a(Callable<T> callable, long j, TimeUnit timeUnit, boolean z) throws Exception {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeUnit);
        a(j);
        Future<T> submit = this.a.submit(callable);
        try {
            if (!z) {
                return (T) Uninterruptibles.getUninterruptibly(submit, j, timeUnit);
            }
            try {
                return submit.get(j, timeUnit);
            } catch (InterruptedException e) {
                submit.cancel(true);
                throw e;
            }
        } catch (ExecutionException e2) {
            throw b(e2, true);
        } catch (TimeoutException e3) {
            submit.cancel(true);
            throw new UncheckedTimeoutException(e3);
        }
    }

    @Override // com.google.common.util.concurrent.TimeLimiter
    @CanIgnoreReturnValue
    public <T> T callWithTimeout(Callable<T> callable, long j, TimeUnit timeUnit) throws TimeoutException, InterruptedException, ExecutionException {
        Throwable e;
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeUnit);
        a(j);
        Future<T> submit = this.a.submit(callable);
        try {
            return submit.get(j, timeUnit);
        } catch (InterruptedException e2) {
            e = e2;
            submit.cancel(true);
            throw e;
        } catch (ExecutionException e3) {
            a(e3.getCause());
            throw new AssertionError();
        } catch (TimeoutException e4) {
            e = e4;
            submit.cancel(true);
            throw e;
        }
    }

    @Override // com.google.common.util.concurrent.TimeLimiter
    @CanIgnoreReturnValue
    public <T> T callUninterruptiblyWithTimeout(Callable<T> callable, long j, TimeUnit timeUnit) throws TimeoutException, ExecutionException {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeUnit);
        a(j);
        Future<T> submit = this.a.submit(callable);
        try {
            return (T) Uninterruptibles.getUninterruptibly(submit, j, timeUnit);
        } catch (ExecutionException e) {
            a(e.getCause());
            throw new AssertionError();
        } catch (TimeoutException e2) {
            submit.cancel(true);
            throw e2;
        }
    }

    @Override // com.google.common.util.concurrent.TimeLimiter
    public void runWithTimeout(Runnable runnable, long j, TimeUnit timeUnit) throws TimeoutException, InterruptedException {
        Throwable e;
        Preconditions.checkNotNull(runnable);
        Preconditions.checkNotNull(timeUnit);
        a(j);
        Future<?> submit = this.a.submit(runnable);
        try {
            submit.get(j, timeUnit);
        } catch (InterruptedException e2) {
            e = e2;
            submit.cancel(true);
            throw e;
        } catch (ExecutionException e3) {
            b(e3.getCause());
            throw new AssertionError();
        } catch (TimeoutException e4) {
            e = e4;
            submit.cancel(true);
            throw e;
        }
    }

    @Override // com.google.common.util.concurrent.TimeLimiter
    public void runUninterruptiblyWithTimeout(Runnable runnable, long j, TimeUnit timeUnit) throws TimeoutException {
        Preconditions.checkNotNull(runnable);
        Preconditions.checkNotNull(timeUnit);
        a(j);
        Future<?> submit = this.a.submit(runnable);
        try {
            Uninterruptibles.getUninterruptibly(submit, j, timeUnit);
        } catch (ExecutionException e) {
            b(e.getCause());
            throw new AssertionError();
        } catch (TimeoutException e2) {
            submit.cancel(true);
            throw e2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Exception b(Exception exc, boolean z) throws Exception {
        Throwable cause = exc.getCause();
        if (cause != null) {
            if (z) {
                cause.setStackTrace((StackTraceElement[]) ObjectArrays.concat(cause.getStackTrace(), exc.getStackTrace(), StackTraceElement.class));
            }
            if (cause instanceof Exception) {
                throw ((Exception) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw exc;
            }
        } else {
            throw exc;
        }
    }

    private static Set<Method> a(Class<?> cls) {
        HashSet newHashSet = Sets.newHashSet();
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            if (a(method)) {
                newHashSet.add(method);
            }
        }
        return newHashSet;
    }

    private static boolean a(Method method) {
        for (Class<?> cls : method.getExceptionTypes()) {
            if (cls == InterruptedException.class) {
                return true;
            }
        }
        return false;
    }

    private void a(Throwable th) throws ExecutionException {
        if (th instanceof Error) {
            throw new ExecutionError((Error) th);
        } else if (th instanceof RuntimeException) {
            throw new UncheckedExecutionException(th);
        } else {
            throw new ExecutionException(th);
        }
    }

    private void b(Throwable th) {
        if (th instanceof Error) {
            throw new ExecutionError((Error) th);
        }
        throw new UncheckedExecutionException(th);
    }

    private static void a(long j) {
        Preconditions.checkArgument(j > 0, "timeout must be positive: %s", j);
    }
}
