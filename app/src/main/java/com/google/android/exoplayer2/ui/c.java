package com.google.android.exoplayer2.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.SimpleExoPlayer;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: StyledPlayerControlViewLayoutManager.java */
/* loaded from: classes2.dex */
public final class c {
    private boolean A;
    private boolean B;
    private final StyledPlayerControlView a;
    @Nullable
    private final View b;
    @Nullable
    private final ViewGroup c;
    @Nullable
    private final ViewGroup d;
    @Nullable
    private final ViewGroup e;
    @Nullable
    private final ViewGroup f;
    @Nullable
    private final ViewGroup g;
    @Nullable
    private final ViewGroup h;
    @Nullable
    private final ViewGroup i;
    @Nullable
    private final View j;
    @Nullable
    private final View k;
    private final AnimatorSet l;
    private final AnimatorSet m;
    private final AnimatorSet n;
    private final AnimatorSet o;
    private final AnimatorSet p;
    private final ValueAnimator q;
    private final ValueAnimator r;
    private final Runnable s = new Runnable() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$c$t7hE4kO7LGJG107u3zWdk1EyGis
        @Override // java.lang.Runnable
        public final void run() {
            c.this.j();
        }
    };
    private final Runnable t = new Runnable() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$c$oYoMB9pE51aAkA8sCo063w41SBY
        @Override // java.lang.Runnable
        public final void run() {
            c.this.k();
        }
    };
    private final Runnable u = new Runnable() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$c$R0sK8R0M9gmFJI_JMkLn6IN5UaU
        @Override // java.lang.Runnable
        public final void run() {
            c.this.l();
        }
    };
    private final Runnable v = new Runnable() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$c$8KvvR4xX2QUpmLnUIFbKuy2gWQM
        @Override // java.lang.Runnable
        public final void run() {
            c.this.m();
        }
    };
    private final Runnable w = new Runnable() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$c$aRlOH3mXkaUN0TZ4VgUy7B86BqM
        @Override // java.lang.Runnable
        public final void run() {
            c.this.n();
        }
    };
    private final View.OnLayoutChangeListener x = new View.OnLayoutChangeListener() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$c$7-AWcDyNnDjKNB9YFHh-Jd4wm-A
        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            c.this.a(view, i, i2, i3, i4, i5, i6, i7, i8);
        }
    };
    private boolean C = true;
    private int z = 0;
    private final List<View> y = new ArrayList();

    public c(final StyledPlayerControlView styledPlayerControlView) {
        this.a = styledPlayerControlView;
        this.b = styledPlayerControlView.findViewById(R.id.exo_controls_background);
        this.c = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_center_controls);
        this.e = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_minimal_controls);
        this.d = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_bottom_bar);
        this.i = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_time);
        this.j = styledPlayerControlView.findViewById(R.id.exo_progress);
        this.f = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_basic_controls);
        this.g = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_extra_controls);
        this.h = (ViewGroup) styledPlayerControlView.findViewById(R.id.exo_extra_controls_scroll_view);
        this.k = styledPlayerControlView.findViewById(R.id.exo_overflow_show);
        View findViewById = styledPlayerControlView.findViewById(R.id.exo_overflow_hide);
        View view = this.k;
        if (!(view == null || findViewById == null)) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$c$HMaTdtQ_PNktEEmD91ouAKEFEVU
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    c.this.b(view2);
                }
            });
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$c$HMaTdtQ_PNktEEmD91ouAKEFEVU
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    c.this.b(view2);
                }
            });
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$c$Owkdn8-LtyTwqhEFoNPLi9Ai3oc
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                c.this.d(valueAnimator);
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.c.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                if ((c.this.j instanceof DefaultTimeBar) && !c.this.A) {
                    ((DefaultTimeBar) c.this.j).hideScrubber(250L);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (c.this.b != null) {
                    c.this.b.setVisibility(4);
                }
                if (c.this.c != null) {
                    c.this.c.setVisibility(4);
                }
                if (c.this.e != null) {
                    c.this.e.setVisibility(4);
                }
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat2.setInterpolator(new LinearInterpolator());
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$c$jcHQ-54pdSC9xKcnYEo9WmOmdZw
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                c.this.c(valueAnimator);
            }
        });
        ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.c.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                int i = 0;
                if (c.this.b != null) {
                    c.this.b.setVisibility(0);
                }
                if (c.this.c != null) {
                    c.this.c.setVisibility(0);
                }
                if (c.this.e != null) {
                    ViewGroup viewGroup = c.this.e;
                    if (!c.this.A) {
                        i = 4;
                    }
                    viewGroup.setVisibility(i);
                }
                if ((c.this.j instanceof DefaultTimeBar) && !c.this.A) {
                    ((DefaultTimeBar) c.this.j).showScrubber(250L);
                }
            }
        });
        Resources resources = styledPlayerControlView.getResources();
        float dimension = resources.getDimension(R.dimen.exo_styled_bottom_bar_height) - resources.getDimension(R.dimen.exo_styled_progress_bar_height);
        float dimension2 = resources.getDimension(R.dimen.exo_styled_bottom_bar_height);
        this.l = new AnimatorSet();
        this.l.setDuration(250L);
        this.l.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.c.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                c.this.a(3);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                c.this.a(1);
                if (c.this.B) {
                    styledPlayerControlView.post(c.this.s);
                    c.this.B = false;
                }
            }
        });
        this.l.play(ofFloat).with(a(0.0f, dimension, this.j)).with(a(0.0f, dimension, this.d));
        this.m = new AnimatorSet();
        this.m.setDuration(250L);
        this.m.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.c.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                c.this.a(3);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                c.this.a(2);
                if (c.this.B) {
                    styledPlayerControlView.post(c.this.s);
                    c.this.B = false;
                }
            }
        });
        this.m.play(a(dimension, dimension2, this.j)).with(a(dimension, dimension2, this.d));
        this.n = new AnimatorSet();
        this.n.setDuration(250L);
        this.n.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.c.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                c.this.a(3);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                c.this.a(2);
                if (c.this.B) {
                    styledPlayerControlView.post(c.this.s);
                    c.this.B = false;
                }
            }
        });
        this.n.play(ofFloat).with(a(0.0f, dimension2, this.j)).with(a(0.0f, dimension2, this.d));
        this.o = new AnimatorSet();
        this.o.setDuration(250L);
        this.o.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.c.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                c.this.a(4);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                c.this.a(0);
            }
        });
        this.o.play(ofFloat2).with(a(dimension, 0.0f, this.j)).with(a(dimension, 0.0f, this.d));
        this.p = new AnimatorSet();
        this.p.setDuration(250L);
        this.p.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.c.7
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                c.this.a(4);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                c.this.a(0);
            }
        });
        this.p.play(ofFloat2).with(a(dimension2, 0.0f, this.j)).with(a(dimension2, 0.0f, this.d));
        this.q = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.q.setDuration(250L);
        this.q.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$c$EXTka99fmSvLicm8b0vhFwuHC-c
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                c.this.b(valueAnimator);
            }
        });
        this.q.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.c.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                if (c.this.h != null) {
                    c.this.h.setVisibility(0);
                    c.this.h.setTranslationX(c.this.h.getWidth());
                    c.this.h.scrollTo(c.this.h.getWidth(), 0);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (c.this.f != null) {
                    c.this.f.setVisibility(4);
                }
            }
        });
        this.r = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.r.setDuration(250L);
        this.r.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$c$1CedslqVZHW-CJm5CQLQkaIwUxg
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                c.this.a(valueAnimator);
            }
        });
        this.r.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.exoplayer2.ui.c.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                if (c.this.f != null) {
                    c.this.f.setVisibility(0);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (c.this.h != null) {
                    c.this.h.setVisibility(4);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        View view = this.b;
        if (view != null) {
            view.setAlpha(floatValue);
        }
        ViewGroup viewGroup = this.c;
        if (viewGroup != null) {
            viewGroup.setAlpha(floatValue);
        }
        ViewGroup viewGroup2 = this.e;
        if (viewGroup2 != null) {
            viewGroup2.setAlpha(floatValue);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        View view = this.b;
        if (view != null) {
            view.setAlpha(floatValue);
        }
        ViewGroup viewGroup = this.c;
        if (viewGroup != null) {
            viewGroup.setAlpha(floatValue);
        }
        ViewGroup viewGroup2 = this.e;
        if (viewGroup2 != null) {
            viewGroup2.setAlpha(floatValue);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(ValueAnimator valueAnimator) {
        a(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ValueAnimator valueAnimator) {
        a(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public void a() {
        if (!this.a.isVisible()) {
            this.a.setVisibility(0);
            this.a.b();
            this.a.c();
        }
        j();
    }

    public void b() {
        int i = this.z;
        if (i != 3 && i != 2) {
            f();
            if (!this.C) {
                n();
            } else if (this.z == 1) {
                l();
            } else {
                k();
            }
        }
    }

    public void c() {
        int i = this.z;
        if (i != 3 && i != 2) {
            f();
            n();
        }
    }

    public void a(boolean z) {
        this.C = z;
    }

    public boolean d() {
        return this.C;
    }

    public void e() {
        if (this.z != 3) {
            f();
            int showTimeoutMs = this.a.getShowTimeoutMs();
            if (showTimeoutMs <= 0) {
                return;
            }
            if (!this.C) {
                a(this.w, showTimeoutMs);
            } else if (this.z == 1) {
                a(this.u, SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            } else {
                a(this.v, showTimeoutMs);
            }
        }
    }

    public void f() {
        this.a.removeCallbacks(this.w);
        this.a.removeCallbacks(this.t);
        this.a.removeCallbacks(this.v);
        this.a.removeCallbacks(this.u);
    }

    public void g() {
        this.a.addOnLayoutChangeListener(this.x);
    }

    public void h() {
        this.a.removeOnLayoutChangeListener(this.x);
    }

    public boolean i() {
        return this.z == 0 && this.a.isVisible();
    }

    public void a(@Nullable View view, boolean z) {
        if (view != null) {
            if (!z) {
                view.setVisibility(8);
                this.y.remove(view);
                return;
            }
            if (!this.A || !c(view)) {
                view.setVisibility(0);
            } else {
                view.setVisibility(4);
            }
            this.y.add(view);
        }
    }

    public boolean a(@Nullable View view) {
        return view != null && this.y.contains(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        int i2 = this.z;
        this.z = i;
        if (i == 2) {
            this.a.setVisibility(8);
        } else if (i2 == 2) {
            this.a.setVisibility(0);
        }
        if (i2 != i) {
            this.a.a();
        }
    }

    public void a(boolean z, int i, int i2, int i3, int i4) {
        View view = this.b;
        if (view != null) {
            view.layout(0, 0, i3 - i, i4 - i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        boolean o = o();
        if (this.A != o) {
            this.A = o;
            view.post(new Runnable() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$c$JYkWsdUOGBK8uTc7TJmc7B4zKe8
                @Override // java.lang.Runnable
                public final void run() {
                    c.this.p();
                }
            });
        }
        boolean z = i3 - i != i7 - i5;
        if (!this.A && z) {
            view.post(new Runnable() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$c$m7RONQKY138ynRua8UvFXKJGnyc
                @Override // java.lang.Runnable
                public final void run() {
                    c.this.q();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(View view) {
        e();
        if (view.getId() == R.id.exo_overflow_show) {
            this.q.start();
        } else if (view.getId() == R.id.exo_overflow_hide) {
            this.r.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        if (!this.C) {
            a(0);
            e();
            return;
        }
        switch (this.z) {
            case 1:
                this.o.start();
                break;
            case 2:
                this.p.start();
                break;
            case 3:
                this.B = true;
                break;
            case 4:
                return;
        }
        e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        this.n.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void l() {
        this.m.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void m() {
        this.l.start();
        a(this.u, SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void n() {
        a(2);
    }

    private static ObjectAnimator a(float f, float f2, View view) {
        return ObjectAnimator.ofFloat(view, "translationY", f, f2);
    }

    private void a(Runnable runnable, long j) {
        if (j >= 0) {
            this.a.postDelayed(runnable, j);
        }
    }

    private void a(float f) {
        ViewGroup viewGroup = this.h;
        if (viewGroup != null) {
            this.h.setTranslationX((int) (viewGroup.getWidth() * (1.0f - f)));
        }
        ViewGroup viewGroup2 = this.i;
        if (viewGroup2 != null) {
            viewGroup2.setAlpha(1.0f - f);
        }
        ViewGroup viewGroup3 = this.f;
        if (viewGroup3 != null) {
            viewGroup3.setAlpha(1.0f - f);
        }
    }

    private boolean o() {
        int width = (this.a.getWidth() - this.a.getPaddingLeft()) - this.a.getPaddingRight();
        int height = (this.a.getHeight() - this.a.getPaddingBottom()) - this.a.getPaddingTop();
        int d = d(this.c);
        ViewGroup viewGroup = this.c;
        int paddingLeft = d - (viewGroup != null ? viewGroup.getPaddingLeft() + this.c.getPaddingRight() : 0);
        int e = e(this.c);
        ViewGroup viewGroup2 = this.c;
        return width <= Math.max(paddingLeft, d(this.i) + d(this.k)) || height <= (e - (viewGroup2 != null ? viewGroup2.getPaddingTop() + this.c.getPaddingBottom() : 0)) + (e(this.d) * 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void p() {
        ViewGroup viewGroup = this.e;
        if (viewGroup != null) {
            viewGroup.setVisibility(this.A ? 0 : 4);
        }
        View view = this.j;
        if (view != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            int dimensionPixelSize = this.a.getResources().getDimensionPixelSize(R.dimen.exo_styled_progress_margin_bottom);
            if (this.A) {
                dimensionPixelSize = 0;
            }
            marginLayoutParams.bottomMargin = dimensionPixelSize;
            this.j.setLayoutParams(marginLayoutParams);
            View view2 = this.j;
            if (view2 instanceof DefaultTimeBar) {
                DefaultTimeBar defaultTimeBar = (DefaultTimeBar) view2;
                if (this.A) {
                    defaultTimeBar.hideScrubber(true);
                } else {
                    int i = this.z;
                    if (i == 1) {
                        defaultTimeBar.hideScrubber(false);
                    } else if (i != 3) {
                        defaultTimeBar.showScrubber();
                    }
                }
            }
        }
        for (View view3 : this.y) {
            view3.setVisibility((!this.A || !c(view3)) ? 0 : 4);
        }
    }

    private boolean c(View view) {
        int id = view.getId();
        return id == R.id.exo_bottom_bar || id == R.id.exo_prev || id == R.id.exo_next || id == R.id.exo_rew || id == R.id.exo_rew_with_amount || id == R.id.exo_ffwd || id == R.id.exo_ffwd_with_amount;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void q() {
        int i;
        if (!(this.f == null || this.g == null)) {
            int width = (this.a.getWidth() - this.a.getPaddingLeft()) - this.a.getPaddingRight();
            while (true) {
                if (this.g.getChildCount() <= 1) {
                    break;
                }
                int childCount = this.g.getChildCount() - 2;
                View childAt = this.g.getChildAt(childCount);
                this.g.removeViewAt(childCount);
                this.f.addView(childAt, 0);
            }
            View view = this.k;
            if (view != null) {
                view.setVisibility(8);
            }
            int d = d(this.i);
            int childCount2 = this.f.getChildCount() - 1;
            for (int i2 = 0; i2 < childCount2; i2++) {
                d += d(this.f.getChildAt(i2));
            }
            if (d > width) {
                View view2 = this.k;
                if (view2 != null) {
                    view2.setVisibility(0);
                    d += d(this.k);
                }
                ArrayList arrayList = new ArrayList();
                for (int i3 = 0; i3 < childCount2; i3++) {
                    View childAt2 = this.f.getChildAt(i3);
                    d -= d(childAt2);
                    arrayList.add(childAt2);
                    if (d <= width) {
                        break;
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.f.removeViews(0, arrayList.size());
                    for (i = 0; i < arrayList.size(); i++) {
                        this.g.addView((View) arrayList.get(i), this.g.getChildCount() - 1);
                    }
                    return;
                }
                return;
            }
            ViewGroup viewGroup = this.h;
            if (!(viewGroup == null || viewGroup.getVisibility() != 0 || this.r.isStarted())) {
                this.q.cancel();
                this.r.start();
            }
        }
    }

    private static int d(@Nullable View view) {
        if (view == null) {
            return 0;
        }
        int width = view.getWidth();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return width;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        return width + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
    }

    private static int e(@Nullable View view) {
        if (view == null) {
            return 0;
        }
        int height = view.getHeight();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return height;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        return height + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }
}
