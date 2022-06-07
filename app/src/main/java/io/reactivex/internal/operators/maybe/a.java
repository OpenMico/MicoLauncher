package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.internal.fuseable.HasUpstreamMaybeSource;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: AbstractMaybeWithUpstream.java */
/* loaded from: classes4.dex */
public abstract class a<T, R> extends Maybe<R> implements HasUpstreamMaybeSource<T> {
    protected final MaybeSource<T> source;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(MaybeSource<T> maybeSource) {
        this.source = maybeSource;
    }

    @Override // io.reactivex.internal.fuseable.HasUpstreamMaybeSource
    public final MaybeSource<T> source() {
        return this.source;
    }
}
