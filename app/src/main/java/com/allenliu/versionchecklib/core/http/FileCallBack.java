package com.allenliu.versionchecklib.core.http;

import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/* loaded from: classes.dex */
public abstract class FileCallBack implements Callback {
    private String a;
    private String b;
    private Handler c = new Handler(Looper.getMainLooper());

    public abstract void onDownloadFailed();

    public abstract void onDownloading(int i);

    public abstract void onSuccess(File file, Call call, Response response);

    public FileCallBack(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    @Override // okhttp3.Callback
    public void onFailure(Call call, IOException iOException) {
        this.c.post(new Runnable() { // from class: com.allenliu.versionchecklib.core.http.FileCallBack.1
            @Override // java.lang.Runnable
            public void run() {
                FileCallBack.this.onDownloadFailed();
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00ba A[Catch: IOException -> 0x00b6, TRY_LEAVE, TryCatch #3 {IOException -> 0x00b6, blocks: (B:43:0x00b2, B:47:0x00ba), top: B:50:0x00b2 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00b2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // okhttp3.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onResponse(final okhttp3.Call r12, final okhttp3.Response r13) throws java.io.IOException {
        /*
            r11 = this;
            r0 = 2048(0x800, float:2.87E-42)
            byte[] r0 = new byte[r0]
            java.io.File r1 = new java.io.File
            java.lang.String r2 = r11.a
            r1.<init>(r2)
            boolean r2 = r1.exists()
            if (r2 != 0) goto L_0x0014
            r1.mkdirs()
        L_0x0014:
            r1 = 0
            okhttp3.ResponseBody r2 = r13.body()     // Catch: Exception -> 0x008d, all -> 0x0089
            java.io.InputStream r2 = r2.byteStream()     // Catch: Exception -> 0x008d, all -> 0x0089
            okhttp3.ResponseBody r3 = r13.body()     // Catch: Exception -> 0x0085, all -> 0x0082
            r3.contentLength()     // Catch: Exception -> 0x0085, all -> 0x0082
            java.io.File r3 = new java.io.File     // Catch: Exception -> 0x0085, all -> 0x0082
            java.lang.String r4 = r11.a     // Catch: Exception -> 0x0085, all -> 0x0082
            java.lang.String r5 = r11.b     // Catch: Exception -> 0x0085, all -> 0x0082
            r3.<init>(r4, r5)     // Catch: Exception -> 0x0085, all -> 0x0082
            boolean r4 = r3.exists()     // Catch: Exception -> 0x0085, all -> 0x0082
            if (r4 == 0) goto L_0x0037
            r3.delete()     // Catch: Exception -> 0x0085, all -> 0x0082
            goto L_0x003a
        L_0x0037:
            r3.createNewFile()     // Catch: Exception -> 0x0085, all -> 0x0082
        L_0x003a:
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: Exception -> 0x0085, all -> 0x0082
            r4.<init>(r3)     // Catch: Exception -> 0x0085, all -> 0x0082
            r5 = 0
        L_0x0041:
            int r1 = r2.read(r0)     // Catch: Exception -> 0x0080, all -> 0x007e
            r7 = -1
            if (r1 == r7) goto L_0x0068
            okhttp3.ResponseBody r7 = r13.body()     // Catch: Exception -> 0x0080, all -> 0x007e
            long r7 = r7.contentLength()     // Catch: Exception -> 0x0080, all -> 0x007e
            r9 = 0
            r4.write(r0, r9, r1)     // Catch: Exception -> 0x0080, all -> 0x007e
            long r9 = (long) r1     // Catch: Exception -> 0x0080, all -> 0x007e
            long r5 = r5 + r9
            double r9 = (double) r5     // Catch: Exception -> 0x0080, all -> 0x007e
            double r7 = (double) r7     // Catch: Exception -> 0x0080, all -> 0x007e
            double r9 = r9 / r7
            r7 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r9 = r9 * r7
            int r1 = (int) r9     // Catch: Exception -> 0x0080, all -> 0x007e
            android.os.Handler r7 = r11.c     // Catch: Exception -> 0x0080, all -> 0x007e
            com.allenliu.versionchecklib.core.http.FileCallBack$2 r8 = new com.allenliu.versionchecklib.core.http.FileCallBack$2     // Catch: Exception -> 0x0080, all -> 0x007e
            r8.<init>()     // Catch: Exception -> 0x0080, all -> 0x007e
            r7.post(r8)     // Catch: Exception -> 0x0080, all -> 0x007e
            goto L_0x0041
        L_0x0068:
            r4.flush()     // Catch: Exception -> 0x0080, all -> 0x007e
            android.os.Handler r0 = r11.c     // Catch: Exception -> 0x0080, all -> 0x007e
            com.allenliu.versionchecklib.core.http.FileCallBack$3 r1 = new com.allenliu.versionchecklib.core.http.FileCallBack$3     // Catch: Exception -> 0x0080, all -> 0x007e
            r1.<init>()     // Catch: Exception -> 0x0080, all -> 0x007e
            r0.post(r1)     // Catch: Exception -> 0x0080, all -> 0x007e
            if (r2 == 0) goto L_0x007a
            r2.close()     // Catch: IOException -> 0x00a2
        L_0x007a:
            r4.close()     // Catch: IOException -> 0x00a2
            goto L_0x00ad
        L_0x007e:
            r12 = move-exception
            goto L_0x00b0
        L_0x0080:
            r12 = move-exception
            goto L_0x0087
        L_0x0082:
            r12 = move-exception
            r4 = r1
            goto L_0x00b0
        L_0x0085:
            r12 = move-exception
            r4 = r1
        L_0x0087:
            r1 = r2
            goto L_0x008f
        L_0x0089:
            r12 = move-exception
            r2 = r1
            r4 = r2
            goto L_0x00b0
        L_0x008d:
            r12 = move-exception
            r4 = r1
        L_0x008f:
            r12.printStackTrace()     // Catch: all -> 0x00ae
            android.os.Handler r12 = r11.c     // Catch: all -> 0x00ae
            com.allenliu.versionchecklib.core.http.FileCallBack$4 r13 = new com.allenliu.versionchecklib.core.http.FileCallBack$4     // Catch: all -> 0x00ae
            r13.<init>()     // Catch: all -> 0x00ae
            r12.post(r13)     // Catch: all -> 0x00ae
            if (r1 == 0) goto L_0x00a4
            r1.close()     // Catch: IOException -> 0x00a2
            goto L_0x00a4
        L_0x00a2:
            r12 = move-exception
            goto L_0x00aa
        L_0x00a4:
            if (r4 == 0) goto L_0x00ad
            r4.close()     // Catch: IOException -> 0x00a2
            goto L_0x00ad
        L_0x00aa:
            r12.printStackTrace()
        L_0x00ad:
            return
        L_0x00ae:
            r12 = move-exception
            r2 = r1
        L_0x00b0:
            if (r2 == 0) goto L_0x00b8
            r2.close()     // Catch: IOException -> 0x00b6
            goto L_0x00b8
        L_0x00b6:
            r13 = move-exception
            goto L_0x00be
        L_0x00b8:
            if (r4 == 0) goto L_0x00c1
            r4.close()     // Catch: IOException -> 0x00b6
            goto L_0x00c1
        L_0x00be:
            r13.printStackTrace()
        L_0x00c1:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.allenliu.versionchecklib.core.http.FileCallBack.onResponse(okhttp3.Call, okhttp3.Response):void");
    }
}
