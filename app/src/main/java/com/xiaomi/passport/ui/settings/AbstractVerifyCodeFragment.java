package com.xiaomi.passport.ui.settings;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.settings.SimpleDialogFragment;
import com.xiaomi.passport.ui.settings.utils.AccountSmsVerifyCodeReceiver;

/* loaded from: classes4.dex */
public abstract class AbstractVerifyCodeFragment extends Fragment implements View.OnClickListener {
    private static final long COUNT_DOWN_GET_VERIFY_CODE_TIME = 60000;
    private static final long COUNT_DOWN_READ_SMS_TIME = 4000;
    protected static final String EXTRA_SEND_VCODE_PHONE_NUM = "phone";
    private static final String TAG = "AbstractVerifyCodeFragment";
    private MyCountDownTimer mAutoReadSmsCountDownTimer;
    private MyCountDownTimer mGetVCodeCountDownTimer;
    private TextView mGetVerifyCodeView;
    protected String mPhoneNumber;
    private AccountSmsVerifyCodeReceiver mSmsReceiver;
    protected CheckBox mTrustDeviceCheckBox;
    private Button mVerifyBtn;
    private EditText mVerifyCodeView;
    private long millisCountDownGetVerifyCode = 30000;

    public abstract void doAfterGetVerifyCode(String str, String str2, boolean z);

