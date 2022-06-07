package com.bumptech.glide.util.pool;

import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public abstract class StateVerifier {
    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a(boolean z);

    public abstract void throwIfRecycled();

    @NonNull
    public static StateVerifier newInstance() {
        return new a();
    }

    private StateVerifier() {
    }

    /* loaded from: classes.dex */
    private static class a extends StateVerifier {
        private volatile boolean a;

        a() {
            super();
        }

        @Override // com.bumptech.glide.util.pool.StateVerifier
        public void throwIfRecycled() {
            if (this.a) {
                throw new IllegalStateException("Already released");
            }
        }

        @Override // com.bumptech.glide.util.pool.StateVerifier
        public void a(boolean z) {
            this.a = z;
        }
    }
}
