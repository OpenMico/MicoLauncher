package autodispose2;

import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AutoDisposeObservable.java */
/* loaded from: classes.dex */
public final class h<T> extends Observable<T> implements ObservableSubscribeProxy<T> {
    private final ObservableSource<T> a;
    private final CompletableSource b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(ObservableSource<T> observableSource, CompletableSource completableSource) {
        this.a = observableSource;
        this.b = completableSource;
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super T> observer) {
        this.a.subscribe(new n(this.b, observer));
    }
}
