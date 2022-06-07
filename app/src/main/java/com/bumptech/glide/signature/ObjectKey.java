package com.bumptech.glide.signature;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.util.Preconditions;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public final class ObjectKey implements Key {
    private final Object a;

    public ObjectKey(@NonNull Object obj) {
        this.a = Preconditions.checkNotNull(obj);
    }

    public String toString() {
        return "ObjectKey{object=" + this.a + '}';
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (obj instanceof ObjectKey) {
            return this.a.equals(((ObjectKey) obj).a);
        }
        return false;
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return this.a.hashCode();
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(this.a.toString().getBytes(CHARSET));
    }
}
