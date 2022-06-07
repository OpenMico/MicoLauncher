package com.bumptech.glide.request.transition;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.request.transition.Transition;

/* loaded from: classes.dex */
public class NoTransition<R> implements Transition<R> {
    static final NoTransition<?> a = new NoTransition<>();
    private static final TransitionFactory<?> b = new NoAnimationFactory();

    @Override // com.bumptech.glide.request.transition.Transition
    public boolean transition(Object obj, Transition.ViewAdapter viewAdapter) {
        return false;
    }

    /* loaded from: classes.dex */
    public static class NoAnimationFactory<R> implements TransitionFactory<R> {
        @Override // com.bumptech.glide.request.transition.TransitionFactory
        public Transition<R> build(DataSource dataSource, boolean z) {
            return NoTransition.a;
        }
    }

    public static <R> TransitionFactory<R> getFactory() {
        return (TransitionFactory<R>) b;
    }

    public static <R> Transition<R> get() {
        return a;
    }
}
