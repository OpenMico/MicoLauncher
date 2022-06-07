package com.google.android.exoplayer2.offline;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import java.io.IOException;

@WorkerThread
/* loaded from: classes2.dex */
public interface DownloadIndex {
    @Nullable
    Download getDownload(String str) throws IOException;

    DownloadCursor getDownloads(int... iArr) throws IOException;
}
