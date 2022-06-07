package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.Key;
import java.security.MessageDigest;

/* compiled from: DataCacheKey.java */
/* loaded from: classes.dex */
final class d implements Key {
    private final Key a;
    private final Key b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(Key key, Key key2) {
        this.a = key;
        this.b = key2;
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (!(obj instanceof d)) {
            return false;
        }
        d dVar = (d) obj;
        return this.a.equals(dVar.a) && this.b.equals(dVar.b);
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }

    public String toString() {
        return "DataCacheKey{sourceKey=" + this.a + ", signature=" + this.b + '}';
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.a.updateDiskCacheKey(messageDigest);
        this.b.updateDiskCacheKey(messageDigest);
    }
}
