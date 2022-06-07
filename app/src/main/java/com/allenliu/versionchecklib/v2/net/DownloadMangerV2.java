package com.allenliu.versionchecklib.v2.net;

import android.os.Handler;
import android.os.Looper;
import com.allenliu.versionchecklib.callback.DownloadListener;
import com.allenliu.versionchecklib.core.http.AllenHttp;
import com.allenliu.versionchecklib.core.http.FileCallBack;
import java.io.File;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes.dex */
public class DownloadMangerV2 {
    public static void download(String str, String str2, String str3, final DownloadListener downloadListener) {
        if (str == null || str.isEmpty()) {
            throw new RuntimeException("you must set download url for download function using");
        }
        Request build = new Request.Builder().url(str).build();
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.allenliu.versionchecklib.v2.net.DownloadMangerV2.1
            @Override // java.lang.Runnable
            public void run() {
                DownloadListener downloadListener2 = DownloadListener.this;
                if (downloadListener2 != null) {
                    downloadListener2.onCheckerStartDownload();
                }
            }
        });
        AllenHttp.getHttpClient().newCall(build).enqueue(new FileCallBack(str2, str3) { // from class: com.allenliu.versionchecklib.v2.net.DownloadMangerV2.2
            @Override // com.allenliu.versionchecklib.core.http.FileCallBack
            public void onSuccess(final File file, Call call, Response response) {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.allenliu.versionchecklib.v2.net.DownloadMangerV2.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (downloadListener != null) {
                            downloadListener.onCheckerDownloadSuccess(file);
                        }
                    }
                });
            }

            @Override // com.allenliu.versionchecklib.core.http.FileCallBack
            public void onDownloading(final int i) {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.allenliu.versionchecklib.v2.net.DownloadMangerV2.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (downloadListener != null) {
                            downloadListener.onCheckerDownloading(i);
                        }
                    }
                });
            }

            @Override // com.allenliu.versionchecklib.core.http.FileCallBack
            public void onDownloadFailed() {
                DownloadMangerV2.b(downloadListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(final DownloadListener downloadListener) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.allenliu.versionchecklib.v2.net.DownloadMangerV2.3
            @Override // java.lang.Runnable
            public void run() {
                DownloadListener downloadListener2 = DownloadListener.this;
                if (downloadListener2 != null) {
                    downloadListener2.onCheckerDownloadFail();
                }
            }
        });
    }
}
