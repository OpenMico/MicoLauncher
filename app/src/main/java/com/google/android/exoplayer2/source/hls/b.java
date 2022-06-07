package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: FullSegmentEncryptionKeyCache.java */
/* loaded from: classes2.dex */
final class b {
    private final LinkedHashMap<Uri, byte[]> a;

    public b(final int i) {
        this.a = new LinkedHashMap<Uri, byte[]>(this, i + 1, 1.0f, false) { // from class: com.google.android.exoplayer2.source.hls.b.1
            @Override // java.util.LinkedHashMap
            protected boolean removeEldestEntry(Map.Entry<Uri, byte[]> entry) {
                return size() > i;
            }
        };
    }

    @Nullable
    public byte[] a(@Nullable Uri uri) {
        if (uri == null) {
            return null;
        }
        return this.a.get(uri);
    }

    @Nullable
    public byte[] a(Uri uri, byte[] bArr) {
        return this.a.put((Uri) Assertions.checkNotNull(uri), (byte[]) Assertions.checkNotNull(bArr));
    }

    @Nullable
    public byte[] b(Uri uri) {
        return this.a.remove(Assertions.checkNotNull(uri));
    }
}
