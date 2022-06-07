package com.xiaomi.passport.ui.settings;

import android.content.Context;
import android.os.AsyncTask;
import com.xiaomi.accountsdk.account.ServerError;
import com.xiaomi.accountsdk.account.exception.InvalidPhoneNumException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.data.XMPassportInfo;
import java.io.IOException;

/* loaded from: classes4.dex */
public class GetUserBindIdAndLimitTask extends AsyncTask<Void, Void, TaskResult> {
    private final String TAG = "GetUserBindIdAndLimitTask";
    private GetUserBindIdAndLimitCallBack mCallBack;
    private Context mContext;
    private String mInputPhone;

    /* loaded from: classes4.dex */
    public interface GetUserBindIdAndLimitCallBack {
        void onFail(int i);

        void onFail(ServerError serverError);

        void onSuccess(UserBindIdAndLimitResult userBindIdAndLimitResult);
    }

    public GetUserBindIdAndLimitTask(Context context, String str, GetUserBindIdAndLimitCallBack getUserBindIdAndLimitCallBack) {
        this.mContext = context != null ? context.getApplicationContext() : null;
        this.mInputPhone = str;
        this.mCallBack = getUserBindIdAndLimitCallBack;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public TaskResult doInBackground(Void... voidArr) {
        Context context;
        if (this.mCallBack == null || (context = this.mContext) == null) {
            AccountLog.w("GetUserBindIdAndLimitTask", "null callback");
            return null;
        }
        XMPassportInfo build = XMPassportInfo.build(context, "passportapi");
        if (build == null) {
            AccountLog.w("GetUserBindIdAndLimitTask", "null passportInfo");
            return new TaskResult(1, null, null);
        }
        int i = 5;
        int i2 = 0;
        while (true) {
            i = 2;
            if (i2 >= 2) {
                return new TaskResult(Integer.valueOf(i), null, null);
            }
            i = 3;
            try {
                return new TaskResult(0, CloudHelper.getUserBindIdAndLimit(build, this.mInputPhone), null);
            } catch (InvalidPhoneNumException e) {
                i = 17;
                AccountLog.e("GetUserBindIdAndLimitTask", "InvalidPhoneNumException", e);
            } catch (AccessDeniedException e2) {
                i = 4;
                AccountLog.e("GetUserBindIdAndLimitTask", "AccessDeniedException", e2);
                i2++;
            } catch (AuthenticationFailureException e3) {
                AccountLog.e("GetUserBindIdAndLimitTask", "AuthenticationFailureException", e3);
                build.refreshAuthToken(this.mContext);
                i = 1;
            } catch (CipherException e4) {
                AccountLog.e("GetUserBindIdAndLimitTask", "CipherException", e4);
            } catch (InvalidResponseException e5) {
                AccountLog.e("GetUserBindIdAndLimitTask", "InvalidResponseException", e5);
                ServerError serverError = e5.getServerError();
                if (serverError != null) {
                    return new TaskResult(3, null, serverError);
                }
            } catch (IOException e6) {
                AccountLog.e("GetUserBindIdAndLimitTask", "IOException", e6);
                i2++;
            }
            i2++;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPostExecute(TaskResult taskResult) {
        AsyncTaskResult asyncTaskResult = new AsyncTaskResult(taskResult.errorType.intValue());
        if (!asyncTaskResult.hasException()) {
            this.mCallBack.onSuccess(taskResult.userBindIdAndLimitResult);
        } else if (taskResult.serverError != null) {
            this.mCallBack.onFail(taskResult.serverError);
        } else {
            this.mCallBack.onFail(asyncTaskResult.getExceptionReason());
        }
    }

    /* loaded from: classes4.dex */
    public static class UserBindIdAndLimitResult {
        private int times;
        private long ts;
        private String userId;

        public UserBindIdAndLimitResult(String str, long j, int i) {
            this.userId = str;
            this.ts = j;
            this.times = i;
        }

        public String getUserId() {
            return this.userId;
        }

        public long getTime() {
            return this.ts;
        }

        public int getTimes() {
            return this.times;
        }
    }

    /* loaded from: classes4.dex */
    public class TaskResult {
        public Integer errorType;
        public ServerError serverError;
        public UserBindIdAndLimitResult userBindIdAndLimitResult;

        public TaskResult(Integer num, UserBindIdAndLimitResult userBindIdAndLimitResult, ServerError serverError) {
            this.errorType = num;
            this.serverError = serverError;
            this.userBindIdAndLimitResult = userBindIdAndLimitResult;
        }
    }
}
