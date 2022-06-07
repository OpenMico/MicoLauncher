package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@Beta
@GwtCompatible
/* loaded from: classes2.dex */
public interface AsyncCallable<V> {
    ListenableFuture<V> call() throws Exception;
}
