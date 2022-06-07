package androidx.appcompat.view;

import android.view.View;
import android.view.animation.Interpolator;
import androidx.annotation.RestrictTo;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import java.util.ArrayList;
import java.util.Iterator;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class ViewPropertyAnimatorCompatSet {
    ViewPropertyAnimatorListener b;
    private Interpolator d;
    private boolean e;
    private long c = -1;
    private final ViewPropertyAnimatorListenerAdapter f = new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.view.ViewPropertyAnimatorCompatSet.1
        private boolean b = false;
        private int c = 0;

        @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationStart(View view) {
            if (!this.b) {
                this.b = true;
                if (ViewPropertyAnimatorCompatSet.this.b != null) {
                    ViewPropertyAnimatorCompatSet.this.b.onAnimationStart(null);
                }
            }
        }

        void a() {
            this.c = 0;
            this.b = false;
            ViewPropertyAnimatorCompatSet.this.a();
        }

        @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationEnd(View view) {
            int i = this.c + 1;
            this.c = i;
            if (i == ViewPropertyAnimatorCompatSet.this.a.size()) {
                if (ViewPropertyAnimatorCompatSet.this.b != null) {
                    ViewPropertyAnimatorCompatSet.this.b.onAnimationEnd(null);
                }
                a();
            }
        }
    };
    final ArrayList<ViewPropertyAnimatorCompat> a = new ArrayList<>();

    public ViewPropertyAnimatorCompatSet play(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
        if (!this.e) {
            this.a.add(viewPropertyAnimatorCompat);
        }
        return this;
    }

    public ViewPropertyAnimatorCompatSet playSequentially(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2) {
        this.a.add(viewPropertyAnimatorCompat);
        viewPropertyAnimatorCompat2.setStartDelay(viewPropertyAnimatorCompat.getDuration());
        this.a.add(viewPropertyAnimatorCompat2);
        return this;
    }

    public void start() {
        if (!this.e) {
            Iterator<ViewPropertyAnimatorCompat> it = this.a.iterator();
            while (it.hasNext()) {
                ViewPropertyAnimatorCompat next = it.next();
                long j = this.c;
                if (j >= 0) {
                    next.setDuration(j);
                }
                Interpolator interpolator = this.d;
                if (interpolator != null) {
                    next.setInterpolator(interpolator);
                }
                if (this.b != null) {
                    next.setListener(this.f);
                }
                next.start();
            }
            this.e = true;
        }
    }

    void a() {
        this.e = false;
    }

    public void cancel() {
        if (this.e) {
            Iterator<ViewPropertyAnimatorCompat> it = this.a.iterator();
            while (it.hasNext()) {
                it.next().cancel();
            }
            this.e = false;
        }
    }

    public ViewPropertyAnimatorCompatSet setDuration(long j) {
        if (!this.e) {
            this.c = j;
        }
        return this;
    }

    public ViewPropertyAnimatorCompatSet setInterpolator(Interpolator interpolator) {
        if (!this.e) {
            this.d = interpolator;
        }
        return this;
    }

    public ViewPropertyAnimatorCompatSet setListener(ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        if (!this.e) {
            this.b = viewPropertyAnimatorListener;
        }
        return this;
    }
}
