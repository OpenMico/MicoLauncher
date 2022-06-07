package com.xiaomi.passport.ui.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.umeng.analytics.pro.ai;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.PhTicketSignInContract;
import com.xiaomi.passport.ui.settings.utils.AccountSmsVerifyCodeReceiver;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FragmentPhTicketAuth.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u0000 92\u00020\u00012\u00020\u00022\u00020\u0003:\u00019B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0017J\b\u0010\u001b\u001a\u00020\u0016H\u0016J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0012\u0010\u001e\u001a\u00020\u00162\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J(\u0010!\u001a\u0004\u0018\u00010\"2\b\b\u0001\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\b\u0010'\u001a\u00020\u0016H\u0016J\u001c\u0010(\u001a\u00020\u00162\b\u0010)\u001a\u0004\u0018\u00010\u001d2\b\u0010*\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010+\u001a\u00020\u0016H\u0016J\u001c\u0010,\u001a\u00020\u00162\b\b\u0001\u0010-\u001a\u00020\"2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\b\u0010.\u001a\u00020\u0016H\u0016J\u0018\u0010/\u001a\u00020\u00162\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\fH\u0016J\u0018\u00103\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u0002042\u0006\u00105\u001a\u00020\u0014H\u0016J\b\u00106\u001a\u00020\u0016H\u0016J\u0010\u00106\u001a\u00020\u00162\u0006\u00105\u001a\u00020\u0014H\u0016J\u0010\u00107\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u000204H\u0016J\b\u00108\u001a\u00020\u0016H\u0002R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PhTicketSignInFragment;", "Lcom/xiaomi/passport/ui/internal/SignInFragment;", "Lcom/xiaomi/passport/ui/internal/PhTicketSignInContract$View;", "Lcom/xiaomi/passport/ui/settings/utils/AccountSmsVerifyCodeReceiver$SmsVerifyCodeMessageListener;", "()V", "mIsCountingDown", "", "getMIsCountingDown", "()Z", "setMIsCountingDown", "(Z)V", "mPhoneWrapper", "Lcom/xiaomi/passport/ui/internal/PhoneWrapper;", "mSmsReceiver", "Lcom/xiaomi/passport/ui/settings/utils/AccountSmsVerifyCodeReceiver;", "passportRepo", "Lcom/xiaomi/passport/ui/internal/PassportRepo;", "presenter", "Lcom/xiaomi/passport/ui/internal/PhTicketSignInContract$Presenter;", "sendTicketSuccessCount", "", "chooseToSignInOrSignUp", "", "authCredential", "Lcom/xiaomi/passport/ui/internal/PhoneSmsAuthCredential;", "userInfo", "Lcom/xiaomi/accountsdk/account/data/RegisterUserInfo;", "enableSendTicketBtn", "getDisplayNickname", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onPause", "onReceived", "message", "verifyCode", "onResume", "onViewCreated", OneTrack.Event.VIEW, "sendTicketSuccess", "showCaptcha", "captcha", "Lcom/xiaomi/passport/ui/internal/Captcha;", "phone", "showInvalidPsw", "Lcom/xiaomi/passport/ui/internal/ChoosePhoneSmsAuthCredential;", "msg", "showInvalidTicket", "showSetPsw", "startFailReceiveSMSVerifyCodeQA", "Companion", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PhTicketSignInFragment extends SignInFragment implements PhTicketSignInContract.View, AccountSmsVerifyCodeReceiver.SmsVerifyCodeMessageListener {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private boolean mIsCountingDown;
    private PhoneWrapper mPhoneWrapper;
    private AccountSmsVerifyCodeReceiver mSmsReceiver;
    private PassportRepo passportRepo = new PassportRepoImpl();
    private PhTicketSignInContract.Presenter presenter;
    private int sendTicketSuccessCount;

    @Override // com.xiaomi.passport.ui.internal.SignInFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.xiaomi.passport.ui.internal.SignInFragment
    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View view2 = getView();
        if (view2 == null) {
            return null;
        }
        View findViewById = view2.findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    @Override // com.xiaomi.passport.ui.internal.SignInFragment, androidx.fragment.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @NotNull
    public static final /* synthetic */ PhTicketSignInContract.Presenter access$getPresenter$p(PhTicketSignInFragment phTicketSignInFragment) {
        PhTicketSignInContract.Presenter presenter = phTicketSignInFragment.presenter;
        if (presenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenter");
        }
        return presenter;
    }

    public final boolean getMIsCountingDown() {
        return this.mIsCountingDown;
    }

    public final void setMIsCountingDown(boolean z) {
        this.mIsCountingDown = z;
    }

    /* compiled from: FragmentPhTicketAuth.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PhTicketSignInFragment$Companion;", "", "()V", "newInstance", "Lcom/xiaomi/passport/ui/internal/PhTicketSignInFragment;", "sid", "", "phone", "Lcom/xiaomi/passport/ui/internal/PhoneWrapper;", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final PhTicketSignInFragment newInstance(@NotNull String sid, @NotNull PhoneWrapper phone) {
            Intrinsics.checkParameterIsNotNull(sid, "sid");
            Intrinsics.checkParameterIsNotNull(phone, "phone");
            PhTicketSignInFragment phTicketSignInFragment = new PhTicketSignInFragment();
            Bundle bundle = new Bundle();
            bundle.putString("sid", sid);
            bundle.putParcelable("phone", phone);
            phTicketSignInFragment.setArguments(bundle);
            return phTicketSignInFragment;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        this.mSmsReceiver = new AccountSmsVerifyCodeReceiver(this);
        getActivity().registerReceiver(this.mSmsReceiver, intentFilter);
    }

    @Override // com.xiaomi.passport.ui.settings.utils.AccountSmsVerifyCodeReceiver.SmsVerifyCodeMessageListener
    public void onReceived(@Nullable String str, @Nullable String str2) {
        if (str2 != null) {
            ((TextInputEditText) _$_findCachedViewById(R.id.ticket)).setText(str2);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        if (this.mSmsReceiver != null) {
            getActivity().unregisterReceiver(this.mSmsReceiver);
            this.mSmsReceiver = null;
        }
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        AccountSmsVerifyCodeReceiver.tryRequestSmsPermission(getActivity());
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        Context context = getContext();
        if (context == null) {
            Intrinsics.throwNpe();
        }
        Bundle arguments = getArguments();
        if (arguments == null) {
            Intrinsics.throwNpe();
        }
        String string = arguments.getString("sid");
        Intrinsics.checkExpressionValueIsNotNull(string, "arguments!!.getString(\"sid\")");
        this.presenter = new PhTicketSignInPresenter(context, string, this, null, 8, null);
        Bundle arguments2 = getArguments();
        if (arguments2 == null) {
            Intrinsics.throwNpe();
        }
        this.mPhoneWrapper = (PhoneWrapper) arguments2.getParcelable("phone");
        return inflater.inflate(R.layout.fg_ph_ticket_signin, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        super.onViewCreated(view, bundle);
        TextView phone_text = (TextView) _$_findCachedViewById(R.id.phone_text);
        Intrinsics.checkExpressionValueIsNotNull(phone_text, "phone_text");
        int i = R.string.passport_sms_phone_intro;
        Object[] objArr = new Object[1];
        PhoneWrapper phoneWrapper = this.mPhoneWrapper;
        objArr[0] = phoneWrapper != null ? phoneWrapper.getPhoneOrMarkPhone() : null;
        phone_text.setText(getString(i, objArr));
        ((TextView) _$_findCachedViewById(R.id.action_get_ph_ticket)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PhTicketSignInFragment$onViewCreated$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PhoneWrapper phoneWrapper2;
                PhoneWrapper phoneWrapper3;
                TextView textView = (TextView) PhTicketSignInFragment.this._$_findCachedViewById(R.id.action_get_ph_ticket);
                if (textView != null) {
                    textView.setClickable(false);
                }
                phoneWrapper2 = PhTicketSignInFragment.this.mPhoneWrapper;
                if (phoneWrapper2 != null) {
                    PhTicketSignInContract.Presenter access$getPresenter$p = PhTicketSignInFragment.access$getPresenter$p(PhTicketSignInFragment.this);
                    phoneWrapper3 = PhTicketSignInFragment.this.mPhoneWrapper;
                    if (phoneWrapper3 == null) {
                        Intrinsics.throwNpe();
                    }
                    PhTicketSignInContract.Presenter.DefaultImpls.sendTicket$default(access$getPresenter$p, phoneWrapper3, null, 2, null);
                }
            }
        });
        ((Button) _$_findCachedViewById(R.id.ph_sign_in_btn)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PhTicketSignInFragment$onViewCreated$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PhoneWrapper phoneWrapper2;
                PhoneWrapper phoneWrapper3;
                phoneWrapper2 = PhTicketSignInFragment.this.mPhoneWrapper;
                if (phoneWrapper2 != null) {
                    PhTicketSignInContract.Presenter access$getPresenter$p = PhTicketSignInFragment.access$getPresenter$p(PhTicketSignInFragment.this);
                    phoneWrapper3 = PhTicketSignInFragment.this.mPhoneWrapper;
                    if (phoneWrapper3 == null) {
                        Intrinsics.throwNpe();
                    }
                    TextInputEditText ticket = (TextInputEditText) PhTicketSignInFragment.this._$_findCachedViewById(R.id.ticket);
                    Intrinsics.checkExpressionValueIsNotNull(ticket, "ticket");
                    access$getPresenter$p.signInPhoneAndTicket(phoneWrapper3, ticket.getText().toString());
                }
            }
        });
        ((TextView) _$_findCachedViewById(R.id.can_not_receive_sms_verify_code)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PhTicketSignInFragment$onViewCreated$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PhTicketSignInFragment.this.startFailReceiveSMSVerifyCodeQA();
            }
        });
        sendTicketSuccess();
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.xiaomi.passport.ui.internal.PhTicketSignInFragment$sendTicketSuccess$1] */
    @Override // com.xiaomi.passport.ui.internal.PhTicketSignInContract.View
    public void sendTicketSuccess() {
        if (!this.mIsCountingDown) {
            TextInputEditText textInputEditText = (TextInputEditText) _$_findCachedViewById(R.id.ticket);
            if (textInputEditText != null) {
                textInputEditText.requestFocus();
            }
            TextInputEditText textInputEditText2 = (TextInputEditText) _$_findCachedViewById(R.id.ticket);
            if (textInputEditText2 != null) {
                textInputEditText2.setText("");
            }
            this.sendTicketSuccessCount++;
            final int i = this.sendTicketSuccessCount * 60;
            final long j = i * 1000;
            new CountDownTimer(j, 1000L) { // from class: com.xiaomi.passport.ui.internal.PhTicketSignInFragment$sendTicketSuccess$1
                @Override // android.os.CountDownTimer
                @SuppressLint({"SetTextI18n"})
                public void onTick(long j2) {
                    int i2 = (int) (j2 / 1000);
                    TextView textView = (TextView) PhTicketSignInFragment.this._$_findCachedViewById(R.id.action_get_ph_ticket);
                    if (textView != null) {
                        textView.setText(String.valueOf(i2) + ai.az);
                    }
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    TextView textView = (TextView) PhTicketSignInFragment.this._$_findCachedViewById(R.id.action_get_ph_ticket);
                    if (textView != null) {
                        textView.setClickable(true);
                    }
                    TextView textView2 = (TextView) PhTicketSignInFragment.this._$_findCachedViewById(R.id.action_get_ph_ticket);
                    if (textView2 != null) {
                        textView2.setText(PhTicketSignInFragment.this.getString(R.string.passport_reload_ph_ticket));
                    }
                    PhTicketSignInFragment.this.setMIsCountingDown(false);
                }
            }.start();
            TextView textView = (TextView) _$_findCachedViewById(R.id.action_get_ph_ticket);
            if (textView != null) {
                textView.setClickable(false);
            }
            this.mIsCountingDown = true;
        }
    }

    @Override // com.xiaomi.passport.ui.internal.PhTicketSignInContract.View
    public void enableSendTicketBtn() {
        TextView textView = (TextView) _$_findCachedViewById(R.id.action_get_ph_ticket);
        if (textView != null) {
            textView.setClickable(true);
        }
    }

    @Override // com.xiaomi.passport.ui.internal.PhTicketSignInContract.View
    public void showCaptcha(@NotNull Captcha captcha, @NotNull PhoneWrapper phone) {
        Intrinsics.checkParameterIsNotNull(captcha, "captcha");
        Intrinsics.checkParameterIsNotNull(phone, "phone");
        CommonErrorHandler mCommonErrorHandler = getMCommonErrorHandler();
        Context context = getContext();
        if (context == null) {
            Intrinsics.throwNpe();
        }
        LayoutInflater layoutInflater = getLayoutInflater();
        Intrinsics.checkExpressionValueIsNotNull(layoutInflater, "layoutInflater");
        mCommonErrorHandler.showCaptcha(context, layoutInflater, captcha, new PhTicketSignInFragment$showCaptcha$1(this, phone));
    }

    @Override // com.xiaomi.passport.ui.internal.PhTicketSignInContract.View
    public void showInvalidTicket() {
        TextInputLayout textInputLayout = (TextInputLayout) _$_findCachedViewById(R.id.ticket_wrapper);
        if (textInputLayout != null) {
            textInputLayout.setError(getString(R.string.ticket_invalid));
        }
    }

    @Override // com.xiaomi.passport.ui.internal.PhTicketSignInContract.View
    public void showInvalidTicket(int i) {
        TextInputLayout textInputLayout = (TextInputLayout) _$_findCachedViewById(R.id.ticket_wrapper);
        if (textInputLayout != null) {
            textInputLayout.setError(getString(i));
        }
    }

    @Override // com.xiaomi.passport.ui.internal.PhTicketSignInContract.View
    @SuppressLint({"SetTextI18n"})
    public void chooseToSignInOrSignUp(@NotNull final PhoneSmsAuthCredential authCredential, @NotNull final RegisterUserInfo userInfo) {
        Intrinsics.checkParameterIsNotNull(authCredential, "authCredential");
        Intrinsics.checkParameterIsNotNull(userInfo, "userInfo");
        View inflate = getLayoutInflater().inflate(R.layout.dg_choose_to_signin_signup, (ViewGroup) null);
        View findViewById = inflate.findViewById(R.id.text_view_user_info);
        if (findViewById != null) {
            ((TextView) findViewById).setText("" + getString(R.string.nick_name) + ':' + getDisplayNickname(userInfo) + "\n" + getString(R.string.phone_number) + ':' + userInfo.phone);
            Context context = getContext();
            if (context == null) {
                Intrinsics.throwNpe();
            }
            new AlertDialog.Builder(context).setTitle(R.string.isornot_your_mi_account).setView(inflate).setNegativeButton(R.string.choose_to_signup, new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PhTicketSignInFragment$chooseToSignInOrSignUp$dialog$1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    PhTicketSignInFragment.access$getPresenter$p(PhTicketSignInFragment.this).chooseSignUp(authCredential, userInfo);
                }
            }).setPositiveButton(R.string.choose_to_signin, new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PhTicketSignInFragment$chooseToSignInOrSignUp$dialog$2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    PhTicketSignInFragment.access$getPresenter$p(PhTicketSignInFragment.this).chooseSignIn(authCredential, userInfo);
                }
            }).create().show();
            if (!TextUtils.isEmpty(userInfo.avatarAddress)) {
                this.passportRepo.loadImage(userInfo.avatarAddress).getSuccess(new PhTicketSignInFragment$chooseToSignInOrSignUp$1(inflate));
                return;
            }
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
    }

    private final String getDisplayNickname(RegisterUserInfo registerUserInfo) {
        if (TextUtils.isEmpty(registerUserInfo.userName)) {
            return registerUserInfo.maskedUserId;
        }
        return registerUserInfo.userName;
    }

    @Override // com.xiaomi.passport.ui.internal.PhTicketSignInContract.View
    public void showSetPsw(@NotNull final ChoosePhoneSmsAuthCredential authCredential) {
        Intrinsics.checkParameterIsNotNull(authCredential, "authCredential");
        TextView sign_in_user_id_text = (TextView) _$_findCachedViewById(R.id.sign_in_user_id_text);
        Intrinsics.checkExpressionValueIsNotNull(sign_in_user_id_text, "sign_in_user_id_text");
        sign_in_user_id_text.setVisibility(0);
        TextInputLayout password_wapper = (TextInputLayout) _$_findCachedViewById(R.id.password_wapper);
        Intrinsics.checkExpressionValueIsNotNull(password_wapper, "password_wapper");
        password_wapper.setVisibility(0);
        TextView phone_text = (TextView) _$_findCachedViewById(R.id.phone_text);
        Intrinsics.checkExpressionValueIsNotNull(phone_text, "phone_text");
        phone_text.setVisibility(8);
        TextInputLayout ticket_wrapper = (TextInputLayout) _$_findCachedViewById(R.id.ticket_wrapper);
        Intrinsics.checkExpressionValueIsNotNull(ticket_wrapper, "ticket_wrapper");
        ticket_wrapper.setVisibility(8);
        ((Button) _$_findCachedViewById(R.id.ph_sign_in_btn)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PhTicketSignInFragment$showSetPsw$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChoosePhoneSmsAuthCredential choosePhoneSmsAuthCredential = authCredential;
                TextInputEditText password = (TextInputEditText) PhTicketSignInFragment.this._$_findCachedViewById(R.id.password);
                Intrinsics.checkExpressionValueIsNotNull(password, "password");
                choosePhoneSmsAuthCredential.setNewPsw(password.getText().toString());
                PhTicketSignInFragment.access$getPresenter$p(PhTicketSignInFragment.this).chooseSignUp(authCredential);
            }
        });
    }

    @Override // com.xiaomi.passport.ui.internal.PhTicketSignInContract.View
    public void showInvalidPsw(@NotNull final ChoosePhoneSmsAuthCredential authCredential, int i) {
        Intrinsics.checkParameterIsNotNull(authCredential, "authCredential");
        TextInputLayout password_wapper = (TextInputLayout) _$_findCachedViewById(R.id.password_wapper);
        Intrinsics.checkExpressionValueIsNotNull(password_wapper, "password_wapper");
        password_wapper.setError(getString(i));
        ((Button) _$_findCachedViewById(R.id.ph_sign_in_btn)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PhTicketSignInFragment$showInvalidPsw$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChoosePhoneSmsAuthCredential choosePhoneSmsAuthCredential = authCredential;
                TextInputEditText password = (TextInputEditText) PhTicketSignInFragment.this._$_findCachedViewById(R.id.password);
                Intrinsics.checkExpressionValueIsNotNull(password, "password");
                choosePhoneSmsAuthCredential.setNewPsw(password.getText().toString());
                PhTicketSignInFragment.access$getPresenter$p(PhTicketSignInFragment.this).chooseSignUp(authCredential);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startFailReceiveSMSVerifyCodeQA() {
        gotoFragment(getMWebAuth().getNotificationFragment("https://static.account.xiaomi.com/html/faq/faqSMSerror.html"), true);
    }
}
