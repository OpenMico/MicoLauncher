package androidx.core.util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public final class Pools {

    /* loaded from: classes.dex */
    public interface Pool<T> {
        @Nullable
        T acquire();

        boolean release(@NonNull T t);
    }

    private Pools() {
    }

    /* loaded from: classes.dex */
    public static class SimplePool<T> implements Pool<T> {
        private final Object[] a;
        private int b;

        public SimplePool(int i) {
            if (i > 0) {
                this.a = new Object[i];
                return;
            }
            throw new IllegalArgumentException("The max pool size must be > 0");
        }

        @Override // androidx.core.util.Pools.Pool
        public T acquire() {
            int i = this.b;
            if (i <= 0) {
                return null;
            }
            int i2 = i - 1;
            Object[] objArr = this.a;
            T t = (T) objArr[i2];
            objArr[i2] = null;
            this.b = i - 1;
            return t;
        }

        @Override // androidx.core.util.Pools.Pool
        public boolean release(@NonNull T t) {
            if (!a(t)) {
                int i = this.b;
                Object[] objArr = this.a;
                if (i >= objArr.length) {
                    return false;
                }
                objArr[i] = t;
                this.b = i + 1;
                return true;
            }
            throw new IllegalStateException("Already in the pool!");
        }

        private boolean a(@NonNull T t) {
            for (int i = 0; i < this.b; i++) {
                if (this.a[i] == t) {
                    return true;
                }
            }
            return false;
        }
    }

    /* loaded from: classes.dex */
    public static class SynchronizedPool<T> extends SimplePool<T> {
        private final Object a = new Object();

        public SynchronizedPool(int i) {
            super(i);
        }

        @Override // androidx.core.util.Pools.SimplePool, androidx.core.util.Pools.Pool
        public T acquire() {
            T t;
            synchronized (this.a) {
                t = (T) super.acquire();
            }
            return t;
        }

        @Override // androidx.core.util.Pools.SimplePool, androidx.core.util.Pools.Pool
        public boolean release(@NonNull T t) {
            boolean release;
            synchronized (this.a) {
                release = super.release(t);
            }
            return release;
        }
    }
}
