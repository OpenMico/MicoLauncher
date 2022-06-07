package com.google.android.material.progressindicator;

import android.animation.Animator;
import androidx.annotation.NonNull;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: IndeterminateAnimatorDelegate.java */
/* loaded from: classes2.dex */
public abstract class e<T extends Animator> {
    protected IndeterminateDrawable b;
    protected final float[] c;
    protected final int[] d;

    protected float a(int i, int i2, int i3) {
        return (i - i2) / i3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void a();

    public abstract void a(@NonNull Animatable2Compat.AnimationCallback animationCallback);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void b();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void c();

    public abstract void d();

    public abstract void e();

    /* JADX INFO: Access modifiers changed from: protected */
    public e(int i) {
        this.c = new float[i * 2];
        this.d = new int[i];
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void a(@NonNull IndeterminateDrawable indeterminateDrawable) {
        this.b = indeterminateDrawable;
    }
}
