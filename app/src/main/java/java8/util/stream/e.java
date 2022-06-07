package java8.util.stream;

import java.util.concurrent.atomic.AtomicReference;
import java8.util.Spliterator;
import java8.util.stream.e;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractShortCircuitTask.java */
/* loaded from: classes5.dex */
public abstract class e<P_IN, P_OUT, R, K extends e<P_IN, P_OUT, R, K>> extends g<P_IN, P_OUT, R, K> {
    protected volatile boolean canceled;
    protected final AtomicReference<R> sharedResult;

    protected abstract R a();

    /* JADX INFO: Access modifiers changed from: protected */
    public e(gb<P_OUT> gbVar, Spliterator<P_IN> spliterator) {
        super(gbVar, spliterator);
        this.sharedResult = new AtomicReference<>(null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public e(K k, Spliterator<P_IN> spliterator) {
        super(k, spliterator);
        this.sharedResult = k.sharedResult;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0051, code lost:
        r8 = r0.i();
     */
    @Override // java8.util.stream.g, java8.util.concurrent.CountedCompleter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void compute() {
        /*
            r10 = this;
            java8.util.Spliterator r0 = r10.spliterator
            long r1 = r0.estimateSize()
            long r3 = r10.c(r1)
            java.util.concurrent.atomic.AtomicReference<R> r5 = r10.sharedResult
            r6 = 0
            r7 = r6
            r6 = r0
            r0 = r10
        L_0x0010:
            java.lang.Object r8 = r5.get()
            if (r8 != 0) goto L_0x0055
            boolean r8 = r0.f()
            if (r8 == 0) goto L_0x0021
            java.lang.Object r8 = r0.a()
            goto L_0x0055
        L_0x0021:
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 <= 0) goto L_0x0051
            java8.util.Spliterator r1 = r6.trySplit()
            if (r1 != 0) goto L_0x002c
            goto L_0x0051
        L_0x002c:
            java8.util.stream.g r2 = r0.a(r1)
            java8.util.stream.e r2 = (java8.util.stream.e) r2
            r0.leftChild = r2
            java8.util.stream.g r8 = r0.a(r6)
            java8.util.stream.e r8 = (java8.util.stream.e) r8
            r0.rightChild = r8
            r9 = 1
            r0.setPendingCount(r9)
            if (r7 == 0) goto L_0x0046
            r6 = r1
            r0 = r2
            r2 = r8
            goto L_0x0047
        L_0x0046:
            r0 = r8
        L_0x0047:
            r7 = r7 ^ 1
            r2.fork()
            long r1 = r6.estimateSize()
            goto L_0x0010
        L_0x0051:
            java.lang.Object r8 = r0.i()
        L_0x0055:
            r0.b(r8)
            r0.tryComplete()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: java8.util.stream.e.compute():void");
    }

    protected void a(R r) {
        if (r != null) {
            this.sharedResult.compareAndSet(null, r);
        }
    }

    @Override // java8.util.stream.g
    protected void b(R r) {
        if (!k()) {
            super.b((e<P_IN, P_OUT, R, K>) r);
        } else if (r != null) {
            this.sharedResult.compareAndSet(null, r);
        }
    }

    @Override // java8.util.stream.g, java8.util.concurrent.CountedCompleter, java8.util.concurrent.ForkJoinTask
    public R getRawResult() {
        return b();
    }

    @Override // java8.util.stream.g
    public R b() {
        if (!k()) {
            return (R) super.b();
        }
        R r = this.sharedResult.get();
        return r == null ? a() : r;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void c() {
        this.canceled = true;
    }

    protected boolean f() {
        boolean z = this.canceled;
        if (!z) {
            Object l = l();
            while (true) {
                e eVar = (e) l;
                if (z || eVar == null) {
                    break;
                }
                z = eVar.canceled;
                l = eVar.l();
            }
        }
        return z;
    }

    protected void g() {
        e<P_IN, P_OUT, R, K> eVar = (e) l();
        e<P_IN, P_OUT, R, K> eVar2 = this;
        while (eVar != null) {
            if (eVar.leftChild == eVar2) {
                e eVar3 = (e) eVar.rightChild;
                if (!eVar3.canceled) {
                    eVar3.c();
                }
            }
            eVar = (e) eVar.l();
            eVar2 = eVar;
        }
    }
}
