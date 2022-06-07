package io.reactivex;

import io.reactivex.annotations.NonNull;

/* loaded from: classes4.dex */
public interface FlowableOnSubscribe<T> {
    void subscribe(@NonNull FlowableEmitter<T> flowableEmitter) throws Exception;
}
