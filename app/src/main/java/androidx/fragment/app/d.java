package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.fragment.R;
import androidx.fragment.app.n;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: FragmentAnim.java */
/* loaded from: classes.dex */
public class d {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static a a(@NonNull Context context, @NonNull Fragment fragment, boolean z, boolean z2) {
        int nextTransition = fragment.getNextTransition();
        int a2 = a(fragment, z, z2);
        boolean z3 = false;
        fragment.setAnimations(0, 0, 0, 0);
        if (!(fragment.mContainer == null || fragment.mContainer.getTag(R.id.visible_removing_fragment_view_tag) == null)) {
            fragment.mContainer.setTag(R.id.visible_removing_fragment_view_tag, null);
        }
        if (fragment.mContainer != null && fragment.mContainer.getLayoutTransition() != null) {
            return null;
        }
        Animation onCreateAnimation = fragment.onCreateAnimation(nextTransition, z, a2);
        if (onCreateAnimation != null) {
            return new a(onCreateAnimation);
        }
        Animator onCreateAnimator = fragment.onCreateAnimator(nextTransition, z, a2);
        if (onCreateAnimator != null) {
            return new a(onCreateAnimator);
        }
        if (a2 == 0 && nextTransition != 0) {
            a2 = a(nextTransition, z);
        }
        if (a2 != 0) {
            boolean equals = "anim".equals(context.getResources().getResourceTypeName(a2));
            if (equals) {
                try {
                    Animation loadAnimation = AnimationUtils.loadAnimation(context, a2);
                    if (loadAnimation != null) {
                        return new a(loadAnimation);
                    }
                    z3 = true;
                } catch (Resources.NotFoundException e) {
                    throw e;
                } catch (RuntimeException unused) {
                }
            }
            if (!z3) {
                try {
                    Animator loadAnimator = AnimatorInflater.loadAnimator(context, a2);
                    if (loadAnimator != null) {
                        return new a(loadAnimator);
                    }
                } catch (RuntimeException e2) {
                    if (!equals) {
                        Animation loadAnimation2 = AnimationUtils.loadAnimation(context, a2);
                        if (loadAnimation2 != null) {
                            return new a(loadAnimation2);
                        }
                    } else {
                        throw e2;
                    }
                }
            }
        }
        return null;
    }

    private static int a(Fragment fragment, boolean z, boolean z2) {
        if (z2) {
            if (z) {
                return fragment.getPopEnterAnim();
            }
            return fragment.getPopExitAnim();
        } else if (z) {
            return fragment.getEnterAnim();
        } else {
            return fragment.getExitAnim();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(@NonNull final Fragment fragment, @NonNull a aVar, @NonNull final n.a aVar2) {
        final View view = fragment.mView;
        final ViewGroup viewGroup = fragment.mContainer;
        viewGroup.startViewTransition(view);
        final CancellationSignal cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.d.1
            @Override // androidx.core.os.CancellationSignal.OnCancelListener
            public void onCancel() {
                if (Fragment.this.getAnimatingAway() != null) {
                    View animatingAway = Fragment.this.getAnimatingAway();
                    Fragment.this.setAnimatingAway(null);
                    animatingAway.clearAnimation();
                }
                Fragment.this.setAnimator(null);
            }
        });
        aVar2.a(fragment, cancellationSignal);
        if (aVar.a != null) {
            b bVar = new b(aVar.a, viewGroup, view);
            fragment.setAnimatingAway(fragment.mView);
            bVar.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.fragment.app.d.2
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    viewGroup.post(new Runnable() { // from class: androidx.fragment.app.d.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (fragment.getAnimatingAway() != null) {
                                fragment.setAnimatingAway(null);
                                aVar2.b(fragment, cancellationSignal);
                            }
                        }
                    });
                }
            });
            fragment.mView.startAnimation(bVar);
            return;
        }
        Animator animator = aVar.b;
        fragment.setAnimator(aVar.b);
        animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.fragment.app.d.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                viewGroup.endViewTransition(view);
                Animator animator3 = fragment.getAnimator();
                fragment.setAnimator(null);
                if (animator3 != null && viewGroup.indexOfChild(view) < 0) {
                    aVar2.b(fragment, cancellationSignal);
                }
            }
        });
        animator.setTarget(fragment.mView);
        animator.start();
    }

    @AnimRes
    private static int a(int i, boolean z) {
        if (i == 4097) {
            return z ? R.animator.fragment_open_enter : R.animator.fragment_open_exit;
        }
        if (i == 4099) {
            return z ? R.animator.fragment_fade_enter : R.animator.fragment_fade_exit;
        }
        if (i != 8194) {
            return -1;
        }
        return z ? R.animator.fragment_close_enter : R.animator.fragment_close_exit;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: FragmentAnim.java */
    /* loaded from: classes.dex */
    public static class a {
        public final Animation a;
        public final Animator b;

        a(Animation animation) {
            this.a = animation;
            this.b = null;
            if (animation == null) {
                throw new IllegalStateException("Animation cannot be null");
            }
        }

        a(Animator animator) {
            this.a = null;
            this.b = animator;
            if (animator == null) {
                throw new IllegalStateException("Animator cannot be null");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: FragmentAnim.java */
    /* loaded from: classes.dex */
    public static class b extends AnimationSet implements Runnable {
        private final ViewGroup a;
        private final View b;
        private boolean c;
        private boolean d;
        private boolean e = true;

        /* JADX INFO: Access modifiers changed from: package-private */
        public b(@NonNull Animation animation, @NonNull ViewGroup viewGroup, @NonNull View view) {
            super(false);
            this.a = viewGroup;
            this.b = view;
            addAnimation(animation);
            this.a.post(this);
        }

        @Override // android.view.animation.AnimationSet, android.view.animation.Animation
        public boolean getTransformation(long j, @NonNull Transformation transformation) {
            this.e = true;
            if (this.c) {
                return !this.d;
            }
            if (!super.getTransformation(j, transformation)) {
                this.c = true;
                OneShotPreDrawListener.add(this.a, this);
            }
            return true;
        }

        @Override // android.view.animation.Animation
        public boolean getTransformation(long j, @NonNull Transformation transformation, float f) {
            this.e = true;
            if (this.c) {
                return !this.d;
            }
            if (!super.getTransformation(j, transformation, f)) {
                this.c = true;
                OneShotPreDrawListener.add(this.a, this);
            }
            return true;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.c || !this.e) {
                this.a.endViewTransition(this.b);
                this.d = true;
                return;
            }
            this.e = false;
            this.a.post(this);
        }
    }
}
