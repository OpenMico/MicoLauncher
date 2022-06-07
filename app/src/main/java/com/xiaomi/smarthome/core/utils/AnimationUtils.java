package com.xiaomi.smarthome.core.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.module.homepage.event.MainPageMaskAnimationEvent;
import com.xiaomi.onetrack.OneTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: AnimationUtils.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/xiaomi/smarthome/core/utils/AnimationUtils;", "", "()V", "Companion", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class AnimationUtils {
    @NotNull
    public static final Companion Companion = new Companion(null);

    /* compiled from: AnimationUtils.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0016\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/xiaomi/smarthome/core/utils/AnimationUtils$Companion;", "", "()V", "ANIMATION_DURATION", "", "ANIMATION_SCALE", "", "endHotZoneAnimationForX6A", "", OneTrack.Event.VIEW, "Landroid/view/View;", "startHotZoneAnimationForX6A", "radius", "", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startHotZoneAnimationForX6A(@NotNull View view, int i) {
            Intrinsics.checkNotNullParameter(view, "view");
            if (Hardware.isX6A()) {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 1.6f), ObjectAnimator.ofFloat(view, "scaleY", 1.6f));
                animatorSet.setDuration(150L);
                animatorSet.setInterpolator(new DecelerateInterpolator());
                animatorSet.start();
                int[] iArr = new int[2];
                view.getLocationOnScreen(iArr);
                int width = view.getWidth();
                float f = width;
                float f2 = 2;
                float f3 = iArr[0] - ((f * 0.6f) / f2);
                float height = view.getHeight();
                float f4 = iArr[1] - ((0.6f * height) / f2);
                EventBusRegistry.getEventBus().post(new MainPageMaskAnimationEvent(true, f3, f4, f3 + (f * 1.6f), f4 + (height * 1.6f), i * 1.6f));
            }
        }

        public final void endHotZoneAnimationForX6A(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            if (Hardware.isX6A()) {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 1.0f), ObjectAnimator.ofFloat(view, "scaleY", 1.0f));
                animatorSet.setDuration(150L);
                animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
                animatorSet.start();
                EventBusRegistry.getEventBus().post(new MainPageMaskAnimationEvent(false));
            }
        }
    }
}
