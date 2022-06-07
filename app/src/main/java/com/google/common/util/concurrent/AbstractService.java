package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.o;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.ForOverride;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public abstract class AbstractService implements Service {
    private static final o.a<Service.Listener> a = new o.a<Service.Listener>() { // from class: com.google.common.util.concurrent.AbstractService.1
        public String toString() {
            return "starting()";
        }

        public void a(Service.Listener listener) {
            listener.starting();
        }
    };
    private static final o.a<Service.Listener> b = new o.a<Service.Listener>() { // from class: com.google.common.util.concurrent.AbstractService.2
        public String toString() {
            return "running()";
        }

        public void a(Service.Listener listener) {
            listener.running();
        }
    };
    private static final o.a<Service.Listener> c = b(Service.State.STARTING);
    private static final o.a<Service.Listener> d = b(Service.State.RUNNING);
    private static final o.a<Service.Listener> e = a(Service.State.NEW);
    private static final o.a<Service.Listener> f = a(Service.State.RUNNING);
    private static final o.a<Service.Listener> g = a(Service.State.STOPPING);
    private final Monitor h = new Monitor();
    private final Monitor.Guard i = new b();
    private final Monitor.Guard j = new c();
    private final Monitor.Guard k = new a();
    private final Monitor.Guard l = new d();
    private final o<Service.Listener> m = new o<>();
    private volatile e n = new e(Service.State.NEW);

    @ForOverride
    protected abstract void doStart();

    @ForOverride
    protected abstract void doStop();

    private static o.a<Service.Listener> a(final Service.State state) {
        return new o.a<Service.Listener>() { // from class: com.google.common.util.concurrent.AbstractService.3
            public void a(Service.Listener listener) {
                listener.terminated(Service.State.this);
            }

            public String toString() {
                return "terminated({from = " + Service.State.this + "})";
            }
        };
    }

    private static o.a<Service.Listener> b(final Service.State state) {
        return new o.a<Service.Listener>() { // from class: com.google.common.util.concurrent.AbstractService.4
            public void a(Service.Listener listener) {
                listener.stopping(Service.State.this);
            }

            public String toString() {
                return "stopping({from = " + Service.State.this + "})";
            }
        };
    }

    /* loaded from: classes2.dex */
    private final class b extends Monitor.Guard {
        b() {
            super(AbstractService.this.h);
        }

        @Override // com.google.common.util.concurrent.Monitor.Guard
        public boolean isSatisfied() {
            return AbstractService.this.state() == Service.State.NEW;
        }
    }

    /* loaded from: classes2.dex */
    private final class c extends Monitor.Guard {
        c() {
            super(AbstractService.this.h);
        }

        @Override // com.google.common.util.concurrent.Monitor.Guard
        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(Service.State.RUNNING) <= 0;
        }
    }

    /* loaded from: classes2.dex */
    private final class a extends Monitor.Guard {
        a() {
            super(AbstractService.this.h);
        }

        @Override // com.google.common.util.concurrent.Monitor.Guard
        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(Service.State.RUNNING) >= 0;
        }
    }

    /* loaded from: classes2.dex */
    private final class d extends Monitor.Guard {
        d() {
            super(AbstractService.this.h);
        }

        @Override // com.google.common.util.concurrent.Monitor.Guard
        public boolean isSatisfied() {
            return AbstractService.this.state().a();
        }
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service startAsync() {
        if (this.h.enterIf(this.i)) {
            try {
                this.n = new e(Service.State.STARTING);
                b();
                doStart();
                return this;
            } finally {
                this.h.leave();
                a();
            }
        } else {
            throw new IllegalStateException("Service " + this + " has already been started");
        }
    }

    @Override // com.google.common.util.concurrent.Service
    @CanIgnoreReturnValue
    public final Service stopAsync() {
        if (this.h.enterIf(this.j)) {
            try {
                Service.State state = state();
                switch (state) {
                    case NEW:
                        this.n = new e(Service.State.TERMINATED);
                        e(Service.State.NEW);
                        break;
                    case STARTING:
                        this.n = new e(Service.State.STARTING, true, null);
                        d(Service.State.STARTING);
                        break;
                    case RUNNING:
                        this.n = new e(Service.State.STOPPING);
                        d(Service.State.RUNNING);
                        doStop();
                        break;
                    case STOPPING:
                    case TERMINATED:
                    case FAILED:
                        throw new AssertionError("isStoppable is incorrectly implemented, saw: " + state);
                    default:
                        throw new AssertionError("Unexpected state: " + state);
                }
            } finally {
                this.h.leave();
                a();
            }
        }
        return this;
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning() {
        this.h.enterWhenUninterruptibly(this.k);
        try {
            c(Service.State.RUNNING);
        } finally {
            this.h.leave();
        }
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning(long j, TimeUnit timeUnit) throws TimeoutException {
        if (this.h.enterWhenUninterruptibly(this.k, j, timeUnit)) {
            try {
                c(Service.State.RUNNING);
            } finally {
                this.h.leave();
            }
        } else {
            throw new TimeoutException("Timed out waiting for " + this + " to reach the RUNNING state.");
        }
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated() {
        this.h.enterWhenUninterruptibly(this.l);
        try {
            c(Service.State.TERMINATED);
        } finally {
            this.h.leave();
        }
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated(long j, TimeUnit timeUnit) throws TimeoutException {
        if (this.h.enterWhenUninterruptibly(this.l, j, timeUnit)) {
            try {
                c(Service.State.TERMINATED);
            } finally {
                this.h.leave();
            }
        } else {
            throw new TimeoutException("Timed out waiting for " + this + " to reach a terminal state. Current state: " + state());
        }
    }

    @GuardedBy("monitor")
    private void c(Service.State state) {
        Service.State state2 = state();
        if (state2 == state) {
            return;
        }
        if (state2 == Service.State.FAILED) {
            throw new IllegalStateException("Expected the service " + this + " to be " + state + ", but the service has FAILED", failureCause());
        }
        throw new IllegalStateException("Expected the service " + this + " to be " + state + ", but was " + state2);
    }

    protected final void notifyStarted() {
        this.h.enter();
        try {
            if (this.n.a == Service.State.STARTING) {
                if (this.n.b) {
                    this.n = new e(Service.State.STOPPING);
                    doStop();
                } else {
                    this.n = new e(Service.State.RUNNING);
                    c();
                }
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("Cannot notifyStarted() when the service is " + this.n.a);
            notifyFailed(illegalStateException);
            throw illegalStateException;
        } finally {
            this.h.leave();
            a();
        }
    }

    protected final void notifyStopped() {
        this.h.enter();
        try {
            Service.State state = this.n.a;
            if (!(state == Service.State.STOPPING || state == Service.State.RUNNING)) {
                IllegalStateException illegalStateException = new IllegalStateException("Cannot notifyStopped() when the service is " + state);
                notifyFailed(illegalStateException);
                throw illegalStateException;
            }
            this.n = new e(Service.State.TERMINATED);
            e(state);
        } finally {
            this.h.leave();
            a();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    protected final void notifyFailed(Throwable th) {
        Preconditions.checkNotNull(th);
        this.h.enter();
        try {
            Service.State state = state();
            switch (state) {
                case NEW:
                case TERMINATED:
                    throw new IllegalStateException("Failed while in state:" + state, th);
                case STARTING:
                case RUNNING:
                case STOPPING:
                    this.n = new e(Service.State.FAILED, false, th);
                    a(state, th);
                    break;
                case FAILED:
                    break;
                default:
                    throw new AssertionError("Unexpected state: " + state);
            }
        } finally {
            this.h.leave();
            a();
        }
    }

    @Override // com.google.common.util.concurrent.Service
    public final boolean isRunning() {
        return state() == Service.State.RUNNING;
    }

    @Override // com.google.common.util.concurrent.Service
    public final Service.State state() {
        return this.n.a();
    }

    @Override // com.google.common.util.concurrent.Service
    public final Throwable failureCause() {
        return this.n.b();
    }

    @Override // com.google.common.util.concurrent.Service
    public final void addListener(Service.Listener listener, Executor executor) {
        this.m.a((o<Service.Listener>) listener, executor);
    }

    public String toString() {
        return getClass().getSimpleName() + " [" + state() + "]";
    }

    private void a() {
        if (!this.h.isOccupiedByCurrentThread()) {
            this.m.a();
        }
    }

    private void b() {
        this.m.a(a);
    }

    private void c() {
        this.m.a(b);
    }

    private void d(Service.State state) {
        if (state == Service.State.STARTING) {
            this.m.a(c);
        } else if (state == Service.State.RUNNING) {
            this.m.a(d);
        } else {
            throw new AssertionError();
        }
    }

    private void e(Service.State state) {
        int i = AnonymousClass6.a[state.ordinal()];
        if (i != 1) {
            switch (i) {
                case 3:
                    this.m.a(f);
                    return;
                case 4:
                    this.m.a(g);
                    return;
                default:
                    throw new AssertionError();
            }
        } else {
            this.m.a(e);
        }
    }

    private void a(final Service.State state, final Throwable th) {
        this.m.a(new o.a<Service.Listener>() { // from class: com.google.common.util.concurrent.AbstractService.5
            public void a(Service.Listener listener) {
                listener.failed(state, th);
            }

            public String toString() {
                return "failed({from = " + state + ", cause = " + th + "})";
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class e {
        final Service.State a;
        final boolean b;
        @NullableDecl
        final Throwable c;

        e(Service.State state) {
            this(state, false, null);
        }

        e(Service.State state, boolean z, @NullableDecl Throwable th) {
            boolean z2 = false;
            Preconditions.checkArgument(!z || state == Service.State.STARTING, "shutdownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.", state);
            Preconditions.checkArgument(!((th != null) ^ (state == Service.State.FAILED)) ? true : z2, "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.", state, th);
            this.a = state;
            this.b = z;
            this.c = th;
        }

        Service.State a() {
            if (!this.b || this.a != Service.State.STARTING) {
                return this.a;
            }
            return Service.State.STOPPING;
        }

        Throwable b() {
            Preconditions.checkState(this.a == Service.State.FAILED, "failureCause() is only valid if the service has failed, service is %s", this.a);
            return this.c;
        }
    }
}
