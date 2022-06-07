package java8.util.concurrent;

import sun.misc.Unsafe;

/* loaded from: classes5.dex */
public abstract class CountedCompleter<T> extends ForkJoinTask<T> {
    private static final Unsafe a = d.a;
    private static final long b;
    private static final long serialVersionUID = 5232453752276485070L;
    final CountedCompleter<?> completer;
    volatile int pending;

    public abstract void compute();

    @Override // java8.util.concurrent.ForkJoinTask
    public T getRawResult() {
        return null;
    }

    public void onCompletion(CountedCompleter<?> countedCompleter) {
    }

    public boolean onExceptionalCompletion(Throwable th, CountedCompleter<?> countedCompleter) {
        return true;
    }

    @Override // java8.util.concurrent.ForkJoinTask
    protected void setRawResult(T t) {
    }

    public CountedCompleter(CountedCompleter<?> countedCompleter, int i) {
        this.completer = countedCompleter;
        this.pending = i;
    }

    public CountedCompleter(CountedCompleter<?> countedCompleter) {
        this.completer = countedCompleter;
    }

    public CountedCompleter() {
        this.completer = null;
    }

    public final CountedCompleter<?> getCompleter() {
        return this.completer;
    }

    public final int getPendingCount() {
        return this.pending;
    }

    public final void setPendingCount(int i) {
        this.pending = i;
    }

    public final void addToPendingCount(int i) {
        Unsafe unsafe;
        long j;
        int i2;
        do {
            unsafe = a;
            j = b;
            i2 = this.pending;
        } while (!unsafe.compareAndSwapInt(this, j, i2, i2 + i));
    }

    public final boolean compareAndSetPendingCount(int i, int i2) {
        return a.compareAndSwapInt(this, b, i, i2);
    }

    public final int decrementPendingCountUnlessZero() {
        int i;
        do {
            i = this.pending;
            if (i == 0) {
                break;
            }
        } while (!a.compareAndSwapInt(this, b, i, i - 1));
        return i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final CountedCompleter<?> getRoot() {
        CountedCompleter countedCompleter = this;
        while (true) {
            CountedCompleter countedCompleter2 = countedCompleter.completer;
            if (countedCompleter2 == null) {
                return countedCompleter;
            }
            countedCompleter = countedCompleter2;
        }
    }

    public final void tryComplete() {
        CountedCompleter<?> countedCompleter = this;
        CountedCompleter<?> countedCompleter2 = countedCompleter;
        while (true) {
            int i = countedCompleter.pending;
            if (i == 0) {
                countedCompleter.onCompletion(countedCompleter2);
                CountedCompleter<?> countedCompleter3 = countedCompleter.completer;
                if (countedCompleter3 == null) {
                    countedCompleter.quietlyComplete();
                    return;
                } else {
                    countedCompleter2 = countedCompleter;
                    countedCompleter = countedCompleter3;
                }
            } else if (a.compareAndSwapInt(countedCompleter, b, i, i - 1)) {
                return;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void propagateCompletion() {
        CountedCompleter countedCompleter = this;
        while (true) {
            int i = countedCompleter.pending;
            if (i == 0) {
                CountedCompleter countedCompleter2 = countedCompleter.completer;
                if (countedCompleter2 == null) {
                    countedCompleter.quietlyComplete();
                    return;
                }
                countedCompleter = countedCompleter2;
            } else if (a.compareAndSwapInt(countedCompleter, b, i, i - 1)) {
                return;
            }
        }
    }

    @Override // java8.util.concurrent.ForkJoinTask
    public void complete(T t) {
        setRawResult(t);
        onCompletion(this);
        quietlyComplete();
        CountedCompleter<?> countedCompleter = this.completer;
        if (countedCompleter != null) {
            countedCompleter.tryComplete();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final CountedCompleter<?> firstComplete() {
        int i;
        do {
            i = this.pending;
            if (i == 0) {
                return this;
            }
        } while (!a.compareAndSwapInt(this, b, i, i - 1));
        return null;
    }

    public final CountedCompleter<?> nextComplete() {
        CountedCompleter<?> countedCompleter = this.completer;
        if (countedCompleter != null) {
            return countedCompleter.firstComplete();
        }
        quietlyComplete();
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void quietlyCompleteRoot() {
        CountedCompleter countedCompleter = this;
        while (true) {
            CountedCompleter countedCompleter2 = countedCompleter.completer;
            if (countedCompleter2 == null) {
                countedCompleter.quietlyComplete();
                return;
            }
            countedCompleter = countedCompleter2;
        }
    }

    public final void helpComplete(int i) {
        if (i > 0 && this.status >= 0) {
            Thread currentThread = Thread.currentThread();
            if (currentThread instanceof ForkJoinWorkerThread) {
                ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread) currentThread;
                forkJoinWorkerThread.a.a(forkJoinWorkerThread.b, (CountedCompleter<?>) this, i);
                return;
            }
            ForkJoinPool.b.a((CountedCompleter<?>) this, i);
        }
    }

    @Override // java8.util.concurrent.ForkJoinTask
    void a(Throwable th) {
        CountedCompleter<?> countedCompleter;
        CountedCompleter<?> countedCompleter2 = this;
        CountedCompleter<?> countedCompleter3 = countedCompleter2;
        while (countedCompleter2.onExceptionalCompletion(th, countedCompleter3) && (countedCompleter = countedCompleter2.completer) != null && countedCompleter.status >= 0 && countedCompleter.b(th) == Integer.MIN_VALUE) {
            countedCompleter3 = countedCompleter2;
            countedCompleter2 = countedCompleter;
        }
    }

    @Override // java8.util.concurrent.ForkJoinTask
    protected final boolean exec() {
        compute();
        return false;
    }

    static {
        try {
            b = a.objectFieldOffset(CountedCompleter.class.getDeclaredField("pending"));
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
