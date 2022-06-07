package com.bumptech.glide.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.ResourceEncoder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ResourceEncoderRegistry {
    private final List<a<?>> a = new ArrayList();

    public synchronized <Z> void append(@NonNull Class<Z> cls, @NonNull ResourceEncoder<Z> resourceEncoder) {
        this.a.add(new a<>(cls, resourceEncoder));
    }

    public synchronized <Z> void prepend(@NonNull Class<Z> cls, @NonNull ResourceEncoder<Z> resourceEncoder) {
        this.a.add(0, new a<>(cls, resourceEncoder));
    }

    @Nullable
    public synchronized <Z> ResourceEncoder<Z> get(@NonNull Class<Z> cls) {
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            a<?> aVar = this.a.get(i);
            if (aVar.a(cls)) {
                return (ResourceEncoder<Z>) aVar.a;
            }
        }
        return null;
    }

    /* loaded from: classes.dex */
    private static final class a<T> {
        final ResourceEncoder<T> a;
        private final Class<T> b;

        a(@NonNull Class<T> cls, @NonNull ResourceEncoder<T> resourceEncoder) {
            this.b = cls;
            this.a = resourceEncoder;
        }

        boolean a(@NonNull Class<?> cls) {
            return this.b.isAssignableFrom(cls);
        }
    }
}
