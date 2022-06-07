package java8.util.concurrent;

/* loaded from: classes5.dex */
public abstract class RecursiveTask<V> extends ForkJoinTask<V> {
    private static final long serialVersionUID = 5232453952276485270L;
    V result;

    protected abstract V compute();

    @Override // java8.util.concurrent.ForkJoinTask
    public final V getRawResult() {
        return this.result;
    }

    @Override // java8.util.concurrent.ForkJoinTask
    protected final void setRawResult(V v) {
        this.result = v;
    }

    @Override // java8.util.concurrent.ForkJoinTask
    protected final boolean exec() {
        this.result = compute();
        return true;
    }
}
