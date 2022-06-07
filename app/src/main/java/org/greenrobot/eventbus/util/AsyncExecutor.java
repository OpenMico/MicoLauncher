package org.greenrobot.eventbus.util;

import java.lang.reflect.Constructor;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes5.dex */
public class AsyncExecutor {
    private final Executor a;
    private final Constructor<?> b;
    private final EventBus c;
    private final Object d;

    /* loaded from: classes5.dex */
    public interface RunnableEx {
        void run() throws Exception;
    }

    /* loaded from: classes5.dex */
    public static class Builder {
        private Executor a;
        private Class<?> b;
        private EventBus c;

        private Builder() {
        }

        public Builder threadPool(Executor executor) {
            this.a = executor;
            return this;
        }

        public Builder failureEventType(Class<?> cls) {
            this.b = cls;
            return this;
        }

        public Builder eventBus(EventBus eventBus) {
            this.c = eventBus;
            return this;
        }

        public AsyncExecutor build() {
            return buildForScope(null);
        }

        public AsyncExecutor buildForScope(Object obj) {
            if (this.c == null) {
                this.c = EventBus.getDefault();
            }
            if (this.a == null) {
                this.a = Executors.newCachedThreadPool();
            }
            if (this.b == null) {
                this.b = ThrowableFailureEvent.class;
            }
            return new AsyncExecutor(this.a, this.c, this.b, obj);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static AsyncExecutor create() {
        return new Builder().build();
    }

    private AsyncExecutor(Executor executor, EventBus eventBus, Class<?> cls, Object obj) {
        this.a = executor;
        this.c = eventBus;
        this.d = obj;
        try {
            this.b = cls.getConstructor(Throwable.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Failure event class must have a constructor with one parameter of type Throwable", e);
        }
    }

    public void execute(final RunnableEx runnableEx) {
        this.a.execute(new Runnable() { // from class: org.greenrobot.eventbus.util.AsyncExecutor.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    runnableEx.run();
                } catch (Exception e) {
                    try {
                        Object newInstance = AsyncExecutor.this.b.newInstance(e);
                        if (newInstance instanceof HasExecutionScope) {
                            ((HasExecutionScope) newInstance).setExecutionScope(AsyncExecutor.this.d);
                        }
                        AsyncExecutor.this.c.post(newInstance);
                    } catch (Exception e2) {
                        AsyncExecutor.this.c.getLogger().log(Level.SEVERE, "Original exception:", e);
                        throw new RuntimeException("Could not create failure event", e2);
                    }
                }
            }
        });
    }
}
