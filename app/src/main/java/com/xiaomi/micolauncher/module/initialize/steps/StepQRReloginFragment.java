package com.xiaomi.micolauncher.module.initialize.steps;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ThirdPartyResponse;
import com.xiaomi.micolauncher.application.LoginModel;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.event.QRLoginSuccessEvent;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.utils.LottieUtil;
import com.xiaomi.micolauncher.module.initialize.BootActivity;
import com.xiaomi.micolauncher.module.initialize.steps.qrlogin.IQRLoginView;
import com.xiaomi.micolauncher.module.initialize.steps.qrlogin.QRLoginPresenter;
import com.xiaomi.micolauncher.module.miot.MiotDeviceManager;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.passport.utils.AuthenticatorUtil;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class StepQRReloginFragment extends BaseFragment implements IQRLoginView {
    public static final String PARAM_SHOW_FALLBACK_HINT = "show_fallback";
    public static final String STEP_NAME = "qr_relogin";
    LottieAnimationView a;
    ConstraintLayout b;
    ImageView c;
    TextView d;
    TextView e;
    private BootActivity f;
    private boolean g;
    private QRLoginPresenter h;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View view;
        if (Hardware.isBigScreen()) {
            view = layoutInflater.inflate(R.layout.fragment_big_qr_login, viewGroup, false);
        } else {
            view = layoutInflater.inflate(R.layout.fragment_qr_login, viewGroup, false);
        }
        this.a = (LottieAnimationView) view.findViewById(R.id.animation);
        this.b = (ConstraintLayout) view.findViewById(R.id.xiaomi_layout);
        this.c = (ImageView) view.findViewById(R.id.qr);
        this.d = (TextView) view.findViewById(R.id.hint1);
        this.e = (TextView) view.findViewById(R.id.hint2);
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.initialize.steps.-$$Lambda$StepQRReloginFragment$5TKu2pwz6SrYW_M2h9eZJ9KEwQU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StepQRReloginFragment.this.a(view2);
            }
        });
        this.h = new QRLoginPresenter(getContext(), this);
        this.b.setVisibility(4);
        if (Hardware.isBigScreen()) {
            LottieUtil.loadLottieView(this.a, "loading/images", "loading/loading.json");
        } else {
            LottieUtil.loadLottieView(this.a, "loading/images", "loading/data.json");
        }
        return view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        if (this.g) {
            this.f.nextStep(true, null);
        } else {
            this.h.loadQr();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.screenBrightness = 1.0f;
        window.setAttributes(attributes);
        this.d.setVisibility(4);
        this.f = (BootActivity) getActivity();
        this.h.loadQr();
    }

    @Override // com.xiaomi.micolauncher.module.initialize.steps.qrlogin.IQRLoginView
    public void showProgressbar() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.initialize.steps.-$$Lambda$StepQRReloginFragment$9HriOzXFWKF1sjp3oCDjsPXNONI
                @Override // java.lang.Runnable
                public final void run() {
                    StepQRReloginFragment.this.b();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() {
        this.b.setVisibility(4);
        this.a.setVisibility(0);
    }

    @Override // com.xiaomi.micolauncher.module.initialize.steps.qrlogin.IQRLoginView
    public void hideProgressbar() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.initialize.steps.-$$Lambda$StepQRReloginFragment$GX5tgKfeODRcx88mBBZKmH-X_qM
                @Override // java.lang.Runnable
                public final void run() {
                    StepQRReloginFragment.this.a();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a() {
        this.b.setVisibility(0);
        this.a.setVisibility(4);
    }

    @Override // com.xiaomi.micolauncher.module.initialize.steps.qrlogin.IQRLoginView
    public void showQr(Bitmap bitmap) {
        this.c.setImageBitmap(bitmap);
    }

    @Override // com.xiaomi.micolauncher.module.initialize.steps.qrlogin.IQRLoginView
    public void onAuthSuccess(ThirdPartyResponse.QrLoginResultResponse qrLoginResultResponse) {
        L.init.i("StepQRReLoginFragment onAuthSuccess");
        if (qrLoginResultResponse.userId.equals(TokenManager.getInstance().getUserId())) {
            addToDisposeBag(QRLoginPresenter.getServiceToken(qrLoginResultResponse).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.initialize.steps.-$$Lambda$StepQRReloginFragment$6c8xk4q_9tfOgMkt-8g1zQoj7PU
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    StepQRReloginFragment.this.a((AccountInfo) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.initialize.steps.-$$Lambda$StepQRReloginFragment$tyU787-sukccKC7ax-WFdfPlb78
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    StepQRReloginFragment.this.a((Throwable) obj);
                }
            }));
        } else {
            this.f.nextStep(false, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AccountInfo accountInfo) throws Exception {
        if (getContext() != null) {
            AuthenticatorUtil.addOrUpdateAccountManager(getContext(), accountInfo);
            TokenManager.getInstance().syncAccountInfo();
            LoginModel.getInstance().onLoginSuccess(getContext());
        } else {
            L.init.e("no valid context to update account info.");
        }
        SpeechManager.getInstance().forceRefreshToken();
        MiotManager.onLogon();
        this.g = true;
        this.f.nextStep(true, null);
        EventBusRegistry.getEventBus().post(new QRLoginSuccessEvent());
        MiotDeviceManager.getInstance().restartServiceAfterLogin();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        L.init.e("getServiceToken failed", th);
        ToastUtil.showToast((int) R.string.error_qr_login_failed);
        this.h.loadQr();
    }

    @Override // com.xiaomi.micolauncher.module.initialize.steps.qrlogin.IQRLoginView
    public void onTimeout() {
        ToastUtil.showToast((int) R.string.init_step_wifi_load_fail);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.h.destroy();
    }
}
