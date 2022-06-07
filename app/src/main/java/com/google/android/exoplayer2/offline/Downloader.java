package com.google.android.exoplayer2.offline;

import androidx.annotation.Nullable;
import java.io.IOException;

/* loaded from: classes2.dex */
public interface Downloader {

    /* loaded from: classes2.dex */
    public interface ProgressListener {
        void onProgress(long j, long j2, float f);
    }

    void cancel();

    void download(@Nullable ProgressListener progressListener) throws IOException, InterruptedException;

    void remove();
}
