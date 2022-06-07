package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RequiresApi(21)
/* loaded from: classes2.dex */
public final class SlideDistanceProvider implements VisibilityAnimatorProvider {
    private int a;
    @Px
    private int b = -1;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface GravityFlag {
    }

    public SlideDistanceProvider(int i) {
        this.a = i;
    }

    public int getSlideEdge() {
        return this.a;
    }

    public void setSlideEdge(int i) {
        this.a = i;
    }

    @Px
    public int getSlideDistance() {
        return this.b;
    }

    public void setSlideDistance(@Px int i) {
        if (i >= 0) {
            this.b = i;
            return;
        }
        throw new IllegalArgumentException("Slide distance must be positive. If attempting to reverse the direction of the slide, use setSlideEdge(int) instead.");
    }

    @Override // com.google.android.material.transition.platform.VisibilityAnimatorProvider
    @Nullable
    public Animator createAppear(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return a(viewGroup, view, this.a, a(view.getContext()));
    }

    @Override // com.google.android.material.transition.platform.VisibilityAnimatorProvider
    @Nullable
    public Animator createDisappear(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return b(viewGroup, view, this.a, a(view.getContext()));
    }

    private int a(Context context) {
        int i = this.b;
        return i != -1 ? i : context.getResources().getDimensionPixelSize(R.dimen.mtrl_transition_shared_axis_slide_distance);
    }

    private static Animator a(View view, View view2, int i, @Px int i2) {
        float translationX = view2.getTranslationX();
        float translationY = view2.getTranslationY();
        if (i == 3) {
            return a(view2, i2 + translationX, translationX, translationX);
        }
        if (i == 5) {
            return a(view2, translationX - i2, translationX, translationX);
        }
        if (i == 48) {
            return b(view2, translationY - i2, translationY, translationY);
        }
        if (i == 80) {
            return b(view2, i2 + translationY, translationY, translationY);
        }
        if (i == 8388611) {
            return a(view2, a(view) ? i2 + translationX : translationX - i2, translationX, translationX);
        } else if (i == 8388613) {
            return a(view2, a(view) ? translationX - i2 : i2 + translationX, translationX, translationX);
        } else {
            throw new IllegalArgumentException("Invalid slide direction: " + i);
        }
    }

    private static Animator b(View view, View view2, int i, @Px int i2) {
        float translationX = view2.getTranslationX();
        float translationY = view2.getTranslationY();
        if (i == 3) {
            return a(view2, translationX, translationX - i2, translationX);
        }
        if (i == 5) {
            return a(view2, translationX, i2 + translationX, translationX);
        }
        if (i == 48) {
            return b(view2, translationY, i2 + translationY, translationY);
        }
        if (i == 80) {
            return b(view2, translationY, translationY - i2, translationY);
        }
        if (i == 8388611) {
            return a(view2, translationX, a(view) ? translationX - i2 : i2 + translationX, translationX);
        } else if (i == 8388613) {
            return a(view2, translationX, a(view) ? i2 + translationX : translationX - i2, translationX);
        } else {
            throw new IllegalArgumentException("Invalid slide direction: " + i);
        }
    }

    private static Animator a(final View view, float f, float f2, final float f3) {
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat(View.TRANSLATION_X, f, f2));
        ofPropertyValuesHolder.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.transition.platform.SlideDistanceProvider.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                view.setTranslationX(f3);
            }
        });
        return ofPropertyValuesHolder;
    }

    private static Animator b(final View view, float f, float f2, final float f3) {
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, f, f2));
        ofPropertyValuesHolder.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.transition.platform.SlideDistanceProvider.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                view.setTranslationY(f3);
            }
        });
        return ofPropertyValuesHolder;
    }

    private static boolean a(View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }
}
