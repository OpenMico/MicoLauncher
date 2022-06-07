package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.transition.platform.VisibilityAnimatorProvider;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MaterialVisibility.java */
@RequiresApi(21)
/* loaded from: classes2.dex */
abstract class h<P extends VisibilityAnimatorProvider> extends Visibility {
    private final P a;
    @Nullable
    private VisibilityAnimatorProvider b;
    private final List<VisibilityAnimatorProvider> c = new ArrayList();

    @AttrRes
    int a(boolean z) {
        return 0;
    }

    @AttrRes
    int b(boolean z) {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public h(P p, @Nullable VisibilityAnimatorProvider visibilityAnimatorProvider) {
        this.a = p;
        this.b = visibilityAnimatorProvider;
    }

    @NonNull
    public P getPrimaryAnimatorProvider() {
        return this.a;
    }

    @Nullable
    public VisibilityAnimatorProvider getSecondaryAnimatorProvider() {
        return this.b;
    }

    public void setSecondaryAnimatorProvider(@Nullable VisibilityAnimatorProvider visibilityAnimatorProvider) {
        this.b = visibilityAnimatorProvider;
    }

    public void addAdditionalAnimatorProvider(@NonNull VisibilityAnimatorProvider visibilityAnimatorProvider) {
        this.c.add(visibilityAnimatorProvider);
    }

    public boolean removeAdditionalAnimatorProvider(@NonNull VisibilityAnimatorProvider visibilityAnimatorProvider) {
        return this.c.remove(visibilityAnimatorProvider);
    }

    public void clearAdditionalAnimatorProvider() {
        this.c.clear();
    }

    @Override // android.transition.Visibility
    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return a(viewGroup, view, true);
    }

    @Override // android.transition.Visibility
    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return a(viewGroup, view, false);
    }

    private Animator a(@NonNull ViewGroup viewGroup, @NonNull View view, boolean z) {
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        a(arrayList, this.a, viewGroup, view, z);
        a(arrayList, this.b, viewGroup, view, z);
        for (VisibilityAnimatorProvider visibilityAnimatorProvider : this.c) {
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
        j.a(this, context, a(z));
        j.a(this, context, b(z), c(z));
    }

    @NonNull
    TimeInterpolator c(boolean z) {
        return AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
    }
}
