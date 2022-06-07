package com.xiaomi.passport.ui.settings;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaomi.accountsdk.account.ServerError;
import com.xiaomi.accountsdk.account.data.MiuiActivatorInfo;
import com.xiaomi.accountsdk.account.exception.InvalidPhoneNumException;
import com.xiaomi.accountsdk.account.exception.InvalidVerifyCodeException;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.exception.NeedVerificationException;
import com.xiaomi.accountsdk.account.exception.ReachLimitException;
import com.xiaomi.accountsdk.account.exception.UserRestrictedException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.ParcelableAttackGuardian;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.data.XMPassportInfo;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.Constants;
import com.xiaomi.passport.ui.internal.util.FriendlyFragmentUtils;
import com.xiaomi.passport.ui.settings.SimpleAsyncTask;
import com.xiaomi.passport.ui.settings.utils.UserDataProxy;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;
import java.io.IOException;

/* loaded from: classes4.dex */
public class BindPhoneActivity extends AppCompatActivity {
    private static final String TAG = "BindPhoneActivity";
    String captchaUrl;
    private Account mAccount;
    private InputBindedPhoneFragment mInputBoundPhoneFragment;
    private SimpleAsyncTask<TaskResult> mModifySafeTask;
    private SimpleAsyncTask<TaskResult> mSendModifySafeTicketTask;

    /* loaded from: classes4.dex */
    public interface ModifyPhoneCallback {
        void onError(int i);

        void onError(ServerError serverError);

        void onNeedSMSCode(String str);

        void onSuccess();
    }

    /* loaded from: classes4.dex */
    public interface SendTicketCallback {
        void onError(int i);

        void onError(ServerError serverError);

        void onNeedCaptchaCode(String str);

