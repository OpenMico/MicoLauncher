package com.google.android.exoplayer2.drm;

/* loaded from: classes2.dex */
public class DecryptionException extends Exception {
    public final int errorCode;

    public DecryptionException(int i, String str) {
        super(str);
        this.errorCode = i;
    }
}
