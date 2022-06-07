package com.xiaomi.smarthome.library.http.async;

import com.xiaomi.smarthome.library.http.Error;
import com.xiaomi.smarthome.library.http.util.CommonUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Call;
import okhttp3.Response;

/* loaded from: classes4.dex */
public abstract class FileAsyncHandler extends AsyncHandler<File, Error> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected static final int BUFFER_SIZE = 4096;
    protected final boolean append;
    protected final File mFile;

    @Override // com.xiaomi.smarthome.library.http.async.AsyncHandler
    public abstract void onFailure(Error error, Exception exc, Response response);

    /* JADX WARN: Can't rename method to resolve collision */
    public abstract void onSuccess(File file, Response response);

    public FileAsyncHandler(File file) {
        this(file, false);
    }

    public FileAsyncHandler(File file, boolean z) {
        checkFileAsyncHandlerValidDirectory(file, false);
        this.mFile = file;
        this.append = z;
    }

    public static boolean checkFileAsyncHandlerValidDirectory(File file, boolean z) {
        if (file == null) {
            if (z) {
                CommonUtil.asserts(false, "File passed into FileAsyncHttpResponseHandler constructor must not be null");
            }
            return false;
        } else if (file.isDirectory()) {
            if (z) {
                CommonUtil.asserts(false, "File passed into FileAsyncHttpResponseHandler constructor must not point to directory");
            }
            return false;
        } else if (file.getParentFile().isDirectory() || file.getParentFile().mkdirs()) {
            return true;
        } else {
            if (z) {
                CommonUtil.asserts(false, "Cannot create parent directories for requested File location");
            }
            return false;
        }
    }

    protected File getTargetFile() {
        return this.mFile;
    }

    @Override // com.xiaomi.smarthome.library.http.async.AsyncHandler
    public void processResponse(Response response) {
        if (response.isSuccessful()) {
            try {
                InputStream byteStream = response.body().byteStream();
                long contentLength = response.body().contentLength();
                FileOutputStream fileOutputStream = new FileOutputStream(getTargetFile(), this.append);
                if (byteStream != null) {
                    byte[] bArr = new byte[4096];
                    int i = 0;
                    while (true) {
                        int read = byteStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        i += read;
                        fileOutputStream.write(bArr, 0, read);
                        sendProgressMessage(i, contentLength);
                    }
                    CommonUtil.silentCloseInputStream(byteStream);
                    fileOutputStream.flush();
                    CommonUtil.silentCloseOutputStream(fileOutputStream);
                }
                sendSuccessMessage(getTargetFile(), response);
            } catch (Exception e) {
                sendFailureMessage(new Error(-1, ""), e, response);
            }
        } else {
            sendFailureMessage(new Error(response.code(), ""), null, response);
        }
    }

    @Override // com.xiaomi.smarthome.library.http.async.AsyncHandler
    public void processFailure(Call call, IOException iOException) {
        sendFailureMessage(new Error(-1, ""), iOException, null);
    }
}
