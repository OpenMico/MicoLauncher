package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Service;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.commons.lang3.StringUtils;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public abstract class AbstractIdleService implements Service {
    private final Supplier<String> a = new b();
    private final Service b = new a();

    protected abstract void shutDown() throws Exception;

    protected abstract void startUp() throws Exception;

    /* loaded from: classes2.dex */
    private final class b implements Supplier<String> {
        private b() {
        }

        /* renamed from: a */
        public String get() {
            return AbstractIdleService.this.serviceName() + StringUtils.SPACE + AbstractIdleService.this.state();
        }
    }

    /* loaded from: classes2.dex */
    private final class a extends AbstractService {
        private a() {
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected final void doStart() {
            MoreExecutors.a(AbstractIdleService.this.executor(), AbstractIdleService.this.a).execute(new Runnable() { // from class: com.google.common.util.concurrent.AbstractIdleService.a.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AbstractIdleService.this.startUp();
                        a.this.notifyStarted();
                    } catch (Throwable th) {
                        a.this.notifyFailed(th);
                    }
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected final void doStop() {
            MoreExecutors.a(AbstractIdleService.this.executor(), AbstractIdleService.this.a).execute(new Runnable() { // from class: com.google.common.util.concurrent.AbstractIdleService.a.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AbstractIdleService.this.shutDown();
                        a.this.notifyStopped();
                    } catch (Throwable th) {
                        a.this.notifyFailed(th);
                    }
                }
            });
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public String toString() {
            return AbstractIdleService.this.toString();
        }
    }

    protected AbstractIdleService() {
    }

    protected Executor executor() {
        return new Executor() { // from class: com.google.common.util.concurrent.AbstractIdleService.1
            @Override // java.util.concurrent.Executor
            public void execute(Runnable runnable) {
                MoreExecutors.a((String) AbstractIdleService.this.a.get(), runnable).start();
            }
        };
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

    protected String serviceName() {
        return getClass().getSimpleName();
    }
}
