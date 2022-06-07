package autodispose2;

import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleSource;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AutoDisposeSingle.java */
/* loaded from: classes.dex */
public final class j<T> extends Single<T> implements SingleSubscribeProxy<T> {
    private final SingleSource<T> a;
    private final CompletableSource b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public j(SingleSource<T> singleSource, CompletableSource completableSource) {
        this.a = singleSource;
        this.b = completableSource;
    }

    @Override // io.reactivex.rxjava3.core.Single
    protected void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new o(this.b, singleObserver));
    }
}
