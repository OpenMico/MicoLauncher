package autodispose2;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableSource;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AutoDisposeCompletable.java */
/* loaded from: classes.dex */
public final class d extends Completable implements CompletableSubscribeProxy {
    private final Completable a;
    private final CompletableSource b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(Completable completable, CompletableSource completableSource) {
        this.a = completable;
        this.b = completableSource;
    }

    @Override // io.reactivex.rxjava3.core.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        this.a.subscribe(new l(this.b, completableObserver));
    }
}
