package com.bumptech.glide.request.transition;

import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.DataSource;

/* loaded from: classes.dex */
public class DrawableCrossFadeFactory implements TransitionFactory<Drawable> {
    private final int a;
    private final boolean b;
    private DrawableCrossFadeTransition c;

    protected DrawableCrossFadeFactory(int i, boolean z) {
        this.a = i;
        this.b = z;
    }

    @Override // com.bumptech.glide.request.transition.TransitionFactory
    public Transition<Drawable> build(DataSource dataSource, boolean z) {
        if (dataSource == DataSource.MEMORY_CACHE) {
            return NoTransition.get();
        }
        return a();
    }

    private Transition<Drawable> a() {
        if (this.c == null) {
            this.c = new DrawableCrossFadeTransition(this.a, this.b);
        }
        return this.c;
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private final int a;
        private boolean b;

        public Builder() {
            this(300);
        }

        public Builder(int i) {
            this.a = i;
        }

        public Builder setCrossFadeEnabled(boolean z) {
            this.b = z;
            return this;
        }

        public DrawableCrossFadeFactory build() {
            return new DrawableCrossFadeFactory(this.a, this.b);
        }
    }
}
