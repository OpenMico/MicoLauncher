package com.xiaomi.passport.ui.settings;

import android.accounts.Account;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.Constants;
import com.xiaomi.passport.ui.settings.CaptchaDialogController;
import com.xiaomi.passport.ui.settings.SendEmailActivateMessageTask;
import com.xiaomi.passport.ui.settings.utils.UserDataProxy;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes4.dex */
public class UnactivatedEmailFragment extends Fragment implements View.OnClickListener {
    private static final String EXTRA_EMAIL_ADDRESS = "extra_email_address";
    private static final String TAG = "UnactivatedEmailFragmen";
    private TextView mActivateNoticeView;
    private CaptchaDialogController mCaptchaDialogController;
    private String mEmailAddress;
    private TextView mEmailAddressView;
    private ResendActivateEmailTask mResendActivateEmailTask;
    private Button mResendEmailBtn;
    private Button mVerifiedEmailBtn;

    public static UnactivatedEmailFragment getUnactivatedEmailFragment(String str) {
        UnactivatedEmailFragment unactivatedEmailFragment = new UnactivatedEmailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_EMAIL_ADDRESS, str);
        unactivatedEmailFragment.setArguments(bundle);
        return unactivatedEmailFragment;
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments == null || arguments.getString(EXTRA_EMAIL_ADDRESS) == null) {
            getActivity().finish();
            return;
        }
        this.mEmailAddress = arguments.getString(EXTRA_EMAIL_ADDRESS);
        this.mCaptchaDialogController = new CaptchaDialogController(getActivity(), new CaptchaDialogController.CaptchaInterface() { // from class: com.xiaomi.passport.ui.settings.UnactivatedEmailFragment.1
            @Override // com.xiaomi.passport.ui.settings.CaptchaDialogController.CaptchaInterface
            public void onCaptchaFinished() {
            }

            @Override // com.xiaomi.passport.ui.settings.CaptchaDialogController.CaptchaInterface
            public void onCaptchaRequired() {
            }

            @Override // com.xiaomi.passport.ui.settings.CaptchaDialogController.CaptchaInterface
            public void tryCaptcha(String str, String str2) {
                UnactivatedEmailFragment.this.resendActivateEmail(str, str2);
            }
        });
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.unactivated_bind_email, viewGroup, false);
        this.mEmailAddressView = (TextView) inflate.findViewById(R.id.email_address);
        this.mEmailAddressView.setText(this.mEmailAddress);
        this.mActivateNoticeView = (TextView) inflate.findViewById(R.id.activate_email_notice);
        this.mResendEmailBtn = (Button) inflate.findViewById(R.id.resend_email_btn);
        this.mVerifiedEmailBtn = (Button) inflate.findViewById(R.id.verified_email_btn);
        this.mResendEmailBtn.setOnClickListener(this);
        this.mVerifiedEmailBtn.setOnClickListener(this);
        setActivateNotice();
        return inflate;
    }

    private void setActivateNotice() {
        String str = getString(R.string.activate_email_notice) + StringUtils.SPACE;
        String string = getString(R.string.change_activate_email);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) str).append((CharSequence) string);
        spannableStringBuilder.setSpan(new ClickableSpan() { // from class: com.xiaomi.passport.ui.settings.UnactivatedEmailFragment.2
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                UnactivatedEmailFragment.this.getActivity().setResult(Constants.RESULT_RESTART_BINDING_EMAIL);
                UnactivatedEmailFragment.this.getActivity().finish();
            }
        }, str.length(), spannableStringBuilder.length(), 33);
        spannableStringBuilder.setSpan(new AbsoluteSizeSpan(36), str.length(), spannableStringBuilder.length(), 33);
        this.mActivateNoticeView.setText(spannableStringBuilder);
        this.mActivateNoticeView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.mResendEmailBtn) {
            resendActivateEmail(null, null);
        } else if (view == this.mVerifiedEmailBtn) {
            UserInfoManager.sendModifyUserEmailResultByLocalBroadcast(getActivity().getApplicationContext(), true, -1);
            getActivity().finish();
        }
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        ResendActivateEmailTask resendActivateEmailTask = this.mResendActivateEmailTask;
        if (resendActivateEmailTask != null) {
            resendActivateEmailTask.cancel(true);
            this.mResendActivateEmailTask = null;
        }
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resendActivateEmail(String str, String str2) {
        if (this.mResendActivateEmailTask == null) {
            Account xiaomiAccount = MiAccountManager.get(getActivity()).getXiaomiAccount();
            if (xiaomiAccount == null) {
                AccountLog.w(TAG, "no xiaomi account");
                getActivity().finish();
            }
            this.mResendActivateEmailTask = new ResendActivateEmailTask(getActivity(), this.mEmailAddress, new UserDataProxy(getActivity()).getUserData(xiaomiAccount, Constants.PASSPORT_IDENTITY_AUTH_TOKEN), str, str2);
            this.mResendActivateEmailTask.executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class ResendActivateEmailTask extends SendEmailActivateMessageTask {
        public ResendActivateEmailTask(Context context, String str, String str2, String str3, String str4) {
            super(context, str, str2, str3, str4);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onPostExecute(SendEmailActivateMessageTask.EmailTaskResult emailTaskResult) {
            int i;
            UnactivatedEmailFragment.this.mResendActivateEmailTask = null;
            if (emailTaskResult != null) {
                if (!TextUtils.isEmpty(emailTaskResult.captchaPath)) {
                    CaptchaDialogController captchaDialogController = UnactivatedEmailFragment.this.mCaptchaDialogController;
                    captchaDialogController.triggerNewCaptchaTask(URLs.ACCOUNT_DOMAIN + emailTaskResult.captchaPath, Constants.URL_ANTI_SPAM_GET_VOICE_CAPTCHA_CODE);
                    return;
                }
                UnactivatedEmailFragment.this.mCaptchaDialogController.dismiss();
                if (emailTaskResult.exceptionType == 13) {
                    UnactivatedEmailFragment.this.showSendEmailReachLimitDialog();
                    return;
                }
                AsyncTaskResult asyncTaskResult = new AsyncTaskResult(emailTaskResult.exceptionType);
                if (asyncTaskResult.hasException()) {
                    i = asyncTaskResult.getExceptionReason();
                } else {
                    i = R.string.resend_email_success;
                    UnactivatedEmailFragment unactivatedEmailFragment = UnactivatedEmailFragment.this;
                    unactivatedEmailFragment.saveUnactivatedEmailAddressAndTimeStamp(unactivatedEmailFragment.mEmailAddress, Long.valueOf(System.currentTimeMillis()));
                }
                Toast.makeText(UnactivatedEmailFragment.this.getActivity(), i, 1).show();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onCancelled(SendEmailActivateMessageTask.EmailTaskResult emailTaskResult) {
            super.onCancelled((ResendActivateEmailTask) emailTaskResult);
            UnactivatedEmailFragment.this.mResendActivateEmailTask = null;
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
        edit.apply();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSendEmailReachLimitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.resend_email_reach_limit_title);
        builder.setMessage(R.string.resend_email_reach_limit_message);
        builder.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
        builder.create().show();
    }
}
