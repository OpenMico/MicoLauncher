package com.xiaomi.passport.ui.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.data.MetaLoginData;
import com.xiaomi.accountsdk.account.data.PasswordLoginParams;
import com.xiaomi.accountsdk.account.data.Step2LoginParams;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.MiuiOsBuildReflection;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.AlertDialog;
import com.xiaomi.passport.ui.internal.PassportGroupEditText;
import com.xiaomi.passport.ui.internal.util.LoginUIController;
import com.xiaomi.passport.ui.internal.util.SysHelper;
import com.xiaomi.passport.ui.settings.SimpleDialogFragment;
import com.xiaomi.passport.utils.AccountHelper;
import com.xiaomi.passport.utils.AuthenticatorUtil;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class QuickLoginFragment extends Fragment implements View.OnClickListener {
    private static final int NOTIFICATION_ID_VERIFICATION_INCOMPLETE = 1000;
    protected static final int REQUEST_NOTIFICATION_LOGIN = 2;
    private static final String TAG = "QuickLoginFragment";
    private Button mButtonCancel;
    private Button mButtonConfirm;
    protected String mCaptchaUrl;
    private CaptchaView mCaptchaView;
    protected TextView mForgetPwdView;
    private View mInnerContentStep2View;
    private View mInnerContentView;
    private LoginUIController mLoginUIController;
    protected volatile MetaLoginData mMetaLoginDataStep2;
    protected PassportGroupEditText mPasswordView;
    private boolean mReturnStsUrl;
    protected String mServiceId;
    private boolean mShowPassword;
    protected ImageView mShowPwdImageView;
    protected volatile String mStep1Token;
    protected String mTitle;
    private TextView mTitleView;
    private CheckBox mTrustDeviceView;
    private String mUserId;
    protected EditText mVCodeView;
    private boolean mVerifyOnly;
    private final AtomicBoolean responseHandled = new AtomicBoolean(false);

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
        Bundle arguments = getArguments();
        if (arguments == null) {
            AccountLog.d(TAG, "extra options is null");
            finish();
            return;
        }
        this.mUserId = arguments.getString(BaseConstants.EXTRA_USER_ID);
        if (TextUtils.isEmpty(this.mUserId)) {
            AccountLog.d(TAG, "extra user is null");
            finish();
            return;
        }
        this.mVerifyOnly = arguments.getBoolean("verify_only", false);
        this.mServiceId = arguments.getString("service_id", "passportapi");
        this.mStep1Token = arguments.getString("extra_step1_token");
        this.mReturnStsUrl = arguments.getBoolean(AccountIntent.EXTRA_RETURN_STS_URL, false);
        this.mLoginUIController = new LoginUIController(getActivity());
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i = 0;
        View inflate = layoutInflater.inflate(R.layout.passport_quick_login, viewGroup, false);
        this.mButtonCancel = (Button) inflate.findViewById(R.id.cancel);
        this.mButtonConfirm = (Button) inflate.findViewById(R.id.passport_confirm);
        this.mPasswordView = (PassportGroupEditText) inflate.findViewById(R.id.et_account_password);
        this.mPasswordView.setStyle(PassportGroupEditText.Style.SingleItem);
        this.mForgetPwdView = (TextView) inflate.findViewById(R.id.tv_forget_pwd);
        this.mShowPwdImageView = (ImageView) inflate.findViewById(R.id.show_password_img);
        this.mCaptchaView = (CaptchaView) inflate.findViewById(R.id.captcha_layout);
        this.mInnerContentView = inflate.findViewById(R.id.inner_content);
        this.mInnerContentStep2View = inflate.findViewById(R.id.inner_content_step2);
        this.mVCodeView = (EditText) inflate.findViewById(R.id.passport_vcode);
        this.mTrustDeviceView = (CheckBox) inflate.findViewById(R.id.passport_trust_device);
        this.mTitleView = (TextView) inflate.findViewById(16908310);
        this.mButtonCancel.setOnClickListener(this);
        this.mButtonConfirm.setOnClickListener(this);
        this.mForgetPwdView.setOnClickListener(this);
        this.mShowPwdImageView.setOnClickListener(this);
        this.mShowPassword = false;
        updateShowPasswordState();
        Bundle arguments = getArguments();
        if (arguments == null) {
            finish();
            return inflate;
        }
        String string = arguments.getString("extra_sign");
        String string2 = arguments.getString("extra_qs");
        String string3 = arguments.getString("extra_callback");
        if (!(string == null || string2 == null || string3 == null)) {
            this.mMetaLoginDataStep2 = new MetaLoginData(string, string2, string3);
        }
        this.mTitle = arguments.getString("title") == null ? getString(R.string.passport_quick_login_title) : arguments.getString("title");
        String string4 = arguments.getString("captcha_url");
        if (!TextUtils.isEmpty(string4)) {
            applyCaptchaUrl(string4);
        }
        ((TextView) inflate.findViewById(R.id.passport_account_name)).setText(getString(R.string.passport_account_name, this.mUserId));
        String string5 = arguments.getString("password");
        this.mPasswordView.setText(string5);
        PassportGroupEditText passportGroupEditText = this.mPasswordView;
        if (!TextUtils.isEmpty(string5)) {
            i = string5.length();
        }
        passportGroupEditText.setSelection(i);
        switchStage();
        setWindowSize();
        return inflate;
    }

    public void setWindowSize() {
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (MiuiOsBuildReflection.isTablet()) {
            attributes.width = getResources().getDimensionPixelSize(R.dimen.passport_quick_login_dialog_width);
        } else {
            attributes.gravity = 80;
            attributes.width = -1;
        }
        window.setAttributes(attributes);
    }

    @Override // android.app.Fragment
    @TargetApi(16)
    public void onStop() {
        if (AccountIntent.PACKAGE_XIAOMI_ACCOUNT.equals(getActivity().getPackageName()) && this.mStep1Token != null) {
            Intent intent = new Intent(getActivity(), getActivity().getClass());
            intent.putExtra("service_id", this.mServiceId);
            intent.putExtra("extra_step1_token", this.mStep1Token);
            intent.putExtra("extra_sign", this.mMetaLoginDataStep2.sign);
            intent.putExtra("extra_qs", this.mMetaLoginDataStep2.qs);
            intent.putExtra("extra_callback", this.mMetaLoginDataStep2.callback);
            ((NotificationManager) getActivity().getSystemService("notification")).notify(1000, new Notification.Builder(getActivity()).setAutoCancel(false).setSmallIcon(17301642).setContentIntent(PendingIntent.getActivity(getActivity(), 0, intent, 134217728)).setContentTitle(getString(R.string.passport_vcode_notification_title)).setContentText(getString(R.string.passport_vcode_prompt_long)).build());
        }
        super.onStop();
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        ((NotificationManager) getActivity().getSystemService("notification")).cancel(1000);
    }

    private void finish() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        GetBackPasswordExecutor.stopIfNeeded();
        LoginUIController loginUIController = this.mLoginUIController;
        if (loginUIController != null) {
            loginUIController.cancel();
            this.mLoginUIController = null;
        }
        super.onDestroy();
    }

    protected void handleResponseIfNeeded(Bundle bundle) {
        Bundle arguments;
        if (this.responseHandled.compareAndSet(false, true) && (arguments = getArguments()) != null) {
            AuthenticatorUtil.handleAccountAuthenticatorResponse(arguments.getParcelable("accountAuthenticatorResponse"), bundle);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.mButtonCancel == view) {
            finish();
        } else if (this.mButtonConfirm == view) {
            confirm();
        } else if (this.mForgetPwdView == view) {
            GetBackPasswordExecutor.execute(getActivity());
        } else if (this.mShowPwdImageView == view) {
            this.mShowPassword = !this.mShowPassword;
            updateShowPasswordState();
        }
    }

    private void updateShowPasswordState() {
        SysHelper.updateShowPasswordState(this.mPasswordView, this.mShowPwdImageView, this.mShowPassword, getResources());
    }

    @Override // android.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 2) {
            if (i2 == -1) {
                AccountLog.i(TAG, "notification completed");
                getActivity().setResult(-1);
                finish();
                return;
            }
            showAlertDialog(getString(R.string.passport_relogin_notice));
        }
    }

    private void confirm() {
        String str;
        if (this.mStep1Token == null) {
            String obj = this.mPasswordView.getText().toString();
            if (TextUtils.isEmpty(obj)) {
                this.mPasswordView.setError(getString(R.string.passport_error_empty_pwd));
                return;
            }
            if (this.mCaptchaView.getVisibility() == 0) {
                String captchaCode = this.mCaptchaView.getCaptchaCode();
                if (!TextUtils.isEmpty(captchaCode)) {
                    str = captchaCode;
                } else {
                    return;
                }
            } else {
                str = null;
            }
            loginByPassword(this.mUserId, obj, str, this.mCaptchaView.getCaptchaIck(), this.mServiceId);
            return;
        }
        String obj2 = this.mVCodeView.getText().toString();
        boolean isChecked = this.mTrustDeviceView.isChecked();
        if (TextUtils.isEmpty(obj2)) {
            this.mVCodeView.setError(getString(R.string.passport_error_empty_vcode));
        } else {
            loginByStep2(this.mUserId, obj2, isChecked, this.mServiceId);
        }
    }

    protected void applyCaptchaUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            AccountLog.w(TAG, "captcha url is null");
            this.mCaptchaView.setVisibility(8);
            return;
        }
        this.mCaptchaView.setVisibility(0);
        CaptchaView captchaView = this.mCaptchaView;
        captchaView.downloadCaptcha(URLs.ACCOUNT_DOMAIN + str);
    }

    protected void switchStage() {
        if (this.mStep1Token == null) {
            this.mInnerContentStep2View.setVisibility(8);
            this.mInnerContentView.setVisibility(0);
            this.mTitleView.setText(this.mTitle);
            return;
        }
        this.mInnerContentView.setVisibility(8);
        this.mInnerContentStep2View.setVisibility(0);
        this.mTitleView.setText(R.string.passport_quick_login_step2_title);
    }

    protected void loginByPassword(String str, String str2, String str3, String str4, String str5) {
        this.mLoginUIController.loginByPassword(new PasswordLoginParams.Builder().setUserId(str).setCaptCode(str3).setCaptIck(str4).setPassword(str2).setServiceId(str5).setIsReturnStsUrl(this.mReturnStsUrl).build(), new LoginUIController.PasswordLoginCallback() { // from class: com.xiaomi.passport.ui.internal.QuickLoginFragment.1
            @Override // com.xiaomi.passport.ui.internal.util.LoginUIController.PasswordLoginCallback
            public void onLoginSuccess(AccountInfo accountInfo) {
                QuickLoginFragment.this.onLoginSuccess(accountInfo);
            }

            @Override // com.xiaomi.passport.ui.internal.util.LoginUIController.PasswordLoginCallback
            public void onLoginByStep2(Step2LoginParams step2LoginParams) {
                if (QuickLoginFragment.this.mVerifyOnly) {
                    QuickLoginFragment.this.processLoginSuccess(new AccountInfo.Builder().userId(QuickLoginFragment.this.mUserId).build());
                    return;
                }
                QuickLoginFragment.this.mStep1Token = step2LoginParams.step1Token;
                QuickLoginFragment.this.mMetaLoginDataStep2 = step2LoginParams.metaLoginData;
                QuickLoginFragment.this.switchStage();
            }

            @Override // com.xiaomi.passport.ui.internal.util.LoginUIController.PasswordLoginCallback
            public void onNeedCaptchaCode(boolean z, String str6) {
                if (QuickLoginFragment.this.mCaptchaView.getVisibility() != 0) {
                    QuickLoginFragment.this.applyCaptchaUrl(str6);
                } else {
                    QuickLoginFragment.this.applyCaptchaUrl(str6);
                }
            }

            @Override // com.xiaomi.passport.ui.internal.util.LoginUIController.PasswordLoginCallback
            public void onNeedNotification(String str6, String str7) {
                QuickLoginFragment.this.startNotificationActivityForResult(str7, str6);
            }

            @Override // com.xiaomi.passport.ui.internal.util.LoginUIController.PasswordLoginCallback
            public void onLoginFailed(int i) {
                if (i == R.string.passport_error_no_password_user) {
                    QuickLoginFragment.this.showNoPwdUserDialog();
                    return;
                }
                QuickLoginFragment quickLoginFragment = QuickLoginFragment.this;
                quickLoginFragment.showAlertDialog(quickLoginFragment.getString(i));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNoPwdUserDialog() {
        SimpleDialogFragment create = new SimpleDialogFragment.AlertDialogFragmentBuilder(1).setTitle(getString(R.string.passport_login_failed)).setMessage(getString(R.string.passport_error_no_password_user)).create();
        create.setPositiveButton(17039370, null);
        create.showAllowingStateLoss(getActivity().getFragmentManager(), "no password user");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startNotificationActivityForResult(String str, String str2) {
        startActivityForResult(AuthenticatorUtil.newNotificationIntent(getActivity(), getActivity().getIntent().getParcelableExtra("accountAuthenticatorResponse"), str, str2, true, getArguments()), 2);
    }

    protected void loginByStep2(String str, String str2, boolean z, String str3) {
        this.mLoginUIController.loginByStep2(new Step2LoginParams.Builder().setUserId(str).setServiceId(str3).setStep1Token(this.mStep1Token).setMetaLoginData(this.mMetaLoginDataStep2).setTrust(z).setStep2code(str2).build(), new LoginUIController.Step2LoginCallback() { // from class: com.xiaomi.passport.ui.internal.QuickLoginFragment.2
            @Override // com.xiaomi.passport.ui.internal.util.LoginUIController.Step2LoginCallback
            public void onLoginSuccess(AccountInfo accountInfo) {
                QuickLoginFragment.this.onLoginSuccess(accountInfo);
            }

            @Override // com.xiaomi.passport.ui.internal.util.LoginUIController.Step2LoginCallback
            public void onPwdError() {
                QuickLoginFragment quickLoginFragment = QuickLoginFragment.this;
                quickLoginFragment.showAlertDialog(quickLoginFragment.getString(R.string.passport_bad_authentication));
            }

            @Override // com.xiaomi.passport.ui.internal.util.LoginUIController.Step2LoginCallback
            public void onInvalidStep2Code(int i) {
                QuickLoginFragment quickLoginFragment = QuickLoginFragment.this;
                quickLoginFragment.showAlertDialog(quickLoginFragment.getString(i));
            }

            @Override // com.xiaomi.passport.ui.internal.util.LoginUIController.Step2LoginCallback
            public void onLoginFailed(int i) {
                QuickLoginFragment quickLoginFragment = QuickLoginFragment.this;
                quickLoginFragment.showAlertDialog(quickLoginFragment.getString(i));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLoginSuccess(AccountInfo accountInfo) {
        this.mStep1Token = null;
        this.mMetaLoginDataStep2 = null;
        this.mCaptchaUrl = null;
        if (!this.mVerifyOnly) {
            AuthenticatorUtil.addOrUpdateAccountManager(getActivity(), accountInfo);
        }
        processLoginSuccess(accountInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processLoginSuccess(AccountInfo accountInfo) {
        AccountLog.i(TAG, "login success");
        AuthenticatorUtil.saveAccountInfoInAM(getActivity().getApplicationContext(), accountInfo);
        handleResponseIfNeeded(AccountHelper.getAuthenticatorResponseBundle(accountInfo, getArguments().getBoolean("need_retry_on_authenticator_response_result", false)));
        getActivity().setResult(-1);
        getActivity().finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAlertDialog(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (getActivity().getIntent() != null) {
            builder.setTitle(R.string.passport_verification_failed);
        } else {
            builder.setTitle(R.string.passport_login_failed);
        }
        builder.setMessage(str);
        builder.setNeutralButton(17039370, (DialogInterface.OnClickListener) null);
        builder.show();
    }

    void setButtonCancelForTest(Button button) {
        this.mButtonCancel = button;
    }
}
