package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes2.dex */
public interface Predicate<T> {
    @CanIgnoreReturnValue
    boolean apply(@NullableDecl T t);

    boolean equals(@NullableDecl Object obj);
}
