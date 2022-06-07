package com.google.android.exoplayer2.offline;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import java.io.File;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class ActionFileUpgradeUtil {

    /* loaded from: classes2.dex */
    public interface DownloadIdProvider {
        String getId(DownloadRequest downloadRequest);
    }

    private ActionFileUpgradeUtil() {
    }

    @WorkerThread
    public static void upgradeAndDelete(File file, @Nullable DownloadIdProvider downloadIdProvider, DefaultDownloadIndex defaultDownloadIndex, boolean z, boolean z2) throws IOException {
        a aVar = new a(file);
        if (aVar.a()) {
            try {
                long currentTimeMillis = System.currentTimeMillis();
                DownloadRequest[] c = aVar.c();
                for (DownloadRequest downloadRequest : c) {
                    if (downloadIdProvider != null) {
                        downloadRequest = downloadRequest.copyWithId(downloadIdProvider.getId(downloadRequest));
                    }
                    a(downloadRequest, defaultDownloadIndex, z2, currentTimeMillis);
                }
                aVar.b();
            } catch (Throwable th) {
                if (z) {
                    aVar.b();
                }
                throw th;
            }
        }
    }

    static void a(DownloadRequest downloadRequest, DefaultDownloadIndex defaultDownloadIndex, boolean z, long j) throws IOException {
        Download download;
        Download download2 = defaultDownloadIndex.getDownload(downloadRequest.id);
        if (download2 != null) {
            download = DownloadManager.a(download2, downloadRequest, download2.stopReason, j);
        } else {
            download = new Download(downloadRequest, z ? 3 : 0, j, j, -1L, 0, 0);
        }
        defaultDownloadIndex.putDownload(download);
    }
}
