package com.bumptech.glide.signature;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Key;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public class MediaStoreSignature implements Key {
    @NonNull
    private final String a;
    private final long b;
    private final int c;

    public MediaStoreSignature(@Nullable String str, long j, int i) {
        this.a = str == null ? "" : str;
        this.b = j;
        this.c = i;
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MediaStoreSignature mediaStoreSignature = (MediaStoreSignature) obj;
        return this.b == mediaStoreSignature.b && this.c == mediaStoreSignature.c && this.a.equals(mediaStoreSignature.a);
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        long j = this.b;
        return (((this.a.hashCode() * 31) + ((int) (j ^ (j >>> 32)))) * 31) + this.c;
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ByteBuffer.allocate(12).putLong(this.b).putInt(this.c).array());
        messageDigest.update(this.a.getBytes(CHARSET));
    }
}
