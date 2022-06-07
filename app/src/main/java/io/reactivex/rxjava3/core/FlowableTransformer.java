package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.annotations.NonNull;
import org.reactivestreams.Publisher;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface FlowableTransformer<Upstream, Downstream> {
    @NonNull
    Publisher<Downstream> apply(@NonNull Flowable<Upstream> flowable);
}
