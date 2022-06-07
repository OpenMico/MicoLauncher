package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.lang.ref.SoftReference;

@GwtIncompatible
/* loaded from: classes2.dex */
public abstract class FinalizableSoftReference<T> extends SoftReference<T> implements FinalizableReference {
    protected FinalizableSoftReference(T t, FinalizableReferenceQueue finalizableReferenceQueue) {
        super(t, finalizableReferenceQueue.a);
        finalizableReferenceQueue.a();
    }
}
