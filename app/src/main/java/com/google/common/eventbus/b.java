package com.google.common.eventbus;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.j2objc.annotations.Weak;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Subscriber.java */
/* loaded from: classes2.dex */
public class b {
    @VisibleForTesting
    final Object a;
    @Weak
    private EventBus b;
    private final Method c;
    private final Executor d;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static b a(EventBus eventBus, Object obj, Method method) {
        return a(method) ? new b(eventBus, obj, method) : new a(eventBus, obj, method);
    }

    private b(EventBus eventBus, Object obj, Method method) {
        this.b = eventBus;
        this.a = Preconditions.checkNotNull(obj);
        this.c = method;
        method.setAccessible(true);
        this.d = eventBus.a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(final Object obj) {
        this.d.execute(new Runnable() { // from class: com.google.common.eventbus.b.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    b.this.b(obj);
                } catch (InvocationTargetException e) {
                    b.this.b.a(e.getCause(), b.this.c(obj));
                }
            }
        });
    }

    @VisibleForTesting
    void b(Object obj) throws InvocationTargetException {
        try {
            this.c.invoke(this.a, Preconditions.checkNotNull(obj));
        } catch (IllegalAccessException e) {
            throw new Error("Method became inaccessible: " + obj, e);
        } catch (IllegalArgumentException e2) {
            throw new Error("Method rejected target/argument: " + obj, e2);
        } catch (InvocationTargetException e3) {
            if (e3.getCause() instanceof Error) {
                throw ((Error) e3.getCause());
            }
            throw e3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SubscriberExceptionContext c(Object obj) {
        return new SubscriberExceptionContext(this.b, obj, this.a, this.c);
    }

    public final int hashCode() {
        return ((this.c.hashCode() + 31) * 31) + System.identityHashCode(this.a);
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        return this.a == bVar.a && this.c.equals(bVar.c);
    }

    private static boolean a(Method method) {
        return method.getAnnotation(AllowConcurrentEvents.class) != null;
    }

    /* compiled from: Subscriber.java */
    @VisibleForTesting
    /* loaded from: classes2.dex */
    static final class a extends b {
        private a(EventBus eventBus, Object obj, Method method) {
            super(eventBus, obj, method);
        }

        @Override // com.google.common.eventbus.b
        void b(Object obj) throws InvocationTargetException {
            synchronized (this) {
                b.super.b(obj);
            }
        }
    }
}
