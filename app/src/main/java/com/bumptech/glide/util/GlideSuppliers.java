package com.bumptech.glide.util;

/* loaded from: classes.dex */
public final class GlideSuppliers {

    /* loaded from: classes.dex */
    public interface GlideSupplier<T> {
        T get();
    }

    private GlideSuppliers() {
    }

    public static <T> GlideSupplier<T> memorize(final GlideSupplier<T> glideSupplier) {
        return new GlideSupplier<T>() { // from class: com.bumptech.glide.util.GlideSuppliers.1
            private volatile T b;

            /* JADX WARN: Type inference failed for: r0v6, types: [T, java.lang.Object] */
            @Override // com.bumptech.glide.util.GlideSuppliers.GlideSupplier
            public T get() {
                if (this.b == 0) {
                    synchronized (this) {
                        if (this.b == 0) {
                            this.b = Preconditions.checkNotNull(GlideSupplier.this.get());
                        }
                    }
                }
                return this.b;
            }
        };
    }
}
