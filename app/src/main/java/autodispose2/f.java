package autodispose2;

import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Flowable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AutoDisposeFlowable.java */
/* loaded from: classes.dex */
public final class f<T> extends Flowable<T> implements FlowableSubscribeProxy<T> {
    private final Publisher<T> b;
    private final CompletableSource c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(Publisher<T> publisher, CompletableSource completableSource) {
        this.b = publisher;
        this.c = completableSource;
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    protected void subscribeActual(Subscriber<? super T> subscriber) {
        this.b.subscribe(new p(this.c, subscriber));
    }
}
