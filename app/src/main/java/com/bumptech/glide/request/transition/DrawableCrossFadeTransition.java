package com.bumptech.glide.request.transition;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import com.bumptech.glide.request.transition.Transition;

/* loaded from: classes.dex */
public class DrawableCrossFadeTransition implements Transition<Drawable> {
    private final int a;
    private final boolean b;

    public DrawableCrossFadeTransition(int i, boolean z) {
        this.a = i;
        this.b = z;
    }

    public boolean transition(Drawable drawable, Transition.ViewAdapter viewAdapter) {
        Drawable currentDrawable = viewAdapter.getCurrentDrawable();
        if (currentDrawable == null) {
            currentDrawable = new ColorDrawable(0);
        }
        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{currentDrawable, drawable});
        transitionDrawable.setCrossFadeEnabled(this.b);
        transitionDrawable.startTransition(this.a);
        viewAdapter.setDrawable(transitionDrawable);
        return true;
    }
}
