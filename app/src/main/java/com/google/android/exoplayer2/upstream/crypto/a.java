package com.google.android.exoplayer2.upstream.crypto;

import androidx.annotation.Nullable;

/* compiled from: CryptoUtil.java */
/* loaded from: classes2.dex */
final class a {
    public static long a(@Nullable String str) {
        long j = 0;
        if (str == null) {
            return 0L;
        }
        for (int i = 0; i < str.length(); i++) {
            long charAt = j ^ str.charAt(i);
            j = charAt + (charAt << 1) + (charAt << 4) + (charAt << 5) + (charAt << 7) + (charAt << 8) + (charAt << 40);
        }
        return j;
    }
}
