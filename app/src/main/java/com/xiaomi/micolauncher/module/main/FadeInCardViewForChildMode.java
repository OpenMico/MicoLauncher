package com.xiaomi.micolauncher.module.main;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.main.CardViewForChildMode;
import com.xiaomi.micolauncher.module.main.view.FadeInCardViewInterface;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class FadeInCardViewForChildMode extends RelativeLayout implements FadeInCardViewInterface {
    CardViewForChildMode a;
    CardViewForChildMode b;
    private boolean c;
    private String d;
    private String e;
    private int f;
    private int g;
    private Bitmap h;
    private ValueAnimator i;
    private ValueAnimator j;
    public static final long DURATION_FADE_OUT = TimeUnit.SECONDS.toMillis(1);
    public static final long DURATION_FADE_INT = TimeUnit.SECONDS.toMillis(2);

    @Override // com.xiaomi.micolauncher.module.main.view.FadeInCardViewInterface
    public void setIconIv(int i) {
    }

    public FadeInCardViewForChildMode(Context context) {
        this(context, null);
    }

    public FadeInCardViewForChildMode(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FadeInCardViewForChildMode(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.view_fade_in_card_for_child_mode, this);
        this.a = (CardViewForChildMode) findViewById(R.id.previewCardView);
        this.b = (CardViewForChildMode) findViewById(R.id.cardView);
        this.i = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.i.setDuration(DURATION_FADE_OUT);
        this.j = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.j.setDuration(DURATION_FADE_INT);
    }

    @Override // com.xiaomi.micolauncher.module.main.view.FadeInCardViewInterface
    public void clear() {
        this.j.removeAllListeners();
        this.j.removeAllUpdateListeners();
        this.i.removeAllListeners();
        this.i.removeAllUpdateListeners();
    }

    @Override // com.xiaomi.micolauncher.module.main.view.FadeInCardViewInterface
    public void refreshPage(String str, String str2, String str3, int i, int i2) {
        if (this.h == null) {
            this.b.updateTitleAndImage(str2, str, i, i2);
            this.b.setImageLoadListener(new CardViewForChildMode.b() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$FadeInCardViewForChildMode$DuEnKjew7Uw8RCrrasmT8xkH16s
                @Override // com.xiaomi.micolauncher.module.main.CardViewForChildMode.b
                public final void onImageLoaded(Bitmap bitmap) {
                    FadeInCardViewForChildMode.this.b(bitmap);
                }
            });
            return;
        }
        if (this.c) {
            L.launcher.i(" is refreshing page, stop former refreshing");
            this.c = false;
            this.b.setAlpha(1.0f);
            this.j.removeAllListeners();
            this.j.removeAllUpdateListeners();
            this.j.cancel();
            this.i.removeAllListeners();
            this.i.removeAllUpdateListeners();
            this.i.cancel();
        }
        this.e = str2;
        this.d = str;
        this.f = i;
        this.g = i2;
        this.a.updateTitleAndImage(str2, str, i, i2);
        this.a.setImageLoadListener(new CardViewForChildMode.b() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$FadeInCardViewForChildMode$VfvkBHgJO0GI8YChlLFv3-i3O1w
            @Override // com.xiaomi.micolauncher.module.main.CardViewForChildMode.b
            public final void onImageLoaded(Bitmap bitmap) {
                FadeInCardViewForChildMode.this.a(bitmap);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Bitmap bitmap) {
        this.h = bitmap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Bitmap bitmap) {
        this.h = bitmap;
        this.c = true;
        a();
        b();
    }

    private void a() {
        this.i.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$FadeInCardViewForChildMode$evQnFMWoS9US7v6bBJZEKVCV5lM
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                FadeInCardViewForChildMode.this.b(valueAnimator);
            }
        });
        this.i.addListener(new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.module.main.FadeInCardViewForChildMode.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }
        });
        this.i.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(ValueAnimator valueAnimator) {
        this.b.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    private void b() {
        this.j.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.module.main.-$$Lambda$FadeInCardViewForChildMode$N3pJIG7o9TjJC05V2xCo_zYVWQE
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                FadeInCardViewForChildMode.this.a(valueAnimator);
            }
        });
        this.j.addListener(new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.module.main.FadeInCardViewForChildMode.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                FadeInCardViewForChildMode.this.b.updateTitleAndImage(FadeInCardViewForChildMode.this.e, FadeInCardViewForChildMode.this.d, FadeInCardViewForChildMode.this.f, FadeInCardViewForChildMode.this.g);
                FadeInCardViewForChildMode.this.c = false;
            }
        });
        this.j.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ValueAnimator valueAnimator) {
        this.a.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }
}
