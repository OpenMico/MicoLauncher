package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.transition.TransitionValues;
import androidx.transition.Visibility;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.transition.VisibilityAnimatorProvider;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MaterialVisibility.java */
/* loaded from: classes2.dex */
abstract class h<P extends VisibilityAnimatorProvider> extends Visibility {
    private final P a;
    @Nullable
    private VisibilityAnimatorProvider i;
    private final List<VisibilityAnimatorProvider> j = new ArrayList();

    @AttrRes
    int c(boolean z) {
        return 0;
    }

    @AttrRes
    int d(boolean z) {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public h(P p, @Nullable VisibilityAnimatorProvider visibilityAnimatorProvider) {
        this.a = p;
        this.i = visibilityAnimatorProvider;
    }

    @NonNull
    public P getPrimaryAnimatorProvider() {
        return this.a;
    }

    @Nullable
    public VisibilityAnimatorProvider getSecondaryAnimatorProvider() {
        return this.i;
    }

    public void setSecondaryAnimatorProvider(@Nullable VisibilityAnimatorProvider visibilityAnimatorProvider) {
        this.i = visibilityAnimatorProvider;
    }

    public void addAdditionalAnimatorProvider(@NonNull VisibilityAnimatorProvider visibilityAnimatorProvider) {
        this.j.add(visibilityAnimatorProvider);
    }

    public boolean removeAdditionalAnimatorProvider(@NonNull VisibilityAnimatorProvider visibilityAnimatorProvider) {
        return this.j.remove(visibilityAnimatorProvider);
    }

    public void clearAdditionalAnimatorProvider() {
        this.j.clear();
    }

    @Override // androidx.transition.Visibility
    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return a(viewGroup, view, true);
    }

    @Override // androidx.transition.Visibility
    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return a(viewGroup, view, false);
    }

    private Animator a(@NonNull ViewGroup viewGroup, @NonNull View view, boolean z) {
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        a(arrayList, this.a, viewGroup, view, z);
        a(arrayList, this.i, viewGroup, view, z);
        for (VisibilityAnimatorProvider visibilityAnimatorProvider : this.j) {
            a(arrayList, visibilityAnimatorProvider, viewGroup, view, z);
        }
        a(viewGroup.getContext(), z);
        AnimatorSetCompat.playTogether(animatorSet, arrayList);
        return animatorSet;
    }

    private static void a(List<Animator> list, @Nullable VisibilityAnimatorProvider visibilityAnimatorProvider, ViewGroup viewGroup, View view, boolean z) {
        Animator animator;
        if (visibilityAnimatorProvider != null) {
            if (z) {
                animator = visibilityAnimatorProvider.createAppear(viewGroup, view);
            } else {
                animator = visibilityAnimatorProvider.createDisappear(viewGroup, view);
            }
            if (animator != null) {
                list.add(animator);
            }
        }
    }

    private void a(@NonNull Context context, boolean z) {
        j.a(this, context, c(z));
        j.a(this, context, d(z), e(z));
    }

    @NonNull
    TimeInterpolator e(boolean z) {
        return AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
    }
}
