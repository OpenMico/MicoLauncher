package io.reactivex.rxjava3.internal.fuseable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;

/* loaded from: classes4.dex */
public interface FuseToMaybe<T> {
    @NonNull
    Maybe<T> fuseToMaybe();
}
