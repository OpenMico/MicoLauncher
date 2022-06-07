package com.bumptech.glide.request.transition;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.transition.Transition;

/* loaded from: classes.dex */
public abstract class BitmapContainerTransitionFactory<R> implements TransitionFactory<R> {
    private final TransitionFactory<Drawable> a;

    protected abstract Bitmap getBitmap(R r);

    public BitmapContainerTransitionFactory(TransitionFactory<Drawable> transitionFactory) {
        this.a = transitionFactory;
    }

    @Override // com.bumptech.glide.request.transition.TransitionFactory
    public Transition<R> build(DataSource dataSource, boolean z) {
        return new a(this.a.build(dataSource, z));
    }

    /* loaded from: classes.dex */
    private final class a implements Transition<R> {
        private final Transition<Drawable> b;

        a(Transition<Drawable> transition) {
            this.b = transition;
        }

        @Override // com.bumptech.glide.request.transition.Transition
        public boolean transition(R r, Transition.ViewAdapter viewAdapter) {
            return this.b.transition(new BitmapDrawable(viewAdapter.getView().getResources(), BitmapContainerTransitionFactory.this.getBitmap(r)), viewAdapter);
        }
    }
}
