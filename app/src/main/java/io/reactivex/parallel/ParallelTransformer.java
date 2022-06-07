package io.reactivex.parallel;

import io.reactivex.annotations.NonNull;

/* loaded from: classes4.dex */
public interface ParallelTransformer<Upstream, Downstream> {
    @NonNull
    ParallelFlowable<Downstream> apply(@NonNull ParallelFlowable<Upstream> parallelFlowable);
}
