package io.reactivex.rxjava3.internal.operators.maybe;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.internal.fuseable.HasUpstreamMaybeSource;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractMaybeWithUpstream.java */
/* loaded from: classes5.dex */
public abstract class a<T, R> extends Maybe<R> implements HasUpstreamMaybeSource<T> {
    protected final MaybeSource<T> source;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(MaybeSource<T> maybeSource) {
        this.source = maybeSource;
    }

    @Override // io.reactivex.rxjava3.internal.fuseable.HasUpstreamMaybeSource
    public final MaybeSource<T> source() {
        return this.source;
    }
}
