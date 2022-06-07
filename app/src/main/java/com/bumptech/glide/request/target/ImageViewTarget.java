package com.bumptech.glide.request.target;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.transition.Transition;

/* loaded from: classes.dex */
public abstract class ImageViewTarget<Z> extends ViewTarget<ImageView, Z> implements Transition.ViewAdapter {
    @Nullable
    private Animatable a;

    protected abstract void setResource(@Nullable Z z);

    public ImageViewTarget(ImageView imageView) {
        super(imageView);
    }

    @Deprecated
    public ImageViewTarget(ImageView imageView, boolean z) {
        super(imageView, z);
    }

    @Override // com.bumptech.glide.request.transition.Transition.ViewAdapter
    @Nullable
    public Drawable getCurrentDrawable() {
        return ((ImageView) this.view).getDrawable();
    }

    @Override // com.bumptech.glide.request.transition.Transition.ViewAdapter
    public void setDrawable(Drawable drawable) {
        ((ImageView) this.view).setImageDrawable(drawable);
    }

    @Override // com.bumptech.glide.request.target.ViewTarget, com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
    public void onLoadStarted(@Nullable Drawable drawable) {
        super.onLoadStarted(drawable);
        a(null);
        setDrawable(drawable);
    }

    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
    public void onLoadFailed(@Nullable Drawable drawable) {
        super.onLoadFailed(drawable);
        a(null);
        setDrawable(drawable);
    }

    @Override // com.bumptech.glide.request.target.ViewTarget, com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
    public void onLoadCleared(@Nullable Drawable drawable) {
        super.onLoadCleared(drawable);
        Animatable animatable = this.a;
        if (animatable != null) {
            animatable.stop();
        }
        a(null);
        setDrawable(drawable);
    }

    @Override // com.bumptech.glide.request.target.Target
    public void onResourceReady(@NonNull Z z, @Nullable Transition<? super Z> transition) {
        if (transition == null || !transition.transition(z, this)) {
            a(z);
        } else {
            b(z);
        }
    }

    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.manager.LifecycleListener
    public void onStart() {
        Animatable animatable = this.a;
        if (animatable != null) {
            animatable.start();
        }
    }

    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.manager.LifecycleListener
    public void onStop() {
        Animatable animatable = this.a;
        if (animatable != null) {
            animatable.stop();
        }
    }

    private void a(@Nullable Z z) {
        setResource(z);
        b(z);
    }

    private void b(@Nullable Z z) {
        if (z instanceof Animatable) {
            this.a = (Animatable) z;
            this.a.start();
            return;
        }
        this.a = null;
    }
}
