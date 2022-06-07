package java8.util.stream;

import java8.util.Spliterator;
import java8.util.concurrent.CountedCompleter;
import java8.util.concurrent.ForkJoinPool;
import java8.util.concurrent.ForkJoinWorkerThread;
import java8.util.stream.g;

/* compiled from: AbstractTask.java */
/* loaded from: classes5.dex */
public abstract class g<P_IN, P_OUT, R, K extends g<P_IN, P_OUT, R, K>> extends CountedCompleter<R> {
    private static final int a = ForkJoinPool.getCommonPoolParallelism() << 2;
    protected final gb<P_OUT> helper;
    protected K leftChild;
    private R localResult;
    protected K rightChild;
    protected Spliterator<P_IN> spliterator;
    protected long targetSize;

    protected abstract K a(Spliterator<P_IN> spliterator);

    protected abstract R i();

    public g(gb<P_OUT> gbVar, Spliterator<P_IN> spliterator) {
        super(null);
        this.helper = gbVar;
        this.spliterator = spliterator;
        this.targetSize = 0L;
    }

    public g(K k, Spliterator<P_IN> spliterator) {
        super(k);
        this.spliterator = spliterator;
        this.helper = k.helper;
        this.targetSize = k.targetSize;
    }

    public static int h() {
        Thread currentThread = Thread.currentThread();
        if (currentThread instanceof ForkJoinWorkerThread) {
            return ((ForkJoinWorkerThread) currentThread).getPool().getParallelism() << 2;
        }
        return a;
    }

    public static long b(long j) {
        long h = j / h();
        if (h > 0) {
            return h;
        }
        return 1L;
    }

    protected final long c(long j) {
        long j2 = this.targetSize;
        if (j2 != 0) {
            return j2;
        }
        long b = b(j);
        this.targetSize = b;
        return b;
    }

    @Override // java8.util.concurrent.CountedCompleter, java8.util.concurrent.ForkJoinTask
    public R getRawResult() {
        return this.localResult;
    }

    @Override // java8.util.concurrent.CountedCompleter, java8.util.concurrent.ForkJoinTask
    protected void setRawResult(R r) {
        if (r != null) {
            throw new IllegalStateException();
        }
    }

    public R b() {
        return this.localResult;
    }

    public void b(R r) {
        this.localResult = r;
    }

    protected boolean j() {
        return this.leftChild == null;
    }

    protected boolean k() {
        return l() == null;
    }

    protected K l() {
        return (K) ((g) getCompleter());
    }

    @Override // java8.util.concurrent.CountedCompleter
    public void compute() {
        Spliterator<P_IN> trySplit;
        Spliterator<P_IN> spliterator = this.spliterator;
        long estimateSize = spliterator.estimateSize();
        long c = c(estimateSize);
        boolean z = false;
        g<P_IN, P_OUT, R, K> gVar = this;
        while (estimateSize > c && (trySplit = spliterator.trySplit()) != null) {
            K a2 = gVar.a(trySplit);
            gVar.leftChild = a2;
            K a3 = gVar.a(spliterator);
            gVar.rightChild = a3;
            gVar.setPendingCount(1);
            if (z) {
                spliterator = trySplit;
                gVar = a2;
                a2 = a3;
            } else {
                gVar = a3;
            }
            z = !z;
            a2.fork();
            estimateSize = spliterator.estimateSize();
        }
        gVar.b((g<P_IN, P_OUT, R, K>) gVar.i());
        gVar.tryComplete();
    }

    @Override // java8.util.concurrent.CountedCompleter
    public void onCompletion(CountedCompleter<?> countedCompleter) {
        this.spliterator = null;
        this.rightChild = null;
        this.leftChild = null;
    }

    protected boolean m() {
        g<P_IN, P_OUT, R, K> gVar = this;
        while (gVar != null) {
            K l = gVar.l();
            if (l != null && l.leftChild != gVar) {
                return false;
            }
            gVar = l;
        }
        return true;
    }
}
