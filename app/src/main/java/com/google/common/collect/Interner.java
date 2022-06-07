package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public interface Interner<E> {
    @CanIgnoreReturnValue
    E intern(E e);
}