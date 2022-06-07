package autodispose2;

import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import org.reactivestreams.Subscriber;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AutoDisposeParallelFlowable.java */
/* loaded from: classes.dex */
public final class i<T> extends ParallelFlowable<T> implements ParallelFlowableSubscribeProxy<T> {
    private final ParallelFlowable<T> a;
    private final CompletableSource b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(ParallelFlowable<T> parallelFlowable, CompletableSource completableSource) {
        this.a = parallelFlowable;
        this.b = completableSource;
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable, autodispose2.ParallelFlowableSubscribeProxy
    public void subscribe(Subscriber<? super T>[] subscriberArr) {
        if (validate(subscriberArr)) {
            Subscriber<? super T>[] subscriberArr2 = new Subscriber[subscriberArr.length];
            for (int i = 0; i < subscriberArr.length; i++) {
                subscriberArr2[i] = new p(this.b, subscriberArr[i]);
            }
            this.a.subscribe(subscriberArr2);
        }
    }

    @Override // io.reactivex.rxjava3.parallel.ParallelFlowable
    public int parallelism() {
        return this.a.parallelism();
    }
}
