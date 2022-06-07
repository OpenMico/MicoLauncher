package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@RequiresApi(21)
/* loaded from: classes2.dex */
public final class ScaleProvider implements VisibilityAnimatorProvider {
    private float a;
    private float b;
    private float c;
    private float d;
    private boolean e;
    private boolean f;

    public ScaleProvider() {
        this(true);
    }

    public ScaleProvider(boolean z) {
        this.a = 1.0f;
        this.b = 1.1f;
        this.c = 0.8f;
        this.d = 1.0f;
        this.f = true;
        this.e = z;
    }

    public boolean isGrowing() {
        return this.e;
    }

    public void setGrowing(boolean z) {
        this.e = z;
    }

    public boolean isScaleOnDisappear() {
        return this.f;
    }

    public void setScaleOnDisappear(boolean z) {
        this.f = z;
    }

    public float getOutgoingStartScale() {
        return this.a;
    }

    public void setOutgoingStartScale(float f) {
        this.a = f;
    }

    public float getOutgoingEndScale() {
        return this.b;
    }

    public void setOutgoingEndScale(float f) {
        this.b = f;
    }

    public float getIncomingStartScale() {
        return this.c;
    }

    public void setIncomingStartScale(float f) {
        this.c = f;
    }

    public float getIncomingEndScale() {
        return this.d;
    }

    public void setIncomingEndScale(float f) {
        this.d = f;
    }

    @Override // com.google.android.material.transition.platform.VisibilityAnimatorProvider
    @Nullable
    public Animator createAppear(@NonNull ViewGroup viewGroup, @NonNull View view) {
        if (this.e) {
            return a(view, this.c, this.d);
        }
        return a(view, this.b, this.a);
    }

    @Override // com.google.android.material.transition.platform.VisibilityAnimatorProvider
    @Nullable
    public Animator createDisappear(@NonNull ViewGroup viewGroup, @NonNull View view) {
        if (!this.f) {
            return null;
        }
        if (this.e) {
            return a(view, this.a, this.b);
        }
        return a(view, this.d, this.c);
    }

    private static Animator a(final View view, float f, float f2) {
        final float scaleX = view.getScaleX();
        final float scaleY = view.getScaleY();
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat(View.SCALE_X, scaleX * f, scaleX * f2), PropertyValuesHolder.ofFloat(View.SCALE_Y, f * scaleY, f2 * scaleY));
        ofPropertyValuesHolder.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.transition.platform.ScaleProvider.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                view.setScaleX(scaleX);
                view.setScaleY(scaleY);
            }
        });
        return ofPropertyValuesHolder;
    }
}
