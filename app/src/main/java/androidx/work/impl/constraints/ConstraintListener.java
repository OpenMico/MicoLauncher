package androidx.work.impl.constraints;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public interface ConstraintListener<T> {
    @MainThread
    void onConstraintChanged(@Nullable T t);
}