        void onSuccess();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!new ParcelableAttackGuardian().safeCheck(this)) {
            finish();
            return;
        }
        this.mAccount = MiAccountManager.get(this).getXiaomiAccount();
        if (this.mAccount == null) {
            AccountLog.i(TAG, "no xiaomi account");
            finish();
            return;
        }
        this.mInputBoundPhoneFragment = new InputBindedPhoneFragment();
        this.mInputBoundPhoneFragment.setArguments(getIntent().getExtras());
        FriendlyFragmentUtils.addFragment(getFragmentManager(), 16908290, this.mInputBoundPhoneFragment);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        SimpleAsyncTask<TaskResult> simpleAsyncTask = this.mModifySafeTask;
        if (simpleAsyncTask != null) {
            simpleAsyncTask.cancel(true);
            this.mModifySafeTask = null;
        }
        SimpleAsyncTask<TaskResult> simpleAsyncTask2 = this.mSendModifySafeTicketTask;
        if (simpleAsyncTask2 != null) {
            simpleAsyncTask2.cancel(true);
            this.mSendModifySafeTicketTask = null;
        }
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        InputBindedPhoneFragment inputBindedPhoneFragment = this.mInputBoundPhoneFragment;
        if (inputBindedPhoneFragment != null) {
            inputBindedPhoneFragment.onRequestPermissionsResult(i, strArr, iArr);
        }
    }

    public void modifySafePhone(final String str, final MiuiActivatorInfo miuiActivatorInfo, final String str2, final ModifyPhoneCallback modifyPhoneCallback) {
        SimpleAsyncTask<TaskResult> simpleAsyncTask = this.mModifySafeTask;
        if (simpleAsyncTask == null || !simpleAsyncTask.isRunning()) {
            final Context applicationContext = getApplicationContext();
            this.mModifySafeTask = new SimpleAsyncTask.Builder().setProgressDialogMessage(getFragmentManager(), getString(R.string.just_a_second)).setDoInBackgroundRunnable(new SimpleAsyncTask.DoInBackgroundRunnable<TaskResult>() { // from class: com.xiaomi.passport.ui.settings.BindPhoneActivity.2
                @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.DoInBackgroundRunnable
                public TaskResult run() {
                    IOException e;
                    XMPassportInfo build = XMPassportInfo.build(applicationContext, "passportapi");
                    if (build == null) {
                        AccountLog.w(BindPhoneActivity.TAG, "null passportInfo");
                        return null;
                    }
                    Account xiaomiAccount = MiAccountManager.get(applicationContext).getXiaomiAccount();
                    int i = 5;
                    if (xiaomiAccount == null || TextUtils.isEmpty(xiaomiAccount.name)) {
                        return new TaskResult(null, 5);
                    }
                    UserDataProxy userDataProxy = new UserDataProxy(applicationContext);
                    String userData = userDataProxy.getUserData(BindPhoneActivity.this.mAccount, Constants.ACCOUNT_USER_PHONE);
                    String userData2 = userDataProxy.getUserData(BindPhoneActivity.this.mAccount, Constants.PASSPORT_IDENTITY_AUTH_TOKEN);
                    for (int i2 = 0; i2 < 2; i2++) {
                        try {
                            try {
                                i = 2;
                                try {
                                    CloudHelper.modifySafePhone(build, str, str2, miuiActivatorInfo, !TextUtils.isEmpty(userData), userData2, "passportapi");
                                    return new TaskResult(null, 0);
                                } catch (IOException e2) {
                                    e = e2;
                                    AccountLog.e(BindPhoneActivity.TAG, "modifySafePhone", e);
                                    return new TaskResult(null, i);
                                }
                            } catch (IOException e3) {
                                e = e3;
                                i = 2;
                            }
                        } catch (InvalidPhoneNumException e4) {
                            AccountLog.e(BindPhoneActivity.TAG, "modifySafePhone", e4);
                            i = 17;
                        } catch (InvalidVerifyCodeException e5) {
                            AccountLog.e(BindPhoneActivity.TAG, "modifySafePhone", e5);
                            i = 7;
                        } catch (NeedVerificationException e6) {
                            AccountLog.e(BindPhoneActivity.TAG, "modifySafePhone", e6);
                            i = 15;
                        } catch (UserRestrictedException e7) {
                            AccountLog.e(BindPhoneActivity.TAG, "modifySafePhone", e7);
                            i = 11;
                        } catch (AccessDeniedException e8) {
                            AccountLog.e(BindPhoneActivity.TAG, "modifySafePhone", e8);
                            i = 4;
                        } catch (AuthenticationFailureException e9) {
                            AccountLog.e(BindPhoneActivity.TAG, "modifySafePhone", e9);
                            build.refreshAuthToken(applicationContext);
                            i = 1;
                        } catch (CipherException e10) {
                            AccountLog.e(BindPhoneActivity.TAG, "modifySafePhone", e10);
                            i = 3;
                        } catch (InvalidResponseException e11) {
                            AccountLog.e(BindPhoneActivity.TAG, "modifySafePhone", e11);
                            return new TaskResult(e11.getServerError(), 3);
                        }
                    }
                    return new TaskResult(null, i);
                }
            }).setOnPostExecuteRunnable(new SimpleAsyncTask.OnPostExecuteRunnable<TaskResult>() { // from class: com.xiaomi.passport.ui.settings.BindPhoneActivity.1
                public void run(TaskResult taskResult) {
                    if (taskResult == null) {
                        AccountLog.i(BindPhoneActivity.TAG, "modifySafePhone result is null");
                        return;
                    }
                    AsyncTaskResult asyncTaskResult = new AsyncTaskResult(taskResult.exceptionType);
                    if (asyncTaskResult.getExceptionType() == 15) {
                        modifyPhoneCallback.onNeedSMSCode(str);
                    } else if (!asyncTaskResult.hasException()) {
                        Intent intent = new Intent();
                        intent.putExtra(Constants.ACCOUNT_USER_PHONE, str);
                        BindPhoneActivity.this.setResult(-1, intent);
                        new UserDataProxy(applicationContext).setUserData(BindPhoneActivity.this.mAccount, Constants.ACCOUNT_USER_PHONE, str);
                        Toast.makeText(applicationContext, R.string.set_success, 1).show();
                        UserInfoManager.sendModifyUserPhoneResultByLocalBroadcast(BindPhoneActivity.this.getApplicationContext(), true, -1);
                        BindPhoneActivity.this.finish();
                    } else if (taskResult.serverError == null) {
                        modifyPhoneCallback.onError(asyncTaskResult.getExceptionReason());
                    } else {
                        modifyPhoneCallback.onError(taskResult.serverError);
                    }
                }
            }).build();
            this.mModifySafeTask.executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
            return;
        }
        AccountLog.d(TAG, "modify safe phone task id running");
    }

    public void sendModifySafePhoneTicket(final String str, final String str2, final String str3, final SendTicketCallback sendTicketCallback) {
        SimpleAsyncTask<TaskResult> simpleAsyncTask = this.mSendModifySafeTicketTask;
        if (simpleAsyncTask == null || !simpleAsyncTask.isRunning()) {
            final Context applicationContext = getApplicationContext();
            this.mSendModifySafeTicketTask = new SimpleAsyncTask.Builder().setProgressDialogMessage(getFragmentManager(), getString(R.string.passport_sending_vcode)).setDoInBackgroundRunnable(new SimpleAsyncTask.DoInBackgroundRunnable<TaskResult>() { // from class: com.xiaomi.passport.ui.settings.BindPhoneActivity.4
                @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.DoInBackgroundRunnable
                public TaskResult run() {
                    XMPassportInfo build = XMPassportInfo.build(applicationContext, "passportapi");
                    if (build == null) {
                        AccountLog.w(BindPhoneActivity.TAG, "null passportInfo");
                        return null;
                    }
                    int i = 5;
                    int i2 = 0;
                    while (i2 < 2) {
                        try {
                            CloudHelper.sendModifySafePhoneTicket(build, str, str2, str3, "passportapi");
                            return new TaskResult(null, 0);
                        } catch (InvalidPhoneNumException e) {
                            AccountLog.e(BindPhoneActivity.TAG, "sendModifySafePhoneTicket", e);
                            i = 17;
                        } catch (NeedCaptchaException e2) {
                            AccountLog.e(BindPhoneActivity.TAG, "sendModifySafePhoneTicket", e2);
                            i = 12;
                            BindPhoneActivity.this.captchaUrl = e2.getCaptchaUrl();
                        } catch (ReachLimitException e3) {
                            AccountLog.e(BindPhoneActivity.TAG, "sendModifySafePhoneTicket", e3);
                            i = 10;
                        } catch (AccessDeniedException e4) {
                            AccountLog.e(BindPhoneActivity.TAG, "sendModifySafePhoneTicket", e4);
                            i = 4;
                        } catch (AuthenticationFailureException e5) {
                            AccountLog.e(BindPhoneActivity.TAG, "sendModifySafePhoneTicket", e5);
                            build.refreshAuthToken(applicationContext);
                            i2++;
                            i = 1;
                        } catch (CipherException e6) {
                            AccountLog.e(BindPhoneActivity.TAG, "sendModifySafePhoneTicket", e6);
                            i = 3;
                        } catch (InvalidResponseException e7) {
                            AccountLog.e(BindPhoneActivity.TAG, "sendModifySafePhoneTicket", e7);
                            return new TaskResult(e7.getServerError(), 3);
                        } catch (IOException e8) {
                            AccountLog.e(BindPhoneActivity.TAG, "sendModifySafePhoneTicket", e8);
                            i = 2;
                        }
                    }
                    return new TaskResult(null, i);
                }
            }).setOnPostExecuteRunnable(new SimpleAsyncTask.OnPostExecuteRunnable<TaskResult>() { // from class: com.xiaomi.passport.ui.settings.BindPhoneActivity.3
                public void run(TaskResult taskResult) {
                    if (taskResult == null) {
                        AccountLog.i(BindPhoneActivity.TAG, "send ticket result is null");
                        return;
                    }
                    AsyncTaskResult asyncTaskResult = new AsyncTaskResult(taskResult.exceptionType);
                    if (asyncTaskResult.getExceptionType() == 12) {
                        sendTicketCallback.onNeedCaptchaCode(BindPhoneActivity.this.captchaUrl);
                    } else if (!asyncTaskResult.hasException()) {
                        Toast.makeText(applicationContext, R.string.sms_send_success, 1).show();
                        sendTicketCallback.onSuccess();
                    } else if (taskResult.serverError != null) {
                        sendTicketCallback.onError(taskResult.serverError);
                    } else {
                        sendTicketCallback.onError(asyncTaskResult.getExceptionReason());
                    }
                }
            }).build();
            this.mSendModifySafeTicketTask.executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
            return;
        }
        AccountLog.d(TAG, "send modify phone ticket task is running");
    }

    /* loaded from: classes4.dex */
    public class TaskResult {
        int exceptionType;
        public ServerError serverError;

        TaskResult(ServerError serverError, int i) {
            BindPhoneActivity.this = r1;
            this.serverError = serverError;
            this.exceptionType = i;
        }
    }
}
