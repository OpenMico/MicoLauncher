package com.bumptech.glide.signature;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.Key;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public final class EmptySignature implements Key {
    private static final EmptySignature a = new EmptySignature();

    public String toString() {
        return "EmptySignature";
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    }

    @NonNull
    public static EmptySignature obtain() {
        return a;
    }

    private EmptySignature() {
    }
}
