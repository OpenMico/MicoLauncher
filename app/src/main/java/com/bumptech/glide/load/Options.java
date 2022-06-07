package com.bumptech.glide.load;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.collection.SimpleArrayMap;
import com.bumptech.glide.util.CachedHashCodeArrayMap;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public final class Options implements Key {
    private final ArrayMap<Option<?>, Object> a = new CachedHashCodeArrayMap();

    public void putAll(@NonNull Options options) {
        this.a.putAll((SimpleArrayMap<? extends Option<?>, ? extends Object>) options.a);
    }

    @NonNull
    public <T> Options set(@NonNull Option<T> option, @NonNull T t) {
        this.a.put(option, t);
        return this;
    }

    @Nullable
    public <T> T get(@NonNull Option<T> option) {
        return this.a.containsKey(option) ? (T) this.a.get(option) : option.getDefaultValue();
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (obj instanceof Options) {
            return this.a.equals(((Options) obj).a);
        }
        return false;
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return this.a.hashCode();
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        for (int i = 0; i < this.a.size(); i++) {
            a(this.a.keyAt(i), this.a.valueAt(i), messageDigest);
        }
    }

    public String toString() {
        return "Options{values=" + this.a + '}';
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <T> void a(@NonNull Option<T> option, @NonNull Object obj, @NonNull MessageDigest messageDigest) {
        option.update(obj, messageDigest);
    }
}
