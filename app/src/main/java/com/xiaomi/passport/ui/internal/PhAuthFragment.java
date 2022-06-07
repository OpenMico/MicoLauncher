package com.xiaomi.passport.ui.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputLayout;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.StatConstants;
import com.xiaomi.passport.ui.TrackEventManager;
import com.xiaomi.passport.ui.internal.PhAuthContract;
import com.xiaomi.passport.ui.internal.SignInContract;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FragmentGetPhAuthMethod.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000 22\u00020\u00012\u00020\u0002:\u00012B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\"\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001e2\b\u0010 \u001a\u0004\u0018\u00010!H\u0017J(\u0010\"\u001a\u0004\u0018\u00010#2\b\b\u0001\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010'2\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J\b\u0010*\u001a\u00020\u0015H\u0016J\u001c\u0010+\u001a\u00020\u00152\b\b\u0001\u0010,\u001a\u00020#2\b\u0010(\u001a\u0004\u0018\u00010)H\u0017J\u0018\u0010-\u001a\u00020\u00152\u0006\u0010.\u001a\u00020/2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u00100\u001a\u00020\u00152\u0006\u00101\u001a\u00020\u001eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u00063"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PhAuthFragment;", "Lcom/xiaomi/passport/ui/internal/BaseSignInFragment;", "Lcom/xiaomi/passport/ui/internal/PhAuthContract$View;", "()V", "idPswAuthProvider", "Lcom/xiaomi/passport/ui/internal/BaseAuthProvider;", "mPhoneViewWrapper", "Lcom/xiaomi/passport/ui/internal/PhoneViewWrapper;", "passportRepo", "Lcom/xiaomi/passport/ui/internal/PassportRepo;", "getPassportRepo", "()Lcom/xiaomi/passport/ui/internal/PassportRepo;", "setPassportRepo", "(Lcom/xiaomi/passport/ui/internal/PassportRepo;)V", "presenter", "Lcom/xiaomi/passport/ui/internal/PhAuthContract$Presenter;", "getPresenter", "()Lcom/xiaomi/passport/ui/internal/PhAuthContract$Presenter;", "setPresenter", "(Lcom/xiaomi/passport/ui/internal/PhAuthContract$Presenter;)V", "clearPhonePopList", "", "gotoPswSignIn", BaseConstants.EXTRA_USER_ID, "", "gotoTicketSignIn", "phone", "Lcom/xiaomi/passport/ui/internal/PhoneWrapper;", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", OneTrack.Event.VIEW, "showCaptcha", "captcha", "Lcom/xiaomi/passport/ui/internal/Captcha;", "showPhoneNumError", "msgRes", "Companion", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PhAuthFragment extends BaseSignInFragment implements PhAuthContract.View {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private PhoneViewWrapper mPhoneViewWrapper;
    @NotNull
    public PhAuthContract.Presenter presenter;
    private BaseAuthProvider idPswAuthProvider = PassportUI.INSTANCE.getBaseAuthProvider(PassportUI.ID_PSW_AUTH_PROVIDER);
    @NotNull
    private PassportRepo passportRepo = new PassportRepoImpl();

    @Override // com.xiaomi.passport.ui.internal.BaseSignInFragment, com.xiaomi.passport.ui.internal.SignInFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.xiaomi.passport.ui.internal.BaseSignInFragment, com.xiaomi.passport.ui.internal.SignInFragment
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

    public PhAuthFragment() {
        super(PassportUI.PHONE_SMS_AUTH_PROVIDER);
    }

    @NotNull
    public final PhAuthContract.Presenter getPresenter() {
        PhAuthContract.Presenter presenter = this.presenter;
        if (presenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenter");
        }
        return presenter;
    }

    public final void setPresenter(@NotNull PhAuthContract.Presenter presenter) {
        Intrinsics.checkParameterIsNotNull(presenter, "<set-?>");
        this.presenter = presenter;
    }

    @NotNull
    public final PassportRepo getPassportRepo() {
        return this.passportRepo;
    }

    public final void setPassportRepo(@NotNull PassportRepo passportRepo) {
        Intrinsics.checkParameterIsNotNull(passportRepo, "<set-?>");
        this.passportRepo = passportRepo;
    }

    /* compiled from: FragmentGetPhAuthMethod.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PhAuthFragment$Companion;", "", "()V", "newInstance", "Lcom/xiaomi/passport/ui/internal/PhAuthFragment;", "sid", "", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final PhAuthFragment newInstance(@NotNull String sid) {
            Intrinsics.checkParameterIsNotNull(sid, "sid");
            PhAuthFragment phAuthFragment = new PhAuthFragment();
            Bundle bundle = new Bundle();
            bundle.putString("sid", sid);
            phAuthFragment.setArguments(bundle);
            return phAuthFragment;
        }
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        return inflater.inflate(R.layout.fg_ph_auth_method, viewGroup, false);
    }

    @Override // com.xiaomi.passport.ui.internal.BaseSignInFragment, androidx.fragment.app.Fragment
    @SuppressLint({"SetTextI18n"})
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        super.onViewCreated(view, bundle);
        ((Button) _$_findCachedViewById(R.id.ph_sign_in_btn)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PhAuthFragment$onViewCreated$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PhoneViewWrapper phoneViewWrapper;
                CheckBox cb_agree_something = (CheckBox) PhAuthFragment.this._$_findCachedViewById(R.id.cb_agree_something);
                Intrinsics.checkExpressionValueIsNotNull(cb_agree_something, "cb_agree_something");
                if (cb_agree_something.isChecked()) {
                    TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_SMS_CLICK_NEXT_AFTER_GET_PHONE);
                    PhAuthContract.Presenter presenter = PhAuthFragment.this.getPresenter();
                    phoneViewWrapper = PhAuthFragment.this.mPhoneViewWrapper;
                    presenter.getPhoneAuthMethod(phoneViewWrapper != null ? phoneViewWrapper.getPhoneWrapper() : null);
                    return;
                }
                TextInputLayout user_agreement_error_tip = (TextInputLayout) PhAuthFragment.this._$_findCachedViewById(R.id.user_agreement_error_tip);
                Intrinsics.checkExpressionValueIsNotNull(user_agreement_error_tip, "user_agreement_error_tip");
                user_agreement_error_tip.setError(PhAuthFragment.this.getString(R.string.passport_error_user_agreement_error));
            }
        });
        ((TextView) _$_findCachedViewById(R.id.action_goto_psw_signin)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PhAuthFragment$onViewCreated$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                BaseAuthProvider baseAuthProvider;
                PhAuthFragment phAuthFragment = PhAuthFragment.this;
                baseAuthProvider = phAuthFragment.idPswAuthProvider;
                Bundle arguments = PhAuthFragment.this.getArguments();
                if (arguments == null) {
                    Intrinsics.throwNpe();
                }
                String string = arguments.getString("sid");
                Intrinsics.checkExpressionValueIsNotNull(string, "arguments!!.getString(\"sid\")");
                SignInContract.View.DefaultImpls.gotoFragment$default(phAuthFragment, baseAuthProvider.getFragment(string, PhAuthFragment.this.getDefaultCountryCodeWithPrefix()), false, 2, null);
            }
        });
        ((CheckBox) _$_findCachedViewById(R.id.cb_agree_something)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaomi.passport.ui.internal.PhAuthFragment$onViewCreated$3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    TextInputLayout user_agreement_error_tip = (TextInputLayout) PhAuthFragment.this._$_findCachedViewById(R.id.user_agreement_error_tip);
                    Intrinsics.checkExpressionValueIsNotNull(user_agreement_error_tip, "user_agreement_error_tip");
                    user_agreement_error_tip.setError("");
                }
            }
        });
        ((TextView) _$_findCachedViewById(R.id.passport_country_code_text)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PhAuthFragment$onViewCreated$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PhAuthFragment phAuthFragment = PhAuthFragment.this;
                phAuthFragment.startActivityForResult(new Intent(phAuthFragment.getActivity(), AreaCodePickerActivity.class), 2001);
            }
        });
        if (getDefaultCountryCodeWithPrefix() != null) {
            TextView passport_country_code_text = (TextView) _$_findCachedViewById(R.id.passport_country_code_text);
            Intrinsics.checkExpressionValueIsNotNull(passport_country_code_text, "passport_country_code_text");
            passport_country_code_text.setText(getDefaultCountryCodeWithPrefix());
        } else {
            TextView passport_country_code_text2 = (TextView) _$_findCachedViewById(R.id.passport_country_code_text);
            Intrinsics.checkExpressionValueIsNotNull(passport_country_code_text2, "passport_country_code_text");
            if (TextUtils.isEmpty(passport_country_code_text2.getText().toString())) {
                TextView passport_country_code_text3 = (TextView) _$_findCachedViewById(R.id.passport_country_code_text);
                Intrinsics.checkExpressionValueIsNotNull(passport_country_code_text3, "passport_country_code_text");
                passport_country_code_text3.setText(PassportUI.CHINA_COUNTRY_CODE);
            }
        }
        Bundle arguments = getArguments();
        if (arguments == null) {
            Intrinsics.throwNpe();
        }
        String string = arguments.getString("sid");
        Intrinsics.checkExpressionValueIsNotNull(string, "arguments!!.getString(\"sid\")");
        Context context = getContext();
        if (context == null) {
            Intrinsics.throwNpe();
        }
        AutoCompleteTextView phone = (AutoCompleteTextView) _$_findCachedViewById(R.id.phone);
        Intrinsics.checkExpressionValueIsNotNull(phone, "phone");
        TextView passport_country_code_text4 = (TextView) _$_findCachedViewById(R.id.passport_country_code_text);
        Intrinsics.checkExpressionValueIsNotNull(passport_country_code_text4, "passport_country_code_text");
        ImageView delete_phone = (ImageView) _$_findCachedViewById(R.id.delete_phone);
        Intrinsics.checkExpressionValueIsNotNull(delete_phone, "delete_phone");
        this.mPhoneViewWrapper = new PhoneViewWrapper(string, context, phone, passport_country_code_text4, delete_phone, (TextView) _$_findCachedViewById(R.id.passport_operator_license));
    }

    @Override // androidx.fragment.app.Fragment
    @SuppressLint({"SetTextI18n"})
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 2001 && i2 == -1) {
            if (intent == null) {
                Intrinsics.throwNpe();
            }
            String stringExtra = intent.getStringExtra("code");
            TextView passport_country_code_text = (TextView) _$_findCachedViewById(R.id.passport_country_code_text);
            Intrinsics.checkExpressionValueIsNotNull(passport_country_code_text, "passport_country_code_text");
            passport_country_code_text.setText('+' + stringExtra);
        }
    }

    @Override // com.xiaomi.passport.ui.internal.BaseSignInFragment, com.xiaomi.passport.ui.internal.SignInFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        PhoneViewWrapper phoneViewWrapper = this.mPhoneViewWrapper;
        if (phoneViewWrapper != null) {
            phoneViewWrapper.destroy();
        }
        this.mPhoneViewWrapper = null;
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.xiaomi.passport.ui.internal.PhAuthContract.View
    public void clearPhonePopList() {
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) _$_findCachedViewById(R.id.phone);
        if (autoCompleteTextView != null) {
            autoCompleteTextView.setText("");
        }
        AutoCompleteTextView autoCompleteTextView2 = (AutoCompleteTextView) _$_findCachedViewById(R.id.phone);
        if (autoCompleteTextView2 != null) {
            autoCompleteTextView2.setEnabled(true);
        }
    }

    @Override // com.xiaomi.passport.ui.internal.PhAuthContract.View
    public void gotoTicketSignIn(@NotNull PhoneWrapper phone) {
        Intrinsics.checkParameterIsNotNull(phone, "phone");
        gotoFragment(PhTicketSignInFragment.Companion.newInstance(phone.getSid(), phone), true);
    }

    @Override // com.xiaomi.passport.ui.internal.PhAuthContract.View
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
        mCommonErrorHandler.showCaptcha(context, layoutInflater, captcha, new PhAuthFragment$showCaptcha$1(this, phone));
    }

    @Override // com.xiaomi.passport.ui.internal.PhAuthContract.View
    public void showPhoneNumError(int i) {
        TextInputLayout textInputLayout = (TextInputLayout) _$_findCachedViewById(R.id.phone_wrapper);
        if (textInputLayout != null) {
            textInputLayout.setError(getString(i));
        }
    }

    @Override // com.xiaomi.passport.ui.internal.PhAuthContract.View
    public void gotoPswSignIn(@NotNull String userId) {
        Intrinsics.checkParameterIsNotNull(userId, "userId");
        BaseAuthProvider baseAuthProvider = this.idPswAuthProvider;
        if (baseAuthProvider != null) {
            IdPswAuthProvider idPswAuthProvider = (IdPswAuthProvider) baseAuthProvider;
            Bundle arguments = getArguments();
            if (arguments == null) {
                Intrinsics.throwNpe();
            }
            String string = arguments.getString("sid");
            Intrinsics.checkExpressionValueIsNotNull(string, "arguments!!.getString(\"sid\")");
            gotoFragment(idPswAuthProvider.getFragmentWithUserId(string, userId), true);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.xiaomi.passport.ui.internal.IdPswAuthProvider");
    }
}
