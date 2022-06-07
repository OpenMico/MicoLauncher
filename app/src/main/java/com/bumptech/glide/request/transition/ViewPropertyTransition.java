package com.bumptech.glide.request.transition;

import android.view.View;
import com.bumptech.glide.request.transition.Transition;

/* loaded from: classes.dex */
public class ViewPropertyTransition<R> implements Transition<R> {
    private final Animator a;

    /* loaded from: classes.dex */
    public interface Animator {
        void animate(View view);
    }

    public ViewPropertyTransition(Animator animator) {
        this.a = animator;
    }

    @Override // com.bumptech.glide.request.transition.Transition
    public boolean transition(R r, Transition.ViewAdapter viewAdapter) {
        if (viewAdapter.getView() == null) {
            return false;
        }
        this.a.animate(viewAdapter.getView());
        return false;
    }
}
