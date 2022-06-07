package com.xiaomi.account.diagnosis.task;

import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.account.diagnosis.DiagnosisConstants;
import com.xiaomi.account.diagnosis.DiagnosisController;
import com.xiaomi.account.diagnosis.upload.FDSUploader;
import com.xiaomi.account.diagnosis.util.FileUtils;
import com.xiaomi.account.diagnosis.util.LogcatCollector;
import com.xiaomi.account.diagnosis.util.ZipUtils;
import com.xiaomi.accountsdk.utils.XMPassportUtil;
import com.xiaomi.infra.galaxy.fds.android.model.PutObjectResult;
import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/* loaded from: classes2.dex */
public class CollectAndUploadDiagnosisTask extends AsyncTask<Void, Void, String> {
    private final Callback mCallback;
    private Random mRandom = new Random();
    private final boolean mUseDefaultDiagnosisDomain;

    /* loaded from: classes2.dex */
    public interface Callback {
        void onFinished(boolean z, String str);
    }

    public CollectAndUploadDiagnosisTask(Callback callback, boolean z) {
        this.mCallback = callback;
        this.mUseDefaultDiagnosisDomain = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String doInBackground(Void... voidArr) {
        PutObjectResult putObjectResult;
        String diagnosisDomain = DiagnosisController.get().getDiagnosisDomain();
        if (TextUtils.isEmpty(diagnosisDomain) && !this.mUseDefaultDiagnosisDomain) {
            return null;
        }
        File collectLogcat = LogcatCollector.collectLogcat();
        File passportCacheDir = DiagnosisConstants.getPassportCacheDir();
        ArrayList arrayList = new ArrayList();
        File[] listFiles = passportCacheDir.listFiles();
        for (File file : listFiles) {
            if (file.isFile() && file.getName().endsWith(".log")) {
                arrayList.add(file);
            }
        }
        arrayList.add(collectLogcat);
        String generateTraceId = generateTraceId();
        File file2 = new File(DiagnosisConstants.getZipSubDir(), getFileNameByInfo(generateTraceId) + ".zip");
        File parentFile = file2.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            ZipUtils.zipFiles((File[]) arrayList.toArray(new File[0]), file2);
            putObjectResult = FDSUploader.upload(file2, diagnosisDomain);
        } catch (IOException e) {
            e.printStackTrace();
            putObjectResult = null;
        }
        try {
            FileUtils.cleanDirectory(file2.getParentFile());
            FileUtils.cleanDirectory(collectLogcat.getParentFile());
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (putObjectResult == null) {
            return null;
        }
        return generateTraceId;
    }

    private String generateTraceId() {
        return String.valueOf(this.mRandom.nextInt(1000000));
    }

    private String getFileNameByInfo(String str) {
        return str + Constants.ACCEPT_TIME_SEPARATOR_SERVER + getModelWithBase64() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + XMPassportUtil.getISOLocaleString(Locale.getDefault()) + Constants.ACCEPT_TIME_SEPARATOR_SERVER + (System.currentTimeMillis() / 1000);
    }

    private String getModelWithBase64() {
        return Base64.encodeToString(Build.MODEL.getBytes(), 10);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPostExecute(String str) {
        this.mCallback.onFinished(!TextUtils.isEmpty(str), str);
    }
}
