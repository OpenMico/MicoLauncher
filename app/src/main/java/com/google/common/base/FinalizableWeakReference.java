package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.lang.ref.WeakReference;

@GwtIncompatible
/* loaded from: classes2.dex */
public abstract class FinalizableWeakReference<T> extends WeakReference<T> implements FinalizableReference {
    protected FinalizableWeakReference(T t, FinalizableReferenceQueue finalizableReferenceQueue) {
        super(t, finalizableReferenceQueue.a);
        finalizableReferenceQueue.a();
    }
}
