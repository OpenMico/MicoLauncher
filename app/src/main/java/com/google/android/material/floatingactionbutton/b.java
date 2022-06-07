package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Property;
import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Preconditions;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.MotionSpec;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BaseMotionStrategy.java */
/* loaded from: classes2.dex */
public abstract class b implements f {
    private final Context a;
    @NonNull
    private final ExtendedFloatingActionButton b;
    private final ArrayList<Animator.AnimatorListener> c = new ArrayList<>();
    private final a d;
    @Nullable
    private MotionSpec e;
    @Nullable
    private MotionSpec f;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(@NonNull ExtendedFloatingActionButton extendedFloatingActionButton, a aVar) {
        this.b = extendedFloatingActionButton;
        this.a = extendedFloatingActionButton.getContext();
        this.d = aVar;
    }

    @Override // com.google.android.material.floatingactionbutton.f
    public final void a(@Nullable MotionSpec motionSpec) {
        this.f = motionSpec;
    }

    public final MotionSpec a() {
        MotionSpec motionSpec = this.f;
        if (motionSpec != null) {
            return motionSpec;
        }
        if (this.e == null) {
            this.e = MotionSpec.createFromResource(this.a, h());
        }
        return (MotionSpec) Preconditions.checkNotNull(this.e);
    }

    @Override // com.google.android.material.floatingactionbutton.f
    public final void a(@NonNull Animator.AnimatorListener animatorListener) {
        this.c.add(animatorListener);
    }

    @Override // com.google.android.material.floatingactionbutton.f
    public final void b(@NonNull Animator.AnimatorListener animatorListener) {
        this.c.remove(animatorListener);
    }

    @Override // com.google.android.material.floatingactionbutton.f
    @NonNull
    public final List<Animator.AnimatorListener> b() {
        return this.c;
    }

    @Override // com.google.android.material.floatingactionbutton.f
    @Nullable
    public MotionSpec c() {
        return this.f;
    }

    @Override // com.google.android.material.floatingactionbutton.f
    @CallSuper
    public void a(Animator animator) {
        this.d.a(animator);
    }

    @Override // com.google.android.material.floatingactionbutton.f
    @CallSuper
    public void d() {
        this.d.b();
    }

    @Override // com.google.android.material.floatingactionbutton.f
    @CallSuper
    public void e() {
        this.d.b();
    }

    @Override // com.google.android.material.floatingactionbutton.f
    public AnimatorSet f() {
        return b(a());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public AnimatorSet b(@NonNull MotionSpec motionSpec) {
        ArrayList arrayList = new ArrayList();
        if (motionSpec.hasPropertyValues("opacity")) {
            arrayList.add(motionSpec.getAnimator("opacity", this.b, View.ALPHA));
        }
        if (motionSpec.hasPropertyValues("scale")) {
            arrayList.add(motionSpec.getAnimator("scale", this.b, View.SCALE_Y));
            arrayList.add(motionSpec.getAnimator("scale", this.b, View.SCALE_X));
        }
        if (motionSpec.hasPropertyValues("width")) {
            arrayList.add(motionSpec.getAnimator("width", this.b, ExtendedFloatingActionButton.a));
        }
        if (motionSpec.hasPropertyValues("height")) {
            arrayList.add(motionSpec.getAnimator("height", this.b, ExtendedFloatingActionButton.b));
        }
        if (motionSpec.hasPropertyValues("paddingStart")) {
            arrayList.add(motionSpec.getAnimator("paddingStart", this.b, ExtendedFloatingActionButton.c));
        }
        if (motionSpec.hasPropertyValues("paddingEnd")) {
            arrayList.add(motionSpec.getAnimator("paddingEnd", this.b, ExtendedFloatingActionButton.d));
        }
        if (motionSpec.hasPropertyValues("labelOpacity")) {
            arrayList.add(motionSpec.getAnimator("labelOpacity", this.b, new Property<ExtendedFloatingActionButton, Float>(Float.class, "LABEL_OPACITY_PROPERTY") { // from class: com.google.android.material.floatingactionbutton.b.1
                /* renamed from: a */
                public Float get(ExtendedFloatingActionButton extendedFloatingActionButton) {
                    return Float.valueOf(AnimationUtils.lerp(0.0f, 1.0f, (Color.alpha(extendedFloatingActionButton.getCurrentTextColor()) / 255.0f) / Color.alpha(extendedFloatingActionButton.originalTextCsl.getColorForState(extendedFloatingActionButton.getDrawableState(), b.this.b.originalTextCsl.getDefaultColor()))));
                }

                /* renamed from: a */
                public void set(ExtendedFloatingActionButton extendedFloatingActionButton, Float f) {
                    int colorForState = extendedFloatingActionButton.originalTextCsl.getColorForState(extendedFloatingActionButton.getDrawableState(), b.this.b.originalTextCsl.getDefaultColor());
                    ColorStateList valueOf = ColorStateList.valueOf(Color.argb((int) (AnimationUtils.lerp(0.0f, Color.alpha(colorForState) / 255.0f, f.floatValue()) * 255.0f), Color.red(colorForState), Color.green(colorForState), Color.blue(colorForState)));
                    if (f.floatValue() == 1.0f) {
                        extendedFloatingActionButton.silentlyUpdateTextColor(extendedFloatingActionButton.originalTextCsl);
                    } else {
                        extendedFloatingActionButton.silentlyUpdateTextColor(valueOf);
                    }
                }
            }));
        }
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSetCompat.playTogether(animatorSet, arrayList);
        return animatorSet;
    }
}