    public abstract void sendVerifyCode(String str);

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        countDownAutoReadSmsTimer();
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(getVCodeLayout(), viewGroup, false);
        Bundle arguments = getArguments();
        if (arguments == null) {
            AccountLog.i(TAG, "args is null");
            getActivity().finish();
            return inflate;
        }
        this.mPhoneNumber = arguments.getString("phone");
        ((TextView) inflate.findViewById(R.id.sms_send_notice)).setText(String.format(getString(R.string.passport_vcode_sms_send_prompt), this.mPhoneNumber));
        this.mVerifyCodeView = (EditText) inflate.findViewById(R.id.ev_verify_code);
        this.mGetVerifyCodeView = (TextView) inflate.findViewById(R.id.get_vcode_notice);
        this.mVerifyBtn = (Button) inflate.findViewById(R.id.btn_verify);
        this.mTrustDeviceCheckBox = (CheckBox) inflate.findViewById(R.id.trust_device);
        this.mGetVerifyCodeView.setOnClickListener(this);
        this.mVerifyBtn.setOnClickListener(this);
        countDownGetVerifyCodeTime();
        return inflate;
    }

    protected int getVCodeLayout() {
        return R.layout.passport_input_phone_vcode;
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        this.mSmsReceiver = new AccountSmsVerifyCodeReceiver(new SmsVerifyCodeListener());
        getActivity().registerReceiver(this.mSmsReceiver, intentFilter);
    }

    @Override // android.app.Fragment
    public void onPause() {
        if (this.mSmsReceiver != null) {
            getActivity().unregisterReceiver(this.mSmsReceiver);
            this.mSmsReceiver = null;
        }
        cancelCountDownAutoReadSmsTimer();
        cancelCountDownGetVCodeTimer();
        super.onPause();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.mGetVerifyCodeView) {
            sendVerifyCode(this.mPhoneNumber);
        } else if (view == this.mVerifyBtn) {
            onVerifyButtonClicked();
        }
    }

    protected void onVerifyButtonClicked() {
        String obj = this.mVerifyCodeView.getText().toString();
        if (TextUtils.isEmpty(obj)) {
            this.mVerifyCodeView.requestFocus();
            this.mVerifyCodeView.setError(getString(R.string.passport_error_empty_vcode));
            return;
        }
        doAfterGetVerifyCode(this.mPhoneNumber, obj, isTrustDevice());
    }

    protected void showErrorMessageDialog(String str) {
        new AlertDialog.Builder(getActivity()).setMessage(str).setNeutralButton(17039370, (DialogInterface.OnClickListener) null).create().show();
    }

    private void countDownAutoReadSmsTimer() {
        final SimpleDialogFragment create = new SimpleDialogFragment.AlertDialogFragmentBuilder(2).setMessage(getString(R.string.passport_trying_read_verify_code_sms)).setCancelable(false).create();
        create.showAllowingStateLoss(getFragmentManager(), "autoReadSmsProgress");
        this.mAutoReadSmsCountDownTimer = new MyCountDownTimer(COUNT_DOWN_READ_SMS_TIME, 1000L) { // from class: com.xiaomi.passport.ui.settings.AbstractVerifyCodeFragment.1
            @Override // android.os.CountDownTimer
            public void onTick(long j) {
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                SimpleDialogFragment simpleDialogFragment = create;
                if (!(simpleDialogFragment == null || simpleDialogFragment.getActivity() == null || create.getActivity().isFinishing())) {
                    create.dismissAllowingStateLoss();
                }
                AbstractVerifyCodeFragment.this.mAutoReadSmsCountDownTimer = null;
            }

            @Override // com.xiaomi.passport.ui.settings.AbstractVerifyCodeFragment.MyCountDownTimer
            public void cancelCountDown() {
                super.cancelCountDown();
                onFinish();
            }
        };
        this.mAutoReadSmsCountDownTimer.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelCountDownAutoReadSmsTimer() {
        MyCountDownTimer myCountDownTimer = this.mAutoReadSmsCountDownTimer;
        if (myCountDownTimer != null) {
            myCountDownTimer.cancelCountDown();
            this.mAutoReadSmsCountDownTimer = null;
        }
    }

    protected void countDownGetVerifyCodeTime() {
        this.mGetVerifyCodeView.setEnabled(false);
        this.millisCountDownGetVerifyCode *= 2;
        this.mGetVCodeCountDownTimer = new MyCountDownTimer(this.millisCountDownGetVerifyCode, 1000L) { // from class: com.xiaomi.passport.ui.settings.AbstractVerifyCodeFragment.2
            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                TextView textView = AbstractVerifyCodeFragment.this.mGetVerifyCodeView;
                textView.setText(AbstractVerifyCodeFragment.this.getString(R.string.passport_getting_verify_code) + " (" + (j / 1000) + ")");
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                AbstractVerifyCodeFragment.this.mGetVerifyCodeView.setText(AbstractVerifyCodeFragment.this.getString(R.string.passport_re_get_verify_code));
                AbstractVerifyCodeFragment.this.mGetVerifyCodeView.setEnabled(true);
                AbstractVerifyCodeFragment.this.mGetVCodeCountDownTimer = null;
            }

            @Override // com.xiaomi.passport.ui.settings.AbstractVerifyCodeFragment.MyCountDownTimer
            public void cancelCountDown() {
                super.cancelCountDown();
                AbstractVerifyCodeFragment.this.mGetVCodeCountDownTimer = null;
            }
        };
        this.mGetVCodeCountDownTimer.start();
    }

    private void cancelCountDownGetVCodeTimer() {
        MyCountDownTimer myCountDownTimer = this.mGetVCodeCountDownTimer;
        if (myCountDownTimer != null) {
            myCountDownTimer.cancelCountDown();
            this.mGetVCodeCountDownTimer = null;
        }
    }

    /* loaded from: classes4.dex */
    private class SmsVerifyCodeListener implements AccountSmsVerifyCodeReceiver.SmsVerifyCodeMessageListener {
        private SmsVerifyCodeListener() {
        }

        @Override // com.xiaomi.passport.ui.settings.utils.AccountSmsVerifyCodeReceiver.SmsVerifyCodeMessageListener
        public void onReceived(String str, String str2) {
            if (TextUtils.isEmpty(AbstractVerifyCodeFragment.this.mVerifyCodeView.getText().toString())) {
                AbstractVerifyCodeFragment abstractVerifyCodeFragment = AbstractVerifyCodeFragment.this;
                abstractVerifyCodeFragment.doAfterGetVerifyCode(abstractVerifyCodeFragment.mPhoneNumber, str2, AbstractVerifyCodeFragment.this.isTrustDevice());
            }
            AbstractVerifyCodeFragment.this.cancelCountDownAutoReadSmsTimer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isTrustDevice() {
        CheckBox checkBox = this.mTrustDeviceCheckBox;
        if (checkBox != null) {
            return checkBox.isChecked();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public abstract class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long j, long j2) {
            super(j, j2);
        }

        public void cancelCountDown() {
            cancel();
        }
    }
}
