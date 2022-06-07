package com.bumptech.glide.request.transition;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import com.bumptech.glide.request.transition.Transition;

/* loaded from: classes.dex */
public class ViewTransition<R> implements Transition<R> {
    private final a a;

    /* loaded from: classes.dex */
    interface a {
        Animation a(Context context);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ViewTransition(a aVar) {
        this.a = aVar;
    }

    @Override // com.bumptech.glide.request.transition.Transition
    public boolean transition(R r, Transition.ViewAdapter viewAdapter) {
        View view = viewAdapter.getView();
        if (view == null) {
            return false;
        }
        view.clearAnimation();
        view.startAnimation(this.a.a(view.getContext()));
        return false;
    }
}
