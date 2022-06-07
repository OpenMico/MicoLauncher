package com.xiaomi.passport.ui.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.ServerError;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.IdentityAuthReason;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.data.XMPassportInfo;
import java.io.IOException;

/* loaded from: classes4.dex */
public class GetIdentityAuthUrlTask extends AsyncTask<Void, Void, TaskResult> {
    private final String TAG = "GetIdentityAuthUrlTask";
    private String mAuthST;
    private GetIdentityAuthUrlCallback mCallback;
    @SuppressLint({"StaticFieldLeak"})
    private Context mContext;
    private IdentityAuthReason mIdentityAuthType;

    /* loaded from: classes4.dex */
    public interface GetIdentityAuthUrlCallback {
        void onFail(int i);

        void onFail(ServerError serverError);

        void onNeedNotification(String str);

        void onSuccess();
    }

    public GetIdentityAuthUrlTask(Context context, String str, IdentityAuthReason identityAuthReason, GetIdentityAuthUrlCallback getIdentityAuthUrlCallback) {
        this.mContext = context != null ? context.getApplicationContext() : null;
        this.mAuthST = str;
        this.mIdentityAuthType = identityAuthReason;
        this.mCallback = getIdentityAuthUrlCallback;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public TaskResult doInBackground(Void... voidArr) {
        Context context;
        if (this.mCallback == null || (context = this.mContext) == null) {
            AccountLog.w("GetIdentityAuthUrlTask", "null callback");
            return null;
        }
        XMPassportInfo build = XMPassportInfo.build(context, "passportapi");
        if (build == null) {
            AccountLog.w("GetIdentityAuthUrlTask", "null passportInfo");
            return null;
        }
        int i = 5;
        int i2 = 0;
        while (i2 < 2) {
            try {
                return new TaskResult(XMPassport.getIdentityAuthUrl(build, this.mAuthST, this.mIdentityAuthType), 0, null);
            } catch (AccessDeniedException e) {
                AccountLog.e("GetIdentityAuthUrlTask", "AccessDeniedException", e);
                i = 4;
            } catch (AuthenticationFailureException e2) {
                AccountLog.e("GetIdentityAuthUrlTask", "AuthenticationFailureException", e2);
                build.refreshAuthToken(this.mContext);
                i2++;
                i = 1;
            } catch (CipherException e3) {
                AccountLog.e("GetIdentityAuthUrlTask", "CipherException", e3);
                i = 3;
            } catch (InvalidResponseException e4) {
                AccountLog.e("GetIdentityAuthUrlTask", "InvalidResponseException", e4);
                ServerError serverError = e4.getServerError();
                if (serverError != null) {
                    return new TaskResult(null, 3, serverError);
                }
                i = 3;
            } catch (IOException e5) {
                AccountLog.e("GetIdentityAuthUrlTask", "IOException", e5);
                i = 2;
            }
        }
        return new TaskResult(null, i, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPostExecute(TaskResult taskResult) {
        super.onPostExecute((GetIdentityAuthUrlTask) taskResult);
        if (taskResult == null) {
            AccountLog.w("GetIdentityAuthUrlTask", "null result");
        } else if (taskResult.hasException()) {
            if (taskResult.getServerError() != null) {
                this.mCallback.onFail(taskResult.getServerError());
            } else {
                this.mCallback.onFail(taskResult.getExceptionReason());
            }
        } else if (TextUtils.isEmpty(taskResult.getNotificationUrl())) {
            this.mCallback.onSuccess();
        } else {
            this.mCallback.onNeedNotification(taskResult.getNotificationUrl());
        }
    }

    /* loaded from: classes4.dex */
    public class TaskResult {
        private AsyncTaskResult asyncTaskResult;
        private String notificationUrl;
        private ServerError serverError;

        public TaskResult(String str, int i, ServerError serverError) {
            this.asyncTaskResult = new AsyncTaskResult(i);
            this.notificationUrl = str;
            this.serverError = serverError;
        }

        public boolean hasException() {
            AsyncTaskResult asyncTaskResult = this.asyncTaskResult;
            return asyncTaskResult != null && asyncTaskResult.hasException();
        }

        public String getNotificationUrl() {
            return this.notificationUrl;
        }

        public int getExceptionReason() {
            return this.asyncTaskResult.getExceptionReason();
        }

        public ServerError getServerError() {
            return this.serverError;
        }
    }
}
