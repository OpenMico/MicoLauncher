package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.data.MetaLoginData;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.StatConstants;
import com.xiaomi.passport.ui.TrackEventManager;
import com.xiaomi.passport.ui.internal.PhoneNumUtil;
import com.xiaomi.passport.ui.internal.PswSignInContract;
import com.xiaomi.passport.ui.internal.SignInContract;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FragmentIdPswAuth.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000 .2\u00020\u00012\u00020\u0002:\u0001.B\u0005¢\u0006\u0002\u0010\u0003J(\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\b\u0001\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\u001c\u0010\u001e\u001a\u00020\u001f2\b\b\u0001\u0010 \u001a\u00020\u00172\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\u0018\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020\u0005H\u0016J\u0010\u0010(\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020\u0005H\u0016J \u0010)\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020%2\u0006\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020,H\u0016J\b\u0010-\u001a\u00020\u001fH\u0002R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006/"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PswSignInFragment;", "Lcom/xiaomi/passport/ui/internal/BaseSignInFragment;", "Lcom/xiaomi/passport/ui/internal/PswSignInContract$View;", "()V", "mSignInUserId", "", "getMSignInUserId", "()Ljava/lang/String;", "setMSignInUserId", "(Ljava/lang/String;)V", "phoneAuthProvider", "Lcom/xiaomi/passport/ui/internal/BaseAuthProvider;", "getPhoneAuthProvider", "()Lcom/xiaomi/passport/ui/internal/BaseAuthProvider;", "setPhoneAuthProvider", "(Lcom/xiaomi/passport/ui/internal/BaseAuthProvider;)V", "presenter", "Lcom/xiaomi/passport/ui/internal/PswSignInContract$Presenter;", "getPresenter", "()Lcom/xiaomi/passport/ui/internal/PswSignInContract$Presenter;", "setPresenter", "(Lcom/xiaomi/passport/ui/internal/PswSignInContract$Presenter;)V", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "", OneTrack.Event.VIEW, "showCaptcha", "captcha", "Lcom/xiaomi/passport/ui/internal/Captcha;", "authCredential", "Lcom/xiaomi/passport/ui/internal/IdPswBaseAuthCredential;", "showPswError", "msg", "showUserNameError", "showVStep2Code", "step1Token", "metaLoginData", "Lcom/xiaomi/accountsdk/account/data/MetaLoginData;", "specifyUserId", "Companion", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class PswSignInFragment extends BaseSignInFragment implements PswSignInContract.View {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    @Nullable
    private String mSignInUserId;
    @NotNull
    private BaseAuthProvider phoneAuthProvider = PassportUI.INSTANCE.getBaseAuthProvider(PassportUI.PHONE_SMS_AUTH_PROVIDER);
    @NotNull
    public PswSignInContract.Presenter presenter;

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

    @Override // com.xiaomi.passport.ui.internal.BaseSignInFragment, com.xiaomi.passport.ui.internal.SignInFragment, androidx.fragment.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    public PswSignInFragment() {
        super(PassportUI.ID_PSW_AUTH_PROVIDER);
    }

    @NotNull
    public final PswSignInContract.Presenter getPresenter() {
        PswSignInContract.Presenter presenter = this.presenter;
        if (presenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenter");
        }
        return presenter;
    }

    public final void setPresenter(@NotNull PswSignInContract.Presenter presenter) {
        Intrinsics.checkParameterIsNotNull(presenter, "<set-?>");
        this.presenter = presenter;
    }

    @NotNull
    public final BaseAuthProvider getPhoneAuthProvider() {
        return this.phoneAuthProvider;
    }

    public final void setPhoneAuthProvider(@NotNull BaseAuthProvider baseAuthProvider) {
        Intrinsics.checkParameterIsNotNull(baseAuthProvider, "<set-?>");
        this.phoneAuthProvider = baseAuthProvider;
    }

    @Nullable
    public final String getMSignInUserId() {
        return this.mSignInUserId;
    }

    public final void setMSignInUserId(@Nullable String str) {
        this.mSignInUserId = str;
    }

    /* compiled from: FragmentIdPswAuth.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¨\u0006\b"}, d2 = {"Lcom/xiaomi/passport/ui/internal/PswSignInFragment$Companion;", "", "()V", "newInstance", "Lcom/xiaomi/passport/ui/internal/PswSignInFragment;", "sid", "", BaseConstants.EXTRA_USER_ID, "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final PswSignInFragment newInstance(@NotNull String sid) {
            Intrinsics.checkParameterIsNotNull(sid, "sid");
            return newInstance(sid, null);
        }

        @NotNull
        public final PswSignInFragment newInstance(@NotNull String sid, @Nullable String str) {
            Intrinsics.checkParameterIsNotNull(sid, "sid");
            PswSignInFragment pswSignInFragment = new PswSignInFragment();
            Bundle bundle = new Bundle();
            bundle.putString("sid", sid);
            bundle.putString(BaseConstants.EXTRA_USER_ID, str);
            pswSignInFragment.setArguments(bundle);
            return pswSignInFragment;
        }
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        return inflater.inflate(R.layout.fg_psw_signin, viewGroup, false);
    }

    @Override // com.xiaomi.passport.ui.internal.BaseSignInFragment, androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        super.onViewCreated(view, bundle);
        Context context = getContext();
        PswSignInContract.Presenter presenter = this.presenter;
        if (presenter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("presenter");
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, 17367043, presenter.getSignedInUserIds());
        AutoCompleteTextView userId = (AutoCompleteTextView) _$_findCachedViewById(R.id.userId);
        Intrinsics.checkExpressionValueIsNotNull(userId, "userId");
        userId.setThreshold(0);
        ((AutoCompleteTextView) _$_findCachedViewById(R.id.userId)).setAdapter(arrayAdapter);
        ((Button) _$_findCachedViewById(R.id.sign_in_btn)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PswSignInFragment$onViewCreated$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                String str;
                CheckBox cb_agree_something = (CheckBox) PswSignInFragment.this._$_findCachedViewById(R.id.cb_agree_something);
                Intrinsics.checkExpressionValueIsNotNull(cb_agree_something, "cb_agree_something");
                if (cb_agree_something.isChecked()) {
                    if (PswSignInFragment.this.getMSignInUserId() != null) {
                        str = PswSignInFragment.this.getMSignInUserId();
                    } else {
                        AutoCompleteTextView userId2 = (AutoCompleteTextView) PswSignInFragment.this._$_findCachedViewById(R.id.userId);
                        Intrinsics.checkExpressionValueIsNotNull(userId2, "userId");
                        str = userId2.getText().toString();
                    }
                    TextInputEditText password = (TextInputEditText) PswSignInFragment.this._$_findCachedViewById(R.id.password);
                    Intrinsics.checkExpressionValueIsNotNull(password, "password");
                    String obj = password.getText().toString();
                    if (TextUtils.isEmpty(str)) {
                        PswSignInFragment pswSignInFragment = PswSignInFragment.this;
                        String string = pswSignInFragment.getString(R.string.passport_empty_user_name);
                        Intrinsics.checkExpressionValueIsNotNull(string, "getString(R.string.passport_empty_user_name)");
                        pswSignInFragment.showUserNameError(string);
                    } else if (TextUtils.isEmpty(obj)) {
                        PswSignInFragment pswSignInFragment2 = PswSignInFragment.this;
                        String string2 = pswSignInFragment2.getString(R.string.passport_empty_password);
                        Intrinsics.checkExpressionValueIsNotNull(string2, "getString(R.string.passport_empty_password)");
                        pswSignInFragment2.showPswError(string2);
                    } else {
                        PswSignInContract.Presenter presenter2 = PswSignInFragment.this.getPresenter();
                        if (str == null) {
                            Intrinsics.throwNpe();
                        }
                        presenter2.signInIdAndPsw(str, obj);
                    }
                } else {
                    TextInputLayout tv_user_agreement_tip = (TextInputLayout) PswSignInFragment.this._$_findCachedViewById(R.id.tv_user_agreement_tip);
                    Intrinsics.checkExpressionValueIsNotNull(tv_user_agreement_tip, "tv_user_agreement_tip");
                    tv_user_agreement_tip.setError(PswSignInFragment.this.getString(R.string.passport_error_user_agreement_error));
                }
            }
        });
        ((TextView) _$_findCachedViewById(R.id.action_find_psw)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PswSignInFragment$onViewCreated$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_PASSWORD_CLICK_FORGOT_PASSWORD);
                PswSignInFragment pswSignInFragment = PswSignInFragment.this;
                pswSignInFragment.gotoFragment(pswSignInFragment.getMWebAuth().getFindPswFragment(), true);
            }
        });
        ((TextView) _$_findCachedViewById(R.id.action_goto_siginup_from_psw)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PswSignInFragment$onViewCreated$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                TrackEventManager.addEvent(StatConstants.STAT_CATEGORY_PASSWORD_CLICK_SIGN_UP);
                PswSignInFragment pswSignInFragment = PswSignInFragment.this;
                WebAuth mWebAuth = pswSignInFragment.getMWebAuth();
                Bundle arguments = PswSignInFragment.this.getArguments();
                if (arguments == null) {
                    Intrinsics.throwNpe();
                }
                String string = arguments.getString("sid");
                Intrinsics.checkExpressionValueIsNotNull(string, "arguments!!.getString(\"sid\")");
                Context context2 = PswSignInFragment.this.getContext();
                if (context2 == null) {
                    Intrinsics.throwNpe();
                }
                PhoneNumUtil.CountryPhoneNumData smartGetCountryCodeData = CountryCodeUtilsExtensionKt.smartGetCountryCodeData(context2, PswSignInFragment.this.getDefaultCountryCodeWithPrefix());
                pswSignInFragment.gotoFragment(mWebAuth.getSignUpFragment(string, smartGetCountryCodeData != null ? smartGetCountryCodeData.countryISO : null), true);
            }
        });
        ((TextView) _$_findCachedViewById(R.id.action_ph_ticket_signin)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PswSignInFragment$onViewCreated$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PswSignInFragment pswSignInFragment = PswSignInFragment.this;
                BaseAuthProvider phoneAuthProvider = pswSignInFragment.getPhoneAuthProvider();
                Bundle arguments = PswSignInFragment.this.getArguments();
                if (arguments == null) {
                    Intrinsics.throwNpe();
                }
                String string = arguments.getString("sid");
                Intrinsics.checkExpressionValueIsNotNull(string, "arguments!!.getString(\"sid\")");
                SignInContract.View.DefaultImpls.gotoFragment$default(pswSignInFragment, phoneAuthProvider.getFragment(string, PswSignInFragment.this.getDefaultCountryCodeWithPrefix()), false, 2, null);
            }
        });
        ((CheckBox) _$_findCachedViewById(R.id.cb_agree_something)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaomi.passport.ui.internal.PswSignInFragment$onViewCreated$5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    TextInputLayout tv_user_agreement_tip = (TextInputLayout) PswSignInFragment.this._$_findCachedViewById(R.id.tv_user_agreement_tip);
                    Intrinsics.checkExpressionValueIsNotNull(tv_user_agreement_tip, "tv_user_agreement_tip");
                    tv_user_agreement_tip.setError("");
                }
            }
        });
        Bundle arguments = getArguments();
        if (arguments == null) {
            Intrinsics.throwNpe();
        }
        this.mSignInUserId = arguments.getString(BaseConstants.EXTRA_USER_ID);
        if (this.mSignInUserId != null) {
            specifyUserId();
        }
    }

    private final void specifyUserId() {
        TextView sign_in_user_id_text = (TextView) _$_findCachedViewById(R.id.sign_in_user_id_text);
        Intrinsics.checkExpressionValueIsNotNull(sign_in_user_id_text, "sign_in_user_id_text");
        sign_in_user_id_text.setVisibility(0);
        TextView sign_in_user_id_text2 = (TextView) _$_findCachedViewById(R.id.sign_in_user_id_text);
        Intrinsics.checkExpressionValueIsNotNull(sign_in_user_id_text2, "sign_in_user_id_text");
        sign_in_user_id_text2.setText(getString(R.string.passport_user_id_intro, this.mSignInUserId));
        TextInputLayout userId_wapper = (TextInputLayout) _$_findCachedViewById(R.id.userId_wapper);
        Intrinsics.checkExpressionValueIsNotNull(userId_wapper, "userId_wapper");
        userId_wapper.setVisibility(8);
        TextView action_ph_ticket_signin = (TextView) _$_findCachedViewById(R.id.action_ph_ticket_signin);
        Intrinsics.checkExpressionValueIsNotNull(action_ph_ticket_signin, "action_ph_ticket_signin");
        action_ph_ticket_signin.setVisibility(8);
        TextView action_goto_siginup_from_psw = (TextView) _$_findCachedViewById(R.id.action_goto_siginup_from_psw);
        Intrinsics.checkExpressionValueIsNotNull(action_goto_siginup_from_psw, "action_goto_siginup_from_psw");
        action_goto_siginup_from_psw.setVisibility(8);
        hideSns();
    }

    @Override // com.xiaomi.passport.ui.internal.PswSignInContract.View
    public void showUserNameError(@NotNull String msg) {
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        TextInputLayout textInputLayout = (TextInputLayout) _$_findCachedViewById(R.id.password_wapper);
        if (textInputLayout != null) {
            textInputLayout.setErrorEnabled(false);
        }
        TextInputLayout textInputLayout2 = (TextInputLayout) _$_findCachedViewById(R.id.userId_wapper);
        if (textInputLayout2 != null) {
            textInputLayout2.setError(msg);
        }
    }

    @Override // com.xiaomi.passport.ui.internal.PswSignInContract.View
    public void showPswError(@NotNull String msg) {
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        TextInputLayout textInputLayout = (TextInputLayout) _$_findCachedViewById(R.id.userId_wapper);
        if (textInputLayout != null) {
            textInputLayout.setErrorEnabled(false);
        }
        TextInputLayout textInputLayout2 = (TextInputLayout) _$_findCachedViewById(R.id.password_wapper);
        if (textInputLayout2 != null) {
            textInputLayout2.setError(msg);
        }
    }

    @Override // com.xiaomi.passport.ui.internal.PswSignInContract.View
    public void showCaptcha(@NotNull Captcha captcha, @NotNull IdPswBaseAuthCredential authCredential) {
        Intrinsics.checkParameterIsNotNull(captcha, "captcha");
        Intrinsics.checkParameterIsNotNull(authCredential, "authCredential");
        CommonErrorHandler mCommonErrorHandler = getMCommonErrorHandler();
        Context context = getContext();
        if (context == null) {
            Intrinsics.throwNpe();
        }
        LayoutInflater layoutInflater = getLayoutInflater();
        Intrinsics.checkExpressionValueIsNotNull(layoutInflater, "layoutInflater");
        mCommonErrorHandler.showCaptcha(context, layoutInflater, captcha, new PswSignInFragment$showCaptcha$1(this, authCredential));
    }

    @Override // com.xiaomi.passport.ui.internal.PswSignInContract.View
    public void showVStep2Code(@NotNull final IdPswBaseAuthCredential authCredential, @NotNull final String step1Token, @NotNull final MetaLoginData metaLoginData) {
        Intrinsics.checkParameterIsNotNull(authCredential, "authCredential");
        Intrinsics.checkParameterIsNotNull(step1Token, "step1Token");
        Intrinsics.checkParameterIsNotNull(metaLoginData, "metaLoginData");
        View inflate = getLayoutInflater().inflate(R.layout.dg_vcode_layout, (ViewGroup) null);
        View findViewById = inflate.findViewById(R.id.cb_add_to_trust_device);
        if (findViewById != null) {
            final CheckBox checkBox = (CheckBox) findViewById;
            View findViewById2 = inflate.findViewById(R.id.v_code_input);
            if (findViewById2 != null) {
                final EditText editText = (EditText) findViewById2;
                Context context = getContext();
                if (context == null) {
                    Intrinsics.throwNpe();
                }
                new AlertDialog.Builder(context).setTitle(R.string.v_code_title).setView(inflate).setPositiveButton(17039370, new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.PswSignInFragment$showVStep2Code$dialog$1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        PswSignInFragment.this.getPresenter().signInVStep2(authCredential.getId(), step1Token, metaLoginData, editText.getText().toString(), checkBox.isChecked());
                    }
                }).create().show();
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type android.widget.EditText");
        }
        throw new TypeCastException("null cannot be cast to non-null type android.widget.CheckBox");
    }
}
