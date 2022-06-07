package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.view.GravityCompat;
import com.google.android.material.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RequiresApi(21)
/* loaded from: classes2.dex */
public final class MaterialSharedAxis extends h<VisibilityAnimatorProvider> {
    public static final int X = 0;
    public static final int Y = 1;
    public static final int Z = 2;
    @AttrRes
    private static final int c = R.attr.motionDurationLong1;
    @AttrRes
    private static final int d = R.attr.motionEasingStandard;
    private final int a;
    private final boolean b;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    /* loaded from: classes2.dex */
    public @interface Axis {
    }

    @Override // com.google.android.material.transition.platform.h
    public /* bridge */ /* synthetic */ void addAdditionalAnimatorProvider(@NonNull VisibilityAnimatorProvider visibilityAnimatorProvider) {
        super.addAdditionalAnimatorProvider(visibilityAnimatorProvider);
    }

    @Override // com.google.android.material.transition.platform.h
    public /* bridge */ /* synthetic */ void clearAdditionalAnimatorProvider() {
        super.clearAdditionalAnimatorProvider();
    }

    @Override // com.google.android.material.transition.platform.h
    @NonNull
    public /* bridge */ /* synthetic */ VisibilityAnimatorProvider getPrimaryAnimatorProvider() {
        return super.getPrimaryAnimatorProvider();
    }

    @Override // com.google.android.material.transition.platform.h
    @Nullable
    public /* bridge */ /* synthetic */ VisibilityAnimatorProvider getSecondaryAnimatorProvider() {
        return super.getSecondaryAnimatorProvider();
    }

    @Override // com.google.android.material.transition.platform.h, android.transition.Visibility
    public /* bridge */ /* synthetic */ Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return super.onAppear(viewGroup, view, transitionValues, transitionValues2);
    }

    @Override // com.google.android.material.transition.platform.h, android.transition.Visibility
    public /* bridge */ /* synthetic */ Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return super.onDisappear(viewGroup, view, transitionValues, transitionValues2);
    }

    @Override // com.google.android.material.transition.platform.h
    public /* bridge */ /* synthetic */ boolean removeAdditionalAnimatorProvider(@NonNull VisibilityAnimatorProvider visibilityAnimatorProvider) {
        return super.removeAdditionalAnimatorProvider(visibilityAnimatorProvider);
    }

    @Override // com.google.android.material.transition.platform.h
    public /* bridge */ /* synthetic */ void setSecondaryAnimatorProvider(@Nullable VisibilityAnimatorProvider visibilityAnimatorProvider) {
        super.setSecondaryAnimatorProvider(visibilityAnimatorProvider);
    }

    public MaterialSharedAxis(int i, boolean z) {
        super(a(i, z), a());
        this.a = i;
        this.b = z;
    }

    public int getAxis() {
        return this.a;
    }

    public boolean isForward() {
        return this.b;
    }

    private static VisibilityAnimatorProvider a(int i, boolean z) {
        switch (i) {
            case 0:
                return new SlideDistanceProvider(z ? GravityCompat.END : GravityCompat.START);
            case 1:
                return new SlideDistanceProvider(z ? 80 : 48);
            case 2:
                return new ScaleProvider(z);
            default:
                throw new IllegalArgumentException("Invalid axis: " + i);
        }
    }

    private static VisibilityAnimatorProvider a() {
        return new FadeThroughProvider();
    }

    @Override // com.google.android.material.transition.platform.h
    @AttrRes
    int a(boolean z) {
        return c;
    }

    @Override // com.google.android.material.transition.platform.h
    @AttrRes
    int b(boolean z) {
        return d;
    }
}
