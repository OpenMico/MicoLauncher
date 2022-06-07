package com.xiaomi.passport.ui.settings;

import android.accounts.Account;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.Constants;
import com.xiaomi.passport.ui.internal.util.SysHelper;
import com.xiaomi.passport.ui.settings.SendEmailActivateMessageTask;
import com.xiaomi.passport.ui.settings.utils.UserDataProxy;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;

/* loaded from: classes4.dex */
public class InputBindedEmailFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "InputBindedEmailFragmen";
    private Button mBtnNext;
    private CaptchaView mCaptchaView;
    private EditText mEmailView;
    private TextView mErrorStatusView;
    private SendActivateEmailTask mSendActivateEmailTask;
    private AsyncTask<Void, Void, Integer> mSendVerifyCodeTask;
    final TextWatcher mTextWatcher = new TextWatcher() { // from class: com.xiaomi.passport.ui.settings.InputBindedEmailFragment.1
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            InputBindedEmailFragment.this.updateShowErrorStatusView(false, null);
        }
    };

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.input_bind_email_address, viewGroup, false);
        this.mEmailView = (EditText) inflate.findViewById(R.id.email_address);
        this.mBtnNext = (Button) inflate.findViewById(R.id.btn_next);
        this.mBtnNext.setOnClickListener(this);
        this.mCaptchaView = (CaptchaView) inflate.findViewById(R.id.captcha_layout);
        this.mErrorStatusView = (TextView) inflate.findViewById(R.id.error_status);
        this.mEmailView.addTextChangedListener(this.mTextWatcher);
        return inflate;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.mBtnNext) {
            sendEmailActivateMessage();
        }
    }

    @Override // android.app.Fragment
    public void onPause() {
        SysHelper.displaySoftInputIfNeed(getActivity(), getActivity().getCurrentFocus(), false);
        super.onPause();
    }

    private void sendEmailActivateMessage() {
        String str;
        String theInputEmailAddress = getTheInputEmailAddress();
        if (!TextUtils.isEmpty(theInputEmailAddress)) {
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
            Account xiaomiAccount = MiAccountManager.get(getActivity()).getXiaomiAccount();
            if (xiaomiAccount == null) {
                AccountLog.w(TAG, "no xiaomi account");
                getActivity().finish();
            } else if (this.mSendActivateEmailTask == null) {
                this.mSendActivateEmailTask = new SendActivateEmailTask(getActivity(), theInputEmailAddress, new UserDataProxy(getActivity()).getUserData(xiaomiAccount, Constants.PASSPORT_IDENTITY_AUTH_TOKEN), str, this.mCaptchaView.getCaptchaIck());
                this.mSendActivateEmailTask.executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
            }
        }
    }

    private String getTheInputEmailAddress() {
        String obj = this.mEmailView.getText().toString();
        if (TextUtils.isEmpty(obj)) {
            this.mEmailView.setError(getString(R.string.passport_error_empty_email));
            return null;
        } else if (Patterns.EMAIL_ADDRESS.matcher(obj).matches()) {
            return obj;
        } else {
            this.mEmailView.setError(getString(R.string.passport_error_email));
            return null;
        }
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        AsyncTask<Void, Void, Integer> asyncTask = this.mSendVerifyCodeTask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            this.mSendVerifyCodeTask = null;
        }
        SendActivateEmailTask sendActivateEmailTask = this.mSendActivateEmailTask;
        if (sendActivateEmailTask != null) {
            sendActivateEmailTask.cancel(true);
            this.mSendActivateEmailTask = null;
        }
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class SendActivateEmailTask extends SendEmailActivateMessageTask {
        public SendActivateEmailTask(Context context, String str, String str2, String str3, String str4) {
            super(context, str, str2, str3, str4);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onPostExecute(SendEmailActivateMessageTask.EmailTaskResult emailTaskResult) {
            InputBindedEmailFragment.this.mSendActivateEmailTask = null;
            if (emailTaskResult != null) {
                if (!TextUtils.isEmpty(emailTaskResult.captchaPath)) {
                    CaptchaView captchaView = InputBindedEmailFragment.this.mCaptchaView;
                    captchaView.downloadCaptcha(URLs.ACCOUNT_DOMAIN + emailTaskResult.captchaPath, Constants.URL_ANTI_SPAM_GET_VOICE_CAPTCHA_CODE);
                    if (InputBindedEmailFragment.this.mCaptchaView.getVisibility() != 0) {
                        InputBindedEmailFragment.this.mCaptchaView.setVisibility(0);
                        return;
                    }
                }
                AsyncTaskResult asyncTaskResult = new AsyncTaskResult(emailTaskResult.exceptionType);
                if (asyncTaskResult.hasException()) {
                    InputBindedEmailFragment.this.updateShowErrorStatusView(true, InputBindedEmailFragment.this.getString(asyncTaskResult.getExceptionReason()));
                    return;
                }
                String obj = InputBindedEmailFragment.this.mEmailView.getText().toString();
                InputBindedEmailFragment.this.saveUnactivatedEmailAddressAndTimeStamp(obj, Long.valueOf(System.currentTimeMillis()));
                SysHelper.replaceToFragment(InputBindedEmailFragment.this.getActivity(), UnactivatedEmailFragment.getUnactivatedEmailFragment(obj), false, ((ViewGroup) InputBindedEmailFragment.this.getView().getParent()).getId());
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onCancelled(SendEmailActivateMessageTask.EmailTaskResult emailTaskResult) {
            super.onCancelled((SendActivateEmailTask) emailTaskResult);
            InputBindedEmailFragment.this.mSendActivateEmailTask = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveUnactivatedEmailAddressAndTimeStamp(String str, Long l) {
        Account xiaomiAccount = MiAccountManager.get(getActivity()).getXiaomiAccount();
        if (xiaomiAccount == null) {
            AccountLog.w(TAG, "no xiaomi account");
            getActivity().finish();
            return;
        }
        SharedPreferences.Editor edit = getActivity().getSharedPreferences(xiaomiAccount.name, 0).edit();
        edit.putString(Constants.SP_UNACTIVATED_EMAIL_ADDRESS, str);
        edit.putLong(Constants.SP_UNACTIVATED_EMAIL_TIME_STAMP, l.longValue());
        edit.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateShowErrorStatusView(boolean z, String str) {
        int i;
        if (z) {
            this.mErrorStatusView.setVisibility(0);
            this.mErrorStatusView.setText(str);
            i = R.dimen.passport_buttons_margin_v;
        } else {
            this.mErrorStatusView.setVisibility(8);
            i = R.dimen.passport_reg_content_bottom_margin;
        }
        setBtnNextMarginTop(getResources().getDimensionPixelSize(i));
    }

    private void setBtnNextMarginTop(int i) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(0, i, 0, 0);
        this.mBtnNext.setLayoutParams(layoutParams);
    }
}
