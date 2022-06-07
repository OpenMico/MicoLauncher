package androidx.vectordrawable.graphics.drawable;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* loaded from: classes.dex */
public interface Animatable2Compat extends Animatable {
    void clearAnimationCallbacks();

    void registerAnimationCallback(@NonNull AnimationCallback animationCallback);

    boolean unregisterAnimationCallback(@NonNull AnimationCallback animationCallback);

    /* loaded from: classes.dex */
    public static abstract class AnimationCallback {
        Animatable2.AnimationCallback a;

        public void onAnimationEnd(Drawable drawable) {
        }

        public void onAnimationStart(Drawable drawable) {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @RequiresApi(23)
        public Animatable2.AnimationCallback a() {
            if (this.a == null) {
                this.a = new Animatable2.AnimationCallback() { // from class: androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback.1
                    @Override // android.graphics.drawable.Animatable2.AnimationCallback
                    public void onAnimationStart(Drawable drawable) {
                        AnimationCallback.this.onAnimationStart(drawable);
                    }

                    @Override // android.graphics.drawable.Animatable2.AnimationCallback
                    public void onAnimationEnd(Drawable drawable) {
                        AnimationCallback.this.onAnimationEnd(drawable);
                    }
                };
            }
            return this.a;
        }
    }
}
