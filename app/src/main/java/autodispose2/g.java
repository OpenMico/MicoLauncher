package autodispose2;

import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeSource;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AutoDisposeMaybe.java */
/* loaded from: classes.dex */
public final class g<T> extends Maybe<T> implements MaybeSubscribeProxy<T> {
    private final MaybeSource<T> a;
    private final CompletableSource b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(MaybeSource<T> maybeSource, CompletableSource completableSource) {
        this.a = maybeSource;
        this.b = completableSource;
    }

    @Override // io.reactivex.rxjava3.core.Maybe
    protected void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.a.subscribe(new m(this.b, maybeObserver));
    }
}
