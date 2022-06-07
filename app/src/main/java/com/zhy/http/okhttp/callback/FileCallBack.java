package com.zhy.http.okhttp.callback;

import com.zhy.http.okhttp.OkHttpUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Response;

/* loaded from: classes4.dex */
public abstract class FileCallBack extends Callback<File> {
    private String a;
    private String b;

    public FileCallBack(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.zhy.http.okhttp.callback.Callback
    public File parseNetworkResponse(Response response, int i) throws Exception {
        return saveFile(response, i);
    }

    public File saveFile(Response response, final int i) throws IOException {
        InputStream inputStream;
        Throwable th;
        byte[] bArr = new byte[2048];
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = response.body().byteStream();
            try {
                final long contentLength = response.body().contentLength();
                long j = 0;
                File file = new File(this.a);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File(file, this.b);
                FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                while (true) {
                    try {
                        int read = inputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        final long j2 = j + read;
                        fileOutputStream2.write(bArr, 0, read);
                        OkHttpUtils.getInstance().getDelivery().execute(new Runnable() { // from class: com.zhy.http.okhttp.callback.FileCallBack.1
                            @Override // java.lang.Runnable
                            public void run() {
                                FileCallBack fileCallBack = FileCallBack.this;
                                long j3 = contentLength;
                                fileCallBack.inProgress((((float) j2) * 1.0f) / ((float) j3), j3, i);
                            }
                        });
                        j = j2;
                        bArr = bArr;
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = fileOutputStream2;
                        try {
                            response.body().close();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        } catch (IOException unused) {
                        }
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException unused2) {
                            }
                        }
                        throw th;
                    }
                }
                fileOutputStream2.flush();
                try {
                    response.body().close();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException unused3) {
                }
                try {
                    fileOutputStream2.close();
                } catch (IOException unused4) {
                }
                return file2;
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            inputStream = null;
        }
    }
}
