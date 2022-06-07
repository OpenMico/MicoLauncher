package com.xiaomi.micolauncher.common.utils;

import com.airbnb.lottie.LottieAnimationView;
import com.xiaomi.mico.common.Hardware;

/* loaded from: classes3.dex */
public class LottieUtil {
    public static void loadLottieView(LottieAnimationView lottieAnimationView, String str, String str2) {
        if (lottieAnimationView != null && str != null && str2 != null) {
            lottieAnimationView.setImageAssetsFolder(str);
            lottieAnimationView.setAnimation(str2);
            lottieAnimationView.setRepeatMode(1);
            lottieAnimationView.setRepeatCount(-1);
            lottieAnimationView.playAnimation();
        }
    }

    public static void loadLoadingLottieView(LottieAnimationView lottieAnimationView) {
        if (lottieAnimationView != null) {
            lottieAnimationView.setImageAssetsFolder("loading/images");
            if (Hardware.isBigScreen()) {
                lottieAnimationView.setAnimation("loading/loading.json");
            } else {
                lottieAnimationView.setAnimation("loading/data.json");
            }
            lottieAnimationView.setRepeatMode(1);
            lottieAnimationView.setRepeatCount(-1);
            lottieAnimationView.playAnimation();
        }
    }
}
