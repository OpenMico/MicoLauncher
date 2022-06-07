package com.xiaomi.passport.ui.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.StatConstants;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.TrackEventManager;
import com.xiaomi.passport.ui.internal.AddAccountListener;
import com.xiaomi.passport.ui.internal.PhoneNumUtil;
import com.xiaomi.passport.utils.AccountHelper;
import com.xiaomi.passport.utils.AuthenticatorUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ActivityAddAccount.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 N2\u00020\u00012\u00020\u0002:\u0001NB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0017H\u0002J\n\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\rH\u0016J\u0018\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u001a2\u0006\u0010#\u001a\u00020\rH\u0016J\b\u0010$\u001a\u00020\rH\u0002J\b\u0010%\u001a\u00020\u0017H\u0016J\u0010\u0010&\u001a\u00020\u00172\u0006\u0010'\u001a\u00020(H\u0016J\"\u0010)\u001a\u00020\u00172\u0006\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020\u001e2\b\u0010,\u001a\u0004\u0018\u00010-H\u0014J\u0012\u0010.\u001a\u00020\u00172\b\u0010\"\u001a\u0004\u0018\u00010\u001aH\u0016J\b\u0010/\u001a\u00020\u0017H\u0016J\u0012\u00100\u001a\u00020\u00172\b\u00101\u001a\u0004\u0018\u000102H\u0014J\b\u00103\u001a\u00020\u0017H\u0014J\u0010\u00104\u001a\u00020\r2\u0006\u00105\u001a\u000206H\u0016J\b\u00107\u001a\u00020\u0017H\u0014J\u0010\u00108\u001a\u00020\u00172\u0006\u00109\u001a\u00020\rH\u0002J\b\u0010:\u001a\u00020\u0017H\u0002J\b\u0010;\u001a\u00020\u0017H\u0002J\b\u0010<\u001a\u00020\rH\u0016J\u0010\u0010=\u001a\u00020\u00172\u0006\u0010>\u001a\u00020\u0005H\u0002J\b\u0010?\u001a\u00020\u0017H\u0002J\b\u0010@\u001a\u00020\u0017H\u0002J\u001a\u0010A\u001a\u00020\u00172\u0006\u0010+\u001a\u00020\u001e2\b\u0010'\u001a\u0004\u0018\u00010(H\u0002J\u0010\u0010B\u001a\u00020\u00172\u0006\u0010C\u001a\u00020\rH\u0002J\b\u0010D\u001a\u00020\u0017H\u0002J\u0010\u0010E\u001a\u00020\u00172\u0006\u0010F\u001a\u00020GH\u0002J\b\u0010H\u001a\u00020\u0017H\u0002J\u0018\u0010I\u001a\u00020\u00172\u0006\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020MH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006O"}, d2 = {"Lcom/xiaomi/passport/ui/internal/AddAccountActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/xiaomi/passport/ui/internal/AddAccountListener;", "()V", "TAG", "", "defaultAuthProvider", "Lcom/xiaomi/passport/ui/internal/BaseAuthProvider;", "mCommonErrorHandler", "Lcom/xiaomi/passport/ui/internal/CommonErrorHandler;", "mGlobalLayoutListener", "Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;", "mKeyboardUp", "", "mProgressHolder", "Lcom/xiaomi/passport/ui/internal/ProgressHolder;", "mSid", "mSnsBroadcastReceiver", "Landroid/content/BroadcastReceiver;", "mSnsDirectlySignInType", "mWebAuth", "Lcom/xiaomi/passport/ui/internal/WebAuth;", "addGlobalLayoutListener", "", "dismissProgress", "getCurrentFragment", "Landroidx/fragment/app/Fragment;", "getResources", "Landroid/content/res/Resources;", "getSupportActionBarHeight", "", "goBack", "closeWebView", "gotoFragment", "fragment", "addToBackStack", "isSnsDirectlySignInMode", "loginCancelled", "loginSuccess", "accountInfo", "Lcom/xiaomi/accountsdk/account/data/AccountInfo;", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onAttachFragment", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onResume", "onSnsResultReturned", StatConstants.BIND_SUCCESS, "onSoftKeyboardHidden", "onSoftKeyboardShown", "onSupportNavigateUp", "overrideDefaultAuthProvider", "defaultAuthProviderName", "registerBroadcast", "removeGlobalLayoutListener", "setAddAccountResultAndFinish", "setAllContentVisibility", "visible", "setLoginCancelledResult", "showNetworkError", "e", "Ljava/io/IOException;", "showProgress", "signInWithSnsCredential", "it", "Lcom/xiaomi/passport/ui/internal/SNSAuthProvider;", "authCredential", "Lcom/xiaomi/passport/ui/internal/SNSAuthCredential;", "Companion", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class AddAccountActivity extends AppCompatActivity implements AddAccountListener {
    public static final Companion Companion = new Companion(null);
    private static Intent countryChoiceIntent = null;
    private static String countryName = null;
    private static String countryNameColor = null;
    private static final int requestCountryNameCode = 2020;
    private HashMap _$_findViewCache;
    private boolean mKeyboardUp;
    private String mSid;
    private BroadcastReceiver mSnsBroadcastReceiver;
    private String mSnsDirectlySignInType;
    private final String TAG = "AddAccountActivity";
    private final ProgressHolder mProgressHolder = new ProgressHolder();
    private WebAuth mWebAuth = new WebAuth();
    private BaseAuthProvider defaultAuthProvider = PassportUI.INSTANCE.getDefaultBaseAuthProvider();
    private final CommonErrorHandler mCommonErrorHandler = new CommonErrorHandler();
    private final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.xiaomi.passport.ui.internal.AddAccountActivity$mGlobalLayoutListener$1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public final void onGlobalLayout() {
            int supportActionBarHeight;
            boolean z;
            String str;
            String str2;
            boolean z2;
            View rootView = AddAccountActivity.this.findViewById(16908290);
            supportActionBarHeight = AddAccountActivity.this.getSupportActionBarHeight();
            Intrinsics.checkExpressionValueIsNotNull(rootView, "rootView");
            View rootView2 = rootView.getRootView();
            Intrinsics.checkExpressionValueIsNotNull(rootView2, "rootView.rootView");
            if (rootView2.getHeight() - rootView.getHeight() > supportActionBarHeight + 100) {
                str2 = AddAccountActivity.this.TAG;
                AccountLog.e(str2, "keyboard is shown");
                z2 = AddAccountActivity.this.mKeyboardUp;
                if (!z2) {
                    AddAccountActivity.this.mKeyboardUp = true;
                    AddAccountActivity.this.onSoftKeyboardShown();
                    return;
                }
                return;
            }
            z = AddAccountActivity.this.mKeyboardUp;
            if (z) {
                str = AddAccountActivity.this.TAG;
                AccountLog.e(str, "keyboard is hidden");
                AddAccountActivity.this.mKeyboardUp = false;
                AddAccountActivity.this.onSoftKeyboardHidden();
            }
        }
    };

    public final void onSoftKeyboardHidden() {
    }

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    /* compiled from: ActivityAddAccount.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u0006J\u000e\u0010\r\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/xiaomi/passport/ui/internal/AddAccountActivity$Companion;", "", "()V", "countryChoiceIntent", "Landroid/content/Intent;", "countryName", "", "countryNameColor", "requestCountryNameCode", "", "setCountryChoiceButtonText", "", "setCountryChoiceButtonTextColor", "setCountryChoiceIntent", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void setCountryChoiceButtonText(@NotNull String countryName) {
            Intrinsics.checkParameterIsNotNull(countryName, "countryName");
            AddAccountActivity.countryName = countryName;
        }

        public final void setCountryChoiceButtonTextColor(@NotNull String countryNameColor) {
            Intrinsics.checkParameterIsNotNull(countryNameColor, "countryNameColor");
            AddAccountActivity.countryNameColor = countryNameColor;
        }

        public final void setCountryChoiceIntent(@NotNull Intent countryChoiceIntent) {
            Intrinsics.checkParameterIsNotNull(countryChoiceIntent, "countryChoiceIntent");
            AddAccountActivity.countryChoiceIntent = countryChoiceIntent;
        }
    }

    @NotNull
    public static final /* synthetic */ String access$getMSid$p(AddAccountActivity addAccountActivity) {
        String str = addAccountActivity.mSid;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSid");
        }
        return str;
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        Object obj;
        super.onCreate(bundle);
        registerBroadcast();
        setContentView(R.layout.add_account_main);
        setSupportActionBar((Toolbar) _$_findCachedViewById(R.id.toolbar));
        addGlobalLayoutListener();
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null) {
            Intrinsics.throwNpe();
        }
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        String stringExtra = getIntent().getStringExtra("service_id");
        if (stringExtra == null) {
            stringExtra = "passport";
        }
        this.mSid = stringExtra;
        String stringExtra2 = getIntent().getStringExtra(PassportUI.EXTRA_DEFAULT_AUTH_PROVIDER);
        String stringExtra3 = getIntent().getStringExtra(PassportUI.EXTRA_DEFAULT_PHONE_COUNTRY_CODE);
        if (stringExtra2 != null) {
            overrideDefaultAuthProvider(stringExtra2);
        }
        if (getCurrentFragment() == null) {
            BaseAuthProvider baseAuthProvider = this.defaultAuthProvider;
            String str = this.mSid;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mSid");
            }
            PhoneNumUtil.CountryPhoneNumData smartGetCountryCodeData = CountryCodeUtilsExtensionKt.smartGetCountryCodeData(this, stringExtra3);
            AddAccountListener.DefaultImpls.gotoFragment$default(this, baseAuthProvider.getFragment(str, smartGetCountryCodeData != null ? CountryCodeUtilsExtensionKt.getCountryCodeWithPrefix(smartGetCountryCodeData) : null), false, 2, null);
        }
        this.mSnsDirectlySignInType = getIntent().getStringExtra(PassportUI.EXTRA_SNS_SIGN_IN);
        if (this.mSnsDirectlySignInType != null) {
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : PassportUI.INSTANCE.getMProviders$passportui_release()) {
                if (obj2 instanceof SNSAuthProvider) {
                    arrayList.add(obj2);
                }
            }
            Iterator it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                if (Intrinsics.areEqual(((SNSAuthProvider) obj).getName(), this.mSnsDirectlySignInType)) {
                    break;
                }
            }
            SNSAuthProvider sNSAuthProvider = (SNSAuthProvider) obj;
            if (sNSAuthProvider != null) {
                AddAccountActivity addAccountActivity = this;
                String str2 = this.mSid;
                if (str2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mSid");
                }
                sNSAuthProvider.startLogin(addAccountActivity, str2);
                setAllContentVisibility(false);
                return;
            }
            Toast.makeText(this, R.string.passport_access_denied, 1).show();
            setLoginCancelledResult();
        }
    }

    public final int getSupportActionBarHeight() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            return supportActionBar.getHeight();
        }
        return 0;
    }

    private final void addGlobalLayoutListener() {
        AccountLog.d(this.TAG, "addGlobalLayoutListener");
        View rootView = findViewById(16908290);
        Intrinsics.checkExpressionValueIsNotNull(rootView, "rootView");
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(this.mGlobalLayoutListener);
    }

    private final void removeGlobalLayoutListener() {
        AccountLog.d(this.TAG, "removeGlobalLayoutListener");
        View rootView = findViewById(16908290);
        Intrinsics.checkExpressionValueIsNotNull(rootView, "rootView");
        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this.mGlobalLayoutListener);
    }

    public final void onSoftKeyboardShown() {
        _$_clearFindViewByIdCache();
        final View findViewById = findViewById(R.id.sign_in_title_container);
        final ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view_container);
        if (findViewById != null && scrollView != null) {
            findViewById.postDelayed(new Runnable() { // from class: com.xiaomi.passport.ui.internal.AddAccountActivity$onSoftKeyboardShown$1
                @Override // java.lang.Runnable
                public final void run() {
                    scrollView.smoothScrollTo(0, findViewById.getHeight());
                }
            }, 50L);
        }
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        Intrinsics.checkParameterIsNotNull(item, "item");
        if (item.getItemId() == 16908332) {
            TrackEventManager.addEvent(com.xiaomi.passport.ui.StatConstants.STAT_CATEGORY_COMMON_CLICK_RETURN_BUTTON);
        }
        return super.onOptionsItemSelected(item);
    }

    private final void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter(SNSAuthProvider.ACTION_PASSPORT_SNS_EVENTS);
        this.mSnsBroadcastReceiver = new BroadcastReceiver() { // from class: com.xiaomi.passport.ui.internal.AddAccountActivity$registerBroadcast$1
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onReceive(@org.jetbrains.annotations.Nullable android.content.Context r1, @org.jetbrains.annotations.Nullable android.content.Intent r2) {
                /*
                    r0 = this;
                    if (r2 == 0) goto L_0x0022
                    java.lang.String r1 = "sns_result"
                    java.lang.String r1 = r2.getStringExtra(r1)
                    java.lang.String r2 = "ok"
                    boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r2)
                    com.xiaomi.passport.ui.internal.AddAccountActivity r2 = com.xiaomi.passport.ui.internal.AddAccountActivity.this
                    com.xiaomi.passport.ui.internal.AddAccountActivity.access$onSnsResultReturned(r2, r1)
                    if (r1 != 0) goto L_0x0022
                    com.xiaomi.passport.ui.internal.AddAccountActivity r1 = com.xiaomi.passport.ui.internal.AddAccountActivity.this
                    boolean r1 = com.xiaomi.passport.ui.internal.AddAccountActivity.access$isSnsDirectlySignInMode(r1)
                    if (r1 == 0) goto L_0x0022
                    com.xiaomi.passport.ui.internal.AddAccountActivity r1 = com.xiaomi.passport.ui.internal.AddAccountActivity.this
                    com.xiaomi.passport.ui.internal.AddAccountActivity.access$setLoginCancelledResult(r1)
                L_0x0022:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.passport.ui.internal.AddAccountActivity$registerBroadcast$1.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(this);
        BroadcastReceiver broadcastReceiver = this.mSnsBroadcastReceiver;
        if (broadcastReceiver == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSnsBroadcastReceiver");
        }
        instance.registerReceiver(broadcastReceiver, intentFilter);
    }

    private final void setAllContentVisibility(boolean z) {
        View findViewById = findViewById(16908290);
        if (findViewById != null) {
            findViewById.setVisibility(z ? 0 : 4);
        }
    }

    private final void overrideDefaultAuthProvider(String str) {
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = PassportUI.INSTANCE.getMProviders$passportui_release().iterator();
        while (true) {
            boolean z = false;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            AuthProvider authProvider = (AuthProvider) next;
            if (Intrinsics.areEqual(authProvider.getName(), str) && (authProvider instanceof BaseAuthProvider)) {
                z = true;
            }
            if (z) {
                arrayList.add(next);
            }
        }
        AuthProvider authProvider2 = (AuthProvider) CollectionsKt.getOrNull(arrayList, 0);
        if (authProvider2 != null) {
            this.defaultAuthProvider = (BaseAuthProvider) authProvider2;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onAttachFragment(@Nullable Fragment fragment) {
        TextView textView;
        super.onAttachFragment(fragment);
        if (fragment == null || !(fragment instanceof WebViewBack)) {
            TextView textView2 = (TextView) _$_findCachedViewById(R.id.close_btn);
            if (textView2 != null) {
                textView2.setVisibility(8);
            }
        } else {
            TextView textView3 = (TextView) _$_findCachedViewById(R.id.close_btn);
            if (textView3 != null) {
                textView3.setVisibility(0);
            }
            TextView textView4 = (TextView) _$_findCachedViewById(R.id.close_btn);
            if (textView4 != null) {
                textView4.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.AddAccountActivity$onAttachFragment$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AddAccountActivity.this.goBack(true);
                    }
                });
            }
        }
        if (fragment == null || !(fragment instanceof BaseSignInFragment) || countryName == null) {
            TextView textView5 = (TextView) _$_findCachedViewById(R.id.country_choice_btn);
            if (textView5 != null) {
                textView5.setVisibility(8);
                return;
            }
            return;
        }
        TextView textView6 = (TextView) _$_findCachedViewById(R.id.country_choice_btn);
        if (textView6 != null) {
            textView6.setText(countryName);
        }
        if (!(countryNameColor == null || (textView = (TextView) _$_findCachedViewById(R.id.country_choice_btn)) == null)) {
            textView.setTextColor(Color.parseColor(countryNameColor));
        }
        TextView textView7 = (TextView) _$_findCachedViewById(R.id.country_choice_btn);
        if (textView7 != null) {
            textView7.setVisibility(0);
        }
        TextView textView8 = (TextView) _$_findCachedViewById(R.id.country_choice_btn);
        if (textView8 != null) {
            textView8.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.AddAccountActivity$onAttachFragment$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Intent intent;
                    Intent intent2;
                    intent = AddAccountActivity.countryChoiceIntent;
                    if (intent != null) {
                        AddAccountActivity addAccountActivity = AddAccountActivity.this;
                        intent2 = AddAccountActivity.countryChoiceIntent;
                        addAccountActivity.startActivityForResult(intent2, 2020);
                    }
                }
            });
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        SNSAuthCredential authCredential = SNSAuthProvider.Companion.getAuthCredential();
        if (authCredential != null) {
            SNSAuthProvider.Companion.invalidAuthCredential();
            AuthProvider provider = PassportUI.INSTANCE.getProvider(authCredential);
            if (provider != null) {
                signInWithSnsCredential((SNSAuthProvider) provider, authCredential);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type com.xiaomi.passport.ui.internal.SNSAuthProvider");
        }
    }

    public final boolean isSnsDirectlySignInMode() {
        return !TextUtils.isEmpty(this.mSnsDirectlySignInType);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        dismissProgress();
        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(this);
        BroadcastReceiver broadcastReceiver = this.mSnsBroadcastReceiver;
        if (broadcastReceiver == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mSnsBroadcastReceiver");
        }
        instance.unregisterReceiver(broadcastReceiver);
        removeGlobalLayoutListener();
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        boolean z;
        super.onActivityResult(i, i2, intent);
        ArrayList arrayList = new ArrayList();
        for (Object obj : PassportUI.INSTANCE.getMProviders$passportui_release()) {
            if (obj instanceof SNSAuthProvider) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (true) {
            boolean z2 = true;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            if (((SNSAuthProvider) next).getRequestCode() != i) {
                z2 = false;
            }
            if (z2) {
                arrayList2.add(next);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : arrayList2) {
            SNSAuthProvider sNSAuthProvider = (SNSAuthProvider) obj2;
            sNSAuthProvider.onActivityResult(this, i, i2, intent);
            SNSAuthCredential authCredential = SNSAuthProvider.Companion.getAuthCredential();
            onSnsResultReturned(authCredential != null);
            if (authCredential != null) {
                SNSAuthProvider.Companion.invalidAuthCredential();
                signInWithSnsCredential(sNSAuthProvider, authCredential);
                z = true;
            } else {
                z = false;
            }
            if (z) {
                arrayList3.add(obj2);
            }
        }
        if (i == requestCountryNameCode && i2 == -1) {
            if (intent == null) {
                Intrinsics.throwNpe();
            }
            String stringExtra = intent.getStringExtra("countryName");
            TextView textView = (TextView) _$_findCachedViewById(R.id.country_choice_btn);
            if (textView != null) {
                textView.setText(stringExtra);
            }
            countryName = stringExtra;
        }
    }

    public final void onSnsResultReturned(boolean z) {
        if (isSnsDirectlySignInMode() && z) {
            setAllContentVisibility(true);
        }
    }

    private final void signInWithSnsCredential(SNSAuthProvider sNSAuthProvider, SNSAuthCredential sNSAuthCredential) {
        showProgress();
        sNSAuthProvider.signInAndStoreAccount(this, sNSAuthCredential).get(new AddAccountActivity$signInWithSnsCredential$1(this), new AddAccountActivity$signInWithSnsCredential$2(this));
    }

    @Override // com.xiaomi.passport.ui.internal.AddAccountListener
    public void loginSuccess(@NotNull AccountInfo accountInfo) {
        Intrinsics.checkParameterIsNotNull(accountInfo, "accountInfo");
        setAddAccountResultAndFinish(-1, accountInfo);
    }

    @Override // com.xiaomi.passport.ui.internal.AddAccountListener
    public void loginCancelled() {
        setLoginCancelledResult();
    }

    private final void showProgress() {
        this.mProgressHolder.showProgress(this);
    }

    public final void dismissProgress() {
        this.mProgressHolder.dismissProgress();
    }

    public final void showNetworkError(IOException iOException) {
        this.mCommonErrorHandler.onIOError(iOException, this, (ConstraintLayout) _$_findCachedViewById(R.id.fragment_main));
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        goBack(false);
    }

    @Override // com.xiaomi.passport.ui.internal.AddAccountListener
    public void goBack(boolean z) {
        TextView textView;
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment != null && (currentFragment instanceof WebViewBack)) {
            WebViewBack webViewBack = (WebViewBack) currentFragment;
            if (webViewBack.canGoBack() && !z) {
                webViewBack.goBack();
                return;
            }
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Intrinsics.checkExpressionValueIsNotNull(supportFragmentManager, "supportFragmentManager");
        if (supportFragmentManager.getBackStackEntryCount() > 0) {
            TextView textView2 = (TextView) _$_findCachedViewById(R.id.close_btn);
            if (textView2 != null) {
                textView2.setVisibility(8);
            }
            if (!(countryName == null || (textView = (TextView) _$_findCachedViewById(R.id.country_choice_btn)) == null)) {
                textView.setVisibility(0);
            }
            getSupportFragmentManager().popBackStack();
            return;
        }
        setLoginCancelledResult();
    }

    public final void setLoginCancelledResult() {
        setAddAccountResultAndFinish(0, null);
    }

    private final void setAddAccountResultAndFinish(int i, AccountInfo accountInfo) {
        AuthenticatorUtil.handleAccountAuthenticatorResponse(getIntent().getParcelableExtra("accountAuthenticatorResponse"), AccountHelper.getAccountAuthenticatorResponseResult(i, accountInfo, getIntent().getBooleanExtra("need_retry_on_authenticator_response_result", false)));
        SNSAuthProvider.Companion.invalidBindParameter();
        setResult(i);
        if (!isFinishing()) {
            finish();
        }
    }

    @Override // com.xiaomi.passport.ui.internal.AddAccountListener
    public void gotoFragment(@NotNull Fragment fragment, boolean z) {
        Intrinsics.checkParameterIsNotNull(fragment, "fragment");
        FragmentTransaction replace = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment);
        if (z) {
            replace = replace.addToBackStack(null);
        }
        replace.commitAllowingStateLoss();
    }

    private final Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    @NotNull
    public Resources getResources() {
        if (Build.VERSION.SDK_INT >= 24) {
            Context applicationContext = getApplicationContext();
            Intrinsics.checkExpressionValueIsNotNull(applicationContext, "applicationContext");
            Resources resources = applicationContext.getResources();
            Intrinsics.checkExpressionValueIsNotNull(resources, "applicationContext.resources");
            return resources;
        }
        Resources resources2 = super.getResources();
        Intrinsics.checkExpressionValueIsNotNull(resources2, "super.getResources()");
        return resources2;
    }
}
