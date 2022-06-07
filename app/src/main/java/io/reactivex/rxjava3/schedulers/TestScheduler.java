package io.reactivex.rxjava3.schedulers;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.disposables.EmptyDisposable;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public final class TestScheduler extends Scheduler {
    final Queue<b> b = new PriorityBlockingQueue(11);
    long c;
    volatile long d;

    public TestScheduler() {
    }

    public TestScheduler(long j, TimeUnit timeUnit) {
        this.d = timeUnit.toNanos(j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public static final class b implements Comparable<b> {
        final long a;
        final Runnable b;
        final a c;
        final long d;

        b(a aVar, long j, Runnable runnable, long j2) {
            this.a = j;
            this.b = runnable;
            this.c = aVar;
            this.d = j2;
        }

        public String toString() {
            return String.format("TimedRunnable(time = %d, run = %s)", Long.valueOf(this.a), this.b.toString());
        }

        /* renamed from: a */
        public int compareTo(b bVar) {
            long j = this.a;
            long j2 = bVar.a;
            if (j == j2) {
                return Long.compare(this.d, bVar.d);
            }
            return Long.compare(j, j2);
        }
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    public long now(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.d, TimeUnit.NANOSECONDS);
    }

    public void advanceTimeBy(long j, TimeUnit timeUnit) {
        advanceTimeTo(this.d + timeUnit.toNanos(j), TimeUnit.NANOSECONDS);
    }

    public void advanceTimeTo(long j, TimeUnit timeUnit) {
        a(timeUnit.toNanos(j));
    }

    public void triggerActions() {
        a(this.d);
    }

    private void a(long j) {
        while (true) {
            b peek = this.b.peek();
            if (peek == null || peek.a > j) {
                break;
            }
            this.d = peek.a == 0 ? this.d : peek.a;
            this.b.remove(peek);
            if (!peek.c.a) {
                peek.b.run();
            }
        }
        this.d = j;
    }

    @Override // io.reactivex.rxjava3.core.Scheduler
    @NonNull
    public Scheduler.Worker createWorker() {
        return new a();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes5.dex */
    public final class a extends Scheduler.Worker {
        volatile boolean a;

        a() {
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public void dispose() {
            this.a = true;
        }

        @Override // io.reactivex.rxjava3.disposables.Disposable
        public boolean isDisposed() {
            return this.a;
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            if (this.a) {
                return EmptyDisposable.INSTANCE;
            }
            long nanos = TestScheduler.this.d + timeUnit.toNanos(j);
            TestScheduler testScheduler = TestScheduler.this;
            long j2 = testScheduler.c;
            testScheduler.c = 1 + j2;
            b bVar = new b(this, nanos, runnable, j2);
            TestScheduler.this.b.add(bVar);
            return Disposable.fromRunnable(new RunnableC0344a(bVar));
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            if (this.a) {
                return EmptyDisposable.INSTANCE;
            }
            TestScheduler testScheduler = TestScheduler.this;
            long j = testScheduler.c;
            testScheduler.c = 1 + j;
            b bVar = new b(this, 0L, runnable, j);
            TestScheduler.this.b.add(bVar);
            return Disposable.fromRunnable(new RunnableC0344a(bVar));
        }

        @Override // io.reactivex.rxjava3.core.Scheduler.Worker
        public long now(@NonNull TimeUnit timeUnit) {
            return TestScheduler.this.now(timeUnit);
        }

        /* renamed from: io.reactivex.rxjava3.schedulers.TestScheduler$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        final class RunnableC0344a implements Runnable {
            final b a;

            RunnableC0344a(b bVar) {
                this.a = bVar;
            }

            @Override // java.lang.Runnable
            public void run() {
                TestScheduler.this.b.remove(this.a);
            }
        }
    }
}
