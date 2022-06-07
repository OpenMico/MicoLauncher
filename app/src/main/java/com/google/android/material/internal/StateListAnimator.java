package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.StateSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public final class StateListAnimator {
    private final ArrayList<a> b = new ArrayList<>();
    @Nullable
    private a c = null;
    @Nullable
    ValueAnimator a = null;
    private final Animator.AnimatorListener d = new AnimatorListenerAdapter() { // from class: com.google.android.material.internal.StateListAnimator.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (StateListAnimator.this.a == animator) {
                StateListAnimator.this.a = null;
            }
        }
    };

    public void addState(int[] iArr, ValueAnimator valueAnimator) {
        a aVar = new a(iArr, valueAnimator);
        valueAnimator.addListener(this.d);
        this.b.add(aVar);
    }

    public void setState(int[] iArr) {
        a aVar;
        int size = this.b.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                aVar = null;
                break;
            }
            aVar = this.b.get(i);
            if (StateSet.stateSetMatches(aVar.a, iArr)) {
                break;
            }
            i++;
        }
        a aVar2 = this.c;
        if (aVar != aVar2) {
            if (aVar2 != null) {
                a();
            }
            this.c = aVar;
            if (aVar != null) {
                a(aVar);
            }
        }
    }

    private void a(@NonNull a aVar) {
        this.a = aVar.b;
        this.a.start();
    }

    private void a() {
        ValueAnimator valueAnimator = this.a;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.a = null;
        }
    }

    public void jumpToCurrentState() {
        ValueAnimator valueAnimator = this.a;
        if (valueAnimator != null) {
            valueAnimator.end();
            this.a = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class a {
        final int[] a;
        final ValueAnimator b;

        a(int[] iArr, ValueAnimator valueAnimator) {
            this.a = iArr;
            this.b = valueAnimator;
        }
    }
}
