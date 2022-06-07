package com.xiaomi.passport.ui.settings;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import com.xiaomi.accountsdk.account.ServerError;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.CommonErrorHandler;
import com.xiaomi.passport.ui.internal.Constants;
import com.xiaomi.passport.ui.settings.BindPhoneActivity;

/* loaded from: classes4.dex */
public class InputBindedVerifyCodeFragment extends AbstractVerifyCodeFragment {
    private static final String TAG = "InputBindedVerifyCodeFr";
    private CaptchaView mCaptchaView;

    public static InputBindedVerifyCodeFragment getInstance(String str, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("phone", str);
        InputBindedVerifyCodeFragment inputBindedVerifyCodeFragment = new InputBindedVerifyCodeFragment();
        inputBindedVerifyCodeFragment.setArguments(bundle);
        return inputBindedVerifyCodeFragment;
    }

    @Override // com.xiaomi.passport.ui.settings.AbstractVerifyCodeFragment, android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        if (getArguments() == null) {
            AccountLog.i(TAG, "args is null");
            getActivity().finish();
        }
    }

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mTrustDeviceCheckBox.setVisibility(8);
        this.mCaptchaView = (CaptchaView) view.findViewById(R.id.captcha_layout);
    }

    @Override // com.xiaomi.passport.ui.settings.AbstractVerifyCodeFragment
    public void doAfterGetVerifyCode(String str, String str2, boolean z) {
        modifySafePhone(str, str2);
    }

    @Override // com.xiaomi.passport.ui.settings.AbstractVerifyCodeFragment
    public void sendVerifyCode(String str) {
        sendModifyPhoneTicket(str);
    }

    private void modifySafePhone(String str, String str2) {
        final BindPhoneActivity bindPhoneActivity = (BindPhoneActivity) getActivity();
        bindPhoneActivity.modifySafePhone(str, null, str2, new BindPhoneActivity.ModifyPhoneCallback() { // from class: com.xiaomi.passport.ui.settings.InputBindedVerifyCodeFragment.1
            @Override // com.xiaomi.passport.ui.settings.BindPhoneActivity.ModifyPhoneCallback
            public void onNeedSMSCode(String str3) {
                InputBindedVerifyCodeFragment.this.sendModifyPhoneTicket(str3);
            }

            @Override // com.xiaomi.passport.ui.settings.BindPhoneActivity.ModifyPhoneCallback
            public void onError(int i) {
                InputBindedVerifyCodeFragment inputBindedVerifyCodeFragment = InputBindedVerifyCodeFragment.this;
                inputBindedVerifyCodeFragment.showErrorMessageDialog(inputBindedVerifyCodeFragment.getString(i));
            }

            @Override // com.xiaomi.passport.ui.settings.BindPhoneActivity.ModifyPhoneCallback
            public void onSuccess() {
                AccountLog.i(InputBindedVerifyCodeFragment.TAG, "modify phone success");
            }

            @Override // com.xiaomi.passport.ui.settings.BindPhoneActivity.ModifyPhoneCallback
            public void onError(ServerError serverError) {
                BindPhoneActivity bindPhoneActivity2 = bindPhoneActivity;
                if (bindPhoneActivity2 != null && !bindPhoneActivity2.isFinishing()) {
                    CommonErrorHandler.Companion.showErrorWithTips(bindPhoneActivity, serverError);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendModifyPhoneTicket(String str) {
        String str2;
        final BindPhoneActivity bindPhoneActivity = (BindPhoneActivity) getActivity();
        if (this.mCaptchaView.getVisibility() == 0) {
            str2 = this.mCaptchaView.getCaptchaCode();
            if (TextUtils.isEmpty(str2)) {
                return;
            }
        } else {
            str2 = null;
        }
        bindPhoneActivity.sendModifySafePhoneTicket(str, str2, this.mCaptchaView.getCaptchaIck(), new BindPhoneActivity.SendTicketCallback() { // from class: com.xiaomi.passport.ui.settings.InputBindedVerifyCodeFragment.2
            @Override // com.xiaomi.passport.ui.settings.BindPhoneActivity.SendTicketCallback
            public void onNeedCaptchaCode(String str3) {
                if (InputBindedVerifyCodeFragment.this.mCaptchaView.getVisibility() == 0) {
                    InputBindedVerifyCodeFragment inputBindedVerifyCodeFragment = InputBindedVerifyCodeFragment.this;
                    inputBindedVerifyCodeFragment.showErrorMessageDialog(inputBindedVerifyCodeFragment.getString(R.string.passport_wrong_captcha));
                }
                InputBindedVerifyCodeFragment.this.mCaptchaView.setVisibility(0);
                InputBindedVerifyCodeFragment.this.mCaptchaView.downloadCaptcha(str3, Constants.URL_ANTI_SPAM_GET_VOICE_CAPTCHA_CODE);
            }

            @Override // com.xiaomi.passport.ui.settings.BindPhoneActivity.SendTicketCallback
            public void onError(int i) {
                InputBindedVerifyCodeFragment inputBindedVerifyCodeFragment = InputBindedVerifyCodeFragment.this;
                inputBindedVerifyCodeFragment.showErrorMessageDialog(inputBindedVerifyCodeFragment.getString(i));
            }

            @Override // com.xiaomi.passport.ui.settings.BindPhoneActivity.SendTicketCallback
            public void onSuccess() {
                InputBindedVerifyCodeFragment.this.countDownGetVerifyCodeTime();
            }

            @Override // com.xiaomi.passport.ui.settings.BindPhoneActivity.SendTicketCallback
            public void onError(ServerError serverError) {
                BindPhoneActivity bindPhoneActivity2 = bindPhoneActivity;
                if (bindPhoneActivity2 != null && !bindPhoneActivity2.isFinishing()) {
                    CommonErrorHandler.Companion.showErrorWithTips(bindPhoneActivity, serverError);
                }
            }
        });
    }

    @Override // android.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackKeyPressed();
        return true;
    }

    private void onBackKeyPressed() {
        int i = R.string.restart_phone_bind_title;
        int i2 = R.string.restart_phone_bind_message;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(i);
        builder.setMessage(i2);
        builder.setPositiveButton(R.string.restart_action, new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.InputBindedVerifyCodeFragment.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i3) {
                InputBindedVerifyCodeFragment.this.getActivity().finish();
            }
        });
        builder.setNegativeButton(17039360, (DialogInterface.OnClickListener) null);
        builder.create().show();
    }
}
