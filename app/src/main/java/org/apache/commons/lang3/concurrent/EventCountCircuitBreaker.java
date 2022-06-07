package org.apache.commons.lang3.concurrent;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.concurrent.AbstractCircuitBreaker;

/* loaded from: classes5.dex */
public class EventCountCircuitBreaker extends AbstractCircuitBreaker<Integer> {
    private static final Map<AbstractCircuitBreaker.State, b> a = b();
    private final AtomicReference<a> b;
    private final int c;
    private final long d;
    private final int e;
    private final long f;

    public EventCountCircuitBreaker(int i, long j, TimeUnit timeUnit, int i2, long j2, TimeUnit timeUnit2) {
        this.b = new AtomicReference<>(new a(0, 0L));
        this.c = i;
        this.d = timeUnit.toNanos(j);
        this.e = i2;
        this.f = timeUnit2.toNanos(j2);
    }

    public EventCountCircuitBreaker(int i, long j, TimeUnit timeUnit, int i2) {
        this(i, j, timeUnit, i2, j, timeUnit);
    }

    public EventCountCircuitBreaker(int i, long j, TimeUnit timeUnit) {
        this(i, j, timeUnit, i);
    }

    public int getOpeningThreshold() {
        return this.c;
    }

    public long getOpeningInterval() {
        return this.d;
    }

    public int getClosingThreshold() {
        return this.e;
    }

    public long getClosingInterval() {
        return this.f;
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractCircuitBreaker, org.apache.commons.lang3.concurrent.CircuitBreaker
    public boolean checkState() {
        return a(0);
    }

    public boolean incrementAndCheckState(Integer num) throws CircuitBreakingException {
        return a(1);
    }

    public boolean incrementAndCheckState() {
        return incrementAndCheckState((Integer) 1);
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractCircuitBreaker, org.apache.commons.lang3.concurrent.CircuitBreaker
    public void open() {
        super.open();
        this.b.set(new a(0, a()));
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractCircuitBreaker, org.apache.commons.lang3.concurrent.CircuitBreaker
    public void close() {
        super.close();
        this.b.set(new a(0, a()));
    }

    private boolean a(int i) {
        AbstractCircuitBreaker.State state;
        a aVar;
        a a2;
        do {
            long a3 = a();
            state = (AbstractCircuitBreaker.State) this.state.get();
            aVar = this.b.get();
            a2 = a(i, aVar, state, a3);
        } while (!a(aVar, a2));
        if (b(state).a(this, aVar, a2)) {
            state = state.oppositeState();
            a(state);
        }
        return !isOpen(state);
    }

    private boolean a(a aVar, a aVar2) {
        return aVar == aVar2 || this.b.compareAndSet(aVar, aVar2);
    }

    private void a(AbstractCircuitBreaker.State state) {
        changeState(state);
        this.b.set(new a(0, a()));
    }

    private a a(int i, a aVar, AbstractCircuitBreaker.State state, long j) {
        if (b(state).a(this, aVar, j)) {
            return new a(i, j);
        }
        return aVar.a(i);
    }

    long a() {
        return System.nanoTime();
    }

    private static b b(AbstractCircuitBreaker.State state) {
        return a.get(state);
    }

    private static Map<AbstractCircuitBreaker.State, b> b() {
        EnumMap enumMap = new EnumMap(AbstractCircuitBreaker.State.class);
        enumMap.put((EnumMap) AbstractCircuitBreaker.State.CLOSED, (AbstractCircuitBreaker.State) new c());
        enumMap.put((EnumMap) AbstractCircuitBreaker.State.OPEN, (AbstractCircuitBreaker.State) new d());
        return enumMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class a {
        private final int a;
        private final long b;

        public a(int i, long j) {
            this.a = i;
            this.b = j;
        }

        public int a() {
            return this.a;
        }

        public long b() {
            return this.b;
        }

        public a a(int i) {
            return i != 0 ? new a(a() + i, b()) : this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static abstract class b {
        protected abstract long a(EventCountCircuitBreaker eventCountCircuitBreaker);

        public abstract boolean a(EventCountCircuitBreaker eventCountCircuitBreaker, a aVar, a aVar2);

        private b() {
        }

        public boolean a(EventCountCircuitBreaker eventCountCircuitBreaker, a aVar, long j) {
            return j - aVar.b() > a(eventCountCircuitBreaker);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class c extends b {
        private c() {
            super();
        }

        @Override // org.apache.commons.lang3.concurrent.EventCountCircuitBreaker.b
        public boolean a(EventCountCircuitBreaker eventCountCircuitBreaker, a aVar, a aVar2) {
            return aVar2.a() > eventCountCircuitBreaker.getOpeningThreshold();
        }

        @Override // org.apache.commons.lang3.concurrent.EventCountCircuitBreaker.b
        protected long a(EventCountCircuitBreaker eventCountCircuitBreaker) {
            return eventCountCircuitBreaker.getOpeningInterval();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public static class d extends b {
        private d() {
            super();
        }

        @Override // org.apache.commons.lang3.concurrent.EventCountCircuitBreaker.b
        public boolean a(EventCountCircuitBreaker eventCountCircuitBreaker, a aVar, a aVar2) {
            return aVar2.b() != aVar.b() && aVar.a() < eventCountCircuitBreaker.getClosingThreshold();
        }

        @Override // org.apache.commons.lang3.concurrent.EventCountCircuitBreaker.b
        protected long a(EventCountCircuitBreaker eventCountCircuitBreaker) {
            return eventCountCircuitBreaker.getClosingInterval();
        }
    }
}
