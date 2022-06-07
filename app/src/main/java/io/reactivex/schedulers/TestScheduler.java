package io.reactivex.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
public final class TestScheduler extends Scheduler {
    final Queue<b> c = new PriorityBlockingQueue(11);
    long d;
    volatile long e;

    public TestScheduler() {
    }

    public TestScheduler(long j, TimeUnit timeUnit) {
        this.e = timeUnit.toNanos(j);
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
                return ObjectHelper.compare(this.d, bVar.d);
            }
            return ObjectHelper.compare(j, j2);
        }
    }

    @Override // io.reactivex.Scheduler
    public long now(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.e, TimeUnit.NANOSECONDS);
    }

    public void advanceTimeBy(long j, TimeUnit timeUnit) {
        advanceTimeTo(this.e + timeUnit.toNanos(j), TimeUnit.NANOSECONDS);
    }

    public void advanceTimeTo(long j, TimeUnit timeUnit) {
        a(timeUnit.toNanos(j));
    }

    public void triggerActions() {
        a(this.e);
    }

    private void a(long j) {
        while (true) {
            b peek = this.c.peek();
            if (peek == null || peek.a > j) {
                break;
            }
            this.e = peek.a == 0 ? this.e : peek.a;
            this.c.remove(peek);
            if (!peek.c.a) {
                peek.b.run();
            }
        }
        this.e = j;
    }

    @Override // io.reactivex.Scheduler
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

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.a = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.a;
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            if (this.a) {
                return EmptyDisposable.INSTANCE;
            }
            long nanos = TestScheduler.this.e + timeUnit.toNanos(j);
            TestScheduler testScheduler = TestScheduler.this;
            long j2 = testScheduler.d;
            testScheduler.d = 1 + j2;
            b bVar = new b(this, nanos, runnable, j2);
            TestScheduler.this.c.add(bVar);
            return Disposables.fromRunnable(new RunnableC0345a(bVar));
        }

        @Override // io.reactivex.Scheduler.Worker
        @NonNull
        public Disposable schedule(@NonNull Runnable runnable) {
            if (this.a) {
                return EmptyDisposable.INSTANCE;
            }
            TestScheduler testScheduler = TestScheduler.this;
            long j = testScheduler.d;
            testScheduler.d = 1 + j;
            b bVar = new b(this, 0L, runnable, j);
            TestScheduler.this.c.add(bVar);
            return Disposables.fromRunnable(new RunnableC0345a(bVar));
        }

        @Override // io.reactivex.Scheduler.Worker
        public long now(@NonNull TimeUnit timeUnit) {
            return TestScheduler.this.now(timeUnit);
        }

        /* renamed from: io.reactivex.schedulers.TestScheduler$a$a  reason: collision with other inner class name */
        /* loaded from: classes5.dex */
        final class RunnableC0345a implements Runnable {
            final b a;

            RunnableC0345a(b bVar) {
                this.a = bVar;
            }

            @Override // java.lang.Runnable
            public void run() {
                TestScheduler.this.c.remove(this.a);
            }
        }
    }
}
