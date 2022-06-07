package com.xiaomi.passport.ui.settings;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaomi.accountsdk.account.ServerError;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.data.IdentityAuthReason;
import com.xiaomi.accountsdk.account.data.NotificationAuthResult;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidParameterException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.data.XMPassportInfo;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.CommonErrorHandler;
import com.xiaomi.passport.ui.internal.Constants;
import com.xiaomi.passport.ui.internal.ProgressHolder;
import com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask;
import com.xiaomi.passport.ui.settings.SimpleAsyncTask;
import com.xiaomi.passport.ui.settings.utils.UserDataProxy;
import com.xiaomi.passport.ui.settings.widget.PasswordView;
import com.xiaomi.passport.utils.AccountHelper;
import com.xiaomi.passport.utils.AuthenticatorUtil;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;
import java.io.IOException;

/* loaded from: classes4.dex */
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_PASSPORT_IDENTITY = 16;
    private static final String TAG = "ChangePasswordActivity";
    private Account mAccount;
    private View mChangeButton;
    private SimpleAsyncTask<TaskResult> mChangePwdTask;
    private PasswordView mConfirmPwdView;
    private View mContentView;
    private TextView mErrorView;
    private GetIdentityAuthUrlTask mGetIdentityUrlTask;
    private IdentityAuthReason mIdentityAuthReason;
    private PasswordView mPwdView;
    final TextWatcher mTextWatcher = new TextWatcher() { // from class: com.xiaomi.passport.ui.settings.ChangePasswordActivity.5
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            ChangePasswordActivity.this.updateErrorMsgView(false, -1);
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.change_pwd_layout);
        this.mContentView = findViewById(16908290);
        this.mChangeButton = findViewById(R.id.change_pwd_btn);
        this.mChangeButton.setOnClickListener(this);
        this.mErrorView = (TextView) findViewById(R.id.error_status);
        this.mPwdView = (PasswordView) findViewById(R.id.input_new_pwd_view);
        this.mPwdView.addTextChangedListener(this.mTextWatcher);
        this.mConfirmPwdView = (PasswordView) findViewById(R.id.confirm_pwd_view);
        this.mConfirmPwdView.addTextChangedListener(this.mTextWatcher);
        this.mAccount = MiAccountManager.get(getApplicationContext()).getXiaomiAccount();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.ChangePasswordActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ChangePasswordActivity.this.onBackPressed();
            }
        });
        setContentVisibility(false);
        if (this.mAccount == null) {
            finish();
        } else {
            changePasswordAuth();
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == null || menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.mAccount = MiAccountManager.get(getApplicationContext()).getXiaomiAccount();
        if (this.mAccount == null) {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        SimpleAsyncTask<TaskResult> simpleAsyncTask = this.mChangePwdTask;
        if (simpleAsyncTask != null) {
            simpleAsyncTask.cancel(true);
            this.mChangePwdTask = null;
        }
        GetIdentityAuthUrlTask getIdentityAuthUrlTask = this.mGetIdentityUrlTask;
        if (getIdentityAuthUrlTask != null) {
            getIdentityAuthUrlTask.cancel(true);
            this.mGetIdentityUrlTask = null;
        }
        super.onDestroy();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.mChangeButton) {
            changePassword(checkNewPwd());
        }
    }

    private String checkNewPwd() {
        String password = this.mPwdView.getPassword();
        if (TextUtils.isEmpty(password)) {
            return null;
        }
        String password2 = this.mConfirmPwdView.getPassword();
        if (TextUtils.isEmpty(password2)) {
            return null;
        }
        if (TextUtils.equals(password, password2)) {
            return password;
        }
        this.mConfirmPwdView.setError(getString(R.string.inconsistent_pwd));
        return null;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        AccountLog.d(TAG, "onActivityResult() requestCode:" + i + " resultCode:" + i2);
        if (i2 != -1) {
            UserInfoManager.sendChangePasswordResultByLocalBroadcast(getApplicationContext(), false, i2);
            setResult(i2);
            finish();
        }
        if (i == 16 && i2 == -1) {
            NotificationAuthResult notificationAuthResult = (NotificationAuthResult) intent.getParcelableExtra("notification_auth_end");
            if (notificationAuthResult != null) {
                new UserDataProxy(this).setUserData(this.mAccount, Constants.PASSPORT_IDENTITY_AUTH_TOKEN, notificationAuthResult.serviceToken);
                onPassIdentityAuth(this.mIdentityAuthReason);
            } else {
                return;
            }
        }
        super.onActivityResult(i, i2, intent);
    }

    private void changePasswordAuth() {
        doIdentityAuth(IdentityAuthReason.CHANGE_PASSWORD);
    }

    private void doIdentityAuth(IdentityAuthReason identityAuthReason) {
        if (this.mGetIdentityUrlTask == null) {
            this.mIdentityAuthReason = identityAuthReason;
            String userData = new UserDataProxy(this).getUserData(this.mAccount, Constants.PASSPORT_IDENTITY_AUTH_TOKEN);
            final ProgressHolder progressHolder = new ProgressHolder();
            progressHolder.showProgress(this);
            this.mGetIdentityUrlTask = new GetIdentityAuthUrlTask(this, userData, identityAuthReason, new GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback() { // from class: com.xiaomi.passport.ui.settings.ChangePasswordActivity.2
                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onFail(int i) {
                    ChangePasswordActivity.this.mGetIdentityUrlTask = null;
                    Toast.makeText(ChangePasswordActivity.this, i, 1).show();
                    progressHolder.dismissProgress();
                }

                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onNeedNotification(String str) {
                    ChangePasswordActivity.this.mGetIdentityUrlTask = null;
                    Intent newNotificationIntent = AuthenticatorUtil.newNotificationIntent(ChangePasswordActivity.this, null, str, "passportapi", true, null);
                    newNotificationIntent.putExtra(BaseConstants.EXTRA_USER_ID, ChangePasswordActivity.this.mAccount.name);
                    newNotificationIntent.putExtra(BaseConstants.EXTRA_PASSTOKEN, AuthenticatorUtil.getPassToken(ChangePasswordActivity.this.getApplicationContext(), ChangePasswordActivity.this.mAccount));
                    ChangePasswordActivity.this.startActivityForResult(newNotificationIntent, 16);
                    ChangePasswordActivity.this.overridePendingTransition(0, 0);
                    progressHolder.dismissProgress();
                }

                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onSuccess() {
                    ChangePasswordActivity.this.mGetIdentityUrlTask = null;
                    ChangePasswordActivity changePasswordActivity = ChangePasswordActivity.this;
                    changePasswordActivity.onPassIdentityAuth(changePasswordActivity.mIdentityAuthReason);
                    progressHolder.dismissProgress();
                }

                @Override // com.xiaomi.passport.ui.settings.GetIdentityAuthUrlTask.GetIdentityAuthUrlCallback
                public void onFail(ServerError serverError) {
                    ChangePasswordActivity.this.mGetIdentityUrlTask = null;
                    if (!ChangePasswordActivity.this.isFinishing()) {
                        CommonErrorHandler.Companion.showErrorWithTips(ChangePasswordActivity.this, serverError);
                    }
                }
            });
            this.mGetIdentityUrlTask.executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.passport.ui.settings.ChangePasswordActivity$6  reason: invalid class name */
    /* loaded from: classes4.dex */
    public static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason = new int[IdentityAuthReason.values().length];

        static {
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason[IdentityAuthReason.CHANGE_PASSWORD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPassIdentityAuth(IdentityAuthReason identityAuthReason) {
        if (identityAuthReason != null && AnonymousClass6.$SwitchMap$com$xiaomi$accountsdk$account$data$IdentityAuthReason[identityAuthReason.ordinal()] == 1) {
            setContentVisibility(true);
        }
    }

    private void setContentVisibility(boolean z) {
        View view = this.mContentView;
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
        }
    }

    private void changePassword(final String str) {
        if (TextUtils.isEmpty(str)) {
            AccountLog.w(TAG, "no valid newPwd");
            return;
        }
        final Context applicationContext = getApplicationContext();
        this.mChangePwdTask = new SimpleAsyncTask.Builder().setProgressDialogMessage(getFragmentManager(), getString(R.string.just_a_second)).setDoInBackgroundRunnable(new SimpleAsyncTask.DoInBackgroundRunnable<TaskResult>() { // from class: com.xiaomi.passport.ui.settings.ChangePasswordActivity.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.passport.ui.settings.SimpleAsyncTask.DoInBackgroundRunnable
            public TaskResult run() {
                SimpleAsyncTask.ResultType resultType;
                Throwable e;
                ServerError serverError;
                XMPassportInfo build = XMPassportInfo.build(applicationContext, "passportapi");
                SimpleAsyncTask.ResultType resultType2 = SimpleAsyncTask.ResultType.ERROR_UNKNOWN;
                try {
                    AccountInfo build2 = new AccountInfo.Builder().userId(build.getUserId()).passToken(CloudHelper.changePassword(build, AuthenticatorUtil.getPassToken(applicationContext, ChangePasswordActivity.this.mAccount), str, new UserDataProxy(applicationContext).getUserData(ChangePasswordActivity.this.mAccount, Constants.PASSPORT_IDENTITY_AUTH_TOKEN), "passportapi")).hasPwd(true).build();
                    AuthenticatorUtil.addOrUpdateAccountManager(applicationContext, build2);
                    ChangePasswordActivity.this.handleResponse(AccountHelper.getAuthenticatorResponseBundle(build2, ChangePasswordActivity.this.getIntent().getBooleanExtra("need_retry_on_authenticator_response_result", false)));
                    return new TaskResult(null, SimpleAsyncTask.ResultType.SUCCESS);
                } catch (InvalidCredentialException e2) {
                    AccountLog.e(ChangePasswordActivity.TAG, "changePassword", e2);
                    resultType = SimpleAsyncTask.ResultType.ERROR_AUTH_FAIL;
                    return new TaskResult(null, resultType);
                } catch (InvalidParameterException e3) {
                    AccountLog.e(ChangePasswordActivity.TAG, "changePassword", e3);
                    resultType = SimpleAsyncTask.ResultType.ERROR_SAME_NEW_AND_OLD_PASS;
                    return new TaskResult(null, resultType);
                } catch (AccessDeniedException e4) {
                    AccountLog.e(ChangePasswordActivity.TAG, "changePassword", e4);
                    resultType = SimpleAsyncTask.ResultType.ERROR_ACCESS_DENIED;
                    return new TaskResult(null, resultType);
                } catch (AuthenticationFailureException e5) {
                    AccountLog.e(ChangePasswordActivity.TAG, "changePassword", e5);
                    resultType = SimpleAsyncTask.ResultType.ERROR_AUTH_FAIL;
                    return new TaskResult(null, resultType);
                } catch (CipherException e6) {
                    e = e6;
                    AccountLog.e(ChangePasswordActivity.TAG, "changePassword", e);
                    resultType = SimpleAsyncTask.ResultType.ERROR_SERVER;
                    if ((e instanceof InvalidResponseException) && (serverError = ((InvalidResponseException) e).getServerError()) != null) {
                        return new TaskResult(serverError, resultType);
                    }
                    return new TaskResult(null, resultType);
                } catch (InvalidResponseException e7) {
                    e = e7;
                    AccountLog.e(ChangePasswordActivity.TAG, "changePassword", e);
                    resultType = SimpleAsyncTask.ResultType.ERROR_SERVER;
                    if (e instanceof InvalidResponseException) {
                        return new TaskResult(serverError, resultType);
                    }
                    return new TaskResult(null, resultType);
                } catch (IOException e8) {
                    AccountLog.e(ChangePasswordActivity.TAG, "changePassword", e8);
                    resultType = SimpleAsyncTask.ResultType.ERROR_NETWORK;
                    return new TaskResult(null, resultType);
                }
            }
        }).setOnPostExecuteRunnable(new SimpleAsyncTask.OnPostExecuteRunnable<TaskResult>() { // from class: com.xiaomi.passport.ui.settings.ChangePasswordActivity.3
            public void run(TaskResult taskResult) {
                if (taskResult.resultType.success()) {
                    Toast.makeText(applicationContext, R.string.set_success, 1).show();
                    ChangePasswordActivity.this.setResult(-1);
                    UserInfoManager.sendChangePasswordResultByLocalBroadcast(ChangePasswordActivity.this.getApplicationContext(), true, -1);
                    ChangePasswordActivity.this.finish();
                    return;
                }
                ChangePasswordActivity.this.updateErrorMsgView(true, taskResult.resultType.getErrorMessageResId());
                if (taskResult.serverError != null && !ChangePasswordActivity.this.isFinishing()) {
                    CommonErrorHandler.Companion.showErrorWithTips(ChangePasswordActivity.this, taskResult.serverError);
                }
            }
        }).build();
        this.mChangePwdTask.executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleResponse(Bundle bundle) {
        Intent intent = getIntent();
        if (intent != null) {
            AuthenticatorUtil.handleAccountAuthenticatorResponse(intent.getParcelableExtra("accountAuthenticatorResponse"), bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateErrorMsgView(boolean z, int i) {
        this.mErrorView.setVisibility(z ? 0 : 8);
        if (i != -1) {
            this.mErrorView.setText(i);
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ChangePasswordActivity.class);
    }

    /* loaded from: classes4.dex */
    public class TaskResult {
        SimpleAsyncTask.ResultType resultType;
        public ServerError serverError;

        TaskResult(ServerError serverError, SimpleAsyncTask.ResultType resultType) {
            this.serverError = serverError;
            this.resultType = resultType;
        }
    }
}
