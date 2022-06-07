package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;
import androidx.annotation.Nullable;

/* loaded from: classes2.dex */
public interface ContentMetadata {
    public static final String KEY_CONTENT_LENGTH = "exo_len";
    public static final String KEY_CUSTOM_PREFIX = "custom_";
    public static final String KEY_REDIRECTED_URI = "exo_redir";

    boolean contains(String str);

    long get(String str, long j);

    @Nullable
    String get(String str, @Nullable String str2);

    @Nullable
    byte[] get(String str, @Nullable byte[] bArr);

    static long getContentLength(ContentMetadata contentMetadata) {
        return contentMetadata.get(KEY_CONTENT_LENGTH, -1L);
    }

    @Nullable
    static Uri getRedirectedUri(ContentMetadata contentMetadata) {
        String str = contentMetadata.get(KEY_REDIRECTED_URI, (String) null);
        if (str == null) {
            return null;
        }
        return Uri.parse(str);
    }
}
