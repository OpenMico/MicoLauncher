package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.o;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public final class ServiceManager {
    private static final Logger a = Logger.getLogger(ServiceManager.class.getName());
    private static final o.a<Listener> b = new o.a<Listener>() { // from class: com.google.common.util.concurrent.ServiceManager.1
        public String toString() {
            return "healthy()";
        }

        public void a(Listener listener) {
            listener.healthy();
        }
    };
    private static final o.a<Listener> c = new o.a<Listener>() { // from class: com.google.common.util.concurrent.ServiceManager.2
        public String toString() {
            return "stopped()";
        }

        public void a(Listener listener) {
            listener.stopped();
        }
    };
    private final d d;
    private final ImmutableList<Service> e;

    @Beta
    /* loaded from: classes2.dex */
    public static abstract class Listener {
        public void failure(Service service) {
        }

        public void healthy() {
        }

        public void stopped() {
        }
    }

    public ServiceManager(Iterable<? extends Service> iterable) {
        ImmutableList<Service> copyOf = ImmutableList.copyOf(iterable);
        if (copyOf.isEmpty()) {
            a.log(Level.WARNING, "ServiceManager configured with no services.  Is your application configured properly?", (Throwable) new a());
            copyOf = ImmutableList.of(new b());
        }
        this.d = new d(copyOf);
        this.e = copyOf;
        WeakReference weakReference = new WeakReference(this.d);
        UnmodifiableIterator<Service> it = copyOf.iterator();
        while (it.hasNext()) {
            Service next = it.next();
            next.addListener(new c(next, weakReference), MoreExecutors.directExecutor());
            Preconditions.checkArgument(next.state() == Service.State.NEW, "Can only manage NEW services, %s", next);
        }
        this.d.a();
    }

    public void addListener(Listener listener, Executor executor) {
        this.d.a(listener, executor);
    }

    public void addListener(Listener listener) {
        this.d.a(listener, MoreExecutors.directExecutor());
    }

    @CanIgnoreReturnValue
    public ServiceManager startAsync() {
        UnmodifiableIterator<Service> it = this.e.iterator();
        while (it.hasNext()) {
            Service next = it.next();
            Service.State state = next.state();
            Preconditions.checkState(state == Service.State.NEW, "Service %s is %s, cannot start it.", next, state);
        }
        UnmodifiableIterator<Service> it2 = this.e.iterator();
        while (it2.hasNext()) {
            Service next2 = it2.next();
            try {
                this.d.a(next2);
                next2.startAsync();
            } catch (IllegalStateException e) {
                Logger logger = a;
                Level level = Level.WARNING;
                logger.log(level, "Unable to start Service " + next2, (Throwable) e);
            }
        }
        return this;
    }

    public void awaitHealthy() {
        this.d.b();
    }

    public void awaitHealthy(long j, TimeUnit timeUnit) throws TimeoutException {
        this.d.a(j, timeUnit);
    }

    @CanIgnoreReturnValue
    public ServiceManager stopAsync() {
        UnmodifiableIterator<Service> it = this.e.iterator();
        while (it.hasNext()) {
            it.next().stopAsync();
        }
        return this;
    }

    public void awaitStopped() {
        this.d.c();
    }

    public void awaitStopped(long j, TimeUnit timeUnit) throws TimeoutException {
        this.d.b(j, timeUnit);
    }

    public boolean isHealthy() {
        UnmodifiableIterator<Service> it = this.e.iterator();
        while (it.hasNext()) {
            if (!it.next().isRunning()) {
                return false;
            }
        }
        return true;
    }

    public ImmutableMultimap<Service.State, Service> servicesByState() {
        return this.d.d();
    }

    public ImmutableMap<Service, Long> startupTimes() {
        return this.d.e();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Class<?>) ServiceManager.class).add("services", Collections2.filter(this.e, Predicates.not(Predicates.instanceOf(b.class)))).toString();
    }

    /* loaded from: classes2.dex */
    private static final class d {
        @GuardedBy("monitor")
        boolean e;
        @GuardedBy("monitor")
        boolean f;
        final int g;
        final Monitor a = new Monitor();
        @GuardedBy("monitor")
        final SetMultimap<Service.State, Service> b = MultimapBuilder.enumKeys(Service.State.class).linkedHashSetValues().build();
        @GuardedBy("monitor")
        final Multiset<Service.State> c = this.b.keys();
        @GuardedBy("monitor")
        final Map<Service, Stopwatch> d = Maps.newIdentityHashMap();
        final Monitor.Guard h = new a();
        final Monitor.Guard i = new b();
        final o<Listener> j = new o<>();

        /* loaded from: classes2.dex */
        final class a extends Monitor.Guard {
            a() {
                super(d.this.a);
            }

            @Override // com.google.common.util.concurrent.Monitor.Guard
            @GuardedBy("ServiceManagerState.this.monitor")
            public boolean isSatisfied() {
                return d.this.c.count(Service.State.RUNNING) == d.this.g || d.this.c.contains(Service.State.STOPPING) || d.this.c.contains(Service.State.TERMINATED) || d.this.c.contains(Service.State.FAILED);
            }
        }

        /* loaded from: classes2.dex */
        final class b extends Monitor.Guard {
            b() {
                super(d.this.a);
            }

            @Override // com.google.common.util.concurrent.Monitor.Guard
            @GuardedBy("ServiceManagerState.this.monitor")
            public boolean isSatisfied() {
                return d.this.c.count(Service.State.TERMINATED) + d.this.c.count(Service.State.FAILED) == d.this.g;
            }
        }

        d(ImmutableCollection<Service> immutableCollection) {
            this.g = immutableCollection.size();
            this.b.putAll(Service.State.NEW, immutableCollection);
        }

        void a(Service service) {
            this.a.enter();
            try {
                if (this.d.get(service) == null) {
                    this.d.put(service, Stopwatch.createStarted());
                }
            } finally {
                this.a.leave();
            }
        }

        void a() {
            this.a.enter();
            try {
                if (!this.f) {
                    this.e = true;
                    return;
                }
                ArrayList newArrayList = Lists.newArrayList();
                UnmodifiableIterator<Service> it = d().values().iterator();
                while (it.hasNext()) {
                    Service next = it.next();
                    if (next.state() != Service.State.NEW) {
                        newArrayList.add(next);
                    }
                }
                throw new IllegalArgumentException("Services started transitioning asynchronously before the ServiceManager was constructed: " + newArrayList);
            } finally {
                this.a.leave();
            }
        }

        void a(Listener listener, Executor executor) {
            this.j.a((o<Listener>) listener, executor);
        }

        void b() {
            this.a.enterWhenUninterruptibly(this.h);
            try {
                i();
            } finally {
                this.a.leave();
            }
        }

        void a(long j, TimeUnit timeUnit) throws TimeoutException {
            this.a.enter();
            try {
                if (this.a.waitForUninterruptibly(this.h, j, timeUnit)) {
                    i();
                    return;
                }
                throw new TimeoutException("Timeout waiting for the services to become healthy. The following services have not started: " + Multimaps.filterKeys((SetMultimap) this.b, Predicates.in(ImmutableSet.of(Service.State.NEW, Service.State.STARTING))));
            } finally {
                this.a.leave();
            }
        }

        void c() {
            this.a.enterWhenUninterruptibly(this.i);
            this.a.leave();
        }

        void b(long j, TimeUnit timeUnit) throws TimeoutException {
            this.a.enter();
            try {
                if (!this.a.waitForUninterruptibly(this.i, j, timeUnit)) {
                    throw new TimeoutException("Timeout waiting for the services to stop. The following services have not stopped: " + Multimaps.filterKeys((SetMultimap) this.b, Predicates.not(Predicates.in(EnumSet.of(Service.State.TERMINATED, Service.State.FAILED)))));
                }
            } finally {
                this.a.leave();
            }
        }

        /* JADX WARN: Finally extract failed */
        ImmutableMultimap<Service.State, Service> d() {
            ImmutableSetMultimap.Builder builder = ImmutableSetMultimap.builder();
            this.a.enter();
            try {
                for (Map.Entry<Service.State, Service> entry : this.b.entries()) {
                    if (!(entry.getValue() instanceof b)) {
                        builder.put((Map.Entry) entry);
                    }
                }
                this.a.leave();
                return builder.build();
            } catch (Throwable th) {
                this.a.leave();
                throw th;
            }
        }

        /* JADX WARN: Finally extract failed */
        ImmutableMap<Service, Long> e() {
            this.a.enter();
            try {
                ArrayList newArrayListWithCapacity = Lists.newArrayListWithCapacity(this.d.size());
                for (Map.Entry<Service, Stopwatch> entry : this.d.entrySet()) {
                    Service key = entry.getKey();
                    Stopwatch value = entry.getValue();
                    if (!value.isRunning() && !(key instanceof b)) {
                        newArrayListWithCapacity.add(Maps.immutableEntry(key, Long.valueOf(value.elapsed(TimeUnit.MILLISECONDS))));
                    }
                }
                this.a.leave();
                Collections.sort(newArrayListWithCapacity, Ordering.natural().onResultOf(new Function<Map.Entry<Service, Long>, Long>() { // from class: com.google.common.util.concurrent.ServiceManager.d.1
                    /* renamed from: a */
                    public Long apply(Map.Entry<Service, Long> entry2) {
                        return entry2.getValue();
                    }
                }));
                return ImmutableMap.copyOf(newArrayListWithCapacity);
            } catch (Throwable th) {
                this.a.leave();
                throw th;
            }
        }

        void a(Service service, Service.State state, Service.State state2) {
            Preconditions.checkNotNull(service);
            Preconditions.checkArgument(state != state2);
            this.a.enter();
            try {
                this.f = true;
                if (this.e) {
                    Preconditions.checkState(this.b.remove(state, service), "Service %s not at the expected location in the state map %s", service, state);
                    Preconditions.checkState(this.b.put(state2, service), "Service %s in the state map unexpectedly at %s", service, state2);
                    Stopwatch stopwatch = this.d.get(service);
                    if (stopwatch == null) {
                        stopwatch = Stopwatch.createStarted();
                        this.d.put(service, stopwatch);
                    }
                    if (state2.compareTo(Service.State.RUNNING) >= 0 && stopwatch.isRunning()) {
                        stopwatch.stop();
                        if (!(service instanceof b)) {
                            ServiceManager.a.log(Level.FINE, "Started {0} in {1}.", new Object[]{service, stopwatch});
                        }
                    }
                    if (state2 == Service.State.FAILED) {
                        b(service);
                    }
                    if (this.c.count(Service.State.RUNNING) == this.g) {
                        g();
                    } else if (this.c.count(Service.State.TERMINATED) + this.c.count(Service.State.FAILED) == this.g) {
                        f();
                    }
                }
            } finally {
                this.a.leave();
                h();
            }
        }

        void f() {
            this.j.a(ServiceManager.c);
        }

        void g() {
            this.j.a(ServiceManager.b);
        }

        void b(final Service service) {
            this.j.a(new o.a<Listener>() { // from class: com.google.common.util.concurrent.ServiceManager.d.2
                public void a(Listener listener) {
                    listener.failure(service);
                }

                public String toString() {
                    return "failed({service=" + service + "})";
                }
            });
        }

        void h() {
            Preconditions.checkState(!this.a.isOccupiedByCurrentThread(), "It is incorrect to execute listeners with the monitor held.");
            this.j.a();
        }

        @GuardedBy("monitor")
        void i() {
            if (this.c.count(Service.State.RUNNING) != this.g) {
                throw new IllegalStateException("Expected to be healthy after starting. The following services are not running: " + Multimaps.filterKeys((SetMultimap) this.b, Predicates.not(Predicates.equalTo(Service.State.RUNNING))));
            }
        }
    }

    /* loaded from: classes2.dex */
    private static final class c extends Service.Listener {
        final Service a;
        final WeakReference<d> b;

        c(Service service, WeakReference<d> weakReference) {
            this.a = service;
            this.b = weakReference;
        }

        @Override // com.google.common.util.concurrent.Service.Listener
        public void starting() {
            d dVar = this.b.get();
            if (dVar != null) {
                dVar.a(this.a, Service.State.NEW, Service.State.STARTING);
                if (!(this.a instanceof b)) {
                    ServiceManager.a.log(Level.FINE, "Starting {0}.", this.a);
                }
            }
        }

        @Override // com.google.common.util.concurrent.Service.Listener
        public void running() {
            d dVar = this.b.get();
            if (dVar != null) {
                dVar.a(this.a, Service.State.STARTING, Service.State.RUNNING);
            }
        }

        @Override // com.google.common.util.concurrent.Service.Listener
        public void stopping(Service.State state) {
            d dVar = this.b.get();
            if (dVar != null) {
                dVar.a(this.a, state, Service.State.STOPPING);
            }
        }

        @Override // com.google.common.util.concurrent.Service.Listener
        public void terminated(Service.State state) {
            d dVar = this.b.get();
            if (dVar != null) {
                if (!(this.a instanceof b)) {
                    ServiceManager.a.log(Level.FINE, "Service {0} has terminated. Previous state was: {1}", new Object[]{this.a, state});
                }
                dVar.a(this.a, state, Service.State.TERMINATED);
            }
        }

        @Override // com.google.common.util.concurrent.Service.Listener
        public void failed(Service.State state, Throwable th) {
            d dVar = this.b.get();
            if (dVar != null) {
                if (!(this.a instanceof b)) {
                    Logger logger = ServiceManager.a;
                    Level level = Level.SEVERE;
                    logger.log(level, "Service " + this.a + " has failed in the " + state + " state.", th);
                }
                dVar.a(this.a, state, Service.State.FAILED);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b extends AbstractService {
        private b() {
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected void doStart() {
            notifyStarted();
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected void doStop() {
            notifyStopped();
        }
    }

    /* loaded from: classes2.dex */
    private static final class a extends Throwable {
        private a() {
        }
    }
}
