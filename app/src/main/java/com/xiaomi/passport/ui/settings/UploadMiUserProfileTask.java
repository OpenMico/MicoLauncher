package com.xiaomi.passport.ui.settings;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;
import com.xiaomi.accountsdk.account.ServerError;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.Gender;
import com.xiaomi.accountsdk.account.data.XiaomiUserProfile;
import com.xiaomi.accountsdk.account.exception.InvalidParameterException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.data.XMPassportInfo;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.CommonErrorHandler;
import java.io.IOException;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class UploadMiUserProfileTask extends AsyncTask<Void, Void, UploadMiUserProfileTaskResult> {
    private static final String TAG = "UploadMiUserProfileTask";
    private WeakReference<Activity> activityWeakReference;
    private Gender gender;
    private IUploadUserProfile uploadUserProfile;
    private String userName;

    /* loaded from: classes4.dex */
    public interface IUploadUserProfile {
        void onFinishUploading(String str, Gender gender);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public UploadMiUserProfileTask(Activity activity, String str, Gender gender, IUploadUserProfile iUploadUserProfile) {
        this.userName = str;
        this.gender = gender;
        this.uploadUserProfile = iUploadUserProfile;
        this.activityWeakReference = new WeakReference<>(activity);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public UploadMiUserProfileTaskResult doInBackground(Void... voidArr) {
        Activity activity = this.activityWeakReference.get();
        if (activity == null) {
            AccountLog.w(TAG, "context is null");
            return new UploadMiUserProfileTaskResult(null, 5);
        }
        Context applicationContext = activity.getApplicationContext();
        XMPassportInfo build = XMPassportInfo.build(applicationContext, "passportapi");
        if (build == null) {
            AccountLog.w(TAG, "null passportInfo");
            return new UploadMiUserProfileTaskResult(null, 5);
        }
        int i = 0;
        i = 5;
        int i2 = 0;
        while (i2 < 2) {
            try {
                XMPassport.uploadXiaomiUserProfile(build, new XiaomiUserProfile(build.getUserId(), this.userName, null, this.gender));
                break;
            } catch (InvalidParameterException e) {
                AccountLog.e(TAG, "UploadUserInfoTask error", e);
                i = 16;
            } catch (AccessDeniedException e2) {
                AccountLog.e(TAG, "UploadUserInfoTask error", e2);
                i = 4;
            } catch (AuthenticationFailureException e3) {
                AccountLog.e(TAG, "UploadUserInfoTask error", e3);
                build.refreshAuthToken(applicationContext);
                i2++;
                i = 1;
            } catch (CipherException e4) {
                AccountLog.e(TAG, "UploadUserInfoTask error", e4);
                i = 3;
            } catch (InvalidResponseException e5) {
                AccountLog.e(TAG, "UploadUserInfoTask error", e5);
                ServerError serverError = e5.getServerError();
                if (serverError != null) {
                    return new UploadMiUserProfileTaskResult(serverError, 3);
                }
                i = 3;
            } catch (IOException e6) {
                AccountLog.e(TAG, "UploadUserInfoTask error", e6);
                i = 2;
            }
        }
        return new UploadMiUserProfileTaskResult(null, Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onPostExecute(UploadMiUserProfileTaskResult uploadMiUserProfileTaskResult) {
        int i;
        super.onPostExecute((UploadMiUserProfileTask) uploadMiUserProfileTaskResult);
        AsyncTaskResult asyncTaskResult = new AsyncTaskResult(uploadMiUserProfileTaskResult.exceptionType.intValue());
        if (asyncTaskResult.hasException()) {
            if (uploadMiUserProfileTaskResult.exceptionType.intValue() != 16 || TextUtils.isEmpty(this.userName)) {
                i = asyncTaskResult.getExceptionReason();
            } else {
                i = R.string.account_error_user_name;
            }
            Activity activity = this.activityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                if (uploadMiUserProfileTaskResult.serverError != null) {
                    CommonErrorHandler.Companion.showErrorWithTips(activity, uploadMiUserProfileTaskResult.serverError);
                } else {
                    Toast.makeText(activity, i, 0).show();
                }
            }
        } else {
            this.uploadUserProfile.onFinishUploading(this.userName, this.gender);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public class UploadMiUserProfileTaskResult {
        private Integer exceptionType;
        public ServerError serverError;

        private UploadMiUserProfileTaskResult(ServerError serverError, Integer num) {
            this.serverError = serverError;
            this.exceptionType = num;
        }
    }
}
