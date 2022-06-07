package com.xiaomi.micolauncher.common.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MicoAnimationManager {
    public static final long DEVICE_LIST_VIEW_VISIBLE_DURATION = 200;
    public static final long DEVICE_LIST_VIEW_VISIBLE_HIDE_DURATION = 150;
    private final List<View> a = new ArrayList();
    private final List<View> b = new ArrayList();
    private boolean c = false;
    private final LinearInterpolator d = new LinearInterpolator();

    /* loaded from: classes3.dex */
    private static class a {
        private static final MicoAnimationManager a = new MicoAnimationManager();
    }

    public static MicoAnimationManager getManager() {
        return a.a;
    }

    public boolean isEditMode() {
        return this.c;
    }

    public void addToNormalModeVisibleAnimList(View view) {
        this.a.add(view);
    }

    public void addToEditModeVisibleAnimList(View view) {
        this.b.add(view);
    }

    public void changeToNormalMode() {
        this.c = false;
        startShowAnims((View[]) this.a.toArray(new View[0]));
        startHideAnims((View[]) this.b.toArray(new View[0]));
    }

    public void changeToEditMode() {
        this.c = true;
        startHideAnims((View[]) this.a.toArray(new View[0]));
        startShowAnims((View[]) this.b.toArray(new View[0]));
    }

    public void clearViewLists() {
        this.b.clear();
        this.a.clear();
        this.c = false;
    }

    public void startShowAnims(View... viewArr) {
        startShowAnims(true, viewArr);
    }

    public void startShowAnims(Boolean bool, View... viewArr) {
        if (viewArr.length == 1) {
            if (bool.booleanValue()) {
                a(viewArr[0]);
                return;
            }
            viewArr[0].setVisibility(0);
            viewArr[0].setAlpha(1.0f);
        } else if (viewArr.length > 1) {
            for (View view : viewArr) {
                if (bool.booleanValue()) {
                    a(view);
                } else {
                    view.setVisibility(0);
                    view.setAlpha(1.0f);
                }
            }
        }
    }

    public void startHideAnims(View... viewArr) {
        startHideAnims(true, viewArr);
    }

    public void startHideAnims(Boolean bool, View... viewArr) {
        if (viewArr.length == 1) {
            if (bool.booleanValue()) {
                b(viewArr[0]);
            } else {
                viewArr[0].setVisibility(8);
            }
            b(viewArr[0]);
        } else if (viewArr.length > 1) {
            for (View view : viewArr) {
                b(view);
            }
        }
    }

    public void startScaleSmallAnims(View... viewArr) {
        if (viewArr.length == 1) {
            c(viewArr[0]);
        } else if (viewArr.length > 1) {
            for (View view : viewArr) {
                c(view);
            }
        }
    }

    public void startScaleLargeAnims(View... viewArr) {
        if (viewArr.length == 1) {
            d(viewArr[0]);
        } else if (viewArr.length > 1) {
            for (View view : viewArr) {
                d(view);
            }
        }
    }

    private void a(final View view) {
        if (view.getVisibility() != 0) {
            view.animate().cancel();
            view.animate().alpha(1.0f).setDuration(200L).setInterpolator(this.d).setStartDelay(150L).setListener(new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.common.ui.MicoAnimationManager.1
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    view.setAlpha(0.0f);
                    view.setVisibility(0);
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    view.setVisibility(0);
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    view.setVisibility(0);
                }
            }).start();
        }
    }

    private void b(final View view) {
        if (view.getVisibility() == 0) {
            view.animate().cancel();
            view.animate().alpha(0.0f).setDuration(150L).setInterpolator(this.d).setStartDelay(0L).setListener(new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.common.ui.MicoAnimationManager.2
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    view.setVisibility(4);
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    view.setVisibility(4);
                }
            }).start();
        }
    }

    private void c(View view) {
        view.animate().scaleX(0.95f).scaleY(0.95f).setDuration(200L).setInterpolator(this.d).start();
    }

    private void d(View view) {
        view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200L).setInterpolator(this.d).start();
    }

    public void startLayoutWidthAnim(final View view) {
        ValueAnimator ofInt = ValueAnimator.ofInt(0, 51);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.micolauncher.common.ui.-$$Lambda$MicoAnimationManager$bwN_yypzWftYKMAxk3dnHVmFmtg
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                MicoAnimationManager.a(view, valueAnimator);
            }
        });
        ofInt.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(View view, ValueAnimator valueAnimator) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        view.setLayoutParams(layoutParams);
    }
}
