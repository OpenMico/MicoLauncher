package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.util.concurrent.ScheduledFuture;

@Beta
@GwtCompatible
/* loaded from: classes2.dex */
public interface ListenableScheduledFuture<V> extends ListenableFuture<V>, ScheduledFuture<V> {
}
