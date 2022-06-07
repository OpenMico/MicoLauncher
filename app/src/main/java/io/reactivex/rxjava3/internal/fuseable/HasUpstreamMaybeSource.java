package io.reactivex.rxjava3.internal.fuseable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.MaybeSource;

/* loaded from: classes4.dex */
public interface HasUpstreamMaybeSource<T> {
    @NonNull
    MaybeSource<T> source();
}
