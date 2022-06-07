package com.bumptech.glide.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Encoder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class EncoderRegistry {
    private final List<a<?>> a = new ArrayList();

    @Nullable
    public synchronized <T> Encoder<T> getEncoder(@NonNull Class<T> cls) {
        for (a<?> aVar : this.a) {
            if (aVar.a(cls)) {
                return (Encoder<T>) aVar.a;
            }
        }
        return null;
    }

    public synchronized <T> void append(@NonNull Class<T> cls, @NonNull Encoder<T> encoder) {
        this.a.add(new a<>(cls, encoder));
    }

    public synchronized <T> void prepend(@NonNull Class<T> cls, @NonNull Encoder<T> encoder) {
        this.a.add(0, new a<>(cls, encoder));
    }

    /* loaded from: classes.dex */
    private static final class a<T> {
        final Encoder<T> a;
        private final Class<T> b;

        a(@NonNull Class<T> cls, @NonNull Encoder<T> encoder) {
            this.b = cls;
            this.a = encoder;
        }

        boolean a(@NonNull Class<?> cls) {
            return this.b.isAssignableFrom(cls);
        }
    }
}
