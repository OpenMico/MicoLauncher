package io.reactivex;

import io.reactivex.annotations.NonNull;
import org.reactivestreams.Publisher;

/* loaded from: classes4.dex */
public interface FlowableTransformer<Upstream, Downstream> {
    @NonNull
    Publisher<Downstream> apply(@NonNull Flowable<Upstream> flowable);
}
