package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import androidx.annotation.Nullable;

/* compiled from: AnimatorTracker.java */
/* loaded from: classes2.dex */
class a {
    @Nullable
    private Animator a;

    public void a(Animator animator) {
        a();
        this.a = animator;
    }

    public void a() {
        Animator animator = this.a;
        if (animator != null) {
            animator.cancel();
        }
    }

    public void b() {
        this.a = null;
    }
}
