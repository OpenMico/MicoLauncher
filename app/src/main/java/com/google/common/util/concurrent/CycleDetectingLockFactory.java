package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.Weak;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@CanIgnoreReturnValue
@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public class CycleDetectingLockFactory {
    private static final ConcurrentMap<Class<? extends Enum>, Map<? extends Enum, g>> b = new MapMaker().weakKeys().makeMap();
    private static final Logger c = Logger.getLogger(CycleDetectingLockFactory.class.getName());
    private static final ThreadLocal<ArrayList<g>> d = new ThreadLocal<ArrayList<g>>() { // from class: com.google.common.util.concurrent.CycleDetectingLockFactory.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public ArrayList<g> initialValue() {
            return Lists.newArrayListWithCapacity(3);
        }
    };
    final Policy a;

    @Beta
    /* loaded from: classes2.dex */
    public enum Policies implements Policy {
        THROW {
            @Override // com.google.common.util.concurrent.CycleDetectingLockFactory.Policy
            public void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException) {
                throw potentialDeadlockException;
            }
        },
        WARN {
            @Override // com.google.common.util.concurrent.CycleDetectingLockFactory.Policy
            public void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException) {
                CycleDetectingLockFactory.c.log(Level.SEVERE, "Detected potential deadlock", (Throwable) potentialDeadlockException);
            }
        },
        DISABLED {
            @Override // com.google.common.util.concurrent.CycleDetectingLockFactory.Policy
            public void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException) {
            }
        }
    }

    @Beta
    /* loaded from: classes2.dex */
    public interface Policy {
        void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public interface a {
        g a();

        boolean b();
    }

    public static CycleDetectingLockFactory newInstance(Policy policy) {
        return new CycleDetectingLockFactory(policy);
    }

    public ReentrantLock newReentrantLock(String str) {
        return newReentrantLock(str, false);
    }

    public ReentrantLock newReentrantLock(String str, boolean z) {
        return this.a == Policies.DISABLED ? new ReentrantLock(z) : new b(new g(str), z);
    }

    public ReentrantReadWriteLock newReentrantReadWriteLock(String str) {
        return newReentrantReadWriteLock(str, false);
    }

    public ReentrantReadWriteLock newReentrantReadWriteLock(String str, boolean z) {
        return this.a == Policies.DISABLED ? new ReentrantReadWriteLock(z) : new d(new g(str), z);
    }

    public static <E extends Enum<E>> WithExplicitOrdering<E> newInstanceWithExplicitOrdering(Class<E> cls, Policy policy) {
        Preconditions.checkNotNull(cls);
        Preconditions.checkNotNull(policy);
        return new WithExplicitOrdering<>(policy, b(cls));
    }

    private static Map<? extends Enum, g> b(Class<? extends Enum> cls) {
        Map<? extends Enum, g> map = b.get(cls);
        if (map != null) {
            return map;
        }
        Map<? extends Enum, g> a2 = a(cls);
        return (Map) MoreObjects.firstNonNull(b.putIfAbsent(cls, a2), a2);
    }

    @VisibleForTesting
    static <E extends Enum<E>> Map<E, g> a(Class<E> cls) {
        EnumMap newEnumMap = Maps.newEnumMap(cls);
        E[] enumConstants = cls.getEnumConstants();
        int length = enumConstants.length;
        ArrayList newArrayListWithCapacity = Lists.newArrayListWithCapacity(length);
        int i = 0;
        for (E e2 : enumConstants) {
            g gVar = new g(a((Enum<?>) e2));
            newArrayListWithCapacity.add(gVar);
            newEnumMap.put((EnumMap) e2, (E) gVar);
        }
        for (int i2 = 1; i2 < length; i2++) {
            ((g) newArrayListWithCapacity.get(i2)).a(Policies.THROW, newArrayListWithCapacity.subList(0, i2));
        }
        while (i < length - 1) {
            i++;
            ((g) newArrayListWithCapacity.get(i)).a(Policies.DISABLED, newArrayListWithCapacity.subList(i, length));
        }
        return Collections.unmodifiableMap(newEnumMap);
    }

    private static String a(Enum<?> r2) {
        return r2.getDeclaringClass().getSimpleName() + "." + r2.name();
    }

    @Beta
    /* loaded from: classes2.dex */
    public static final class WithExplicitOrdering<E extends Enum<E>> extends CycleDetectingLockFactory {
        private final Map<E, g> b;

        @VisibleForTesting
        WithExplicitOrdering(Policy policy, Map<E, g> map) {
            super(policy);
            this.b = map;
        }

        public ReentrantLock newReentrantLock(E e) {
            return newReentrantLock((WithExplicitOrdering<E>) e, false);
        }

        public ReentrantLock newReentrantLock(E e, boolean z) {
            return this.a == Policies.DISABLED ? new ReentrantLock(z) : new b(this.b.get(e), z);
        }

        public ReentrantReadWriteLock newReentrantReadWriteLock(E e) {
            return newReentrantReadWriteLock((WithExplicitOrdering<E>) e, false);
        }

        public ReentrantReadWriteLock newReentrantReadWriteLock(E e, boolean z) {
            return this.a == Policies.DISABLED ? new ReentrantReadWriteLock(z) : new d(this.b.get(e), z);
        }
    }

    private CycleDetectingLockFactory(Policy policy) {
        this.a = (Policy) Preconditions.checkNotNull(policy);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class f extends IllegalStateException {
        static final StackTraceElement[] a = new StackTraceElement[0];
        static final ImmutableSet<String> b = ImmutableSet.of(CycleDetectingLockFactory.class.getName(), f.class.getName(), g.class.getName());

        f(g gVar, g gVar2) {
            super(gVar.a() + " -> " + gVar2.a());
            StackTraceElement[] stackTrace = getStackTrace();
            int length = stackTrace.length;
            for (int i = 0; i < length; i++) {
                if (WithExplicitOrdering.class.getName().equals(stackTrace[i].getClassName())) {
                    setStackTrace(a);
                    return;
                } else if (!b.contains(stackTrace[i].getClassName())) {
                    setStackTrace((StackTraceElement[]) Arrays.copyOfRange(stackTrace, i, length));
                    return;
                }
            }
        }
    }

    @Beta
    /* loaded from: classes2.dex */
    public static final class PotentialDeadlockException extends f {
        private final f conflictingStackTrace;

        private PotentialDeadlockException(g gVar, g gVar2, f fVar) {
            super(gVar, gVar2);
            this.conflictingStackTrace = fVar;
            initCause(fVar);
        }

        public f getConflictingStackTrace() {
            return this.conflictingStackTrace;
        }

        @Override // java.lang.Throwable
        public String getMessage() {
            StringBuilder sb = new StringBuilder(super.getMessage());
            for (Throwable th = this.conflictingStackTrace; th != null; th = th.getCause()) {
                sb.append(", ");
                sb.append(th.getMessage());
            }
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class g {
        final Map<g, f> a = new MapMaker().weakKeys().makeMap();
        final Map<g, PotentialDeadlockException> b = new MapMaker().weakKeys().makeMap();
        final String c;

        g(String str) {
            this.c = (String) Preconditions.checkNotNull(str);
        }

        String a() {
            return this.c;
        }

        void a(Policy policy, List<g> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                a(policy, list.get(i));
            }
        }

        void a(Policy policy, g gVar) {
            Preconditions.checkState(this != gVar, "Attempted to acquire multiple locks with the same rank %s", gVar.a());
            if (!this.a.containsKey(gVar)) {
                PotentialDeadlockException potentialDeadlockException = this.b.get(gVar);
                if (potentialDeadlockException != null) {
                    policy.handlePotentialDeadlock(new PotentialDeadlockException(gVar, this, potentialDeadlockException.getConflictingStackTrace()));
                    return;
                }
                f a = gVar.a(this, Sets.newIdentityHashSet());
                if (a == null) {
                    this.a.put(gVar, new f(gVar, this));
                    return;
                }
                PotentialDeadlockException potentialDeadlockException2 = new PotentialDeadlockException(gVar, this, a);
                this.b.put(gVar, potentialDeadlockException2);
                policy.handlePotentialDeadlock(potentialDeadlockException2);
            }
        }

        @NullableDecl
        private f a(g gVar, Set<g> set) {
            if (!set.add(this)) {
                return null;
            }
            f fVar = this.a.get(gVar);
            if (fVar != null) {
                return fVar;
            }
            for (Map.Entry<g, f> entry : this.a.entrySet()) {
                g key = entry.getKey();
                f a = key.a(gVar, set);
                if (a != null) {
                    f fVar2 = new f(key, this);
                    fVar2.setStackTrace(entry.getValue().getStackTrace());
                    fVar2.initCause(a);
                    return fVar2;
                }
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(a aVar) {
        if (!aVar.b()) {
            ArrayList<g> arrayList = d.get();
            g a2 = aVar.a();
            a2.a(this.a, arrayList);
            arrayList.add(a2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(a aVar) {
        if (!aVar.b()) {
            ArrayList<g> arrayList = d.get();
            g a2 = aVar.a();
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (arrayList.get(size) == a2) {
                    arrayList.remove(size);
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public final class b extends ReentrantLock implements a {
        private final g lockGraphNode;

        private b(g gVar, boolean z) {
            super(z);
            this.lockGraphNode = (g) Preconditions.checkNotNull(gVar);
        }

        @Override // com.google.common.util.concurrent.CycleDetectingLockFactory.a
        public g a() {
            return this.lockGraphNode;
        }

        @Override // com.google.common.util.concurrent.CycleDetectingLockFactory.a
        public boolean b() {
            return isHeldByCurrentThread();
        }

        @Override // java.util.concurrent.locks.ReentrantLock, java.util.concurrent.locks.Lock
        public void lock() {
            CycleDetectingLockFactory.this.b(this);
            try {
                super.lock();
            } finally {
                CycleDetectingLockFactory.c(this);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantLock, java.util.concurrent.locks.Lock
        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.this.b(this);
            try {
                super.lockInterruptibly();
            } finally {
                CycleDetectingLockFactory.c(this);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantLock, java.util.concurrent.locks.Lock
        public boolean tryLock() {
            CycleDetectingLockFactory.this.b(this);
            try {
                return super.tryLock();
            } finally {
                CycleDetectingLockFactory.c(this);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantLock, java.util.concurrent.locks.Lock
        public boolean tryLock(long j, TimeUnit timeUnit) throws InterruptedException {
            CycleDetectingLockFactory.this.b(this);
            try {
                return super.tryLock(j, timeUnit);
            } finally {
                CycleDetectingLockFactory.c(this);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantLock, java.util.concurrent.locks.Lock
        public void unlock() {
            try {
                super.unlock();
            } finally {
                CycleDetectingLockFactory.c(this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public final class d extends ReentrantReadWriteLock implements a {
        private final g lockGraphNode;
        private final c readLock;
        private final e writeLock;

        private d(g gVar, boolean z) {
            super(z);
            this.readLock = new c(this);
            this.writeLock = new e(this);
            this.lockGraphNode = (g) Preconditions.checkNotNull(gVar);
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock, java.util.concurrent.locks.ReadWriteLock
        public ReentrantReadWriteLock.ReadLock readLock() {
            return this.readLock;
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock, java.util.concurrent.locks.ReadWriteLock
        public ReentrantReadWriteLock.WriteLock writeLock() {
            return this.writeLock;
        }

        @Override // com.google.common.util.concurrent.CycleDetectingLockFactory.a
        public g a() {
            return this.lockGraphNode;
        }

        @Override // com.google.common.util.concurrent.CycleDetectingLockFactory.a
        public boolean b() {
            return isWriteLockedByCurrentThread() || getReadHoldCount() > 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class c extends ReentrantReadWriteLock.ReadLock {
        @Weak
        final d readWriteLock;

        c(d dVar) {
            super(dVar);
            this.readWriteLock = dVar;
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock, java.util.concurrent.locks.Lock
        public void lock() {
            CycleDetectingLockFactory.this.b(this.readWriteLock);
            try {
                super.lock();
            } finally {
                CycleDetectingLockFactory.c(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock, java.util.concurrent.locks.Lock
        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.this.b(this.readWriteLock);
            try {
                super.lockInterruptibly();
            } finally {
                CycleDetectingLockFactory.c(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock, java.util.concurrent.locks.Lock
        public boolean tryLock() {
            CycleDetectingLockFactory.this.b(this.readWriteLock);
            try {
                return super.tryLock();
            } finally {
                CycleDetectingLockFactory.c(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock, java.util.concurrent.locks.Lock
        public boolean tryLock(long j, TimeUnit timeUnit) throws InterruptedException {
            CycleDetectingLockFactory.this.b(this.readWriteLock);
            try {
                return super.tryLock(j, timeUnit);
            } finally {
                CycleDetectingLockFactory.c(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock, java.util.concurrent.locks.Lock
        public void unlock() {
            try {
                super.unlock();
            } finally {
                CycleDetectingLockFactory.c(this.readWriteLock);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class e extends ReentrantReadWriteLock.WriteLock {
        @Weak
        final d readWriteLock;

        e(d dVar) {
            super(dVar);
            this.readWriteLock = dVar;
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock, java.util.concurrent.locks.Lock
        public void lock() {
            CycleDetectingLockFactory.this.b(this.readWriteLock);
            try {
                super.lock();
            } finally {
                CycleDetectingLockFactory.c(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock, java.util.concurrent.locks.Lock
        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.this.b(this.readWriteLock);
            try {
                super.lockInterruptibly();
            } finally {
                CycleDetectingLockFactory.c(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock, java.util.concurrent.locks.Lock
        public boolean tryLock() {
            CycleDetectingLockFactory.this.b(this.readWriteLock);
            try {
                return super.tryLock();
            } finally {
                CycleDetectingLockFactory.c(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock, java.util.concurrent.locks.Lock
        public boolean tryLock(long j, TimeUnit timeUnit) throws InterruptedException {
            CycleDetectingLockFactory.this.b(this.readWriteLock);
            try {
                return super.tryLock(j, timeUnit);
            } finally {
                CycleDetectingLockFactory.c(this.readWriteLock);
            }
        }

        @Override // java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock, java.util.concurrent.locks.Lock
        public void unlock() {
            try {
                super.unlock();
            } finally {
                CycleDetectingLockFactory.c(this.readWriteLock);
            }
        }
    }
}
