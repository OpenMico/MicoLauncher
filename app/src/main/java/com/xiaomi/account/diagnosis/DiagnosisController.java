package com.xiaomi.account.diagnosis;

import android.os.AsyncTask;
import android.text.TextUtils;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.passport.utils.PassportOnlinePreference;
import java.io.IOException;

/* loaded from: classes2.dex */
public class DiagnosisController {
    private String mDomainCache;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Holder {
        static DiagnosisController instance = new DiagnosisController();

        private Holder() {
        }
    }

    private DiagnosisController() {
    }

    public static DiagnosisController get() {
        return Holder.instance;
    }

    /* loaded from: classes2.dex */
    private class CheckDiagnosisEnabledTask extends AsyncTask<Void, Void, Boolean> {
        private final DiagnosisLaunchCallback mCallback;

        private CheckDiagnosisEnabledTask(DiagnosisLaunchCallback diagnosisLaunchCallback) {
            this.mCallback = diagnosisLaunchCallback;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public Boolean doInBackground(Void... voidArr) {
            return Boolean.valueOf(!TextUtils.isEmpty(DiagnosisController.this.queryDomainOnlineConfig()));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
            if (this.mCallback != null) {
                if (bool != null && bool.booleanValue()) {
                    this.mCallback.onLaunch();
                } else {
                    this.mCallback.onError();
                }
            }
        }
    }

    public void checkStart(DiagnosisLaunchCallback diagnosisLaunchCallback) {
        new CheckDiagnosisEnabledTask(diagnosisLaunchCallback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public String getDiagnosisDomain() {
        if (!TextUtils.isEmpty(this.mDomainCache)) {
            return this.mDomainCache;
        }
        return queryDomainOnlineConfig();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String queryDomainOnlineConfig() {
        try {
            this.mDomainCache = PassportOnlinePreference.getOnlineConfig().diagnosisDomain;
            return this.mDomainCache;
        } catch (AccessDeniedException | AuthenticationFailureException | InvalidResponseException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
