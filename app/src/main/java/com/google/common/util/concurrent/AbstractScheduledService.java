package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Service;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public abstract class AbstractScheduledService implements Service {
    private static final Logger a = Logger.getLogger(AbstractScheduledService.class.getName());
    private final AbstractService b = new b();

    protected abstract void runOneIteration() throws Exception;

    protected abstract Scheduler scheduler();

    protected void shutDown() throws Exception {
    }

    protected void startUp() throws Exception {
    }

    static /* synthetic */ Logger a() {
        return a;
    }

    /* loaded from: classes2.dex */
    public static abstract class Scheduler {
        abstract Future<?> a(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable);

        public static Scheduler newFixedDelaySchedule(final long j, final long j2, final TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            Preconditions.checkArgument(j2 > 0, "delay must be > 0, found %s", j2);
            return new Scheduler() { // from class: com.google.common.util.concurrent.AbstractScheduledService.Scheduler.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // com.google.common.util.concurrent.AbstractScheduledService.Scheduler
                public Future<?> a(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                    return scheduledExecutorService.scheduleWithFixedDelay(runnable, j, j2, timeUnit);
                }
            };
        }

        public static Scheduler newFixedRateSchedule(final long j, final long j2, final TimeUnit timeUnit) {
            Preconditions.checkNotNull(timeUnit);
            Preconditions.checkArgument(j2 > 0, "period must be > 0, found %s", j2);
            return new Scheduler() { // from class: com.google.common.util.concurrent.AbstractScheduledService.Scheduler.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // com.google.common.util.concurrent.AbstractScheduledService.Scheduler
                public Future<?> a(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable) {
                    return scheduledExecutorService.scheduleAtFixedRate(runnable, j, j2, timeUnit);
                }
            };
        }

        private Scheduler() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class b extends AbstractService {
        @MonotonicNonNullDecl
        private volatile Future<?> b;
        @MonotonicNonNullDecl
        private volatile ScheduledExecutorService c;
        private final ReentrantLock d;
        private final Runnable e;

        private b() {
            this.d = new ReentrantLock();
            this.e = new a();
        }

        static /* synthetic */ Future b(b bVar) {
            return bVar.b;
        }

        /* loaded from: classes2.dex */
        class a implements Runnable {
            a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                b.this.d.lock();
                try {
                    if (!b.this.b.isCancelled()) {
                        AbstractScheduledService.this.runOneIteration();
                    }
                } finally {
                    b.this.d.unlock();
                }
            }
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected final void doStart() {
            this.c = MoreExecutors.a(AbstractScheduledService.this.executor(), new Supplier<String>() { // from class: com.google.common.util.concurrent.AbstractScheduledService.b.1
                /* renamed from: a */
                public String get() {
                    return AbstractScheduledService.this.serviceName() + StringUtils.SPACE + b.this.state();
                }
            });
            this.c.execute(new Runnable() { // from class: com.google.common.util.concurrent.AbstractScheduledService.b.2
                @Override // java.lang.Runnable
                public void run() {
                    b.this.d.lock();
                    try {
                        AbstractScheduledService.this.startUp();
                        b.this.b = AbstractScheduledService.this.scheduler().a(AbstractScheduledService.this.b, b.this.c, b.this.e);
                        b.this.notifyStarted();
                    } finally {
                        b.this.d.unlock();
                    }
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected final void doStop() {
            this.b.cancel(false);
            this.c.execute(new Runnable() { // from class: com.google.common.util.concurrent.AbstractScheduledService.b.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        b.this.d.lock();
                        if (b.this.state() != Service.State.STOPPING) {
                            b.this.d.unlock();
                            return;
                        }
                        AbstractScheduledService.this.shutDown();
                        b.this.d.unlock();
                        b.this.notifyStopped();
                    } catch (Throwable th) {
                        b.this.notifyFailed(th);
                    }
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public String toString() {
            return AbstractScheduledService.this.toString();
        }
    }

    protected AbstractScheduledService() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class a implements ThreadFactory {
        a() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return MoreExecutors.a(AbstractScheduledService.this.serviceName(), runnable);
        }
    }

    protected ScheduledExecutorService executor() {
        final ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(new a());
        addListener(new Service.Listener() { // from class: com.google.common.util.concurrent.AbstractScheduledService.1
            @Override // com.google.common.util.concurrent.Service.Listener
            public void terminated(Service.State state) {
                newSingleThreadScheduledExecutor.shutdown();
            }

            @Override // com.google.common.util.concurrent.Service.Listener
            public void failed(Service.State state, Throwable th) {
                newSingleThreadScheduledExecutor.shutdown();
            }
        }, MoreExecutors.directExecutor());
        return newSingleThreadScheduledExecutor;
    }

    protected String serviceName() {
        return getClass().getSimpleName();
    }

    public String toString() {
        return serviceName() + " [" + state() + "]";
    }

    @Override // com.google.common.util.concurrent.Service
    public final boolean isRunning() {
        return this.b.isRunning();
    }

    @Override // com.google.common.util.concurrent.Service
    public final Service.State state() {
        return this.b.state();
    }

    @Override // com.google.common.util.concurrent.Service
    public final void addListener(Service.Listener listener, Executor executor) {
        this.b.addListener(listener, executor);
    }

    @Override // com.google.common.util.concurrent.Service
    public final Throwable failureCause() {
        return this.b.failureCause();
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service startAsync() {
        this.b.startAsync();
        return this;
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service stopAsync() {
        this.b.stopAsync();
        return this;
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning() {
        this.b.awaitRunning();
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning(long j, TimeUnit timeUnit) throws TimeoutException {
        this.b.awaitRunning(j, timeUnit);
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated() {
        this.b.awaitTerminated();
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated(long j, TimeUnit timeUnit) throws TimeoutException {
        this.b.awaitTerminated(j, timeUnit);
    }

    @Beta
    /* loaded from: classes2.dex */
    public static abstract class CustomScheduler extends Scheduler {
        protected abstract Schedule getNextSchedule() throws Exception;

        public CustomScheduler() {
            super();
        }

        @Beta
        /* loaded from: classes2.dex */
        protected static final class Schedule {
            private final long a;
            private final TimeUnit b;

            public Schedule(long j, TimeUnit timeUnit) {
                this.a = j;
                this.b = (TimeUnit) Preconditions.checkNotNull(timeUnit);
            }
        }
    }
}
