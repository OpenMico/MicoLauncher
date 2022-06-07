package com.google.android.exoplayer2.offline;

import com.google.android.exoplayer2.offline.DownloadManager;
import java.util.Comparator;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.offline.-$$Lambda$DownloadManager$b$fjN3c0qDMj2NLNLS1nrkYqt1Kyg  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$DownloadManager$b$fjN3c0qDMj2NLNLS1nrkYqt1Kyg implements Comparator {
    public static final /* synthetic */ $$Lambda$DownloadManager$b$fjN3c0qDMj2NLNLS1nrkYqt1Kyg INSTANCE = new $$Lambda$DownloadManager$b$fjN3c0qDMj2NLNLS1nrkYqt1Kyg();

    private /* synthetic */ $$Lambda$DownloadManager$b$fjN3c0qDMj2NLNLS1nrkYqt1Kyg() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int a;
        a = DownloadManager.b.a((Download) obj, (Download) obj2);
        return a;
    }
}
