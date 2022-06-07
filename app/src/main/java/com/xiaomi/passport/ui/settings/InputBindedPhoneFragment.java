package com.xiaomi.passport.ui.settings;

import android.accounts.Account;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import com.xiaomi.accountsdk.account.ServerError;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.MiuiActivatorInfo;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.CommonErrorHandler;
import com.xiaomi.passport.ui.internal.Constants;
import com.xiaomi.passport.ui.internal.util.SysHelper;
import com.xiaomi.passport.ui.settings.BindPhoneActivity;
import com.xiaomi.passport.ui.settings.GetUserBindIdAndLimitTask;
import com.xiaomi.passport.ui.settings.utils.AccountSmsVerifyCodeReceiver;
import com.xiaomi.passport.ui.settings.utils.PhoneNumUtil;
import com.xiaomi.passport.ui.settings.utils.UserDataProxy;
import com.xiaomi.passport.utils.XiaomiPassportExecutor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes4.dex */
public class InputBindedPhoneFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_CODE_FOREIGN_LOGIN = 10001;
    private static final String TAG = "InputBindedPhoneFragmen";
    private Account mAccount;
    private TextView mAreaCodeView;
    private Button mBtnNext;
    private CaptchaView mCaptchaView;
    private String mCheckedPhone;
    private PhoneNumUtil.CountryPhoneNumData mCountryPhoneNumData;
    private TextView mErrorStatusView;
    private AsyncTask<Void, Void, GetUserBindIdAndLimitTask.TaskResult> mGetUserBindIdAndLimitTask;
    private EditText mPhoneView;
    final TextWatcher mTextWatcher = new TextWatcher() { // from class: com.xiaomi.passport.ui.settings.InputBindedPhoneFragment.5
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            InputBindedPhoneFragment.this.updateShowErrorStatusView(false, null);
        }
    };

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        PhoneNumUtil.initializeCountryPhoneData(getActivity());
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.input_bind_phone_address, viewGroup, false);
        this.mAreaCodeView = (TextView) inflate.findViewById(R.id.tv_area_code);
        this.mPhoneView = (EditText) inflate.findViewById(R.id.ev_phone);
        this.mPhoneView.addTextChangedListener(this.mTextWatcher);
        this.mErrorStatusView = (TextView) inflate.findViewById(R.id.error_status);
        this.mBtnNext = (Button) inflate.findViewById(R.id.btn_phone_next);
        this.mAreaCodeView.setOnClickListener(this);
        this.mBtnNext.setOnClickListener(this);
        this.mCaptchaView = (CaptchaView) inflate.findViewById(R.id.captcha_layout);
        refreshViewStateByISO(Locale.getDefault().getCountry());
        return inflate;
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        this.mAccount = MiAccountManager.get(getActivity()).getXiaomiAccount();
        if (this.mAccount == null) {
            AccountLog.i(TAG, "no xiaomi account");
            getActivity().finish();
        }
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        AsyncTask<Void, Void, GetUserBindIdAndLimitTask.TaskResult> asyncTask = this.mGetUserBindIdAndLimitTask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            this.mGetUserBindIdAndLimitTask = null;
        }
        super.onDestroy();
    }

    @Override // android.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 10001 && -1 == i2) {
            refreshViewStateByISO(intent.getStringExtra(AreaCodePickerFragment.KEY_COUNTRY_ISO));
        }
    }

    private void refreshViewStateByISO(String str) {
        if (TextUtils.isEmpty(str)) {
            AccountLog.d(TAG, "region info is null, and set China as the default area iso");
            str = "CN";
        }
        this.mCountryPhoneNumData = PhoneNumUtil.getCounrtyPhoneDataFromIso(str);
        TextView textView = this.mAreaCodeView;
        if (textView != null) {
            textView.setText(this.mCountryPhoneNumData.countryName + "(+" + this.mCountryPhoneNumData.countryCode + ")");
        }
    }

    private String getTheInputPhoneNumber() {
        String obj = this.mPhoneView.getText().toString();
        if (TextUtils.isEmpty(obj)) {
            this.mPhoneView.setError(getString(R.string.passport_error_empty_phone_num));
            return null;
        }
        PhoneNumUtil.CountryPhoneNumData countryPhoneNumData = this.mCountryPhoneNumData;
        if (countryPhoneNumData != null) {
            obj = PhoneNumUtil.checkNumber(obj, countryPhoneNumData);
            if (TextUtils.isEmpty(obj)) {
                this.mPhoneView.setError(getString(R.string.passport_wrong_phone_number_format));
                return null;
            }
        }
        if (!TextUtils.equals(new UserDataProxy(getActivity()).getUserData(this.mAccount, Constants.ACCOUNT_USER_PHONE), obj)) {
            return obj;
        }
        this.mPhoneView.setError(getString(R.string.failed_dup_secure_phone_number));
        return null;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view == this.mAreaCodeView) {
            Intent intent = new Intent(getActivity(), AreaCodePickerActivity.class);
            intent.setPackage(getActivity().getPackageName());
            startActivityForResult(intent, 10001);
        } else if (view == this.mBtnNext) {
            String theInputPhoneNumber = getTheInputPhoneNumber();
            if (TextUtils.isEmpty(theInputPhoneNumber)) {
                return;
            }
            if (TextUtils.equals(this.mCheckedPhone, theInputPhoneNumber)) {
                sendModifyPhoneTicket(theInputPhoneNumber);
            } else {
                getUserBindIdAndLimit(theInputPhoneNumber);
            }
        }
    }

    private void getUserBindIdAndLimit(final String str) {
        if (this.mGetUserBindIdAndLimitTask == null) {
            this.mGetUserBindIdAndLimitTask = new GetUserBindIdAndLimitTask(getActivity(), str, new GetUserBindIdAndLimitTask.GetUserBindIdAndLimitCallBack() { // from class: com.xiaomi.passport.ui.settings.InputBindedPhoneFragment.1
                @Override // com.xiaomi.passport.ui.settings.GetUserBindIdAndLimitTask.GetUserBindIdAndLimitCallBack
                public void onFail(int i) {
                    InputBindedPhoneFragment.this.mGetUserBindIdAndLimitTask = null;
                    InputBindedPhoneFragment inputBindedPhoneFragment = InputBindedPhoneFragment.this;
                    inputBindedPhoneFragment.updateShowErrorStatusView(true, inputBindedPhoneFragment.getString(i));
                }

                @Override // com.xiaomi.passport.ui.settings.GetUserBindIdAndLimitTask.GetUserBindIdAndLimitCallBack
                public void onSuccess(GetUserBindIdAndLimitTask.UserBindIdAndLimitResult userBindIdAndLimitResult) {
                    InputBindedPhoneFragment.this.mGetUserBindIdAndLimitTask = null;
                    InputBindedPhoneFragment.this.mErrorStatusView.setVisibility(8);
                    try {
                        int times = userBindIdAndLimitResult.getTimes();
                        long time = userBindIdAndLimitResult.getTime();
                        String userId = userBindIdAndLimitResult.getUserId();
                        if (times == 0) {
                            InputBindedPhoneFragment.this.updateShowErrorStatusView(true, InputBindedPhoneFragment.this.getString(R.string.get_phone_bind_exceed_limit));
                        } else if (TextUtils.isEmpty(userId)) {
                            InputBindedPhoneFragment.this.sendModifyPhoneTicket(str);
                        } else {
                            InputBindedPhoneFragment.this.confirmUnbundledPhone(str, time, userId);
                        }
                    } catch (Exception e) {
                        AccountLog.e(InputBindedPhoneFragment.TAG, "GetUserBindIdAndLimitException", e);
                    }
                }

                @Override // com.xiaomi.passport.ui.settings.GetUserBindIdAndLimitTask.GetUserBindIdAndLimitCallBack
                public void onFail(ServerError serverError) {
                    InputBindedPhoneFragment.this.mGetUserBindIdAndLimitTask = null;
                    if (InputBindedPhoneFragment.this.getActivity() != null && !InputBindedPhoneFragment.this.getActivity().isFinishing()) {
                        CommonErrorHandler.Companion.showErrorWithTips(InputBindedPhoneFragment.this.getActivity(), serverError);
                    }
                }
            });
            this.mGetUserBindIdAndLimitTask.executeOnExecutor(XiaomiPassportExecutor.getSingleton(), new Void[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void confirmUnbundledPhone(final String str, long j, String str2) {
        Date date = new Date();
        date.setTime(j);
        String format = new SimpleDateFormat(XMPassport.SIMPLE_DATE_FORMAT).format(date);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.confirm_bundled_phone_dialog_title);
        builder.setMessage(String.format(getString(R.string.confirm_unbundled_phone_dialog_message), format, str2, str2));
        builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.settings.InputBindedPhoneFragment.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                InputBindedPhoneFragment.this.sendModifyPhoneTicket(str);
            }
        });
        builder.setNegativeButton(17039360, (DialogInterface.OnClickListener) null);
        builder.create().show();
    }

    private void modifySafePhone(String str, MiuiActivatorInfo miuiActivatorInfo) {
        final BindPhoneActivity bindPhoneActivity = (BindPhoneActivity) getActivity();
        bindPhoneActivity.modifySafePhone(str, miuiActivatorInfo, null, new BindPhoneActivity.ModifyPhoneCallback() { // from class: com.xiaomi.passport.ui.settings.InputBindedPhoneFragment.3
            @Override // com.xiaomi.passport.ui.settings.BindPhoneActivity.ModifyPhoneCallback
            public void onNeedSMSCode(String str2) {
                InputBindedPhoneFragment.this.sendModifyPhoneTicket(str2);
            }

            @Override // com.xiaomi.passport.ui.settings.BindPhoneActivity.ModifyPhoneCallback
            public void onError(int i) {
                InputBindedPhoneFragment inputBindedPhoneFragment = InputBindedPhoneFragment.this;
                inputBindedPhoneFragment.updateShowErrorStatusView(true, inputBindedPhoneFragment.getString(i));
            }

            @Override // com.xiaomi.passport.ui.settings.BindPhoneActivity.ModifyPhoneCallback
            public void onSuccess() {
                AccountLog.i(InputBindedPhoneFragment.TAG, "modify phone success");
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
    public void sendModifyPhoneTicket(final String str) {
        String str2;
        if (this.mCaptchaView.getVisibility() == 0) {
            str2 = this.mCaptchaView.getCaptchaCode();
            if (TextUtils.isEmpty(str2)) {
                return;
            }
        } else {
            str2 = null;
        }
        final BindPhoneActivity bindPhoneActivity = (BindPhoneActivity) getActivity();
        bindPhoneActivity.sendModifySafePhoneTicket(str, str2, this.mCaptchaView.getCaptchaIck(), new BindPhoneActivity.SendTicketCallback() { // from class: com.xiaomi.passport.ui.settings.InputBindedPhoneFragment.4
            @Override // com.xiaomi.passport.ui.settings.BindPhoneActivity.SendTicketCallback
            public void onNeedCaptchaCode(String str3) {
                if (InputBindedPhoneFragment.this.mCaptchaView.getVisibility() == 0) {
                    InputBindedPhoneFragment inputBindedPhoneFragment = InputBindedPhoneFragment.this;
                    inputBindedPhoneFragment.updateShowErrorStatusView(true, inputBindedPhoneFragment.getString(R.string.passport_wrong_captcha));
                }
                InputBindedPhoneFragment.this.mCaptchaView.setVisibility(0);
                InputBindedPhoneFragment.this.mCaptchaView.downloadCaptcha(str3, Constants.URL_ANTI_SPAM_GET_VOICE_CAPTCHA_CODE);
            }

            @Override // com.xiaomi.passport.ui.settings.BindPhoneActivity.SendTicketCallback
            public void onError(int i) {
                InputBindedPhoneFragment inputBindedPhoneFragment = InputBindedPhoneFragment.this;
                inputBindedPhoneFragment.updateShowErrorStatusView(true, inputBindedPhoneFragment.getString(i));
            }

            @Override // com.xiaomi.passport.ui.settings.BindPhoneActivity.SendTicketCallback
            public void onSuccess() {
                if (!AccountSmsVerifyCodeReceiver.tryRequestSmsPermission(InputBindedPhoneFragment.this.getActivity())) {
                    InputBindedPhoneFragment.this.switchToInputVerifyCodeFragment(str);
                }
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
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (51 == i) {
            switchToInputVerifyCodeFragment(getTheInputPhoneNumber());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchToInputVerifyCodeFragment(String str) {
        SysHelper.replaceToFragment(getActivity(), InputBindedVerifyCodeFragment.getInstance(str, getArguments()), false, ((ViewGroup) getView().getParent()).getId());
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
        setNextBtnMarginTop(getResources().getDimensionPixelSize(i));
    }

    private void setNextBtnMarginTop(int i) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(0, i, 0, 0);
        this.mBtnNext.setLayoutParams(layoutParams);
    }
}
