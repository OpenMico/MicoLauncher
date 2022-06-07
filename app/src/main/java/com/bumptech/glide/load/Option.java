package com.bumptech.glide.load;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.util.Preconditions;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public final class Option<T> {
    private static final CacheKeyUpdater<Object> a = new CacheKeyUpdater<Object>() { // from class: com.bumptech.glide.load.Option.1
        @Override // com.bumptech.glide.load.Option.CacheKeyUpdater
        public void update(@NonNull byte[] bArr, @NonNull Object obj, @NonNull MessageDigest messageDigest) {
        }
    };
    private final T b;
    private final CacheKeyUpdater<T> c;
    private final String d;
    private volatile byte[] e;

    /* loaded from: classes.dex */
    public interface CacheKeyUpdater<T> {
        void update(@NonNull byte[] bArr, @NonNull T t, @NonNull MessageDigest messageDigest);
    }

    @NonNull
    public static <T> Option<T> memory(@NonNull String str) {
        return new Option<>(str, null, b());
    }

    @NonNull
    public static <T> Option<T> memory(@NonNull String str, @NonNull T t) {
        return new Option<>(str, t, b());
    }

    @NonNull
    public static <T> Option<T> disk(@NonNull String str, @NonNull CacheKeyUpdater<T> cacheKeyUpdater) {
        return new Option<>(str, null, cacheKeyUpdater);
    }

    @NonNull
    public static <T> Option<T> disk(@NonNull String str, @Nullable T t, @NonNull CacheKeyUpdater<T> cacheKeyUpdater) {
        return new Option<>(str, t, cacheKeyUpdater);
    }

    private Option(@NonNull String str, @Nullable T t, @NonNull CacheKeyUpdater<T> cacheKeyUpdater) {
        this.d = Preconditions.checkNotEmpty(str);
        this.b = t;
        this.c = (CacheKeyUpdater) Preconditions.checkNotNull(cacheKeyUpdater);
    }

    @Nullable
    public T getDefaultValue() {
        return this.b;
    }

    public void update(@NonNull T t, @NonNull MessageDigest messageDigest) {
        this.c.update(a(), t, messageDigest);
    }

    @NonNull
    private byte[] a() {
        if (this.e == null) {
            this.e = this.d.getBytes(Key.CHARSET);
        }
        return this.e;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Option) {
            return this.d.equals(((Option) obj).d);
        }
        return false;
    }

    public int hashCode() {
        return this.d.hashCode();
    }

    @NonNull
    private static <T> CacheKeyUpdater<T> b() {
        return (CacheKeyUpdater<T>) a;
    }

    public String toString() {
        return "Option{key='" + this.d + "'}";
    }
}
