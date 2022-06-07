package io.reactivex.rxjava3.internal.fuseable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableSource;

/* loaded from: classes4.dex */
public interface HasUpstreamObservableSource<T> {
    @NonNull
    ObservableSource<T> source();
}
