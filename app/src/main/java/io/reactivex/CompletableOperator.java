package io.reactivex;

import io.reactivex.annotations.NonNull;

/* loaded from: classes4.dex */
public interface CompletableOperator {
    @NonNull
    CompletableObserver apply(@NonNull CompletableObserver completableObserver) throws Exception;
}
