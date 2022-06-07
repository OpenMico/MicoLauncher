package com.xiaomi.micolauncher.module.initialize.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.utils.LottieUtil;
import com.xiaomi.micolauncher.module.initialize.BootActivity;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class StepRefreshTokenFragment extends BaseFragment {
    public static final String STEP_NAME = "StepRefreshTokenFragment";
    LottieAnimationView a;
    private BootActivity b;

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View view;
        if (Hardware.isBigScreen()) {
            LottieUtil.loadLottieView(this.a, "loading/images", "loading/loading.json");
            view = layoutInflater.inflate(R.layout.fragment_big_step_refresh_token, viewGroup, false);
        } else {
            view = layoutInflater.inflate(R.layout.fragment_step_refresh_token, viewGroup, false);
        }
        this.b = (BootActivity) getActivity();
        a();
        return view;
    }

    void a() {
        L.init.i("refreshToken");
        addToDisposeBag(TokenManager.getInstance().refreshServiceToken(ApiConstants.MICO_SID).retry(2L).compose(bindToLifecycle()).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.initialize.steps.-$$Lambda$StepRefreshTokenFragment$9t0edDNWVBWfL2TbbAmn5joljuI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StepRefreshTokenFragment.this.a((String) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.initialize.steps.-$$Lambda$StepRefreshTokenFragment$U4PsizpyibVDd8W_DdemXnj6hkk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StepRefreshTokenFragment.this.a((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str) throws Exception {
        this.b.nextStep(true, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        L.login.e("refresh token failed", th);
        this.b.nextStep(false, null);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
    }
}
