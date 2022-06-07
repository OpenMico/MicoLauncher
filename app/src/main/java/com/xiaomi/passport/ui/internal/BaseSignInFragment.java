package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.umeng.analytics.pro.c;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.diagnosis.DiagnosisLauncher;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FragmentBaseSign.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0015\u001a\u00020\u0003H\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0004J\b\u0010\u0018\u001a\u00020\u0017H\u0016J\u001c\u0010\u0019\u001a\u00020\u00172\b\b\u0001\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u0017H\u0002J\b\u0010\u001f\u001a\u00020\u0017H\u0002J\b\u0010 \u001a\u00020\u0017H\u0002R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\u0004R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/xiaomi/passport/ui/internal/BaseSignInFragment;", "Lcom/xiaomi/passport/ui/internal/SignInFragment;", c.M, "", "(Ljava/lang/String;)V", "defaultCountryCodeWithPrefix", "getDefaultCountryCodeWithPrefix", "()Ljava/lang/String;", "setDefaultCountryCodeWithPrefix", "mAuthProvider", "Lcom/xiaomi/passport/ui/internal/BaseAuthProvider;", "mDiagnosisLauncher", "Lcom/xiaomi/passport/ui/diagnosis/DiagnosisLauncher;", "mOnDiagnosisClicked", "Landroid/view/View$OnClickListener;", "mUserLicenseUtils", "Lcom/xiaomi/passport/ui/internal/UserLicenseUtils;", "getProvider", "span1", "Landroid/text/style/ClickableSpan;", "span2", "getLicense", "hideSns", "", "onDestroyView", "onViewCreated", OneTrack.Event.VIEW, "Landroid/view/View;", "savedInstanceState", "Landroid/os/Bundle;", "setAgreementText", "showBindTitle", "showSns", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public abstract class BaseSignInFragment extends SignInFragment {
    private HashMap _$_findViewCache;
    @Nullable
    private String defaultCountryCodeWithPrefix;
    private BaseAuthProvider mAuthProvider;
    private DiagnosisLauncher mDiagnosisLauncher;
    @NotNull
    private final String provider;
    private ClickableSpan span1;
    private ClickableSpan span2;
    private final UserLicenseUtils mUserLicenseUtils = new UserLicenseUtils();
    private final View.OnClickListener mOnDiagnosisClicked = new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.BaseSignInFragment$mOnDiagnosisClicked$1
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            DiagnosisLauncher diagnosisLauncher;
            diagnosisLauncher = BaseSignInFragment.this.mDiagnosisLauncher;
            if (diagnosisLauncher != null) {
                diagnosisLauncher.onClick();
            }
        }
    };

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

    public BaseSignInFragment(@NotNull String provider) {
        Intrinsics.checkParameterIsNotNull(provider, "provider");
        this.provider = provider;
        this.mAuthProvider = PassportUI.INSTANCE.getBaseAuthProvider(this.provider);
    }

    @NotNull
    public final String getProvider() {
        return this.provider;
    }

    @Nullable
    public final String getDefaultCountryCodeWithPrefix() {
        return this.defaultCountryCodeWithPrefix;
    }

    public final void setDefaultCountryCodeWithPrefix(@Nullable String str) {
        this.defaultCountryCodeWithPrefix = str;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        super.onViewCreated(view, bundle);
        this.mDiagnosisLauncher = new DiagnosisLauncher(getActivity());
        BaseAuthProvider baseAuthProvider = this.mAuthProvider;
        Bundle arguments = getArguments();
        if (arguments == null) {
            Intrinsics.throwNpe();
        }
        String string = arguments.getString("sid");
        Intrinsics.checkExpressionValueIsNotNull(string, "arguments!!.getString(\"sid\")");
        baseAuthProvider.setPresenter(string, this);
        String titleText = getString(R.string.passport_auth_title);
        Intrinsics.checkExpressionValueIsNotNull(titleText, "titleText");
        if (titleText != null) {
            String str = titleText;
            if (!TextUtils.isEmpty(StringsKt.trim(str).toString())) {
                ImageView mi_logo = (ImageView) _$_findCachedViewById(R.id.mi_logo);
                Intrinsics.checkExpressionValueIsNotNull(mi_logo, "mi_logo");
                mi_logo.setVisibility(8);
                TextView signin_title = (TextView) _$_findCachedViewById(R.id.signin_title);
                Intrinsics.checkExpressionValueIsNotNull(signin_title, "signin_title");
                signin_title.setVisibility(0);
                TextView signin_title2 = (TextView) _$_findCachedViewById(R.id.signin_title);
                Intrinsics.checkExpressionValueIsNotNull(signin_title2, "signin_title");
                signin_title2.setText(str);
            }
            ((TextView) _$_findCachedViewById(R.id.signin_title)).setOnClickListener(this.mOnDiagnosisClicked);
            ((ImageView) _$_findCachedViewById(R.id.mi_logo)).setOnClickListener(this.mOnDiagnosisClicked);
            if (SNSAuthProvider.Companion.isBindingSnsAccount()) {
                showBindTitle();
                hideSns();
            } else {
                showSns();
            }
            setAgreementText();
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
    }

    @Override // com.xiaomi.passport.ui.internal.SignInFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        TextView textView = (TextView) _$_findCachedViewById(R.id.tv_user_agreement);
        CharSequence text = textView != null ? textView.getText() : null;
        if (text != null) {
            ((Spannable) text).removeSpan(this.span1);
            TextView textView2 = (TextView) _$_findCachedViewById(R.id.tv_user_agreement);
            CharSequence text2 = textView2 != null ? textView2.getText() : null;
            if (text2 != null) {
                ((Spannable) text2).removeSpan(this.span2);
                ClickableSpan clickableSpan = null;
                this.span1 = clickableSpan;
                this.span2 = clickableSpan;
                _$_clearFindViewByIdCache();
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type android.text.Spannable");
        }
        throw new TypeCastException("null cannot be cast to non-null type android.text.Spannable");
    }

    private final void showBindTitle() {
        ImageView mi_logo = (ImageView) _$_findCachedViewById(R.id.mi_logo);
        Intrinsics.checkExpressionValueIsNotNull(mi_logo, "mi_logo");
        mi_logo.setVisibility(8);
        TextView signin_title = (TextView) _$_findCachedViewById(R.id.signin_title);
        Intrinsics.checkExpressionValueIsNotNull(signin_title, "signin_title");
        signin_title.setVisibility(0);
        ((TextView) _$_findCachedViewById(R.id.signin_title)).setText(R.string.bind_sign_in_title);
    }

    protected final void hideSns() {
        LinearLayout sns_layout = (LinearLayout) _$_findCachedViewById(R.id.sns_layout);
        Intrinsics.checkExpressionValueIsNotNull(sns_layout, "sns_layout");
        sns_layout.setVisibility(8);
    }

    private final void showSns() {
        int i = 0;
        for (final AuthProvider authProvider : PassportUI.INSTANCE.getMProviders$passportui_release()) {
            if ((authProvider instanceof SNSAuthProvider) && !PassportUI.INSTANCE.getSnsInvisibleList().contains(authProvider.getName())) {
                View inflate = getLayoutInflater().inflate(R.layout.sns_item, (ViewGroup) null);
                View findViewById = inflate.findViewById(R.id.sns_image);
                if (findViewById != null) {
                    ImageView imageView = (ImageView) findViewById;
                    SNSAuthProvider sNSAuthProvider = (SNSAuthProvider) authProvider;
                    imageView.setImageResource(sNSAuthProvider.getIconRes());
                    ((LinearLayout) _$_findCachedViewById(R.id.sns_list_layout)).addView(inflate);
                    imageView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.BaseSignInFragment$showSns$1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            if (BaseSignInFragment.this.getActivity() != null) {
                                FragmentActivity activity = BaseSignInFragment.this.getActivity();
                                if (activity == null) {
                                    Intrinsics.throwNpe();
                                }
                                if (!activity.isFinishing()) {
                                    SNSAuthProvider sNSAuthProvider2 = (SNSAuthProvider) authProvider;
                                    FragmentActivity activity2 = BaseSignInFragment.this.getActivity();
                                    if (activity2 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    FragmentActivity fragmentActivity = activity2;
                                    Bundle arguments = BaseSignInFragment.this.getArguments();
                                    if (arguments == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    String string = arguments.getString("sid");
                                    Intrinsics.checkExpressionValueIsNotNull(string, "arguments!!.getString(\"sid\")");
                                    sNSAuthProvider2.startLogin(fragmentActivity, string);
                                }
                            }
                        }
                    });
                    i++;
                    sNSAuthProvider.getTintColor();
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type android.widget.ImageView");
                }
            }
        }
        if (i <= 0) {
            hideSns();
        }
    }

    private final void setAgreementText() {
        Spanned fromHtml = Html.fromHtml(getLicense());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(fromHtml);
        URLSpan[] uRLSpanArr = (URLSpan[]) spannableStringBuilder.getSpans(0, fromHtml.length(), URLSpan.class);
        for (URLSpan span : uRLSpanArr) {
            int spanStart = spannableStringBuilder.getSpanStart(span);
            int spanEnd = spannableStringBuilder.getSpanEnd(span);
            int spanFlags = spannableStringBuilder.getSpanFlags(span);
            Intrinsics.checkExpressionValueIsNotNull(span, "span");
            final String url = span.getURL();
            spannableStringBuilder.setSpan(new ClickableSpan() { // from class: com.xiaomi.passport.ui.internal.BaseSignInFragment$setAgreementText$clickableSpan$1
                @Override // android.text.style.ClickableSpan
                public void onClick(@Nullable View view) {
                    if (url != null) {
                        BaseSignInFragment baseSignInFragment = BaseSignInFragment.this;
                        baseSignInFragment.gotoFragment(baseSignInFragment.getMWebAuth().getNotificationFragment(url), true);
                    }
                }
            }, spanStart, spanEnd, spanFlags);
            spannableStringBuilder.removeSpan(span);
        }
        TextView tv_user_agreement = (TextView) _$_findCachedViewById(R.id.tv_user_agreement);
        Intrinsics.checkExpressionValueIsNotNull(tv_user_agreement, "tv_user_agreement");
        tv_user_agreement.setText(spannableStringBuilder);
        TextView tv_user_agreement2 = (TextView) _$_findCachedViewById(R.id.tv_user_agreement);
        Intrinsics.checkExpressionValueIsNotNull(tv_user_agreement2, "tv_user_agreement");
        tv_user_agreement2.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private final String getLicense() {
        String license;
        UserLicenseUtils userLicenseUtils = this.mUserLicenseUtils;
        Context context = getContext();
        Intrinsics.checkExpressionValueIsNotNull(context, "context");
        String userAgreementClicked = userLicenseUtils.getUserAgreementClicked(context);
        UserLicenseUtils userLicenseUtils2 = this.mUserLicenseUtils;
        Context context2 = getContext();
        Intrinsics.checkExpressionValueIsNotNull(context2, "context");
        String privacyPolicyClicked = userLicenseUtils2.getPrivacyPolicyClicked(context2);
        if (this.mUserLicenseUtils.getProductAgreement() == null) {
            license = getString(R.string.passport_user_agreement_link, userAgreementClicked, privacyPolicyClicked);
        } else {
            License productAgreement = this.mUserLicenseUtils.getProductAgreement();
            int i = R.string.passport_user_agreement_link_more;
            Object[] objArr = new Object[4];
            objArr[0] = userAgreementClicked;
            String str = null;
            objArr[1] = productAgreement != null ? productAgreement.getUrl() : null;
            if (productAgreement != null) {
                str = productAgreement.getText();
            }
            objArr[2] = str;
            objArr[3] = privacyPolicyClicked;
            license = getString(i, objArr);
        }
        Intrinsics.checkExpressionValueIsNotNull(license, "license");
        return license;
    }
}
