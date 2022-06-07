package com.xiaomi.micolauncher.skills.common.view;

import android.animation.Animator;
import com.airbnb.lottie.LottieAnimationView;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.common.WakeupHelper;
import com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper;

/* loaded from: classes3.dex */
public class WakeupAnimalView implements Animator.AnimatorListener, WakeupAnimalHelper {
    public static final String LOG_TAG = "WakeupAnimalView";
    private LottieAnimationView a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private WakeupHelper.WakeupStatus j;

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
    }

    public WakeupAnimalView(LottieAnimationView lottieAnimationView) {
        this.a = lottieAnimationView;
        this.a.setVisibility(8);
        this.a.addAnimatorListener(this);
        this.a.setScale(1.0f);
        this.a.setSpeed(1.0f);
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void init(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = i4;
        this.f = i5;
        this.g = i6;
        this.h = i7;
        this.i = i8;
        this.j = WakeupHelper.WakeupStatus.WAKEUP_STATUS_INIT;
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepInit() {
        Logger logger = L.speech;
        logger.d("WakeupAnimalViewstepInit, start=" + this.b + ", end=" + this.d);
        this.a.setVisibility(0);
        this.a.setMinAndMaxFrame(this.b, this.d);
        this.a.setRepeatCount(0);
        this.a.playAnimation();
        this.j = WakeupHelper.WakeupStatus.WAKEUP_STATUS_LISTENING;
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepListening() {
        L.speech.d("WakeupAnimalViewstepListening");
        this.a.setMinAndMaxFrame(this.c, this.d);
        this.a.setRepeatCount(-1);
        this.a.playAnimation();
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepListeningTransToSpeaking() {
        L.speech.d("WakeupAnimalViewstepListeningTransToSpeaking");
        if (this.j == WakeupHelper.WakeupStatus.WAKEUP_STATUS_LISTENING) {
            this.j = WakeupHelper.WakeupStatus.WAKEUP_STATUS_SPEAKING;
            this.a.setMinAndMaxFrame(this.d + 1, this.f);
            this.a.setRepeatCount(0);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepSpeaking() {
        L.speech.d("WakeupAnimalViewstepSpeaking");
        if (this.a.getVisibility() != 0) {
            this.a.setVisibility(0);
        }
        this.a.setMinAndMaxFrame(this.e, this.f);
        this.a.setRepeatCount(-1);
        this.a.playAnimation();
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepTts() {
        L.speech.d("WakeupAnimalViewstepTts");
        if (this.a.getVisibility() != 0) {
            this.a.setVisibility(0);
        }
        this.a.setMinAndMaxFrame(this.e, this.f);
        this.a.setRepeatCount(-1);
        this.a.playAnimation();
        this.j = WakeupHelper.WakeupStatus.WAKEUP_STATUS_TTS;
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepSpeakingTransToLoading() {
        L.speech.d("WakeupAnimalViewstepSpeakingTransToLoading");
        if (this.j == WakeupHelper.WakeupStatus.WAKEUP_STATUS_SPEAKING) {
            this.j = WakeupHelper.WakeupStatus.WAKEUP_STATUS_LOADING;
            this.a.setMinAndMaxFrame(this.f + 1, this.h);
            this.a.setRepeatCount(0);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepLoading() {
        L.speech.d("WakeupAnimalViewstepLoading");
        this.a.setMinAndMaxFrame(this.g, this.h);
        this.a.setRepeatCount(-1);
        this.a.playAnimation();
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepLoadingTransToSpeaking() {
        Logger logger = L.speech;
        logger.d("WakeupAnimalViewstepLoadingTransToSpeaking: mstatus=" + this.j);
        if (WakeupHelper.WakeupStatus.WAKEUP_STATUS_LOADING == this.j) {
            this.a.setMinAndMaxFrame(this.g, this.i);
            this.a.setRepeatCount(0);
        }
        this.j = WakeupHelper.WakeupStatus.WAKEUP_STATUS_TTS;
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public void stepStop() {
        L.speech.d("WakeupAnimalViewstepStop");
        this.a.setVisibility(8);
        if (this.a.isAnimating()) {
            this.a.cancelAnimation();
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        L.speech.d("WakeupAnimalViewonAnimationEnd:mAniView");
        switch (this.j) {
            case WAKEUP_STATUS_LISTENING:
                stepListening();
                return;
            case WAKEUP_STATUS_SPEAKING:
                stepSpeaking();
                return;
            case WAKEUP_STATUS_LOADING:
                stepLoading();
                return;
            case WAKEUP_STATUS_TTS:
                stepTts();
                return;
            default:
                return;
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        L.speech.d("WakeupAnimalViewonAnimationCancel:mAniView");
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {
        L.speech.d("WakeupAnimalViewonAnimationRepeat:mAniView");
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.WakeupAnimalHelper
    public WakeupHelper.WakeupStatus getStatus() {
        return this.j;
    }
}
