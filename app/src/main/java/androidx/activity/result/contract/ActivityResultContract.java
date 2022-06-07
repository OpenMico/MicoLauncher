package androidx.activity.result.contract;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public abstract class ActivityResultContract<I, O> {
    @NonNull
    public abstract Intent createIntent(@NonNull Context context, @SuppressLint({"UnknownNullness"}) I i);

    @Nullable
    public SynchronousResult<O> getSynchronousResult(@NonNull Context context, @SuppressLint({"UnknownNullness"}) I i) {
        return null;
    }

    @SuppressLint({"UnknownNullness"})
    public abstract O parseResult(int i, @Nullable Intent intent);

    /* loaded from: classes.dex */
    public static final class SynchronousResult<T> {
        @SuppressLint({"UnknownNullness"})
        private final T a;

        public SynchronousResult(@SuppressLint({"UnknownNullness"}) T t) {
            this.a = t;
        }

        @SuppressLint({"UnknownNullness"})
        public T getValue() {
            return this.a;
        }
    }
}
