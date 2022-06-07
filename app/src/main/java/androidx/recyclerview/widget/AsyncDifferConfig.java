package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.recyclerview.widget.DiffUtil;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public final class AsyncDifferConfig<T> {
    @Nullable
    private final Executor a;
    @NonNull
    private final Executor b;
    @NonNull
    private final DiffUtil.ItemCallback<T> c;

    AsyncDifferConfig(@Nullable Executor executor, @NonNull Executor executor2, @NonNull DiffUtil.ItemCallback<T> itemCallback) {
        this.a = executor;
        this.b = executor2;
        this.c = itemCallback;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Executor getMainThreadExecutor() {
        return this.a;
    }

    @NonNull
    public Executor getBackgroundThreadExecutor() {
        return this.b;
    }

    @NonNull
    public DiffUtil.ItemCallback<T> getDiffCallback() {
        return this.c;
    }

    /* loaded from: classes.dex */
    public static final class Builder<T> {
        private static final Object d = new Object();
        private static Executor e = null;
        @Nullable
        private Executor a;
        private Executor b;
        private final DiffUtil.ItemCallback<T> c;

        public Builder(@NonNull DiffUtil.ItemCallback<T> itemCallback) {
            this.c = itemCallback;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public Builder<T> setMainThreadExecutor(Executor executor) {
            this.a = executor;
            return this;
        }

        @NonNull
        public Builder<T> setBackgroundThreadExecutor(Executor executor) {
            this.b = executor;
            return this;
        }

        @NonNull
        public AsyncDifferConfig<T> build() {
            if (this.b == null) {
                synchronized (d) {
                    if (e == null) {
                        e = Executors.newFixedThreadPool(2);
                    }
                }
                this.b = e;
            }
            return new AsyncDifferConfig<>(this.a, this.b, this.c);
        }
    }
}
