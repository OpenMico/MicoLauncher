package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MotionStrategy.java */
/* loaded from: classes2.dex */
public interface f {
    void a(@NonNull Animator.AnimatorListener animatorListener);

    void a(Animator animator);

    void a(@Nullable MotionSpec motionSpec);

    void a(@Nullable ExtendedFloatingActionButton.OnChangedCallback onChangedCallback);

    List<Animator.AnimatorListener> b();

    void b(@NonNull Animator.AnimatorListener animatorListener);

    @Nullable
    MotionSpec c();

    void d();

    void e();

    AnimatorSet f();

    void g();

    @AnimatorRes
    int h();

    boolean i();
}
