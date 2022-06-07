package io.reactivex;

import io.reactivex.annotations.NonNull;

/* loaded from: classes4.dex */
public interface CompletableOnSubscribe {
    void subscribe(@NonNull CompletableEmitter completableEmitter) throws Exception;
}
