package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.internal.fuseable.HasUpstreamObservableSource;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractObservableWithUpstream.java */
/* loaded from: classes5.dex */
public abstract class a<T, U> extends Observable<U> implements HasUpstreamObservableSource<T> {
    protected final ObservableSource<T> source;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(ObservableSource<T> observableSource) {
        this.source = observableSource;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.HasUpstreamObservableSource
    public final ObservableSource<T> source() {
        return this.source;
    }
}
